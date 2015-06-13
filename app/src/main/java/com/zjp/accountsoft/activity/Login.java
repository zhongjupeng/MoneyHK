package com.zjp.accountsoft.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zjp.accountsoft.dao.PwdDao;

public class Login extends ActionBarActivity {

    private Button btnLogin,btnClose;
    private EditText txtLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        txtLogin = (EditText)findViewById(R.id.txtLogin);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnClose = (Button)findViewById(R.id.btnClose);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,AccountMSActivity.class);
                startActivity(intent);
            }
        });
    btnClose.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();                //退出当前程序
        }
    });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, AccountMSActivity.class);// 创建Intent对象
                PwdDao pwdDAO = new PwdDao(Login.this);// 创建PwdDAO对象
                // 判断是否有密码及是否输入了密码
                if ((pwdDAO.getCount() == 0 || pwdDAO.find().getPassword().isEmpty())
                     && txtLogin.getText().toString().isEmpty()) {
                    startActivity(intent);// 启动主Activity
                } else {
                    // 判断输入的密码是否与数据库中的密码一致
                    if (pwdDAO.find().getPassword().equals(txtLogin.getText().toString())) {

                        startActivity(intent);// 启动主Activity

                    } else {
                        // 弹出信息提示
                        Toast.makeText(Login.this, "请输入正确的密码！",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                txtLogin.setText("");// 清空密码文本框
            }

        });
    }


}
