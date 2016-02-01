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
<script type="text/javascript"
	src="<%=path%>/js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>
<title>导入元数据</title>
<script type="text/javascript">
var isClearData=0;//默认不清空目标表已有原数据
//上传文件
function ajaxFileUpload()
{
	//文件路径名
	var filePath=$("#filePath").val();
	if (filePath.indexOf(".xlsx") < 0 && filePath.indexOf(".xls") < 0) {
		alert("请选择要导入的EXCEL文件!");
		return;
	}
	
		
	
	if(confirm("点击\"确定\"，清空原数据后导入！\n点击\"取消\"，导入新增数据！")){
		$("#isClearData").val("1");
	}else{
		$("#isClearData").val("0");
	}	
	document.forms[0].submit();	
	//显示导入中.......
	var loadingDiv=document.getElementById("loading_wait_Div");
	if(loadingDiv!=null){
		loadingDiv.style.display="";
	}else{
		loadingDiv=document.createElement("div");
		loadingDiv.id="loading_wait_Div";
		loadingDiv.style.position="absolute";
		loadingDiv.style.left="40%";
		loadingDiv.style.top="30%";
		loadingDiv.innerHTML="<img src='<%=path%>/images/loading.gif'>";
	  document.body.appendChild(loadingDiv);
	}
	document.body.disabled=true;
}
function success(){
  alert("导入成功");
  document.getElementById("loading_wait_Div").style.display="none";
  document.body.disabled=false;
}
function failed(msg){
  alert("导入失败"+msg);
  document.getElementById("loading_wait_Div").style.display="none";
  document.body.disabled=false;
}
//还原导入的数据
function restore(){
	var selValue=$("#fileType_exp").val();
	if(confirm("你确定要还原数据?")){
		$.ajax({
			type : "POST",
			data : {
				selValue : selValue
			},
			url :"<%=path%>/dataImport/restoreImportData.html?randdom="+Math.random,
			success : function(msg) {
				if (msg == null || msg == "") {
					alert("还原成功");
				} else {
					alert(msg);
				}
			}
		});
	}
}

</script>
</head>
<body>
	<form method="post"
		action="<%=path%>/dataImport/importFile.html"
		enctype="multipart/form-data" target="result">
		<input type="hidden" name="isClearData" value="0" id="isClearData">
		<fieldset>
		  <legend><b>数据导入</b></legend>
		     <div align='left'>
                   &nbsp;&nbsp;导入类型：<select id="fileType" name="fileType" style="width:200px;">
							<option value="1">全国地区销量</option>
							<option value="2">渠道投入</option>
							<option value="3">竞争对手销量</option>
							<option value="4">KPI指标手工导入</option>
							<option value="5">全国彩种销量</option>
					</select>
					&nbsp;&nbsp;&nbsp;&nbsp;选择文件：<input type="file" id="filePath" value="" name="filePath" style="width:300px;">
					<input type="button" onclick="ajaxFileUpload()" class="myBtn"
						value="导入数据" style='cursor: pointer;'>
			 </div>
		</fieldset>
		<br/>
		<fieldset>
		  <legend><b>数据还原</b></legend>
		     <div align='left'>
                   &nbsp;&nbsp;数据类型：<select id="fileType_exp" name="fileType_exp" style="width:200px;">
							<option value="1">全国地区销量</option>
							<option value="2">渠道投入</option>
							<option value="3">竞争对手销量</option>
							<option value="4">KPI指标手工导入</option>
							<option value="5">全国彩种销量</option>
					</select><input type="button" onclick="restore()" class="myBtn" value="还原数据"
						style='cursor: pointer; margin-left: 20px'>
			 </div>
		</fieldset>
		
		<br/>
		<fieldset>
		  <legend><b>数据模板</b></legend>
		  <ul style='text-align:left;'>
		      <li><a href="<%=basePath%>/dataImport/down.html?filePath=js/template/nationalSales.xlsx&type=1">全国地区销量</a></li>
			  <li><a href="<%=basePath%>/dataImport/down.html?filePath=js/template/channel.xlsx&type=2">渠道投入</a></li>
			  <li><a href="<%=basePath%>/dataImport/down.html?filePath=js/template/competitor.xlsx&type=3">竞争对手销量</a></li>
			  <li><a href="<%=basePath%>/dataImport/down.html?filePath=js/template/kpi.xlsx&type=4">KPI指标手工导入</a></li>
			  <li><a href="<%=basePath%>/dataImport/down.html?filePath=js/template/areaSales.xlsx&type=5">全国彩种销量</a></li>
		  </ul>
		</fieldset>
		
	</form>
	<iframe name="result" id="result" src="about:blank" frameborder="0"
		width="0" height="0"></iframe>
</body>
</html>