package com.example.smartswitch_wifi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartswitch_wifi.utils.Pref;
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

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.TimerTask;

import speech.setting.IatSettings;
import speech.util.JsonParser;

import static java.lang.Math.abs;

public class BluetoothClientActivity_mytimer_auto extends Activity implements OnItemClickListener, View.OnClickListener {

    //定义按钮
    public Button close1 = null;
    public Button open = null;
    public Button close1timer = null;
    public Button opentimer = null;
    private TextView tv_content;
    private EditText EDIP,EDPORT;
    public Button mStart = null;
    private Pref sp;
    ConnectSocket connectSocket;
    SendAsyncTask sendAsyncTask;
//////////////////////////////////////
    public Button yuyin = null;
    private TextToSpeech texttospeech;

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
//////////////////////////////////
    public TextView receive1;
    public String LED_STATE_OPEN_Timer = "";
    public String LED_STATE_CLOSE_Timer = "";
    public TextView re_msg, select, opentoclose, countdown,recivemsg;
    public RadioGroup lightnum;
    public RadioButton light1,light2,light3,light4;
    public static byte[] ThisOPEN ={'x'};
    public static byte[] ThisCLOSE ={'x'};


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

        setContentView(R.layout.layout_buletooth_seacher_mytimer_auto);
        open = (Button) findViewById(R.id.open);
        close1 = (Button) findViewById(R.id.close);
        mStart= (Button) findViewById(R.id.mStart);
        opentimer = (Button) findViewById(R.id.opentimer);
        close1timer = (Button) findViewById(R.id.closetimer);
        receive1 = (TextView) findViewById(R.id.receive_text);
        re_msg = (TextView) findViewById(R.id.msg);
        select = (TextView) findViewById(R.id.select);
        opentoclose = (TextView) findViewById(R.id.opentoclose);
        countdown = (TextView) findViewById(R.id.countdown);
        tv_content= (TextView) findViewById(R.id.tv_content);
        lightnum=findViewById(R.id.num);
        light1=findViewById(R.id.light1);
        light2=findViewById(R.id.light2);
        light3=findViewById(R.id.light3);
        light4=findViewById(R.id.light4);
        EDIP =  findViewById(R.id.EDIP);
        EDPORT =  findViewById(R.id.EDPORT);
        sp = Pref.getInstance(this);
        EDIP.setText(sp.getHost());
        EDPORT.setText(String.valueOf(sp.getPort()));



      // InitSocket()

            connectSocket=new ConnectSocket();
            connectSocket.setThisPhoneIP(getLocalIpAddress());
            connectSocket.setContext(BluetoothClientActivity_mytimer_auto.this);
            connectSocket.setTv_content_String(tv_content.getText().toString());
            connectSocket.setHost(EDIP.getText().toString());
            connectSocket.setPort(EDPORT.getText().toString());
            connectSocket.setTv_content(tv_content);
            connectSocket.setSp(sp);
            connectSocket.setmStart(mStart);


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


        ////////////////////////////////////////////
        /////////////////////////////////////////////
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

        mStart.setOnClickListener(new View.OnClickListener() {
            //连接服务器
            @Override
            public void onClick(View view) {

                connectSocket.setTv_content_String(tv_content.getText().toString());
                connectSocket.setHost(EDIP.getText().toString());
                connectSocket.setPort(EDPORT.getText().toString());
                if(mStart.getText().equals("断开连接"))
                {
                    connectSocket.stopSocket();
                }
                else
                    connectSocket.startSocket();

            }
        });
        open.setOnClickListener(new View.OnClickListener() {
            //立即开
            @Override
            public void onClick(View view) {
                sendOrder(ThisOPEN);
            }
        });
        close1.setOnClickListener(new View.OnClickListener() {
            //立即关
            @Override
            public void onClick(View view) {

                        sendOrder(ThisCLOSE);


            }
        });

        yuyin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    receive1.setText("语音控制");

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
        });

        //TODO 开启定时
        opentimer.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("HandlerLeak")
            @Override
            public void onClick(View view) {
               if (LED_STATE_OPEN_Timer.equals("")) {
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
        });



    }




    public void starttimer(View view) {

        Intent intent = new Intent(BluetoothClientActivity_mytimer_auto.this, timer_open_mytimer.class);
        startActivityForResult(intent, 1);
    }

    public void endtimer(View view) {

        Intent intent = new Intent(BluetoothClientActivity_mytimer_auto.this, timer_close_mytimer.class);
        startActivityForResult(intent, 2);
    }




//    public void sendOrder(byte[] this_LED_STATE) {
////发送指令
//        String str = ThisPhoneIP+"...:"+ byte2HexStr(this_LED_STATE) +"...";
//        sendAsyncTask=new SendAsyncTask();
//        sendAsyncTask.setIP(EDIP.getText().toString());
//        sendAsyncTask.setPORT(Integer.parseInt(EDPORT.getText().toString()));
//        sendAsyncTask.execute(str);//发送数据
//    }
public void sendOrder(byte[] this_LED_STATE) {
//发送指令
    byte[] str = this_LED_STATE;
    sendAsyncTask=new SendAsyncTask();
    sendAsyncTask.setIP(EDIP.getText().toString());
    sendAsyncTask.setPORT(Integer.parseInt(EDPORT.getText().toString()));
    sendAsyncTask.execute(str);//发送数据
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



        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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

    /////////////////////////////////
    /////////////////////////////////
    /**
     * 初始化监听器。
     */
    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {

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


            if( mTranslateEnable ){
                printTransResult( results );
            }else{
                String text = JsonParser.parseIatResult(results.getResultString());
                //TODO
                // 朗读
                //NoTimer_auto.OPEN1
                if(text.contains("开")&&text.contains("灯"))
                {
                    texttospeech.speak("已为您开灯", TextToSpeech.QUEUE_ADD,
                            null);
                    sendOrder(NoTimer_auto.OPEN1);
                    Log.d("aa", "ThisOPEN  "+ThisOPEN.toString());
                }
                else if(text.contains("关")&&text.contains("灯"))
                {
                    texttospeech.speak("已为您关灯", TextToSpeech.QUEUE_ADD,
                            null);
                    sendOrder(NoTimer_auto.CLOSE1);
                    Log.d("aa", "ThisCLOSE  "+ThisCLOSE.toString());
                }
                if(text.contains("开")&&text.contains("风扇"))
                {
                    texttospeech.speak("已为您打开风扇", TextToSpeech.QUEUE_ADD,
                            null);
                    sendOrder(NoTimer_auto.OPEN2);
                    Log.d("aa", "ThisOPEN  "+ThisOPEN.toString());
                }
                else if(text.contains("关")&&text.contains("风扇"))
                {
                    texttospeech.speak("已为您关闭风扇", TextToSpeech.QUEUE_ADD,
                            null);
                    sendOrder(NoTimer_auto.CLOSE2);
                    Log.d("aa", "ThisCLOSE  "+ThisCLOSE.toString());
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
    /**
     *
     * 获取WIFI下ip地址
     */
    private String getLocalIpAddress() {
        WifiManager wifiManager = (WifiManager) this.getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        // 获取32位整型IP地址
        int ipAddress = wifiInfo.getIpAddress();
        Log.d("wifi","本机	IP	"+String.format("%d.%d.%d.%d",
                (ipAddress & 0xff), (ipAddress >> 8 & 0xff),
                (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff)));
        //返回整型地址转换成“*.*.*.*”地址
        return String.format("%d.%d.%d.%d",
                (ipAddress & 0xff), (ipAddress >> 8 & 0xff),
                (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff));
    }

}
