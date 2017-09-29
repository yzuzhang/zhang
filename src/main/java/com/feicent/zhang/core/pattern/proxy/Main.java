package com.feicent.zhang.core.pattern.proxy;

public class Main {
	
	public static void main(String[] args) {
		RealObject object = new RealObject();
        AbstractObject obj = new ProxyObject(object);
        obj.operation();
    }
}
