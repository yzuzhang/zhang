package com.feicent.zhang.thread.notify;

import java.util.ArrayList;
import java.util.List;

public class MyList {

	private static List<String> list = new ArrayList<String>();
	
	public static void add() {
		list.add("Notify");
	}
	
	public static void add(String str) {
		list.add(str);
	}
	
	public static int size() {
		return list.size();
	}
	
}
