package com.feicent.zhang.thread.notify;

import com.feicent.zhang.util.SleepUtils;
import com.feicent.zhang.util.date.DateUtils;

public class WaitNotify {
	private static boolean flag = true;
	private static Object  lock = new Object();
	
	public static void main(String[] args) {
		Thread waitThread = new Thread(new Wait(), "WaitThread");
		waitThread.start();
		
		SleepUtils.sleep(1000);
		Thread notifyThread = new Thread(new Notify(), "NotifyThread");
		notifyThread.start();
	}

	public static class Wait implements Runnable{
		@Override
		public void run() {
			//加锁,拥有lock的Monitor
			synchronized(lock){
				while (flag) {
					System.out.println(Thread.currentThread()+" flag is true. wait @ "+DateUtils.getNow());
					try {
						lock.wait();//释放锁,等待wait返回
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			// 条件满足时,完成工作
			System.out.println(Thread.currentThread()+" flag is false. wait @ "+ DateUtils.getNow());
		}
	}
	
	public static class Notify implements Runnable{
		@Override
		public void run() {
			//加锁,拥有lock的Monitor
			synchronized (lock) {
				//获取lock的锁,然后运行通知,通知时不释放lock的锁, 直到当前线程释放了lock后,WaitThread才能从wait方法中返回
				System.out.println(Thread.currentThread()+" hold lock. notify @ "+ DateUtils.getNow());
				lock.notify();
				flag = false;
				SleepUtils.sleep(100);
			}
			
			SleepUtils.sleep(1);
			//再次加锁
			synchronized (lock) {
				System.out.println(Thread.currentThread()+" hold lock again. sleep @ "+ DateUtils.getNow());
				SleepUtils.sleep(1000);
			}
		}
		
	}
	
}
