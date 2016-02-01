package com.infosmart.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.infosmart.po.DwpasCKpiInfo;
import com.infosmart.po.DwpasCPrdInfo;
import com.infosmart.po.HelprateShow;
import com.infosmart.po.KpiInfo;
import com.infosmart.po.PrdMngInfo;

/**
 * 产品信息管理service
 */
public interface PrdMngInfoService {
	/**
	 * 查询产品信息列表
	 * 
	 * @param dwpasCPrdInfoDO
	 * @return
	 */
	public List<PrdMngInfo> ListPagePrdInfo(PrdMngInfo dwpasCPrdInfoDO);

	/**
	 * 查询产品目录列表
	 * 
	 * @return
	 */
	public List<PrdMngInfo> qryPrdFolder();

	/**
	 * 查询产品信息列表
	 * 
	 * @return
	 */
	public List<PrdMngInfo> qryPrdInfo();

	/**
	 * 查询产品健康度关系的所有产品信息
	 * 
	 * @return
	 */
	public List<PrdMngInfo> qryHelthPrdInfo();

	/**
	 * 根据模板Id查询产品健康度关系的所有产品信息
	 * 
	 * @return
	 */
	public List<PrdMngInfo> qryHelthPrdInfoByTemplateId(String templateId);

	/**
	 * 根据模板Id查询产品信息
	 * 
	 * @param templateId
	 * @return
	 */
	public List<PrdMngInfo> qryPrdInfoByTemplateId(String templateId);

	/**
	 * 根据产品Id修改产品的关联
	 * 
	 * @param markValue
	 * @param productIds
	 */
	public void updateProInfoByProductIdList(String markValue,
			List<String> productIds)throws Exception;

	/**
	 * 新增产品信息
	 * 
	 * @param dwpasCPrdInfoDO
	 * @param dwpasCHelprateShowDO
	 * @return
	 */
	public boolean insertPrdInfo(PrdMngInfo dwpasCPrdInfoDO) throws Exception;

	/**
	 * 修改产品信息
	 * 
	 * @param dwpasCPrdInfoDO
	 * @param dwpasCHelprateShowDO
	 * @return
	 */
	public boolean savePrdInfo(PrdMngInfo dwpasCPrdInfoDO) throws Exception;

	/**
	 * 新增产品关联指标
	 * 
	 * @param productId
	 * @param kpiCodes
	 * @return
	 */
	public boolean insertPrdKPIRelation(String productId, String kpiCodes) throws Exception;

	/**
	 * 通过productId查询产品信息
	 * 
	 * @param productId
	 * @return
	 */
	public PrdMngInfo getPrdInfo(String productId);

	/**
	 * 分别获得指定产品相关联指标列表及不关联指标列表
	 * 
	 * @param productId
	 * @return
	 */
	public Map<String, List<KpiInfo>> getKPIRelationAndPrd(String productId)throws Exception;

	/**
	 * 产品信息删除
	 * 
	 * @param productId
	 * @return
	 */
	public boolean deletePrdInfo(String productId)throws Exception;

	/**
	 * 通过产品ID获得产品求助率
	 * 
	 * @param productId
	 * @return
	 */
	public HelprateShow getPrdHelpRateById(String productId);

	/**
	 * 获取此产品关联的指标
	 * 
	 * @param event
	 * @return
	 * @throws Exception
	 */
	public List<DwpasCKpiInfo> getRelativeKPI(Serializable id) throws Exception;

	/**
	 * 获取此产品不关联的指标
	 * 
	 * @param event
	 * @return
	 * @throws Exception
	 */
	public List<DwpasCKpiInfo> getUnRelativeKPI(String id, String id1)
			throws Exception;

	// 搜索未关联指标
	public List<DwpasCKpiInfo> searchNotRelateKpiCodes(String kpiCode,
			String linkedKpiCode);

	/**
	 * 删除此产品关联的指标
	 * 
	 * @param event
	 * @return
	 * @throws Exception
	 */
	public void deleteRelativeKPI(Serializable productId) throws Exception;

	/**
	 * 插入此产品关联的指标
	 * 
	 * @param kList
	 * @return
	 * @throws Exception
	 */
	public void insertRelativeKPI(Map<?, ?> DwpasCKpiInfoList) throws Exception;
	/**
	 * 根据产品和指标CODE更新关联指标信息
	 * @param productId
	 * @param kpiCode
	 * @throws Exception
	 */
	public void updateRelateKpiInfo(String productId, String kpiCode) throws Exception;

	/**
	 * 获取已关联产品
	 * 
	 * @param typeId
	 * @return
	 */
	public List<PrdMngInfo> getMarkedPro(String typeId);

	/**
	 * 获取为关联产品
	 * 
	 * @param typeId
	 * @return
	 */
	public List<PrdMngInfo> getUnMarkedPro(String typeId, String keyCode);

	/**
	 * 更新已关联产品
	 * 
	 * @param productId
	 * @return
	 */
	public void updateMarkedPro(String typeId, List<String> productIds) throws Exception;

	/**
	 * 更新为关联产品
	 * 
	 * @param productId
	 * @return
	 */
	public void updateUnMarkedPro(String typeId, List<String> productIds) throws Exception;

	/**
	 * 根据productIds查询产品列表
	 * 
	 * @param productIds
	 * @return
	 */
	public List<PrdMngInfo> getPrdMngInfoByProids(List<String> productIds);

	/**
	 * 删除模板产品关联表
	 * @param productId
	 */
	public void deleteTepRPrd(String productId)throws Exception;
	/**
	 * 根据templateId获取所有可用的关联产品标记为4001的产品
	 * @return
	 */
	List<DwpasCPrdInfo> getAllProInfosByTemplateId(String templateId);
}
