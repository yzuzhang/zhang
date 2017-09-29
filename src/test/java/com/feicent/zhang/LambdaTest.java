package com.feicent.zhang;

import java.lang.invoke.CallSite;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.List;

import com.google.common.base.Function;

/**
 * https://www.oschina.net/translate/hacking-lambda-expressions-in-java?lang=chs&page=2#
 * @author yzuzhang
 * @date 2017年8月10日
 */
public class LambdaTest {
	
	public void printElements(List<String> strings){
	    strings.forEach(item -> System.out.println("Item = "+ item));
	}
	
	public static Function<?, ?> createGetter(final MethodHandles.Lookup lookup,
            final MethodHandle getter) throws Exception{
		
		final CallSite site = LambdaMetafactory.metafactory(lookup, "apply",
		MethodType.methodType(Function.class),
		MethodType.methodType(Object.class, Object.class), //signature of method Function.apply after type erasure
		getter,
		getter.type()); //actual signature of getter
		
		try {
			return (Function<?, ?>) site.getTarget().invokeExact();
		} catch (final Exception e) {
			throw e;
		} catch (final Throwable e) {
			throw new Error(e);
		}
	}
	
}
