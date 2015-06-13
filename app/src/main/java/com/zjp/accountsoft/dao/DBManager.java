package com.zjp.accountsoft.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.zjp.accountsoft.activity.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 该类主要是将android平台外部新建SQLite数据库并将数据库加入到android程序中操作
 */
public class DBManager {

    private final int BUFFER_SIZE = 400000;
    public static final String DB_NAME = "account.db";
    public static final String PACKAGE_NAME = "com.zjp.accountsoft.activity";
    public static final String DB_PATH = "/data"
            + Environment.getDataDirectory().getAbsolutePath() + "/"
            + PACKAGE_NAME;
    private SQLiteDatabase database;
    private Context context;

    public DBManager(Context context) {
        this.context = context;
    }


    //获得SQLiteDatabase对象
    public SQLiteDatabase getDatabase(){
        return database;
    }

    //有SQLiteDatabase返回值的openDatabase方法
    private SQLiteDatabase openDatabase(String dbfile) {
        try {
            //判断数据库文件是否存在，若不存在则执行导入，否则直接打开数据库
            if (!(new File(dbfile).exists())) {
                InputStream is = this.context.getResources().openRawResource(R.raw.account);
                FileOutputStream fos = new FileOutputStream(dbfile);
                byte[] buffer = new byte[BUFFER_SIZE];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);

                }
                fos.close();
                is.close();
            }
            SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbfile, null);
            return db;
        } catch (FileNotFoundException e) {
            Log.e("zjp", "File not found");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("zjp", "IO exception");
            e.printStackTrace();
        }
        return null;
    }

    //无SQLiteDatabase返回值的openDatabase方法
    public void openDatabase(){
        this.database = this.openDatabase(DB_PATH + "/" + DB_NAME);
    }



    //关闭数据库
    public void closeDatabase(){
        this.database.close();
    }
}



