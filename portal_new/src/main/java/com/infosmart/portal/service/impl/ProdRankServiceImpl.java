package com.infosmart.portal.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosmart.portal.pojo.DropDownList;
import com.infosmart.portal.pojo.DwpasCKpiInfo;
import com.infosmart.portal.pojo.DwpasCrossUserKpi;
import com.infosmart.portal.pojo.DwpasLongHuBang;
import com.infosmart.portal.pojo.DwpasRColumnComKpi;
import com.infosmart.portal.pojo.DwpasStKpiData;
import com.infosmart.portal.pojo.KpiNameUnitRelation;
import com.infosmart.portal.pojo.ProdKpiInfoDTO;
import com.infosmart.portal.pojo.TrendListDTO;
import com.infosmart.portal.service.DwpasCColumnInfoService;
import com.infosmart.portal.service.DwpasCKpiInfoService;
import com.infosmart.portal.service.DwpasStKpiDataService;
import com.infosmart.portal.service.ProdRankService;
import com.infosmart.portal.util.Constants;
import com.infosmart.portal.util.MathUtils;
import com.infosmart.portal.util.SystemColumnEnum;
import com.infosmart.portal.util.dwpas.ProdRankRelationEnum;
import com.infosmart.portal.vo.dwpas.UserCrossKpi;

@Service
public class ProdRankServiceImpl extends BaseServiceImpl implements
		ProdRankService {
	@Autowired
	private DwpasCKpiInfoService dwpasCKpiInfoService;
	@Autowired
	private DwpasStKpiDataService dwpasStKpiDataService;
	@Autowired
	private DwpasCColumnInfoService dwpasCColumnInfoService;
	/**
	 * 列与最大值关系CODE
	 */
	private static final String COLUNM_CODE = "column";
	/**
	 * 产品交叉指标分析，最大值比例
	 */
	private static final double PERCENT_MAX_VALUE = 0.9;

	@Override
	public List<Object> getCrossUserKpiDataByDate(String date) {

		// 查询出所有
		List<DwpasCrossUserKpi> dwpasCrossUserKpiList = this.myBatisDao
				.getList("com.infosmart.mapper.DwpasCrossUserKpiMapper.queryAllCrossUserKpi");
		if (dwpasCrossUserKpiList == null) {
			this.logger.info("根据时间获取交叉用户指标值失败：返回交叉用户数据为空");
			return null;
		}
		List<String> kpiCodes = this
				.getAllCrossUserKpiCodes(dwpasCrossUserKpiList);
		// 根据指标获取指标数据
		List<DwpasStKpiData> dwpasStKpiDataList = this.dwpasStKpiDataService
				.listDwpasStKpiData(kpiCodes, date,
						DwpasStKpiData.DATE_TYPE_OF_MONTH);
		Map<String, DwpasStKpiData> map = new HashMap<String, DwpasStKpiData>();
		Map<String, DwpasCKpiInfo> mapInfo = new HashMap<String, DwpasCKpiInfo>();

		if (dwpasStKpiDataList != null && dwpasStKpiDataList.size() > 0) {

			for (DwpasStKpiData dwpasStKpiData : dwpasStKpiDataList) {
				map.put(dwpasStKpiData.getKpiCode(), dwpasStKpiData);
				mapInfo.put(dwpasStKpiData.getKpiCode(),
						dwpasStKpiData.getDwpasCKpiInfo());
			}
			// List加判断-------mf
			if (dwpasCrossUserKpiList != null
					&& dwpasCrossUserKpiList.size() > 0) {

				for (DwpasCrossUserKpi dwpasCrossUserKpi : dwpasCrossUserKpiList) {
					// 商品产品数据
					dwpasCrossUserKpi.setTabaoKpiData(map.get(dwpasCrossUserKpi
							.getTaobaoCode()));
					dwpasCrossUserKpi.setAlipayKpiData(map
							.get(dwpasCrossUserKpi.getAlipayCode()));
					dwpasCrossUserKpi.setAlipayOutKpiData(map
							.get(dwpasCrossUserKpi.getAlipayOutCode()));
					dwpasCrossUserKpi.setAllKpiData(map.get(dwpasCrossUserKpi
							.getAllCode()));
					dwpasCrossUserKpi.setOnlyAlipayKpiData(map
							.get(dwpasCrossUserKpi.getAlipayOnlyCode()));
					dwpasCrossUserKpi.setOnlyOutKpiData(map
							.get(dwpasCrossUserKpi.getOutOnlyCode()));
					dwpasCrossUserKpi.setOnlyTaobaoKpiData(map
							.get(dwpasCrossUserKpi.getTaobaoOnlyCode()));
					dwpasCrossUserKpi.setOutKpiData(map.get(dwpasCrossUserKpi
							.getOutCode()));
					dwpasCrossUserKpi.setTaobaoAlipayData(map
							.get(dwpasCrossUserKpi.getTaobaoAlipayCode()));
					dwpasCrossUserKpi.setTaobaoOutData(map
							.get(dwpasCrossUserKpi.getTaobaoOutCode()));
					// 设置产品指标
					dwpasCrossUserKpi.getAlipayKpiData().setDwpasCKpiInfo(
							mapInfo.get(dwpasCrossUserKpi.getAlipayCode()));
					dwpasCrossUserKpi.getAlipayOutKpiData().setDwpasCKpiInfo(
							mapInfo.get(dwpasCrossUserKpi.getAlipayOutCode()));
					dwpasCrossUserKpi.getAllKpiData().setDwpasCKpiInfo(
							mapInfo.get(dwpasCrossUserKpi.getAllCode()));
					dwpasCrossUserKpi.getOnlyAlipayKpiData().setDwpasCKpiInfo(
							mapInfo.get(dwpasCrossUserKpi.getAlipayOnlyCode()));
					dwpasCrossUserKpi.getOnlyOutKpiData().setDwpasCKpiInfo(
							mapInfo.get(dwpasCrossUserKpi.getOutCode()));
					dwpasCrossUserKpi.getOnlyTaobaoKpiData().setDwpasCKpiInfo(
							mapInfo.get(dwpasCrossUserKpi.getTaobaoOnlyCode()));
					dwpasCrossUserKpi.getOutKpiData().setDwpasCKpiInfo(
							mapInfo.get(dwpasCrossUserKpi.getOutCode()));
					dwpasCrossUserKpi.getTabaoKpiData().setDwpasCKpiInfo(
							mapInfo.get(dwpasCrossUserKpi.getTaobaoCode()));
					dwpasCrossUserKpi.getTaobaoAlipayData()
							.setDwpasCKpiInfo(
									mapInfo.get(dwpasCrossUserKpi
											.getTaobaoAlipayCode()));
					dwpasCrossUserKpi.getTaobaoOutData().setDwpasCKpiInfo(
							mapInfo.get(dwpasCrossUserKpi.getTaobaoOutCode()));
				}
			}

		}
		return this.column2Row(dwpasCrossUserKpiList);
	}

	@Override
	public List<Object> getProdKpiDatas(String templateId, String date,
			String kpiType) {
		// 根据通用指标、产品关联的指标来取得指标数据
		// 龙虎榜数据集(通用指标名称\产品\指标数据)

		// 最终显示数据集
		List<Object> finalKpiInfos = new ArrayList<Object>();
		List<DwpasRColumnComKpi> columnRComKpiList = dwpasCColumnInfoService
				.getCommonCodeByColumnCode(templateId,
						SystemColumnEnum.LHB_PRODUCT_RANKING.getColumnCode());
		List<String> commKpiCodeList = new ArrayList<String>();
		if (columnRComKpiList != null && columnRComKpiList.size() > 0) {
			for (DwpasRColumnComKpi commKpiCode : columnRComKpiList) {
				commKpiCodeList.add(commKpiCode.getComKpiCode());
			}
		}
		if (commKpiCodeList == null || commKpiCodeList.isEmpty()) {
			this.logger.warn("龙虎榜模块之产品排行没有配置关联通用指标....");
			return finalKpiInfos;
		}
		this.logger.info("龙虎榜关联指标：" + commKpiCodeList.toString());
		// 获取通用指标、产品、指标数据==============================================================
		Map<String, List<DwpasLongHuBang>> dwpasLongHuBangMap = dwpasCKpiInfoService
				.listDwpasCKpiInfoByProductIdsAndMultiCommonKpiCodeAndKpiType(
						templateId, Integer.parseInt(kpiType), date,
						commKpiCodeList);
		int size = 0;
		if (dwpasLongHuBangMap != null && !dwpasLongHuBangMap.isEmpty()) {
			// 获得列名(通用指标名称)===================================================================
			// 通用指标名
			List<KpiNameUnitRelation> kpiNames = new ArrayList<KpiNameUnitRelation>();
			for (Object prdInfoAndName : (Object[]) dwpasLongHuBangMap.keySet()
					.toArray()) {
				KpiNameUnitRelation relation = null;
				
				
				for(int i=0; i<columnRComKpiList.size(); i++){
					DwpasStKpiData dwpasStKpiDate = new DwpasStKpiData();
					relation = new KpiNameUnitRelation();
					Boolean theFlag = false;
					
					for(DwpasLongHuBang dwpasLongHuBang : dwpasLongHuBangMap
							.get(prdInfoAndName)){
						if(dwpasLongHuBang.getDwpasStKpiData().getDwpasCKpiInfo().getKpiCode().equals(columnRComKpiList.get(i).getComKpiCode())){
							dwpasStKpiDate = dwpasLongHuBang.getDwpasStKpiData();
							theFlag = true;
							break;
						}
						
					}
					if(theFlag){
						relation.setKpiName(dwpasStKpiDate
								.getDwpasCKpiInfo().getDispName());
						relation.setUnit("("
								+ dwpasStKpiDate
										.getDwpasCKpiInfo().getUnit() + ")");
						kpiNames.add(relation);
					}else{
						relation.setKpiName(columnRComKpiList.get(i).getComKpiName()+"(未发现指标)");
						relation.setUnit("");
						kpiNames.add(relation);
					}
					
				}
				
				
/*				for (DwpasLongHuBang dwpasLongHuBang : dwpasLongHuBangMap
						.get(prdInfoAndName)) {
					relation = new KpiNameUnitRelation();
					relation.setKpiName(dwpasLongHuBang.getDwpasStKpiData()
							.getDwpasCKpiInfo().getDispName());
					relation.setUnit("("
							+ dwpasLongHuBang.getDwpasStKpiData()
									.getDwpasCKpiInfo().getUnit() + ")");
					kpiNames.add(relation);
					size = dwpasLongHuBangMap.get(prdInfoAndName).size();
				}*/
				break;
			}
			finalKpiInfos.add(kpiNames);
			// 求每一列的数据占总列数据的百分比================================================================================
			List<BigDecimal> maxValueList = new ArrayList<BigDecimal>();
			Map<String, List<DwpasStKpiData>> colDatas = new HashMap<String, List<DwpasStKpiData>>();
			initMaxDatas(maxValueList, colDatas, commKpiCodeList.size());

			// 组织结果集================================================================================
			// 指标信息结果集(产品\指标数据)
			List<DwpasStKpiData> dwpasStKpiDataList = new ArrayList<DwpasStKpiData>();
			// 产品信息(显示用)
			ProdKpiInfoDTO prodKpiInfo = null;
			for (Object prdInfoAndName : (Object[]) dwpasLongHuBangMap.keySet()
					.toArray()) {
				int index = 0;
				prodKpiInfo = new ProdKpiInfoDTO();
				prodKpiInfo.setProdId(prdInfoAndName.toString().split(";")[0]);
				prodKpiInfo
						.setProdName(prdInfoAndName.toString().split(";")[1]);
				dwpasStKpiDataList = new ArrayList<DwpasStKpiData>();
				if (dwpasLongHuBangMap.size() > 0) {
					for(int i=0; i<commKpiCodeList.size(); i++){
						Boolean flag = false;
						DwpasStKpiData dwpasStKpiDate = new DwpasStKpiData();
						for(DwpasLongHuBang dwpasLongHuBang : dwpasLongHuBangMap
								.get(prdInfoAndName)){
							if(dwpasLongHuBang.getDwpasStKpiData().getDwpasCKpiInfo().getKpiCode().equals(commKpiCodeList.get(i))){
								dwpasStKpiDate = dwpasLongHuBang.getDwpasStKpiData();
								break;
							}
							
						}
						if (i >= maxValueList.size())
							break;
						if (maxValueList.get(i) == null)
							continue;
						dwpasStKpiDataList.add(dwpasStKpiDate);
						int remove = maxValueList.get(i).compareTo(dwpasStKpiDate.getBaseValue());
						if (remove < 0) {
							maxValueList.remove(i);
							maxValueList.add(i, dwpasStKpiDate.getBaseValue());
						}
						colDatas.get(COLUNM_CODE + (i)).add(dwpasStKpiDate);
						
						
					}
					
					
				/*	for (DwpasLongHuBang dwpasLongHuBang : dwpasLongHuBangMap
							.get(prdInfoAndName)) {
						System.out.println(dwpasLongHuBangMap.get(prdInfoAndName).get(index).getDwpasStKpiData().getDwpasCKpiInfo().getKpiCode());
						System.out.println(dwpasLongHuBang.getDwpasStKpiData().getDwpasCKpiInfo().getKpiCode());
						DwpasStKpiData aa = dwpasLongHuBangMap.get(prdInfoAndName).get(0).getDwpasStKpiData();
						index++;
						if (index > maxValueList.size())
							break;
						if (maxValueList.get(index - 1) == null)
							continue;
						dwpasStKpiDataList.add(dwpasLongHuBang
								.getDwpasStKpiData());
						int remove = maxValueList.get(index - 1).compareTo(
								dwpasLongHuBang.getDwpasStKpiData()
										.getBaseValue());
						if (remove < 0) {
							maxValueList.remove(index - 1);
							maxValueList.add(index - 1, dwpasLongHuBang
									.getDwpasStKpiData().getBaseValue());
						}
						colDatas.get(COLUNM_CODE + (index - 1)).add(
								dwpasLongHuBang.getDwpasStKpiData());
					}*/
					prodKpiInfo.setKpiDatas(dwpasStKpiDataList);
				}
				finalKpiInfos.add(prodKpiInfo);
			}
			findMaxValues(colDatas, maxValueList);
		}
		return finalKpiInfos;
	}

	/**
	 * @param kpis
	 *            交叉用户指标对象集合
	 * @return 交叉用户指标CODE集合
	 */
	private List<String> getAllCrossUserKpiCodes(List<DwpasCrossUserKpi> kpis) {
		List<String> codes = new ArrayList<String>();
		// List加判断-------mf
		if (kpis != null & kpis.size() >= 0) {
			for (DwpasCrossUserKpi kpi : kpis) {
				codes.add(kpi.getAlipayCode());
				codes.add(kpi.getAlipayOutCode());
				codes.add(kpi.getAllCode());
				codes.add(kpi.getAlipayOnlyCode());
				codes.add(kpi.getOutOnlyCode());
				codes.add(kpi.getTaobaoOnlyCode());
				codes.add(kpi.getOutCode());
				codes.add(kpi.getTaobaoOutCode());
				codes.add(kpi.getTaobaoAlipayCode());
				codes.add(kpi.getTaobaoCode());
			}
		}
		return codes;
	}

	private List<Object> column2Row(List<DwpasCrossUserKpi> datas) {

		List<KpiNameUnitRelation> kpiNames = new ArrayList<KpiNameUnitRelation>();
		List<DwpasStKpiData> taobaoValues = new ArrayList<DwpasStKpiData>();
		List<DwpasStKpiData> alipayValues = new ArrayList<DwpasStKpiData>();
		List<DwpasStKpiData> outValues = new ArrayList<DwpasStKpiData>();
		List<DwpasStKpiData> onlyTaobao = new ArrayList<DwpasStKpiData>();
		List<DwpasStKpiData> onlyAlipay = new ArrayList<DwpasStKpiData>();
		List<DwpasStKpiData> onlyOut = new ArrayList<DwpasStKpiData>();
		List<DwpasStKpiData> taobaoAlipay = new ArrayList<DwpasStKpiData>();
		List<DwpasStKpiData> alipayOut = new ArrayList<DwpasStKpiData>();
		List<DwpasStKpiData> taobaoOut = new ArrayList<DwpasStKpiData>();
		List<DwpasStKpiData> all = new ArrayList<DwpasStKpiData>();

		List<Object> finalDatas = new ArrayList<Object>();
		finalDatas.add(kpiNames);

		finalDatas.add(fabricateUserCrossKpi(
				ProdRankRelationEnum.TAOBAO.getMessage(), taobaoValues,
				ProdRankRelationEnum.TAOBAO.getCode()));
		finalDatas.add(fabricateUserCrossKpi(
				ProdRankRelationEnum.ALIPAY.getMessage(), alipayValues,
				ProdRankRelationEnum.ALIPAY.getCode()));
		finalDatas.add(fabricateUserCrossKpi(
				ProdRankRelationEnum.OUT.getMessage(), outValues,
				ProdRankRelationEnum.OUT.getCode()));
		finalDatas.add(fabricateUserCrossKpi(
				ProdRankRelationEnum.ONLYTAOBAO.getMessage(), onlyTaobao,
				ProdRankRelationEnum.ONLYTAOBAO.getCode()));
		finalDatas.add(fabricateUserCrossKpi(
				ProdRankRelationEnum.ONLYALIPAY.getMessage(), onlyAlipay,
				ProdRankRelationEnum.ONLYALIPAY.getCode()));
		finalDatas.add(fabricateUserCrossKpi(
				ProdRankRelationEnum.ONLYOUT.getMessage(), onlyOut,
				ProdRankRelationEnum.ONLYOUT.getCode()));
		finalDatas.add(fabricateUserCrossKpi(
				ProdRankRelationEnum.TAOBAOANDALIPAY.getMessage(),
				taobaoAlipay, ProdRankRelationEnum.TAOBAOANDALIPAY.getCode()));
		finalDatas.add(fabricateUserCrossKpi(
				ProdRankRelationEnum.ALIPAYANDOUT.getMessage(), alipayOut,
				ProdRankRelationEnum.ALIPAYANDOUT.getCode()));
		finalDatas.add(fabricateUserCrossKpi(
				ProdRankRelationEnum.TAOBAOANDOUT.getMessage(), taobaoOut,
				ProdRankRelationEnum.TAOBAOANDOUT.getCode()));
		finalDatas.add(fabricateUserCrossKpi(
				ProdRankRelationEnum.ALL.getMessage(), all,
				ProdRankRelationEnum.ALL.getCode()));

		KpiNameUnitRelation nameRelation = null;
		if (datas != null && datas.size() > 0) {
			for (DwpasCrossUserKpi data : datas) {
				nameRelation = new KpiNameUnitRelation();
				nameRelation.setKpiName(data.getKpiName());
				data.getTabaoKpiData();
				if (data.getTabaoKpiData() != null) {
					if (data.getTabaoKpiData().getDwpasCKpiInfo() != null) {
						nameRelation.setUnit(data.getTabaoKpiData()
								.getDwpasCKpiInfo() != null ? data
								.getTabaoKpiData().getDwpasCKpiInfo().getUnit()
								: null);
					}

				}
				kpiNames.add(nameRelation);
				taobaoValues.add(data.getTabaoKpiData());
				alipayValues.add(data.getAlipayKpiData());
				outValues.add(data.getOutKpiData());
				onlyTaobao.add(data.getOnlyTaobaoKpiData());
				onlyAlipay.add(data.getOnlyAlipayKpiData());
				onlyOut.add(data.getOnlyOutKpiData());
				taobaoAlipay.add(data.getTaobaoAlipayData());
				alipayOut.add(data.getAlipayOutKpiData());
				taobaoOut.add(data.getTaobaoOutData());
				all.add(data.getAllKpiData());
			}
		}
		return finalDatas;
	}

	private UserCrossKpi fabricateUserCrossKpi(String rowName,
			List<DwpasStKpiData> kpiDatas, String type) {
		UserCrossKpi dto = new UserCrossKpi();
		dto.setKpiDatas(kpiDatas);
		dto.setFlatName(rowName);
		dto.setType(type);
		return dto;
	}

	// 平台交叉分析趋势图
	@Override
	public List<TrendListDTO> init() {
		List<TrendListDTO> kpis = new ArrayList<TrendListDTO>();
		List<DwpasCrossUserKpi> crossUsers = this.myBatisDao
				.getList("com.infosmart.mapper.DwpasCrossUserKpiMapper.queryAllCrossUserKpi");
		if (crossUsers != null && crossUsers.size() > 0) {
			for (DwpasCrossUserKpi crossUser : crossUsers) {
				TrendListDTO dto = new TrendListDTO();
				dto.setName(crossUser.getKpiName());
				List<DropDownList> properties = new ArrayList<DropDownList>();
				// 使用过淘宝
				DropDownList taobao = new DropDownList();
				taobao.setName(ProdRankRelationEnum.TAOBAO.getMessage());
				taobao.setValue(crossUser.getTaobaoCode());
				properties.add(taobao);
				// 使用过站内
				DropDownList alipay = new DropDownList();
				alipay.setName(ProdRankRelationEnum.ALIPAY.getMessage());
				alipay.setValue(crossUser.getAlipayCode());
				properties.add(alipay);
				// 使用过外部商家
				DropDownList out = new DropDownList();
				out.setName(ProdRankRelationEnum.OUT.getMessage());
				out.setValue(crossUser.getOutCode());
				properties.add(out);
				// 仅使用过淘宝
				DropDownList onlyTaobao = new DropDownList();
				onlyTaobao
						.setName(ProdRankRelationEnum.ONLYTAOBAO.getMessage());
				onlyTaobao.setValue(crossUser.getTaobaoOnlyCode());
				properties.add(onlyTaobao);
				// 仅使用过站内
				DropDownList onlyAlipay = new DropDownList();
				onlyAlipay
						.setName(ProdRankRelationEnum.ONLYALIPAY.getMessage());
				onlyAlipay.setValue(crossUser.getAlipayOnlyCode());
				properties.add(onlyAlipay);
				// 仅使用外部商家
				DropDownList onlyOut = new DropDownList();
				onlyOut.setName(ProdRankRelationEnum.ONLYOUT.getMessage());
				onlyOut.setValue(crossUser.getOutOnlyCode());
				properties.add(onlyOut);
				// 淘宝和站内
				DropDownList taobaoAndAlipay = new DropDownList();
				taobaoAndAlipay.setName(ProdRankRelationEnum.TAOBAOANDALIPAY
						.getMessage());
				taobaoAndAlipay.setValue(crossUser.getTaobaoAlipayCode());
				properties.add(taobaoAndAlipay);
				// 外部商家和站内
				DropDownList outAndAlipay = new DropDownList();
				outAndAlipay.setName(ProdRankRelationEnum.ALIPAYANDOUT
						.getMessage());
				outAndAlipay.setValue(crossUser.getAlipayOutCode());
				properties.add(outAndAlipay);
				// 淘宝和外部商家
				DropDownList taobaoAndOut = new DropDownList();
				taobaoAndOut.setName(ProdRankRelationEnum.TAOBAOANDOUT
						.getMessage());
				taobaoAndOut.setValue(crossUser.getTaobaoOutCode());
				properties.add(taobaoAndOut);
				// 所有
				DropDownList all = new DropDownList();
				all.setName(ProdRankRelationEnum.ALL.getMessage());
				all.setValue(crossUser.getAllCode());
				properties.add(all);
				dto.setProperties(properties);
				kpis.add(dto);
			}
		}
		return kpis;
	}

	private void initMaxDatas(List<BigDecimal> maxValues,
			Map<String, List<DwpasStKpiData>> map, int colunmSize) {
		for (int i = 0; i < colunmSize; i++) {
			maxValues.add(BigDecimal.valueOf(0));
			map.put(COLUNM_CODE + i, new ArrayList<DwpasStKpiData>());
		}
	}

	private void findMaxValues(Map<String, List<DwpasStKpiData>> map,
			List<BigDecimal> maxValues) {
		for (int i = 0; i < maxValues.size(); i++) {
			List<DwpasStKpiData> datas = map.get(COLUNM_CODE + i);
			for (DwpasStKpiData data : datas) {
				data.setMaxValue(MathUtils
						.div(data.getBaseValue(), maxValues.get(i))
						.multiply(Constants.ONE_HANDREN)
						.multiply(BigDecimal.valueOf(PERCENT_MAX_VALUE)));
			}
		}
	}
}
