package com.feicent.zhang.util.cache.redis.demo;

import java.io.Serializable;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import org.springframework.dao.DataAccessException;

public class RedisTemplateUtils {

	public static String getCache(String cacheKey, RedisTemplate<Serializable, Object> redisTemplate) {
		String result = redisTemplate.execute(new RedisCallback<String>() { 
			
            public String doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = getRedisSerializer(redisTemplate);  
                byte[] key = serializer.serialize(cacheKey);  
                byte[] value = connection.get(key);  
                if (value == null) {  
                    return null;  
                }  
                String nickname = serializer.deserialize(value);  
                return nickname;  
            }  
        });
		
        return result; 
	}

	/**
	 * 设置缓存
	 * @param cacheKey
	 * @param jsonString
	 * @param expire 过期时间 秒
	 * @param redisTemplate
	 * @return
	 */
	public static boolean setCache(String cacheKey, String jsonString, long expire,
			RedisTemplate<Serializable, Object> redisTemplate) {
		
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = getRedisSerializer(redisTemplate);  
                byte[] key  = serializer.serialize(cacheKey);  
                byte[] name = serializer.serialize(jsonString);
                
                boolean flag = connection.setNX(key, name);
                if( flag ) {
                	flag = connection.expire(key, expire);
                }
                return flag;
            }  
        });
		
        return result;
	}
    
    protected static RedisSerializer<String> getRedisSerializer(RedisTemplate<Serializable, Object> redisTemplate) {  
        return redisTemplate.getStringSerializer();  
    }

}
