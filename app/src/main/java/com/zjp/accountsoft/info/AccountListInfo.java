package com.zjp.accountsoft.info;


/**
 * 该类是一个收入、支出部分信息实体类，用于显示在AccountList上的信息
 */
public class AccountListInfo {

   private int id;
   private String mType;
   private double mMoney;
   private String mTime;
   private String mSign;
   private String mMark;
   private String mHandler;
   private String mAddress;


   public AccountListInfo(int id,String type,double money,String time,String sign,String Mark,String Handler,String Address){
       this.id = id;
       this.mType = type;
       this.mMoney = money;
       this.mTime = time;
       this.mSign = sign;
       this.mMark = Mark;
       this.mHandler = Handler;
       this.mAddress = Address;
   }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getmMark() {
        return mMark;
    }

    public void setmMark(String mMark) {
        this.mMark = mMark;
    }

    public String getmHandler() {
        return mHandler;
    }

    public void setmHandler(String mHandler) {
        this.mHandler = mHandler;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getmSign() {
        return mSign;
    }

    public void setmSign(String mSign) {
        this.mSign = mSign;
    }

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    public double getmMoney() {
        return mMoney;
    }

    public void setmMoney(double mMoney) {
        this.mMoney = mMoney;
    }

    public String getmTime() {
        return mTime;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }
}
