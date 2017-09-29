package com.feicent.zhang.core.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 数据源动态切换类
 * <p>
 * Copyright: Copyright (c) 2015-3-9 下午3:15:19
 * <p>
 * Company: 
 * <p>
 * 
 * @author macl@c-platform.com
 * @version 1.0.0
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		return DataSourceSwitcher.getDataSource();
	}

}
