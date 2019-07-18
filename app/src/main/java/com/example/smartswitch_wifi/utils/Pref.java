package com.example.smartswitch_wifi.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/11/29.
 */

public class Pref {

    private static Pref instance = null;
    private static final String DEFAULT_HOST = "192.168.137.164";
    private static final int DEFAULT_PORT = 5000;

    private final SharedPreferences p;

    private Pref(Context context){
        p = context.getSharedPreferences("data", Context.MODE_PRIVATE);
    }

    public static Pref getInstance(Context context){
        if (instance == null){
            instance = new Pref(context);
        }
        return instance;
    }

    public void setHost(String hostStr){
        p.edit().putString("host",hostStr).apply();
    }

    public String getHost(){
       return p.getString("host",DEFAULT_HOST);
    }

    public void setPort(int port){
        p.edit().putInt("port",port).apply();
    }

    public int getPort(){
       return p.getInt("port",DEFAULT_PORT);
    }

}

