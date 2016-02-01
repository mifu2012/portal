<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>自定义平台</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<script src="<%=basePath%>js/jquery-1.5.1.min.js" type="text/javascript"></script>
<style type='text/css'>
label {
	font-size:13px;
}
div {
	border: 1px solid #FFFFFF;
}
td {
	border-left: 1px solid #F5F5F5;
	border-top: 1px solid #F5F5F5;
	border-right: 1px solid #F5F5F5;
	border-bottom: 1px solid #F5F5F5;
	font-size: 12px;
	border-collapse: collapse;
}
</style>
</head>
<script type="text/javascript">
	$(function() {
		// 哪块的按钮被选中1为数据列，2为二级表头，3为三级表头,4为批量控制
		var status = 0;
		// 数据列选中
		$("input:checkbox[id^='chk_dataColumn']").bind("click", function() {
			window.parent.customDisplay($(this).attr("dataField"), this.checked ? false :true);
		});
		// 二级表头选中
		$("input:checkbox[id^='chk_secondLevelHeader']").bind("click", function() {
			// 如果三级表头没勾选，则勾选
			if (status != 3) {
				if (this.checked == true) {
					$("input:checkbox[id='chk_thirdLevelHeader_"+this.id.substring(22, 23)+"'][checked='false']").attr("checked",true);
				} else {
					// 该列二级表头是否全部没有选中
					if ($("input:checkbox[id^='chk_secondLevelHeader_"+this.id.substring(22, 23)+"'][checked=true]").attr("id") == null) {
						$("input:checkbox[id='chk_thirdLevelHeader_"+this.id.substring(22, 23)+"']").attr("checked",false);
					}
				}
			}
			// 选中默认按钮
			$("#checkDefalut").click();
			// 显示未被批量隐藏的列
			$("input:checkbox[id^='chk_dataColumn_"+this.id.substring(24, 25)+"'][enable='true']").click();
			// 定义该列已被禁用或启用
			$("input:checkbox[id^='chk_dataColumn_"+this.id.substring(24, 25)+"']").each(function() {
				if ($(this).attr("enableTwo") == "true") {
					$(this).attr("enableTwo", "false");
				} else {
					$(this).attr("enableTwo", "true");
				}
			});
		});
		// 三级表头选中
		$("input:checkbox[id^='chk_thirdLevelHeader']").bind("click", function() {
			status = 3;
			$("#checkDefalut").click();
			// 判断是选中还是取消
			if (this.checked == false) {
				$("input:checkbox[id^='chk_secondLevelHeader_"+this.id.substring(21, 22)+"'][checked='true']").click();
			} else {
				$("input:checkbox[id^='chk_secondLevelHeader_"+this.id.substring(21, 22)+"'][checked='false']").click();
			}
			status = 0;
		});
		// 全选
		$("input:radio[id='checkAll']").bind("click", function() {
			// 启用全部checkbox
			$("input:checkbox").removeAttr("disabled"); 
			// 显示未显示数据列
			$("input:checkbox[id^='chk_dataColumn'][checked='false']").click();
			$("input:checkbox").attr("checked",true);
			// 重置禁用情况
			$("input:checkbox[id^='chk_dataColumn_']").attr({enable:'true',enableTwo:'true'});
		});
	});
	// 生成批量控制选框
	window.onload = function() {
		var array = new Array();
		var newCheck;
		$("input:checkbox[id^='chk_dataColumn_0']").each(function() {
			array[array.length] = $(this).attr("value");
		});
		for ( var i = 0; i < array.length; i++) {
			newCheck = '';
			newCheck = "<input type='checkbox' id='chk_batchControl_"+i+"' name='chk_batchControl' checked><label for='chk_batchControl_"+i+"' style='cursor: pointer; padding: 0 6px 0 6px;'>"+array[i]+"</label>";
			$("#dataColumn").append(newCheck);
		}
		// 数据列批量
		$("input:checkbox[id^='chk_batchControl_']").bind("click", function() {
			$("#checkDefalut").click();
			//显示未被二级表头禁用的数据列
			$("input:checkbox[id^='chk_dataColumn_'][id$='"+this.id.substring(17, 18)+"'][enableTwo='true']").click();
			// 定义该列已被禁用或启用
			$("input:checkbox[id^='chk_dataColumn_'][id$='"+this.id.substring(17, 18)+"']").each(function() {
				if ($(this).attr("enable") == "true") {
					$(this).attr("enable", "false");
				} else {
					$(this).attr("enable", "true");
				}
			});
			// 批量控制是否全部没有选中,如果没有选中,禁用所有checkbox
			if ($("input:checkbox[id^='chk_batchControl_'][checked=true]").attr("id") == null) {
				$("input:checkbox").attr("checked",false);
				$("input:checkbox").attr("disabled","disabled");
			}
		});
	}
</script>
<body style="width: 100%">
<div id="checkOpt" style="background-color: #FFDAB5; width: 99%;">
	<input type="radio" id="checkAll" name="check" value="checkAll" style="margin-left: 15px;" checked><label for='checkAll' style='cursor:pointer;'>全部</label>
	<input type="radio" id="checkDefalut" name="check" value="checkDefalut"><label for='checkDefalut' style='cursor:pointer;'>默认</label>
</div>
<div id="titleColumn">
	<table style="height: 165px;" cellspacing="0">
		<tr id="thirdLevelHeader" style="background-color: #EEEEEE; height: 35px;">
			<c:forEach items="${thirdLevelHeaderList}"  var="reportField" varStatus="vs">
				<td style="padding: 0 10px 0 10px;"><input type="checkbox" id="chk_thirdLevelHeader_${vs.index}" name="chk_thirdLevelHeader" checked><label for='chk_thirdLevelHeader_${vs.index}' style="cursor: pointer;">${reportField.content}</label></td>
			</c:forEach>
		</tr>
		<tr id="seconddLevelHeader">
			<c:set var="secondCount">0</c:set>
			<c:forEach items="${thirdLevelHeaderList}"  var="reportHeader" varStatus="status">
				<td style="vertical-align: top;">
					<c:forEach items="${secondLevelHeaderList}"  var="reportField" varStatus="vs">
						<c:if test="${reportField.pId == reportHeader.id}">
						<input type="checkbox" id="chk_secondLevelHeader_${status.index}_${secondCount}" name="chk_secondLevelHeader_${secondCount}" checked><label for='chk_secondLevelHeader_${status.index}_${secondCount}' style='cursor:pointer;'>${reportField.content}</label>
						<br/>
						<c:set var="secondCount">${secondCount+1}</c:set>
						</c:if>
					</c:forEach>
				</td>
			</c:forEach>
		</tr>
	</table>
</div>
<div id="dataColumn"></div>
	<c:set var="headerColumn" value="0"></c:set>
	<c:forEach items="${secondLevelHeaderList}"  var="reportHeader" varStatus="status">
		<c:if test="${reportHeader.rowSpan != 2}">
			<c:set var="data">0</c:set>
			<c:forEach items="${reportCellList}"  var="reportField" varStatus="vs">
				<c:if test="${reportField.pId == reportHeader.id}">
					<input type="checkbox" id="chk_dataColumn_${headerColumn}_${data}" name="chk_dataColumn_${vs.index}" value="${reportField.content}" checked style="display: none;">
					<label for='chk_dataColumn_${headerColumn}_${data}' style="cursor: pointer; display: none;">${reportField.content}</label>
					<script language='javascript'>
						$("#chk_dataColumn_${headerColumn}_${data}").attr("dataField","${reportField.dataField}");
						$("#chk_dataColumn_${headerColumn}_${data}").attr("enable","true");
						$("#chk_dataColumn_${headerColumn}_${data}").attr("enableTwo","true");
					</script>
					<c:set var="data">${data+1}</c:set>
				</c:if>
			</c:forEach>
			<c:set var="headerColumn">${headerColumn+1}</c:set>
		</c:if>
	</c:forEach>
</body>
</html>