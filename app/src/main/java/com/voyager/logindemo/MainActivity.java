package com.voyager.logindemo;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Main";
    private Button btn_register;
    private Button btn_login;
    private EditText et_main_user;
    private EditText et_main_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        et_main_user = (EditText) findViewById(R.id.et_main_user);
        et_main_pwd = (EditText) findViewById(R.id.et_main_pwd);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                register();
                break;
            case R.id.btn_login:
                break;
        }
    }

    private void register() {
        String userName = et_main_user.getText().toString();
        String userPwd = et_main_pwd.getText().toString();
        if (!Utils.isUserNameQualifiedRule(userName) || !Utils.isUserPwdQualifiedRule(userPwd)) {
            Toast.makeText(this, "用户名或密码不合法", Toast.LENGTH_SHORT).show();
            return;
        }
        RequestParams requestParams = new RequestParams();
        requestParams.add("user", userName);
        requestParams.add("pwd", userPwd);
        Client.post(this, "http://192.168.1.117:8080/LoginProject/servlet/Register", requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                boolean flag = false;
                for (Header header : headers) {
                    Log.i(TAG, "-----" + header.getName() + "=" + header.getValue());
                    if ("result".equals(header.getName())) {
                        if ("1".equals(header.getValue())) {
                            flag = true;
                        }
                    }
                }
                if (flag) {
                    Toast.makeText(MainActivity.this, "注册成功!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "注册失败!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(MainActivity.this, "访问失败!", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
