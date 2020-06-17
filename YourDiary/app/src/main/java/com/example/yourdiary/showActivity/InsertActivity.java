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
import com.example.yourdiary.dao.UserDao;

import java.util.Calendar;

public class InsertActivity extends AppCompatActivity  {
    private Button btReturn;
    private Button btSave;
    private TextView tvRiqi;
    private EditText etContent;

    private DiaryDao diaryDao;

    //存储日期
    private static String date;

    //存储查询到的日记，用于保存按钮判断
    private static String diaryContent;

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        diaryDao = new DiaryDao(this);
        //日期初始化
        initDate();
        //日记内容初始化
        initDiary();
        //按钮初始化
        initButton();
    }

    private void initDiary() {
        etContent = findViewById(R.id.et_content);
        String content = diaryDao.select(date);
        diaryContent = content;
        etContent.setText(content);
    }

    private void initButton(){
        btSave = findViewById(R.id.bt_save);
        btReturn = findViewById(R.id.bt_return);
        //保存按钮的响应事件
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = etContent.getText().toString();
                //如果日记内容为空
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(InsertActivity.this, "日记内容不能为空哦o(*￣▽￣*)o", Toast.LENGTH_SHORT).show();
                }
                //不为空
                else {
                    //如果是当天没写过日记,对应插入操作
                    if (diaryContent.equals("")){
                        //将内容插入数据库
                        ContentValues values = new ContentValues();
                        values.put("content",content);
                        values.put("date",date);
                        values.put("name", UserDao.name);
                        if (diaryDao.insert(values) >0) {
                            Toast.makeText(InsertActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(InsertActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                    //如果当天写日记了，对应更新操作
                    else{
                        ContentValues values = new ContentValues();
                        values.put("content",content);
                        if (diaryDao.update(values,date) >0) {
                            Toast.makeText(InsertActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(InsertActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(InsertActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
        //取消按钮的响应事件
        btReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击取消回到主页面
                Intent intent = new Intent(InsertActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initDate() {
        tvRiqi=findViewById(R.id.tv_riqi);
        //获取到当前的年月日
        Calendar calendar = Calendar.getInstance();
        String year =  String.valueOf(calendar.get(Calendar.YEAR));//年
        String month = String.valueOf(calendar.get(Calendar.MONTH)+1);//月
        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));//日
        date =year+month+day;
        //显示当前时间
        tvRiqi.setText(year+"年"+month+"月"+day+"日");
    }


}

