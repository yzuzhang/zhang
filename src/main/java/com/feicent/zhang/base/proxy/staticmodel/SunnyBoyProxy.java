package com.feicent.zhang.base.proxy.staticmodel;

public class SunnyBoyProxy implements SunnyBoy {
	
	private SunnyBoy target;
	
	public SunnyBoyProxy(SunnyBoy target) {
		super();
		this.target = target;
	}
	
	@Override
	public String code() {
		System.out.println("预订出差机票");
		System.out.println("预订住宿酒店");
		System.out.println(target.code());
		System.out.println("预订返程机票");
		System.out.println("回公司报销费用");
		return "出差完毕";
	}

}
