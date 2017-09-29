package com.feicent.zhang.io;

import java.util.ResourceBundle;

/**
 * 读取配置文件.properties的工具类
 * @author yzuzhang
 */
public class ConfigHelper {
	private static ConfigHelper instance = new ConfigHelper();
	private static final ResourceBundle bundle;
	
	private ConfigHelper() {
		
	}
	
	static {
		bundle = ResourceBundle.getBundle("config");
	}
	
	public static ConfigHelper getInstance() {
		return instance;
	}

	/**
	 * xxx.properties配置文件可以放在resource目录下
	 * @param propertyName 配置文件名,不带后缀.properties
	 */
	public static ResourceBundle getConfigResource(String propertyName) {
		return ResourceBundle.getBundle(propertyName);
	}
	
	public static String getString(String key) {
		return bundle.getString(key);
	}
	
	public static void main(String[] args){
		String password = ConfigHelper.getString("jdbc.password");
		System.out.println(password);
	}
	
}
