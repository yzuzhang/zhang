package com.feicent.zhang.core.singleton;

public class MyThread extends Thread {

	@Override
	public void run() {
		int hashCode = Singleton1.getInstance().hashCode();
		System.out.println(Singleton5.getInstance().hashCode()+", "+hashCode);
	}
	
	public static void main(String[] args) {
		MyThread thread1 = new MyThread();
		MyThread thread2 = new MyThread();
		MyThread thread3 = new MyThread();
		
		thread1.start();
		thread2.start();
		thread3.start();
		
		//Thread.sleep(100);
		System.out.println("start...");
	}
}
