package com.feicent.zhang.thread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池
 * @author yzuzhang
 * @date 2016年11月11日
 */
public class Threadpool {
    private static volatile ExecutorService threadpool;
    private final static int THREAD_COUNT = 5; //线程池数量
    
    private Threadpool() {
    	
    }
    
    private static class InnerClass{
    	private static Threadpool pool = new Threadpool();
    }
    
    public static Threadpool getThreadpool() {
    	return InnerClass.pool;
    }
    
    public static synchronized ExecutorService getInstance() {
        if (null == threadpool) {
            threadpool = Executors.newFixedThreadPool(THREAD_COUNT);
        }
        return threadpool;
    }
    
    public static ExecutorService getInstance2() {
        if (null == threadpool) {
        	synchronized (threadpool) {
				if (null == threadpool) {
					threadpool = Executors.newFixedThreadPool(THREAD_COUNT);
				}
			}
        }
        return threadpool;
    }
}
