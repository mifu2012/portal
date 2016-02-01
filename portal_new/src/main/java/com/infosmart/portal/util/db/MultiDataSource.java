package com.infosmart.portal.util.db;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

public class MultiDataSource implements DataSource {

	private Logger logger = Logger.getLogger(this.getClass());

	private static final Map<String,DataSource> dataSources = new HashMap<String,DataSource>();// /数据源对象池

	private DataSource dataSource = null;// //默认数据源用于测试的数据源

	public Connection getConnection() throws SQLException {
		return getDataSource().getConnection();
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return getDataSource().unwrap(iface);
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return getDataSource().isWrapperFor(iface);
	}

	public Connection getConnection(String arg0, String arg1)
			throws SQLException {
		return getDataSource().getConnection(arg0, arg1);
	}

	public PrintWriter getLogWriter() throws SQLException {
		return getDataSource().getLogWriter();
	}

	public int getLoginTimeout() throws SQLException {
		return getDataSource().getLoginTimeout();
	}

	public java.util.logging.Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return null;
	}

	public void setLogWriter(PrintWriter arg0) throws SQLException {
		getDataSource().setLogWriter(arg0);
	}

	public void setLoginTimeout(int arg0) throws SQLException {
		getDataSource().setLoginTimeout(arg0);
	}
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public DataSource getDataSource() {
		return getDataSource(SpObserver.getDbName());
	}

	private DataSource getDataSource(String dataSourceName) {
		this.logger.info("-------->" + dataSourceName);
		if (dataSourceName == null || dataSourceName.equals("")) {
			return this.dataSource;
		}
		if (dataSources.get(dataSourceName) != null) {
			return (DataSource) dataSources.get(dataSourceName);
		} else {
			String url = "";
			String user = "";
			String pwd = "";
			// 根据用户名后缀（如@20090001）查询数据库配置信息,创建新的数据源
			if ("dataSource_500w".equalsIgnoreCase(dataSourceName)) {
				url = "jdbc:mysql://192.168.0.15:3306/portal500?useUnicode=true&amp;characterEncoding=utf-8&amp;zeroDateTimeBehavior=convertToNull";
				user = "root";
				pwd = "root";
			} else {
				url = "jdbc:mysql://127.0.0.1:3306/portal_08_16?useUnicode=true&amp;characterEncoding=utf-8&amp;zeroDateTimeBehavior=convertToNull";
				user = "root";
				pwd = "";
			}
			DataSource newDataSource = createDataSource(url, user, pwd);
			//
			dataSources.put(dataSourceName, newDataSource);
			return newDataSource;
		}
	}

	private DataSource createDataSource(String url, String userName,
			String password) {
		BasicDataSource newDataSource = new BasicDataSource();
		BasicDataSource ts = (BasicDataSource) dataSource;
		newDataSource.setUsername(userName);
		newDataSource.setPassword(password);
		newDataSource.setUrl(url);
		newDataSource.setDriverClassName(ts.getDriverClassName());
		//
		try {
			this.logger.info("------------------>创建新的数据源:"
					+ newDataSource.getConnection().getMetaData().getURL());
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
		}
		return newDataSource;
	}

}
