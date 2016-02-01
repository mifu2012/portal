package com.infosmart.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.infosmart.portal.pojo.DwpasCColumnInfo;
import com.infosmart.portal.pojo.DwpasCKpiInfo;
import com.infosmart.portal.pojo.DwpasCModuleInfo;
import com.infosmart.portal.pojo.DwpasRColumnComKpi;
import com.infosmart.portal.service.DwpasCColumnInfoService;
import com.infosmart.portal.util.StringUtils;

@Service
public class DwpasCColumnInfoServiceImpl extends BaseServiceImpl implements
		DwpasCColumnInfoService {

	@Override
	public List<DwpasCColumnInfo> listColumnAndRelKpiCodeByModuleId(
			String moduleId, String productId, int kpiType) {
		if (!StringUtils.notNullAndSpace(moduleId)) {
			this.logger.warn("列出某模块关联的栏目信息失败：模块ID为空");
			return null;
		}
		this.logger.info("查询产品及栏目关联的指标信息:" + productId);
		Map paramMap = new HashMap();
		paramMap.put("moduleId", moduleId);
		paramMap.put("productId", productId);
		paramMap.put("kpiType", kpiType);
		return this.myBatisDao
				.getList(
						"com.infosmart.mapper.DwpasCColumnInfoSqlMap.listColumnAndKpiCodeByModuleId",
						paramMap);
	}

	@Override
	public List<DwpasCColumnInfo> listColumnInfoListByModuleIdAndKpiType(
			String moduleId, int kpiType) {
		if (!StringUtils.notNullAndSpace(moduleId)) {
			this.logger.warn("列出某模块关联的栏目信息失败：模块ID为空");
			return null;
		}
		Map paramMap = new HashMap();
		paramMap.put("moduleId", moduleId);
		paramMap.put("kpiType", kpiType);
		return this.myBatisDao
				.getList(
						"com.infosmart.mapper.DwpasCColumnInfoSqlMap.listColumnInfoListByModuleIdAndKpiType",
						paramMap);
	}
	
	/**
	 * 自定义图形时，根据模块Id和指标类型kpiType，以及页面上获得的产品Id 获得关联栏目指标信息
	 * @param moduleId
	 * @param kpiType
	 * @param productId
	 * @return
	 */
	@Override
	public List<DwpasCColumnInfo> listColumnInfoListByModuleIdAndKpiTypeAndProductId(String moduleId, int kpiType, String productId){
		if (!StringUtils.notNullAndSpace(moduleId)) {
			this.logger.warn("列出某模块关联的栏目信息失败：模块ID为空");
			return null;
		}
		this.logger.info("查询产品及栏目关联的指标信息:" + productId);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("moduleId", moduleId);
		paramMap.put("productId", productId);
		paramMap.put("kpiType", kpiType);
		return this.myBatisDao
				.getList(
						"com.infosmart.mapper.DwpasCColumnInfoSqlMap.listColumnInfoListByModuleIdAndKpiTypeAndProductId",
						paramMap);
	}
	

	@Override
	public DwpasCModuleInfo getDwpasCModuleInfo(String templateId,
			String moduleCode) {
		this.logger.info("取栏目信息");
		if (!StringUtils.notNullAndSpace(moduleCode)) {
			this.logger.warn("取栏目信息:" + moduleCode);
			return null;
		}
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("moduleCode", moduleCode);
		paramMap.put("templateId", templateId);
		return this.myBatisDao
				.get("com.infosmart.mapper.DwpasCColumnInfoSqlMap.queryOneModuleInfoByCode",
						paramMap);
	}

	@Override
	public Map<String, List<DwpasCKpiInfo>> listColumnInfoByPrdAndColumnCodeForPrdDevelopColumn(
			String sytemTmplateId, List<String> columnCodeList,
			String productId, int kpiType) {
		this.logger.info("根据栏目CODE取栏目信息及关联的指标信息");
		if (!StringUtils.notNullAndSpace(sytemTmplateId)) {
			this.logger.warn("模块ID为空");
			return null;
		}
		if (!StringUtils.notNullAndSpace(productId)) {
			this.logger.warn("产品ID为空");
			return null;
		}
		if (columnCodeList == null || columnCodeList.isEmpty()) {
			this.logger.warn("栏目编码为空");
			return null;
		}
		Map paramMap = new HashMap();
		paramMap.put("productId", productId);
		paramMap.put("kpiType", kpiType);
		paramMap.put("templateId", sytemTmplateId);
		paramMap.put("columnCodes", columnCodeList);
		List<DwpasCColumnInfo> columnInfoList = this.myBatisDao
				.getList(
						"com.infosmart.mapper.DwpasCColumnInfoSqlMap.queryColumnAndKpiInfoForProductDevelopeColumn",
						paramMap);
		if (columnInfoList == null || columnInfoList.isEmpty())
			return null;

		Map<String, List<DwpasCKpiInfo>> columnAndKpiMap = new HashMap<String, List<DwpasCKpiInfo>>();
		for (DwpasCColumnInfo columnInfo : columnInfoList) {
			if (columnInfo == null || columnInfo.getKpiInfoList().isEmpty()) {
				continue;
			}
			// columnKind咱为栏目类型（如上月值，当月值，环比，同比，去年同期值）
			columnAndKpiMap.put(columnInfo.getColumnCode(),
					columnInfo.getKpiInfoList());
		}
		return columnAndKpiMap;
	}

	@Override
	public Map<String, List<DwpasCKpiInfo>> listColumnInfoByPrdAndColumnCode(
			String sytemTmplateId, List<String> columnCodeList,
			String productId, int kpiType) {
		this.logger.info("根据栏目CODE取栏目信息及关联的指标信息");
		if (!StringUtils.notNullAndSpace(sytemTmplateId)) {
			this.logger.warn("模块ID为空");
			return null;
		}
		if (!StringUtils.notNullAndSpace(productId)) {
			this.logger.warn("产品ID为空");
			return null;
		}
		if (columnCodeList == null || columnCodeList.isEmpty()) {
			this.logger.warn("栏目编码为空");
			return null;
		}
		Map paramMap = new HashMap();
		paramMap.put("productId", productId);
		paramMap.put("kpiType", kpiType);
		paramMap.put("templateId", sytemTmplateId);
		paramMap.put("columnCodes", columnCodeList);
		List<DwpasCColumnInfo> columnInfoList = this.myBatisDao
				.getList(
						"com.infosmart.mapper.DwpasCColumnInfoSqlMap.queryColumnAndKpiInfoByColumnCodesAndPrdId",
						paramMap);
		if (columnInfoList == null || columnInfoList.isEmpty())
			return null;

		Map<String, List<DwpasCKpiInfo>> columnAndKpiMap = new HashMap<String, List<DwpasCKpiInfo>>();
		for (DwpasCColumnInfo columnInfo : columnInfoList) {
			if (columnInfo == null || columnInfo.getKpiInfoList().isEmpty()) {
				continue;
			}
			columnAndKpiMap.put(columnInfo.getColumnCode(),
					columnInfo.getKpiInfoList());
		}
		// 防止前台取数报错
		List<DwpasCKpiInfo> kpiInfoList = new ArrayList<DwpasCKpiInfo>();
		kpiInfoList.add(new DwpasCKpiInfo());
		for (String columnCode : columnCodeList) {
			if (!columnAndKpiMap.containsKey(columnCode)) {
				columnAndKpiMap.put(columnCode, kpiInfoList);
			}
		}
		return columnAndKpiMap;
	}

	@Override
	public Map<String, DwpasCModuleInfo> listDwpasCModuleInfo(
			String templateId, List<String> moduleCodeList) {
		this.logger.info("根据(多个)模块编码列出模块信息");
		if (moduleCodeList == null || moduleCodeList.isEmpty()) {
			this.logger.warn("根据(多个)模块编码列出模块信息，参数为空");
			return null;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("moduleCodes", moduleCodeList);
		paramMap.put("templateId", templateId);
		List<DwpasCModuleInfo> moduleInfoList = this.myBatisDao
				.get("com.infosmart.mapper.DwpasCColumnInfoSqlMap.queryOneModuleInfoByCode",
						paramMap);
		if (moduleInfoList == null || moduleInfoList.isEmpty()) {
			this.logger.warn("根据(多个)模块编码列出模块信息失败，没有找到数据");
			return null;
		}
		Map<String, DwpasCModuleInfo> moduleInfoMap = new HashMap<String, DwpasCModuleInfo>();
		for (DwpasCModuleInfo moduleInfo : moduleInfoList) {
			moduleInfoMap.put(moduleInfo.getModuleCode(), moduleInfo);
		}
		return moduleInfoMap;
	}

	@Override
	public Map<String, List<DwpasRColumnComKpi>> listCommonCodeByColumnCodes(
			String sytemTmplateId, List<String> columnCodeList) {
		this.logger.info("批量查询某模块下的多个栏目关联的通用指标");
		if (columnCodeList == null || columnCodeList.isEmpty()) {
			this.logger.warn("批量查询某模块下的多个栏目关联的通用指标，参数为空");
			return new HashMap<String, List<DwpasRColumnComKpi>>();
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("templateId", sytemTmplateId);
		paramMap.put("columnCodes", columnCodeList);
		List<DwpasRColumnComKpi> rColumnComKpiList = this.myBatisDao
				.getList(
						"com.infosmart.mapper.DwpasCColumnInfoSqlMap.listCommKpiCodeByColumnCodes",
						paramMap);
		if (rColumnComKpiList == null || rColumnComKpiList.isEmpty()) {
			this.logger.warn("没有找到相关栏目关联的通用指标信息");
			return new HashMap<String, List<DwpasRColumnComKpi>>();
		}
		Map<String, List<DwpasRColumnComKpi>> rColumnComKpiMap = new HashMap<String, List<DwpasRColumnComKpi>>();
		List<DwpasRColumnComKpi> rlist = null;
		for (DwpasRColumnComKpi rColumnCom : rColumnComKpiList) {
			if (rColumnComKpiMap.containsKey(rColumnCom.getColumnCode())) {
				rColumnComKpiMap.get(rColumnCom.getColumnCode())
						.add(rColumnCom);
			} else {
				rlist = new ArrayList<DwpasRColumnComKpi>();
				rlist.add(rColumnCom);
				rColumnComKpiMap.put(rColumnCom.getColumnCode(), rlist);
			}
		}
		return rColumnComKpiMap;
	}

	@Override
	public DwpasCColumnInfo getColumnAndModelInfoByCode(String templateId,
			String columnCode) {
		this.logger.info("取栏目信息");
		if (!StringUtils.notNullAndSpace(columnCode)) {
			this.logger.warn("取栏目信息:" + columnCode);
			return null;
		}
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("columnCode", columnCode);
		paramMap.put("templateId", templateId);
		return this.myBatisDao.get(
				"com.infosmart.mapper.DwpasCColumnInfoSqlMap.queryOneByCode",
				paramMap);
	}

	@Override
	public List<DwpasRColumnComKpi> getCommonCodeByColumnCode(String columnCode) {
		return this.getCommonCodeByColumnCode("1", columnCode);
	}

	@Override
	public List<DwpasRColumnComKpi> getCommonCodeByColumnCode(
			String sytemTmplateId, String columnCode) {
		this.logger.info("列出某栏目关联的通用指标信息");
		if (!StringUtils.notNullAndSpace(sytemTmplateId)) {
			// 暂定,
			sytemTmplateId = "1";
		}
		if (!StringUtils.notNullAndSpace(columnCode)) {
			return null;
		}
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("templateId", sytemTmplateId);
		paramMap.put("columnCode", columnCode);
		return this.myBatisDao
				.getList(
						"com.infosmart.mapper.DwpasCColumnInfoSqlMap.queryCommKpiCodeByColumnCode",
						paramMap);
	}

	@Override
	public DwpasCColumnInfo getColumnAndKpiInfoByColumnCodeAndPrdId(
			String templateId, List<String> codes, String productId, int kpiType) {
		if (!StringUtils.notNullAndSpace(productId)) {
			this.logger.warn("根据产品ID，查询类型得到栏目信息失败：参数［产品ID］为空");
			return null;
		}
		if (!StringUtils.notNullAndSpace(templateId)) {
			this.logger.warn("根据产品ID，查询类型得到栏目信息失败：参数［模板ID］为空");
			return null;
		}
		if (codes == null || codes.isEmpty()) {
			this.logger.warn("根据产品ID，查询类型得到栏目信息失败：参数［栏目编码］为空");
			return null;
		}
		this.logger.info("根据产品ID，查询类型得到栏目信息");
		Map<String, Object> params = new HashMap<String, Object>();
		// 产品ID
		params.put("productId", productId);
		// 栏目编码
		params.put("codes", codes);
		// 指标类型
		params.put("kpiType", kpiType);
		// 模板ID
		params.put("templateId", templateId);
		// 栏目信息
		DwpasCColumnInfo dwpasCColumnInfo = new DwpasCColumnInfo();
		// 返回关联的指标信息
		List<DwpasCKpiInfo> kpiInfoList = this.myBatisDao
				.getList(
						"com.infosmart.mapper.DwpasCColumnInfoSqlMap.listKpiCodeByPrdIdAndColumnCode",
						params);
		dwpasCColumnInfo.setKpiInfoList(kpiInfoList);
		return dwpasCColumnInfo;
	}

	@Override
	public List<DwpasRColumnComKpi> getByTemplateIdAndUserType(
			Map<Object, Object> map) {
		return this.myBatisDao
				.getList(
						"com.infosmart.mapper.DwpasCColumnInfoSqlMap.queryCommonCodeByTemplateIdAndUserType",
						map);
	}

	@Override
	public List<DwpasCColumnInfo> listDwpasCColumnInfoSByMoudleId(
			String moudleId) {
		this.logger.info("根据模块ID列出关联的栏目信息");
		return this.myBatisDao
				.getList(
						"com.infosmart.mapper.DwpasCColumnInfoSqlMap.listDwpasCColumnInfoSByMoudleId",
						moudleId);
	}

	@Override
	public List<DwpasCColumnInfo> listAllCoumnInfosByModelIdAndKpiType(
			String moduleId, Integer kpiType) {
		this.logger.info("根据模块Id和日期类型查询所有模块信息");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("moduleId", moduleId);
		map.put("kpiType", kpiType);
		return this.myBatisDao
				.getList(
						"com.infosmart.mapper.DwpasCColumnInfoSqlMap.listAllCoumnInfosByModelIdAndKpiType",
						map);
	}
	
	/**
	 * 根据moduleId获取RColumnComKpi信息
	 * @param moduleId
	 * @return
	 */
	public List<DwpasRColumnComKpi> listRColumnComKpiInfoByModuleId(String moduleId){
		return this.myBatisDao
				.getList(
						"com.infosmart.mapper.DwpasCColumnInfoSqlMap.listRColumnKpiInfoByModuleId",
						moduleId);
	}
}
