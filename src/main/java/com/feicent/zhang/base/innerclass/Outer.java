package com.feicent.zhang.base.innerclass;

import org.junit.Test;

/**
 * 静态内部类
 * 外部类为Outer，静态内部类为StaticInner带有static修饰符。
 * 语法上，静态内部类除了位置放在别的类内部外，它与一个独立的类差别不大
 * 可以有静态变量、静态方法、成员方法、成员变量、构造方法等。
 * @author yzuzhang
 */
public class Outer {
	private static int shared = 100;
	 
	/**
	 * 静态内部类与外部类的联系也不大(与后面其他内部类相比)
	 * 它可以访问外部类的静态变量和方法:如innerMethod直接访问shared变量
	 * 但不可以访问实例变量和方法。在类内部，可以直接使用内部静态类，如test()方法所示
	 */
    public static class StaticInner {
        public void innerMethod(){
        	//访问Outer的静态变量
            System.out.println("inner " + shared);
        }
    }
    
    @Test
    public void test(){
    	//public静态内部类可以被外部使用，只是需要通过"外部类.静态内部类"的方式使用
    	Outer.StaticInner inner = new Outer.StaticInner();
    	inner.innerMethod();
    	
    	//内部调用
    	StaticInner si = new StaticInner();
    	si.innerMethod();
    }
}
