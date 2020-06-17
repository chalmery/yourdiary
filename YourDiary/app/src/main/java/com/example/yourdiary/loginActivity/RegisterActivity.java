package com.example.yourdiary.loginActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.yourdiary.R;
import com.example.yourdiary.dao.UserDao;

public class RegisterActivity extends AppCompatActivity {

    private EditText etUserName2;
    private EditText etPassword2;
    private Button btRegister2;
    private CheckBox cbLiumang;

    private UserDao userDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        etUserName2 = findViewById(R.id.et_username2);
        etPassword2 = findViewById(R.id.et_password2);
        btRegister2 =findViewById(R.id.bt_register2);
        cbLiumang =findViewById(R.id.cb_liumang);


        userDao =new UserDao(this);

        //注册按钮的单击响应,注册成功跳转到登陆界面
        btRegister2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = etUserName2.getText().toString().trim();
                String password = etPassword2.getText().toString().trim();

                //如果流氓条款签订了
                if(cbLiumang.isChecked()){

                    //如果账号密码未输入
                    if(TextUtils.isEmpty(username)||TextUtils.isEmpty(password)){
                        Toast.makeText(RegisterActivity.this,"请正确输入账号密码",Toast.LENGTH_SHORT).show();
                    }
                    //如果账号密码输入
                    else{
                        //如果已有此用户
                        if(userDao.selectName(username)){
                            Toast.makeText(RegisterActivity.this,"用户已被注册",Toast.LENGTH_SHORT).show();
                        }
                        //如果是新用户
                        else{
                            //将账号密码插入数据库，然后跳转到登录界面
                            ContentValues values =new ContentValues();
                            values.put("username",username);
                            values.put("password",password);
                            if(userDao.insert(values)>0){
                                Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }

                        }
                    }
                }
                else{
                    Toast.makeText(RegisterActivity.this,"您尚未勾选用户协议",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
