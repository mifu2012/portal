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
<title>Example_Info</title>
<link type="text/css" rel="stylesheet" href="<%=path%>/css/main.css"/>
<style type="text/css">
body{width:100%;height:100%;background-color: #FFFFFF;text-align: center;}
.input_txt{width:200px;height:20px;line-height:20px;}
.info{height:40px;line-height:40px;}
.info th{text-align: right;width:65px;color: #4f4f4f;padding-right:5px;font-size: 13px;}
.info td{text-align:left;}
</style>
</head>
<body>
	<form action="saveUpadateGroup.html" name="systemTyppgGroupEdit" id="systemTyppgGroupEdit" target="result" method="post" onsubmit="return checkInfo();">
	<table border="0" cellpadding="0" cellspacing="0">
	    <tr class="info">
			<th width="35%">系统类型组编号:</th>
			<td width="65%"><input type="text"  name="typeId" id="typeId" class="input_txt" value="${dwmisSystemParamType.typeId }"/></td>
		</tr>
		<tr class="info" style="display: none" >
			<th width="35%"></th>
			<td width="65%"><input type="text"  name="groupId" id="groupId" class="input_txt" value="0"/></td>
		</tr>
		<tr class="info">
			<th width="35%">系统类型组名称:</th>
			<td width="65%"><input type="text"  name="typeName" id="typeName" class="input_txt" value="${dwmisSystemParamType.typeName }"/></td>
		</tr>
		<tr class="info">
			<th>系统类型组详情:</th>
			<td><textarea rows="8" cols="23" name="detail" id="detail" >${dwmisSystemParamType.detail }</textarea> </td>
			</tr>
	</table>
	</form>
	<iframe name="result" id="result" src="about:blank" frameborder="0" width="0" height="0"></iframe>
	
	<script type="text/javascript" src="<%=path%>/js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript">
		var dg;
		$(document).ready(function(){
			dg = frameElement.lhgDG;
			dg.addBtn('ok','保存',function(){
				$("#systemTyppgGroupEdit").submit();
			});
			if($("#typeId").val()!=""){
				$("#typeId").attr("readonly","readonly");
				$("#typeId").css("color","gray");
			}
		});
		
		function checkInfo(){
			
			if($("#typeName").val()==""){
				alert("请输入系统类型名称!");
				$("#typeName").focus();
				return false;
			}
			return true;
		}
		
		function success(){
			if(dg.curWin.document.forms[0]){
				dg.curWin.document.forms[0].action = dg.curWin.location+"";
				dg.curWin.document.forms[0].submit();
			}else{
				dg.curWin.location.reload();
			}
			dg.cancel();
		}

	</script>
</body>
</html>