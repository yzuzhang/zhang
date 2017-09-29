package com.feicent.zhang.plugin.mq.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.feicent.zhang.plugin.mq.MQConstants;
import com.feicent.zhang.util.SleepUtils;

/**
 * 
 * @author yzuzhang
 * @date 2017年9月14日
 */
public class TopicProducer {

	private static final String url = MQConstants.MQ_URL;
    private static final String topicName = MQConstants.TOPIC_NAME;
    
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
        Topic destination = session.createTopic(topicName);
        //6.创建生产者
        MessageProducer producer = session.createProducer(destination);
        //7.设置持久化
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        
        System.out.print("开始投递Topic消息...");
        for (int i = 1; i <= 10; i++) {
            //7.创建消息
            TextMessage textMessage = session.createTextMessage("activemq-topic-" + System.currentTimeMillis());
            System.out.println(textMessage.getText());
            producer.send(textMessage);
            SleepUtils.sleepRandom(5000);
        }
        session.commit();
        
        System.out.print("所有消息已经全部发送完了!");
        connection.close();
    }
	
}
