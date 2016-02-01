package com.infosmart.portal.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.infosmart.portal.pojo.DwpasCKpiInfo;
import com.infosmart.portal.pojo.DwpasLongHuBang;
import com.infosmart.portal.pojo.DwpasStKpiData;
import com.infosmart.portal.service.DwpasCKpiInfoService;
import com.infosmart.portal.taglib.PageInfo;
import com.infosmart.portal.util.StringUtils;

@Service
public class DwpasCKpiInfoServiceImpl extends BaseServiceImpl implements
		DwpasCKpiInfoService {

	public PageInfo listDwpasCKpiInfoByPagination(DwpasCKpiInfo dwpasCKpiInfo,
			int pageNo, int pageSize) {
		this.logger.info("分页查询指标信息");
		return this.myBatisDao
				.getListByPagination(
						"com.infosmart.mapper.DwpasCKpiInfoMapper.listKpiInfoByPagination",
						dwpasCKpiInfo, pageNo, pageSize);
	}

	public DwpasCKpiInfo getDwpasCKpiInfoByCode(String kpiCode) {
		this.logger.info("根据KPI编码查询KPI信息");
		if (!StringUtils.notNullAndSpace(kpiCode)) {
			this.logger.error("根据KPI编码查询KPI信息，查询的参数为空:" + kpiCode);
		}
		return this.myBatisDao.get(
				"com.infosmart.mapper.DwpasCKpiInfoMapper.queryOneByKpiCode",
				kpiCode);
	}

	public List<DwpasCKpiInfo> listDwpasCKpiInfoByCodes(List<String> kpiCodeList) {
		this.logger.info("根据qKPI编码查询KPI信息");
		if (kpiCodeList == null || kpiCodeList.isEmpty()) {
			return null;
		}
		String codeStr = ",";
		for (String code : kpiCodeList) {
			codeStr = codeStr + code + ",";
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("codeStr", codeStr);
		paramMap.put("kpiCodeList", kpiCodeList);
		return this.myBatisDao
				.getList(
						"com.infosmart.mapper.DwpasCKpiInfoMapper.listKpiInfoByKpiCodes",
						paramMap);
	}

	public DwpasCKpiInfo getDwpasCKpiInfoByProductIdAndCommonKpiCodeAndKpiType(
			String productId, String commonKpiCode, int kpiType) {
		this.logger.info("根据产品ID，通用指标，指标类型查询指标信息");
		if (!StringUtils.notNullAndSpace(productId)) {
			this.logger.error("根据产品ID，通用指标，指标类型查询指标信息,传入的参数[产品ID]为空");
			return null;
		}
		if (!StringUtils.notNullAndSpace(commonKpiCode)) {
			this.logger.error("根据产品ID，通用指标，指标类型查询指标信息,传入的参数[通用指标编码]为空");
			return null;
		}
		// 查询参数
		Map<String, String> paramMap = new HashMap<String, String>();
		//
		paramMap.put("productId", productId);
		paramMap.put("kpiType", String.valueOf(kpiType));
		paramMap.put("commonKpiCode", commonKpiCode);
		return this.myBatisDao
				.get("com.infosmart.mapper.DwpasCKpiInfoMapper.queryOneByCommonKpiAndKpiTypeIdAndProductId",
						paramMap);
	}

	public List<DwpasCKpiInfo> listDwpasCKpiInfoByProductIdAndMultiCommonKpiCodeAndKpiType(
			String productId, List<String> commonKpiCodeList, int kpiType) {
		this.logger.info("根据产品ID，多个通用指标，指标类型查询指标信息");
		if (!StringUtils.notNullAndSpace(productId)) {
			this.logger.error("根据产品ID，多个通用指标，指标类型查询指标信息,传入的参数[产品ID]为空");
			return null;
		}
		if (commonKpiCodeList == null || commonKpiCodeList.isEmpty()) {
			this.logger.error("根据产品ID，多个通用指标，指标类型查询指标信息,传入的参数[通用指标编码]为空");
			return null;
		}
		// 查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		//
		paramMap.put("productId", productId);
		paramMap.put("kpiType", String.valueOf(kpiType));
		paramMap.put("commonKpiCodes", commonKpiCodeList);
		return this.myBatisDao
				.getList(
						"com.infosmart.mapper.DwpasCKpiInfoMapper.queryMultiByMultiCommonKpiAndKpiTypeIdAndProductId",
						paramMap);
	}

	@Override
	public List<DwpasCKpiInfo> getAllOverallKpis(String kpiType) {

		if (!StringUtils.notNullAndSpace(kpiType)) {
			this.logger.error("根据日期类型查询大盘指标信息，传入的参数[日期类型]为空");
			return null;
		}
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("kpiType", kpiType);
		paramMap.put("isOverall", "1");
		paramMap.put("isShow", "1");
		paramMap.put("isUse", "1");
		return this.myBatisDao.getList(
				"com.infosmart.mapper.DwpasCKpiInfoMapper.getAllOverallKpis",
				paramMap);
	}

	@Override
	public DwpasCKpiInfo getOverallKpiCodeByCommonKpiCodeAndKpiType(
			String commonKpiCode, int kpiType) {
		this.logger.info("根据通用指标和指标类型,得到大盘指标信息");
		if (!StringUtils.notNullAndSpace(commonKpiCode)) {
			this.logger.error("根据通用指标和指标类型,得到大盘指标信息,传入的参数[通用指标]为空");
			return null;
		}
		// 查询参数
		Map<String, String> paramMap = new HashMap<String, String>();
		//
		paramMap.put("kpiType", String.valueOf(kpiType));
		paramMap.put("commonKpiCode", commonKpiCode);
		return this.myBatisDao
				.get("com.infosmart.mapper.DwpasCKpiInfoMapper.queryOverallKpiCodeByCommonKpiCodeAndKpiType",
						paramMap);
	}

	@Override
	public List<DwpasCKpiInfo> getChildCodeByTypeIds(String productId,
			List<String> typeIds, String kpiType) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (typeIds == null || typeIds.isEmpty()) {
			this.logger.warn("查询指标小类失败：typeIds为空");
			return null;
		}
		// xml文件按foreach In中的循序顺序排序
		String typeIdStr = ",";
		for (String typeId : typeIds) {
			typeIdStr = typeIdStr + typeId + ",";
		}
		paramMap.put("productId", productId);
		paramMap.put("typeIds", typeIds);
		paramMap.put("kpiType", kpiType);
		paramMap.put("isOverall", 0);
		paramMap.put("typeIdStr", typeIdStr);
		return this.myBatisDao
				.getList(
						"com.infosmart.mapper.DwpasCKpiInfoMapper.getChildCodeByTypeIds",
						paramMap);
	}

	@Override
	public Map<String, DwpasCKpiInfo> queryKpiInfoByProdId(String commonCode,
			int kpiType, List<String> prodIdList) {
		if (prodIdList == null || prodIdList.isEmpty()) {
			this.logger.info("产品ID为空");
			return null;
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("productIds", prodIdList);
		param.put("kpiType", String.valueOf(kpiType));
		param.put("comKpiCode", commonCode);
		List<Map<String, Object>> kpiInfoList = myBatisDao
				.getList(
						"com.infosmart.mapper.DwpasUserCounselMapper.qryAllByComAndPrdIds",
						param);
		return this.convertMaps2Map(kpiInfoList, "product_id");
	}

	/**
	 * 将do对象集合转换为Map,以指标CODE为KEY，指标对象为VALUE
	 * 
	 * @param kpiInfoDOs
	 *            指标对象集合
	 * @return 指标对象MAP
	 */
	private Map<String, DwpasCKpiInfo> convertMaps2Map(
			List<Map<String, Object>> kpiInfoList, String extraName) {
		Map<String, DwpasCKpiInfo> kpiInfos = new HashMap<String, DwpasCKpiInfo>();
		if (kpiInfoList == null || kpiInfoList.size() <= 0) {
			return kpiInfos;
		}
		DwpasCKpiInfo dto = null;
		for (Map<String, Object> prdAndKpiMap : kpiInfoList) {
			dto = fabricateDTO(prdAndKpiMap);
			if (dto == null) {
				continue;
			}
			Object product = prdAndKpiMap.get(extraName.toLowerCase());
			if (product == null) {
				continue;
			}
			kpiInfos.put(product.toString(), dto);
		}
		return kpiInfos;
	}

	/**
	 * 将hashmap数据转换为DTO对象
	 * 
	 * @param info
	 *            map数据,以字段名为KEY，字段值为VALUE
	 * @return KpiInfoDTO对象
	 */
	private static DwpasCKpiInfo fabricateDTO(Map<String, Object> info) {
		if (info == null) {
			return null;
		}
		DwpasCKpiInfo kpiInfo = new DwpasCKpiInfo();
		kpiInfo.setUnit(info.get("unit") == null ? "" : info.get("unit")
				.toString());
		kpiInfo.setKpiName(info.get("kpi_name") == null ? "" : info.get(
				"kpi_name").toString());
		kpiInfo.setConvertNum((short) (info.get("convert_num") == null ? 0
				: Integer.valueOf(info.get("convert_num").toString())));
		kpiInfo.setConvertType(info.get("convert_type") == null ? "" : info
				.get("convert_type").toString());
		kpiInfo.setDecimalNum((short) (info.get("decimal_num") == null ? 0
				: Integer.valueOf(info.get("decimal_num").toString())));
		kpiInfo.setDispName(info.get("disp_name") == null ? "" : info.get(
				"disp_name").toString());
		kpiInfo.setIsAverage(info.get("is_average") == null ? "" : info.get(
				"is_average").toString());
		kpiInfo.setIsCalKpi(info.get("is_cal_kpi") == null ? "" : info.get(
				"is_cal_kpi").toString());
		kpiInfo.setIsMax(info.get("is_max") == null ? "" : info.get("is_max")
				.toString());
		kpiInfo.setIsPercent(info.get("is_percent") == null ? "" : info.get(
				"is_percent").toString());
		kpiInfo.setIsShow(info.get("is_show") == null ? "" : info
				.get("is_show").toString());
		kpiInfo.setIsUse(info.get("is_use") == null ? "" : info.get("is_use")
				.toString());
		kpiInfo.setKpiCode(info.get("kpi_code") == null ? "" : info.get(
				"kpi_code").toString());
		kpiInfo.setKpiType(info.get("kpi_type") == null ? "" : info.get(
				"kpi_type").toString());
		return kpiInfo;
	}

	@Override
	public Map<String, List<DwpasLongHuBang>> listDwpasCKpiInfoByProductIdsAndMultiCommonKpiCodeAndKpiType(
			String templateId, int kpiType, String reportDate,
			List<String> commKpiCodeList) {
		// 查询参数
		// Map加泛型----------mf
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("templateId", templateId);
		paramMap.put("reportDate", reportDate);
		paramMap.put("kpiType", String.valueOf(kpiType));
		paramMap.put("commKpiCodeList", commKpiCodeList);
		StringBuffer orderField = new StringBuffer();
		if (commKpiCodeList != null && !commKpiCodeList.isEmpty()) {
			for (String comKpiCode : commKpiCodeList) {
				if (comKpiCode == null || comKpiCode.length() == 0)
					continue;
				if (orderField.toString().length() > 0)
					orderField.append(",");
				orderField.append(comKpiCode.trim());
			}
		}
		paramMap.put("orderField", orderField.toString());
		List<Map> DwpasLongHuBangList = this.myBatisDao
				.getList(
						"com.infosmart.mapper.DwpasLongHuBangMapper.querylongHuBangByCommonKpiAndKpiTypeIdAndProductIds",
						paramMap);
		List<DwpasLongHuBang> dwpasLongHuBangList = null;
		DwpasLongHuBang dwpasLongHuBang = null;
		DwpasCKpiInfo dwpasCKpiInfo = null;
		DwpasStKpiData dwpasStKpiData = null;
		Map<String, List<DwpasLongHuBang>> dwpasLongHuBangMap = new HashMap<String, List<DwpasLongHuBang>>();
		if (DwpasLongHuBangList != null && !DwpasLongHuBangList.isEmpty()) {
			try {
				for (Map longHuBangMap : DwpasLongHuBangList) {
					// 设置指标============================================================================
					dwpasCKpiInfo = new DwpasCKpiInfo();
					dwpasCKpiInfo.setKpiCode((String) (longHuBangMap
							.get("com_kpi_code") == null ? "" : longHuBangMap
							.get("com_kpi_code")));
					dwpasCKpiInfo.setIsPercent((String) (longHuBangMap
							.get("is_percent") == null ? "0" : longHuBangMap
							.get("is_percent")));
					dwpasCKpiInfo
							.setUnit(longHuBangMap.get("unit") == null ? ""
									: longHuBangMap.get("unit").toString());
					dwpasCKpiInfo
							.setDispName(longHuBangMap.get("com_kpi_name") == null ? ""
									: longHuBangMap.get("com_kpi_name")
											.toString());
					// 计算规则
					dwpasCKpiInfo
							.setKpiType(longHuBangMap.get("kpi_type") == null ? String
									.valueOf(DwpasCKpiInfo.KPI_TYPE_OF_DAY)
									: longHuBangMap.get("kpi_type").toString());// kpi_type
					dwpasCKpiInfo.setConvertNum(longHuBangMap
							.get("convert_num") == null ? 0 : Short
							.valueOf(longHuBangMap.get("convert_num")
									.toString()));
					dwpasCKpiInfo.setConvertType(longHuBangMap
							.get("convert_type") == null ? "" : longHuBangMap
							.get("convert_type").toString());
					dwpasCKpiInfo.setDecimalNum(longHuBangMap
							.get("decimal_num") == null ? 0 : Short
							.valueOf(longHuBangMap.get("decimal_num")
									.toString()));

					// 设置指标数据===================================================================
					dwpasStKpiData = new DwpasStKpiData();
					// 设置指标信息
					dwpasStKpiData.setDwpasCKpiInfo(dwpasCKpiInfo);
					// 当前值
					dwpasStKpiData
							.setBaseValue(new BigDecimal(Double
									.parseDouble(longHuBangMap
											.get("base_value") == null ? "0"
											: longHuBangMap.get("base_value")
													.toString())));
					dwpasStKpiData
							.setMaxValue(new BigDecimal(
									Double.parseDouble(longHuBangMap
											.get("max_value") == null ? "0"
											: longHuBangMap.get("max_value")
													.toString())));
					if (longHuBangMap.get("is_cal_kpi") != null
							&& "1".equals(longHuBangMap.get("is_cal_kpi"))) {
						this.logger.info("--->龙虎榜需要按规则进行计算:"
								+ dwpasCKpiInfo.getKpiCode());
						dwpasCKpiInfo.setIsCalKpi("1");
						// 如果KPI指标是计算型，则重新计算KPI数据
						dwpasCKpiInfo.setRoleFormula(longHuBangMap.get(
								"role_formula").toString());
						this.logger.info("--->计算规则:"
								+ dwpasCKpiInfo.getRoleFormula());
						dwpasStKpiData.setBaseValue(this.caculateRuleValue(
						// ki.role_formula
								dwpasCKpiInfo, reportDate).getBaseValue());
					}
					// 设置龙虎榜信息====================================================================
					dwpasLongHuBang = new DwpasLongHuBang();
					dwpasLongHuBang.setProductId(longHuBangMap
							.get("product_id").toString());
					dwpasLongHuBang.setProductName(longHuBangMap.get(
							"product_name").toString());
					dwpasLongHuBang.setDwpasStKpiData(dwpasStKpiData);
					if (!dwpasLongHuBangMap.containsKey(dwpasLongHuBang
							.getProductId().toString()
							+ ";"
							+ dwpasLongHuBang.getProductName())) {
						dwpasLongHuBangList = new ArrayList<DwpasLongHuBang>();
						dwpasLongHuBangMap.put(
								longHuBangMap.get("product_id").toString()
										+ ";"
										+ dwpasLongHuBang.getProductName(),
								dwpasLongHuBangList);
					}
					dwpasLongHuBangMap.get(
							dwpasLongHuBang.getProductId() + ";"
									+ dwpasLongHuBang.getProductName()).add(
							dwpasLongHuBang);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dwpasLongHuBangMap;
	}

	@Override
	public List<DwpasCKpiInfo> queryEventRelKpiCode(String eventId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("eventId", eventId);
		return this.myBatisDao
				.getList(
						"com.infosmart.mapper.DwpasCKpiInfoMapper.queryEventRelKpiCode",
						param);
	}
}
