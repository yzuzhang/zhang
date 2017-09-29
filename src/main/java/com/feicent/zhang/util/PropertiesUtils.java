package com.feicent.zhang.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Properties;

/**
 * 对属性文件操作的工具类: 获取，新增，修改
 * 注意:以下方法读取属性文件会缓存问题,在修改属性文件时，不起作用
 *　InputStream in = PropertiesUtils.class.getResourceAsStream("/config.properties");
 *　解决办法：
 *　String savePath = PropertiesUtils.class.getResource("/config.properties").getPath();
 * @version 3.0v
 */
public class PropertiesUtils {
	
	private static final String CONFIG = "/config.properties"; 
	
	/**
	 * 获取属性文件的数据 根据key获取值
	 * @param fileName 文件名　(注意：加载的是src下的文件,如果在某个包下．请把包名加上)
	 * @param key
	 * @return
	 */
	public static String getValue(String key) {
		try {
			Properties prop = getProperties();
			return prop.getProperty(key);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 返回　Properties
	 * @param fileName 文件名　(注意：加载的是src下的文件,如果在某个包下．请把包名加上)
	 * @param 
	 * @return
	 */
	public static Properties getProperties(){
		InputStream in = null;
		Properties prop = new Properties();
		String savePath = PropertiesUtils.class.getResource(CONFIG).getPath();
		try {
			in =new BufferedInputStream(new FileInputStream(savePath));  
			prop.load(in);
		} catch (Exception e) {
			return null;
		} finally {
			CloseUtil.close(in);
		}
		return prop;
	}
	
	/**
	 * 写入properties信息
	 * @param key   名称
	 * @param value 值
	 */
	public static void modifyProperties(String key, String value) {
		modifyProperties(CONFIG, key, value);
	}
	
	public static void modifyProperties(String propertyFile, String key, String value) {
		FileOutputStream out = null;
		try {
			// 从输入流中读取属性列表（键和元素对）
			Properties prop = getProperties();
			prop.setProperty(key, value);
			String path = PropertiesUtils.class.getResource(propertyFile).getPath();
			
			out = new FileOutputStream(path);
			prop.store(out, "modify");
			out.flush();
		} catch (Exception e) {
			//
		} finally {
			CloseUtil.close(out);
		}
	}
	
	/**
     * 写入Properties信息
     *
     * @param filePath 写入的属性文件
     * @param pKey     属性名称
     * @param pValue   属性值
     */
    public static void writeProperties(String filePath, String pKey, String pValue) {
        Properties props = new Properties();
        OutputStream fos = null;
        FileInputStream fis = null;
        try {
        	fis = new FileInputStream(filePath);
			props.load(fis);
			// 调用 Hashtable 的方法 put，使用 getProperty 方法提供并行性。
			// 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
			fos = new FileOutputStream(filePath);
			props.setProperty(pKey, pValue);
			// 以适合使用 load 方法加载到 Properties 表中的格式，
			// 将此 Properties 表中的属性列表（键和元素对）写入输出流
			props.store(fos, "Update '" + pKey + "' value");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			CloseUtil.close(fos, fis);
		}
    }
	
	public static void properties() throws IOException {
		InputStream inputStream = null;
		String config = "config.properties";
		ClassLoader loader = PropertiesUtils.class.getClassLoader();
		if ( loader !=  null ) {
			System.out.println("loader.getResourceAsStream");
			inputStream = loader.getResourceAsStream(config);
			
			inputStream = loader.getResource(config).openStream();
			loader.getResource(config).openConnection().getInputStream();
			
			System.out.println(PropertiesUtils.class.getResource("/config.properties").getPath());
			System.out.println(loader.getResource(config).getPath());
			System.out.println(loader.getResource(config).getFile());
		} else {
			inputStream = ClassLoader.getSystemResourceAsStream(config);
		}
		
		Properties dbProps =  new  Properties();        
		dbProps.load(inputStream);        
		inputStream.close(); 
		String username = dbProps.getProperty("jdbc.username");
		System.out.println("jdbc.username = "+ username);
	}
	
	public static void main(String[] args) {
		String path = PropertiesUtils.class.getResource(CONFIG).getPath();
		System.out.println("path==="+ path);
		
		try {
			Properties prop = getProperties();
			for(Map.Entry<Object, Object> entry : prop.entrySet()){
				System.err.println(entry.getKey() + " = " + entry.getValue());
			}
			properties();
			//modifyProperties(CONFIG, "jdbc.password", "123123");
		} catch (Exception e) {
			
		}
		writeProperties(path, "password", "123123");
	}
	
}
