package com.feicent.zhang.core.datasource;

import org.springframework.util.Assert;

/**
 * 数据源选择类
 * <p>
 * Copyright: Copyright (c) 2015-3-9 下午3:14:55
 * <p>
 * Company:  
 * <p>
 * 
 * @author macl@c-platform.com
 * @version 1.0.0
 */
public class DataSourceSwitcher {

	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

	/** 主库（写库） **/
	public static final String MASTER_DATA_SOURCE = "master";

	/** 从库(读库) **/
	public static final String SLAVE_DATA_SOURCE = "slave";

	public static void setDataSource(String dataSource) {
		Assert.notNull(dataSource, "dataSource cannot be null");
		contextHolder.set(dataSource);
	}

	public static void setMaster() {
		clearDataSource();
	}

	public static void setSlave() {
		setDataSource(SLAVE_DATA_SOURCE);
	}

	public static String getDataSource() {
		return (String) contextHolder.get();
	}

	public static void clearDataSource() {
		contextHolder.remove();
	}
}
