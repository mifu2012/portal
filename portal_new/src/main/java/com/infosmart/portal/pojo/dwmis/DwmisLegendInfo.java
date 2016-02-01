package com.infosmart.portal.pojo.dwmis;

import java.util.Date;
import java.util.List;

public class DwmisLegendInfo {
	private String legendId;
	private String legendName;
	// 所属分类
	private String categoryId;
	private DwmisLegendCategory dwmisLegendCategory;
	// 创建时间
	private Date gmtCreate;
	// 图类型 -１：未知　０：趋势图　１：饼图　２：矩形图　３：折线　４：矩形图+折线　11：新增URL　
	private int chartType;
	// 图类型描述，主要用于矩形，折线，矩形+折线
	private String chartTypeDesc;

	private String chartUrl;
	// 显示预警线，对趋势图或折线图有效 0：不显示 1:显示
	private int showWarnLine;
	// 统计方式，当期值2001、期末值2002、峰值2003、谷值2004、周日平均2005、某月四周日平均2006
	private int statCode;
	private String remark;
	private Page page;
	private String kpiCodes;
	private String moduleId;

	public final static int STOCK_CHART = 0;		// ０：趋势图
	public final static int PIE_CHART = 1; 			// １：饼图
	public final static int COLUMN_CHART = 2;		// ２：矩形图
	public final static int LINE_CHART = 3;			// ３：折线
	public final static int COLUMNORLINE_CHART = 4;	// ４：矩形图+折线
	public final static int AREA_CHART = 5;			// 5 ：面积图

	private List<DwmisKpiInfo> kpiInfoList;

	public String getKpiCodes() {
		if (this.kpiInfoList == null || this.kpiInfoList.isEmpty())
			return "";
		StringBuffer multiKpiCode = new StringBuffer("");
		for (DwmisKpiInfo kpiInfo : this.kpiInfoList) {
			if (multiKpiCode.toString().length() > 0)
				multiKpiCode.append(";");
			multiKpiCode.append(kpiInfo.getKpiCode());
		}
		this.kpiCodes = multiKpiCode.toString();
		return kpiCodes;
	}

	public void setKpiCodes(String kpiCodes) {
		this.kpiCodes = kpiCodes;
	}

	public Page getPage() {
		if (page == null)
			page = new Page();
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public DwmisLegendCategory getDwmisLegendCategory() {
		return dwmisLegendCategory;
	}

	public void setDwmisLegendCategory(DwmisLegendCategory dwmisLegendCategory) {
		this.dwmisLegendCategory = dwmisLegendCategory;
	}

	public String getLegendId() {
		return legendId;
	}

	public void setLegendId(String legendId) {
		this.legendId = legendId;
	}

	public String getLegendName() {
		return legendName;
	}

	public void setLegendName(String legendName) {
		this.legendName = legendName;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public int getChartType() {
		return chartType;
	}

	public void setChartType(int chartType) {
		this.chartType = chartType;
	}

	public String getChartTypeDesc() {
		return chartTypeDesc;
	}

	public void setChartTypeDesc(String chartTypeDesc) {
		this.chartTypeDesc = chartTypeDesc;
	}

	public String getChartUrl() {
		return chartUrl;
	}

	public void setChartUrl(String chartUrl) {
		this.chartUrl = chartUrl;
	}

	public int getShowWarnLine() {
		return showWarnLine;
	}

	public void setShowWarnLine(int showWarnLine) {
		this.showWarnLine = showWarnLine;
	}

	public int getStatCode() {
		return statCode;
	}

	public void setStatCode(int statCode) {
		this.statCode = statCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public List<DwmisKpiInfo> getKpiInfoList() {
		return kpiInfoList;
	}

	public void setKpiInfoList(List<DwmisKpiInfo> kpiInfoList) {
		this.kpiInfoList = kpiInfoList;
	}

}
