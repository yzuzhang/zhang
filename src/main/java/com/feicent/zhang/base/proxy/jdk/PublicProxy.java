package com.feicent.zhang.base.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class PublicProxy implements InvocationHandler {
	
	private Object target;
	
	public PublicProxy(Object target) {
		super();
		this.target =target;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		System.out.println("动态代理:预订出差机票");
		System.out.println("动态代理:预订住宿酒店");
		Object result = method.invoke(target, args);
		System.out.println(result.toString());
		System.out.println("动态代理:预订返程机票");
		System.out.println("动态代理:回公司报销费用");
		return "出差完毕";
	}

}
