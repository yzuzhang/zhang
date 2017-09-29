package com.feicent.zhang.core.pattern.proxy;

public class ProxyObject extends AbstractObject {
	
	private AbstractObject realObject;
	
	public ProxyObject(AbstractObject realObject){
		this.realObject = realObject;
	}
	
	@Override
	public void operation() {
		//调用目标对象之前可以做相关操作
        System.out.println("before");        
        realObject.operation();        
        //调用目标对象之后可以做相关操作
        System.out.println("after");
	}

}
