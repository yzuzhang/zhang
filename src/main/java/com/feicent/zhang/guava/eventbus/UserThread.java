package com.feicent.zhang.guava.eventbus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.feicent.zhang.util.CloseUtil;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

/**
 * 用户线程
 * @author yzuzhang
 * @date 2017年9月28日 下午5:25:02
 */
public class UserThread extends Thread{
	private Socket connection;
	private EventBus channel;
	private BufferedReader in;
	private PrintWriter out;

	/**
	 * 构造函数
	 * @param connection
	 * @param channel
	 */
	public UserThread(Socket connection, EventBus channel) {
		this.connection = connection;
		this.channel = channel;
		try {
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			out = new PrintWriter(connection.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Subscribe
	public void recieveMessage(String message) {
		if (out != null) {
			//返回客户端OK
			out.println("OK!");
			System.out.println("recieveMessage: " + message);
		}
	}

	@Override
	public void run() {
		try {
			String input = null;
			while ((input = in.readLine()) != null) {
				channel.post(input);//
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		channel.unregister(this);
		
		CloseUtil.close(in, out);
		CloseUtil.closeSocket(connection);
	}
}
