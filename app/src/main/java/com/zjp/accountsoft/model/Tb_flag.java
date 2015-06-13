package com.zjp.accountsoft.model;

/**
 * 该类是一个便签信息实体类，是对便签信息表中所有字段的封装
 */
public class Tb_flag {

    private int id;            //存储便签编号
    private String flag;        //存储便签内容
    private String time;        //存储写便签的时间

    public Tb_flag(){
        super();
    }

    //定义有参数的构造函数，用来初始化便签信息实体类中的各个字段
    public Tb_flag(int id, String flag,String time){
        super();
        this.id = id;
        this.flag = flag;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getid() {
        return id;
    }

    public void setid(int _id) {
        this.id = _id;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
