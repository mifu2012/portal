<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="com.infosmart.portal.pojo.dwmis.DwmisModuleInfo"%>
<%@ page import="java.util.Date"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	session.setAttribute("POSITION_BOTTOM",
			DwmisModuleInfo.POSITION_BOTTOM);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>唯品会瞭望塔分析系统-首页</title>
<link href="<%=path%>/common/css/lwt.css" rel="stylesheet"
	type="text/css" />
<!-- 日历控件 -->
<script src="<%=path%>/common/js/My97DatePicker/WdatePicker.js"
	type="text/javascript"></script>
<script type="text/javascript"
	src="<%=path%>/common/js/jquery-1.4.3.min.js"></script>
<style type="text/css">
<!--
body {
	background-color: #f7f7f7;
}
-->
</style>
</head>
<body class="indexbody">
	<!--头部结束-->
	<div class="index_home">
		<div  style="font-size:1px;">
			<img src="<%=path%>/dwmis/images/wph_8.gif" />
		</div>
		<div class="ddy_channel1">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				style="margin-top: -2px">
				<tr>
					<td width="65%">当前位置：首页&gt;<a href="<%=path%>/dwmisHomePage/getAllSubjects">仪表盘</a></td>
					<td width="10%" valign="top"><div align="right"
							style="margin-top: 4px">报告时间：</div></td>
					<td width="15%">
					<a href="javascript:void(0);" onclick="changeTheDate(-1);"><img style="vertical-align: middle;" align="middle"  src="<%=path%>/common/images/ddy_2.gif" width="11" height="23" border="0" /></a>
					<input id="maxDate" name="maxDate" type="text" class="ddy_index_input" readonly="readonly" value="${queryDate}"
						onclick="WdatePicker({isShowWeek:true,isShowClear:false,readOnly:true,startDate:'${queryDate}',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true,doubleCalendar:true,onpicked:function(dp){restartOpen(dp.cal.getNewDateStr());}})"  style="cursor: pointer; " />		
					<a href="javascript:void(0);" onclick="changeTheDate(1);"><img style="vertical-align: middle;" align="middle"  src="<%=path%>/common/images/ddy_1.gif" width="11" height="23" border="0" /></a>
					
					</td>
				</tr>
			</table>
		</div>
		<div id="tabContainer">
			<ul class="mouseover">
				<!-- 列出所有的主题-->
				<c:set var="firstSubjectId">${firstSubjectId}</c:set>
				<c:forEach items="${subjectInfoList}" var="subjectInfo" step="1"
					varStatus="status">
					<c:if test="${status.index==0}">
						<c:set var="firstDeptId">${subjectInfo.subjectId}</c:set>
					</c:if>
					<c:if test="${status.index<=6}">
						<li id="tab_${status.index}"><a href="javascript:void(0);"
							<c:if test="${status.index==0}">class="on"</c:if>
								onclick="switchTab(${status.index},'${subjectInfo.subjectId}');this.blur();return false;">
								<div class="report_list_ftpd" id="${subjectInfo.subjectId}">${subjectInfo.subjectName}</div>
						</a></li>
					</c:if>
				</c:forEach>
			</ul>

			<div style="clear: both"></div>
			<div id="con1">
				<iframe
					src="<%=path%>/dwmisHomePage/gotoHomePage?subjectId=${firstSubjectId}&queryDate=${queryDate}"
					id="iframepage" name="iframepage" frameBorder="0" scrolling="no"
					width="100%" onLoad="iFrameHeight() " onreadystatechange="showLoading(this);"></iframe>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript" language="javascript">   

//进度条
window.onload = function() 
{ 
	var loadingDiv=window.parent.document.getElementById("loading_wait_Div");
	if(loadingDiv!=null) loadingDiv.style.display="block";
}
	function iFrameHeight() {   
		var ifm= document.getElementById("iframepage");   
		var subWeb = document.frames ? document.frames["iframepage"].document : ifm.contentDocument;   
		if(ifm != null && subWeb != null) {
			document.getElementById("iframepage").height =subWeb.body.scrollHeight
			//自适应
			var iframeContentHeight = document.body.scrollHeight;
			parent.changeIframeSize(iframeContentHeight);
		}   
	}
	
	function switchTab(tabId,subjectId) {
		var sujectCount=10;
		for (i = 0; i<=10; i++) {
			if(document.getElementById("tab_" + i)==null) continue;
			document.getElementById("tab_" + i).getElementsByTagName("a")[0].className = "";
		}
		var loadingDiv=window.parent.document.getElementById("loading_wait_Div");
		if(loadingDiv!=null) loadingDiv.style.display="block";
		document.getElementById("tab_"+tabId).getElementsByTagName("a")[0].className = "on";
		document.getElementById("iframepage").src="<%=path%>/dwmisHomePage/gotoHomePage?subjectId="+subjectId;
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
	
	function restartOpen(queryDate){
		location.href="<%=path%>/dwmisHomePage/getAllSubjects.html?1=1&queryDate="+queryDate;
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
				  location.href="<%=path%>/dwmisHomePage/getAllSubjects.html?&queryDate="+changedDate;
		  }
	}
</script>
<div
		style="width: 825px; height: auto; margin: auto; border: 3px solid #73726e; position: absolute; top: 230px; left: 534px; margin: -150px 0 0 -450px; display: none; background: #fff"
		id="detailEventDiv">
		<div style="width: 820px; height: auto; float: left;">
			<h3
				style="background: #d8e1e6; width: 815px; padding-left: 10px; line-height: 30px; color: #e85507; font-size: 16px;">
				<label id="windowOpenDiv_title"><b>大事记</b> </label> <span
					style="color: #333333; position: absolute; right: 20px; font-size: 12px; font-weight: normal; color: #333333; top: 0px;">
					<a href="javascript:void(0);"
					onclick="Javascript:document.getElementById('detailEventDiv').style.display='none';"><font
						color='red'>关闭</font> </a> </span>
			</h3>
			<div class="trend-sel" id="trend-sel">
				<iframe src="" id="eventIframe" style="width: 100%;height:100%; border: none;"
					scrolling="no" frameborder="0"> </iframe>
			</div>
		</div>
	</div>
</html>
