<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>My Test</title>
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
	<form action="save" name="Form" id="Form" target="result" method="post"
		onsubmit="return checkInfo();">
		<input type="hidden" name="categoryId" id="categoryId"
			value="${dwmisLegendCategory.categoryId }" />
		<table border="0" cellpadding="0" cellspacing="0">
			<tr class="info" style="display: none;">
				<th>父级:</th>
				<td><select id="categoryPid" name="categoryPid">
						<option value="-1">无</option>
				</select>
				</td>
			</tr>
			<tr class="info">
				<th><font color="red">*</font>&nbsp;图例类名:</th>
				<td><input type="text" name="categoryName" id="categoryName"
					class="input_txt" value="${dwmisLegendCategory.categoryName }" style="width: 210px;" maxlength="30"/>
				</td>
			</tr>
			<tr class="info">
				<th>备注:</th>
				<td><textarea name="remark" id="remark" rows="7" cols="24">${dwmisLegendCategory.remark }</textarea>
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
				$("#Form").submit();
			});
		});

		function checkInfo() {
			if ($("#categoryName").val() == "") {
				alert("请输入图例类名!");
				$("#categoryName").focus();
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