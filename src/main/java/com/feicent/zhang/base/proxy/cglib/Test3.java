package com.feicent.zhang.base.proxy.cglib;

import com.feicent.zhang.base.proxy.staticmodel.SunnyBoy;
import com.feicent.zhang.base.proxy.staticmodel.XiaoMing;

public class Test3 {
	
	public static void main(String[] args) {
		SunnyBoy target = new XiaoMing();
		SunnyBoy boy = (SunnyBoy) new CglibProxy(target).getProxyInstance();
		boy.code();
	}

}
