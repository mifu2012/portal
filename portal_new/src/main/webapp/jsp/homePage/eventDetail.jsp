<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml" lang="UTF-8"> 
<head>
<script src="<%=path%>/common/js/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="<%=path%>/common/js/miaov.js" type="text/javascript"></script>
<script src="<%=path%>/common/js/amcharts.js" type="text/javascript"></script>
<script src="<%=path%>/common/js/raphael.js" type="text/javascript"></script>
<script src="<%=path%>/common/js/arale.js" type="text/javascript"></script>
<script src="<%=path%>/common/amchart/column/amcolumn/swfobject1.js" type="text/javascript"></script>
<script src="<%=path%>/common/amchart/pie/ampie/swfobject.js" type="text/javascript"></script>
<script src="<%=path%>/common/js/FusionCharts_Trial.js" type="text/javascript"></script>
<%-- <script src="<%=path%>/common/js/arale.core-1.1.js" type="text/javascript" charset="utf-8"></script> --%>
<script src="<%=path%>/common/amchart/stock/amstock/swfobject.js" type="text/javascript"></script>
<script src="<%=path%>/common/js/amfallback.js" type="text/javascript"></script>
<link charset="utf-8" rel="stylesheet" href="https://static.alipay.com/build/css/alice/alice.css?t=aa11" media="all" />
<style>
.event-detail{
	color:#686868;
	font-family:'微软雅黑';
	height:400px;
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

function loadShow(){
	   var kpicodelist =
	  [
		   <c:forEach  items="${eventAndCodeList}" var="item" varStatus="sel">
         <c:if test="${sel.index!=0}">,</c:if>  
		            [
	       		          <c:forEach  items="${item.properties}" var="it" varStatus="selProperties">
	       		            <c:choose>
	       		              <c:when test="${selProperties.index==0}">"${it.value}"</c:when>
	       		              <c:otherwise>,"${it.value}"</c:otherwise>
	       		           </c:choose>
	       		          </c:forEach>
	            	]
		   </c:forEach>
    	]
		   	    var kpif = "";
				var childsel = parseInt($("J_child_select").node.value);
				var parentsel = parseInt($("J_index_select").node.value);
				kpif = kpicodelist[parentsel][childsel];
		        var so1 = new SWFObject("<%=path%>/common/amchart/stock/amstock/amstock.swf", "amline1", "780", "300", "9", "#EFF0F2"); 
		        so1.addVariable("path", "<%=path%>/common/amchart/stock/amstock/");
				so1.addVariable("chart_id", "amline1");
				so1.addParam("wmode","transparent");
				so1.addVariable("settings_file", escape("<%=path%>/stockChart/showStockChart?kpiCodes="+kpif+"&kpiType=1&reportDate=${date}"));
				so1.addVariable("key", amchart_key);
				so1.write("chartdiv2");
		
	       		$("J_index_select").change(function(){
	       			changeChart();
	       		});
	       		$("J_child_select").change(function(){
	       			changeChart();
	       		});
	       		function changeChart(){
	       			var kpi = "";
	       			var parentsel = parseInt($("J_index_select").node.value);
	       			var childsel  = parseInt($("J_child_select").node.value);
	       			kpi = kpicodelist[parentsel][childsel];
	       			so1.addVariable("settings_file", escape("<%=path%>/stockChart/showStockChart?kpiCodes="+kpi+"&kpiType=1&reportDate=${date}"));
	       			so1.write("chartdiv2");
	       	}
}
</script>
</head>  
<body> 
 <div class="event-detail">
	<h3>大事记</h3>
	<h1>${event.title}</h1>
	<dl class="meta">
		<c:if test="${event.eventStartDate}">
		<dt>事件发生时间:</dt>
		<dd>${dateUtils.dtSimpleChineseFormat(${event.eventStartDate})}</dd>
		</c:if>
		<c:if test="${event.eventEndDate}">
		<dt>事件结束时间:</dt>
		<dd>${dateUtils.dtSimpleChineseFormat(${event.eventEndDate})}</dd>		
		</c:if>
		<c:if test="${eventTypeMap}">
		<dt>事件类别:</dt>
		<dd>$eventTypeMap.get(${event.eventType})</dd>
		</c:if>
	</dl>	
	<c:if test="${event.content}">
		<div class="content">${event.content}</div>
	</c:if>
</div>
<div id="chartdiv2"></div>
</body> 
</html>
