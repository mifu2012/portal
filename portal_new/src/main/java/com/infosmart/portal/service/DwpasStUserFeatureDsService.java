package com.infosmart.portal.service;

import java.util.List;
import java.util.Map;

import com.infosmart.portal.pojo.DwpasStUserFeatureDs;
import com.infosmart.portal.vo.dwpas.ProductUsingCharacter;

/**
 * 特征数据统计
 * 
 * @author infosmart
 * 
 */
public interface DwpasStUserFeatureDsService {
	/**
	 * 列出特征数据
	 * 
	 * @param productId
	 *            产品ID
	 * @param reqportDate
	 *            统计日期
	 * @param featureType
	 *            特征类型 1-使用次数，2-时间偏好，3-年龄段，4-地区，等
	 * @return
	 */
	List<DwpasStUserFeatureDs> listDwpasStUserFeatureDs(String productId,
			String reportDate, int featureType);
	
  /**
   * 查询用户特征
   * @param productId
   * @param featureType
   * @param reportDate
   * @return
   */
	public List<Map<String, Object>> qryAllByPrdIdAndFeatureType(String productId, String featureType, String reportDate);
  /**
   * 获取产品,大盘通用指标对应的指标集合
   * @param productId
   * @param kpiType
   * @return
   */
	public Map<String, String>  queryRealKpiCodeMap(String productId,int kpiType,String menuId);
	/**
	 * 获取地图数据
	 * @param map
	 * @return
	 */
	public String getMapData(List<String> proIds,String queryMonth,String featureType);
	/**
	 * 获取ammap中国地图数据
	 */
	public List<DwpasStUserFeatureDs> getAmmapDate(String productId,String queryMonth,String featureType);
}
