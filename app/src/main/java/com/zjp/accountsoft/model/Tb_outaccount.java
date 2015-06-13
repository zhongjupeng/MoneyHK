package com.zjp.accountsoft.model;

/**
 * 该类是一个支出信息实体类，是对支出信息表中所有字段的封装
 */
public class Tb_outaccount {

    private int _id;                //存储支出编号
    private double money;           //存储支出金额
    private String time;            //存储支出时间
    private String type;            //存储支出类别
    private String address;         //存储支出地点
    private String mark;            //存储支出备注

    public Tb_outaccount(){         //默认构造函数
        super();
    }

    //定义有参数的构造函数，用来初始化支出信息实体类中的各个字段
    public Tb_outaccount(int id,double money,String time,String type,String address,String mark){
        super();
        this._id = id;
        this.money = money;
        this.time = time;
        this.type = type;
        this.address = address;
        this.mark = mark;
    }


    public int getid() {
        return _id;
    }

    public void setid(int _id) {
        this._id = _id;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }


}
