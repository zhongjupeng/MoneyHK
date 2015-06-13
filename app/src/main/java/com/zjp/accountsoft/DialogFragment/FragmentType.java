package com.zjp.accountsoft.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.zjp.accountsoft.activity.R;

/**
 * 这是一个为自定义类别的Fragment
 */
public class FragmentType extends DialogFragment{


    public interface CustomTypeListenner{
        void getType(String type);
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragmenttype,null);
        final EditText etCustom = (EditText)view.findViewById(R.id.etCustom);
        builder.setView(view)
               .setTitle(getTag())
               .setPositiveButton("确定",new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                    CustomTypeListenner listenner = (CustomTypeListenner)getActivity();
                    listenner.getType(etCustom.getText().toString());
                   }
               })
               .setNegativeButton("取消",new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {

                   }
               });
        return builder.create();
    }
}
