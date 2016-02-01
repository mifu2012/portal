package com.infosmart.po.dwmis;

import java.util.Date;
import java.util.List;

/**
 * 瞭望塔模块表
 * 
 * @author Administrator
 * 
 */
public class DwmisModuleInfo {
	// 模块id
	private String moduleId;
	// 主题Id
	private String subjectId;
	// 模块名称
	private String moduleName;
	// 是否显示
	private int isShow;
	// 备注
	private String remark;
	// 创建时间
	private Date gmtCreate;
	// 显示位置
	private int position = DwmisModuleInfo.POSITION_BOTTOM;
	// 顶部
	public final static int POSITION_TOP = 0;
	// 左边
	public final static int POSITION_LEFT = 1;
	// 右边
	public final static int POSITION_RIGHT = 2;
	// 底部
	public final static int POSITION_BOTTOM = 3;
	// 宽度
	private int width;
	// 高度
	private int height;
	// 模块类型
	private int moduleType;
	// 模块排序
	private int moduleSort;
	// x坐标
	private Float positionX;
	// y坐标
	private Float positionY;
	// tab显示 1：TAB显示；0：并排显示
	private int tabShow;
	// 关联图列
	private List<DwmisLegendInfo> legendInfos;
	// 图例Ids
	private String legendIds;

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
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

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
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

	public Float getPositionX() {
		return positionX;
	}

	public void setPositionX(Float positionX) {
		this.positionX = positionX;
	}

	public Float getPositionY() {
		return positionY;
	}

	public void setPositionY(Float positionY) {
		this.positionY = positionY;
	}

	public int getTabShow() {
		return tabShow;
	}

	public void setTabShow(int tabShow) {
		this.tabShow = tabShow;
	}

	public List<DwmisLegendInfo> getLegendInfos() {
		return legendInfos;
	}

	public void setLegendInfos(List<DwmisLegendInfo> legendInfos) {
		this.legendInfos = legendInfos;
	}

	public String getLegendIds() {
		return legendIds;
	}

	public void setLegendIds(String legendIds) {
		this.legendIds = legendIds;
	}

}
