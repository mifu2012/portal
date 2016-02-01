<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>selfApply_info</title>
<link type="text/css" rel="stylesheet" href="../css/main.css"/>
<style type="text/css">
body{width:100%;height:100%;background-color: #FFFFFF;text-align: center;}
.input_txt{width:200px;height:20px;line-height:20px;}
.info{height:40px;line-height:40px;}
.info th{text-align: right;width:65px;color: #4f4f4f;padding-right:5px;font-size: 13px;}
.info td{text-align:left;}
</style>
</head>
<body>
	<form action="saveSelfApply.html" name="selfForm" id="selfForm" target="result" method="post" onsubmit="return checkInfo();" ><!-- -->
		<input type="hidden" name="id" id="id" value="${selfApply.id}"/>
		<input type="hidden" name="reportId" id="reportId" value="${selfApply.reportId}"/>
		<input type="hidden" name="userId" id="userId" value="${selfApply.userId}"/>
	<table border="0" cellpadding="0" cellspacing="0">
		<tr class="info">
			<th>状态:</th>
			<td>
			<select name="state" id="state" class="input_txt">
				<option value="">请选择</option>
				<option <c:if test="${selfApply.state eq 3}"> selected="selected"</c:if> value="3">申请通过</option>
				<option <c:if test="${selfApply.state eq 4}"> selected="selected"</c:if> value="4">申请驳回</option>
			</select>
			</td>
		</tr>
		<tr class="info"> 
		<th>备注:</th>
			<td>
				<textarea name="memo" id="memo" rows="5" cols="22">${selfApply.memo}</textarea>
			</td>
			</tr>
	</table>
	</form>
	<iframe name="result" id="result" src="about:blank" frameborder="0" width="0" height="0"></iframe>
	
	<script type="text/javascript" src="../js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript">
		var dg;
		$(document).ready(function(){
			dg = frameElement.lhgDG;
			dg.addBtn('ok','保存',function(){
				$("#selfForm").submit();
			});
		});
		
		function checkInfo() {
			if ($("#state").val() == "") {
				alert("请选择审核状态!");
				$("#state").focus();
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
		function failed(){
			alert("操作失败！");
			
		}
	</script>
</body>
</html>