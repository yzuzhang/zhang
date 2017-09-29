package com.feicent.zhang.thread.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import com.feicent.zhang.util.SleepUtils;

public class SemaphoreDemo {
	
	private static final int THREAD_COUNT = 30;
	
	//只允许10个线程获得许可证,最大并发数是10
	private static Semaphore semaphore = new Semaphore(10);
	
	private static ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_COUNT);
	
	@SuppressWarnings("unused")
	private static class MyTest implements Runnable{
		private String name;
		
		public MyTest(String name){
			this.name = name;
		}
		
		@Override
		public void run() {
			System.out.println("Hello World!!! "+ name);
		}
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < THREAD_COUNT; i++) {
			threadPool.execute(new Runnable() {
				@Override
				public void run() {
					try {
						//获取许可证
						semaphore.acquire();
						System.out.println("成功获取数据库连接, 开始保存...");
						SleepUtils.sleep(2000);
						//归还许可证
						semaphore.release();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}
		
		if ( threadPool != null ) {
			threadPool.shutdown();
			System.out.println("关闭线程池");
		}
	}

}
