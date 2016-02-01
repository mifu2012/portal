<!--CHART图显示-->
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="com.infosmart.portal.pojo.DwpasCKpiInfo"%>
<%@ page import="com.infosmart.portal.pojo.DwpasCColumnInfo"%>
<%@ page import="com.infosmart.portal.pojo.DwpasStKpiData"%>
<%@ page import="com.infosmart.portal.util.DateUtils"%>
<%@ page import="java.util.Date"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	//定义栏目常量
	//趋势图
	session.setAttribute("CHART_TYPE_STOCK",
			DwpasCColumnInfo.CHART_TYPE_STOCK);
	//饼图
	session.setAttribute("CHART_TYPE_PIE",
			DwpasCColumnInfo.CHART_TYPE_PIE);
	//矩形图
	session.setAttribute("CHART_TYPE_COLUMN",
			DwpasCColumnInfo.CHART_TYPE_COLUMN);
	//折线图
	session.setAttribute("CHART_TYPE_LINE",
			DwpasCColumnInfo.CHART_TYPE_LINE);
	//矩形+折线图
	session.setAttribute("CHART_TYPE_COLUMN_AND_LINE",
			DwpasCColumnInfo.CHART_TYPE_COLUMN_AND_LINE);
	//条形图
	session.setAttribute("CHART_TYPE_BAR",
			DwpasCColumnInfo.CHART_TYPE_BAR);
	//面积图
	session.setAttribute("CHART_TYPE_AREA",
			DwpasCColumnInfo.CHART_TYPE_AREA);	
	//日期
	Object crtDate=session.getAttribute("date");
	if(crtDate==null) crtDate=DateUtils.getPreviousDate(new Date());
	//指标类型
	session.setAttribute("date",crtDate);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>CHART图显示</title>
<style>
 body{ background:##EFF0F2}

 #tagr {
	background-color: #EFF0F2
 }
</style>
<!-- TAB切换 -->
<link href="<%=path%>/common/css/cssz.css" rel="stylesheet"
	type="text/css" />
<link href="<%=path%>/common/css/base.css" rel="stylesheet"
	type="text/css" />
<link href="<%=path%>/common/css/lwt.css" rel="stylesheet"
	type="text/css" />
<!-- TAB切换 -->
<!-- 生成图形 -->	
<script src="<%=path%>/common/js/My97DatePicker/WdatePicker.js"
	type="text/javascript"></script>
<script src="<%=path%>/common/amchart/stock/amstock/swfobject.js"
	type="text/javascript"></script>
<script src="<%=path%>/common/js/arale.js" type="text/javascript"></script>
<!-- 生成图形 -->	
</head>
<script type="text/javascript">
//CHART_KEY
var amchart_key = "${amchartKey}";
var kpiType="${kpiType}";
var crtDate="${queryDate}";
<%-- if(parseInt(kpiType)=="NAN") kpiType="<%=DwpasCKpiInfo.KPI_TYPE_OF_DAY%>";
if(kpiType==<%=DwpasCKpiInfo.KPI_TYPE_OF_MONTH%>)
{
	if(crtDate.length==8)
	{
		crtDate=crtDate.substring(0,6);//YYYYMMMDD取放六位
	}else
	{
		var date=new Date(crtDate);//yyyy-MM-dd或yyyy/MM/dd
		var crtYear=date.getFullYear();
		var crtMonth=date.getMonth()+1;
		if((crtMonth+"").length==1) crtMonth="0"+crtMonth;
		crtDate=crtYear+""+crtMonth;
	}
} --%>
//以什么结束
String.prototype.endWith=function(str){
	if(str==null||str==""||this.length==0||str.length>this.length)
	{
	   return false;
	}
	if(this.substring(this.length-str.length)==str)
	{
	   return true;
	}
	else
	{
	  return false;
	}
	return true;
}
String.prototype.startWith=function(str){
	if(str==null||str==""||this.length==0||str.length>this.length)
	{
	  return false;
	}
	if(this.substr(0,str.length)==str)
	{
	  return true;
	}
	else
	{
	  return false;
	}
	return true;
}
var tempEvengDivId="";
//大事记详细数据
/*
function amClickedOnEvent(chart_id, date, description, id, url)
{ 
  try
  {
	  //原来的
     var url="<%=path%>/stockChart/getDetailEvent.html?eventId="+id+"&random="+Math.random();
	 tempEvengDivId="eventItemDiv";
	 window.parent.openDivWindow(tempEvengDivId,"大事件",1000,200,url);
  }
  catch (e)
  {
	  try
	  {
		  openDivWindow(tempEvengDivId,"大事件",1000,200,url);
	  }catch(ee)
	  {
          alert('对不起!加载数据失败:'+ee.message);
	  }
  }
}
*/
</script>
<body style="margin-top: 0px;">
<c:if test="${fn:length(columnInfoList)==0}">
	<!-- 没有数据 -->
	<div style="color: red;" align='center'>当前模块没有任何栏目</div>
</c:if>
<c:if test="${fn:length(columnInfoList)==1}">
	<!-- 只有一个栏目 -->
	<c:forEach items="${columnInfoList}" var="columnInfo" varStatus="status">
		<!--存放图的DIV-->
		<div id="chart_${columnInfo.columnId}" style='z-index:100;height:100%;background-color:#EFF0F2;'></div>
		<!--生成图-->
		<script language='javascript'>
		  var width=document.body.clientWidth;
		  var parentFrmHeight=300;
		  var parentFrm=window.parent.document.getElementById("iframepage_${moduleId}");
		  if(parentFrm==null)
		  {
			  parentFrm=document.getElementById("content_${moduleId}");
			  parentFrmHeight=(parentFrm==null?300:parentFrm.style.height);
		  }else
		  {
              parentFrmHeight=(parentFrm==null?300:parentFrm.height);
		  }
		  if((parentFrmHeight+"").indexOf("px")!=-1)
		  {
			  parentFrmHeight=parentFrmHeight.substring(0,parentFrmHeight.indexOf("px"));
		  }
	      var height=parentFrmHeight-5;
		  //alert('sss');
		<c:choose>
		   <c:when test="${columnInfo.chartType eq CHART_TYPE_STOCK}">
			//趋势图
			//alert('${fn:replace(columnInfo.columnId, "-", "_")}');
			var stock_${fn:replace(columnInfo.columnId, "-", "_")}=new SWFObject("<%=path%>/common/amchart/stock/amstock/amstock.swf?id=<%=new Date().getTime()%>", "stock${fn:replace(columnInfo.columnId, "-", "_")}", "100%", "100%", "8", "#EFF0F2");
				stock_${fn:replace(columnInfo.columnId, "-", "_")}.addParam("wmode", "transparent");
				stock_${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("path", "<%=path%>/common/amchart/stock/amstock/");
				stock_${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("chart_id", "stock${fn:replace(columnInfo.columnId, "-", "_")}");
				var needPercent=${columnInfo.needPercent};//${fn:length(columnInfo.kpiInfoList)}>=2?1:0;
				var url="<%=basePath%>/stockChart/showStockChart?1=1&isPrd=1&kpiCodes=${columnInfo.kpiCodes};&needPercent="+needPercent+"&reportDate=${queryDate}&kpiType=${kpiType}&rid="+Math.random();
				stock_${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("settings_file", escape(url));
				stock_${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("key", amchart_key);
				/*
				//关联模块ID
				stock_${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("moduleId", "${moduleId}");
				
				//指标Codes
				stock_${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("kpiCodes", "${columnInfo.kpiCodes}");
				//日期类型
				stock_${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("kpiType", "${kpiType}");
				*/
				//
				stock_${fn:replace(columnInfo.columnId, "-", "_")}.write("chart_${columnInfo.columnId}");
		   </c:when>
		   <c:when test="${columnInfo.chartType eq CHART_TYPE_AREA}">
			//面积图
			var stock_${fn:replace(columnInfo.columnId, "-", "_")}=new SWFObject("<%=path%>/common/amchart/stock/amstock/amstock.swf?id=<%=new Date().getTime()%>", "stock${fn:replace(columnInfo.columnId, "-", "_")}", "100%", "100%", "8", "#EFF0F2");
				stock_${fn:replace(columnInfo.columnId, "-", "_")}.addParam("wmode", "transparent");
				stock_${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("path", "<%=path%>/common/amchart/stock/amstock/");
				stock_${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("chart_id", "stock${fn:replace(columnInfo.columnId, "-", "_")}");
				var url="<%=basePath%>/stockChart/showStockAreaChart?1=1&isPrd=1&kpiCodes=${columnInfo.kpiCodes};&needPercent=0&kpiType=${kpiType}&reportDate="+crtDate+"&rid="+Math.random();
				stock_${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("settings_file", escape(url));
				stock_${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("key", amchart_key);
				/*
				//关联模块ID
				stock_${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("moduleId", "${moduleId}");
				
				//指标Codes
				stock_${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("kpiCodes", "${columnInfo.kpiCodes}");
				//日期类型
				stock_${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("kpiType", "${kpiType}");
				*/
				//
				stock_${fn:replace(columnInfo.columnId, "-", "_")}.write("chart_${columnInfo.columnId}");
		   </c:when>		   
		   <c:when test="${columnInfo.chartType eq CHART_TYPE_PIE}">
			//饼图
			var pie_${fn:replace(columnInfo.columnId, "-", "_")}= new SWFObject("<%=path%>/common/amchart/pie/ampie/ampie1.swf", "ampie${fn:replace(columnInfo.columnId, "-", "_")}", height, height, "8.0.0", "#EFF0F2");
				pie_${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("path", "<%=path%>/common/amchart/pie/ampie/");
				pie_${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("settings_file", escape("<%=path%>/common/amchart/pie/ampie/commonpie-settings-legends.xml"));
				//数据类型
				var kpiType=${kpiType};
				var dateType="<%=DwpasStKpiData.DATE_TYPE_OF_DAY%>";
				if(kpiType==3) dateType="<%=DwpasStKpiData.DATE_TYPE_OF_MONTH%>";
				if(kpiType==2) dateType="<%=DwpasStKpiData.DATE_TYPE_OF_WEEK%>";
				//查询数据
				pie_${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("data_file",escape("<%=basePath%>/PieChart/showPieChart?kpiCode=${columnInfo.kpiCodes};&reportDate="+crtDate+"&dateType="+dateType+"&rid="+Math.random()));
				pie_${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("key", amchart_key);
				/*
				//关联模块ID
				pie_${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("moduleId", "${moduleId}");
				*/
				//
				pie_${fn:replace(columnInfo.columnId, "-", "_")}.addParam("wmode", "transparent");
				pie_${fn:replace(columnInfo.columnId, "-", "_")}.write("chart_${columnInfo.columnId}");
			</c:when>
			<c:when test="${columnInfo.chartType eq CHART_TYPE_LINE}">
			//折线
			var line${fn:replace(columnInfo.columnId, "-", "_")} = new SWFObject("<%=path%>/common/amchart/amline/amline.swf", "line${fn:replace(columnInfo.columnId, "-", "_")}", "100%", "100%", "8", "#EFF0F2");
				//healthtre.addParam("wmode", "opaque");
				line${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("path", "<%=path%>/common/js");
				line${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("chart_id", "line${fn:replace(columnInfo.columnId, "-", "_")}");
				var url="<%=basePath%>/columnOrLineChart/showLineChartSetting.html?1=1&kpiCodes=${columnInfo.kpiCodes};&needPercent=0&kpiType=${kpiType}&reportDate="+crtDate+"&chartTypes=${columnInfo.chartTypeDesc}&rid="+Math.random();
				line${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("settings_file", escape(url));//设置文件
				var dataUrl="<%=basePath%>/columnOrLineChart/showChartData.html?1=1&kpiCodes=${columnInfo.kpiCodes};&needPercent=0&kpiType=${kpiType}&reportDate="+crtDate+"&chartTypes=${columnInfo.chartTypeDesc}&rid="+Math.random();
				line${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("data_file", escape(dataUrl));//数据文件
				line${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("key", amchart_key);
				/*
				//关联模块ID
				line${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("moduleId", "${moduleId}");
				*/
				//
				line${fn:replace(columnInfo.columnId, "-", "_")}.addParam("wmode","transparent");
				//line${fn:replace(columnInfo.columnId, "-", "_")}.addParam("bgcolor","#000000");
				line${fn:replace(columnInfo.columnId, "-", "_")}.write("chart_${columnInfo.columnId}");			
			</c:when>
			<c:when test="${columnInfo.chartType eq CHART_TYPE_BAR}">
			//条型图
			var bar${fn:replace(columnInfo.columnId, "-", "_")} = new SWFObject("<%=path%>/common/amchart/column/amcolumn/amcolumn.swf", "bar${fn:replace(columnInfo.columnId, "-", "_")}", "100%", "100%", "8", "#EFF0F2");
				//healthtre.addParam("wmode", "opaque");
				bar${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("path", "<%=path%>/common/js");
				bar${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("chart_id", "bar${fn:replace(columnInfo.columnId, "-", "_")}");
				var url="<%=basePath%>/columnOrLineChart/showChartSetting.html?1=1&kpiCodes=${columnInfo.kpiCodes};&needPercent=0&kpiType=${kpiType}&reportDate="+crtDate+"&chartTypes=${columnInfo.chartTypeDesc}&rid="+Math.random();
				bar${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("settings_file", escape(url));//设置文件
				var dataUrl="<%=basePath%>/columnOrLineChart/showChartData.html?1=1&kpiCodes=${columnInfo.kpiCodes};&needPercent=0&kpiType=${kpiType}&reportDate="+crtDate+"&chartTypes=${columnInfo.chartTypeDesc}&rid="+Math.random();
				bar${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("data_file", escape(dataUrl));//数据文件
				bar${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("key", amchart_key);
				/*
				//关联模块ID
				bar${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("moduleId", "${moduleId}");
				*/
				//
				bar${fn:replace(columnInfo.columnId, "-", "_")}.addParam("wmode","transparent");
				//line${fn:replace(columnInfo.columnId, "-", "_")}.addParam("bgcolor","#000000");
				bar${fn:replace(columnInfo.columnId, "-", "_")}.write("chart_${columnInfo.columnId}");			
			</c:when>			
			<c:when test="${columnInfo.chartType eq CHART_TYPE_COLUMN ||columnInfo.chartType eq CHART_TYPE_COLUMN_AND_LINE}">
			//折线或矩形或折线+矩形
			var column${fn:replace(columnInfo.columnId, "-", "_")} = new SWFObject("<%=path%>/common/amchart/column/amcolumn/amcolumn.swf", "column${fn:replace(columnInfo.columnId, "-", "_")}", "100%", height, "8", "#EFF0F2");
				//healthtre.addParam("wmode", "opaque");
				column${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("path", "<%=path%>/common/js");
				column${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("chart_id", "column${fn:replace(columnInfo.columnId, "-", "_")}");
				var url="<%=basePath%>/columnOrLineChart/showChartSetting.html?1=1&kpiCodes=${columnInfo.kpiCodes};&needPercent=0&kpiType=${kpiType}&reportDate="+crtDate+"&chartTypes=${columnInfo.chartTypeDesc}&rid="+Math.random();
				column${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("settings_file", escape(url));//设置文件
				var dataUrl="<%=basePath%>/columnOrLineChart/showChartData.html?1=1&kpiCodes=${columnInfo.kpiCodes};&needPercent=0&kpiType=${kpiType}&reportDate="+crtDate+"&chartTypes=${columnInfo.chartTypeDesc}&rid="+Math.random();
				column${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("data_file", escape(dataUrl));//数据文件
				column${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("key", amchart_key);
				/*
				//关联模块ID
				column${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("moduleId", "${moduleId}");
				*/
				//
				column${fn:replace(columnInfo.columnId, "-", "_")}.addParam("wmode","transparent");
				column${fn:replace(columnInfo.columnId, "-", "_")}.write("chart_${columnInfo.columnId}");
			//折线或矩形
			</c:when>
			<c:otherwise>
			   //未知图类型
			   document.getElementById("chart_${columnInfo.columnId}").innerHTML="<font color='red'>未知图类型：${columnInfo.chartType}</font>";
			</c:otherwise>
			</c:choose>
		</script>
	</c:forEach>
</c:if>
<!-- 多个栏目 -->
<c:if test="${fn:length(columnInfoList)>1}">
    <div id="tagr" style='border:0px solid red;height:100%;'>
    <c:if test="${tabShow==1}">
       <!-- 如果就并排显示 -->
       <c:forEach items="${columnInfoList}" var="columnInfo" varStatus="status">
			<!-- 栏目：${columnInfo.columnName} -->
			<c:set var="marginLeft">0</c:set>
			<c:if test="${status.index!=0}"><c:set var="marginLeft">5</c:set></c:if>
			<div id="chart_${columnInfo.columnId}" style='z-index:100;height:100%;background-color:#EFF0F2;float:left;margin-left:${marginLeft}px;'></div>
			<!--生成图-->
			<script language='javascript'>
// 			  var width=document.body.clientWidth;
			  var parentFrmHeight=300;
			  var parentFrm=window.parent.document.getElementById("iframepage_${moduleId}");
			  if(parentFrm==null)
			  {
				  parentFrm=document.getElementById("content_${moduleId}");
				  parentFrmHeight=(parentFrm==null?300:parentFrm.style.height);
			  }else
			  {
				  parentFrmHeight=(parentFrm==null?300:parentFrm.height);
			  }
			  if((parentFrmHeight+"").indexOf("px")!=-1)
			  {
				  parentFrmHeight=parentFrmHeight.substring(0,parentFrmHeight.indexOf("px"));
			  }
			  var width=parentFrm.offsetWidth;
			  var height=parentFrmHeight-5;
			  var chartWidth=width/${fn:length(columnInfoList)}-10;//${fn:length(columnInfoList)}
			  //alert('sss');
			<c:choose>
			   <c:when test="${columnInfo.chartType eq CHART_TYPE_STOCK}">
				//趋势图
				//alert('${fn:replace(columnInfo.columnId, "-", "_")}');
				var stock_${fn:replace(columnInfo.columnId, "-", "_")}=new SWFObject("<%=path%>/common/amchart/stock/amstock/amstock.swf?id=<%=new Date().getTime()%>", "stock${fn:replace(columnInfo.columnId, "-", "_")}", chartWidth, "100%", "8", "#EFF0F2");
					stock_${fn:replace(columnInfo.columnId, "-", "_")}.addParam("wmode", "transparent");
					stock_${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("path", "<%=path%>/common/amchart/stock/amstock/");
					stock_${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("chart_id", "stock${fn:replace(columnInfo.columnId, "-", "_")}");
					var needPercent=${columnInfo.needPercent};//${fn:length(columnInfo.kpiInfoList)}>=2?1:0;
					var url="<%=basePath%>/stockChart/showStockChart?1=1&isPrd=1&kpiCodes=${columnInfo.kpiCodes};&needPercent="+needPercent+"&reportDate=${queryDate}&kpiType=${kpiType}&rid="+Math.random();
					stock_${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("settings_file", escape(url));
					stock_${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("key", amchart_key);
					/*
					//关联模块ID
					stock_${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("moduleId", "${moduleId}");
					
					//指标Codes
					stock_${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("kpiCodes", "${columnInfo.kpiCodes}");
					//日期类型
					stock_${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("kpiType", "${kpiType}");
					*/
					//
					stock_${fn:replace(columnInfo.columnId, "-", "_")}.write("chart_${columnInfo.columnId}");
			   </c:when>
			   <c:when test="${columnInfo.chartType eq CHART_TYPE_AREA}">
				//面积图
				var stock_${fn:replace(columnInfo.columnId, "-", "_")}=new SWFObject("<%=path%>/common/amchart/stock/amstock/amstock.swf?id=<%=new Date().getTime()%>", "stock${fn:replace(columnInfo.columnId, "-", "_")}", chartWidth, "100%", "8", "#EFF0F2");
					stock_${fn:replace(columnInfo.columnId, "-", "_")}.addParam("wmode", "transparent");
					stock_${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("path", "<%=path%>/common/amchart/stock/amstock/");
					stock_${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("chart_id", "stock${fn:replace(columnInfo.columnId, "-", "_")}");
					var url="<%=basePath%>/stockChart/showStockAreaChart?1=1&isPrd=1&kpiCodes=${columnInfo.kpiCodes};&needPercent=0&kpiType=${kpiType}&reportDate="+crtDate+"&rid="+Math.random();
					stock_${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("settings_file", escape(url));
					stock_${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("key", amchart_key);
					/*
					//关联模块ID
					stock_${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("moduleId", "${moduleId}");
					
					//指标Codes
					stock_${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("kpiCodes", "${columnInfo.kpiCodes}");
					//日期类型
					stock_${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("kpiType", "${kpiType}");
					*/
					//
					stock_${fn:replace(columnInfo.columnId, "-", "_")}.write("chart_${columnInfo.columnId}");
			   </c:when>		   
			   <c:when test="${columnInfo.chartType eq CHART_TYPE_PIE}">
				//饼图
				var pie_${fn:replace(columnInfo.columnId, "-", "_")}= new SWFObject("<%=path%>/common/amchart/pie/ampie/ampie1.swf", "ampie${fn:replace(columnInfo.columnId, "-", "_")}", height, height, "8.0.0", "#EFF0F2");
					pie_${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("path", "<%=path%>/common/amchart/pie/ampie/");
					pie_${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("settings_file", escape("<%=path%>/common/amchart/pie/ampie/commonpie-settings-legends.xml"));
					//数据类型
					var kpiType=${kpiType};
					var dateType="<%=DwpasStKpiData.DATE_TYPE_OF_DAY%>";
					if(kpiType==3) dateType="<%=DwpasStKpiData.DATE_TYPE_OF_MONTH%>";
					if(kpiType==2) dateType="<%=DwpasStKpiData.DATE_TYPE_OF_WEEK%>";
					//查询数据
					pie_${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("data_file",escape("<%=basePath%>/PieChart/showPieChart?kpiCode=${columnInfo.kpiCodes};&reportDate="+crtDate+"&dateType="+dateType+"&rid="+Math.random()));
					pie_${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("key", amchart_key);
					/*
					//关联模块ID
					pie_${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("moduleId", "${moduleId}");
					*/
					//
					pie_${fn:replace(columnInfo.columnId, "-", "_")}.addParam("wmode", "transparent");
					pie_${fn:replace(columnInfo.columnId, "-", "_")}.write("chart_${columnInfo.columnId}");
				</c:when>
				<c:when test="${columnInfo.chartType eq CHART_TYPE_LINE}">
				//折线
				var line${fn:replace(columnInfo.columnId, "-", "_")} = new SWFObject("<%=path%>/common/amchart/amline/amline.swf", "line${fn:replace(columnInfo.columnId, "-", "_")}", chartWidth, "100%", "8", "#EFF0F2");
					//healthtre.addParam("wmode", "opaque");
					line${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("path", "<%=path%>/common/js");
					line${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("chart_id", "line${fn:replace(columnInfo.columnId, "-", "_")}");
					var url="<%=basePath%>/columnOrLineChart/showLineChartSetting.html?1=1&kpiCodes=${columnInfo.kpiCodes};&needPercent=0&kpiType=${kpiType}&reportDate="+crtDate+"&chartTypes=${columnInfo.chartTypeDesc}&rid="+Math.random();
					line${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("settings_file", escape(url));//设置文件
					var dataUrl="<%=basePath%>/columnOrLineChart/showChartData.html?1=1&kpiCodes=${columnInfo.kpiCodes};&needPercent=0&kpiType=${kpiType}&reportDate="+crtDate+"&chartTypes=${columnInfo.chartTypeDesc}&rid="+Math.random();
					line${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("data_file", escape(dataUrl));//数据文件
					line${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("key", amchart_key);
					/*
					//关联模块ID
					line${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("moduleId", "${moduleId}");
					*/
					//
					line${fn:replace(columnInfo.columnId, "-", "_")}.addParam("wmode","transparent");
					//line${fn:replace(columnInfo.columnId, "-", "_")}.addParam("bgcolor","#000000");
					line${fn:replace(columnInfo.columnId, "-", "_")}.write("chart_${columnInfo.columnId}");			
				</c:when>
				<c:when test="${columnInfo.chartType eq CHART_TYPE_BAR}">
				//条型图
				var bar${fn:replace(columnInfo.columnId, "-", "_")} = new SWFObject("<%=path%>/common/amchart/column/amcolumn/amcolumn.swf", "bar${fn:replace(columnInfo.columnId, "-", "_")}", chartWidth, "100%", "8", "#EFF0F2");
					//healthtre.addParam("wmode", "opaque");
					bar${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("path", "<%=path%>/common/js");
					bar${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("chart_id", "bar${fn:replace(columnInfo.columnId, "-", "_")}");
					var url="<%=basePath%>/columnOrLineChart/showChartSetting.html?1=1&kpiCodes=${columnInfo.kpiCodes};&needPercent=0&kpiType=${kpiType}&reportDate="+crtDate+"&chartTypes=${columnInfo.chartTypeDesc}&rid="+Math.random();
					bar${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("settings_file", escape(url));//设置文件
					var dataUrl="<%=basePath%>/columnOrLineChart/showChartData.html?1=1&kpiCodes=${columnInfo.kpiCodes};&needPercent=0&kpiType=${kpiType}&reportDate="+crtDate+"&chartTypes=${columnInfo.chartTypeDesc}&rid="+Math.random();
					bar${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("data_file", escape(dataUrl));//数据文件
					bar${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("key", amchart_key);
					/*
					//关联模块ID
					bar${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("moduleId", "${moduleId}");
					*/
					//
					bar${fn:replace(columnInfo.columnId, "-", "_")}.addParam("wmode","transparent");
					//line${fn:replace(columnInfo.columnId, "-", "_")}.addParam("bgcolor","#000000");
					bar${fn:replace(columnInfo.columnId, "-", "_")}.write("chart_${columnInfo.columnId}");			
				</c:when>			
				<c:when test="${columnInfo.chartType eq CHART_TYPE_COLUMN ||columnInfo.chartType eq CHART_TYPE_COLUMN_AND_LINE}">
				//折线或矩形或折线+矩形
				var column${fn:replace(columnInfo.columnId, "-", "_")} = new SWFObject("<%=path%>/common/amchart/column/amcolumn/amcolumn.swf", "column${fn:replace(columnInfo.columnId, "-", "_")}", chartWidth, height, "8", "#EFF0F2");
					//healthtre.addParam("wmode", "opaque");
					column${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("path", "<%=path%>/common/js");
					column${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("chart_id", "column${fn:replace(columnInfo.columnId, "-", "_")}");
					var url="<%=basePath%>/columnOrLineChart/showChartSetting.html?1=1&kpiCodes=${columnInfo.kpiCodes};&needPercent=0&kpiType=${kpiType}&reportDate="+crtDate+"&chartTypes=${columnInfo.chartTypeDesc}&rid="+Math.random();
					column${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("settings_file", escape(url));//设置文件
					var dataUrl="<%=basePath%>/columnOrLineChart/showChartData.html?1=1&kpiCodes=${columnInfo.kpiCodes};&needPercent=0&kpiType=${kpiType}&reportDate="+crtDate+"&chartTypes=${columnInfo.chartTypeDesc}&rid="+Math.random();
					column${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("data_file", escape(dataUrl));//数据文件
					column${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("key", amchart_key);
					/*
					//关联模块ID
					column${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("moduleId", "${moduleId}");
					*/
					//
					column${fn:replace(columnInfo.columnId, "-", "_")}.addParam("wmode","transparent");
					column${fn:replace(columnInfo.columnId, "-", "_")}.write("chart_${columnInfo.columnId}");
				//折线或矩形
				</c:when>
				<c:otherwise>
				   //未知图类型
				   document.getElementById("chart_${columnInfo.columnId}").innerHTML="<font color='red'>未知图类型：${columnInfo.chartType}</font>";
				</c:otherwise>
				</c:choose>
			</script>         
       </c:forEach>
    </c:if>
    <c:if test="${tabShow!=1}">
       <!-- 如果就Tab切换显示 -->
		<!--有多个栏目-->
		<div class="box-nav" style="margin-top:5px;">
			 <ul class="box-nav-ul">
				<c:forEach items="${columnInfoList}" var="columnInfo" varStatus="status">
				   <c:if test="${status.index==0}">
					  <li class="box-nav-li box-li-act" id="J-${columnInfo.columnId}" style="z-index:99;">${columnInfo.columnName}</li>
				   </c:if>
				   <c:if test="${status.index!=0}">
					  <li class="box-nav-li" id="J-${columnInfo.columnId}" style="z-index:99;">${columnInfo.columnName}</li>
				   </c:if>
				   <script type="text/javascript">
				   <!--
					  document.getElementById("J-${columnInfo.columnId}").setAttribute("moduleId","${moduleId}");
				   //-->
				   </script>
				</c:forEach>
		 </ul>
		</div>
		<c:forEach items="${columnInfoList}" var="columnInfo" varStatus="status">
		  <!-- ${columnInfo.columnName} -->
		  <c:if test="${status.index==0}">
			  <div class="normalbox mt15 J-tabbox" id="J-${columnInfo.columnId}_box" style="width:100%;height:85%;margin-top:-10px;"></div>
		  </c:if>
		   <c:if test="${status.index!=0}">
			  <div class="normalbox mt15 J-tabbox hidden" id="J-${columnInfo.columnId}_box" style="width:100%;height:85%;margin-top:-10px;"></div>
		   </c:if>
			<!--生成图-->
			<script language='javascript'>
			  document.getElementById("J-${columnInfo.columnId}_box").setAttribute("moduleId","${moduleId}");
			  var ajaxLoad=false;//为ajax加载
			  var width=document.body.clientWidth;
			  var parentFrmHeight=300;
			  var parentFrm=window.parent.document.getElementById("iframepage_${moduleId}");
			  if(parentFrm==null) 
			  {
				  //为ajax加载
				  parentFrm=document.getElementById("content_${moduleId}");
				  parentFrmHeight=(parentFrm==null?300:parentFrm.style.height);
				  ajaxLoad=true;
			  }else
			  {
				  //为iframe加载
				  parentFrmHeight=(parentFrm==null?300:parentFrm.height);
				  ajaxLoad=false;
			  }
			  if((parentFrmHeight+"").indexOf("px")!=-1)
			  {
				  parentFrmHeight=parentFrmHeight.substring(0,parentFrmHeight.indexOf("px"));
			  }
			  var height=parentFrmHeight-45+15;
			<c:choose>
			   <c:when test="${columnInfo.chartType eq CHART_TYPE_STOCK}">
				//趋势图
				var stock_${fn:replace(columnInfo.columnId, "-", "_")}=new SWFObject("<%=path%>/common/amchart/stock/amstock/amstock.swf?id=<%=new Date().getTime()%>", "stock${fn:replace(columnInfo.columnId, "-", "_")}","100%", height, "8", "#EFF0F2");
					stock_${fn:replace(columnInfo.columnId, "-", "_")}.addParam("wmode", "transparent");
					stock_${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("path", "<%=path%>/common/amchart/stock/amstock/");
					stock_${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("chart_id", "stock${fn:replace(columnInfo.columnId, "-", "_")}");
					//是否归一
					var needPercent=${fn:length(columnInfo.kpiInfoList)}>=2?1:0;
					var url="<%=basePath%>/stockChart/showStockChart?1=1&isPrd=1&kpiCodes=${columnInfo.kpiCodes};&needPercent="+needPercent+"&kpiType=${kpiType}&reportDate="+crtDate+"&rid="+Math.random();
					stock_${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("settings_file", escape(url));
					stock_${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("key", amchart_key);
					/*
					//指标Codes
					stock_${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("kpiCodes", "${columnInfo.kpiCodes}");
					//日期类型
					stock_${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("kpiType", "${kpiType}");
					
					//栏目名称
					stock_${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("columnName", "${columnInfo.columnName}");
					*/
					stock_${fn:replace(columnInfo.columnId, "-", "_")}.write("J-${columnInfo.columnId}_box");
			   </c:when>
			   <c:when test="${columnInfo.chartType eq CHART_TYPE_AREA}">
				//面积图
				var stock_${fn:replace(columnInfo.columnId, "-", "_")}=new SWFObject("<%=path%>/common/amchart/stock/amstock/amstock.swf?id=<%=new Date().getTime()%>", "stock${fn:replace(columnInfo.columnId, "-", "_")}","100%", height, "8", "#EFF0F2");
					stock_${fn:replace(columnInfo.columnId, "-", "_")}.addParam("wmode", "transparent");
					stock_${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("path", "<%=path%>/common/amchart/stock/amstock/");
					stock_${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("chart_id", "stock${fn:replace(columnInfo.columnId, "-", "_")}");
					var url="<%=basePath%>/stockChart/showStockAreaChart?1=1&isPrd=1&kpiCodes=${columnInfo.kpiCodes};&needPercent=0&kpiType=${kpiType}&reportDate="+crtDate+"&rid="+Math.random();
					stock_${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("settings_file", escape(url));
					stock_${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("key", amchart_key);
					/*
					//指标Codes
					stock_${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("kpiCodes", "${columnInfo.kpiCodes}");
					//日期类型
					stock_${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("kpiType", "${kpiType}");
					
					//栏目名称
					stock_${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("columnName", "${columnInfo.columnName}");
					*/
					stock_${fn:replace(columnInfo.columnId, "-", "_")}.write("J-${columnInfo.columnId}_box");
			   </c:when>		   
			   <c:when test="${columnInfo.chartType eq CHART_TYPE_PIE}">
				//饼图
				//height=width;//默认设置正文形
				var pie_${fn:replace(columnInfo.columnId, "-", "_")}= new SWFObject("<%=path%>/common/amchart/pie/ampie/ampie1.swf", "ampie${fn:replace(columnInfo.columnId, "-", "_")}", height, height, "8.0.0", "#EFF0F2");
					pie_${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("path", "<%=path%>/common/amchart/pie/ampie/");
					pie_${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("settings_file", escape("<%=path%>/common/amchart/pie/ampie/commonpie-settings-legends.xml"));
					//数据类型
					var kpiType=${kpiType};
					var dateType="<%=DwpasStKpiData.DATE_TYPE_OF_DAY%>";
					if(kpiType==<%=DwpasCKpiInfo.KPI_TYPE_OF_MONTH%>) dateType="<%=DwpasStKpiData.DATE_TYPE_OF_MONTH%>";
					if(kpiType==<%=DwpasCKpiInfo.KPI_TYPE_OF_WEEK%>) dateType="<%=DwpasStKpiData.DATE_TYPE_OF_WEEK%>";
					//查询数据
					pie_${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("data_file",escape("<%=basePath%>/PieChart/showPieChart?kpiCode=${columnInfo.kpiCodes};&reportDate="+crtDate+"&dateType="+dateType+"&rid="+Math.random()));
					pie_${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("key", amchart_key);
					/*
					//栏目名称
					pie_${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("columnName", "${columnInfo.columnName}");
					*/
					pie_${fn:replace(columnInfo.columnId, "-", "_")}.addParam("wmode", "transparent");
					pie_${fn:replace(columnInfo.columnId, "-", "_")}.write("J-${columnInfo.columnId}_box");
				</c:when>
				<c:when test="${columnInfo.chartType eq CHART_TYPE_LINE}">
				//折线
				var line${fn:replace(columnInfo.columnId, "-", "_")} = new SWFObject("<%=path%>/common/amchart/amline/amline.swf", "line${fn:replace(columnInfo.columnId, "-", "_")}", "100%", height, "8", "#EFF0F2");
					//healthtre.addParam("wmode", "opaque");
					line${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("path", "<%=path%>/common/js");
					line${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("chart_id", "line${fn:replace(columnInfo.columnId, "-", "_")}");
					var url="<%=basePath%>/columnOrLineChart/showLineChartSetting.html?1=1&kpiCodes=${columnInfo.kpiCodes};&needPercent=0&kpiType=${kpiType}&reportDate="+crtDate+"&chartTypes=${columnInfo.chartTypeDesc}&rid="+Math.random();
					line${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("settings_file", escape(url));//设置文件
					var dataUrl="<%=basePath%>/columnOrLineChart/showChartData.html?1=1&kpiCodes=${columnInfo.kpiCodes};&needPercent=0&kpiType=${kpiType}&reportDate="+crtDate+"&chartTypes=${columnInfo.chartTypeDesc}&rid="+Math.random();
					line${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("data_file", escape(dataUrl));//数据文件
					line${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("key", amchart_key);
					/*
					//栏目名称
					line${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("columnName", "${columnInfo.columnName}");
					*/
					line${fn:replace(columnInfo.columnId, "-", "_")}.addParam("wmode","transparent");
					//line${fn:replace(columnInfo.columnId, "-", "_")}.addParam("bgcolor","#000000");
					line${fn:replace(columnInfo.columnId, "-", "_")}.write("J-${columnInfo.columnId}_box");			
				</c:when>
				<c:when test="${columnInfo.chartType eq CHART_TYPE_BAR}">
				//条形图
				var bar${fn:replace(columnInfo.columnId, "-", "_")} = new SWFObject("<%=path%>/common/amchart/column/amcolumn/amcolumn.swf", "bar${fn:replace(columnInfo.columnId, "-", "_")}", "100%", height, "8", "#EFF0F2");
					//healthtre.addParam("wmode", "opaque");
					bar${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("path", "<%=path%>/common/js");
					bar${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("chart_id", "bar${fn:replace(columnInfo.columnId, "-", "_")}");
					var url="<%=basePath%>/columnOrLineChart/showChartSetting.html?1=1&kpiCodes=${columnInfo.kpiCodes};&needPercent=0&kpiType=${kpiType}&reportDate="+crtDate+"&chartTypes=${columnInfo.chartTypeDesc}&rid="+Math.random();
					bar${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("settings_file", escape(url));//设置文件
					var dataUrl="<%=basePath%>/columnOrLineChart/showChartData.html?1=1&kpiCodes=${columnInfo.kpiCodes};&needPercent=0&kpiType=${kpiType}&reportDate="+crtDate+"&chartTypes=${columnInfo.chartTypeDesc}&rid="+Math.random();
					bar${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("data_file", escape(dataUrl));//数据文件
					bar${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("key", amchart_key);
					/*
					//栏目名称
					bar${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("columnName", "${columnInfo.columnName}");
					*/
					bar${fn:replace(columnInfo.columnId, "-", "_")}.addParam("wmode","transparent");
					//line${fn:replace(columnInfo.columnId, "-", "_")}.addParam("bgcolor","#000000");
					bar${fn:replace(columnInfo.columnId, "-", "_")}.write("J-${columnInfo.columnId}_box");			
				</c:when>			
				<c:when test="${columnInfo.chartType eq CHART_TYPE_COLUMN ||columnInfo.chartType eq CHART_TYPE_COLUMN_AND_LINE}">			
				//折线或矩形或折线+矩形
				var column${fn:replace(columnInfo.columnId, "-", "_")} = new SWFObject("<%=path%>/common/amchart/column/amcolumn/amcolumn.swf", "column${fn:replace(columnInfo.columnId, "-", "_")}", "100%", height, "8", "#EFF0F2");
					//healthtre.addParam("wmode", "opaque");
					column${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("path", "<%=path%>/common/js");
					column${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("chart_id", "column${fn:replace(columnInfo.columnId, "-", "_")}");
					var url="<%=basePath%>/columnOrLineChart/showChartSetting.html?1=1&kpiCodes=${columnInfo.kpiCodes};&needPercent=0&kpiType=${kpiType}&reportDate="+crtDate+"&chartTypes=${columnInfo.chartTypeDesc}&rid="+Math.random();
					column${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("settings_file", escape(url));//设置文件
					var dataUrl="<%=basePath%>/columnOrLineChart/showChartData.html?1=1&kpiCodes=${columnInfo.kpiCodes};&needPercent=0&kpiType=${kpiType}&reportDate="+crtDate+"&chartTypes=${columnInfo.chartTypeDesc}&rid="+Math.random();
					column${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("data_file", escape(dataUrl));//数据文件
					column${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("key", amchart_key);
					/*
					//栏目名称
					column${fn:replace(columnInfo.columnId, "-", "_")}.addVariable("columnName", "${columnInfo.columnName}");
					*/
					column${fn:replace(columnInfo.columnId, "-", "_")}.addParam("wmode", "transparent");
					column${fn:replace(columnInfo.columnId, "-", "_")}.write("J-${columnInfo.columnId}_box");
				//折线或矩形
				</c:when>
				<c:otherwise>
				   //未知图类型
					document.getElementById("J-${columnInfo.columnId}_box").innerHTML="<font color='red'>加载失败：未知或未定义图类型：${columnInfo.chartType}</font>";
				</c:otherwise>
				</c:choose>
				//TABN切换
				E.domReady(function () {	
					//alert(1);
					A($$(".box-nav-li")).each(function(el){
						el.click(function(){
							var did = el.attr("id");
							A($$(".box-nav-li")).each(function(es){
								if(es.attr("moduleId")==el.attr("moduleId")) es.removeClass("box-li-act");
							});
							el.addClass("box-li-act");
							A($$(".J-tabbox")).each(function(es){
								if(es.attr("moduleId")==el.attr("moduleId")) es.addClass("hidden");
							});
							$(did+"_box").removeClass("hidden");
						});
					});	
			   });
			</script>
		</c:forEach>
	</c:if>
  </div>
</c:if>
</body>
</html>