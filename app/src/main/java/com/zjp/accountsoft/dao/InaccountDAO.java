package com.zjp.accountsoft.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zjp.accountsoft.model.Tb_inaccount;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 该类主要用来对收入信息进行管理，包括收入信息的添加、删除、修改、查询及
 * 获取最大编号、总记录数等功能
 *
 */
public class InaccountDAO {

    private DBManager dbManager;
    private SQLiteDatabase db;

    public InaccountDAO(Context context) {

        dbManager = new DBManager(context);
        dbManager.openDatabase();
    }


    //添加收入信息
    public void add(Tb_inaccount tb_inaccount) {
        db = dbManager.getDatabase();
        //执行添加收入信息操作
        db.execSQL("insert into tb_inaccount (_id,money,time,type,handler,mark) values (?,?,?,?,?,?)",
                new Object[]{tb_inaccount.getid(), tb_inaccount.getMoney(), tb_inaccount.getTime(),
                        tb_inaccount.getType(), tb_inaccount.getHandler(), tb_inaccount.getMark()});
    }

    //更新收入信息
    public void updata(Tb_inaccount tb_inaccount) {
        db = dbManager.getDatabase();
        //执行更新收入信息操作
        db.execSQL("update tb_inaccount set money = ?,time = ?,type = ?,handler = ?,mark = ? where _id = ?",
                new Object[]{tb_inaccount.getMoney(), tb_inaccount.getTime(), tb_inaccount.getType(), tb_inaccount.getHandler(), tb_inaccount.getMark(), tb_inaccount.getid()});
    }


    //查找本月的收入总和
    public double inaccount_sum(String str){
        db = dbManager.getDatabase();
        double sum=0.00;
        Cursor cursor = db.rawQuery("select total(money) from tb_inaccount where time like '"+ str +"'",null);
        if(cursor!=null){
            if(cursor.moveToFirst()){
                do{
                    sum= cursor.getDouble(0);
                }while (cursor.moveToNext());
            }
        }
        return sum;
    }
    //查找收入信息
    public Tb_inaccount find(int id) {
        db = dbManager.getDatabase();
        Cursor cursor = db.rawQuery("select _id,money,time,type,handler,mark from tb_inaccount where _id = ?",
                new String[]{String.valueOf(id)});
        if (cursor.moveToNext()) {
            //将遍历到的收入信息存储到Tb_inaccount类中
            return new Tb_inaccount(cursor.getInt(cursor.getColumnIndex("_id")),
            cursor.getDouble(cursor.getColumnIndex("money")),cursor.getString(cursor.getColumnIndex("time")),
            cursor.getString(cursor.getColumnIndex("type")),cursor.getString(cursor.getColumnIndex("handler")),
            cursor.getString(cursor.getColumnIndex("mark")));
        }
        //如果没有信息则返回null
        return null;
    }

    //删除收入信息
    public void detele(HashSet<Integer> ids){
        //判断是否存在要删除的id
        if(ids.size() > 0){
            StringBuffer sb = new StringBuffer();    //创建StringBuffer对像
            for(int i=0;i<ids.size();i++){           //遍历要删除的id集合
                sb.append('?').append(',');          //将删除条件添加到StringBuffer对象中
            }
            sb.deleteCharAt(sb.length()-1);          //去掉最后一个","字符
            db = dbManager.getDatabase();            //初始化SQLiteDatabase对象
            //执行删除收入信息操作            ????????????????????
            db.execSQL("delete from tb_inaccount where _id in (" + sb + ")",ids.toArray());
        }

    }

    //该方法的主要功能是从收入数据表的指定索引处获取指定数量的收入数据

    /**
     *
     * @param start
     * @param count
     * @return
     */
    public List<Tb_inaccount> getScrollData(int start,int count){
        List<Tb_inaccount> tb_inaccount = new ArrayList<Tb_inaccount>();
        db = dbManager.getDatabase();
        Cursor cursor = db.rawQuery("select * from tb_inaccount limit ?,?",new String[]
                {String.valueOf(start),String.valueOf(count)});
        //遍历所有的收入信息到集合中
        while(cursor.moveToNext()){
            tb_inaccount.add(new Tb_inaccount(cursor.getInt(cursor.getColumnIndex("_id")),
            cursor.getDouble(cursor.getColumnIndex("money")),cursor.getString(cursor.getColumnIndex("time")),
            cursor.getString(cursor.getColumnIndex("type")),cursor.getString(cursor.getColumnIndex("handler")),
            cursor.getString(cursor.getColumnIndex("mark"))));
        }
        return tb_inaccount;
    }


    //该方法的主要功能是获取收入数据表中的总记录数，返回值为获取到的总记录数

    /**
     *
     * @return
     */
    public long getCount(){
        db = dbManager.getDatabase();
        Cursor cursor = db.rawQuery("select count(_id) from tb_inaccount",null);
        if(cursor.moveToNext()){
            return cursor.getLong(0);
        }
        return 0;
    }

    /**
     * 获取收入最大编号
     * @return
     */
    public int getMaxId(){
        db = dbManager.getDatabase();
        Cursor cursor = db.rawQuery("select max(_id) from tb_inaccount",null);
        while(cursor.moveToLast()){
            return cursor.getInt(0);
        }
        return 0;
    }
}

