package com.feicent.zhang.plugin.mq.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.feicent.zhang.plugin.mq.MQConstants;
import com.feicent.zhang.util.SleepUtils;

/**
 * 点对点模式(point to point)
 * http://www.cnblogs.com/xsnd/p/7515602.html
 * @author yzuzhang
 * @date 2017年9月14日
 */
public class QueueConsumer {
	
	public static final String url = MQConstants.MQ_URL;
    public static final String queueName = MQConstants.QUEUE_NAME;

    public static void main(String[] args) throws JMSException {
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
        //Destination destination = session.createTopic(MQConstants.TOPIC_NAME);
        //6.创建消费者
        MessageConsumer consumer = session.createConsumer(destination);
        System.out.println("开始接收消息...");
        
        //7.创建一个监听器
        /*consumer.setMessageListener(new MessageListener() {
        	@Override
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println("接收消息--->" + textMessage.getText());
                    //SleepUtils.sleepRandom(3000);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });*/
        
        while (true) {  
            //设置接收者接收消息的时间，为了便于测试，这里定为10s
            TextMessage message = (TextMessage) consumer.receive(10*1000);  
            if ( null != message ) {
                System.out.println("接收消息--->" + message.getText());
                SleepUtils.sleepRandom(3000);
            } else {  
                break;  
            }  
        }
        
        consumer.close();
        session.close();
        connection.close();
    }
    
}