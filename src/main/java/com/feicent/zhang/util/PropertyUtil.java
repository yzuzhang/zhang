package com.feicent.zhang.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 加载配置文件的参数属性值
 */
public class PropertyUtil {
	
	private static Properties p = new Properties();
	
	static {
		init("config.properties");
	}
	
	/**
	 * read config file and set vlaue to Properties p
	 * @param propertyFileName 文件名
	 */
	protected static void init(String propertyFileName)
	{
		InputStream in = null;
		try {
			in = PropertyUtil.class.getClassLoader().getResourceAsStream(propertyFileName);
			if (in != null){
				p.load(in);
			}
		} catch (IOException e){
			System.err.println("load ["+propertyFileName+"] error!"+e.getMessage());
		} finally {
			CloseUtil.close(in);
		}
	}

	/**
	 * @param key
	 *            property key
	 * @return property value
	 */
	protected static String getValue(String key)
	{
		String value = null;
		try {
			value = p.getProperty(key);
			if (null == value){
				System.out.println("[" + key + "] not fount!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * @param key
	 *            property key
	 * @param defaultValue
	 *            if property value isn't exist, use default value
	 * @return property value
	 */
	protected static String getValue(String key, String defaultValue)
	{
		return p.getProperty(key, defaultValue);
	}

	/**
	 * @param key
	 *            property key
	 * @return int value. if value isn't exist, return 0
	 */
	protected static int getIntValue(String key)
	{
		return getIntValue(key, 0);
	}

	/**
	 * @param key
	 *            property key
	 * @param defaultValue
	 *            if property value isn't exist, use default value
	 * @return int value
	 */
	protected static int getIntValue(String key, int defaultValue)
	{
		String value = getValue(key);

		return value == null ? defaultValue : Integer.parseInt(value);
	}
	
	protected static long getLongValue(String key)
	{
		return getLongValue(key, 0);
	}
	
	protected static long getLongValue(String key, long defaultValue)
	{
		String value = getValue(key);
		return value == null ? defaultValue : Long.valueOf(value);
	}

	/**
	 * @param key
	 *            property key
	 * @return boolean value. if value isn't exist, return false
	 */
	protected static boolean getBoolValue(String key)
	{
		return getBoolValue(key, false);
	}
	
	/**
	 * @param key
	 *            property key
	 * @param defaultValue
	 *            defaultValue if property value isn't exist, use default value
	 * @return boolean value
	 */
	protected static boolean getBoolValue(String key, boolean defaultValue)
	{
		String value = getValue(key);
		return value == null ? defaultValue : "true".equalsIgnoreCase(value);
	}
	
}
