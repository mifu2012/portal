package com.infosmart.portal.util.db;

import java.util.Map;

import org.apache.log4j.Logger;

public class CustomerContextHolder {

	protected static final Logger logger = Logger
			.getLogger(CustomerContextHolder.class);

	private static final ThreadLocal contextHolder = new ThreadLocal();

	public static void setDataSourceType(String dataSourceType) {
		contextHolder.set(dataSourceType);
	}

	public static String getDataSourceType() {
		logger.info("DS_TYPE:"+contextHolder.get());
		return (String) contextHolder.get();
	}

	public static void clearDataSourceType() {
		contextHolder.remove();
	}

}
