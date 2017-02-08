package com.mybooks.presenter.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mybooks.models.Account;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JTY on 2016/8/11 0014.
 */
public class AccountDBDAO {
    Context context;
    MyDBOpenHelper myDBOpenHelper;
    public AccountDBDAO(Context context) {
        this.context = context;
        myDBOpenHelper = new MyDBOpenHelper(context);

    }

    /**
     * 增
     */
    public void add(String time, float money, String type, boolean earnings, String remark,String name){
        SQLiteDatabase db = myDBOpenHelper.getWritableDatabase();
        if(db.isOpen()){
//            String sql = "insert into account(time,money,type,earnings,remark,name) values (?,?,?,?,?,?)";
//            db.execSQL(sql,new Object[]{time,money,type,earnings,remark,name});
            ContentValues cv = new ContentValues();
            cv.put("name",name);
            cv.put("money",money);
            cv.put("time",time);
            cv.put("type",type);
            cv.put("earnings",earnings);
            cv.put("remark", remark);
            db.insert("account", null, cv);
        }
        db.close();
    }
    /**
     * 删
     */
    public void delete(String accountid){
        SQLiteDatabase db = myDBOpenHelper.getWritableDatabase();
        if(db.isOpen()){
            db.delete("account", "accountid=?", new String[]{accountid});
        }
        db.close();
    }

    /**
     * 改
     */
    public void update(String accountid,String time, float money, String type, boolean earnings, String remark){
        SQLiteDatabase db = myDBOpenHelper.getWritableDatabase();
        if(db.isOpen()){
            ContentValues cv = new ContentValues();
            cv.put("time",time);
            cv.put("money",money);
            cv.put("type",type);
            cv.put("earnings",earnings);
            cv.put("remark", remark);
            db.update("account", cv, "accountid=?", new String[]{accountid});
        }
        db.close();
    }
    /**
     * 查
     */
    public boolean find(String name){
        boolean result = false;
        SQLiteDatabase db = myDBOpenHelper.getReadableDatabase();
        if(db.isOpen()){
            Cursor cursor = db.query("account", null, "name=?", new String[]{name}, null, null, null);
            if(cursor.moveToNext()){
                result = true;
            }
            cursor.close();
        }
        db.close();
        return result;
    }
    /**
     * 全查
     */
    public List<Account> findAll(){
        List<Account> accounts=null;
        SQLiteDatabase db = myDBOpenHelper.getReadableDatabase();
        if(db.isOpen()){
            accounts = new ArrayList<>();
            Cursor cursor = db.query("account", null, null, null, null, null, null);
            while(cursor.moveToNext()){
                Account account = new Account();
                int id = cursor.getInt(cursor.getColumnIndex("accountid"));
                account.setId(id);
                String name = cursor.getString(cursor.getColumnIndex("name"));
                account.setName(name);
                String time = cursor.getString(cursor.getColumnIndex("time"));
                account.setTime(time);
                float money = cursor.getFloat(cursor.getColumnIndex("money"));
                account.setMoney(money);
                String type = cursor.getString(cursor.getColumnIndex("type"));
                account.setType(type);
                long earnings = cursor.getLong(cursor.getColumnIndex("earnings"));
                //对boolean型的可以用long来判断 0,1可以进行自定义
                if(earnings==0){
                    account.setEarnings(false);
                }else{
                    account.setEarnings(true);
                }
                String remark = cursor.getString(cursor.getColumnIndex("remark"));
                account.setRemark(remark);
                accounts.add(account);
            }
            cursor.close();
        }
        db.close();
        return accounts;
    }
    /**
     * 根据用户名查询
     */
    public List<Account> findAllByName(String username){
        List<Account> accounts=null;
        SQLiteDatabase db = myDBOpenHelper.getReadableDatabase();
        if(db.isOpen()){
//            Cursor cursor = db.query("account",null,"name=?",new String[]{username},null,null,null);
            Cursor cursor = db.rawQuery("select * from account where name=?", new String[]{username});
            accounts = new ArrayList<Account>();
            while(cursor.moveToNext()){
                Account account = new Account();
                int id = cursor.getInt(cursor.getColumnIndex("accountid"));
                account.setId(id);
                String name = cursor.getString(cursor.getColumnIndex("name"));
                account.setName(name);
                String time = cursor.getString(cursor.getColumnIndex("time"));
                account.setTime(time);
                float money = cursor.getFloat(cursor.getColumnIndex("money"));
                account.setMoney(money);
                String type = cursor.getString(cursor.getColumnIndex("type"));
                account.setType(type);
                long earnings = cursor.getLong(cursor.getColumnIndex("earnings"));
                //对boolean型的可以用long来判断 0,1可以进行自定义
                if(earnings==0){
                    account.setEarnings(false);
                }else{
                    account.setEarnings(true);
                }
                String remark = cursor.getString(cursor.getColumnIndex("remark"));
                account.setRemark(remark);
                accounts.add(account);
            }
            cursor.close();
        }
        db.close();
        return accounts;
    }
    /**
     * 查询收入信息
     */
    public List<Account> findInComeByName(String username){
        List<Account> accounts=null;
        SQLiteDatabase db = myDBOpenHelper.getReadableDatabase();
        if(db.isOpen()){
//            Cursor cursor = db.query("account",null,"earnings=1 and name=?",new String[]{username},null,null,null);
            Cursor cursor = db.rawQuery("select * from account where earnings=1 and name=?", new String[]{username});
            accounts = new ArrayList<Account>();
            while(cursor.moveToNext()){
                Account account = new Account();
                int id = cursor.getInt(cursor.getColumnIndex("accountid"));
                account.setId(id);
                String name = cursor.getString(cursor.getColumnIndex("name"));
                account.setName(name);
                String time = cursor.getString(cursor.getColumnIndex("time"));
                account.setTime(time);
                float money = cursor.getFloat(cursor.getColumnIndex("money"));
                account.setMoney(money);
                String type = cursor.getString(cursor.getColumnIndex("type"));
                account.setType(type);
                long earnings = cursor.getLong(cursor.getColumnIndex("earnings"));
                //对boolean型的可以用long来判断 0,1可以进行自定义
                if(earnings==0){
                    account.setEarnings(false);
                }else{
                    account.setEarnings(true);
                }
                String remark = cursor.getString(cursor.getColumnIndex("remark"));
                account.setRemark(remark);
                accounts.add(account);
            }
            cursor.close();
        }
        db.close();

        return accounts;
    }


    /**
     * 查询支出信息
     */
    public List<Account> findSpendByName(String username){
        List<Account> accounts=null;
        SQLiteDatabase db = myDBOpenHelper.getReadableDatabase();
        if(db.isOpen()){
//            Cursor cursor = db.query("account",null,"earnings=0 and name=?",new String[]{username},null,null,null);
            Cursor cursor = db.rawQuery("select * from account where earnings=0 and name=?", new String[]{username});
            accounts = new ArrayList<Account>();
            while(cursor.moveToNext()){
                Account account = new Account();
                int id = cursor.getInt(cursor.getColumnIndex("accountid"));
                account.setId(id);
                String name = cursor.getString(cursor.getColumnIndex("name"));
                account.setName(name);
                String time = cursor.getString(cursor.getColumnIndex("time"));
                account.setTime(time);
                float money = cursor.getFloat(cursor.getColumnIndex("money"));
                account.setMoney(money);
                String type = cursor.getString(cursor.getColumnIndex("type"));
                account.setType(type);
                long earnings = cursor.getLong(cursor.getColumnIndex("earnings"));
                //对boolean型的可以用long来判断 0,1可以进行自定义
                if(earnings==0){
                    account.setEarnings(false);
                }else{
                    account.setEarnings(true);
                }
                String remark = cursor.getString(cursor.getColumnIndex("remark"));
                account.setRemark(remark);
                accounts.add(account);
            }
            cursor.close();
        }
        db.close();


        return accounts;
    }
    /**
     * 时间段查询
     */
    public List<Account> findAllByTime(String username,String sometime){
        List<Account> accounts=null;
        SQLiteDatabase db = myDBOpenHelper.getReadableDatabase();
        if(db.isOpen()){
//            Cursor cursor = db.query("account",null,"earnings=1 and name=?",new String[]{username},null,null,null);
            Cursor cursor = db.rawQuery("select * from account where name=? and time like ?",
                    new String[]{username,sometime});
            accounts = new ArrayList<Account>();
            while(cursor.moveToNext()){
                Account account = new Account();
                int id = cursor.getInt(cursor.getColumnIndex("accountid"));
                account.setId(id);
                String name = cursor.getString(cursor.getColumnIndex("name"));
                account.setName(name);
                String time = cursor.getString(cursor.getColumnIndex("time"));
                account.setTime(time);
                float money = cursor.getFloat(cursor.getColumnIndex("money"));
                account.setMoney(money);
                String type = cursor.getString(cursor.getColumnIndex("type"));
                account.setType(type);
                long earnings = cursor.getLong(cursor.getColumnIndex("earnings"));
                //对boolean型的可以用long来判断 0,1可以进行自定义
                if(earnings==0){
                    account.setEarnings(false);
                }else{
                    account.setEarnings(true);
                }
                String remark = cursor.getString(cursor.getColumnIndex("remark"));
                account.setRemark(remark);
                accounts.add(account);
            }
            cursor.close();
        }
        db.close();
        return accounts;
    }
    /**
     * 查询总收入
     */
    public float findAllInCome(String name){
        SQLiteDatabase db = myDBOpenHelper.getReadableDatabase();
        if(db.isOpen()){
            Cursor cursor = db.rawQuery(
                    "select sum(money) as sumincome from account where earnings=1 and name=?",
                    new String[]{name});
            while(cursor.moveToNext()){
                return cursor.getFloat(cursor.getColumnIndex("sumincome"));
            }
            cursor.close();
        }
        db.close();
        return 0;
    }
    /**
     * 查询总支出
     */
    public float findAllSpend(String name) {
        SQLiteDatabase db = myDBOpenHelper.getReadableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery(
                    "select sum(money) as sumspend from account where earnings=0 and name=?",
                    new String[]{name});
            while(cursor.moveToNext()){
                return cursor.getFloat(cursor.getColumnIndex("sumspend"));
            }
            cursor.close();
        }
        db.close();
        return 0;
    }
    /**
     * 日收入查询
     */
    public float findDayInCome(String name,String time) {
        SQLiteDatabase db = myDBOpenHelper.getReadableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery(
                    "select sum(money) as sumvalues from account where earnings=1 and name=? and time=?",
                    new String[]{name,time});
            while(cursor.moveToNext()){
                return cursor.getFloat(cursor.getColumnIndex("sumvalues"));
            }
            cursor.close();
        }
        db.close();
        return 0;
    }
    /**
     * 日支出查询
     */
    public float findDaySpend(String name,String time) {
        SQLiteDatabase db = myDBOpenHelper.getReadableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery(
                    "select sum(money) as sumvalues from account where earnings=0 and name=? and time=?",
                    new String[]{name,time});
            while(cursor.moveToNext()){
                return cursor.getFloat(cursor.getColumnIndex("sumvalues"));
            }
            cursor.close();
        }
        db.close();
        return 0;
    }
    /**
     * 月收入查询
     */
    public float findMonthInCome(String name,String time) {
        SQLiteDatabase db = myDBOpenHelper.getReadableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery(
                    "select sum(money) as sumvalues from account where earnings=1 and name=? and time like ?",
                    new String[]{name,time});
            while(cursor.moveToNext()){
                return cursor.getFloat(cursor.getColumnIndex("sumvalues"));
            }
            cursor.close();
        }
        db.close();
        return 0;
    }
    /**
     * 月支出查询
     */
    public float findMonthSpend(String name,String time) {
        SQLiteDatabase db = myDBOpenHelper.getReadableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery(
                    "select sum(money) as sumvalues from account where earnings=0 and name=? and time like ?",
                    new String[]{name,time});
            while(cursor.moveToNext()){
                return cursor.getFloat(cursor.getColumnIndex("sumvalues"));
            }
            cursor.close();
        }
        db.close();
        return 0;
    }
    /**
     * 年收入查询
     */
    public float findYearInCome(String name,String time) {
        SQLiteDatabase db = myDBOpenHelper.getReadableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery(
                    "select sum(money) as sumvalues from account where earnings=1 and name=? and time like ?",
                    new String[]{name,time});
            while(cursor.moveToNext()){
                return cursor.getFloat(cursor.getColumnIndex("sumvalues"));
            }
            cursor.close();
        }
        db.close();
        return 0;
    }
    /**
     * 年支出查询
     */
    public float findYearSpend(String name,String time) {
        SQLiteDatabase db = myDBOpenHelper.getReadableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery(
                    "select sum(money) as sumvalues from account where earnings=0 and name=? and time like ?",
                    new String[]{name,time});
            while(cursor.moveToNext()){
                return cursor.getFloat(cursor.getColumnIndex("sumvalues"));
            }
            cursor.close();
        }
        db.close();
        return 0;
    }
    /**
     * 根据id查询记录信息
     */
    public Account findInfoById(String accountId){
        Account account=null;
        SQLiteDatabase db = myDBOpenHelper.getReadableDatabase();
        if(db.isOpen()){
            Cursor cursor = db.rawQuery("select * from account where accountid=?",
                    new String[]{accountId});
            while(cursor.moveToNext()){
                account = new Account();
                int id = cursor.getInt(cursor.getColumnIndex("accountid"));
                account.setId(id);
                String name = cursor.getString(cursor.getColumnIndex("name"));
                account.setName(name);
                String time = cursor.getString(cursor.getColumnIndex("time"));
                account.setTime(time);
                float money = cursor.getFloat(cursor.getColumnIndex("money"));
                account.setMoney(money);
                String type = cursor.getString(cursor.getColumnIndex("type"));
                account.setType(type);
                long earnings = cursor.getLong(cursor.getColumnIndex("earnings"));
                //对boolean型的可以用long来判断 0,1可以进行自定义
                if(earnings==0){
                    account.setEarnings(false);
                }else{
                    account.setEarnings(true);
                }
                String remark = cursor.getString(cursor.getColumnIndex("remark"));
                account.setRemark(remark);
            }
            cursor.close();
        }
        db.close();
        return account;
    }



}
