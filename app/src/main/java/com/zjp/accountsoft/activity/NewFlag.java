package com.zjp.accountsoft.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.zjp.accountsoft.dao.FlagDAO;
import com.zjp.accountsoft.model.Tb_flag;

import java.util.Calendar;

/**
 * 这是一个创建便签的一个Activity
 */
public class NewFlag extends Activity{

    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;

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

        final Calendar c = Calendar.getInstance();// 获取当前系统日期
        mYear = c.get(Calendar.YEAR);// 获取年份
        mMonth = c.get(Calendar.MONTH);// 获取月份
        mDay = c.get(Calendar.DAY_OF_MONTH);// 获取天数
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        String strTime = mYear + "年" + (mMonth + 1) + "月" + mDay + "日 " + mHour + ":" + mMinute;
        tvFlagTime.setText(strTime);

        //关闭该Activity
        imNewFlagBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewFlag.this.finish();
            }
        });
        imbSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = etFlag.getText().toString();
                if(!str.isEmpty()){
                    FlagDAO flagDAO = new FlagDAO(NewFlag.this);
                    Tb_flag tb_flag = new Tb_flag(flagDAO.getMaxId()+1,etFlag.getText().toString(),
                            tvFlagTime.getText().toString());
                    flagDAO.add(tb_flag);
                    Toast.makeText(NewFlag.this,"便签保存成功",Toast.LENGTH_SHORT).show();
                    finish();
                }else
                {
                    Toast.makeText(NewFlag.this,"请输入内容",Toast.LENGTH_SHORT).show();

                }
            }
        });

        imbCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NewFlag.this,"cancel",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

}
