package com.feicent.zhang.util.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 用JAVA编写一个算法实现对一个字符数组的所有元素的所有组合
 * http://blog.csdn.net/u010215407/article/details/52164761 
 * @author yzuzhang
 * @date 2017年9月12日
 */
public class GroupHelper {

    public static void main(String[] args) {
        String[] a = new String[] { "tomcat", "jdk", "maven", "mysql" };
        List<String> list = new ArrayList<String>(Arrays.asList(a));
        
        List<List<String>> listAll = GroupHelper.iterator(list);
        for(List<String> ss : listAll){
        	for(String s: ss){
                System.out.print(s+",");
            }
            System.out.println();
        }
    }
    
    public static List<List<String>> iterator(List<String> list) {
        List<List<String>> result = new ArrayList<List<String>>();
        long n = (long)Math.pow(2,list.size());
        List<String> combine;
        for (long l=0L; l<n; l++) {
            combine = new ArrayList<String>();
            for (int i=0; i<list.size(); i++) {
                if ((l>>>i&1) == 1)
                    combine.add(list.get(i));
            }
            if( combine.size() > 0 ){
            	result.add(combine);
            }
        }
        return result;
    }

}
