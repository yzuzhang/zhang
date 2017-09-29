package com.feicent.zhang.thread.notify;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ThreadA extends Thread {

	private Object lock;
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public ThreadA(Object lock) {
		super();
		this.lock = lock;
	}
	
	@Override
	public void run() {
		synchronized(lock){
			try {
				System.out.println("ThreadA 释放同步对象的锁,进入等待状态"+df.format(new Date()));
				lock.wait();//释放锁给其他线程使用
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//其他线程有notify()操作，该线程被唤醒重新获取锁
			System.out.println("ThreadA wait end:"+df.format(new Date()));
		}
	}
	
}
