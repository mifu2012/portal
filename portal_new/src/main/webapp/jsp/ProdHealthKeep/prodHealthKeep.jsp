<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.infosmart.portal.pojo.DwpasCKpiInfo"%>
<%@page import="com.infosmart.portal.pojo.DwpasStKpiData"%>
<%@ page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Arrays"%>
<%@page import="com.infosmart.portal.util.Const"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
	+ request.getServerName() + ":" + request.getServerPort()
	+ path + "/";
	String baserootPath = request.getScheme() + "://"
	+ request.getServerName() + ":" + request.getServerPort()
	+ path + "/";
	String colors = this.getInitParameter("chartColor");
	//默认需要5种
	if (colors == null || colors.length() == 0 || colors.length() < 5) {
		colors = "52A4BA;8BAA5C;89759A;4E7DA7;B85D5A;8B1A1A;FFD700;00B2EE;8AAC57;EE7600;RED;BLUE;GREE;CDB79E;5C5C5C";
	}
	List<String> colorList = Arrays.asList(colors.split(";"));
	String indexMenuId =(String) session.getAttribute(Const.INDEX_MENU_ID);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>${systemName}健康度-用户留存</title>
<link href="<%=path%>/common/css/cssz.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/common/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=path%>/common/js/My97DatePicker/My98MonthPicker.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/arale.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/swfobject.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/amcharts.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/amfallback.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/raphael.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/jquery-1.4.3.min.js"></script>
<script type="text/javascript">

$(function(){
    $(".tab>h6>span").click(
        function(){
            $(this).addClass("current").siblings().removeClass("current");
            //$(".tab>div").eq($(".tab>h6>span").index(this)).show().siblings("div").hide();
        }
    )
})
</script>
<script type="text/javascript">
    var amchart_key = "${amchartKey}";
 	$(document).ready(function(){
		
 		//趋势图
 		var healthtre = new SWFObject("<%=path%>/common/amchart/stock/amstock/amstock.swf?id=<%=new Date().getTime()%>", "amline3", "998", "300", "8", "#EFF0F2");
		    healthtre.addParam("wmode", "opaque");
			healthtre.addVariable("path", "<%=path%>/common/amchart/stock/amstock/");
			healthtre.addVariable("chart_id", "amline3");
			//业务量及业务笔数
			//加了isThreeMonth判断是否要在趋势图上有3月按钮
			var url="<%=baserootPath%>/stockChart/showStockChart?1=1&isPrd=1&kpiCodes=${stockChartCodes}&needPercent=0&isThreeMonth=0&kpiType=<%=DwpasCKpiInfo.KPI_TYPE_OF_MONTH%>&reportDate=${date}&rid="+Math.random();
			healthtre.addVariable("settings_file", escape(url));//月统计
			healthtre.addVariable("key", amchart_key);
			//指标Codes
			healthtre.addVariable("kpiCodes", "${stockChartCodes}");
			//日期类型
			healthtre.addVariable("kpiType", <%=DwpasCKpiInfo.KPI_TYPE_OF_MONTH%>);
			healthtre.write("ProductDevDiv");
 			
 		//全年饼图--全年用户产品构造情况
		var pieChartSwf = new SWFObject("<%=path%>/common/amchart/pie/ampie/ampie1.swf", "ampie1", "470", "405", "8", "#EFF0F2");
			pieChartSwf.addVariable("path", "<%=path%>/common/amchart/pie/ampie/");
			pieChartSwf.addVariable("settings_file", escape("<%=path%>/common/amchart/pie/ampie/commonpie-settings-legends.xml"));
			pieChartSwf.addVariable("data_file",escape("<%=path%>/PieChart/showPieChart?kpiCode=${codes};&reportDate=${date}&dateType=<%=DwpasStKpiData.DATE_TYPE_OF_MONTH%>"));
			pieChartSwf.addParam("wmode", "transparent");
			pieChartSwf.addVariable("key", amchart_key);
			pieChartSwf.write("UserUsedProductDiv");
		//流失率	
 		var vo1 = new SWFObject("<%=path%>/common/amchart/pie/ampie/ampie1.swf", "ampie1", "260", "150", "8.0.0", "#EFF0F2");
	 		vo1.addVariable("path", "<%=path%>/common/amchart/pie/ampie/");
	 		vo1.addVariable("settings_file", escape("<%=path%>/common/amchart/pie/ampie/commonpie-settings-rate.xml"));
	 		vo1.addVariable("data_file",escape("<%=path%>/PieChart/showPieChart?kpiCode=${loseUserRateCode}&color=DD85DD;CDB79E&reportDate=${date}&dateType=<%=DwpasStKpiData.DATE_TYPE_OF_MONTH%>"));
	 		vo1.addParam("wmode", "transparent");
	 		vo1.addVariable("key", amchart_key);
	 		vo1.write("chart1");
	 	//老用户占比	
	 	var vo2 = new SWFObject("<%=path%>/common/amchart/pie/ampie/ampie1.swf", "ampie1", "260", "150", "8.0.0", "#EFF0F2");
	 		vo2.addVariable("path", "<%=path%>/common/amchart/pie/ampie/");
	 		vo2.addVariable("settings_file", escape("<%=path%>/common/amchart/pie/ampie/commonpie-settings-rate.xml"));
	 		vo2.addVariable("data_file",escape("<%=path%>/PieChart/showPieChart?kpiCode=${oldUserCode};${newUserCode}&color=5B00AE;CDB79E&reportDate=${date}&dateType=<%=DwpasStKpiData.DATE_TYPE_OF_MONTH%>"));
	 		vo2.addParam("wmode", "transparent");
	 		vo2.addVariable("key", amchart_key);
	 		vo2.write("chart2");
	 	//新用户构成
	 	var vo3 = new SWFObject("<%=path%>/common/amchart/pie/ampie/ampie1.swf", "ampie1", "260", "150", "8.0.0", "#EFF0F2");
	 		vo3.addVariable("path", "<%=path%>/common/amchart/pie/ampie/");
	 		vo3.addVariable("settings_file", escape("<%=path%>/common/amchart/pie/ampie/commonpie-settings-rate.xml"));
	 		vo3.addVariable("data_file",escape("<%=path%>/PieChart/showPieChart?kpiCode=${zhuceUserCode};${zhuanhuaUserCode}&color=8F4586;CDB79E&reportDate=${date}&dateType=<%=DwpasStKpiData.DATE_TYPE_OF_MONTH%>"));
	 		vo3.addParam("wmode", "transparent");
	 		vo3.addVariable("key", amchart_key);
	 		vo3.write("chart3");
	 	//老用户构成	
	 	var vo4 = new SWFObject("<%=path%>/common/amchart/pie/ampie/ampie1.swf", "ampie1", "260", "150", "8.0.0", "#EFF0F2");
	 		vo4.addVariable("path", "<%=path%>/common/amchart/pie/ampie/");
	 		vo4.addVariable("settings_file", escape("<%=path%>/common/amchart/pie/ampie/commonpie-settings-rate.xml"));
	 		vo4.addVariable("data_file",escape("<%=path%>/PieChart/showPieChart?kpiCode=${huoyueUserCode};${fuhuoUserCode};${sleepUserCode}&reportDate=${date}&dateType=<%=DwpasStKpiData.DATE_TYPE_OF_MONTH%>"));
	 		vo4.addParam("wmode", "transparent");
	 		vo4.addVariable("key", amchart_key);
	 		vo4.write("chart4");
	 	  
	 }); 
	//产品选择 js end
	$(document).ready(function changeIframeSize(){
		parent.changeIframeSize(document.body.scrollHeight+5);
			});
	 //变更日期，重新查询数据	
	 function restartOpen() {
		 //显示进度条
	    if(window.parent!=null&&window.parent.document.getElementById("loading_wait_div")!=null)
	    {
	    	window.parent.document.getElementById("loading_wait_div").style.display="";
	    }
		var queryDate = document.getElementById('maxDate').value;
		var productId = document.getElementById('productId').value;
		if (queryDate != "") {
			location.href = '<%=path%>/UserKeep/showUserKeep?menuId=${menuId}&productId='+productId+'&queryDate='+queryDate+'&kpiType=<%=DwpasStKpiData.DATE_TYPE_OF_MONTH%>';
		}
	}
	 function changeProduct(newProdId){
			if(newProdId==null)
			{
				  return;
			}
			document.location.href="<%=path%>/UserKeep/showUserKeep?productId="+newProdId+"&menuId=${menuId}&queryDate=${date}&kpiType="+<%=DwpasCKpiInfo.KPI_TYPE_OF_MONTH%>;
		}
 </script>
<script type="text/javascript">
 	function check(){
	 var amchart_key = "${amchartKey}";
	 var kpiCode = "";
	 <c:forEach items="${stockCColumnInfo.kpiInfoList}" var="stock">
	 		if(document.getElementById("${stock.kpiCode}").checked==true){
	 			kpiCode = kpiCode + "${stock.kpiCode};";
	 		}
	 </c:forEach>
	 
	  	
	  	var healthtre1 = new SWFObject("<%=path%>/common/amchart/stock/amstock/amstock.swf", "amline3", "998", "300", "8", "#EFF0F2");
	  	healthtre1.addVariable("path", "<%=path%>/common/amchart/stock/amstock/");
	  	healthtre1.addVariable("chart_id", "amline3");
	  	healthtre1.addParam("wmode", "transparent");
		//业务量及业务笔数
		var url="<%=baserootPath%>/stockChart/showStockChart?1=1&isPrd=1&kpiCodes="+kpiCode+"&needPercent=0&kpiType=<%=DwpasCKpiInfo.KPI_TYPE_OF_MONTH%>&reportDate=${date}&rid="
				+ Math.random();
	    healthtre1.addVariable("settings_file", escape(url));//月统计
	    healthtre1.addVariable("key", amchart_key);
	    healthtre1.write("ProductDevDiv");
	}
 	//在趋势图滑动时，日期变化
	function amRolledOver(chart_id, date, period, data_object){
		var dateDesc="";
		if(date.length==6)
		{
			dateDesc=date.substring(0,4)+"年"+date.substring(4,6)+"月";
		}else if(date.length==8)
		{
            dateDesc=date.substring(0,4)+"年"+date.substring(4,6)+"月"+date.substring(6,8)+"日";
		}
		var chartDateDiv=document.getElementById("ProductDevDiv-date");
		if(chartDateDiv!=null)
		{
			chartDateDiv.innerHTML=dateDesc;
 	    }
	}
 	
  //隐藏细分用户复活或留存趋势线条
  function hideOrShowGraph(chkObj)
  {
    if(chkObj.checked==true)
	  {
         document.getElementById("amline3").showGraph("0", chkObj.value);
	  }else
	  {
         document.getElementById("amline3").hideGraph("0", chkObj.value); 
	  }
  }

	 function changeTheDate(n){
		  var reg=new RegExp("-","g");
		  var dt=document.getElementById('maxDate').value.replace(reg,"/")+"/01";
		  var today=new Date(new Date(dt).valueOf() );
		  today.setMonth(today.getMonth()+n);
		  var monthDate=today.getMonth()+1;
		  if((today.getMonth() + 1)<10){ 
	    	  monthDate="0"+(today.getMonth() + 1);
		  }
		  var changedDate=today.getFullYear() + "-" + monthDate;
		  document.getElementById('maxDate').value=changedDate; 
		  if(changedDate!=''){
			  location.href = '<%=path%>/UserKeep/showUserKeep?menuId=${menuId}&queryDate='+changedDate+'&kpiType=<%=DwpasStKpiData.DATE_TYPE_OF_MONTH%>';
		}
	}
	//帮助按钮
	function helpShow1() {
		document.getElementById('help1').style.display = "";
	}
	function helpHidden1() {
		document.getElementById('help1').style.display = "none";
	}
	function helpShow2() {
		document.getElementById('help2').style.display = "";
	}
	function helpHidden2() {
		document.getElementById('help2').style.display = "none";
	}
	function helpShow3() {
		document.getElementById('help3').style.display = "";
	}
	function helpHidden3() {
		document.getElementById('help3').style.display = "none";
	}
</script>
<style>
.index_ycys {
	display: none
}

.index_xsys {
	display: block
}
</style>
</head>
<body class="indexbody2">
	<div class="new_jwy_yy" style="width: 1010px; background: #fff">
		<!-- 选择产品 -->
		<jsp:include page="../common/ChoiceProduct.jsp" />
		<!-- 产品选择 end-->
		<!-- 当前位置 -->
		<div class="kpi_position" id="navigationDiv" style="z-index:9999999">
			<div style="padding-top: 2px">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="78%">
						<div style="padding:3px 0px 0px 11px">
                        <div style="float:left">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;当前位置：
							<a href="<%=path%>/index/gotoHomePage.html?menuId=<%=indexMenuId%>">首页</a>&gt; <a
							href="<%=path%>/productHealth/showProductHealth.html?menuId=${parentMenu.menuId}">${parentMenu.menuName}</a>&gt; <a
							href="<%=path%>/UserKeep/showUserKeep.html?kpiType=3&menuId=${menuId}">${moduleInfoMap.USER_KEEP_YEAR_PROD_DISTRIBUTION_CHART.menuName}</a>&gt;
							<font color="red">${productInfo.productName}</font> </div>
	           <div style="float:left">&nbsp;&nbsp;
	           <a href="javascript:void(0);" onclick="choiceRrdInfo();"><img src="<%=path%>/common/images/jwy_button_01.gif" border="0" /></a></div>					
				</div>
					</td>
						<td width="22%">
							<table width="100%" border="0" cellspacing="0" cellpadding="0"
								style="padding-top: 5px">
								<tr>
									<td>数据时间：<a href="javascript:void(0);"
										onclick="changeTheDate(-1);"> <img
											style="vertical-align: middle;" align="middle"
											src="<%=path%>/common/images/ddy_2.gif" width="11"
											height="23" border="0" /> </a> <input id="maxDate"
										name="maxDate" class="ddy_index_input" type="text"
										value="${date}" onclick="setMonth(this,this);"
										onchange="restartOpen();"
										style="vertical-align: middle; cursor: pointer;" /> <a
										href="javascript:void(0);" onclick="changeTheDate(1);"> <img
											style="vertical-align: middle;" align="middle"
											src="<%=path%>/common/images/ddy_1.gif" width="11"
											height="23" border="0" /> </a></td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<!-- 当前位置结束 -->
		<div class="clear"></div>

		<div class="ddy_channel2"></div>
		<div class="index_home">

			<div style="padding: 3px 0px 0px 0px;">

				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="67%"></td>
						<td width="33%" align="right"></td>
					</tr>
				</table>
			</div>
		</div>
		<!--第一部分-->
		<table width="1000px" border="0" cellspacing="0" cellpadding="0"
			style="margin: auto; width: 1000px;">
			<tr>
				<td width="452" valign="top">
					<!--KPI图1 开始-->
					<div style="width: 452px; overflow: hidden; height: 432px;">
						<div class="index_title" style="width: 450px;">
							<input type="hidden" id="productId"
								value="${productInfo.productId}">
							<div class="index_title_pd">
								<table>
									<tr>
										<td><img src="<%=basePath%>/common/images/new_jwy_4.gif">
										</td>
										<td>&nbsp;${moduleInfoMap.USER_KEEP_YEAR_PROD_DISTRIBUTION_CHART.moduleName}</td>
									</tr>
								</table>

							</div>
							<span class="box-top-right"
								style="float: right; margin-right: 5px"> <em
								style="background: url(<%=basePath%>/common/images/askicon5.png); cursor: pointer"
								class="box-askicon" onmouseover="helpShow1();"
								onmouseout="helpHidden1();"></em> </span>
							<div id="help1" style="display: none; font-size: 12px;">
								<div style="position:relative;z-index:99999999; margin:0px 0px 0px -230px ">
										<div class="pop_w">
										  <div class="pop_z" style="z-index:99999; left:260px;">	<img src="<%=path%>/common/images/pop_tip.png" /></div>
       
<div class="pop_x">							<p>
											<b>${moduleInfoMap.USER_KEEP_YEAR_PROD_DISTRIBUTION_CHART.moduleName}：</b>
										</p>
										<p style="font-weight: normal;">
											${moduleInfoMap.USER_KEEP_YEAR_PROD_DISTRIBUTION_CHART.remark}</p>
</div>
</div></div>
							</div>
						</div>

						<div id="UserUsedProductDiv"
							style="width: 437px; height: 400px; padding-left: 0px;">
							<!--这里是amchart结束 -->
						</div>
					</div> <!--KIP图1 结束-->
				</td>
				<td valign="top">
					<!--KIP图2  开始-->
					<div style="margin-left: 10px;">
						<div style="width: 538px; overflow: hidden">
							<div class="index_title"
								style="width: 534px; border-right: 1px solid #ccc">
								<div class="index_title_pd" style="float: left; width: 400px">
									<table>
										<tr>
											<td><img src="<%=basePath%>/common/images/new_jwy_4.gif">
											</td>
											<td>${moduleInfoMap.USER_KEEP_LAST_MONTH_PROD_DISTRIBUTION_CHART.moduleName}</td>
										</tr>
									</table>

								</div>
								<span class="box-top-right"
									style="float: right; margin-right: 5px"> <em
									style="background: url(<%=basePath%>/common/images/askicon5.png); cursor: pointer"
									class="box-askicon" onmouseover="helpShow2();"
									onmouseout="helpHidden2();"></em> </span>

								<div id="help2" style="display: none; font-size: 12px;">
									<div style="position:relative;z-index:99999999;margin:0px 0px 0px -150px">
										<div class="pop_w">
										  <div class="pop_z" style="z-index:99999; left:260px;">	<img src="<%=path%>/common/images/pop_tip.png" /></div>
       
<div class="pop_x">					<p>
												<b>${moduleInfoMap.USER_KEEP_LAST_MONTH_PROD_DISTRIBUTION_CHART.moduleName}：</b>
											</p>
											<p style="font-weight: normal;">
												${moduleInfoMap.USER_KEEP_LAST_MONTH_PROD_DISTRIBUTION_CHART.remark}</p>
</div>
</div></div>
								</div>
							</div>

							<div class="clear"></div>
							<!--这里是amchart开始 -->
							<div class="box-conent box-leave box-leave-crossbg"
								style="width: 537px; height: 400px">
								<div class="leave-chartbox">
									<h3>流失用户占比</h3>
									<div>
										<div style="float: left; width: 260px; height: 150px;">
											<span id="chart1"></span>
										</div>
										<%-- <div style="color: #DD85DD; padding-top: 50px">
											留存率：<br>
											${beforeNewRate.showValue}${beforeNewRate.dwpasCKpiInfo.unit}
										</div> --%>
									</div>
								</div>
								<div class="leave-chartbox">
									<h3>老用户占比</h3>
									<div>
										<div style="float: left; width: 260px; height: 150px;">
											<span id="chart2"></span>
										</div>
									<%-- 	<div style="color: #5B00AE; padding-top: 50px">
											留存率：<br>
											${beforeOldRate.showValue}${beforeOldRate.dwpasCKpiInfo.unit}
										</div> --%>
									</div>
								</div>
								<div class="leave-chartbox">
									<h3>新用户构成</h3>
									<div>
										<div style="float: left; width: 260px; height: 150px;">
											<span id="chart3"></span>
										</div>
									<%-- 	<div style="color: #8F4586; padding-top: 50px">
											复活率：<br>
											${beforeSleepRate.showValue}${beforeSleepRate.dwpasCKpiInfo.unit}
										</div> --%>
									</div>
								</div>
								<div class="leave-chartbox">
									<h3>老用户构成</h3>
									<div>
										<div style="float: left; width: 260px; height: 150px;">
											<span id="chart4"></span>
										</div>
									<%-- 	<div style="color: #FF8000; padding-top: 50px;">
											复活率：<br>
											${beforeAwayRate.showValue}${beforeAwayRate.dwpasCKpiInfo.unit}
										</div> --%>
										<div></div>
									</div>
								</div>
								<!--KIP图2 结束-->
							</div>
						</div>
					</div>
				</td>
			</tr>
		</table>

	</div>
	<div class="clear"></div>

	<div
		style="width: 998px; margin: auto; overflow: hidden; margin-top: 10px;">
		<div class="index_title"
			style="width: 996px; border-right: 1px solid #ccc">
			<div class="index_title_pd">
				<table>
					<tr>
						<td><img src="<%=basePath%>/common/images/new_jwy_4.gif">
						</td>
						<td>&nbsp;${moduleInfoMap.USER_KEEP_USER_THREAD_CHART.moduleName}</td>
					</tr>
				</table>

			</div>
			<span class="box-top-right" style="float: right; margin-right: 5px">
				<em
				style="background: url(<%=basePath%>/common/images/askicon5.png); cursor: pointer"
				class="box-askicon" onmouseover="helpShow3();"
				onmouseout="helpHidden3();"></em> </span>
			<div id="help3" style="display: none; font-size: 12px;">
				<div style="position:relative;z-index:99999999;margin:0px 0px 0px 310px">
										<div class="pop_w">
										  <div class="pop_z" style="z-index:99999; left:260px;">	<img src="<%=path%>/common/images/pop_tip.png" /></div>
       
<div class="pop_x">							<p>
							<b>${moduleInfoMap.USER_KEEP_USER_THREAD_CHART.moduleName}：</b>
						</p>
						<p style="font-weight: normal;">
							${moduleInfoMap.USER_KEEP_USER_THREAD_CHART.remark}</p>
</div>
</div></div>
			</div>
		</div>

		<div class="index_report">
			<div class="clear"></div>
			<div align="center">
				<!--正文内容-->
				<div style="background-color: #e8f0f0; width: 998px;">
					<table width="100%" border="0" cellspacing="0" cellpadding="0"
						style="color: #666; padding-top: 10px; padding-bottom: 10px">
						<tr>
							<td width="10%"></td>
							<c:forEach items="${stockCColumnInfo.kpiInfoList}" var="stock">
								<c:choose>
									<c:when test="${fn:length(stockCColumnInfo.kpiInfoList)<=8  }">
										<td><input type="checkbox" name="checkbox"
											id="${stock.kpiCode }" value="${stock.kpiCode }"
											onclick="javascript:hideOrShowGraph(this);" checked="checked" />
										</td>
										<td><label for="${stock.kpiCode }"
											style="float: left; cursor: pointer;">${stock.dispName }</label>
										</td>
									</c:when>
									<c:otherwise>
										<td><input type="checkbox" name="checkbox"
											id="${stock.kpiCode }" value="${stock.kpiCode }"
											onclick="javascript:hideOrShowGraph(this);" checked="checked" />
											<label for="${stock.kpiCode }"
											style="cursor: pointer; float: left;">${stock.dispName }</label>
										</td>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							<td width="10%"></td>
						</tr>
					</table>
				</div>
				<!--thread chart  -->
				<div id="ProductDevDiv"></div>
				<div id="ProductDevDiv-date"
					style="position: absolute; width: 120px; left: 230px; top: 600px; display: none;">${date}</div>
				<!--内容结束-->
			</div>
		</div>
	</div>
</body>
</html>