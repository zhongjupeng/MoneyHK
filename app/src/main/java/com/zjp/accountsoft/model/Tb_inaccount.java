package com.zjp.accountsoft.model;

/**
 * 该类是一个收入信息实体类，是对收入信息表中所有字段的封装
 */
public class Tb_inaccount {

    private int _id;                //存储收入编号
    private double money;           //存储收入金额
    private String time;            //存储收入时间
    private String type;            //存储收入类别
    private String handler;         //存储收入付款方
    private String mark;            //存储收入备注

    public Tb_inaccount(){          //默认构造函数
        super();
    }

    //定义有参数的构造函数，用来初始化收入信息实体类中的各个字段
    public Tb_inaccount(int id,double money,String time,String type,String handler,String mark){
        super();
        this._id = id;
        this.money = money;
        this.time = time;
        this.type = type;
        this.handler = handler;
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

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }




}
