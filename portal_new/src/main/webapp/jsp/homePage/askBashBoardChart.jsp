<!--用户咨询情况（预警图）-->
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
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
<title>用户咨询情况（预警图）</title>
<style type='text/css'>
 body{ background:#eff0f2}
</style>
<link href="<%=path%>/common/css/cssz.css" rel="stylesheet"
	type="text/css" />
<link href="<%=path%>/common/css/base.css" rel="stylesheet"
	type="text/css" />
<link href="<%=path%>/common/css/lwt.css" rel="stylesheet"
	type="text/css" />
<!--<link href="<%=path%>/common/css/style.css" rel="stylesheet"
	type="text/css" />-->
<script src="<%=path%>/common/js/My97DatePicker/WdatePicker.js"
	type="text/javascript"></script>
<script src="<%=path%>/common/js/miaov.js" type="text/javascript"></script>
<script src="<%=path%>/common/js/amcharts.js" type="text/javascript"></script>
<script src="<%=path%>/common/js/raphael.js" type="text/javascript"></script>
<script src="<%=path%>/common/js/arale.js" type="text/javascript"></script>
<script src="<%=path%>/common/js/FusionCharts_Trial.js"
	type="text/javascript"></script>

<!--
<script src="<%=path%>/common/js/arale.core-1.1.js"
	type="text/javascript" charset="utf-8"></script>
-->
<script src="<%=path%>/common/amchart/column/amcolumn/swfobject1.js" type="text/javascript"></script>
<script src="<%=path%>/common/amchart/pie/ampie/swfobject.js" type="text/javascript"></script>
<script src="<%=path%>/common/amchart/stock/amstock/swfobject.js"
	type="text/javascript"></script>
<script src="<%=path%>/common/js/amfallback.js" type="text/javascript"></script>
</head>
<script type="text/javascript">	
    var amchart_key = "${amchartKey}";
	//业务笔数监控
var flashMovie;
var chartIsLoad=false;
function amChartInited (chart_id){
	flashMovie = document.getElementById("amline3");
	flashMovie.hideGraph(2);
}
	window.onload = function() {
	//仪表盘 
	$A($$(".J-yili")).each(function(el){
		el.click(function(){
			var dataid = el.attr('data-id');
			$A($$(".userhelp-middle")).each(function (es) {
				if(dataid == es.attr("data-id")){
					es.removeClass("fn-hide");
				}else{
					es.addClass("fn-hide");
				}  
            });
            $A($$(".J-helpbg")).each(function (es) {
            	es.removeClass("userhelp-middle-act");
				if(dataid == es.attr("data-id")){
					es.addClass("userhelp-middle-act");
				} 
            });
		});
	});
	
	$A($$(".J-top-er")).each(function(el){
		var dataid = el.attr("data-id");
        var params = {bgcolor:"#EFF0F2",key:amchart_key,wmode:"transparent"};
	    var flashVars = {
		    path: "<%=path%>/common/amchart/column/amcolumn/",
	        settings_file: escape("<%=path%>/common/amchart/column/amcolumn/amcolumn_settings4.xml"),
	        data_file: escape("<%=path%>/ProdsHelpRateChart/instrumentPanelShow?queryDate=${date}&productId="+dataid),
	        chart_id:"columndiv_" +dataid,
			key:amchart_key
		};
    	swfobject.embedSWF("<%=path%>/common/amchart/column/amcolumn/amcolumn.swf", "columndiv_"+dataid, "700", "150", "8.0.0", "<%=path%>/common/amchart/column/amcolumn/expressInstall.swf", flashVars, params);
	});
}
</script>
<body>
	<div class="box-conent box-userhelp">
		<ul class="userhelp-top">
			<c:forEach items="${queryKpiDataList}" var="productCatalog"
				varStatus="velocityCount">
				<li style="width: 130px; height: 110px; float: left;">
					<div id="chartdiv_${productCatalog.productId}"
						style="position: relative; background: #eff0f2; width: 130px; height: 110px; float: left;"
						class="J-top-yi">
						<OBJECT 　name="HYETA${velocityCount.index}"
							id="HUY${velocityCount.index}"
							classid="clsid:D27CDB6E-AE6D-11cf-96B8-${productCatalog.productId}"
							WIDTH="125" HEIGHT="110"
							id="myMovieName-${productCatalog.productId}">
							<EMBED src="<%=path%>/common/amchart/swf/qzl_new_5.swf"
								quality=high bgcolor=#EFF0F2 WIDTH="100" HEIGHT="110"
								NAME="myMovieName" ALIGN=""
								TYPE="application/x-shockwave-flash" play="true" loop="true"
								wmode="transparent" FlashVars="${productCatalog.flashPar}">
							</EMBED>
						</OBJECT>
						<div data-id="${productCatalog.productId}"
							class="userhelp-top-yi<c:if test="${not empty productCatalog.hasChild}">  J-yili</c:if>"
							style="z-index:10; line-height:1em; over-flow:hidden; font-size:100px; display:block;
					  <c:if test="${not empty productCatalog.hasChild}"> cursor:pointer; </c:if> top:0px; left:0px; width:174px; height:110px; position:absolute;">
						</div>
					</div>
				</li>
			</c:forEach>
		</ul>
		<c:set var="crtPrdId">0</c:set>
		<%-- <div class="userhelp-middle-bg">
			<c:forEach items="${queryKpiDataList}" var="productCatalog"
				varStatus="velocityCount">
				<c:if test="${velocityCount.index==0}">
					<c:set var="crtPrdId">${productCatalog.productId}</c:set>
				</c:if>
				<div style="width: 175px; float: left;">
					<c:if test="${not empty productCatalog.hasChild }">
						<a data-id="${productCatalog.productId}"
							class="J-helpbg userhelp-middle-no <c:if test="${velocityCount.index==1}">  userhelp-middle-act</c:if>"
							style="margin-left: 68px;">${ml}</a>
					</c:if>
				</div>
			</c:forEach>
		</div> --%>

		<c:forEach items="${queryKpiDataList}" var="productCatalog"
			varStatus="velocityCount">
			<div style="height: 150px;"
				class="userhelp-middle <c:if test="${velocityCount.index!=0}"> fn-hide </c:if>"
				data-id="${productCatalog.productId}">
				<span id="columndiv_${productCatalog.productId}"
					data-id="${productCatalog.productId}" class="J-top-er"></span>
			</div>
		</c:forEach>
		</div>
</body>
</html>