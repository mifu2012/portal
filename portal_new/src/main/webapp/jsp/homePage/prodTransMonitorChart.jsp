<!--业务笔数监控矩形图-->
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String moduleId=request.getParameter("moduleId");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>业务笔数监控矩形图</title>
<style type='text/css'>
 body{ background:#eff0f2}
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
	
<script src="<%=path%>/common/amchart/column/amcolumn/swfobject1.js"
	type="text/javascript"></script>
<script src="<%=path%>/common/amchart/pie/ampie/swfobject.js"
	type="text/javascript"></script>
<!-- <script src="https://static.alipay.com/ar/arale.core-1.1.js"
	type="text/javascript" charset="utf-8"></script> -->
<script src="<%=path%>/common/js/amfallback.js" type="text/javascript"></script>
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
	var flashMovie;
	var chartIsLoad=false;
	function amChartInited (chart_id){
		flashMovie = document.getElementById("amline3");
		flashMovie.hideGraph(2);
	}	
	window.onload=function()
	{
		var width=document.body.clientWidth;
	    var parentFrm=window.parent.document.getElementById("iframepage_<%=moduleId%>");
	    var height=300;
	    var parentFrmHeight=(parentFrm==null?300:parentFrm.height);
		  if(parentFrmHeight.indexOf("px")!=-1)
		  {
			  height=parentFrmHeight.substring(0,parentFrmHeight.indexOf("px"));
		  }
		var otherHieght=document.getElementById("secondTable").offsetHeight+document.getElementById("firstTable").offsetHeight+30;
		var healthtre = new SWFObject("<%=path%>/common/amchart/amline/amline.swf", "amline3",width, height-otherHieght, "8", "#FFFFFF");
		healthtre.addParam("wmode", "transparent");
		healthtre.addVariable("path", "<%=path%>/common/amchart/amline/");
		healthtre.addVariable("chart_id", "amline3");
		healthtre.addVariable("chart_settings",
				encodeURIComponent("#SLITERAL(${transAmchartSettings})"));
		healthtre.addVariable("chart_data",
				encodeURIComponent("#SLITERAL(${transAmchartData})"));
		healthtre.addVariable("key", amchart_key);
		healthtre.write("prodTransMonitorChart");

		$A($$(".J-fjd")).each(function(el) {
			var className="active"+el.attr('data-id');
			el.click(function() {
				if (el.hasClass(className)) {
					el.removeClass(className);
					flashMovie.hideGraph(el.attr('data-id'));
				} else {
					el.addClass(className);
					flashMovie.showGraph(el.attr('data-id'));
				}
			});
		});
	}
</script>
<body>
	<table width="100%" border="0" cellspacing="0" cellpadding="0"
		style="float: left; border: 1px solid #dbe1e6; border-top: 0px; border-bottom: 0px" id="firstTable">
		<tr>
			<td width="100%" valign="top" class="fjd-png">
			<a href="javascript:;" data-id="1" value="峰　值" class="J-fjd fjd-top active1"></a> <a href="javascript:;" data-id="2"
				value="均　值" class="J-fjd fjd-avg"></a> 
				<a href="javascript:;" data-id="0" value="当日值" class="J-fjd fjd-now active0"></a>
			</td>
		</tr>
		<tr>
			<td width="100%"><div id="prodTransMonitorChart"></div>
			</td>
		</tr>
	</table>
		<table border="0" cellspacing="0" cellpadding="0"
			style="height: 50px; float: left;font-size:12px;" id="secondTable">
			<tr style="height: auto;">
				<td width="45px" align='center'>变化<br/>幅度</td>
			    <c:forEach items="${prodTransCountList}" var="prodTranInfo">
				  <td>
					<c:choose>
						<c:when test="${prodTranInfo.trend eq 'up'}">+${prodTranInfo.changeRate}% &nbsp;</c:when>
						<c:otherwise>${prodTranInfo.changeRate}% &nbsp;</c:otherwise>
					</c:choose>
				 </td>
			   </c:forEach>
			</tr>
		</table>
	<!-- 业务笔数监控数据 -->
	<div style="display: none;" id="prodTransMonitorDataDiv">
		<table
			style="width: 500px; height: auto; float: left; line-height: 24px; text-align: center; border: 1px solid #ccc;">
			<tr style="background: #333; color: #fff; font-weight: bold;">
				<td>产品名称</td>
				<td>当日值(${unit})</td>
				<td>均值(${unit})</td>
				<td>变化幅度</td>
				<td>峰值(${unit})</td>
				<td>峰值时间</td>
				<td>求助量</td>
			</tr>
			<c:if test="not empty prodTransCountList">
				<c:forEach items="${prodTransCountList}" var="tranInfo"
					varStatus="sel">
					<tr
						<c:if test="${sel.index%2 eq 0}">style="background:#eff0f2;height:24px;"</c:if>>
						<td
							style="border-left: 1px solid #fff; border-bottom: 1px solid #fff; height: 24px;">${tranInfo.prodName}</td>
						<td
							style="border-left: 1px solid #fff; border-bottom: 1px solid #fff; height: 24px;">${tranInfo.nowValue}</td>
						<td
							style="border-left: 1px solid #fff; border-bottom: 1px solid #fff; height: 24px;">${tranInfo.avgValue}</td>
						<td
							style="border-left: 1px solid #fff; border-bottom: 1px solid #fff; height: 24px;">${tranInfo.changeRate}</td>
						<td
							style="border-left: 1px solid #fff; border-bottom: 1px solid #fff; height: 24px;">${tranInfo.topValue}</td>
						<td
							style="border-left: 1px solid #fff; border-bottom: 1px solid #fff; height: 24px;">${tranInfo.topTime}</td>
						<td
							style="border-left: 1px solid #fff; border-bottom: 1px solid #fff; height: 24px;">${tranInfo.helpCount}</td>
					</tr>
				</c:forEach>
			</c:if>
		</table>
	</div>
</body>
</html>
