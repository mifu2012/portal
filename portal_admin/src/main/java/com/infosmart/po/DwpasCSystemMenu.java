package com.infosmart.po;

import java.util.Date;
import java.util.List;

import com.infosmart.util.StringUtils;

public class DwpasCSystemMenu {
	private String menuId;
	/**
	 * eg:01,02,0201
	 */
	private String menuCode;
	/**
	 * 01,02
	 */
	private String menuPid;
	/**
	 * 1_01
	 */
	private String menuUrl;
	/**
	 * 模板
	 */
	private DwpasCmoduleInfo[] moduleInfo;
	private Integer templateId;
	private String menuName;
	private Integer isShow;
	private String remark;
	private Date createTime;
	private Integer menuOrder;
	private String[] columnIds;
	private String[] columnNames;
	private String[] displayNames;
	private String[] commCodes;
	private String[] remarks;
	private String[] dateTypes;
	private String[] columnKinds;
	private String[] moduleIds;
	private String[] moduleRemark;
	private String[] moduleNames;
	private String[] userTypes;
	private String[] value1;
	private String[] value2;
	private String[] value3;
	private String[] value4;
	private String[] value5;
	private String[] commCodes1;
	private String[] commCodes2;
	private String[] commCodes3;
	private String[] commCodes4;
	private String[] commCodes5;
	private String[] types1;
	private String[] types2;
	private String[] types3;
	private String[] types4;
	private String[] types5;
	//新增moduleIsShow
	private String[] moduleIsShows;
	
	public String[] getModuleIsShows() {
		return moduleIsShows;
	}

	public void setModuleIsShows(String[] moduleIsShows) {
		this.moduleIsShows = moduleIsShows;
	}

	public String[] getTypes1() {
		return types1;
	}

	public void setTypes1(String[] types1) {
		this.types1 = types1;
	}

	public String[] getTypes2() {
		return types2;
	}

	public void setTypes2(String[] types2) {
		this.types2 = types2;
	}

	public String[] getTypes3() {
		return types3;
	}

	public void setTypes3(String[] types3) {
		this.types3 = types3;
	}

	public String[] getTypes4() {
		return types4;
	}

	public void setTypes4(String[] types4) {
		this.types4 = types4;
	}

	public String[] getTypes5() {
		return types5;
	}

	public void setTypes5(String[] types5) {
		this.types5 = types5;
	}

	public String[] getCommCodes1() {
		return commCodes1;
	}

	public void setCommCodes1(String[] commCodes1) {
		this.commCodes1 = commCodes1;
	}

	public String[] getCommCodes2() {
		return commCodes2;
	}

	public void setCommCodes2(String[] commCodes2) {
		this.commCodes2 = commCodes2;
	}

	public String[] getCommCodes3() {
		return commCodes3;
	}

	public void setCommCodes3(String[] commCodes3) {
		this.commCodes3 = commCodes3;
	}

	public String[] getCommCodes4() {
		return commCodes4;
	}

	public void setCommCodes4(String[] commCodes4) {
		this.commCodes4 = commCodes4;
	}

	public String[] getCommCodes5() {
		return commCodes5;
	}

	public void setCommCodes5(String[] commCodes5) {
		this.commCodes5 = commCodes5;
	}

	public String[] getValue1() {
		return value1;
	}

	public void setValue1(String[] value1) {
		this.value1 = value1;
	}

	public String[] getValue2() {
		return value2;
	}

	public void setValue2(String[] value2) {
		this.value2 = value2;
	}

	public String[] getValue3() {
		return value3;
	}

	public void setValue3(String[] value3) {
		this.value3 = value3;
	}

	public String[] getValue4() {
		return value4;
	}

	public void setValue4(String[] value4) {
		this.value4 = value4;
	}

	public String[] getValue5() {
		return value5;
	}

	public void setValue5(String[] value5) {
		this.value5 = value5;
	}

	private String dateType;
	private String menuType;
	// 新增的
	private List<DwpasCmoduleInfo> moduleInfoList;

	private int kpiType = DwpasCKpiInfo.KPI_TYPE_OF_DAY;

	public int getKpiType() {
		if (StringUtils.notNullAndSpace(dateType)) {
			if (dateType.equalsIgnoreCase("D")) {
				this.kpiType = DwpasCKpiInfo.KPI_TYPE_OF_DAY;
			} else if (dateType.equalsIgnoreCase("W")) {
				this.kpiType = DwpasCKpiInfo.KPI_TYPE_OF_WEEK;
			} else {
				this.kpiType = DwpasCKpiInfo.KPI_TYPE_OF_MONTH;
			}
		}
		return kpiType;
	}

	public List<DwpasCmoduleInfo> getModuleInfoList() {
		return moduleInfoList;
	}

	public void setModuleInfoList(List<DwpasCmoduleInfo> moduleInfoList) {
		this.moduleInfoList = moduleInfoList;
	}

	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public String[] getUserTypes() {
		return userTypes;
	}

	public void setUserTypes(String[] userTypes) {
		this.userTypes = userTypes;
	}

	public String[] getDisplayNames() {
		return displayNames;
	}

	public void setDisplayNames(String[] displayNames) {
		this.displayNames = displayNames;
	}

	public String[] getModuleNames() {
		return moduleNames;
	}

	public void setModuleNames(String[] moduleNames) {
		this.moduleNames = moduleNames;
	}

	public String[] getModuleRemark() {
		return moduleRemark;
	}

	public void setModuleRemark(String[] moduleRemark) {
		this.moduleRemark = moduleRemark;
	}

	public String[] getColumnIds() {
		return columnIds;
	}

	public void setColumnIds(String[] columnIds) {
		this.columnIds = columnIds;
	}

	public String[] getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(String[] columnNames) {
		this.columnNames = columnNames;
	}

	public String[] getCommCodes() {
		return commCodes;
	}

	public void setCommCodes(String[] commCodes) {
		this.commCodes = commCodes;
	}

	public String[] getRemarks() {
		return remarks;
	}

	public void setRemarks(String[] remarks) {
		this.remarks = remarks;
	}

	public String[] getDateTypes() {
		return dateTypes;
	}

	public void setDateTypes(String[] dateTypes) {
		this.dateTypes = dateTypes;
	}

	public String[] getColumnKinds() {
		return columnKinds;
	}

	public void setColumnKinds(String[] columnKinds) {
		this.columnKinds = columnKinds;
	}

	public String[] getModuleIds() {
		return moduleIds;
	}

	public void setModuleIds(String[] moduleIds) {
		this.moduleIds = moduleIds;
	}

	public Integer getMenuOrder() {
		return menuOrder;
	}

	public void setMenuOrder(Integer menuOrder) {
		this.menuOrder = menuOrder;
	}

	private Integer newTemplateId;// 临时字段

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public Integer getNewTemplateId() {
		return newTemplateId;
	}

	public void setNewTemplateId(Integer newTemplateId) {
		this.newTemplateId = newTemplateId;
	}

	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	public String getMenuPid() {
		return menuPid;
	}

	public void setMenuPid(String menuPid) {
		this.menuPid = menuPid;
	}

	/**
	 * 1_01,1_0101
	 * 
	 * @return
	 */
	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public Integer getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public DwpasCmoduleInfo[] getModuleInfo() {
		return moduleInfo;
	}

	public void setModuleInfo(DwpasCmoduleInfo[] moduleInfo) {
		this.moduleInfo = moduleInfo;
	}
}
