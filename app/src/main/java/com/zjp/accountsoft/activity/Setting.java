package com.zjp.accountsoft.activity;

import android.app.Activity;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zjp.accountsoft.dao.PwdDao;
import com.zjp.accountsoft.model.Tb_pwd;

/**
 * 这是一个系统设置类
 */
public class Setting extends ActionBarActivity {

    Button btOK,btCancel;
    EditText etPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);
        btOK = (Button)findViewById(R.id.btPwdOk);
        btCancel = (Button)findViewById(R.id.btPwdCancel);
        etPassword = (EditText)findViewById(R.id.etPassword);
        btOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PwdDao pwdDAO = new PwdDao(Setting.this);// 创建PwdDAO对象
                Tb_pwd tb_pwd = new Tb_pwd(etPassword.getText().toString());// 根据输入的密码创建Tb_pwd对象
                if (pwdDAO.getCount() == 0) {// 判断数据库中是否已经设置了密码
                    pwdDAO.add(tb_pwd);// 添加用户密码
                } else {
                    pwdDAO.update(tb_pwd);// 修改用户密码
                }
                etPassword.setText("");
                // 弹出信息提示
                Toast.makeText(Setting.this, "〖密码〗设置成功！", Toast.LENGTH_SHORT)
                        .show();
                finish();
            }

        });
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etPassword.setText("");
                finish();
            }
        });

    }
}
