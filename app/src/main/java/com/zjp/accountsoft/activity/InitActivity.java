package com.zjp.accountsoft.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.zjp.accountsoft.dao.PwdDao;


public class InitActivity extends Activity{
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.init_layout);
        final PwdDao pwdDAO = new PwdDao(InitActivity.this);// 创建PwdDAO对象
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(pwdDAO.getCount()==0 || pwdDAO.find().getPassword().isEmpty()){
                    intent = new Intent(InitActivity.this,AccountMSActivity.class);
                    startActivity(intent);
                }else{
                    intent = new Intent(InitActivity.this,Login.class);
                    startActivity(intent);
                }
                finish();
            }
        }, 2000);

    }
}
