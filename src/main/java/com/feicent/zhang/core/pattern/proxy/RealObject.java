package com.feicent.zhang.core.pattern.proxy;

public class RealObject extends AbstractObject{
	
	@Override
	public void operation() {
		System.out.println("九九乘法表");
		for (int i = 1; i <=9 ; i++) {
			for (int j = 1; j <= i; j++) {
				System.out.print(j+"*"+i+ "=" + (i*j)+"\t");
			}
			System.out.println("");
		}
	}
	
}
