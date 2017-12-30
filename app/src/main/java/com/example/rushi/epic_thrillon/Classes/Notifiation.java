package com.example.rushi.epic_thrillon.Classes;

/**
 * Created by dhaval on 28/12/2017.
 */

public class Notifiation {

    private String name;
    private String data;

    public  Notifiation(){

    }

    public Notifiation(String name, String data) {
        this.name = name;
        this.data = data;
    }

    public String getName(){
        return name;
    }
    public String getData(){
        return data;
    }
}
