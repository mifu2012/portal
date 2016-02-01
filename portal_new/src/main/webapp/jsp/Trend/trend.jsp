<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<title>${systemName}趋势图</title>
<link href="<%=path%>/common/css/base.css" rel="stylesheet"
	type="text/css" />
<link href="<%=path%>/common/css/style.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" charset="utf-8"
	src="https://static.alipay.com/ar/arale.core-1.1.js"></script>
<script type="text/javascript"
	src="<%=path%>/common/js/jquery-1.4.3.min.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/amcharts.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/amfallback.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/arale.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/ddycom.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/excanvas.js"></script>
<script type="text/javascript"
	src="<%=path%>/common/js/FusionCharts_Trial.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/FusionCharts.js"></script>
<%-- <script type="text/javascript"
	src="<%=path%>/common/js/jquery-1.4.3.min.js"></script> --%>
<%-- <script type="text/javascript" src="<%=path%>/common/js/pro.js"></script> --%>
<script type="text/javascript" src="<%=path%>/common/js/raphael.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/swfobject.js"></script>

<style type="text/css">
﻿body {
	font: 12px/1.5 '微软雅黑';
	color: #333;
}

.wrap {
	width: 800px;
	overflow: X-scroll;
	height: 600px;
	margin: 0px auto 0px;
	font: 12px/1.5 '微软雅黑';
	margin: 0px auto 0px;
}

.trend-banner {
	font-size: 16px;
	line-height: 34px;
	padding: 0 30px;
	font-weight: bold;
	height: 31px;
	color: #ff5500;
	background: url(/images/prodana/box-top-bg.png) repeat-x;
}

.J_fsl {
	margin-left: 20px;
}

.trend-sel {
	height: 20px;
	margin: 5px 0 0 10px;
}
</style>
</head>
<body>
	<div class="wrap">
		<h3 class="trend-banner">趋势图</h3>
		<!-- 首页趋势图 -->
		<c:if test="${type==1 }">
			<div class="trend-sel">
				<select id="J_index_select">
					<c:forEach items="${leftList }" var="item" varStatus="sel">
						<option value="${sel.index }">${item.name }</option>
					</c:forEach>
				</select>
				<c:forEach items="${leftList }" var="item" varStatus="sel">
					<c:choose>
						<c:when test="${sel.index==0}">
							<select class="J_fsl" id="J_child_select">
								<c:forEach items="${item.properties }" var="it" varStatus="sel">
									<option value="${sel.index }">${it.name }</option>
								</c:forEach>
							</select>
						</c:when>
					</c:choose>
				</c:forEach>
			</div>
			<div style="padding: 0 10px 20px 10px; position: relative;">
				<div id="chartdiv2"></div>
				<div id="chartdate-amline1"
					style="position: absolute; width: 120px; left: 14px; top: 28px;"></div>
			</div>
		</c:if>
		<!--场景交叉趋势图 -->
		<c:if test="${type==2 }">
			<div class="trend-sel">
				<select id="J-cross-f-sel"
					onchange="reBulidChartByPrdId(this.value);">
					<c:forEach items="${leftList }" var="item">
						<option value="${item.relProductId}">${item.relProductName}</option>
					</c:forEach>
				</select> <select class="J_fsl" id="J-cross-s-sel"
					onchange="reBulidChartByDataType(this.value);">
					<c:forEach items="${rightList }" var="item">
						<option value="${item.value }">${item.name }</option>
					</c:forEach>
				</select>
			</div>
			<div style="padding: 0 10px 20px 10px; position: relative;">
				<div id="chartdiv2"></div>
				<div id="chartdate-amline1"
					style="position: absolute; width: 120px; left: 14px; top: 28px;"></div>
			</div>
		</c:if>
	</div>
	<script type="text/javascript">
	var amchart_key ="${amchartKey}";
    E.domReady(function(){
    	
    	<c:if test="${type==1}">
    	   var kpicodelist = [
    	           	    <c:forEach items="${leftList}" var="item" varStatus="sel">               
    	           	    <c:choose>
    	       			<c:when test="${sel.index!=0}">,</c:when> 
    	       			</c:choose>
    	           			[
    	       				<c:forEach items="${item.properties}" var="it"> 
    	       					<c:choose>
    	       					<c:when test="${sel.index==0}">"${it.value}"</c:when>
    	       					<c:otherwise>,"${it.value}"</c:otherwise>
    	       					</c:choose>
    	                   	</c:forEach>
    	                   	]
    	                 </c:forEach>
    	           	]
    	           	
    	   var kpicodelist =[
    		   <c:forEach  items="${leftList}" var="item" varStatus="sel">
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
    	            
    	       		var parentsel = parseInt($("J_index_select").node.value);
    	       		var childsel = parseInt($("J_child_select").node.value);
    	       		kpif = kpicodelist[parentsel-1][childsel-1];
    	            var so1 = new SWFObject("<%=path%>/common/amchart/stock/amstock/amstock.swf", "amline1", "780", "300", "9", "#EFF0F2"); 
    	            so1.addVariable("path", "<%=path%>/common/amchart/stock/amstock/");
    	       		var parentsel = parseInt($("J_index_select").node.value);
    	       		kpif = kpicodelist[parentsel][childsel];
    	            var so1 = new SWFObject("<%=path%>/common/amchart/stock/amstock/amstock.swf", "amline1", "780", "300", "9", "#EFF0F2"); 
    	            so1.addVariable("path", "<%=path%>/common/amchart/stock/amstock/");
    	       		so1.addVariable("chart_id", "amline1");
    	       		so1.addParam("wmode","transparent");	
    	       		so1.addVariable("settings_file", escape("<%=path%>/stockChart/showStockChart?kpiCodes="+kpif+"&kpiType=1&reportDate="));
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
    	       			so1.addVariable("settings_file", escape("<%=path%>/stockChart/showStockChart?kpiCodes="+kpi+"&kpiType=1&reportDate="));
    	       			so1.write("chartdiv2");
    	       		}
    	</c:if>
    	<c:if test="${type==2}">
    	var repid = document.getElementById("J-cross-f-sel").value;
    	var datatype = document.getElementById("J-cross-s-sel").value;
		var so2 = new SWFObject("<%=path%>/common/amchart/stock/amstock/amstock.swf", "amline1", "780", "300", "9", "#EFF0F2"); 
        so2.addVariable("path", "<%=path%>/common/amchart/stock/amstock/");
		so2.addVariable("chart_id", "amline1");
		so2.addParam("wmode","transparent");	
		so2.addVariable("settings_file", escape("<%=path%>/stockChart/getPrdCrossStock?prdId=${productId}&relPrdId="+repid+"&dataType="+datatype+"&endDate=${date}"));
		so2.addVariable("key", amchart_key);
		so2.write("chartdiv2");

    	</c:if>
    })
		function amRolledOver(chart_id, date, period, data_object){
			$("chartdate-"+chart_id)&&$("chartdate-"+chart_id).setHtml(treadChartFormatDate(date));
		}
		function treadChartFormatDate(xdate) {
			
			var xyear = xdate.substring(0, 4) + "年";
		    var xmonth = xdate.substring(4, 6) + "月";
		    var xday = "";
		    if(xdate.length>6){
		    	 xday = xdate.substring(6, 8) + "日";
		   	}
		    return (xyear + xmonth + xday);
		} 
		
		//改变产品
		function reBulidChartByPrdId(repid){
			var datatype = document.getElementById("J-cross-s-sel").value;
			var so2 = new SWFObject("<%=path%>/common/amchart/stock/amstock/amstock.swf", "amline1", "780", "300", "9", "#EFF0F2"); 
                so2.addVariable("path", "<%=path%>/common/amchart/stock/amstock/");
		        so2.addVariable("chart_id", "amline1");
		        so2.addParam("wmode","transparent");	
				so2.addVariable("settings_file", escape("<%=path%>/stockChart/getPrdCrossStock?prdId=${productId}&relPrdId="+repid+"&dataType="+datatype+"&endDate=${date}"));
				so2.addVariable("key", amchart_key);
				so2.write("chartdiv2");
		};
		
		//交叉数或产品占比
		function reBulidChartByDataType(datatype){
			var repid = document.getElementById("J-cross-f-sel").value;
			var so2 = new SWFObject("<%=path%>/common/amchart/stock/amstock/amstock.swf", "amline1", "780", "300", "9", "#EFF0F2"); 
                so2.addVariable("path", "<%=path%>/common/amchart/stock/amstock/");
		        so2.addVariable("chart_id", "amline1");
		        so2.addParam("wmode","transparent");	
				so2.addVariable("settings_file", escape("<%=path%>/stockChart/getPrdCrossStock?prdId=${productId}&relPrdId="+repid+"&dataType="+datatype+"&endDate=${date}"));
				so2.addVariable("key", amchart_key);
				so2.write("chartdiv2");
		};
		
</script>


</body>
</html>