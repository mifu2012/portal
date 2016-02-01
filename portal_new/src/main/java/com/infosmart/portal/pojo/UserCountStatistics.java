package com.infosmart.portal.pojo;

import java.math.BigDecimal;

import com.infosmart.portal.vo.Constants;

public class UserCountStatistics {

	private String     kpiName;
	private BigDecimal fromTaobao;
	private BigDecimal fromOutter;
	private BigDecimal fromInner;
	private BigDecimal fromOther;
	
	private String fromTaobaoModule;
	private String fromOutterModule;
	private String fromInnerModule;
	private String fromOtherModule;
	
	

	
	
	public String getKpiName() {
		return kpiName;
	}
	public void setKpiName(String kpiName) {
		this.kpiName = kpiName;
	}
	public BigDecimal getFromTaobao() {
		return fromTaobao;
	}
	public void setFromTaobao(BigDecimal fromTaobao) {
		this.fromTaobao = fromTaobao;
	}
	public BigDecimal getFromOutter() {
		return fromOutter;
	}
	public void setFromOutter(BigDecimal fromOutter) {
		this.fromOutter = fromOutter;
	}
	public BigDecimal getFromInner() {
		return fromInner;
	}
	public void setFromInner(BigDecimal fromInner) {
		this.fromInner = fromInner;
	}
	public BigDecimal getFromOther() {
		return fromOther;
	}
	public void setFromOther(BigDecimal fromOther) {
		this.fromOther = fromOther;
	}
	public String getFromTaobaoModule() {
		return fromTaobaoModule;
	}
	public void setFromTaobaoModule(String fromTaobaoModule) {
		this.fromTaobaoModule = fromTaobaoModule;
	}
	public String getFromOutterModule() {
		return fromOutterModule;
	}
	public void setFromOutterModule(String fromOutterModule) {
		this.fromOutterModule = fromOutterModule;
	}
	public String getFromInnerModule() {
		return fromInnerModule;
	}
	public void setFromInnerModule(String fromInnerModule) {
		this.fromInnerModule = fromInnerModule;
	}
	public String getFromOtherModule() {
		return fromOtherModule;
	}
	public void setFromOtherModule(String fromOtherModule) {
		this.fromOtherModule = fromOtherModule;
	}
	
	public BigDecimal getTotalValue() {
	    fromInner= fromInner == null?Constants.ZERO:fromInner;
	    fromOther= fromOther == null?Constants.ZERO:fromOther;
	    fromOutter= fromOutter == null?Constants.ZERO:fromOutter;
	    fromTaobao= fromTaobao == null?Constants.ZERO:fromTaobao;
		return this.fromInner.add(this.fromOther).add(this.fromOutter).add(this.fromTaobao);
	}
}
