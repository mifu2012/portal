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
		<title>�°�demo</title>
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
</script>
  <body style="text-align:center;">
     <br/><br/><br/><br/><br/><br/>
     <div> 
     <br/>
		  <a href="<%=baserootPath%>/prodRank">������ʹ��ʾ��</a> 
	  ����<a href="<%=baserootPath%>/productHealth/showProductHealth?productId=2001"><font color='red'>����ͼ����ά��ͼʾ��</font></a>
		  <br/>
		  <br/>
		  <a href="<%=baserootPath%>/kpiInfoMan/searchPage">���ݷ�ҳ����ҳ��ǩʹ��ʾ��</a>
		  <br/>
		  <br/>
		  <a href="<%=baserootPath%>/testIndex/showIndex?productId=2001">������Ŀ�Ͳ�ƷIDչ�ָ���ͼ(��)</a>
		  <br/>
		  <br/>
		  <a href="<%=baserootPath%>/UserConsulting/UserConsultingShow?queryDate=2011-02-01">�û���ѯ���</a>
		  <br/>
		  <br/>
		  <a href="<%=baserootPath%>/ProductDev/doPost?queryDate=2011-02-01">��Ʒ��չ</a>
		  <br/>
		  <br/>
		  <a href="<%=baserootPath%>/prodAnalyze/kpiAnalyze">ָ�����</a>
		  <br/>
		  <a href="<%=baserootPath%>/userVoice/showUserVoice">�û�����</a>
		  <br/>
		  <br/>
		  <a href="<%=baserootPath%>/userExperience/showUserExperience">�û�����</a> 
		  
	</div>
	<!--���²�Ʒ�û�����-->
	���²�Ʒ�û�����֮�������û�(����Ϊ2011��3��)
	<div id="lastMonthNewUserPieDiv"></div>
  </body>
</html>
<script type="text/javascript">
<!--
    var amchart_key = "${amchartKey};"
	window.onload=new function()
	{
		var pieChartSwf = new SWFObject("<%=baserootPath%>/common/amchart/pie/ampie/ampie1.swf", "ampie1", "450", "325", "8", "#EFF0F2");
		pieChartSwf.addVariable("rootPath", "<%=baserootPath%>/common/amchart/pie/ampie/");
		pieChartSwf.addVariable("settings_file", escape("<%=baserootPath%>/common/amchart/pie/ampie/commonpie-settings-legends.xml"));
		pieChartSwf.addVariable("data_file",escape("<%=baserootPath%>/PieChart/showPieChart?kpiCode=CUS101000AS01M&reportDate=201103&dateType=<%=DwpasStKpiData.DATE_TYPE_OF_MONTH%>"));
		pieChartSwf.addParam("wmode", "transparent");
		pieChartSwf.addVariable("key", amchart_key);
		pieChartSwf.write("lastMonthNewUserPieDiv");
	}
//-->
</script>