<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="java.util.Date"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>报表设计器--${reportDesign.reportName}</title>
<meta name="Generator" content="EditPlus">
<meta name="Author" content="">
<meta name="Keywords" content="">
<meta name="Description" content="">
<script src="<%=basePath%>/js/jquery-1.5.1.min.js" type="text/javascript"></script>
<link type="text/css" rel="stylesheet" href="<%=basePath%>/css/main.css" />
<link type="text/css" rel="stylesheet" href="<%=basePath%>/css/report.css" />
<script type="text/javascript" src="<%=basePath%>/js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>
<!--日期控件-->
<script type="text/javascript" src="<%=basePath%>/js/datePicker/WdatePicker.js"></script>
<!--月历控件-->
<script type="text/javascript" src="<%=basePath%>/js/datePicker/My98MonthPicker.js"></script>
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
    window.onload = function() {
    	if(window.opener!=null) {
    		//定位左上角
    	    self.moveTo(0, 0);   
    		//调整屏幕
    		self.resizeTo(screen.availWidth, screen.availHeight);
    	}
    }
    
	/* 表头设计  */
	function designHeader() {
		var url='<%=basePath%>/NewReport/designReportHeader.html?reportId=${reportDesign.reportId}';
		var dg = new $.dialog({
			title : '表头设计',
			id : '${reportDesign.reportId}',
			width : 700,
			height : 470,
			top:0, 
			iconTitle : false,
			cover : true,
			maxBtn : false,
			xButton : true,
			resize : true,
			page : url
		});
		dg.ShowDialog();
	}
	
	/* 报表查询条件  */
	function designReportSearch(reportId) {
		var dg = new $.dialog({
			title : '查询条件配置',
			id : '${reportDesign.reportId}',
			width : 800,
			height : 450,
			top:0, 
			iconTitle : false,
			cover : true,
			maxBtn : false,
			xButton : true,
			resize : true,
			cancelBtnTxt : "返回",
			page : 'reportSearch' + reportId,
			onXclick: function(){
				window.frames['rsearchFrom'].document.location.href='<%=basePath%>/NewReport/searchConfig.html?reportId=${reportDesign.reportId}';
				dg.cancel();
			},
			onCancel: function(){
				window.frames['rsearchFrom'].document.location.href='<%=basePath%>/NewReport/searchConfig.html?reportId=${reportDesign.reportId}';
				dg.cancel();
			}
		});
		dg.ShowDialog();
	}
	
	/* 数据源配置  */
	function designDataSource(reportId) {
		//var url='<%=basePath%>/NewReport/dataSource.html?reportId=${reportDesign.reportId}';
		var dg = new $.dialog({
			title : '数据源配置',
			id : '${reportDesign.reportId}',
			width : 450,
			height : 500,
			top:0, 
			iconTitle : false,
			cover : true,
			maxBtn : false,
			xButton : true,
			resize : true,
			page : 'dataSource'+reportId
		});
		dg.ShowDialog();
	}
	
	/**
	        链接报表配置
	  setting : 链接设置
	  relRptId : 关联报表ID
	*/
	function linkSetting(relRptId, setting) {
		if (setting == null) {
			setting == "";
		} else {
			setting = setting.replace(/&/g, ";").replace(/#/g, ":");
		}
		if (relRptId == 'undefined') {
			relRptId = "";
		}
		var url = "linkSetting.html?reportId=${reportDesign.reportId}&setting="+setting+"&relRptId="+relRptId;
		var dg = new $.dialog({
			title : '链接配置',
			id : 'LinkSetting_${reportDesign.reportId}',
			width : 900,
			height : 500,
			top:0, 
			iconTitle : false,
			cover : true,
			maxBtn : true,
			xButton : true,
			resize : true,
			page : url
		});
		dg.ShowDialog();
	}
	
	//重新打开报表界面
	function reReviewReportForm() {
		/* ${reportDesign.isOk}=1; */
		window.frames['reportFrm'].document.location.href='<%=basePath%>/NewReport/parseReportTemplate.html?reportId=${reportDesign.reportId}';
	}
	
    //预览报表
	function previewReport() {
    	if(document.reportForm.reportId.value==null||document.reportForm.reportId.value.length==0) {
		   alert('请先配置报表');
		   return;
		}
	    window.open('<%=basePath%>/NewReport/previewReport.html?reportId=${reportDesign.reportId}', '预览报表', '');
	}
    
    //保存报表配置成功
	function success(){
		//预览报表界面
		reReviewReportForm();
	}
	
	//保存报表配置失败
	function failed(){
		alert("操作失败！");
	}
	
	//保存
	function saveReportDesign() {
	   document.reportForm.action="<%=basePath%>/NewReport/updateReportDesign.html";
	   document.reportForm.target="result";
       document.reportForm.submit();
	}
	
	//图表设计
	function designReportChart(reportId) {
		location.href="<%=basePath%>/reportChart/designReportChart.html?reportId="+reportId;
	}
	
	//图表自适应
	function changeReportChartIframeSize(iframeContentHeight) {
		document.getElementById('reportChartIfram').height = iframeContentHeight;
	}
</script>
<body style="overflow: no;">
<div style="width: 800px;margin-left: 240px;*margin-left: 0px" align="center">
	<form method="post"
		action="<%=basePath%>/NewReport/updateReportDesign.html"
		name="reportForm" id="reportForm" target='result'>
		<input type="hidden" name="reportId" value='${reportDesign.reportId}'/>
		<input type="hidden" name="reportName" value="${reportDesign.reportName}" id='reportName'/>
		<input type="hidden" name="dataSourceId" value="${reportDesign.dataSourceId}"/>
		<input type="hidden" name="reportSql" value="${reportDesign.reportSql}"/>
		<input type="hidden" name="reportDefine" id='reportDefine'/>
		<div class="search_div" id="search_div">
			<div>
				<a style="font-weight: bold;font-size: medium;margin-right: 30px;" >当前报表:${reportDesign.reportName}</a>
				<a href="javascript:designDataSource(${reportDesign.reportId});" class="myBtn" id="designDataSource"><em>配置数据源</em></a>
				<a href="javascript:designHeader();" class="myBtn" id="designHeader"><em>表头设计</em></a>
				<a href="javascript:designReportSearch(${reportDesign.reportId});" class="myBtn" id="designReportSearch"><em>查询条件</em></a>
				 <a href="javascript:designReportChart(${reportDesign.reportId});" class="myBtn" id="designReportChart"><em>图表设计</em></a>
				<a href="javascript:previewReport();" class="myBtn" id="previewBtn"><em>报表预览</em></a>
				
			</div>
		</div>
		<fieldset>
			<legend align="left">查询条件</legend>
			<c:if test="${fn:length(configList)>=1}">
				<iframe src="<%=basePath%>/NewReport/searchConfig.html?reportId=${reportDesign.reportId}" id="rsearchFrom" name="rsearchFrom" frameborder='0' width='100%' height="auto"></iframe>
			</c:if>
			<c:if test="${fn:length(configList)<=0}">
				<iframe src="about:blank" id="rsearchFrom" name="rsearchFrom" frameborder='0' width='100%' height="0px"></iframe>
			</c:if>
		</fieldset>
		<fieldset>
			<legend align="left">图表界面</legend>
			<iframe src="<%=basePath%>/reportChart/imgReportChart.html?reportId=${reportDesign.reportId}" id="reportChartIfram" name="reportChartIfram" frameborder='0' scrolling="no" width='100%' height='100%'></iframe>
		</fieldset>
		<fieldset>
			<legend align="left">报表界面</legend>
			<c:if test="${reportDesign.isOk==0}">
			    <iframe src="about:blank" id="reportFrm" name="reportFrm" frameborder='0' width='100%' height='0px'></iframe>
			</c:if>
			<c:if test="${reportDesign.isOk==1}">
				<iframe src="<%=basePath%>/NewReport/parseReportTemplate.html?reportId=${reportDesign.reportId}" id="reportFrm" name="reportFrm" frameborder='0' width='100%' height='auto'></iframe>
			</c:if>
		</fieldset>
	</form>
	<iframe name="result" id="result" src="about:blank" frameborder="1" width="0" height="0"></iframe>
	</div>
</body>
</html>