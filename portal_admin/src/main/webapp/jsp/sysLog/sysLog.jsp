<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>User</title>
<link type="text/css" rel="stylesheet" href="css/main.css"/>
</head>
<body>
	<form action="sysLog" method="post" name="sysLogForm" id="sysLogForm">
	<div class="search_div">
			登录名称: <input style="width: 130px;" type="text" name="user_code" id="user_code" value="${sysLog.user_code}" onkeydown="butOnClick()"/>&nbsp;&nbsp;
			用户名称：<input style="width: 130px;" type="text" name="user_name" id="user_name" value="${sysLog.user_name}" onkeydown="butOnClick()"/>&nbsp;&nbsp;
			操作内容：<input style="width: 130px;" type="text" name="operator_content" id="operator_content" value="${sysLog.operator_content}" onkeydown="butOnClick()"/>&nbsp;&nbsp;
			登录日期： <input type="text" name="operator_time" id="operator_time" style="width:94px; text-align:center" value="<fmt:formatDate value="${sysLog.operator_time}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker()" readonly="readonly" style="width:150px;"/> &nbsp;
			<a href="javascript:search();" class="myBtn"><em>查询</em></a>
			<a href="javascript:clearValue();" class="myBtn"><em>清空</em></a>
			&nbsp;<a href="javascript:delData(3);" class="myBtn"><em>删除3个月前信息</em></a>
	</div>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="main_table">
		<tr class="main_head">
			<th width="5%">序号</th>
			<th width="15%">登录名称</th>
			<th width="15%">用户名称</th>
			<th align='left'>&nbsp;&nbsp;&nbsp;&nbsp;操作内容</th>
			<th width="13%">操作时间</th>
			<th width="13%">用户IP</th>
		</tr>
		<c:choose>
			<c:when test="${not empty sysLogList}">
				<c:forEach items="${sysLogList}" var="log" varStatus="vs">
				<tr class="main_info" onmouseover="this.style.backgroundColor='#D2E9FF';" onmouseout="this.style.backgroundColor='';" >
				<td>${vs.index+1}</td>
				<td>${log.user_code }</td>
				<td>${log.user_name }</td>
				<td align='left'>&nbsp;&nbsp;&nbsp;&nbsp;${log.operator_content }</td>
				<td><fmt:formatDate value="${log.operator_time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${log.user_ip }</td>
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
		${sysLog.page.pageStr }
	</div>
	</form>
	<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="js/datePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>
	<script type="text/javascript">
	$(document).ready(function(){
		$(".main_info:even").addClass("main_table_even");
	});
	function butOnClick() {
		if (event.keyCode == 13) {
			search();
			return false;
		}
	}
	//清空按钮
	function clearValue(){
		$("#user_code").val("");
		$("#user_name").val("");
		$("#operator_content").val("");
		$("#operator_time").val("");
		search();
	}
	function search(){
		$("#sysLogForm").submit();
	}
	function delData(monthNum){
		if (confirm("确定要删除这些记录？")) {
			var url = "sysLog/deleteData.html?monthNum=" + monthNum;
			$.get(url, function(data) {
				if (data == "success") {
					alert("删除成功！");
					document.location.href = "sysLog";
				} else {
					alert("删除失败！");
				}
			});
		}
	}
	</script>
</body>
</html>