package com.infosmart.portal.pojo;

import java.math.BigDecimal;

public class CrossUser {
	
	private String relProductId;
	
	private String relProductName;
	
	private long crossUserCnt;
	
	private BigDecimal crossUserRate;

	public BigDecimal getCrossUserRate() {
        return crossUserRate;
    }

    public void setCrossUserRate(BigDecimal crossUserRate) {
        this.crossUserRate = crossUserRate;
    }

    public String getRelProductId() {
		return relProductId;
	}

	public void setRelProductId(String relProductId) {
		this.relProductId = relProductId;
	}

	public String getRelProductName() {
		return relProductName;
	}

	public void setRelProductName(String relProductName) {
		this.relProductName = relProductName;
	}

	public long getCrossUserCnt() {
		return crossUserCnt;
	}

	public void setCrossUserCnt(long crossUserCnt) {
		this.crossUserCnt = crossUserCnt;
	}


}
