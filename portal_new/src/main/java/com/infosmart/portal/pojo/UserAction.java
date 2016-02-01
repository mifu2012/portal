package com.infosmart.portal.pojo;

import java.math.BigDecimal;

public class UserAction {
private String relActionCode;
	
	private String relActionName;
	
	private long crossUserCnt;
	
	private BigDecimal crossUserRate;

	public String getRelActionCode() {
		return relActionCode;
	}

	public void setRelActionCode(String relActionCode) {
		this.relActionCode = relActionCode;
	}

	public String getRelActionName() {
		return relActionName;
	}

	public void setRelActionName(String relActionName) {
		this.relActionName = relActionName;
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

}
