package com.infosmart.portal.pojo;

import java.util.List;

/**
 * 
 * 大事记PO
 * 
 */
public class BigEventPo {
	/* 关联的指标 */
	private String kpiCode;
	/** 大事记ID */
	private String eventId;
	/** 事记起始时间 */
	private String eventStartDate;
	private String eventDate;
	/** 事记结束时间 */
	private String eventEndDate;
	/** 事记类别 */
	private int eventType;
	/** 标题 */
	private String title;
	/** 首页显示的标题 */
	private String titleShow;
	/** 内容 */
	private String content;
	/** 附件 */
	private String attach;
	/** 公开与否 */
	private int isPublic;
	/** 创建者 */
	private String createUser;
	/** 创建时间 */
	private String createDate;

	private String eventTypeName;

	private String actionType;

	private Page page;
	// zy开始
	/**
	 * 大事件名称
	 * */
	private String eventName;

	/**
	 * 大事件的id
	 */
	private int eventID;

	private String eventColor;

	public String getEventColor() {
		return eventColor;
	}

	public void setEventColor(String eventColor) {
		this.eventColor = eventColor;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public int getEventID() {
		return eventID;
	}

	public void setEventID(int eventID) {
		this.eventID = eventID;
	}

	// zy结束

	// 关联的KPI指标列表
	private List<DwpasCKpiInfo> kpiInfoList;

	private String relateKpiCodes;

	public String getTitleShow() {
		if (this.title != null && this.title.length() > 18) {
			titleShow = title.substring(0, 18) + "...";
		} else {
			titleShow = this.title;
		}
		return titleShow;
	}

	public String getRelateKpiCodes() {
		StringBuffer kpiCodes = new StringBuffer();
		if (kpiInfoList != null) {
			for (DwpasCKpiInfo kpiInfo : kpiInfoList) {
				if (kpiInfo == null)
					continue;
				if (kpiCodes.length() > 0) {
					kpiCodes.append(";");
				}
				kpiCodes.append(kpiInfo.getKpiCode());
			}
		}
		relateKpiCodes = kpiCodes.toString();
		return relateKpiCodes;
	}

	public List<DwpasCKpiInfo> getKpiInfoList() {
		return kpiInfoList;
	}

	public void setKpiInfoList(List<DwpasCKpiInfo> kpiInfoList) {
		this.kpiInfoList = kpiInfoList;
	}

	public String getKpiCode() {
		return kpiCode;
	}

	public void setKpiCode(String kpiCode) {
		this.kpiCode = kpiCode;
	}

	public String getEventDate() {
		return eventDate;
	}

	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public void setEventStartDate(String string) {
		this.eventStartDate = string;
	}

	public int getEventType() {
		return eventType;
	}

	public void setEventType(int eventType) {
		this.eventType = eventType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public int getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(int isPublic) {
		this.isPublic = isPublic;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getEventEndDate() {
		return eventEndDate;
	}

	public void setEventEndDate(String eventEndDate) {
		this.eventEndDate = eventEndDate;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getEventStartDate() {
		return eventStartDate;
	}

	public String getEventTypeName() {
		return eventTypeName;
	}

	public void setEventTypeName(String eventTypeName) {
		this.eventTypeName = eventTypeName;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public Page getPage() {
		if (page == null) {
			page = new Page();
		}

		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

}
