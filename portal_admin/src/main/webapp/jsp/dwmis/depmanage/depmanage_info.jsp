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
<link type="text/css" rel="stylesheet" href="<%=path%>/css/main.css" />
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
	height: 50px;
	line-height: 50px;
}

.info th {
	text-align: right;
	width: 80px;
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
	<form action="<%=path%>/dwmisMisDptmnt/save.html" name="dptForm"
		id="dptForm" target="result" method="post"
		onsubmit="return checkInfo();">
		<input id="depId" name="depId" type="hidden" class="input_txt"
			value="${dwmisMisDptmnt.depId}">
		<table border="0" cellpadding="0" cellspacing="0">
			<tr class="info">
				<th><font color="red">*</font>部门名称:</th>
				<td><input id="depName" name="depName" type="text"
					class="input_txt" value="${dwmisMisDptmnt.depName}" /></td>
			</tr>
			<tr class="info">
				<th><font color="red">*</font>部门序号:</th>
				<td><input id="depOrder" name="depOrder" type="text"
					class="input_txt" value="${dwmisMisDptmnt.depOrder}" style="ime-mode: disabled;" onfocus='this.select();'
					onkeyup="value=value.replace(/[^\d]/g,'')"
					onblur="if(this.value.length==0) this.value=0;"/></td>
			</tr>
			<tr class="info">
				<th><font color="red">*</font>部门分类:</th>
				<td><select name="depGroupId" id="depGroupId" class="input_txt">
						<option value="">请选择</option>
						<c:forEach items="${dwmisMisTypes}" var="type">
							<option value="${type.typeId }">${type.typeName }</option>
						</c:forEach>
				</select></td>
			</tr>
		</table>
	</form>
	<iframe name="result" id="result" src="about:blank" frameborder="0"
		width="0" height="0"></iframe>

	<script type="text/javascript" src="<%=path%>/js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript">
		var dg;
		$(document).ready(function() {
			dg = frameElement.lhgDG;
			dg.addBtn('ok', '保存', function() {
				$("#dptForm").submit();
			});

			var depGroupId = "${dwmisMisDptmnt.depGroupId}";
			if (depGroupId != "") {
				$("#depGroupId").val(depGroupId);
			}
		});

		//验证数字
		function fucCheckNUM(NUM) {
			var i, j, strTemp;
			strTemp = "0123456789";
			if (NUM.length == 0)
				return 0;
			for (i = 0; i < NUM.length; i++) {
				j = strTemp.indexOf(NUM.charAt(i));
				if (j == -1) {
					//说明有字符不是数字     
					return 0;
				}
			}
			//说明是数字     
			return 1;
		}

		function checkInfo() {
			if ($("#depName").val() == "") {
				alert("请输入部门名称!");
				$("#depName").focus();
				return false;
			}
			var depOrder = $("#depOrder").val();
			if (depOrder == "" ) {
				alert("请输入部门序号!");
				$("#depOrder").focus();
				return false;
			} else if(fucCheckNUM(depOrder) == 0){
					alert("部门序号必须为数字");
					$("#depOrder").focus();
					return false;
			}
			
			if($("#depGroupId").val()==""){
				alert("请选择部门分类!");
				$("#depGroupId").focus();
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

		function failed() {
			alert("操作失败");
			$("#depId").select();
			$("#depId").focus();
		}
	</script>
</body>
</html>