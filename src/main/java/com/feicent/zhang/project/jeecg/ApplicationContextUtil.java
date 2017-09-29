package com.feicent.zhang.project.jeecg;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextUtil implements ApplicationContextAware {
	
	private static ApplicationContext context;
	
	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		ApplicationContextUtil.context = context;
	}

	public static ApplicationContext getContext() {
		return context;
	}
	
	public static Object getBean(String name) {
		return context.getBean(name);
	}

	public static <T> T getBean(String name, Class<T> requiredType) {
		return context.getBean(name, requiredType);
	}

	public static boolean containsBean(String name) {
		return context.containsBean(name);
	}
	
	public static boolean isSingleton(String name) {
		return context.isSingleton(name);
	}
	
	public static Class<? extends Object> getType(String name) {
		return context.getType(name);
	}
	
}
