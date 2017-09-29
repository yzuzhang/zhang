package com.feicent.zhang.util;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 线程休眠工具类
 * @author yzuzhang
 */
public class SleepUtils {
	
	public static Random random = new Random();
	
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 随机休眠
	 */
	public static void sleepRandom(int n) {
		int millis = random.nextInt(n);
		SleepUtils.sleep(millis);
	}
	
	/**
	 * 返回无锁的ThreadLocalRandom
	 */
	public static ThreadLocalRandom threadLocalRandom() {
		return ThreadLocalRandom.current();
	}
	
}
