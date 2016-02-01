package com.infosmart.portal.vo.dwmis;

import java.util.Date;

public class ChartEvent {
	// 事件日期
	public Date date;

	// 事件内容
	public String event;

	// 事件数据id
	public int eventID;

	// 事件公开与否
	public int isPublic;

	// 事件的原始时间（当用户选择“周”、“月”粒度的时候，会把事件
	// 日期处理成当周的周日或者当月的月末最后一天）
	// 该属性时候为了保留事件的真实日期，以备后期适用
	public Date originalDate;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public int getEventID() {
		return eventID;
	}

	public void setEventID(int eventID) {
		this.eventID = eventID;
	}

	public int getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(int isPublic) {
		this.isPublic = isPublic;
	}

	public Date getOriginalDate() {
		return originalDate;
	}

	public void setOriginalDate(Date originalDate) {
		this.originalDate = originalDate;
	}
}
