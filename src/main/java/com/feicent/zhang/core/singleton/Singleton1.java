package com.feicent.zhang.core.singleton;

/**
 * 饿汉式：类初始化时，实例化
 * @author yzuzhang
 * 类初始化时开销大，线程安全
 */
public class Singleton1 {
	
	private Singleton1(){
        
    }
	
    private static final Singleton1 singleton = new Singleton1();
    public static Singleton1 getInstance(){
        return singleton;
    }
}
