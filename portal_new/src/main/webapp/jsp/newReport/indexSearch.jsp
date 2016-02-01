<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>搜索列表</title>
<!-- <link href="../common/css/css.css" rel="stylesheet" type="text/css" /> -->
<link type="text/css" rel="stylesheet" href="../common/css/main.css" />
<script type="text/javascript"
	src="<%=basePath%>/common/js/jquery-1.4.3.min.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/public.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$(".main_info:even").addClass("main_table_even");
		var heigth=0;
		if(navigator.userAgent.indexOf("MSIE")>0){
			height=document.body.scrollHeight;
		}else{
			height=document.documentElement.scrollHeight;
		}
		var newHeight=document.getElementById("reportDataTable").offsetHeight;
	    if(navigator.userAgent.indexOf("MSIE 7.0")!=-1)
	    {
	    	newHeight+=180;
	    }else
	    {
	    	newHeight+=130;
	    }
		parent.changeIframeSize(newHeight);
		var loadingDiv=document.getElementById("loading_wait_Div");
		if(loadingDiv==null) loadingDiv=window.parent.document.getElementById("loading_wait_Div");
		if(loadingDiv==null) loadingDiv=window.parent.parent.document.getElementById("loading_wait_Div");
		if(loadingDiv!=null)
		{
			loadingDiv.style.display="none";
		}
		document.getElementById("reportName").focus();
	});
	function queryForm(e)
	{
		var keynum;
		if(window.event) {// IE
		  keynum = e.keyCode;
		}
		else if(e.which) {// Netscape/Firefox/Opera
		  keynum = e.which;
		}
		if (keynum == 13) {
			search();
		}
	}
	
	//select_Report
	function search() {
		var states = new Array();
		var reportName = document.getElementById("reportName").value.Trim();
		var id = document.getElementsByName("selfApply");
		for ( var i = 0; i < id.length; i++) {
			if (id[i].checked == true) {
					states.push(id[i].value);
			}
		}
		location.href = encodeURI("<%=basePath%>/selfApply/indexSearch?&reportName="+ reportName + "&state="+states.toString());
	}
	//隐藏或显示条件查询模块 
	String.prototype.Trim = function() 
	{ 
	  return this.replace(/(^\s*)|(\s*$)/g, ""); 
	}
	
	function apply(reportId,reportName) {
		if(confirm('您确认申请吗？')){
			var states = new Array();
			var id = document.getElementsByName("selfApply");
			for ( var i = 0; i < id.length; i++) {
				if (id[i].checked == true) {
						states.push(id[i].value);
				}
			}
			location.href = '<%=basePath%>/selfApply/apply?&reportId='+reportId+'&reportName='+ reportName + '&state=' + states.toString();
		}
		
	}
	//显示或隐藏
	function showOrHide(crtTr)
	{
		if(crtTr==null) return;
		var openFlag=crtTr.getAttribute("openFlag");
		if(openFlag==null||openFlag.length==0) openFlag="1";
		if(openFlag=="1")
		{
		   //隐藏
           crtTr.setAttribute("openFlag","0");
		   document.getElementById("img_"+crtTr.id).src="<%=basePath%>/common/js/zTree/img/plus_noLine.gif";
		}else
		{
           crtTr.setAttribute("openFlag","1");
		   document.getElementById("img_"+crtTr.id).src="<%=basePath%>/common/js/zTree/img/minus_noLine.gif";
		}
        var reportDataTable=document.getElementById("reportDataTable");
		if(reportDataTable==null||reportDataTable.rows.length==1) return;
		for(var i=1;i<reportDataTable.rows.length;i++)
		{
           if(reportDataTable.rows[i].getAttribute("parentId")==crtTr.id)
			{
			   if(openFlag=="1")
				{
				    //隐藏
					reportDataTable.rows[i].style.display="none";
				}else
				{
					//显示
					reportDataTable.rows[i].style.display="";
				}
			}
		}
	}
</script>
</head>
<body class="indexbody"  id="mybody">
	<!--头部结束-->
	<!-- <div class="index_home"> -->
	<!-- <div>
		<img src="../common/images/wph_8.gif" />
	</div> -->
	<!-- <div class="index_title">

			<table width="100%" height="100%" border="0" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="87%"><div class="index_title_pd">搜索列表</div></td>
				</tr>
			</table>
		</div> -->
	<div class="search_div">	
	    <!--
		<form action="" name="searchForm" id="searchForm" method="post">
		-->
			<fieldset style="height: 90px;" >
	        <legend style='font-size:12px;' align="left">查询条件</legend>
	        <table width="100%" align="center" >
	        <tr align="center">
	        <td width="100%"><font>报表名称：</font>
				<input style="width: 200px;" type="text" name="reportName" id="reportName" onkeyup="queryForm(event);" value="${reportName}"/> 
				<a href="javascript:search();" class="myBtn" ><em>查询</em></a>
				</td>
	        </tr>
	        <tr align="center">
	        <td width="100%">
	        <font>状态：</font>
				<label for="selfApply_1" style="cursor: pointer;">未申请</label> <input type="checkbox" id="selfApply_1"
					name="selfApply" value="1" onclick="search();" />
				<label for="selfApply_2" style="cursor: pointer;"><font color="blue">申请中</font></label> <input type="checkbox" id="selfApply_2"
					name="selfApply" value="2" onclick="search();" />
				<label for="selfApply_3" style="cursor: pointer;"><font color="#18b301">申请通过</font></label> <input type="checkbox"
					id="selfApply_3" name="selfApply" value="3"
					onclick="search();" />
				<label for="selfApply_4" style="cursor: pointer;"><font color="#ff0000">申请驳回</font></label> <input type="checkbox"
					id="selfApply_4" name="selfApply" value="4"
					onclick="search();" />
	        </td>
	        </tr>
	        </table>
	       <%--  <div align="center">
			<div align="center" style="">
				<font>报表名称：</font>
				<input style="width: 200px;" type="text" name="reportName" id="reportName" onkeyup="queryForm(event);" value="${reportName}"/> 
				<a href="javascript:search();" class="myBtn" /><em>查询</em></a>
			</div>
			<div align="center">
				<font>状态：</font>
				<th>未申请</th> <input type="checkbox" id="selfApply_1"
					name="selfApply" value="1" onclick="search();" />
				<th><font color="blue">申请中</font></th> <input type="checkbox" id="selfApply_2"
					name="selfApply" value="2" onclick="search();" />
				<th><font color="18b301">申请通过</font></th> <input type="checkbox"
					id="selfApply_3" name="selfApply" value="3"
					onclick="search();" />
				<th><font color="ff0000">申请驳回</font></th> <input type="checkbox"
					id="selfApply_4" name="selfApply" value="4"
					onclick="search();" />
			</div>
			</div> --%>
			</fieldset>
			<!--
		</form>
		-->
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="main_table" id="reportDataTable">
			<tr class="" style="background-color:#E2E2E2 ;"><!-- main_head -->
				<th align="center" width="10%" height="40">序号</th>
				<th align="left" width="25%">名称</th>
				<th align="center" width="15%">申请状态</th>
				<th align="center" width="20%">操作</th>
				<th align="left" width="30%">备注</th>
			</tr>
			<!-- 文件夹 -->
			<c:set var="tempRptFloldId">-1</c:set>
			<c:choose>
				<c:when test="${not empty reportList}">
					<c:forEach items="${reportList}" var="report" varStatus="vs">
						<c:if test="${tempRptFloldId!=report.parentId}">
						  <!-- 新的报表文件夹 -->
						  <tr style="background-color:#EDEDED;cursor:pointer;" onclick="showOrHide(this);" id="${report.parentId}">
							 <td colspan="5" style="border-top:1px solid #C4C4C4;border-bottom:1px solid #C9C9C9;">&nbsp;&nbsp;<img src="<%=basePath%>/common/js/zTree/img/minus_noLine.gif" id="img_${report.parentId}">&nbsp;<b>${report.parentName}</b></td>
						  </tr>
						</c:if>
						<c:set var="tempRptFloldId">${report.parentId}</c:set>
						<!-- 报表 -->
						<tr class="main_info"
							onmouseover="this.style.backgroundColor='#D2E9FF';"
							onmouseout="this.style.backgroundColor='';" id="tr_${report.reportId}">
							<td width="10%">${vs.index+1}</td>
							<td width="25%" align="left">${report.reportName}</td>
							<td width="15%" align="center">
								<c:if test="${report.selfApply.state eq 1}">未申请</c:if> 
								<c:if test="${report.selfApply.state eq 2}">申请中</c:if> 
								<c:if test="${report.selfApply.state eq 3}">
									<font color="18b301">申请通过</font>
								</c:if> 
								<c:if test="${report.selfApply.state eq 4}">
									<font color="ff0000">申请驳回</font>
								</c:if>
							</td>
							<td width="20%" align="center"><c:choose>
									<c:when test="${report.selfApply.state eq 1}">
										<a href="javascript:void(0);"
											onclick="apply(${report.reportId},'${report.reportName }');"><img
											src="../common/images/wsq.gif" width="16" height="16"
											border="0" title="去申请" />&nbsp;我要申请</a>
									</c:when>
									<c:when test="${report.selfApply.state eq 2}">
										<img src="../common/images/sqz.gif" width="16" height="16"
											border="0" title="申请中" />&nbsp;申请中...
									</c:when>
									<c:when test="${report.selfApply.state eq 3}">
										<img src="../common/images/sqtg.gif" width="16" height="16"
											border="0" title="申请通过" />&nbsp;申请通过
									</c:when>
									<c:when test="${report.selfApply.state eq 4}">
										<img src="../common/images/sqbh.gif" width="16" height="16"
											border="0" title="申请驳回" />&nbsp;申请驳回
									</c:when>
								</c:choose></td>
							<td width="30%" align="center">${report.remark}</td>
						</tr>
						<script language='javascript'>
						   document.getElementById("tr_${report.reportId}").setAttribute("parentId","${report.parentId}");
						</script>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr class="main_info" style="height: 430px">
						<td colspan="5" align='center'><font color="red">没有相关数据</font></td>
					</tr>
				</c:otherwise>
			</c:choose>
		</table>
		<%-- <div class="page_and_btn">${report.page.pageStr}</div> --%>
	</div>
	<!--分页代码开始-->
	<%-- <div class="page_link">
			<div>
				<div align="center">
					<strong>${report.page.pageStr}</strong>
				</div>
			</div>
		</div> --%>
	<!--分页代码结束-->
	<!-- </div> -->
</body>
</html>
<script language='javascript'>
	<c:forEach items="${stateList}" var="state" varStatus="vs">
	if (document.getElementById("selfApply_${state}") != null) {
		document.getElementById("selfApply_${state}").checked = true;
	}
	</c:forEach>
</script>