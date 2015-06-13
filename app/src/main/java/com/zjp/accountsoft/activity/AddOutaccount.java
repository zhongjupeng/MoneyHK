package com.zjp.accountsoft.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.zjp.accountsoft.DialogFragment.FragmentData;
import com.zjp.accountsoft.DialogFragment.FragmentType;
import com.zjp.accountsoft.FileIO.TypeFile;
import com.zjp.accountsoft.dao.InaccountDAO;
import com.zjp.accountsoft.dao.OutaccountDAO;
import com.zjp.accountsoft.info.AccountListInfo;
import com.zjp.accountsoft.model.Tb_inaccount;
import com.zjp.accountsoft.model.Tb_outaccount;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * 该类用于实现新增支出功能模块
 */
public class AddOutaccount extends Activity implements FragmentType.CustomTypeListenner{

    private int mYear;
    private int mMonth;
    private int mDay;
    //定义各个控件
    private EditText etOutMoney,etOutTime,etOutAddress,etOutMark;
    private Spinner spOutType;
    private ImageButton btOutSave,btOutReset;
    private ImageButton imOutBack;
    private String mCusType;
    private ArrayList<String> list = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private TypeFile typeFile;
    public static final String OUTFILENAME = "outtype.txt";
    @Override
    public void getType(String type) {
        this.mCusType = type;
        adapter.insert(type,0);
        spOutType.setSelection(0,true);
        typeFile.WriteFile(this,OUTFILENAME,type, Context.MODE_APPEND);
    }

    FragmentData fragmentData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addoutaccount);
        //初始化FragmentData
        fragmentData = FragmentData.getInstance(2);
        //初始化各个控件
        //初始化TypeFile类
        typeFile = new TypeFile();
        etOutMoney = (EditText)findViewById(R.id.txtOutMoney);
        etOutTime = (EditText)findViewById(R.id.txtOutTime);
        etOutAddress = (EditText)findViewById(R.id.txtOutAddress);
        etOutMark = (EditText)findViewById(R.id.txtOutMark);

        spOutType = (Spinner)findViewById(R.id.spOutType);

        btOutSave = (ImageButton)findViewById(R.id.imbOutSave);
        btOutReset = (ImageButton)findViewById(R.id.imbOutReset);
        imOutBack = (ImageButton)findViewById(R.id.imOutBack);


        //为Spinner设定选项值
        SetItem();

        //关闭该Activity
        imOutBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddOutaccount.this.finish();
            }
        });
        //为控件设置监听时间
        etOutTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //显示一个DatePickerDialog,自定义时间
                fragmentData.show(getFragmentManager(),"DataDialog");
            }
        });


        //为Spinner设置一个选项监听事件
        spOutType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
            Toast.makeText(AddOutaccount.this,"nothingSelect",Toast.LENGTH_SHORT).show();

            }
        });


        //保存字段
        btOutSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取金额
                String strOutmoney = etOutMoney.getText().toString();
                if(!strOutmoney.isEmpty()) {
                  //创建outaccountDAO对象
                    OutaccountDAO outaccountDAO = new OutaccountDAO(AddOutaccount.this);
                    //创建Tb_outaccount对象
                    Tb_outaccount tb_outaccount = new Tb_outaccount(outaccountDAO.getMaxId() + 1,
                            Double.parseDouble(strOutmoney), etOutTime.getText().toString(),
                            spOutType.getSelectedItem().toString(), etOutAddress.getText().toString(),
                            etOutMark.getText().toString());
                    outaccountDAO.add(tb_outaccount);  //添加支出信息
//                    AccountList.list.add(new AccountListInfo(tb_outaccount.getType(),tb_outaccount.getMoney(),
//                            tb_outaccount.getTime(),"- "));
                    Toast.makeText(AddOutaccount.this,"添加支出成功",Toast.LENGTH_SHORT).show();
                    finish();

                }else
                {
                    Toast.makeText(AddOutaccount.this,"请输入金额数",Toast.LENGTH_SHORT).show();
                }

            }
        });

        btOutReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //重写字段
                etOutTime.setText("");
                etOutMoney.setText("");
                etOutAddress.setText("");
                etOutMark.setText("");

            }
        });

        final Calendar c = Calendar.getInstance();// 获取当前系统日期
        mYear = c.get(Calendar.YEAR);// 获取年份
        mMonth = c.get(Calendar.MONTH);// 获取月份
        mDay = c.get(Calendar.DAY_OF_MONTH);// 获取天数
        Updatadisplay(mYear,mMonth,mDay);
    }

    //定义一个函数用来为Spinner设置选项值
    private void SetItem(){
        //为Spinner设置选项值
        //获取相应对象
        list = typeFile.ReadFiles(this,OUTFILENAME);
        //把数组导入到ArrayList中
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);
        //设置下拉菜单的风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //绑定适配器
        spOutType.setAdapter(adapter);

    }

    //定义一个函数用来更新显示时间
    public void Updatadisplay(int mYear,int mMonth,int mDay){
        etOutTime.setText(new StringBuilder().append(mYear).append("-")
                .append(mMonth + 1).append("-").append(mDay));
    }

}
