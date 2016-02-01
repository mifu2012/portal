<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<title>Example</title>
<link href="<%=basePath%>css/css.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>js/jquery-1.5.1.min.js" type="text/javascript"></script>
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
	height: 40px;
	line-height: 40px;
}

.info th {
	text-align: right;
	width: 85px;
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
<body class="indexbody" style="background: #ffffff">
	<!-- 维度从表信息添加或修改 -->
	<form id="dimensionDetaiform" method="post"
		action="<%=basePath%>/Dimensionality/saveDimensionDetailSec.html"
		target="result">
		<c:choose>
			<c:when test="${dimensionDetaiState eq 'add'}">
				<table border="0" cellpadding="0" cellspacing="0">
					<tr class="info">
						<td><input type="text" id="parentId"
							style="background-color: #f1f1f1;display: none" name="parent_id"
							value="${parentId}" /></td>
					</tr>
					<tr class="info">
						<th><font color="red">*</font>细项CODE:</th>
						<td><input type="text" class="input_txt" id="key"
							style="ime-mode: disabled" name="key" />
					</tr>
					<tr class="info">
						<th><font color="red">*</font>细项名称:</th>
						<td><input type="text" class="input_txt" id="value"
							name="value" />
					</tr>
				</table>
			</c:when>
			<c:when test="${dimensionDetaiState eq 'edit'}">
				<input type="hidden" id="primary_id" name="primary_id"
					value="${dimensionDetailSec.primary_id}" style="display: none;">
				<table border="0" cellpadding="0" cellspacing="0">
					<tr class="info">
						<th><font color="red">*</font>细项CODE:</th>
						<td><input type="text" class="input_txt" id="key"
							style="ime-mode: disabled" name="key"
							value="${dimensionDetailSec.key}" />
					</tr>
					<tr class="info">
						<th><font color="red">*</font>细项名称:</th>
						<td><input type="text" class="input_txt" id="value"
							name="value" value="${dimensionDetailSec.value}" />
					</tr>
				</table>
			</c:when>
			<c:otherwise>
			</c:otherwise>
		</c:choose>
	</form>
	<iframe name="result" id="result" src="about:blank" frameborder="0"
		width="0" height="0"></iframe>
	<script type="text/javascript">
		var dg;
		$(document).ready(function() {
			dg = frameElement.lhgDG;
			dg.addBtn('ok', '保存', function() {

				var primaryKeyId = $("#primary_id").val();
				//添加
				if (primaryKeyId == "") {
					var dimensionKey = $("#dimensionKey1").val();
					var dimensionValue = $("#dimensionValue1").val();

					if (dimensionKey == "") {
						alert("详细维度key不能为空!");
						return false;
					}
					if (dimensionValue == "") {
						alert("详细维度名称不能为空!");
						return false;
					}
					$("#dimensionDetaiform").submit();
				} else {
					//修改
					var dimensionKey = $("#key").val();
					var dimensionValue = $("#key").val();
					if (dimensionKey == "") {
						alert("详细维度key不能为空!");
						return false;
					}
					if (dimensionValue == "") {
						alert("详细维度名称不能为空!");
						return false;
					}
					$("#dimensionDetaiform").submit();
				}
				return false;
			});

		});
	</script>
	<script type="text/javascript">
		function success() {
			alert("操作成功！");
			try {
				//重新查询数据
				<c:choose>
				<c:when test="${dimensionDetaiState eq 'add'}">
				dg.curWin.showDimensionInfoSec("${parentId}");
				</c:when>
				<c:otherwise>
				dg.curWin.showDimensionInfoSec("${dimensionDetailSec.parent_id}");
				</c:otherwise>
				</c:choose>
			} catch (e) {
				if (dg.curWin.document.forms[0]) {
					dg.curWin.document.forms[0].action = dg.curWin.location
							+ "";
					dg.curWin.document.forms[0].submit();
				} else {
					dg.curWin.location.reload();
				}
			}
			dg.cancel();
		}

		function failed() {
			alert("操作失败！");
		}
	</script>

</body>
</html>