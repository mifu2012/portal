package com.infosmart.orm.mybatis;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.ibatis.builder.xml.dynamic.ForEachSqlNode;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.apache.log4j.Logger;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.infosmart.view.PageInfo;


/**
 * 持久化类
 * 
 * @author infosmart
 * 
 */
@Repository
public class MyBatisDao extends SqlSessionDaoSupport {
	protected final Logger logger = Logger.getLogger(MyBatisDao.class);

	public void save(String key, Object object) {
		logger.debug("save start.");
		logger.debug(key);
		if (object != null) {
			if (object instanceof java.util.Map) {
				this.logger.info("新增数据，参数类型为map");
				Map map = (Map) object;
				Entry entry = null;
				for (Iterator it = map.entrySet().iterator(); it.hasNext();) {
					entry = (Entry) it.next();
					this.logger.info("参数[’" + entry.getKey() + "’]:"
							+ entry.getValue());
				}
			}
			logger.debug(object.toString());
		}
		getSqlSession().insert(key, object);
		logger.debug("save close.");
	}

	public void update(String key, Object object) {
		logger.debug("save start.");
		logger.debug(key);
		if (object != null) {
			if (object instanceof java.util.Map) {
				this.logger.info("参数类型为map");
				Map map = (Map) object;
				Entry entry = null;
				for (Iterator it = map.entrySet().iterator(); it.hasNext();) {
					entry = (Entry) it.next();
					this.logger.info("参数[’" + entry.getKey() + "’]:"
							+ entry.getValue());
				}
			}
			logger.debug(object.toString());
		}
		getSqlSession().update(key, object);
		logger.debug("save close.");
	}

	public void delete(String key, Serializable id) {
		logger.debug("delete start.");
		logger.debug(key);
		logger.debug(id);
		getSqlSession().delete(key, id);
		logger.debug("save close.");
	}

	public void delete(String key, Object object) {
		logger.debug("delete start.");
		logger.debug(key);
		if (object != null) {
			if (object instanceof java.util.Map) {
				this.logger.info("参数类型为map");
				Map map = (Map) object;
				Entry entry = null;
				for (Iterator it = map.entrySet().iterator(); it.hasNext();) {
					entry = (Entry) it.next();
					this.logger.info("参数[’" + entry.getKey() + "’]:"
							+ entry.getValue());
				}
			}
			logger.debug(object.toString());
		}
		getSqlSession().delete(key, object);
		logger.debug("save close.");
	}

	public <T> T get(String key, Object params) {
		logger.debug("get start.");
		logger.debug(key);
		if (params != null) {
			if (params instanceof java.util.Map) {
				this.logger.info("参数类型为map");
				Map map = (Map) params;
				Entry entry = null;
				for (Iterator it = map.entrySet().iterator(); it.hasNext();) {
					entry = (Entry) it.next();
					this.logger.info("参数[’" + entry.getKey() + "’]:"
							+ entry.getValue());
				}
			}
			logger.debug(params.toString());
		}
		try {
			return (T) getSqlSession().selectOne(key, params);
		} catch (Exception e) {
			this.logger.error("查询失败:" + key);
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
			return null;
		}
	}

	public <T> List<T> getList(String key) {
		logger.debug("getList start.");
		logger.debug(key);
		try {
			return getSqlSession().selectList(key);
		} catch (Exception e) {
			this.logger.error("查询失败:" + key);
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
			return null;
		}
	}

	public <T> List<T> getList(String key, Object params)  {
		logger.debug("getList start.");
		logger.debug(key);
		if (params != null) {
			if (params instanceof java.util.Map) {
				this.logger.info("参数类型为map");
				Map map = (Map) params;
				Entry entry = null;
				for (Iterator it = map.entrySet().iterator(); it.hasNext();) {
					entry = (Entry) it.next();
					this.logger.info("参数[’" + entry.getKey() + "’]:"
							+ entry.getValue());
				}
			}
			logger.debug(params.toString());
		}
			return getSqlSession().selectList(key, params);

	}

	/**
	 * 分页查询
	 * 
	 * @param key
	 * @param params
	 * @param pageNo
	 *            当前页码
	 * @return
	 */
	public PageInfo getListByPagination(String key, Object params, int pageNo) {
		return this.getListByPagination(key, params, pageNo,
				PageInfo.DEFAULT_PAGE_SIZE);
	}

	/**
	 * 分页查询
	 * 
	 * @param key
	 * @param params
	 * @param pageNo
	 *            当前页码
	 * @param pageSize
	 *            每页大小
	 * @return
	 */
	public PageInfo getListByPagination(String key, Object params, int pageNo,
			int pageSize) {
		logger.debug("开始分页查询");
		logger.debug(key);
		if (params != null) {
			if (params instanceof java.util.Map) {
				this.logger.info("参数类型为map");
				Map map = (Map) params;
				Entry entry = null;
				for (Iterator it = map.entrySet().iterator(); it.hasNext();) {
					entry = (Entry) it.next();
					this.logger.info("参数[’" + entry.getKey() + "’]:"
							+ entry.getValue());
				}
			}
			logger.debug(params.toString());
		}
		PageInfo pageInfo = new PageInfo(pageNo, pageSize);
		// 查询总的记录数
		Configuration configuration = this.getSqlSession().getConfiguration();
		//
		MappedStatement mappedStatement = configuration.getMappedStatement(key);
		if (mappedStatement == null) {
			this.logger.error("分页查询失败,未找到配置文件:" + key);
			return pageInfo;
		}
		try {
			// 注入参数值
			BoundSql boundSql = mappedStatement.getBoundSql(params);
			String countSql = "select count(0) from (" + boundSql.getSql()
					+ ") as tmp_count"; // 记录统计
			PreparedStatement countStmt = this.getSqlSession().getConnection()
					.prepareStatement(countSql);
			// 为预处理语句注入值
			this.setParameters(countStmt, mappedStatement, boundSql, params);
			ResultSet rs = countStmt.executeQuery();
			if (rs.next()) {
				// 设置总的记录数
				pageInfo.setTotalSize(rs.getInt(1));
			}
			rs.close();
			countStmt.close();
		} catch (SQLException e) {
			this.logger.info("查询[" + mappedStatement.getId() + "]总的记录数失败");
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
		}
		try {
			List dataList = getSqlSession().selectList(
					key,
					params,
					new RowBounds((pageInfo.getPageNo() - 1)
							* pageInfo.getPageSize(), pageInfo.getPageSize()));
			// 记录集
			pageInfo.setDataList(dataList);
		} catch (Exception e) {
			this.logger.error("查询失败:" + key);
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
		}
		return pageInfo;
	}

	private int queryTotalSize() {
		return 0;
	}

	/**
	 * 对SQL参数(?)设值,参考org.apache.ibatis.executor.parameter.DefaultParameterHandler
	 * 
	 * @param ps
	 * @param mappedStatement
	 * @param boundSql
	 * @param parameterObject
	 * @throws SQLException
	 */
	private void setParameters(PreparedStatement ps,
			MappedStatement mappedStatement, BoundSql boundSql,
			Object parameterObject) throws SQLException {
		ErrorContext.instance().activity("setting parameters").object(
				mappedStatement.getParameterMap().getId());
		List<ParameterMapping> parameterMappings = boundSql
				.getParameterMappings();
		if (parameterMappings != null) {
			Configuration configuration = mappedStatement.getConfiguration();
			TypeHandlerRegistry typeHandlerRegistry = configuration
					.getTypeHandlerRegistry();
			MetaObject metaObject = parameterObject == null ? null
					: configuration.newMetaObject(parameterObject);
			for (int i = 0; i < parameterMappings.size(); i++) {
				ParameterMapping parameterMapping = parameterMappings.get(i);
				if (parameterMapping.getMode() != ParameterMode.OUT) {
					Object value;
					String propertyName = parameterMapping.getProperty();
					PropertyTokenizer prop = new PropertyTokenizer(propertyName);
					if (parameterObject == null) {
						value = null;
					} else if (typeHandlerRegistry
							.hasTypeHandler(parameterObject.getClass())) {
						value = parameterObject;
					} else if (boundSql.hasAdditionalParameter(propertyName)) {
						value = boundSql.getAdditionalParameter(propertyName);
					} else if (propertyName
							.startsWith(ForEachSqlNode.ITEM_PREFIX)
							&& boundSql.hasAdditionalParameter(prop.getName())) {
						value = boundSql.getAdditionalParameter(prop.getName());
						if (value != null) {
							value = configuration.newMetaObject(value)
									.getValue(
											propertyName.substring(prop
													.getName().length()));
						}
					} else {
						value = metaObject == null ? null : metaObject
								.getValue(propertyName);
					}
					TypeHandler typeHandler = parameterMapping.getTypeHandler();
					if (typeHandler == null) {
						throw new ExecutorException(
								"There was no TypeHandler found for parameter "
										+ propertyName + " of statement "
										+ mappedStatement.getId());
					}
					typeHandler.setParameter(ps, i + 1, value, parameterMapping
							.getJdbcType());
				}
			}
		}
	}
}
