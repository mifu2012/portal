package com.infosmart.portal.service.report.impl;

import java.sql.Connection;
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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.infosmart.portal.pojo.report.GroupingField;
import com.infosmart.portal.pojo.report.ReportCell;
import com.infosmart.portal.pojo.report.ReportDataSource;
import com.infosmart.portal.pojo.report.ReportDesign;
import com.infosmart.portal.service.impl.BaseServiceImpl;
import com.infosmart.portal.service.report.NewReportService;
import com.infosmart.portal.util.StringDes;
import com.infosmart.portal.util.StringUtils;
import com.infosmart.portal.util.report.NamedParameterStatement;
import com.infosmart.portal.util.report.PoolManager;
import com.infosmart.portal.vo.report.ReportPageInfo;

@Service
public class NewReportServiceImpl extends BaseServiceImpl implements
		NewReportService {

	@Override
	public ReportDesign getReportDesignById(Integer rptDesignId) {
		return this.myBatisDao.get(
				"com.infosmart.mapper.ReportDesignMapper.getReportDesignById",
				rptDesignId);
	}

	/**
	 * 获取报表数据源
	 */
	@Override
	public ReportDataSource getDataSourceById(Integer id) {
		ReportDataSource dataSource = null;
		dataSource = this.myBatisDao
				.get("com.infosmart.mapper.ReportDesignMapper.getDataSourceById",
						id);
		return dataSource;
	}

	/**
	 * 获取报表数据源
	 */
	@Override
	public List<Map<String, String>> listReportData(String querySql,
			List<ReportCell> queryFieldList, String dataSourceId,
			Map<String, Object> paramValMap) {
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
		NamedParameterStatement pstmt = null;
		ResultSet rs = null;
		Map<String, String> reportDataMap = null;
		String querySQL = "";
		if (queryFieldList == null || queryFieldList.size() == 0) {
			querySQL = "SELECT * FROM (" + querySql + ") t";
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
			if (conn == null) {
				return reportDataList;
			}
			pstmt = new NamedParameterStatement(conn, querySQL, paramValMap);
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
					rs.close();
				}
				if (conn != null)
					pm.freeConnection(conn);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return reportDataList;
	}

	/**
	 * 查询报表导出数据
	 */
	@Override
	public List<List<String>> queryReport(ReportDesign reportDesign,
			int pageNo, List<ReportCell> reportCellList,
			Map<String, Object> paramValMap) {
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
		try {
			conn = pm.getConnection();
			List<String> columnDataList = null;
			StringBuffer queryFields = new StringBuffer("");
			if (reportCellList != null) {
				// querySQL = "SELECT * FROM (" + querySql + ") t";
				// StringBuffer queryFields = new StringBuffer("");
				for (ReportCell queryField : reportCellList) {
					if (queryFields.toString().length() > 0) {
						queryFields.append(",");
					}
					queryFields.append(queryField.getDataField());
				}
			}
			String newQuerySql = "SELECT " + queryFields + " FROM ("
					+ reportDesign.getReportSql() + ") a ";
			if (StringUtils.notNullAndSpace(reportDesign.getQueryWhere())) {
				if (reportDesign.getQueryWhere().trim().toUpperCase()
						.startsWith("AND")) {
					newQuerySql += " WHERE 1=1  "
							+ reportDesign.getQueryWhere();
				} else {
					newQuerySql += " WHERE 1=1 AND "
							+ reportDesign.getQueryWhere();
				}
			}
			// 排序字段
			if (StringUtils.notNullAndSpace(reportDesign.getOrderFieldName())) {
				newQuerySql += " ORDER BY " + reportDesign.getOrderFieldName()
						+ " " + reportDesign.getSortOrder();
			}
			this.logger.info("报表查询分页SQL:" + newQuerySql);
			// 分页查询
			/*
			 * if (reportDesign.getPageSize() > 0) { newQuerySql += " limit " +
			 * (pageNo - 1) reportDesign.getPageSize() + "," +
			 * reportDesign.getPageSize(); }
			 */
			this.logger.info("报表查询SQL:" + newQuerySql);
			pstmt = new NamedParameterStatement(conn, newQuerySql, paramValMap);
			rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			while (rs.next()) {
				columnDataList = new ArrayList<String>();
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					columnDataList.add(String.valueOf(rs.getString(i)));
				}
				reportDataList.add(columnDataList);
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
				if (conn != null)
					pm.freeConnection(conn);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return reportDataList;
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
				columnDataList = manageDataFormat(columnDataList, queryFieldList);
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
			url = "previewReport.html?type=drill&reportId=" + StringDes.StringToEnc(cell.getRelRptId()) + "&"
					+ url;
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
				columnDataList.put(
						cell.getDataField(),
						"<a href='javascript:openDrill(\"" + url + "\")' style='color:"+color+";' >"
								+ fieldString + "</a>");
						
			} else {
				columnDataList.put(
						cell.getDataField(),
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
	 * 			  : 查询数据类型 "exportData":导出数据；"queryData":查询数据。
	 * @return data1 : 处理后的数据
	 */
	private JSONArray getDynamicData(List<ReportCell> fixedColumns,
			List<ReportCell> dynamicColumns, ResultSet rs,
			ResultSetMetaData rsmd, List<ReportCell> queryFieldList, String queryType) {
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
					columnDataList = manageDataFormat(columnDataList, queryFieldList);
					// 处理相关报表（下钻报表）
					columnDataList = manageRelRpt(queryFieldList, allValMap,
							columnDataList);
				}
				// 修改动态列数据
				for (ReportCell dynamicColumn : dynamicColumns) {
					if (dynamicColumn.getDynamicHeaderFieldList() == null) {
						if (columnDataList.get(dynamicColumn.getDynamicColumnField()) == null)
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
	 * @param columnDataList 一行数据
	 * @param queryFieldList 查询数据列
	 * @return 处理后的数据
	 */
	private JSONObject manageDataFormat(JSONObject columnDataList,
			List<ReportCell> queryFieldList) {
		for (ReportCell reportCell : queryFieldList) {
			if (reportCell.getDataField() == null) continue;
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
				if (format.equals("###0") && reportCell.getFormatter().equals("integer")) continue;
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
					if (Float.parseFloat(data.replace("￥", "").replace(",", "")) >= Float.parseFloat(reportCell.getWarningValue())) {
						data = "<font color='green'>"+data+"</font>";
					} else {
						data = "<font color='red'>"+data+"</font>";
					}
				}
				columnDataList.put(reportCell.getDataField(), data);
			}
		}
		return columnDataList;
	}
	
	public List<List<String>> queryExportData(ReportDesign reportDesign,
			List<ReportCell> fieldCellList, Map<String, Object> paramValMap) {
		List<List<String>> reportDataList = new ArrayList<List<String>>();
		ReportDataSource dataSource=null;
		if (reportDesign == null) {
			this.logger.warn("报表未定义或查询列未定义");
			return null;
		}
		if(!StringUtils.notNullAndSpace(reportDesign.getDataSourceId())){
			this.logger.warn("查询报表数据失败，数据源ID为空--->");
			return reportDataList;
		}
		dataSource = this.getDataSourceById(Integer.valueOf(reportDesign
				.getDataSourceId()));
		if(dataSource==null){
			this.logger.warn("查询报表数据失败，数据源为空--->"+dataSource);
			return reportDataList;
		}
		/*解密*/
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
			conn= pm.getConnection();
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
							List<String> dynamicColumnsList = 
									getDynamicColumns(reportDesign, reportCell.getDynamicColumnField(), paramValMap);
							for (String dynamicColumn : dynamicColumnsList) {
								if (data.getJSONObject(i).get(dynamicColumn+"_"+reportCell.getDataField()) != null) {
									columnDataList.add(data.getJSONObject(i)
											.get(dynamicColumn+"_"+reportCell.getDataField()).toString());
								} else {
									columnDataList.add("");
								}
							}
						} else if (reportCell.getDynamicHeaderFieldList() != null
								|| dynamicDataFieldList.size() > 0) {
							// 如果已经不是动态列，且前面有动态列
							if (reportCell.getDynamicHeaderFieldList() == null) {
								columnDataList.addAll(manageDynamicDataFieldList(reportDesign,
										dynamicDataFieldList, paramValMap, (JSONObject)data.get(i)));
								dynamicDataFieldList = new ArrayList<ReportCell>();
								continue;
							}
							// 动态列数据列
							if (dynamicDataFieldList.size() == 0) {
								dynamicDataFieldList.add(reportCell);
							} else {
								if (dynamicDataFieldList.get(0).getDynamicHeaderFieldList()
										.equals(reportCell.getDynamicHeaderFieldList())) {
									dynamicDataFieldList.add(reportCell);
								} else {
									columnDataList.addAll(manageDynamicDataFieldList(reportDesign,
											dynamicDataFieldList, paramValMap, (JSONObject)data.get(i)));
									dynamicDataFieldList = new ArrayList<ReportCell>();
								}
							}
						} else if (reportCell.getDynamicColumnField() == null) {
							// 固定列数据
							if (data.getJSONObject(i).get(reportCell.getDataField()) != null) {
								columnDataList.add(data.getJSONObject(i).get(reportCell.getDataField()).toString());
							} else {
								columnDataList.add("");
							}
						}
					}
					if (dynamicDataFieldList.size() > 0) {
						columnDataList.addAll(manageDynamicDataFieldList(reportDesign,
								dynamicDataFieldList, paramValMap, (JSONObject)data.get(i)));
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
				if (rs != null){
					rs.close();
				}
				if (pstmt != null){
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
	 * @param reportDesign 报表配置
	 * @param dynamicDataFieldList 同级动态列
	 * @param paramValMap 参数Map
	 * @param data 一行数据
	 * @return 二级动态数据列对应数据
	 */
	private List<String> manageDynamicDataFieldList(
			ReportDesign reportDesign, List<ReportCell> dynamicDataFieldList,
			Map<String, Object> paramValMap, JSONObject data) {
		List<String> dynamicColumnsList = 
				getDynamicColumns(reportDesign, dynamicDataFieldList.get(0).getDynamicHeaderFieldList(), paramValMap);
		List<String> columnDataList = new ArrayList<String>();
		for (int j = 0; j < dynamicColumnsList.size(); j++) {
			for (ReportCell reportCell : dynamicDataFieldList) {
				if (data.get(dynamicColumnsList.get(j)+"_"+reportCell.getDataField()) != null) {
					columnDataList.add(data.get(dynamicColumnsList.get(j)+"_"+reportCell.getDataField()).toString());
				} else {
					columnDataList.add("");
				}
			}
		}
		return columnDataList;
	}
}
