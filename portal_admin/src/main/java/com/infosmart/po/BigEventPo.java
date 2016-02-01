package com.infosmart.po;


/**
 * 
 * 大事记PO
 * 
 */
public class BigEventPo {
	/** 大事记ID */
	private String eventId;
	/** 事记起始时间 */
	private String eventStartDate;
	/** 事记结束时间 */
	private String eventEndDate;
	/** 事记类别 */
	private int eventType;
	/** 标题 */
	private String title;
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
		if (page == null){
			page = new Page();
		}
			
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

}
