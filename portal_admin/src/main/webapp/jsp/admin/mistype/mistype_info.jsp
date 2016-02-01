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
<title>mistype_Info</title>
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
	<form action="saveAddType.html" name="mistypeForm" id="mistypeForm" target="result" method="post" onsubmit="return checkInfo();">
	<table border="0" cellpadding="0" cellspacing="0">
		<tr class="info">
			<th><font color="red">*</font>类型ID:</th>
			<td><input type="text" name="typeId" id="typeId" class="input_txt" value="选择类型组保存后将自动分配ID"/></td>
		</tr>
		<tr class="info">
			<th><font color="red">*</font>类型组:</th>
			<td><select name="groupId" id="groupId" style="vertical-align: middle;width:200px;" >
				<option value='' selected >请选择</option>
				<c:forEach items="${mistypes}" var="mistype">
				<c:if test="${mistype.groupId eq 0 }"><option value='${mistype.typeId}'>${mistype.typeId}-${mistype.typeName}</option></c:if> 
				</c:forEach>
			</select>
			</td>
		</tr>
		<tr class="info">
			<th><font color="red">*</font>类型名称:</th>
			<td><input type="text" name="typeName" id="typeName" class="input_txt"  value="${mistype.typeName}" /></td>
		</tr>
		<tr class="info">
			<th>详情:</th>
			<td><textarea rows="5" cols="23" name="detail" id="detail" style="width:200px;" >${mistype.detail}</textarea></td>
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
				var typeName= $.trim($("#typeName").val());
				$.ajax({
                    type: "POST",                                                                 
                    url:encodeURI("<%=basePath%>/mistype/checkTypeName.html?typeName="+typeName),
                    success: function(msg){ 
      	            if(msg=="success"){
      	            	$("#mistypeForm").submit();
      	             }else{
      	            	alert("该系统类型名称已存在！");
      	            	$("#typeName").focus();
      	           }
                }
            }); 
			});
			$("#typeId").attr("readonly","readonly");
			$("#typeId").css("color","gray");

		});
		
		function checkInfo(){
			if($("#typeId").val()==""){
				alert("请输入类型ID!");
				$("#typeId").focus();
				return false;
			}
			if($("#groupId").val()==""){
				alert("请选择所在系统类型组!");
				$("#groupId").focus();
				return false;
			}
			if($("#typeName").val()==""){
				alert("请输入系统类型名称!");
				$("#typeName").focus();
				return false;
			}
			return true;
		}
		
		function success(){
			alert("新增成功！");
			if(dg.curWin.document.forms[0]){
				dg.curWin.document.forms[0].action = dg.curWin.location+"";
				dg.curWin.document.forms[0].submit();
			}else{
				dg.curWin.location.reload();
			}
			dg.cancel();
		}
		
		function failed(){
			alert("新增失败！\n该类型名称已存在！");
			$("#typeName").select();
			$("#typeName").focus();
		}
	</script>
</body>
</html>