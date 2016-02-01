package com.infosmart.po.dwmis;

import java.util.Date;
import java.util.List;

import com.infosmart.po.Page;
import com.infosmart.util.dwmis.CoreConstant;

public class DwmisLegendInfo {
	private String legendId;
	private String legendName;
	// 所属分类
	private String categoryId;
	private DwmisLegendCategory dwmisLegendCategory;

	/**
	 * 图例关联的指标
	 */
	private List<String> relateKpiCodeList;
	// 创建时间
	private Date gmtCreate;
	// 图类型 -１：未知　０：趋势图　１：饼图　２：矩形图　３：折线　４：矩形图+折线　11：新增URL　
	private int chartType;
	// 图类型描述，主要用于矩形，折线，矩形+折线
	private String chartTypeDesc;
	private String chartName;
	private String chartUrl;
	// 显示预警线，对趋势图或折线图有效 0：不显示 1:显示
	private int showWarnLine;
	// 统计方式，当期值2001、期末值2002、峰值2003、谷值2004、周日平均2005、某月四周日平均2006
	private int statCode;
	private String statCodeDesc;
	private String remark;
	private Page page;
	private String kpiCodes;
	private String moduleId;

	public final static int STOCK_CHART = 0; // ０：趋势图
	public final static int PIE_CHART = 1; // １：饼图
	public final static int COLUMN_CHART = 2; // ２：矩形图
	public final static int LINE_CHART = 3; // ３：折线
	public final static int COLUMNORLINE_CHART = 4; // ４：矩形图+折线
	public final static int AREA_CHART = 5; // 5 ：面积图

	public List<String> getRelateKpiCodeList() {
		return relateKpiCodeList;
	}

	public void setRelateKpiCodeList(List<String> relateKpiCodeList) {
		this.relateKpiCodeList = relateKpiCodeList;
	}

	public String getChartName() {
		switch (chartType) {
		case CoreConstant.CHART_NONE: {
			chartName = "未知图";
			break;
		}
		case CoreConstant.CHART_TREND: {
			chartName = "趋势图";
			break;
		}
		case CoreConstant.CHART_PIE: {
			chartName = "饼图";
			break;
		}
		case CoreConstant.CHART_RECTANGULAR: {
			chartName = "矩形图";
			break;
		}
		case CoreConstant.CHART_DISCOUNT: {
			chartName = "折线图";
			break;
		}
		case CoreConstant.CHART_AREA: {
			chartName = "面积图";
			break;
		}
		case CoreConstant.CHART_RECTANGULAR_DISCOUNT: {
			chartName = "矩形+折线图";
			break;
		}
		case CoreConstant.CHART_NEW_URL: {
			chartName = "新增URL";
			break;
		}
		}
		return chartName;
	}

	public void setChartName(String chartName) {
		this.chartName = chartName;
	}

	public String getStatCodeDesc() {
		switch (statCode) {
		case CoreConstant.STA_CURRENT: {
			statCodeDesc = "当期值";
			break;
		}
		case CoreConstant.STA_FINAL: {
			statCodeDesc = "期末值";
			break;
		}
		case CoreConstant.STA_PEAK: {
			statCodeDesc = "峰值";
			break;
		}
		case CoreConstant.STA_VALLEY: {
			statCodeDesc = "谷值";
			break;
		}
		case CoreConstant.STA_SUNDAY_AVERAGE: {
			statCodeDesc = "周日平均";
			break;
		}
		case CoreConstant.STA_SEVEN_DAY_AVERAGE: {
			statCodeDesc = "七日平均";
			break;
		}
		}
		return statCodeDesc;
	}

	public void setStatCodeDesc(String statCodeDesc) {
		this.statCodeDesc = statCodeDesc;
	}

	public String getKpiCodes() {
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

}
