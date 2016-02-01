<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
<title>${systemName}首页</title>
<style>
#tagr {
	background-color: #eff0f2
}
</style>

<link href="<%=path%>/common/css/cssz.css" rel="stylesheet"
	type="text/css" />
<link href="<%=path%>/common/css/base.css" rel="stylesheet"
	type="text/css" />
<%-- <link href="<%=path%>/common/css/style.css" rel="stylesheet"
	type="text/css" /> --%>
<!--
<link href="<%=path%>/common/dwmis/css/lwt.css" rel="stylesheet" type="text/css"  />
-->

<%-- <script src="<%=path%>/common/js/miaov.js" type="text/javascript"></script> --%>
<script src="<%=path%>/common/js/amcharts.js" type="text/javascript"></script>
<script src="<%=path%>/common/js/raphael.js" type="text/javascript"></script>
<script src="<%=path%>/common/js/arale.js" type="text/javascript"></script>
<script src="<%=path%>/common/js/FusionCharts_Trial.js" type="text/javascript"></script>
<script src="https://static.alipay.com/ar/arale.core-1.1.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=path%>/common/js/amfallback.js" type="text/javascript"></script>
<script src="<%=basePath%>/common/js/jquery-1.4.3.min.js" type="text/javascript"></script>
<!--进度条样式-->
<style type="text/css">
.progress-bar {
	width: 241px;
	height: 21px;
	margin-left: 10px;
	margin-right: 20px;
	padding-right: 29px;
	background: url(<%=path%>/common/dwmis/images/bg-progress.gif) no-repeat
		0 -40px;
	text-align: left;
}

.progress-alert {
	background-position: 0 0;
}

.progress-bar .percent {
	display: inline-block;
	height: 21px;
	padding: 0 4px;
	background: url(<%=path%>/common/dwmis/images/bg-progress.gif) no-repeat
		0 -80px;
	color: #fff;
	text-align: right;
	font-weight: 100;
	line-height: 20px;
	white-space: nowrap;
}

.progress-alert .percent {
	background-position: 0 -120px;
}
</style>
<script type="text/javascript">

 	window.onload=function()
	{
		var loadingDiv=window.parent.document.getElementById("loading_wait_Div");
		if(loadingDiv==null) loadingDiv=window.parent.parent.document.getElementById("loading_wait_Div");
		if(loadingDiv!=null) loadingDiv.style.display="none";
		//自适应
		var iframeContentHeight = document.body.scrollHeight;
		parent.changeIframeSize(iframeContentHeight);
	} 

</script>
</head>
<body class="indexbody">
	<div class="index_home">
		<div id="tabContainer">

			<div style="clear: both"></div>
			<div id="con1">
				<div class="index_title_zbjk">
					<table width="100%" border="0" cellspacing="0" cellpadding="0"
						style="font-size: 12px;">
						<tr>
							<td width="23%" height="37"><div align="center">KPI指标</div>
							</td>
							<td width="30%"><div align="center">KPI指标完成率（3.5分）</div>
							</td>
							<td width="9%"><div align="center">当前完成值</div>
							</td>
							<td width="6%"><div align="center">单位</div>
							</td>
							<td width="9%"><div align="center">3.5分值</div>
							</td>
							<td width="8%"><div align="center">5分值</div>
							</td>
							<td width="15%"><div align="center">
									<a href="#">操作</a><a href="#"></a>
								</div>
							</td>
						</tr>
					</table>
				</div>
				<c:set var="tempDeptId">-1</c:set>
				<c:forEach items="${deptKpiDataList}" var="detpKpiData"
					varStatus="status">
					<!--${detpKpiData.dwmisMisDptmnt.depName}-->
					<c:if test="${status.index==0}">
						<!-- 第一个部门信息 -->
						<!--下面是新的tab开始-->
						<div class="index_report" style="border-bottom: 1px solid #c0c4c0"
							style="display:block;"
							id="tabContentDiv_${detpKpiData.dwmisMisDptmnt.depId}">
					</c:if>
					<c:if test="${status.index!=0}">
						<c:if test="${tempDeptId !=detpKpiData.dwmisMisDptmnt.depId}">
							<!-- 不同部门 -->
			</div>
			<div class="clear"></div>
			<!--tab1结束-->
			<!--下面是新的tab开始-->
			<div class="index_report" style="border-bottom: 1px solid #c0c4c0"
				style="display:<c:if test="${status.index!=0}">none;</c:if>"
				id="tabContentDiv_${detpKpiData.dwmisMisDptmnt.depId}">
				</c:if>
				</c:if>
				<c:set var="tempDeptId">${detpKpiData.dwmisMisDptmnt.depId}</c:set>
				<!--达标显示的是灰底-->
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					style="background-color: #f0f0f0; border-top: 1px solid #c0c4c0; color: #787c78">
					<tr>
						<!-- 指标名称 -->
						<td width="23%" height="37"
							style="border-left: 1px solid #c0c4c0; border-right: 1px solid #c0c4c0;"
							id='${detpKpiData.kpiTreands.kpiInfo.kpiCode}'>&nbsp;&nbsp;${detpKpiData.kpiTreands.kpiInfo.kpiNameShow}</td>
						<!-- 指标进度条${detpKpiData.kpiTreands.percent}% -->
						<td width="30%" style="border-right: 1px solid #c0c4c0;">
							<c:if test="${detpKpiData.kpiTreands.passGoal}">
								<div class="progress-bar">
							</c:if> 
							<c:if test="${detpKpiData.kpiTreands.passGoal==false}">
								<div class="progress-bar progress-alert">
							</c:if> 
							<c:if test="${detpKpiData.kpiTreands.percent>=100}">
								<span class="percent" style="_width: 100%; min-width: 100%;"> ${detpKpiData.kpiTreands.percent} 
									<c:if test="${detpKpiData.kpiTreands.isPTValue}">pt</c:if> 
									<c:if test="${detpKpiData.kpiTreands.isPTValue==false}">%</c:if>
								</span>
							</c:if> 
							<c:if test="${detpKpiData.kpiTreands.percent<100}">
								<span class="percent" style="_width:100%;min-width:${detpKpiData.kpiTreands.percent}%;"> ${detpKpiData.kpiTreands.percent} 
									<c:if test="${detpKpiData.kpiTreands.isPTValue}">pt</c:if> 
									<c:if test="${detpKpiData.kpiTreands.isPTValue==false}">%</c:if>
								</span>
							</c:if>
							</div>
						</td>
						<!-- 当前完成值 -->
						<td width="9%" style="border-right: 1px solid #c0c4c0;">
							<div align="center">${detpKpiData.kpiTreands.amountDone}</div>
						</td>
						<!-- 单位 -->
						<td width="6%" style="border-right: 1px solid #c0c4c0;">
							<div align="center">${detpKpiData.kpiTreands.kpiInfo.unitIdDesc}</div>
						</td>
						<!-- 3.5分值 -->
						<td width="9%" style="border-right: 1px solid #c0c4c0;">
							<div align="center">${detpKpiData.kpiTreands.goal35}</div>
						</td>
						<!-- 5分值 -->
						<td width="8%" style="border-right: 1px solid #c0c4c0;">
							<div align="center">${detpKpiData.kpiTreands.goal5}</div>
						</td>
						<!-- 操作 -->
						<td width="15%" style="border-right: 1px solid #c0c4c0;">
							<div align="center">
								<a href="javascript:void(0);"
									onclick="window.parent.showKpiThread('${detpKpiData.kpiTreands.kpiInfo.kpiCode}','${detpKpiData.kpiTreands.kpiInfo.unitIdDesc}','${detpKpiData.kpiTreands.amountDone}','${detpKpiData.kpiTreands.goal35}','${detpKpiData.kpiTreands.goal5}','${detpKpiData.kpiTreands.percent}','${detpKpiData.kpiTreands.lastYearKPI}','${detpKpiData.kpiTreands.kpiInfo.goalType}','${queryDate}');">指标走势</a>&nbsp;&nbsp;
								<a href="javascript:void(0);"
									onclick="window.parent.showKpiDetailThread('${detpKpiData.kpiTreands.kpiInfo.kpiCode}','${detpKpiData.dwmisMisDptmnt.depId}','${queryDate}');">详情分析</a>
							</div></td>
					</tr>
					<c:if test="${detpKpiData.kpiTreands.hasSubKPITrends}">
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							style="background-color: #f0f0f0; border-top: 1px solid #c0c4c0; color: #787c78">
							<!-- 有子指标 -->
							<c:forEach items="${detpKpiData.kpiTreands.subKPITrendsList}" var="subKpiThread">
								<tr style='background-color: #ffffff;'>
									<!-- 子指标名称 -->
									<td width="23%" height="37"
										style="border-left: 1px solid #c0c4c0; border-right: 1px solid #c0c4c0;"
										id='${subKpiThread.kpiInfo.kpiCode}'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${subKpiThread.kpiInfo.kpiNameShow}</td>
									<!-- 子指标进度条${subKpiThread.percent}% -->
									<td width="30%" style="border-right: 1px solid #c0c4c0;">
										<c:if test="${subKpiThread.passGoal}">
											<div class="progress-bar">
										</c:if> <c:if test="${subKpiThread.passGoal==false}">
											<div class="progress-bar progress-alert">
										</c:if> <c:if test="${subKpiThread.percent>=100}">
											<span class="percent" style="_width: 100%; min-width: 100%;">
												${subKpiThread.percent} <c:if
													test="${subKpiThread.isPTValue}">pt</c:if> <c:if
													test="${subKpiThread.isPTValue==false}">%</c:if> </span>
										</c:if> <c:if test="${subKpiThread.percent<100}">
											<span class="percent"
												style="_width:100%;min-width:${subKpiThread.percent}%;">
												${subKpiThread.percent} <c:if
													test="${subKpiThread.isPTValue}">pt</c:if> <c:if
													test="${subKpiThread.isPTValue==false}">%</c:if> </span>
										</c:if>
										</div></td>
									<!-- 当前完成值 -->
									<td width="9%" style="border-right: 1px solid #c0c4c0;"><div
											align="center">${subKpiThread.amountDone}</div>
									</td>
									<!-- 单位 -->
									<td width="6%" style="border-right: 1px solid #c0c4c0;"><div
											align="center">${subKpiThread.kpiInfo.unitIdDesc}</div></td>
									<!-- 3.5分值 -->
									<td width="9%" style="border-right: 1px solid #c0c4c0;"><div
											align="center">${subKpiThread.goal35}</div>
									</td>
									<!-- 5分值 -->
									<td width="8%" style="border-right: 1px solid #c0c4c0;"><div
											align="center">${subKpiThread.goal5}</div>
									</td>
									<!-- 操作 -->
									<td width="15%" style="border-right: 1px solid #c0c4c0;"><div
											align="center">
											<a href="javascript:void(0);"
												onclick="window.parent.showKpiThread('${subKpiThread.kpiInfo.kpiCode}','${subKpiThread.kpiInfo.unitIdDesc}','${subKpiThread.amountDone}','${subKpiThread.goal35}','${subKpiThread.goal5}','${subKpiThread.percent}','${subKpiThread.lastYearKPI}','${subKpiThread.kpiInfo.goalType}','${queryDate}');">指标走势</a>
											&nbsp;&nbsp; <a href="javascript:void(0);"
												onclick="window.parent.showKpiDetailThread('${subKpiThread.kpiInfo.kpiCode}','${detpKpiData.dwmisMisDptmnt.depId}','${queryDate}');">详情分析</a>
										</div></td>            <!-- '${detpKpiData.kpiTreands.kpiInfo.kpiCode}'    -->                          
								</tr>
							</c:forEach>
						</table>
					</c:if>
				</table>
				</c:forEach>
			</div>
			<div align="left"> 
			<div style="font-size:14px; font-weight:bold; border-bottom:1px solid #ccc; padding:10px 0px 10px 0px">备注：</div>
			<div style="font-size:14px;; padding:0px 0px 5px 10px;">
				<table>
					<tr>
						<td style="height:30px"><img src="<%=path%>/common/dwmis/images/lwt_1.png"  />：</td>
						<td style="height:30px">表示该指标走势落后与时间进度值</td>
					</tr>
					<tr>
						<td style="height:30px"><img src="<%=path%>/common/dwmis/images/lwt_2.png" />：</td>
						<td style="height:30px">表示该指标走势超过时间进度值</td>
					</tr>
				</table></div>
			</div>
	</div>
</body>
</html>
