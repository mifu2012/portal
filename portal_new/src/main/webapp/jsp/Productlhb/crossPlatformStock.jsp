<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.infosmart.portal.pojo.DwpasCKpiInfo"%>	
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
	var amchart_key ="${amchartKey}";
	 var kpicodelist =
		  [
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
E.domReady(function(){
	var childsel = document.getElementById("J_child_select").value;
	var parentsel = document.getElementById("J_index_select").value;
	kpif = kpicodelist[parentsel][childsel];
    var so1 = new SWFObject("<%=path%>/common/amchart/stock/amstock/amstock.swf?", "amline1", "780", "300", "9", "#EFF0F2"); 
    so1.addVariable("path", "<%=path%>/common/amchart/stock/amstock/");
	so1.addVariable("chart_id", "amline1");
	so1.addParam("wmode","transparent");
	so1.addVariable("settings_file", escape("<%=path%>/stockChart/showStockChart?kpiCodes="+kpif+"&isPrd=1&kpiType=<%=DwpasCKpiInfo.KPI_TYPE_OF_MONTH%>&reportDate=${date}&showRightMenu=0"));
	so1.addVariable("key", amchart_key);
	so1.write("chartdiv2");
	 
});

function reBulidPlatformStock(){
				var childsel = document.getElementById("J_child_select").value;
				var parentsel = document.getElementById("J_index_select").value;
				kpif = kpicodelist[parentsel][childsel];
		        var so1 = new SWFObject("<%=path%>/common/amchart/stock/amstock/amstock.swf?", "amline1", "780", "300", "9", "#EFF0F2"); 
		        so1.addVariable("path", "<%=path%>/common/amchart/stock/amstock/");
				so1.addVariable("chart_id", "amline1");
				so1.addParam("wmode","transparent");
				so1.addVariable("settings_file", escape("<%=path%>/stockChart/showStockChart?kpiCodes="+kpif+"&isPrd=1&kpiType=<%=DwpasCKpiInfo.KPI_TYPE_OF_MONTH%>&reportDate=${date}&showRightMenu=0"));
				so1.addVariable("key", amchart_key);
				so1.write("chartdiv2");
  		
	       		$("J_index_select").change(function(){
	       			changeChart();
	       		});
	       		$("J_child_select").change(function(){
	       			changeChart();
	       		});
				//重新设置弹窗的高度
	    		function changeChart(){
	       			var kpi = "";
	       			var parentsel = parseInt($("J_index_select").value);
	       			var childsel  = parseInt($("J_child_select").value);
	       			kpi = kpicodelist[parentsel][childsel];
	       			so1.addVariable("settings_file", escape("<%=path%>/stockChart/showStockChart?kpiCodes="+kpi+"&isPrd=1&kpiType=<%=DwpasCKpiInfo.KPI_TYPE_OF_MONTH%>&reportDate=${date}&showRightMenu=0"));
	       			so1.write("chartdiv2");
	       	}
}
	</script>
<title>详细事件</title>
</head>
<body>
	<!-- 选择产品 -->
	<jsp:include page="../common/ChoiceProduct.jsp" />
			<div style=" height: auto; float: left;">
				<div style="margin-left: 15px;">
					<select id="J_index_select" onchange="reBulidPlatformStock()">
						<c:forEach items="${leftList}" var="item" varStatus="sel">
							<option value="${sel.index}">${item.name}</option>
						</c:forEach>
					</select> &nbsp;&nbsp;
					<c:forEach items="${leftList}" var="item" varStatus="sel">
						<c:choose>
							<c:when test="${sel.index eq 0}">
								<select class="J_fsl" id="J_child_select" onchange="reBulidPlatformStock()">
									<c:forEach items="${item.properties }" var="it"
										varStatus="selsel">
										<option value="${selsel.index}">${it.name }</option>
									</c:forEach>
								</select>
							</c:when>
						</c:choose>
					</c:forEach>
				</div>
				<div  style="margin-left: 15px;" id="chartdiv2" ></div>
			</div>
</body>
</html>