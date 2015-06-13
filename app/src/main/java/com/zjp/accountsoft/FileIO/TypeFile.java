package com.zjp.accountsoft.FileIO;

import android.content.Context;
import android.util.Log;

import com.zjp.accountsoft.activity.AddInaccount;
import com.zjp.accountsoft.activity.AddOutaccount;
import com.zjp.accountsoft.activity.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * 该类是一个用于类型文件存储数据的工具类
 */
public class TypeFile {

    //将自定义的类型写入type.txt中
    public void WriteFile(Context c, String fileName, String content, int mode){
        //打开文件获取输出流，不存在则自动创建
        try{
            FileOutputStream fos = c.openFileOutput(fileName,mode);
            OutputStreamWriter osw = new OutputStreamWriter(fos,"UTF-8");
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write(content + "\r\n");
            bw.close();
            osw.close();
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void WriteFiles(Context c, String fileName, String[] content, int mode){
        //打开文件获取输出流，不存在则自动创建
        try{
            FileOutputStream fos = c.openFileOutput(fileName,mode);
            OutputStreamWriter osw = new OutputStreamWriter(fos,"UTF-8");
            BufferedWriter bw = new BufferedWriter(osw);
            for(int i=0 ; i<content.length ; i++){
                bw.write(content[i] + "\r\n");
            }
            bw.close();
            osw.close();
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    //将类型读到ArrayList<String>中
    public ArrayList<String> ReadFiles(Context c, String fileName){
        ArrayList<String> list = new ArrayList<String>();
        String content = null;
        try {
            FileInputStream fis = c.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            while((content = br.readLine())!=null){
                list.add(content);
            }
            br.close();
            isr.close();
            fis.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return list;

    }
}
