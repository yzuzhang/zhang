package com.feicent.zhang.guava.eventbus;

import com.google.common.eventbus.Subscribe;

/**
 * MultiListener的使用
 * 只需要在要订阅消息的方法上加上@Subscribe注解即可实现对多个消息的订阅
 * @author yzuzhang
 */
public class MultipleListener {
	private Long lastLong;
    private Integer lastInteger;
    
    @Subscribe  
    public void listenInteger(Integer event) {  
        lastInteger = event; 
        System.out.println("event Integer:"+lastInteger);
    }  
   
    @Subscribe  
    public void listenLong(Long event) {  
        lastLong = event; 
        System.out.println("event Long:"+lastLong);
    }  
   
    public Integer getLastInteger() {  
        return lastInteger;  
    }  
   
    public Long getLastLong() {  
        return lastLong;  
    }
}
