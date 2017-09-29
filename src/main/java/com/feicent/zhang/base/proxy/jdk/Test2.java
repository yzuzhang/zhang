package com.feicent.zhang.base.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import com.feicent.zhang.base.proxy.staticmodel.SunnyBoy;
import com.feicent.zhang.base.proxy.staticmodel.XiaoMing;

public class Test2 {
	
	public static void main(String[] args) {
		SunnyBoy target = new XiaoMing();
		InvocationHandler handler = new PublicProxy(target);
		
		SunnyBoy boy = (SunnyBoy)Proxy.newProxyInstance(target.getClass().getClassLoader(), 
				target.getClass().getInterfaces(), handler);
		boy.code();
	}

}
