package com.infosmart.portal.taglib;

public class DisplayRowInfo {
	private String onClick = "javascript:ClickCheck(this);";

	private String onDblClick = "";

	private String onMouseOver = "javascript:mouseover(this);";

	private String onMouseOut = "javascript:mouseout(this);";

	private String headStyleClass = "";

	private String bodyStyleClass = "";

	public String getOnMouseOver() {
		return this.onMouseOver;
	}

	public String getHeadStyleClass() {
		return this.headStyleClass;
	}

	public String getOnClick() {
		return this.onClick;
	}

	public String getBodyStyleClass() {
		return this.bodyStyleClass;
	}

	public String getOnMouseOut() {
		return this.onMouseOut;
	}

	public void setOnDblClick(String onDblClick) {
		this.onDblClick = onDblClick;
	}

	public void setOnMouseOver(String onMouseOver) {
		this.onMouseOver = onMouseOver;
	}

	public void setHeadStyleClass(String headStyleClass) {
		this.headStyleClass = headStyleClass;
	}

	public void setOnClick(String onClick) {
		this.onClick = onClick;
	}

	public void setBodyStyleClass(String bodyStyleClass) {
		this.bodyStyleClass = bodyStyleClass;
	}

	public void setOnMouseOut(String onMouseOut) {
		this.onMouseOut = onMouseOut;
	}

	public String getOnDblClick() {
		return this.onDblClick;
	}
}