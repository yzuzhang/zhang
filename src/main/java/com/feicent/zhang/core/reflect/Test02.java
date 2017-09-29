package com.feicent.zhang.core.reflect;

public class Test02 {
	private String name = "张三";
	
	private String getName() {
		return name;
	}
	
	public static void main(String[] args) {
		Test02 t = new Test02();
		t.getName();
	}
}
