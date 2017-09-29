package com.feicent.zhang.plugin.mq.activemq.spring;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.MessageCreator;

public class MyMessageCreator implements MessageCreator{
	
	private int count; 
	private String strMessage;
	
	public MyMessageCreator(int count , String strMessage) {
		this.count = count;
		this.strMessage = strMessage;
	}
	
	@Override
	public Message createMessage(Session session) throws JMSException {
		return null;
	}
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getStrMessage() {
		return strMessage;
	}

	public void setStrMessage(String strMessage) {
		this.strMessage = strMessage;
	}

}
