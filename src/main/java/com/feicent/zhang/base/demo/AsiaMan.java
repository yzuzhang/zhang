package com.feicent.zhang.base.demo;

/**
 * 当某个子类和父类是 is a关系时
 * 比如一个亚洲人是一个人,那么一个亚洲人的子类可以继承一个人的父类
 */
public class AsiaMan extends Man{
	
	@Override
	public void eat() {
		System.out.println("亚洲人用筷子吃");
	}
	
	public void skin(){
		System.out.println("亚洲人是黄皮肤");
	}

}
