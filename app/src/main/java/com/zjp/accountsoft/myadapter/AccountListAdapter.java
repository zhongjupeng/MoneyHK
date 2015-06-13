package com.zjp.accountsoft.myadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.zjp.accountsoft.activity.R;
import com.zjp.accountsoft.info.AccountListInfo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * 这是一个自定义的用于账单明细的adapter
 */
public class AccountListAdapter extends BaseAdapter{

    class ViewHolder{
        public TextView tvType;
        public TextView tvMoney;
        public TextView tvTime;
        public ImageView imageView;
        public CheckBox delCheckBox;
    }
    private List<AccountListInfo> mList;
    private Context mContext;
    private boolean flage = false;
    public Map<Integer, Boolean> selectedMap;
    public HashSet<Integer> delInaccountIdSet;
    public HashSet<Integer> delOutaccountIdSet;
    public AccountListAdapter(Context context,List<AccountListInfo> list){
        mContext = context;
        mList = list;
        // 保存每条记录是否被选中的状态
        selectedMap = new HashMap<Integer, Boolean>();
        // 保存被选中记录作数据库表中的Id

        delInaccountIdSet = new HashSet<Integer>();
        delOutaccountIdSet = new HashSet<Integer>();

        for (int i = 0; i < list.size(); i++) {
            selectedMap.put(i, false);
        }
    }

    public boolean getFlage() {
        return flage;
    }

    public void setFlage(boolean flage) {
        this.flage = flage;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String sign = mList.get(position).getmSign();
        ViewHolder viewHolder = null;
        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.accountlistview, null);
            viewHolder = new ViewHolder();
            viewHolder.tvType = (TextView) convertView.findViewById(R.id.tvType);
            viewHolder.tvMoney = (TextView) convertView.findViewById(R.id.tvMoney);
            viewHolder.tvTime = (TextView) convertView.findViewById(R.id.tvTime);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.ivSign);
            viewHolder.delCheckBox = (CheckBox)convertView.findViewById(R.id.cbAccount);
            convertView.setTag(viewHolder);
        }else {
            viewHolder =  (ViewHolder)convertView.getTag();
        }
        viewHolder.tvType.setText(mList.get(position).getmType());
        viewHolder.tvTime.setText(mList.get(position).getmTime());
        viewHolder.delCheckBox.setChecked(selectedMap.get(position));
        if(flage){
            viewHolder.delCheckBox.setVisibility(View.VISIBLE);
        }else {
            viewHolder.delCheckBox.setVisibility(View.GONE);
        }
        if(sign.equals("+")){
            viewHolder.imageView.setBackgroundResource(R.drawable.appwidget_income_normal);
            viewHolder.tvMoney.setText(sign + Double.toString(mList.get(position).getmMoney()));
            // 保存记录Id
            if (selectedMap.get(position)) {
                delInaccountIdSet.add(mList.get(position).getId());
            } else {
                delInaccountIdSet.remove(mList.get(position).getId());
            }
        }else if(sign.equals("-")){
            viewHolder.imageView.setBackgroundResource(R.drawable.appwidget_payout_normal);
            viewHolder.tvMoney.setText(sign + Double.toString(mList.get(position).getmMoney()));
            // 保存记录Id
            if (selectedMap.get(position)) {
                delOutaccountIdSet.add(mList.get(position).getId());
            } else {
                delOutaccountIdSet.remove(mList.get(position).getId());
            }
        }
        return convertView;
    }
}
