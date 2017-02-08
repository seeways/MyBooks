package com.mybooks.models;

import java.io.Serializable;

/**
 * Created by JTY on 2016/8/10 0014.
 */
public class Person implements Serializable{
    int id;
    String name;
    boolean login;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
