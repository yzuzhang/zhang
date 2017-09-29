package com.feicent.zhang.core.singleton;

/**
 * 内部静态类
 * @author yzuzhang
 * 懒汉式：通过静态内部类实现方式，既实现了线程安全，又提高了效率
 */
public class Singleton5 {
	
	private Singleton5(){    
    }
	
    public static Singleton5 getInstance(){
        return Instance.singleton;//当需要返回实例的时候，初始化创建静态实例对象。
    }
    
    private static class Instance{    
        private static final Singleton5 singleton = new Singleton5();
    }
}
