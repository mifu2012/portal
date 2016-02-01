<%@ page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="gbk"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="com.infosmart.portal.pojo.DwpasCKpiInfo"%>
<%@ page import="com.infosmart.portal.pojo.DwpasStKpiData"%>
<%@ page import="com.infosmart.portal.pojo.DwpasCUserFeature"%>
<%
String rootPath = request.getContextPath();
String baserootPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+rootPath+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>${systemName}</title>
		<script type="text/javascript" src="<%=baserootPath%>/common/Common.jsp"></script>
		<link href="<%=rootPath%>/common/css/css.css" rel="stylesheet" type="text/css" />
		<link href="<%=rootPath%>/common/css/style.css" rel="stylesheet"
			type="text/css" />

		<!--����Ǿ���,��Ҫ����������ļ�-->
       <script src="<%=rootPath%>/common/amchart/column/amcolumn/swfobject.js" type="text/javascript"></script>
       <script src="<%=rootPath%>/common/amchart/column/amcolumn/swfobject1.js" type="text/javascript"></script>  
       <!--����Ǿ���,��Ҫ����������ļ�-->

		<script src="<%=rootPath%>/common/amchart/stock/amstock/swfobject.js"
			type="text/javascript"></script>
		<script type="text/javascript" src="<%=rootPath%>/common/js/amcharts.js"></script>
		<script type="text/javascript" src="<%=rootPath%>/common/js/amfallback.js"></script>
		<script type="text/javascript" src="<%=rootPath%>/common/js/raphael.js"></script>
		<script type="text/javascript" src="<%=rootPath%>/common/js/arale.js"></script>
		<script type="text/javascript" src="<%=rootPath%>/common/js/ddycom.js"></script>
		<script type="text/javascript"
			src="<%=rootPath%>/common/js/jquery-1.4.3.min.js"></script>
  </head>
  		<script type="text/javascript">
//amchartKey ��web.xml����
var amchart_key = "${amchartKey};"//"110-037eaff57f02320aee1b8576e4f4062a";
window.changeHealthDate = null;
     //init
	$(document).ready(function(){
		//��Ʒ��չ����ͼ��ʾ
		//��Ŀ����  ��Ŀ����:${productSaleAmountColumn.columnCode}
	    document.getElementById("productSaleAmountColumnDiv").innerText="${productSaleAmountColumn.columnName}";
		//��Ŀ������ָ��
		var healthtre = new SWFObject("<%=rootPath%>/common/amchart/stock/amstock/amstock.swf", "amline3", "695", "295", "9", "#EFF0F2");
		healthtre.addVariable("rootPath", "<%=rootPath%>/common/amchart/stock/amstock/");
		healthtre.addVariable("chart_id", "amline3");
		healthtre.addParam("wmode", "transparent");
		//ҵ������ҵ�����
		var url="<%=baserootPath%>/stockChart/showStockChart?1=1&kpiCodes=${productSaleAmountColumn.kpiCodes};${productSaleTimesColumn.kpiCodes}&lineColors=8aac57;dc3c28&needPercent=1&kpiType=<%=DwpasCKpiInfo.KPI_TYPE_OF_DAY%>&reportDate=2011-02-01&rid="+Math.random();
		healthtre.addVariable("settings_file", escape(url));//��ͳ��
		healthtre.addVariable("key", amchart_key);
		healthtre.write("ProductDevDiv");

		//2011���û���Ʒ�������${userDistributionColumn.columnCode}
		//��ʾʵ�ʵ���Ŀ����
		document.getElementById("userDistributionColumnDiv").innerText="${userDistributionColumn.columnName}";
		var pieChartSwf = new SWFObject("<%=baserootPath%>/common/amchart/pie/ampie/ampie1.swf", "ampie1", "450", "325", "8", "#EFF0F2");
		pieChartSwf.addVariable("rootPath", "<%=baserootPath%>/common/amchart/pie/ampie/");
		pieChartSwf.addVariable("settings_file", escape("<%=baserootPath%>/common/amchart/pie/ampie/commonpie-settings-legends.xml"));
		pieChartSwf.addVariable("data_file",escape("<%=baserootPath%>/PieChart/showPieChart?kpiCode=${userDistributionColumn.kpiCodes}&color=B85D5A;8BAA5C;89759A;4E7DA7;52A4BA;&reportDate=201106&dateType=<%=DwpasStKpiData.DATE_TYPE_OF_MONTH%>"));
		pieChartSwf.addParam("wmode", "transparent");
		pieChartSwf.addVariable("key", amchart_key);
		pieChartSwf.write("UserUsedProductDiv");

		//ʹ�ô����ֲ�
        var params ={wmode:"transparent"};
		var flashVars ={
			rootPath: "<%=baserootPath%>/common/amchart/column/amcolumn/",
			settings_file: escape("<%=baserootPath%>/userFeature/showUserFeatureChart?productId=2001&types=column&reportDate=201106&featureType=<%=DwpasCUserFeature.FEATURE_TYPE_OF_USE_FREQUENCY%>&colors=8AAA58"),
			key:amchart_key
    	};
		swfobject.embedSWF("<%=baserootPath%>/common/amchart/column/amcolumn/amcolumn.swf", "UserUsedFrequencyDiv", "745", "220", "8.0.0", "<%=baserootPath%>/common/amchart/column/amcolumn/expressInstall.swf", flashVars, params);

	});
</script>
  <body style="text-align:center;">
     <br/><br/><br/><br/><br/><br/>

	<hr size='1'>
	<div id="productSaleAmountColumnDiv"><b>��Ʒ��չ����</b></div><br/>
	<!--��Ʒ��չ(����ͼ)-->
	<div id="ProductDevDiv"></div>

	<hr size='1'>
	<div id="userDistributionColumnDiv"><b>2011���û���Ʒ�������</b></div><br/>
	<!--2011���û���Ʒ�������(��ͼ)-->
	<div id="UserUsedProductDiv"></div>

	<b>ʹ�ô����ֲ�ͼ</b><br/>
	<!--ʹ�ô����ֲ�ͼ(����ͼ)-->
	<div id="UserUsedFrequencyDiv"></div>

  </body>
</html>
