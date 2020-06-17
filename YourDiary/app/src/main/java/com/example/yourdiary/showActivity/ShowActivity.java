package com.example.yourdiary.showActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.yourdiary.MainActivity;
import com.example.yourdiary.R;
import com.example.yourdiary.dao.DiaryDao;


public class ShowActivity extends AppCompatActivity {
    private TextView tv0;
    private TextView tv1;
    private TextView tv2;

    private Button btQuit;
    private ImageButton ibDelete;
    private ImageButton ibUpdate;

    private DiaryDao diaryDao;

    public static String date;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        diaryDao =new DiaryDao(this);
        //初始化
        init();
        //初始化按钮
        initButton();
    }

    private void init() {

        //获取到intent传递过来的date
        Intent intent=getIntent();
        date=intent.getStringExtra("date");

        //指定年月
        String year=date.substring(0,4);
        String month=date.substring(4,5);
        tv0=findViewById(R.id.tv_0);
        tv0.setText(year+"年"+month+"月");

        //指定日
        String day=date.substring(5);
        tv1= findViewById(R.id.tv_1);
        tv1.setText(day);

        //显示日记内容
        String content=diaryDao.select(date);
        tv2 = findViewById(R.id.tv_2);
        //如果当天有写日记
        if (!TextUtils.isEmpty(content)){
            tv2.setText(content);
        }
    }


    private void initButton() {

        btQuit=findViewById(R.id.bt_quit);
        ibDelete = findViewById(R.id.ib_delete);
        ibUpdate = findViewById(R.id.ib_update);

        //退出按钮的单击响应事件
        btQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(ShowActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        //删除按钮的单击响应事件
        ibDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出选择框
                dialogBox();
            }
        });

        //更新按钮的响应事件
        ibUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent =new Intent(ShowActivity.this, UpdateActivity.class);
              intent.putExtra("date" ,date);
              startActivity(intent);
            }
        });


    }

    //点击删除按钮所显示的对话框
    private void  dialogBox() {
        new AlertDialog.Builder(this)
                .setMessage("确定要删除这篇日记吗？此操作不可逆")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        diaryDao.deleteByDiary(date);
                        finish();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //取消对话框的显示
                        dialog.dismiss();
                    }
                }).create().show();

    }
}
