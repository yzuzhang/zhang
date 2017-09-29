package com.feicent.zhang.core.singleton;

/**
 * 同步饿汉式
 * @author yzuzhang
 * 问题: 排队严重  严重影响效率
 * 每个用户在计算机里面都是一个单独的线程 
 * 当 一个线程 调用对象以后加了一把锁，别人就无法访问
 */
public class Singleton2 {
	//构造方法私有化
	private Singleton2(){
		
	}
	//先创建一个 静态变量
    private static Singleton2 instance = null;
    
    public static synchronized Singleton2 getInstance(){  
        if(instance == null){//如果为空  我返回一个新的  
        	instance = new Singleton2();
        }
        return instance;
    }
}
