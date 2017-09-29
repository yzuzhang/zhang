package com.feicent.zhang.guava.eventbus;

import com.google.common.eventbus.Subscribe;

/**
 * EventBus基本用法:
 * 使用Guava之后,如果要订阅消息,就不用再继承指定的接口
 * 只需要在指定的方法上加上@Subscribe注解即可
 * @author yzuzhang
 */
public class EventListener {
    private String lastMessage = null;
    
    @Subscribe
    public void listen(TestEvent event) {
        lastMessage = event.getMessage();
        System.out.println("Receive Message: "+lastMessage);
    }
    
    public String getLastMessage() {      
        return lastMessage;
    }
}
