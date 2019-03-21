package com.example.smartswitch;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class dateToString {


    public static String nowdateToString() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
        String reStr = sdf.format(date);
        System.out.println(reStr);
        return reStr;
    }

    public static String nowdateToString4() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String reStr = sdf.format(date);
        System.out.println(reStr);
        return reStr;
    }

    public static String nowdateToString2() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String reStr = sdf.format(date);
        System.out.println(reStr);
        return reStr;
    }

    public static String nowdateToString3() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String reStr = sdf.format(date);
        System.out.println(reStr);
        return reStr;
    }

    public static String dateToWeek(String datetime) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance(); // 获得一个日历
        Date datet = null;
        try {
            datet = f.parse(datetime);
            cal.setTime(datet);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    public static String dateToWeekNum(String datetime) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = {"日", "一", "二", "三", "四", "五", "六"};
        Calendar cal = Calendar.getInstance(); // 获得一个日历
        Date datet = null;
        try {
            datet = f.parse(datetime);
            cal.setTime(datet);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    public static boolean compareDateByGetTime(Date date1, Date date2) {
        if (date1.getTime() < date2.getTime()) {
            System.out.println(date1 + "在" + date2 + "前面");
            return true;
        } else if (date1.getTime() > date2.getTime()) {
            System.out.println(date1 + "在" + date2 + "后面");
            return false;
        } else {
            System.out.println("是同一天的同一时间");
            return false;
        }
    }

    public static boolean compareDate(String date1, String date2) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        try {
            Date date3 = format.parse(date1);
            Date date4 = format.parse(date2);
            //compareDate(date3,date4);//方式一
            if (compareDateByGetTime(date3, date4)) {
                return true;
            } else return false;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static void main(String args[]) {
        if (compareDate("10:10:00", "10:10:02")) {
            System.out.println("0k");
        }
    }
}
