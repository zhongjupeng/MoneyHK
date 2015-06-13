package com.zjp.accountsoft.myadapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.zjp.accountsoft.activity.R;
import com.zjp.accountsoft.info.FlagListInfo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * 这是一个自定义的用于展示便签内容的adapter
 */
public class FlagListAdapter extends BaseAdapter{

    class ViewHolder{
        public TextView tvFlag;
        public ImageView imageView;
        public CheckBox delCheckBox;
    }
    private List<FlagListInfo> mList;
    private Context mContext;
    private boolean flage = false;
    public ViewHolder viewHolder;
    public Map<Integer, Boolean> selectedMap;
    public HashSet<Integer> delFlagsIdSet;
    public FlagListAdapter(Context context,List<FlagListInfo> list){
        this.mContext = context;
        this.mList = list;
        // 保存每条记录是否被选中的状态
        selectedMap = new HashMap<Integer, Boolean>();
        // 保存被选中记录作数据库表中的Id

        delFlagsIdSet = new HashSet<Integer>();

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

        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.flaglist,null);
            viewHolder = new ViewHolder();
            viewHolder.tvFlag = (TextView)convertView.findViewById(R.id.tvFlag);
            viewHolder.delCheckBox = (CheckBox)convertView.findViewById(R.id.cbDelete);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.tvFlag.setText(mList.get(position).getFlag().toString());
        viewHolder.delCheckBox.setChecked(selectedMap.get(position));
        if(flage){
            viewHolder.delCheckBox.setVisibility(View.VISIBLE);
        }else {
            viewHolder.delCheckBox.setVisibility(View.GONE);
        }
        // 保存记录Id
        if (selectedMap.get(position)) {
            delFlagsIdSet.add(mList.get(position).getId());
        } else {
            delFlagsIdSet.remove(mList.get(position).getId());
        }

        return convertView;
    }
}
