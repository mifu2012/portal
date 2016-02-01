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
<link href="<%=basePath%>/common/css/cssz.css" rel="stylesheet"
	type="text/css" />
<link href="<%=basePath%>/common/css/base.css" rel="stylesheet"
	type="text/css" />
<link href="<%=basePath%>/common/css/lwt.css" rel="stylesheet"
	type="text/css" />
<!--<link href="<%=basePath%>/common/css/style.css" rel="stylesheet"
	type="text/css" />-->
<!--日历控件-->
<script src="<%=basePath%>/common/js/My97DatePicker/WdatePicker.js"
	type="text/javascript"></script>
<!--月历控件-->
<script type="text/javascript"
	src="<%=basePath%>/common/js/My97DatePicker/My98MonthPicker.js"></script>
<script src="<%=basePath%>/common/js/miaov.js" type="text/javascript"></script>
<script src="<%=basePath%>/common/js/amcharts.js" type="text/javascript"></script>
<script src="<%=basePath%>/common/js/raphael.js" type="text/javascript"></script>
<script src="<%=basePath%>/common/js/arale.js" type="text/javascript"></script>
<script src="<%=basePath%>/common/js/FusionCharts_Trial.js" type="text/javascript"></script>
<!--
<script src="<%=path%>/common/js/arale.core-1.1.js" type="text/javascript" charset="utf-8"></script>
-->
<script src="<%=basePath%>/common/amchart/stock/amstock/swfobject.js" type="text/javascript"></script>
<script src="<%=basePath%>/common/js/amfallback.js" type="text/javascript"></script>
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
//显示或隐藏帮助信息
function showHelpDiv(moduleCode,isShow)
{
   var helpInfoDiv=document.getElementById("help_"+moduleCode);
       helpInfoDiv.style.display=isShow?"block":"none";
}
//重新打开页面
function restartOpen(queryDate)
{
	document.body.disabled=true;
    document.location.href='<%=basePath%>/jumpToUrl/gotoNewPage.html?1=1&menuId=${systemMenu.menuId}&queryDate='+ queryDate;
}
//日期控件左右点击事件
function changeTheDate(n)
{
   var reg=new RegExp("-","g");
   var dt="${date}".replace(reg,"/");
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
   //重新打开页面
   restartOpen(changedDate);
}
//日期控件左右点击事件
function changeTheMonth(n)
{
　 var thisMonth=new Date("${date}");
   var newTime=thisMonth.setMonth( thisMonth.getMonth() + n*1 );
   var newYear=(new Date(newTime)).getFullYear();
   var newMonth=(new Date(newTime)).getMonth()+1;
   if(newMonth<=9) newMonth="0"+newMonth;
   //重新打开页面
   restartOpen(newYear+"-"+newMonth);
}
//显示弹窗内容
function showWindowOpenDiv(divTitle,divContent)
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
	document.getElementById("windowOpenDiv").style.top=divTop;
	document.getElementById("windowOpenDiv").style.display="block";
	document.getElementById("windowOpenDiv_title").innerHTML="<B>"+divTitle+"</B>";
	//div内容
	document.getElementById("windowOpenDiv_content").innerHTML=divContent;
}
</script>
</head>
<body class="indexbody">
    <c:if test="${systemMenu.menuCode ne '01'}">
       <!-- 开始加载产品选择 -->
       <jsp:include page="../common/ChoiceProduct.jsp"/>
       <!-- 结束加载产品选择 -->
    </c:if>
	<div class="index_home">
		<div class="ddy_channel" style="padding: 5px 0px 3px 0px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="50%">当前位置： <c:if
							test="${not empty systemMenu.menuPid}">
							<a
								href='<%=path%>${systemMenu.systemParentMenu.menuUrl}&menuId=${systemMenu.systemParentMenu.menuId}'>${systemMenu.systemParentMenu.menuName}</a>
						</c:if> <a
						href='<%=path%>${systemMenu.menuUrl}&menuId=${systemMenu.menuId}'>${systemMenu.menuName}</a><c:if test="${systemMenu.menuCode ne '01'}"><c:if test="${not empty productInfo}">&nbsp;>&nbsp;<font color='red'>${productInfo.productName}</font></c:if><a href="javascript:void(0);" onclick="choiceRrdInfo()">(更改分析产品或渠道)</a></c:if>
					</td>
					<td width="50%" style="text-align: right;"><span
						style="line-height: 20px; vertical-align: middle;"> 数据时间: </span>
						<c:if test="${kpiType eq 1}">
						    <!--日期-->
							<a href="javascript:void(0);" onclick="changeTheDate(-1);"><img
								style="vertical-align: middle;" align="middle"
								src="<%=path%>/common/images/ddy_2.gif" width="11" height="23"
								border="0" /> </a>
							<input id="maxDate" name="maxDate" type="text"
								class="ddy_index_input" value="${date}" readonly="readonly"
								onclick="WdatePicker({isShowClear:false,readOnly:true,startDate:'${date}',dateFmt:'yyyy-MM-dd ',alwaysUseStartDate:true})"
								onchange="restartOpen(this.value);" style="cursor: pointer;" />
							<a href="javascript:void(0);" onclick="changeTheDate(1);"><img
								style="vertical-align: middle;" align="middle"
								src="<%=path%>/common/images/ddy_1.gif" width="11" height="23"
								border="0" /> </a>
						</c:if> 
						<c:if test="${kpiType eq 3}">
						　　<!--月份-->
							<a href="javascript:void(0);" onclick="changeTheMonth(-1);"><img
								style="vertical-align: middle;" align="middle"
								src="<%=path%>/common/images/ddy_2.gif" width="11" height="23"
								border="0" /> </a>
							<input id="queryDate" name="queryDate" type="text"
								class="ddy_index_input" readonly="readonly"
								onclick="setMonth(this,this)" value="${date}"
								onchange="restartOpen()" style="cursor: pointer;" />

							<a href="javascript:void(0);" onclick="changeTheMonth(1);"><img
								style="vertical-align: middle;" align="middle"
								src="<%=path%>/common/images/ddy_1.gif" width="11" height="23"
								border="0" /> </a>
						</c:if>
					</td>
				</tr>
			</table>
		</div>
		<!-- 空隙 -->
		<div class="clear"></div>
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
						<div style="width: 1000px; height: ${moduleInfo.height}px; overflow: hidden;z-index:1;">
					</c:if>
				</c:if>
				<c:choose>
					<c:when test="${moduleInfo.position eq POSITION_LEFT}">
						<!-- 左边 -->
						<!------------------接下来出场的是：模块:${moduleInfo.moduleName}，位置:${moduleInfo.position}-->
						<div class="tabdata02_cdy"
							style="overflow: hidden; height: ${moduleInfo.height}px;">
							<!-- 头信息 -->
							<div class="datat02">
								<!-- 模块名 -->
								<div class="index_title_pd " style="float: left; width: 600px;">${moduleInfo.moduleName}</div>
								<!--帮助信息开始 -->
								<div class="box-askicon"
									onmousemove="showHelpDiv('${moduleInfo.moduleCode}',true);"
									onmouseout="showHelpDiv('${moduleInfo.moduleCode}',false);"
									style="cursor: pointer">
									<img src="<%=path%>/common/images/askicon5.png" />
								</div>
								<div id="help_${moduleInfo.moduleCode}"
									style="position:absolute;display:none; font-size: 12px;z-index:999;">
									<div style="padding: 32px 0px 0px 420px;">
										<div style="width: 250px" class="tip_33">
											<p>
												<b>${moduleInfo.moduleName}：</b>
											</p>
											<p style="font-weight: normal;">${moduleInfo.remark}</p>
										</div>
									</div>
								</div>
								<!--帮助信息结束-->
							</div>
							<!--标题头底色-->
							<div class="index_title_yy3"></div>
							<!-- 模块内容-->
							<div id="tagr"
								style="border: 1px solid #dbe1e6; border-top: 0px; border-bottom: 0px;">
								<c:choose>
									<c:when test="${moduleInfo.moduleType eq MODULE_TYPE_SYSTEM}">
										<!-- 系统模块：自定义URL -->
										<iframe
											src="<%=basePath%>/index/redirectColumnUrl.html?moduleId=${moduleInfo.moduleId}&kpiType=${moduleInfo.kpiType}" style='z-index:-1;'
											id="iframepage_${moduleInfo.moduleId}" name="iframepage_${moduleInfo.moduleId}"
											frameBorder="0" scrolling="no" width="100%" height="${moduleInfo.height-40}px"></iframe>
									</c:when>
									<c:when test="${moduleInfo.moduleType eq MODULE_TYPE_REPORT}">
										<!-- 报表模块：报表URL -->
										<iframe
											src="<%=basePath%>/index/redirectReportUrl.html?moduleId=${moduleInfo.moduleId}&kpiType=${moduleInfo.kpiType}" style='z-index:-1;'
											id="iframepage_${moduleInfo.moduleId}" name="iframepage_${moduleInfo.moduleId}"
											frameBorder="0" scrolling="no" width="100%" height="${moduleInfo.height-40}px"></iframe>
									</c:when>
									<c:otherwise>
										<!-- 自定义图表模块 -->
										<iframe
											src="<%=basePath%>/index/forwardChartUrl.html?moduleId=${moduleInfo.moduleId}&kpiType=${moduleInfo.kpiType}" style='z-index:-1;'
											id="iframepage_${moduleInfo.moduleId}" name="iframepage_${moduleInfo.moduleId}"
											frameBorder="0" scrolling="no" width="100%" height="${moduleInfo.height-40}px"></iframe>
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
						<div class="tabdata01"
							style="overflow: hidden; width: 300px; height: ${moduleInfo.height}px;">
							<div class="datat01">
								<div class="index_title_pd">${moduleInfo.moduleName}</div>
								<div class="box-askicon" onmousemove="showHelpDiv('${moduleInfo.moduleCode}',true);"
									onmouseout="showHelpDiv('${moduleInfo.moduleCode}',false);"
									style="cursor: pointer">
									<img src="<%=path%>/common/images/askicon5.png" />
								</div>
								<!--帮助信息开始-->
								<div id="help_${moduleInfo.moduleCode}"
									style="position:absolute;display:none; font-size: 12px;z-index:999;">
									<div style="padding: 32px 0px 0px 35px">
										<div style="width: 250px" class="tip_33">
											<p>
												<b>${moduleInfo.moduleName}：</b>
											</p>
											<p style="font-weight: normal;">${moduleInfo.remark}</p>
										</div>
									</div>
								</div>
								<!--结束信息结束-->
							</div>
							<!--标题头底色-->
							<div class="index_title_yy2"></div>
							<div id="tagr"
								style="border: 0px solid #dbe1e6; border-top: 0px; border-bottom: 0px; height: auto">
								<c:choose>
									<c:when test="${moduleInfo.moduleType eq MODULE_TYPE_SYSTEM}">
										<!-- 系统模块：自定义URL -->
										<iframe
											src="<%=basePath%>/index/redirectColumnUrl.html?moduleId=${moduleInfo.moduleId}&kpiType=${moduleInfo.kpiType}" style='z-index:-1;'
											id="iframepage_${moduleInfo.moduleId}" name="iframepage_${moduleInfo.moduleId}"
											frameBorder="0" scrolling="no" width="100%" height="${moduleInfo.height-40}px"></iframe>
									</c:when>
									<c:when test="${moduleInfo.moduleType eq MODULE_TYPE_REPORT}">
										<!-- 报表模块：报表URL -->
										<iframe
											src="<%=basePath%>/index/redirectReportUrl.html?moduleId=${moduleInfo.moduleId}&kpiType=${moduleInfo.kpiType}" style='z-index:-1;'
											id="iframepage_${moduleInfo.moduleId}" name="iframepage_${moduleInfo.moduleId}"
											frameBorder="0" scrolling="no" width="100%" height="${moduleInfo.height-40}px"></iframe>
									</c:when>
									<c:otherwise>
										<!-- 自定义图表模块 -->
										<iframe
											src="<%=basePath%>/index/forwardChartUrl.html?moduleId=${moduleInfo.moduleId}&kpiType=${moduleInfo.kpiType}" style='z-index:-1;'
											id="iframepage_${moduleInfo.moduleId}" name="iframepage_${moduleInfo.moduleId}"
											frameBorder="0" scrolling="no" width="100%" height="${moduleInfo.height-40}px"></iframe>
									</c:otherwise>
								</c:choose>
							</div>
							<div class="clear"></div>
						</div>
						<!------------------模块:${moduleInfo.moduleName} 结束啦-->
						<c:set var="isNewRow">1</c:set>
					</c:when>
					<c:otherwise>
						<!-- 底部 -->
						<!------------------接下来出场的是：模块:${moduleInfo.moduleName}，位置:${moduleInfo.position}-->
						<div class="ddy_one_home"></div>
						<!-- 模块头 -->
						<div class="index_title">
							<!-- 模块名 -->
							<div class="index_title_pd">${moduleInfo.moduleName}</div>
							<!-- ？提示 -->
							<span class="box-top-right"> <em
								style="background: url(<%=basePath%>/common/images/askicon5.png); cursor: pointer"
								class="box-askicon"
								onmouseover="showHelpDiv('${moduleInfo.moduleCode}',true);"
								onmouseout="showHelpDiv('${moduleInfo.moduleCode}',false);"></em> </span>
							<!--帮助信息开始-->
							<div id="help_${moduleInfo.moduleCode}"
								style="position:absolute;display:none; font-size: 12px;z-index:999;">
								<div style="padding: 32px 0px 0px 730px;">
									<div style="width: 250px" class="tip_33">
										<p>
											<b>${moduleInfo.moduleName}：</b>
										</p>
										<p style="font-weight: normal;">${moduleInfo.remark}</p>
									</div>
								</div>
							</div>
							<!--结束信息结束-->
						</div>
						<!-- 底色 -->
						<div class="index_title_yy"></div>
						<div class="index_report" style="height: ${moduleInfo.height}px;">
							<div id="tagr"
								style="border: 1px solid #dbe1e6; border-top: 0px; border-bottom: 0px;">
								<c:choose>
									<c:when test="${moduleInfo.moduleType eq MODULE_TYPE_SYSTEM}">
										<!-- 系统模块：自定义URL -->
										<iframe
											src="<%=basePath%>/index/redirectColumnUrl.html?moduleId=${moduleInfo.moduleId}&kpiType=${moduleInfo.kpiType}" style='z-index:-1;'
											id="iframepage_${moduleInfo.moduleId}" name="iframepage_${moduleInfo.moduleId}"
											frameBorder="0" scrolling="no" width="100%" height="${moduleInfo.height-40}px"></iframe>
									</c:when>
									<c:when test="${moduleInfo.moduleType eq MODULE_TYPE_REPORT}">
										<!-- 报表模块：报表URL(报表) -->
										<iframe
											src="<%=basePath%>/index/redirectReportUrl.html?moduleId=${moduleInfo.moduleId}&kpiType=${moduleInfo.kpiType}" style='z-index:-1;'
											id="iframepage_${moduleInfo.moduleId}" name="iframepage_${moduleInfo.moduleId}"
											frameBorder="0" scrolling="no" width="100%" height="${moduleInfo.height-40}px"></iframe>
									</c:when>
									<c:otherwise>
										<!-- 自定义图表模块 -->
										<iframe
											src="<%=basePath%>/index/forwardChartUrl.html?moduleId=${moduleInfo.moduleId}&kpiType=${moduleInfo.kpiType}" style='z-index:-1;'
											id="iframepage_${moduleInfo.moduleId}" name="iframepage_${moduleInfo.moduleId}"
											frameBorder="0" scrolling="no" width="100%" height="${moduleInfo.height-40}px"></iframe>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
						<!------------------模块:${moduleInfo.moduleName} 结束啦-->
						<c:set var="isNewRow">1</c:set>
					</c:otherwise>
				</c:choose>
				<c:if test="${isNewRow eq 1}">
					<!--第${rowCount+1}部分结束-->
					<!--空隙-->
					<div class="clear"></div>
					<c:if test="${rowCount eq 0 }">
						<!-- 第1部门需加下面的(结束)DIV -->
	</div>
	</c:if>
	<!-- 上一部分结束 -->
	<c:if test="${rowCount<fn:length(moduleInfoList)}">
		<!-- 下一部分开始 -->
		<div class="clear"></div>
		<!-- 行数加1 -->
		<c:set var="rowCount">${rowCount+1}</c:set>
	</c:if>
	</c:if>
	</c:forEach>
	</c:if>
	</div>
	<div style="width: 825px; height:auto; margin: auto; border: 3px solid #73726e; position: absolute; top: 230px; left: 534px; margin: -150px 0 0 -450px;display:none;background: #fff" id="windowOpenDiv">
		<div style="width: 820px; height: auto; float: left;">
			<h3 style="background: #d8e1e6;width: 815px;padding-left: 10px;line-height: 30px;color: #e85507;font-size: 16px;">
			  <label id="windowOpenDiv_title"><b>弹窗标题</b></label>
			   <span style="color: #333333;position: absolute;right: 20px;font-size: 12px;font-weight: normal;color: #333333;top: 0px;">
				 <a href="javascript:void(0);" onclick="Javascript:document.getElementById('windowOpenDiv').style.display='none';"><font color='red'>关闭</font></a>
			   </span>
			</h3>
			<div class="trend-sel" id="windowOpenDiv_content">
				 这是弹出正文
			</div>
		</div>
	</div>
</body>
</html>