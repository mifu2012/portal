package com.infosmart.service.report.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.infosmart.po.report.ReportChartColumn;
import com.infosmart.po.report.ReportChartFiled;
import com.infosmart.po.report.ReportDataSource;
import com.infosmart.service.impl.BaseServiceImpl;
import com.infosmart.service.report.ReportChartService;
import com.infosmart.util.StringDes;
import com.infosmart.util.StringUtils;
import com.infosmart.util.report.NamedParameterStatement;
import com.infosmart.util.report.PoolManager;

@Service
public class ReportChartServiceImpl extends BaseServiceImpl implements
		ReportChartService {

	@Override
	public List<ReportChartColumn> getTableFiledY(String querySql,
			ReportDataSource dataSource) {
		if (!StringUtils.notNullAndSpace(querySql)) {
			this.logger.warn("查询Y轴字段出错：querySql为空");
			return null;
		}
		/* 连接数据库 */
		if (dataSource == null) {
			this.logger.warn("报表数据源信息为空----->");
			return null;
		}
		List<ReportChartColumn> reportChartColumnList = new ArrayList<ReportChartColumn>();
		ReportChartColumn reportChartColumn = null;
		/* 解密 添加数据源 */
		dataSource.setPassword(StringDes.StringToDec(dataSource.getPassword()));
		PoolManager pm = new PoolManager(dataSource);
		Connection conn = null;
		NamedParameterStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = pm.getConnection();
			String newQuerySql = "select * from (" + querySql
					+ ") t  where 1=2 ";
			this.logger.info("查询语句:" + newQuerySql);
			// 参数值
			Map<String, Object> paramValMap = new HashMap<String, Object>();
			newQuerySql = NamedParameterStatement.getNewQuerySql(newQuerySql,
					paramValMap);
			this.logger.info("新查询语句:"+newQuerySql);
			pstmt = new NamedParameterStatement(conn, newQuerySql, paramValMap);
			rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				/*
				 * if (rsmd.getColumnType(i) <= 0) { continue; }
				 */
				String fielTypeName = rsmd.getColumnTypeName(i).toLowerCase();
				if (fielTypeName.equals("int") || fielTypeName.equals("float")
						|| fielTypeName.equals("double")
						|| fielTypeName.equals("char")
						|| fielTypeName.equals("integer")
						|| fielTypeName.equals("decimal")
						|| fielTypeName.equals("number")
						|| fielTypeName.equals("bigint")) {
					// 列名
					reportChartColumn = new ReportChartColumn();
					reportChartColumn.setFiledName(rsmd.getTableName(i) + "."
							+ rsmd.getColumnName(i).toLowerCase());
					reportChartColumn.setColumnLabel(rsmd.getColumnLabel(i));
					reportChartColumn.setFileType(rsmd.getColumnTypeName(i));
					reportChartColumnList.add(reportChartColumn);
				}
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
				if (conn != null) {
					// 关闭链接
					pm.freeConnection(conn);
				}

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return reportChartColumnList;
	}

	@Override
	public ReportChartFiled getIsSelOrNotTableFiled(int chartId) {
		return this.myBatisDao.get(
				"reportChartFiledMapper.getIsSelOrNotTableFiled", chartId);
	}

	@Override
	public List<ReportChartFiled> getAllReportChart(int reportId) {
		return this.myBatisDao.getList(
				"reportChartFiledMapper.getAllReportChart", reportId);
	}

	@Override
	public void updateChartFiled(ReportChartFiled reportChartFiled) {
		try {
			this.myBatisDao.update("reportChartFiledMapper.updateChartFiled",

			reportChartFiled);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteChartFiled(int chartId) {
		this.myBatisDao.delete("reportChartFiledMapper.delChartFiled", chartId);

	}

	@Override
	public List<ReportChartColumn> getTableFiledX(String querySql,
			ReportDataSource dataSource) {
		if (!StringUtils.notNullAndSpace(querySql)) {
			this.logger.warn("查询Y轴字段出错：querySql为空");
			return null;
		}
		if (dataSource == null) {
			this.logger.warn("报表数据源信息为空----->");
			return null;
		}
		List<ReportChartColumn> reportChartColumnList = new ArrayList<ReportChartColumn>();
		ReportChartColumn reportChartColumn = null;
		/* 解密 添加数据源 */
		dataSource.setPassword(StringDes.StringToDec(dataSource.getPassword()));
		PoolManager pm = new PoolManager(dataSource);
		Connection conn = null;
		NamedParameterStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = pm.getConnection();
			String newQuerySql = "select * from (" + querySql
					+ ") t  where 1=2 ";
			this.logger.info("查询语句:" + newQuerySql);
			// 参数值，只取字段，不取值，故设置为空值
			Map<String, Object> paramValMap = new HashMap<String, Object>();
			newQuerySql = NamedParameterStatement.getNewQuerySql(newQuerySql,
					paramValMap);
			this.logger.info("新查询语句:"+newQuerySql);
			pstmt = new NamedParameterStatement(conn, newQuerySql, paramValMap);
			rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				/*
				 * if (rsmd.getColumnType(i) <= 0) { continue; }
				 */
				String fielTypeName = rsmd.getColumnTypeName(i).toLowerCase();
				if (fielTypeName.equals("blob") || fielTypeName.equals("clob")
						|| fielTypeName.equals("text")) {
					continue;
				}
				reportChartColumn = new ReportChartColumn();
				reportChartColumn.setFiledName(rsmd.getTableName(i) + "."
						+ rsmd.getColumnName(i).toLowerCase());
				reportChartColumn.setColumnLabel(rsmd.getColumnLabel(i));
				reportChartColumn.setFileType(rsmd.getColumnTypeName(i));
				reportChartColumnList.add(reportChartColumn);
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
				if (conn != null) {
					// 关闭链接
					pm.freeConnection(conn);
				}

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return reportChartColumnList;
	}

	@Override
	public void insertChartFiled(ReportChartFiled reportChartFiled) {
		// TODO Auto-generated method stub
		this.myBatisDao.save("reportChartFiledMapper.addChartFiled",
				reportChartFiled);
	}

}
