<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@page import="com.infosmart.portal.pojo.DwpasCKpiInfo"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title></title>
<!-- TAB切换 -->
<link href="<%=basePath%>/common/css/cssz.css" rel="stylesheet"
	type="text/css" />
<link href="<%=basePath%>/common/css/base.css" rel="stylesheet"
	type="text/css" />
<link href="<%=basePath%>/common/css/lwt.css" rel="stylesheet"
	type="text/css" />
<!-- TAB切换 -->
<!-- 生成图形 -->
<script src="<%=basePath%>/common/js/arale.js" type="text/javascript"></script>
<script src="<%=path%>/common/amchart/stock/amstock/swfobject.js"
	type="text/javascript"></script>
<script type="text/javascript" src="<%=path%>/common/js/amcharts.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/amfallback.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/raphael.js"></script>
<script src="<%=path%>/common/js/arale.js" type="text/javascript"></script>
<style>
.event-detail{
	color:#686868;
	font-family:'微软雅黑';
	/*height:400px;*/
	overflow:auto;
}
.event-detail a{
	color:#009CFF;
}
.event-detail h3{
	font-size:16px;
	line-height:34px;
	padding:0 30px;
	font-weight:bold;
	height:31px;
	color:#ff5500;
	background:url(/images/prodana/box-top-bg.png) repeat-x;
}
.event-detail h1{
	font-size:16px;
	font-weight:bold;
	text-align:center;
}
.event-detail dl{
	text-align:center;
	font-weight:bold;
}
.event-detail dt, .event-detail dd{
	display:inline;
}
.event-detail dd{
	margin-right:10px;
}
.event-detail .content{
	padding:12px 20px;
	line-height:1.6;
}
.event-detail .attach{
	padding-left:20px;
}
</style>
<script type="text/javascript">
	var amchart_key = "${amchartKey};";
	//TABN切换
	E.domReady(function() {
		//alert(1);
		A($$(".box-nav-li")).each(function(el) {
			el.click(function() {
				var did = el.attr("id");
				A($$(".box-nav-li")).each(function(es) {
					//alert(4);
					es.removeClass("box-li-act");
				});
				el.addClass("box-li-act");
				A($$(".J-tabbox")).each(function(es) {
					//alert(es);
					es.addClass("hidden");
				});
				//alert($(did+"_box"));
				$(did + "_box").removeClass("hidden");
			});
		});
		//生成趋势图
       var bigEventChart = new SWFObject("<%=path%>/common/amchart/stock/amstock/amstock.swf", "amline2", "100%", "250", "8", "#EFF0F2");
 		   bigEventChart.addVariable("path", "<%=path%>/common/amchart/stock/amstock/");
 		   bigEventChart.addVariable("chart_id", "amline2");
 		   bigEventChart.addParam("wmode","transparent");
		   //${bigEvent.eventStartDate}
 	   var chartUrl="<%=path%>/stockChart/showStockChart?1=1&isPrd=1&kpiCodes=${relKpiCodes}&beginDate=${bigEvent.eventStartDate}&reportDate=${date}&needPercent=1&isMIS=&showRightMenu=0&hasEvent=0&rid="+ Math.random();
		   bigEventChart.addVariable("settings_file", escape(chartUrl));
		   bigEventChart.addVariable("key", amchart_key);
		   bigEventChart.write("J-eveng_chart_box");
	});
</script>
</head>
<body>
    <jsp:include page="../common/ChoiceProduct.jsp" />
	<div class="event-detail" style="overflow-y: auto;border:0px solid red;">
		<h1>${bigEvent.title}</h1>
		<dl class="meta">
			<dt>发生时间:</dt>
			<dd>${bigEvent.eventStartDate}</dd>
			<dt>结束时间:</dt>
			<dd>${bigEvent.eventEndDate}</dd>
			<dt>事件类别:</dt>
			<dd>${bigEvent.eventTypeName}</dd>
		</dl>
	</div>
	<div class="box-nav" style="margin-top: 5px;">
		<ul class="box-nav-ul">
			<li class="box-nav-li box-li-act" id="J-eveng_content"
				style="z-index: 99;">大事记详情</li>
			<li class="box-nav-li" id="J-eveng_chart" style="z-index: 99;">关联指标走势</li>
		</ul>
	</div>
	<!-- 内容 -->
	<div class="normalbox mt15 J-tabbox" id="J-eveng_content_box"
		style="width:99%;overflow-y:auto; height:300px; margin-top: -10px; border: 0px solid red; margin-left:1px;border:0px solid red;">${bigEvent.content}</div>
	<div class="normalbox mt15 J-tabbox hidden" id="J-eveng_chart_box"
		style="width:99%; height:300px; margin-top: -10px;; border: 0px solid red;margin-left:1px;">ssghh</div>
</body>
</html>
