package com.zjp.accountsoft.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.zjp.accountsoft.dao.FlagDAO;
import com.zjp.accountsoft.info.FlagListInfo;
import com.zjp.accountsoft.model.Tb_flag;
import com.zjp.accountsoft.myadapter.FlagListAdapter;
import java.util.ArrayList;
import java.util.List;

public class Accountflag extends Activity implements View.OnClickListener,AdapterView.OnItemClickListener{

    private ImageButton imbNew,imbDelete,imFlagBack;
    private Button rbtCancel,rbtDelete;
    private CheckBox cbDelete;
    private ListView listView;
    private List<FlagListInfo> list;
    private FlagListAdapter flagListAdapter;
    private RelativeLayout layout1;
    private LinearLayout layout2;
    private LinearLayout layout3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accountflag);
        listView = (ListView)findViewById(R.id.lvFlag);
        imbNew = (ImageButton)findViewById(R.id.imbNew);
        imbDelete = (ImageButton)findViewById(R.id.imbDeleteFlag);
        imFlagBack = (ImageButton)findViewById(R.id.imFlagBack);
        rbtCancel = (Button)findViewById(R.id.rbtCancel);
        rbtDelete = (Button)findViewById(R.id.rbtDelete);
        cbDelete = (CheckBox)findViewById(R.id.cbAll);
        layout1 = (RelativeLayout)findViewById(R.id.rAll);
        layout2 = (LinearLayout)findViewById(R.id.lDelete2);
        layout3 = (LinearLayout)findViewById(R.id.lDelete1);
        imbNew.setOnClickListener(this);
        imbDelete.setOnClickListener(this);
        imFlagBack.setOnClickListener(this);
        rbtCancel.setOnClickListener(this);
        rbtDelete.setOnClickListener(this);
        cbDelete.setOnClickListener(this);
        listView.setOnItemClickListener(this);
        showFlagInfo();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imbNew:
                Intent intent = new Intent(Accountflag.this,NewFlag.class);
                startActivity(intent);
                break;
            case R.id.rbtCancel:
                layout1.setVisibility(View.GONE);
                layout2.setVisibility(View.GONE);
                layout3.setVisibility(View.VISIBLE);
                flagListAdapter.setFlage(false);
                flagListAdapter.notifyDataSetChanged();
                break;
            case R.id.imbDeleteFlag:
                if(flagListAdapter.getCount()==0){
                    Toast.makeText(Accountflag.this, "没有要删除的项", Toast.LENGTH_SHORT).show();
                }else{
                    rbtDelete.setEnabled(false);
                    layout3.setVisibility(View.GONE);
                    layout1.setVisibility(View.VISIBLE);
                    layout2.setVisibility(View.VISIBLE);
                    flagListAdapter.setFlage(true);
                    flagListAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.rbtDelete:
                new AlertDialog.Builder(this).setTitle(
                        R.string.clearConfirmation_title).setMessage(
                        R.string.clearConfirmation).setNegativeButton(
                        android.R.string.cancel, null).setPositiveButton(
                        android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final ProgressDialog progressDialog = ProgressDialog
                                        .show(Accountflag.this, getString(R.string.clearProgress_title),
                                        "", true, false);
                                final AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
                                    @Override
                                    protected Void doInBackground(Void... params) {
                                        // 删除记录事件处理
                                        FlagDAO flagDAO = new FlagDAO(Accountflag.this);
                                        flagDAO.detele(flagListAdapter.delFlagsIdSet);
                                        return null;
                                    }

                                    @Override
                                    protected void onPostExecute(Void result) {
                                        progressDialog.dismiss();
                                        showFlagInfo();
                                        cbDelete.setChecked(false);
                                        layout1.setVisibility(View.GONE);
                                        layout2.setVisibility(View.GONE);
                                        layout3.setVisibility(View.VISIBLE);
                                    }
                                };
                                progressDialog.show();
                                task.execute();
                            }
                        }).setCancelable(true).create().show();
                break;
            case R.id.cbAll:
                if(cbDelete.isChecked()){
                    for(int i=0;i<flagListAdapter.getCount();i++){
                        flagListAdapter.selectedMap.put(i,true);
                        flagListAdapter.delFlagsIdSet.add(list.get(i).getId());
                    }
                    rbtDelete.setEnabled(true);
                    rbtDelete.setTextColor(this.getResources().getColor(R.color.redDel));
                }else {
                    for (int i = 0; i < flagListAdapter.getCount(); i++) {
                        flagListAdapter.selectedMap.put(i, false);
                    }
                    //click事件：全选checkbox被取消勾选则把delContactsIdSet清空
                    flagListAdapter.delFlagsIdSet.clear();
                    rbtDelete.setEnabled(false);
                    rbtDelete.setTextColor(this.getResources().getColor(R.color.grayDel));
                }
                flagListAdapter.notifyDataSetChanged();
                break;
            case R.id.imFlagBack:
                Accountflag.this.finish();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(layout1.isShown()){
            CheckBox cb = (CheckBox)view.findViewById(R.id.cbDelete);
            cb.toggle();
            flagListAdapter.selectedMap.put(position, cb
                    .isChecked());
            flagListAdapter.notifyDataSetChanged();
            // 判断是否有记录没被选中，以便修改全选CheckBox勾选状态
            if (flagListAdapter.selectedMap.containsValue(false)) {
                cbDelete.setChecked(false);
            } else {
                cbDelete.setChecked(true);
            }
            // 判断是否有记录被选中，以便设置删除按钮是否可用
            if (flagListAdapter.selectedMap.containsValue(true)) {
                rbtDelete.setEnabled(true);
                rbtDelete.setTextColor(this.getResources().getColor(R.color.redDel));
            } else {
                rbtDelete.setEnabled(false);
                rbtDelete.setTextColor(this.getResources().getColor(R.color.grayDel));
            }
        }else {
            Intent intent = new Intent(Accountflag.this,UpdataFlag.class);
            intent.putExtra("id",list.get(position).getId());
            intent.putExtra("flag",list.get(position).getFlag().toString());
            intent.putExtra("time",list.get(position).getTime().toString());
            startActivity(intent);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        showFlagInfo();
    }

    private void showFlagInfo(){
        //建立数据源
        List<FlagListInfo> list = new ArrayList<FlagListInfo>();
        this.list = list;
        FlagDAO flagDAO = new FlagDAO(this);
        List<Tb_flag> flagListInfos = flagDAO.getScrollData(0,(int)flagDAO.getCount());
        for(Tb_flag tb_flag: flagListInfos){
            list.add(new FlagListInfo(tb_flag.getid(),tb_flag.getFlag(),tb_flag.getTime()));
        }
        //创建adapter，并绑定数据源
        flagListAdapter = new FlagListAdapter(this,list);
        //adapter与界面绑定
        listView.setAdapter(flagListAdapter);

    }

}
