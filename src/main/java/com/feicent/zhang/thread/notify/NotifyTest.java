package com.feicent.zhang.thread.notify;

/**
 * 任何一个时刻，对象的控制权（monitor）只能被一个线程拥有,JVM基于多线程，默认情况下不能保证运行时线程的时序性
 * 无论是执行对象的wait、notify还是notifyAll方法，必须保证当前运行的线程取得了该对象的控制权（monitor）
 * 如果在没有控制权的线程里执行对象的以上三种方法，就会报java.lang.IllegalMonitorStateException异常。
 * 来源:http://www.cnphp6.com/archives/62258
 * @author yzuzhang
 * @date 2016年11月23日
 */
public class NotifyTest {
    private static String flag[] = { "true" };  
    
    static class NotifyThread extends Thread {  
        public NotifyThread(String name) {  
            super(name);  
        }  
  
        public void run() {  
        	System.out.println("************************");
            try { 
            	System.out.println(getName()+" sleep 3000ms");
                sleep(3000);  
            } catch (InterruptedException e) {  
                e.printStackTrace();  
            }  
            synchronized (flag) {  
                flag[0] = "false";  
                flag.notifyAll();  
                System.out.println(getName()+" notifyAll...");
            } 
            System.out.println("************************");
        }  
    };  
  
    class WaitThread extends Thread {  
        public WaitThread(String name) {  
            super(name);  
        }  
  
        public void run() {  
            synchronized (flag) {  
                while ( !"false".equals(flag[0]) ) {  
                    System.out.println(getName() + " begin waiting!");  
                    long waitTime = System.currentTimeMillis();  
                    try {  
                    	// 释放控制权
                        flag.wait();  
                    } catch (InterruptedException e) {  
                        e.printStackTrace();  
                    }  
                    waitTime = System.currentTimeMillis() - waitTime;  
                    System.out.println(getName()+" wait time :" + waitTime);  
                }    
            }  
        }  
    }  
    
    public static void main(String[] args) throws InterruptedException {  
        System.out.println("Main Thread Run!");  
        NotifyTest test = new NotifyTest();  
          
        WaitThread waitThread01 = test.new WaitThread("waiter01");  
        WaitThread waitThread02 = test.new WaitThread("waiter02");  
        WaitThread waitThread03 = test.new WaitThread("waiter03");
        waitThread01.start();  
        waitThread02.start();  
        waitThread03.start(); 
        
        NotifyThread notifyThread = new NotifyThread("notify01");
        notifyThread.start();  
        
    }  
}
