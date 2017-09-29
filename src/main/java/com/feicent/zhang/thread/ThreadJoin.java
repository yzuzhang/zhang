package com.feicent.zhang.thread;

import com.feicent.zhang.util.SleepUtils;

/**
 * Thread中join()方法是让调用该方法的主线程执行run()时暂时卡住
 * 等待run()执行完成后， 主线程再调用执行join()后面的代码
 * @author yzuzhang
 *
 */
public class ThreadJoin {
	
	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread(new ThreadTesterA());
		Thread t2 = new Thread(new ThreadTesterB());
		
		t1.start();
		t1.join(); // wait t1 to be finished
		
		t2.start();
		t2.join(); // in this program, this may be removed
		
		System.out.println("任务执行1...");
		System.out.println("任务执行2...");
		Thread.sleep(1000);
		System.out.println("任务执行完...");
	}
}

class ThreadTesterA implements Runnable {

	private int counter;

	public void run() {
		while (counter <= 10) {
			SleepUtils.sleep(200);
			System.out.println("ThreadTesterA = " + counter);
			counter++;
		}
	}
}

class ThreadTesterB implements Runnable {

	private int i;

	public void run() {
		while (i <= 10) {
			System.out.println("Thread-B = " + i);
			i++;
		}
	}
}
