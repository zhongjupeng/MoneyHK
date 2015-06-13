package com.zjp.accountsoft.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zjp.accountsoft.model.Tb_pwd;

/**
 * 该类主要用来对密码信息进行管理，包括密码信息的添加、修改、查询及
 * 获取总记录数等功能
 */
public class PwdDao {

    private DBManager dbManager;
    private SQLiteDatabase db;
    public PwdDao(Context context)// 定义构造函数
    {
        dbManager = new DBManager(context);
        dbManager.openDatabase();
    }

    /**
     * 添加密码信息
     *
     * @param tb_pwd
     */
    public void add(Tb_pwd tb_pwd) {
        db = dbManager.getDatabase();// 初始化SQLiteDatabase对象
        // 执行添加密码操作
        db.execSQL("insert into tb_pwd (password) values (?)",
                new Object[] { tb_pwd.getPassword() });
    }

    /**
     * 设置密码信息
     *
     * @param tb_pwd
     */
    public void update(Tb_pwd tb_pwd) {
        db = dbManager.getDatabase();// 初始化SQLiteDatabase对象
        // 执行修改密码操作
        db.execSQL("update tb_pwd set password = ?",
                new Object[] { tb_pwd.getPassword() });
    }

    /**
     * 查找密码信息
     *
     * @return
     */
    public Tb_pwd find() {
        db = dbManager.getDatabase();// 初始化SQLiteDatabase对象
        // 查找密码并存储到Cursor类中
        Cursor cursor = db.rawQuery("select password from tb_pwd", null);
        if (cursor.moveToNext())// 遍历查找到的密码信息
        {
            // 将密码存储到Tb_pwd类中
            return new Tb_pwd(cursor.getString(cursor
                    .getColumnIndex("password")));
        }
        return null;// 如果没有信息，则返回null
    }

    public long getCount() {
        db = dbManager.getDatabase();// 初始化SQLiteDatabase对象
        Cursor cursor = db.rawQuery("select count(password) from tb_pwd", null);// 获取密码信息的记录数
        if (cursor.moveToNext())// 判断Cursor中是否有数据
        {
            return cursor.getLong(0);// 返回总记录数
        }
        return 0;// 如果没有数据，则返回0
    }
}
