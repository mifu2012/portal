<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<title>SysParam_Info</title>
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
	<form action="save.html" name="sysParamForm" id="sysParamForm" target="result" method="post" onsubmit="return checkInfo();">
		<input type="hidden" name="paramId" id="paramId" value="${sysparam.paramId }"/>
	<table border="0" cellpadding="0" cellspacing="0">
		<tr class="info">
			<th>参数名称:</th>
			<td><input type="text" name="paramName" id="paramName" class="input_txt" value="${sysparam.paramName }" onblur="validateParamName(this.value);"/></td>
		</tr>
		<tr class="info">
			<th>参数值:</th>
			<td><input type="text" name="paramValue" id="paramValue" class="input_txt" value="${sysparam.paramValue }"/></td>
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
				
				$("#sysParamForm").submit();
			});
			if($("#paramId").val()!=""){
				$("#paramId").attr("readonly","readonly");
				$("#paramId").css("color","gray");
			}
		});
		
		function checkInfo(){
			if($("#paramName").val()==""){
				alert("请输入参数名称!");
				$("#paramName").focus();
				return false;
			}
			return true;
		}
		
		function success(){
			alert("操作成功");
			if(dg.curWin.document.forms[0]){
				dg.curWin.document.forms[0].action = dg.curWin.location+"";
				dg.curWin.document.forms[0].submit();
			}else{
				dg.curWin.location.reload();
			}
			
			dg.cancel();
		}
		
		function failed(){
			alert("新增失败，该参数名已存在！");
			$("#paramName").select();
			$("#paramName").focus();
		}
		function validateParamName(val) {  
		       // 如果为空或者输入空格执行   
		       if(val==null||val.length==0) return;
		       $.ajax({
			        type: "POST",                                                                 
			        url:encodeURI("<%=path%>/sysparam/alidateSysName.html?paramName="
									+ val + "&random=" + Math.random()),
							success : function(msg) {
								if(msg=="1"){
				             		alert("参数名称[ "+val+" ]已存在，请重新输入！");
				             		$("#paramName").val("");
				             	 }  
							}
		       });  
		   }
	</script>
</body>
</html>