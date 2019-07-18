package com.example.smartswitch_wifi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.UUID;

public class timer_close extends AppCompatActivity implements NumberPicker.OnValueChangeListener, View.OnClickListener {
    public final UUID MY_UUID = UUID
            .fromString("00001101-0000-1000-8000-00805F9B34FB");
    public byte[] LED_STATE_TIMER_CLOSE = {(byte) 0xff, 0x02, 0x00, 0x0A, (byte) 0xfe};
    public byte HH = 00, MM = 00, SS = 00;
    TextView tvlength, tvclosetime, tvstartTime, tvendTime, houguandeng;
    Button open;
    private CountDownTimer countDownTimer;
    private long value, pauseTime = 0, startlong = 0, endlong = 0;
    private changeNunPickerFontSizeAndColor NumberPickerHour, NumberPickerMinute, NumberPickerSecond;
    private Button endTime;
    private Switch z1, z2, z3, z4, z5, z6, z7;
    private CheckBox ck7, ckr;
    private String Hour = "00", Minute = "00", Second = "00", date = "000000", status = "0", openlong = "未设置";
    private String op1 = "0", op2 = "0", op3 = "0", op4 = "0", op5 = "0", op6 = "0", op7 = "0", ops = "00000000";
    private int startH = 0, startM = 0, startS = 0;
    private String weeks, nowtime;

    public static String byte2HexStr(byte[] b) {
        String stmp = "";
        StringBuilder sb = new StringBuilder("");
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0xFF);
            sb.append((stmp.length() == 1) ? "0" + stmp : stmp);
            sb.append(" ");
        }
        return sb.toString().toUpperCase().trim();
    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timerclose);
        z1 = findViewById(R.id.z1);
        z2 = findViewById(R.id.z2);
        z3 = findViewById(R.id.z3);
        z4 = findViewById(R.id.z4);
        z5 = findViewById(R.id.z5);
        z6 = findViewById(R.id.z6);
        z7 = findViewById(R.id.z7);
        ck7 = findViewById(R.id.ck7);
        ckr = findViewById(R.id.ckr);
        NumberPickerHour = findViewById(R.id.numberPickerHour);
        NumberPickerMinute = findViewById(R.id.numberPickerMinute);
        NumberPickerSecond = findViewById(R.id.numberPickerSecond);
        tvlength = findViewById(R.id.tvlength);
        open = findViewById(R.id.open);
        tvclosetime = findViewById(R.id.tvclosetime);
        tvstartTime = findViewById(R.id.tvstartTime);
        tvendTime = findViewById(R.id.tvendTime);
        houguandeng = findViewById(R.id.houguandeng);

        String time = dateToString.nowdateToString();
        NumberPickerHour.setMinValue(00);
        NumberPickerHour.setMaxValue(24);
        NumberPickerHour.setValue(Integer.parseInt(time.substring(0, 2)));

        NumberPickerMinute.setMinValue(00);
        NumberPickerMinute.setMaxValue(60);
        NumberPickerMinute.setValue(Integer.parseInt(time.substring(2, 4)));

        NumberPickerSecond.setMinValue(00);
        NumberPickerSecond.setMaxValue(60);
        NumberPickerSecond.setValue(Integer.parseInt(time.substring(4, 6)));

        NumberPickerHour.setOnValueChangedListener(this);
        NumberPickerMinute.setOnValueChangedListener(this);
        NumberPickerSecond.setOnValueChangedListener(this);

        changeNunPickerDividerColor.setNumberPickerDividerColor(NumberPickerHour, "#00000000");
        changeNunPickerDividerColor.setNumberPickerDividerColor(NumberPickerMinute, "#00000000");
        changeNunPickerDividerColor.setNumberPickerDividerColor(NumberPickerSecond, "#00000000");

        ck7.setOnClickListener(this);
        ckr.setOnClickListener(this);

        tvlength.setText(" ");
        houguandeng.setVisibility(View.INVISIBLE);

        z1.setVisibility(View.INVISIBLE);
        z2.setVisibility(View.INVISIBLE);
        z3.setVisibility(View.INVISIBLE);
        z4.setVisibility(View.INVISIBLE);
        z5.setVisibility(View.INVISIBLE);
        z6.setVisibility(View.INVISIBLE);
        z7.setVisibility(View.INVISIBLE);
        nowtime = dateToString.nowdateToString4();
        Log.d("aa", "nowtime  赋值 dateToString.nowdateToString4()    " + nowtime);
        open.setEnabled(false);
        initweek();
    }

    private void checkweek() {
        weeks = dateToString.dateToWeek(dateToString.nowdateToString2());
        if (weeks.equals("星期一")) {
            z1.setChecked(true);
            z2.setChecked(false);
            z3.setChecked(false);
            z4.setChecked(false);
            z5.setChecked(false);
            z6.setChecked(false);
            z7.setChecked(false);
        } else if (weeks.equals("星期二")) {
            z1.setChecked(false);
            z2.setChecked(true);
            z3.setChecked(false);
            z4.setChecked(false);
            z5.setChecked(false);
            z6.setChecked(false);
            z7.setChecked(false);
        } else if (weeks.equals("星期三")) {
            z1.setChecked(false);
            z2.setChecked(false);
            z3.setChecked(true);
            z4.setChecked(false);
            z5.setChecked(false);
            z6.setChecked(false);
            z7.setChecked(false);
        } else if (weeks.equals("星期四")) {
            z1.setChecked(false);
            z2.setChecked(false);
            z3.setChecked(false);
            z4.setChecked(true);
            z5.setChecked(false);
            z6.setChecked(false);
            z7.setChecked(false);
        } else if (weeks.equals("星期五")) {
            z1.setChecked(false);
            z2.setChecked(false);
            z3.setChecked(false);
            z4.setChecked(false);
            z5.setChecked(true);
            z6.setChecked(false);
            z7.setChecked(false);
        } else if (weeks.equals("星期六")) {
            z1.setChecked(false);
            z2.setChecked(false);
            z3.setChecked(false);
            z4.setChecked(false);
            z5.setChecked(false);
            z6.setChecked(true);
            z7.setChecked(false);
        } else if (weeks.equals("星期日")) {
            z1.setChecked(false);
            z2.setChecked(false);
            z3.setChecked(false);
            z4.setChecked(false);
            z5.setChecked(false);
            z6.setChecked(false);
            z7.setChecked(true);
        }


    }

    private void initweek() {
        /*
         *
         * 生成周几 8位字符串
         *
         * */
        z1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    op1 = "1";
                    ops = "0" + op1 + op2 + op3 + op4 + op5 + op6 + op7;
                    Log.d("bb", "重复时间  " + ops);
                    checkNowTime("星期一");
                } else {
                    op1 = "0";
                    ops = "0" + op1 + op2 + op3 + op4 + op5 + op6 + op7;
                    Log.d("bb", "重复时间  " + ops);
                    Toast.makeText(getApplicationContext(), "取消选择周一  " + op1 + "重复值   " + ops, Toast.LENGTH_SHORT).show();
                }
            }


        });

        z2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    op2 = "1";
                    ops = "0" + op1 + op2 + op3 + op4 + op5 + op6 + op7;
                    Log.d("bb", "重复时间  " + ops);
                    checkNowTime("星期二");
                } else {
                    op2 = "0";
                    ops = "0" + op1 + op2 + op3 + op4 + op5 + op6 + op7;
                    Log.d("bb", "重复时间  " + ops);
                    Toast.makeText(getApplicationContext(), "取消选择周二  " + op2 + "重复值   " + ops, Toast.LENGTH_SHORT).show();
                }
            }
        });
        z3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    op3 = "1";
                    ops = "0" + op1 + op2 + op3 + op4 + op5 + op6 + op7;
                    Log.d("bb", "重复时间  " + ops);
                    checkNowTime("星期三");
                } else {
                    op3 = "0";
                    ops = "0" + op1 + op2 + op3 + op4 + op5 + op6 + op7;
                    Log.d("bb", "重复时间  " + ops);
                    Toast.makeText(getApplicationContext(), "取消选择周三  " + op3 + "重复值   " + ops, Toast.LENGTH_SHORT).show();
                }
            }
        });
        z4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    op4 = "1";
                    ops = "0" + op1 + op2 + op3 + op4 + op5 + op6 + op7;
                    Log.d("bb", "重复时间  " + ops);
                    checkNowTime("星期四");
                } else {
                    op4 = "0";
                    ops = "0" + op1 + op2 + op3 + op4 + op5 + op6 + op7;
                    Log.d("bb", "重复时间  " + ops);
                }
            }
        });
        z5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    op5 = "1";
                    ops = "0" + op1 + op2 + op3 + op4 + op5 + op6 + op7;
                    Log.d("bb", "重复时间  " + ops);
                    checkNowTime("星期五");
                } else {
                    op5 = "0";
                    ops = "0" + op1 + op2 + op3 + op4 + op5 + op6 + op7;
                    Log.d("bb", "重复时间  " + ops);
                    Toast.makeText(getApplicationContext(), "取消选择周五  " + op5 + "重复值   " + ops, Toast.LENGTH_SHORT).show();
                }
            }
        });
        z6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    op6 = "1";
                    ops = "0" + op1 + op2 + op3 + op4 + op5 + op6 + op7;
                    Log.d("bb", "重复时间  " + ops);
                    checkNowTime("星期六");
                } else {
                    op6 = "0";
                    ops = "0" + op1 + op2 + op3 + op4 + op5 + op6 + op7;
                    Log.d("bb", "重复时间  " + ops);
                    Toast.makeText(getApplicationContext(), "取消选择周六  " + op6 + "重复值   " + ops, Toast.LENGTH_SHORT).show();
                }
            }
        });
        z7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    op7 = "1";
                    ops = "0" + op1 + op2 + op3 + op4 + op5 + op6 + op7;
                    Log.d("bb", "重复时间  " + ops);
                    checkNowTime("星期日");
                } else {
                    op7 = "0";
                    ops = "0" + op1 + op2 + op3 + op4 + op5 + op6 + op7;
                    Log.d("bb", "重复时间  " + ops);
                    Toast.makeText(getApplicationContext(), "取消选择周日  " + op7 + "重复值   " + ops, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onDestroy() {
        super.onDestroy();
        date = "000000";
        status = "0";
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        switch (picker.getId()) {
            case R.id.numberPickerHour:
                Hour = String.valueOf(String.format("%02d", newVal));
                NumberPickerMinute.setValue(NumberPickerMinute.getValue());
                NumberPickerSecond.setValue(NumberPickerSecond.getValue());
                Minute = String.format("%02d", NumberPickerMinute.getValue());
                Second = String.format("%02d", NumberPickerSecond.getValue());
                //checkweek2();
                break;
            case R.id.numberPickerMinute:
                Minute = String.valueOf(String.format("%02d", newVal));
                NumberPickerHour.setValue(NumberPickerHour.getValue());
                NumberPickerSecond.setValue(NumberPickerSecond.getValue());
                Hour = String.format("%02d", NumberPickerHour.getValue());
                Second = String.format("%02d", NumberPickerSecond.getValue());
                //checkweek2();
                break;
            case R.id.numberPickerSecond:
                Second = String.valueOf(String.format("%02d", newVal));
                NumberPickerHour.setValue(NumberPickerHour.getValue());
                NumberPickerMinute.setValue(NumberPickerMinute.getValue());
                Hour = String.format("%02d", NumberPickerHour.getValue());
                Minute = String.format("%02d", NumberPickerMinute.getValue());
                //checkweek2();
                break;
            default:
                break;
        }
        date = Hour + Minute + Second;
        nowtime = Hour + ":" + Minute + ":" + Second;
        Log.d("aa", "nowtime  onvalue   " + nowtime);

        setTitle("关闭时间:  " + Hour + " 时 " + Minute + " 分 " + Second + " 秒");

    }

    public void open(View view) {


        if (!ck7.isChecked() && !ckr.isChecked() || date.equals("000000")) {
            Toast.makeText(this, "请选择时长",
                    Toast.LENGTH_SHORT).show();

        } else {
            //普通开始
            Log.d("aa", "已执行");
            ChooseDayClose();
            sendOrder();


        }

    }

///////////////////////////////////////////////////
    ///////////生成周几//////////////////////////////
    ///////////////////////////////////////////////////

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ck7: {

                z1.setVisibility(View.VISIBLE);
                z2.setVisibility(View.VISIBLE);
                z3.setVisibility(View.VISIBLE);
                z4.setVisibility(View.VISIBLE);
                z5.setVisibility(View.VISIBLE);
                z6.setVisibility(View.VISIBLE);
                z7.setVisibility(View.VISIBLE);
                z1.setChecked(true);
                z2.setChecked(true);
                z3.setChecked(true);
                z4.setChecked(true);
                z5.setChecked(true);
                z6.setChecked(true);
                z7.setChecked(true);
                ckr.setChecked(false);
                z1.setEnabled(false);
                z2.setEnabled(false);
                z3.setEnabled(false);
                z4.setEnabled(false);
                z5.setEnabled(false);
                z6.setEnabled(false);
                z7.setEnabled(false);
                open.setEnabled(true);
                ops = "01111111";
                Log.d("bb", "重复时间  " + ops);
                Toast.makeText(this, "重复时间  " + ops, Toast.LENGTH_LONG).show();
            }
            break;
            case R.id.ckr: {//自定义
                Log.d("bb", "ck7");
                z1.setVisibility(View.VISIBLE);
                z2.setVisibility(View.VISIBLE);
                z3.setVisibility(View.VISIBLE);
                z4.setVisibility(View.VISIBLE);
                z5.setVisibility(View.VISIBLE);
                z6.setVisibility(View.VISIBLE);
                z7.setVisibility(View.VISIBLE);
                ck7.setChecked(false);
                checkweek();

                z1.setEnabled(true);
                z2.setEnabled(true);
                z3.setEnabled(true);
                z4.setEnabled(true);
                z5.setEnabled(true);
                z6.setEnabled(true);
                z7.setEnabled(true);
                //open.setEnabled(true);

                ops = "0" + op1 + op2 + op3 + op4 + op5 + op6 + op7;
                Log.d("bb", "重复时间  " + ops);
                Toast.makeText(this, "重复时间  " + ops, Toast.LENGTH_LONG).show();
            }
            break;
        }
    }
    ///////////////////////////////////////////////////
    ///////////数据格式转换//////////////////////////////
    ///////////////////////////////////////////////////

    public String initweek(String ops) {

        String week = "";
        for (int i = 0; i < ops.length(); i++) {
            if (!ops.substring(i, i + 1).equals("0")) {
                week = week + String.valueOf(i);
            }
            Log.d("aa", ops.substring(i, i + 1));
        }
        Log.d("aa", "week    " + week);
        return week;
    }

    public int judgedays(String ops) {

        int num = 0;
        for (int i = 0; i < ops.length(); i++) {
            if (!ops.substring(i, i + 1).equals("0")) {
                num++;
            }
            Log.d("aa", ops.substring(i, i + 1));
        }
        Log.d("aa", "days    " + num);
        return num;
    }

    public void ChooseDayClose() {
//        HH=hexStringToBytes(Hour)[0];
//        MM=hexStringToBytes(Minute)[0];
//        SS=hexStringToBytes(Second)[0];
        HH = (byte) (Integer.parseInt(Hour));
        MM = (byte) (Integer.parseInt(Minute));
        SS = (byte) (Integer.parseInt(Second));

        switch (judgedays(ops)) {
            case 1:
                LED_STATE_TIMER_CLOSE = TimeClose.OneDayClose(initweek(ops), HH, MM, SS);
            case 2:
                LED_STATE_TIMER_CLOSE = TimeClose.TwoDaysClose(initweek(ops), HH, MM, SS);
            case 3:
                LED_STATE_TIMER_CLOSE = TimeClose.ThreeDaysClose(initweek(ops), HH, MM, SS);
            case 4:
                LED_STATE_TIMER_CLOSE = TimeClose.FourDays(initweek(ops), HH, MM, SS);
            case 5:
                LED_STATE_TIMER_CLOSE = TimeClose.FiveDays(initweek(ops), HH, MM, SS);
            case 6:
                LED_STATE_TIMER_CLOSE = TimeClose.SixDays(initweek(ops), HH, MM, SS);
            case 7:
                LED_STATE_TIMER_CLOSE = TimeClose.EveryDay(initweek(ops), HH, MM, SS);
        }
    }

    ///////////////////////////////////////////////////
    ///////////回传指令//////////////////////////////
    ///////////////////////////////////////////////////
    public void sendOrder() {
        Intent intent = new Intent();
        intent.putExtra("LED_STATE_TIMER_CLOSE", LED_STATE_TIMER_CLOSE);
        setResult(2, intent);
        timer_close.this.finish();
    }

    ///////////////////////////////////////////////////
    ///////////检测时间是否合法//////////////////////////////
    ///////////////////////////////////////////////////
    public void checkNowTime(String week) {
        if (dateToString.dateToWeek(dateToString.nowdateToString2()).equals(week) && !dateToString.compareDate(dateToString.nowdateToString4(), nowtime)) {
            open.setEnabled(false);
            Toast.makeText(getApplicationContext(), "请修改时间至  " + dateToString.nowdateToString3() + "  之后", Toast.LENGTH_SHORT).show();
        } else open.setEnabled(true);
    }
    ///////////////////////////////////////////////////
    ///////////数据格式转换//////////////////////////////
    ///////////////////////////////////////////////////

    private void checkweek2() {

        z1.setChecked(false);
        z2.setChecked(false);
        z3.setChecked(false);
        z4.setChecked(false);
        z5.setChecked(false);
        z6.setChecked(false);
        z7.setChecked(false);
        checkweek();
    }

    /**
     * Convert hex string to byte[]
     *
     * @param hexString the hex string
     * @return byte[]
     */
    public byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    /**
     * Convert char to byte
     *
     * @param c char
     * @return byte
     */
    public byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
}
