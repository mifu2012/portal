package com.infosmart.portal.vo.dwpas;


public class PrdCrossParam {
	private String prdId; // 产品id
	private String relPrdId; // 关联产品id
	private PrdCrossDataTypeEnum prdCrossDataTypeEnum; // 产品交叉数据类型
	private String endDate; // 查询结束时间

	public String getPrdId() {
		return prdId;
	}

	public void setPrdId(String prdId) {
		this.prdId = prdId;
	}

	public String getRelPrdId() {
		return relPrdId;
	}

	public void setRelPrdId(String relPrdId) {
		this.relPrdId = relPrdId;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public PrdCrossDataTypeEnum getPrdCrossDataTypeEnum() {
		return prdCrossDataTypeEnum;
	}

	public void setPrdCrossDataTypeEnum(
			PrdCrossDataTypeEnum prdCrossDataTypeEnum) {
		this.prdCrossDataTypeEnum = prdCrossDataTypeEnum;
	}
}
