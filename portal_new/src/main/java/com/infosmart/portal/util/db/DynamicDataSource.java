package com.infosmart.portal.util.db;

import java.sql.SQLFeatureNotSupportedException;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {

	public final Logger logger = Logger.getLogger(DynamicDataSource.class);
	
	 private Map<Object, Object> _targetDataSources;

	@Override
	protected Object determineCurrentLookupKey() {
		return CustomerContextHolder.getDataSourceType();// 获得当前数据源标识符
	}

	public void setTargetDataSources(Map<Object, Object> targetDataSources) {
		this._targetDataSources = targetDataSources;
		super.setTargetDataSources(targetDataSources);
		super.afterPropertiesSet();
	}

	public void addTargetDataSource(String key, DataSource dataSource,
			boolean defaultDataSource) {
		this.logger.info("---------------->add");
		this._targetDataSources.put(key, dataSource);
		super.setTargetDataSources(_targetDataSources);
		super.afterPropertiesSet();
		/*
		super.afterPropertiesSet();
		this.logger.info("--------------size:" + this.targetDataSources.size());
		if (defaultDataSource) {
			CustomerContextHolder.setDataSourceType(key);
			this.setDefaultTargetDataSource(dataSource);
			this.logger.info("--------------default:" +CustomerContextHolder.getDataSourceType());
		}
		*/
	}

	public static DataSource addTargetDataSource(String driverClassName,
			String url, String username, String password) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		return dataSource;
	}

	public java.util.logging.Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return null;
	}
}
