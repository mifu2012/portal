<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="com.infosmart.portal.pojo.DwpasCKpiInfo"%>
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
<title>${systemName}监控</title>
<link href="<%=path%>/common/css/cssz.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript"
	src="<%=path%>/common/js/jquery-1.4.3.min.js"></script>
</head>
<script type="text/javascript">
<!--
		  //切换tab
	  function switchTab(tabId,deptId) {
			var deptCount=10;
			for (i = 0; i<=10; i++) {
				if(document.getElementById("tab_" + i)==null) continue;
				document.getElementById("tab_" + i).getElementsByTagName("a")[0].className = "";
			}
			document.getElementById("tab_"+tabId).getElementsByTagName("a")[0].className = "on";
			document.getElementById("iframepage").src="<%=path%>/dwpasStDeptKpiData/loadDeptKpiData.html?deptId="+deptId;
		}
//-->
</script>
<body class="indexbody">
	<div id="tabContainer">
		<ul class="mouseover">
			<!-- 列出所有的部门 -->
			<c:set var="firstDeptId">-1</c:set>
			<c:forEach items="${dwpasStDeptNames}" var="deptKpiDateList" step="1"
				varStatus="status">
				<c:if test="${status.index==0}">
					<c:set var="firstDeptId">${deptKpiDateList.deptId}</c:set>
				</c:if>
				<c:if test="${status.index<=6}">
					<li id="tab_${status.index}"><a href="javascript:void(0);"
						<c:if test="${status.index==0}">class="on"</c:if>
						onclick="switchTab(${status.index},'${deptKpiDateList.deptId}');this.blur();return false;">
							<div class="report_list_ftpd" id="${deptKpiDateList.deptId}">${deptKpiDateList.deptName}</div>
					</a>
					</li>
				</c:if>
			</c:forEach>
		</ul>
		<div style="clear: both"></div>
		<div id="con1">
			<iframe
				src="<%=path%>/dwpasStDeptKpiData/loadDeptKpiData.html?deptId=${firstDeptId}"
				id="iframepage" name="iframepage" frameBorder="0" scrolling="no"
				width="100%"></iframe>
		</div>
	</div>
</body>
</html>
