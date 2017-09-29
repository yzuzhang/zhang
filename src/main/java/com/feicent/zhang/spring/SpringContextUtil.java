package com.feicent.zhang.spring;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public class SpringContextUtil {
	
	/**
	 * 通过实体名称得它对应的service
	 * @param beanName
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName) {
		// 获取当前运行环境下Spring上下文
		WebApplicationContext webApp = ContextLoader.getCurrentWebApplicationContext();
		return (T) webApp.getBean(beanName);
	}
	
	/**
	 * 按类型获取spring bean 实例
	 * @param beanType
	 * @return
	 */
	public static <T> T getBean(Class<T> beanType) {
		// 获取当前运行环境下Spring上下文
		WebApplicationContext webApp = ContextLoader.getCurrentWebApplicationContext();
		return webApp.getBean(beanType);
	}
	
	public static WebApplicationContext getWebApplicationContext() {
		return ContextLoader.getCurrentWebApplicationContext();
	}

}
