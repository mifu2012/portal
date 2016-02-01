<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>KPI信息</title>
<link type="text/css" rel="stylesheet" href="css/main.css"/>
</head>
<body>
	<form action="kpiInfo.html" method="post" name="kpiInfoForm" id="kpiInfoForm">
	<div class="search_div">
		KPI指标：<input type="text" name="kpiCode" value="${dwmisKpiInfo.kpiCode }"/>
		<%-- 创建日期：<input type="text" name="birthday" value="<fmt:formatDate value="${example.birthday}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker()" readonly="readonly" /> --%>
		<a href="javascript:search();" class="myBtn"><em>查询</em></a>
	</div>
	</form>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="main_table">
		<tr class="main_head">
			<th>序号</th>
			<th>KPI指标</th>
			<th>KPI名称</th>
			<th>KPI显示名称</th>
			<th width="160">创建日期</th>
			<th>操作</th>
		</tr>
		<c:choose>
			<c:when test="${not empty kpiInfoList}">
				<c:forEach items="${kpiInfoList}" var="kpiInfo" varStatus="vs">
				<tr class="main_info">
				<td>${vs.index+1}</td>
				<td>${kpiInfo.kpiCode }</td>
				<td>${kpiInfo.kpiName }</td>
				<td>${kpiInfo.kpiNameShow }</td>
				<td><fmt:formatDate value="${kpiInfo.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td><a href="javascript:boundKpi(${kpiInfo.kpiCode});">绑定指标</a></td>
				</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr class="main_info">
					<td colspan="7">没有相关数据</td>
				</tr>
			</c:otherwise>
		</c:choose>
	</table>
	<div class="page_and_btn">
		${dwmisKpiInfo.page.pageStr }
	</div>
	<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="js/datePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$(".main_info:even").addClass("main_table_even");
		});
		//绑定
		function boundKpi(kpiCode){
			var dg = new $.dialog({
				title:'绑定指标',				
				width:750,
				height:630,
				iconTitle:false,
				cover:true, 
				maxBtn:true,
				resize:true,
				page:'kpiDmnsnr/showKpiDmnsn'+kpiCode+'.html'
				});
    		dg.ShowDialog();
		}
		//查询
		function search(){
			$("#kpiInfoForm").submit();
		}
	</script>
</body>
</html>