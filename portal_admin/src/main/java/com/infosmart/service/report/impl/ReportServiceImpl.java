package com.infosmart.service.report.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosmart.orm.mybatis.MyBatisDao;
import com.infosmart.po.DimensionDetail;
import com.infosmart.po.report.ReportCell;
import com.infosmart.po.report.ReportConfig;
import com.infosmart.po.report.ReportDataSource;
import com.infosmart.po.report.ReportDesign;
import com.infosmart.service.impl.BaseServiceImpl;
import com.infosmart.service.report.NewReportService;
import com.infosmart.service.report.ReportService;
import com.infosmart.service.report.SelfApplyService;
import com.infosmart.util.StringDes;
import com.infosmart.util.StringUtils;
import com.infosmart.util.report.PoolManager;

/**
 * 报表结构实现
 * 
 * @author hgt
 * 
 */
@Service
public class ReportServiceImpl extends BaseServiceImpl implements ReportService {
	protected final Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private MyBatisDao myBatisDao;
	@Autowired
	private SelfApplyService selfApplyService;
	@Autowired
	private NewReportService newReportService;
	/**
	 * 通过Id查询报表
	 *  infosmart 20120604
	 */
	public ReportDesign queryReportById(String reportId) {
		ReportDesign report = null;
		report = myBatisDao.get("com.infosmart.mapper.ReportMapper.queryReportById", reportId);
		return report;
	}

	/**
	 * 查询报表关联控件
	 * 
	 * @return
	 */
	public List<ReportConfig> listPageConfigById(String reportId) {
		List<ReportConfig> configList = null;
		configList = myBatisDao.getList(
				"com.infosmart.mapper.ReportMapper.queryConfigById", reportId);
		return configList;
	}

	/**
	 * 通过Id查询维度组
	 * 
	 * @param primaryKeyId
	 * @return
	 */
	public List<DimensionDetail> listDimensionDetail(Integer dimensionId) {
		return myBatisDao.getList(
				"com.infosmart.mapper.ReportMapper.listDimensionDetail",
				dimensionId);
	}

	/**
	 * date 2012-06-04 infosmart
	 */
	public List<ReportDesign> listAllParentReport() {
		return myBatisDao.getList("com.infosmart.mapper.ReportMapper.listAllParentReport");
	}
	/**
	 * date 2012-06-04 infosmart
	 */
	public List<ReportDesign> listAllToAdd() {
		return myBatisDao.getList("com.infosmart.mapper.ReportMapper.listAllToAdd");
	}
	/**
	 * date 2012-06-04 infosmart
	 * @param report
	 */
	public void saveReport(ReportDesign report) {
		if (report == null) {
			this.logger.warn("saveReport方法执行失败：参数report为空");
			return;
		}
		if (report.getReportId() != null && report.getReportId().intValue() > 0) {
			myBatisDao.update("com.infosmart.mapper.ReportMapper.updateReport", report);
			/*自服务报表名称同步*/
			selfApplyService.updateSelfReportName(report);
		} else {
			myBatisDao.save("com.infosmart.mapper.ReportMapper.insertReport", report);
		}
	}

	/**
	 * 展开二级菜单
	 * date 2012-06-04 infosmart
	 * @param parentId
	 * @return
	 */
	public List<ReportDesign> listSubReportByParentId(Integer parentId) {
		return myBatisDao.getList("com.infosmart.mapper.ReportMapper.listSubReportByParentId",parentId );
	}
	/**
	 * 删除报表
	 * date 2012-06-04 infosmart
	 */
	public void deleteReportById(Integer reportId) {
		if (reportId == null) {
			this.logger.warn("deleteReportById方法执行失败：参数reportId为空");
			return;
		}
		myBatisDao.delete("com.infosmart.mapper.ReportMapper.deleteReportById",reportId );
	}

	public ReportDesign getReportById(Integer reportId) {
		return myBatisDao.get("com.infosmart.mapper.ReportMapper.getReportById",reportId );
	}

	/**
	 * 获取所有报表
	 * date 2012-06-04 infosmart
	 * @param userId
	 * @return
	 */
	public List<ReportDesign> listAllReport() {
		List<ReportDesign> rl = this.listAllParentReport();
		for (ReportDesign report : rl) {
			List<ReportDesign> subList = this.listSubReportByParentId(report
					.getReportId());
			report.setSubReport(subList);
			for (ReportDesign subReport : subList) {
				List<ReportDesign> threeList = this.listSubReportByParentId(subReport
						.getReportId());
				subReport.setSubReport(threeList);
			}
		}
		return rl;
	}
	/**
	 * date 2012-06-04 infosmart
	 */
	@Override
	public List<ReportDesign> listAllReports() {
		return this.myBatisDao.getList("com.infosmart.mapper.ReportMapper.listAllReport");
	}
	
	/**
	 * 查询报表导出数据
	 */
	@Override
	public List<List<String>> queryReport(ReportDesign reportDesign, int pageNo,List<ReportCell> reportCellList) {
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
		dataSource = this.newReportService.getDataSourceById(Integer.valueOf(reportDesign.getDataSourceId()));
		if(dataSource==null){
			this.logger.warn("查询报表数据失败，数据源为空--->"+dataSource);
			return reportDataList;
		}
		/*解密*/
		dataSource.setPassword(StringDes.StringToDec(dataSource.getPassword()));
		PoolManager pm = new PoolManager(dataSource);
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn= pm.getConnection();
			List<String> columnDataList = null;
			//
			// 查询列
			/*StringBuffer queryColumn = new StringBuffer();
			List<SelfReportColumnConfig> queryColumnList = null;//reportConfig.getColumnConfigList();
			for (SelfReportColumnConfig column : queryColumnList) {
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
			}*/
			StringBuffer queryFields = new StringBuffer("");
			if (reportCellList != null) {
				//querySQL = "SELECT * FROM (" + querySql + ") t";
				//StringBuffer queryFields = new StringBuffer("");
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
			//分页查询
			//newQuerySql=this.getNewQuerySql(reportInfo, dataSource, newQuerySql, pageNo);
			this.logger.info("报表查询SQL:" + newQuerySql);
			pstmt = conn.prepareStatement(newQuerySql);
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

}
