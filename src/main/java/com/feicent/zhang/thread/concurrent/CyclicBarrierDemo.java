package com.feicent.zhang.thread.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import com.feicent.zhang.util.SleepUtils;

/**
 * 同步屏蔽
 * CyclicBarrier的某个线程运行到某个点上之后,该线程即停止运行
 * 直到所有的线程都到达了这个点,所有线程才重新运行
 * @author yzuzhang
 * @date 2016年11月21日
 */
public class CyclicBarrierDemo {

	private static CyclicBarrier c = new CyclicBarrier(2);//设置2个线程到达屏蔽点
	
	/**
	 * CountDownLatch的计数器只能使用一次, 而CyclicBarrier的计数器
	 * 可以使用reset()方法重置,能处理更为复杂的业务场景。例如:如果计算发送错误
	 * 则重置计数器,并让线程重新执行一次。
	 */
	public static void main(String[] args) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					c.await();//第一个
					SleepUtils.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (BrokenBarrierException e) {
					e.printStackTrace();
				}
				// 集合点之后，继续执行
				System.out.println(1);
			}
		}).start();
		
		try {
			c.await();//第二个
			System.out.println(2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(3);
	}
}
