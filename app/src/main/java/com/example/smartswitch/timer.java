package com.example.smartswitch;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;

public class timer extends AppCompatActivity implements NumberPicker.OnValueChangeListener, View.OnClickListener {
    private CountDownTimer countDownTimer;
    private long value,pauseTime=0,startlong=0,endlong=0;
    private changeNunPickerFontSizeAndColor NumberPickerHour,NumberPickerMinute,NumberPickerSecond;
    private  Button startTime,endTime;
    private Switch z1,z2,z3,z4,z5,z6,z7;
    private CheckBox ck1,ck7,ckr;
    private  String Hour="00",Minute="00",Second="00",length="00时00分00秒",date="000000",status="0",openlong="未设置";
    TextView tvlength,tvtiming,tvclosetime,tvstartTime,tvendTime,houguandeng;
    Button open,pause,close;
    private  String op1="0",op2="0",op3="0",op4="0",op5="0",op6="0",op7="0",ops="00000000";
    private  int startH=0,startM=0,startS=0;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer);
        z1=findViewById(R.id.z1);
        z2=findViewById(R.id.z2);
        z3=findViewById(R.id.z3);
        z4=findViewById(R.id.z4);
        z5=findViewById(R.id.z5);
        z6=findViewById(R.id.z6);
        z7=findViewById(R.id.z7);
        ck1=findViewById(R.id.ck1);
        ck7=findViewById(R.id.ck7);
        ckr=findViewById(R.id.ckr);
        NumberPickerHour=findViewById(R.id.numberPickerHour);
        NumberPickerMinute= findViewById(R.id.numberPickerMinute);
        NumberPickerSecond= findViewById(R.id.numberPickerSecond);
        tvlength= findViewById(R.id.tvlength);
        tvtiming= findViewById(R.id.tvtiming);
        open= findViewById(R.id.open);
        close= findViewById(R.id.close);
        pause= findViewById(R.id.pause);
        tvclosetime= findViewById(R.id.tvclosetime);
        tvstartTime= findViewById(R.id.tvstartTime);
        tvendTime= findViewById(R.id.tvendTime);
        startTime= findViewById(R.id.startTime);
        endTime= findViewById(R.id.endTime);
        houguandeng= findViewById(R.id.houguandeng);

        pause.setEnabled(false);
        close.setEnabled(false);
        NumberPickerHour.setMinValue(00);
        NumberPickerHour.setMaxValue(24);
        NumberPickerHour.setValue(00);

        NumberPickerMinute.setMinValue(00);
        NumberPickerMinute.setMaxValue(60);

        NumberPickerSecond.setMinValue(00);
        NumberPickerSecond.setMaxValue(60);

        NumberPickerHour.setOnValueChangedListener(this);
        NumberPickerMinute.setOnValueChangedListener(this);
        NumberPickerSecond.setOnValueChangedListener(this);

        changeNunPickerDividerColor.setNumberPickerDividerColor(NumberPickerHour,"#00000000");
        changeNunPickerDividerColor.setNumberPickerDividerColor(NumberPickerMinute,"#00000000");
        changeNunPickerDividerColor.setNumberPickerDividerColor(NumberPickerSecond,"#00000000");

        ck1.setOnClickListener(this);
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

        open.setEnabled(false);
        initTime();
        initweek();
    }

    private void initweek() {
        z1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    op1 = "1";
                    ops="0"+op1+op2+op3+op4+op5+op6+op7;
                    Log.d("bb","重复时间  " +ops);
                    Toast.makeText(getApplicationContext(), "选择周一  " + op1  +"重复值   "+ops, Toast.LENGTH_SHORT).show();
                } else {
                    op1 = "0";
                    ops="0"+op1+op2+op3+op4+op5+op6+op7;
                    Log.d("bb","重复时间  " +ops);
                    Toast.makeText(getApplicationContext(), "取消选择周一  " + op1+"重复值   "+ops, Toast.LENGTH_SHORT).show();
                }
            }
        });

        z2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    op2 = "1";
                    ops="0"+op1+op2+op3+op4+op5+op6+op7;
                    Log.d("bb","重复时间  " +ops);
                    Toast.makeText(getApplicationContext(), "选择周二  " + op2+"重复值   "+ops, Toast.LENGTH_SHORT).show();
                } else {
                    op2 = "0";
                    ops="0"+op1+op2+op3+op4+op5+op6+op7;
                    Log.d("bb","重复时间  " +ops);
                    Toast.makeText(getApplicationContext(), "取消选择周二  " + op2+"重复值   "+ops, Toast.LENGTH_SHORT).show();
                }
            }
        });
        z3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    op3 = "1";
                    ops="0"+op1+op2+op3+op4+op5+op6+op7;
                    Log.d("bb","重复时间  " +ops);
                    Toast.makeText(getApplicationContext(), "选择周三  " + op3+"重复值   "+ops, Toast.LENGTH_SHORT).show();
                } else {
                    op3 = "0";
                    ops="0"+op1+op2+op3+op4+op5+op6+op7;
                    Log.d("bb","重复时间  " +ops);
                    Toast.makeText(getApplicationContext(), "取消选择周三  " + op3+"重复值   "+ops, Toast.LENGTH_SHORT).show();
                }
            }
        });
        z4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    op4 = "1";
                    ops="0"+op1+op2+op3+op4+op5+op6+op7;
                    Log.d("bb","重复时间  " +ops);
                    Toast.makeText(getApplicationContext(), "选择周四  " + op4+"重复值   "+ops, Toast.LENGTH_SHORT).show();
                } else {
                    op4 = "0";
                    ops="0"+op1+op2+op3+op4+op5+op6+op7;
                    Log.d("bb","重复时间  " +ops);
                    Toast.makeText(getApplicationContext(), "取消选择周四  " + op4+"重复值   "+ops, Toast.LENGTH_SHORT).show();
                }
            }
        });
        z5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    op5 = "1";
                    ops="0"+op1+op2+op3+op4+op5+op6+op7;
                    Log.d("bb","重复时间  " +ops);
                    Toast.makeText(getApplicationContext(), "选择周五  " + op5+"重复值   "+ops, Toast.LENGTH_SHORT).show();
                } else {
                    op5 = "0";
                    ops="0"+op1+op2+op3+op4+op5+op6+op7;
                    Log.d("bb","重复时间  " +ops);
                    Toast.makeText(getApplicationContext(), "取消选择周五  " + op5+"重复值   "+ops, Toast.LENGTH_SHORT).show();
                }
            }
        });
        z6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    op6 = "1";
                    ops="0"+op1+op2+op3+op4+op5+op6+op7;
                    Log.d("bb","重复时间  " +ops);
                    Toast.makeText(getApplicationContext(), "选择周六  " + op6+"重复值   "+ops, Toast.LENGTH_SHORT).show();
                } else {
                    op6 = "0";
                    ops="0"+op1+op2+op3+op4+op5+op6+op7;
                    Log.d("bb","重复时间  " +ops);
                    Toast.makeText(getApplicationContext(), "取消选择周六  " + op6+"重复值   "+ops, Toast.LENGTH_SHORT).show();
                }
            }
        });
        z7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    op7 = "1";
                    ops="0"+op1+op2+op3+op4+op5+op6+op7;
                    Log.d("bb","重复时间  " +ops);
                    Toast.makeText(getApplicationContext(), "选择周日  " + op7+"重复值   "+ops, Toast.LENGTH_SHORT).show();
                } else {
                    op7 = "0";
                    ops="0"+op1+op2+op3+op4+op5+op6+op7;
                    Log.d("bb","重复时间  " +ops);
                    Toast.makeText(getApplicationContext(), "取消选择周日  " + op7+"重复值   "+ops, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initTime() {
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               tvstartTime.setText(Hour+" 时 "+Minute+" 分 "+Second+" 秒");
                startH= Integer.parseInt(Hour);
                startM= Integer.parseInt(Minute);
                startS= Integer.parseInt(Second);
                Log.d("bb", "startH   "+startH+"   startM   "+startM+"   startS   "+startS);
               startlong= Long.parseLong(Hour)*3600+Long.parseLong(Minute)*60+Long.parseLong(Second);

            }
        });
        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                endlong= Long.parseLong(Hour)*3600+Long.parseLong(Minute)*60+Long.parseLong(Second);

                if(endlong<=startlong){
                    tvendTime.setText("开灯时间不能大于关灯时间");
                }
                else
                {
                    tvendTime.setText(Hour+" 时 "+Minute+" 分 "+Second+" 秒");
                    try {
                        openlong=Dateadd.adddays2(Hour+" 时 "+Minute+" 分 "+Second+" 秒",-startH , -startM,-startS );
                        tvtiming.setText(openlong);
                        houguandeng.setVisibility(View.VISIBLE);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
            }
        });


    }

    public void onDestroy(){
        super.onDestroy();
        date="000000";
        status="0";
    }
    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        switch (picker.getId()) {
            case R.id.numberPickerHour:
                Hour= String.valueOf(String.format("%02d",newVal));
                break;
            case R.id.numberPickerMinute:
                Minute= String.valueOf(String.format("%02d",newVal));
                break;
            case R.id.numberPickerSecond:
                Second= String.valueOf(String.format("%02d",newVal));
                break;
            default:
                break;
        }
        length=Hour+" 小时 "+Minute+" 分钟 "+Second+" 秒";
        date=Hour+Minute+Second;
        Toast.makeText(this, "定时： " + length,
                Toast.LENGTH_SHORT).show();

    }

    public void open(View view) {


        if (!ck1.isChecked()&&!ck7.isChecked()&&!ckr.isChecked()&&date.equals("000000")&&pauseTime==0&&status.equals("0")
                &&startlong==0&&endlong==0) {
            Toast.makeText(this, "请选择时长",
                    Toast.LENGTH_SHORT).show();

        }
        else if (!date.equals("000000")&&pauseTime!=0&&status.equals("1")) {
            //暂停开始
            Log.d("aa", "已执行");
            countDownTimer = new CountDownTimer(pauseTime*1000, 1) {
                @Override
                public void onTick(long millisUntilFinished) {
                    value = (millisUntilFinished / 1000);
                    tvlength.setText(SecondsTest.secondToTime(value)+" 后关闭");

                }

                @Override
                public void onFinish() {
                    tvlength.setText("已关闭");
                    houguandeng.setVisibility(View.INVISIBLE);
                    tvclosetime.setVisibility(View.INVISIBLE);
                    status="0";
                }
            };
            new Thread() {
                @Override
                public void run() {
                    countDownTimer.start();
                }
            }.start();

            try {
                String newday= Dateadd.adddays(dateToString.nowdateToString(), Integer.parseInt(Hour), Integer.parseInt(Minute),Integer.parseInt( Second));
                tvclosetime.setText("关灯时间: "+newday.substring(0,2)+"时"+newday.substring(6,8)+"分"+newday.substring(12,14)+"秒");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            pause.setEnabled(true);
            open.setEnabled(false);
            tvclosetime.setVisibility(View.VISIBLE);
        }
        else {
            //普通开始
            Log.d("aa", "已执行");
            Log.d("aa", "openlong   "+openlong);
           // date=Hour+Minute+Second;
            date=openlong.substring(0,2)+openlong.substring(5,7)+openlong.substring(10,12);

            countDownTimer = new CountDownTimer(SecondsTest.timeToSecond(date), 1) {
                @Override
                public void onTick(long millisUntilFinished) {
                    value = (millisUntilFinished / 1000);
                    Log.d("aa", value+"暂停时间  value");
                    tvlength.setText(SecondsTest.secondToTime(value)+" 后关闭");
                }

                @Override
                public void onFinish() {
                    tvlength.setText("已关闭");
                    houguandeng.setVisibility(View.INVISIBLE);
                    close.setEnabled(false);
                    pause.setEnabled(false);
                    open.setEnabled(false);
                    tvclosetime.setVisibility(View.INVISIBLE);
                }
            };
            new Thread() {
                @Override
                public void run() {
                    countDownTimer.start();
                }
            }.start();
            pause.setEnabled(true);
            close.setEnabled(true);

            try {
                String newday= Dateadd.adddays(dateToString.nowdateToString(), Integer.parseInt(Hour), Integer.parseInt(Minute),Integer.parseInt( Second));
                tvclosetime.setText("关灯时间: "+newday.substring(0,2)+"时"+newday.substring(6,8)+"分"+newday.substring(12,14)+"秒");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            open.setEnabled(false);
            tvclosetime.setVisibility(View.VISIBLE);
        }
    }

    public void pause(View view) {
        pauseTime=value;
        Log.d("aa", pauseTime+"暂停时间  pauseTime");
        countDownTimer.cancel();
        status="1";
        tvlength.setText(SecondsTest.secondToTime(pauseTime)+" 后关闭");
        pause.setEnabled(false);
        open.setEnabled(true);
    }

    public void close(View view) {
        countDownTimer.cancel();
        tvlength.setText("已关闭");
        houguandeng.setVisibility(View.INVISIBLE);
        pause.setEnabled(false);
        close.setEnabled(false);
        open.setEnabled(true);
        status="0";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ck1:
            {
                z1.setVisibility(View.INVISIBLE);
                z2.setVisibility(View.INVISIBLE);
                z3.setVisibility(View.INVISIBLE);
                z4.setVisibility(View.INVISIBLE);
                z5.setVisibility(View.INVISIBLE);
                z6.setVisibility(View.INVISIBLE);
                z7.setVisibility(View.INVISIBLE);
                ck7.setChecked(false);
                ckr.setChecked(false);
                z1.setChecked(false);
                z2.setChecked(false);
                z3.setChecked(false);
                z4.setChecked(false);
                z5.setChecked(false);
                z6.setChecked(false);
                z7.setChecked(false);

                z1.setEnabled(true);
                z2.setEnabled(true);
                z3.setEnabled(true);
                z4.setEnabled(true);
                z5.setEnabled(true);
                z6.setEnabled(true);
                z7.setEnabled(true);
                open.setEnabled(true);
            }
                break;
            case R.id.ck7: {
                Log.d("bb", "ck7");
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
                ck1.setChecked(false);
                ckr.setChecked(false);
                z1.setEnabled(false);
                z2.setEnabled(false);
                z3.setEnabled(false);
                z4.setEnabled(false);
                z5.setEnabled(false);
                z6.setEnabled(false);
                z7.setEnabled(false);
                open.setEnabled(true);
                ops="01111111";
                Log.d("bb","重复时间  " +ops);
                Toast.makeText(this,"重复时间  " +ops, Toast.LENGTH_LONG);
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
                ck1.setChecked(false);
                ck7.setChecked(false);
                z1.setChecked(false);
                z2.setChecked(false);
                z3.setChecked(false);
                z4.setChecked(false);
                z5.setChecked(false);
                z6.setChecked(false);
                z7.setChecked(false);

                z1.setEnabled(true);
                z2.setEnabled(true);
                z3.setEnabled(true);
                z4.setEnabled(true);
                z5.setEnabled(true);
                z6.setEnabled(true);
                z7.setEnabled(true);
                open.setEnabled(true);

                ops="0"+op1+op2+op3+op4+op5+op6+op7;
                Log.d("bb","重复时间  " +ops);
                Toast.makeText(this,"重复时间  " +ops, Toast.LENGTH_LONG);
            }
            break;
    }}
}
