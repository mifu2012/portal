package com.infosmart.portal.pojo;

/**
 * 用户行为信息
 * @author Administrator
 *
 */
public class DwpasCUserActionInfo {
	private String actionCode;
	private String actionName;
	private Integer isShow;
	private Integer isDefault;
	private Integer showSort;
	
	
	public String getActionCode() {
		return actionCode;
	}
	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	public Integer getIsShow() {
		return isShow;
	}
	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}
	public Integer getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}
	public Integer getShowSort() {
		return showSort;
	}
	public void setShowSort(Integer showSort) {
		this.showSort = showSort;
	}

}
