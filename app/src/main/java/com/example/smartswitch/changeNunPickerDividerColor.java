package com.example.smartswitch;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.NumberPicker;

import java.lang.reflect.Field;

/**
 * 设置numberpicker 中间分割线颜色
 */
public class changeNunPickerDividerColor {
    /**
     * 设置numberpicker分割线颜色
     * color 为空默认为蓝色
     *
     * @param numberPicker
     * @param color        颜色  “#000000”
     */
    @SuppressLint("NewApi")
    public static void setNumberPickerDividerColor(NumberPicker numberPicker, String color) {
        if (color == null || color.equals("")) {
            color = "#0000FF";
        }
        NumberPicker picker = numberPicker;
        Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    //设置分割线的颜色值
                    pf.set(picker, new ColorDrawable(Color.parseColor(color)));
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}
