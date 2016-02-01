<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="<%=path%>/css/main.css" />
<script type="text/javascript" src="<%=path%>/js/jquery-1.5.1.min.js"></script>
<!--异步上传组件-->
<script type="text/javascript"
	src="<%=path%>/js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>
<title>导入数据</title>
<script type="text/javascript">
	var isClearData=0;//默认不清空目标表已有原数据
	//上传文件
	function ajaxFileUpload()
	{
		//文件路径名
		var filePath=$("#filePath").val().toLowerCase();
		//上传类型
		var fileType=$("#fileType").val();
		var extensions=filePath.substring(filePath.lastIndexOf("."),filePath.length);
		if (fileType == "1") {
			if (extensions.indexOf(".xlsx") < 0 && extensions.indexOf(".xls") < 0) {
				alert("请选择EXCEL文件!");
				return;
			}
		}else {
			if (extensions.indexOf(".txt") < 0) {
				alert("请选择txt文件!");
				return;
			}
		}
		if(!confirm("您导入的数据可能覆盖原来的数据!\n\t确定导入吗?")) return;
		document.forms[0].submit();	
	}
	function success(){
	  alert("上传成功");
	  
	}
	function failed(msg){
		alert(msg);
	  //alert("上传失败，请重试"+msg);
	}
	
	function downTxt(filePath){
		location.href='<%=basePath%>/dataImport/down.html?filePath='+filePath;
	}
</script>
</head>
<body>
	<form method="post" action="<%=path%>/kpiinfo1/oneFileUpload.html" enctype="multipart/form-data" target="result">
		<fieldset>
			<legend>
				<b>导入数据 ${dateType}</b>
			</legend>
			<div align='left'>
			<input type="hidden" name="kpiCode" id="kpiCode" value="${kpiCode}">
			<input type="hidden" name="kpiType" id="kpiType" value="${kpiType}">
				&nbsp;&nbsp;数据类型：
				<select id="fileType" name="fileType" style="width: 90px;">
					<option value="1">Excel</option>
					<option value="2">txt</option>
				</select> 
				<br>
				<br>
				&nbsp;&nbsp;选择文件：
				<input type="file" id="filePath"
					value="" name="filePath" style="width: 300px;"> <input
					type="button" onclick="ajaxFileUpload()" class="myBtn" value="导入"
					style='cursor:'>
			</div>
		</fieldset>
		<br />
		<fieldset>
			<legend>
				<b>数据模板</b>
			</legend>
			<ul style='text-align: left;'>
				<li><a href="<%=basePath%>/js/template/kpiData.xls">Excel模板</a></li>
				<li><a href="<%=basePath%>/dataImport/down.html?filePath=js/template/kpiData.txt " >txt模板</a></li>
			</ul>
		</fieldset>
	</form>
	<iframe name="result" id="result" src="about:blank" frameborder="0"
		width="0" height="0"></iframe>
</body>
</html>