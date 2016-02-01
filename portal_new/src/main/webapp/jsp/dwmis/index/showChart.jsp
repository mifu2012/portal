<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="com.infosmart.portal.pojo.dwmis.DwmisModuleInfo"%>
<%@ page import="com.infosmart.portal.pojo.dwmis.DwmisLegendInfo"%>
<%@ page import="java.util.Date"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	session.setAttribute("POSITION_BOTTOM", DwmisModuleInfo.POSITION_BOTTOM);
	session.setAttribute("POSITION_LEFT", DwmisModuleInfo.POSITION_LEFT);
	session.setAttribute("POSITION_RIGHT", DwmisModuleInfo.POSITION_RIGHT);
	//趋势图
	session.setAttribute("STOCK_CHART", DwmisLegendInfo.STOCK_CHART);
	//饼图
	session.setAttribute("PIE_CHART", DwmisLegendInfo.PIE_CHART);
	//柱状图
	session.setAttribute("COLUMN_CHART", DwmisLegendInfo.COLUMN_CHART);
	//折线图
	session.setAttribute("LINE_CHART", DwmisLegendInfo.LINE_CHART);
	//组合图
	session.setAttribute("COLUMNORLINE_CHART", DwmisLegendInfo.COLUMNORLINE_CHART);
	//位置图形
	session.setAttribute("AREA_CHART", DwmisLegendInfo.AREA_CHART);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>唯品会瞭望塔分析系统-首页</title>
<link href="<%=basePath%>/common/css/cssz.css" rel="stylesheet"
	type="text/css" />
<link href="<%=basePath%>/common/css/lwtIndex.css" rel="stylesheet"
	type="text/css" />	
<script src="<%=basePath%>/common/js/My97DatePicker/WdatePicker.js"
	type="text/javascript"></script>
<script src="<%=basePath%>/common/amchart/stock/amstock/swfobject.js"
	type="text/javascript"></script>
<script src="<%=path%>/common/js/arale.js" type="text/javascript"></script>
<script src="<%=path%>/common/js/json.js" type="text/javascript"></script>
<script language='javascript'>

	var amchart_key = "${amchartKey}";
	window.onload = function() 
	{ 
	<c:forEach items="${moduleInfoList}" var="moduleInfo" varStatus="vs">
		<c:forEach items="${moduleInfo.legendInfos}" var="legendInfo" varStatus="status">
		<c:if test="${moduleInfo.tabShow==0}">
		<c:if test="${legendInfo.chartType==STOCK_CHART}">
			//趋势图-->
			var width_${status.index}=482/${fn:length(moduleInfo.legendInfos)};//487为CSS定义左边或右边的宽度
			<c:if test="${moduleInfo.position==POSITION_BOTTOM}">
				width_${status.index}=990/${fn:length(moduleInfo.legendInfos)};//996为CSS定义的底部宽度
			</c:if>
			//如果有多个图例，图例间隔为5px
			<c:if test="${fn:length(moduleInfo.legendInfos)>=2}">width_${status.index}=width_${status.index}-5;</c:if>
			var stock_${status.index} = new SWFObject("<%=path%>/common/amchart/stock/amstock/amstock.swf", "stock_${status.index}", width_${status.index}, ${moduleInfo.height}, "9", "#EFF0F2");
			stock_${status.index}.addVariable("path", "<%=path%>/common/amchart/stock/amstock/");
			stock_${status.index}.addVariable("chart_id", "amline1");
			stock_${status.index}.addParam("wmode","transparent");
			var url1="<%=path%>/misStockChart/showStockChart?1=1&kpiCodes=${legendInfo.kpiCodes}&needPercent=0&kpiType=${dateType}&staCode=${legendInfo.statCode}&queryDate=${queryDate}&rid="+Math.random();
			stock_${status.index}.addVariable("settings_file", escape(url1));
			stock_${status.index}.addVariable("key", amchart_key);
			stock_${status.index}.write("chart_${moduleInfo.moduleId}_${legendInfo.legendId}");
		</c:if>
		<c:if test="${legendInfo.chartType==PIE_CHART}">
		   //饼图
		   	var width_${status.index}=492/${fn:length(moduleInfo.legendInfos)};//487为CSS定义左边或右边的宽度
			<c:if test="${moduleInfo.position==POSITION_BOTTOM}">
				width_${status.index}=996/${fn:length(moduleInfo.legendInfos)};//996为CSS定义的底部宽度
			</c:if>
			//如果有多个图例，图例间隔为5px
			<c:if test="${fn:length(moduleInfo.legendInfos)>=2}">width_${status.index}=width_${status.index}-5;</c:if>	
			var stock_${status.index} = new SWFObject("<%=path%>/common/amchart/pie/ampie/ampie1.swf", "stock_${status.index}", width_${status.index}, ${moduleInfo.height}, "8.0.0", "#FFFFFF");
			stock_${status.index}.addVariable("path", "<%=path%>/common/amchart/pie/ampie/");
			stock_${status.index}.addVariable("settings_file", escape("<%=path%>/vm/dwmis/dwmis_pie_settings.xml"));
			stock_${status.index}.addVariable("data_file",escape("<%=path%>/DwmisPieChart/PieChartByOneKpiCode.htm?kpiCode=${legendInfo.kpiCodes}&queryDate=${queryDate}&dateType=${dateType}&staCode=${legendInfo.statCode}"));
			stock_${status.index}.addParam("wmode", "transparent");
			stock_${status.index}.addVariable("key", amchart_key);
			stock_${status.index}.write("chart_${moduleInfo.moduleId}_${legendInfo.legendId}");
		</c:if>
		<c:if test="${legendInfo.chartType==COLUMN_CHART}">
			//矩形图 996/${fn:length(moduleInfo.legendInfos)}
			var width_${status.index}=482/${fn:length(moduleInfo.legendInfos)};//487为CSS定义左边或右边的宽度
			<c:if test="${moduleInfo.position==POSITION_BOTTOM}">
				width_${status.index}=996/${fn:length(moduleInfo.legendInfos)};//996为CSS定义的底部宽度
			</c:if>
			//如果有多个图例，图例间隔为5px
			<c:if test="${fn:length(moduleInfo.legendInfos)>=2}">width_${status.index}=width_${status.index}-5;</c:if>				
			var stock_${status.index} = new SWFObject("<%=basePath%>/common/amchart/column/amcolumn/amcolumn.swf", "stock_${status.index}", width_${status.index}, ${moduleInfo.height}, "8", "#EFF0F2");
			//healthtre.addParam("wmode", "opaque");
			stock_${status.index}.addVariable("path", "<%=basePath%>	/common/js");
			stock_${status.index}.addVariable("chart_id", "column0");
			var url="<%=basePath%>/DwmisColumnOrLineChart/showChartSetting.html?1=1&kpiCodes=${legendInfo.kpiCodes};&chartTypes=${legendInfo.chartTypeDesc}&queryDate=${queryDate}&dateType=${dateType}&staCode=${legendInfo.statCode}";
			stock_${status.index}.addVariable("settings_file", escape(url));//设置文件
			var dataUrl="<%=basePath%>/DwmisColumnOrLineChart/showChartData.html?1=1&kpiCodes=${legendInfo.kpiCodes};&chartTypes=${legendInfo.chartTypeDesc}&queryDate=${queryDate}&dateType=${dateType}&staCode=${legendInfo.statCode}";
			stock_${status.index}.addVariable("data_file", escape(dataUrl));//数据文件
			stock_${status.index}.addVariable("key", amchart_key);
			stock_${status.index}.addParam("wmode", "transparent");
			stock_${status.index}.write("chart_${moduleInfo.moduleId}_${legendInfo.legendId}");
		</c:if>
		<c:if test="${legendInfo.chartType==LINE_CHART}">
			//折线图 
			var width_${status.index}=482/${fn:length(moduleInfo.legendInfos)};//487为CSS定义左边或右边的宽度
			<c:if test="${moduleInfo.position==POSITION_BOTTOM}">
				width_${status.index}=996/${fn:length(moduleInfo.legendInfos)};//996为CSS定义的底部宽度
			</c:if>	
			//如果有多个图例，图例间隔为5px
			<c:if test="${fn:length(moduleInfo.legendInfos)>=2}">width_${status.index}=width_${status.index}-5;</c:if>				
			var stock_${status.index} = new SWFObject("<%=path%>/common/amchart/bundle/amline/amline.swf", "$stock_${status.index}", width_${status.index}, ${moduleInfo.height}, "8", "#FFFFFF");
			stock_${status.index}.addParam("wmode", "opaque");
			stock_${status.index}.addVariable("path", "<%=path%>/amchart/bundle/amline/");
			stock_${status.index}.addVariable("chart_id", "amline");
			stock_${status.index}.addVariable("key", amchart_key);
			stock_${status.index}.addVariable("settings_file", escape("<%=path%>/DwmisLineGraph/ShowLineGraphSetting.htm?kpiCodes=${legendInfo.kpiCodes}&queryDate=${queryDate}&showWarnLine=${legendInfo.showWarnLine}"));
			stock_${status.index}.addVariable("data_file", escape("<%=path%>/DwmisLineGraph/ShowLineGraph.htm?kpiCodes=${legendInfo.kpiCodes}&queryDate=${queryDate}&dateType=${dateType}&staCode=${legendInfo.statCode}&showWarnLine=${legendInfo.showWarnLine}"));
			stock_${status.index}.write("chart_${moduleInfo.moduleId}_${legendInfo.legendId}");
		</c:if>
		<c:if test="${legendInfo.chartType==COLUMNORLINE_CHART}">
			//矩形图+折线
			var width_${status.index}=482/${fn:length(moduleInfo.legendInfos)};//487为CSS定义左边或右边的宽度
			<c:if test="${moduleInfo.position==POSITION_BOTTOM}">
				width_${status.index}=996/${fn:length(moduleInfo.legendInfos)};//996为CSS定义的底部宽度
			</c:if>
			//如果有多个图例，图例间隔为5px
			<c:if test="${fn:length(moduleInfo.legendInfos)>=2}">width_${status.index}=width_${status.index}-5;</c:if>				
			var stock_${status.index}  = new SWFObject("<%=basePath%>/common/amchart/column/amcolumn/amcolumn.swf", "stock_${status.index}",width_${status.index}, ${moduleInfo.height}, "8", "#EFF0F2");
			//healthtre.addParam("wmode", "opaque");
			stock_${status.index} .addVariable("path", "<%=basePath%>	/common/js");
			stock_${status.index} .addVariable("chart_id", "column0");
			var url="<%=basePath%>/DwmisColumnOrLineChart/showChartSetting.html?1=1&kpiCodes=${legendInfo.kpiCodes}&dateType=${dateType}&queryDate=${queryDate}&chartTypes=${legendInfo.chartTypeDesc}&staCode=${legendInfo.statCode}";
			stock_${status.index} .addVariable("settings_file", escape(url));//设置文件
			var dataUrl="<%=basePath%>/DwmisColumnOrLineChart/showChartData.html?1=1&kpiCodes=${legendInfo.kpiCodes}&dateType=${dateType}&queryDate=${queryDate}&chartTypes=${legendInfo.chartTypeDesc}&staCode=${legendInfo.statCode}";
			stock_${status.index} .addVariable("data_file", escape(dataUrl));//数据文件
			stock_${status.index} .addVariable("key", amchart_key);
			stock_${status.index} .addParam("wmode", "transparent");
			stock_${status.index}.write("chart_${moduleInfo.moduleId}_${legendInfo.legendId}");
		</c:if>
		<c:if test="${legendInfo.chartType==AREA_CHART}">
		//面积图
		var width_${status.index}=482/${fn:length(moduleInfo.legendInfos)};//487为CSS定义左边或右边的宽度
		<c:if test="${moduleInfo.position==POSITION_BOTTOM}">
			width_${status.index}=996/${fn:length(moduleInfo.legendInfos)};//996为CSS定义的底部宽度
		</c:if>	
		//如果有多个图例，图例间隔为5px
		<c:if test="${fn:length(moduleInfo.legendInfos)>=2}">width_${status.index}=width_${status.index}-5;</c:if>			
			var stock_${status.index}=new SWFObject("<%=path%>/common/amchart/stock/amstock/amstock.swf?id=<%=new Date().getTime()%>", "stock_${status.index}", width_${status.index},  ${moduleInfo.height}, "8", "#EFF0F2");
			stock_${status.index}.addParam("wmode", "transparent");
			stock_${status.index}.addVariable("path", "<%=path%>/common/amchart/stock/amstock/");
			stock_${status.index}.addVariable("chart_id", "stock");
			var url="<%=path%>/misStockChart/showStockAreaChart?1=1&kpiCodes=${legendInfo.kpiCodes}&needPercent=0&kpiType=${dateType}&queryDate=${queryDate}&staCode=${legendInfo.statCode}&rid="+Math.random();
			stock_${status.index}.addVariable("settings_file", escape(url));
			stock_${status.index}.addVariable("key", amchart_key);
			stock_${status.index}.write("chart_${moduleInfo.moduleId}_${legendInfo.legendId}");
		</c:if>
		</c:if>
		<c:if test="${moduleInfo.tabShow==1}">
			<c:if test="${legendInfo.chartType==STOCK_CHART}">
				//趋势图-->
				var width_${status.index}=482;//487为CSS定义左边或右边的宽度
				<c:if test="${moduleInfo.position==POSITION_BOTTOM}">
					width_${status.index}=990;//996为CSS定义的底部宽度
				</c:if>
				//如果有多个图例，图例间隔为5px
				<c:if test="${fn:length(moduleInfo.legendInfos)>=2}">width_${status.index}=width_${status.index}-5;</c:if>
				var stock_${status.index} = new SWFObject("<%=path%>/common/amchart/stock/amstock/amstock.swf", "stock_${status.index}", width_${status.index}, ${moduleInfo.height}, "9", "#EFF0F2");
				stock_${status.index}.addVariable("path", "<%=path%>/common/amchart/stock/amstock/");
				stock_${status.index}.addVariable("chart_id", "amline1");
				stock_${status.index}.addParam("wmode","transparent");
				var url1="<%=path%>/misStockChart/showStockChart?1=1&kpiCodes=${legendInfo.kpiCodes}&needPercent=0&kpiType=${dateType}&queryDate=${queryDate}&staCode=${legendInfo.statCode}&rid="+Math.random();
				stock_${status.index}.addVariable("settings_file", escape(url1));
				stock_${status.index}.addVariable("key", amchart_key);
				stock_${status.index}.write("J-${moduleInfo.moduleId}_${legendInfo.legendId}_box");
			</c:if>
			<c:if test="${legendInfo.chartType==PIE_CHART}">
			   //饼图
			   var width_${status.index}=492/${fn:length(moduleInfo.legendInfos)};//487为CSS定义左边或右边的宽度
				<c:if test="${moduleInfo.position==POSITION_BOTTOM}">
					width_${status.index}=996/${fn:length(moduleInfo.legendInfos)};//996为CSS定义的底部宽度
				</c:if>
				//如果有多个图例，图例间隔为5px
				<c:if test="${fn:length(moduleInfo.legendInfos)>=2}">width_${status.index}=width_${status.index}-5;</c:if>	
				var stock_${status.index} = new SWFObject("<%=path%>/common/amchart/pie/ampie/ampie1.swf", "stock_${status.index}", width_${status.index}, ${moduleInfo.height}, "8.0.0", "#FFFFFF");
				stock_${status.index}.addVariable("path", "<%=path%>/common/amchart/pie/ampie/");
				stock_${status.index}.addVariable("settings_file", escape("<%=path%>/vm/dwmis/dwmis_pie_settings.xml"));
				stock_${status.index}.addVariable("data_file",escape("<%=path%>/DwmisPieChart/PieChartByOneKpiCode.htm?kpiCode=${legendInfo.kpiCodes}&queryDate=${queryDate}&dateType=${dateType}&staCode=${legendInfo.statCode}"));
				stock_${status.index}.addParam("wmode", "transparent");
				stock_${status.index}.addVariable("key", amchart_key);
				stock_${status.index}.write("J-${moduleInfo.moduleId}_${legendInfo.legendId}_box");
			</c:if>
			<c:if test="${legendInfo.chartType==COLUMN_CHART}">
				//矩形图 996/${fn:length(moduleInfo.legendInfos)}
				var width_${status.index}=482;//487为CSS定义左边或右边的宽度
				<c:if test="${moduleInfo.position==POSITION_BOTTOM}">
					width_${status.index}=996;//996为CSS定义的底部宽度
				</c:if>
				//如果有多个图例，图例间隔为5px
				<c:if test="${fn:length(moduleInfo.legendInfos)>=2}">width_${status.index}=width_${status.index}-5;</c:if>				
				var stock_${status.index} = new SWFObject("<%=basePath%>/common/amchart/column/amcolumn/amcolumn.swf", "stock_${status.index}", width_${status.index}, ${moduleInfo.height}, "8", "#EFF0F2");
				//healthtre.addParam("wmode", "opaque");
				stock_${status.index}.addVariable("path", "<%=basePath%>	/common/js");
				stock_${status.index}.addVariable("chart_id", "column0");
				var url="<%=basePath%>/DwmisColumnOrLineChart/showChartSetting.html?1=1&kpiCodes=${legendInfo.kpiCodes};&chartTypes=${legendInfo.chartTypeDesc}&queryDate=${queryDate}&dateType=${dateType}&staCode=${legendInfo.statCode}";
				stock_${status.index}.addVariable("settings_file", escape(url));//设置文件
				var dataUrl="<%=basePath%>/DwmisColumnOrLineChart/showChartData.html?1=1&kpiCodes=${legendInfo.kpiCodes};&chartTypes=${legendInfo.chartTypeDesc}&queryDate=${queryDate}&dateType=${dateType}&staCode=${legendInfo.statCode}";
				stock_${status.index}.addVariable("data_file", escape(dataUrl));//数据文件
				stock_${status.index}.addVariable("key", amchart_key);
				stock_${status.index}.addParam("wmode", "transparent");
				stock_${status.index}.write("J-${moduleInfo.moduleId}_${legendInfo.legendId}_box");
			</c:if>
			<c:if test="${legendInfo.chartType==LINE_CHART}">
				//折线图
				var width_${status.index}=482;//487为CSS定义左边或右边的宽度
				<c:if test="${moduleInfo.position==POSITION_BOTTOM}">
					width_${status.index}=996;//996为CSS定义的底部宽度
				</c:if>	
				//如果有多个图例，图例间隔为5px
				<c:if test="${fn:length(moduleInfo.legendInfos)>=2}">width_${status.index}=width_${status.index}-5;</c:if>				
				var stock_${status.index} = new SWFObject("<%=path%>/common/amchart/bundle/amline/amline.swf", "$stock_${status.index}", width_${status.index}, ${moduleInfo.height}, "8", "#FFFFFF");
				stock_${status.index}.addParam("wmode", "opaque");
				stock_${status.index}.addVariable("path", "<%=path%>/amchart/bundle/amline/");
				stock_${status.index}.addVariable("chart_id", "amline");
				stock_${status.index}.addVariable("key", amchart_key);
				stock_${status.index}.addVariable("settings_file", escape("<%=path%>/DwmisLineGraph/ShowLineGraphSetting.htm?kpiCodes=${legendInfo.kpiCodes}&queryDate=${queryDate}&showWarnLine=${legendInfo.showWarnLine}"));
				stock_${status.index}.addVariable("data_file", escape("<%=path%>/DwmisLineGraph/ShowLineGraph.htm?kpiCodes=${legendInfo.kpiCodes}&queryDate=${queryDate}&dateType=${dateType}&staCode=${legendInfo.statCode}&showWarnLine=${legendInfo.showWarnLine}"));
				stock_${status.index}.write("J-${moduleInfo.moduleId}_${legendInfo.legendId}_box");
			</c:if>
			<c:if test="${legendInfo.chartType==COLUMNORLINE_CHART}">
				//矩形图+折线  
				var width_${status.index}=482;//487为CSS定义左边或右边的宽度
				<c:if test="${moduleInfo.position==POSITION_BOTTOM}">
					width_${status.index}=996;//996为CSS定义的底部宽度
				</c:if>
				//如果有多个图例，图例间隔为5px
				<c:if test="${fn:length(moduleInfo.legendInfos)>=2}">width_${status.index}=width_${status.index}-5;</c:if>				
				var stock_${status.index}  = new SWFObject("<%=basePath%>/common/amchart/column/amcolumn/amcolumn.swf", "stock_${status.index}",width_${status.index}, ${moduleInfo.height}, "8", "#EFF0F2");
				//healthtre.addParam("wmode", "opaque");
				stock_${status.index} .addVariable("path", "<%=basePath%>	/common/js");
				stock_${status.index} .addVariable("chart_id", "column0");
				var url="<%=basePath%>/DwmisColumnOrLineChart/showChartSetting.html?1=1&kpiCodes=${legendInfo.kpiCodes}&queryDate=${queryDate}&chartTypes=${legendInfo.chartTypeDesc}&dateType=${dateType}&staCode=${legendInfo.statCode}";
				stock_${status.index} .addVariable("settings_file", escape(url));//设置文件
				var dataUrl="<%=basePath%>/DwmisColumnOrLineChart/showChartData.html?1=1&kpiCodes=${legendInfo.kpiCodes}&queryDate=${queryDate}&chartTypes=${legendInfo.chartTypeDesc}&dateType=${dateType}&staCode=${legendInfo.statCode}";
				stock_${status.index} .addVariable("data_file", escape(dataUrl));//数据文件
				stock_${status.index} .addVariable("key", amchart_key);
				stock_${status.index} .addParam("wmode", "transparent");
				stock_${status.index}.write("J-${moduleInfo.moduleId}_${legendInfo.legendId}_box");
			</c:if>
			<c:if test="${legendInfo.chartType==AREA_CHART}">
			//面积图
			var width_${status.index}=482;//487为CSS定义左边或右边的宽度
			<c:if test="${moduleInfo.position==POSITION_BOTTOM}">
				width_${status.index}=996;//996为CSS定义的底部宽度
			</c:if>	
			//如果有多个图例，图例间隔为5px
			<c:if test="${fn:length(moduleInfo.legendInfos)>=2}">width_${status.index}=width_${status.index}-5;</c:if>			
				var stock_${status.index}=new SWFObject("<%=path%>/common/amchart/stock/amstock/amstock.swf?id=<%=new Date().getTime()%>", "stock_${status.index}", width_${status.index},  ${moduleInfo.height}, "8", "#EFF0F2");
				stock_${status.index}.addParam("wmode", "transparent");
				stock_${status.index}.addVariable("path", "<%=path%>/common/amchart/stock/amstock/");
				stock_${status.index}.addVariable("chart_id", "stock");
				var url="<%=path%>/misStockChart/showStockAreaChart?1=1&kpiCodes=${legendInfo.kpiCodes}&needPercent=0&kpiType=${dateType}&queryDate=${queryDate}&staCode=${legendInfo.statCode}&rid="+Math.random();
				stock_${status.index}.addVariable("settings_file", escape(url));
				stock_${status.index}.addVariable("key", amchart_key);
				stock_${status.index}.write("J-${moduleInfo.moduleId}_${legendInfo.legendId}_box");
			</c:if>
		</c:if>
		</c:forEach>
	</c:forEach>
	//进度条消失
	var loadingDiv=window.parent.parent.document.getElementById("loading_wait_Div");
	if(loadingDiv!=null) loadingDiv.style.display="none";
	}
	
	function timeChange(dateType){
		var loadingDiv=window.parent.parent.document.getElementById("loading_wait_Div");
		if(loadingDiv!=null) loadingDiv.style.display="";
		location.href='<%=path%>/dwmisHomePage/gotoHomePage.html?subjectId=${subjectId}&dateType='+dateType;
	}
	var xmlHttp;
	//大事记详细数据
	function amClickedOnEvent(chart_id, date, description, id, url){
		//alert(id);
			date= date.trim();
			document.getElementById('dsjtck_pop').className='pop_block';
			 var dsjtck_pop=window.document.getElementById("dsjtck_pop");
				if(dsjtck_pop==null) return;
			   //设置DIV高度
			    var divTop=0;
				var clientHeight=window.parent.document.getElementById('iframepage').height;
				if(window.parent!=null)
				{
					divTop=window.parent.parent.document.body.scrollTop+window.parent.parent.document.documentElement.scrollTop+10;
				}else
				{
			        divTop=document.body.scrollTop+document.documentElement.scrollTop;
				}
				dsjtck_pop.style.top=(divTop-75)+"px";
				dsjtck_pop.style.position="absolute";
				
				var dateDesc = "";
				if (date.length == 6) {
					dateDesc = date.substring(0, 4) + "年"
							+ date.substring(4, 6) + "月";
				} else if (date.length == 8) {
					dateDesc = date.substring(0, 4) + "年"
							+ date.substring(4, 6) + "月"
							+ date.substring(6, 8) + "日";
				}
				document.getElementById('eventInfoDate').innerHTML = dateDesc;
				
		         if(window.XMLHttpRequest)
		         {
		             xmlHttp = new XMLHttpRequest();
		         }
		         xmlHttp.onreadystatechange=callback;//function(){}
		         xmlHttp.open("POST","<%=path%>/MisEvent/showEventMessageByEventId.html?1=1&eventId="+ id,true);
		         xmlHttp.send(null); 
	};
	
	function callback(){
		   if(xmlHttp.readyState == 4)
		         {
		             if(xmlHttp.status == 200)
		             {
		            	//document.getElementById("json_data_div").innerHTML=xmlHttp.responseText;
		            	var misEvent =JSON.parse(xmlHttp.responseText);
// 		            	alert(misEvent.title);
		            	//var eventTypeName = misEvent.eventTypeName;
						//var eventTitle = misEvent.title;
						//var content = misEventMessage.content;
						document.getElementById('eventInfoTypeName').innerHTML = misEvent.eventTypeName;
						document.getElementById('eventInfoTitle').innerHTML = misEvent.title;					
						document.getElementById('eventInfoContent').innerHTML = misEvent.content;
		             }
		         }
	}
</script>

<script language='javascript'>
	//TABN切换
E.domReady(function () {	    
	A($$(".box-nav-li")).each(function(el){
		el.click(function(){
			var did = el.attr("id");
			A($$(".box-nav-li")).each(function(es){
				if(es.attr("moduleId")==el.attr("moduleId"))
				{
					es.removeClass("box-li-act");
				}				
			});
			el.addClass("box-li-act");
			A($$(".J-tabbox")).each(function(es){
				if(es.attr("moduleId")==el.attr("moduleId"))
				{
				   es.addClass("hidden");
				}
			});
			$(did+"_box").removeClass("hidden");
		});
	});	
});
</script>
</head>
<body class="indexbody">
	<!--头部结束-->
	<div class="pop_none" id="dsjtck_pop" style="color:#000">
		<div  style="z-index: 999;">
			<div class="layer4"
				style="background-color: #fff; z-index: 9999; margin-top: 10px"
				id="dsjtck_pop_title">
				<div style="margin:0px 0px 0px 0px">
				<table border="0" style="height: 100%;"  width="765">
					<tr>
						<td colspan="3"  valign="top">
							<h2>
								查看详情 <b> <a href="Javascript:void(0)"
									onclick="javascript:getElementById('dsjtck_pop').className='lwtpop_gb'">关闭</a>
								</b>							</h2>						</td>
					</tr>
					<tr>
						<td colspan="3">
							<div class="layer4_mm">
								<div align="left"></div>
							</div>						</td>
					</tr>
					<tr>
						<td align="center" colspan="3">
							<div style="width: 700px; font-size: 14px; font-weight: bold; margin-top:-15px"
								id="eventInfoTitle" ></div>						</td>
					</tr>
					<tr>
						<td colspan="3">
							<div class="layer4_mm">
								<div align="left"></div>
							</div>						</td>
					</tr>
					<tr>
						<td colspan="3">
						<div style="margin:auto; width:340px; margin-top:-15px">
						  <div style="float: left; padding: 0 10px">
						    <div align="center">发生时间:</div>
						  </div>
						  <div id="eventInfoDate" style="float: left; padding: 0 10px"></div>
						  <div style="float: left; padding: 0 10px">
						    <div align="center">事件类别：</div>
					  </div>						  <div id="eventInfoTypeName" style="float: left; padding: 0 10px"></div>
					  </div>
					  </td></tr>
					<tr>
						<td colspan="3">
								<div align="left"></div>
								</td>
							<td width="200">
						
						</td>
					</tr>
					<tr>
						<td colspan="3">
							<div id="eventInfoContent"
								style="position: relative; overflow-x: hidden; ;width: 765px; height: 200px; overflow-y: auto; margin: 0px 0px 0px 0px">							</div>						</td>
					</tr>
				</table>
		  </div>
			</div>
		</div>
	</div>
	<div class="index_home">
		<div style="font-size:1px;height: 1px;">
			<img src="<%=path%>/dwmis/images/wph_8.gif" />
		</div>

		<!--第一部分-->
		<div class="clear"></div>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bgcolor="#f7f7f7" class="lwt_tag">
			<tr>
				<td valign="top">
				<div align="right">
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							style="padding-top: 5px">
							<tr>
								<td  align="right"><c:if test="${subjectInfo.dateType==4 }">
										<input type="button" id="dayButton" name="dayButton"
											style="width: 35px; height: 24px;cursor:pointer;" value="日"
											onclick="timeChange(1002);"
											<c:if test="${dateType==1002}">disabled</c:if> />
										|<input type="button" id="weekButton" name="weekButton"
											style="width: 35px; height: 24px;cursor:pointer;" value="周"
											onclick="timeChange(1003);"
											<c:if test="${dateType==1003}">disabled</c:if> />
										|<input type="button" id="monthButton" name="monthButton"
											style="width: 35px; height: 24px;cursor:pointer;" value="月"
											onclick="timeChange(1004);" 
											<c:if test="${dateType==1004}">disabled</c:if> />
									</c:if></td>
							</tr>
						</table>
					</div></td>
			</tr>

		</table>
		<input type="hidden" id="dateType" value="${dateType}" />
		<div id="tab">
			<div class="clear"></div>
			<!--业务量开始-->
			<c:if test="${empty moduleInfoList }">
				<div align="center" style="color: red">该主题没有关联模块</div>
			</c:if>
			<c:forEach items="${moduleInfoList }" var="moduleInfo">
				<c:if test="${moduleInfo.position  eq POSITION_LEFT }">
					<div class="clear"></div>
					<div
						style="margin-right:20px;WIDTH:487px;float: left; height:${moduleInfo.height};">
						<div class="datattop5">
							<div class="index_title_pd">${moduleInfo.moduleName }</div>
						</div>
						<table cellpadding="0" cellspacing="10">
							<div class="index_title_yytop5"></div>
							<c:if test="${moduleInfo.tabShow ==0 }">
								<c:forEach items="${moduleInfo.legendInfos }" var="legendInfo"
									varStatus="status">
									<!--如果有多个图例，间隔为5px -->
									<c:if test="${status.index!=0}">
										<td width='5px'>&nbsp;</td>
									</c:if>
									<td><div
											id="chart_${moduleInfo.moduleId}_${legendInfo.legendId }"
											style='border: 0px solid red;'></div></td>
								</c:forEach>
							</c:if>
							<c:if test="${moduleInfo.tabShow ==1 }">
								<c:if test="${fn:length(moduleInfo.legendInfos)==1 }">
									<c:forEach items="${moduleInfo.legendInfos }" var="legendInfo"
										varStatus="status">
										<!--如果有多个图例，间隔为5px -->
										<c:if test="${status.index!=0}">
											<td width='5px'>&nbsp;</td>
										</c:if>
										<div id="J-${moduleInfo.moduleId}_${legendInfo.legendId}_box"></div>
									</c:forEach>
								</c:if>
								<c:if test="${fn:length(moduleInfo.legendInfos)>1 }">
									<!-- tab显示 -->
									<div style="width: 480px; height: auto">
										<div class="box-nav">
											<ul class="box-nav-ul">
												<c:forEach items="${moduleInfo.legendInfos}"
													var="legendInfo" varStatus="status">
													<!-- tab 头 -->
													<c:if test="${status.index==0}">
														<li class="box-nav-li box-li-act"
															id="J-${moduleInfo.moduleId}_${legendInfo.legendId}"
															title="${legendInfo.legendName}"><div
																style='width: 110px; line-height: 25px; text-overflow: ellipsis; white-space: nowrap; overflow: hidden;'>&nbsp;&nbsp;${legendInfo.legendName}</div></li>
													</c:if>
													<c:if test="${status.index!=0}">
														<li class="box-nav-li"
															id="J-${moduleInfo.moduleId}_${legendInfo.legendId}"
															title="${legendInfo.legendName}"><div
																style='width: 110px; line-height: 25px; text-overflow: ellipsis; white-space: nowrap; overflow: hidden;'>&nbsp;&nbsp;${legendInfo.legendName}</div></li>
													</c:if>
													<script language='javascript'>
												   if(document.getElementById("J-${moduleInfo.moduleId}_${legendInfo.legendId}")!=null) document.getElementById("J-${moduleInfo.moduleId}_${legendInfo.legendId}").setAttribute("moduleId","${moduleInfo.moduleId}");
												</script>
												</c:forEach>
											</ul>
										</div>
									</div>
									<c:forEach items="${moduleInfo.legendInfos}" var="legendInfo"
										varStatus="status">
										<c:if test="${status.index==0}">
											<!-- tab内容 -->
											<div class="normalbox mt15 J-tabbox"
												id="J-${moduleInfo.moduleId}_${legendInfo.legendId}_box"></div>
										</c:if>
										<c:if test="${status.index!=0}">
											<div class="normalbox mt15 J-tabbox hidden"
												id="J-${moduleInfo.moduleId}_${legendInfo.legendId}_box"></div>
										</c:if>
										<script language='javascript'>
									      if(document.getElementById("J-${moduleInfo.moduleId}_${legendInfo.legendId}_box")!=null)
										  {
										     document.getElementById("J-${moduleInfo.moduleId}_${legendInfo.legendId}_box").setAttribute("moduleId","${moduleInfo.moduleId}");
										  }
										</script>
									</c:forEach>
								</c:if>
							</c:if>
						</table>
					</div>
				</c:if>
				<c:if test="${moduleInfo.position  eq POSITION_RIGHT }">
					<div
						style="WIDTH: 487px; float: right; height:${moduleInfo.height};">
						<div class="datattop5">
							<div class="index_title_pd">${moduleInfo.moduleName }</div>
						</div>
						<table cellpadding="0" cellspacing="10">
							<div class="index_title_yytop5"></div>
							<c:if test="${moduleInfo.tabShow ==0 }">
								<c:forEach items="${moduleInfo.legendInfos }" var="legendInfo"
									varStatus="status">
									<!--如果有多个图例，间隔为5px -->
									<c:if test="${status.index!=0}">
										<td width='5px'>&nbsp;</td>
									</c:if>
									<td><div
											id="chart_${moduleInfo.moduleId}_${legendInfo.legendId }"
											style='border: 0px solid red;'></div></td>
								</c:forEach>
							</c:if>
							<c:if test="${moduleInfo.tabShow ==1 }">
								<c:if test="${fn:length(moduleInfo.legendInfos)==1 }">
									<c:forEach items="${moduleInfo.legendInfos }" var="legendInfo"
										varStatus="status">
										<td><div
												id="J-${moduleInfo.moduleId}_${legendInfo.legendId}_box"></div></td>
									</c:forEach>
								</c:if>
								<c:if test="${fn:length(moduleInfo.legendInfos)>1 }">
									<!-- tab显示 -->
									<div style="width: 480px; height: auto">
										<div class="box-nav">
											<ul class="box-nav-ul">
												<c:forEach items="${moduleInfo.legendInfos}"
													var="legendInfo" varStatus="status">
													<!-- tab 头 -->
													<c:if test="${status.index==0}">
														<li class="box-nav-li box-li-act"
															id="J-${moduleInfo.moduleId}_${legendInfo.legendId}"
															title="${legendInfo.legendName}"><div
																style='width: 110px; line-height: 25px; text-overflow: ellipsis; white-space: nowrap; overflow: hidden;'>&nbsp;&nbsp;${legendInfo.legendName}</div></li>
													</c:if>
													<c:if test="${status.index!=0}">
														<li class="box-nav-li"
															id="J-${moduleInfo.moduleId}_${legendInfo.legendId}"
															title="${legendInfo.legendName}"><div
																style='width: 110px; line-height: 25px; text-overflow: ellipsis; white-space: nowrap; overflow: hidden;'>&nbsp;&nbsp;${legendInfo.legendName}</div></li>
													</c:if>
													<script language='javascript'>
												if(document.getElementById("J-${moduleInfo.moduleId}_${legendInfo.legendId}")!=null) document.getElementById("J-${moduleInfo.moduleId}_${legendInfo.legendId}").setAttribute("moduleId","${moduleInfo.moduleId}");
												</script>
												</c:forEach>
											</ul>
										</div>
									</div>
									<c:forEach items="${moduleInfo.legendInfos}" var="legendInfo"
										varStatus="status">
										<c:if test="${status.index==0}">
											<!-- tab内容 -->
											<div class="normalbox mt15 J-tabbox"
												id="J-${moduleInfo.moduleId}_${legendInfo.legendId}_box"></div>
										</c:if>
										<c:if test="${status.index!=0}">
											<div class="normalbox mt15 J-tabbox hidden"
												id="J-${moduleInfo.moduleId}_${legendInfo.legendId}_box"></div>
										</c:if>
										<script language='javascript'>
									      if(document.getElementById("J-${moduleInfo.moduleId}_${legendInfo.legendId}_box")!=null)
										  {
										     document.getElementById("J-${moduleInfo.moduleId}_${legendInfo.legendId}_box").setAttribute("moduleId","${moduleInfo.moduleId}");
										  }
										</script>
									</c:forEach>
								</c:if>
							</c:if>
						</table>
					</div>
				</c:if>
				<c:if test="${moduleInfo.position  eq POSITION_BOTTOM }">
					<div
						style="WIDTH: 997px; float: left; height: ${moduleInfo.height};">
						<div class="datattop57">
							<div class="index_title_pd">${moduleInfo.moduleName }</div>
						</div>
						<table border='0' cellpadding="0" cellspacing="0" width="100%">
							<div class="index_title_yytop52"></div>
							<c:if test="${moduleInfo.tabShow ==0 }">
								<c:forEach items="${moduleInfo.legendInfos }" var="legendInfo"
									varStatus="status">
									<!--如果有多个图例，间隔为5px -->
									<c:if test="${status.index!=0}">
										<td width='5px'>&nbsp;</td>
									</c:if>
									<td><div
											id="chart_${moduleInfo.moduleId}_${legendInfo.legendId }"
											style='border: 0px solid red;'></div></td>
								</c:forEach>
							</c:if>
							<c:if test="${moduleInfo.tabShow ==1 }">
								<!-- TAB切换显示-->
								<c:if test="${fn:length(moduleInfo.legendInfos)==1 }">
									<!-- 如只有一个，默认为并排显示 -->
									<c:forEach items="${moduleInfo.legendInfos }" var="legendInfo"
										varStatus="status">
										<td><div
												id="J-${moduleInfo.moduleId}_${legendInfo.legendId}_box"></div></td>
									</c:forEach>
								</c:if>
								<c:if test="${fn:length(moduleInfo.legendInfos)>1 }">
									<!-- tab显示 -->
									<div class="box-nav">
										<ul class="box-nav-ul">
											<c:forEach items="${moduleInfo.legendInfos}" var="legendInfo"
												varStatus="status">
												<!-- tab 头 -->
												<c:if test="${status.index==0}">
													<li class="box-nav-li box-li-act"
														id="J-${moduleInfo.moduleId}_${legendInfo.legendId}"
														title="${legendInfo.legendName}"><div
															style='width: 110px; line-height: 25px; text-overflow: ellipsis; white-space: nowrap; overflow: hidden;'>&nbsp;&nbsp;${legendInfo.legendName}</div></li>
												</c:if>
												<c:if test="${status.index!=0}">
													<li class="box-nav-li"
														id="J-${moduleInfo.moduleId}_${legendInfo.legendId}"
														title="${legendInfo.legendName}"><div
															style='width: 110px; line-height: 25px; text-overflow: ellipsis; white-space: nowrap; overflow: hidden;'>&nbsp;&nbsp;${legendInfo.legendName}</div></li>
												</c:if>
												<script language='javascript'>
												if(document.getElementById("J-${moduleInfo.moduleId}_${legendInfo.legendId}")!=null)  document.getElementById("J-${moduleInfo.moduleId}_${legendInfo.legendId}").setAttribute("moduleId","${moduleInfo.moduleId}");
												</script>
											</c:forEach>
										</ul>
									</div>
									<c:forEach items="${moduleInfo.legendInfos}" var="legendInfo"
										varStatus="status">
										<c:if test="${status.index==0}">
											<!-- tab内容 -->
											<div class="normalbox mt15 J-tabbox"
												id="J-${moduleInfo.moduleId}_${legendInfo.legendId}_box"></div>
										</c:if>
										<c:if test="${status.index!=0}">
											<div class="normalbox mt15 J-tabbox hidden"
												id="J-${moduleInfo.moduleId}_${legendInfo.legendId}_box"></div>
										</c:if>
										<script language='javascript'>
									      if(document.getElementById("J-${moduleInfo.moduleId}_${legendInfo.legendId}_box")!=null)
										  {
										     document.getElementById("J-${moduleInfo.moduleId}_${legendInfo.legendId}_box").setAttribute("moduleId","${moduleInfo.moduleId}");
										  }
										</script>
									</c:forEach>
								</c:if>
							</c:if>
						</table>
					</div>
				</c:if>
			</c:forEach>
		</div>
	</div>
</body>
</html>
