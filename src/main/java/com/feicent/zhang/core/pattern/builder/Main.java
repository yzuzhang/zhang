package com.feicent.zhang.core.pattern.builder;

/**
 * 建造模式
 * http://www.cnblogs.com/java-my-life/archive/2012/04/07/2433939.html
 */
public class Main {
	
	public static void main(String[] args) {
		Student student = new Student.Builder("kevin", 23).grade("1年级").build();
		System.out.println(student);
		
		//创建构建器对象
		InsuranceContract.ConcreteBuilder builder =
	            new InsuranceContract.ConcreteBuilder("9527", 123L, 456L);
	    //设置需要的数据，然后构建保险合同对象
		InsuranceContract contract = 
				builder.setPersonName("小明").setOtherData("test").build();
	    //操作保险合同对象的方法
		contract.someOperation();
	}
}
