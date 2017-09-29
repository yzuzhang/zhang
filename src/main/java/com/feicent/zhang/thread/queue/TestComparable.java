package com.feicent.zhang.thread.queue;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class TestComparable {

	public static void main(String[] args) {
		try {
			testWithComparable();
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}
	}
	
	//测试函数代码：
    public static void testWithComparable() throws InterruptedException, IOException{
        AtomicInteger counter = new AtomicInteger();
        PriorityBlockingQueue<ExecutorCmp> runQueue = new PriorityBlockingQueue<ExecutorCmp>(1000);
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream("D:\\priorityQueueComparable.txt"));
        ExecutorService producerPool = Executors.newFixedThreadPool(16);
        long start = System.nanoTime();
        for(int i=0; i < 1000; i++){
            producerPool.execute(new CmpProducer(runQueue, counter, writer));
        }
        producerPool.shutdown();
        while(!producerPool.isTerminated()){
             
        }
        long end = System.nanoTime();
        System.out.println("comparatable add time is " + (end - start));
         
        ExecutorService consumerPool = Executors.newFixedThreadPool(16);
        start = System.nanoTime();
        for(int i=0; i < 16; i++){
            consumerPool.execute(new CmpConsumer(runQueue, counter));
        }
        consumerPool.shutdown();
        while(!consumerPool.isTerminated()){
            ;
        }
        end = System.nanoTime();
        System.out.println("comparatable exe time is " + (end - start));
        System.out.println(counter.get());
        writer.close();
    }
}

class CmpConsumer implements Runnable {
    private AtomicInteger counter;
    private PriorityBlockingQueue<ExecutorCmp> runQueue;  
    public CmpConsumer(PriorityBlockingQueue<ExecutorCmp> runQueue, AtomicInteger counter){
//  private PriorityQueue<Executor> runQueue;
//  public Consumer(PriorityQueue<Executor> runQueue, AtomicInteger counter){
        this.runQueue = runQueue;
        this.counter = counter;
    }
    @Override
    public void run() {
        ExecutorCmp exe = null;
        while((exe = runQueue.poll()) != null){
            exe.run();
            counter.decrementAndGet();
        }
    }
}

class CmpProducer implements Runnable {
    private AtomicInteger counter;
    private OutputStreamWriter writer;
     
    private PriorityBlockingQueue<ExecutorCmp> runQueue;
    public CmpProducer(PriorityBlockingQueue<ExecutorCmp> runQueue, AtomicInteger counter,
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
            ExecutorCmp exe = new ExecutorCmp(timeout, writer);
            runQueue.offer(exe);
            counter.incrementAndGet();
        }
    }
}