package com.feicent.zhang.core.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 要想使用使用反射，我们要去获取我们需要进行去处理的类或者对象的Class对象,其中我们主要有三种方法去获取
 * ①:使用XXX.Class语法:例如:String.Class;
 * ②:使用Class的静态方法forName():例如:Class.forName("java.lang.Class");
 * ③:使用具体某个对象.getClass()方法：例如String str="abc"; Class<?> tClass=str.getClass();
 * @author yzuzhang
 * @date 2016年11月24日
 */
public class BaseDemo {
	
	public static void main(String[] args) throws Exception {
		//getDeclaredMethods();
		
		visitPrivateMethod();
	}
	
	/**
	 * 使用反射机制去访问私有变量
	 * @throws Exception
	 */
	public static void visitPrivateMethod() throws Exception {
		Test02 p = new Test02();
		Class<?> classType = p.getClass();
		Field field = classType.getDeclaredField("name");
		//设置true,使用可以绕过Java语言规范的检查
		field.setAccessible(true);
		
		//对变量进行设置值
		field.set(p, "李四");
		Method method = classType.getDeclaredMethod("getName", new Class[] {});
		method.setAccessible(true);
		Object object = method.invoke(p, new Object[] {});
		System.out.println((String) object);
	}

	public static void getDeclaredMethods() throws Exception {
		//使用Class去调用静态方法forName()获得java.lang.Class的Class对象
		Class<?> tClass = Class.forName("java.lang.Class");
		//获取该class中声明的所有方法
		Method[] methods = tClass.getDeclaredMethods();
		//methods = tClass.getMethods();
		for (Method method : methods) {
			System.out.println(method);
		}
	}
}
