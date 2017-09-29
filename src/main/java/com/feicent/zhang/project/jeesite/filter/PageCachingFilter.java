/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.feicent.zhang.project.jeesite.filter;

import com.feicent.zhang.project.jeesite.utils.SpringContextHolder;

import net.sf.ehcache.CacheManager;


/**
 * 页面高速缓存过滤器
 * @author ThinkGem
 * @version 2013-8-5
 */
public class PageCachingFilter {

	private CacheManager cacheManager = SpringContextHolder.getBean(CacheManager.class);
	
	protected CacheManager getCacheManager() {
		return cacheManager;
	}
	
}
