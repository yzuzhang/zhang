package com.feicent.zhang.plugin.mq.activemq.spring;

public class SendMessage {

	private String username;  
    private String password;  
      
    public void setUsername(String user){  
        this.username = user;  
    }  
    public void setPassword(String pass){  
        this.password = pass;  
    }  
      
    public String getUsername(){  
        return this.username;  
    }  
    public String getPassword(){  
        return this.password;  
    }  
}
