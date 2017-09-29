package com.feicent.zhang.plugin.mq.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class MQUtil {

	public static Connection createConnection(String url) throws JMSException {
		//1.创建连接工场
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        //2.创建连接
        Connection connection = connectionFactory.createConnection();
        // 
        return connection;
	}
	
	public static MessageProducer createProducer(String url, String queueName) throws JMSException {
		//1.创建连接工场
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        //2.创建连接
        Connection connection = connectionFactory.createConnection();
        //3.启动连接
        connection.start();
        //4.创建会话
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5.创建一个目标
        Destination destination = session.createQueue(queueName);
        //6.创建生产者
        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);//持久化
        
        return producer;
	}
	
	public static void sendMessage(Session session, MessageProducer producer, String message) throws JMSException {
		TextMessage textMessage = session.createTextMessage(message);  
        producer.send(textMessage);  
    }
	
	public static void close(Connection connection) {
		if( null != connection ){
			try {
                connection.close();
            }  catch (JMSException e) {
                //ignored
            }
		}
	}
	
}
