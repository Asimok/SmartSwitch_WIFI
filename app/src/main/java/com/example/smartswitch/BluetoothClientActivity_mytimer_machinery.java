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
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.iflytek.cloud.util.ResourceUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimerTask;
import java.util.UUID;

import speech.setting.IatSettings;
import speech.util.JsonParser;

import static java.lang.Math.abs;
import static java.lang.Math.pow;

public class BluetoothClientActivity_mytimer_machinery extends Activity implements OnItemClickListener {
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
    public byte[] SET_TIME = {(byte) 0xff, 10, 0x30, 2019 / 256, (byte) (2019 % 256), 02, 20, 3, 14, 05, 0x0A, (byte) 0xfe};
    public TextView re_msg, select, opentoclose, countdown;
    public LocationManager lm;//【位置管理】
    //////////////////////////////////////
    public Button yuyin = null;
    private TextToSpeech texttospeech;
    private static String TAG = "IatDemo";
    // 语音听写对象
    private SpeechRecognizer mIat;
    // 语音听写UI
    private RecognizerDialog mIatDialog;
    // 用HashMap存储听写结果
    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();

    private Toast mToast;

    private SharedPreferences mSharedPreferences;
    private boolean mTranslateEnable = false;
    private String mEngineType = "cloud";

    int ret = 0;// 函数调用返回值
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

                Toast.makeText(BluetoothClientActivity_mytimer_machinery.this, "搜索完成", Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.layout_buletooth_seacher_mytimer_machinery);
        open = (Button) findViewById(R.id.open);
        close1 = (Button) findViewById(R.id.close);
        opentimer = (Button) findViewById(R.id.opentimer);
        close1timer = (Button) findViewById(R.id.closetimer);
        receive1 = (TextView) findViewById(R.id.receive_text);
        re_msg = (TextView) findViewById(R.id.msg);
        select = (TextView) findViewById(R.id.select);
        opentoclose = (TextView) findViewById(R.id.opentoclose);
        countdown = (TextView) findViewById(R.id.countdown);

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
                        sendOrder(NoTimer_machinery.OPEN);

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
                        sendOrder(NoTimer_machinery.CLOSE);
                    }
                }
            }
        });
///////////////////////////////////////
        //////////////////////////////////////
        // 初始化识别无UI识别对象
        // 使用SpeechRecognizer对象，可根据回调消息自定义界面；
        yuyin=(Button) findViewById(R.id.yuyin);

        mIat = SpeechRecognizer.createRecognizer(this, mInitListener);

        // 初始化听写Dialog，如果只使用有UI听写功能，无需创建SpeechRecognizer
        // 使用UI听写功能，请根据sdk文件目录下的notice.txt,放置布局文件和图片资源
        mIatDialog = new RecognizerDialog(this,mInitListener);

        mSharedPreferences = getSharedPreferences(IatSettings.PREFER_NAME, Activity.MODE_PRIVATE);
        mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        mEngineType = SpeechConstant.TYPE_CLOUD;
        // 初始化TextToSpeech对象
        texttospeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {
                // 如果装载TTS引擎成功
                if (status == TextToSpeech.SUCCESS) {
                    // 设置使用美式英语朗读
                    int result = texttospeech.setLanguage(Locale.US);
                    // 如果不支持所设置的语言
                    if (result != TextToSpeech.LANG_COUNTRY_AVAILABLE
                            && result != TextToSpeech.LANG_AVAILABLE) {
                        Log.d("ff", "TTS暂时不支持这种语言的朗读！");
                    }
                }
            }
        });

        yuyin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (select.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "未连接到蓝牙设备！请重新连接！", Toast.LENGTH_SHORT).show();
                } else {
                    receive1.setText("语音控制");
                    if (address != null) {
                        //TODO  语音控制

                        mIatResults.clear();
                        // 设置参数
                        setParam();
                        boolean isShowDialog = mSharedPreferences.getBoolean(getString(R.string.pref_key_iat_show), true);
                        if (isShowDialog) {
                            // 显示听写对话框
                            mIatDialog.setListener(mRecognizerDialogListener);
                            mIatDialog.show();
                            showTip(getString(R.string.text_begin));
                        } else {
                            // 不显示听写对话框
                            ret = mIat.startListening(mRecognizerListener);
                            if (ret != ErrorCode.SUCCESS) {
                                showTip("听写失败,错误码：" + ret);
                            } else {
                                showTip(getString(R.string.text_begin));
                            }
                        }


                    }
                }
            }
        });
        ////////////////////////////////////////////
        /////////////////////////////////////////////

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
        Intent intent = new Intent(BluetoothClientActivity_mytimer_machinery.this, timer_open_mytimer.class);
        startActivityForResult(intent, 1);
    }

    public void endtimer(View view) {

        Intent intent = new Intent(BluetoothClientActivity_mytimer_machinery.this, timer_close_mytimer.class);
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
            texttospeech.speak("指令发送失败", TextToSpeech.QUEUE_ADD,
                    null);
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
                    sendOrder(NoTimer_machinery.OPEN);
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
                    sendOrder(NoTimer_machinery.CLOSE);
                }
            }
        }
    }
    /////////////////////////////////
    /////////////////////////////////
    /**
     * 初始化监听器。
     */
    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            Log.d(TAG, "SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                showTip("初始化失败，错误码：" + code);
            }
        }
    };

    /**
     * 听写监听器。
     */
    private RecognizerListener mRecognizerListener = new RecognizerListener() {

        @Override
        public void onBeginOfSpeech() {
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
            showTip("开始说话");
        }

        @Override
        public void onError(SpeechError error) {
            // Tips：
            // 错误码：10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。
            if(mTranslateEnable && error.getErrorCode() == 14002) {
                showTip( error.getPlainDescription(true)+"\n请确认是否已开通翻译功能" );
            } else {
                showTip(error.getPlainDescription(true));
            }
        }

        @Override
        public void onEndOfSpeech() {
            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
            showTip("结束说话");
        }

        @Override
        public void onResult(RecognizerResult results, boolean isLast) {
            if( mTranslateEnable ){
                printTransResult( results );
            }else{
                String text = JsonParser.parseIatResult(results.getResultString());


            }

            if(isLast) {
                //TODO 最后的结果
            }
        }

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
            showTip("当前正在说话，音量大小：" + volume);
            Log.d(TAG, "返回音频数据："+data.length);
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }
    };

    /**
     * 听写UI监听器
     */
    private RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
        public void onResult(RecognizerResult results, boolean isLast) {
            Log.d(TAG, "recognizer result：" + results.getResultString());

            if( mTranslateEnable ){
                printTransResult( results );
            }else{
                String text = JsonParser.parseIatResult(results.getResultString());
                //TODO
                // 朗读
                if(text.contains("开")&&text.contains("灯"))
                {
                    texttospeech.speak("已为您开灯", TextToSpeech.QUEUE_ADD,
                            null);
                    sendOrder(NoTimer_machinery.OPEN);
                    Log.d("aa", "ThisOPEN  "+NoTimer_machinery.OPEN.toString());
                }
                else if(text.contains("关")&&text.contains("灯"))
                {
                    texttospeech.speak("已为您关灯", TextToSpeech.QUEUE_ADD,
                            null);
                    sendOrder(NoTimer_machinery.CLOSE);
                    Log.d("aa", "ThisCLOSE  "+NoTimer_machinery.CLOSE.toString());
                }


            }
        }

        /**
         * 识别回调错误.
         */
        public void onError(SpeechError error) {
            if(mTranslateEnable && error.getErrorCode() == 14002) {
                showTip( error.getPlainDescription(true)+"\n请确认是否已开通翻译功能" );
            } else {
                showTip(error.getPlainDescription(true));
            }
        }

    };


    private void showTip(final String str)
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mToast.setText(str);
                mToast.show();
            }
        });
    }

    /**
     * 参数设置
     * @return
     */
    public void setParam(){
        // 清空参数
        mIat.setParameter(SpeechConstant.PARAMS, null);
        String lag = mSharedPreferences.getString("iat_language_preference", "mandarin");
        // 设置引擎
        mIat.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
        // 设置返回结果格式
        mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");

        this.mTranslateEnable = mSharedPreferences.getBoolean( this.getString(R.string.pref_key_translate), false );
        if (mEngineType.equals(SpeechConstant.TYPE_LOCAL)) {
            // 设置本地识别资源
            mIat.setParameter(ResourceUtil.ASR_RES_PATH, getResourcePath());
        }
        if( mEngineType.equals(SpeechConstant.TYPE_CLOUD) && mTranslateEnable ){
            Log.i( TAG, "translate enable" );
            mIat.setParameter( SpeechConstant.ASR_SCH, "1" );
            mIat.setParameter( SpeechConstant.ADD_CAP, "translate" );
            mIat.setParameter( SpeechConstant.TRS_SRC, "its" );
        }
        //设置语言，目前离线听写仅支持中文
        if (lag.equals("en_us")) {
            // 设置语言
            mIat.setParameter(SpeechConstant.LANGUAGE, "en_us");
            mIat.setParameter(SpeechConstant.ACCENT, null);


            if( mEngineType.equals(SpeechConstant.TYPE_CLOUD) && mTranslateEnable ){
                mIat.setParameter( SpeechConstant.ORI_LANG, "en" );
                mIat.setParameter( SpeechConstant.TRANS_LANG, "cn" );
            }
        }else {
            // 设置语言
            mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
            // 设置语言区域
            mIat.setParameter(SpeechConstant.ACCENT,lag);

            if( mEngineType.equals(SpeechConstant.TYPE_CLOUD) && mTranslateEnable ){
                mIat.setParameter( SpeechConstant.ORI_LANG, "cn" );
                mIat.setParameter( SpeechConstant.TRANS_LANG, "en" );
            }
        }

        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        mIat.setParameter(SpeechConstant.VAD_BOS, mSharedPreferences.getString("iat_vadbos_preference", "4000"));

        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        mIat.setParameter(SpeechConstant.VAD_EOS, mSharedPreferences.getString("iat_vadeos_preference", "1000"));

        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        mIat.setParameter(SpeechConstant.ASR_PTT, mSharedPreferences.getString("iat_punc_preference", "1"));

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        mIat.setParameter(SpeechConstant.AUDIO_FORMAT,"wav");
        mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/iat.wav");
    }

    private String getResourcePath(){
        StringBuffer tempBuffer = new StringBuffer();
        //识别通用资源
        tempBuffer.append(ResourceUtil.generateResourcePath(this, ResourceUtil.RESOURCE_TYPE.assets, "asr/common.jet"));
        tempBuffer.append(";");
        tempBuffer.append(ResourceUtil.generateResourcePath(this, ResourceUtil.RESOURCE_TYPE.assets, "asr/sms.jet"));
        //识别8k资源-使用8k的时候请解开注释
        return tempBuffer.toString();
    }

    private void printTransResult (RecognizerResult results) {
        String trans  = JsonParser.parseTransResult(results.getResultString(),"dst");
        String oris = JsonParser.parseTransResult(results.getResultString(),"src");

        if( TextUtils.isEmpty(trans)|| TextUtils.isEmpty(oris) ){
            showTip( "解析结果失败，请确认是否已开通翻译功能。" );
        }else{

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if( null != mIat ){
            // 退出时释放连接
            mIat.cancel();
            mIat.destroy();
        }
    }
}
