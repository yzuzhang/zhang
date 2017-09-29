package com.feicent.zhang;

import java.util.Arrays;
import java.util.Random;

public class RandomDemo {
	
	public static Random random = new Random();
	
	/**
	 * 运行一万次，1-50随机出现的次数
	 */
	public static void main(String[] args) {
		//进行10000000次试验1
		int k = 1000000;
		//创建数组保存结果
		int brr[]= new int[50];
		//创建数组输出结果
		int crr[]= new int[50];
		for (int y = 0; y < crr.length; y++) {
			crr[y]=y+1;
		}
		System.out.printf("1-50之间所有的数依次为：%s", Arrays.toString(crr));
		System.out.println();
		
		//调用方法	
		long start = System.currentTimeMillis();
		brr = count(k);
		System.out.println("耗时间====="+(System.currentTimeMillis()-start)+"ms");
		System.out.printf("1-50之间所有的数出现的次数依次为：%s", Arrays.toString(brr));
	}

	public static int[] count(int k) {
		//定义长度为50的整形数组
		int[] arr = new int[50];
		//创建随机对象
		
		//循环输出1000000万次1~50之间的随机数
		for (int j = 0; j < k; j++) {
			int a = random.nextInt(50);
			//下标为随机产生的数减1，每产生一次自加
			arr[a]++;
		}
		//返回数组
		return arr;
	}

}
