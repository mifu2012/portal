<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Example</title>
<link type="text/css" rel="stylesheet" href="css/main.css"/>
</head>
<body>
	<form action="selfApply.html" method="post" name="searchForm" id="searchForm">
	<div class="search_div">
		报表名称：<input type="text" name="reportName" id="reportName" value="${selfApply.reportName }" onkeydown="butOnClick()"/>&nbsp;&nbsp;&nbsp;&nbsp;
		申请人：<input type="text" name="userName" id="userName" value="${selfApply.userName }" onkeydown="butOnClick()"/>&nbsp;&nbsp;&nbsp;&nbsp;
		状态：<select id="state" name="state"  value="${selfApply.state }" style="vertical-align: middle;"
				onchange="javascript:search();">
		<option value="-1">请选择</option>
		<%-- <option <c:if test="${selfApply.state eq 1}">selected="selected"</c:if> value="1">未申请</option> --%>
		<option <c:if test="${selfApply.state eq 2}">selected="selected"</c:if> value="2">申请中</option>
		<option <c:if test="${selfApply.state eq 3}">selected="selected"</c:if> value="3">审核通过</option>
		<option <c:if test="${selfApply.state eq 4}">selected="selected"</c:if> value="4">审核驳回</option>
			  </select>
		<%-- 出生日期：<input type="text" name="birthday" value="<fmt:formatDate value="${selfApply.birthday}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker()" readonly="readonly" /> --%>
		<a href="javascript:search();" class="myBtn"><em>查询</em></a>
		<a href="javascript:clearValue();" class="myBtn"><em>清空</em></a>
	</div>
	</form>
	<form action="" method="post" name="selfApplyForm" id="selfApplyForm">
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="main_table">
		<tr class="main_head">
			<th width="5%">序号</th>
			<th>报表名称</th>
			<th>申请人</th>
			<th>状态</th>
			<th width="160">日期</th>
			<th>操作</th>
		</tr>
		<c:choose>
			<c:when test="${not empty itemList}">
				<c:forEach items="${itemList}" var="selfApply" varStatus="vs">
				<tr class="main_info" style="cursor: pointer;" title="双击进行审核" onmouseover="this.style.backgroundColor='#D2E9FF';" onmouseout="this.style.backgroundColor='';" ondblclick="editSelfApply(${selfApply.id});" >
				<td width="5%">${vs.index+1}</td>
				<td>${selfApply.reportName }</td>
				<td>${selfApply.userName }</td>
				<td>
				<c:if test="${selfApply.state eq 1}">未申请</c:if>
				<c:if test="${selfApply.state eq 2}">申请中</c:if>
				<c:if test="${selfApply.state eq 3}"><font color="18b301">申请通过</font></c:if>
				<c:if test="${selfApply.state eq 4}"><font color="ff0000">申请驳回</font></c:if>
				</td>
				<td><fmt:formatDate value="${selfApply.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td><a href="javascript:editSelfApply(${selfApply.id});">审核</a></td>
				</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr class="main_info">
					<td colspan="6">没有相关数据</td>
				</tr>
			</c:otherwise>
		</c:choose>
	</table>
	<div class="page_and_btn">
		${selfApply.page.pageStr }
	</div>
	</form>
	<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$(".main_info:even").addClass("main_table_even");
		});
		//清空
		function clearValue(){
			$("#reportName").val("");
			$("#userName").val("");
			$("#state option")[0].selected=true;
			search();
		}
		function butOnClick() {
			if (event.keyCode == 13) {
				search();
				return false;
			}
		}
		function editSelfApply(id){
			var dg = new $.dialog({
				title:'审核',
				id:'selfApply_apply',
				width:330,
				height:300,
				iconTitle:false,
				cover:true,
				maxBtn:true,
				resize:true,
				page:'selfApply/edit'+id+'.html'
				});
    		dg.ShowDialog();
		}
		
		function search(){
			$("#searchForm").submit();
		}
	</script>
</body>
</html>