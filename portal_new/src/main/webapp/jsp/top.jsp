<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.infosmart.portal.pojo.DwpasCKpiInfo"%>
<%@ page import="com.infosmart.portal.pojo.User"%>
<%@ page import="com.infosmart.portal.util.Const"%>
<%@ page import="com.infosmart.portal.util.Constants"%>
<%@ page import="com.infosmart.portal.util.StringDes"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	String homePage = request.getContextPath();
	Object userObj = session.getAttribute(Constants.SESSION_USER_INFO);
	User user = null;
	if (userObj == null) {
		//response.sendRedirect(homePage);
	} else {
		user = (User) userObj;
	}
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${systemName}</title>
<link href="<%=homePage%>/common/css/css.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="<%=homePage%>/common/js/public.js"></script>
<script type='text/javascript' src='<%=homePage%>/common/js/dropdown.js'></script>
<script type="text/javascript" src="<%=homePage%>/common/js/jquery-1.4.3.min.js"></script>
<script type="text/javascript" src="<%=homePage%>/common/js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>
<script type="text/javascript">
function updatePassword(){
	var dg = new $.dialog({
		title:'修改密码',
		id:'passWord_update',
		width:320,
		height:220,
		iconTitle:false,
		cover:true,
		maxBtn:true,
		xButton:true,
		resize:false,
		page:'updatePassWord.html'
		});
	dg.ShowDialog();
}
function indexSearch(){
	var state="1,2,3,4";
	var reportName=document.getElementById('reportName').value.Trim();
	alert(reportName);
	document.getElementById('center_iframe').src='<%=homePage%>/selfApply/indexSearch?&reportName='+reportName+'&state='+state;
}
//隐藏或显示条件查询模块 
String.prototype.Trim = function() 
{ 
  return this.replace(/(^\s*)|(\s*$)/g, ""); 
}
function changeTempLate(obj){
	var dwpasTemplateId = obj.value;
	location.href='<%=homePage%>/UserLogin/getDwpasTemplateId.html?dwpasTemplateId='+dwpasTemplateId+'&type=1DF89CA85F489C29';
}
</script>
</head>
<body class="indexbody">
	<div class="index_top" id="indexTopDiv">
    <!--${type} 1:了望塔：2：经纬仪：3：报表-->
    <c:if test="${type==2}">
		<div style="position: absolute; top: 85px;padding: 0px 0px 0px 0px;left:980px;border:0px solid;" id="templateListDiv">
		  <font color='#ffffff'>模板：</font>
		  <select name="theDwpasTempLateId" id="theDwpasTempLateId" onchange="changeTempLate(this);" style="width: 100px;">
		  <c:forEach items="${templateInfoList }" var="templateInfo">
			<option value="${templateInfo.templateId }" <c:if test="${templateInfo.templateId eq dwpasTemplateId}">selected="selected" </c:if> >${templateInfo.templateName }</option>
		 </c:forEach>
		  </select>
		</div>
	</c:if>	
		<!-- 报表查询框 -->
		<c:if test="${type==3}">
			<div style="position: relative;display: none;">
				<div id="wrap_top"
					style="position: absolute; padding: 90px 0px 0px 868px;">
					<div id="sider_top">
						
					</div>
				</div>
			</div>
		</c:if>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
<%-- 				<td width="12%"><img src="<%=homePage%>/common/images/wph_logo.gif" width="124" height="68" /> --%>
				<td width="200"><img src="<%=homePage%>/common/img_500w/500w_logo.png" width="124" height="68" /></td>
				<td width="500" valign="bottom" class="top_logo">
					<table width="100%" border="0" cellspacing="0" cellpadding="0"  style="position: relative;bottom: 10px;">
						<tr><td width="310" height="26"><b style="color: #666" > <%=user.getUserName()%>，欢迎您登录经营分析系统！</b></td>
							<td width="5" height="26">
									<img src="<%=homePage%>/common/images/wph_6.gif"/>
							</td>
							<td width="70"><a href="javascript:void(0);"
								onclick="updatePassword();">修改密码</a>
							</td>
							<td width="5">
								<div align="right">
									<img src="<%=homePage%>/common/images/wph_5.gif"/>
								</div>
							</td>
							<td width="30"><div align="right">
									<a
										href="javascript:if(confirm('您确认要退出吗？'))location.href='<%=homePage%>/quit.html'">退出</a>
								</div>
							</td>
							<td></td>
						</tr>
					</table>
				</td>
				<td align="right"  valign="top">
					<% 
				      if(!"0".equals(user.getDwmisRights())||!"0".equals(user.getRole().getDwmisRights())){
			        %>
					<embed src="<%=homePage%>/common/swf/lwt_2.swf" quality="high" wmode="transparent" 
					bgcolor="#ffffff" width="90" height="90" align="middle" allowScriptAccess="sameDomain" 
					type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" />
			        <%}
					  if(!"0".equals(user.getDwpasRights())||!"0".equals(user.getRole().getDwpasRights())){
					%>
					<embed src="<%=homePage%>/common/swf/lwt_3.swf" quality="high" wmode="transparent" 
					bgcolor="#ffffff" width="90" height="90" align="middle" allowScriptAccess="sameDomain" 
					type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" />
					<%} 
					  if(!"0".equals(user.getReportRights())||!"0".equals(user.getRole().getReportRights())){
					%>
					<!-- 报表暂时屏蔽 -->
					
					<embed src="<%=homePage%>/common/swf/lwt_4.swf" quality="high" wmode="transparent" 
					bgcolor="#ffffff" width="90" height="90" align="middle" allowScriptAccess="sameDomain" 
					type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" />
					
					<%}%>
				</td>
			</tr>
		</table>
	</div>
	<div class="index_nav">
		<div class="index_nav_center">
			<!--下拉导航开始-->
			<div id="nav">
				<ul id="navMenu">
					<c:choose>
						<c:when test="${type==1}">
						<!--<li><a href='<%=homePage%>/dwmis/index.html'-->
							<li><a href='<%=homePage%>/dwmisHomePage/getAllSubjects.html'
								target="center_iframe" ><input name="nav_lwt" type="hidden" value="panel" /><div style="width: 100%;height: 100%" id="lwt_panel" onclick="showLoading('panel',1);" >仪表盘</div></a>
							</li>
							<li><input name="nav_lwt" type="hidden" value="monitor" /><a
								href='<%=homePage%>/dwmisDeptKpiData/showDeptKpiData.html'
								target="center_iframe" ><div style="width: 100%;height: 100%" id="lwt_monitor" onclick="showLoading('monitor',1);" >指标监控</div></a>
							</li>
							<li><input name="nav_lwt" type="hidden" value="bigEvent" /><a href='<%=homePage%>/MisEvent/showEventMessage.html'
								target="center_iframe" ><div style="width: 100%;height: 100%" id="lwt_bigEvent" onclick="showLoading('bigEvent',1);" >大事记</div></a>
							</li>
						</c:when>
						<c:when test="${type==3}">
							<li><a href='<%=homePage%>/selfApply/reportMenuList.html'
								target="center_iframe"><input name="nav_rpt" type="hidden" value="myRpt" /><div style="width: 100%;height: 100%" id="rpt_myRpt" onclick="showLoading('myRpt',3);">我的报表</div></a>
							</li>
							<c:forEach items="${reportMenuList}" var="reportMenu">
								<li><a
									href='<%=homePage%>/NewReport/${reportMenu.reportId}.html?reportMenuName=${reportMenu.reportName}'
									rel='${reportMenu.reportId}' target="center_iframe"
									><input name="nav_rpt" type="hidden" value="${reportMenu.reportId}" /><div style="width: 100%;height: 100%" id="rpt_${reportMenu.reportId}" onclick="showLoading('${reportMenu.reportId}',3);" >${reportMenu.reportName}</div></a>
								</li>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<c:forEach items="${systemMenuList}" var="systemMenu">
								<!-- 如果是一级子菜单,没有父级菜单ID -->
								<c:choose>
									<c:when test="${not empty systemMenu.menuPid}"></c:when>
									<c:when test="${systemMenu.menuType!=type}"></c:when>
									<c:otherwise>
										<li><input name="nav_li" type="hidden" value="${systemMenu.menuId }" /><a class="last"
											href="<%=homePage%>${systemMenu.menuUrl}&menuId=${systemMenu.menuId}"
											rel='${systemMenu.menuId}' target="center_iframe"><div id="li_${systemMenu.menuId}" style="width: 100%;height: 100%;border:0px solid red;z-index:999;" onclick="showLoading('${systemMenu.menuId}',2);" >${systemMenu.menuName}</div></a>
										</li>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</ul>
			</div>
			<c:set var="subMenuCount">0</c:set>
			<c:set var="tmpMenuPid">-1</c:set>
			<c:forEach items="${systemMenuList}" var="subSystemMenu"
				varStatus="status">
				<c:if test="${not empty subSystemMenu.menuPid}">
					<c:if test="${subMenuCount==0}">
						<ul id="${subSystemMenu.menuPid}" class="dropMenu">
					</c:if>
					<c:if test="${subMenuCount!=0}">
						<c:if test="${tmpMenuPid != subSystemMenu.menuPid}">
							</ul>
							<ul id="${subSystemMenu.menuPid}" class="dropMenu">
								<c:set var="subMenuCount">0</c:set>
						</c:if>
					</c:if>
					<!-- 二级菜单  -->
					<li><a
						href="<%=homePage%>${subSystemMenu.menuUrl}&menuId=${subSystemMenu.menuId}"
						rel='${subSystemMenu.menuId}' target="center_iframe"
						onclick="showLoading('${subSystemMenu.menuPid}',2);">${subSystemMenu.menuName}</a>
					</li>
					<c:set var="subMenuCount">${subMenuCount+1}</c:set>
				</c:if>
				<c:set var="tmpMenuPid">${subSystemMenu.menuPid}</c:set>
			</c:forEach>
			</ul>
			<script type="text/javascript">
				cssdropdown.startchrome("navMenu")
			</script>
			<!--下拉导航结束-->
		</div>
	</div>
</body>
</html>
<script type="text/javascript">
//显示进度条
function showLoading(id,val)
{
	//瞭望塔
	if(val==1){
		var lwtList = document.getElementsByName("nav_lwt");
		for(var i=0;i<lwtList.length; i++)
		{
			if(id==lwtList[i].value)
			{
				document.getElementById("lwt_"+lwtList[i].value).style.backgroundImage="url(<%=homePage%>/common/images/top_click.gif)";
			}else{
				document.getElementById("lwt_"+lwtList[i].value).style.backgroundImage="";
			}
		}
	}else if(val==2){
		//经纬仪
		var liList = document.getElementsByName("nav_li");
		for(var i=0; i<liList.length; i++){
			if(liList[i].value==id){
				document.getElementById("li_"+liList[i].value).style.backgroundImage="url(<%=homePage%>/common/images/top_click.gif)";
			}else{
				document.getElementById("li_"+liList[i].value).style.backgroundImage="";
			}
		}
	}else if(val==3){
		//报表
		var rptList = document.getElementsByName("nav_rpt");
		for(var i=0;i<rptList.length; i++)
		{
			if(id==rptList[i].value)
			{
				document.getElementById("rpt_"+rptList[i].value).style.backgroundImage="url(<%=homePage%>/common/images/top_click.gif)";
			}else{
				document.getElementById("rpt_"+rptList[i].value).style.backgroundImage="";
			}
		}
	}

	
	var loadingDiv=document.getElementById("loading_wait_Div");
	if(loadingDiv!=null) loadingDiv.style.display="";
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
	var iframe = document.getElementById('center_iframe');
	if (iframe.attachEvent) {
		iframe.attachEvent("onload", function() {
			loadingDiv.style.display = "none";
		});
	} else {
		iframe.onload = function() {
			loadingDiv.style.display = "none";
		};
	}
	
	//当前系统LOGO不出现
	if (document.getElementById("label_${type}") != null) {
		//document.getElementById("label_${type}").style.display="none";
		document.title = document.title + '_'+ document.getElementById("label_${type}").title;
	}	
}
var templateListDiv=document.getElementById("templateListDiv");
if(templateListDiv!=null)
{
   var indexTopDiv=document.getElementById("indexTopDiv");
   templateListDiv.style.left=indexTopDiv.offsetLeft+indexTopDiv.offsetWidth-templateListDiv.offsetWidth-20+"px";
}
</script>