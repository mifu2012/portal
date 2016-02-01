package com.infosmart.service;

import java.util.List;

import com.infosmart.po.DwpasStKpiData;
import com.infosmart.po.KpiInfo;
import com.infosmart.po.MisTypeInfo;

/**
 * 指标信息管理
 */
public interface KpiInfoService {
	/**
	 * 获得分页指标信息列表
	 * 
	 * @param param
	 * @return
	 */
	public List<KpiInfo> queryKpiInfos(KpiInfo kpiinfo);

	/**
	 * 获得指标信息列表
	 * 
	 * @return
	 */
	public List<KpiInfo> queryKpiInfos1();

	/**
	 * 根据产品Id获得指标信息列表
	 * 
	 * @param productId
	 * @return
	 */
	public List<KpiInfo> queryKpiInfoByProductId(String productId);

	/**
	 * 增加指标信息
	 * 
	 * @param dwpasCKpiInfoDO
	 * @return
	 */
	public boolean insertKpiInfo(KpiInfo dwpasCKpiInfoDO);

	/**
	 * 通过指标code查询指标信息
	 * 
	 * @param kpiCode
	 * @return
	 */
	public KpiInfo queryKpiInfoByCode(String kpiCode);

	/**
	 * 指标信息修改保存
	 * 
	 * @param dwpasCKpiInfoDO
	 * @return
	 */
	public boolean saveKpiInfo(KpiInfo dwpasCKpiInfoDO) throws Exception;

	/**
	 * 通过指标code删除指标信息
	 * 
	 * @param kpiCode
	 * @return
	 */
	public boolean deleteKpiInfoByCode(String kpiCode);

	/**
	 * 校验表达式是否符合指标计算规则
	 * 
	 * @param roleFormula
	 * @return
	 */
	public String checkRule(String roleFormula);

	/**
	 * 获得指标大类列表
	 * 
	 * @return
	 */
	public List<MisTypeInfo> qryCSysType();

	/**
	 * 指标信息更新
	 * 
	 * @param dwpasCKpiInfoDO
	 */
	public boolean updateKpiInfo(KpiInfo dwpasCKpiInfoDO);

	/**
	 * 指标信息批量删除
	 * 
	 * @param ids
	 */
	public void deleteKpiInfos(List<String> ids) throws Exception ;

	/**
	 * 获取基础指标列表
	 * 
	 * @return
	 */
	public List<KpiInfo> queryBaseKpiInfos();

	/**
	 * 列出所有的大盘指标
	 * @param kpiType
	 * @return
	 */
	List<KpiInfo> listAllOverallKpiInfo(int kpiType);
	
	/**
	 * 查询计算指标中是否存在kpiCode
	 * @param kpiCode
	 * @return
	 */
	String selKpiInfoLikeRoleFormula(String kpiCode);

	public void insertKpiData(DwpasStKpiData dwpasStKpiData) throws Exception;

}
