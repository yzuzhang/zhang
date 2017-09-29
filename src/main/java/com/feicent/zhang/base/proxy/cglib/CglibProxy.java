package com.feicent.zhang.base.proxy.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CglibProxy implements MethodInterceptor{
	
	private Object target;
	
	public CglibProxy(Object target) {
		super();
		this.target =target;
	}
	
	@Override
	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		
		System.out.println("cglib代理:预订出差机票");
		System.out.println("cglib代理:预订住宿酒店");
		Object result = proxy.invokeSuper(obj, args);
		System.out.println(result.toString());
		System.out.println("cglib代理:预订返程机票");
		System.out.println("cglib代理:回公司报销费用");
		return "cglib代理:出差完毕";
	}
	
	public Object getProxyInstance() {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(target.getClass());
		// 设置回调函数
		enhancer.setCallback(this);
		return enhancer.create();
	}

}
