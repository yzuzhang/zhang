package com.feicent.zhang.guava.collections;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class MapTest {

	public static void main(String[] args) {

	}

	public static void test(){
		Multimap<String, Object> map = HashMultimap.create();
		map.put("", "");
	}
}
