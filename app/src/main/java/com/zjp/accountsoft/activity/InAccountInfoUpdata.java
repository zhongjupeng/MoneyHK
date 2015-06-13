package com.zjp.accountsoft.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.zjp.accountsoft.DialogFragment.FragmentData;
import com.zjp.accountsoft.DialogFragment.FragmentType;
import com.zjp.accountsoft.FileIO.TypeFile;
import com.zjp.accountsoft.dao.InaccountDAO;
import com.zjp.accountsoft.model.Tb_inaccount;

import java.util.ArrayList;

/**
 * 该类用于修改收入信息
 */
public class InAccountInfoUpdata extends AddInaccount{

    private ImageButton btUpdata,btCancel,imInBack;
    private TextView tvInUpdata,tvInCancel;
    private EditText txtInTime,txtInMoney,txtInHandler,txtInMark;
    private Spinner spType;
    private FragmentData fragmentData;
    private  Bundle mBundle;

    private ArrayList<String> list = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private TypeFile typeFile;
    @Override
    public void getType(String type) {
       // super.getType(type);
        adapter.insert(type,0);
        spType.setSelection(0,true);
        typeFile.WriteFile(this,AddInaccount.INFILENAME,type, Context.MODE_APPEND);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addinaccount);
        fragmentData = FragmentData.getInstance(3);
        //初始化TypeFile类
        typeFile = new TypeFile();
        mBundle = getIntent().getExtras();
        txtInMoney = (EditText)findViewById(R.id.txtInMoney);
        txtInTime = (EditText)findViewById(R.id.txtInTime);
        txtInHandler = (EditText)findViewById(R.id.txtInHandler);
        txtInMark = (EditText)findViewById(R.id.txtInMark);
        spType = (Spinner)findViewById(R.id.spInType);
        btUpdata = (ImageButton)findViewById(R.id.imbInSave);
        btCancel = (ImageButton)findViewById(R.id.imbInReset);
        imInBack = (ImageButton)findViewById(R.id.imInBack);
        tvInUpdata = (TextView)findViewById(R.id.tvInSave);
        tvInCancel = (TextView)findViewById(R.id.tvInReset);
        btUpdata.setBackgroundResource(R.drawable.updata);
        btCancel.setBackgroundResource(R.drawable.close);
        tvInUpdata.setText("修 改");
        tvInCancel.setText("取 消");
        txtInMoney.setText(mBundle.get("money").toString());
        txtInTime.setText(mBundle.get("time").toString());
        txtInHandler.setText(mBundle.get("handler").toString());
        txtInMark.setText(mBundle.get("mark").toString());
        SetItemUpdata();
        //关闭该Activity
        imInBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InAccountInfoUpdata.this.finish();
            }
        });
        spType.setSelection(adapter.getPosition(mBundle.get("type").toString()));
        //为控件设置监听时间
        txtInTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //显示一个DatePickerDialog,自定义时间
                fragmentData.show(getFragmentManager(),"DataDialog");
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
                    fragmentType.show(getFragmentManager(),"自定义收入类型");

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(InAccountInfoUpdata.this,"nothingSelect",Toast.LENGTH_SHORT).show();

            }
        });

        btUpdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InaccountDAO inaccountDAO = new InaccountDAO(InAccountInfoUpdata.this);
                Tb_inaccount tb_inaccount = new Tb_inaccount();
                tb_inaccount.setid(mBundle.getInt("id"));
                tb_inaccount.setTime(txtInTime.getText().toString());
                tb_inaccount.setMoney(Double.parseDouble(txtInMoney.getText().toString()));
                tb_inaccount.setType(spType.getSelectedItem().toString());
                tb_inaccount.setHandler(txtInHandler.getText().toString());
                tb_inaccount.setMark(txtInMark.getText().toString());
                inaccountDAO.updata(tb_inaccount);
                Toast.makeText(InAccountInfoUpdata.this,"修改成功",Toast.LENGTH_SHORT).show();
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
        list = typeFile.ReadFiles(this,AddInaccount.INFILENAME);
        //把数组导入到ArrayList中
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);
        //设置下拉菜单的风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //绑定适配器
        spType.setAdapter(adapter);
    }
    //定义一个函数用来更新显示时间
    public void InUpdatadisplay(int mYear,int mMonth,int mDay){
        txtInTime.setText(new StringBuilder().append(mYear).append("-")
                .append(mMonth + 1).append("-").append(mDay));
    }
}
