package com.feicent.zhang.guava.eventbus;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Subscribe;

/**
 * @author yzuzhang
 * 如果EventBus发送的消息都不是订阅者关心的称之为Dead Event
 * 来源:http://www.cnblogs.com/peida/p/EventBus.html
 */
public class DeadEventListener {
    boolean notDelivered = false;  
    
    @Subscribe  
    public void listen(DeadEvent event) {  
        notDelivered = true;  
    }  
    
    public boolean isNotDelivered() {  
        return notDelivered;  
    }  
}
