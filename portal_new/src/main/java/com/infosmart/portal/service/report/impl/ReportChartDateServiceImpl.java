package com.infosmart.portal.service.report.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.infosmart.portal.pojo.report.ReportChartFiled;
import com.infosmart.portal.pojo.report.ReportDataSource;
import com.infosmart.portal.pojo.report.ReportDesign;
import com.infosmart.portal.service.impl.BaseServiceImpl;
import com.infosmart.portal.service.report.ReportChartDateService;
import com.infosmart.portal.util.StringDes;
import com.infosmart.portal.util.StringUtils;
import com.infosmart.portal.util.report.NamedParameterStatement;
import com.infosmart.portal.util.report.PoolManager;

@Service
public class ReportChartDateServiceImpl extends BaseServiceImpl implements
		ReportChartDateService {
	@Override
	public List<Object> getReportChartDate(ReportDesign reportDesign,
			String fileds, final String reportSql,
			final Map<String, Object> paramValMap, String pageNo,
			ReportDataSource dataSource) {
		if (reportDesign == null) {
			this.logger.warn("查询图表数据出错：reportDesign为空");
			return null;
		}
		// 查询语句
		if (!StringUtils.notNullAndSpace(reportSql)) {
			this.logger.warn("获取图表数据出错：查询语句reportSql为空");
			return null;
		}
		/* 连接数据库 */
		if (dataSource == null) {
			this.logger.warn("报表数据源信息为空----->");
			return null;
		}
		// 当前页码
		if (!StringUtils.notNullAndSpace(pageNo)) {
			pageNo = "1";
		}
		List<Object> objList = new ArrayList<Object>();
		Map map = new HashMap();
		List<Object> integerList = null;
		List<String> stringList = new ArrayList<String>();
		Map<String, List<Object>> mapIntegerList = new HashMap<String, List<Object>>();
		String arry[] = fileds.split(",");
		/* 解密 */
		dataSource.setPassword(StringDes.StringToDec(dataSource.getPassword()));
		PoolManager pm = new PoolManager(dataSource);
		Connection conn = pm.getConnection();
		NamedParameterStatement pstmt = null;
		ResultSet rs = null;
		String querySQL = "select " + fileds + " from(" + reportSql + ") a";
		try {
			querySQL = this.getNewQuerySql(reportDesign, dataSource, querySQL,
					Integer.parseInt(pageNo));
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.warn("查询图表数据出错");
		}
		this.logger.info("查询图表数据语句：" + querySQL.toString());
		try {
			conn = pm.getConnection();
			pstmt = new NamedParameterStatement(conn, querySQL, paramValMap);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int index = 0;
				/* 处理结果集 */
				if (arry.length > 0) {
					for (int i = 0; i < arry.length; i++) {
						index++;
						if (rs.getObject(arry[i]) == null) {
							continue;
						}
						if (index < arry.length) {
							if (!mapIntegerList.containsKey(arry[i])) {
								integerList = new ArrayList<Object>();
								mapIntegerList.put(arry[i], integerList);
							}
							mapIntegerList.get(arry[i]).add(
									(rs.getObject(arry[i]) == null ? "0" : rs
											.getObject(arry[i])));
						} else {
							if (rs.getObject(arry[i]) instanceof Date) {
								SimpleDateFormat sf = new SimpleDateFormat(
										"yyyy-MM-dd");
								stringList
										.add(rs.getObject(arry[i]) == null ? "0"
												: sf.format(rs
														.getObject(arry[i])));
							} else {
								stringList
										.add(rs.getObject(arry[i]) == null ? "0"
												: rs.getObject(arry[i])
														.toString());
							}
						}
					}
				}
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage(), e);
			this.logger.error("获取图表数据出错：查询异常");
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
				this.logger.warn("查询图表数据报错：关闭连接异常");
				return null;
			}
		}
		objList.add(mapIntegerList);
		objList.add(stringList);
		return objList;
	}

	@Override
	public List<Map<Object, Object>> getPieChartDates(final String querySql,
			final Map<String, Object> paramValMap, ReportChartFiled chartField,
			ReportDataSource dataSource) {
		this.logger.info("查询饼图数据......");
		List<Map<Object, Object>> objList = new ArrayList<Map<Object, Object>>();
		Map<Object, Object> objMap = null;
		// 查询语句
		if (!StringUtils.notNullAndSpace(querySql)) {
			this.logger.warn("查询报表数据失败：查询SQL语句为空");
			return null;
		}
		if (dataSource == null) {
			this.logger.warn("报表数据源信息为空----->");
			return null;
		}
		if (chartField == null) {
			this.logger.warn("报表字段信息为空----->");
			return null;
		}
		/* 解密 */
		dataSource.setPassword(StringDes.StringToDec(dataSource.getPassword()));
		PoolManager pm = new PoolManager(dataSource);
		Connection conn = null;
		NamedParameterStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer querySQL = new StringBuffer("select ");
		if (chartField.getSummaryType() == 1) {
			querySQL.append(" sum(" + chartField.getFields() + ") as sum ");
		} else {
			querySQL.append(" count(" + chartField.getFields() + ") as count ");
		}
		querySQL.append("," + chartField.getxFields());
		querySQL.append(" FROM (" + querySql + ") t");
		querySQL.append(" group by " + chartField.getxFields());
		this.logger.info("查询语句：" + querySQL.toString());
		try {
			conn = pm.getConnection();
			pstmt = new NamedParameterStatement(conn, querySQL.toString(),
					paramValMap);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				this.logger.warn("饼图数据:" + rs.getObject(2) + ","
						+ rs.getObject(1));
				objMap = new HashMap<Object, Object>();
				if (rs.getObject(2) instanceof Date) {
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
					objMap.put(sf.format(rs.getObject(2)), rs.getObject(1));
				} else {
					objMap.put(rs.getObject(2) == null ? "" : rs.getObject(2),
							rs.getObject(1));
				}
				objList.add(objMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
			this.logger.error("获取饼图数据出错：查询异常");
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
				this.logger.warn("查询饼图数据出错：关闭连接异常");
				return null;
			}
		}
		return objList;
	}
}
