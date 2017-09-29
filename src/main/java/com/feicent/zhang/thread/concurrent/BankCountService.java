package com.feicent.zhang.thread.concurrent;

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

import com.feicent.zhang.util.SleepUtils;

/**
 * 银行流水处理服务类
 * @author yzuzhang
 * @date 2016年11月21日
 */
public class BankCountService implements Runnable{
	
	private final int threadCount = 4;
	private ThreadLocalRandom random = ThreadLocalRandom.current();
	
	/**
	 * 创建4个屏障类，都处理完之后 执行当前类的run方法
	 */
	private CyclicBarrier barrier = new CyclicBarrier(threadCount, this);
	
	private ExecutorService executor = Executors.newFixedThreadPool(threadCount);
	
	private ConcurrentHashMap<String, Integer> countMap = new ConcurrentHashMap<String, Integer>();
	
	/**
	 * 开启线程池进行计算,4个屏障到了之后 才执行run()函数
	 */
	private void count() {
		System.out.println(">>>>>开始计算>>>>>");
		for (int i = 0; i < threadCount; i++) {
			executor.execute( new Runnable() {
				@Override
				public void run() {
					//计算当前sheet的银行流水，模拟计算
					int value = random.nextInt(10000);
					SleepUtils.sleep(value);
					
					String threadName = Thread.currentThread().getName();
					countMap.put(threadName, value);
					System.out.println("["+threadName+"]计算完成:"+value+", 等待汇总...");
					
					//银行流水计算完成,插入一个屏蔽，等待其他线程的计算
					try {
						barrier.await();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			});
		}
	}
	
	@Override
	public void run() {
		int total = 0;
		System.out.println("开始汇总...");
		//汇总结果
		for(Entry<String,Integer> entry : countMap.entrySet()) {
			total += entry.getValue().intValue();
		}
		
		//将结果输出
		System.out.println("银行总流水==="+ total);
		//关闭线程池
		if (executor != null) {
			executor.shutdown();
			System.out.println(">>>>>计算结束>>>>>");
		}
	}
	
	protected Runnable getJob(){
		return new Runnable() {
			@Override
			public void run() {
				//计算当前sheet的银行流水，模拟计算
				int value = random.nextInt(10000);
				SleepUtils.sleep(value);
				
				String threadName = Thread.currentThread().getName();
				countMap.put(threadName, value);
				System.out.println("["+threadName+"]计算完成:"+value+", 等待汇总...");
				
				//银行流水计算完成,插入一个屏蔽，等待其他线程的计算
				try {
					barrier.await();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
	}
	
	public static void main(String[] args) {
		BankCountService service = new BankCountService();
		service.count();
	}
}
