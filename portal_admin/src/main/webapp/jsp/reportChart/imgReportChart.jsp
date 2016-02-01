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
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>数据管理平台</title>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script src="<%=basePath%>/js/jquery-1.5.1.min.js" type="text/javascript"></script>
<script type="text/javascript">
//定义饼图的高宽度
var imgWidth=150;
var imgHeight=150;
	$(document).ready(function(){
		var heigth=0;
		var chartCollectionDiv=document.getElementById("chartCollectionDiv");
		var chartDiv=chartCollectionDiv.childNodes;
		for(var i=0;i<chartDiv.length;i++)
		{
			if(heigth<chartDiv[i].offsetTop+chartDiv[i].offsetHeight)
			{
				heigth=chartDiv[i].offsetTop+chartDiv[i].offsetHeight;	
			}
		}
		document.getElementById("chartCollectionDiv").style.height=heigth+"px";
		window.parent.changeReportChartIframeSize(heigth);
	});
	</script>
</head>
<body>
		<div style="float:left;margin-left:0px height=0px;" id="chartCollectionDiv">
			<c:forEach items="${reportFieldList}" var="reportField">
				<c:choose>
					<c:when test="${reportField.chartType eq '1'}">
						<!-- position: absolute;  float: left;-->
						<div
							style="position: absolute;left:${reportField.left-10}px;top:${reportField.top-20}px;width:${reportField.width}px;height:${reportField.height}px;border:1px solid #babdb4;margin-left:5px;">
							<img src="<%=path%>/images/pie.png" id="img_${reportField.chartId}" style="position: absolute;"></img>
						</div>
						<script type="text/javascript">
									document.getElementById("img_${reportField.chartId}").style.left=(${reportField.width}-imgWidth)/2+"px";
									document.getElementById("img_${reportField.chartId}").style.top=(${reportField.height}-imgHeight)/2+"px";
									document.getElementById("img_${reportField.chartId}").style.height=imgHeight+"px";
									document.getElementById("img_${reportField.chartId}").style.width=imgWidth+"px";
							</script>
					</c:when>
					<c:when test="${reportField.chartType eq '2'}">
						<div
							style="position: absolute;left:${reportField.left-10}px;top:${reportField.top-20}px;width:${reportField.width}px;height:${reportField.height}px;border:1px solid #babdb4;margin-left:5px;">
							<img src="<%=path%>/images/spline.jpg"
								style="width: 100%; height: 100%"></img>
						</div>
					</c:when>
					<c:when test="${reportField.chartType eq '3'}">
						<div
							style="position: absolute;left:${reportField.left-10}px;top:${reportField.top-20}px;width:${reportField.width}px;height:${reportField.height}px;border:1px solid #babdb4;margin-left:5px;">
							<img src="<%=path%>/images/column.jpg"
								style="width: 100%; height: 100%"></img>
						</div>
					</c:when>
					<c:when test="${reportField.chartType eq '4'}">
						<div
							style="position: absolute;left:${reportField.left-10}px;top:${reportField.top-20}px;width:${reportField.width}px;height:${reportField.height}px;border:1px solid #babdb4;margin-left:5px;">
							<img src="<%=path%>/images/line.jpg"
								style="width: 100%; height: 100%"></img>
						</div>
					</c:when>
					<c:when test="${reportField.chartType eq '5'}">
						<div
							style="position: absolute;left:${reportField.left-10}px;top:${reportField.top-20}px;width:${reportField.width}px;height:${reportField.height}px;border:1px solid #babdb4;margin-left:5px;">
							<img src="<%=path%>/images/column_line.jpg"
								style="width: 100%; height: 100%"></img>
						</div>
					</c:when>
				</c:choose>
			</c:forEach>
		</div>
</body>
</html>
