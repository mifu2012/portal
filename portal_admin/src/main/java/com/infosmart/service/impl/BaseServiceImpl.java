package com.infosmart.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosmart.dialect.DB2Dialect;
import com.infosmart.dialect.Dialect;
import com.infosmart.dialect.MySQLDialect;
import com.infosmart.dialect.OracleDialect;
import com.infosmart.dialect.PostgreSQLDialect;
import com.infosmart.dialect.SQLServer2005Dialect;
import com.infosmart.dialect.SQLServerDialect;
import com.infosmart.dialect.SybaseDialect;
import com.infosmart.orm.mybatis.MyBatisDao;
import com.infosmart.po.report.ReportDataSource;
import com.infosmart.po.report.ReportDesign;
import com.infosmart.util.Const;
import com.infosmart.util.report.PoolManager;

@Service
public class BaseServiceImpl {
	@Autowired
	protected MyBatisDao myBatisDao;

	protected final Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 
	 * @param dataSourceId
	 * @return
	 */
	public PoolManager getPoolManagerByDsId(String dataSourceId) {
		if (Const.CONNECTION_POOL_MAP.containsKey(dataSourceId)) {
			return Const.CONNECTION_POOL_MAP.get(dataSourceId);
		} else {
			// 查询数据库信息，初始化连接池
			ReportDataSource dataSource = new ReportDataSource();
			dataSource.setId(Integer.valueOf(dataSourceId));
			ReportDataSource reportDataSource = this.myBatisDao
					.get("com.infosmart.mapper.ReportDataSourceMapper.getDataSource",
							dataSource);
			PoolManager poolManager = new PoolManager(reportDataSource);
			Const.CONNECTION_POOL_MAP.put(dataSourceId, poolManager);
			return poolManager;
		}
	}

	/**
	 * 获取不同数据库下的分页sql
	 * 
	 * @param reportInfo
	 * @param dataSource
	 * @param pageNo
	 * @return
	 */
	protected String getNewQuerySql(ReportDesign reportInfo,
			ReportDataSource dataSource, String newQuerySql, int pageNo) {
		Dialect dialect = null;
		// String newQuerySql="";
		if (reportInfo.getPageSize() > 0) {
			switch (dataSource.getDbType()) {
			case ReportDataSource.DB_TYPE_MYSQL: {
				dialect = new MySQLDialect();
				break;
			}
			case ReportDataSource.DB_TYPE_ORACLE: {
				dialect = new OracleDialect();
				break;
			}
			case ReportDataSource.DB_TYPE_DB2: {
				dialect = new DB2Dialect();
				break;
			}
			case ReportDataSource.DB_TYPE_POSTGRESQL: {
				dialect = new PostgreSQLDialect();
				break;
			}
			case ReportDataSource.DB_TYPE_SQLSERVER_2000: {
				dialect = new SQLServerDialect();
				break;
			}
			case ReportDataSource.DB_TYPE_SQLSERVER_2005: {
				dialect = new SQLServer2005Dialect();
				break;
			}
			case ReportDataSource.DB_TYPE_SYBASE: {
				dialect = new SybaseDialect();
				break;
			}
			}
			newQuerySql = dialect.getLimitString(newQuerySql, (pageNo - 1)
					* reportInfo.getPageSize(), reportInfo.getPageSize());
		}
		return newQuerySql;
	}

}
