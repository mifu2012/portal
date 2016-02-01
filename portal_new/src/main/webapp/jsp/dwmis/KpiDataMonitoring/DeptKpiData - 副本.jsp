<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
<title>${systemName}首页</title>
<style>
#tagr {
	background-color: #eff0f2
}
</style>

<link href="<%=path%>/common/css/cssz.css" rel="stylesheet"
	type="text/css" />
<link href="<%=path%>/common/css/base.css" rel="stylesheet"
	type="text/css" />
<%-- <link href="<%=path%>/common/css/style.css" rel="stylesheet"
	type="text/css" /> --%>
<!--
<link href="<%=path%>/common/dwmis/css/lwt.css" rel="stylesheet" type="text/css"  />
-->
<script src="<%=path%>/common/js/My97DatePicker/WdatePicker.js"
	type="text/javascript"></script>
<script src="<%=path%>/common/js/miaov.js" type="text/javascript"></script>
<script src="<%=path%>/common/js/amcharts.js" type="text/javascript"></script>
<script src="<%=path%>/common/js/raphael.js" type="text/javascript"></script>
<script src="<%=path%>/common/js/arale.js" type="text/javascript"></script>
<script src="<%=path%>/common/amchart/column/amcolumn/swfobject1.js"
	type="text/javascript"></script>
<script src="<%=path%>/common/amchart/pie/ampie/swfobject.js"
	type="text/javascript"></script>
<script src="<%=path%>/common/js/FusionCharts_Trial.js"
	type="text/javascript"></script>
<script src="https://static.alipay.com/ar/arale.core-1.1.js"
	type="text/javascript" charset="utf-8"></script>
<script src="<%=path%>/common/amchart/stock/amstock/swfobject.js"
	type="text/javascript"></script>
<script src="<%=path%>/common/js/amfallback.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>/common/js/jquery-1.4.3.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$('.son_ul').hide();
		$('.select_box span').hover(function() {
			$(this).parent().find('ul.son_ul').slideDown();
			$(this).parent().find('li').hover(function() {
				$(this).addClass('hb')
			}, function() {
				$(this).removeClass('hover')
			});
			$(this).parent().hover(function() {
			}, function() {
				$(this).parent().find("ul.son_ul").slideUp();
			});
		}, function() {
		});
		$('ul.son_ul li').click(function() {
			$(this).parents('li').find('span').html($(this).html());
			$(this).parents('li').find('ul').slideUp();
		});
	});

	//
	function switchTab(tabId) {
		var deptCount=10;//$//fmt:length(deptInfoList);
		for (i = 1; i<=10; i++) {
			if(document.getElementById("tab_" + i)==null) continue;
            document.getElementById("tab_" + i).getElementsByTagName("a")[0].className = "";
			document.getElementById("tabContentDiv_" + i).style.display = "none";
		}
		document.getElementById("tab_"+tabId).getElementsByTagName("a")[0].className = "on";
		document.getElementById("tabContentDiv_" + tabId).style.display = "block";
	}
</script>
<!--进度条样式-->
<style type="text/css">
.progress-bar{
	width:241px;
	height:21px;
	margin-left:10px;
	margin-right:20px;
	padding-right:29px;
	background:url(<%=path%>/common/dwmis/images/bg-progress.gif) no-repeat 0 -40px;
	text-align:left;
}
.progress-alert{
	background-position:0 0;
}
.progress-bar .percent{
	display:inline-block;
	height:21px;
	padding:0 4px;
	background:url(<%=path%>/common/dwmis/images/bg-progress.gif) no-repeat 0 -80px;
	color:#fff;
	text-align:right;
	font-weight:100;
	line-height:20px;
	white-space:nowrap;
}
.progress-alert .percent{
	background-position:0 -120px;
}
</style>
<!--业务量大字的样式-->
<style type="text/css">
.lwt_ywgz_g{
width:223px;
height:59px;
color:#908c88;
font-size:16px;
font-weight:bold;
font-family:"微软雅黑";
border-right:1px solid #d0d0d0
}
</style>
<!--走势图样式-->
<style type="text/css">
.layer {
	width: 1000px;
	margin: 0 auto;
	height: auto;
	position: relative;
}
.layer2 {
	position: absolute;
	top: 100px;
	height: 410px;
	border: 5px solid #73726e;
	width: 820px;
	background-color: #FFFFFF
}
.layer2 h2 {
	height: 30px;
	background: #d8e1e6;
	width: 790px;
	padding-left: 30px;
	line-height: 30px;
	color: #e85507;
	font-size: 16px
}
.layer2 span {
	position: absolute;
	right: 20px;
	font-size: 12px;
	font-weight: normal;
	color: #333333;
	top: 0px
}
</style>
</head>
<script type="text/javascript">
<!--
var flashMovie;
//amcharts图表生成时，自动调用函数
function amChartInited(chart_id){
	flashMovie = document.getElementById(chart_id);
	//显示进度条
	showLoading(false);	
}
//显示或隐藏进度条
function showLoading(isShow)
{
	var loadingDiv=window.parent.document.getElementById("loading_wait_Div");
	if(loadingDiv!=null)
	{
		if(isShow==false)
		{
           loadingDiv.style.display="none";
		   return;
		}
		loadingDiv.style.display="";
	}
	if(loadingDiv==null)
	{
		loadingDiv=document.createElement("div");
		loadingDiv.id="loading_wait_Div";
		loadingDiv.style.position="absolute";
		loadingDiv.style.left="45%";
		loadingDiv.style.top="40%";
		loadingDiv.innerHTML="<img src='<%=request.getContextPath()%>/common/images/loading.gif'>";
		document.body.appendChild(loadingDiv);
	}
}
//显示数据走势图
function showKpiThread(kpiCode,kpiUnit)
{
	var  e = e||event;
	document.getElementById("threadChartDiv").style.left=(screen.width-1000)/4;
	//L2.style.top=((document.body.offsetHeight-parseFloat (L2.style.height))/9)+document.body.scrollTop;
	//document.getElementById("threadChartDiv").style.top=document.body.scrollTop+50;
	document.getElementById("threadChartDiv").style.display="";
	//显示进度条
	showLoading(true);
	var swfLine = new SWFObject("<%=path%>/common/amchart/bundle/amline/amline.swf", "amline", "750", "380", "8", "#FFFFFF");
		swfLine.addParam("wmode", "opaque");
		swfLine.addVariable("path", "/amchart/bundle/amline/");
		swfLine.addVariable("chart_id", "amline");
		swfLine.addVariable("key", "${amchartKey}");
		swfLine.addVariable("settings_file", "<%=path%>/dwmisDeptKpiData/generateTrendChartSeting.htm?kpiUnit="+kpiUnit+"&date=" + (+new Date()));
		swfLine.addVariable("data_file", "<%=path%>/dwmisDeptKpiData/generateTrendChartData.htm?kpiCode="+kpiCode+"&pageId=kpiTrends&date=" + (+new Date()));
		swfLine.write("threadChartContentDiv");
}
//显示详情分析图
function showKpiDetailThread(kpiCode,kpiUnit,datePeriod)
{
	var  e = e||event;
	document.getElementById("threadDetailChartDiv").style.left=(screen.width-1000)/4;
	//L2.style.top=((document.body.offsetHeight-parseFloat (L2.style.height))/9)+document.body.scrollTop;
	//document.getElementById("threadChartDiv").style.top=document.body.scrollTop+50;
	//显示进度条
	showLoading(true);
	document.getElementById("threadDetailChartDiv").style.display="";
	var swfLine = new SWFObject("<%=path%>/common/amchart/stock/amstock/amstock.swf", "amline3", "820", "450", "8", "#FFFFFF");
		swfLine.addParam("wmode", "transparent");
		swfLine.addVariable("path", "/amchart/stock/amstock/");
		swfLine.addVariable("chart_id", "amline3");
		swfLine.addVariable("key", "${amchartKey}");
		//swfLine.addVariable("settings_file", "<%=path%>/dwmisDeptKpiData/generateTrendChartSeting.htm?kpiUnit="+kpiUnit+"&date=" + (+new Date()));
		swfLine.addVariable("settings_file", "<%=path%>/dwmisDeptKpiData/generateKpiDetailThreadChart.htm?kpiCode="+kpiCode+"&period=1002&haveEvent=true&linkedpagecharttype=original&needPercent=false&date=" + (+new Date()));
		swfLine.write("threadDetailChartContentDiv");
}

//-->
</script>
<body class="indexbody">
	<!-- 走势图 begin-->
	<div class="layer" id="threadChartDiv" style="z-index:999;display:none;top:0px;">
		<div class="layer2">
			<h2>
				指标走势图<span><a href="javascript:void(0);"
					onclick="Javascript:document.getElementById('threadChartDiv').style.display='none';" style='cursor:hand;'>关闭</a>
				</span>
			</h2>
			<div
				style='border: 1px solid; height: 380px; overflow: auto; overflow-x: hidden; width: 100%;' id="threadChartContentDiv">
				数据正在整理中，请稍候...
			</div>
		</div>
	</div>
  	<!-- 详情分析 begin-->
	<div class="layer" id="threadDetailChartDiv" style="z-index:999;display:none;top:0px;">
		<div class="layer2" style='height:520px;'>
			<h2>
				指标详情<span><a href="javascript:void(0);"
					onclick="Javascript:document.getElementById('threadDetailChartDiv').style.display='none';" style='cursor:hand;'>关闭</a>
				</span>
			</h2>
			<div
				style='border: 1px solid; height: 450px; overflow: auto; overflow-x: hidden; width: 100%;text-align:center;' id="threadDetailChartContentDiv">
				数据正在整理中，请稍候...
			</div>
		</div>
	</div>
	<!-- 详情分析 end-->
	<div class="index_home">
		<div>
			<img src="<%=path%>/common/dwmis/images/wph_8.gif" />
		</div>
		<div class="ddy_channel">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				style="padding-top: 3px">
				<tr>
					<td width="67%">当前位置：<a href="#">首页</a>&gt;<a href="#">业务跟踪</a>
					</td>
					<td width="33%">&nbsp;</td>
				</tr>
			</table>
		</div>
		<!--第一部分-->
		<div class="ddy_one_home"></div>
		<div class="index_title">
			<table width="100%" height="100%" border="0" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="87%"><table width="100%" border="0" cellspacing="0"
							cellpadding="0">
							<tr>
								<td></td>
							</tr>
						</table></td>
					<td width="13%"><script type=text/javascript>
						function show_hiddendiv() {
							document.getElementById("hidden_div").style.display = 'none';
							document.getElementById("_strHref").href = 'javascript:hidden_showdiv();';
							document.getElementById("_strSpan").innerHTML = "展开";
						}
						function hidden_showdiv() {
							document.getElementById("hidden_div").style.display = 'block';
							document.getElementById("_strHref").href = 'javascript:show_hiddendiv();';
							document.getElementById("_strSpan").innerHTML = "收起";
						}
					</script>
						<div class="lwtzk_bg2">
							<div
								style="padding-top: 4px; padding-left: 24px; font-size: 12px;">
								<a id="_strHref" href="javascript:show_hiddendiv();"><span
									id="_strSpan">收起</span></span> </a>
							</div>
						</div></td>
				</tr>
			</table>
		</div>
		<div class="index_title_yy"></div>
		<div class="index_report" style="display: block" id="hidden_div">
			<div class="clear"></div>
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				bgcolor="#f0f0f0">
				<tr>
					<td width="25%" height="80" align="right"><div
							class="lwt_ywgz_g">
							<div align="left">
								<div>当年累计业务量</div>
								<div style="margin-top: 10px">
									<span class="lwt_ywgz_sz">10,256,548,147</span>元
								</div>
							</div>
						</div></td>
					<td width="25%" align="right"><div class="lwt_ywgz_g">
							<div align="left">
								<div>当年累计业务笔数</div>
								<div style="margin-top: 10px">
									<span class="lwt_ywgz_sz">16,465,443,234</span>笔
								</div>
							</div>
						</div></td>
					<td width="25%" align="right"><div class="lwt_ywgz_g">
							<div align="left">
								<div>上月活跃账户数</div>
								<div style="margin-top: 10px">
									<span class="lwt_ywgz_sz">10,256,548</span>个
								</div>
							</div>
						</div></td>
					<td width="25%" align="right"><div class="lwt_ywgz_g"
							style="border-right: 0px">
							<div align="left">
								<div>动态活跃账户数</div>
								<div style="margin-top: 10px">
									<span class="lwt_ywgz_sz">567,332,678</span>个
								</div>
							</div>
						</div></td>
				</tr>
			</table>
		</div>
		<!--第一部分结束-->
		<div style="width: 100%; height: 20px; background-color: #f7f7f7"></div>
		<!--第二部分-->
		<!--tab切换开始-->
		<!--
		<ul id="main_box">
			<li class="select_box"><span>按事业部</span>
				<ul class="son_ul">
					<li><a href="ywgz.html">按事业部</a></li>
					<li><a href="ywgz_tt.html">按平衡计分卡</a></li>

				</ul>
			</li>
		</ul>
		-->
		<div id="tabContainer">
			<ul class="mouseover">
				<!-- 列出所有的部门 -->
				<c:forEach items="${deptInfoList}" var="detpInfo" step="1"
					varStatus="status">
					<li id="tab_${detpInfo.depId}"><a href="javascript:void(0);"
						<c:if test="${status.index==0}">class="on"</c:if>
						onclick="switchTab('${detpInfo.depId}');this.blur();return false;">
							<div class="report_list_ftpd" id="${detpInfo.depId}">${detpInfo.depName}</div>
					</a></li>
				</c:forEach>
			</ul>

			<div style="clear: both"></div>
			<div id="con1">
				<div class="index_title">
					<table width="100%" border="0" cellspacing="0" cellpadding="0"
						style="font-size: 12px;">
						<tr>
							<td width="23%" height="37"><div align="center">KPI指标</div>
							</td>
							<td width="30%"><div align="center">KPI指标完成率</div></td>
							<td width="9%"><div align="center">当前完成值</div></td>
							<td width="6%"><div align="center">单位</div></td>
							<td width="9%"><div align="center">3.5分值</div></td>
							<td width="8%"><div align="center">5分值</div></td>
							<td width="15%"><div align="center">
									<a href="#">操作</a><a href="#"></a>
								</div></td>
						</tr>
					</table>
				</div>
				<c:set var="tempDeptId">-1</c:set>
				<c:forEach items="${deptKpiDataList}" var="detpKpiData"
					varStatus="status">
					<!--${detpKpiData.dwmisMisDptmnt.depName}-->
					<c:if test="${status.index==0}">
					    <!-- 第一个部门信息 -->
						<!--下面是新的tab开始-->
						<div class="index_report" style="border-bottom: 1px solid #c0c4c0" style="display:block;"
							id="tabContentDiv_${detpKpiData.dwmisMisDptmnt.depId}">
					</c:if>
					<c:if test="${status.index!=0}">
					   <c:if test="${tempDeptId !=detpKpiData.dwmisMisDptmnt.depId}">
					      <!-- 不同部门 -->
					   　 </div>
					   　 <div class="clear"></div>
					   　 <!--tab1结束-->
					      <!--下面是新的tab开始-->
					   	　<div class="index_report" style="border-bottom: 1px solid #c0c4c0" style="display:<c:if test="${status.index!=0}">none;</c:if>"
							id="tabContentDiv_${detpKpiData.dwmisMisDptmnt.depId}">
						</c:if>
					</c:if>
					<c:set var="tempDeptId">${detpKpiData.dwmisMisDptmnt.depId}</c:set>
					<!--达标显示的是灰底-->
					<table width="100%" border="0" cellspacing="0" cellpadding="0"
						style="background-color: #f0f0f0; border-top: 1px solid #c0c4c0; color: #787c78">
						<tr>
							<!-- 指标名称 -->
							<td width="23%" height="37"
								style="border-left: 1px solid #c0c4c0; border-right: 1px solid #c0c4c0;"
								id='${detpKpiData.kpiTreands.kpiInfo.kpiCode}'>&nbsp;&nbsp;${detpKpiData.kpiTreands.kpiInfo.kpiName}</td>
							<!-- 指标进度条${detpKpiData.kpiTreands.percent}% -->
							<td width="30%" style="border-right: 1px solid #c0c4c0;">
							    <c:if test="${detpKpiData.kpiTreands.passGoal}"><div class="progress-bar"></c:if>
							    <c:if test="${detpKpiData.kpiTreands.passGoal==false}"><div class="progress-bar progress-alert"></c:if>
								    <c:if test="${detpKpiData.kpiTreands.percent>=100}">
								       <span class="percent" style="_width:100%;min-width:100%;">
								           ${detpKpiData.kpiTreands.percent} 
                                          <c:if test="${detpKpiData.kpiTreands.isPTValue}">pt</c:if>
                                          <c:if test="${detpKpiData.kpiTreands.isPTValue==false}">%</c:if>
								       </span>
								    </c:if>
								    <c:if test="${detpKpiData.kpiTreands.percent<100}">
								       <span class="percent" style="_width:100%;min-width:${detpKpiData.kpiTreands.percent}%;">
								           ${detpKpiData.kpiTreands.percent}
                                          <c:if test="${detpKpiData.kpiTreands.isPTValue}">pt</c:if>
                                          <c:if test="${detpKpiData.kpiTreands.isPTValue==false}">%</c:if>
								       </span>
								    </c:if>
								</div>
							</td>
							<!-- 当前完成值 -->
							<td width="9%" style="border-right: 1px solid #c0c4c0;"><div
									align="center">${detpKpiData.kpiTreands.amountDone}</div></td>
							<!-- 单位 -->
							<td width="6%" style="border-right: 1px solid #c0c4c0;"><div
									align="center">${detpKpiData.kpiTreands.kpiInfo.unitIdDesc}</div>
							</td>
							<!-- 3.5分值 -->
							<td width="9%" style="border-right: 1px solid #c0c4c0;"><div
									align="center">${detpKpiData.kpiTreands.goal35}</div></td>
							<!-- 5分值 -->
							<td width="8%" style="border-right: 1px solid #c0c4c0;"><div
									align="center">${detpKpiData.kpiTreands.goal5}</div></td>
							<!-- 操作 -->
							<td width="15%" style="border-right: 1px solid #c0c4c0;"><div
									align="center">
									<a href="javascript:void(0);" onclick="showKpiThread('${detpKpiData.kpiTreands.kpiInfo.kpiCode}','${detpKpiData.kpiTreands.kpiInfo.unitIdDesc}');">指标走势</a>&nbsp;&nbsp;
									<a href="javascript:void(0);" onclick="showKpiDetailThread('${detpKpiData.kpiTreands.kpiInfo.kpiCode}','${detpKpiData.kpiTreands.kpiInfo.unitIdDesc}');">详情分析</a>
								</div>
							</td>
						</tr>
						<c:if test="${detpKpiData.kpiTreands.hasSubKPITrends}">
						   <table width="100%" border="0" cellspacing="0" cellpadding="0"
						style="background-color: #f0f0f0; border-top: 1px solid #c0c4c0; color: #787c78">
							   <!-- 有子指标 -->
							   <c:forEach items="${detpKpiData.kpiTreands.subKPITrendsList}" var="subKpiThread">
								<tr style='background-color:#ffffff;'>
									<!-- 子指标名称 -->
									<td width="23%" height="37"
										style="border-left: 1px solid #c0c4c0; border-right: 1px solid #c0c4c0;"
										id='${subKpiThread.kpiInfo.kpiCode}'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${subKpiThread.kpiInfo.kpiName}</td>
									<!-- 子指标进度条${subKpiThread.percent}% -->
									<td width="30%" style="border-right: 1px solid #c0c4c0;">
										<c:if test="${subKpiThread.passGoal}"><div class="progress-bar"></c:if>
										<c:if test="${subKpiThread.passGoal==false}"><div class="progress-bar progress-alert"></c:if>
											<c:if test="${subKpiThread.percent>=100}">
											   <span class="percent" style="_width:100%;min-width:100%;">
												   ${subKpiThread.percent} 
												  <c:if test="${subKpiThread.isPTValue}">pt</c:if>
												  <c:if test="${subKpiThread.isPTValue==false}">%</c:if>
											   </span>
											</c:if>
											<c:if test="${subKpiThread.percent<100}">
											   <span class="percent" style="_width:100%;min-width:${subKpiThread.percent}%;">
												   ${subKpiThread.percent}
												  <c:if test="${subKpiThread.isPTValue}">pt</c:if>
												  <c:if test="${subKpiThread.isPTValue==false}">%</c:if>
											   </span>
											</c:if>
										</div>
									</td>
									<!-- 当前完成值 -->
									<td width="9%" style="border-right: 1px solid #c0c4c0;"><div
											align="center">${subKpiThread.amountDone}</div></td>
									<!-- 单位 -->
									<td width="6%" style="border-right: 1px solid #c0c4c0;"><div
											align="center">${subKpiThread.kpiInfo.unitIdDesc}</div>
									</td>
									<!-- 3.5分值 -->
									<td width="9%" style="border-right: 1px solid #c0c4c0;"><div
											align="center">${subKpiThread.goal35}</div></td>
									<!-- 5分值 -->
									<td width="8%" style="border-right: 1px solid #c0c4c0;"><div
											align="center">${subKpiThread.goal5}</div></td>
									<!-- 操作 -->
									<td width="15%" style="border-right: 1px solid #c0c4c0;"><div
											align="center">
											<a href="javascript:void(0);" onclick="showKpiThread('${subKpiThread.kpiInfo.kpiCode}','${subKpiThread.kpiInfo.unitIdDesc}');">指标走势</a>
											&nbsp;&nbsp;
									        <a href="javascript:void(0);" onclick="showKpiDetailThread('${detpKpiData.kpiTreands.kpiInfo.kpiCode}','${detpKpiData.kpiTreands.kpiInfo.unitIdDesc}');">详情分析</a>
										</div>
									</td>
								</tr>
							   </c:forEach>
						   </table>
						</c:if>
					</table>
					<!--未达标显示的是灰底-->
				</c:forEach>
				<c:if test="${tempDeptId !=-1}">
				    </div>
					<div class="clear"></div>
					<!--tab1结束-->
				</c:if>
			</div>
		</div>
	</div>
</body>
</html>
