<!--风向标老式风格-->
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
<title>风向标</title>
<style type='text/css'>
 body{ background:#eff0f2}
</style>
<link href="<%=path%>/common/css/cssz.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/common/css/base.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/common/css/lwt.css" rel="stylesheet" type="text/css" />
<!--<link href="<%=path%>/common/css/style.css" rel="stylesheet"
	type="text/css" />-->
<script src="<%=path%>/common/js/My97DatePicker/WdatePicker.js"
	type="text/javascript"></script>
<script src="<%=path%>/common/js/miaov.js" type="text/javascript"></script>
<script src="<%=path%>/common/js/amcharts.js" type="text/javascript"></script>
<script src="<%=path%>/common/js/raphael.js" type="text/javascript"></script>
<script src="<%=path%>/common/js/arale.js" type="text/javascript"></script>
<script src="<%=path%>/common/js/FusionCharts_Trial.js" type="text/javascript"></script>
<!--
<script src="<%=path%>/common/js/arale.core-1.1.js" type="text/javascript" charset="utf-8"></script>
-->
<script src="<%=path%>/common/amchart/stock/amstock/swfobject.js" type="text/javascript"></script>
<script src="<%=path%>/common/js/amfallback.js" type="text/javascript"></script>
</head>
<script type="text/javascript">	
    var amchart_key = "${amchartKey}";
	window.onload = function() {
        if(window.XMLHttpRequest)
        {
            xmlHttp = new XMLHttpRequest();
        }
        xmlHttp.onreadystatechange=callback;
        xmlHttp.open("POST","<%=path%>/index/showAskWind?queryDate=${date}",
				true);
		xmlHttp.send(null);
	}
	//风向标
	var xmlHttp;
	function callback(){
	   if(xmlHttp.readyState == 4){
       		if(xmlHttp.status == 200){
           		var tagcloud = new SWFObject("<%=path%>/common/js/my99tagcloud.swf", "tagcloud","300", "270", "8", "#EFF0F2", "high");
				tagcloud.addParam("tplayername", "SWF");
				tagcloud.addParam("splayername", "SWF");
				tagcloud.addParam("type", "application/x-shockwave-flash");
				tagcloud.addParam("mediawrapchecked", "true");
				tagcloud.addParam("pluginspage","http://www.macromedia.com/go/getflashplayer");
				tagcloud.addParam("name", "tagcloudflash");
				tagcloud.addParam("wmode", "transparent");
				tagcloud.addParam("allowscriptaccess", "always");
				tagcloud.addVariable("tcolor", "0x333333");
				tagcloud.addVariable("mode", "tags");
				tagcloud.addVariable("distr", "true");
				tagcloud.addVariable("tspeed", "100");
				tagcloud.addVariable("tagcloud", xmlHttp.responseText);
				tagcloud.write("showWindChartDiv");
			}
		}
	}
</script>
<body>
	<!--风向标JS-->
	<!-- 老式风格去掉 -->
	<!-- 
	<p>
		<c:if test="not empty askWindVanes">
			<c:forEach items="${askWindVaneDatasList}" var="askWindVane">
				<nobr> <a class="fz15" href="#">${askWindVane.catTitle}</a>
				</nobr>
			</c:forEach>
		</c:if>
	</p>
	<script type="text/javascript" src="<%=path%>/common/js/tag.js"></script>
	-->
	<!-- 显示风向标图 -->
	<div id="showWindChartDiv"></div>
	<!--风向标结束-->
</body>
</html>
