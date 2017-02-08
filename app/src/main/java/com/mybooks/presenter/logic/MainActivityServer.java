package com.mybooks.presenter.logic;

import android.content.Context;

import com.jty.utils.TimeUtils;
import com.mybooks.models.DataRange;
import com.mybooks.presenter.db.AccountDBDAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by JTY on 2016/8/12 0012.
 */
public class MainActivityServer {

    public static List<Map<String,String>> getData(float income,float spend){
        List<Map<String,String>> maps = new ArrayList<Map<String, String>>();
        Map<String,String> map;
        String allInCome = "￥"+income;
        String allSpend = "￥"+spend;
        String allBalance = "￥"+(income - spend);

        map = new HashMap<String,String>();
        map.put("MyMoneyName","总收入：");
        map.put("MyMoneyNum",allInCome);
        maps.add(map);

        map = new HashMap<String,String>();
        map.put("MyMoneyName","总支出：");
        map.put("MyMoneyNum", allSpend);
        maps.add(map);

        map = new HashMap<String,String>();
        map.put("MyMoneyName","总余额：");
        map.put("MyMoneyNum",allBalance);
        maps.add(map);

        return maps;
    }

    public static List<DataRange> getAllData(Context context,String name){
        List<DataRange> dataRanges = new ArrayList<DataRange>();
        DataRange dataRange=null;
        AccountDBDAO accountDBDAO = new AccountDBDAO(context);

        String today = TimeUtils.getDayTime();
        String month = TimeUtils.getLikeMonth();
        String year = TimeUtils.getLikeYear();

        dataRange = new DataRange();
        dataRange.setTimeRange("今日账务：");
        dataRange.setInCome("收入：" + accountDBDAO.findDayInCome(name, today));
        dataRange.setSpend("支出：" + accountDBDAO.findDaySpend(name, today));
        dataRanges.add(dataRange);

        dataRange = new DataRange();
        dataRange.setTimeRange("今月账务：");
        dataRange.setInCome("收入：" + accountDBDAO.findMonthInCome(name, month));
        dataRange.setSpend("支出：" + accountDBDAO.findMonthSpend(name, month));
        dataRanges.add(dataRange);

        dataRange = new DataRange();
        dataRange.setTimeRange("今年账务：");
        dataRange.setInCome("收入：" + accountDBDAO.findYearInCome(name, year));
        dataRange.setSpend("支出：" + accountDBDAO.findYearSpend(name, year));
        dataRanges.add(dataRange);

        return dataRanges;
    }


}
