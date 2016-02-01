package com.infosmart.portal.pojo;

import java.util.Date;
import java.util.List;

import com.infosmart.portal.util.StringUtils;

public class DwpasCModuleInfo {
	private String moduleId;
	private String menuId;
	private String moduleName;
	private String dateType;
	private Integer isShow;
	private String remark;
	private Date gmtCreate;
	private String moduleCode;
	private String menuName;
	private List<DwpasCColumnInfo> columnlist;
	// 下面是新增的属性
	private int kpiType = DwpasCKpiInfo.KPI_TYPE_OF_DAY;
	// 0：顶部 1：左边 2：右边 3：底部
	private int position = DwpasCModuleInfo.POSITION_BOTTOM;
	// 顶部
	public final static int POSITION_TOP = 0;
	// 左边
	public final static int POSITION_LEFT = 1;
	// 右边
	public final static int POSITION_RIGHT = 2;
	// 底部
	public final static int POSITION_BOTTOM = 3;

	private int tabShow = 0; // 显示方式 0 为tab切换 1 为并排显示

	// X坐标
	private int positionX;
	// y坐标
	private int positionY;

	// 宽度
	private int width;

	// 高度
	private int height;

	// 模块类型
	private int moduleType = DwpasCModuleInfo.MODULE_TYPE_SYSTEM;// 0：系统默认，1：用户新增

	// 系统模块
	public final static int MODULE_TYPE_SYSTEM = 0;
	// 自定义图表模块
	public final static int MODULE_TYPE_SELF = 1;
	// 自定义报表模块
	public final static int MODULE_TYPE_REPORT = 2;

	private int moduleSort = 0; // 排序

	public int getKpiType() {
		if (StringUtils.notNullAndSpace(dateType)) {
			if ("D".equalsIgnoreCase(dateType)) {
				return DwpasCKpiInfo.KPI_TYPE_OF_DAY;
			} else {
				return DwpasCKpiInfo.KPI_TYPE_OF_MONTH;
			}
		}
		return kpiType;
	}

	public void setKpiType(int kpiType) {
		this.kpiType = kpiType;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getPositionX() {
		return positionX;
	}

	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}

	public int getPositionY() {
		return positionY;
	}

	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		// 默认是300
		if (this.height <= 0)
			this.height = 300;
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getModuleType() {
		return moduleType;
	}

	public void setModuleType(int moduleType) {
		this.moduleType = moduleType;
	}

	public int getModuleSort() {
		return moduleSort;
	}

	public void setModuleSort(int moduleSort) {
		this.moduleSort = moduleSort;
	}

	public List<DwpasCColumnInfo> getColumnlist() {
		return columnlist;
	}

	public void setColumnlist(List<DwpasCColumnInfo> columnlist) {
		this.columnlist = columnlist;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public int getTabShow() {
		return tabShow;
	}

	public void setTabShow(int tabShow) {
		this.tabShow = tabShow;
	}
	
}
