package com.infosmart.portal.pojo;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 用户行为数据信息
 * @author Administrator
 *
 */
public class DwpasStUserActionCrossAnalyseData {
	private String actionCode;
	private String reportDate;
	private String relActionCode;
	private long crossUserCnt;
	private BigDecimal crossUserRate;
	private Date gmtCreate;
	private Date gmtModified;
	public String getActionCode() {
		return actionCode;
	}
	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}
	public String getReportDate() {
		return reportDate;
	}
	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}
	public String getRelActionCode() {
		return relActionCode;
	}
	public void setRelActionCode(String relActionCode) {
		this.relActionCode = relActionCode;
	}
	public long getCrossUserCnt() {
		return crossUserCnt;
	}
	public void setCrossUserCnt(long crossUserCnt) {
		this.crossUserCnt = crossUserCnt;
	}
	public BigDecimal getCrossUserRate() {
		return crossUserRate;
	}
	public void setCrossUserRate(BigDecimal crossUserRate) {
		this.crossUserRate = crossUserRate;
	}
	public Date getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	public Date getGmtModified() {
		return gmtModified;
	}
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

}
