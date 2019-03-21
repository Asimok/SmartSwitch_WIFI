package com.example.smartswitch;

public class NoTimer {
    public static byte[] CLOSE = {(byte) 0xff, 0x02, 0x01, 0x0A, (byte) 0xfe};//立即关闭
    public static byte[] OPEN = {(byte) 0xff, 0x02, 0x00, 0x0A, (byte) 0xfe};//立即开启
    public static byte[] Time = {(byte) 0xff, 0x09, 0x30, 2019 / 256, (byte) (2019 % 256), 2, 20, 4, 10, 33, 00, 0x0A, (byte) 0xfe};
    byte[] b1 = {0x30, 2019 / 256, (byte) (2019 % 256), 2, 20, 10, 13, 00, 0x0A};
}
