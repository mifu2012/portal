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
    String depGroupId=request.getParameter("depGroupId");
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
<script type="text/javascript"
	src="<%=basePath%>/common/js/jquery-1.4.3.min.js"></script>
<script type="text/javascript">
/* window.parent.onscroll=function()
{
  //document.getElementById("threadChartDiv").style.top=absolute;
  scrollTop.innerText=window.parent.document.documentElement.scrollTop;

} */
//切换TAB
function switchTab(tabId,deptId) {
	var deptCount=10;
	for (i = 0; i<=10; i++) {
		if(document.getElementById("tab_" + i)==null) continue;
		document.getElementById("tab_" + i).getElementsByTagName("a")[0].className = "";
		//document.getElementById("tabContentDiv_" + i).style.display = "none";
	}
	var loadingDiv=window.parent.document.getElementById("loading_wait_Div");
	if(loadingDiv!=null) loadingDiv.style.display="";
	document.getElementById("tab_"+tabId).getElementsByTagName("a")[0].className = "on";
	document.getElementById("iframepage").src="<%=path%>/dwmisDeptKpiData/loadDeptKpiData.html?deptId="+deptId+"&queryDate=${queryDate}";
}
</script>
<!--业务量大字的样式-->
<style type="text/css">
.lwt_ywgz_g {
	width: 223px;
	height: 59px;
	color: #908c88;
	font-size: 16px;
	font-weight: bold;
	font-family: "微软雅黑";
	border-right: 1px solid #d0d0d0
}
</style>
<script type="text/javascript">
window.onload=function()
{
	var depGroupId="<%=depGroupId%>";//部门类型
	var estimateDate="${estimateDate}";//跨年
	if(depGroupId=="14002")
	{
       if(estimateDate==1)
		{
           document.getElementById("spanselectID").innerHTML="按平衡计分卡(${headDate}年快照)";
		}else
		{
		   //非跨年
           document.getElementById("spanselectID").innerHTML="按平衡计分卡";
		}
	}else
	{
       if(estimateDate==1)
		{
		   //跨年
           document.getElementById("spanselectID").innerHTML="按事业部(${headDate}年快照)";
		}else
		{
		   //非跨年
           document.getElementById("spanselectID").innerHTML="按事业部";
		}
	}
}
//显示或关闭进度条
function showLoading(crtFrm)
{
	var loadingDiv=window.parent.document.getElementById("loading_wait_Div");
	if(loadingDiv==null) return;
	if(crtFrm.readyState == "complete")
	{
		//如果加载完成
		if(loadingDiv!=null) loadingDiv.style.display="none";
	}else
	{
		if(loadingDiv!=null) loadingDiv.style.display="block";
	}
}
</script>
<script type="text/javascript">var flashMovie;
//amcharts图表生成时，自动调用函数
function amChartInited(chart_id){
	flashMovie = document.getElementById(chart_id);
	//显示进度条
	showLoading(false);	
}
//显示或隐藏进度条
function showLoading(isShow)
{
	var loadingDiv=document.getElementById("loading_wait_Div");
	if(loadingDiv==null) loadingDiv=window.parent.document.getElementById("loading_wait_Div");
	if(loadingDiv==null) loadingDiv=window.parent.parent.document.getElementById("loading_wait_Div");
	if(loadingDiv!=null)
	{
		if(isShow==false)
		{
           loadingDiv.style.display="none";
		   return;
		}
		loadingDiv.style.display="";
	}
	if(loadingDiv==null&&isShow==true)
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
function showKpiThread(kpiCode,kpiUnit,amountDone,goal35,goal5,percent,lastYearKPI,goalType,queryDate)
{
	var divTop=0;
	if(window.parent!=null)
	{
		var scrollTop=window.parent.document.body.scrollTop+window.parent.document.documentElement.scrollTop;
		if(scrollTop!=0) divTop=(scrollTop-150)+"px";
		if(scrollTop==0) divTop=(scrollTop-100)+"px";
	}else
	{
        var scrollTop=document.body.scrollTop+document.documentElement.scrollTop;
		if(scrollTop!=0) divTop=(scrollTop-150)+"px";
		if(scrollTop==0) divTop=(scrollTop-100)+"px";
	}
	
	document.getElementById("threadChartDiv").style.top=divTop;
	document.getElementById("threadChartDiv").style.display="";
	document.getElementById('unit_div_id').innerHTML=kpiUnit;
	document.getElementById('last_year_div_id').innerHTML=lastYearKPI;
	document.getElementById('current_value_div_id').innerHTML=amountDone;
	document.getElementById('three_five_points').innerHTML=goal35;
	document.getElementById('five_points').innerHTML=goal5;
	document.getElementById('completion_rates').innerHTML=percent+"%";

	
	//显示进度条
	showLoading(true);	
	    var isShowSelectButton = "yes";
		var line1 = new SWFObject("<%=path%>/common/amchart/bundle/amline/amline.swf", "amline", "750", "380", "8", "#FFFFFF");
		line1.addParam("wmode", "opaque");
		line1.addVariable("path", "<%=path%>/amchart/bundle/amline/");
		line1.addVariable("chart_id", "amline");
		line1.addVariable("key","${amchartKey}");
		line1.addVariable("settings_file", escape("<%=path%>/DwmisLineGraph/ShowLineGraphSetting.htm?kpiCodes="+kpiCode+"&showWarnLine=1&isShowSelectButton="+isShowSelectButton));
		line1.addVariable("data_file", escape("<%=path%>/DwmisLineGraph/ShowLineGraph.htm?1=1&showWarnLine=1&kpiCodes="+kpiCode+"&goalType="+goalType+"&queryDate="+queryDate));
		line1.write("threadChartContentDiv");
		
}


//显示详情分析图
function showKpiDetailThread(kpiCode,deptId,queryDate)
{
	location.href = '<%=path%>/dwmisDeptKpiData/detailsAnalysis?1=1&kpiCode='+kpiCode+'&deptId='+deptId+'&queryDate='+queryDate;
	

}

// function showKpiDetailThread(kpiCode,kpiUnit)
// {
<%-- 	location.href = '<%=path%>/dwmisDeptKpiData/detailsAnalysis?kpiCode='+kpiCode; --%>
		
// }


function restartOpen(queryDate){
	location.href="<%=path%>/dwmisDeptKpiData/showDeptKpiData.html?1=1&queryDate="+queryDate;
}


function changeTheDate(n){
	 	  var reg=new RegExp("-","g");
		  var dt=document.getElementById('maxDate').value.replace(reg,"/");
		  var today=new Date(new Date(dt).valueOf() + n*24*60*60*1000);
		  var dayDate=today.getDate();
		  var monthDate=(today.getMonth() + 1);
		  if(today.getDate()<10){
			  dayDate="0"+today.getDate();
		  }
	      if((today.getMonth() + 1)<10){ 
	    	  monthDate="0"+(today.getMonth() + 1);
		  }
		  var changedDate=today.getFullYear() + "-" + monthDate + "-" + dayDate;
		  document.getElementById('maxDate').value=changedDate; 
		  if(changedDate!=''){
			  location.href="<%=path%>/dwmisDeptKpiData/showDeptKpiData.html?&queryDate="+changedDate;
	  }
}
</script>
</head>

<body class="indexbody">
	<!-- 走势图 begin-->
	<div class="layer" id="threadChartDiv"
		style="z-index: 999; display: none; top: 0px; left: 6%;">
		<div class="layer2" style='height: 430px;'>
			<h2>
				指标走势图<span><a href="javascript:void(0);"
					onclick="Javascript:document.getElementById('threadChartDiv').style.display='none';showLoading(false);"
					style='cursor: hand;'>关闭</a> </span>
			</h2>
			<table>
				<tr>
					<td width="43"><label>单位：</label></td>
					<td width="51">
						<div id="unit_div_id"></div>
					</td>
					<td width="54"><label>去年值：</label></td>
					<td width="56">
						<div id="last_year_div_id"></div>
					</td>
				  <td width="78"><label>当前完成值：</label></td>
					<td width="55">
						<div id="current_value_div_id"></div>
				  </td>
			      <td width="62"><label>3.5分值：</label></td>
					<td width="56">
						<div id="three_five_points"></div>
				  </td>
			      <td width="47"><label>5分值：</label></td>
					<td width="57">
						<div id="five_points"></div>
				  </td>				  
				  <td width="101"><label>KPI指标完成率：</label></td>
					<td width="70">
						<div id="completion_rates"></div>
				  </td>
				</tr>
				<tr>
					<td colspan="12" >
						<div id="threadChartContentDiv">数据正在整理中，请稍候...</div>
					</td>
				</tr>
			</table>

		</div>
	</div>
	<div class="index_home">
		<div>
			<img src="<%=path%>/common/dwmis/images/wph_8.gif" />
		</div>
	
		<div class="index_title_zbjk">
<div class="index_title_pd2">
<div style="padding:5px 0px 0px 20px">
<table width="100%" border="0" cellspacing="0" cellpadding="0"
				style="padding-top: 3px">
				<tr>
					<td width="67%">当前位置：指标监控
					</td>
					<td width="10%" valign="top"><div align="right"
							style="margin-top: 4px">报告时间：</div></td>
					<td width="15%">
					<a href="javascript:void(0);" onclick="changeTheDate(-1);"><img style="vertical-align: middle;" align="middle"  src="<%=path%>/common/images/ddy_2.gif" width="11" height="23" border="0" /></a>
					<input id="maxDate" name="maxDate" type="text" class="ddy_index_input" readonly="readonly" value="${queryDate}"
						onclick="WdatePicker({isShowWeek:true,isShowClear:false,readOnly:true,startDate:'${queryDate}',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true,doubleCalendar:true,onpicked:function(dp){restartOpen(dp.cal.getNewDateStr());}})"  style="cursor: pointer; " />		
					<a href="javascript:void(0);" onclick="changeTheDate(1);"><img style="vertical-align: middle;" align="middle"  src="<%=path%>/common/images/ddy_1.gif" width="11" height="23" border="0" /></a>
					
					</td>
<!-- 					<td width="33%"><script type=text/javascript> -->
<!--  						function show_hiddendiv() { -->
<!--  							document.getElementById("hidden_div").style.display = 'none'; -->
<!--  							document.getElementById("_strHref").href = 'javascript:hidden_showdiv();'; -->
<!--  							document.getElementById("_strSpan").innerHTML = "展开"; -->
<!--  						} -->
<!--  						function hidden_showdiv() { -->
<!--  							document.getElementById("hidden_div").style.display = 'block'; -->
<!--  							document.getElementById("_strHref").href = 'javascript:show_hiddendiv();'; -->
<!--  							document.getElementById("_strSpan").innerHTML = "收起"; -->
<!--  						} -->
<!-- 					</script> -->
<!-- 						<div class="lwtzk_bg2"style="margin-left:225px; font-size: 12px; margin-top:0px"> -->
<!-- 							<div -->
<!-- 								style="padding-top: 2px; padding-left: 24px; font-size: 12px;"> -->
<!-- 								<a id="_strHref" href="javascript:show_hiddendiv();"><span -->
<!-- 									id="_strSpan">收起</span></span> </a> -->
<!-- 							</div> -->
<!-- 						</div></td> -->
				</tr>
			</table>
			</div>
			</div>
</div>
		<!--第一部分-->
		<div class="ddy_one_home"></div>
		
			<table width="100%" height="100%" border="0" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="87%"><table width="100%" border="0" cellspacing="0"
							cellpadding="0">
							<tr>
								<td></td>
							</tr>
						</table></td>
					<td width="13%"></td>
				</tr>
			</table>
		
		<div class="index_title_yy"></div>
		<div class="index_report" style="display: block" id="hidden_div">
			<div class="clear"></div>
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				bgcolor="#f0f0f0">
				<c:choose>
				<c:when test="${estimateDate == 1}">
				<tr>
					<td width="25%" height="80" align="right"><div
							class="lwt_ywgz_g">
							<div align="left">
								<div>年当年累计业务量</div>
								<div style="margin-top: 10px">
									<span class="lwt_ywgz_sz" style="font-size: 25px;">${AccumulatedVolumeOfBusinessByYearMap}</span>元
								</div>
							</div>
						</div></td>
					<td width="25%" align="right"><div class="lwt_ywgz_g">
							<div align="left">
								<div>年当年累计业务笔数</div>
								<div style="margin-top: 10px">
									<span class="lwt_ywgz_sz" style="font-size: 25px;">${TotalNumberOfBusinessPenByYearMap}</span>笔
								</div>
							</div>
						</div></td>
					<td width="25%" align="right"><div class="lwt_ywgz_g">
							<div align="left">
								<div>${headDate}年12月活跃账户数</div>
								<div style="margin-top: 10px">
									<span class="lwt_ywgz_sz" style="font-size: 25px;">${PreviousMonthActiveAccountNumberMap}</span>个
								</div>
							</div>
						</div></td>
					<td width="25%" align="right"><div class="lwt_ywgz_g"
							style="border-right: 0px">
							<div align="left">
								<div>${headDate}年动态一年活跃商户数</div>
								<div style="margin-top: 10px">
									<span class="lwt_ywgz_sz" style="font-size: 25px;">${ActiveMerchantsNumberByYearMap}</span>个
								</div>
							</div>
						</div></td>
				</tr>
				</c:when>
				<c:otherwise>
				<tr>
					<td width="25%" height="80" align="right"><div
							class="lwt_ywgz_g">
							<div align="left">
								<div>当年累计业务量</div>
								<div style="margin-top: 10px">
									<span class="lwt_ywgz_sz" style="font-size: 25px;">${AccumulatedVolumeOfBusinessByYearMap}</span>元
								</div>
							</div>
						</div></td>
					<td width="25%" align="right"><div class="lwt_ywgz_g">
							<div align="left">
								<div>当年累计业务笔数</div>
								<div style="margin-top: 10px">
									<span class="lwt_ywgz_sz" style="font-size: 25px;">${TotalNumberOfBusinessPenByYearMap}</span>笔
								</div>
							</div>
						</div></td>
					<td width="25%" align="right"><div class="lwt_ywgz_g">
							<div align="left">
								<div>上月活跃账户数</div>
								<div style="margin-top: 10px">
									<span class="lwt_ywgz_sz" style="font-size: 25px;">${PreviousMonthActiveAccountNumberMap}</span>个
								</div>
							</div>
						</div></td>
					<td width="25%" align="right"><div class="lwt_ywgz_g"
							style="border-right: 0px">
							<div align="left">
								<div>动态一年活跃商户数</div>
								<div style="margin-top: 10px">
									<span class="lwt_ywgz_sz" style="font-size: 25px;">${ActiveMerchantsNumberByYearMap}</span>个
								</div>
							</div>
						</div></td>
				</tr>
				</c:otherwise>
				</c:choose>
				
				
			</table>
		</div>
		<!--第一部分结束-->
		<div style="width: 100%; height: 20px; background-color: #f7f7f7"></div>
		<!--第二部分-->
		<!--tab切换开始-->
        <script>
        function func(){
        	document.getElementById('selectBox').style.display='block';
        }
        function func2(){
        	
        	<c:forEach items="${deptInfoList}" var="detpInfo" step="1"
				varStatus="status">
				
				
				var i =${status.index};
				
			</c:forEach>
			
			if(${estimateDate} == 1)
			{
				document.getElementById('spanselectID').innerHTML='按平衡计分卡';
			}
			else
			{
				document.getElementById('spanselectID').innerHTML='按平衡计分卡'+'(${headDate}年快照)';
			}
			
        	
        }
        </script>
       
        <c:choose>
        <c:when test="${estimateDate == 1}">
        <ul id="main_box" style="display:none;">
			<li
				style="width: 170px; height:22px; background:  url(<%=path%>/common/dwmis/images/button_011.gif) no-repeat; background-position:0px 0px; line-height:22px; text-indent:8px;  position:relative;z-index:100; float:right;color:#5b5a5a;"><span
				style="cursor: pointer; display: block; width: 100%; overflow: hidden; text-align: left; padding-left: 0px" onclick="func()" id="spanselectID">按事业部(${headDate}年快照)</span>
				<ul
					style="width: 168px; position: absolute; left: 0; top: 22px; border: 1px solid #ccc; border-top: 0px; background: #fff; z-index: 100003; display:none;"
					id="selectBox">
					<li style="display: block; line-height: 25px; width: 168px;"><a
						href="<%=path%>/dwmisDeptKpiData/showDeptKpiData.html?1=1&depGroupId=14001">按事业部(${headDate}年快照)</a></li>
					<li style="display: block; line-height: 25px; width: 168px;"><a
						href="<%=path%>/dwmisDeptKpiData/showDeptKpiData.html?1=1&depGroupId=14002" onclick="func2()">按平衡计分卡(${headDate}年快照)</a></li>

				</ul></li>
		</ul>
        </c:when>
        <c:otherwise>
        <ul id="main_box">
			<li
				style="width: 100px; height:22px; background:  url(<%=path%>/common/dwmis/images/button_nn.jpg) no-repeat; background-position:0px 0px; line-height:22px; text-indent:8px;  position:relative;z-index:100; float:right;color:#5b5a5a;"><span
				style="cursor: pointer; display: block; width: 100%; overflow: hidden; text-align: left; padding-left: 5px" onclick="func()" id="spanselectID">按事业部</span>
				<ul
					style="width: 98px; position: absolute; left: 0; top: 22px; border: 1px solid #ccc; border-top: 0px; background: #fff; z-index: 100003; display:none;"
					id="selectBox">
					<li style="display: block; line-height: 25px; width: 98px;"><a
						href="<%=path%>/dwmisDeptKpiData/showDeptKpiData.html?1=1&depGroupId=14001">按事业部</a></li>
					<li style="display: block; line-height: 25px; width: 98px;"><a
						href="<%=path%>/dwmisDeptKpiData/showDeptKpiData.html?1=1&depGroupId=14002" onclick="func2()">按平衡计分卡</a></li>

				</ul></li>
		</ul>
        </c:otherwise>
        </c:choose>
        
        
		

		<div id="tabContainer">
			<ul class="mouseover">
				<!-- 列出所有的部门 -->
				<c:set var="firstDeptId">-1</c:set>
				<c:forEach items="${deptInfoList}" var="detpInfo" step="1"
					varStatus="status">
					<c:if test="${status.index==0}">
						<c:set var="firstDeptId">${detpInfo.depId}</c:set>
					</c:if>
					<c:if test="${status.index<=6}">
						<li id="tab_${status.index}"><a href="javascript:void(0);"
							<c:if test="${status.index==0}">class="on"</c:if>
							onclick="switchTab(${status.index},'${detpInfo.depId}');this.blur();return false;">
								<div class="report_list_ftpd" id="${detpInfo.depId}">${detpInfo.depName}</div>
						</a></li>
					</c:if>
				</c:forEach>
			</ul>

			<div style="clear: both"></div>
			<div id="con1">
				<iframe
					src="<%=path%>/dwmisDeptKpiData/loadDeptKpiData.html?deptId=${firstDeptId}&queryDate=${queryDate}"
					id="iframepage" name="iframepage" frameBorder="0" scrolling="no"
					width="100%" onLoad="iFrameHeight()"
					onreadystatechange="showLoading(this);"></iframe>
			</div>
		</div>
	</div>
<!-- 	<div> -->
<!-- 	<table> -->
<!-- 	<tr> -->
<!-- 	<td> -->

<!-- 	<div style="width:995px; hegiht:30px; border-bottom:1px solid #ccc; font-size:16px; font-bolde:bold; margin-left:5px; margin-top:10px"> -->
<!-- 	备注： -->
<!-- 	</div> -->
<!--     <div style="width:995px; hegiht:auto; font-size:12px;  margin-left:20px; margin-top:5px; line-height:140%; margin-right:0px">费地方的费地方的费地方的费地方的费地方的费地方的费地方的费地方的费地方的费地方的费地方的费地方的费地方的费地方的费地方的费地方的费地方的费地方的费地方的费地方的费地方的费地方的费地方的费地方的费地方的费地方的费地方的费地方的费地方的费地方的费地方的费地方的费地方的费地方的费地方的费地方的</div> -->
<!-- 	</td> -->
<!-- 	</tr> -->
<!-- 	<tr> -->
<!-- 	<td> -->
<!-- 	</td> -->
<!-- 	</tr> -->
<!-- 	</table> -->
<!-- 	</div> -->
</body>
</html>
<script type="text/javascript" language="javascript">   
	function iFrameHeight() {   
		var ifm= document.getElementById("iframepage");   
		var subWeb = document.frames ? document.frames["iframepage"].document : ifm.contentDocument;   
		if(ifm != null && subWeb != null) {
		   ifm.height = subWeb.body.scrollHeight;
		   try
		   {
			  window.parent.iframeOnload();
		   }
		   catch (e)
		   {
		   }
		}
		//自适应
		var iframeContentHeight = document.body.scrollHeight;
		parent.changeIframeSize(iframeContentHeight);
	}   
</script>