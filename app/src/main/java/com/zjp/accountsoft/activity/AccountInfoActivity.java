package com.zjp.accountsoft.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * 这是展示详细信息的一个Activity
 */
public class AccountInfoActivity extends Activity{

    private TextView tvType,tvTime,tvMoney,tvHandler,tvMark;
    private ImageButton imInInfoBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accountinfo);
        Bundle bundle = getIntent().getExtras();
        tvType = (TextView)findViewById(R.id.tvInfoType);
        tvTime = (TextView)findViewById(R.id.tvInfoTime1);
        tvMoney = (TextView)findViewById(R.id.tvInfoMoney1);
        tvHandler = (TextView)findViewById(R.id.tvInfoHandler1);
        tvMark = (TextView)findViewById(R.id.tvInfoMark1);
        imInInfoBack = (ImageButton)findViewById(R.id.imInInfoBack);
        tvType.setText(bundle.get("type").toString());
        tvTime.setText(bundle.get("time").toString());
        tvMoney.setText(bundle.get("money").toString());
        tvHandler.setText(bundle.get("handler").toString());
        tvMark.setText(bundle.get("mark").toString());
        imInInfoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountInfoActivity.this.finish();
            }
        });

    }
}
