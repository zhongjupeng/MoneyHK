package com.zjp.accountsoft.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * 这是展示详细信息的一个Activity
 */
public class AccountInfoActivity1 extends Activity{

    private TextView tvType,tvTime,tvMoney,tvAddress,tvMark;
    private ImageButton imOutInfoBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accountinfo1);
        Bundle bundle = getIntent().getExtras();
        tvType = (TextView)findViewById(R.id.tvInfoType1);
        tvTime = (TextView)findViewById(R.id.tvInfoTime11);
        tvMoney = (TextView)findViewById(R.id.tvInfoMoney11);
        tvAddress = (TextView)findViewById(R.id.tvInfoHandler11);
        tvMark = (TextView)findViewById(R.id.tvInfoMark11);
        imOutInfoBack = (ImageButton)findViewById(R.id.imOutInfoBack);
        tvType.setText(bundle.get("type").toString());
        tvTime.setText(bundle.get("time").toString());
        tvMoney.setText(bundle.get("money").toString());
        tvAddress.setText(bundle.get("address").toString());
        tvMark.setText(bundle.get("mark").toString());
        imOutInfoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountInfoActivity1.this.finish();
            }
        });

    }
}
