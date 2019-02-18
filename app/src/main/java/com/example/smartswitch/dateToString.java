package com.example.smartswitch;


    import java.text.SimpleDateFormat;
import java.util.Date;

public  class dateToString {


        public  static String nowdateToString() {
            Date date = new Date();
            SimpleDateFormat sdf=new SimpleDateFormat("HH 小时 mm 分钟 ss 秒");
            String reStr = sdf.format(date);
            System.out.println(reStr);
            return reStr;
        }

    public   static void main(String args[] ){
        nowdateToString();
    }
}
