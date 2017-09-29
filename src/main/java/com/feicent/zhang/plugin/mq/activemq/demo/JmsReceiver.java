package com.feicent.zhang.plugin.mq.activemq.demo;

import javax.jms.*;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import com.feicent.zhang.plugin.mq.MQConstants;
import com.feicent.zhang.plugin.mq.activemq.MQUtil;

/**
 * 消息接收者
 * 发布/订阅类型
 * http://www.open-open.com/lib/view/open1371822097588.html
 * @author yzuzhang
 * @date 2017年9月14日
 */
public class JmsReceiver {
	
    private ConnectionFactory connectionFactory = null;
    private Connection connection = null;
    private Session session = null;
    private MessageConsumer consumer = null;
    protected Destination destination = null;
    
    public void doReceiver() {
    	
        connectionFactory = new ActiveMQConnectionFactory(
                ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD,
                MQConstants.MQ_URL); // ActiveMQ默认使用的TCP连接端口是61616
        try {
            // 构造从工厂得到连接对象
            connection = connectionFactory.createConnection();
            //connection.setClientID("clientID_001");
            connection.start();
            
            // 获取操作连接
            session = connection.createSession(Boolean.TRUE,  Session.AUTO_ACKNOWLEDGE); 
            
            /**
            * 第一种方式：Queue
            */
            //destination = session.createQueue(MQConstants.QUEUE_NAME);
            //consumer = session.createConsumer(destination);
            
            /**
             * 第二种方式：Topic
             */
            Topic topic = session.createTopic(MQConstants.TOPIC_NAME);
            consumer = session.createConsumer(topic);
            
            System.out.println("开始接收Topic消息...");
            while (true) { 
                //设置接收者接收消息的时间，为了便于测试，这里定为10s
                TextMessage message = (TextMessage) consumer.receive(10*1000);  
                if ( null != message ) {  
                    System.out.println("Receiver " + message.getText());  
                } else {  
                    break;  
                }  
            }  
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            MQUtil.close(connection);
        }
        System.out.println("Topic消息接收完毕!");
    }
    
    public static void main(String[] args) {
        JmsReceiver receiver = new JmsReceiver();
        receiver.doReceiver();
    }
    
}
