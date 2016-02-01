/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2011 All Rights Reserved.
 */
package com.infosmart.portal.pojo;

import java.util.List;
import com.infosmart.portal.util.DateUtils;

/**
 * 
 * @author
 * @version
 */
public class ProkpiAnalyze {

	private String date;

	private String dateDesc;

	private List<DwpasStKpiData> dataList;

	private List<String> kpiNames;

	private List<Integer> isPercent;

	public List<String> getKpiNames() {
		return kpiNames;
	}

	public String getDateDesc() {
		if (date != null && date.length() > 0) {
			if (date.length() == 6) {
				this.dateDesc = date.substring(0, 4) + "-"
						+ date.substring(4, 6);
			} else if (date.length() == 8) {
				this.dateDesc = date.substring(0, 4) + "-"
						+ date.substring(4, 6) +"-"+ date.substring(6, 8);
			}
		}
		return dateDesc;
	}

	public void setKpiNames(List<String> kpiNames) {
		this.kpiNames = kpiNames;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<Integer> getIsPercent() {
		return isPercent;
	}

	public void setIsPercent(List<Integer> isPercent) {
		this.isPercent = isPercent;
	}

	public List<DwpasStKpiData> getDataList() {
		return dataList;
	}

	public void setDataList(List<DwpasStKpiData> dataList) {
		this.dataList = dataList;
	}

}
