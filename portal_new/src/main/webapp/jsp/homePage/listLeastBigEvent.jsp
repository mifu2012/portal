<!--最近的大事记-->
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
<style>
#tagr {
	background-color: #eff0f2
}
</style>
<link href="<%=path%>/common/css/cssz.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/common/css/base.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/common/css/lwt.css" rel="stylesheet" type="text/css" />
<!--<link href="<%=path%>/common/css/style.css" rel="stylesheet" type="text/css" />-->
<script src="<%=path%>/common/js/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
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
    window.onload=function()
    {
    	<c:forEach items="${bigEventList}" var="erkl">
		  var obj=document.getElementById("div_${erkl.eventId}");
		  if(obj==null)
		   {
			  obj=document.createElement("div");
			  obj.id="div_${erkl.eventId}";
			  obj.innerHTML="${erkl.relateKpiCodes}";
			  obj.style.display="none";
			  document.body.appendChild(obj);
		   }else
		   {
			   obj.innerHTML=obj.innerHTML+";"+"${erkl.relateKpiCodes}";
		   }
	    </c:forEach>
    }
    //产品聚焦大事件谈窗
    function pop_func(eventTitle,eventTypeName,eventStartDate,eventId){
	   //return;
	   //window.parent.document.getElementById("windowOpenDiv_content").style.height="480";
 	   var eventRelCodes= document.getElementById("div_"+eventId).innerHTML;
		//var beginDate = document.getElementById()
	   document.getElementById('eventDateDiv').innerHTML=eventStartDate;
	   document.getElementById('eventTypeNameDiv').innerHTML=eventTypeName;
	   document.getElementById('eventTitleDiv').innerHTML=eventTitle;
	   document.getElementById('bigEventContentDiv').innerHTML=document.getElementById("evnetContent_"+eventId).innerHTML;
 	   var bigEventChart = new SWFObject("<%=path%>/common/amchart/stock/amstock/amstock.swf", "amline2", "100%", "290", "9", "#EFF0F2");
 		   bigEventChart.addVariable("path", "<%=path%>/common/amchart/stock/amstock/");
 		   bigEventChart.addVariable("chart_id", "amline2");
 		   bigEventChart.addParam("wmode","transparent");
 	   var chartUrl="<%=path%>/stockChart/showStockChart?1=1&isPrd=1&kpiCodes="+ eventRelCodes + "&beginDate=" +eventStartDate+ "&reportDate=${date}&needPercent=1&isMIS=&showRightMenu=0&rid="+ Math.random();
		   bigEventChart.addVariable("settings_file", escape(chartUrl));
		   bigEventChart.addVariable("key", amchart_key);
		   bigEventChart.write("bigEventChartDiv");
	  //显示弹窗内容
	  //alert(window.parent.document.getElementById("windowOpenDiv_content").style.display);
	  try
	  {
		  //window.parent.showWindowOpenDiv("大事件详情",document.getElementById("showBigEventDetailDiv").innerHTML);
		  //window.parent.createDivWindow("eventItemDiv","大事件详情",1000,530,document.getElementById("showBigEventDetailDiv").innerHTML);
		  var url="<%=basePath%>/BigEvent/showBigEventDetails.html?eventId="+eventId;
		  window.parent.openDivWindow("eventItemDiv_"+eventId,"大事件详情",1000,420,url);
	  }
	  catch (e)
	  {
		  alert('对不起!加载数据失败:'+e.message);
	  }
	}
</script>
<body>
	<div id="tagr"
		style="padding: 10px 0px 0px 0px; height: 350px; background: #eff0f2; border: 1px solid #dbe1e6; border-top: 0px;">
		<c:forEach items="${bigEventList}" var="profocus">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				style="color: #666; line-height: 30px">
				<tr>
					<td><div style='line-height:25px;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;width:300px;'><font size='2'>【${profocus.eventTypeName}】</font><a href="javascript:void(0);"
						id="${profocus.eventId}"
						onclick="pop_func('${profocus.title}','${profocus.eventTypeName}','${profocus.eventStartDate}','${profocus.eventId}')" title='${profocus.title}'>
						 ${profocus.title}
						</a></div>
						<div id="evnetContent_${profocus.eventId}" style="display: none;">${profocus.content}</div>
					</td>
				</tr>
			</table>
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				style="color: #666"></table>
		</c:forEach>
	</div>
	<!--显示详细内容-->
	<div style="display: none;" id="showBigEventDetailDiv">
		<table border="0" width="100%">
			<tr>
				<td colspan="3">
					<div class="layer2_mm">
						<div align="left"></div>
					</div>
				</td>
			</tr>
			<tr>
				<td  align="center" colspan="3">
					<div style="width:700px;font-size: 14px;font-weight: bold;" id="eventTitleDiv"/>
				</td>
			</tr>
			<!--
			<tr>
				<td colspan="3">
					<div class="layer2_mm">
						<div align="left"></div>
					</div>
				</td>
			</tr>
			-->
			<tr>
				<td colspan="3" align='center'>
					<label>发生时间:</label>
					<label id="eventDateDiv"></label>
					&nbsp;&nbsp;&nbsp;
					<label>事件类别：</label>
					<label id="eventTypeNameDiv"></label>
				</td>
			</tr>
			<!--
			<tr>
				<td colspan="3">
					<div class="layer2_mm">
						<div align="left"></div>
					</div>
				</td>
			</tr>
			-->
			<tr>
				<td colspan="3">
				    <!--事件详情-->
					<div  id="bigEventContentDiv"  style="position: relative;width: 750px; height:120px;overflow-y:auto;margin: 0px 0px 0px 10px;border:0px solid red;">
						
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="3">
				    <!--图表-->
					<div align="center" id="bigEventChartDiv" style="padding-top: 0px"></div>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>
