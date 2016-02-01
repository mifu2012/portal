package com.infosmart.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosmart.po.Dimension;
import com.infosmart.po.DimensionDetail;
import com.infosmart.po.DimensionDetailSec;
import com.infosmart.po.report.ReportColumnConfig;
import com.infosmart.service.DimensionalityService;
import com.infosmart.util.StringUtils;

@Service
public class DimensionalityServiceImpl extends BaseServiceImpl implements
		DimensionalityService {

	@Override
	public void insertDimensionDetailBySQL(DimensionDetail dimensionDetail)
			throws Exception {
		if (dimensionDetail == null
				|| !StringUtils.notNullAndSpace(dimensionDetail.getQuerySql())) {
			throw new Exception("保存维度从表失败：参数为空");
		}
		this.logger.info("通过执行SQL批量保存维度从表信息");
		Connection conn = this.myBatisDao.getSqlSession().getConnection();
		PreparedStatement pstmt = conn.prepareStatement(dimensionDetail
				.getQuerySql());
		ResultSet rs = pstmt.executeQuery();
		DimensionDetail detail = null;
		List<DimensionDetail> dimensionDetailList=new ArrayList<DimensionDetail>();
		while (rs.next()) {
			detail = new DimensionDetail();
			detail.setDimensionId(dimensionDetail.getDimensionId());
			detail.setKey(rs.getString(dimensionDetail.getKey()));
			detail.setValue(rs.getString(dimensionDetail.getValue()));
			dimensionDetailList.add(detail);
		}
		try {
			myBatisDao.save(
					"com.infosmart.mapper.Dimension.insertDimensionDetailBySQL",
					dimensionDetailList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("dimensionId=" + dimensionDetail.getDimensionId()
					+ "的维度从表记录插入失败", e);
			throw e;
		}
	}

	@Override
	public List<Dimension> getDimensionList() {

		return myBatisDao
				.getList("com.infosmart.mapper.Dimension.getALLDimension");
	}

	@Override
	public List<DimensionDetail> getDimensionDetailList(Integer dimensionId) {

		return myBatisDao.getList(
				"com.infosmart.mapper.Dimension.getSomeOneDimensionDetail",
				dimensionId);
	}

	@Override
	public void insertDimensionDetai(DimensionDetail dimensionDetail)
			throws Exception {
		if (dimensionDetail == null) {
			this.logger.warn("插入维度信息从表失败：参数dimensionDetail为空");
			return;
		}
		try {
			myBatisDao.save(
					"com.infosmart.mapper.Dimension.insertDimensionDetail",
					dimensionDetail);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("dimensionId=" + dimensionDetail.getDimensionId()
					+ "的维度从表记录插入失败", e);
			throw e;
		}

	}

	@Override
	public void updateDimensionDetai(DimensionDetail dimensionDetail)
			throws Exception {
		if (dimensionDetail == null) {
			this.logger.warn("修改维度表从表失败：参数dimensionDetail为空");
			return;
		}
		try {
			myBatisDao.update(
					"com.infosmart.mapper.Dimension.updateDimensionDetailById",
					dimensionDetail);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("dimensionId=" + dimensionDetail.getDimensionId()
					+ "的维度从表记录更新失败", e);
			throw e;
		}

	}

	@Override
	public void deleteDimensionDetai(Integer primaryKeyId) throws Exception {
		if (primaryKeyId == null) {
			this.logger.warn("删除维度从表失败:参数primaryKeyId为空");
			return;
		}
		try {
			myBatisDao
					.delete("com.infosmart.mapper.ReportDesignSecMapper.deleteDimensionDetailSecById",
							primaryKeyId);
			myBatisDao.delete(
					"com.infosmart.mapper.Dimension.deleteDimensionDetailById",
					primaryKeyId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("primaryKeyId=" + primaryKeyId + "的维度从表记录删除失败", e);
			throw e;
		}
	}

	@Override
	public DimensionDetail getDimensionDetaiById(Integer primaryKeyId) {

		return myBatisDao.get(
				"com.infosmart.mapper.Dimension.getDimensionDetailById",
				primaryKeyId);
	}

	@Override
	public Dimension getDimensionById(Integer id) {

		return myBatisDao.get(
				"com.infosmart.mapper.Dimension.getALLDimensionById", id);
	}

	@Override
	public void insertDimension(Dimension dimension) throws Exception {
		if (dimension == null) {
			this.logger.warn("添加维度信息主表列表失败:参数dimension为空");
			return;
		}
		try {
			myBatisDao.save("com.infosmart.mapper.Dimension.insertDimension",
					dimension);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Id=" + dimension.getId() + "的维度主表记录插入失败", e);
			throw e;
		}

	}

	@Override
	public void updateDimension(Dimension dimension) throws Exception {
		if (dimension == null) {
			this.logger.warn("修改维度信息从表列表失败：参数dimension为空");
			return;
		}
		try {
			myBatisDao.update(
					"com.infosmart.mapper.Dimension.updateDimensionById",
					dimension);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Id=" + dimension.getId() + "的维度主表记录更新失败", e);
			throw e;
		}

	}

	@Override
	public void deleteDimension(Integer id) throws Exception {
		if (id == null) {
			this.logger.warn("删除维度信息主表列表失败：参数id为空");
			return;
		}
		try {
			myBatisDao
					.delete("com.infosmart.mapper.ReportDesignSecMapper.deleteDimensionDetailSec",
							id);
			myBatisDao
					.delete("com.infosmart.mapper.Dimension.deleteDimensionDetailByDimensionId",
							id);
			myBatisDao.delete(
					"com.infosmart.mapper.Dimension.deleteDimensionById", id);

		} catch (Exception e) {
			logger.error("id=" + id + "的维度主表记录删除失败", e);
			throw e;
		}

	}

	@Override
	public List<Dimension> getDimensionByCodeOrName(String KeyCode) {
		List<Dimension> dimensionList = null;
		Map<String, String> map = new HashMap<String, String>();
		map.put("keyCode", KeyCode);
		dimensionList = this.myBatisDao.getList(
				"com.infosmart.mapper.Dimension.getDimensionByCodeOrName", map);
		return dimensionList;
	}

	@Override
	public List<DimensionDetail> getDimensionDetaiByIdAndKeyCode(
			String dimensionId, String KeyCode) {
		if (!StringUtils.notNullAndSpace(dimensionId)) {
			this.logger.warn("查询维度详细信息失败：维度详细信息表Id为空");
			return null;
		}
		List<DimensionDetail> dimensionDetailList = null;
		Map<String, String> map = new HashMap<String, String>();
		map.put("dimensionId", dimensionId);
		map.put("keyCode", KeyCode);
		dimensionDetailList = this.myBatisDao
				.getList(
						"com.infosmart.mapper.Dimension.getDimensionDetaiByIdAndKeyCode",
						map);
		return dimensionDetailList;
	}

	@Override
	public List<DimensionDetailSec> getDimensionDetailSecList(String parentId) {
		List<DimensionDetailSec> dimensionDetailSecList = new ArrayList<DimensionDetailSec>();
		try {
			dimensionDetailSecList = this.myBatisDao

					.getList(
							"com.infosmart.mapper.ReportDesignSecMapper.getDimensionDetailSecList",
							parentId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dimensionDetailSecList;
	}

	@Override
	public DimensionDetailSec getDimensionDetailSecById(int id) {
		return this.myBatisDao
				.get("com.infosmart.mapper.ReportDesignSecMapper.getDimensionDetailSecById",
						id);
	}

	@Override
	public void updateDimensionDetailSec(DimensionDetailSec dimensionDetailSec) {
		this.myBatisDao
				.update("com.infosmart.mapper.ReportDesignSecMapper.updateDimensionDetailSec",
						dimensionDetailSec);

	}

	@Override
	public void insertDimensionDetailSec(DimensionDetailSec dimensionDetailSec) {
		this.myBatisDao
				.save("com.infosmart.mapper.ReportDesignSecMapper.insertDimensionDetailSec",
						dimensionDetailSec);

	}

	@Override
	public List<ReportColumnConfig> listSelfReportColumnConfigBySQL(
			String querySql) {
		if (!StringUtils.notNullAndSpace(querySql)) {
			this.logger.warn("执行批量sql语句出错：querySql为空");
			return null;
		}
		List<ReportColumnConfig> columnConfigList = new ArrayList<ReportColumnConfig>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = this.myBatisDao.getSqlSession().getConnection();
			ReportColumnConfig columnConfig = null;
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
				columnConfig = new ReportColumnConfig();
				//
				columnConfig.setColumnSort(i);
				columnConfig.setColumnClassName(rsmd.getColumnClassName(i));
				columnConfig.setColumnCode(rsmd.getTableName(i) + "."
						+ rsmd.getColumnName(i).toLowerCase());
				columnConfig.setColumnLabel(rsmd.getColumnLabel(i));
				columnConfig.setColumnType(rsmd.getColumnType(i));
				columnConfig.setColumnTypeName(rsmd.getColumnTypeName(i));
				columnConfig.setIsDataColumn(rsmd.isSearchable(i) ? 1 : 0);
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
	public void deleteDimensionDetaiSec(String primary_id) throws Exception {
		this.myBatisDao
				.delete("com.infosmart.mapper.ReportDesignSecMapper.deleteDimensionDetaiSec",
						primary_id);
	}

	@Override
	public void insertDimensionDetailSecBySQL(
			DimensionDetailSec dimensionDetailSec) throws Exception {
		if (dimensionDetailSec == null
				|| !StringUtils.notNullAndSpace(dimensionDetailSec
						.getQuerySql())) {
			throw new Exception("保存维度从表失败：参数为空");
		}
		this.logger.info("通过执行SQL批量保存维度从表信息");
		Connection conn = this.myBatisDao.getSqlSession().getConnection();
		PreparedStatement pstmt = conn.prepareStatement(dimensionDetailSec
				.getQuerySql());
		ResultSet rs = pstmt.executeQuery();
		DimensionDetailSec detail = null;
		List<DimensionDetailSec> dimensionDetailSecList = new ArrayList<DimensionDetailSec>();
		while (rs.next()) {
			detail = new DimensionDetailSec();
			detail.setParent_id(dimensionDetailSec.getParent_id());
			detail.setKey(rs.getString(dimensionDetailSec.getKey()));
			detail.setValue(rs.getString(dimensionDetailSec.getValue()));
			dimensionDetailSecList.add(detail);
		}
		this.myBatisDao
				.delete("com.infosmart.mapper.ReportDesignSecMapper.insertDimensionDetailSecBySQL",
						dimensionDetailSecList);
	}

}
