package com.feicent.zhang.util.security;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import com.alibaba.fastjson.JSON;

/**
 * 生成salt进行加密操作
 * @author yzuzhang
 */
public class PasswordHelper {
	
	private static int hashIterations = 2;
	private static final String algorithmName = "MD5";
	private static RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
	
	public static void encryptPassword(Map<String, Object> userFormMap) {
		String salt = createSalt();
		userFormMap.put("salt", salt);
		String newPassword = new SimpleHash(algorithmName, userFormMap.get("password"), 
				ByteSource.Util.bytes(userFormMap.get("userName")+salt), hashIterations).toHex();
		userFormMap.put("password", newPassword); 
	}
	
	public static String encryptPassword(String userName, String password) {
		String salt = createSalt();
		System.out.println(password+", salt="+ salt);
		String newPassword = new SimpleHash(algorithmName, password, 
				ByteSource.Util.bytes(userName + salt), hashIterations).toHex();
		return newPassword;
	}
	
	public static String createSalt() {
		return randomNumberGenerator.nextBytes().toHex();
	}
	
	public static void main(String[] args) {
		Map<String, Object> userFormMap = new HashMap<String, Object>();
		userFormMap.put("password", "123456");
		userFormMap.put("userName", "admin");
		encryptPassword(userFormMap);
		System.out.println(JSON.toJSONString(userFormMap));
		
		System.out.println("encryptPassword---->"+encryptPassword("admin", "123456"));
	}
	
}
