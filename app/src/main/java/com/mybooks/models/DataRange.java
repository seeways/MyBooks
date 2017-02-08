package com.mybooks.models;

import java.io.Serializable;

/**
 * Created by JTY on 2016/8/11 0011.
 */
public class DataRange implements Serializable{
    String timeRange;
    String inCome;
    String Spend;

    public String getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(String timeRange) {
        this.timeRange = timeRange;
    }

    public String getInCome() {
        return inCome;
    }

    public void setInCome(String inCome) {
        this.inCome = inCome;
    }

    public String getSpend() {
        return Spend;
    }

    public void setSpend(String spend) {
        Spend = spend;
    }
}
