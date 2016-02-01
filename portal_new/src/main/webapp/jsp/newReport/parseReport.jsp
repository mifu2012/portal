<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="java.util.Date"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>报表预览--NEW</title>
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
    window.onload=function(){
	    if(window.parent.document.getElementById('reportFrm')!=null)
	    {
	    	 window.parent.document.getElementById('reportFrm').height=(document.getElementById("reportTable").offsetHeight+50)+"px";
	    } 
    }
</script>
<body style="background-color: #FAFBFD;">
	<!-- 生成报表模板 -->
	<!-- 行号 -->
	<c:set var="rowNum">0</c:set>
	<table border='1' bordercolor="#000000" id='reportTable' height='auto'>
	    <!--表头-->
		<c:forEach items="${reportDefine.reportCellList}" var="reportCell" varStatus="vs">
			<c:if test="${vs.index==0}">
				<!-- 第一行 -->
				<tr style='background-color:#F0FFF6;'>
			</c:if>
			<c:if test="${rowNum!=reportCell.rowNum}">
				<!-- 结束上一行 -->
				</tr>
				<!-- 新的一行 -->
				<tr style='background-color:#F0FFF6;'>
			</c:if>
			<c:set var="rowNum">${reportCell.rowNum}</c:set>
			<td colspan="${reportCell.colSpan}" rowspan="${reportCell.rowSpan}"
				id="td_${reportCell.rowNum}_${reportCell.colNum}" style='font-size:13px;' align='center' id='td_${reportCell.dataField}' width='${reportCell.width}'><b>${reportCell.content}</b></td>
		</c:forEach>
		<c:if test="${fn:length(reportCellList)>0}">
			<!-- 最后一行结束符 -->
			</tr>
		</c:if>
		<!--下面是分组头-->
		<c:if test="${reportDefine.grouping==1}">
			<c:set var="groupSumFlag">0</c:set>
			<tr style='background-color:#F2FFD8;display:;' id='tr_group_sum_split'>
			   <td colspan="${fn:length(fieldCellList)}" style='font-size:12px;'>&nbsp;<img src='<%=basePath%>/js/zTree/img/total_flag1.gif'>&nbsp;分组头</td>
			</tr>
			<tr id='tr_group_sum_content' style='display:;'>
				<td colspan="${fn:length(fieldCellList)}" style='font-size:12px;'>&nbsp;按〈${reportDefine.groupingField}〉分组</td>
			</tr>
		</c:if>
		<!--下面是数据列-->
		<c:if test="${fn:length(fieldCellList)>=1}">
			<tr style='background-color:#F2FFD8;'>
			   <td colspan="${fn:length(fieldCellList)}" style='font-size:12px;'>&nbsp;<img src='<%=basePath%>/js/zTree/img/total_flag1.gif'>&nbsp;内容行</td>
			</tr>
		</c:if>
		<tr>
			<c:forEach items="${fieldCellList}" var="reportCell" varStatus="vs">
			      <td style='font-size:12px;' align='center'>#list[${reportCell.dataField}]</td>
			</c:forEach>
        </tr>
        <!--下面是分组头-->
        <c:if test="${reportDefine.grouping==1}">
			<c:set var="groupSumFlag">0</c:set>
			<tr style='background-color:#F2FFD8;display:;' id='tr_group_sum_split'>
			   <td colspan="${fn:length(fieldCellList)}" style='font-size:12px;'>&nbsp;<img src='<%=basePath%>/js/zTree/img/total_flag1.gif'>&nbsp;分组尾</td>
			</tr>
			<tr id='tr_group_sum_content' style='display:;'>
				<c:forEach items="${fieldCellList}" var="reportCell" varStatus="vs">
				   <c:if test="${reportCell.groupSum==1}">
				      <c:if test="${reportCell.groupSummaryType=='sum'}">
					      <td style='font-size:12px;' align='center'>#GroupSum[${reportCell.dataField}]</td>
					  </c:if>
					  <c:if test="${reportCell.groupSummaryType=='count'}">
					      <td style='font-size:12px;' align='center'>#GroupCount[${reportCell.dataField}]</td>
					  </c:if>
					  <c:if test="${reportCell.groupSummaryType=='avg'}">
					      <td style='font-size:12px;' align='center'>#GroupAvg[${reportCell.dataField}]</td>
					  </c:if>
					  <c:if test="${reportCell.groupSummaryType=='min'}">
					      <td style='font-size:12px;' align='center'>#GroupMin[${reportCell.dataField}]</td>
					  </c:if>
					  <c:if test="${reportCell.groupSummaryType=='max'}">
					      <td style='font-size:12px;' align='center'>#GroupMax[${reportCell.dataField}]</td>
					  </c:if>
					  <!--存在分组行-->
					  <c:set var="groupSumFlag">1</c:set>
				   </c:if>
				   <c:if test="${reportCell.groupSum==0}">
					  <td>&nbsp;</td>
				   </c:if>
				</c:forEach>
			</tr>
		</c:if>
		<!--下面是合计列-->
		<c:if test="${reportDefine.summary==1}">
			<c:set var="pageSumFlag">0</c:set>
			<tr style='background-color:#F2FFD8;display:;' id='tr_page_sum_split'>
			   <td colspan="${fn:length(fieldCellList)}" style='font-size:12px;'>&nbsp;<img src='<%=basePath%>/js/zTree/img/total_flag1.gif'>&nbsp;合计行</td>
			</tr>
			<tr id='tr_page_sum_content' style='display:;'>
				<c:forEach items="${fieldCellList}" var="reportCell" varStatus="vs">
					<c:if test="${reportCell.pageSum==1}">
					  <c:if test="${reportCell.pageSummaryType=='sum'}">
					      <td style='font-size:12px;' align='center'>#Sum[${reportCell.dataField}]</td>
					  </c:if>
					  <c:if test="${reportCell.pageSummaryType=='count'}">
					      <td style='font-size:12px;' align='center'>#Count[${reportCell.dataField}]</td>
					  </c:if>
					  <c:if test="${reportCell.pageSummaryType=='avg'}">
					      <td style='font-size:12px;' align='center'>#Avg[${reportCell.dataField}]</td>
					  </c:if>
					  <c:if test="${reportCell.pageSummaryType=='min'}">
					      <td style='font-size:12px;' align='center'>#Min[${reportCell.dataField}]</td>
					  </c:if>
					  <c:if test="${reportCell.pageSummaryType=='max'}">
					      <td style='font-size:12px;' align='center'>#Max[${reportCell.dataField}]</td>
					  </c:if>
					  <!--存在合计行-->
					  <c:set var="pageSumFlag">1</c:set>
					</c:if>
				   <c:if test="${reportCell.pageSum==0}">
					  <td>&nbsp;</td>
				   </c:if>
				</c:forEach>
			</tr>
        </c:if>
	</table>
</body>
</html>
