package com.feicent.zhang.thread.future;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 本篇说明的是Callable和Future,它俩很有意思的,一个产生结果,一个拿到结果
 * http://blog.csdn.net/ghsau/article/details/7451464
 */
public class CallableAndFuture {
	
	public static void main(String[] args) {
		//futureTask();
		//executorService();
		completionService();
    }
	
	public static void futureTask() {
        Callable<Integer> callable = new Callable<Integer>() {
        	@Override
            public Integer call() throws Exception {
            	int millis = new Random().nextInt(6000);
                Thread.sleep(millis);// 可能做一些事情
                return millis;
            }
        };
        
        try {
        	System.out.println("FutureTask start");
        	FutureTask<Integer> future = new FutureTask<Integer>(callable);
            new Thread(future).start();
            
            System.out.println("正在处理一些其他事情...");
            //
        	Integer result = future.get(6000, TimeUnit.MILLISECONDS);
        	System.out.println("异步返回的结果："+result);
            System.out.println("FutureTask end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
			e.printStackTrace();
		}
    }
	
	/**
	 * 另一种方式使用Callable和Future
	 * 通过ExecutorService的submit方法执行Callable，并返回Future
	 */
	@SuppressWarnings("unused")
	public static void executorService() {
        ExecutorService threadPool = Executors.newSingleThreadExecutor();
        try {
        	Callable<Integer> callable = new Callable<Integer>() {
                @Override
            	public Integer call() throws Exception {
                	System.out.println("ExecutorService start...");
                	Thread.sleep(1000);// 可能做一些事情
                    return new Random().nextInt(10000);
                }
            };
        	Future<Integer> future = threadPool.submit(new Callable<Integer>() {
                @Override
            	public Integer call() throws Exception {
                	System.out.println("ExecutorService start...");
                	Thread.sleep(1000);// 可能做一些事情
                    return new Random().nextInt(10000);
                }
            });
            System.out.println("ExecutorService future :"+future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
        	threadPool.shutdown();
        }
    }
	
	/**
	 * 执行多个带返回值的任务，并取得多个返回值
	 */
	public static void completionService() {
		final int count = 5; 
        ExecutorService threadPool = Executors.newCachedThreadPool();
        CompletionService<Integer> cs = new ExecutorCompletionService<Integer>(threadPool);
        for(int i = 1; i < count; i++) {
            final int taskID = i;
            cs.submit(new Callable<Integer>() {
            	@Override
                public Integer call() throws Exception {
            		int millis = new Random().nextInt(10*1000);
            		System.out.println("Threrad-"+taskID+" sleep "+millis);
                	Thread.sleep(millis);
                    return taskID;
                }
            });
        }
        // 可能做一些事情
        // TO DO something
        
        for(int i = 1; i < count; i++) {
            try {
            	Future<Integer> future = cs.take();
                System.out.println("Threrad-"+ future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        threadPool.shutdown();
    }
	
}
