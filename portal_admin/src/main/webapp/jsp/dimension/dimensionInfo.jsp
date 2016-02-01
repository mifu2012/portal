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
body{width:100%;height:100%;background-color: #FFFFFF;text-align: center;}
.input_txt{width:200px;height:20px;line-height:20px;}
.info{height:40px;line-height:40px;}
.info th{text-align: right;width:85px;color: #4f4f4f;padding-right:5px;font-size: 13px;}
.info td{text-align:left;}
</style>
</head>
<body>
<body>
	<!-- 维度主表信息添加或修改 -->
	<form id="dimensionform" method="post"
		action="<%=basePath%>/Dimensionality/saveDimension.html"
		target="result">
		<c:choose>
			<c:when test="${dimensionState eq 'add'}">
					<input type="hidden" id="dimensionId" name="id" value=""
						style="display: none;" />
				<div style="height: 35px;" ></div>
				<table  border="0" cellpadding="0" cellspacing="0">
				
			       <tr class="info">
			        <th><font color="red">*</font>维度Code:</th>
			        <td><input type="text" name="dimensionCode" id="code1"  style="ime-mode:disabled" class="input_txt" value=""/></td>
		           </tr>
		           <tr class="info">
			        <th><font color="red">*</font>维度名称:</th>
			        <td><input type="text" name="dimensionName" id="nameShow1"   class="input_txt" value=""/></td>
		           </tr>
				</table>
			</c:when>
			<c:when test="${dimensionState eq 'select'}">
				<input type="hidden" id="dimensionId" name="id"
					value="${dimension.id}" style="display: none;" />
					<div style="height: 35px;" ></div>
				<table  border="0" cellpadding="0" cellspacing="0">
				
			       <tr class="info">
			        <th><font color="red">*</font>维度Code:</th>
			        <td><input type="text" name="dimensionCode" id="code2"  style="ime-mode:disabled" class="input_txt" value="${dimension.dimensionCode}"/></td>
		           </tr>
		           <tr class="info">
			        <th><font color="red">*</font>维度名称:</th>
			        <td><input type="text" name="dimensionName" id="nameShow2"  class="input_txt" value="${dimension.dimensionName}"/></td>
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
				var dimensionId = $("#dimensionId").val();
				if (dimensionId == "") {
					var dimensionCode = $("#code1").val();
					var dimensionName = $("#nameShow1").val();
					if (dimensionCode == "") {
						alert("维度code不能为空!");
						return false;
					}
					if (dimensionName == "") {
						alert("维度名称不能为空!");
						return false;
					}
					$("#dimensionform").submit();
				} else {
					var dimensionCode = $("#code2").val();
					var dimensionName = $("#nameShow2").val();
					if (dimensionCode == "") {
						alert("维度code不能为空!");
						return false;
					}
					if (dimensionName == "") {
						alert("维度名称不能为空!");
						return false;
					}
					$("#dimensionform").submit();
				}
				return false;
			});

		});

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
			alert("操作失败！");
		}
	</script>
</body>
</html>
