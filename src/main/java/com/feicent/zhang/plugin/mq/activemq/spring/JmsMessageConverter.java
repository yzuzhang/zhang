package com.feicent.zhang.plugin.mq.activemq.spring;

import javax.jms.JMSException;  
import javax.jms.MapMessage;  
import javax.jms.Message;  
import javax.jms.Session;

import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
  
public class JmsMessageConverter implements MessageConverter{  
  
    private SendMessage sendMessage;  
    public void setSendMessage(SendMessage sendMsg){  
        this.sendMessage = sendMsg;  
    }  
      
    public Object fromMessage(Message message) throws JMSException,  
            MessageConversionException {  
        MapMessage  mapmessage= (MapMessage)message;   
        this.sendMessage.setUsername(mapmessage.getString("username"));  
        this.sendMessage.setPassword(mapmessage.getString("password"));  
        System.out.println("First");  
        return sendMessage;  
    }  
  
    public Message toMessage(Object arg0, Session session) throws JMSException,  
            MessageConversionException {  
    	this.sendMessage = (SendMessage)arg0;  
        MapMessage  mapmessage= (MapMessage) session.createMapMessage();  
        mapmessage.setString("username", this.sendMessage.getUsername());  
        mapmessage.setString("password", this.sendMessage.getPassword());  
        System.out.println("Second");  
        return mapmessage;  
    }  
  
}
