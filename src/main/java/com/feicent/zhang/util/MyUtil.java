package com.feicent.zhang.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 通用工具类
 */
public class MyUtil {
	
	public static final String SHORT_DATE = "HH:mm:ss";
	public static final String LONG_DATE = "yyyy-MM-dd HH:mm:ss";
	
	public static final SimpleDateFormat DF_LONG = new SimpleDateFormat(LONG_DATE);
	public static final SimpleDateFormat DF_SHORT = new SimpleDateFormat(SHORT_DATE);
	
	private static final ThreadLocalRandom threadLocalRandom;
	
	static {
		threadLocalRandom = ThreadLocalRandom.current();
	}
	
	/**
	 * @return 生成UUID字符串
	 */
	public static String uuid(){
		return UUID.randomUUID().toString();
	}
	
	public static String getLine(List<String> list, int length) {
		int index = threadLocalRandom.nextInt(length);
		return list.get(index);
	}
	
	public static String getLine(List<String> list) {
		int length = list.size();
		if(list==null || length==0){
			return null;
		}
		int index = threadLocalRandom.nextInt(length);
		return list.get(index);
	}
	
	/**
	 * 判断是否为null或者空字符串
	 * @param str
	 */
	public static boolean isEmpty(String str) {
		return null == str || str.trim().length() == 0;
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}
	
	public static String getNow(){
		return DF_LONG.format(new Date());
	}
	
	public static String getNow(long ts){
		return DF_LONG.format(ts);
	}
	
	/**
	 * 获取当前时间 HH:mm:ss
	 */
	public static String getNowShort(){
		return DF_SHORT.format(new Date());
	}
	
	/**
	 * 用户的当前工作目录路径
	 * D:\javawed\maven_project\sdk\
	 */
	public static String getUserDir() {
		return System.getProperty("user.dir") + System.getProperty("file.separator");
	}
	
	/**
	 * 资源配置文件目录</br>
	 * /D:/eclipse/workspaces/imloadtest-stat/target/classes/
	 */
	public static String getResourcePath() {
		return MyUtil.class.getClass().getResource("/").getPath();
	}

	public static String[] buildShell(String command) {
		if (isEmpty(command)) {
			throw new NullPointerException();
		}

		String[] cmdarray;
		String os = System.getProperty("os.name");

		if ("Windows 95".equals(os) || "Windows 98".equals(os) || "Windows ME".equals(os)){
			cmdarray = new String[]{"command.exe", "/C", command};
		} else if (os.startsWith("Windows")){
			cmdarray = new String[]{"cmd.exe", "/C", command};
		} else {
			cmdarray = new String[]{"/bin/sh", "-c", command};
		}
		
		return cmdarray;
	}

	public static String getLocalServerIp() {
		InetAddress address = null;
		String serverIp = "127.0.0.1";
		try {
			address = InetAddress.getLocalHost();
			serverIp = address.getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return serverIp;
	}

	public static String getLocalHostQuietly() {
	    String localAddress;
	    try {
	      localAddress = InetAddress.getLocalHost().getHostAddress();
	    } catch (Exception ex) {
	      localAddress = "localhost";
	    }
	    return localAddress;
	}

}
