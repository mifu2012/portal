package com.infosmart.util.report;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDriver;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.log4j.Logger;

import com.infosmart.po.report.ReportDataSource;



public class PoolManager {
	protected final static Logger logger = Logger.getLogger(PoolManager.class);
	// private static String driver = "com.mysql.jdbc.Driver",// 驱动
	// url =
	// "jdbc:mysql://localhost:3306/portal?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull",//
	// URL
	// name = "root",// 用户名
	// password = "root";// 密码
	private String driver = "",// 驱动
			url = "", name = "",// 用户名
			password = "";// 密码

	private Class driverClass = null;
	private ObjectPool connectionPool = null;

	public PoolManager(ReportDataSource reportDataSource) {
		this.driver = reportDataSource.getDataDriver();
		this.url = reportDataSource.getUrl();
		this.name = reportDataSource.getUserName();
		this.password = reportDataSource.getPassword();
	}

	/**
	 * 装配配置文件 initProperties
	 */
	/*
	 * private static void loadProperties() { try { java.io.InputStream stream =
	 * new java.io.FileInputStream( "config.properties"); java.util.Properties
	 * props = new java.util.Properties(); props.load(stream);
	 * 
	 * driver = props.getProperty("ORACLE_DRIVER"); url =
	 * props.getProperty("ORACLE_URL"); name =
	 * props.getProperty("ORACLE_LOGIN_NAME"); password =
	 * props.getProperty("ORACLE_LOGIN_PASSWORD");
	 * 
	 * } catch (FileNotFoundException e) { System.out.println("读取配置文件异常"); }
	 * catch (IOException ie) { System.out.println("读取配置文件时IO异常"); } }
	 */

	/**
	 * 初始化数据源
	 */
	private synchronized boolean initDataSource() {
		if (driverClass == null) {
			try {
				driverClass = Class.forName(driver);
				return true;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				this.logger.error(e.getMessage(), e);
				return false;
			}
		}
		return false;
	}

	/**
	 * 连接池启动
	 * 
	 * @throws Exception
	 */
	public void StartPool() throws Exception {
		// loadProperties();
		boolean isOk = initDataSource();
		if (!isOk) {
			throw new Exception("初始化数据源失败....");
		}
		if (connectionPool != null) {
			ShutdownPool();
		}
		try {
			connectionPool = new GenericObjectPool(null);
			ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(
					url, name, password);
			PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(
					connectionFactory, connectionPool, null, null, false, true);
			Class.forName("org.apache.commons.dbcp.PoolingDriver");
			PoolingDriver driver = (PoolingDriver) DriverManager
					.getDriver("jdbc:apache:commons:dbcp:");
			driver.registerPool("dbpool", connectionPool);
			System.out.println("装配连接池OK");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 释放连接池
	 */
	public static void ShutdownPool() {
		try {
			PoolingDriver driver = (PoolingDriver) DriverManager
					.getDriver("jdbc:apache:commons:dbcp:");
			driver.closePool("dbpool");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 取得连接池中的连接
	 * 
	 * @return
	 */
	public Connection getConnection() {
		Connection conn = null;
		try {
			if (connectionPool == null) {
				logger.info("初始化连接池。。。。。。。");
				StartPool();
			}
			conn = DriverManager
					.getConnection("jdbc:apache:commons:dbcp:dbpool");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 获取连接 getConnection
	 * 
	 * @param name
	 * @return
	 */
	public Connection getConnection(String name) {
		return getConnection();
	}

	/**
	 * 释放连接 freeConnection
	 * 
	 * @param conn
	 */
	public void freeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 释放连接 freeConnection
	 * 
	 * @param name
	 * @param con
	 */
	public void freeConnection(String name, Connection con) {
		freeConnection(con);
	}

	/**
	 * 例子 main
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		ReportDataSource reportDataSource = new ReportDataSource();
		reportDataSource.setDataDriver("com.mysql.jdbc.Driver");
		reportDataSource
				.setUrl("jdbc:mysql://localhost:3306/portal?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull");
		reportDataSource.setUserName("root");
		reportDataSource.setPassword("");
		PoolManager pm = new PoolManager(reportDataSource);

		System.out.println(pm.getConnection() == null);
		;

	}

}