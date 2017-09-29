package com.feicent.zhang.util.sequence.test;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;

import com.feicent.zhang.util.sequence.Sequence;

public class TestSequence {

	public static void main(String[] args) {
		Set<Long> set = new HashSet<Long>();
		int maxTimes = 10000 * 1000;
		Sequence sequence = new Sequence(0, 0);
		long start = System.currentTimeMillis();
		for (int i = 0; i < maxTimes; i++) {
			set.add(sequence.nextId());
		}
		
		long total = System.currentTimeMillis()-start;
		System.out.println("耗时："+total+"ms, TPS==="+(1000*maxTimes/total)+"/s");
		//耗时：9090ms,  TPS===155122/s
		//耗时：10036ms, TPS===140500/s
		if(maxTimes != set.size()){
			System.out.println("有重复的");
		}
		Assert.assertEquals(maxTimes, set.size());
	}
	
}
