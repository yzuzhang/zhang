package com.feicent.zhang.thread;

public class DeadThread implements Runnable {
	
	public String username;
	public Object lock1 = new Object();
	public Object lock2 = new Object();
	
	public void setUserName (String username) {
		this.username = username;
	}
	
	@Override
	public void run() {
		if ("a".equals(username)) {
			synchronized(lock1){
				try {
					System.out.println("执行username="+username);
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				synchronized (lock2) {
					System.out.println("按照lock1->lock2的顺序执行了！");
				}
			}
		}
		//
		else if ("b".equals(username)) {
			synchronized(lock2){
				try {
					System.out.println("执行username="+username);
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				synchronized (lock1) {
					System.out.println("按照lock2->lock1的顺序执行了！");
				}
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		DeadThread t1 = new DeadThread();
		t1.setUserName("a");
		Thread thread1 = new Thread(t1);
		thread1.start();
		//
		Thread.sleep(1000);
		
		t1.setUserName("b");
		Thread thread2 = new Thread(t1);
		thread2.start();
	}
}
