package com.feicent.zhang.util.cache.redis.demo;

import java.util.ArrayList;
import java.util.List;

import com.feicent.zhang.entity.User;

public class Main {

	CacheService cacheService;
	
	/**
	 * 查询用户信息
	 * 缓存没有记录时,再查数据库
	 * @param userName
	 */
	public List<User> getUserList(String userName) {
		List<User> list = cacheService.getData(userName, 30000, new CacheLoader<List<User>>() {
			@Override
			public List<User> getData() {
				List<User> list = new ArrayList<User>();
				//从数据库里获取
				//list = userDao.selectByUserName(userName);
				
				return list;
			}
		});
		
		return list;
	}
	
}
