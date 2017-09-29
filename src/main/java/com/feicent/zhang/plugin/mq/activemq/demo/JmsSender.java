package com.feicent.zhang.plugin.mq.activemq.demo;

import javax.jms.*;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import com.feicent.zhang.plugin.mq.MQConstants;
import com.feicent.zhang.plugin.mq.activemq.MQUtil;
import com.feicent.zhang.util.SleepUtils;

/**
 * 消息发送者
 * @author yzuzhang
 * @date 2017年9月14日
 */
public class JmsSender {
	
	private Session session = null;
    private Connection connection = null;
    private MessageProducer producer = null;
    protected Destination destination = null;
    private static final int SEND_NUMBER = 5;
    private ConnectionFactory connectionFactory = null;
    
    public void doSend() {
        // 构造ConnectionFactory实例对象，此处采用ActiveMq来实现
        connectionFactory = new ActiveMQConnectionFactory(
                ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD,
                MQConstants.MQ_URL);// ActiveMQ默认使用的TCP连接端口是61616
        
        try{
            // 构造从工厂得到连接对象
            connection = connectionFactory.createConnection();
            connection.start();
            
            // 获取操作连接
            session = connection.createSession(Boolean.TRUE,  Session.AUTO_ACKNOWLEDGE);
            
            /**
            * 第一种方式：Queue
            */
            //destination = session.createQueue(MQConstants.QUEUE_NAME);// "xkey"可以取其他的。
            //producer = session.createProducer(destination); // 得到消息生成者【发送者】
            
            /**
             * 第二种方式：Topic
             */          
             Topic topic = session.createTopic(MQConstants.TOPIC_NAME);
             producer = session.createProducer(topic);
            
            // 是否持久化
            //producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);//持久化
            
            // 调用方法 发送消息
            sendMessage(session, producer);
            session.commit();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
        	MQUtil.close(connection);
        }
    }
    
    private void sendMessage(Session session, MessageProducer producer) throws JMSException {
        for (int i = 1; i <= SEND_NUMBER; i ++) {  
            TextMessage message = session.createTextMessage("activemq-queue-msg-"+ System.currentTimeMillis());  
            // 发送消息
            System.out.println("发送消息:" + message.getText());  
            producer.send(message);
            SleepUtils.sleepRandom(3000);
        }  
    }

    /**
     * Topic模式 启动步骤
     * 1.先启动Sender类->再启动Receiver类->结果无任何记录被订阅
     * 2.先启动Receiver类，让Receiver在相关主题上进行订阅 ->停止Receiver类，再启动Sender类 ->
     *   待Sender类运行完成后，再启动Receiver类 -> 结果发现相应主题的信息被订阅
     * 3.http://www.open-open.com/lib/view/open1371822097588.html
     * @param args
     */
    public static void main(String[] args) {
        JmsSender sender = new JmsSender();
        sender.doSend();
    }
    
}
