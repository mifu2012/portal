<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import="com.infosmart.portal.pojo.DwpasCKpiInfo"%>
<%@ page import="com.infosmart.portal.pojo.DwpasCModuleInfo"%>
<%@ page import="com.infosmart.portal.pojo.DwpasCColumnInfo"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	//定义位置常量
	request.setAttribute("POSITION_TOP", DwpasCModuleInfo.POSITION_TOP);
	request.setAttribute("POSITION_LEFT", DwpasCModuleInfo.POSITION_LEFT);
	request.setAttribute("POSITION_RIGHT", DwpasCModuleInfo.POSITION_RIGHT);
	request.setAttribute("POSITION_BOTTOM", DwpasCModuleInfo.POSITION_BOTTOM);
	//模块：系统模块类型
	request.setAttribute("MODULE_TYPE_SYSTEM", DwpasCModuleInfo.MODULE_TYPE_SYSTEM);
	//模块：自定义模块类型
	request.setAttribute("MODULE_TYPE_SELF", DwpasCModuleInfo.MODULE_TYPE_SELF);
	//模块：报表模块类型
	request.setAttribute("MODULE_TYPE_REPORT", DwpasCModuleInfo.MODULE_TYPE_REPORT);
	//栏目：图表类型
	request.setAttribute("CHART_TYPE_UNDEFINED", DwpasCColumnInfo.CHART_TYPE_UNDEFINED);
	//栏目：自定义URL类型
	request.setAttribute("CHART_TYPE_URL", DwpasCColumnInfo.CHART_TYPE_URL);
	//栏目：自定义报表
	request.setAttribute("CHART_TYPE_REPORT_URL", DwpasCColumnInfo.CHART_TYPE_REPORT_URL);
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
<link href="<%=basePath%>/common/css/cssz.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>/common/css/base.css" rel="stylesheet" type="text/css" />
<!--<link href="<%=basePath%>/common/css/style.css" rel="stylesheet" type="text/css" />-->
<!--日历控件-->
<script src="<%=basePath%>/common/js/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<!--月历控件-->
<script type="text/javascript" src="<%=basePath%>/common/js/My97DatePicker/My98MonthPicker.js"></script>
<!--
<script src="<%=basePath%>/common/js/miaov.js" type="text/javascript"></script>
-->
<!--AMCHART-->
<%-- <script src="<%=basePath%>/common/amchart/amcharts.js" type="text/javascript"></script>
<script src="<%=basePath%>/common/amchart/amfallback.js" type="text/javascript"></script> --%>
<script src="<%=basePath%>/common/js/raphael.js" type="text/javascript"></script>
<script src="<%=basePath%>/common/js/arale.js" type="text/javascript"></script>
<script src="<%=basePath%>/common/js/FusionCharts_Trial.js" type="text/javascript"></script>
<script src="<%=basePath%>/common/js/jquery-1.5.1.min.js" type="text/javascript"></script>
<!--
<script src="<%=path%>/common/js/arale.core-1.1.js" type="text/javascript" charset="utf-8"></script>
-->
<script src="<%=basePath%>/common/amchart/stock/amstock/swfobject.js" type="text/javascript"></script>
<script type="text/javascript">
	var amchart_key = "${amchartKey}";
</script>
<style>
.ddy_index_input {
	width: 110px;
	height: 20px;
	border: 0px;
	background-repeat: no-repeat;
	text-align: center;
	background-image: url('<%=path%>/common/images/input_button_1.gif');
}
</style>
<script type="text/javascript">
window.onload=function()
{
	//自适应
	var iframeContentHeight = document.body.scrollHeight;
	if(window.parent.document.getElementById('center_iframe')!=null) window.parent.document.getElementById('center_iframe').height=iframeContentHeight;
	var loadingDiv=window.parent.document.getElementById("loading_wait_Div");
	if(loadingDiv!=null) loadingDiv.style.display="none";
	var mastParent = window.parent.parent;
	var liList= mastParent.document.getElementsByName("nav_li");
	for(var i=0; i<liList.length; i++){
		var value = liList[i].value;
		if(value=="${systemMenu.menuId}"){
			mastParent.document.getElementById("li_"+value).style.backgroundImage="url(<%=basePath%>/common/images/top_click.gif)";
			
		}else{
			mastParent.document.getElementById("li_"+value).style.backgroundImage="";
		}
	}
	
}
//显示或隐藏帮助信息
function showHelpDiv(moduleCode,isShow)
{
   var helpInfoDiv=document.getElementById("help_"+moduleCode);
   helpInfoDiv.style.display=isShow?"block":"none";
}
function test()
{
}
//重新打开页面
function restartOpen(queryDate)
{
	if(queryDate==null||queryDate.length==0) queryDate=document.getElementById("maxDate").value;
	document.body.disabled=true;
    document.location.href='<%=basePath%>/jumpToUrl/gotoNewPage.html?1=1&menuId=${systemMenu.menuId}&queryDate='
				+ queryDate;
}
	//日期控件左右点击事件
	function changeTheDate(n) {
		var reg = new RegExp("-", "g");
		var dt = "${date}".replace(reg, "/");
		var today = new Date(new Date(dt).valueOf() + n * 24 * 60 * 60 * 1000);
		var dayDate = today.getDate();
		var monthDate = (today.getMonth() + 1);
		if (today.getDate() < 10) {
			dayDate = "0" + today.getDate();
		}
		if ((today.getMonth() + 1) < 10) {
			monthDate = "0" + (today.getMonth() + 1);
		}
		var changedDate = today.getFullYear() + "-" + monthDate + "-" + dayDate;
		//重新打开页面
		restartOpen(changedDate);
	}
	//日期控件左右点击事件
	function changeTheMonth(n) {
		var reg = new RegExp("-", "g");
		var dt = "${date}";
		dt = dt.replace(reg, "/") + "/01";
		var today = new Date(new Date(dt).valueOf());
		today.setMonth(today.getMonth() + n);
		var monthDate = today.getMonth() + 1;
		if ((today.getMonth() + 1) < 10) {
			monthDate = "0" + (today.getMonth() + 1);
		}
		var changedDate = today.getFullYear() + "-" + monthDate;
		document.getElementById('maxDate').value = changedDate;
		restartOpen(changedDate);
	}
	
	function changeProduct(newProductId){
		document.location.href="<%=basePath%>/jumpToUrl/gotoNewPage.html?1=1&menuId=${systemMenu.menuId}&kpiType=${kpiType}&productId="+newProductId;
	}
	
	//TABN切换(解决只有一个模块，TAB切换的BUG)
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
</head>
<body class="indexbody2">
	<!-- 开始加载产品选择 -->
	<jsp:include page="../common/ChoiceProduct.jsp" />
	<!-- 结束加载产品选择 -->
	<div class="new_jwy_yy">
		<!-- 当前位置 -->
		<div class="kpi_position" id="navigationDiv">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="78%">
						<div style="padding: 3px 0px 0px 11px">
							<div style="float: left">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 当前位置：
								<c:if test="${not empty systemMenu.menuPid}">
									<a
										href='<%=path%>${systemMenu.systemParentMenu.menuUrl}&menuId=${systemMenu.systemParentMenu.menuId}'>${systemMenu.systemParentMenu.menuName}</a>&gt;
							</c:if>
								<a
									href='<%=path%>${systemMenu.menuUrl}&menuId=${systemMenu.menuId}'>${systemMenu.menuName}</a>
								<!-- 部门指标监控暂时不需产品选择，先注释以下2个if -->
								 							<c:if test="${systemMenu.menuCode ne '01'}">
								<c:if test="${not empty productInfo}">&nbsp;>&nbsp;<font
										color='red'>${productInfo.productName}</font>
								</c:if>
							</c:if> 
							</div>
							 						<c:if test="${systemMenu.menuCode ne '01'}">
						<div style="float:left">&nbsp;&nbsp;
						<a href="javascript:void(0);" onclick="choiceRrdInfo();"><img src="<%=path%>/common/images/jwy_button_01.gif" border="0" /></a></div>
	                    </c:if>	 
						</div></td>
					<td width="22%">
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							style="margin-top:0px;">
							<tr>
								<td>
									<div style="padding-top: 4px">
										<span style="vertical-align: middle;"> 数据时间: </span>
										<c:if test="${kpiType eq 1}">
											<!--日期-->
											<a href="javascript:void(0);" onclick="changeTheDate(-1);"><img
												style="vertical-align: middle;" align="middle"
												src="<%=path%>/common/images/ddy_2.gif" width="11"
												height="23" border="0" /> </a>
											<input id="maxDate" name="maxDate" type="text"
												class="ddy_index_input" style="z-index:99999" value="${date}" readonly="readonly"
												onclick="WdatePicker({isShowWeek:true,doubleCalendar:true,isShowClear:false,readOnly:true,startDate:'${date}',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true,onpicked:function(dp){restartOpen();}})"
												 style="cursor: pointer;" />
											<a href="javascript:void(0);" onclick="changeTheDate(1);"><img
												style="vertical-align: middle;" align="middle"
												src="<%=path%>/common/images/ddy_1.gif" width="11"
												height="23" border="0" /> </a>
										</c:if>
										<c:if test="${kpiType eq 3}">
											<!--月份-->
											<a href="javascript:void(0);" onclick="changeTheMonth(-1);"><img
												style="vertical-align: middle;" align="middle"
												src="<%=path%>/common/images/ddy_2.gif" width="11"
												height="23" border="0" /> </a>
											<input id="maxDate" name="maxDate" type="text"
												class="ddy_index_input" readonly="readonly"
												onclick="setMonth(this,this)" value="${date}"
												onchange="restartOpen(this.value)" style="cursor: pointer;" />

											<a href="javascript:void(0);" onclick="changeTheMonth(1);"><img
												style="vertical-align: middle;" align="middle"
												src="<%=path%>/common/images/ddy_1.gif" width="11"
												height="23" border="0" /> </a>
										</c:if>
									</div></td>
							</tr>
						</table></td>
				</tr>
			</table>
		</div>
		<!-- 当前位置结束 -->
		<div class="index_home">
			<!--置顶为灰色-->
			<div class="ddy_channel2"></div>
			<!-- 空隙 -->
			<div class="clear" style="margin-top: 7px"></div>
			<!-- 下面是正文 -->
			<c:if test="${fn:length(moduleInfoList)==0}">
				<div style="color: red;" align='center'>当前菜单没有关联任何模块</div>
			</c:if>
			<c:if test="${fn:length(moduleInfoList)>0}">
				<!-- 是否新行，0：否，1是;有左右，或为底部，则为新行 -->
				<c:set var="isNewRow">0</c:set>
				<c:set var="rowCount">0</c:set>
				<c:forEach items="${moduleInfoList}" var="moduleInfo" varStatus="vs">
					<c:if test="${vs.index eq 0 || isNewRow eq 1}">
						<!--第${rowCount+1}部分开始 -->
						<c:if test="${rowCount eq 0 }">
							<!-- 第1部门需加下面的DIV -->
							<div
								style="width: 1000px; height: ${moduleInfo.height}px; overflow: hidden;z-index:1;">
						</c:if>
					</c:if>
					<c:choose>
						<c:when test="${moduleInfo.position eq POSITION_LEFT}">
							<!-- 左边 -->
							<!------------------接下来出场的是：模块:${moduleInfo.moduleName}，位置:${moduleInfo.position}-->
							<div class="tabdata02_cdy"
								style="overflow: hidden; height: ${moduleInfo.height}px;border:0px solid red;background:#ffffff;">
								<!-- 头信息 -->
								<div class="datat02_new">
									<!-- 模块名 -->
									<div class="index_title_pd" style="float: left; width: 600px;">
										<table>
											<tr>
												<td><img src="<%=path%>/common/images/new_jwy_4.gif" />
												</td>
												<td id="td_header_${moduleInfo.moduleId}">&nbsp;${moduleInfo.moduleName}</td>
											</tr>
										</table>
									</div>
									<!--帮助信息开始 -->
									<div class="box-askicon"
										onmousemove="showHelpDiv('${moduleInfo.moduleCode}',true);"
										onmouseout="showHelpDiv('${moduleInfo.moduleCode}',false);"
										style="cursor: pointer">
										<img src="<%=path%>/common/images/askicon5.png" />
									</div>
									<div id="help_${moduleInfo.moduleCode}"
										style="display: none; font-size: 12px;">
										<div style="position:relative;z-index:99999999">
										<div class="pop_w">
										  <div class="pop_z" style="z-index:99999; left:260px;" >	<img src="<%=path%>/common/images/pop_tip.png" /></div>
       
<div class="pop_x">				<b>${moduleInfo.moduleName}：</b>
													</p>
													<p style="font-weight: normal;">${moduleInfo.remark}</p></p>
   
</div>
</div></div>
									</div>
									<!--帮助信息结束-->
								</div>
								<!-- 模块内容-->
								<div id="content_${moduleInfo.moduleId}"
									style="border: 0px solid blue; border-top: 0px; border-bottom: 0px;width:100%;height:${moduleInfo.height-40}px">
									<c:choose>
										<c:when test="${moduleInfo.moduleType eq MODULE_TYPE_SYSTEM}">
											<!-- 系统模块：自定义URL -->
											<iframe
												src="<%=basePath%>/index/redirectColumnUrl.html?moduleId=${moduleInfo.moduleId}&kpiType=${moduleInfo.kpiType}&moduleCode=${moduleInfo.moduleCode}"
												style='z-index: -1;' id="iframepage_${moduleInfo.moduleId}"
												name="iframepage_${moduleInfo.moduleId}" frameBorder="0"
												scrolling="no" width="100%"
												height="${moduleInfo.height-40}px"></iframe>
										</c:when>
										<c:when test="${moduleInfo.moduleType eq MODULE_TYPE_REPORT}">
											<!-- 报表模块：报表URL -->
											<iframe
												src="<%=basePath%>/index/redirectReportUrl.html?moduleId=${moduleInfo.moduleId}&kpiType=${moduleInfo.kpiType}"
												style='z-index: -1;' id="iframepage_${moduleInfo.moduleId}"
												name="iframepage_${moduleInfo.moduleId}" frameBorder="0"
												scrolling="no" width="100%"
												height="${moduleInfo.height-40}px"></iframe>
										</c:when>
										<c:otherwise>
											<!-- 自定义图表模块 -->
											<%--
											<iframe
												src="<%=basePath%>/index/forwardChartUrl.html?moduleId=${moduleInfo.moduleId}&kpiType=${moduleInfo.kpiType}"
												style='z-index: -1;' id="iframepage_${moduleInfo.moduleId}"
												name="iframepage_${moduleInfo.moduleId}" frameBorder="0"
												scrolling="no" width="100%"
												height="${moduleInfo.height-40}px"></iframe>
											
											--%>
											<!--改用AJAX加载-->
											 
											<img src="<%=basePath%>/common/images/loading-small.gif" border="0"><font size='3'>数据加载中,请稍候...</font>
											<script language="javascript">
											   //涉及加载页面及脚本，故用LOAD()方法
											   $("#content_${moduleInfo.moduleId}").load("<%=basePath%>/index/forwardChartUrl.html?moduleId=${moduleInfo.moduleId}&kpiType=${moduleInfo.kpiType}&tabShow=${moduleInfo.tabShow}&productId=${productInfo.productId}", function(responseText, textStatus, XMLHttpRequest) {
												   if(textStatus=="success")
												   {
												      //alert('加载成功:'+responseText);
												   }else
												   {
													   //alert('加载失败');
													   document.getElementById("content_${moduleInfo.moduleId}").innerHTML="<img src='<%=basePath%>/common/images/admin_error_02.gif' border='0'><font color='red' size='3'>抱歉，加载数据失败...</font>";
												   }
												});
											</script>
										
										</c:otherwise>
									</c:choose>
								</div>
							</div>
							<!------------------模块:${moduleInfo.moduleName} 结束啦-->
							<c:set var="isNewRow">0</c:set>
						</c:when>
						<c:when test="${moduleInfo.position eq POSITION_RIGHT}">
							<!-- 右边 -->
							<!------------------接下来出场的是：模块:${moduleInfo.moduleName}，位置:${moduleInfo.position}-->
							<div class="tabdata01_cdy"
								style="overflow: hidden; width: 300px; height: ${moduleInfo.height}px;border:0px solid red;">
								<div class="datat012">
									<div class="index_title_pd">
										<table>
											<tr>
												<td><img src="<%=path%>/common/images/new_jwy_4.gif" />
												</td>
												<td id="td_header_${moduleInfo.moduleId}">&nbsp;${moduleInfo.moduleName}</td>
											</tr>
										</table>
									</div>
									<div class="box-askicon"
										onmousemove="showHelpDiv('${moduleInfo.moduleCode}',true);"
										onmouseout="showHelpDiv('${moduleInfo.moduleCode}',false);"
										style="cursor: pointer">
										<img src="<%=path%>/common/images/askicon5.png" />
									</div>
									<!--帮助信息开始-->
									<div id="help_${moduleInfo.moduleCode}"
										style="display: none; font-size: 12px; z-index: 999;">
										
										<div class="pop_ww" style="margin-top:46px" >
										  <div class="pop_z"  style="z-index:99999999;  margin-left:0px">	<img src="<%=path%>/common/images/pop_tip.png" /></div>
       
<div class="pop_x">				<b>${moduleInfo.moduleName}：</b>
													</p>
													<p style="font-weight: normal;">${moduleInfo.remark}</p></p>
   
</div></div>

									</div>
									<!--结束信息结束-->
								</div>
								<div id="content_${moduleInfo.moduleId}"
									style="border: 1px solid #dbe1e6; border-top: 0px; border-bottom: 1px solid #dbe1e6;; height:${moduleInfo.height-40}px">
									<c:choose>
										<c:when test="${moduleInfo.moduleType eq MODULE_TYPE_SYSTEM}">
											<!-- 系统模块${moduleInfo.moduleCode}：自定义URL -->
											<iframe
												src="<%=basePath%>/index/redirectColumnUrl.html?moduleId=${moduleInfo.moduleId}&kpiType=${moduleInfo.kpiType}&moduleCode=${moduleInfo.moduleCode}"
												style='z-index: -1;' id="iframepage_${moduleInfo.moduleId}"
												name="iframepage_${moduleInfo.moduleId}" frameBorder="0"
												scrolling="no" width="100%"
												height="${moduleInfo.height-40}px"></iframe>
										</c:when>
										<c:when test="${moduleInfo.moduleType eq MODULE_TYPE_REPORT}">
											<!-- 报表模块：报表URL -->
											<iframe
												src="<%=basePath%>/index/redirectReportUrl.html?moduleId=${moduleInfo.moduleId}&kpiType=${moduleInfo.kpiType}"
												style='z-index: -1;' id="iframepage_${moduleInfo.moduleId}"
												name="iframepage_${moduleInfo.moduleId}" frameBorder="0"
												scrolling="no" width="100%"
												height="${moduleInfo.height-40}px"></iframe>
										</c:when>
										<c:otherwise>
											<!-- 自定义图表模块 -->
											<%--
											<iframe
												src="<%=basePath%>/index/forwardChartUrl.html?moduleId=${moduleInfo.moduleId}&kpiType=${moduleInfo.kpiType}"
												style='z-index: -1;' id="iframepage_${moduleInfo.moduleId}"
												name="iframepage_${moduleInfo.moduleId}" frameBorder="0"
												scrolling="no" width="100%"
												height="${moduleInfo.height-40}px"></iframe>
											--%>
											<!--改用AJAX加载-->
											<img src="<%=basePath%>/common/images/loading-small.gif" border="0"><font size='3'>数据加载中,请稍候...</font>
											<script language="javascript">
											    //涉及加载页面及脚本，故用LOAD()方法
											   $("#content_${moduleInfo.moduleId}").load("<%=basePath%>/index/forwardChartUrl.html?moduleId=${moduleInfo.moduleId}&kpiType=${moduleInfo.kpiType}&tabShow=${moduleInfo.tabShow}&productId=${productInfo.productId}", function(responseText, textStatus, XMLHttpRequest) {
												   if(textStatus=="success")
												   {
												      //alert('加载成功');
												   }else
												   {
													   //alert('加载失败');
													   document.getElementById("content_${moduleInfo.moduleId}").innerHTML="<img src='<%=basePath%>/common/images/admin_error_02.gif' border='0'><font color='red' size='3'>抱歉，加载数据失败...</font>";
												   }
												});
											</script>
										</c:otherwise>
									</c:choose>
								</div>
								<!--
							<div class="clear"></div>
							-->
							</div>
							<!------------------模块:${moduleInfo.moduleName} 结束啦-->
							<c:set var="isNewRow">1</c:set>
						</c:when>
						<c:otherwise>
							<!-- 底部 -->
							<!------------------接下来出场的是：模块:${moduleInfo.moduleName}，位置:${moduleInfo.position}-->
							<div class="normalbox mt15" id="J-userchangebox">

								<div class="index_title" style="background-image: url(<%=path%>/common/images/new_jwy_6.gif);">
									<div class="index_title_pd">
										<table>
											<tr>
												<td><img src="<%=path%>/common/images/new_jwy_4.gif" />
												</td>
												<td id="td_header_${moduleInfo.moduleId}">&nbsp; ${moduleInfo.moduleName}</td>
											</tr>
										</table>

									</div>
									<span class="box-top-right"> <em
										style="background: url(<%=path%>/common/images/askicon5.png); cursor: pointer"
										class="box-askicon"
										onmouseover="showHelpDiv('${moduleInfo.moduleCode}',true);"
										onmouseout="showHelpDiv('${moduleInfo.moduleCode}',false);"></em>
									</span>
									<div id="help_${moduleInfo.moduleCode}"
										style="display: none; font-size: 12px;">
										<div class="pop_w">
										  <div class="pop_z" style="z-index:99999">	<img src="<%=path%>/common/images/pop_tip.png" /></div>
       
<div class="pop_x">				<b>${moduleInfo.moduleName}：</b>
													</p>
													<p style="font-weight: normal;">${moduleInfo.remark}</p></p>
   
</div>
</div>
									</div>
								</div>
								<div class="clear"></div>
								<div class="box-conent box-ued-table" id="content_${moduleInfo.moduleId}" style="height:${moduleInfo.height-40}px;">
									<c:choose>
										<c:when test="${moduleInfo.moduleType eq MODULE_TYPE_SYSTEM}">
											<!-- 系统模块：自定义URL -->
											<iframe
												src="<%=basePath%>/index/redirectColumnUrl.html?moduleId=${moduleInfo.moduleId}&kpiType=${moduleInfo.kpiType}&moduleCode=${moduleInfo.moduleCode}"
												style='z-index: -1;' id="iframepage_${moduleInfo.moduleId}"
												name="iframepage_${moduleInfo.moduleId}" frameBorder="0"
												scrolling="no" width="100%"
												height="${moduleInfo.height-40}px"></iframe>
										</c:when>
										<c:when test="${moduleInfo.moduleType eq MODULE_TYPE_REPORT}">
											<!-- 报表模块：报表URL(报表) -->
											<iframe
												src="<%=basePath%>/index/redirectReportUrl.html?moduleId=${moduleInfo.moduleId}&kpiType=${moduleInfo.kpiType}"
												style='z-index: -1;' id="iframepage_${moduleInfo.moduleId}"
												name="iframepage_${moduleInfo.moduleId}" frameBorder="0"
												scrolling="no" width="100%"
												height="${moduleInfo.height-40}px"></iframe>
										</c:when>
										<c:otherwise>
											<!-- 自定义图表模块 -->
											<%--
											<iframe
												src="<%=basePath%>/index/forwardChartUrl.html?moduleId=${moduleInfo.moduleId}&kpiType=${moduleInfo.kpiType}"
												style='z-index: -1;' id="iframepage_${moduleInfo.moduleId}"
												name="iframepage_${moduleInfo.moduleId}" frameBorder="0"
												scrolling="no" width="100%"
												height="${moduleInfo.height-40}px"></iframe>
											--%>
											<!--改用AJAX加载-->
											<img src="<%=basePath%>/common/images/loading-small.gif" border="0"><font size='3'>数据加载中,请稍候...</font>
											<script language="javascript">
											    //涉及加载页面及脚本，故用LOAD()方法
											   $("#content_${moduleInfo.moduleId}").load("<%=basePath%>/index/forwardChartUrl.html?moduleId=${moduleInfo.moduleId}&kpiType=${moduleInfo.kpiType}&tabShow=${moduleInfo.tabShow}&productId=${productInfo.productId}", function(responseText, textStatus, XMLHttpRequest) {
												   if(textStatus=="success")
												   {
												      //alert('加载成功');
												   }else
												   {
													   //alert('加载失败');
													   document.getElementById("content_${moduleInfo.moduleId}").innerHTML="<img src='<%=basePath%>/common/images/admin_error_02.gif' border='0'><font color='red' size='3'>抱歉，加载数据失败...</font>";
												   }
												});
											</script>
										</c:otherwise>
									</c:choose>
								</div>
							</div>
							<div class="clear"></div>
							<div class="clear"></div>
							<div class="clear"></div>
							<!------------------模块:${moduleInfo.moduleName} 结束啦-->
							<c:set var="isNewRow">1</c:set>
						</c:otherwise>
					</c:choose>
					<c:if test="${isNewRow eq 1}">
						<!--第${rowCount+1}部分结束-->
						<c:if test="${rowCount eq 0 }">
							<!-- 第1部门需加下面的(结束)DIV -->
		</div>
		</c:if>
		<!--当前第${vs.index+1}个-->
		<c:if test="${vs.index+1<fn:length(moduleInfoList)}">
			<!--空隙-->
			<!--
		<div class="clear"></div>
		-->
			<!-- 上一部分结束 -->
			<!-- 下一部分开始 -->
			<!--
		<div class="clear"></div>
		-->
			<!-- 行数加1 -->
			<c:set var="rowCount">${rowCount+1}</c:set>
		</c:if>
		</c:if>
		</c:forEach>
		</c:if>
	</div>
</body>
</html>