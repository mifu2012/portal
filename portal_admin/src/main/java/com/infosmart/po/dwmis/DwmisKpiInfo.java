package com.infosmart.po.dwmis;

import java.util.Date;

import com.infosmart.po.Page;

/**
 * KPI_INFO表
 * 
 * @author hgt
 * 
 */
public class DwmisKpiInfo {
	private String kpiCode;
	private Integer typeId;
	private Integer chartId;
	private Integer unitId;
	private String kpiName;
	private String detail;
	private String kpiRestrict;
	private String restrictTime;
	private String relateTable;
	private String relateTableDes;
	private Integer isShow;
	private String createUser;
	private Date createDate;
	private Integer goalType;
	private Integer levelType1;
	private Integer levelType2;
	private Integer isShowDesc;
	private String period;
	private String kpiNameShow;
	private Integer sizeId;

	// 绩效类别名称
	private String goalTypeDesc;

	public String getGoalTypeDesc() {
		return goalTypeDesc;
	}

	public void setGoalTypeDesc(String goalTypeDesc) {
		this.goalTypeDesc = goalTypeDesc;
	}

	// 部门Id
	private String depId;
	// 图例Id
	private String legendId;

	public String getLegendId() {
		return legendId;
	}

	public void setLegendId(String legendId) {
		this.legendId = legendId;
	}

	private Page page;

	public String getDepId() {
		return depId;
	}

	public void setDepId(String depId) {
		this.depId = depId;
	}

	public Page getPage() {
		if (page == null)
			page = new Page();
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
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

	public Integer getShowOrder() {
		return showOrder;
	}

	public void setShowOrder(Integer showOrder) {
		this.showOrder = showOrder;
	}

	public Integer getDayOffset() {
		return dayOffset;
	}

	public void setDayOffset(Integer dayOffset) {
		this.dayOffset = dayOffset;
	}

	public String getKpiNameKpi() {
		return kpiNameKpi;
	}

	public void setKpiNameKpi(String kpiNameKpi) {
		this.kpiNameKpi = kpiNameKpi;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public Integer getIsDataRangeFix() {
		return isDataRangeFix;
	}

	public void setIsDataRangeFix(Integer isDataRangeFix) {
		this.isDataRangeFix = isDataRangeFix;
	}

	public Integer getDataRangeTop() {
		return dataRangeTop;
	}

	public void setDataRangeTop(Integer dataRangeTop) {
		this.dataRangeTop = dataRangeTop;
	}

	public Integer getDataRangeBottom() {
		return dataRangeBottom;
	}

	public void setDataRangeBottom(Integer dataRangeBottom) {
		this.dataRangeBottom = dataRangeBottom;
	}

	private Integer showOrder;
	private Integer dayOffset;
	private String kpiNameKpi;
	private String businessType;
	private Integer isDataRangeFix;
	private Integer dataRangeTop;
	private Integer dataRangeBottom;

}
