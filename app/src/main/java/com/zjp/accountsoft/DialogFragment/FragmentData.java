package com.zjp.accountsoft.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import com.zjp.accountsoft.activity.AddInaccount;
import com.zjp.accountsoft.activity.AddOutaccount;
import com.zjp.accountsoft.activity.InAccountInfoUpdata;
import com.zjp.accountsoft.activity.OutAccountInfoUpdata;

import java.util.Calendar;

/**
 * 这是一个设置日期的Fragment
 */
public class FragmentData extends DialogFragment{
    private int mYear;
    private int mMonth;
    private int mDay;

     public static FragmentData getInstance(int title){
        FragmentData frag = new FragmentData();
        Bundle mBundle = new Bundle();
        mBundle.putInt("title",title);
        frag.setArguments(mBundle);
        return frag;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Calendar c = Calendar.getInstance();// 获取当前系统日期
        mYear = c.get(Calendar.YEAR);// 获取年份
        mMonth = c.get(Calendar.MONTH);// 获取月份
        mDay = c.get(Calendar.DAY_OF_MONTH);// 获取天数
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new DatePickerDialog(getActivity(),mDataSetListenner,mYear,mMonth,mDay);
    }

    private DatePickerDialog.OnDateSetListener mDataSetListenner = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
              mYear = year;
              mMonth = monthOfYear;
              mDay = dayOfMonth;
            int title = getArguments().getInt("title");
            switch (title){
                case 1:
                    ((AddInaccount) getActivity()).Updatadisplay(mYear, mMonth, mDay);
                    break;
                case 2:
                    ((AddOutaccount) getActivity()).Updatadisplay(mYear, mMonth, mDay);
                    break;
                case 3:
                    ((InAccountInfoUpdata) getActivity()).InUpdatadisplay(mYear, mMonth, mDay);
                    break;

                case 4:
                    ((OutAccountInfoUpdata) getActivity()).OutUpdatadisplay(mYear, mMonth, mDay);
                    break;
            }

        }
    };

    public int getmYear() {
        return mYear;
    }

    public int getmMonth() {
        return mMonth;
    }

    public int getmDay() {
        return mDay;
    }
}
