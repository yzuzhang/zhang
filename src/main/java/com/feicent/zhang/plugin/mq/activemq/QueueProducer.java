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

import com.feicent.zhang.plugin.mq.MQConstants;
import com.feicent.zhang.util.SleepUtils;

/**
 * 消息生产者
 * 队列模式：在点对点的传输方式中，消息数据被持久化，每条消息都能被消费，没有监听QUEUE地址也能被消费，数据不会丢失，一对一的发布接受策略，保证数据完整。
 * 主题模式：在发布订阅消息方式中，消息是无状态的，不保证每条消息被消费，只有监听该TOPIC地址才能收到消息并消费，否则该消息将会丢失。一对多的发布接受策略，可以同时消费多个消息。
 * @author yzuzhang
 * @date 2017年9月14日
 */
public class QueueProducer {

    private static final String url = MQConstants.MQ_URL;
    private static final String queueName = MQConstants.QUEUE_NAME;

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
        //6.创建生产者
        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);//持久化
        
        System.out.println("开始投递消息...");
        for (int i = 1; i <= 10; i++) {
            //7.创建消息
            TextMessage textMessage = session.createTextMessage("activemq-queue-msg-" + System.currentTimeMillis());
            System.out.println(textMessage.getText());
            producer.send(textMessage);
            SleepUtils.sleepRandom(5000);
        }
        System.out.print("所有消息已经全部发送完了!");
        connection.close();
    }
}
