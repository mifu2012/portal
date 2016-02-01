package com.infosmart.portal.pojo;

import java.util.Date;

import com.infosmart.portal.util.StringUtils;

/**
 * 系统栏目信息
 * 
 * @author infosmart
 * 
 */
public class DwpasCSystemMenu {
	// id
	private String menuId;
	// 父级ID
	private String menuPid;
	// 关联的模块D
	private int templateId;
	// 菜单编码
	private String menuCode;
	// 菜单名称
	private String menuName;
	// 菜单类型：报表，经纬仪，嘹望塔
	private String menuType;
	// 菜单URL
	private String menuUrl;
	// 是否显示
	private int isShow = 1;
	// 排序
	private int menuOrder = 1;
	// 备注
	private String remark;
	// 创建时间
	private Date createTime;

	// 日期类型
	private String dateType;

	/**
	 * 日
	 */
	public final static int DATE_OF_DATE_TYPE = 0;

	/**
	 * 月
	 */
	public final static int MONTH_OF_DATE_TYPE = 1;

	/**
	 * 日和月
	 */
	public final static int DATE_AND_MONTH_OF_DATE_TYPE = 2;

	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	/**
	 * 父级菜单信息
	 */
	private DwpasCSystemMenu systemParentMenu;

	public DwpasCSystemMenu getSystemParentMenu() {
		return systemParentMenu;
	}

	public void setSystemParentMenu(DwpasCSystemMenu systemParentMenu) {
		this.systemParentMenu = systemParentMenu;
	}

	public int getMenuOrder() {
		return menuOrder;
	}

	public void setMenuOrder(int menuOrder) {
		this.menuOrder = menuOrder;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuPid() {
		return menuPid;
	}

	public void setMenuPid(String menuPid) {
		this.menuPid = menuPid;
	}

	public int getTemplateId() {
		return templateId;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuUrl() {
		if (StringUtils.notNullAndSpace(menuUrl)) {
			if (this.menuUrl.indexOf("?") == -1) {
				this.menuUrl += "?1=1";
			}
		}
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public int getIsShow() {
		return isShow;
	}

	public void setIsShow(int isShow) {
		this.isShow = isShow;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

}
