package com.zjp.accountsoft.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zjp.accountsoft.FileIO.TypeFile;
import com.zjp.accountsoft.dao.DBManager;
import com.zjp.accountsoft.dao.InaccountDAO;
import com.zjp.accountsoft.dao.OutaccountDAO;

import java.util.ArrayList;
import java.util.Calendar;

public class AccountMSActivity extends Activity {
    //private DBManager dbManager;
    private int mYear;
    private int mMonth;
    private final Calendar c = Calendar.getInstance();// 获取当前系统日期
    private TextView tvMainMonth,tvMainYear,tvInSum,tvOutSum;
    private TypeFile typeFile;
    GridView gvInfo;
    String[] titles = new String[]{"收入","支出","明细","数据管理","便签","密码设置"};  //建立数据源
    int[] images = new int[]{R.drawable.inaccountinfo,R.drawable.outaccountinfo,R.drawable.showinfo,
    R.drawable.accountmanage,R.drawable.accountflag,R.drawable.sysset};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        //将类别写入进文件
        typeFile = new TypeFile();
        LoadData(typeFile);
        mYear = c.get(Calendar.YEAR);// 获取年份
        mMonth = c.get(Calendar.MONTH);// 获取月份
        tvMainMonth = (TextView)findViewById(R.id.tvMainMonth);
        tvMainYear = (TextView)findViewById(R.id.tvMainYear);
        tvInSum = (TextView)findViewById(R.id.inAccount_sum);
        tvOutSum = (TextView)findViewById(R.id.outAccount_sum);
        //显示当前年份和月份
        UpdataTime();
        ShowMoney();
        gvInfo = (GridView)findViewById(R.id.gvInfo);
        //创建pictureAdapter对象
        PictureAdapter adapter = new PictureAdapter(titles,images,this);
        gvInfo.setAdapter(adapter);
        gvInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                switch (position){
                    case 0:
                        intent = new Intent(AccountMSActivity.this,AddInaccount.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(AccountMSActivity.this,AddOutaccount.class);
                        startActivity(intent);
                        break;

                    case 2:
                        intent = new Intent(AccountMSActivity.this,AccountList.class);
                        startActivity(intent);
                        break;

                    case 3:
                        intent = new Intent(AccountMSActivity.this,InfoManage.class);
                        startActivity(intent);
                        break;

                    case 4:
                        intent = new Intent(AccountMSActivity.this,Accountflag.class);
                        startActivity(intent);
                        break;
                    case 5:
                        intent = new Intent(AccountMSActivity.this,Setting.class);
                        startActivity(intent);
                        break;

                }
            }
        });
    }



//定义一个ViewHolder类，用来定义文本组件及图片组件对象
    class ViewHolder{
    public TextView title;
    public ImageView image;
}


//定义一个Picture类，用来定义功能图标及说明文字的实体
    class Picture{
    private String title;
    private int imageId;
    public Picture(){
        super();
    }
    public Picture(String title,int imageId){
        super();
        this.title = title;
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
    //自定义一个pictureAdater类，该类继承字BaseAdapter类
    class PictureAdapter extends BaseAdapter{
        private LayoutInflater inflater;
        private ArrayList<Picture> pictures;
        //创建一个构造函数
        public PictureAdapter(String titles[],int images[],Context context){
            inflater = LayoutInflater.from(context);
            pictures = new ArrayList<Picture>();
            for(int i=0; i<images.length ; i++){
                Picture picture = new Picture(titles[i],images[i]);
                pictures.add(picture);
            }
        }
        @Override
        public int getCount() {  //获取泛型集合长度
           if(pictures!=null){
               return pictures.size();
           }
            return 0;
        }

        @Override
        public Object getItem(int position) { //获取泛型集合索引项

            return pictures.get(position);
        }

        @Override
        public long getItemId(int position) {  //获取泛型集合索引
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {   //返回图像标识
            ViewHolder viewHolder;
            if(convertView == null){
                convertView = inflater.inflate(R.layout.gvitem_layout,null);
                viewHolder = new ViewHolder();
                viewHolder.title = (TextView)convertView.findViewById(R.id.ItemTitle);
                viewHolder.image = (ImageView)convertView.findViewById(R.id.ItemImage);
                convertView.setTag(viewHolder);
            }else {
              viewHolder =  (ViewHolder)convertView.getTag();
            }
            viewHolder.title.setText(pictures.get(position).getTitle());
            viewHolder.image.setImageResource(pictures.get(position).getImageId());

            return convertView;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ShowMoney();
    }

    //在主界面destory后关闭数据库
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //dbManager.closeDatabase();
    }

    //设置当前年份和月份
    private void UpdataTime(){
        tvMainMonth.setText((mMonth+1)+"");
        tvMainYear.setText("/ "+mYear);

    }

    //显示该月份总收入值和总支出值
    private void ShowMoney(){
        int year = c.get(Calendar.YEAR);// 获取年份
        int month = c.get(Calendar.MONTH);// 获取月份
        String str = year + "-" + (month+1) + "%";
        InaccountDAO inaccountDAO = new InaccountDAO(AccountMSActivity.this);
        OutaccountDAO outaccountDAO = new OutaccountDAO(AccountMSActivity.this);
        double inSum = inaccountDAO.inaccount_sum(str);
        double outSum = outaccountDAO.outaccount_sum(str);
        tvInSum.setText("￥"+inSum);
        tvOutSum.setText("￥"+outSum);

    }
    //定义一个函数在程序第一次运行时,加载文件中的数据
    private void LoadData(TypeFile typeFile) {

        SharedPreferences sharedPreferences = this.getSharedPreferences("share", MODE_PRIVATE);
        boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (isFirstRun)
        {
            Log.d("zjp", "第一次运行");
            String[] strIn=getResources().getStringArray(R.array.intype);
            String[] strOut=getResources().getStringArray(R.array.outtype);
            typeFile.WriteFiles(this,AddInaccount.INFILENAME,strIn,Context.MODE_APPEND);
            typeFile.WriteFiles(this,AddOutaccount.OUTFILENAME,strOut,Context.MODE_APPEND);
            editor.putBoolean("isFirstRun", false);
            editor.commit();
        } else
        {
            Log.d("zjp", "不是第一次运行");
        }
    }
}
