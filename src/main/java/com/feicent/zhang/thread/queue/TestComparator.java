package com.feicent.zhang.thread.queue;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class TestComparator {
	
	public static void main(String[] args) {
		try {
			TestWithComparator();
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}
	}
	
    //测试函数代码
    public static void TestWithComparator() throws InterruptedException, IOException{
        AtomicInteger counter = new AtomicInteger();
        //PriorityQueue<Executor> runQueue = new PriorityQueue<Executor>(1000);
        PriorityBlockingQueue<Executor> runQueue = new PriorityBlockingQueue<Executor>(1000, new Comparators());
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream("D:\\priorityQueueComparator.txt"));
        ExecutorService producerPool = Executors.newFixedThreadPool(16);
        long start = System.nanoTime();
        for(int i=0; i < 1000; i++){
            producerPool.execute(new Producer(runQueue, counter, writer));
        }
        producerPool.shutdown();
        while(!producerPool.isTerminated()){
             //wait...
        }
        
        long end = System.nanoTime();
        System.out.println("comparator add time is " + (end - start));
        ExecutorService consumerPool = Executors.newFixedThreadPool(16);
        start = System.nanoTime();
        for(int i=0; i < 16; i++){
            consumerPool.execute(new Consumer(runQueue, counter));
        }
        consumerPool.shutdown();
        while(!consumerPool.isTerminated()){
            ;
        }
        end = System.nanoTime();
        System.out.println("comparator exe time is " + (end - start));
        System.out.println(counter.get());
        writer.close();
    }
    
}

class Consumer implements Runnable {
    private AtomicInteger counter;
    private PriorityBlockingQueue<Executor> runQueue; 
    public Consumer(PriorityBlockingQueue<Executor> runQueue, AtomicInteger counter){
//  private PriorityQueue<Executor> runQueue;
//  public Consumer(PriorityQueue<Executor> runQueue, AtomicInteger counter){
        this.runQueue = runQueue;
        this.counter = counter;
    }
    @Override
    public void run() {
        Executor exe = null;
        while((exe = runQueue.poll()) != null){
            exe.run();
            counter.decrementAndGet();
        }
    }
}
class Producer implements Runnable {
    private AtomicInteger counter;
    private OutputStreamWriter writer;
    
    private PriorityBlockingQueue<Executor> runQueue;
    public Producer(PriorityBlockingQueue<Executor> runQueue, AtomicInteger counter,
            OutputStreamWriter writer){
//  private PriorityQueue<Executor> runQueue;
//  public Producer(PriorityQueue<Executor> runQueue, AtomicInteger counter, 
//      OutputStreamWriter writer){
        this.runQueue = runQueue;
        this.counter = counter;
        this.writer = writer;
    }
    
    @Override
    public void run() {
        Random rand = new Random();
        for(int i=0; i < 1000; i++){
            long timeout = rand.nextInt(10000000);
            Executor exe = new Executor(timeout, writer);
            runQueue.offer(exe);
            counter.incrementAndGet();
        }
    }
}