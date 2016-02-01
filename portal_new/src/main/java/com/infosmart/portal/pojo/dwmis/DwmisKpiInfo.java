package com.infosmart.portal.pojo.dwmis;

import java.util.Date;
import java.util.List;

/**
 * 指标信息
 * 
 * @author infosmart
 * 
 */
public class DwmisKpiInfo {
	// 指标编码
	private String kpiCode;
	/**
	 * 指标类型ID select * from MIS_TYPE mt where mt.group_id=4000; 计算指标；基础指标
	 */
	private Integer typeId;
	private String typeIdDesc;
	//
	private Integer chartId;
	/**
	 * 指明该指标单位，如人，笔数，元 select * from MIS_TYPE mt where mt.group_id=5000 5001
	 * 5000 元 5002 5000 笔 5003 5000 个 5004 5000 % 5005 5000 基点
	 */
	private Integer unitId;
	private String unitIdDesc;
	private String legendId;

	/**
	 * 指标信息
	 */
	private DwmisKpiInfo dwmisKpiInfo;
	// 指标名称
	private String kpiName;
	// 指标说明
	private String detail;
	// 指标口径
	private String kpiRestrict;
	// 时间口径
	private String restrictTime;
	/**
	 * 日统计指标
	 */
	public static final int KPI_TYPE_OF_DAY = 1002;
	/**
	 * 周统计指标
	 */
	public static final int KPI_TYPE_OF_WEEK = 1003;
	/**
	 * 月统计指标
	 */
	public static final int KPI_TYPE_OF_MONTH = 1004;

	// 关联表名
	private String relateTable;
	private String relateTableDes;
	// 是否在首页展示
	private Integer isShow;
	private String createUser;
	private Date createDate;
	/**
	 * 指明该指标是关注什么（峰值、谷值、平均值、周日平均值等）。 select * from MIS_TYPE mt where
	 * mt.group_id=3000 30012 月峰值（计去年） 30021 月谷值（pt） 30052 日累积值（计去年） 30051
	 * 日累积值（pt） 3008 月当期 3009 月期末 3001 月峰值 3002 月谷值 3003 日峰值 3004 日谷值 3005 日累积值
	 * 3006 月累积值 3007 七日均值峰值 30011 月峰值（pt）
	 */
	private Integer goalType;
	private String goalTypeDesc;
	/**
	 * 一级指标类别 select * from MIS_TYPE mt where mt.group_id=9000 9001 9000 用户
	 * 用户KPI 9002 9000 资金 资金KPI 9003 9000 商户 商户KPI 9004 9000 无线 无线KPI 9009 9000
	 * 运维 运维KPI
	 */
	private Integer levelType1;
	private String levelType1Desc;
	/**
	 * 二级指标类别 select * from MIS_TYPE mt where mt.group_id=10000 10001 10000 用户
	 * 10002 10000 商户 10003 10000 无线
	 */
	private Integer levelType2;
	private String levelType2Desc;
	/**
	 * 是否显示描述
	 */
	private Integer isShowDesc;
	/**
	 * 时间粒度定义 年|月|季周|日
	 */
	private String period;
	// 展现名称
	private String kpiNameShow;
	/**
	 * 指定首页要展示的数量级，如十、千、万、千万 select * from MIS_TYPE mt where mt.group_id=6000
	 * 6001 6000 个 6002 6000 十 6003 6000 百 6004 6000 千 6005 6000 万 6006 6000 十万
	 * 6007 6000 百万 6008 6000 千万 6009 6000 亿 6010 6000 十亿 6011 6000 百亿
	 */
	private Integer sizeId;
	private String sizeIdDesc;
	/**
	 * 指明该指标的日粒度为 T+n ，这里记录n为多少
	 */
	private Integer dayOffSet;

	private Integer showOrder;

	private Integer isDataRangeFix;

	private double dataRangeTop;

	private double dataRangeBottom;

	private String kpiNameKpi;

	private String businessType;

	/**
	 * 指标单位字符
	 */
	private String unitStr;

	/**
	 * 指标单位字符（跟随在KPI Name后面的，形如 生活助手新增用户数(万)，这里的 (万)
	 */
	private String unitStrPost;

	// 单位
	private String unitName;
	// 数量级
	private String sizName;

	/**
	 * 保留小数位数
	 */
	private Integer decimalNum;

	public String getSizName() {
		return sizName;
	}

	public void setSizName(String sizName) {
		this.sizName = sizName;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	// 关联的子指标
	private List<DwmisKpiInfo> subKpiInfoList;

	public String getLegendId() {
		return legendId;
	}

	public void setLegendId(String legendId) {
		this.legendId = legendId;
	}

	public String getUnitStr() {
		return unitStr;
	}

	public void setUnitStr(String unitStr) {
		this.unitStr = unitStr;
	}

	public String getUnitStrPost() {
		return unitStrPost;
	}

	public void setUnitStrPost(String unitStrPost) {
		this.unitStrPost = unitStrPost;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getKpiNameKpi() {
		return kpiNameKpi;
	}

	public void setKpiNameKpi(String kpiNameKpi) {
		this.kpiNameKpi = kpiNameKpi;
	}

	public double getDataRangeBottom() {
		return dataRangeBottom;
	}

	public void setDataRangeBottom(double dataRangeBottom) {
		this.dataRangeBottom = dataRangeBottom;
	}

	public double getDataRangeTop() {
		return dataRangeTop;
	}

	public void setDataRangeTop(double dataRangeTop) {
		this.dataRangeTop = dataRangeTop;
	}

	public Integer getIsDataRangeFix() {
		return isDataRangeFix;
	}

	public void setIsDataRangeFix(Integer isDataRangeFix) {
		this.isDataRangeFix = isDataRangeFix;
	}

	public Integer getShowOrder() {
		return showOrder;
	}

	public void setShowOrder(Integer showOrder) {
		this.showOrder = showOrder;
	}

	public List<DwmisKpiInfo> getSubKpiInfoList() {
		return subKpiInfoList;
	}

	public void setSubKpiInfoList(List<DwmisKpiInfo> subKpiInfoList) {
		this.subKpiInfoList = subKpiInfoList;
	}

	public String getTypeIdDesc() {
		return typeIdDesc;
	}

	public void setTypeIdDesc(String typeIdDesc) {
		this.typeIdDesc = typeIdDesc;
	}

	public String getUnitIdDesc() {
		if (this.unitIdDesc == null || "个".equals(unitIdDesc.trim())
				|| "%".equals(unitIdDesc.trim())) {
			return unitIdDesc;
		} else {
			return this.sizeIdDesc + unitIdDesc;
		}
	}

	public void setUnitIdDesc(String unitIdDesc) {
		this.unitIdDesc = unitIdDesc;
	}

	public String getGoalTypeDesc() {
		return goalTypeDesc;
	}

	public void setGoalTypeDesc(String goalTypeDesc) {
		this.goalTypeDesc = goalTypeDesc;
	}

	public String getLevelType1Desc() {
		return levelType1Desc;
	}

	public void setLevelType1Desc(String levelType1Desc) {
		this.levelType1Desc = levelType1Desc;
	}

	public String getLevelType2Desc() {
		return levelType2Desc;
	}

	public void setLevelType2Desc(String levelType2Desc) {
		this.levelType2Desc = levelType2Desc;
	}

	public String getSizeIdDesc() {
		return sizeIdDesc;
	}

	public void setSizeIdDesc(String sizeIdDesc) {
		this.sizeIdDesc = sizeIdDesc;
	}

	public String getKpiCode() {
		return kpiCode;
	}

	public void setKpiCode(String kpiCode) {
		this.kpiCode = kpiCode;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public Integer getChartId() {
		return chartId;
	}

	public void setChartId(Integer chartId) {
		this.chartId = chartId;
	}

	public Integer getUnitId() {
		return unitId;
	}

	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}

	public String getKpiName() {
		return kpiName;
	}

	public void setKpiName(String kpiName) {
		this.kpiName = kpiName;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getKpiRestrict() {
		return kpiRestrict;
	}

	public void setKpiRestrict(String kpiRestrict) {
		this.kpiRestrict = kpiRestrict;
	}

	public String getRestrictTime() {
		return restrictTime;
	}

	public void setRestrictTime(String restrictTime) {
		this.restrictTime = restrictTime;
	}

	public String getRelateTable() {
		return relateTable;
	}

	public void setRelateTable(String relateTable) {
		this.relateTable = relateTable;
	}

	public String getRelateTableDes() {
		return relateTableDes;
	}

	public void setRelateTableDes(String relateTableDes) {
		this.relateTableDes = relateTableDes;
	}

	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getGoalType() {
		return goalType;
	}

	public void setGoalType(Integer goalType) {
		this.goalType = goalType;
	}

	public Integer getLevelType1() {
		return levelType1;
	}

	public void setLevelType1(Integer levelType1) {
		this.levelType1 = levelType1;
	}

	public Integer getLevelType2() {
		return levelType2;
	}

	public void setLevelType2(Integer levelType2) {
		this.levelType2 = levelType2;
	}

	public Integer getIsShowDesc() {
		return isShowDesc;
	}

	public void setIsShowDesc(Integer isShowDesc) {
		this.isShowDesc = isShowDesc;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getKpiNameShow() {
		return kpiNameShow;
	}

	public void setKpiNameShow(String kpiNameShow) {
		this.kpiNameShow = kpiNameShow;
	}

	public Integer getSizeId() {
		return sizeId;
	}

	public void setSizeId(Integer sizeId) {
		this.sizeId = sizeId;
	}

	public Integer getDayOffSet() {
		return dayOffSet;
	}

	public void setDayOffSet(Integer dayOffSet) {
		this.dayOffSet = dayOffSet;
	}

	public DwmisKpiInfo getDwmisKpiInfo() {
		return dwmisKpiInfo;
	}

	public void setDwmisKpiInfo(DwmisKpiInfo dwmisKpiInfo) {
		this.dwmisKpiInfo = dwmisKpiInfo;
	}

	public static String sizeName(int sizeId) {
		String sizeName = null;
		if (sizeId == 6001) {
			sizeName = "个";
		}
		if (sizeId == 6002) {
			sizeName = "十";
		}
		if (sizeId == 6003) {
			sizeName = "百";
		}
		if (sizeId == 6004) {
			sizeName = "千";
		}
		if (sizeId == 6005) {
			sizeName = "万";
		}
		if (sizeId == 6006) {
			sizeName = "十万";
		}
		if (sizeId == 6007) {
			sizeName = "百万";
		}
		if (sizeId == 6008) {
			sizeName = "千万";
		}
		if (sizeId == 6009) {
			sizeName = "亿";
		}
		if (sizeId == 6010) {
			sizeName = "十亿";
		}
		if (sizeId == 6011) {
			sizeName = "百亿";
		}
		return sizeName;
	}

	public Integer getDecimalNum() {

		return decimalNum == null ? 0 : decimalNum;
	}

	public void setDecimalNum(Integer decimalNum) {
		this.decimalNum = decimalNum;
	}

}
