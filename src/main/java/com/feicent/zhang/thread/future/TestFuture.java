package com.feicent.zhang.thread.future;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.feicent.zhang.util.SleepUtils;

public class TestFuture implements Runnable {
	
	List<Future<String>> resultList = new ArrayList<Future<String>>();
	
	public void run() {
		MyJob t2 = new MyJob();
		ExecutorService exeService = Executors.newSingleThreadExecutor();
		//exeService = Executors.newFixedThreadPool(1);
		//exeService = Executors.newCachedThreadPool();
		Future<String> future = exeService.submit(t2);
		resultList.add(future);
		
		//获取执行结果
         try {
        	 for (Future<String> fs : resultList) {
        		 String get = fs.get();
        		 System.out.println("result: "+get); //打印各个线程（任务）执行的结果
        	 }
         } catch (Exception e) {
         	System.out.println("TestFuture error: "+e.getMessage());
         } finally {
            //启动一次顺序关闭，执行以前提交的任务，但不接受新任务。如果已经关闭，则调用没有其他作用。
     		exeService.shutdown();
     		System.out.println("ThreadPool close.");
         }
	}
	
	public static void main(String[] args) {
		TestFuture t1 =new TestFuture();
		Thread th1 = new Thread(t1);
		th1.start();
		System.out.println("start...");
	}
}

class MyJob implements Callable<String>{
	
	@Override
	public String call() {
		String name = Thread.currentThread().getName();
		for(int i = 0 ;i<100;i++){
			System.out.println(Thread.currentThread().getName()+": i="+i);
			if( i == 50 ){
				//throw new RuntimeException(name+" 执行出错,任务停止!");
			}
			SleepUtils.sleep(150);
		}
		
		return name +" over";
	}
	
}
