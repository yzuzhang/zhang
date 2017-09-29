package com.feicent.zhang.thread.notify;

public class RunTest {

	public static void main(String[] args) throws InterruptedException {
		Object lock = new Object();
		ThreadA a = new ThreadA(lock);
		a.start();
		//
		Thread.sleep(50);
		ThreadB b = new ThreadB(lock);
		b.start();
	}
}
