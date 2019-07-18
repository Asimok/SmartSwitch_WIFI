package com.example.smartswitch_wifi;

public class TimeOpen {
    public static byte[] OneDay(String week, byte hh, byte mm, byte ss) {
        byte[] MON_OPEN = {(byte) 0xff, 0x06, 0x11, 0x01, hh, mm, ss, 0x0A, (byte) 0xfe};//周一开启
        byte[] TUE_OPEN = {(byte) 0xff, 0x06, 0x11, 0x02, hh, mm, ss, 0x0A, (byte) 0xfe};//周二开启
        byte[] WED_OPEN = {(byte) 0xff, 0x06, 0x11, 0x04, hh, mm, ss, 0x0A, (byte) 0xfe};//周三开启
        byte[] THU_OPEN = {(byte) 0xff, 0x06, 0x11, 0x08, hh, mm, ss, 0x0A, (byte) 0xfe};//周四开启
        byte[] FRI_OPEN = {(byte) 0xff, 0x06, 0x11, 0x10, hh, mm, ss, 0x0A, (byte) 0xfe};//周五开启
        byte[] STA_OPEN = {(byte) 0xff, 0x06, 0x11, 0x20, hh, mm, ss, 0x0A, (byte) 0xfe};//周六开启
        byte[] SUN_OPEN = {(byte) 0xff, 0x06, 0x11, 0x40, hh, mm, ss, 0x0A, (byte) 0xfe};//周日开启
        if (week.equals("1")) {
            return MON_OPEN;
        } else if (week.equals("2")) {
            return TUE_OPEN;
        } else if (week.equals("3")) {
            return WED_OPEN;
        } else if (week.equals("4")) {
            return THU_OPEN;
        } else if (week.equals("5")) {
            return FRI_OPEN;
        } else if (week.equals("6")) {
            return STA_OPEN;
        } else if (week.equals("7")) {
            return SUN_OPEN;
        }
        return null;
    }

    public static byte[] TwoDays(String week, byte hh, byte mm, byte ss) {
        byte[] MON_TUE_OPEN = {(byte) 0xff, 0x06, 0x11, 0x03, hh, mm, ss, 0x0A, (byte) 0xfe};//周一周二开启
        byte[] MON_WED_OPEN = {(byte) 0xff, 0x06, 0x11, 0x05, hh, mm, ss, 0x0A, (byte) 0xfe};//周一周三开启
        byte[] MON_THU_OPEN = {(byte) 0xff, 0x06, 0x11, 0x09, hh, mm, ss, 0x0A, (byte) 0xfe};//周一周四开启
        byte[] MON_FRI_OPEN = {(byte) 0xff, 0x06, 0x11, 0x11, hh, mm, ss, 0x0A, (byte) 0xfe};//周一周五开启
        byte[] MON_STA_OPEN = {(byte) 0xff, 0x06, 0x11, 0x21, hh, mm, ss, 0x0A, (byte) 0xfe};//周一周六开启
        byte[] MON_SUN_OPEN = {(byte) 0xff, 0x06, 0x11, 0x41, hh, mm, ss, 0x0A, (byte) 0xfe};//周一周日开启
        byte[] TUE_WED_OPEN = {(byte) 0xff, 0x06, 0x11, 0x06, hh, mm, ss, 0x0A, (byte) 0xfe};//周二周三开启
        byte[] TUE_THU_OPEN = {(byte) 0xff, 0x06, 0x11, 0x0A, hh, mm, ss, 0x0A, (byte) 0xfe};//周二周四开启
        byte[] TUE_FRI_OPEN = {(byte) 0xff, 0x06, 0x11, 0x12, hh, mm, ss, 0x0A, (byte) 0xfe};//周二周五开启
        byte[] TUE_STA_OPEN = {(byte) 0xff, 0x06, 0x11, 0x22, hh, mm, ss, 0x0A, (byte) 0xfe};//周二周六开启
        byte[] TUE_SUN_OPEN = {(byte) 0xff, 0x06, 0x11, 0x42, hh, mm, ss, 0x0A, (byte) 0xfe};//周二周日开启
        byte[] WED_THU_OPEN = {(byte) 0xff, 0x06, 0x11, 0x0C, hh, mm, ss, 0x0A, (byte) 0xfe};//周三周四开启
        byte[] WED_FRI_OPEN = {(byte) 0xff, 0x06, 0x11, 0x14, hh, mm, ss, 0x0A, (byte) 0xfe};//周三周五开启
        byte[] WED_STA_OPEN = {(byte) 0xff, 0x06, 0x11, 0x24, hh, mm, ss, 0x0A, (byte) 0xfe};//周三周六开启
        byte[] WED_SUN_OPEN = {(byte) 0xff, 0x06, 0x11, 0x44, hh, mm, ss, 0x0A, (byte) 0xfe};//周三周日开启
        byte[] THU_FRI_OPEN = {(byte) 0xff, 0x06, 0x11, 0x18, hh, mm, ss, 0x0A, (byte) 0xfe};//周四周五开启
        byte[] THU_STA_OPEN = {(byte) 0xff, 0x06, 0x11, 0x28, hh, mm, ss, 0x0A, (byte) 0xfe};//周四周六开启
        byte[] THU_SUN_OPEN = {(byte) 0xff, 0x06, 0x11, 0x48, hh, mm, ss, 0x0A, (byte) 0xfe};//周四周日开启
        byte[] FRI_STA_OPEN = {(byte) 0xff, 0x06, 0x11, 0x30, hh, mm, ss, 0x0A, (byte) 0xfe};//周五周六开启
        byte[] FRI_SUN_OPEN = {(byte) 0xff, 0x06, 0x11, 0x50, hh, mm, ss, 0x0A, (byte) 0xfe};//周五周日开启
        byte[] STA_SUN_OPEN = {(byte) 0xff, 0x06, 0x11, 0x60, hh, mm, ss, 0x0A, (byte) 0xfe};//周六周日开启
        if (week.equals("12")) {
            return MON_TUE_OPEN;
        } else if (week.equals("13")) {
            return MON_WED_OPEN;
        } else if (week.equals("14")) {
            return MON_THU_OPEN;
        } else if (week.equals("15")) {
            return MON_FRI_OPEN;
        } else if (week.equals("16")) {
            return MON_STA_OPEN;
        } else if (week.equals("17")) {
            return MON_SUN_OPEN;
        } else if (week.equals("23")) {
            return TUE_WED_OPEN;
        } else if (week.equals("24")) {
            return TUE_THU_OPEN;
        } else if (week.equals("25")) {
            return TUE_FRI_OPEN;
        } else if (week.equals("26")) {
            return TUE_STA_OPEN;
        } else if (week.equals("27")) {
            return TUE_SUN_OPEN;
        } else if (week.equals("34")) {
            return WED_THU_OPEN;
        } else if (week.equals("35")) {
            return WED_FRI_OPEN;
        } else if (week.equals("36")) {
            return WED_STA_OPEN;
        } else if (week.equals("37")) {
            return WED_SUN_OPEN;
        } else if (week.equals("45")) {
            return THU_FRI_OPEN;
        } else if (week.equals("46")) {
            return THU_STA_OPEN;
        } else if (week.equals("47")) {
            return THU_SUN_OPEN;
        } else if (week.equals("56")) {
            return FRI_STA_OPEN;
        } else if (week.equals("57")) {
            return FRI_SUN_OPEN;
        } else if (week.equals("67")) {
            return STA_SUN_OPEN;
        }

        return null;

    }

    public static byte[] ThreeDays(String week, byte hh, byte mm, byte ss) {
        byte[] MON_TUE_WED_OPEN = {(byte) 0xff, 0x06, 0x11, 0x07, hh, mm, ss, 0x0A, (byte) 0xfe};//周一、二、三开启
        byte[] MON_TUE_THU_OPEN = {(byte) 0xff, 0x06, 0x11, 0x0B, hh, mm, ss, 0x0A, (byte) 0xfe};//周一、二、四开启
        byte[] MON_TUE_FRI_OPEN = {(byte) 0xff, 0x06, 0x11, 0x13, hh, mm, ss, 0x0A, (byte) 0xfe};//周一、二、五开启
        byte[] MON_TUE_STA_OPEN = {(byte) 0xff, 0x06, 0x11, 0x23, hh, mm, ss, 0x0A, (byte) 0xfe};//周一、二、六开启
        byte[] MON_TUE_SUN_OPEN = {(byte) 0xff, 0x06, 0x11, 0x43, hh, mm, ss, 0x0A, (byte) 0xfe};//周一、二、日开启
        byte[] MON_WED_THU_OPEN = {(byte) 0xff, 0x06, 0x11, 0x0D, hh, mm, ss, 0x0A, (byte) 0xfe};//周一、三、四开启
        byte[] MON_WED_FRI_OPEN = {(byte) 0xff, 0x06, 0x11, 0x15, hh, mm, ss, 0x0A, (byte) 0xfe};//周一、三、五开启
        byte[] MON_WED_STA_OPEN = {(byte) 0xff, 0x06, 0x11, 0x25, hh, mm, ss, 0x0A, (byte) 0xfe};//周一、三、六开启
        byte[] MON_WED_SUN_OPEN = {(byte) 0xff, 0x06, 0x11, 0x45, hh, mm, ss, 0x0A, (byte) 0xfe};//周一、三、日开启
        byte[] MON_THU_FRI_OPEN = {(byte) 0xff, 0x06, 0x11, 0x19, hh, mm, ss, 0x0A, (byte) 0xfe};//周一、四、五开启
        byte[] MON_THU_STA_OPEN = {(byte) 0xff, 0x06, 0x11, 0x29, hh, mm, ss, 0x0A, (byte) 0xfe};//周一、四、六开启
        byte[] MON_THU_SUN_OPEN = {(byte) 0xff, 0x06, 0x11, 0x49, hh, mm, ss, 0x0A, (byte) 0xfe};//周一、四、日开启
        byte[] MON_FRI_STA_OPEN = {(byte) 0xff, 0x06, 0x11, 0x31, hh, mm, ss, 0x0A, (byte) 0xfe};//周一、五、六开启
        byte[] MON_FRI_SUN_OPEN = {(byte) 0xff, 0x06, 0x11, 0x51, hh, mm, ss, 0x0A, (byte) 0xfe};//周一、五、日开启
        byte[] MON_STA_SUN_OPEN = {(byte) 0xff, 0x06, 0x11, 0x61, hh, mm, ss, 0x0A, (byte) 0xfe};//周一、六、日开启
        byte[] TUE_WED_THU_OPEN = {(byte) 0xff, 0x06, 0x11, 0x0E, hh, mm, ss, 0x0A, (byte) 0xfe};//周二、三、四开启
        byte[] TUE_WED_FRI_OPEN = {(byte) 0xff, 0x06, 0x11, 0x16, hh, mm, ss, 0x0A, (byte) 0xfe};//周二、三、五开启
        byte[] TUE_WED_STA_OPEN = {(byte) 0xff, 0x06, 0x11, 0x26, hh, mm, ss, 0x0A, (byte) 0xfe};//周二、三、六开启
        byte[] TUE_WED_SUN_OPEN = {(byte) 0xff, 0x06, 0x11, 0x46, hh, mm, ss, 0x0A, (byte) 0xfe};//周二、三、日开启
        byte[] TUE_THU_FRI_OPEN = {(byte) 0xff, 0x06, 0x11, 0x1A, hh, mm, ss, 0x0A, (byte) 0xfe};//周二、四、五开启
        byte[] TUE_THU_STA_OPEN = {(byte) 0xff, 0x06, 0x11, 0x2A, hh, mm, ss, 0x0A, (byte) 0xfe};//周二、四、六开启
        byte[] TUE_THU_SUN_OPEN = {(byte) 0xff, 0x06, 0x11, 0x4A, hh, mm, ss, 0x0A, (byte) 0xfe};//周二、四、日开启
        byte[] TUE_FRI_STA_OPEN = {(byte) 0xff, 0x06, 0x11, 0x32, hh, mm, ss, 0x0A, (byte) 0xfe};//周二、五、六开启
        byte[] TUE_FRI_SUN_OPEN = {(byte) 0xff, 0x06, 0x11, 0x52, hh, mm, ss, 0x0A, (byte) 0xfe};//周二、五、日开启
        byte[] TUE_SRA_SUN_OPEN = {(byte) 0xff, 0x06, 0x11, 0x62, hh, mm, ss, 0x0A, (byte) 0xfe};//周二、六、日开启
        byte[] WED_THU_FRI_OPEN = {(byte) 0xff, 0x06, 0x11, 0x1C, hh, mm, ss, 0x0A, (byte) 0xfe};//周三、四、五开启
        byte[] WED_THU_STA_OPEN = {(byte) 0xff, 0x06, 0x11, 0x2C, hh, mm, ss, 0x0A, (byte) 0xfe};//周三、四、六开启
        byte[] WED_THU_SUN_OPEN = {(byte) 0xff, 0x06, 0x11, 0x4C, hh, mm, ss, 0x0A, (byte) 0xfe};//周三、四、日开启
        byte[] WED_FRI_STA_OPEN = {(byte) 0xff, 0x06, 0x11, 0x34, hh, mm, ss, 0x0A, (byte) 0xfe};//周三、五、六开启
        byte[] WED_FRI_SUN_OPEN = {(byte) 0xff, 0x06, 0x11, 0x54, hh, mm, ss, 0x0A, (byte) 0xfe};//周三、五、日开启
        byte[] WED_STA_SUN_OPEN = {(byte) 0xff, 0x06, 0x11, 0x64, hh, mm, ss, 0x0A, (byte) 0xfe};//周三、六、日开启
        byte[] THU_FRI_STA_OPEN = {(byte) 0xff, 0x06, 0x11, 0x38, hh, mm, ss, 0x0A, (byte) 0xfe};//周四、五、六开启
        byte[] THU_FRI_SUN_OPEN = {(byte) 0xff, 0x06, 0x11, 0x58, hh, mm, ss, 0x0A, (byte) 0xfe};//周四、五、日开启
        byte[] THU_STA_SUN_OPEN = {(byte) 0xff, 0x06, 0x11, 0x68, hh, mm, ss, 0x0A, (byte) 0xfe};//周四、六、日开启
        byte[] FRI_STA_SUN_OPEN = {(byte) 0xff, 0x06, 0x11, 0x70, hh, mm, ss, 0x0A, (byte) 0xfe};//周五、六、日开启
        if (week.equals("123")) {
            return MON_TUE_WED_OPEN;
        } else if (week.equals("124")) {
            return MON_TUE_THU_OPEN;
        } else if (week.equals("125")) {
            return MON_TUE_FRI_OPEN;
        } else if (week.equals("126")) {
            return MON_TUE_STA_OPEN;
        } else if (week.equals("127")) {
            return MON_TUE_SUN_OPEN;
        } else if (week.equals("134")) {
            return MON_WED_THU_OPEN;
        } else if (week.equals("135")) {
            return MON_WED_FRI_OPEN;
        } else if (week.equals("136")) {
            return MON_WED_STA_OPEN;
        } else if (week.equals("137")) {
            return MON_WED_SUN_OPEN;
        } else if (week.equals("145")) {
            return MON_THU_FRI_OPEN;
        } else if (week.equals("146")) {
            return MON_THU_STA_OPEN;
        } else if (week.equals("147")) {
            return MON_THU_SUN_OPEN;
        } else if (week.equals("156")) {
            return MON_FRI_STA_OPEN;
        } else if (week.equals("157")) {
            return MON_FRI_SUN_OPEN;
        } else if (week.equals("167")) {
            return MON_STA_SUN_OPEN;
        } else if (week.equals("234")) {
            return TUE_WED_THU_OPEN;
        } else if (week.equals("235")) {
            return TUE_WED_FRI_OPEN;
        } else if (week.equals("236")) {
            return TUE_WED_STA_OPEN;
        } else if (week.equals("237")) {
            return TUE_WED_SUN_OPEN;
        } else if (week.equals("245")) {
            return TUE_THU_FRI_OPEN;
        } else if (week.equals("246")) {
            return TUE_THU_STA_OPEN;
        } else if (week.equals("247")) {
            return TUE_THU_SUN_OPEN;
        } else if (week.equals("256")) {
            return TUE_FRI_STA_OPEN;
        } else if (week.equals("257")) {
            return TUE_FRI_SUN_OPEN;
        } else if (week.equals("267")) {
            return TUE_SRA_SUN_OPEN;
        } else if (week.equals("345")) {
            return WED_THU_FRI_OPEN;
        } else if (week.equals("346")) {
            return WED_THU_STA_OPEN;
        } else if (week.equals("347")) {
            return WED_THU_SUN_OPEN;
        } else if (week.equals("356")) {
            return WED_FRI_STA_OPEN;
        } else if (week.equals("357")) {
            return WED_FRI_SUN_OPEN;
        } else if (week.equals("367")) {
            return WED_STA_SUN_OPEN;
        } else if (week.equals("456")) {
            return THU_FRI_STA_OPEN;
        } else if (week.equals("457")) {
            return THU_FRI_SUN_OPEN;
        } else if (week.equals("467")) {
            return THU_STA_SUN_OPEN;
        } else if (week.equals("567")) {
            return FRI_STA_SUN_OPEN;
        }
        return null;
    }

    public static byte[] FourDays(String week, byte hh, byte mm, byte ss) {
        byte[] MON_TUE_WED_THU_OPEN = {(byte) 0xff, 0x06, 0x11, 0x0F, hh, mm, ss, 0x0A, (byte) 0xfe};//周一、二、三，四开启
        byte[] MON_TUE_WED_FRI_OPEN = {(byte) 0xff, 0x06, 0x11, 0x17, hh, mm, ss, 0x0A, (byte) 0xfe};//周一、二、三、五开启
        byte[] MON_TUE_WED_STA_OPEN = {(byte) 0xff, 0x06, 0x11, 0x27, hh, mm, ss, 0x0A, (byte) 0xfe};//周一、二、三、六开启
        byte[] MON_TUE_WED_SUN_OPEN = {(byte) 0xff, 0x06, 0x11, 0x47, hh, mm, ss, 0x0A, (byte) 0xfe};//周一、二、三、日开启
        byte[] MON_TUE_THU_FRI_OPEN = {(byte) 0xff, 0x06, 0x11, 0x1B, hh, mm, ss, 0x0A, (byte) 0xfe};//周一、二、四，五开启
        byte[] MON_TUE_THU_STA_OPEN = {(byte) 0xff, 0x06, 0x11, 0x2B, hh, mm, ss, 0x0A, (byte) 0xfe};//周一、二、四、六开启
        byte[] MON_TUE_THU_SUN_OPEN = {(byte) 0xff, 0x06, 0x11, 0x4B, hh, mm, ss, 0x0A, (byte) 0xfe};//周一、二、四、日开启
        byte[] MON_TUE_FRI_STA_OPEN = {(byte) 0xff, 0x06, 0x11, 0x33, hh, mm, ss, 0x0A, (byte) 0xfe};//周一、二、五、六开启
        byte[] MON_TUE_FRI_SUN_OPEN = {(byte) 0xff, 0x06, 0x11, 0x53, hh, mm, ss, 0x0A, (byte) 0xfe};//周一、二、五、日开启
        byte[] MON_TUE_STA_SUN_OPEN = {(byte) 0xff, 0x06, 0x11, 0x63, hh, mm, ss, 0x0A, (byte) 0xfe};//周一、二、六、日开启

        byte[] MON_WED_THU_FRI_OPEN = {(byte) 0xff, 0x06, 0x11, 0x1D, hh, mm, ss, 0x0A, (byte) 0xfe};//周一、三、四、五开启
        byte[] MON_WED_THU_STA_OPEN = {(byte) 0xff, 0x06, 0x11, 0x2D, hh, mm, ss, 0x0A, (byte) 0xfe};//周一、三、四、六开启
        byte[] MON_WED_THU_SUN_OPEN = {(byte) 0xff, 0x06, 0x11, 0x4D, hh, mm, ss, 0x0A, (byte) 0xfe};//周一、三、四、日开启
        byte[] MON_WED_FRI_STA_OPEN = {(byte) 0xff, 0x06, 0x11, 0x35, hh, mm, ss, 0x0A, (byte) 0xfe};//周一、三、五、六开启
        byte[] MON_WED_FRI_SUN_OPEN = {(byte) 0xff, 0x06, 0x11, 0x55, hh, mm, ss, 0x0A, (byte) 0xfe};//周一、三、五、日开启
        byte[] MON_WED_STA_SUN_OPEN = {(byte) 0xff, 0x06, 0x11, 0x65, hh, mm, ss, 0x0A, (byte) 0xfe};//周一、三、六、日开启
        byte[] MON_THU_FRI_STA_OPEN = {(byte) 0xff, 0x06, 0x11, 0x39, hh, mm, ss, 0x0A, (byte) 0xfe};//周一、四、五、六开启
        byte[] MON_THU_FRI_SUN_OPEN = {(byte) 0xff, 0x06, 0x11, 0x59, hh, mm, ss, 0x0A, (byte) 0xfe};//周一、四、五、日开启
        byte[] MON_THU_STA_SUN_OPEN = {(byte) 0xff, 0x06, 0x11, 0x69, hh, mm, ss, 0x0A, (byte) 0xfe};//周一、四、六、日开启
        byte[] MON_FRI_STA_SUN_OPEN = {(byte) 0xff, 0x06, 0x11, 0x71, hh, mm, ss, 0x0A, (byte) 0xfe};//周一、五、六、日开启

        byte[] TUE_WED_THU_FRI_OPEN = {(byte) 0xff, 0x06, 0x11, 0x1E, hh, mm, ss, 0x0A, (byte) 0xfe};//周二、三、四、五开启
        byte[] TUE_WED_THU_STA_OPEN = {(byte) 0xff, 0x06, 0x11, 0x2E, hh, mm, ss, 0x0A, (byte) 0xfe};//周二、三、四、六开启
        byte[] TUE_WED_THU_SUN_OPEN = {(byte) 0xff, 0x06, 0x11, 0x4E, hh, mm, ss, 0x0A, (byte) 0xfe};//周二、三、四、日开启
        byte[] TUE_WED_FRI_STA_OPEN = {(byte) 0xff, 0x06, 0x11, 0x36, hh, mm, ss, 0x0A, (byte) 0xfe};//周二、三、五、六开启
        byte[] TUE_WED_FRI_SUN_OPEN = {(byte) 0xff, 0x06, 0x11, 0x56, hh, mm, ss, 0x0A, (byte) 0xfe};//周二、三、五、日开启
        byte[] TUE_WED_STA_SUN_OPEN = {(byte) 0xff, 0x06, 0x11, 0x66, hh, mm, ss, 0x0A, (byte) 0xfe};//周二、三、六、日开启
        byte[] TUE_THU_FRI_STA_OPEN = {(byte) 0xff, 0x06, 0x11, 0x3A, hh, mm, ss, 0x0A, (byte) 0xfe};//周二、四、五、六开启
        byte[] TUE_THU_FRI_SUN_OPEN = {(byte) 0xff, 0x06, 0x11, 0x5A, hh, mm, ss, 0x0A, (byte) 0xfe};//周二、四、五、日开启
        byte[] TUE_THU_STA_SUN_OPEN = {(byte) 0xff, 0x06, 0x11, 0x6A, hh, mm, ss, 0x0A, (byte) 0xfe};//周二、四、六、日开启
        byte[] TUE_FRI_STA_SUN_OPEN = {(byte) 0xff, 0x06, 0x11, 0x72, hh, mm, ss, 0x0A, (byte) 0xfe};//周二、五、六、日开启
        byte[] WED_THU_FRI_STA_OPEN = {(byte) 0xff, 0x06, 0x11, 0x3C, hh, mm, ss, 0x0A, (byte) 0xfe};//周三、四、五、六开启
        byte[] WED_THU_FRI_SUN_OPEN = {(byte) 0xff, 0x06, 0x11, 0x5C, hh, mm, ss, 0x0A, (byte) 0xfe};//周三、四、五、日开启
        byte[] WED_THU_STA_SUN_OPEN = {(byte) 0xff, 0x06, 0x11, 0x6C, hh, mm, ss, 0x0A, (byte) 0xfe};//周三、四、六、日开启
        byte[] WED_FRI_STA_SUN_OPEN = {(byte) 0xff, 0x06, 0x11, 0x74, hh, mm, ss, 0x0A, (byte) 0xfe};//周三、五、六、日开启

        byte[] THU_FRI_STA_SUN_OPEN = {(byte) 0xff, 0x06, 0x11, 0x78, hh, mm, ss, 0x0A, (byte) 0xfe};//周四、五、六、日开启
        if (week.equals("1234")) {
            return MON_TUE_WED_THU_OPEN;
        } else if (week.equals("1235")) {
            return MON_TUE_WED_FRI_OPEN;
        } else if (week.equals("1236")) {
            return MON_TUE_WED_STA_OPEN;
        } else if (week.equals("1237")) {
            return MON_TUE_WED_SUN_OPEN;
        } else if (week.equals("1245")) {
            return MON_TUE_THU_FRI_OPEN;
        } else if (week.equals("1246")) {
            return MON_TUE_THU_STA_OPEN;
        } else if (week.equals("1247")) {
            return MON_TUE_THU_SUN_OPEN;
        } else if (week.equals("1256")) {
            return MON_TUE_FRI_STA_OPEN;
        } else if (week.equals("1257")) {
            return MON_TUE_FRI_SUN_OPEN;
        } else if (week.equals("1267")) {
            return MON_TUE_STA_SUN_OPEN;
        } else if (week.equals("1345")) {
            return MON_WED_THU_FRI_OPEN;
        } else if (week.equals("1346")) {
            return MON_WED_THU_STA_OPEN;
        } else if (week.equals("1347")) {
            return MON_WED_THU_SUN_OPEN;
        } else if (week.equals("1356")) {
            return MON_WED_FRI_STA_OPEN;
        } else if (week.equals("1357")) {
            return MON_WED_FRI_SUN_OPEN;
        } else if (week.equals("1367")) {
            return MON_WED_STA_SUN_OPEN;
        } else if (week.equals("1456")) {
            return MON_THU_FRI_STA_OPEN;
        } else if (week.equals("1457")) {
            return MON_THU_FRI_SUN_OPEN;
        } else if (week.equals("1467")) {
            return MON_THU_STA_SUN_OPEN;
        } else if (week.equals("1567")) {
            return MON_FRI_STA_SUN_OPEN;
        } else if (week.equals("2345")) {
            return TUE_WED_THU_FRI_OPEN;
        } else if (week.equals("2346")) {
            return TUE_WED_THU_STA_OPEN;
        } else if (week.equals("2347")) {
            return TUE_WED_THU_SUN_OPEN;
        } else if (week.equals("2356")) {
            return TUE_WED_FRI_STA_OPEN;
        } else if (week.equals("2357")) {
            return TUE_WED_FRI_SUN_OPEN;
        } else if (week.equals("2367")) {
            return TUE_WED_STA_SUN_OPEN;
        } else if (week.equals("2456")) {
            return TUE_THU_FRI_STA_OPEN;
        } else if (week.equals("2457")) {
            return TUE_THU_FRI_SUN_OPEN;
        } else if (week.equals("2467")) {
            return TUE_THU_STA_SUN_OPEN;
        } else if (week.equals("2567")) {
            return TUE_FRI_STA_SUN_OPEN;
        } else if (week.equals("3456")) {
            return WED_THU_FRI_STA_OPEN;
        } else if (week.equals("3457")) {
            return WED_THU_FRI_SUN_OPEN;
        } else if (week.equals("3467")) {
            return WED_THU_STA_SUN_OPEN;
        } else if (week.equals("3567")) {
            return WED_FRI_STA_SUN_OPEN;
        } else if (week.equals("4567")) {
            return THU_FRI_STA_SUN_OPEN;
        }
        return null;
    }

    public static byte[] FiveDays(String week, byte hh, byte mm, byte ss) {
        byte[] MON_TUE_WED_THU_FRI_OPEN = {(byte) 0xff, 0x06, 0x11, 0x1F, hh, mm, ss, 0x0A, (byte) 0xfe};//周一、二、三、四、五开启
        byte[] MON_TUE_WED_THU_STA_OPEN = {(byte) 0xff, 0x06, 0x11, 0x2F, hh, mm, ss, 0x0A, (byte) 0xfe};//周一、二、三、四、六开启
        byte[] MON_TUE_WED_THU_SUN_OPEN = {(byte) 0xff, 0x06, 0x11, 0x4F, hh, mm, ss, 0x0A, (byte) 0xfe};//周一、二、三、四、日开启

        byte[] MON_TUE_WED_FRI_STA_OPEN = {(byte) 0xff, 0x06, 0x11, 0x37, hh, mm, ss, 0x0A, (byte) 0xfe};//周一、二、三、五、六开启
        byte[] MON_TUE_WED_FRI_SUN_OPEN = {(byte) 0xff, 0x06, 0x11, 0x57, hh, mm, ss, 0x0A, (byte) 0xfe};//周一、二、三、五、日开启
        byte[] MON_TUE_WED_STA_SUN_OPEN = {(byte) 0xff, 0x06, 0x11, 0x67, hh, mm, ss, 0x0A, (byte) 0xfe};//周一、二、三、六、日开启

        byte[] MON_TUE_THU_FRI_STA_OPEN = {(byte) 0xff, 0x06, 0x11, 0x3B, hh, mm, ss, 0x0A, (byte) 0xfe};//周一、二、四、五、六开启
        byte[] MON_TUE_THU_FRI_SUN_OPEN = {(byte) 0xff, 0x06, 0x11, 0x5B, hh, mm, ss, 0x0A, (byte) 0xfe};//周一、二、四、五、日开启
        byte[] MON_TUE_THU_STA_SUN_OPEN = {(byte) 0xff, 0x06, 0x11, 0x6B, hh, mm, ss, 0x0A, (byte) 0xfe};//周一、二、四、六、日开启
        byte[] MON_TUE_FRI_STA_SUN_OPEN = {(byte) 0xff, 0x06, 0x11, 0x73, hh, mm, ss, 0x0A, (byte) 0xfe};//周一、二、五、六、日开启

        byte[] MON_WED_THU_FRI_STA_OPEN = {(byte) 0xff, 0x06, 0x11, 0x3D, hh, mm, ss, 0x0A, (byte) 0xfe};//周一、三、四、五、六开启
        byte[] MON_WED_THU_FRI_SUN_OPEN = {(byte) 0xff, 0x06, 0x11, 0x5D, hh, mm, ss, 0x0A, (byte) 0xfe};//周一、三、四、五、日开启
        byte[] MON_WED_THU_STA_SUN_OPEN = {(byte) 0xff, 0x06, 0x11, 0x6D, hh, mm, ss, 0x0A, (byte) 0xfe};//周一、三、四、六、日开启
        byte[] MON_WED_FRI_STA_SUN_OPEN = {(byte) 0xff, 0x06, 0x11, 0x75, hh, mm, ss, 0x0A, (byte) 0xfe};//周一、三、五、六、日开启
        byte[] MON_THU_FRI_STA_SUN_OPEN = {(byte) 0xff, 0x06, 0x11, 0x79, hh, mm, ss, 0x0A, (byte) 0xfe};//周一、四、五、六、日开启

        byte[] TUE_WED_THU_FRI_STA_OPEN = {(byte) 0xff, 0x06, 0x11, 0x3E, hh, mm, ss, 0x0A, (byte) 0xfe};//周二、三、四、五、六开启
        byte[] TUE_WED_THU_FRI_SUN_OPEN = {(byte) 0xff, 0x06, 0x11, 0x5E, hh, mm, ss, 0x0A, (byte) 0xfe};//周二、三、四、五、日开启

        byte[] TUE_WED_THU_STA_SUN_OPEN = {(byte) 0xff, 0x06, 0x11, 0x6E, hh, mm, ss, 0x0A, (byte) 0xfe};//周二、三、四、六、日开启
        byte[] TUE_WED_FRI_STA_SUN_OPEN = {(byte) 0xff, 0x06, 0x11, 0x76, hh, mm, ss, 0x0A, (byte) 0xfe};//周二、三、五、六、日开启
        byte[] TUE_THU_FRI_STA_SUN_OPEN = {(byte) 0xff, 0x06, 0x11, 0x7A, hh, mm, ss, 0x0A, (byte) 0xfe};//周二、四、五、六、日开启
        byte[] WED_THU_FRI_STA_SUN_OPEN = {(byte) 0xff, 0x06, 0x11, 0x7C, hh, mm, ss, 0x0A, (byte) 0xfe};//周三、四、五、六、日开启
        if (week.equals("12345")) {
            return MON_TUE_WED_THU_FRI_OPEN;
        } else if (week.equals("12346")) {
            return MON_TUE_WED_THU_STA_OPEN;
        } else if (week.equals("12347")) {
            return MON_TUE_WED_THU_SUN_OPEN;
        } else if (week.equals("12356")) {
            return MON_TUE_WED_FRI_STA_OPEN;
        } else if (week.equals("12357")) {
            return MON_TUE_WED_FRI_SUN_OPEN;
        } else if (week.equals("12367")) {
            return MON_TUE_WED_STA_SUN_OPEN;
        } else if (week.equals("12456")) {
            return MON_TUE_THU_FRI_STA_OPEN;
        } else if (week.equals("12457")) {
            return MON_TUE_THU_FRI_SUN_OPEN;
        } else if (week.equals("12467")) {
            return MON_TUE_THU_STA_SUN_OPEN;
        } else if (week.equals("12567")) {
            return MON_TUE_FRI_STA_SUN_OPEN;
        } else if (week.equals("13456")) {
            return MON_WED_THU_FRI_STA_OPEN;
        } else if (week.equals("13457")) {
            return MON_WED_THU_FRI_SUN_OPEN;
        } else if (week.equals("13467")) {
            return MON_WED_THU_STA_SUN_OPEN;
        } else if (week.equals("13567")) {
            return MON_WED_FRI_STA_SUN_OPEN;
        } else if (week.equals("14567")) {
            return MON_THU_FRI_STA_SUN_OPEN;
        } else if (week.equals("23456")) {
            return TUE_WED_THU_FRI_STA_OPEN;
        } else if (week.equals("23457")) {
            return TUE_WED_THU_FRI_SUN_OPEN;
        } else if (week.equals("23467")) {
            return TUE_WED_THU_STA_SUN_OPEN;
        } else if (week.equals("23567")) {
            return TUE_WED_FRI_STA_SUN_OPEN;
        } else if (week.equals("24567")) {
            return TUE_THU_FRI_STA_SUN_OPEN;
        } else if (week.equals("34567")) {
            return WED_THU_FRI_STA_SUN_OPEN;
        }
        return null;

    }

    public static byte[] SixDays(String week, byte hh, byte mm, byte ss) {
        byte[] MON_TUE_WED_THU_FRI_STA_OPEN = {(byte) 0xff, 0x06, 0x11, 0x3F, hh, mm, ss, 0x0A, (byte) 0xfe};//周一、二、三，四、五、六开启
        byte[] MON_TUE_WED_THU_FRI_SUN_OPEN = {(byte) 0xff, 0x06, 0x11, 0x5F, hh, mm, ss, 0x0A, (byte) 0xfe};//周一、二、三、四、五、日开启
        byte[] MON_TUE_WED_THU_STA_SUN_OPEN = {(byte) 0xff, 0x06, 0x11, 0x6F, hh, mm, ss, 0x0A, (byte) 0xfe};//周一、二、三，四、六、日开启
        byte[] MON_TUE_WED_FRI_STA_SUN_OPEN = {(byte) 0xff, 0x06, 0x11, 0x77, hh, mm, ss, 0x0A, (byte) 0xfe};//周一、二、三、五、六、日开启
        byte[] MON_TUE_THU_FRI_STA_SUN_OPEN = {(byte) 0xff, 0x06, 0x11, 0x7B, hh, mm, ss, 0x0A, (byte) 0xfe};//周一、二、四、五、六、日开启
        byte[] MON_WED_THU_FRI_STA_SUN_OPEN = {(byte) 0xff, 0x06, 0x11, 0x7D, hh, mm, ss, 0x0A, (byte) 0xfe};//周一、三、四、五、六、日开启

        byte[] TUE_WED_THU_FRI_STA_SUN_OPEN = {(byte) 0xff, 0x06, 0x11, 0x7E, hh, mm, ss, 0x0A, (byte) 0xfe};//周二、三、四、五、六、日开启
        if (week.equals("123456")) {
            return MON_TUE_WED_THU_FRI_STA_OPEN;
        } else if (week.equals("123457")) {
            return MON_TUE_WED_THU_FRI_SUN_OPEN;
        } else if (week.equals("123467")) {
            return MON_TUE_WED_THU_STA_SUN_OPEN;
        } else if (week.equals("123567")) {
            return MON_TUE_WED_FRI_STA_SUN_OPEN;
        } else if (week.equals("124567")) {
            return MON_TUE_THU_FRI_STA_SUN_OPEN;
        } else if (week.equals("134567")) {
            return MON_WED_THU_FRI_STA_SUN_OPEN;
        } else if (week.equals("234567")) {
            return TUE_WED_THU_FRI_STA_SUN_OPEN;
        }
        return null;
    }

    public static byte[] EveryDay(String week, byte hh, byte mm, byte ss) {
        byte[] EVERYDAY_OPEN = {(byte) 0xff, 0x06, 0x11, 0x7F, hh, mm, ss, 0x0A, (byte) 0xfe};//每天开启
        return EVERYDAY_OPEN;
    }
}
