package com.infosmart.service;

import java.util.List;

import com.infosmart.po.Dimension;
import com.infosmart.po.DimensionDetail;
import com.infosmart.po.DimensionDetailSec;
import com.infosmart.po.report.ReportColumnConfig;

public interface DimensionalityService {

	/**
	 * 获得所有维度信息表列表
	 * 
	 * @return
	 */
	public List<Dimension> getDimensionList();

	/**
	 * 获得所有维度信息从表列表
	 * 
	 * @param map
	 * @return
	 */
	public List<DimensionDetail> getDimensionDetailList(Integer dimensionId);

	/**
	 * 获取二级维度
	 * 
	 * @param parentId
	 * @return
	 */
	public List<DimensionDetailSec> getDimensionDetailSecList(String parentId);

	/**
	 * 修改二级维度
	 * 
	 * @param dimensionDetailSec
	 */
	public void updateDimensionDetailSec(DimensionDetailSec dimensionDetailSec);

	/**
	 * 测试SQL语句，得到列定义
	 * 
	 * @param querySql
	 * @return
	 */
	List<ReportColumnConfig> listSelfReportColumnConfigBySQL(String querySql);

	/**
	 * 添加二级维度
	 * 
	 * @param dimensionDetailSec
	 */
	public void insertDimensionDetailSec(DimensionDetailSec dimensionDetailSec);

	/**
	 * 根据主键值获得二级维度对象
	 * 
	 * @param id
	 * @return
	 */
	public DimensionDetailSec getDimensionDetailSecById(int id);

	/**
	 * 添加维度信息主表列表
	 * 
	 * @param map
	 * @return
	 */
	public void insertDimension(Dimension dimension) throws Exception;

	/**
	 * 添加维度信息从表列表
	 * 
	 * @param map
	 * @return
	 */
	public void insertDimensionDetai(DimensionDetail dimensionDetail)
			throws Exception;

	/**
	 * 通过SQL语句批量添加维度信息从表列表
	 * 
	 * @param dimensionDetail
	 */
	void insertDimensionDetailBySQL(DimensionDetail dimensionDetail)
			throws Exception;
	/**
	 * 通过SQL语句批量添加二级维度数据
	 * @param dimensionDetail
	 * @throws Exception
	 */
	void insertDimensionDetailSecBySQL(DimensionDetailSec dimensionDetailSec)
	throws Exception;

	/**
	 * 修改维度信息从表列表
	 * 
	 * @param map
	 * @return
	 */
	public void updateDimension(Dimension dimension) throws Exception;

	/**
	 * 修改维度信息从表列表
	 * 
	 * @param map
	 * @return
	 */
	public void updateDimensionDetai(DimensionDetail dimensionDetail)
			throws Exception;

	/**
	 * 删除维度信息主表列表
	 * 
	 * @param map
	 * @return
	 */
	public void deleteDimension(Integer id) throws Exception;

	/**
	 * 删除维度信息从表列表
	 * 
	 * @param map
	 * @return
	 */
	public void deleteDimensionDetai(Integer dimensionId) throws Exception;

	/**
	 * 删除二级维度数据
	 * 
	 * @param primary_id
	 * @throws Exception
	 */
	public void deleteDimensionDetaiSec(String primary_id) throws Exception;

	/**
	 * 根据主键id获取某一维度从表信息
	 * 
	 * @param primaryKeyId
	 * @return
	 */
	public DimensionDetail getDimensionDetaiById(Integer primaryKeyId);

	/**
	 * 根据主键id获取所有维度信息
	 * 
	 * @param id
	 * @return
	 */
	public Dimension getDimensionById(Integer id);

	/**
	 * 根据code模糊查询维度主表信息
	 * 
	 * @param code
	 * @return
	 */
	public List<Dimension> getDimensionByCodeOrName(String keyCode);

	/**
	 * 根据dimensionId、key模糊查询维度从表信息
	 * 
	 * @param dimensionDetail
	 * @return
	 */
	public List<DimensionDetail> getDimensionDetaiByIdAndKeyCode(
			String dimensionId, String KeyCode);

}
