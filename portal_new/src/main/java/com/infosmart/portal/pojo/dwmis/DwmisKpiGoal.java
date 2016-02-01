package com.infosmart.portal.pojo.dwmis;

import java.math.BigDecimal;
import java.util.List;

/**
 * KPI绩效分解表
 * 
 * @author infosmart
 * 
 */
public class DwmisKpiGoal {

	private String kpiCode;
	private String goalDate;
	private BigDecimal scroll35;
	private BigDecimal scroll50;
	private BigDecimal lastYearKpi;

	private List<String> goalDateList;

	/** 3.5分值 */
	private Double score35;

	/** 5分值 */
	private Double score5;

	public Double getScore35() {
		this.score35 = scroll35 == null ? 0 : scroll35.doubleValue();
		return score35;
	}

	public void setScore35(Double score35) {
		this.score35 = score35;
	}

	public Double getScore5() {
		this.score5 = scroll50 == null ? 0 : scroll50.doubleValue();
		return score5;
	}

	public void setScore5(Double score5) {
		this.score5 = score5;
	}

	public List<String> getGoalDateList() {
		return goalDateList;
	}

	public void setGoalDateList(List<String> goalDateList) {
		this.goalDateList = goalDateList;
	}

	public BigDecimal getLastYearKpi() {
		return lastYearKpi;
	}

	public void setLastYearKpi(BigDecimal lastYearKpi) {
		this.lastYearKpi = lastYearKpi;
	}

	public String getKpiCode() {
		return kpiCode;
	}

	public void setKpiCode(String kpiCode) {
		this.kpiCode = kpiCode;
	}

	public String getGoalDate() {
		return goalDate;
	}

	public void setGoalDate(String goalDate) {
		this.goalDate = goalDate;
	}

	public BigDecimal getScroll35() {
		return scroll35;
	}

	public void setScroll35(BigDecimal scroll35) {
		this.scroll35 = scroll35;
	}

	public BigDecimal getScroll50() {
		return scroll50;
	}

	public void setScroll50(BigDecimal scroll50) {
		this.scroll50 = scroll50;
	}

}
