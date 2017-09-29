package com.feicent.zhang.base;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Concurrent {
	
	public static void main(String[] args) {
		example();
	}
	
	public static void example(){
		// a map that may be modified (by the same or different thread) while being iterated
		Map<String, String> repository = new ConcurrentHashMap<String, String>();
		repository.put("", "");
		
		// same with lists. This one is only available with Java 6
		List<String> list = new CopyOnWriteArrayList<String>();
		list.add("");
		
	}
}
