package com.example.smartswitch;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public  class Dateadd {

    public  static String adddays(String hourMinSec,int hour,int min,int sec) throws ParseException
    {

        SimpleDateFormat sdf= new SimpleDateFormat("HH 小时 mm 分钟 ss 秒");

        Date date =sdf.parse(hourMinSec);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR,hour);
        calendar.add(Calendar.MINUTE,min);
        calendar.add(Calendar.SECOND,sec);

        Date dt1=calendar.getTime();
        String reStr = sdf.format(dt1);
        System.out.println(reStr);
        return reStr;

    }
    public  static String adddays2(String hourMinSec,int hour,int min,int sec) throws ParseException
    {

        SimpleDateFormat sdf= new SimpleDateFormat("HH 时 mm 分 ss 秒");

        Date date =sdf.parse(hourMinSec);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR,hour);
        calendar.add(Calendar.MINUTE,min);
        calendar.add(Calendar.SECOND,sec);

        Date dt1=calendar.getTime();
        String reStr = sdf.format(dt1);
        System.out.println(reStr);
        return reStr;

    }

//    public static  static void main(String args[] ){
//        try {
//            adddays("01 小时 5 分钟 55 秒",1,2,3);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//    }
}