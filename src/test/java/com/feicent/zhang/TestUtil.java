package com.feicent.zhang;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.feicent.zhang.util.tool.ExecuteHelper;

public class TestUtil {
	
	public static String path = System.getProperty("user.dir");
	public static URL urlPath = TestUtil.class.getProtectionDomain().getCodeSource().getLocation();
	
	public static void main(String[] args) {
		//showPath();
		//hashTest();
		//showJavaVersion();
		
		//常量池,不能存在两个相同的值
		String s1= "java", s2="java";   //(String 类型为引用类型)
	    String s3=new String("java");   //new会在堆内存中开辟一个空间
	    System.out.println(s1 == s2);   
	    System.out.println(s1.equals(s3));//比较的是内容
	    System.out.println(s1 == s3);     //比较的是地址
	    
	    int[] array = {1,2,3};
	    System.out.println(Arrays.toString(array));
	}
	
	public static void hashTest(){
		/*
		 * Hashtable的key和value都不能为null
		 */
		Hashtable<String, String> table = new Hashtable<String, String>();
		table.put("1", null);//此时会报错
		
		/*
		 * HashMap的key和value都可以为null
		 */
		Map<String, String> map = new HashMap<String, String>();
		map.put(null, null);
		System.out.println(map.get(null));
	}
	
	public static void showPath(){
		String fileName = "mybatis-config.xml";
		try {
			File mybatis = new File(path +"\\src\\main\\resources\\", fileName);
			String content = FileUtils.readFileToString(mybatis, "UTF-8");
			System.out.println(fileName+"读取内容:\n"+content);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println();
		System.out.println("System.getProperty(\"user.dir\")====> "+path);
		System.out.println("classes文件所在路径： "+urlPath.getPath());
	}
	
	/**
	 * 调用执行命令行,显示Java版本
	 */
	public static void showJavaVersion(){
		try {
			ExecuteHelper helper = ExecuteHelper.execUsingShell("java -version", "GBK");
			String result = helper.getResult();
			System.out.println(result);
			//DbUtils
			
			/*String file = ExecHelper.exec(new String[]{"java -version"}, "GBK").getOutput();
			System.out.println("file====="+ file);
			
			String hello = ExecHelper.execUsingShell("echo 'Hello World'").getOutput();
			System.out.println("hello====="+ hello);*/
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
