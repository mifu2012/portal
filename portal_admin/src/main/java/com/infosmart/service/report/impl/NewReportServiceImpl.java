package com.infosmart.service.report.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.infosmart.po.report.GroupingField;
import com.infosmart.po.report.ReportCell;
import com.infosmart.po.report.ReportConfig;
import com.infosmart.po.report.ReportDataSource;
import com.infosmart.po.report.ReportDesign;
import com.infosmart.po.report.ReportField;
import com.infosmart.po.report.ReportPageInfo;
import com.infosmart.service.impl.BaseServiceImpl;
import com.infosmart.service.report.NewReportService;
import com.infosmart.util.StringDes;
import com.infosmart.util.StringUtils;
import com.infosmart.util.report.NamedParameterStatement;
import com.infosmart.util.report.PoolManager;

@Service
public class NewReportServiceImpl extends BaseServiceImpl implements
		NewReportService {

	@Override
	public List<ReportField> listReportField(String querySql,
			String dataSourceId) {
		ReportDataSource dataSource = null;
		List<ReportField> reportFieldList = new ArrayList<ReportField>();
		if (!StringUtils.notNullAndSpace(dataSourceId)) {
			this.logger.warn("查询报表数据失败，数据源ID为空--->");
			return reportFieldList;
		}
		if (!StringUtils.notNullAndSpace(querySql)) {
			this.logger.warn("查询报表数据失败，SQL为空--->");
			return reportFieldList;
		}
		dataSource = this.getDataSourceById(Integer.valueOf(dataSourceId));
		if (dataSource == null) {
			this.logger.warn("查询报表数据失败，数据源为空--->" + dataSource);
			return reportFieldList;
		}
		/* 解密 */
		dataSource.setPassword(StringDes.StringToDec(dataSource.getPassword()));
		PoolManager pm = new PoolManager(dataSource);
		Connection conn = null;
		NamedParameterStatement pstmt = null;
		ResultSet rs = null;
		ReportField reportField = null;
		try {
			// conn = this.myBatisDao.getSqlSession().getConnection();
			conn = pm.getConnection();
			// 只需要表结构，不需要数据
			querySql = querySql.replaceAll("where", "where 1=2 and");
			String newQuerySql = "select * from (" + querySql
					+ ") t  where 1=2 ";
			this.logger.info("查询语句:" + newQuerySql);
			// 参数值
			Map<String, Object> paramValMap = new HashMap<String, Object>();
			newQuerySql = NamedParameterStatement.getNewQuerySql(newQuerySql,
					paramValMap);
			this.logger.info("reportSql:" + newQuerySql);
			pstmt = new NamedParameterStatement(conn, newQuerySql, paramValMap);
			rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				// blob,clob,text不能查询
				// if (rsmd.getColumnType(i) <= 0)
				// continue;
				String fielTypeName = rsmd.getColumnTypeName(i).toLowerCase();
				if (fielTypeName.equals("blob") || fielTypeName.equals("clob")
						|| fielTypeName.equals("text")) {
					continue;
				}
				reportField = new ReportField();
				reportField.setColumnClassName(rsmd.getColumnClassName(i));
				reportField.setColumnCode(rsmd.getColumnName(i));
				reportField.setColumnFullCode(rsmd.getTableName(i) + "."
						+ rsmd.getColumnLabel(i));
				reportField.setColumnScale(rsmd.getScale(i));
				reportField.setColumnLabel(rsmd.getColumnLabel(i));
				reportField.setColumnType(rsmd.getColumnType(i));
				reportField.setColumnTypeName(rsmd.getColumnTypeName(i));
				// add
				reportFieldList.add(reportField);
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					// 关闭链接
					pm.freeConnection(conn);
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return reportFieldList;
	}

	@Override
	public List<Map<String, String>> listReportData(String querySql,
			List<ReportCell> queryFieldList, String dataSourceId) {
		if (!StringUtils.notNullAndSpace(querySql)) {
			this.logger.warn("查询报表数据失败，SQL为空--->");
			return null;
		}
		ReportDataSource dataSource = null;
		List<Map<String, String>> reportDataList = new ArrayList<Map<String, String>>();
		if (!StringUtils.notNullAndSpace(dataSourceId)) {
			this.logger.warn("查询报表数据失败，数据源ID为空--->");
			return reportDataList;
		}
		dataSource = this.getDataSourceById(Integer.valueOf(dataSourceId));
		if (dataSource == null) {
			this.logger.warn("查询报表数据失败，数据源为空--->" + dataSource);
			return reportDataList;
		}
		/* 解密 */
		dataSource.setPassword(StringDes.StringToDec(dataSource.getPassword()));
		PoolManager pm = new PoolManager(dataSource);
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Map<String, String> reportDataMap = null;
		String querySQL = "";
		if (queryFieldList == null || queryFieldList.size() == 0) {
			querySQL = "SELECT * FROM (" + querySql
					+ " GROUP BY a.stat_date limit 10) t";
		} else {
			StringBuffer queryFields = new StringBuffer("");
			for (ReportCell queryField : queryFieldList) {
				if (queryFields.toString().length() > 0) {
					queryFields.append(",");
				}
				queryFields.append(queryField.getDataField());
			}
			querySQL = "SELECT " + queryFields.toString() + " FROM ("
					+ querySql + ") t";
		}
		this.logger.info("querySQL:" + querySQL);
		try {
			// conn = this.myBatisDao.getSqlSession().getConnection();
			conn = pm.getConnection();
			pstmt = conn.prepareStatement(querySQL);
			rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			while (rs.next()) {
				reportDataMap = new HashMap<String, String>();
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					reportDataMap.put(rsmd.getColumnName(i), rs.getString(i));
				}
				reportDataList.add(reportDataMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					// 关闭链接
					pm.freeConnection(conn);
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return reportDataList;
	}

	@Override
	public ReportDesign getReportDesignById(Integer rptDesignId) {
		return this.myBatisDao.get(
				"com.infosmart.mapper.ReportDesignMapper.getReportDesignById",
				rptDesignId);
	}

	@Override
	public void updateReportDesign(ReportDesign repDesign) {
		if (repDesign == null) {
			this.logger.warn("修改报表设置失败，参数为空");
			return;
		}
		this.myBatisDao.update(
				"com.infosmart.mapper.ReportDesignMapper.updateReportDesign",
				repDesign);
	}

	/**
	 * 获取报表数据源
	 */
	@Override
	public ReportDataSource getDataSourceById(Integer id) {
		ReportDataSource dataSource = null;
		dataSource = this.myBatisDao
				.get("com.infosmart.mapper.ReportDataSourceMapper.getDataSourceById",
						id);
		// //解密
		// if(dataSource!=null
		// &&StringUtils.notNullAndSpace(dataSource.getPassword())){
		// dataSource.setPassword(StringDes.StringToDec(dataSource.getPassword()));
		// }
		return dataSource;
	}

	/**
	 * 查询所有数据源信息
	 * 
	 * @return
	 */
	@Override
	public List<ReportDataSource> getListDataSource() {
		return this.myBatisDao
				.getList("com.infosmart.mapper.ReportDataSourceMapper.listDataSource");
	}

	/**
	 * 编辑/添加数据源信息
	 * 
	 * @param dataSource
	 * @param request
	 */
	@Override
	public void saveDataSource(ReportDataSource dataSource, String sourceId,
			HttpServletRequest request) {
		if (dataSource == null) {
			this.logger.warn("编辑/添加数据源信息失败，对象dataSource为空.");
			return;
		}
		/* 密码加密 */
		if (StringUtils.notNullAndSpace(dataSource.getPassword())) {
			dataSource.setPassword(StringDes.StringToEnc(dataSource
					.getPassword()));
		}
		if (StringUtils.notNullAndSpace(sourceId)) {
			this.myBatisDao
					.update("com.infosmart.mapper.ReportDataSourceMapper.updateDataSource",
							dataSource);
		} else {
			this.myBatisDao
					.save("com.infosmart.mapper.ReportDataSourceMapper.saveDataSource",
							dataSource);
		}

	}

	/**
	 * 更新报表sql
	 */
	@Override
	public void updateReportSql(ReportDesign reportDesign) {
		if (reportDesign == null) {
			this.logger.warn("修改报表设置失败，参数为空");
			return;
		}
		String querySQL = reportDesign.getReportSql();
		querySQL = StringUtils.IgnoreCaseReplace(querySQL, "like$", " like $");
		//所有的LIKE 转为小写
		Pattern pattern = Pattern.compile(" like ", Pattern.CASE_INSENSITIVE);
		Matcher matcher= pattern.matcher(querySQL);
		querySQL = matcher.replaceAll(" like ");
		//this.logger.info("---->新的SQL语句:" + querySQL);
		reportDesign.setReportSql(querySQL);
		this.logger.info("||||--->报表SQL语句：" + reportDesign.getReportSql());
		// 保存报表数据源，SQL语句与数据库连接
		myBatisDao.update(
				"com.infosmart.mapper.ReportDesignMapper.updateReportSql",
				reportDesign);
		// 删除原查询条件
		this.logger.info("删除原报表查询条件");
		Map paramMap = new HashMap();
		paramMap.put("reportId", reportDesign.getReportId());
		this.myBatisDao
				.delete("com.infosmart.mapper.ReportConfigMapper.deleteReportConfigByRptId",
						paramMap);
		// 保存新查询条件
		Map<String, List<String>> paramNameMap = NamedParameterStatement
				.getSQLParam(reportDesign.getReportSql());
		List<ReportConfig> rptFieldList = new ArrayList<ReportConfig>();
		ReportConfig reportConfig = null;
		if (paramNameMap != null && paramNameMap.size() > 0) {
			Map.Entry<String, Object> entry = null;
			Iterator iter = paramNameMap.entrySet().iterator();
			Integer showOrder = 0;
			while (iter.hasNext()) {
				entry = (Map.Entry) iter.next();
				if (entry != null
						&& StringUtils.notNullAndSpace(entry.toString())) {
					String paramName = entry.getKey().toString();
					paramName = StringUtils.replaceAll(paramName, "%", "");
					reportConfig = new ReportConfig();
					reportConfig.setReportId(reportDesign.getReportId());
					reportConfig.setColumnCode(paramName);
					reportConfig.setColumnLabel(paramName);
					reportConfig.setControlType(0);
					showOrder = showOrder + 1;
					reportConfig.setColumnSort(showOrder);
					//
					this.logger.info("------>" + reportConfig.getColumnCode());
					rptFieldList.add(reportConfig);
				}
			}
			// 保存查询条件
			this.logger.info("保存新报表查询条件");
			if (rptFieldList != null && !rptFieldList.isEmpty()) {
				this.myBatisDao
						.save("com.infosmart.mapper.ReportConfigMapper.insertReportConfigByBatch",
								rptFieldList);
			}
		}
	}

	/**
	 * 测试数据源链接
	 */
	@Override
	public boolean testDataSource(ReportDataSource reportDataSource) {
		if (reportDataSource == null) {
			this.logger.warn("测试数据源链接失败，参数为空");
			return false;
		}
		PoolManager poolManager = new PoolManager(reportDataSource);
		Connection conn = poolManager.getConnection();
		if (conn == null) {
			this.logger.error("--------------->数据源测试失败");
			return false;// 失败
		} else {
			poolManager.freeConnection(conn);
			PoolManager.ShutdownPool();
			return true;
		}
	}

	/**
	 * 测试报表语句
	 */
	@Override
	public int testReportSql(ReportDataSource dataSource, String reportSql) {
		this.logger.info("测试SQL语句：" + reportSql);
		if (dataSource == null) {
			this.logger.warn("测试报表语句，参数[dataSource]为空");
			return 0;
		}
		if (!StringUtils.notNullAndSpace(reportSql)) {
			this.logger.warn("测试报表语句，参数[reportSql]为空");
			return 0;
		}
		int counts = 0;
		PoolManager poolManager = new PoolManager(dataSource);
		Connection conn = null;
		NamedParameterStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = poolManager.getConnection();
			// 参数值
			Map<String, Object> paramValMap = new HashMap<String, Object>();
			// 测试SQL，默认注入值为1
			Map<String, List<String>> paramNameMap = NamedParameterStatement
					.getSQLParam(reportSql);
			if (paramNameMap != null && paramNameMap.size() > 0) {
				Map.Entry<String, Object> entry = null;
				Iterator iter = paramNameMap.entrySet().iterator();
				while (iter.hasNext()) {
					entry = (Map.Entry) iter.next();
					paramValMap.put(entry.getKey(), "1");
				}
			}
			reportSql = NamedParameterStatement.getNewQuerySql(reportSql,
					paramValMap);
			this.logger.info("新的reportSql:" + reportSql);
			pstmt = new NamedParameterStatement(conn, reportSql, paramValMap);
			rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			List<String> columnCodeList = new ArrayList<String>();
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				if (!columnCodeList.contains(rsmd.getColumnLabel(i))) {
					columnCodeList.add(rsmd.getColumnLabel(i));
				} else {
					counts = -2;// 重复字段
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.info("数据库SQL语句测试失败!");
			counts = -1;
		} finally {
			try {
				// 关闭链接等
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
				poolManager.freeConnection(conn);
				PoolManager.ShutdownPool();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		this.logger.info("测试结果：" + counts);
		return counts;
	}

	public List<String> getDynamicColumns(ReportDesign reportInfo,
			String queryColumn, Map<String, Object> paramValMap) {
		List<String> columnList = new ArrayList<String>();
		if (reportInfo == null) {
			this.logger.warn("报表未定义或查询列未定义");
			return null;
		}
		ReportDataSource dataSource = null;
		if (!StringUtils.notNullAndSpace(reportInfo.getDataSourceId())) {
			this.logger.warn("查询报表数据失败，数据源ID为空--->");
			return columnList;
		}
		dataSource = this.getDataSourceById(Integer.valueOf(reportInfo
				.getDataSourceId()));
		if (dataSource == null) {
			this.logger.warn("查询报表数据失败，数据源为空--->" + dataSource);
			return columnList;
		}
		/* 解密 */
		dataSource.setPassword(StringDes.StringToDec(dataSource.getPassword()));
		PoolManager pm = new PoolManager(dataSource);
		Connection conn = null;
		NamedParameterStatement pstmt = null;
		ResultSet rs = null;
		String countSql = "select count(0) from (" + reportInfo.getReportSql()
				+ ") tmp_count"; // 记录统计
		String newQuerySql = null;
		try {
			conn = pm.getConnection();
			newQuerySql = "SELECT DISTINCT " + queryColumn + " FROM ("
					+ reportInfo.getReportSql() + ") a ORDER BY " + queryColumn;
			this.logger.info("报表查询SQL:" + newQuerySql);
			pstmt = new NamedParameterStatement(conn, newQuerySql, paramValMap);
			rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			while (rs.next()) {
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					if (rs.getString(i) == null) {
						continue;
					}
					columnList.add(rs.getString(i));
					// columnDataList.put(rsmd.getColumnName(i),
					// rs.getString(i));
				}
				// data.add(columnDataList);
			}
		} catch (Exception e) {
			this.logger.error("ERROR_QUERY_SQL:" + newQuerySql);
			this.logger.error("ERROR_UNT_SQL:" + countSql);
			e.printStackTrace();
			this.logger.error("查询自定义SQL报表[" + reportInfo.getReportName()
					+ "]失败:" + e.getMessage(), e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					pm.freeConnection(conn);
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return columnList;
	}

	/**
	 * 异步加载数据
	 * 
	 */
	@Override
	public JSONObject queryReportJsonDataByPaging(ReportDesign reportInfo,
			List<ReportCell> queryFieldList, int pageNo,
			List<GroupingField> groupFieldList, Map<String, Object> paramValMap) {
		JSONObject reportData = new JSONObject();
		if (reportInfo == null) {
			this.logger.warn("报表未定义或查询列未定义");
			return null;
		}
		ReportDataSource dataSource = null;
		if (!StringUtils.notNullAndSpace(reportInfo.getDataSourceId())) {
			this.logger.warn("查询报表数据失败，数据源ID为空--->");
			return reportData;
		}
		dataSource = this.getDataSourceById(Integer.valueOf(reportInfo
				.getDataSourceId()));
		if (dataSource == null) {
			this.logger.warn("查询报表数据失败，数据源为空--->" + dataSource);
			return reportData;
		}
		/* 解密 */
		dataSource.setPassword(StringDes.StringToDec(dataSource.getPassword()));
		PoolManager pm = new PoolManager(dataSource);
		Connection conn = null;
		NamedParameterStatement pstmt = null;
		ResultSet rs = null;
		ReportPageInfo pageInfo = new ReportPageInfo();
		String countSql = "select count(0) from (" + reportInfo.getReportSql()
				+ ") tmp_count"; // 记录统计
		String newQuerySql = null;
		// 动态列
		List<ReportCell> dynamicColumns = new ArrayList<ReportCell>();
		// 固定列
		List<ReportCell> fixedColumns = new ArrayList<ReportCell>();
		try {
			// 查询字段
			StringBuffer queryFields = new StringBuffer("");
			if (queryFieldList != null) {
				for (ReportCell queryField : queryFieldList) {
					if (queryFields.toString().length() > 0) {
						queryFields.append(",");
					}
					if (queryField.getDynamicColumnField() != null) {
						if (queryField.getDataField() != null) {
							queryFields.append(queryField
									.getDynamicColumnField() + ",");
							dynamicColumns.add(queryField);
						} else {
							queryFields.append(queryField
									.getDynamicColumnField());
							continue;
						}
					} else if (queryField.getDynamicHeaderFieldList() != null) {
						dynamicColumns.add(queryField);
					} else {
						fixedColumns.add(queryField);
					}
					queryFields.append(queryField.getDataField());
				}
				if (groupFieldList != null) {
					for (GroupingField groupField : groupFieldList) {
						if (groupField.getIsDataColumn() == 0) {
							if (queryFields.toString().length() > 0) {
								queryFields.append(",");
							}
							queryFields.append(groupField.getFieldName());
						}
					}
				}
			}
			this.logger.info("queryFields==" + queryFields);
			conn = pm.getConnection();
			newQuerySql = "SELECT " + queryFields + " FROM ("
					+ reportInfo.getReportSql() + ") a ";
			// 排序字段
			if (StringUtils.notNullAndSpace(reportInfo.getOrderFieldName())) {
				int i = reportInfo.getOrderFieldName().lastIndexOf(",");
				if (StringUtils.notNullAndSpace(reportInfo.getOrderFieldName()
						.substring(i + 1))) {
					newQuerySql += " ORDER BY "
							+ reportInfo.getOrderFieldName() + " "
							+ reportInfo.getSortOrder();
				} else {
					newQuerySql += " ORDER BY "
							+ reportInfo.getOrderFieldName().substring(0, i);
				}
			}
			this.logger.info("报表查询分页SQL:" + newQuerySql);
			// 分页查询
			if (dynamicColumns.size() == 0) {
				newQuerySql = this.getNewQuerySql(reportInfo, dataSource,
						newQuerySql, pageNo);
			}
			this.logger.info("报表查询SQL:--------------------->" + newQuerySql);
			pstmt = new NamedParameterStatement(conn, newQuerySql, paramValMap);
			rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			JSONArray data = new JSONArray();
			// 处理数据
			if (dynamicColumns.size() == 0) {
				data = getData(rs, rsmd, queryFieldList);
			} else {
				data = getDynamicData(fixedColumns, dynamicColumns, rs, rsmd,
						queryFieldList, "queryData");
			}
			/*
			 * 查询总数据量
			 */
			if (dynamicColumns.size() == 0) {
				pstmt = new NamedParameterStatement(conn, countSql, paramValMap);
				// 设置总的记录数
				rs = pstmt.executeQuery();
				if (rs.next()) {
					pageInfo.setTotalRowNum(rs.getInt(1));
				}
				// 总页数
				pageInfo.setTotalPageNum((int) Math.ceil(pageInfo
						.getTotalRowNum() * 1.0 / reportInfo.getPageSize()));
			} else {
				pageInfo.setTotalPageNum((int) Math.ceil(data.size() * 1.0
						/ reportInfo.getPageSize()));
				pageInfo.setTotalRowNum(data.size());
			}
			reportData.put("page", pageNo); // 当前页
			reportData.put("total", pageInfo.getTotalPageNum()); // 总页数
			reportData.put("records", pageInfo.getTotalRowNum()); // 总记录数
			if (dynamicColumns.size() != 0) {
				// 如果为动态列数据，获取所需的数据
				int size = data.size();
				int startRowNum = (pageNo - 1) * reportInfo.getPageSize();
				int endRowNum = pageNo * reportInfo.getPageSize() - 1;
				JSONArray tempData = new JSONArray();
				for (int i = 0; i < size; i++) {
					if (i >= startRowNum && i <= endRowNum) {
						tempData.add(data.get(i));
					}
				}
				data = tempData;
			}
			reportData.put("rows", data);
		} catch (Exception e) {
			this.logger.error("ERROR_QUERY_SQL:" + newQuerySql);
			this.logger.error("ERROR_UNT_SQL:" + countSql);
			e.printStackTrace();
			this.logger.error("查询自定义SQL报表[" + reportInfo.getReportName()
					+ "]失败:" + e.getMessage(), e);
			reportData.put("exception", "查询自定义报表‘" + reportInfo.getReportName()
					+ "’失败:" + e.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					pm.freeConnection(conn);
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return reportData;
	}

	/**
	 * 表格为非动态列时获取数据
	 * 
	 * @param rs
	 * @param rsmd
	 * @param queryFieldList
	 * @return
	 */
	private JSONArray getData(ResultSet rs, ResultSetMetaData rsmd,
			List<ReportCell> queryFieldList) {
		JSONObject columnDataList = new JSONObject();
		JSONArray data = new JSONArray();
		Map<String, String> allValMap = new HashMap<String, String>();// 用于下钻处理
		try {
			while (rs.next()) {
				// 清空数据
				allValMap.clear();
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					columnDataList.put(rsmd.getColumnName(i), rs.getString(i));
					allValMap.put(rsmd.getColumnName(i), rs.getString(i));
				}
				// 处理数据格式
				columnDataList = manageDataFormat(columnDataList,
						queryFieldList);
				// 处理相关报表（下钻报表）
				columnDataList = manageRelRpt(queryFieldList, allValMap,
						columnDataList);
				data.add(columnDataList);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * 处理关联报表（下钻）
	 * 
	 * @param queryFieldList
	 *            ：数据列
	 * @param allValMap
	 *            ：一条数据（用于下钻）
	 * @param columnDataList
	 *            ：一条数据
	 * @return columnDataList：处理后的数据（改为link）
	 */
	private JSONObject manageRelRpt(List<ReportCell> queryFieldList,
			Map<String, String> allValMap, JSONObject columnDataList) {
		Map.Entry<String, String> entry = null;
		for (ReportCell cell : queryFieldList) {
			if (!StringUtils.notNullAndSpace(cell.getLink()))
				continue;
			// 下钻的URL参数替换为真实值
			String url = cell.getLink();
			for (Iterator it = allValMap.entrySet().iterator(); it.hasNext();) {
				entry = (Map.Entry<String, String>) it.next();
				if (url.indexOf("#" + entry.getKey().trim() + "#") != -1) {
					url = StringUtils.replace(url, "#" + entry.getKey().trim()
							+ "#", entry.getValue());
				}
			}
			url = "previewReport.html?type=drill&reportId="
					+ cell.getRelRptId() + "&" + url;
			// 如果是前台
			// url+="&reportId="+cell.getRelRptId();
			if (!columnDataList.containsKey(cell.getDataField()))
				continue;
			String fieldString = columnDataList.getString(cell.getDataField());
			if (fieldString.indexOf("font") > -1) {
				// 处理预警值
				String color = "green";
				if (fieldString.indexOf("red") > -1) {
					color = "red";
				}
				fieldString = fieldString.replaceAll("<(.*)>(.*)</font>", "$2");
				columnDataList.put(cell.getDataField(),
						"<a href='javascript:openDrill(\"" + url
								+ "\")' style='color:" + color + ";' >"
								+ fieldString + "</a>");

			} else {
				columnDataList.put(cell.getDataField(),
						"<a href='javascript:openDrill(\"" + url + "\")' >"
								+ fieldString + "</a>");
			}
		}
		return columnDataList;
	}

	/**
	 * 动态列时，获取表格数据
	 * 
	 * @param fixedColumns
	 *            : 固定列，用于作为数据的唯一标示
	 * @param dynamicColumns
	 *            : 动态列
	 * @param rs
	 *            : 查询的结果
	 * @param rsmd
	 *            : 查询结果的数据类型
	 * @param queryFieldList
	 *            : 查询数据列，用于下钻
	 * @param queryType
	 *            : 查询数据类型 "exportData":导出数据；"queryData":查询数据。
	 * @return data1 : 处理后的数据
	 */
	private JSONArray getDynamicData(List<ReportCell> fixedColumns,
			List<ReportCell> dynamicColumns, ResultSet rs,
			ResultSetMetaData rsmd, List<ReportCell> queryFieldList,
			String queryType) {
		JSONArray data1 = new JSONArray();
		Map<String, JSONObject> data = new LinkedHashMap<String, JSONObject>();
		Map<String, String> allValMap = new HashMap<String, String>();// 用于下钻处理
		try {
			while (rs.next()) {
				JSONObject columnDataList = new JSONObject();
				String firstColumn = "";
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					for (ReportCell fixedColumn : fixedColumns) {
						if (rsmd.getColumnName(i).equals(
								fixedColumn.getDataField())) {
							firstColumn += rs.getString(i);
							break;
						}
					}
					columnDataList.put(rsmd.getColumnName(i), rs.getString(i));
					allValMap.put(rsmd.getColumnName(i), rs.getString(i));
				}
				if (queryType.equals("queryData")) {
					// 处理数据格式
					columnDataList = manageDataFormat(columnDataList,
							queryFieldList);
					// 处理相关报表（下钻报表）
					columnDataList = manageRelRpt(queryFieldList, allValMap,
							columnDataList);
				}
				// 修改动态列数据
				for (ReportCell dynamicColumn : dynamicColumns) {
					if (dynamicColumn.getDynamicHeaderFieldList() == null) {
						if (columnDataList.get(dynamicColumn
								.getDynamicColumnField()) == null)
							continue;
						String dynamicComlumnField = columnDataList.get(
								dynamicColumn.getDynamicColumnField())
								.toString();
						String tempValue = columnDataList.get(
								dynamicColumn.getDataField()).toString();
						// columnDataList.remove(dynamicColumn.getDynamicColumnField());
						// columnDataList.remove(dynamicColumn.getDataField());
						columnDataList.put(dynamicComlumnField + "_"
								+ dynamicColumn.getDataField(), tempValue);
					} else {
						if (columnDataList.get(dynamicColumn
								.getDynamicHeaderFieldList()) == null)
							continue;
						String dynamicComlumnField = columnDataList.get(
								dynamicColumn.getDynamicHeaderFieldList())
								.toString();
						String tempValue = columnDataList.get(
								dynamicColumn.getDataField()).toString();
						columnDataList.put(dynamicComlumnField + "_"
								+ dynamicColumn.getDataField(), tempValue);
					}
				}
				if (!data.containsKey(firstColumn)) {
					data.put(firstColumn, columnDataList);
				} else {
					data.get(firstColumn).putAll(columnDataList);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (data.size() == 0) {
			return data1;
		}
		Iterator tempData = data.entrySet().iterator();
		Map.Entry entry = null;
		while (tempData.hasNext()) {
			entry = (Map.Entry) tempData.next();
			data1.add(entry.getValue());
		}
		return data1;
	}

	/**
	 * 处理数据格式
	 * 
	 * @param columnDataList
	 *            一行数据
	 * @param queryFieldList
	 *            查询数据列
	 * @return 处理后的数据
	 */
	private JSONObject manageDataFormat(JSONObject columnDataList,
			List<ReportCell> queryFieldList) {
		for (ReportCell reportCell : queryFieldList) {
			if (reportCell == null || reportCell.getDataField() == null)
				continue;
			if (reportCell.getFormatter().equals("number")
					|| reportCell.getFormatter().equals("integer")) {
				String format = "";
				// 千分符
				if (reportCell.getCommas() == 1) {
					format += "###,###,###,###,##0";
				} else {
					format += "###0";
				}
				// 小数位
				for (int i = 0; i < reportCell.getScale(); i++) {
					if (i == 0) {
						format += ".";
					}
					format += "0";
				}
				// 小数位全部显示
				if (reportCell.getScale() == -1) {
					format += ".#####################";
				}
				// 货币符
				if (reportCell.getCurrency() == 1) {
					format = "￥" + format;
				}
				if (format.equals("###0")
						&& reportCell.getFormatter().equals("integer"))
					continue;
				String data = "";
				if (columnDataList.get(reportCell.getDataField()) != null) {
					data = columnDataList.get(reportCell.getDataField())
							.toString();
				} else {
					columnDataList.put(reportCell.getDataField(), "");
					continue;
				}
				DecimalFormat df = new DecimalFormat(format);
				data = df.format(Float.valueOf(data));
				// 预警值
				if (StringUtils.notNullAndSpace(reportCell.getWarningValue())) {
					String a = data.replace("￥", "").replace(",", "");
					if (Float
							.parseFloat(data.replace("￥", "").replace(",", "")) >= Float
							.parseFloat(reportCell.getWarningValue())) {
						data = "<font color='green'>" + data + "</font>";
					} else {
						data = "<font color='red'>" + data + "</font>";
					}
				}
				columnDataList.put(reportCell.getDataField(), data);
			}
		}
		return columnDataList;
	}

	/**
	 * 校验驱动名
	 */
	@Override
	public String checkDriverName(ReportDataSource dataSource,
			String aliasDriverName) {
		List<ReportDataSource> dataSourcelist = myBatisDao.getList(
				"com.infosmart.mapper.ReportDataSourceMapper.checkDriverName",
				dataSource);
		if (StringUtils.notNullAndSpace(aliasDriverName)
				&& (dataSourcelist != null && !(dataSourcelist.isEmpty()))) {
			if (dataSourcelist.size() <= 1) {
				return "true";
			} else {
				return "false";
			}
		}
		if (dataSourcelist == null || dataSourcelist.isEmpty()) {
			return "true";
		} else {
			return "false";
		}
	}

	@Override
	public List<ReportDataSource> getListReportById(String dataSourceId) {
		// TODO Auto-generated method stub
		return myBatisDao
				.getList(
						"com.infosmart.mapper.ReportDesignMapper.listReportByDataSourceId",
						dataSourceId);
	}

	@Override
	public void deleteDataSourceById(Integer dataSourceId) {
		// TODO Auto-generated method stub
		myBatisDao
				.delete("com.infosmart.mapper.ReportDataSourceMapper.deleteDataSourceById",
						dataSourceId);
	}

	public List<List<String>> queryExportData(ReportDesign reportDesign,
			List<ReportCell> fieldCellList, Map<String, Object> paramValMap) {
		List<List<String>> reportDataList = new ArrayList<List<String>>();
		ReportDataSource dataSource = null;
		if (reportDesign == null) {
			this.logger.warn("报表未定义或查询列未定义");
			return null;
		}
		if (!StringUtils.notNullAndSpace(reportDesign.getDataSourceId())) {
			this.logger.warn("查询报表数据失败，数据源ID为空--->");
			return reportDataList;
		}
		dataSource = this.getDataSourceById(Integer.valueOf(reportDesign
				.getDataSourceId()));
		if (dataSource == null) {
			this.logger.warn("查询报表数据失败，数据源为空--->" + dataSource);
			return reportDataList;
		}
		/* 解密 */
		dataSource.setPassword(StringDes.StringToDec(dataSource.getPassword()));
		PoolManager pm = new PoolManager(dataSource);
		Connection conn = null;
		NamedParameterStatement pstmt = null;
		ResultSet rs = null;
		// 动态列
		List<ReportCell> dynamicColumns = new ArrayList<ReportCell>();
		// 固定列
		List<ReportCell> fixedColumns = new ArrayList<ReportCell>();
		try {
			conn = pm.getConnection();
			List<String> columnDataList = null;
			// 查询字段
			StringBuffer queryFields = new StringBuffer("");
			if (fieldCellList != null) {
				for (ReportCell queryField : fieldCellList) {
					if (queryFields.toString().length() > 0) {
						queryFields.append(",");
					}
					if (queryField.getDynamicColumnField() != null) {
						if (queryField.getDataField() != null) {
							queryFields.append(queryField
									.getDynamicColumnField() + ",");
							dynamicColumns.add(queryField);
						} else {
							queryFields.append(queryField
									.getDynamicColumnField());
							continue;
						}
					} else if (queryField.getDynamicHeaderFieldList() != null) {
						dynamicColumns.add(queryField);
					} else {
						fixedColumns.add(queryField);
					}
					queryFields.append(queryField.getDataField());
				}
			}
			String newQuerySql = "SELECT " + queryFields + " FROM ("
					+ reportDesign.getReportSql() + ") a ";
			this.logger.info("报表查询SQL:" + newQuerySql);
			pstmt = new NamedParameterStatement(conn, newQuerySql, paramValMap);
			rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			JSONArray data = new JSONArray();
			// 处理数据
			if (dynamicColumns.size() == 0) {
				// 不存在动态列
				while (rs.next()) {
					columnDataList = new ArrayList<String>();
					for (int i = 1; i <= rsmd.getColumnCount(); i++) {
						columnDataList.add(String.valueOf(rs.getString(i)));
					}
					reportDataList.add(columnDataList);
				}
			} else {
				// 存在动态列
				data = getDynamicData(fixedColumns, dynamicColumns, rs, rsmd,
						null, "exportData");
				List<ReportCell> dynamicDataFieldList = new ArrayList<ReportCell>();
				for (int i = 0; i < data.size(); i++) {
					columnDataList = new ArrayList<String>();
					for (ReportCell reportCell : fieldCellList) {
						if (reportCell.getDynamicColumnField() != null
								&& reportCell.getDataField() != null) {
							// 一级动态列
							List<String> dynamicColumnsList = getDynamicColumns(
									reportDesign,
									reportCell.getDynamicColumnField(),
									paramValMap);
							for (String dynamicColumn : dynamicColumnsList) {
								if (data.getJSONObject(i).get(
										dynamicColumn + "_"
												+ reportCell.getDataField()) != null) {
									columnDataList
											.add(data
													.getJSONObject(i)
													.get(dynamicColumn
															+ "_"
															+ reportCell
																	.getDataField())
													.toString());
								} else {
									columnDataList.add("");
								}
							}
						} else if (reportCell.getDynamicHeaderFieldList() != null
								|| dynamicDataFieldList.size() > 0) {
							// 如果已经不是动态列，且前面有动态列
							if (reportCell.getDynamicHeaderFieldList() == null) {
								columnDataList
										.addAll(manageDynamicDataFieldList(
												reportDesign,
												dynamicDataFieldList,
												paramValMap,
												(JSONObject) data.get(i)));
								dynamicDataFieldList = new ArrayList<ReportCell>();
								continue;
							}
							// 动态列数据列
							if (dynamicDataFieldList.size() == 0) {
								dynamicDataFieldList.add(reportCell);
							} else {
								if (dynamicDataFieldList
										.get(0)
										.getDynamicHeaderFieldList()
										.equals(reportCell
												.getDynamicHeaderFieldList())) {
									dynamicDataFieldList.add(reportCell);
								} else {
									columnDataList
											.addAll(manageDynamicDataFieldList(
													reportDesign,
													dynamicDataFieldList,
													paramValMap,
													(JSONObject) data.get(i)));
									dynamicDataFieldList = new ArrayList<ReportCell>();
								}
							}
						} else if (reportCell.getDynamicColumnField() == null) {
							// 固定列数据
							if (data.getJSONObject(i).get(
									reportCell.getDataField()) != null) {
								columnDataList.add(data.getJSONObject(i)
										.get(reportCell.getDataField())
										.toString());
							} else {
								columnDataList.add("");
							}
						}
					}
					if (dynamicDataFieldList.size() > 0) {
						columnDataList.addAll(manageDynamicDataFieldList(
								reportDesign, dynamicDataFieldList,
								paramValMap, (JSONObject) data.get(i)));
						dynamicDataFieldList = new ArrayList<ReportCell>();
					}
					reportDataList.add(columnDataList);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error("查询自定义SQL报表[" + reportDesign.getReportName()
					+ "]失败:" + e.getMessage(), e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				pm.freeConnection(conn);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return reportDataList;
	}

	/**
	 * 导出报表数据，处理二级动态数据列数据
	 * 
	 * @param reportDesign
	 *            报表配置
	 * @param dynamicDataFieldList
	 *            同级动态列
	 * @param paramValMap
	 *            参数Map
	 * @param data
	 *            一行数据
	 * @return 二级动态数据列对应数据
	 */
	private List<String> manageDynamicDataFieldList(ReportDesign reportDesign,
			List<ReportCell> dynamicDataFieldList,
			Map<String, Object> paramValMap, JSONObject data) {
		List<String> dynamicColumnsList = getDynamicColumns(reportDesign,
				dynamicDataFieldList.get(0).getDynamicHeaderFieldList(),
				paramValMap);
		List<String> columnDataList = new ArrayList<String>();
		for (int j = 0; j < dynamicColumnsList.size(); j++) {
			for (ReportCell reportCell : dynamicDataFieldList) {
				if (data.get(dynamicColumnsList.get(j) + "_"
						+ reportCell.getDataField()) != null) {
					columnDataList.add(data.get(
							dynamicColumnsList.get(j) + "_"
									+ reportCell.getDataField()).toString());
				} else {
					columnDataList.add("");
				}
			}
		}
		return columnDataList;
	}
}
