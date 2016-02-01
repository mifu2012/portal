<!--用户数统计-->
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<title>用户数统计</title>
<style>
#tagr {
	background-color: #eff0f2
}
</style>
<link href="<%=path%>/common/css/cssz.css" rel="stylesheet"
	type="text/css" />
<link href="<%=path%>/common/css/base.css" rel="stylesheet"
	type="text/css" />
<link href="<%=path%>/common/css/lwt.css" rel="stylesheet"
	type="text/css" />
<!--<link href="<%=path%>/common/css/style.css" rel="stylesheet"
	type="text/css" />-->
<script src="<%=path%>/common/js/My97DatePicker/WdatePicker.js"
	type="text/javascript"></script>
<script src="<%=path%>/common/js/miaov.js" type="text/javascript"></script>
<script src="<%=path%>/common/js/amcharts.js" type="text/javascript"></script>
<script src="<%=path%>/common/js/raphael.js" type="text/javascript"></script>
<script src="<%=path%>/common/js/arale.js" type="text/javascript"></script>
<script src="<%=path%>/common/js/FusionCharts_Trial.js"
	type="text/javascript"></script>
<%-- <script src="<%=path%>/common/js/arale.core-1.1.js"
	type="text/javascript" charset="utf-8"></script> --%>
<script src="<%=path%>/common/amchart/stock/amstock/swfobject.js"
	type="text/javascript"></script>
<script src="<%=path%>/common/js/amfallback.js" type="text/javascript"></script>
</head>
<script type="text/javascript">
	var amchart_key = "${amchartKey}";
	window.onload=function()
	{
		 var kpicodelist =
			  [
				   <c:forEach  items="${trendListList}" var="item" varStatus="sel">
		            <c:if test="${sel.index!=0}">,</c:if>  
				            [
			       		          <c:forEach  items="${item.properties}" var="it" varStatus="selProperties">
			       		            <c:choose>
			       		              <c:when test="${selProperties.index==0}">"${it.value}"</c:when>
			       		              <c:otherwise>,"${it.value}"</c:otherwise>
			       		           </c:choose>
			       		          </c:forEach>
			            	]
				   </c:forEach>
		       ]
				   	    var kpif = "";
						var childsel = parseInt($("J_child_select").node.value);
						var parentsel = parseInt($("J_index_select").node.value);
						kpif = kpicodelist[parentsel][childsel];
				        var so1 = new SWFObject("<%=path%>/common/amchart/stock/amstock/amstock.swf", "amline1", "780", "300", "9", "#EFF0F2"); 
				        so1.addVariable("path", "<%=path%>/common/amchart/stock/amstock/");
						so1.addVariable("chart_id", "amline1");
						so1.addParam("wmode","transparent");
						so1.addVariable("settings_file", escape("<%=path%>/stockChart/showStockChart?kpiCodes="+kpif+"&kpiType=1&reportDate=${date}"));
						so1.addVariable("key", amchart_key);
						so1.write("chartdiv2");
		   		
			       		$("J_index_select").change(function(){
			       			changeChart();
			       		});
			       		$("J_child_select").change(function(){
			       			changeChart();
			       		});
			       		function changeChart(){
			       			var kpi = "";
			       			var parentsel = parseInt($("J_index_select").node.value);
			       			var childsel  = parseInt($("J_child_select").node.value);
			       			kpi = kpicodelist[parentsel][childsel];
			       			so1.addVariable("settings_file", escape("<%=path%>/stockChart/showStockChart?kpiCodes="+kpi+"&kpiType=1&reportDate=${date}"));
			       			so1.write("chartdiv2");
			       	}
	}
</script>
<body>
	<div id="tagr"
		style="padding: 10px 0px 0px 0px; height: 145px; background: #eff0f2; border: 1px solid #dbe1e6; border-top: 0px;">
		<c:forEach items="${userCountStatisticsList}" var="userCount">
			<div
				style="width: 170px; line-height: 30px; height: auto; float: left;">
				<table style="margin-left: 4px">
					<tr>
						<td valign="top" width="95px">${userCount.kpiName}</br>(${userCount.fromTaobaoModule})</br>
							<span
							style="font-size: 18px; font-weight: bold; padding-left: 10px">${userCount.totalValue}</span>
						</td>
						<td width="80px">
							<table style="border-right: 1px solid #ccc;">
								<tr>
									<td
										style="border-bottom: 1px solid #ccc; width: 40px; text-align: left;">WAP:</td>
									<td
										style="border-bottom: 1px solid #ccc; width: 40px; text-align: right; padding-right: 2px">${userCount.fromTaobao}</td>
								</tr>
								<tr>
									<td
										style="border-bottom: 1px solid #ccc; width: 40px; text-align: left;">外部:</td>
									<td
										style="border-bottom: 1px solid #ccc; width: 40px; text-align: right; padding-right: 2px">${userCount.fromOutter}</td>
								</tr>
								<tr>
									<td
										style="border-bottom: 1px solid #ccc; width: 40px; text-align: left;">站内:</td>
									<td
										style="border-bottom: 1px solid #ccc; width: 40px; text-align: right; padding-right: 2px">${userCount.fromOther}</td>
								</tr>
								<tr>
									<td style="width: 40px; text-align: left;">其它:</td>
									<td style="width: 40px; text-align: right; padding-right: 2px">${userCount.fromInner}</td>
								</tr>
							</table></td>
					</tr>
				</table>
			</div>
		</c:forEach>
	</div>
	<div style="display: none;" id="userCountStatisticsChartDiv">
		<select id="J_index_select">
			<c:forEach items="${leftList}" var="item" varStatus="sel">
				<option value="${sel.index}">${item.name}</option>
			</c:forEach>
		</select>
		<c:forEach items="${leftList}" var="item" varStatus="sel">
			<c:choose>
				<c:when test="${sel.index eq 0}">
					<select class="J_fsl" id="J_child_select">
						<c:forEach items="${item.properties }" var="it" varStatus="selsel">
							<option value="${selsel.index}">${it.name }</option>
						</c:forEach>
					</select>
				</c:when>
			</c:choose>
		</c:forEach>
		<div id="userCountStatisticsChart"></div>
	</div>
</body>
</html>
