<%@ page language="java"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.net.URLDecoder"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	response.setContentType("application/vnd.ms-excel;charset=utf-8");
	String agent = request.getHeader("User-agent");
	if (agent.indexOf("Firefox") > -1) {
		String filename = new String(request.getAttribute("filename").toString().getBytes("utf-8"), "iso-8859-1");//iso-8859-1
		response.setHeader("Content-Disposition", "attachment;filename="+filename+".xls");
	} else {
		String fileName = request.getAttribute("filename").toString();
		fileName = URLDecoder.decode(fileName, "utf-8");
	    fileName= java.net.URLEncoder.encode(fileName,"utf-8");
	    response.setHeader("Content-Disposition", "attachment; filename=\""+ new String((fileName + ".xls").getBytes(),"UTF-8") +"\"");
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>导出报表</title>
<meta name="Generator" content="EditPlus">
<meta name="Author" content="">
<meta name="Keywords" content="">
<meta name="Description" content="">
<style type="text/css">
<!--
table {
	border-collapse: collapse;
}

td {
	border-left: 1px solid #000000;
	border-top: 1px solid #000000;
	border-right: 1px solid #000000;
	border-bottom: 1px solid #000000;
	font-family: "Courier New", Courier, mono;
	font-size: 11px;
	height: 25px;
	padding: 0 12px 0 12px;
	border-collapse: collapse;
}

.tdblue {
	font-weight: bold;
	color: #3C00C6;
}
-->
</style>
<script type="text/javascript">
	window.onload = function() {
		//alert(document.getElementById("reportTable").offsetHeight);
		if (window.parent.document.getElementById('reportFrm') != null) {
			window.parent.document.getElementById('reportFrm').height = (document
					.getElementById("reportTable").offsetHeight + 50)
					+ "px";
		}
	}
</script>
<body style="background-color: #FAFBFD;">
	<!-- 生成报表模板 -->
	<!-- 行号 -->
	<c:set var="rowNum">0</c:set>
	<table border='1' bordercolor="#000000" id='reportTable' height='auto'>
		<!--表头-->
		<c:forEach items="${reportCellList}" var="reportCell"
			varStatus="vs">
			<c:if test="${vs.index==0}">
				<!-- 第一行 -->
				<tr style='background-color: #F0FFF6;'>
			</c:if>
			<c:if test="${rowNum!=reportCell.rowNum}">
				<!-- 结束上一行 -->
				</tr>
				<!-- 新的一行 -->
				<tr style='background-color: #F0FFF6;'>
			</c:if>
			<c:set var="rowNum">${reportCell.rowNum}</c:set>
			<td colspan="${reportCell.colSpan}" rowspan="${reportCell.rowSpan}"
				id="td_${reportCell.rowNum}_${reportCell.colNum}"
				style='font-size: 13px;' align='center'
				id='td_${reportCell.dataField}' width='${reportCell.width}'><b>${reportCell.content}</b>
			</td>
		</c:forEach>
		<c:if test="${fn:length(reportCellList)>0}">
			<!-- 最后一行结束符 -->
			</tr>
		</c:if>
		<c:forEach items="${reportDataList}" var="cellDataList" varStatus="vs">
			<tr>
				<c:forEach items="${cellDataList}" var="cellData" varStatus="vs1">
					<td style='font-size: 12px;
						<c:if test="${fieldList[vs1.index].formatter == 'date'}">vnd.ms-excel.numberformat:@;</c:if>
						' align='center'>
					<c:if test="${cellData == 'null'}"></c:if>
					<c:if test="${cellData != 'null'}">${cellData}</c:if>
					</td>
				</c:forEach>
			</tr>
		</c:forEach>
	</table>
</body>
</html>
