package com.infosmart.portal.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosmart.portal.pojo.DwpasCKpiInfo;
import com.infosmart.portal.pojo.DwpasCPrdInfo;
import com.infosmart.portal.pojo.DwpasRColumnComKpi;
import com.infosmart.portal.pojo.DwpasStKpiData;
import com.infosmart.portal.pojo.ProdTransMonitor;
import com.infosmart.portal.service.DwpasCKpiInfoService;
import com.infosmart.portal.service.DwpasCPrdInfoService;
import com.infosmart.portal.service.DwpasStKpiDataService;
import com.infosmart.portal.util.DateUtils;
import com.infosmart.portal.util.StringUtils;

@Service
public class DwpasCPrdInfoServiceImpl extends BaseServiceImpl implements
		DwpasCPrdInfoService {
	@Autowired
	private DwpasCKpiInfoService dwpasCKpiInfoService;
	@Autowired
	private DwpasStKpiDataService dwpasStKpiDataService;

	@Override
	public DwpasCPrdInfo getDwpasCPrdInfoByProductId(String productId) {
		this.logger.info("查询产品信息:" + productId);
		if (!StringUtils.notNullAndSpace(productId)) {
			this.logger.error("根据产品ID查询产品六维度配置信息,传的参数为空");
			return null;
		}
		return this.myBatisDao.get(
				"com.infosmart.mapper.DwpasCPrdInfoMapper.queryOneByProductId",
				productId);
	}

	@Override
	public DwpasCPrdInfo getPrdInfoAndChildPrdByProductId(String productId) {
		this.logger.info("列出某产品信息及其子产品线");
		if (!StringUtils.notNullAndSpace(productId)) {
			this.logger.error("根据产品ID查询产品六维度配置信息,传的参数为空");
			return null;
		}
		return this.myBatisDao
				.get("com.infosmart.mapper.DwpasCPrdInfoMapper.getPrdAndChildPrdInfoByPrdId",
						productId);
	}

	@Override
	public List<DwpasCPrdInfo> queryAllPrdAndChildPrdInfo() {
		this.logger.info("列出产品及其子产品信息");
		return this.myBatisDao
				.getList("com.infosmart.mapper.DwpasCPrdInfoMapper.listPrdAndChildPrdInfo");
	}

	@Override
	public List<ProdTransMonitor> getProdTransInfos(
			List<DwpasRColumnComKpi> dwpasRColumnComKpi, String queryDate,
			String lastDate) {
		this.logger.info("查询产品全图信息");
		if (dwpasRColumnComKpi == null || dwpasRColumnComKpi.size() < 2) {
			this.logger.error("没有指定业务笔数及求助率指标");
			return null;
		}
		// 获得通用指标
		String transTimesKpiCode = dwpasRColumnComKpi.get(0).getComKpiCode();// 业务笔数
		// 发现暂未用到求助率，暂去掉 yangwg 2012.03.30
		/*
		 * String helpReateKpiCode =
		 * dwpasRColumnComKpi.get(1).getComKpiCode();// 求助于率
		 */
		// 查询所有产品信息
		List<DwpasCPrdInfo> dwpasCPrdInfoList = this.queryAllDwpasCPrdInfo();
		if (dwpasCPrdInfoList == null || dwpasCPrdInfoList.isEmpty()) {
			this.logger.warn("没有产品信息");
			return null;
		}
		// 产品ID
		List<String> prodIdList = new ArrayList<String>();
		prodIdList = getProdIds(dwpasCPrdInfoList);
		// 产品<业务笔数>关联的指标信息
		Map<String, DwpasCKpiInfo> prodAndKpiMapOfTransTimes = this.dwpasCKpiInfoService
				.queryKpiInfoByProdId(transTimesKpiCode,
						DwpasCKpiInfo.KPI_TYPE_OF_DAY, prodIdList);
		// 查询所有产品的当天和前一天的业务笔数
		Map<String, List<DwpasStKpiData>> crtProdKpiDataMap = this.getKpiData(
				prodAndKpiMapOfTransTimes, lastDate, queryDate);
		/*
		 * // 产品<求助率>关联的指标信息 Map<String, DwpasCKpiInfo> prodAndKpiMapOfHelpRete
		 * = this.dwpasCKpiInfoService .queryKpiInfoByProdId(helpReateKpiCode,
		 * DwpasCKpiInfo.KPI_TYPE_OF_DAY, prodIdList); // 查询所有产品的当天的用户求助率
		 * Map<String, List<DwpasStKpiData>> todayHelpKpiDataMap = this
		 * .getKpiData(prodAndKpiMapOfHelpRete, queryDate, queryDate);
		 */
		List<ProdTransMonitor> prodTransMonitorList = new ArrayList<ProdTransMonitor>();
		List<ProdTransMonitor> prodTransMonitorLastDayList = new ArrayList<ProdTransMonitor>();
		// 总的业务笔数
		BigDecimal totalAmount = new BigDecimal(0);
		if (crtProdKpiDataMap != null && !crtProdKpiDataMap.isEmpty()) {
			// 某产品今天的业务笔数
			DwpasStKpiData todayDataKpiData = null;
			// 某产品昨天的业务笔数
			DwpasStKpiData lastDataKpiData = null;
			/*
			 * // 某产品今天的 求助率 DwpasStKpiData todayHelpKpiData = null;
			 */
			ProdTransMonitor prodTransMonitor = null;
			// 昨天数据
			ProdTransMonitor prodTransMonitorLastDay = null;
			for (DwpasCPrdInfo prodInfo : dwpasCPrdInfoList) {
				if (prodInfo == null)
					continue;
				if (crtProdKpiDataMap.get(prodInfo.getProductId()) == null
						|| crtProdKpiDataMap.get(prodInfo.getProductId())
								.isEmpty()) {
					continue;
				}
				if (crtProdKpiDataMap.size() == 1) {
					this.logger.debug("没有昨天的记录");
					lastDataKpiData = new DwpasStKpiData();
					lastDataKpiData.setBaseValue(new BigDecimal(0));
					// 得到该产品的当天交易记录
					todayDataKpiData = crtProdKpiDataMap.get(
							prodInfo.getProductId()).get(0);
				} else {
					// 昨天的记录
					lastDataKpiData = crtProdKpiDataMap.get(
							prodInfo.getProductId()).get(0);
					// 得到该产品的当天交易记录
					todayDataKpiData = crtProdKpiDataMap.get(
							prodInfo.getProductId()).get(1);
				}
				if (todayDataKpiData == null)
					continue;
				totalAmount = totalAmount.add(todayDataKpiData.getBaseValue());
				/*
				 * // 求助KPi数据 todayHelpKpiData = todayHelpKpiDataMap.get(
				 * prodInfo.getProductId()).get(0);
				 */
				// 产品全图数据
				prodTransMonitor = new ProdTransMonitor();
				prodTransMonitor.setProdId(prodInfo.getProductId());
				prodTransMonitorLastDay = new ProdTransMonitor();
				prodTransMonitorLastDay.setProdId(prodInfo.getProductId());
				/*
				 * // 求助率
				 * prodTransMonitor.setHelpCount(todayHelpKpiData.getBaseValue
				 * ()); prodTransMonitor.setHelpCountUnit(todayHelpKpiData
				 * .getDwpasCKpiInfo().getUnit());
				 */
				// 产品名称
				prodTransMonitor.setProdName(prodInfo.getProductName());
				prodTransMonitorLastDay.setProdName(prodInfo.getProductName());
				// 交易数据值
				prodTransMonitor.setNowValue(todayDataKpiData.getBaseValue());
				// 昨天
				prodTransMonitorLastDay
						.setNowValue(lastDataKpiData == null ? new BigDecimal(0)
								: lastDataKpiData.getBaseValue());
				prodTransMonitor.setAvgValue(todayDataKpiData.getAgeValue());
				prodTransMonitor
						.setChangeRate(todayDataKpiData.getTrendValue());
				prodTransMonitor.setTopValue(todayDataKpiData.getMaxValue());
				prodTransMonitor.setHelpCount(todayDataKpiData.getShowValue());
				prodTransMonitor.setTopTime(todayDataKpiData.getGmtMaxTime());
				prodTransMonitor.setUnit(todayDataKpiData.getDwpasCKpiInfo()
						.getUnit());
				// add
				prodTransMonitorList.add(prodTransMonitor);
				prodTransMonitorLastDayList.add(prodTransMonitorLastDay);
			}
		}

		// 对百分比进行排序 ,从高到低
		Collections.sort(prodTransMonitorList, new Comparator() {

			@Override
			public int compare(Object o1, Object o2) {
				ProdTransMonitor pm1 = (ProdTransMonitor) o1;
				ProdTransMonitor pm2 = (ProdTransMonitor) o2;
				return pm2.getNowValue().compareTo(pm1.getNowValue());
			}

		});
		/*
		 * int index = 1; for (ProdTransMonitor vo : prodTransMonitorList) {
		 * vo.setIndexVal(index++); }
		 */
		// 对昨天数据进行排序
		Collections.sort(prodTransMonitorLastDayList, new Comparator() {

			@Override
			public int compare(Object o1, Object o2) {
				ProdTransMonitor pm1 = (ProdTransMonitor) o1;
				ProdTransMonitor pm2 = (ProdTransMonitor) o2;
				return pm2.getNowValue().compareTo(pm1.getNowValue());
			}

		});
		/*
		 * index = 1; for (ProdTransMonitor vo : prodTransMonitorLastDayList) {
		 * vo.setIndexVal(index++); }
		 */
		// 产品排行
		int i = 1, j = 1;
		for (ProdTransMonitor votoday : prodTransMonitorList) {
			if (votoday == null)
				continue;
			votoday.setIndexVal(i++);
			j = 1;
			// 跟昨天环比
			for (ProdTransMonitor volastday : prodTransMonitorLastDayList) {
				volastday.setIndexVal(j++);
				if (votoday.getProdId().equals(volastday.getProdId())) {
					votoday.setRankChange(volastday.getIndexVal()
							- votoday.getIndexVal());
					if (volastday.getIndexVal() - votoday.getIndexVal() > 0) {
						votoday.setTrend("up");
					} else if (volastday.getIndexVal() - votoday.getIndexVal() < 0) {
						votoday.setTrend("down");
					} else {
						votoday.setTrend("bal");
					}
					break;
				}
			}
			// 计算值占的百分比
			votoday.setPercentage(votoday.getNowValue().divide(totalAmount, 4,
					BigDecimal.ROUND_HALF_UP));
			votoday.setPercentageForDisplay(String.valueOf(votoday
					.getPercentage().multiply(new BigDecimal(100))
					.setScale(2, BigDecimal.ROUND_HALF_UP))
					+ "%");

		}
		if (prodTransMonitorList != null) {
			// 只列出排名前九个的记录，剩下的为都为“”
			if (prodTransMonitorList.size() > 10) {
				List<ProdTransMonitor> prodTransMonitorCount = prodTransMonitorList;
				prodTransMonitorList = prodTransMonitorList.subList(0, 9);
				List<ProdTransMonitor> after9 = prodTransMonitorCount.subList(
						9, prodTransMonitorCount.size());
				BigDecimal after9Sum = new BigDecimal(0);
				for (ProdTransMonitor pm : after9) {
					after9Sum = after9Sum.add(pm.getNowValue());
				}
				ProdTransMonitor last = new ProdTransMonitor();
				last.setProdName("其他");
				if (after9Sum.compareTo(new BigDecimal(0)) != 0) {
					last.setPercentage(after9Sum.divide(totalAmount, 4,
							BigDecimal.ROUND_HALF_UP));
					last.setPercentageForDisplay(String.valueOf(last
							.getPercentage().multiply(new BigDecimal(100))
							.setScale(2, BigDecimal.ROUND_HALF_UP))
							+ "%");
				} else {
					last.setPercentage(new BigDecimal(0));
					last.setPercentageForDisplay("0%");
					last.setPercentage(new BigDecimal(0));
				}
				prodTransMonitorList.add(last);
			}
		}
		return prodTransMonitorList;
	}

	/**
	 * 产品关联的指标数据<prod_id,kpi_data>
	 * 
	 * @param commKpiCode
	 * @param reportDate
	 * @param productIdList
	 * @return
	 */
	private Map<String, List<DwpasStKpiData>> getKpiData(
			Map<String, DwpasCKpiInfo> prodAndKpiMap, String reportStartDate,
			String reportEndDate) {
		this.logger.info("列出产品关联的在定范围<" + reportStartDate + "至" + reportEndDate
				+ ">的指标数据");
		if (prodAndKpiMap == null || prodAndKpiMap.isEmpty()) {
			this.logger.warn("产品关联的指标信息为空");
			return null;
		}
		// 产品关联的指标列表
		List<DwpasCKpiInfo> kpiInfoList = new ArrayList<DwpasCKpiInfo>();
		Entry entry = null;
		for (Iterator it = prodAndKpiMap.entrySet().iterator(); it.hasNext();) {
			entry = (Entry) it.next();
			kpiInfoList.add((DwpasCKpiInfo) entry.getValue());
		}
		// 查询指标数据
		Map<String, List<DwpasStKpiData>> kpiDataMap = this.dwpasStKpiDataService
				.listDwpasStKpiDataByKpiCode(kpiInfoList, reportStartDate,
						reportEndDate, DwpasCKpiInfo.KPI_TYPE_OF_DAY);
		Map<String, List<DwpasStKpiData>> prodIdAndKpiDataMap = new HashMap<String, List<DwpasStKpiData>>();
		String kpiCode = null;
		for (Iterator it = prodAndKpiMap.entrySet().iterator(); it.hasNext();) {
			entry = (Entry) it.next();
			kpiCode = ((DwpasCKpiInfo) entry.getValue()).getKpiCode();
			prodIdAndKpiDataMap.put(entry.getKey().toString(),
					kpiDataMap.get(kpiCode));
		}
		return prodIdAndKpiDataMap;
	}

	// 获取所有产品信息的productId
	private List<String> getProdIds(List<DwpasCPrdInfo> prdList) {
		List<String> lst = new ArrayList<String>();
		for (DwpasCPrdInfo ddo : prdList) {
			lst.add(ddo.getProductId());
		}
		return lst;
	}

	@Override
	public List<DwpasCPrdInfo> queryAllDwpasCPrdInfo() {
		return this.myBatisDao
				.getList("com.infosmart.mapper.DwpasCPrdInfoMapper.queryAllDwpasCPrdInfoAll");
	}

	@Override
	public List<ProdTransMonitor> getServiceInfos(
			List<DwpasRColumnComKpi> dwpasRColumnComKpi, String queryDate) {
		if (dwpasRColumnComKpi == null || dwpasRColumnComKpi.size() < 2) {
			this.logger.warn("没有关联多个指标");
			return null;
		}
		// 获得通用指标
		String transTimesKpiCode = dwpasRColumnComKpi.get(0).getComKpiCode();
		String helpReateKpiCode = dwpasRColumnComKpi.get(1).getComKpiCode();
		List<DwpasCPrdInfo> dwpasCPrdInfoList = new ArrayList<DwpasCPrdInfo>();
		// 查询所有产品信息
		dwpasCPrdInfoList = this.getDwpasCPrdInfoList(null);
		// 产品ID
		List<String> prodIdList = new ArrayList<String>();
		prodIdList = getProdIds(dwpasCPrdInfoList);
		// 产品<业务笔数>关联的指标信息
		Map<String, DwpasCKpiInfo> prodAndKpiMapOfTransTime = this.dwpasCKpiInfoService
				.queryKpiInfoByProdId(transTimesKpiCode,
						DwpasCKpiInfo.KPI_TYPE_OF_DAY, prodIdList);
		// 查询所有产品的当天的业务笔数
		Map<String, List<DwpasStKpiData>> crtProdKpiDataMap = this.getKpiData(
				prodAndKpiMapOfTransTime, queryDate, queryDate);
		// /////////////////////////修改说明，发现未用到求助率，故去掉,yangwg 2012.03.30
		/*
		 * // 产品<求助率>关联的指标信息 Map<String, DwpasCKpiInfo> prodAndKpiMapOfHelpRate
		 * = this.dwpasCKpiInfoService .queryKpiInfoByProdId(helpReateKpiCode,
		 * DwpasCKpiInfo.KPI_TYPE_OF_DAY, prodIdList); // 查询所有产品的当天的用户求助率
		 * Map<String, List<DwpasStKpiData>> todayHelpKpiDataMap = this
		 * .getKpiData(prodAndKpiMapOfHelpRate, queryDate, queryDate);
		 */
		List<ProdTransMonitor> prodTransMonitorList = new ArrayList<ProdTransMonitor>();
		// 总的业务笔数
		BigDecimal totalAmount = new BigDecimal(0);
		if (crtProdKpiDataMap != null && !crtProdKpiDataMap.isEmpty()) {
			// 某产品今天的业务笔数
			DwpasStKpiData todayDataKpiData = null;
			/*
			 * // 某产品今天的 求助率 DwpasStKpiData todayHelpKpiData = null;
			 */
			//
			ProdTransMonitor prodTransMonitor = null;
			//
			DwpasCKpiInfo dwpasCKpiInfo = null;
			for (DwpasCPrdInfo prodInfo : dwpasCPrdInfoList) {
				// 得到该产品的交易记录
				if (crtProdKpiDataMap.get(prodInfo.getProductId()) == null) {
					continue;
				}
				todayDataKpiData = crtProdKpiDataMap.get(
						prodInfo.getProductId()).get(0);
				if (todayDataKpiData == null)
					continue;
				totalAmount = totalAmount.add(todayDataKpiData.getBaseValue());
				/*
				 * if (todayHelpKpiDataMap.get(prodInfo.getProductId()) == null)
				 * { continue; }
				 */
				/*
				 * // 求助KPi数据 todayHelpKpiData = todayHelpKpiDataMap.get(
				 * prodInfo.getProductId()).get(0);
				 */
				// 产品全图数据
				prodTransMonitor = new ProdTransMonitor();
				/*
				 * // 求助率 if (todayHelpKpiDataMap.get(prodInfo.getProductId())
				 * == null) { continue; }
				 * 
				 * prodTransMonitor.setHelpCountUnit(todayHelpKpiData
				 * .getDwpasCKpiInfo().getUnit());
				 */
				// 产品名称
				prodTransMonitor.setProdName(prodInfo.getProductName());
				// 交易数据值
				if (todayDataKpiData.getDwpasCKpiInfo() != null) {
					dwpasCKpiInfo = todayDataKpiData.getDwpasCKpiInfo();
					prodTransMonitor.setUnit(dwpasCKpiInfo.getUnit());
					prodTransMonitor.setNowValue(this.getShowValue(
							dwpasCKpiInfo, todayDataKpiData.getBaseValue()));
					prodTransMonitor.setTopValue(this.getShowValue(
							dwpasCKpiInfo, todayDataKpiData.getMaxValue()));
					prodTransMonitor.setAvgValue(this.getShowValue(
							dwpasCKpiInfo, todayDataKpiData.getAgeValue()));
				} else {
					prodTransMonitor.setNowValue(todayDataKpiData
							.getBaseValue());
					prodTransMonitor
							.setAvgValue(todayDataKpiData.getAgeValue());
				}
				prodTransMonitor
						.setChangeRate(todayDataKpiData.getTrendValue());
				prodTransMonitor.setHelpCount(todayDataKpiData.getShowValue());
				prodTransMonitor.setTopTime(todayDataKpiData.getGmtMaxTime());
				prodTransMonitor.setProdName(prodInfo.getProductName());
				prodTransMonitor.setTopTime(todayDataKpiData.getGmtMaxTime());
				if ((null != prodTransMonitor.getChangeRate())) {
					if (prodTransMonitor.getChangeRate().compareTo(
							new BigDecimal(0)) == 1) {
						prodTransMonitor.setTrend("up");
					}
					BigDecimal changeRate = prodTransMonitor.getChangeRate()
							.multiply(new BigDecimal(100))
							.setScale(2, BigDecimal.ROUND_HALF_UP);
					prodTransMonitor.setChangeRate(changeRate);
				}
				// add
				prodTransMonitorList.add(prodTransMonitor);
			}
		}

		return prodTransMonitorList;
	}

	/**
	 * 转换数据格式
	 * 
	 * @param dwpasCKpiInfo
	 * @param baseValue
	 * @return
	 */
	public BigDecimal getShowValue(DwpasCKpiInfo dwpasCKpiInfo,
			BigDecimal baseValue) {
		BigDecimal showValue = null;
		if (dwpasCKpiInfo == null) {
			this.logger.info("没有指标信息");
			return new BigDecimal(0);
		} else if (baseValue != null) {
			// 计算�?
			BigDecimal calUnit = new BigDecimal(Math.pow(10,
					dwpasCKpiInfo.getConvertNum()));
			if (String.valueOf(DwpasCKpiInfo.CONVERT_TYPE_DIVIDE).equals(
					dwpasCKpiInfo.getConvertType())) {
				// 除法
				showValue = baseValue
						.divide(calUnit, dwpasCKpiInfo.getDecimalNum(),
								BigDecimal.ROUND_HALF_UP);
			} else if (String.valueOf(DwpasCKpiInfo.CONVERT_TYPE_MULTIPLY)
					.equals(dwpasCKpiInfo.getConvertType())) {
				// 乘法
				showValue = baseValue.multiply(calUnit);
			} else {
				showValue = baseValue;
			}
		}
		if (showValue == null)
			return new BigDecimal(0);
		// 格式化数�?
		DecimalFormat format = new DecimalFormat("##.00");
		showValue = new BigDecimal(format.format(showValue.doubleValue()));
		return showValue;
	}

	public List<DwpasCPrdInfo> getDwpasCPrdInfoList(List<String> productIdList) {
		// 查询所有产品信息
		DwpasCPrdInfo dwpasCPrdInfo = new DwpasCPrdInfo();
		dwpasCPrdInfo.setIsIndexShow("1");
		dwpasCPrdInfo.setIsUse("1");
		dwpasCPrdInfo.setIsFolder("0");
		dwpasCPrdInfo.setProductIdList(productIdList);
		return this.myBatisDao
				.getList(
						"com.infosmart.mapper.DwpasCPrdInfoMapper.queryDwpasCPrdInfoList",
						dwpasCPrdInfo);
	}

	/**
	 * 产品排行趋势图 要根据模块id templateId zy
	 * 
	 * @param productIdList
	 *            查询的产品ID
	 * @return
	 */
	public List<DwpasCPrdInfo> getDwpasCPrdInfoList(String templateId,
			List<String> productIdList) {
		// 查询所有产品信息
		DwpasCPrdInfo dwpasCPrdInfo = new DwpasCPrdInfo();
//		dwpasCPrdInfo.setIsIndexShow("1");
		dwpasCPrdInfo.setIsUse("1");
		dwpasCPrdInfo.setIsFolder("0");
		dwpasCPrdInfo.setTemplateId(templateId);
		dwpasCPrdInfo.setProductIdList(productIdList);
		List<DwpasCPrdInfo> prdInfoList = this.myBatisDao
				.getList(
						"com.infosmart.mapper.DwpasCPrdInfoMapper.queryDwpasCPrdInfoList",
						dwpasCPrdInfo);
		return prdInfoList;
	}
}