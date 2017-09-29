package com.feicent.zhang.base.demo;

/**
 * 正确的使用abstract和interface有利提高代码可读性
 * 当人们看到extend时就会知道这是一个is a什么
 * 看到implement时可以知道这个类有什么特性like a
 */
public class AmericanSuperMan extends Man implements SuperMan {

	public void superPower() {
		System.out.println("美国超人会飞~~~~");
	}

	@Override
	public void eat() {
		System.out.println("美国人用刀叉吃");
	}

}
