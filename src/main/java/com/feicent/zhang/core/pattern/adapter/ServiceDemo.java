package com.feicent.zhang.core.pattern.adapter;

public class ServiceDemo extends ServiceAdapter {
	
	@Override
    public void serviceOperation1() {
    	System.out.println("具体类都可以选择所需要的方法实现...");
    }
}
