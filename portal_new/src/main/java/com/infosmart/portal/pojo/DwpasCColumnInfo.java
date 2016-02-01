package com.infosmart.portal.pojo;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.infosmart.portal.util.StringUtils;

/**
 * 栏目信息
 * 
 * @author infosmart
 * 
 */
public class DwpasCColumnInfo {

	private static Map<String, String> SYSTEM_COLUMN_URL_MAP = new HashMap<String, String>();

	static {
		/**
		 * 产品全图
		 */
		SYSTEM_COLUMN_URL_MAP.put("INDEX_PRODUCT_CHART",
				"/index/showAllPlatFormUser");
		/**
		 * 风向标
		 */
		SYSTEM_COLUMN_URL_MAP.put("INDEX_USER_HELP_KEYWORD_LIST",
				"/index/showAskWindVane");
		/**
		 * 业务笔数监控
		 */
		SYSTEM_COLUMN_URL_MAP.put("INDEX_RECTANGLE_CHART",
				"/index/prodTransMonitorChart");
		/**
		 * 用户数统计
		 */
		SYSTEM_COLUMN_URL_MAP.put("INDEX_USER_COUNT_TABLE",
				"/index/userCountStatistics");
		/**
		 * 用户咨询情况:
		 */
		SYSTEM_COLUMN_URL_MAP.put("INDEX_ALERT_DASHBOARD_CHART",
				"/index/askBashBoardChart");
		/**
		 * 大事件
		 */
		SYSTEM_COLUMN_URL_MAP.put("INDEX_IMPORTANT_EVENT_LIST",
				"/index/listLeastBigEvent");
	}
	/**
	 * 栏目ID
	 */
	private String columnId;

	/**
	 * 关联的模块信息
	 */
	private DwpasCModuleInfo moduleInfo;
	/**
	 * 模块ID
	 * 
	 */
	private String moduleId;
	/**
	 * 栏目编码,唯一
	 */
	private String columnCode;

	/**
	 * 栏目名称
	 */
	private String columnName;

	/**
	 * 显示名称
	 */
	private String columnDisplayName;

	/**
	 * 大类名称
	 */
	private String columnKind;

	/**
	 * 是否显示
	 */
	private Integer isShow;

	/**
	 * 栏目顺序
	 */
	private Integer columnOrder;

	/**
	 * 备注,比如放帮助信息
	 */
	private String remark;

	// 关联的指标信息
	private String kpiCodes = "";

	private List<DwpasCKpiInfo> kpiInfoList;

	private int needPercent = 0;

	public int getNeedPercent() {
		if (kpiInfoList != null && kpiInfoList.size() >= 2) {
			String unit = null;
			for (DwpasCKpiInfo kpiInfo : kpiInfoList) {
				if (unit != null && !unit.equalsIgnoreCase(kpiInfo.getUnit())) {
					needPercent = 1;
					break;
				}
				unit = kpiInfo.getUnit();
			}
		}
		return needPercent;
	}

	// *******************************下面是新增的
	// 关联的通用指标列表
	private List<DwpasCComKpiInfo> relCommKpiCodeList;
	// 关联的通用指标（前台取值用）
	private String relCommKpiCodes;
	private String relCommKpiNames;

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

	/**
	 * 显示的图的类型,如stock,pie,column,line,column+line
	 */
	private String chartTypeDesc;

	// 可能会几个参数#{kpiType},#{chartType}
	private String columnUrl;

	public List<DwpasCComKpiInfo> getRelCommKpiCodeList() {
		return relCommKpiCodeList;
	}

	public void setRelCommKpiCodeList(List<DwpasCComKpiInfo> relCommKpiCodeList) {
		this.relCommKpiCodeList = relCommKpiCodeList;
	}

	public String getRelCommKpiCodes() {
		if (relCommKpiCodeList != null && !relCommKpiCodeList.isEmpty()) {
			StringBuffer relCommKpi = new StringBuffer();
			for (DwpasCComKpiInfo comKpi : relCommKpiCodeList) {
				if (comKpi == null)
					continue;
				if (relCommKpi.toString().length() > 0) {
					relCommKpi.append(",");
				}
				relCommKpi.append(comKpi.getComKpiCode());
			}
			return relCommKpi.toString();
		}
		return this.relCommKpiCodes;
	}

	public String getRelCommKpiNames() {
		if (relCommKpiCodeList != null && !relCommKpiCodeList.isEmpty()) {
			StringBuffer relCommKpi = new StringBuffer();
			for (DwpasCComKpiInfo comKpi : relCommKpiCodeList) {
				if (comKpi == null)
					continue;
				if (relCommKpi.toString().length() > 0) {
					relCommKpi.append(",");
				}
				relCommKpi.append(comKpi.getComKpiName());
			}
			return relCommKpi.toString();
		}
		return this.relCommKpiNames;
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
		return chartType;
	}

	public void setChartType(int chartType) {
		this.chartType = chartType;
	}

	public String getChartTypeDesc() {
		return chartTypeDesc;
	}

	public void setChartTypeDesc(String chartTypeDesc) {
		this.chartTypeDesc = chartTypeDesc;
	}

	public String getColumnUrl() {
		// 报表栏目
		if (chartType == DwpasCColumnInfo.CHART_TYPE_REPORT_URL) {
			return this.columnUrl;
		}
		if (columnUrl != null && columnUrl.indexOf("?") == -1) {
			columnUrl += "?1=1";
		}
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
				// 指标类型
				if (columnUrl.indexOf("#{kpiType}") != -1) {
					columnUrl = StringUtils.replace(columnUrl, "#{kpiType}",
							String.valueOf(kpiType));
				} else if (columnUrl.indexOf("&kpiInfo=") == -1
						|| columnUrl.indexOf("kpiInfo=") == -1) {
					columnUrl += "&kpiType=" + kpiType;
				}
			}
			// 图表类型
			if (columnUrl.indexOf("#{chartType}") != -1) {
				columnUrl = StringUtils.replace(columnUrl, "#{chartType}",
						String.valueOf(this.chartType))
						+ "&chartTypeDesc="
						+ chartTypeDesc;
			} else if (columnUrl.indexOf("&chartType=") == -1
					|| columnUrl.indexOf("chartType=") == -1) {
				columnUrl += "&chartType=" + chartType + "&chartTypeDesc="
						+ chartTypeDesc;
			}
		}
		return columnUrl;
	}

	public void setColumnUrl(String columnUrl) {
		this.columnUrl = columnUrl;
	}

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column dwpas_c_column_info.GMT_CREATE
	 * 
	 * @ibatorgenerated Fri Mar 02 09:56:01 CST 2012
	 */
	private Date gmtCreate;

	public String getKpiCodes() {
		if (this.kpiInfoList == null || this.kpiInfoList.isEmpty())
			return "";
		StringBuffer multiKpiCode = new StringBuffer("");
		for (DwpasCKpiInfo kpiInfo : this.kpiInfoList) {
			if (multiKpiCode.toString().length() > 0)
				multiKpiCode.append(";");
			multiKpiCode.append(kpiInfo.getKpiCode());
		}
		this.kpiCodes = multiKpiCode.toString();
		return kpiCodes;
	}

	public DwpasCModuleInfo getModuleInfo() {
		return moduleInfo;
	}

	public void setModuleInfo(DwpasCModuleInfo moduleInfo) {
		this.moduleInfo = moduleInfo;
	}

	public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	public void setKpiCodes(String kpiCodes) {
		this.kpiCodes = kpiCodes;
	}

	public List<DwpasCKpiInfo> getKpiInfoList() {
		return kpiInfoList;
	}

	public void setKpiInfoList(List<DwpasCKpiInfo> kpiInfoList) {
		this.kpiInfoList = kpiInfoList;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column dwpas_c_column_info.COLUMN_CODE
	 * 
	 * @return the value of dwpas_c_column_info.COLUMN_CODE
	 * 
	 * @ibatorgenerated Fri Mar 02 09:56:01 CST 2012
	 */
	public String getColumnCode() {
		return columnCode;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column dwpas_c_column_info.COLUMN_CODE
	 * 
	 * @param columnCode
	 *            the value for dwpas_c_column_info.COLUMN_CODE
	 * 
	 * @ibatorgenerated Fri Mar 02 09:56:01 CST 2012
	 */
	public void setColumnCode(String columnCode) {
		this.columnCode = columnCode == null ? null : columnCode.trim();
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column dwpas_c_column_info.COLUMN_NAME
	 * 
	 * @return the value of dwpas_c_column_info.COLUMN_NAME
	 * 
	 * @ibatorgenerated Fri Mar 02 09:56:01 CST 2012
	 */
	public String getColumnName() {
		return columnName;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column dwpas_c_column_info.COLUMN_NAME
	 * 
	 * @param columnName
	 *            the value for dwpas_c_column_info.COLUMN_NAME
	 * 
	 * @ibatorgenerated Fri Mar 02 09:56:01 CST 2012
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName == null ? null : columnName.trim();
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column dwpas_c_column_info.IS_SHOW
	 * 
	 * @return the value of dwpas_c_column_info.IS_SHOW
	 * 
	 * @ibatorgenerated Fri Mar 02 09:56:01 CST 2012
	 */
	public Integer getIsShow() {
		return isShow;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column dwpas_c_column_info.IS_SHOW
	 * 
	 * @param isShow
	 *            the value for dwpas_c_column_info.IS_SHOW
	 * 
	 * @ibatorgenerated Fri Mar 02 09:56:01 CST 2012
	 */
	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column dwpas_c_column_info.REMARK
	 * 
	 * @return the value of dwpas_c_column_info.REMARK
	 * 
	 * @ibatorgenerated Fri Mar 02 09:56:01 CST 2012
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column dwpas_c_column_info.REMARK
	 * 
	 * @param remark
	 *            the value for dwpas_c_column_info.REMARK
	 * 
	 * @ibatorgenerated Fri Mar 02 09:56:01 CST 2012
	 */
	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
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

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column dwpas_c_column_info.GMT_CREATE
	 * 
	 * @return the value of dwpas_c_column_info.GMT_CREATE
	 * 
	 * @ibatorgenerated Fri Mar 02 09:56:01 CST 2012
	 */
	public Date getGmtCreate() {
		return gmtCreate;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column dwpas_c_column_info.GMT_CREATE
	 * 
	 * @param gmtCreate
	 *            the value for dwpas_c_column_info.GMT_CREATE
	 * 
	 * @ibatorgenerated Fri Mar 02 09:56:01 CST 2012
	 */
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Integer getColumnOrder() {
		return columnOrder;
	}

	public void setColumnOrder(Integer columnOrder) {
		this.columnOrder = columnOrder;
	}

}