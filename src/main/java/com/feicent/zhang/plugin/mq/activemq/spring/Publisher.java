package com.feicent.zhang.plugin.mq.activemq.spring;

import java.util.Scanner;  
import javax.jms.Destination;  
import org.springframework.jms.core.JmsTemplate;  
  
public class Publisher {
	
    private JmsTemplate template;  
    private Destination[] destinations;  
    private SendMessage sendMessage;  
   
    public void chart()  
    {  
        boolean chart = true;  
        int count = 0;  
        while(chart)  
        {  
            count ++;  
            @SuppressWarnings("resource")
			Scanner cin=new Scanner(System.in);  
            System.out.println("输入聊天内容，输入N停止聊天");  
            String text=cin.nextLine();  
            if(text.equals("N"))  
            {  
                chart = false;  
            }  
            System.out.println("我："+text);  
            sendChartMessage(count,text);  
        }  
   
    }  
    @SuppressWarnings("resource")
	public void sendMsgCon(){  
        Scanner cin=new Scanner(System.in);  
        String username = cin.nextLine();  
        String password = cin.nextLine();  
        this.sendMessage.setUsername(username);  
        this.sendMessage.setPassword(password);  
        sendConvertor(this.sendMessage);  
    }  
      
    public void sendConvertor(SendMessage sendMsg){  
        template.convertAndSend(destinations[0],sendMsg);  
    }  
      
    protected void sendChartMessage(int count , String strMessage) {  
        MyMessageCreator creator = new MyMessageCreator(count,strMessage);  
        template.send(destinations[0], creator);  
    }  
   
    public JmsTemplate getJmsTemplate() {  
        return template;  
    }  
   
    public void setJmsTemplate(JmsTemplate template) {  
        this.template = template;  
    }  
   
    public Destination[] getDestinations() {  
        return destinations;  
    }  
   
    public void setDestinations(Destination[] destinations) {  
        this.destinations = destinations;  
    }  
      
    public void setSendMessage(SendMessage sendMsg){  
        this.sendMessage = sendMsg;  
    }  
    public SendMessage getSendMessage(){  
        return this.sendMessage;  
    }  
    
}  
