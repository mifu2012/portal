<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
<link href="<%=path%>/common/css/style.css" rel="stylesheet"
	type="text/css" />
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
</head>
<script type="text/javascript">
<!--
	window.onload = function() {
		var loadingDiv = window.parent.document
				.getElementById("loading_wait_Div");
		if (loadingDiv == null)
			loadingDiv = window.parent.parent.document
					.getElementById("loading_wait_Div");
		if (loadingDiv != null)
			loadingDiv.style.display = "none";
	}
//-->
</script>
<body style="margin:0;padding:0;">
	<div class="index_title">
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			style="background-color: #f0f0f0; border-top: 1px solid #c0c4c0; border-left: 1px solid #c0c4c0; border-right: 1px solid #c0c4c0; color: #787c78">
			<tr>
				<td width="20%" height="37" style=""><div align="center">KPI指标</div>
				</td>
				<td width="30%"><div align="center">KPI指标完成率</div>
				</td>
				<td width="12%"><div align="center">当前完成值</div>
				</td>
				<td width="12%"><div align="center">预计完成值</div>
				</td>
				<td width="10%"><div align="center">单位</div>
				</td>
			</tr>
		</table>
	</div>
	<c:set var="tempDeptId">-1</c:set>
	<!-- 没有数据 -->
	<c:if test="${fn:length(dwpasStDeptKpiDateList)==0}">
		<div style="color: red; width: 100%; height: 100%" align='center'>当前日期没有数据</div>
	</c:if>
	<c:if test="${fn:length(dwpasStDeptKpiDateList)!=0}">
		<c:forEach items="${dwpasStDeptKpiDateList}" var="detpKpiData"
			varStatus="status">
			<c:if test="${status.index==0}">
				<!-- 第一个部门信息 -->
				<!--下面是新的tab开始-->
				<div class="index_report" style="border-bottom: 1px solid #c0c4c0"
					style="display:block;" id="tabContentDiv_${detpKpiData.deptId}">
			</c:if>
			<c:if test="${status.index!=0}">
				<c:if test="${tempDeptId !=detpKpiData.deptId}">
					<!-- 不同部门 -->
					</div>
					<div class="clear"></div>
					<!--tab1结束-->
					<!--下面是新的tab开始-->
					<div class="index_report" style="border-bottom: 1px solid #c0c4c0"
						style="display:<c:if test="${status.index!=0}">none;</c:if>"
						id="tabContentDiv_${detpKpiData.deptId}">
				</c:if>
			</c:if>
			<c:set var="tempDeptId">${detpKpiData.deptId}</c:set>
			<!--达标显示的是灰底-->
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				style="background-color: #f0f0f0; border-top: 1px solid #c0c4c0; color: #787c78">
				<tr>
					<!-- 指标名称 -->
					<td width="20%" height="37"
						style="border-left: 1px solid #c0c4c0; border-right: 1px solid #c0c4c0;"
						id='${detpKpiData.kpiCode}'>&nbsp;&nbsp;${detpKpiData.kpiName}</td>
					<td width="30%" style="border-right: 1px solid #c0c4c0;"><c:if
							test="${detpKpiData.completeRate>=50}">
							<div class="progress-bar">
						</c:if> <c:if test="${detpKpiData.completeRate<50}">
							<div class="progress-bar progress-alert">
						</c:if> <c:if test="${detpKpiData.completeRate>=100}">
							<span class="percent" style="_width: 100%; min-width: 100%;">
								${detpKpiData.completeRate}%</span>
						</c:if> <c:if test="${detpKpiData.completeRate<100}">
							<span class="percent"
								style="_width:100%;min-width:${detpKpiData.completeRate}%;">
								${detpKpiData.completeRate} %</span>
						</c:if>
						</div>
					</td>
					<!-- 完成值 -->
					<td width="12%" style="border-right: 1px solid #c0c4c0;"><div
							align="center">${detpKpiData.kpiFulfillValue}</div>
					</td>
					<!--当年完成值-->
					<td width="12%" style="border-right: 1px solid #c0c4c0;"><div
							align="center">${detpKpiData.kpiTaskValue}</div>
					</td>
					<!-- 单位 -->
					<td width="10%" style="border-right: 1px solid #c0c4c0;"><div
							align="center">${detpKpiData.unit}</div>
					</td>
				</tr>
			</table>
			<!--未达标显示的是灰底-->
		</c:forEach>
	</c:if>
	<c:if test="${tempDeptId !=-1}">
		<div class="clear"></div>
		<!--tab1结束-->
	</c:if>
</body>
</html>
