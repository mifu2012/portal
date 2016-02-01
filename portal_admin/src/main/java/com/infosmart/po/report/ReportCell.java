package com.infosmart.po.report;

import com.infosmart.util.StringUtils;

/**
 * 报表设计器之单元格
 * 
 * @author infosmart
 * 
 */
public class ReportCell implements Cloneable {
	// 对象复制
	public Object clone() {
		ReportCell reportCell = null;
		try {
			reportCell = (ReportCell) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return reportCell;
	}

	// id(主要用于设计器)
	private String id;
	// 父级id (主要用于设计器)
	private String pId;
	// 行号
	private int rowNum;
	// 列号
	private int colNum;
	// 跨行
	private int rowSpan;
	// 跨列
	private int colSpan;
	// 内容
	private String content = "&nbsp;";
	// 链接(不包含关联的报表ID)
	private String link;
	// 关联的报表ID
	private String relRptId;
	/**
	 * 关联报表名称
	 */
	private String relRptName;
	// 数据类型1：数据列，2:二级表头，3：三级表头
	private int dataType;
	// 显示方式，如分组统计，合计等(没有用到)
	private int dataShowType;
	// 绑定的字段
	private String dataField;

	/****************************************/
	/*数据格式化配置*/
	/**
	 * integer、date、number
	 */
	private String formatter;
	
	/**
	 * 精度，小数位数
	 */
	private int scale;
	
	/**
	 * 货币符标识
	 */
	private int currency;
	
	/**
	 * 千分符标识
	 */
	private int commas;
	
	/**
	 * 预警值
	 */
	private String warningValue;
	/****************************************/

	// 字段类型，默认是0,字符串
	private int columnType = 12;

	// 日期格式化类型
	private String datefmt;

	// 对齐方式
	private String align;

	// 小计
	private int rowSum;

	// 分组统计
	private int groupSum;

	// 统计方式
	private String groupSummaryType;

	// 合计
	private int pageSum;

	// 统计方式
	private String pageSummaryType;

	// 第一个子节点列
	private String firstChildCol;

	// 宽度
	private int width;

	/**
	 * 动态列，绑定字段
	 */
	private String dynamicColumnField;
	/**
	 * 动态列，表头字段
	 */
	private String dynamicHeaderFieldList;
	/**
	 * 动态列，列号
	 */
	private int dynamicColNum = -1;
	/**
	 * 父节点名称
	 */
	private String pDataField;

	public void setpDataField(String pDataField) {
		this.pDataField = pDataField;
	}

	public String getpDataField() {
		return this.pDataField;
	}

	public String getRelRptName() {
		return relRptName;
	}

	public void setRelRptName(String relRptName) {
		this.relRptName = relRptName;
	}

	public String getRelRptId() {
		return relRptId;
	}

	public void setRelRptId(String relRptId) {
		this.relRptId = relRptId;
	}

	public int getDynamicColNum() {
		return dynamicColNum;
	}

	public void setDynamicColNum(int dynamicColNum) {
		this.dynamicColNum = dynamicColNum;
	}

	public String getDynamicHeaderFieldList() {
		return dynamicHeaderFieldList;
	}

	public void setDynamicHeaderFieldList(String dynamicHeaderFieldList) {
		this.dynamicHeaderFieldList = dynamicHeaderFieldList;
	}

	public String getDynamicColumnField() {
		return dynamicColumnField;
	}

	public void setDynamicColumnField(String dynamicColumnField) {
		this.dynamicColumnField = dynamicColumnField;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public void setColumnType(int columnType) {
		this.columnType = columnType;
	}

	public void setGroupSum(int groupSum) {
		this.groupSum = groupSum;
	}

	public void setPageSum(int pageSum) {
		this.pageSum = pageSum;
	}

	public int getColumnType() {
		if (StringUtils.notNullAndSpace(formatter)) {
			if (formatter.equalsIgnoreCase("date")) {
				columnType = 91;// 91以上为日期格式
			} else if (formatter.equalsIgnoreCase("number")) {
				columnType = 1;// 1到10为数字型
			} else if (formatter.equalsIgnoreCase("currency")) {
				columnType = 100;// 100为金额
			} else {
				columnType = 12;// 12为字符
			}
		} else {
			columnType = 12;// 12为字符
		}
		return columnType;
	}

	public int getWidth() {
		this.width = this.width <= 0 ? 100 : width;
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public String getFormatter() {
		return formatter;
	}

	public void setFormatter(String formatter) {
		this.formatter = formatter;
	}

	public String getDatefmt() {
		return datefmt;
	}

	public void setDatefmt(String datefmt) {
		this.datefmt = datefmt;
	}

	public String getGroupSummaryType() {
		return groupSummaryType;
	}

	public void setGroupSummaryType(String groupSummaryType) {
		this.groupSummaryType = groupSummaryType;
	}

	public String getPageSummaryType() {
		return pageSummaryType;
	}

	public void setPageSummaryType(String pageSummaryType) {
		this.pageSummaryType = pageSummaryType;
	}

	public String getFirstChildCol() {
		return firstChildCol;
	}

	public void setFirstChildCol(String firstChildCol) {
		this.firstChildCol = firstChildCol;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public int getRowSum() {
		return rowSum;
	}

	public void setRowSum(int rowSum) {
		this.rowSum = rowSum;
	}

	public int getGroupSum() {
		if (StringUtils.notNullAndSpace(this.groupSummaryType)) {
			groupSum = 1;
		} else {
			groupSum = 0;
		}
		return groupSum;
	}

	public int getPageSum() {
		if (StringUtils.notNullAndSpace(pageSummaryType)) {
			pageSum = 1;
		} else {
			pageSum = 0;
		}
		return pageSum;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public int getColNum() {
		return colNum;
	}

	public void setColNum(int colNum) {
		this.colNum = colNum;
	}

	public int getRowSpan() {
		return rowSpan;
	}

	public void setRowSpan(int rowSpan) {
		this.rowSpan = rowSpan;
	}

	public int getColSpan() {
		return colSpan;
	}

	public void setColSpan(int colSpan) {
		this.colSpan = colSpan;
	}

	public String getContent() {
		if (content == null || content.length() == 0) {
			content = "&nbsp;";
		}
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getDataType() {
		return dataType;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

	public int getDataShowType() {
		return dataShowType;
	}

	public void setDataShowType(int dataShowType) {
		this.dataShowType = dataShowType;
	}

	public String getDataField() {
		return dataField;
	}

	public void setDataField(String dataField) {
		this.dataField = dataField;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	public int getCurrency() {
		return currency;
	}

	public void setCurrency(int currency) {
		this.currency = currency;
	}

	public int getCommas() {
		return commas;
	}

	public void setCommas(int commas) {
		this.commas = commas;
	}

	public String getWarningValue() {
		return warningValue;
	}

	public void setWarningValue(String warningValue) {
		this.warningValue = warningValue;
	}
}
