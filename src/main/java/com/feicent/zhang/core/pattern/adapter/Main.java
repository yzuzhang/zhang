package com.feicent.zhang.core.pattern.adapter;

public class Main {
	
	public static void main(String[] args) {
		AbstractService service = new ServiceDemo();
		service.serviceOperation1();
	}
}