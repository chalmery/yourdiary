package com.example.yourdiary.imageView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
/**
 * 设置圆形的头像
 */
public class HeadImageView extends AppCompatImageView {
    //画笔
    private Paint mPaint;
    //圆形图片的半径
    private int mRadius;
    //图片的宿放比例
    private float mScale;

    public HeadImageView(Context context) {
        super(context);
    }

    public HeadImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HeadImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //裁剪图片，以宽，高的最小值为直径，
        int size = Math.min(getMeasuredWidth(), getMeasuredHeight());
        //设置半径
        mRadius = size / 2;
        //设置imageView大小
        setMeasuredDimension(size, size);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        mPaint = new Paint();
        //获取到头像，转换为bitmap类型
        Drawable drawable = getDrawable();
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();

       if (drawable != null ) {
           //初始化BitmapShader，传入bitmap对象，
           // TileMode.CLMP 是如果需要填充的内容大小超过了bitmap size 就选bitmap 边界的颜色进行扩展
           BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
           //计算缩放比例，即
           mScale = (mRadius * 2.00f) / Math.min(bitmap.getHeight(), bitmap.getWidth());
           //对图片进行缩放
           Matrix matrix = new Matrix();
           matrix.setScale(mScale, mScale);
           bitmapShader.setLocalMatrix(matrix);
           mPaint.setShader(bitmapShader);
           //画圆形，指定圆心x,y坐标，半径，画笔
           canvas.drawCircle(mRadius, mRadius, mRadius, mPaint);
       } else {
           super.onDraw(canvas);
       }
    }

}