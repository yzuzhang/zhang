package com.feicent.zhang.plugin.db.flywaydb;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.flywaydb.core.Flyway;

import com.alibaba.fastjson.JSON;
import com.feicent.zhang.util.CloseUtil;

/**
 * http://casheen.iteye.com/blog/1749916
 * http://shuzheng5201314.iteye.com/blog/2044517
 * http://itindex.net/detail/52467-flyway-%E5%BA%94%E7%94%A8-%E6%95%B0%E6%8D%AE%E5%BA%93
 * @author yzuzhang
 * @date 2017年9月12日
 */
public class FlywaydbClinet {
	
	private static String dbconfig = "config.properties";
	// 读取数据库配置参数
	private static Properties config = new Properties();
	
	static {
		InputStream inputStream = null;
		try {
			inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(dbconfig);
			config.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			CloseUtil.close(inputStream);
		}
	}
	
	// 执行数据库版本升级
	public static void migration() {
		// Create the Flyway instance
		Flyway flyway = new Flyway();
		
		//flyway.setDataSource(dataSource);  
		  
        //flyway.setSchemas("flywaydemo"); // 设置接受flyway进行版本管理的多个数据库  
        //flyway.setTable("schema_version"); // 设置存放flyway metadata数据的表名  
        
		//flyway.setLocations("flyway/migrations", "com.kedacom.flywaydemo.migrations"); // 设置flyway扫描sql升级脚本、java升级脚本的目录路径或包路径  
		//flyway.setLocations("db/migration");//默认在目录:db/migration
		
		// Point it to the database
		flyway.setDataSource(config.getProperty("jdbc.url"), 
				config.getProperty("jdbc.username"), 
				config.getProperty("jdbc.password"));
		//flyway.setInitOnMigrate(true);
		
		flyway.setEncoding("UTF-8"); // 设置sql脚本文件的编码  
        
		// 开始数据库迁移
		flyway.migrate();
		System.out.println("Flyway migrate successfully.");
	}
	
	public static void main(String[] args) {
		System.out.println(JSON.toJSONString(config));
		
		FlywaydbClinet.migration();
	}
}
