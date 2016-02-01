package com.infosmart.portal.pojo.dwmis;

public class MisEventPo {
	private String kpiCode;
	/** 大事记ID */
	private String eventId;
	/** 事记起始时间 */
	private String eventStartDate;
	/** 事记结束时间 */
	private String eventEndDate;
	/** 事记类别ID */
	private String eventType;
	/** 事记类别名称 */
	private String eventTypeName;
	/** 标题 */
	private String title;
	/** 内容 */
	private String content;
	/** 公开与否 */
	private int isPublic;
	/** 创建者 */
	private String createUser;
	/** 创建时间 */
	private String createDate;

	/**
	 * 事件时间
	 */
	private String eventDate;

	/** 分页 */
	private Page page;

	private String eventColor;

	public String getEventColor() {
		return eventColor;
	}

	public void setEventColor(String eventColor) {
		this.eventColor = eventColor;
	}

	public String getKpiCode() {
		return kpiCode;
	}

	public void setKpiCode(String kpiCode) {
		this.kpiCode = kpiCode;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getEventStartDate() {
		return eventStartDate;
	}

	public void setEventStartDate(String eventStartDate) {
		this.eventStartDate = eventStartDate;
	}

	public String getEventEndDate() {
		return eventEndDate;
	}

	public void setEventEndDate(String eventEndDate) {
		this.eventEndDate = eventEndDate;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
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

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getEventTypeName() {
		return eventTypeName;
	}

	public void setEventTypeName(String eventTypeName) {
		this.eventTypeName = eventTypeName;
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

	public String getEventDate() {
		return eventDate;
	}

	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}
}
