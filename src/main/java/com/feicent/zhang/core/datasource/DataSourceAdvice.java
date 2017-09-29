package com.feicent.zhang.core.datasource;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.ThrowsAdvice;

/**
 * 配置AOP切面类，动态切换读/写数据库。
 * <p>
 * Copyright: Copyright (c) 2015-3-9 下午3:16:51
 * <p>
 * Company:
 * <p>
 * 
 * @author macl@c-platform.com
 * @version 1.0.0
 */

public class DataSourceAdvice implements MethodBeforeAdvice, AfterReturningAdvice, ThrowsAdvice {

	private static final Logger log = Logger.getLogger(DataSourceAdvice.class);

	private String logInfo;

	// 需要切换到从库(读库)的方法名前缀
	private List<String> slaveMethods;

	/**
	 * service方法执行之前被调用.
	 */
	@Override
	public void before(Method method, Object[] args, Object target) throws Throwable {
		logInfo = String.format("before切入点:%s-->%s(),切换到:", target.getClass().getName(), method.getName());
		
		String methodName = method.getName();
		boolean hasSwitchedSlave = false;
		
		for (String slaveMethod : slaveMethods) {
			if (methodName.startsWith(slaveMethod)) {
				log.info(logInfo + DataSourceSwitcher.SLAVE_DATA_SOURCE);
				hasSwitchedSlave = true;
				DataSourceSwitcher.setSlave();
				break;
			}
		}

		if (!hasSwitchedSlave) {
			log.info(logInfo + "切换到:" + DataSourceSwitcher.MASTER_DATA_SOURCE);
			DataSourceSwitcher.setMaster();
		}
	}

	/**
	 * service方法执行完之后被调用.
	 */
	@Override
	public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {

	}

	/**
	 * 抛出Exception之后被调用。
	 * 
	 * @param method
	 * @param args
	 * @param target
	 * @param ex
	 * @throws Throwable
	 */
	public void afterThrowing(Method method, Object[] args, Object target, Exception ex) throws Throwable {
		logInfo = String.format("after throwing:%s类中%s方法,", target.getClass().getName(), method.getName());
		log.error(logInfo + "发生异常:" + ex.getMessage() + ",切换到:" + DataSourceSwitcher.SLAVE_DATA_SOURCE);
		DataSourceSwitcher.setSlave();
	}

	public List<String> getSlaveMethods() {
		return slaveMethods;
	}

	public void setSlaveMethods(List<String> slaveMethods) {
		this.slaveMethods = slaveMethods;
	}
}
