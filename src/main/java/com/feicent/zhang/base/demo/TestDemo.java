package com.feicent.zhang.base.demo;

/*
 * 正确的使用abstract和interface有利提高代码可读性
 * 当人们看到extend时就会知道这是一个is a什么
 * 看到implement时可以知道这个类有什么特性like a
 * http://www.cnblogs.com/panxiaochun/p/5613282.html
 */
public class TestDemo {
	
	public static void main(String[] args) {
		Man asiaMan = new AsiaMan();
		//AsiaMan asiaMan = new AsiaMan();//亚洲人其他的功能
	    AmericanSuperMan americanSuperMan = new AmericanSuperMan();
	    
	    asiaMan.eat();
	    americanSuperMan.eat();   
	    americanSuperMan.superPower();
	    
	    Animal pigeon = new Pigeon();
	    pigeon.fly();
	    Pigeon pigeon1 = new Pigeon();
	    pigeon1.printName();
	    
	    Integer a = 1;
	    Integer b = 1;
	    Integer c = 26888;
	    Integer d = 26888;
	    System.out.println(a==b);
	    System.out.println(c==d);
	}

}
