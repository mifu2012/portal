<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>KPI信息</title>
<link type="text/css" rel="stylesheet" href="<%=path%>/css/main.css"/>
</head>
<body>
	<form action="kpiGoal.html" method="post" name="kpiInfoForm" id="kpiInfoForm">
	<div class="search_div">
		指标Code：<input type="text" name="kpiCode" id="kpiCode" value="${dwmisKpiInfo.kpiCode }" />
		指标名称：<input type="text" name="kpiName" id="kpiName" value="${dwmisKpiInfo.kpiName }" />
		<%-- 创建日期：<input type="text" name="birthday" value="<fmt:formatDate value="${example.birthday}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker()" readonly="readonly" /> --%>
		<a href="javascript:search();" class="myBtn"><em>查询</em></a>
		<a href="javascript:clearValue();" class="myBtn"><em>清空</em></a>
	</div>
	</form>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="main_table">
		<tr class="main_head">
			<th width="10%" align="left" style="padding-left: 20px;">序号</th>
			<th width="15%" align="left">指标Code</th>
			<th width="20%" align="left">指标名称</th>
			<th width="15%" align="left">KPI显示名称</th>
			<th width="15%" align="left">绩效类型</th>
			<th width="10%" align="left">创建日期</th>
			<th width="15%">操作</th>
		</tr>
		<c:choose>
			<c:when test="${not empty kpiInfoList}">
				<c:forEach items="${kpiInfoList}" var="kpiInfo" varStatus="vs">
				<tr class="main_info" style="cursor: pointer;" <c:if test="${kpiInfo.goalType != 3003&&kpiInfo.goalType != 3004 &&kpiInfo.goalType != 3006&&kpiInfo.goalType != 3008 }">title="双击进行绩效管理" </c:if> onmouseover="this.style.backgroundColor='#D2E9FF';" onmouseout="this.style.backgroundColor='';" ondblclick="editKPIGoal(${kpiInfo.goalType},'${kpiInfo.kpiCode}');">
				<td align="left" style="padding-left: 20px;">${vs.index+1}</td>
				<td align="left">${kpiInfo.kpiCode }</td>
				<td align="left">${kpiInfo.kpiName }</td>
				<td align="left">${kpiInfo.kpiNameShow }</td>
				<td align="left">${kpiInfo.goalTypeDesc }</td>
				<td align="left"><fmt:formatDate value="${kpiInfo.createDate}" pattern="yyyy-MM-dd"/></td>
				<td>
				<c:choose>
					<c:when test="${kpiInfo.goalType != 3003&&kpiInfo.goalType != 3004 &&kpiInfo.goalType != 3006&&kpiInfo.goalType != 3008 }">
						 <a href="javascript:editKPIGoal(${kpiInfo.goalType },'${kpiInfo.kpiCode}');">绩效管理</a>
					</c:when>
					<c:otherwise>
						暂无绩效管理
					</c:otherwise>
				</c:choose>
				</td>
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
	<script type="text/javascript" src="<%=path%>/js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="<%=path%>/js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$(".main_info:even").addClass("main_table_even");
		});
		
		function editKPIGoal(goalType,kpiCode){
			if(goalType != 3003&&goalType != 3004 &&goalType != 3006&&goalType != 3008){
				var dg = new $.dialog({
					title:'绩效管理',
					id:'kpi_Goal',
					width:550,
					height:300,
					iconTitle:false,
					cover:true,
					maxBtn:true,
					xButton:true,
					resize:true,
					page:'kpiGoal/add'+kpiCode
					});
	    		dg.ShowDialog();
			}
		}
		//清空
		function clearValue(){
			$("#kpiCode").val("");
			$("#kpiName").val("");
			search();
		}
		
		//查询
		function search(){
			$("#kpiCode").val(jQuery.trim($("#kpiCode").val()));
			$("#kpiName").val(jQuery.trim($("#kpiName").val()));
			$("#kpiInfoForm").submit();
		}
	</script>
</body>
</html>