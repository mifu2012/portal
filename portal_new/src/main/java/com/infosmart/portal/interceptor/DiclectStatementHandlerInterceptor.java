package com.infosmart.portal.interceptor;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.util.Properties;

import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.PreparedStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;

import com.infosmart.portal.dialect.Dialect;
import com.infosmart.portal.dialect.MySQLDialect;
import com.infosmart.portal.dialect.OracleDialect;
import com.infosmart.portal.util.ReflectUtil;
import com.infosmart.portal.util.StringUtils;

@Intercepts( { @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class DiclectStatementHandlerInterceptor implements Interceptor {

	private final Logger logger = Logger.getLogger(this.getClass());
	// 数据库方言
	private String databaseDialect = "MySQL";

	public Object intercept(Invocation invocation) throws Throwable {
		RoutingStatementHandler routingStatementHandler = (RoutingStatementHandler) invocation
				.getTarget();
		PreparedStatementHandler preparedStatementHandler = (PreparedStatementHandler) ReflectUtil
				.getFieldValue(routingStatementHandler, "delegate");
		RowBounds rowBounds = (RowBounds) ReflectUtil.getFieldValue(
				preparedStatementHandler, "rowBounds");
		BaseStatementHandler delegate = (BaseStatementHandler) ReflectUtil
				.getFieldValue(routingStatementHandler, "delegate");
		MappedStatement mappedStatement = (MappedStatement) ReflectUtil
				.getFieldValue(delegate, "mappedStatement");
		if (rowBounds == null || rowBounds == RowBounds.DEFAULT) {
			//不分页处理
			return invocation.proceed();
		}
		this.logger.info("将进行分页查询:" + mappedStatement.getId());
		BoundSql boundSql = routingStatementHandler.getBoundSql();
		String sql = boundSql.getSql();
		Dialect dialect = null;
		try {
			if ("MySQL".equalsIgnoreCase(this.databaseDialect)) {
				dialect = MySQLDialect.class.newInstance();
			} else if ("Oracle".equalsIgnoreCase(this.databaseDialect)) {
				dialect = OracleDialect.class.newInstance();
			} else {
				// 其他数据库
				String packageName = Dialect.class.getPackage().getName();
				String dialectClassName = "";
				URI uri = Dialect.class.getResource(
						"/" + packageName.replace(".", "/")).toURI();
				File classFile = new File(uri);
				for (String fileName : classFile.list()) {
					if (fileName.toLowerCase().startsWith(
							databaseDialect.toLowerCase())) {
						dialectClassName = packageName + "."
								+ fileName.substring(0, fileName.indexOf("."));
						break;
					}
				}
				if (StringUtils.notNullAndSpace(dialectClassName)) {
					dialect = (Dialect) Class.forName(dialectClassName)
							.newInstance();
				} else {
					dialect = (Dialect) Class.forName(
							packageName + "." + this.databaseDialect
									+ "Dialect").newInstance();
				}
			}
			if (dialect != null) {
				// 生成分页语句
				String paginationSql = dialect.getLimitString(sql, rowBounds
						.getOffset(), rowBounds.getLimit());
				// 分页查询语句
				ReflectUtil.setFieldValue(boundSql, "sql", paginationSql);
			}
		} catch (ClassNotFoundException e) {
			this.logger.info("数据库方言:" + databaseDialect);
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
		} catch (URISyntaxException e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
		} catch (Exception e) {
			this.logger.info("数据库方言:" + databaseDialect);
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
		}
		return invocation.proceed();
	}

	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties properties) {
		this.databaseDialect = properties.getProperty("databaseDialect");
	}
}