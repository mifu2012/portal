<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>My Test</title>
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
	<form  action="<%=path %>/dwpas/saveMenu.html" name="addmenuForm" id="addmenuForm" target="result" method="post" onsubmit="return checkInfo();">
	<input name="templateId" type="hidden" value="${templateId}"/>
	<table border="0" cellpadding="0" cellspacing="0">
		<tr class="info">
			<th>从属菜单:</th>
			<td>
				<select name="menuPid" id="menuPid" class="input_txt">
					<option value="">请选择</option>
					<c:forEach items="${menuList}" var="menu">
					<option value="${menu.menuId }">${menu.menuName }</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr class="info">
			<th>名　称:</th>
			<td><input type="text" name="menuName" id="menuName" class="input_txt" /></td>
		</tr>
		<tr class="info">
			<th>日期类型:</th>
			<td>
				<select  style="width:120px;" name="dateType">
					<option value="0">日</option>
					<option value="1">月</option>
			   </select>
			</td>
		</tr>
		<tr class="info">
			<th>资源路径:</th>
			<td><input type="text" name="menuUrl" id="menuUrl" class="input_txt" value="/jumpToUrl/gotoNewPage.html" readonly="readonly" style="color:gray;"/></td>
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
				$("#addmenuForm").submit();
			});
		});
		
		function checkInfo(){
			if($("#menuUrl").val()==""){
				alert("请输入资源路径!");
				$("#menuUrl").focus();
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
		
		function failed(){
			alert("新增失败！");
		}
		
	
	</script>
</body>
</html>