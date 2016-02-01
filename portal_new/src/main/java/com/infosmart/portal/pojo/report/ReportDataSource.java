package com.infosmart.portal.pojo.report;

import com.infosmart.portal.util.StringDes;
import com.infosmart.portal.util.StringUtils;

/**
 * 数据源
 * 
 * @author infosmart
 * 
 */
public class ReportDataSource {
	private Integer id;
	// private Integer reportId;
	private String driverName;
	private String dataDriver;
	private String url;
	private String userName;
	private String password;
	private String pwdDesc;
	private String querySql;
	private int dbType;
	/**
	 * mysql
	 */
	public final static int DB_TYPE_MYSQL = 0;
	/**
	 * oracle
	 */
	public final static int DB_TYPE_ORACLE = 1;
	/**
	 * sql server 2005以上版本
	 */
	public final static int DB_TYPE_SQLSERVER_2005 = 2;
	/**
	 * sql server 2000以下版本
	 */
	public final static int DB_TYPE_SQLSERVER_2000 = 3;
	/**
	 * sybase
	 */
	public final static int DB_TYPE_SYBASE = 4;
	/**
	 * db2
	 */
	public final static int DB_TYPE_DB2 = 5;
	/**
	 * PostgreSQL
	 */
	public final static int DB_TYPE_POSTGRESQL = 6;

	public int getDbType() {
		if (dataDriver != null) {
			if (dataDriver.equalsIgnoreCase("com.mysql.jdbc.Driver")) {
				dbType = ReportDataSource.DB_TYPE_MYSQL;
			} else if (dataDriver
					.equalsIgnoreCase("oracle.jdbc.driver.OracleDriver")) {
				dbType = ReportDataSource.DB_TYPE_ORACLE;
			} else if (dataDriver
					.equalsIgnoreCase("com.microsoft.sqlserver.jdbc.SQLServerDriver")) {
				dbType = ReportDataSource.DB_TYPE_SQLSERVER_2005;
			} else if (dataDriver
					.equalsIgnoreCase("com.microsoft.jdbc.sqlserver.SQLServerDriver")) {
				dbType = ReportDataSource.DB_TYPE_SQLSERVER_2000;
			} else if (dataDriver
					.equalsIgnoreCase("com.sysbase.jdbc.SybDriver")) {
				dbType = ReportDataSource.DB_TYPE_SYBASE;
			} else if (dataDriver
					.equalsIgnoreCase("com.ibm.db2.jdbc.app.DB2Driver")) {
				dbType = ReportDataSource.DB_TYPE_DB2;
			} else if (dataDriver
					.equalsIgnoreCase("com.ibm.db2.org.postgresql.Driver")) {
				dbType = ReportDataSource.DB_TYPE_POSTGRESQL;
			}
		}
		return dbType;
	}

	public String getPwdDesc() {
		if (StringUtils.notNullAndSpace(password)) {
			this.pwdDesc = StringDes.StringToDec(password);
		}
		return pwdDesc;
	}

	public void setPwdDesc(String password) {
		this.pwdDesc = password;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getDataDriver() {
		return dataDriver;
	}

	public void setDataDriver(String dataDriver) {
		this.dataDriver = dataDriver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getQuerySql() {
		return querySql;
	}

	public void setQuerySql(String querySql) {
		this.querySql = querySql;
	}

}
