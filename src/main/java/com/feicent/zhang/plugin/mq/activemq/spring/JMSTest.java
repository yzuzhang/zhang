package com.feicent.zhang.plugin.mq.activemq.spring;

import org.fusesource.mqtt.cli.Publisher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

/**
 * Spring + JMS + ActiveMQ实现简单的消息队列（监听器异步实现）
 * http://blog.csdn.net/acceptedxukai/article/details/7775746
 * @author yzuzhang
 * @date 2017年9月14日
 */
public class JMSTest {

    @SuppressWarnings({ "resource", "unused" })
	public static void main(String[] args) {  
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-activemq.xml");  
        
        /**Sender sender = (Sender)context.getBean("sender"); 
        sender.SendInfo(); 
        Receiver receiver = (Receiver)context.getBean("receiver"); 
        try { 
            System.out.println(receiver.receiverInfo()); 
             
        } catch (JMSException e) { 
            // TODO Auto-generated catch block 
            e.printStackTrace(); 
        }*/  
        
        Publisher pub = (Publisher)context.getBean("publisher");  
        DefaultMessageListenerContainer consumer =   
            (DefaultMessageListenerContainer)context.getBean("consumer");  
        consumer.start();  
        
        //pub.sendMsgCon();  
        //pub.chart();  
    }  
}
