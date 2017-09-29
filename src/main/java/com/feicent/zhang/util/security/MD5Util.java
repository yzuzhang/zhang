package com.feicent.zhang.util.security;

import java.security.MessageDigest;
import org.apache.log4j.Logger;

public class MD5Util {

	private static Logger log = Logger.getLogger(MD5Util.class);
	
	private final static String[] strDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };
	
	public static String getMD5Code16(String strObj) {
		return getMD5Code32(strObj).substring(8, 24);
	}
	
	public static String getMD5Code32(String strObj) {
		String resultString = null;
		try {
			resultString = new String(strObj);
			MessageDigest md = MessageDigest.getInstance("MD5");
			// md.digest() 该函数返回值为存放哈希值结果的byte数组
			resultString = byteToString(md.digest(strObj.getBytes("UTF-8")));
		} catch (Exception ex) {
			log.error("", ex);
		}
		return resultString;
	}

	// 转换字节数组为16进制字串
	private static String byteToString(byte[] bByte) {
		StringBuffer sBuffer = new StringBuffer();
		for (int i = 0; i < bByte.length; i++) {
			sBuffer.append(byteToArrayString(bByte[i]));
		}
		return sBuffer.toString();
	}
	 
	// 返回形式为数字跟字符串
	private static String byteToArrayString(byte bByte) {
		int iRet = bByte;
		if (iRet < 0) {
			iRet += 256;
		}
		int iD1 = iRet / 16;
		int iD2 = iRet % 16;
		return strDigits[iD1] + strDigits[iD2];
	}

	public static void main(String[] args) {
		String str = "123456";
		System.out.println(getMD5Code16(str));
		System.out.println(getMD5Code32(str));
	}
}
