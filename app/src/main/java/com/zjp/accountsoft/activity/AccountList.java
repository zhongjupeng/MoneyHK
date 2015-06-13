package com.zjp.accountsoft.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.zjp.accountsoft.dao.InaccountDAO;
import com.zjp.accountsoft.dao.OutaccountDAO;
import com.zjp.accountsoft.info.AccountListInfo;
import com.zjp.accountsoft.model.Tb_inaccount;
import com.zjp.accountsoft.model.Tb_outaccount;
import com.zjp.accountsoft.myadapter.AccountListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 该类主要用于实现账单明细的功能
 */
public class AccountList extends Activity{

    private List<AccountListInfo> list ;
    private ListView mAccountList;
    private ImageButton imAccountListBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accountlist);
        mAccountList = (ListView)findViewById(R.id.lvAccountList);
        imAccountListBack = (ImageButton)findViewById(R.id.imAccountListBack);
        showInfo();
        imAccountListBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountList.this.finish();
            }
        });
        mAccountList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                String strSign = list.get(position).getmSign();
                if (strSign.equals("+")) {
                   intent = new Intent(AccountList.this,AccountInfoActivity.class);
                   intent.putExtra("type",list.get(position).getmType());
                   intent.putExtra("money",list.get(position).getmSign() + list.get(position).getmMoney());
                   intent.putExtra("time",list.get(position).getmTime());
                   intent.putExtra("handler",list.get(position).getmHandler());
                   intent.putExtra("mark",list.get(position).getmMark());
                   startActivity(intent);
                }else if(strSign.equals("-")){
                    intent = new Intent(AccountList.this,AccountInfoActivity1.class);
                    intent.putExtra("type",list.get(position).getmType());
                    intent.putExtra("money",list.get(position).getmSign() + list.get(position).getmMoney());
                    intent.putExtra("time",list.get(position).getmTime());
                    intent.putExtra("address",list.get(position).getmAddress());
                    intent.putExtra("mark",list.get(position).getmMark());
                    startActivity(intent);
                }

            }
        });
    }

    private void showInfo(){
        //建立数据源
        //创建adapter，并于数据源绑定
        //adapter与界面绑定
        List<AccountListInfo> list = new ArrayList<AccountListInfo>();
        this.list = list;
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
        AccountListAdapter adapter = new AccountListAdapter(this,list);
        mAccountList.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showInfo();
    }
}
