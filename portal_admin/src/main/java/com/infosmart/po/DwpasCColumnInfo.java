package com.infosmart.po;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.infosmart.util.StringUtils;

/**
 * 系统栏目 （对于首页或新增的栏目，如果有多个栏目，则以TAB进行切换）
 * 
 * @author infosmart
 * 
 */
public class DwpasCColumnInfo {
	private String columnId;
	private String moduleId;
	private String columnCode;
	private String columnName;
	private String columnDisplayName;
	private String columnKind;
	private Integer isShow = 1;
	private String remark;
	private Date gmtCreate;

	private Integer columnOrder; // 栏目顺序
	// 关联通用指标
	private String comkpilist;// 多个指标的话以,分割
	private String newModuleId;
	private String userType;// 临时字段，用户声音模块用户类型
	// *******************************下面是新增的
	// 关联的通用指标列表
	private List<ComKpiInfo> relCommKpiCodeList;
	// 关联的通用指标（前台取值用）
	private String relCommKpiCodes;
	private String relCommKpiNames;
	// 关联模块
	private DwpasCmoduleInfo moduleInfo;
	private List<DwpasRColumnComKpi> kpiCodelist;// (产品发展日类型)指标集合
	private Map<String, String> comcodeMap = new HashMap<String, String>();// //(产品发展日类型)指标集合Map格式

	public Map<String, String> getComcodeMap() {
		return comcodeMap;
	}

	public void setComcodeMap(Map<String, String> comcodeMap) {
		this.comcodeMap = comcodeMap;
	}

	public List<DwpasRColumnComKpi> getKpiCodelist() {
		return kpiCodelist;
	}

	public void setKpiCodelist(List<DwpasRColumnComKpi> kpiCodelist) {
		this.kpiCodelist = kpiCodelist;
	}

	/**
	 * 图的类型，如果展现多个不同类型的图(趋势图 /饼图/矩形图/折线图/矩形+折线等)，以“;分隔”
	 * 
	 */
	private int chartType;
	public final static int CHART_TYPE_UNDEFINED = -1;// 未知
	public final static int CHART_TYPE_STOCK = 0;// 趋势图
	public final static int CHART_TYPE_PIE = 1;// 饼图
	public final static int CHART_TYPE_COLUMN = 2;// 矩形图
	public final static int CHART_TYPE_LINE = 3;// 折线
	public final static int CHART_TYPE_COLUMN_AND_LINE = 4;// 矩形图+折线

	public final static int CHART_TYPE_BAR = 5;// 条形图
	public final static int CHART_TYPE_AREA = 6;// 面积图
	public final static int CHART_TYPE_BUBBLE = 7;// 气泡图
	public final static int CHART_TYPE_SCATTER = 8;// 散点图

	public final static int CHART_TYPE_URL = 11;// 新增URL
	public final static int CHART_TYPE_REPORT_URL = 12;// 新增报表
	/**
	 * 下面的系统栏目需要配置指标
	 */
	public final static List<String> SYSTEM_COLUMN_COCE_LIST = new ArrayList<String>();

	static {
		// 预警图
		SYSTEM_COLUMN_COCE_LIST.add("INDEX_ALERT_DASHBOARD_CHART");
		SYSTEM_COLUMN_COCE_LIST.add("INDEX_ALERT_DENOMINATOR_DASHBOARD_CHART");
		SYSTEM_COLUMN_COCE_LIST.add("INDEX_ALERT_MOLECULES_DASHBOARD_CHART");
		// 业务笔数监控
		SYSTEM_COLUMN_COCE_LIST.add("INDEX_RECTANGLE_CHART_COUNTS");
		// 产品全图
		SYSTEM_COLUMN_COCE_LIST.add("INDEX_PRODUCT_PIE_CHART");
	}
	/**
	 * 显示的图的类型,如stock,pie,column,line,column+line
	 */
	private String chartTypeDesc;

	/**
	 * 趋势图
	 */
	public final static String CHART_TYPE_DESC_STOCK = "stock";
	/**
	 * 饼图
	 */
	public final static String CHART_TYPE_DESC_PIE = "pie";
	/**
	 * 矩形图
	 */
	public final static String CHART_TYPE_DESC_COLUMN = "column";
	/**
	 * 折线图
	 */
	public final static String CHART_TYPE_DESC_LINE = "line";
	/**
	 * 矩形+折线
	 */
	public final static String CHART_TYPE_DESC_LINE_COLUMN = "line_column";
	/**
	 * 条形图
	 */
	public final static String CHART_TYPE_DESC_BAR = "bar";
	/**
	 * 面积图
	 */
	public final static String CHART_TYPE_DESC_AREA = "area";
	/**
	 * 气泡图
	 */
	public final static String CHART_TYPE_DESC_BUBBLE = "bubble";
	/**
	 * 散点图
	 */
	public final static String CHART_TYPE_DESC_SCATTER = "scatter";

	// 可能会几个参数#{kpiType},#{chartType}
	private String columnUrl;

	/**
	 * 关联指标类型:0:关联产品(通用指标),1:非关联产品(大盘指标)
	 */
	private String relKpiKind = "0";
	// 通用指标
	public final static int COMM_KPI_OF_REL_KPI_KIND = 0;
	// 大盘指标
	public final static int OVERALL_KPI_OF_REL_KPI_KIND = 1;

	public String getRelCommKpiCodes() {
		if (relCommKpiCodeList != null && !relCommKpiCodeList.isEmpty()) {
			StringBuffer relCommKpi = new StringBuffer();
			for (ComKpiInfo comKpi : relCommKpiCodeList) {
				if (comKpi == null)
					continue;
				if (relCommKpi.toString().length() > 0) {
					relCommKpi.append(",");
				}
				relCommKpi.append(comKpi.getComKpiCode() + "^"
						+ comKpi.getProductId());
			}
			return relCommKpi.toString();
		}
		return this.relCommKpiCodes;
	}

	public String getRelKpiKind() {
		return relKpiKind;
	}

	public void setRelKpiKind(String relKpiKind) {
		this.relKpiKind = relKpiKind;
	}

	public String getRelCommKpiNames() {
		if (relCommKpiCodeList != null && !relCommKpiCodeList.isEmpty()) {
			StringBuffer relCommKpi = new StringBuffer();
			for (ComKpiInfo comKpi : relCommKpiCodeList) {
				if (comKpi == null)
					continue;
				if (relCommKpi.toString().length() > 0) {
					relCommKpi.append(",");
				}
				relCommKpi.append(comKpi.getComKpiName() + "——"
						+ comKpi.getProductName());
			}
			return relCommKpi.toString();
		}
		return this.relCommKpiNames;
	}

	public void setRelCommKpiCodes(String relCommKpiCodes) {
		this.relCommKpiCodes = relCommKpiCodes;
	}

	public void setRelCommKpiNames(String relCommKpiNames) {
		this.relCommKpiNames = relCommKpiNames;
	}

	public String getChartTypeDesc() {
		return chartTypeDesc;
	}

	public void setChartTypeDesc(String chartTypeDesc) {
		this.chartTypeDesc = chartTypeDesc;
	}

	public DwpasCmoduleInfo getModuleInfo() {
		return moduleInfo;
	}

	public void setModuleInfo(DwpasCmoduleInfo moduleInfo) {
		this.moduleInfo = moduleInfo;
	}

	public int getChartType() {
		if (StringUtils.notNullAndSpace(chartTypeDesc)) {
			if (chartTypeDesc
					.equalsIgnoreCase(DwpasCColumnInfo.CHART_TYPE_DESC_PIE)) {
				// 饼图
				chartType = DwpasCColumnInfo.CHART_TYPE_PIE;
			} else if (chartTypeDesc
					.equalsIgnoreCase(DwpasCColumnInfo.CHART_TYPE_DESC_STOCK)) {
				// 趋势图
				chartType = DwpasCColumnInfo.CHART_TYPE_STOCK;
			} else if (chartTypeDesc.toLowerCase().indexOf(
					DwpasCColumnInfo.CHART_TYPE_DESC_LINE) != -1
					&& chartTypeDesc.toLowerCase().indexOf(
							DwpasCColumnInfo.CHART_TYPE_DESC_COLUMN) != -1) {
				// 矩形+折线
				chartType = DwpasCColumnInfo.CHART_TYPE_COLUMN_AND_LINE;
			} else if (chartTypeDesc.toLowerCase().indexOf(
					DwpasCColumnInfo.CHART_TYPE_DESC_LINE) != -1) {
				// 折线
				chartType = DwpasCColumnInfo.CHART_TYPE_LINE;
			} else if (chartTypeDesc
					.equalsIgnoreCase(DwpasCColumnInfo.CHART_TYPE_DESC_BAR)) {
				// 条形图
				chartType = DwpasCColumnInfo.CHART_TYPE_BAR;
			} else if (chartTypeDesc
					.equalsIgnoreCase(DwpasCColumnInfo.CHART_TYPE_DESC_AREA)) {
				// 面积图
				chartType = DwpasCColumnInfo.CHART_TYPE_AREA;
			} else if (chartTypeDesc
					.equalsIgnoreCase(DwpasCColumnInfo.CHART_TYPE_DESC_BUBBLE)) {
				// 气泡图
				chartType = DwpasCColumnInfo.CHART_TYPE_BUBBLE;
			} else if (chartTypeDesc
					.equalsIgnoreCase(DwpasCColumnInfo.CHART_TYPE_DESC_SCATTER)) {
				// 散点图
				chartType = DwpasCColumnInfo.CHART_TYPE_SCATTER;
			} else {
				// 默认矩形图
				chartType = DwpasCColumnInfo.CHART_TYPE_COLUMN;
			}
		}
		// 如果是系统栏目（涉及选指标）
		if (DwpasCColumnInfo.SYSTEM_COLUMN_COCE_LIST.contains(this.columnCode)) {
			this.chartType = DwpasCColumnInfo.CHART_TYPE_UNDEFINED;// 其他图
		}
		return chartType;
	}

	public void setChartType(int chartType) {
		this.chartType = chartType;
	}

	public String getColumnUrl() {
		if (StringUtils.notNullAndSpace(columnUrl)) {
			int kpiType = DwpasCKpiInfo.KPI_TYPE_OF_DAY;
			if (moduleInfo != null
					&& StringUtils.notNullAndSpace(moduleInfo.getDateType())) {
				if (moduleInfo.getDateType().equalsIgnoreCase("D")) {
					kpiType = DwpasCKpiInfo.KPI_TYPE_OF_DAY;
				} else if (moduleInfo.getDateType().equalsIgnoreCase("W")) {
					kpiType = DwpasCKpiInfo.KPI_TYPE_OF_WEEK;
				} else {
					kpiType = DwpasCKpiInfo.KPI_TYPE_OF_MONTH;
				}
			}
			// 指标类型
			columnUrl = StringUtils.replace(columnUrl, "#{kpiType}",
					String.valueOf(kpiType));
			// 图表类型
			columnUrl = StringUtils.replace(columnUrl, "#{chartType}",
					String.valueOf(this.chartType));
		}
		return columnUrl;
	}

	public void setColumnUrl(String columnUrl) {
		this.columnUrl = columnUrl;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getNewModuleId() {
		return newModuleId;
	}

	public void setNewModuleId(String newModuleId) {
		this.newModuleId = newModuleId;
	}

	public String getComkpilist() {
		return comkpilist;
	}

	public void setComkpilist(String comkpilist) {
		this.comkpilist = comkpilist;
	}

	private String templateName;// 临时字段

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	private Page page;

	public String getColumnCode() {
		return columnCode;
	}

	public void setColumnCode(String columnCode) {
		this.columnCode = columnCode;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
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

	public Page getPage() {
		if (page == null)
			page = new Page();
		return page;
	}

	public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getColumnDisplayName() {
		return columnDisplayName;
	}

	public void setColumnDisplayName(String columnDisplayName) {
		this.columnDisplayName = columnDisplayName;
	}

	public String getColumnKind() {
		return columnKind;
	}

	public void setColumnKind(String columnKind) {
		this.columnKind = columnKind;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public Integer getColumnOrder() {
		return columnOrder;
	}

	public void setColumnOrder(Integer columnOrder) {
		this.columnOrder = columnOrder;
	}

}
