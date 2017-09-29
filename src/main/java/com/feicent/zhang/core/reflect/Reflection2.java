package com.feicent.zhang.core.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * 使用反射访问类中的方法
 * @author yzuzhang
 * @date 2016年11月24日
 */
public class Reflection2 {
	
	public int sum(int a, int b) {
		return a + b;
	}
	
	public String addStr(String str) {
		return "Hello " + str;
	}
	
	public static void main(String[] args) throws Exception {
		Class<?> classType = Reflection2.class;
		// Object reflection2 = classType.newInstance();
		Constructor<?> constructor = classType.getConstructor(new Class[] {});
		Object reflection2 = constructor.newInstance(new Object[] {});
		// 通过反射进行反射出类中的方法
		Method sumMethod = classType.getMethod("sum", new Class[] { int.class,
				int.class });
		//invoke方法的值永远只是对象
		Object result1 = sumMethod.invoke(reflection2, new Object[] { 6, 10 });
		System.out.println((Integer) result1);
		
		Method addStrMethod = classType.getMethod("addStr",
				new Class[] { String.class });
		Object result2 = addStrMethod.invoke(reflection2,
				new Object[] { "Tom" });
		System.out.println((String) result2);
	}

}
