package com.infosmart.portal.pojo;


/**
 * 大事记与KPI关联表  对应表:mis_kpi_event_r
 * 
 * @author Administrator
 * 
 */
public class DwpasMisKpiEventRel {

	/** 大事记ID */
	private String eventId;

	/**
	 * 指标编码
	 */
	private String kpiCode;

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getKpiCode() {
		return kpiCode;
	}

	public void setKpiCode(String kpiCode) {
		this.kpiCode = kpiCode;
	}

}
