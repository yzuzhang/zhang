package com.feicent.zhang.plugin.netty.client;

import com.feicent.zhang.util.SleepUtils;

public class MyThread extends Thread {
	private TcpClient client;
	
	public MyThread(TcpClient client) {
		this.client = client;
	}
	
	@Override
	public void run() {
		try {
		    //循环
			for(int i=0; i<100; i++) {
				SleepUtils.sleepRandom(10000);
				client.sendMsg("{\"from\": \""+ client.getUserName() +"\",\"to\": \""+ i+1 +"\", \"msg\": \"Hello " + i+1 + "\"}");
			}
		    //channel.closeFuture();
		} catch (Exception e) {
			System.err.println("推送消息出现异常"+ e.getMessage());
		} finally {
			System.out.println("操作完成");
		}
	}
	
}
