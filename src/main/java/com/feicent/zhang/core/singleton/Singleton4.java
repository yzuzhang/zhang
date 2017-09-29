package com.feicent.zhang.core.singleton;

/**
 * 双重检测懒汉式
 * 线程安全,效率高,内存占用低
 * @author yzuzhang
 */
public class Singleton4 {
	// 构造方法私有化  volatile关键字
    private static volatile Singleton4 instance = null;// 私有的静态变量
    
    public static Singleton4 getInstance() {// 获取当前类对象的方法
        if (null == instance) {
        	synchronized (Singleton4.class) {// 现在是对整个类锁住了
            if (null == instance) {//两个同时进入if判断,到这里就会判断不通过,就会直接返回一个对象,不会再去创建新的对象
            	instance = new Singleton4();
            }
        }
        }
        return instance;
    }
}
