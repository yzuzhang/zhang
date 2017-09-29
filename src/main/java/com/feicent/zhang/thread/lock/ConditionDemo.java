package com.feicent.zhang.thread.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.feicent.zhang.util.SleepUtils;

/**
 * 使用Condition实现线程间生产消费模式
 * Object类的wait()、notify()和notifyAll()
 * Conditon中的await()对应Object的wait()
 * Conditon中的signal()对应Object的notify()
 * Conditon中的signalAll()对应Object的notifyAll()
 * http://www.cnblogs.com/tankaixiong/p/6093779.html
 * Synchronized和Lock的区别
 * https://zhidao.baidu.com/question/1575422394936698780.html
 */
public class ConditionDemo {
	
    Object[] queue = new Object[10];//队列
    static int readIndex = 0;//read索引位置
    int writeIndex = 0;
    int dataLen = 0;
    
    final Lock lock = new ReentrantLock();
    final Condition fullCondition = lock.newCondition();
    final Condition emptyCondition = lock.newCondition();
    
    public static void main(String[] args) {
        final ConditionDemo demo = new ConditionDemo();
        
        new Thread() {
            @Override
            public void run() {
                for (int i = 1; i <= 100; i++) {
                    demo.write(i);
                }
            }
        }.start();
        
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    Object obj = demo.read();
                    if (obj != null) {
                        System.out.println("read--"+(Integer) obj);
                    }

                }
            }
        }.start();
        
        SleepUtils.sleep(1000);
        long nanoTime = System.nanoTime();
        System.out.println("nanoTime==="+nanoTime);
    }

    //生产者
    public void write(Object obj) {
        lock.lock();
        
        try {
            if ( dataLen >= queue.length ) {//队列写满了
                System.out.println("队列写满了,等待.....");
                fullCondition.await();
                System.out.println("队列有空位了，唤醒.....");
            }
            
            queue[writeIndex] = obj;
            writeIndex++;
            dataLen++;
            if (writeIndex >= queue.length) {
                writeIndex = 0;
            }
            
            emptyCondition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    //消费者
    public Object read() {
        lock.lock();

        try {
            if ( dataLen <= 0 ) {
                System.out.println("队列空了,等待数据.....");
                emptyCondition.await();
                System.out.println("队列有数据了,唤醒.....");
            }
            
            Object obj = queue[readIndex];
            readIndex++;
            dataLen--;
            if (readIndex >= queue.length) {
                readIndex = 0;
            }
            
            fullCondition.signal();
            return obj;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return null;
    }
}
