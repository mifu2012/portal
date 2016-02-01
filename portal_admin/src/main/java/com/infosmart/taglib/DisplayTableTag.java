package com.infosmart.taglib;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

import com.infosmart.util.DateUtils;
import com.infosmart.util.InterpreterUtil;
import com.infosmart.util.StringUtils;
import com.infosmart.view.PageInfo;

/**
 * 分页显示标签
 * 
 * @author Winker
 * 
 */
public class DisplayTableTag extends TagSupport {
	private final Logger log = Logger.getLogger(DisplayTableTag.class);

	// 日期类型
	final String dateType = java.util.Date.class.getName();

	final String integerType = java.lang.Integer.class.getName();

	final String longType = java.lang.Long.class.getName();

	final String floatType = java.lang.Float.class.getName();

	final String doubleType = java.lang.Double.class.getName();

	final String bigDecimalType = java.math.BigDecimal.class.getName();

	// 实体类名
	private String entityClazz = "";

	// 数据列在内存中名字
	private String attributeName = "";

	// 图片路径
	private String imgPath = "";

	// 提交的URL
	private String formAction = "";

	// height
	private String tableHeight = "353px";

	// width
	private String tableWidth = "100%";

	// id
	private String tableId = "";

	// name
	private String tableName = "";

	// 表格行数
	private int tableRowCount = 15;

	private String serviceFactory;

	// 表格样式
	private String tableStyleClass = "listTableNoFixed";

	// 行定义信息
	private DisplayRowInfo displayRowInfo = new DisplayRowInfo();

	// 列定义信息
	private List displayColInfoList = new ArrayList();

	// 页面表格数据对象
	private PageInfo pageInfo = new PageInfo();

	// 当前索引号
	private final String rowStatusIndex = "#rowStatus.index";

	// 当前根路径
	private final String rootPathTag = "#rootPath";

	private String rootPath;

	public DisplayTableTag() {

		this.attributeName = "";
		this.formAction = "";
		this.tableHeight = "";
		this.tableId = "";
		this.imgPath = "/common/images/";// 默认路径
		this.tableName = "";
		this.tableStyleClass = "listTableNoFixed";
		this.tableWidth = "100%";
		this.entityClazz = "";
		this.tableRowCount = 15;
	}

	public void setTableStyleClass(String tableStyleClass) {
		this.tableStyleClass = tableStyleClass;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setTableWidth(String tableWidth) {
		this.tableWidth = tableWidth;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public void setFormAction(String formAction) {
		this.formAction = formAction;
	}

	public void setTableHeight(String tableHeight) {
		this.tableHeight = tableHeight;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public void setDisplayRowInfo(DisplayRowInfo displayRowInfo) {
		this.displayRowInfo = displayRowInfo;
	}

	public void setEntityClazz(String entityClazz) {
		this.entityClazz = entityClazz;
	}

	public void setTableRowCount(int tableRowCount) {
		this.tableRowCount = tableRowCount;
	}

	public void setDisplayColumnInfo(DisplayColumnInfo displayColumnInfo) {
		this.displayColInfoList.add(displayColumnInfo);
	}

	/**
	 * 取数据
	 * 
	 * @throws JspException
	 * @return int
	 */
	public int doStartTag() throws JspException {
		this.log.debug("开始生成数据");
		// 清空数据
		this.displayRowInfo = new DisplayRowInfo();
		this.displayColInfoList.clear();
		// 得到页面数据对象
		this.pageInfo = (PageInfo) this.pageContext
				.findAttribute(PageInfo.PROPERTY_LIST_NAME);
		if (this.pageInfo == null) {
			this.pageInfo = (PageInfo) this.pageContext
					.getAttribute(PageInfo.PROPERTY_LIST_NAME);
		}
		if (this.pageInfo == null) {
			this.pageInfo = (PageInfo) this.pageContext
					.getAttribute(this.attributeName);
		}
		if (this.pageInfo != null) {
			this.pageContext.removeAttribute(PageInfo.PROPERTY_LIST_NAME);
			this.pageContext.removeAttribute(this.attributeName);
		}
		if (this.pageInfo == null) {
			this.pageInfo = new PageInfo();
		} else {
			this.tableRowCount = this.pageInfo.getPageSize();
		}
		return this.EVAL_BODY_INCLUDE;
	}

	/**
	 * 生成列表数据
	 */
	public int doEndTag() {

		try {
			if (pageInfo == null) {
				pageInfo = new PageInfo();
				// 输出信息
			}
			StringBuffer out = new StringBuffer("");
			HttpServletRequest request = (HttpServletRequest) this.pageContext
					.getRequest();
			String contextPath = request.getContextPath();
			this.rootPath = request.getContextPath();
			if (!this.formAction.startsWith("/")) {
				contextPath += "/";
			}
			// formAction = contextPath + this.formAction;
			if (this.formAction == null || this.formAction.length() == 0) {
				// webwork特殊要求决定
				String requestUri = request
						.getAttribute("javax.servlet.forward.request_uri") == null ? null
						: (String) request
								.getAttribute("javax.servlet.forward.servlet_path");
				formAction = request.getScheme() + "://"
						+ request.getServerName() + ":"
						+ request.getServerPort() + request.getContextPath()
						+ "/" + requestUri;// webwork:webwork.request_uri
			}
			out.append("<form name='pulbicquery_form' method='post' action='"
					+ this.formAction + "' target='_self'>\n");
			// 隐含参数控件即加上查询参数
			Map paramMap = request.getParameterMap();
			if (paramMap == null) {
				paramMap = new HashMap();
			}
			Set paramSet = paramMap.entrySet();
			String paramName = "";
			String paramValue = "";
			for (Iterator it = paramSet.iterator(); it.hasNext();) {
				Entry paramEntry = (Entry) it.next();
				paramName = (String) paramEntry.getKey();
				if (paramName.equalsIgnoreCase("publicquery_gotopage")) {
					continue;
				}
				try {
					paramValue = (String) paramEntry.getValue();
				} catch (Exception e) {
					paramValue = request.getParameter(paramName);
				}
				if (StringUtils.notNullAndSpace(paramValue)
						|| paramName
								.equalsIgnoreCase(PageInfo.PAGE_ORDER_FIELD)
						|| paramName.equalsIgnoreCase(PageInfo.QUERY_CONDITION)) {
					out.append("<input type='hidden' name='" + paramName
							+ "' value=\"" + paramValue + "\">\n");
				}
			}
			// 当前页码
			if (!paramMap.containsKey(PageInfo.PAGE_PAGE_NO)) {
				out.append("<input type='hidden' name='"
						+ PageInfo.PAGE_PAGE_NO + "' value='1'>\n");
			}
			// 没有排序字段
			if (!paramMap.containsKey(PageInfo.PAGE_ORDER_FIELD)) {
				out.append("<input type='hidden' name='"
						+ PageInfo.PAGE_ORDER_FIELD + "' value='"
						+ this.pageInfo.getOrderField() + "'>\n");
			}
			// 查询条件
			if (!paramMap.containsKey(PageInfo.QUERY_CONDITION)) {
				out.append("<input type='hidden' name='"
						+ PageInfo.QUERY_CONDITION + "' value=''>\n");
			}

			out
					.append("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class='"
							+ this.tableStyleClass + "' >\n");
			out.append("<tr>\n");
			out.append("<td>\n");
			out.append("<div  style=\"width:" + this.tableWidth);
			if (!this.tableHeight.equals("")) {
				out.append(";height:" + this.tableHeight);
			}
			out
					.append(";top:0px;left:0px;right:0px;overflow:auto;border:solid 1px;border-bottom: 0px solid;\">\n");
			out
					.append("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" >\n");// class='query_list_table'
			out.append("<tr>\n");
			out.append("<td>\n");
			out
					.append("<table width='100%' border='0' cellpadding='0' cellspacing='0' id='"
							+ this.tableId
							+ "' name='"
							+ this.tableName
							+ "'>\n");
			// 表格头信息开始
			out
					.append("<tr align='center' style='Z-INDEX:5; POSITION: relative;TOP: expression(this.offsetParent.scrollTop);'>\n");
			int columnCount = this.displayColInfoList.size();
			DisplayColumnInfo displayColumn = null;
			// 得到表格头信息
			String tableHeaderTitle = "&nbsp;";
			String width = "nowrap";
			for (int i = 0; i < columnCount; i++) {
				displayColumn = (DisplayColumnInfo) displayColInfoList.get(i);
				if (displayColumn == null) {
					continue;
				}
				tableHeaderTitle = displayColumn.getTitle();
				if (tableHeaderTitle == null || tableHeaderTitle.length() == 0) {
					tableHeaderTitle = "&nbsp;";
				}
				if (displayColumn.getWidth() != null
						&& displayColumn.getWidth().length() > 0) {
					width = " width='" + displayColumn.getWidth() + "' ";
				} else {
					width = "nowrap";
				}
				if (displayColumn.isCanOrder()) {
					// 加了排序
					String headTdClassName = "query_list_meta_td";
					String crtOrderField = this.pageInfo.getOrderField();
					String crtColumnName = displayColumn.getProperty()
							.toLowerCase().trim();
					if (crtOrderField != null && crtOrderField.length() > 0) {
						// 有排序字段
						crtOrderField = crtOrderField.toLowerCase().trim();
						if (crtOrderField.startsWith(crtColumnName + " ")) {
							if (crtOrderField.endsWith("desc")) {
								// 倒序
								headTdClassName = "query_list_meta_td";
							} else {
								// 升序
								headTdClassName = "query_list_meta_td";
							}
						} else if (crtOrderField
								.equalsIgnoreCase(crtColumnName)) {
							// 默认升序
							headTdClassName = "query_list_meta_td";
						}
					}
					// 暂不加排序
					String orderProperty = null;
					if (displayColumn.getOrderProperty() != null
							&& !"".equals(displayColumn.getOrderProperty())) {
						orderProperty = displayColumn.getOrderProperty();
					} else {
						orderProperty = displayColumn.getProperty();
					}
					String orderImg = request.getContextPath() + this.imgPath;
					String orderField = "";
					if (this.pageInfo.getOrderField() != null) {
						orderField = this.pageInfo.getOrderField()
								.toLowerCase();// 转小写
					}
					if (orderField.startsWith(orderProperty.toLowerCase())) {
						// 当前字段排序
						if (orderField.endsWith("asc")) {
							// 升序
							orderImg += "arrow_up.png";
						} else {
							// 降序
							orderImg += "arrow_down.png";
						}
					} else {
						orderImg += "arrow_off.png";
					}
					out.append("<td class='" + headTdClassName + "' " + width
							+ " snowrap onClick=\"javascript:orderFieldClick('"
							+ orderProperty
							+ "');\" style='cursor:hand;' title='点击排序'>"
							+ tableHeaderTitle + "<img src='" + orderImg + "'>"
							+ "</td>\n");
				} else {
					// 是表单对象，没有排序
					out.append("<td class='query_list_meta_td' snowrap "
							+ width + " >" + tableHeaderTitle + "</td>\n");
				}
			}
			out.append("</tr>\n");
			// 列表数据对象集合
			List tableDataList = this.pageInfo.getDataList();
			if (tableDataList == null) {
				tableDataList = new ArrayList();
			}
			// 每一行数据
			Object rowEntityObj = null;
			// 当前页记录数
			int rowCount = tableDataList.size();
			// 页数
			int pageSize = this.pageInfo.getPageSize();
			if (rowCount == 0) {
				pageSize = this.getTableRowCount();
			}
			int showRowCount = 0; // 已显示的行数
			String trClass = "query_list_data_tr1";
			String tdClass = "query_list_data_td1";
			// 显示数据
			for (int i = 0; i < rowCount; i++) {
				// 每一行数据
				rowEntityObj = tableDataList.get(i); // 实体对象
				if (rowEntityObj == null) {
					continue;
				}
				if (showRowCount % 2 == 0) {
					trClass = "query_list_data_tr1";
					tdClass = "query_list_data_td1";
				} else {
					trClass = "query_list_data_tr2";
					tdClass = "query_list_data_td2";
				}
				out.append("<tr style='cursor:hand;' class='" + trClass
						+ "' index='" + i + "'");
				if (StringUtils.notNullAndSpace(displayRowInfo.getOnClick())) {
					out
							.append(" onClick='" + displayRowInfo.getOnClick()
									+ "'");
				}
				if (StringUtils.notNullAndSpace(displayRowInfo.getOnDblClick())) {
					out.append(" onDblClick='" + displayRowInfo.getOnDblClick()
							+ "'");
				}
				if (StringUtils
						.notNullAndSpace(displayRowInfo.getOnMouseOver())) {
					out.append(" onmouseover='"
							+ displayRowInfo.getOnMouseOver() + "'");
				}
				if (StringUtils.notNullAndSpace(displayRowInfo.getOnMouseOut())) {
					out.append(" onmouseout='" + displayRowInfo.getOnMouseOut()
							+ "'");
				}
				out.append(">\n");
				// 实际数据
				for (int j = 0; j < columnCount; j++) {
					displayColumn = (DisplayColumnInfo) this.displayColInfoList
							.get(j);
					if (displayColumn == null) {
						continue;
					}
					// 当前序号
					displayColumn.setColumnIndex(i);
					out.append("<td class='" + tdClass + "' align='"
							+ displayColumn.getAlign() + "'>");
					out.append(getPropertyValue(rowEntityObj, displayColumn));
					out.append("</td>\n");
				}
				out.append("</tr>\n");
				showRowCount++;
			}
			// 没有数据显示空白
			for (int i = rowCount; i < pageSize; i++) {
				if (showRowCount % 2 == 0) {
					trClass = "query_list_data_tr1";
					tdClass = "query_list_data_td1";
				} else {
					trClass = "query_list_data_tr2";
					tdClass = "query_list_data_td2";
				}
				out.append("<tr class='" + trClass + "'>\n");
				if (displayColInfoList.size() > 0) {
					String colTitle = ((DisplayColumnInfo) displayColInfoList
							.get(0)).getTitle();
					if (colTitle.indexOf("radio") != -1) {
						out
								.append("<td class='"
										+ tdClass
										+ "' ><input type='radio' disabled='true'></td>\n");
					} else {
						out
								.append("<td class='"
										+ tdClass
										+ "' ><input type='checkbox' disabled='true'></td>\n");
					}
				} else {
					out
							.append("<td class='"
									+ tdClass
									+ "' ><input type='checkbox' disabled='true'></td>\n");
				}
				out.append("<td class='" + tdClass + "' align='center'>"
						+ (i + 1) + "</td>\n");
				for (int j = 2; j < columnCount; j++) {
					out.append("<td class='" + tdClass + "' >&nbsp;</td>\n");
				}
				out.append("</tr>\n");
				showRowCount++;
			}
			// 结束
			out.append("</table>\n");
			out.append("</td>\n");
			out.append("</tr>\n");
			out.append("</table>\n");
			out.append("</div>\n");
			out.append("</td>\n");
			out.append("</tr>\n");
			out.append("<tr>\n");
			out.append("<td>\n");
			// 生成分页导航条
			out
					.append("<table width='100%' border='0' cellspacing='0' cellpadding='0' class='"
							+ this.tableStyleClass + "'>\n");
			out.append("<tr>\n");
			out.append("<td align='left' class='tail_Td1'>");
			out.append("&nbsp;&nbsp;&nbsp;&nbsp;当前第"
					+ this.pageInfo.getPageNo() + "页/共"
					+ this.pageInfo.getPageCount() + "页（共"
					+ this.pageInfo.getTotalSize() + "条记录）&nbsp;&nbsp");
			out.append("</td>\n");
			out.append("<td align='right' class='tail_Td2'>\n");
			// 前一页及后一页
			if (this.pageInfo.getPageCount() > 1
					&& this.pageInfo.getPageNo() > 1) {
				out
						.append(
								"\t\t<span class='ListUtil_NavText' onclick=\"gotoPage('")
						.append(1)
						.append("');\" style='cursor:hand;'>[首页]</span>\r\n")
						.append(
								"\t\t<span class='ListUtil_NavText' onclick=\"gotoPage('")
						.append(pageInfo.getPageNo() - 1).append(
								"');\" style='cursor:hand;'>[上一页]</span>\r\n");
			}
			// 下一页
			if (this.pageInfo.getPageCount() > 1
					&& pageInfo.getPageNo() < pageInfo.getPageCount()) {
				out
						.append(
								"\t\t<span class='ListUtil_NavText' onclick=\"gotoPage('")
						.append(pageInfo.getPageNo() + 1)
						.append("');\" style='cursor:hand;'>[下一页]</span>\r\n")
						.append(
								"\t\t<span class='ListUtil_NavText' onclick=\"gotoPage('")
						.append(pageInfo.getPageCount()).append(
								"');\" style='cursor:hand;'>[尾页]</span>\r\n");
			}
			String jsScript = "onkeyup=\"this.value= this.value.replace(/[^; "
					+ "\\" + "d]/g,'');\"";
			out
					.append("\t\t<span class='ListUtil_inputSpan'>")
					.append(" 　转到第")
					.append(
							"<input type='text' autocheck='1' cannull='0' "
									+ jsScript
									+ " datatype='number' max_value='")
					.append(pageInfo.getPageCount())
					.append(
							"' min_value='1' propname='页号' name='publicquery_gotopage' class='ListUtil_input' onkeypress='gotoInputPageKeyPress();'>")
					.append("页&nbsp;\r\n</span>\r\n");
			out.append("</td>\n");
			out.append("</tr>\n");
			out.append("</table>\n");
			out.append("</td>\n");
			out.append("</tr>\n");
			out.append("</table>\n");
			out.append("</form>\n");
			JspWriter outPrint = null;
			try {
				outPrint = pageContext.getOut();
				if (outPrint != null) {
					outPrint.write(out.toString().trim());
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			this.log.error(ex.getMessage(), ex);
		}
		this.log.debug("结束生成数据");
		return EVAL_PAGE;
	}

	/**
	 * 得到当前属性值
	 * 
	 * @return String
	 */
	private String getPropertyValue(final Object rowEntityObj,
			DisplayColumnInfo displayColumnInfo) {

		String propertyValue = "&nbsp;";
		if (rowEntityObj == null) {
			System.err.println("rowEntityObj is null");
			return propertyValue;
		}
		if (displayColumnInfo == null) {
			System.err.println("displayColumnInfo is null");
			return propertyValue;
		}
		String propertyName = displayColumnInfo.getProperty();
		if (!StringUtils.notNullAndSpace(propertyName)) {
			System.err.println("propertyName is null");
			return propertyValue;
		}

		String propertyNameArray[] = propertyName.split(",");
		List<String> propertyValueLst = new ArrayList<String>();
		if (propertyNameArray != null && propertyNameArray.length > 0) {
			String tmpPropertyValue = "";
			// OgnlValueStack stack = new OgnlValueStack();
			try {
				for (int i = 0; i < propertyNameArray.length; i++) {
					if (!StringUtils.notNullAndSpace(propertyNameArray[i])) {
						propertyValueLst.add("");
						continue;
					}
					if (propertyNameArray[i].toLowerCase().startsWith(
							this.rowStatusIndex.toLowerCase())) {
						// 序号
						String indexProperty = StringUtils.replace(
								propertyNameArray[i].toLowerCase(),
								this.rowStatusIndex.toLowerCase(), String
										.valueOf(displayColumnInfo
												.getColumnIndex()));

						tmpPropertyValue = String.valueOf(InterpreterUtil
								.eval(indexProperty));
					} else if (propertyNameArray[i].toLowerCase().startsWith(
							this.rootPathTag.toLowerCase())) {
						// 根路径
						String indexProperty = StringUtils.replace(
								propertyNameArray[i].toLowerCase(),
								this.rootPathTag.toLowerCase(), String
										.valueOf(displayColumnInfo
												.getColumnIndex()));
						tmpPropertyValue = this.rootPath;
					} else {
						// 数据
						Object value = PropertyUtils.getProperty(rowEntityObj,
								propertyNameArray[i]);
						if (value == null || value.toString().length() == 0) {
							tmpPropertyValue = "";
							continue;
						}
						// 属性值
						tmpPropertyValue = value.toString();
						try {
							if (StringUtils.notNullAndSpace(displayColumnInfo
									.getFormat())
									&& StringUtils
											.notNullAndSpace(tmpPropertyValue)) {
								// 得到属性类型
								String propertyType = PropertyUtils
										.getPropertyType(rowEntityObj,
												propertyNameArray[i]).getName();
								if (dateType.equalsIgnoreCase(propertyType)) {
									// 格式化日期,yyyy-MM-dd
									tmpPropertyValue = DateUtils
											.formatByFormatRule((Date) value,
													displayColumnInfo
															.getFormat());
								} else if ("byte"
										.equalsIgnoreCase(propertyType)
										|| "short"
												.equalsIgnoreCase(propertyType)
										|| "int".equalsIgnoreCase(propertyType)
										|| "long"
												.equalsIgnoreCase(propertyType)
										|| "float"
												.equalsIgnoreCase(propertyType)
										|| "double"
												.equalsIgnoreCase(propertyType)
										|| integerType
												.equalsIgnoreCase(propertyType)
										|| longType
												.equalsIgnoreCase(propertyType)
										|| floatType
												.equalsIgnoreCase(propertyType)
										|| doubleType
												.equalsIgnoreCase(propertyType)
										|| bigDecimalType
												.equalsIgnoreCase(propertyType)) {
									// 格式化金额 #,###.00
									DecimalFormat decimalFormat = new DecimalFormat(
											displayColumnInfo.getFormat());
									tmpPropertyValue = decimalFormat
											.format(Double
													.parseDouble(tmpPropertyValue));
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
							this.log.error(e.getMessage(), e);
						}
					}
					propertyValueLst.add(tmpPropertyValue);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 列显示模式
			String thisColumnPattern = displayColumnInfo.getPattern();
			if (StringUtils.notNullAndSpace(thisColumnPattern)) {
				MessageFormat patternFormat = new MessageFormat("");
				propertyValue = patternFormat.format(thisColumnPattern,
						propertyValueLst.toArray());
			} else if (StringUtils.notNullAndSpace(displayColumnInfo
					.getExpression())) {
				// 表达式,如是否删除，数据为0/1，实际显示为中文描述，"{0}==1?'是':'否'"
				MessageFormat patternFormat = new MessageFormat("");
				propertyValue = patternFormat.format(displayColumnInfo
						.getExpression(), propertyValueLst.toArray());
				propertyValue = (String) InterpreterUtil.eval(propertyValue);
			} else {
				String tmpPrppertyValue = propertyValueLst.toString();
				propertyValue = tmpPrppertyValue.substring(1, tmpPrppertyValue
						.length() - 1);
			}
		}
		if (!StringUtils.notNullAndSpace(propertyValue)) {
			propertyValue = "&nbsp;";
		}

		return propertyValue;
	}

	/**
	 * 退出
	 */
	public void release() {
		super.release();
		this.attributeName = "";
		this.formAction = "";
		this.tableHeight = "";
		this.tableId = "";
		this.imgPath = "";
		this.tableName = "";
		this.tableStyleClass = "";
		this.tableWidth = "100%";
		this.entityClazz = "";
		this.tableRowCount = 15;
	}

	public String getServiceFactory() {
		return serviceFactory;
	}

	public void setServiceFactory(String serviceFactory) {
		this.serviceFactory = serviceFactory;
	}

	public int getTableRowCount() {
		return tableRowCount;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
}
