package com.mybooks.presenter.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mybooks.models.Person;

import java.util.List;

/**
 * Created by JTY on 2016/8/10 0014.
 */
public class PersonDBDDAO {
    private Context context;
    MyDBOpenHelper myDBOpenHelper;

    public PersonDBDDAO(Context context){
        this.context=context;
        myDBOpenHelper = new MyDBOpenHelper(context);
    }

    /**
     * 增
     */
    public void addPerson(String name,String pass){
        SQLiteDatabase db = myDBOpenHelper.getWritableDatabase();
        if(db.isOpen()){
            ContentValues cv = new ContentValues();
            cv.put("name",name);
            cv.put("password",pass);
            cv.put("login", false);
            db.insert("person", null, cv);
        }
        db.close();
    }
    /**
     * 删
     */
    public void deletePerson(String name){
        SQLiteDatabase db = myDBOpenHelper.getWritableDatabase();
        if(db.isOpen()){
            db.delete("person", "name=?", new String[]{name});

        }
        db.close();
    }
    /**
     * 改
     */
    //改用户
    public void updateUser(String name,String newName,String newPass){
        SQLiteDatabase db = myDBOpenHelper.getWritableDatabase();
        if(db.isOpen()){
            ContentValues cv = new ContentValues();
            cv.put("name",newName);
            cv.put("password",newPass);
            cv.put("login", false);
            db.update("person", cv, "name=?", new String[]{name});

        }
        db.close();

    }
    //改登录状态
    public void updataLoginOK(String name){
        SQLiteDatabase db = myDBOpenHelper.getWritableDatabase();
        if(db.isOpen()){
            ContentValues cv = new ContentValues();
            cv.put("login", true);
            db.update("person", cv, "name=?", new String[]{name});

        }
        db.close();
    }

    public void updateLoginCancel(String name){
        SQLiteDatabase db = myDBOpenHelper.getWritableDatabase();
        if(db.isOpen()){
            ContentValues cv = new ContentValues();
            cv.put("login", false);
            db.update("person", cv, "name=?", new String[]{name});

        }
        db.close();
    }

    /**
     * 查
     */
    //用户是否存在
    public boolean findPerson(String name){
    boolean result=false;
        SQLiteDatabase db = myDBOpenHelper.getWritableDatabase();
        if(db.isOpen()){
            Cursor cursor = db.query("person",null,"name=?",new String[]{name},null,null,null);
            if(cursor.moveToNext()){
                result = true;
            }
            cursor.close();
        }
        db.close();
        return result;
    }

    //查看全部用户
    public List<Person> findAll(){
        List<Person> persons=null;
        SQLiteDatabase db = myDBOpenHelper.getWritableDatabase();
        if(db.isOpen()){
            Cursor cursor = db.query("person",null,null,null,null,null,null);
            while(cursor.moveToNext()){
                Person person = new Person();
                String name = cursor.getString(cursor.getColumnIndex("name"));
                person.setName(name);
                //对boolean型的可以用long来判断，0，1可以自定义
                long login = cursor.getLong(cursor.getColumnIndex("login"));
                if(login==0){
                    person.setLogin(false);
                }else{
                    person.setLogin(true);
                }
                persons.add(person);
            }
            cursor.close();

        }
        db.close();
        return persons;
    }

    //查看是否登录
    public boolean findLogin(String name,String pass){
        boolean login = false;
        SQLiteDatabase db = myDBOpenHelper.getWritableDatabase();
        if(db.isOpen()){
            Cursor cursor = db.query("person",null,"name=? and password=?",new String[]{name,pass},null,null,null);
            if(cursor.moveToNext()){
                login = true;
            }

            cursor.close();
        }
        db.close();
        return login;
    }

    //判断是否登录成功
    public Person findLoginOK(){
        Person person=null;
        SQLiteDatabase db = myDBOpenHelper.getReadableDatabase();
        if(db.isOpen()){
            Cursor cursor = db.rawQuery("select personid as _id,name from person where login=1",null);
            if(cursor.moveToNext()){
                person = new Person();
                String name = cursor.getString(cursor.getColumnIndex("name"));
                person.setName(name);
                person.setLogin(true);
            }
            cursor.close();

        }
        db.close();
        return person;
    }




}
