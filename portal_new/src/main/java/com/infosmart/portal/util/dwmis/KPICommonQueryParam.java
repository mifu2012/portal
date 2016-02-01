package com.infosmart.portal.util.dwmis;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.infosmart.portal.util.DateUtils;

/**
 * 
 * 公用查询参数对象（查询条件可以在里面加）
 * 
 * @author wb-luoyang
 * @version $Id: KPIDataQueryParam.java, v 0.1 2011-3-18 上午10:03:04 wb-luoyang
 *          Exp $
 */
public class KPICommonQueryParam {
	// 指标的查询参数
	/** 指标code */
	private String kpiCode;
	/** 多个指标code **/
	private List<String> kpiCodeList = new ArrayList<String>();
	/** 时间粒度 */
	private Integer dateType;
	/** 统计方式 */
	private Integer staCode;
	/** 用于参照的当年第一天 */
	private String currentDate;
	/** 业务类型 */
	private List<String> businessTypes;

	// 本系统自定义的时钟源，参见 TimeFormatProcessor.getSysDate()
	private Date mySysDate;

	private int yearOfmySysDate;

	private int yearMonthOfmySysDate;

	private String goalDate;// 绩效值

	// 大事记的查询参数
	/** 大事记类型编号 */
	private int eventType;
	/** 大事记搜索字符串：事记标题或事记内容 */
	private String str;

	// 维度查询参数
	private int dmnsnId;

	// 统计KPI_DATA空字段中查询对应的KPI_INFO信息所用到的KPI_CODE参数集
	private List<String> kpiCodes;

	// 部门查询参数
	/** 部门ID */
	private int depId;

	// 分页查询参数
	private int startRow;
	private int endRow;

	/** 跨年方案 **/
	// 被观察年份
	private int fixedYear;

	public List<String> getKpiCodeList() {
		return kpiCodeList;
	}

	public void setKpiCodeList(String kpiCode) {
		this.kpiCodeList.add(kpiCode);
	}

	public void setKpiCodeList(List<String> kpiCodeList) {
		this.kpiCodeList = kpiCodeList;
	}

	public int getYearOfmySysDate() {
		if (this.getMySysDate() != null) {
			yearOfmySysDate = Integer.parseInt(DateUtils.formatUtilDate(
					DateUtils.getPreviousYear(this.getMySysDate()), "yyyy"));
		} else {
			yearOfmySysDate = Integer.parseInt(DateUtils.formatUtilDate(
					DateUtils.getPreviousYear(new Date()), "yyyy"));
		}
		return yearOfmySysDate;
	}

	public int getYearMonthOfmySysDate() {
		if (this.getMySysDate() != null) {
			yearMonthOfmySysDate = Integer.parseInt(DateUtils.formatUtilDate(
					DateUtils.getPreviousMonth(this.getMySysDate()), "yyyyMM"));
		} else {
			yearMonthOfmySysDate = Integer.parseInt(DateUtils.formatUtilDate(
					DateUtils.getPreviousMonth(new Date()), "yyyyMM"));
		}
		return yearMonthOfmySysDate;
	}

	public List<String> getKpiCodes() {
		return kpiCodes;
	}

	public void setKpiCodes(List<String> kpiCodes) {
		this.kpiCodes = kpiCodes;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getDmnsnId() {
		return dmnsnId;
	}

	public void setDmnsnId(int dmnsnId) {
		this.dmnsnId = dmnsnId;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	public String getKpiCode() {
		return kpiCode;
	}

	public void setKpiCode(String kpiCode) {
		this.kpiCode = kpiCode;
	}

	public Integer getDateType() {
		return dateType;
	}

	public void setDateType(Integer dateType) {
		this.dateType = dateType;
	}

	public Integer getStaCode() {
		return staCode;
	}

	public void setStaCode(Integer staCode) {
		this.staCode = staCode;
	}

	public String getCurrentDate() {
		if (this.getMySysDate() != null) {
			currentDate = DateUtils
					.formatUtilDate(
							DateUtils.getPreviousMonth(this.getMySysDate()),
							"yyyyMMdd");
		} else {
			currentDate = DateUtils.formatUtilDate(
					DateUtils.getPreviousMonth(new Date()), "yyyyMMdd");
		}

		return currentDate;
	}

	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}

	public int getEventType() {
		return eventType;
	}

	public void setEventType(int eventType) {
		this.eventType = eventType;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	/**
	 * @param mySysDate
	 *            the mySysDate to set
	 */
	public void setMySysDate(Date mySysDate) {
		this.mySysDate = mySysDate;
	}

	/**
	 * @return the mySysDate
	 */
	public Date getMySysDate() {
		return mySysDate;
	}

	public int getDepId() {
		return depId;
	}

	public void setDepId(int depId) {
		this.depId = depId;
	}

	public List<String> getBusinessTypes() {
		return businessTypes;
	}

	public void setBusinessTypes(List<String> businessTypes) {
		this.businessTypes = businessTypes;
	}

	/**
	 * Getter method for property <tt>fixedYear</tt>.
	 * 
	 * @return property value of fixedYear
	 */
	public int getFixedYear() {

		if (this.getMySysDate() != null) {
			fixedYear = Integer.parseInt(DateUtils.formatUtilDate(
					DateUtils.getPreviousMonth(this.getMySysDate()), "yyyy"));
		} else {
			fixedYear = Integer.parseInt(DateUtils.formatUtilDate(
					DateUtils.getPreviousMonth(new Date()), "yyyy"));
		}
		return fixedYear;
	}

	/**
	 * Setter method for property <tt>fixedYear</tt>.
	 * 
	 * @param fixedYear
	 *            value to be assigned to property fixedYear
	 */
	public void setFixedYear(int fixedYear) {
		this.fixedYear = fixedYear;
	}

	public String getGoalDate() {
		return goalDate;
	}

	public void setGoalDate(String goalDate) {
		this.goalDate = goalDate;
	}

}
