package com.feicent.zhang.guava.eventbus;

import org.junit.Test;

import com.google.common.eventbus.EventBus;

/**
 * 测试类
 * @author yzuzhang
 *
 */
public class TestEventBus {
    @Test
    public void testReceiveEvent() throws Exception {
    	
        EventBus eventBus = new EventBus("test");
        EventListener listener = new EventListener();
        
        eventBus.register(listener);
        
        eventBus.post(new TestEvent("Hello"));
        eventBus.post(new TestEvent("World"));
        eventBus.post(new TestEvent("Hello World"));
        
        System.out.println("LastMessage:"+listener.getLastMessage());
    }
}
