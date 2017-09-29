package com.feicent.zhang.util.sequence.test;

import org.junit.Test;

import com.feicent.zhang.util.sequence.Sequence;

public class SequenceTest1 {

	@Test
	public void name() {
		try {
			int times = 0, maxTimes = 10000*10;
			Sequence sequence = new Sequence(0, 0);
			for (int i = 0; i < maxTimes; i++) {
				long id = sequence.nextId();
				System.out.println(id);
				if(id%2==0){
					times++;
				}
				//Thread.sleep(10);
			}
			System.out.println("偶数:" + times + ",奇数:" + (maxTimes - times) + "!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
