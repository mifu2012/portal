<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>报表设计器</title>
<meta name="Generator" content="EditPlus">
<meta name="Author" content="">
<meta name="Keywords" content="">
<meta name="Description" content="">
<script src="<%=basePath%>/js/jquery-1.5.1.min.js"
	type="text/javascript"></script>
<link type="text/css" rel="stylesheet" href="<%=basePath%>/css/main.css" />
<link type="text/css" rel="stylesheet"
	href="<%=basePath%>/css/report.css" />
<script type="text/javascript"
	src="<%=basePath%>/js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>
<style type='text/css'>
.box {
	border-top-width: 1px;
	border-right-width: 0px;
	border-bottom-width: 0px;
	border-left-width: 1px;
	border-top-style: solid;
	border-right-style: solid;
	border-bottom-style: solid;
	border-left-style: solid;
	border-top-color: #000000;
	border-right-color: #000000;
	border-bottom-color: #000000;
	border-left-color: #000000;
}

.box td {
	border-top-width: 0px;
	border-right-width: 1px;
	border-bottom-width: 1px;
	border-left-width: 0px;
	border-top-style: solid;
	border-right-style: solid;
	border-bottom-style: solid;
	border-left-style: solid;
	border-top-color: #000000;
	border-right-color: #000000;
	border-bottom-color: #000000;
	border-left-color: #000000;
	font-size: 13px;
	word-break: break-all;
}

.td_input {
	border-right: 0px #253E7A solid;
	border-left: 0px #253E7A solid;
	border-top: 0px #253E7A solid;
	border-bottom: 1px #253E7A solid;
	background-color: #ffffff;
	width: 95%;
	height: 99%;
	font-size: 9pt;
}

.td_label {
	border-right: 0px #253E7A solid;
	border-left: 0px #253E7A solid;
	border-top: 0px #253E7A solid;
	border-bottom: 0px #253E7A solid;
	background-color: #ffffff;
	font-size: 9pt;
}

.myMouseover {
	background-color: #D2E9FF
}
</style>
</head>
<script type="text/javascript">
	/* 表头设计  */
	function designHeader() {
		var url='<%=basePath%>/NewReport/designReportHeader.html?reportId=${reportDesign.reportId}';
		var dg = new $.dialog({
			title : '报表设计',
			id : '${reportDesign.reportId}',
			width : 600,
			height : 450,
			top:0, 
			iconTitle : false,
			cover : true,
			fixed:true,
			maxBtn : true,
			xButton : true,
			resize : true,
			page : url
		});
		dg.ShowDialog();
	}
	//重新打开报表界面
	function reReviewReportForm()
	{
	   window.frames['reportFrm'].document.location.reload();　
	}
    //预览报表
	function previewReport()
	{
	　 if(document.reportForm.reportId.value==null||document.reportForm.reportId.value.length==0)
		{
		   alert('请先配置报表');
		   return;
		}
		document.getElementById("fieldset_previewReport").style.display="";
	    window.frames['previewReportFrm'].document.location.href='<%=basePath%>/NewReport/previewReport.html?reportId=${reportDesign.reportId}';　　　　　
	}
	function success(){
		//预览报表界面
		reReviewReportForm();
	}
	
	function failed(){
		alert("操作失败！");
	}
	//保存
	function saveReportDesign()
	{
       document.reportForm.submit();
	}
</script>
<body style="overflow: no;" scroll="no">
	<form method="post"
		action="<%=basePath%>/NewReport/updateReportDesign.html"
		name="reportForm" id="reportForm" target='result'>
		<input type="hidden" name="reportId" value='${reportDesign.reportId}'/>
		<input type="hidden" name="reportName" value="${reportDesign.reportName}" id='reportName'/>
		<input type="hidden" name="dataSourceId" value="${reportDesign.dataSourceId}"/>
		<input type="hidden" name="reportDefine" id='reportDefine'/>
		<input type='button' value='配置数据源' onclick='designHeader();'>
		&nbsp;&nbsp;
		<input type='button' value='报表设计' onclick='designHeader();'>
		&nbsp;&nbsp;
		<input type='button' value='报表预览' onclick='previewReport()'>
		<fieldset>
			<legend>报表配置</legend>
			<table border="0" width="100%" class='box'>
				<tr>
					<td align='center'>查询SQL语句：</td>
					<td align='left' colspan='3'><textarea name="reportSql"
							style='width: 80%; height: 80px;' id="querySql"
							onchange='document.getElementById("saveBtn").disabled=true;'>${reportDesign.reportSql}</textarea>&nbsp;&nbsp;<input
						type='button' name="testQutyBtn" value="测试" id="testQutyBtn"
						onclick="testSQL();">
					</td>
				</tr>
			</table>
		</fieldset>
		<fieldset>
			<legend>报表界面</legend>
			<c:if test="${reportDesign.isOk==0}">
			    <iframe src="about:blank" id="reportFrm" name="reportFrm" frameborder='0' width='100%' height='250px'></iframe>
			</c:if>
			<c:if test="${reportDesign.isOk==1}">
				<iframe src="<%=basePath%>/NewReport/parseReportTemplate.html?reportId=${reportDesign.reportId}" id="reportFrm" name="reportFrm" frameborder='0' width='100%' height='250px'></iframe>
			</c:if>
		</fieldset>
		<fieldset id='fieldset_previewReport' style='display:none;'>
			<legend>报表预览</legend>
				<iframe src="about:blank" id="previewReportFrm" name="previewReportFrm" frameborder='0' width='100%' height='350px'></iframe>
		</fieldset>
	</form>
	<iframe name="result" id="result" src="about:blank" frameborder="10" width="0" height="0"></iframe>
</body>
</html>