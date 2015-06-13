package com.zjp.accountsoft.info;

/**
 * 该类用于存储便签的信息
 */
public class FlagListInfo {

    private int id;
    private String flag;
    private String time;

    public FlagListInfo(int id,String flag,String time){
        this.id = id;
        this.flag = flag;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
