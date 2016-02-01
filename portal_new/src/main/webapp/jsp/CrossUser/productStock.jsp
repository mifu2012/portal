<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<link href="<%=path%>/common/css/css.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="<%=path%>/common/js/amcharts.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/amfallback.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/arale.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/swfobject.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		var heigth=0;
		if(navigator.userAgent.indexOf("MSIE")>0){
		//IE
			height=document.body.scrollHeight;
		}else{
		//非IE
			height=document.documentElement.scrollHeight;
		}
	});
</script>
<script type="text/javascript">
E.domReady(function(){
	var amchart_key ="${amchartKey}";
	var repid = document.getElementById("J-cross-f-sel").value;
	var datatype = document.getElementById("J-cross-s-sel").value;
	var so2 = new SWFObject("<%=path%>/common/amchart/stock/amstock/amstock.swf", "amline1", "780", "300", "9", "#EFF0F2"); 
	so2.addVariable("path", "<%=path%>/common/amchart/stock/amstock/");
	so2.addVariable("chart_id", "amline1");
	so2.addParam("wmode","transparent");	
	so2.addVariable("settings_file", escape("<%=path%>/stockChart/getPrdCrossStock?prdId=${productId}&relPrdId="+repid+"&dataType="+datatype+"&endDate=${date}&showRightMenu=0"));
	so2.addVariable("key", amchart_key);
	so2.write("chartdiv2");
});


	//改变产品
	var amchart_key ="${amchartKey}";
	function reBulidChartByPrdId(repid){
		var datatype = document.getElementById("J-cross-s-sel").value;
		var so2 = new SWFObject("<%=path%>/common/amchart/stock/amstock/amstock.swf", "amline1", "780", "300", "9", "#EFF0F2"); 
	        so2.addVariable("path", "<%=path%>/common/amchart/stock/amstock/");
	        so2.addVariable("chart_id", "amline1");
	        so2.addParam("wmode","transparent");	
			so2.addVariable("settings_file", escape("<%=path%>/stockChart/getPrdCrossStock?prdId=${productId}&relPrdId="+repid+"&dataType="+datatype+"&endDate=${date}&showRightMenu=0"));
			so2.addVariable("key", amchart_key);
			so2.write("chartdiv2");
	};

	//交叉数或产品占比
	function reBulidChartByDataType(datatype){
		var repid = document.getElementById("J-cross-f-sel").value;
		var so2 = new SWFObject("<%=path%>/common/amchart/stock/amstock/amstock.swf", "amline1", "780", "260", "9", "#EFF0F2"); 
	        so2.addVariable("path", "<%=path%>/common/amchart/stock/amstock/");
	        so2.addVariable("chart_id", "amline1");
	        so2.addParam("wmode","transparent");
			so2.addVariable("settings_file", escape("<%=path%>/stockChart/getPrdCrossStock?prdId=${productId}&relPrdId="+repid+"&dataType="+datatype+"&endDate=${date}&showRightMenu=0"));
			so2.addVariable("key", amchart_key);
			so2.write("chartdiv2");
	};
	</script>
<title>详细事件</title>
</head>
<body>
	<!-- 选择产品 -->
	<jsp:include page="../common/ChoiceProduct.jsp" />
	<div style=" margin: auto; position: absolute; background: #fff; z-index:9999">
			<div style="margin-left: 15px;">
				<select id="J-cross-f-sel"
					onchange="reBulidChartByPrdId(this.value);">
					<c:forEach items="${crossUsers }" var="item">
						<option value="${item.relProductId}">${item.relProductName}</option>
					</c:forEach>
				</select> &nbsp;&nbsp;&nbsp;&nbsp;<select class="J_fsl" id="J-cross-s-sel"
					onchange="reBulidChartByDataType(this.value);">
					<c:forEach items="${rightList }" var="item">
						<option value="${item.value }">${item.name }</option>
					</c:forEach>
				</select>
			</div>
			<div style="margin-left: 15px;" id="chartdiv2"></div>
	</div>
</body>
</html>