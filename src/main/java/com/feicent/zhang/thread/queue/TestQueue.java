package com.feicent.zhang.thread.queue;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;

/**
 * 使用PriorityBlockingQueue时元素实现Comparable接口进行排序性能上会高一些
 * 在ThreadPoolExecutor中使用PriorityBlockingQueue时，要封装Runnable并实现Comparable接口
 * @author yzuzhang
 * @date 2017年1月10日
 */
public class TestQueue {
	
	public static void main(String[] args) throws InterruptedException {
		BlockingQueue<Runnable> configQueue = new PriorityBlockingQueue<Runnable>();
        ThreadPoolExecutor stpe = new ThreadPoolExecutor(1, 10, 1000, TimeUnit.MILLISECONDS, configQueue);
        
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Random rand = new Random();
        for(int i = 0; i < 1000; i++){
            int order = rand.nextInt(1000);
            Thread.sleep(1);
            stpe.execute(new SafTask(order));
        }
        stpe.shutdown();
        
        stopWatch.stop();
        System.out.println("stopWatch.getTime---"+stopWatch.getTime());
	}
}

class SafTask implements Runnable, Comparable<SafTask> {
    
    private int order;
     
    public SafTask(int order){
        this.order = order;
    }
     
    public void run() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(order);
    }
    
    @Override
    public int compareTo(SafTask other) {
        return this.order - other.order;
    }
}