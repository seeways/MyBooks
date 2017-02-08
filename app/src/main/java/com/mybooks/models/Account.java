package com.mybooks.models;

import java.io.Serializable;

/**
 * Created by JTY on 2016/8/10 0014.
 */
public class Account implements Serializable{
    private int id;//账户id，自动增长
    private String time;//时间
    private float money;//钱
    private String type;//类型
    private boolean earnings;//收益
    private String remark;//备注
    private String name;//用户名
//
//    public Account( String time, float money, String type, boolean earnings, String remark,String name) {
//        this.time = time;
//        this.money = money;
//        this.type = type;
//        this.earnings = earnings;
//        this.remark = remark;
//        this.name = name;
//    }
//
//    public Account() {
//
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isEarnings() {
        return earnings;
    }

    public void setEarnings(boolean earnings) {
        this.earnings = earnings;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
