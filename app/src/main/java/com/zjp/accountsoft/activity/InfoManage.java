package com.zjp.accountsoft.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.zjp.accountsoft.DialogFragment.FragmentUpdata;
import com.zjp.accountsoft.dao.FlagDAO;
import com.zjp.accountsoft.dao.InaccountDAO;
import com.zjp.accountsoft.dao.OutaccountDAO;
import com.zjp.accountsoft.info.AccountListInfo;
import com.zjp.accountsoft.model.Tb_inaccount;
import com.zjp.accountsoft.model.Tb_outaccount;
import com.zjp.accountsoft.myadapter.AccountListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 该类用于实现数据管理模块的功能
 */
public class InfoManage extends Activity implements FragmentUpdata.OnNewItemClick,View.OnClickListener{

    private List<AccountListInfo> list ;
    private FragmentUpdata fragmentUpdata;
    AccountListAdapter adapter;
    private ImageButton imbDelete;
    private CheckBox cbAccount;
    private Button btAccountCancel,btAccountDelete;
    private RelativeLayout layout1;
    private ImageButton imInfomanageBack;
   // private RelativeLayout layout2;
    private LinearLayout layout3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infomanage_layout);
        imbDelete = (ImageButton)findViewById(R.id.imbAccountDelete);
        btAccountDelete = (Button)findViewById(R.id.rbtAccountDelete);
        btAccountCancel = (Button)findViewById(R.id.rbtAccountCancel);
        imInfomanageBack = (ImageButton)findViewById(R.id.imInfomanageBack);
        cbAccount = (CheckBox)findViewById(R.id.cbAccountAll);
        fragmentUpdata = (FragmentUpdata)getFragmentManager().findFragmentById(R.id.listFragment);
        layout1 = (RelativeLayout)findViewById(R.id.rAccountAll);
  //      layout2 = (RelativeLayout)findViewById(R.id.rAccountDelete);
        layout3 = (LinearLayout)findViewById(R.id.lAccountDelete);
        imbDelete.setOnClickListener(this);
        cbAccount.setOnClickListener(this);
        btAccountCancel.setOnClickListener(this);
        btAccountDelete.setOnClickListener(this);
        imInfomanageBack.setOnClickListener(this);
        showInfo();

    }

    @Override
    public void OnListItemClick(int position,View view) {
        Intent intent = null;
        String strSign = list.get(position).getmSign();
        if (strSign.equals("+") && !layout1.isShown()) {
            intent = new Intent(InfoManage.this,InAccountInfoUpdata.class);
            intent.putExtra("id",list.get(position).getId());
            intent.putExtra("type",list.get(position).getmType());
            intent.putExtra("money",list.get(position).getmMoney());
            intent.putExtra("time",list.get(position).getmTime());
            intent.putExtra("handler",list.get(position).getmHandler());
            intent.putExtra("mark",list.get(position).getmMark());
            startActivity(intent);
        }else if(strSign.equals("-") && !layout1.isShown()){
            intent = new Intent(InfoManage.this,OutAccountInfoUpdata.class);
            intent.putExtra("id",list.get(position).getId());
            intent.putExtra("type",list.get(position).getmType());
            intent.putExtra("money",list.get(position).getmMoney());
            intent.putExtra("time",list.get(position).getmTime());
            intent.putExtra("address",list.get(position).getmAddress());
            intent.putExtra("mark",list.get(position).getmMark());
            startActivity(intent);
        }else if(layout1.isShown()){
            CheckBox cb = (CheckBox)view.findViewById(R.id.cbAccount);
            cb.toggle();
            adapter.selectedMap.put(position, cb
                    .isChecked());
            adapter.notifyDataSetChanged();
            // 判断是否有记录没被选中，以便修改全选CheckBox勾选状态
            if (adapter.selectedMap.containsValue(false)) {
                cbAccount.setChecked(false);
            } else {
                cbAccount.setChecked(true);
            }
            // 判断是否有记录被选中，以便设置删除按钮是否可用
            if (adapter.selectedMap.containsValue(true)) {
                btAccountDelete.setEnabled(true);
                btAccountDelete.setTextColor(this.getResources().getColor(R.color.redDel));
            } else {
                btAccountDelete.setEnabled(false);
                btAccountDelete.setTextColor(this.getResources().getColor(R.color.grayDel));
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imbAccountDelete:
               // layout2.setVisibility(View.GONE);
                if(adapter.getCount()==0){
                    Toast.makeText(InfoManage.this,"没有要删除的项",Toast.LENGTH_SHORT).show();
                }else {
                    layout1.setVisibility(View.VISIBLE);
                    layout3.setVisibility(View.VISIBLE);
                    adapter.setFlage(true);
                    btAccountDelete.setEnabled(false);
                    btAccountDelete.setTextColor(this.getResources().getColor(R.color.grayDel));
                }
                    break;
            case R.id.cbAccountAll:
                if(cbAccount.isChecked()){
                    for(int i=0;i<adapter.getCount();i++){
                        adapter.selectedMap.put(i,true);
                        adapter.delInaccountIdSet.add(list.get(i).getId());
                        adapter.delOutaccountIdSet.add(list.get(i).getId());
                    }
                    btAccountDelete.setEnabled(true);
                    btAccountDelete.setTextColor(this.getResources().getColor(R.color.redDel));

                }else {
                    for (int i = 0; i < adapter.getCount(); i++) {
                        adapter.selectedMap.put(i, false);
                    }
                    //click事件：全选checkbox被取消勾选则把delContactsIdSet清空
                    adapter.delInaccountIdSet.clear();
                    adapter.delOutaccountIdSet.clear();
                    btAccountDelete.setEnabled(false);
                    btAccountDelete.setTextColor(this.getResources().getColor(R.color.grayDel));
                }
                adapter.notifyDataSetChanged();
                break;
            case R.id.rbtAccountDelete:
                new AlertDialog.Builder(this).setTitle(
                        R.string.clearConfirmation_title).setMessage(
                        R.string.clearConfirmation).setNegativeButton(
                        android.R.string.cancel, null).setPositiveButton(
                        android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final ProgressDialog progressDialog = ProgressDialog
                                        .show(InfoManage.this, getString(R.string.clearProgress_title),
                                                "", true, false);
                                final AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
                                    @Override
                                    protected Void doInBackground(Void... params) {
                                        // 删除记录事件处理
                                        InaccountDAO inaccountDAO = new InaccountDAO(InfoManage.this);
                                        OutaccountDAO outaccountDAO = new OutaccountDAO(InfoManage.this);
                                        inaccountDAO.detele(adapter.delInaccountIdSet);
                                        outaccountDAO.detele(adapter.delOutaccountIdSet);
                                        return null;
                                    }

                                    @Override
                                    protected void onPostExecute(Void result) {
                                        progressDialog.dismiss();
                                        showInfo();
                                        cbAccount.setChecked(false);
                                        layout1.setVisibility(View.GONE);
                                        layout3.setVisibility(View.GONE);
                                       // layout2.setVisibility(View.VISIBLE);
                                    }
                                };
                                progressDialog.show();
                                task.execute();
                            }
                        }).setCancelable(true).create().show();
                break;
            case R.id.rbtAccountCancel:
                layout1.setVisibility(View.GONE);
                layout3.setVisibility(View.GONE);
              //  layout2.setVisibility(View.VISIBLE);
                adapter.setFlage(false);
                break;
            case R.id.imInfomanageBack:
                InfoManage.this.finish();
        }
    }

    private void showInfo(){
        //建立数据源
        //创建adapter，并于数据源绑定
        //adapter与界面绑定
        List<AccountListInfo> list = new ArrayList<AccountListInfo>();
        InaccountDAO inaccountDAO = new InaccountDAO(this);
        OutaccountDAO outaccountDAO = new OutaccountDAO(this);
        List<Tb_inaccount> inlistinfos = inaccountDAO.getScrollData(0,(int)inaccountDAO.getCount());
        List<Tb_outaccount> outlistinfos = outaccountDAO.getScrollData(0,(int)outaccountDAO.getCount());
        for (Tb_inaccount tb_inaccount : inlistinfos){
            list.add(new AccountListInfo(tb_inaccount.getid(),tb_inaccount.getType(),tb_inaccount.getMoney(),
                    tb_inaccount.getTime(),"+",tb_inaccount.getMark(),tb_inaccount.getHandler(),null));

        }
        for (Tb_outaccount tb_outaccount : outlistinfos){
            list.add(new AccountListInfo(tb_outaccount.getid(),tb_outaccount.getType(),tb_outaccount.getMoney(),
                    tb_outaccount.getTime(),"-",tb_outaccount.getMark(),null,tb_outaccount.getAddress()));
        }
        adapter = new AccountListAdapter(this,list);
        fragmentUpdata.setListAdapter(adapter);
        this.list = list;
    }

    @Override
    protected void onResume() {
        super.onResume();
        showInfo();
    }
}
