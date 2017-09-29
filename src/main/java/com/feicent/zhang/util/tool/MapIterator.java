package com.feicent.zhang.util.tool;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MapIterator {
	
	static Map<String, String> map = new HashMap<String, String>(3);
	
	static{
		  map.put("1", "v1");
		  map.put("2", "v2");
		  map.put("3", "v3");
	}

	public static void main(String[] args) {
		
		// 第一种：遍历key，再通过key获取对应的value
		System.out.println("通过Map.keySet遍历key和value：");
		for (String key : map.keySet()) {
			System.out.println("key= " + key + " and value= " + map.get(key));
		}
		
		// 第二种：使用Entry,尤其是容量大时
		System.out.println("通过Map.entrySet遍历key和value");
		for (Map.Entry<String, String> entry : map.entrySet()) {
			System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
		}
		
		// 第三种:迭代器
		System.out.println("通过Map.entrySet使用iterator遍历key和value：");
		Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> entry = it.next();
			System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
		}
		
		// 第四种： 单单遍历value
		System.out.println("通过Map.values()遍历所有的value，但不能遍历key");
		for (String v : map.values()) {
			System.out.println("value= " + v);
		}
	}
	
	public static void iterator(Map<String, String> map) {
		Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
		while ( iterator.hasNext() ) {
			Map.Entry<String, String> entry = iterator.next();
			System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
		}
	}
}
