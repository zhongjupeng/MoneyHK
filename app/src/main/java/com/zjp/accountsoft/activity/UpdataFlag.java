package com.zjp.accountsoft.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.zjp.accountsoft.dao.FlagDAO;
import com.zjp.accountsoft.model.Tb_flag;

/**
 * 该类用于显示便签内容以及修改便签内容
 */
public class UpdataFlag extends Activity{

    private TextView tvFlagTime;
    private ImageButton imbSave,imbCancel,imNewFlagBack;
    private EditText etFlag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newflag);
        tvFlagTime = (TextView)findViewById(R.id.tvFlagTime);
        imbSave = (ImageButton)findViewById(R.id.imbSave);
        imbCancel = (ImageButton)findViewById(R.id.imbCancel);
        imNewFlagBack = (ImageButton)findViewById(R.id.imNewFlagBack);
        etFlag = (EditText)findViewById(R.id.etFalg);

        tvFlagTime.setText(getIntent().getExtras().get("time").toString());
        etFlag.setText(getIntent().getExtras().get("flag").toString());

        imNewFlagBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdataFlag.this.finish();
            }
        });
        imbSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tb_flag tb_flag = new Tb_flag(Integer.parseInt(getIntent().getExtras().get("id").toString()),
                        etFlag.getText().toString(),tvFlagTime.getText().toString());
                FlagDAO flagDAO = new FlagDAO(UpdataFlag.this);
                flagDAO.update(tb_flag);
                finish();

            }
        });

        imbCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
