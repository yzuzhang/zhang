package com.feicent.zhang.thread;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * http://www.cnblogs.com/clarechen/p/4558825.html
 * @author yzuzhang
 * @date 2016年11月23日
 */
public class ShutDownTest {
	
    public static void main(String[] args) {
        try {
            testShutDown();
            testShutDowNow();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 关闭操作不影响任务执行
     */
    public static void testShutDown() throws InterruptedException {
    	//并发执行2个任务的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 5; i++) {
            executorService.execute(getTask(i + 100));
        }
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.DAYS);
        System.out.println("shutDown->all thread shutdown");
    }
    
    /**
     * 停掉所有的线程任务
     */
    public static void testShutDowNow() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 5; i++) {
            executorService.execute(getTask(i + 200));
        }
        executorService.shutdownNow();
        executorService.awaitTermination(1, TimeUnit.DAYS);
        System.out.println("shutdownNow->all thread shutdown");
    }

    public static Runnable getTask(int threadNo) {
        final Random rand = new Random();
        final int no = threadNo;
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(no + "-->" + rand.nextInt(10));
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("thread-" + no + ": " + e);
                }
            }
        };
        return task;
    }

}
