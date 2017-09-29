package com.feicent.zhang.project.lanyuan;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import com.feicent.zhang.project.lanyuan.entity.UserFormMap;

public class PasswordHelper {
	
	private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
	private String algorithmName = "md5";
	private int hashIterations = 2;
	
	public void encryptPassword(UserFormMap userFormMap) {
		String salt = randomNumberGenerator.nextBytes().toHex();
		userFormMap.put("credentialsSalt", salt);
		String newPassword = new SimpleHash(algorithmName, userFormMap.get("password"), 
				ByteSource.Util.bytes(userFormMap.get("accountName")+salt), hashIterations).toHex();
		userFormMap.put("password", newPassword); 
	}
	
	public String encryptPassword(String userName, String password) {
		String salt = randomNumberGenerator.nextBytes().toHex();
		String newPassword = new SimpleHash(algorithmName, password, 
				ByteSource.Util.bytes(userName + salt), hashIterations).toHex();
		return newPassword;
	}
	
	public static void main(String[] args) {
		PasswordHelper passwordHelper = new PasswordHelper();
		UserFormMap userFormMap = new UserFormMap();
		userFormMap.put("password", "123456");
		userFormMap.put("accountName", "admin");
		passwordHelper.encryptPassword(userFormMap);
		System.out.println(userFormMap);
		
		System.out.println(passwordHelper.encryptPassword("admin", "123456"));
	}
}
