package com.feicent.zhang.guava.eventbus;

import org.junit.Test;
import com.google.common.eventbus.EventBus;

/**
 * DeadEventListener的测试类
 * @author yzuzhang
 */
public class DeadEventListenersTest {
	@Test
	public void testDeadEventListeners() throws Exception {

		EventBus eventBus = new EventBus("test");
		DeadEventListener deadEventListener = new DeadEventListener();
		eventBus.register(deadEventListener);

		eventBus.post(new TestEvent("zhang"));
		eventBus.post(new TestEvent("lei"));
		
		System.out.println("deadEvent:" + deadEventListener.isNotDelivered());
		
	}
}
