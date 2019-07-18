package com.example.smartswitch_wifi;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.smartswitch_wifi.utils.DateUtil;
import com.example.smartswitch_wifi.utils.Pref;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

public class ConnectSocket extends AppCompatActivity implements View.OnClickListener{
    public void setTv_content(TextView tv_content) {
        this.tv_content = tv_content;
    }

    /**
     * 连接socket
     */
    private TextView tv_content;
    SendAsyncTask sendAsyncTask;
    private InputStream input = null;
    private DataInputStream reader = null;
    private DataOutputStream writer = null;
    private MsgRunnable msgRunnable;
    private Handler mHandler = new Handler();
    private Socket mClient;

    public void setThisPhoneIP(String thisPhoneIP) {
        ThisPhoneIP = thisPhoneIP;
    }

    private  String ThisPhoneIP;
    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setContext(Context context) {
        this.context = context;
    }


    public void setSp(Pref sp) {
        this.sp = sp;
    }


    public void setEd_send_text_String(String ed_send_text_String) {
        this.ed_send_text_String = ed_send_text_String;
    }

    public void setTv_content_String(String tv_content_String) {
        this.tv_content_String = tv_content_String;
    }

    public void setmStart(Button mStart) {
        this.mStart = mStart;
    }

    private Button mStart;
    private  String tv_content_String;
    private String host,port;
    private Context context;
    private Pref sp;
    private String ed_send_text_String;


    public void startSocket() {

        if (host.equals("")) {
            Toast.makeText(context, "请输入server Ip", Toast.LENGTH_SHORT).show();
            return;
        } else if (port.equals("")) {
            Toast.makeText(context, "请输入server 端口号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mClient == null || mClient.isClosed() || !mClient.isConnected()) {
            connect(host, Integer.parseInt(port));
        }
    }

    private void connect(String host, int port) {

        new ConnectThread(host, port).start();
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 连接socket的线程
     */
    private class ConnectThread extends Thread {

        private String host;
        private int port;

        public ConnectThread(String host, int port) {
            this.host = host;
            this.port = port;
        }

        @Override
        public void run() {
            try {
                mClient = new Socket(host, port);
                //修改按钮的状态
                input = mClient.getInputStream();
                OutputStream output = mClient.getOutputStream();
                reader = new DataInputStream(input);
               // writer = new DataOutputStream(output);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mStart.setText("断开连接");
                        Toast.makeText(context, "连接成功", Toast.LENGTH_SHORT).show();
                    }
                });
                //保存host和port
                sp.setHost(host);
                sp.setPort(port);
                new ReceivedThread().start();
                //连接成功后发送一条消息
              //  writer.writeBytes("hello server");
                //writer.flush();

                //一次性发送
                PrintStream writer2 = new PrintStream(mClient.getOutputStream());
                writer2.print(ThisPhoneIP);
                writer2.flush();
            } catch (IOException e) {
                e.printStackTrace();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "连接失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }


    /**
     * 断开scoket连接
     */
    public void stopSocket() {
        try {
            if (msgRunnable != null) {
                mHandler.removeCallbacks(msgRunnable);//msgRunnable != null
                msgRunnable = null;
            }
            if (reader != null)
                reader.close();
            if (writer != null)
                writer.close();
            if (mClient != null) {
                mClient.close();
                mClient = null;
            }
            //修改按钮状态
            Toast.makeText(context, "连接已断开", Toast.LENGTH_SHORT).show();
            mStart.setText("连接");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 连续请求的消息
     * 每过一个周期时间就执行一次自身
     */
    class MsgRunnable implements Runnable {
        String msg;
        int period;

        public MsgRunnable(String msg, int period) {
            this.msg = msg;
            this.period = period;
        }

//        @Override
//        public void run() {
//            // sendString(msg);
//            String str = ed_send_text_String;
//            sendAsyncTask=new SendAsyncTask();
//            sendAsyncTask.setIP(host);
//            sendAsyncTask.setPORT(Integer.parseInt(port));
//            sendAsyncTask.execute(str);//发送数据
//            mHandler.postDelayed(msgRunnable, period);
//        }
        @Override
        public void run() {
            // sendString(msg);
            byte[] str = new byte[]{(byte) 0xff};
            sendAsyncTask=new SendAsyncTask();
            sendAsyncTask.setIP(host);
            sendAsyncTask.setPORT(Integer.parseInt(port));
            sendAsyncTask.execute(str);//发送数据
            mHandler.postDelayed(msgRunnable, period);
        }
    }

    /**
     * 接受消息的线程
     */
    private class ReceivedThread extends Thread {
        @Override
        public void run() {
            String showText = tv_content_String;
            final StringBuilder builder = new StringBuilder(showText);
            try {
                byte[] bytes = new byte[1024 * 10];
                while (mClient.isConnected()) {

                    int length = input.read(bytes);
                    if (length < 0) {
                        Log.i("wifi", "run: socket disConnected");
                        tv_content.append("接收信息失败");
                        Log.i("wifi", "接收信息失败");
                        Looper.loop();
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                               mStart.setText("连接已断开");
                            }
                        });
                        break;
                    }
                    else{
                        tv_content.append("接收信息成功");
                        final String s = new String(bytes, 0, length, "utf-8");
                        builder.append("\n");
                        builder.append(DateUtil.formatTime());
                        builder.append("=>\r\r");
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                builder.append(s);
                                tv_content.setText(builder.toString());
                                Log.i("wifi", "接收信息: "+builder.toString());
                            }
                        });

                    }
                }
            } catch (final IOException e) {
                e.printStackTrace();
                        mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        builder.append("\n");
                        builder.append(DateUtil.formatTime());
                        builder.append("=>\r\r");
                        builder.append(e.toString());
                        tv_content.setText(builder.toString());
                    }
                });
            }
        }
    }
}
