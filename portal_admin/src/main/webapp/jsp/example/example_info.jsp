<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Example_Info</title>
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
	<form action="save.html" name="exampleForm" id="exampleForm" target="result" method="post" onsubmit="return checkInfo();">
		<input type="hidden" name="id" id="id" value="${example.id }"/>
	<table border="0" cellpadding="0" cellspacing="0">
		<tr class="info">
			<th>姓名:</th>
			<td><input type="text" name="name" id="name" class="input_txt" value="${example.name }"/></td>
		</tr>
		<tr class="info">
			<th>出生日期:</th>
			<td><input type="text" name="birthday" id="birthday" class="input_txt" value="<fmt:formatDate value="${example.birthday}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker()" readonly="readonly" /></td>
		</tr>
	</table>
	</form>
	<iframe name="result" id="result" src="about:blank" frameborder="0" width="0" height="0"></iframe>
	
	<script type="text/javascript" src="../js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="../js/datePicker/WdatePicker.js"></script>
	<script type="text/javascript">
		var dg;
		$(document).ready(function(){
			dg = frameElement.lhgDG;
			dg.addBtn('ok','保存',function(){
				$("#exampleForm").submit();
			});
			if($("#id").val()!=""){
				$("#name").attr("readonly","readonly");
				$("#name").css("color","gray");
			}
		});
		
		function checkInfo(){
			if($("#name").val()==""){
				alert("请输入姓名!");
				$("#name").focus();
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
			alert("新增失败，该姓名已存在！");
			$("#name").select();
			$("#name").focus();
		}
	</script>
</body>
</html>