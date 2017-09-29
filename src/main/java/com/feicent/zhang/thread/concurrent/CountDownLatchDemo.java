package com.feicent.zhang.thread.concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch一般用于某个线程A等待若干个其他线程执行完任务之后，它才执行
 * @author yzuzhang
 * @date 2017年8月17日
 */
public class CountDownLatchDemo {
	
	public static void main(String[] args) {
        final CountDownLatch latch = new CountDownLatch(2);
        
        new Thread(){
            public void run() {
                try {
                   System.out.println("子线程"+Thread.currentThread().getName()+"正在执行");
                   Thread.sleep(3000);
                   System.out.println("子线程"+Thread.currentThread().getName()+"执行完毕");
                   latch.countDown();
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
            };
        }.start();
        
        new Thread(){
            public void run() {
                try {
                    System.out.println("子线程"+Thread.currentThread().getName()+"正在执行");
                    Thread.sleep(5000);
                    System.out.println("子线程"+Thread.currentThread().getName()+"执行完毕");
                    latch.countDown();
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
            };
        }.start();
        
        try {
           System.out.println("等待2个子线程执行完毕...");
           latch.await();
           System.out.println("2个子线程已经执行完毕");
           System.out.println("继续执行主线程");
       } catch (InterruptedException e) {
           e.printStackTrace();
       }
    }
	
}
