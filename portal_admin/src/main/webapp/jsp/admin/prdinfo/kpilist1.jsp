<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据管理平台</title>
<link type="text/css" rel="stylesheet" href="<%=path %>/css/main.css"/>
<script type="text/javascript" src="<%=path %>/js/jquery-1.5.1.min.js"></script>
</head>
<body>
<form  target="result"  name="commonForm" id="commonForm">
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="main_table">
		<tr class="main_head">
			<th><input type="checkbox" name="sltAll" id="sltAll"/></th>
			<th>序号 </th>
			<th>指标编码</th>
			<th>指标名称</th>
		</tr>
		<c:choose>
			<c:when test="${not empty kpiinfoList}">
				<c:forEach items="${kpiinfoList}" var="common" varStatus="vs">
				<tr class="main_info">
				<td><input type="checkbox" name="kpiCodes"  value="${common.kpiCode }" class="kpiCodes"/></td>
				<td>${vs.index+1}</td>
				<td>${common.kpiCode }</td>
				<td>${common.kpiName }</td>
				</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr class="main_info">
					<td colspan="7">没有相关数据</td>
				</tr>
			</c:otherwise>
		</c:choose>
	</table>
	</form>
		<iframe name="result" id="result" src="about:blank" frameborder="0" width="0" height="0"></iframe>
		<script type="text/javascript">
		var dg;
		$(document).ready(function(){
			$(".main_info:even").addClass("main_table_even");
			dg = frameElement.lhgDG;
			dg.addBtn('ok','确定',function(){
				var sign = '${sign}';
				var array = new Array();
			    $('.kpiCodes').each(function(i,v){
			    	if(v.checked == true){
			    		array.push(v.value);
			    	}
			    	});
			    //var content = dg.curWin.document.getElementById(sign).value;
			    //if(content!=''){
			    //	array.push(content);
			    //}
			    dg.curWin.document.getElementById(sign).setAttribute('value',array.toString());
			    dg.cancel();
			});
		});
		
		</script>
</body>
</html>