<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<title>格式配置</title>
<link type="text/css" rel="stylesheet" href="<%=basePath%>css/main.css"/>
<script type="text/javascript"
	src="<%=basePath%>/js/zTree/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript">
	var dg;
	$(document).ready(function(){
		// 添加确定按钮
		dg = frameElement.lhgDG;
		var option = dg.opt.args;
		if (option.format == "number") {
			$("#decimalDigits").removeAttr("disabled");
			$("#decimalDigits").val(option.scale);
		}
		if (option.commas == 1) {
			$("#commas").attr("checked", "true");
		}
		if (option.currency == 1) {
			$("#currency").attr("checked", "true");
		}
		if (option.warningValue != "") {
			$("#warning").attr("checked", "true");
			document.getElementById("warningValue").disabled = false;
			document.getElementById("warningValue").value = option.warningValue;
		}
		dg.addBtn('ok','确定',function(){
			var warningValue = $("#warningValue").val();
			if (document.getElementById("warning").checked && warningValue == "") {
				alert("请输入预警值或取消预警设置");
				document.getElementById("warningValue").focus();
				return;
			}
			var commas = $("#commas").attr("checked") ? 1 : 0;
			var decimalDigits = $("#decimalDigits").val();
			var currency = $("#currency").attr("checked") ? 1 : 0;
			window.parent.saveFormatConfig(commas, currency, decimalDigits, warningValue);
			dg.cancel();
		});
	});
	
	/** 
		点击预警设置
		value checkbox状态
	*/
	function warningClick(value) {
		document.getElementById("warningValue").disabled = value;
		document.getElementById("warningValue").value = "";
	}
</script>
</head>
<body>
	<table align="center">
		<tr>
			<td align="right">千分符：</td>
			<td align="left"><input type="checkbox" id="commas" style="cursor: pointer;" /></td>
		</tr>
		<tr>
			<td align="right">货币符：</td>
			<td align="left"><input type="checkbox" id="currency" style="cursor: pointer;" /></td>
		</tr>
		<tr>
			<td>小数位数：</td>
			<td>
				<select id="decimalDigits" style="width: 88px;" disabled="disabled">
					<option value="-1"></option>
					<option value="0">0</option>
					<option value="1">1</option>
					<option value="2">2</option>
					<option value="3">3</option>
					<option value="4">4</option>
					<option value="5">5</option>
					<option value="6">6</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>预警设置：</td>
			<td align="left">
				<input type="checkbox" id="warning" style="cursor: pointer;" onclick="warningClick(!this.checked);" />
				<input type="text" id="warningValue" style="width: 60px; ime-mode:disabled;" disabled="disabled" onclick="this.select()"
					onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" />
			</td>
		</tr>
	</table>
</body>
</html>