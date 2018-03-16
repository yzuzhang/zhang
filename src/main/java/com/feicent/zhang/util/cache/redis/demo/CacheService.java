package com.feicent.zhang.util.cache.redis.demo;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.alibaba.fastjson.JSON;
import com.feicent.zhang.util.StringUtils;

/**
 * Java Web开发之缓存击穿问题
 * @date 2018年3月16日 下午12:58:30
 * https://baijiahao.baidu.com/s?id=1594522686068480589&wfr=spider&for=pc
 */
public class CacheService {
	
	private Logger logger = LoggerFactory.getLogger(CacheService.class);
	
	@Autowired
	private RedisTemplate<Serializable, Object> redisTemplate;
	
	/**
	 * 
	 * @param key 缓存key
	 * @param expire 设置缓存过期时间
	 * @param cacheLoader 业务具体的实现类
	 * @return
	 */
	public <T> T getData(String key, long expire, CacheLoader<T> cacheLoader){
		String jsonStr = RedisTemplateUtils.getCache(key, redisTemplate);
		if( StringUtils.isEmpty(jsonStr) ) {
			synchronized(logger) {
				jsonStr = RedisTemplateUtils.getCache(key, redisTemplate);
				if( StringUtils.isNotEmpty(jsonStr) ) {
					return JSON.parseObject(jsonStr, cacheLoader.getType());
				}
				T t = cacheLoader.getData();
				RedisTemplateUtils.setCache(key, JSON.toJSONString(t), expire, redisTemplate);
				return t;
			}
		}
		return JSON.parseObject(jsonStr, cacheLoader.getType());
	}
	
}
