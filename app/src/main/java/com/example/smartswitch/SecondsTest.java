package com.example.smartswitch;

 
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
 
/**
 * 将秒转换为时间
 */
public  class SecondsTest {

    /**
     * 返回毫秒
     * @param date
     * @return
     */
    static long timeToSecond(String date) {
        Log.d("aa", "传入的时间 "+date);
        String Hour=date.substring(0,2 );
      //  Log.d("aa", "传入的时间 Hour "+Hour);
        String Minute=date.substring(2,4 );
       // Log.d("aa", "传入的时间 Minute"+Minute);
        String Second=date.substring(4,6 );
      //  Log.d("aa", "传入的时间 Second"+Second);
        long mMillisInFuture= (Integer.parseInt(Hour)*3600+Integer.parseInt(Minute)*60+Integer.parseInt(Second))*1000;
        Log.d("aa", mMillisInFuture+"毫秒");
        return mMillisInFuture;

    }
    static String secondToTime(long second) {
        long days = second / 86400;//转换天数
        second = second % 86400;//剩余秒数
        long hours = second / 3600;//转换小时数
        second = second % 3600;//剩余秒数
        long minutes = second / 60;//转换分钟
        second = second % 60;//剩余秒数
        if (0 < days){
            return days + "天 "+hours+" 小时 "+minutes+" 分 "+second+" 秒 ";
        }else {
            return hours+" 小时 "+minutes+" 分 "+second+" 秒 ";
        }
    }
}
