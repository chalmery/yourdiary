package com.example.yourdiary.loginActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.yourdiary.MainActivity;
import com.example.yourdiary.R;
import com.example.yourdiary.dao.UserDao;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{


    private EditText etUserName1;
    private EditText etPassword1;
    private Button btLogin;
    private Button btRegister1;

    private UserDao userDao;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //初始化
        init();

    }

    private void init() {

        etUserName1 = findViewById(R.id.et_username1);
        etPassword1 = findViewById(R.id.et_password1);
        btLogin = findViewById(R.id.bt_login);
        btRegister1 = findViewById(R.id.bt_register1);

        btLogin.setOnClickListener(this);
        btRegister1.setOnClickListener(this);

        userDao=new UserDao(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_register1:
                startActivity(new Intent(this, RegisterActivity.class));
                finish();
                break;
                //登录按钮的响应事件
            case R.id.bt_login:
                String username = etUserName1.getText().toString().trim();
                String password = etPassword1.getText().toString().trim();

               //如果输入账号密码为空
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    Toast.makeText(this, "您的账号或密码为空", Toast.LENGTH_SHORT).show();
                }
                //账号密码不为空
                else {
                    //先后判断账号密码的正确性
                    if(userDao.selectName(username)) {
                        if(userDao.selectPassword(username,password)) {
                            Toast.makeText(this, "登陆成功", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(this, MainActivity.class));
                            finish();
                        }
                        else{
                            Toast.makeText(this, "请输入正确的密码", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(this, "请输入正确的账号", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }

    }
}
