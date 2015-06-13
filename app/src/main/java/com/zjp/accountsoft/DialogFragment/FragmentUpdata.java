package com.zjp.accountsoft.DialogFragment;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 *
 */
public class FragmentUpdata extends ListFragment {

    public interface OnNewItemClick{
        void OnListItemClick(int position,View v);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        OnNewItemClick onNewItemClick = (OnNewItemClick)getActivity();
        onNewItemClick.OnListItemClick(position,v);
    }
}
