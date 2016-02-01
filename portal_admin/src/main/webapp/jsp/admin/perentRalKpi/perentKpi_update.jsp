<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<title>Dmnsn_Info</title>
<link type="text/css" rel="stylesheet" href="../css/main.css" />
<style type="text/css">
body {
	width: 100%;
	height: 100%;
	background-color: #FFFFFF;
	text-align: center;
}

.input_txt {
	width: 190px;
	height: 20px;
	line-height: 20px;
}

.info {
	height: 50px;
	line-height: 50px;
}

.info th {
	text-align: right;
	width: 100px;
	color: #4f4f4f;
	padding-right: 5px;
	font-size: 13px;
}

.info td {
	text-align: left;
}
</style>
</head>
<body>
	<form action="<%=path%>/typeRelKpi/save.html?&temp=2" name="dptForm"
		id="dptForm" target="result" method="post" onsubmit="return checkInfo();">
		<input type="hidden" name="typeId" id="typeId" class="input_txt" value="${parentKpi.typeId}" />
		<table border="0" cellpadding="0" cellspacing="0">
			<tr class="info">
				<th><font color="red">*</font>大类指标名称:</th>
				<td><input id="typeName" name="typeName" type="text"
					class="input_txt" value="${parentKpi.typeName}" /></td>
			</tr>
			<tr class="info">
				<th>大类指标描述:</th>
				<td><textarea rows="8" cols="22" name="detail" id="detail" >${parentKpi.detail}</textarea></td>
			</tr>
		</table>
	</form>
	<iframe name="result" id="result" src="about:blank" frameborder="0"
		width="0" height="0"></iframe>

	<script type="text/javascript" src="../js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="../js/datePicker/WdatePicker.js"></script>
	<script type="text/javascript">
		var dg;
		$(document).ready(function() {
			dg = frameElement.lhgDG;
			dg.addBtn('ok', '保存', function() {
				$("#dptForm").submit();
			});
		});

		function checkInfo() {
			if ($("#depName").val() == "") {
				alert("请输入大类指标名称!");
				$("#depName").focus();
				return false;
			}
			return true;
		}

		function success() {
			alert("修改指标大类成功");
			if (dg.curWin.document.forms[0]) {
				dg.curWin.document.forms[0].action = dg.curWin.location + "";
				dg.curWin.document.forms[0].submit();
			} else {
				dg.curWin.location.reload();
			}
			dg.cancel();
		}

		function failed() {
			alert("修改失败");
			$("#depId").select();
			$("#depId").focus();
		}
	</script>
</body>
</html>