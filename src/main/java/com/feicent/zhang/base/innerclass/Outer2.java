package com.feicent.zhang.base.innerclass;

import org.junit.Test;

/*
 * 成员内部类
 * 成员内部类没有static修饰符，少了一个static修饰符，但含义却有很大不同
 */
public class Outer2 {
	private int a = 100;
    
    public class Inner {
        public void innerMethod(){
            System.out.println("成员内部类: 静态变量== " +a);
            Outer2.this.action();//这种写法一般在重名的情况下使用，没有重名的话，"外部类.this."是多余的。
        }
    }
    
    private void action(){
        System.out.println("action");
    }
    
    @Test
    public void test(){
        Inner inner = new Inner();
        inner.innerMethod();
    }

}
