package com.example.yourdiary.showActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.yourdiary.MainActivity;
import com.example.yourdiary.R;
import com.example.yourdiary.dao.DiaryDao;

public class UpdateActivity extends AppCompatActivity  {
    private Button btReturn;
    private Button btSave;
    private TextView tvRiqi;
    private EditText etContent;

    private DiaryDao diaryDao;

    private String date;

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        diaryDao =new DiaryDao(this);
        //初始化
        init();
        //按钮初始化
        initButton();

    }
    private void init() {
       //获取到intent传递过来的date
        Intent intent=getIntent();
        date=intent.getStringExtra("date");
        //设置日期
        tvRiqi=findViewById(R.id.tv_riqi);
        String year=date.substring(0,4);
        String month=date.substring(4,5);
        String day=date.substring(5);
        tvRiqi.setText(year+"年"+month+"月"+day+"日");

        //显示内容
        etContent=findViewById(R.id.et_content);
        String content=diaryDao.select(date);
        etContent.setText(content);
    }

    private void initButton() {
        //按钮绑定id
        btSave=findViewById(R.id.bt_save);
        //保存按钮的响应事件
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = etContent.getText().toString();

                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(UpdateActivity.this, "日记内容不能为空哦o(*￣▽￣*)o", Toast.LENGTH_SHORT).show();
                }
                //更新日记内容
                else {
                    ContentValues values = new ContentValues();
                    values.put("content",content);
                    if (diaryDao.update(values,date) >0) {
                        Toast.makeText(UpdateActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(UpdateActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btReturn=findViewById(R.id.bt_return);
        //取消按钮的响应事件
        btReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击取消回到主页面
                Intent intent = new Intent(UpdateActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

}