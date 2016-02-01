<!--产品全图统计-->
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>产品全图统计</title>
<style>
body {
	background: #eff0f2
}

#tagr {
	background-color: #eff0f2
}
</style>
<link href="<%=path%>/common/css/cssz.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/common/css/base.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/common/css/lwt.css" rel="stylesheet" type="text/css" />
<!--<link href="<%=path%>/common/css/style.css" rel="stylesheet" type="text/css" />-->
<script src="<%=path%>/common/js/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="<%=path%>/common/js/miaov.js" type="text/javascript"></script>
<script src="<%=path%>/common/js/amcharts.js" type="text/javascript"></script>
<script src="<%=path%>/common/js/raphael.js" type="text/javascript"></script>
<script src="<%=path%>/common/js/arale.js" type="text/javascript"></script>
<script src="<%=path%>/common/js/FusionCharts_Trial.js" type="text/javascript"></script>
<!--
<script src="<%=path%>/common/js/arale.core-1.1.js"
	type="text/javascript" charset="utf-8"></script>
-->
<script src="<%=path%>/common/amchart/stock/amstock/swfobject.js"
	type="text/javascript"></script>
<script src="<%=path%>/common/js/amfallback.js" type="text/javascript"></script>
</head>
<script type="text/javascript">
	var amchart_key = "${amchartKey}";
	window.onload=function()
	{
	   	//产品全图
	    var so = new SWFObject("<%=path%>/common/amchart/pie/ampie/ampie1.swf", "ampie", "200", "175", "8.0.0", "#EFF0F2");
	   	so.addVariable("path", "<%=path%>/common/amchart/pie/ampie/");
	   	so.addVariable("settings_file", escape("<%=path%>/common/amchart/pie/ampie/commonpie-settings-showone-rate.xml"));
	    so.addVariable("data_file",escape("<%=path%>/productAmchart/chartShow?queryDate=${date}&random="
								+ Math.random()));
		so.addVariable("key", amchart_key);
		so.addParam("wmode", "transparent");
		so.write("platFormUserPie");
	}
</script>
<body>
	<div id="tagr"
		style="height: auto; width: 300px; background: #eff0f2; border: 1px solid #dbe1e6; border-top: 0px; padding-bottom: 0px; overflow: hidden;">
		<table border="0" cellspacing="0" cellpadding="0"
			style="margin-left: 12px; margin-bottom: 10px; line-height: 25px"">
			<tr>
				<td width="100px">
					<table border="0" cellspacing="0" cellpadding="0" width="100px"
						class="index-box">
						<tr style="line-height: 20px">
							<td width="100px" style="border-bottom: 1px solid #fff"
								class="<c:if test="${platFormDataTB.trend eq 'up'}">proall-top-left-up</c:if>
								<c:if test="${platFormDataTB.trend eq 'down'}"> proall-top-left-down</c:if>
								<c:if test="${platFormDataTB.trend eq 'bal'}">proall-top-left-equal</c:if> fchencolor"><span
								style="font-size: 16px; font-weight: bold; padding-left: 10px">WAP</span><br />
								<span style="font-size: 16px; padding-left: 10px">${platFormDataTB.value}</span>
							</td>
							<td width="20px" style="border-bottom: 1px solid #fff"><c:if
									test="${platFormDataTB.trend eq 'up'}">

									<img src="<%=path%>/common/images/lwt_5.png" alt="上升" />

								</c:if> <c:if test="${platFormDataTB.trend eq 'down'}">

									<img src="<%=path%>/common/images/lwt_6 .png" alt="下降" />

								</c:if></td>
						</tr>
						<tr style="line-height: 20px">
							<td width="100px" style="border-bottom: 1px solid #fff"
								class="<c:if test="${platFormDataOUT.trend eq 'up'}">proall-top-left-up</c:if>
								<c:if test="${platFormDataOUT.trend eq 'down'}"> proall-top-left-down</c:if>
								<c:if test="${platFormDataOUT.trend eq 'bal'}">proall-top-left-equal</c:if> fqincolor"><span
								style="font-size: 16px; font-weight: bold; padding-left: 10px">外部</span><br />
								<span style="font-size: 16px; padding-left: 10px">${platFormDataOUT.value}</span>
							</td>
							<td width="20px" style="border-bottom: 1px solid #fff"><c:if
									test="${platFormDataOUT.trend eq 'up'}">

									<img src="<%=path%>/common/images/lwt_5.png" alt="上升" />

								</c:if> <c:if test="${platFormDataOUT.trend eq 'down'}">

									<img src="<%=path%>/common/images/lwt_6 .png" alt="下降" />

								</c:if></td>
						</tr>
						<tr style="line-height: 20px">
							<td width="100px" style="border-bottom: 1px solid #fff"
								class="<c:if test="${platFormDataIN.trend eq 'up'}">proall-top-left-up</c:if>
								<c:if test="${platFormDataIN.trend eq 'down'}"> proall-top-left-down</c:if>
								<c:if test="${platFormDataIN.trend eq 'bal'}">proall-top-left-equal</c:if> fzicolor"
								style="border-bottom:none;""><span
								style="font-size: 16px; font-weight: bold; padding-left: 10px">站内</span><br />
								<span style="font-size: 16px; padding-left: 10px">${platFormDataIN.value}</span>
							</td>
							<td width="20px" style="border-bottom: 1px solid #fff"><c:if
									test="${platFormDataIN.trend eq 'up'}">

									<img src="<%=path%>/common/images/lwt_5.png" alt="上升" />

								</c:if> <c:if test="${platFormDataIN.trend eq 'down'}">

									<img src="<%=path%>/common/images/lwt_6 .png" alt="下降" />

								</c:if></td>
						</tr>
					</table>
				</td>
				<td width="135px" style="text-align: left">
					<!-- 产品全图饼图 -->
					<div id="platFormUserPie"></div>
				</td>
			</tr>
		</table>
		<div style="width: 100%; float: left; background: #eff0f2;">
			<table border="0" cellspacing="0" cellpadding="0"
				style="line-height: 15px; margin-left: 1px">	
				<tr style="height: 30px;">
					<td 
						style="width:25%; border-bottom: 1px solid #ccc; overflow: hidden;font-size:12px;text-align:right;font-weight: bold;"><!-- 今日销售量 -->${columnInfoMap.INDEX_PRODUCT_PIE_CHART_TODAY_SALE.columnName}:</td>
					<td style="width:25%; border-bottom: 1px solid #ccc;font-size:12px;text-align:left;">${columnCodeRKpiDataMap.INDEX_PRODUCT_PIE_CHART_TODAY_SALE.showValue}${columnCodeRKpiDataMap.INDEX_PRODUCT_PIE_CHART_TODAY_SALE.unit}
					</td>
					<td
						style="width:25%;border-bottom: 1px solid #ccc; overflow: hidden;font-size:12px;text-align:right;font-weight: bold;"><!-- 今日目标销售量 -->${columnInfoMap.INDEX_PRODUCT_PIE_CHART_TODAY_GOAL_SALE.columnName}:</td>
					<td style="width:25%;border-bottom: 1px solid #ccc;font-size:12px;text-align:left;">${columnCodeRKpiDataMap.INDEX_PRODUCT_PIE_CHART_TODAY_GOAL_SALE.showValue}${columnCodeRKpiDataMap.INDEX_PRODUCT_PIE_CHART_TODAY_GOAL_SALE.unit}
					</td>
					
					
					
				</tr>
				<tr style="height: 30px;">
					<td
						style="width:25%;border-bottom: 1px solid #ccc; overflow: hidden;font-size:12px;text-align:right;font-weight: bold;"><!--月累计销售-->${columnInfoMap.INDEX_PRODUCT_PIE_CHART_MONTH_SALE.columnName}:</td>
					<td style="width:25%;border-bottom: 1px solid #ccc;font-size:12px;text-align:left;">${columnCodeRKpiDataMap.INDEX_PRODUCT_PIE_CHART_MONTH_SALE.showValue}${columnCodeRKpiDataMap.INDEX_PRODUCT_PIE_CHART_MONTH_SALE.unit}
					</td>
					
					<td
						style="width:25%;border-bottom: 1px solid #ccc; overflow: hidden;font-size:12px;text-align:right;font-weight: bold;"><!--月目标销售-->${columnInfoMap.INDEX_PRODUCT_PIE_CHART_MONTH_GOAL_SALE.columnName}:</td>
					<td style="width:25%;border-bottom: 1px solid #ccc;font-size:12px;text-align:left;">${columnCodeRKpiDataMap.INDEX_PRODUCT_PIE_CHART_MONTH_GOAL_SALE.showValue}${columnCodeRKpiDataMap.INDEX_PRODUCT_PIE_CHART_MONTH_GOAL_SALE.unit}
					</td>
					
					
				</tr>
 				<tr style="height: 30px;">
					<td
						style="width:25%;border-bottom: 1px solid #ccc; overflow: hidden;font-size:12px;font-size:12px;text-align:right;font-weight: bold;"><!--年累计销售-->${columnInfoMap.INDEX_PRODUCT_PIE_CHART_YEAR_SALE.columnName}:</td>
					<td style="border-bottom: 1px solid #ccc;font-size:12px;text-align:left;">${columnCodeRKpiDataMap.INDEX_PRODUCT_PIE_CHART_TODAY_GOAL_SALE.showValue}${columnCodeRKpiDataMap.INDEX_PRODUCT_PIE_CHART_TODAY_GOAL_SALE.unit}
					</td>
					<td
						style="width:25%;border-bottom: 1px solid #ccc; overflow: hidden;font-size:12px;text-align:right;font-weight: bold;"><!--年目标销售额-->${columnInfoMap.INDEX_PRODUCT_PIE_CHART_YEAR_GOAL_SALE.columnName}:</td>
					<td style="width:25%;border-bottom: 1px solid #ccc;font-size:12px;text-align:left;">${columnCodeRKpiDataMap.INDEX_PRODUCT_PIE_CHART_YEAR_GOAL_SALE.showValue}${columnCodeRKpiDataMap.INDEX_PRODUCT_PIE_CHART_YEAR_GOAL_SALE.unit}
					</td>
				</tr> 
			    <tr style="height: 30px;">
					<td
						style="width:25%;border-bottom: 1px solid #ccc; overflow: hidden;font-size:12px;text-align:right;font-weight: bold;"><!--总用户数-->${columnInfoMap.INDEX_PRODUCT_PIE_CHART_TOTAL_USER_COUNT.columnName}:</td>
					<td style="width:25%;border-bottom: 1px solid #ccc;font-size:12px;text-align:left;">${columnCodeRKpiDataMap.INDEX_PRODUCT_PIE_CHART_TOTAL_USER_COUNT.showValue}${columnCodeRKpiDataMap.INDEX_PRODUCT_PIE_CHART_TOTAL_USER_COUNT.unit}
					</td>
					<td
						style="width:25%;border-bottom: 1px solid #ccc; overflow: hidden;font-size:12px;text-align:right;font-weight: bold;"><!--总销量数-->${columnInfoMap.INDEX_PRODUCT_PIE_CHART_TOTAL_SALE_AMOUNT.columnName}:</td>
					<td style="width:25%;border-bottom: 1px solid #ccc;font-size:12px;text-align:left;">${columnCodeRKpiDataMap.INDEX_PRODUCT_PIE_CHART_TOTAL_SALE_AMOUNT.showValue}${columnCodeRKpiDataMap.INDEX_PRODUCT_PIE_CHART_TOTAL_SALE_AMOUNT.unit}
					</td>
				</tr>
			</table>
			<br />
		</div>
		<!-- 下面是原支付宝风格 -->
		<!--  
		<c:if test="not empty prodTranInfosRetList">
			<c:forEach items="${prodTranInfosRetList}" var="pro" varStatus="sel">
				<div style="width: 150px; float: left; background: #eff0f2;">
					<table border="0" cellspacing="0" cellpadding="0"
						style="line-height: 26px; margin-left: 3px">
						<tr>
							<td
								style=" border-bottom: 1px solid #ccc; overflow: hidden;">${(sel.index+1)}.${pro.prodName}</td>
							<td style="border-bottom: 1px solid #ccc">${pro.percentageForDisplay}
								<c:choose>
									<c:when test="${pro.trend eq 'up'}">+${pro.rankChange}</c:when>
									<c:when test="${pro.trend eq 'down'}">${pro.rankChange}</c:when>
									<c:otherwise></c:otherwise>
								</c:choose>
							</td>
						</tr>

					</table>
				</div>
				<c:if test="${(sel.index+1)%2==0}">
					</br>
				</c:if>
			</c:forEach>
		</c:if>
		-->
	</div>

</body>
</html>
