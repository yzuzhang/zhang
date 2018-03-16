package com.feicent.zhang.util.cache.redis.demo;

import com.alibaba.fastjson.TypeReference;

public abstract class CacheLoader<T> {

	public TypeReference<T> getType(){
		return new TypeReference<T>() {			
		};
	}
	
	public abstract T getData();

}
