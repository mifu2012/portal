package com.infosmart.portal.service.report.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.infosmart.portal.pojo.User;
import com.infosmart.portal.pojo.report.DimensionDetail;
import com.infosmart.portal.pojo.report.DimensionDetailSec;
import com.infosmart.portal.pojo.report.ReportConfig;
import com.infosmart.portal.pojo.report.ReportDesign;
import com.infosmart.portal.pojo.report.SelfReport;
import com.infosmart.portal.pojo.report.SelfReportColumn;
import com.infosmart.portal.service.impl.BaseServiceImpl;
import com.infosmart.portal.service.report.SelfReportService;
import com.infosmart.portal.util.StringUtils;
import com.infosmart.portal.vo.ReportPageInfo;

@Service
public class SelfReportServiceImpl extends BaseServiceImpl implements
		SelfReportService {

	/*
	 * @Override public ReportDesign getSelfReportConfigById(String reportId) {
	 * return this.myBatisDao
	 * .get("com.infosmart.portal.pojo.SelfApplyMapper.queryReportConfigById",
	 * reportId); }
	 */

	/**
	 * 用户权限
	 * 
	 * @param userId
	 * @return
	 */
	public User getUserAndRoleById(Integer userId) {
		User user = (User) myBatisDao.get(
				"com.infosmart.mapper.SelfReportMapper.getUserAndRoleById",
				userId);
		return user;
	}

	public List<SelfReport> listAllParentReport() {
		return myBatisDao
				.getList("com.infosmart.mapper.SelfReportMapper.listAllParentReport");
	}

	/**
	 * 获取某目录下所有报表
	 * 
	 * @param userId
	 * @return
	 */
	public List<SelfReport> listTreeReportByParentId(Integer parentId) {
		List<SelfReport> subList = this.listSubReportByParentId(parentId);
		for (SelfReport subReport : subList) {
			List<SelfReport> threeList = this.listSubReportByParentId(subReport
					.getReportId());
			subReport.setSubReport(threeList);
		}
		return subList;
	}

	/**
	 * 获取某目录下一级报表
	 * 
	 * @param userId
	 * @return
	 */
	public List<SelfReport> listSubReportByParentId(Integer parentId) {
		/* add by yangwg 报表系统启动不了 */
		return this.myBatisDao
				.getList(
						"com.infosmart.portal.pojo.SelfApplyMapper.listSubReportByParentId",
						parentId);
		/* 报表系统启动不了,改了----yangwg */
		// return
		// myBatisDao.getList("com.infosmart.mapper.SelfReportMapper.listSubReportByParentId",parentId);
	}

	/**
	 * 查询报表关联控件
	 * 
	 * @return
	 */
	public List<ReportConfig> queryConfigById(String reportId) {
		List<ReportConfig> configList = null;
		/*
		 * configList = myBatisDao.getList(
		 * "com.infosmart.mapper.SelfReportMapper.queryConfigById", reportId);
		 */
		configList = myBatisDao
				.getList(
						"com.infosmart.portal.pojo.SelfApplyMapper.listQueryColumnConfigById",
						reportId);
		return configList;
	}

	/**
	 * 通过Id查询维度组
	 * 
	 * @param primaryKeyId
	 * @return
	 */
	public List<DimensionDetail> listDimensionDetail(Integer dimensionId) {
		return myBatisDao
				.getList(
						"com.infosmart.portal.pojo.SelfApplyMapper.listDimensionDetail",
						dimensionId);
	}

	@Override
	public ReportDesign getSelfReportById(String reportId) {
		return this.myBatisDao.get(
				"com.infosmart.mapper.ReportMapper.queryReportById", reportId);
	}

	@Override
	public List<SelfReportColumn> listSelfReportColumnConfigById(String reportId) {
		return this.myBatisDao.getList(
				"com.infosmart.mapper.SelfReportMapper.listReportColumnConfig",
				reportId);
	}

	@Override
	public List<SelfReportColumn> listSelfReportColumnConfigBySQL(
			String querySql) {
		if (!StringUtils.notNullAndSpace(querySql)) {
			return null;
		}
		List<SelfReportColumn> columnConfigList = new ArrayList<SelfReportColumn>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = this.myBatisDao.getSqlSession().getConnection();
			SelfReportColumn columnConfig = null;
			// 查询
			try {
				pstmt = conn.prepareStatement(querySql + " limit 1");
				rs = pstmt.executeQuery();
			} catch (Exception e) {
				this.logger.warn("测试报表SQL失败：默认SQL已分页");
				pstmt = conn.prepareStatement(querySql);
				rs = pstmt.executeQuery();
			}
			ResultSetMetaData rsmd = rs.getMetaData();
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				if (rsmd.getColumnTypeName(i).toLowerCase().indexOf("blob") != -1)
					continue;
				if (rsmd.getColumnTypeName(i).toLowerCase().indexOf("clob") != -1)
					continue;
				if (rsmd.getColumnTypeName(i).toLowerCase().indexOf("binary") != -1)
					continue;
				if (rsmd.getColumnTypeName(i).toLowerCase().indexOf("bit") != -1)
					continue;
				// 列名
				columnConfig = new SelfReportColumn();
				//
				columnConfig.setColumnSort(i);
				columnConfig.setColumnClassName(rsmd.getColumnClassName(i));
				columnConfig.setColumnCode(rsmd.getTableName(i) + "."
						+ rsmd.getColumnName(i).toLowerCase());
				columnConfig.setColumnLabel(rsmd.getColumnLabel(i));
				columnConfig.setColumnType(rsmd.getColumnType(i));
				columnConfig.setColumnTypeName(rsmd.getColumnTypeName(i));
				columnConfig.setIsQueryColumn(rsmd.isSearchable(i) ? 1 : 0);
				//
				columnConfigList.add(columnConfig);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			this.logger.error("自定义SQL报表测试失败:" + e.getMessage(), e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					rs.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return columnConfigList;
	}

	@Override
	public String saveSelfReport(SelfReport selfReport) {
		if (selfReport == null || selfReport.getColumnList() == null
				|| selfReport.getColumnList().isEmpty()) {
			this.logger.warn("保存报表配置信息失败，错误的参数");
			return null;
		}
		try {
			if (StringUtils
					.notNullAndSpace(selfReport.getReportId().toString())) {
				this.logger.info("修改报表配置信息:" + selfReport.getReportName());
				// 删除报表列
				this.myBatisDao
						.delete("com.infosmart.mapper.SelfReportMapper.deletReportColumnConfig",
								selfReport.getReportId());
				// 修改报表信息
				this.myBatisDao
						.update("com.infosmart.mapper.SelfReportMapper.updateReportConfig",
								selfReport);
				List<SelfReportColumn> columnConfigList = selfReport
						.getColumnList();
				for (SelfReportColumn columnConfig : columnConfigList) {
					if (columnConfig == null)
						continue;
					columnConfig.setReportId(selfReport.getReportId());
					this.myBatisDao
							.save("com.infosmart.mapper.SelfReportMapper.insertReportColumnConfig",
									columnConfig);
				}
			} else {
				this.logger.info("保存报表配置信息:" + selfReport.getReportName());
				// String reportId = UUID.randomUUID().toString();//
				// selfReport.setReportId(reportId);
				this.myBatisDao
						.save("com.infosmart.mapper.SelfReportMapper.insertReportConfig",
								selfReport);
				List<SelfReportColumn> columnConfigList = selfReport
						.getColumnList();
				for (SelfReportColumn columnConfig : columnConfigList) {
					if (columnConfig == null)
						continue;
					columnConfig.setReportId(selfReport.getReportId());
					this.myBatisDao
							.save("com.infosmart.mapper.SelfReportMapper.insertReportColumnConfig",
									columnConfig);
				}
			}
			return selfReport.getReportId().toString();
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public List<List<String>> queryReportDataByConfig(ReportDesign reportConfig) {
		if (reportConfig == null || reportConfig.getColumnConfigList() == null
				|| "".equals(reportConfig.getReportSql())
				|| reportConfig.getReportSql() == null) {
			this.logger.info("报表配置未找到或报表列未定义");
			return null;
		}
		// 查询语句
		String querySql = reportConfig.getReportSql();
		// 分页大小
		int limitCount = reportConfig.getPageSize();
		List<ReportConfig> columnConfigList = reportConfig
				.getColumnConfigList();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String> columnDataList = null;
		List<List<String>> reportDataList = new ArrayList<List<String>>();
		String columnCode = null;
		try {
			conn = this.myBatisDao.getSqlSession().getConnection();
			// 查询
			if (limitCount > 0) {
				pstmt = conn
						.prepareStatement(querySql + " limit " + limitCount);
			} else {
				pstmt = conn.prepareStatement(querySql);
			}
			rs = pstmt.executeQuery();
			while (rs.next()) {
				columnDataList = new ArrayList<String>();
				for (ReportConfig column : columnConfigList) {
					if (column.getIsDataColumn() == 1) {
						columnCode = column.getColumnCode();
						columnCode = columnCode.substring(columnCode
								.indexOf(".") + 1);
						Object columnData = rs.getObject(columnCode);
						columnDataList.add(String.valueOf(columnData));
					}
				}
				reportDataList.add(columnDataList);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			this.logger.error("查询自定义SQL报表失败:" + e.getMessage(), e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					rs.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return reportDataList;
	}

	@Override
	public JSONObject queryReportJsonDataByPaging(ReportDesign reportConfig,
			int pageNo) {
		if (reportConfig == null
				|| reportConfig.getColumnConfigList().isEmpty()) {
			this.logger.warn("报表未定义或查询列未定义");
			return null;
		}
		if (reportConfig == null || null == reportConfig.getReportSql()) {
			this.logger.warn("报表未定义SQL语句");
			return null;
		}
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ReportPageInfo pageInfo = new ReportPageInfo();
		String countSql = "select count(0) from ("
				+ reportConfig.getReportSql() + ") tmp_count"; // 记录统计
		if (StringUtils.notNullAndSpace(reportConfig.getQueryWhere())) {
			if (reportConfig.getQueryWhere().trim().toUpperCase()
					.startsWith("AND")) {
				countSql += " WHERE 1=1  " + reportConfig.getQueryWhere();
			} else {
				countSql += " WHERE 1=1 AND " + reportConfig.getQueryWhere();
			}
		}
		JSONObject reportData = new JSONObject();
		try {
			conn = this.myBatisDao.getSqlSession().getConnection();
			// Unknown column 'com_kpi_code' in 'where clause'
			pstmt = conn.prepareStatement(countSql);
			// 设置总的记录数
			rs = pstmt.executeQuery();
			if (rs.next()) {
				pageInfo.setTotalRowNum(rs.getInt(1));
			}
			// 每页大小
			pageInfo.setPageSize(reportConfig.getPageSize());
			int pageCount = (pageInfo.getTotalRowNum() / pageInfo.getPageSize());
			pageCount += (pageInfo.getTotalRowNum() == pageCount
					* pageInfo.getPageSize() ? 0 : 1);
			// 总页数
			pageInfo.setTotalPageNum(pageCount <= 0 ? 0 : pageCount);
			// 当前页码
			pageInfo.setPageNum(pageNo);
			// 开始序号
			pageInfo.setStartRowNum((pageNo - 1) * reportConfig.getPageSize());
			// 结束
			pageInfo.setEndRowNum(pageNo * reportConfig.getPageSize());
			// 分页查询记录
			List<ReportConfig> columnConfigList = reportConfig
					.getColumnConfigList();
			String columnCode = null;
			List<String> columnDataList = null;
			List<List<String>> reportDataList = new ArrayList<List<String>>();
			//
			// 查询列
			StringBuffer queryColumn = new StringBuffer();
			List<ReportConfig> queryColumnList = reportConfig
					.getColumnConfigList();
			for (ReportConfig column : queryColumnList) {
				if (column == null
						|| !StringUtils.notNullAndSpace(column.getColumnCode()))
					continue;
				if (queryColumn.toString().length() > 0) {
					queryColumn.append(",");
				}
				if (column.getColumnCode().indexOf(".") != -1) {
					queryColumn.append(column.getColumnCode().substring(
							column.getColumnCode().indexOf(".") + 1));
				} else {
					queryColumn.append(column.getColumnCode());
				}
			}
			this.logger.info("queryColumn==" + queryColumn);
			if (queryColumn == null || queryColumn.length() == 0) {
				queryColumn = new StringBuffer("*");
			}
			String newQuerySql = "SELECT " + queryColumn + " FROM ("
					+ reportConfig.getReportSql() + ") a ";
			if (StringUtils.notNullAndSpace(reportConfig.getQueryWhere())) {
				if (reportConfig.getQueryWhere().trim().toUpperCase()
						.startsWith("AND")) {
					newQuerySql += " WHERE 1=1  "
							+ reportConfig.getQueryWhere();
				} else {
					newQuerySql += " WHERE 1=1 AND "
							+ reportConfig.getQueryWhere();
				}
			}
			// 排序字段
			if (StringUtils.notNullAndSpace(reportConfig.getOrderFieldName())) {
				newQuerySql += " ORDER BY " + reportConfig.getOrderFieldName()
						+ " " + reportConfig.getSortOrder();
			}
			this.logger.info("报表查询分页SQL:" + newQuerySql);
			if (reportConfig.getPageSize() > 0) {
				newQuerySql += " limit " + (pageNo - 1)
						* reportConfig.getPageSize() + ","
						+ reportConfig.getPageSize();
			}
			this.logger.info("报表查询SQL:" + newQuerySql);
			pstmt = conn.prepareStatement(newQuerySql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				columnDataList = new ArrayList<String>();
				for (ReportConfig column : columnConfigList) {
					if (column == null
							|| !StringUtils.notNullAndSpace(column
									.getColumnCode()))
						continue;
					if (column.getIsDataColumn() == 1) {
						columnCode = column.getColumnCode();
						columnCode = columnCode.substring(columnCode
								.indexOf(".") + 1);
						Object columnData = rs.getObject(columnCode);
						columnDataList.add(String.valueOf(columnData));
					}
				}
				reportDataList.add(columnDataList);
			}
			reportData.put("data", reportDataList);
			reportData.put("pageInfo", pageInfo);
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error("查询自定义SQL报表[" + reportConfig.getReportName()
					+ "]失败:" + e.getMessage(), e);
			reportData.put(
					"exception",
					"查询自定义报表‘" + reportConfig.getReportName() + "’失败:"
							+ e.getMessage());
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return reportData;
	}

	/**
	 * 查询报表导出数据
	 */
	@Override
	public List<List<String>> queryReport(ReportDesign reportConfig, int pageNo) {
		if (reportConfig == null
				|| reportConfig.getColumnConfigList().isEmpty()) {
			this.logger.warn("报表未定义或查询列未定义");
			return null;
		}
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<List<String>> reportDataList = new ArrayList<List<String>>();
		try {
			conn = this.myBatisDao.getSqlSession().getConnection();
			String columnCode = null;
			List<String> columnDataList = null;
			//
			// 查询列
			StringBuffer queryColumn = new StringBuffer();
			List<ReportConfig> queryColumnList = reportConfig
					.getColumnConfigList();
			for (ReportConfig column : queryColumnList) {
				if (column == null
						|| !StringUtils.notNullAndSpace(column.getColumnCode()))
					continue;
				if (queryColumn.toString().length() > 0) {
					queryColumn.append(",");
				}
				if (column.getColumnCode().indexOf(".") != -1) {
					queryColumn.append(column.getColumnCode().substring(
							column.getColumnCode().indexOf(".") + 1));
				} else {
					queryColumn.append(column.getColumnCode());
				}
			}
			String newQuerySql = "SELECT " + queryColumn + " FROM ("
					+ reportConfig.getReportSql() + ") a ";
			if (StringUtils.notNullAndSpace(reportConfig.getQueryWhere())) {
				if (reportConfig.getQueryWhere().trim().toUpperCase()
						.startsWith("AND")) {
					newQuerySql += " WHERE 1=1  "
							+ reportConfig.getQueryWhere();
				} else {
					newQuerySql += " WHERE 1=1 AND "
							+ reportConfig.getQueryWhere();
				}
			}
			// 排序字段
			if (StringUtils.notNullAndSpace(reportConfig.getOrderFieldName())) {
				newQuerySql += " ORDER BY " + reportConfig.getOrderFieldName()
						+ " " + reportConfig.getSortOrder();
			}
			this.logger.info("报表查询分页SQL:" + newQuerySql);
			if (reportConfig.getPageSize() > 0) {
				newQuerySql += " limit " + (pageNo - 1)
						* reportConfig.getPageSize() + ","
						+ reportConfig.getPageSize();
			}
			this.logger.info("报表查询SQL:" + newQuerySql);
			pstmt = conn.prepareStatement(newQuerySql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				columnDataList = new ArrayList<String>();
				for (ReportConfig column : queryColumnList) {
					if (column == null
							|| !StringUtils.notNullAndSpace(column
									.getColumnCode()))
						continue;
					if (column.getIsDataColumn() == 1) {
						columnCode = column.getColumnCode();
						columnCode = columnCode.substring(columnCode
								.indexOf(".") + 1);
						Object columnData = rs.getObject(columnCode);
						columnDataList.add(String.valueOf(columnData));
					}
				}
				reportDataList.add(columnDataList);
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error("查询自定义SQL报表[" + reportConfig.getReportName()
					+ "]失败:" + e.getMessage(), e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return reportDataList;
	}

	@Override
	public List<DimensionDetailSec> getDimensionDetailSecList(String parentId) {
		return myBatisDao
				.getList(
						"com.infosmart.portal.pojo.SelfApplyMapper.getDimensionDetailSecList",
						parentId);
	}

}
