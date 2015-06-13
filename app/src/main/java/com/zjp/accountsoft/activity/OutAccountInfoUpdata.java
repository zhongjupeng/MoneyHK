package com.zjp.accountsoft.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.zjp.accountsoft.DialogFragment.FragmentData;
import com.zjp.accountsoft.DialogFragment.FragmentType;
import com.zjp.accountsoft.FileIO.TypeFile;
import com.zjp.accountsoft.dao.OutaccountDAO;
import com.zjp.accountsoft.model.Tb_outaccount;

import java.util.ArrayList;

/**
 * 该类用于修改收入信息
 */
public class OutAccountInfoUpdata extends AddOutaccount{

    private ImageButton btUpdata,btCancel,imOutBack;
    private TextView tvOutUpdata,tvOutCancel;
    private EditText txtOutTime,txtOutMoney,txtOutAddress,txtOutMark;
    private Spinner spType;
    private FragmentData fragmentData;
    Bundle mBundle;
    private ArrayList<String> list = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private TypeFile typeFile;
    @Override
    public void getType(String type) {
      // super.getType(type);
        adapter.insert(type,0);
        spType.setSelection(0,true);
        typeFile.WriteFile(this,AddOutaccount.OUTFILENAME,type, Context.MODE_APPEND);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addoutaccount);
        fragmentData = FragmentData.getInstance(4);
        //初始化TypeFile类
        typeFile = new TypeFile();
        mBundle = getIntent().getExtras();
        txtOutMoney = (EditText)findViewById(R.id.txtOutMoney);
        txtOutTime = (EditText)findViewById(R.id.txtOutTime);
        txtOutAddress = (EditText)findViewById(R.id.txtOutAddress);
        txtOutMark = (EditText)findViewById(R.id.txtOutMark);
        spType = (Spinner)findViewById(R.id.spOutType);
        btUpdata = (ImageButton)findViewById(R.id.imbOutSave);
        btCancel = (ImageButton)findViewById(R.id.imbOutReset);
        imOutBack = (ImageButton)findViewById(R.id.imOutBack);
        tvOutUpdata = (TextView)findViewById(R.id.tvOutSave);
        tvOutCancel = (TextView)findViewById(R.id.tvOutReset);
        btUpdata.setBackgroundResource(R.drawable.updata);
        btCancel.setBackgroundResource(R.drawable.close);
        tvOutUpdata.setText("修 改");
        tvOutCancel.setText("取 消");
        txtOutMoney.setText(mBundle.get("money").toString());
        txtOutTime.setText(mBundle.get("time").toString());
        txtOutAddress.setText(mBundle.get("address").toString());
        txtOutMark.setText(mBundle.get("mark").toString());
        SetItemUpdata();
        spType.setSelection(adapter.getPosition(mBundle.get("type").toString()));
        //为控件设置监听时间
        txtOutTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //显示一个DatePickerDialog,自定义时间
                fragmentData.show(getFragmentManager(),"DataDialog");
            }
        });


        //关闭该Activity
        imOutBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OutAccountInfoUpdata.this.finish();
            }
        });
        //为Spinner设置一个选项监听事件
        spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //当选中的下拉菜单为自定义时弹出一个对话框自定义一个类别
                String mCustom = parent.getItemAtPosition(position).toString();
                if(mCustom.equals("自定义")){
                    FragmentType fragmentType = new FragmentType();
                    fragmentType.show(getFragmentManager(),"自定义支出类型");

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            Toast.makeText(OutAccountInfoUpdata.this,"nothingSelect",Toast.LENGTH_SHORT).show();

            }
        });

        btUpdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OutaccountDAO outaccountDAO = new OutaccountDAO(OutAccountInfoUpdata.this);
                Tb_outaccount tb_outaccount = new Tb_outaccount();
                tb_outaccount.setid(mBundle.getInt("id"));
                tb_outaccount.setTime(txtOutTime.getText().toString());
                tb_outaccount.setMoney(Double.parseDouble(txtOutMoney.getText().toString()));
                tb_outaccount.setType(spType.getSelectedItem().toString());
                tb_outaccount.setAddress(txtOutAddress.getText().toString());
                tb_outaccount.setMark(txtOutMark.getText().toString());
                outaccountDAO.updata(tb_outaccount);
                Toast.makeText(OutAccountInfoUpdata.this,"修改成功",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              finish();
            }
        });

    }

    private void SetItemUpdata() {
        //为Spinner设置选项值
        //获取相应对象
        list = typeFile.ReadFiles(this,AddOutaccount.OUTFILENAME);
        //把数组导入到ArrayList中
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);
        //设置下拉菜单的风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //绑定适配器
        spType.setAdapter(adapter);
    }

    //定义一个函数用来更新显示时间
    public void OutUpdatadisplay(int mYear,int mMonth,int mDay){
        txtOutTime.setText(new StringBuilder().append(mYear).append("-")
                .append(mMonth + 1).append("-").append(mDay));
    }
}
