package com.zjp.accountsoft.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zjp.accountsoft.model.Tb_flag;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 该类主要用来对便签信息进行管理，包括便签信息的添加、删除、修改、查询及
 * 获取最大编号、总记录数等功能
 */
public class FlagDAO {
    private DBManager dbManager;
    private SQLiteDatabase db;

    public FlagDAO(Context context)// 定义构造函数
    {
        dbManager = new DBManager(context);
        dbManager.openDatabase();
    }

    /**
     * 添加便签信息
     *
     * @param tb_flag
     */
    public void add(Tb_flag tb_flag) {
        db = dbManager.getDatabase();// 初始化SQLiteDatabase对象
        db.execSQL("insert into tb_flag (_id,flag,time) values (?,?,?)", new Object[] {
                tb_flag.getid(), tb_flag.getFlag() ,tb_flag.getTime()});// 执行添加便签信息操作
    }

    /**
     * 更新便签信息
     *
     * @param tb_flag
     */
    public void update(Tb_flag tb_flag) {
        db = dbManager.getDatabase();// 初始化SQLiteDatabase对象

        db.execSQL("update tb_flag set flag = ? where _id = ?", new Object[] {
                tb_flag.getFlag(), tb_flag.getid() });// 执行修改便签信息操作
    }

    /**
     * 查找便签信息
     *
     * @param id
     * @return
     */
    public Tb_flag find(int id) {
        db = dbManager.getDatabase();// 初始化SQLiteDatabase对象

        Cursor cursor = db.rawQuery(
                "select _id,flag ,time from tb_flag where _id = ?",
                new String[] { String.valueOf(id) });// 根据编号查找便签信息，并存储到Cursor类中
        if (cursor.moveToNext())// 遍历查找到的便签信息
        {
            // 将遍历到的便签信息存储到Tb_flag类中
            return new Tb_flag(cursor.getInt(cursor.getColumnIndex("_id")),
                    cursor.getString(cursor.getColumnIndex("flag")),cursor.getString(cursor.getColumnIndex("time")));
        }
        return null;// 如果没有信息，则返回null
    }

    /**
     * 刪除便签信息
     *
     * @param ids
     */
    public void detele(HashSet<Integer> ids) {
        if (ids.size() > 0)// 判断是否存在要删除的id
        {
            StringBuffer sb = new StringBuffer();// 创建StringBuffer对象
            for (int i = 0; i < ids.size(); i++)// 遍历要删除的id集合
            {
                sb.append('?').append(',');// 将删除条件添加到StringBuffer对象中
            }
            sb.deleteCharAt(sb.length() - 1);// 去掉最后一个“,“字符
            db = dbManager.getDatabase();// 初始化SQLiteDatabase对象

            // 执行删除便签信息操作
            db.execSQL("delete from tb_flag where _id in (" + sb + ")",
                    ids.toArray());
        }
    }

    /**
     * 获取便签信息
     *
     * @param start
     *            起始位置
     * @param count
     *            每页显示数量
     * @return
     */
    public List<Tb_flag> getScrollData(int start, int count) {
        List<Tb_flag> lisTb_flags = new ArrayList<Tb_flag>();// 创建集合对象
        db = dbManager.getDatabase();// 初始化SQLiteDatabase对象

        // 获取所有便签信息
        Cursor cursor = db.rawQuery("select * from tb_flag limit ?,?",
                new String[] { String.valueOf(start), String.valueOf(count) });
        while (cursor.moveToNext())// 遍历所有的便签信息
        {
            // 将遍历到的便签信息添加到集合中
            lisTb_flags.add(new Tb_flag(cursor.getInt(cursor
                    .getColumnIndex("_id")), cursor.getString(cursor
                    .getColumnIndex("flag")),cursor.getString(cursor.getColumnIndex("time"))));
        }
        return lisTb_flags;// 返回集合
    }

    /**
     * 获取总记录数
     *
     * @return
     */
    public long getCount() {
        db = dbManager.getDatabase();// 初始化SQLiteDatabase对象

        Cursor cursor = db.rawQuery("select count(_id) from tb_flag", null);// 获取便签信息的记录数
        if (cursor.moveToNext())// 判断Cursor中是否有数据
        {
            return cursor.getLong(0);// 返回总记录数
        }
        return 0;// 如果没有数据，则返回0
    }

    /**
     * 获取便签最大编号
     *
     * @return
     */
    public int getMaxId() {
        db = dbManager.getDatabase();// 初始化SQLiteDatabase对象

        Cursor cursor = db.rawQuery("select max(_id) from tb_flag", null);// 获取便签信息表中的最大编号
        while (cursor.moveToLast()) {// 访问Cursor中的最后一条数据
            return cursor.getInt(0);// 获取访问到的数据，即最大编号
        }
        return 0;// 如果没有数据，则返回0
    }
}
