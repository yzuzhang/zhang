package com.feicent.zhang.project.webside.redis;

import org.apache.shiro.cache.Cache;

import com.feicent.zhang.project.webside.RedisClient;
import com.feicent.zhang.project.webside.shiro.ShiroCacheManager;

/**
 * 
 * @ClassName RedisShiroCacheManager
 * @Description Redis管理
 *
 * @author wjggwm
 * @data 2016年12月13日 下午1:43:57
 */
public class RedisShiroCacheManager implements ShiroCacheManager {

    private RedisClient cacheManager;
    
    public RedisClient getCacheManager() {
		return cacheManager;
	}

	public void setCacheManager(RedisClient cacheManager) {
		this.cacheManager = cacheManager;
	}

	@Override
    public <K, V> Cache<K, V> getCache(String name) {
        return new RedisShiroCache<K, V>(name, getCacheManager());
    }

    @Override
    public void destroy() {
    	//做一些需要释放资源的操作
    }

}
