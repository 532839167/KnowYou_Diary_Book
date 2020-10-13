package com.diarybook.entity;


public class User {

    private String ID;
    private String name;
    private String passwd;


    public String getName() {
        return name;
    }
    public void setID(String iD) {
        ID = iD;
    }
    public String getID() {
        return ID;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPasswd() {
        return passwd;
    }
    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

}
