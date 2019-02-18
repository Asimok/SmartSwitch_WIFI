package com.example.smartswitch;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.AppOpsManager;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.AppOpsManagerCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.Math.abs;
import static java.lang.Math.pow;

public  class BuletoothClientActivity extends Activity implements OnItemClickListener{
    public  int GPS_REQUEST_CODE = 1;
    private  String address;
    public   List deviceList;
    // 获取到蓝牙适配器
    public  BluetoothAdapter mBluetoothAdapter;
    // 用来保存搜索到的设备信息
    public  List<String> bluetoothDevices = new ArrayList<String>();
    // ListView组件
    public  ListView lvDevices;
    // ListView的字符串数组适配器
    public  ArrayAdapter<String> arrayAdapter;
    // UUID，蓝牙建立链接需要的
    public  final UUID MY_UUID = UUID
            .fromString("00001101-0000-1000-8000-00805F9B34FB");
    // 为其链接创建一个名称
    public  final String NAME = "Bluetooth_Socket";
    // 选中发送数据的蓝牙设备，全局变量，否则连接在方法执行完就结束了
    public  BluetoothDevice selectDevice;
    // 获取到选中设备的客户端串口，全局变量，否则连接在方法执行完就结束了
    public  BluetoothSocket clientSocket;
    // 获取到向设备写的输出流，全局变量，否则连接在方法执行完就结束了
    public  OutputStream os;
    // 服务端利用线程不断接受客户端信息
    public  AcceptThread thread;
    //定义按钮
    //定义按钮
    public  Button close1 = null;
    public  Button open = null;
    public  TextView receive1;
    public  byte[] LED_STATE = {(byte) 0xff, 0x02, 0x00, 0x0A, (byte) 0xfe};
    public  TextView re_msg,select;
    public  LocationManager lm;//【位置管理】

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getPremession();//获取虚拟定位权限
        setContentView(R.layout.layout_buletooth_seacher);
        open = (Button)findViewById(R.id.open);
        close1 = (Button)findViewById(R.id.close);
        receive1 = (TextView)findViewById(R.id.receive_text);
        re_msg = (TextView)findViewById(R.id.msg);
        select= (TextView)findViewById(R.id.select);
        Log.d("aa", String.valueOf((int)(TimeOpen.OneDay("1",(byte) 0x04, (byte)0x04, (byte)0x04)[3])));
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(View view) {
                LED_STATE=NoTimer.OPEN;
                receive1.setText(LED_STATE.toString());
             //   Log.d("aa",LED_STATE);
                if(address!=null) {
                    sendOrder();
                }
            }
        });
        close1.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(View view) {
                LED_STATE=NoTimer.CLOSE;
                receive1.setText(LED_STATE.toString());
                if(address!=null) {
                    sendOrder();
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
//        if (devices.size() > 0) {
//            for (BluetoothDevice bluetoothDevice : devices) {
//                // 保存到arrayList集合中
//                String mm="未知";
//                bluetoothDevices.add(bluetoothDevice.getName() + ":  "
//                        + bluetoothDevice.getAddress() + "  距离:  "+mm+"\n");
//
//            }
//        }

        // 因为蓝牙搜索到设备和完成搜索都是通过广播来告诉其他应用的
        // 这里注册找到设备和完成搜索广播
        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(receiver, filter);
        filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver, filter);

        // 实例接收客户端传过来的数据线程
        thread = new AcceptThread();
        // 线程开始
        thread.start();
    }

    //搜索蓝牙设备
    public  void onClick_Search(View view) {
        bluetoothDevices.clear();
        // 点击搜索周边设备，如果正在搜索，则暂停搜索
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
            Log.d("aa", "暂停搜索");
        }

        mBluetoothAdapter.startDiscovery();
            Log.d("aa", "正在扫描...");
    }
    // 注册广播接收者
    public  BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public  void onReceive(Context arg0, Intent intent) {
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
                    Log.d("aa", "设备没有绑定过   "+device.getName() + ":"
                            + device.getAddress() + "\n");
                    short rssi = intent.getExtras().getShort(
                            BluetoothDevice.EXTRA_RSSI);
                    int iRssi = abs(rssi);
                    // 将蓝牙信号强度换算为距离
                    double power = (iRssi - 59) / 25.0;
                    String mm = new Formatter().format("%.2f", pow(10, power)).toString();

                    Log.d("aa", "距离    "+mm);


                    bluetoothDevices.add(device.getName() + ":  "
                            + device.getAddress() + "  距离:  "+mm+ "m"+"\n");
                    // 更新字符串数组适配器，将内容显示在listView中

                    arrayAdapter.notifyDataSetChanged();

                }
                else{
                    Log.d("aa", "设备绑定过   "+device.getName() + ":"
                            + device.getAddress() + "\n");
                    short rssi = intent.getExtras().getShort(
                            BluetoothDevice.EXTRA_RSSI);
                    int iRssi = abs(rssi);
                    // 将蓝牙信号强度换算为距离
                    double power = (iRssi - 59) / 25.0;
                    String mm = new Formatter().format("%.2f", pow(10, power)).toString();

                    Log.d("aa", "距离    "+mm);


                    bluetoothDevices.add(device.getName() + ":  "
                            + device.getAddress() + "  距离:  "+mm+ "m   已配对"+"\n");
                    // 更新字符串数组适配器，将内容显示在listView中

                    arrayAdapter.notifyDataSetChanged();
                }
            } else if (action
                    .equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)) {

                Toast.makeText(BuletoothClientActivity.this, "搜索完成", Toast.LENGTH_SHORT).show();
                Log.d("aa", "搜索完成");
            }

        }
    };

    // 点击listView中的设备，传送数据
    @Override
    public  void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        // 获取到这个设备的信息
        String s = arrayAdapter.getItem(position);

        // 对其进行分割，获取到这个设备的地址
        address = s.substring((s.indexOf(":")+1),s.indexOf("距")).trim();
        select.setText("选择设备："+address);
        Log.d("aa","地址   "+address);
        // 判断当前是否还是正在搜索周边设备，如果是则暂停搜索
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }

    }

    // 创建handler，因为我们接收是采用线程来接收的，在线程中无法操作UI，所以需要handler
    Handler handler = new Handler() {
        @Override
        public  void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            // 通过msg传递过来的信息，吐司一下收到的信息
            // Toast.makeText(BuletoothClientActivity.this, (String) msg.obj, Toast.LENGTH_SHORT).show();
            re_msg.setText((String)msg.obj);
        }
    };

    public  void timer(View view) {
        Intent intent=new Intent(this,timer.class);
        startActivity(intent);
    }



    // 服务端接收信息线程
    public  class AcceptThread extends Thread {
        public  BluetoothServerSocket serverSocket;// 服务端接口
        public  BluetoothSocket socket;// 获取到客户端的接口
        public  InputStream is;// 获取到输入流
        public  OutputStream os;// 获取到输出流

        public  AcceptThread() {
            try {
                // 通过UUID监听请求，然后获取到对应的服务端接口
                serverSocket = mBluetoothAdapter
                        .listenUsingRfcommWithServiceRecord(NAME, MY_UUID);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public  void run() {
            try {
                // 接收其客户端的接口
                socket = serverSocket.accept();
                // 获取到输入流
                is = socket.getInputStream();
                // 获取到输出流
                os = socket.getOutputStream();

                // 无线循环来接收数据
                while (true) {
                    // 创建一个128字节的缓冲
                    byte[] buffer = new byte[128];
                    // 每次读取128字节，并保存其读取的角标
                    int count = is.read(buffer);
                    Log.d("aa","每次读取128字节"+count);
                    // 创建Message类，向handler发送数据
                    Message msg = new Message();
                    // 发送一个String的数据，让他向上转型为obj类型
                    msg.obj = new String(buffer, 0, count, "utf-8");
                    // 发送数据
                    handler.sendMessage(msg);
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }

        }
    }




    public  void getPremession() {
        //判断是否有权限
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("aa", "模糊定位");
//请求权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},
                    0x114);
//判断是否需要 向用户解释，为什么要申请该权限
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {
                Log.d("aa", "判断是否需要 向用户解释，为什么要申请该权限");
            }
        }
    }
    @Override
    public  void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean grantedLocation = true;
        if(requestCode == 0x114){
            Log.d("aa", "允许获取权限");
            for(int i : grantResults){
                if(i != PackageManager.PERMISSION_GRANTED){
                    grantedLocation = false;
                }
            }
        }

        if(!grantedLocation){
            Log.d("aa", "Permission error !!!");
            Toast.makeText(this,"定位权限已拒绝，请手动打开权限!" , Toast.LENGTH_LONG).show();
//            finish();
        }
    }
    public  void sendOrder()
    {
        // 如果选择设备为空则代表还没有选择设备
        if (selectDevice == null) {
            //通过地址获取到该设备
            selectDevice = mBluetoothAdapter.getRemoteDevice(address);
            Log.d("aa","selectDevice1   "+selectDevice);
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
                select.setText("成功连接："+selectDevice);
                Log.d("aa","连接成功");
                // 获取到输出流，向外写数据
                os = clientSocket.getOutputStream();
            }

            // 判断是否拿到输出流
            if (os != null) {
                // 需要发送的信息
                Log.d("aa","拿到输出流");
                os.write(LED_STATE);
                // 吐司一下，告诉用户发送成功
                Toast.makeText(this, "发送信息成功，请查收", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Log.d("aa","没有  拿到输出流");
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            // 如果发生异常则告诉用户发送失败
            Toast.makeText(this, "发送信息失败", Toast.LENGTH_SHORT).show();
        }
    }
}
