package com.zjp.accountsoft.activity;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
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
import com.zjp.accountsoft.info.AccountListInfo;
import com.zjp.accountsoft.model.Tb_inaccount;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * 该类用于实现新增收入功能模块
 */
public class AddInaccount extends Activity implements FragmentType.CustomTypeListenner{
    private int mYear;
    private int mMonth;
    private int mDay;
    //定义各个控件
    private EditText etInMoney,etInTime,etInHandler,etInMark;
    private Spinner spInType;
    private ImageButton btInSave,btInReset;

    private String mCusType;
    private ImageButton imInBack;
    private ArrayList<String> list = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    public static final String INFILENAME = "intype.txt";
    private TypeFile typeFile;
    @Override
    public void getType(String type) {
        this.mCusType = type;
        adapter.insert(type,0);
        spInType.setSelection(0,true);
        typeFile.WriteFile(this,INFILENAME,type, Context.MODE_APPEND);
    }

    FragmentData fragmentData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addinaccount);
        //初始化FragmentData
        fragmentData = FragmentData.getInstance(1);
        //初始化TypeFile类
        typeFile = new TypeFile();
        //初始化各个控件
        etInMoney = (EditText)findViewById(R.id.txtInMoney);
        etInTime = (EditText)findViewById(R.id.txtInTime);
        etInHandler = (EditText)findViewById(R.id.txtInHandler);
        etInMark = (EditText)findViewById(R.id.txtInMark);

        spInType = (Spinner)findViewById(R.id.spInType);

        btInSave = (ImageButton)findViewById(R.id.imbInSave);
        btInReset = (ImageButton)findViewById(R.id.imbInReset);
        imInBack = (ImageButton)findViewById(R.id.imInBack);
        //为Spinner设定选项值
        SetItem();

        //关闭该Activity
        imInBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddInaccount.this.finish();
            }
        });
        //为控件设置监听时间
        etInTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //显示一个DatePickerDialog,自定义时间
                fragmentData.show(getFragmentManager(),"DataDialog");
            }
        });

        //为Spinner设置一个选项监听事件
        spInType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //当选中的下拉菜单为自定义时弹出一个对话框自定义一个类别
                String mCustom = parent.getItemAtPosition(position).toString();
                if (mCustom.equals("自定义")) {
                    FragmentType fragmentType = new FragmentType();
                    fragmentType.show(getFragmentManager(), "自定义收入类型");


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            Toast.makeText(AddInaccount.this,"nothingSelect",Toast.LENGTH_SHORT).show();
            }
        });

        //保存字段
        btInSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取金额
                String strInmoney = etInMoney.getText().toString();
                if(!strInmoney.isEmpty()) {
                    //创建InaccountDAO对象
                    InaccountDAO inaccountDAO = new InaccountDAO(AddInaccount.this);
                    //创建Tb_inaccount对象
                    Tb_inaccount tb_inaccount = new Tb_inaccount(inaccountDAO.getMaxId() + 1,
                            Double.parseDouble(strInmoney), etInTime.getText().toString(),
                            spInType.getSelectedItem().toString(), etInHandler.getText().toString(),
                            etInMark.getText().toString());
                    inaccountDAO.add(tb_inaccount);  //添加收入信息
                    Toast.makeText(AddInaccount.this,"添加收入成功",Toast.LENGTH_SHORT).show();
                    finish();

                }else
                {
                    Toast.makeText(AddInaccount.this,"请输入金额数",Toast.LENGTH_SHORT).show();
                }

            }
        });

        btInReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //重写字段
                etInMoney.setText("");
                etInTime.setText("");
                etInHandler.setText("");
                etInMark.setText("");

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
        list = typeFile.ReadFiles(this,INFILENAME);
        //把数组导入到ArrayList中
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);
        //设置下拉菜单的风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //绑定适配器
        spInType.setAdapter(adapter);
    }

    //定义一个函数用来更新显示时间
    public void Updatadisplay(int mYear,int mMonth,int mDay){
        etInTime.setText(new StringBuilder().append(mYear).append("-")
                .append(mMonth + 1).append("-").append(mDay));
    }

}
