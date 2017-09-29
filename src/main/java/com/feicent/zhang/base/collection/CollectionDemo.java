package com.feicent.zhang.base.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Collections是一个包装类,它包含有有关集合操作的静态多态方法。
 * 此类不能实例化,就像一个工具类,服务于Java的Collection框架
 * @author yzuzhang
 */
public class CollectionDemo {
	
    public static void main(String args[]) {  
    	int array[] = { 112, 151, 23, 456, 231 }; 
    	int length = array.length;
        //注意List是实现Collection接口的  
        List<Integer> list = new ArrayList<Integer>(length);  
        
        for (int i = 0; i < length; i++) {  
            list.add(array[i]);  
        }  
        Collections.sort(list); 
        
        System.out.print("排序结果："); 
        for (int i = 0; i < length; i++) {  
            System.out.print(list.get(i)+" ");  
        }  
        System.out.println(list.toString()); 
    }  
}
