package com.feicent.zhang.core.singleton;

/**
 * 如果两个线程同时判断了instance是空 
 * 进去了 第一个线程创建的疏忽,锁住了,第二个需要瞪大
 * 第一个线程创建完了之后,本来是第二个,我不需要再创建了
 * 但是他已经在判断里面了,所以又创建了一个 ,所以说,单例模式没有成功
 * @author yzuzhang
 *
 */
public class Singleton3 {
	//构造方法私有化
    private static Singleton3 instance = null;
    
    public static Singleton3 getInstance() {
        if(instance == null){//如果两个或多个线程同时进入这里,判断完成后都为空,那么会创建好多对象吧,单例模式就失效了
	        synchronized(Singleton3.class){//我现在对整个类锁了 
	        	instance = new Singleton3();
	        }
        } 
        return instance;
    }
}
