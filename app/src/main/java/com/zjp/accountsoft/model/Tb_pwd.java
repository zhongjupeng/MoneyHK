package com.zjp.accountsoft.model;

/**
 * 该类是一个密码信息实体类，是对密码信息表中所有字段的封装
 */
public class Tb_pwd {

    private String password;            //存储用户密码

    public Tb_pwd(){                    //默认构造函数
        super();
    }

    //定义有参数的构造函数，用来初始化密码信息实体类中的各个字段
    public Tb_pwd(String password){
        super();
        this.password = password;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
