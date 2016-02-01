package com.infosmart.portal.service;

import java.util.List;
import java.util.Map;

import com.infosmart.portal.pojo.DwpasCColumnInfo;
import com.infosmart.portal.pojo.DwpasCKpiInfo;
import com.infosmart.portal.pojo.DwpasCModuleInfo;
import com.infosmart.portal.pojo.DwpasRColumnComKpi;

/**
 * 栏目管理
 * 
 * @author infosmart
 * 
 */
public interface DwpasCColumnInfoService {
	/**
	 * 得到模块信息
	 * 
	 * @param moduleCode
	 * @return
	 */
	DwpasCModuleInfo getDwpasCModuleInfo(String templateId, String moduleCode);

	/**
	 * 根据(多个)模块编码列出模块信息
	 * 
	 * @param templateId
	 * @param moduleCodeList
	 * @return
	 */
	Map<String, DwpasCModuleInfo> listDwpasCModuleInfo(String templateId,
			List<String> moduleCodeList);

	/**
	 * 根据模块ID列出关联的栏目及指标信息
	 * 
	 * @param moduleId
	 * @return
	 */
	List<DwpasCColumnInfo> listColumnAndRelKpiCodeByModuleId(String moduleId,
			String productId, int kpiType);
	/**
	 * 自定义图形时，根据模块Id和指标类型获得关联栏目和指标信息（产品Id后台配置进去）
	 * @param moduleId
	 * @param kpiType
	 * @return
	 */
	List<DwpasCColumnInfo> listColumnInfoListByModuleIdAndKpiType(String moduleId, int kpiType);

	/**
	 * 根据moudleid获得栏目信息及关联的大盘指标
	 * 
	 * @param moudleId
	 * @return
	 */
	List<DwpasCColumnInfo> listDwpasCColumnInfoSByMoudleId(String moudleId);

	/**
	 * 根据栏目编码只得到栏目信息
	 * 
	 * @param columnCode
	 *            栏目编码
	 * @param templateId
	 *            模板ID
	 * @return
	 */
	DwpasCColumnInfo getColumnAndModelInfoByCode(String templateId,
			String columnCode);

	/**
	 * 根据栏目编码得到通用指标编码(暂时支持)
	 * 
	 * 请改用getCommonCodeByColumnCode(String sytemTmplateId, String columnCode);
	 * 
	 * @param sytemTmplateId
	 *            系统模块ID
	 * @param columnCode
	 *            栏目编码
	 * @return
	 * @deprecated
	 */
	List<DwpasRColumnComKpi> getCommonCodeByColumnCode(String columnCode);

	/**
	 * 批量查询某模块下的多个栏目关联的通用指标<columnCode,关联的通用指标列表>
	 * 
	 * @param sytemTmplateId
	 * @param columnCodeList
	 * @return
	 */
	Map<String, List<DwpasRColumnComKpi>> listCommonCodeByColumnCodes(
			String sytemTmplateId, List<String> columnCodeList);

	/**
	 * 根据栏目编码得到通用指标编码
	 * 
	 * @param sytemTmplateId
	 *            系统模块ID
	 * @param columnCode
	 *            栏目编码
	 * @return
	 */
	List<DwpasRColumnComKpi> getCommonCodeByColumnCode(String sytemTmplateId,
			String columnCode);

	/**
	 * 根据产品ID，栏目CODE，取关联的指标信息(产品发展栏目专用)
	 * 
	 * @param sytemTmplateId
	 * @param columnCodeList
	 * @param productId
	 * @param kpiType
	 * @return 〈栏目CODE,关联的指标〉
	 */
	Map<String, List<DwpasCKpiInfo>> listColumnInfoByPrdAndColumnCodeForPrdDevelopColumn(
			String sytemTmplateId, List<String> columnCodeList,
			String productId, int kpiType);

	/**
	 * 根据产品ID，栏目CODE，取关联的指标信息
	 * 
	 * @param sytemTmplateId
	 * @param columnCodeList
	 * @param productId
	 * @param kpiType
	 * @return 〈栏目CODE,关联的指标〉
	 */
	Map<String, List<DwpasCKpiInfo>> listColumnInfoByPrdAndColumnCode(
			String sytemTmplateId, List<String> columnCodeList,
			String productId, int kpiType);

	/**
	 * 根据产品ID及栏目编码得到栏目信息及关联的指标编码
	 * 
	 * @param columnCode
	 *            栏目编码
	 * @param productId
	 *            产品ID
	 * @return
	 */
	DwpasCColumnInfo getColumnAndKpiInfoByColumnCodeAndPrdId(String templateId,
			List<String> codes, String productId, int kpiType);

	/**
	 * 根据用户所属模板Id和userType查询通用指标集合
	 * 
	 * @param map
	 * @return
	 */
	List<DwpasRColumnComKpi> getByTemplateIdAndUserType(Map<Object, Object> map);
	
	/**
	 * 根据模块Id和日期类型获取所有栏目信息
	 * @param moduleId
	 * @param kpiType
	 * @return
	 */
	List<DwpasCColumnInfo> listAllCoumnInfosByModelIdAndKpiType(String moduleId, Integer kpiType);
	
	/**
	 * 自定义图形时，根据模块Id和指标类型kpiType，以及页面上获得的产品Id 获得关联栏目指标信息
	 * @param moduleId
	 * @param kpiType
	 * @param productId
	 * @return
	 */
	List<DwpasCColumnInfo> listColumnInfoListByModuleIdAndKpiTypeAndProductId(String moduleId, int kpiType, String productId);
	
	/**
	 * 根据moduleId获取RColumnComKpi信息
	 * @param moduleId
	 * @return
	 */
	List<DwpasRColumnComKpi> listRColumnComKpiInfoByModuleId(String moduleId);


}
