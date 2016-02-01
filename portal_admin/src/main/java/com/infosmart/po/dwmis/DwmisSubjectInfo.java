package com.infosmart.po.dwmis;

import java.util.List;
import java.util.Arrays;

/**
 * 瞭望塔主题表
 * 
 * @author Administrator
 * 
 */
public class DwmisSubjectInfo {

	// 主题ID
	private String subjectId;
	// 模板ID
	private String templateId;
	// 主题编码
	private String subjectCode;
	// 主题名称
	private String subjectName;
	// 备注
	private String remark;
	// 模块信息
	private DwmisModuleInfo[] moduleInfoArray;
	// 日期类型
	private int dateType; // 1为日,2为周,3为周,4为日周月
	private int isShow;// 0 为不显示 1 为显示
	private int subjectSort;

	public int getSubjectSort() {
		return subjectSort;
	}

	public void setSubjectSort(int subjectSort) {
		this.subjectSort = subjectSort;
	}

	private List<DwmisModuleInfo> moduleInfoList;

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public DwmisModuleInfo[] getModuleInfoArray() {
		return moduleInfoArray;
	}

	public void setModuleInfoArray(DwmisModuleInfo[] moduleInfoArray) {
		this.moduleInfoArray = moduleInfoArray;
	}

	public int getDateType() {
		return dateType;
	}

	public void setDateType(int dateType) {
		this.dateType = dateType;
	}

	public int getIsShow() {
		return isShow;
	}

	public void setIsShow(int isShow) {
		this.isShow = isShow;
	}

	public List<DwmisModuleInfo> getModuleInfoList() {
		if (moduleInfoArray != null && moduleInfoArray.length > 0) {
			this.moduleInfoList = Arrays.asList(moduleInfoArray);
		}
		return moduleInfoList;
	}

	public void setModuleInfoList(List<DwmisModuleInfo> moduleInfoList) {
		this.moduleInfoList = moduleInfoList;
	}

}
