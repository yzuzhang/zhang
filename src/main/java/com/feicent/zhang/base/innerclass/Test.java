package com.feicent.zhang.base.innerclass;

/*
 * http://www.cnblogs.com/swiftma/p/5619071.html
 */
public class Test {

	public static void main(String[] args) {
		
		testOuter2();
	}
	
	/*
	 * 静态内部类
	 */
	public static void testOuter(){
		Outer.StaticInner inner = new Outer.StaticInner();
    	inner.innerMethod();
    }
	
	/*
	 *成员内部类 
	 */
	public static void testOuter2(){
		Outer2 outer = new Outer2();
		Outer2.Inner inner = outer.new Inner();
		//com.jd.ptest.common.InnerClass.Outer2.Inner inner1 = outer.new Inner();
		inner.innerMethod();
    }
}
