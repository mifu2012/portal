package com.infosmart.portal.service.dwmis;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.infosmart.portal.pojo.dwmis.DwmisKpiData;
import com.infosmart.portal.pojo.dwmis.DwmisKpiInfo;
import com.infosmart.portal.pojo.dwmis.KPIDataCheck;
import com.infosmart.portal.util.dwmis.KPICommonQueryParam;

/**
 * 指标数据管理器
 * 
 * @author infosmart
 * 
 */
public interface KPIDataManager {

	/**
	 * 
	 * 统计指标各个字段有空值的记录数
	 * 
	 * @return
	 */
	public KPIDataCheck checkKPIDataNullValue();

	/**
	 * 
	 * 查找（统计）数据时间为空的对应的无重复的指标CODE集
	 * 
	 * @return
	 */
	public List<String> getKPICodesForNullReportDate();

	/**
	 * 
	 * 查找（统计）指标数据值为空的对应的无重复的指标CODE集
	 * 
	 * @return
	 */
	public List<String> getKPICodesForNullValue();

	/**
	 * 
	 * 查找（统计）时间粒度为空的对应的无重复的指标CODE集
	 * 
	 * @return
	 */
	public List<String> getKPICodesForNullDateType();

	/**
	 * 
	 * 查找（统计）统计方式为空的对应的无重复的指标CODE集
	 * 
	 * @return
	 */
	public List<String> getKPICodesForNullStaCode();

	/**
	 * 根据周期获取指定KPI Code 上期值（日为前天、周为上上周、月为上上月，都为当期值）
	 * 
	 * @param kpiCode
	 * @param period
	 * @return
	 */
	public Double getLastAmountByKPICodePeriod(String kpiCode, int period,
			Date mySysDate);

	/**
	 * 根据周期获取指定KPI Code当前值（日为昨天、周为上周、月为上月，都为当期值）
	 * 
	 * @param kpiCode
	 * @param period
	 * @param mySysDate
	 * @return
	 */
	public Double getCurrentAmountByKPICodePeriod(String kpiCode, int period,
			Date mySysDate);

	/**
	 * 根据日期粒度、统计方式获取前一天数据
	 * 
	 * @param dateType
	 * @param staCode
	 * @return
	 */
	public Double getRecentValue(String kpiCode, int dateType, int staCode);

	/**
	 * 根据KPICODE获取对应的指标数据值
	 * 
	 * 本方法将返回整个数值，不经过单位数量级转换。
	 */
	public double getKPIFullValue(String kpiCode);

	/**
	 * 根据KPICODE获取对应的指标数据值
	 * 
	 * 本方法将返回整个数值，不经过单位数量级转换。
	 */
	public double getKPIFullValue(DwmisKpiInfo kpiInfo);

	/**
	 * 列出多个指标的对应的数据值返回的类型为Map(kpiCode,kpiDataValue)
	 * 
	 * @param kpiInfoList
	 * @return
	 */
	public Map<String, Double> getMultiKpiFullValue(List<DwmisKpiInfo> kpiInfoList,String queryDate);

	/**
	 * 
	 * 根据KPICODE获取对应的指标数据值 本方法将返回经过单位数量级转换的数值。
	 * 
	 * 如某个KPI的值为1000，单位为百人，则返回10
	 * 
	 * @param kpiCode
	 * @return
	 */
	public double getKPIValue(String kpiCode);

	/**
	 * 获取指标列表
	 * 
	 * @return
	 */
	public List<DwmisKpiInfo> getKPIInfo(String actionType) throws Exception;

	/**
	 * 根据指标名搜索出相应信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<DwmisKpiInfo> getKPINameByKPIName(String kpiName)
			throws Exception;

	/**
	 * 根据指标code搜索相关指标数据信息
	 * 
	 * @param kpiCode
	 * @throws Exception
	 */
	public List<String> getKPIDataInfoByKPICode(String kpiCode)
			throws Exception;

	/**
	 * 根据指标的code，统计方式，时间粒度，时间获取指标的值
	 * 
	 * @param kpiCode
	 * @param stateCode
	 * @param dateType
	 * @param date
	 * @return
	 * @throws Exception
	 * 
	 * @version 2011年11月25日, PM 04:24:46
	 */
	public double getKPIValueByDetail(String kpiCode, Integer stateCode,
			Integer dateType, Date date) throws Exception;

	/**
	 * 获取当期值，使用跨年方案时间源
	 * 
	 * @param kpiCode
	 * @param period
	 * @return
	 */
	public Double getCurrentAmount(String kpiCode, int period);
}
