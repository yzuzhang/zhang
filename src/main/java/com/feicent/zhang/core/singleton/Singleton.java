package com.feicent.zhang.core.singleton;

/**
 * 懒汉式
 * 重大缺陷：线程不安全
 * @author yzuzhang
 * @data 2016-06-21
 */
public class Singleton {
	//私有化构造函数
    private Singleton(){
        
    }
    private static Singleton instance = null;
    
    //静态工厂方法
    public static Singleton getInstance(){
        if(null == instance){
        	instance = new Singleton();
        }
        return instance;
    }
}
