package com.example.smartswitch_wifi;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;


/**
 * 添加一个异步处理类
 */
 
public class SendAsyncTask extends AsyncTask<byte[], Void, Void> {

	public void setIP(String IP) {
		this.IP = IP;
	}


	public void setPORT(int PORT) {
		this.PORT = PORT;
	}

	//这里是连接ESP8266的IP和端口号，IP是通过指令在单片机开发板查询到，而端口号可以自行设置，也可以使用默认的，333就是默认的
	private   String IP = "192.168.1.252";
	private   int PORT = 5000;



	private Socket client = null;
	//private DataOutputStream out = null;
	private OutputStream out =null;
	//private OutputStream out;
 
//	@Override
//	protected Void doInBackground(String... params) {
//
//		String str = params[0];
//		try {
//			client = new Socket(IP, PORT);
//			client.setSoTimeout(5000);
//			// 获取Socket的输出流，用来发送数据到服务端
//			out = new PrintStream(client.getOutputStream());
//			out.print(str);
//			out.flush();
//
//			if (client == null) {
//				Log.d("wifi","发送失败");
//				return null;
//			} else {
//
//				out.close();
//				client.close();
//				Log.d("wifi","发送成功");
//			}
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		return null;
//	}

	@Override
	protected Void doInBackground(byte[]... params) {

		byte[] str = params[0];
		try {
			client = new Socket(IP, PORT);
			client.setSoTimeout(5000);
			// 获取Socket的输出流，用来发送数据到服务端
			//out = new PrintStream(client.getOutputStream());
			//out  = client.getOutputStream();


			out =client.getOutputStream();
			out.write(str);
			//释放资源
			out.close();
			//out.write(str);//发送byte类型
			//out.flush();

			if (client == null) {
				Log.d("wifi","发送失败");
				return null;
			} else {

				out.close();
				client.close();
				Log.d("wifi","发送成功");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
}