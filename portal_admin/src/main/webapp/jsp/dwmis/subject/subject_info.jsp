<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑主题</title>
<link type="text/css" rel="stylesheet" href="../css/main.css" />
<style type="text/css">
body {
	width: 100%;
	height: 100%;
	background-color: #FFFFFF;
	text-align: center;
}

.input_txt {
	width: 200px;
	height: 20px;
	line-height: 20px;
}

.info {
	height: 30px;
	line-height: 40px;
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
	<form action="save" name="Form" id="Form" target="result" method="post"
		onsubmit="return checkInfo();">
		<input type="hidden" name="subjectId" id="subjectId" value="${subjectInfo.subjectId }" /> 
		<input type="hidden" name="templateId" id="templateId" value="${templateId }" /> 
		<table border="0" cellpadding="0" cellspacing="0">
			<tr class="info">
				<th><font color="red">*</font>&nbsp;主题名称:</th>
				<td><input type="text" name="subjectName" id="subjectName"
					class="input_txt" value="${subjectInfo.subjectName }"
					style="width: 210px;" /></td>
			</tr>
			<tr class="info">
				<th>&nbsp;排序序号:</th>
				<td><input type="text" name="subjectSort" id="subjectSort"
					value="${subjectInfo.subjectSort }"
					style="width: 210px; ime-mode: disabled;" onfocus='this.select();'
					onkeyup="value=value.replace(/[^\d]/g,'')"
					onblur="if(this.value.length==0) this.value=0;" /></td>
			</tr>
			<tr class="info">
				<th>是否可见:</th>
				<td><input type="checkbox" name="isShow" id="isShow" value="1"
					<c:if test="${subjectInfo.isShow ==null ||subjectInfo.isShow==1  }"> checked="checked" </c:if> />&nbsp;&nbsp;<label
					for="isShow">主题在首页中是否可见</label></td>
			</tr>
			<tr class="info">
				<th>备注:</th>
				<td><textarea name="remark" id="remark" rows="4" cols="24">${subjectInfo.remark }</textarea>
				</td>
			</tr>
		</table>
	</form>
	<iframe name="result" id="result" src="about:blank" frameborder="0"
		width="0" height="0"></iframe>

	<script type="text/javascript" src="../js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript">
		var dg;
		$(document).ready(function() {
			dg = frameElement.lhgDG;
			dg.addBtn('ok', '保存', function() {
				if ($("#subjectSort").val() == "") {
					document.getElementById("subjectSort").value = "0";
				}
				$("#Form").submit();
			});
		});

		function checkInfo() {
			if ($("#subjectName").val() == "") {
				alert("请输入主题名称!");
				$("#subjectName").focus();
				return false;
			}

			return true;
		}

		function success() {
			alert("操作成功！");
			if (dg.curWin.document.forms[0]) {
				dg.curWin.document.forms[0].action = dg.curWin.location + "";
				dg.curWin.document.forms[0].submit();
			} else {
				dg.curWin.location.reload();
			}
			dg.cancel();
		}
	</script>
</body>
</html>