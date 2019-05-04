package com.example.smartswitch;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Set;
import java.util.TimerTask;
import java.util.UUID;

import static java.lang.Math.abs;
import static java.lang.Math.pow;

public class BluetoothClientActivity_mytimer_auto extends Activity implements OnItemClickListener {
    // UUID，蓝牙建立链接需要的
    public final UUID MY_UUID = UUID
            .fromString("00001101-0000-1000-8000-00805F9B34FB");
    // 为其链接创建一个名称
    public final String NAME = "Bluetooth_Socket";
    // 获取到蓝牙适配器
    public BluetoothAdapter mBluetoothAdapter;
    // 用来保存搜索到的设备信息
    public List<String> bluetoothDevices = new ArrayList<String>();
    // ListView组件
    public ListView lvDevices;
    // ListView的字符串数组适配器
    public ArrayAdapter<String> arrayAdapter;
    // 选中发送数据的蓝牙设备，全局变量，否则连接在方法执行完就结束了
    public BluetoothDevice selectDevice;
    // 获取到选中设备的客户端串口，全局变量，否则连接在方法执行完就结束了
    public BluetoothSocket clientSocket;
    // 获取到向设备写的输出流，全局变量，否则连接在方法执行完就结束了
    public OutputStream os;
    public InputStream is;
    //定义按钮
    public Button close1 = null;
    public Button open = null;
    public Button close1timer = null;
    public Button opentimer = null;
    public TextView receive1;
    public String LED_STATE_OPEN_Timer = "";
    public String LED_STATE_CLOSE_Timer = "";
    public TextView re_msg, select, opentoclose, countdown;
    public RadioGroup lightnum;
    public RadioButton light1,light2,light3,light4;
    public static byte[] ThisOPEN ={'x'};
    public static byte[] ThisCLOSE ={'x'};
    // 注册广播接收者
    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context arg0, Intent intent) {
            // 获取到广播的action
            String action = intent.getAction();
            // 判断广播是搜索到设备还是搜索完成
            if (action.equals(BluetoothDevice.ACTION_FOUND)) {
                // 找到设备后获取其设备
                BluetoothDevice device = intent
                        .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // 判断这个设备是否是之前已经绑定过了，如果是则不需要添加，在程序初始化的时候已经添加了
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    // 设备没有绑定过，则将其保持到arrayList集合中
                    Log.d("aa", "设备没有绑定过   " + device.getName() + ":"
                            + device.getAddress() + "\n");
                    short rssi = intent.getExtras().getShort(
                            BluetoothDevice.EXTRA_RSSI);
                    int iRssi = abs(rssi);
                    // 将蓝牙信号强度换算为距离
                    double power = (iRssi - 59) / 25.0;
                    String mm = new Formatter().format("%.2f", pow(10, power)).toString();

                    Log.d("aa", "距离    " + mm);


                    bluetoothDevices.add(device.getName() + ":  "
                            + device.getAddress() + "  距离:  " + mm + "m" + "\n");
                    // 更新字符串数组适配器，将内容显示在listView中

                    arrayAdapter.notifyDataSetChanged();

                } else {
                    Log.d("aa", "设备绑定过   " + device.getName() + ":"
                            + device.getAddress() + "\n");
                    short rssi = intent.getExtras().getShort(
                            BluetoothDevice.EXTRA_RSSI);
                    int iRssi = abs(rssi);
                    // 将蓝牙信号强度换算为距离
                    double power = (iRssi - 59) / 25.0;
                    String mm = new Formatter().format("%.2f", pow(10, power)).toString();

                    Log.d("aa", "距离    " + mm);


                    bluetoothDevices.add(device.getName() + ":  "
                            + device.getAddress() + "  距离:  " + mm + "m   已配对" + "\n");
                    // 更新字符串数组适配器，将内容显示在listView中

                    arrayAdapter.notifyDataSetChanged();
                }
            } else if (action
                    .equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)) {

                Toast.makeText(BluetoothClientActivity_mytimer_auto.this, "搜索完成", Toast.LENGTH_SHORT).show();
                Log.d("aa", "搜索完成");
            }

        }

    };
    // 服务端利用线程不断接受客户端信息
    //public  AcceptThread thread;
    String message = "";
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
        }
    };
    TimerTask tasknowtime = new TimerTask() {
        public void run() {
            //每次需要执行的代码放到这里面。
            String nowtime = dateToString.nowdateToString4();
            String week = dateToString.dateToWeek(dateToString.nowdateToString2());
            countdown.setText(week + "  " + nowtime);
            // Log.d("mytime","系统时间   "+week+"  "+nowtime);
        }
    };
    private String address;
    TimerTask taskopen = new TimerTask() {
        public void run() {
            //每次需要执行的代码放到这里面。
            String mowtime = dateToString.nowdateToString4();
            Log.d("mytime", "开灯时间   " + mowtime);
            Log.d("mytime", "传回的开灯时间   " + LED_STATE_OPEN_Timer);
            if (LED_STATE_OPEN_Timer.contains(mowtime)) {

                Log.d("mytime", "到开灯时间了");
                if (address != null) {
                    sendOrder(NoTimer_machinery.OPEN);
                }

            }
        }
    };
    TimerTask taskclose = new TimerTask() {
        public void run() {
            //每次需要执行的代码放到这里面。
            String mowtime = dateToString.nowdateToString4();
            Log.d("mytime", "关灯时间   " + mowtime);
            Log.d("mytime", "传回的关灯时间   " + LED_STATE_CLOSE_Timer);
            if (LED_STATE_CLOSE_Timer.contains(mowtime)) {
                Log.d("mytime", "到关灯时间了");
                if (address != null) {
                    sendOrder(NoTimer_machinery.CLOSE);
                }
            }
        }
    };
    private Handler mHandler;//将mHandler指定轮询的Looper
    private MyTimerTaskopen Taskopen1;
    private MyTimerTaskclose Taskclose1;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("cc", byte2HexStr(NoTimer_machinery.Time));
        getPremession();//获取虚拟定位权限
        setContentView(R.layout.layout_buletooth_seacher_mytimer_auto);
        open = (Button) findViewById(R.id.open);
        close1 = (Button) findViewById(R.id.close);
        opentimer = (Button) findViewById(R.id.opentimer);
        close1timer = (Button) findViewById(R.id.closetimer);
        receive1 = (TextView) findViewById(R.id.receive_text);
        re_msg = (TextView) findViewById(R.id.msg);
        select = (TextView) findViewById(R.id.select);
        opentoclose = (TextView) findViewById(R.id.opentoclose);
        countdown = (TextView) findViewById(R.id.countdown);
        lightnum=findViewById(R.id.num);
        light1=findViewById(R.id.light1);
        light2=findViewById(R.id.light2);
        light3=findViewById(R.id.light3);
        light4=findViewById(R.id.light4);

        lightnum.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                // 获取用户选中的灯号
                String num = "";
                switch (checkedId) {
                    case R.id.light1:
                        num = light1.getText().toString();
                        ThisOPEN=NoTimer_auto.OPEN1;
                        ThisCLOSE=NoTimer_auto.CLOSE1;
                        break;
                    case R.id.light2:
                        num = light2.getText().toString();
                        ThisOPEN=NoTimer_auto.OPEN2;
                        ThisCLOSE=NoTimer_auto.CLOSE2;
                        break;
                    case R.id.light3:
                        num = light3.getText().toString();
                        ThisOPEN=NoTimer_auto.OPEN3;
                        ThisCLOSE=NoTimer_auto.CLOSE3;
                        break;
                    case R.id.light4:
                        num = light4.getText().toString();
                        ThisOPEN=NoTimer_auto.OPEN4;
                        ThisCLOSE=NoTimer_auto.CLOSE4;
                        break;
                    default:break;
                }

                // 消息提示
                Toast.makeText(BluetoothClientActivity_mytimer_auto.this,
                        "选择的设备是：" + num, Toast.LENGTH_SHORT).show();
            }
        });



        java.util.Timer timernowtime = new java.util.Timer(true);
        timernowtime.schedule(tasknowtime, 1000, 1000);
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (select.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "未连接到蓝牙设备！请重新连接！", Toast.LENGTH_SHORT).show();
                } else {
                    receive1.setText("开");
                    if (address != null) {
                        sendOrder(ThisOPEN);

                    }
                }
            }
        });
        close1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (select.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "未连接到蓝牙设备！请重新连接！", Toast.LENGTH_SHORT).show();
                } else {
                    receive1.setText("关");
                    if (address != null) {
                        sendOrder(ThisCLOSE);
                    }
                }
            }
        });


        //TODO 开启定时
        opentimer.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("HandlerLeak")
            @Override
            public void onClick(View view) {
                if (select.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "未连接到蓝牙设备！请重新连接！", Toast.LENGTH_SHORT).show();
                } else if (LED_STATE_OPEN_Timer.equals("")) {
                    Toast.makeText(getApplicationContext(), "请设置开灯时间", Toast.LENGTH_SHORT).show();
                } else if (LED_STATE_CLOSE_Timer.equals("")) {
                    Toast.makeText(getApplicationContext(), "请设置关灯时间", Toast.LENGTH_SHORT).show();
                } else {

                    receive1.setText("启用定时");
//TODO  线程

//定时开
                    java.util.Timer timeropen = new java.util.Timer(true);

                    if (Taskopen1 != null) {
                        Taskopen1.cancel();  //将原任务从队列中移除
                    }
                    Taskopen1 = new MyTimerTaskopen();  // 新建一个任务
                    timeropen.schedule(Taskopen1, 1000, 1000);
//定时关

                    java.util.Timer timerclose = new java.util.Timer(true);
                    if (Taskclose1 != null) {
                        Taskclose1.cancel();  //将原任务从队列中移除
                    }
                    Taskclose1 = new MyTimerTaskclose();  // 新建一个任务
                    timerclose.schedule(Taskclose1, 1000, 1000);

                }
            }
        });

        //TODO 关闭定时
        close1timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (select.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "未连接到蓝牙设备！请重新连接！", Toast.LENGTH_SHORT).show();
                } else {
                    receive1.setText("停用定时");

                    if (Taskclose1 != null) {
                        Taskclose1.cancel();  //将原任务从队列中移除
                    } else
                        Toast.makeText(getApplicationContext(), "关灯定时未生效，无需关闭！", Toast.LENGTH_SHORT).show();
                    if (Taskopen1 != null) {
                        Taskopen1.cancel();  //将原任务从队列中移除
                    } else
                        Toast.makeText(getApplicationContext(), "开灯定时未生效，无需关闭！", Toast.LENGTH_SHORT).show();


                }

            }
        });

        // 获取到蓝牙默认的适配器
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        // 获取到ListView组件
        lvDevices = (ListView) findViewById(R.id.lvDevices);
        // 为listview设置字符换数组适配器
        arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1,
                bluetoothDevices);
        // 为listView绑定适配器
        lvDevices.setAdapter(arrayAdapter);
        // 为listView设置item点击事件侦听
        lvDevices.setOnItemClickListener(this);

        // 用Set集合保持已绑定的设备   将绑定的设备添加到Set集合。
        Set<BluetoothDevice> devices = mBluetoothAdapter.getBondedDevices();

        // 因为蓝牙搜索到设备和完成搜索都是通过广播来告诉其他应用的
        // 这里注册找到设备和完成搜索广播
        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(receiver, filter);
        filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver, filter);

    }

    //搜索蓝牙设备
    public void onClick_Search(View view) {
        bluetoothDevices.clear();
        // 点击搜索周边设备，如果正在搜索，则暂停搜索
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
            Log.d("aa", "暂停搜索");
        }

        mBluetoothAdapter.startDiscovery();
        Log.d("aa", "正在扫描...");
    }

    // 点击listView中的设备，传送数据
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        // 获取到这个设备的信息
        String s = arrayAdapter.getItem(position);

        // 对其进行分割，获取到这个设备的地址
        address = s.substring((s.indexOf(":") + 1), s.indexOf("距")).trim();
        select.setText("选择设备：" + address);
        Log.d("aa", "地址   " + address);
        // 判断当前是否还是正在搜索周边设备，如果是则暂停搜索
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }

    }

    public void starttimer(View view) {
//        taskopen.cancel();
//        taskclose.cancel();
//        tasknowtime.cancel();
        Intent intent = new Intent(BluetoothClientActivity_mytimer_auto.this, timer_open_mytimer.class);
        startActivityForResult(intent, 1);
    }

    public void endtimer(View view) {

        Intent intent = new Intent(BluetoothClientActivity_mytimer_auto.this, timer_close_mytimer.class);
        startActivityForResult(intent, 2);
    }

    public void getPremession() {
        //判断是否有权限
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("aa", "模糊定位");
//请求权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    0x114);
//判断是否需要 向用户解释，为什么要申请该权限
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {
                Log.d("aa", "判断是否需要 向用户解释，为什么要申请该权限");
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean grantedLocation = true;
        if (requestCode == 0x114) {
            Log.d("aa", "允许获取权限");
            for (int i : grantResults) {
                if (i != PackageManager.PERMISSION_GRANTED) {
                    grantedLocation = false;
                }
            }
        }

        if (!grantedLocation) {
            Log.d("aa", "Permission error !!!");
            Toast.makeText(this, "定位权限已拒绝，请手动打开权限!", Toast.LENGTH_LONG).show();

        }
    }

    public void sendOrder(byte[] this_LED_STATE) {
        // 如果选择设备为空则代表还没有选择设备
        if (selectDevice == null) {
            //通过地址获取到该设备
            selectDevice = mBluetoothAdapter.getRemoteDevice(address);
            Log.d("aa", "selectDevice1   " + selectDevice);
        }
        // 这里需要try catch一下，以防异常抛出
        try {
            // 判断客户端接口是否为空
            if (clientSocket == null) {

                // 获取到客户端接口
                clientSocket = selectDevice
                        .createRfcommSocketToServiceRecord(MY_UUID);
                // 向服务端发送连接
                clientSocket.connect();
                select.setText("成功连接：" + selectDevice);
                Log.d("aa", "连接成功");
                ConnectedThread connectedThread = new ConnectedThread(clientSocket);
                connectedThread.start();

                // 获取到输出流，向外写数据
                os = clientSocket.getOutputStream();
                is = clientSocket.getInputStream();
            }

            // 判断是否拿到输出流
            if (os != null) {
                // 需要发送的信息
                Log.d("aa", "拿到输出流");
                os.write(this_LED_STATE);
                //    Toast.makeText(this, "发送信息成功，请查收", Toast.LENGTH_SHORT).show();
            } else {
                Log.d("aa", "没有  拿到输出流");
            }
        } catch (IOException e) {

            e.printStackTrace();
            // 如果发生异常则告诉用户发送失败
            Toast.makeText(this, "发送信息失败", Toast.LENGTH_SHORT).show();
        }
    }

    //  处理SecondActivity返回来的数据
    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (1 == resultCode) {
            //TODO 定时开
            LED_STATE_OPEN_Timer = data.getStringExtra("LED_STATE_TIMER_OPEN");
            opentoclose.setText("开灯：" + LED_STATE_OPEN_Timer);
            Log.d("mytime", "LED_STATE_OPEN_Timer  intent 回传  " + LED_STATE_OPEN_Timer);

        } else if (2 == resultCode) {
            //TODO 定时关
            LED_STATE_CLOSE_Timer = data.getStringExtra("LED_STATE_TIMER_CLOSE");
            Log.d("mytime", "LED_STATE_CLOSE_Timer  intent 回传  " + LED_STATE_CLOSE_Timer);


            if (LED_STATE_OPEN_Timer.contains("周一二三四五六日")) {
                LED_STATE_OPEN_Timer = LED_STATE_OPEN_Timer.replace("周一二三四五六日", "每日");
            }
            if (LED_STATE_CLOSE_Timer.contains("周一二三四五六日")) {
                LED_STATE_CLOSE_Timer = LED_STATE_CLOSE_Timer.replace("周一二三四五六日", "每日");
            }
            opentoclose.setText("开灯：" + LED_STATE_OPEN_Timer + "\n关灯：" + LED_STATE_CLOSE_Timer);
            // opentimer.performClick();

            Log.d("aa", "opentimer  自动点击");
        }
    }

    private class ConnectedThread extends Thread {
        private final BluetoothSocket socket;
        private final InputStream inputStream;
        private final OutputStream outputStream;

        public ConnectedThread(BluetoothSocket socket) {
            this.socket = socket;
            InputStream input = null;
            OutputStream output = null;
            try {
                input = socket.getInputStream();
                output = socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.inputStream = input;
            this.outputStream = output;
        }

        public void run() {
            Log.d("aa", "进入run");

            byte[] buff = new byte[8];
            int bytes;
            while (true) {
                try {

                    bytes = inputStream.read(buff);
                    Log.e("aa", " bytes 长度       " + bytes);
                    String str = new String(buff, "ISO-8859-1");
                    str = str.substring(0, bytes);

                    Message message = handler.obtainMessage();
                    message.obj = byte2HexStr(buff);
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }


        }
    }

    class MyTimerTaskopen extends TimerTask {
        @Override
        public void run() {
            //每次需要执行的代码放到这里面。
            String mowtime = dateToString.nowdateToString4();
            String week = dateToString.dateToWeekNum(mowtime);
            // Log.d("mytime","开灯时间   "+mowtime);
            Log.d("mytime", "传回的开灯时间   " + LED_STATE_OPEN_Timer);
            if (LED_STATE_OPEN_Timer.contains(mowtime) && LED_STATE_OPEN_Timer.contains(week.trim())) {

                Log.d("mytime", "到开灯时间了");
                if (address != null) {
                    sendOrder(ThisOPEN);
                }

            }
        }
    }

    class MyTimerTaskclose extends TimerTask {
        @Override
        public void run() {
            //每次需要执行的代码放到这里面。
            String mowtime = dateToString.nowdateToString4();
            String week = dateToString.dateToWeekNum(mowtime);
            //  Log.d("mytime","关灯时间   "+mowtime);
            Log.d("mytime", "传回的关灯时间   " + LED_STATE_CLOSE_Timer);
            if (LED_STATE_CLOSE_Timer.contains(mowtime) && LED_STATE_CLOSE_Timer.contains(week.trim())) {
                Log.d("mytime", "到关灯时间了");
                if (address != null) {
                    sendOrder(ThisCLOSE);
                }
            }
        }
    }
}
