package com.feicent.zhang;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * http://blog.csdn.net/chenleixing/article/details/44087131
 * @author yzuzhang
 * @date 2016年11月25日
 */
public class MapTest {

	/**
	 * 如果对Map讲究效率的遍历的话，还是采用entrySet()方法
	 * 1. keySet遍历map耗时-3696微秒
	 * 2. values遍历map(只得到值)耗时-2569微秒
	 * 3. entrySet遍历map耗时-1323微秒
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		int length = 10000;
        Map<String, String> mapTest = new HashMap<String, String>(length);  
		for(int i=0; i<length; i++){  
			mapTest.put(String.valueOf(i),String.valueOf(i) );  
		}  
		
		//一种遍历，keySet()方法  
		long start = System.nanoTime();  
		Set<String> setEach = mapTest.keySet();  
		for(String key : setEach){  
			String value = mapTest.get(key);  
		}  
		long end = System.nanoTime();  
		System.out.println("1. keySet遍历map耗时-"+(end-start)/1000+"微秒");
		
        //二种遍历，可用values()返回Collection<T>,不容易得到对应的key  
		start = System.nanoTime();  
		Collection<String> co = mapTest.values();  
		for(String value : co){  
			//遍历中也在创建value  
		}  
		end=System.nanoTime();  
		System.out.println("2. values遍历map(只得到值)耗时-"+(end-start)/1000+"微秒");  
		
		//三种遍历，用entrySet()方法返回Set<Map.Entry<T,T>>类型，再获取里边的Map.Entry  
		start=System.nanoTime();
		Set<Map.Entry<String,String>> entrySet=mapTest.entrySet();  
		for(Map.Entry<String, String> entry : entrySet){  
		    String key=entry.getKey();  
		    String value=entry.getValue();  
		}  
		end=System.nanoTime();  
		System.out.println("3. entrySet遍历map耗时-"+(end-start)/1000+"微秒"); 
	}

}
