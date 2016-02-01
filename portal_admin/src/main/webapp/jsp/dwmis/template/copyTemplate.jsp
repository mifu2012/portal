<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Example_Info</title>
<link type="text/css" rel="stylesheet" href="../css/main.css"/>
<style type="text/css">
.input_txt{width:200px;height:20px;line-height:20px;}
.info{height:40px;line-height:40px;}
.info th{text-align: right;width:65px;color: #4f4f4f;padding-right:5px;font-size: 13px;}
.info td{text-align:left;}
</style>
</head>
<body>
	<form action="<%=path %>/dwmisTemplateInfo/copy" name="Form" id="Form" target="result"  method="post" >
		<input type="hidden" name="templateId" id="templateId" value="${dwmisTemplateInfo.templateId }"/>
	<table border="0" cellpadding="0" cellspacing="0">
		<tr class="info">
			<th>复制名称:</th>
			<td><input maxlength="15" type="text" name="templateName" id="templateName" class="input_txt" value="复制${dwmisTemplateInfo.templateName}模板"/></td>
		</tr>
	</table>
	</form>
	<iframe name="result" id="result" src="about:blank" frameborder="0" width="0" height="0"></iframe>
	<script type="text/javascript" src="<%=path %>/js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript">
		var dg;
		$(document).ready(function(){
			dg = frameElement.lhgDG;
			dg.addBtn('ok','保存',function(){
				$("#Form").submit();
			});
		});
		
		function checkInfo(){
			if($("#templateName").val()==""){
				alert("请输入模板名称!");
				$("#templateName").focus();
				return false;
			}
			return true;
		}
		
		function success(){
			alert('复制成功！');
			if(dg.curWin.document.forms[0]){
				dg.curWin.document.forms[0].action = dg.curWin.location+"";
				dg.curWin.document.forms[0].submit();
			}else{
				dg.curWin.location.reload();
			}
			dg.cancel();
			
		}
		function failed(){
			alert('复制失败！');
		}
		</script>
</body>
</html>
