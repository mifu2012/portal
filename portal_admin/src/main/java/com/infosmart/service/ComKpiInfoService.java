package com.infosmart.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.infosmart.po.ComKpiInfo;
import com.infosmart.po.DwpasCColumnInfo;
import com.infosmart.po.DwpasCKpiInfo;
import com.infosmart.po.KpiInfo;

/**
 * 通用指标信息管理
 */
public interface ComKpiInfoService {

	/**
	 * 列出某模块关联的通用指标
	 * 
	 * @param moduleId
	 * @return
	 */
	List<ComKpiInfo> listCommKpiInfo(String moduleId);

	/**
	 * 保存某模块关联的通用指标
	 * 
	 * @param comKpiList
	 * @param moduleId
	 * @throws Exception
	 */
	void saveCommKpiOfModule(DwpasCColumnInfo columnInfo,
			List<String> comKpiList, String moduleId) throws Exception;

	/**
	 * 获得通用指标信息列表
	 * 
	 * @return
	 */
	public List<ComKpiInfo> searchCommonKPI();

	/**
	 * 查询通用指标信息列表
	 * 
	 * @param cComKpiInfoDO
	 * @return
	 */
	public List<ComKpiInfo> listPageComKpiInfo(ComKpiInfo cComKpiInfoDO);

	/**
	 * 查询是否在龙榜榜中显示的通用指标
	 * 
	 * @param index
	 * @return
	 */
	public List<ComKpiInfo> tigerIsShowComKpiInfo(String moduleId);

	/**
	 * 搜索未在龙虎榜中显示的通用指标列表
	 * 
	 * @param comKpiCode
	 * @param linkedKpiCode
	 * @return
	 */
	public List<ComKpiInfo> searchTigerNotShow(String comKpiCode,
			String linkedKpiCode);

	/**
	 * 批量修改是否在龙虎榜中显示
	 * 
	 * @param showKpiCode
	 * @param notShowKpiCode
	 */
	public void updateShowTigerComKpiCodes(String showKpiCode,
			String notShowKpiCode) throws Exception;

	/**
	 * 保存龙虎榜之产品排行
	 * 
	 * @param comKpiCodeList
	 * @param moduleId
	 */
	void updatePrdRanking(List<String> comKpiCodeList, String moduleId);

	/**
	 * 通用指标管理新增
	 */
	public boolean insertCommonKPIConfig(ComKpiInfo cComKpiInfoDO);

	/**
	 * 通用指标管理修改
	 * 
	 * @param cComKpiInfoDO
	 * @return
	 */
	public boolean saveCommonKPIConfig(ComKpiInfo cComKpiInfoDO);

	/**
	 * 通用指标删除
	 * 
	 * @param comKpiCode
	 * @return
	 */
	public boolean deleteCommonKPI(String comKpiCode) throws Exception;

	/**
	 * 通用指标相关联指标保存
	 * 
	 * @param comKpiCode
	 * @param kpiCodes
	 * @return
	 */
	public boolean insertKPIRelation(String comKpiCode, String kpiCodes)
			throws Exception;

	public void updateRelateKpiInfo(String kpiCode, String linkedKpiCode)
			throws Exception;

	/**
	 * 获得通用指标信息
	 * 
	 * @param comKpiCode
	 * @return
	 */
	public List<ComKpiInfo> getCommonKPIInfo(String comKpiCode);

	/**
	 * 根据code获得通用指标信息
	 * 
	 * @param comKpiCode
	 * @return
	 */
	public ComKpiInfo getComkpiInfo(String comKpiCode);

	/**
	 * 分别获得指定通用指标相关联指标列表及不关联指标列表
	 * 
	 * @param comKpiCode
	 * @return
	 */
	public List<ComKpiInfo> getKPIRelationAndKPI(String comKpiCode);

	/**
	 * 更新通用指标信息
	 * 
	 * @param cComKpiInfoDO
	 */
	public void updateComKpiInfo(ComKpiInfo cComKpiInfoDO) throws Exception;

	/**
	 * 获取此通用指标关联的指标
	 * 
	 * @param event
	 * @return
	 * @throws Exception
	 */
	public List<DwpasCKpiInfo> getRelativeKPI(Serializable id) throws Exception;

	/**
	 * 获取此通用指标不关联的指标
	 * 
	 * @param event
	 * @return
	 * @throws Exception
	 */
	public List<DwpasCKpiInfo> getUnRelativeKPI(String id, String id1)
			throws Exception;

	/**
	 * 点击链接查询未关联指标
	 * 
	 * @param kpiCode
	 * @return
	 */
	public List<DwpasCKpiInfo> getNotRelateKpiBySetValue(String kpiCode);

	/**
	 * 删除此通用指标关联的指标
	 * 
	 * @param event
	 * @return
	 * @throws Exception
	 */
	public void deleteRelativeKPI(Serializable eventId) throws Exception;

	/**
	 * 插入此通用指标关联的指标
	 * 
	 * @param kList
	 * @return
	 * @throws Exception
	 */
	public void insertRelativeKPI(Map<?, ?> DwpasCKpiInfoList) throws Exception;

	/**
	 * 根据通用指标模块查询数据 zy
	 */
	public List<ComKpiInfo> selKpiCodeByCode(ComKpiInfo kpiInfoDO);
}
