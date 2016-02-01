package com.infosmart.po.dwmis;

import java.util.List;

/**
 * 绩效管理
 * 
 * @author hgt
 * 
 */
public class DwmisKpiGoal {
	/** 指标code */
	private String kpiCode;

	/** 绩效目标时间粒度 */
	private String goalDate;

	/** 3.5分值 */
	private Double score35;

	/** 5分值 */
	private Double score5;

	/** 去年KPI的值 */
	private Double lastYearKPI;
	/**
	 * 月份
	 */
	private String month;

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	/** 绩效目标时间粒度列表 */
	private List<String> goalDateList;

	public Double getLastYearKPI() {
		return lastYearKPI;
	}

	public void setLastYearKPI(Double lastYearKPI) {
		this.lastYearKPI = lastYearKPI;
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

	public Double getScore35() {
		return score35;
	}

	public void setScore35(Double score35) {
		this.score35 = score35;
	}

	public Double getScore5() {
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

}
