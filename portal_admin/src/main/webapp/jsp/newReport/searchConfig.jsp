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
<title>报表条件查询</title>
<script src="<%=basePath%>/js/jquery-1.5.1.min.js"
	type="text/javascript"></script>
<link type="text/css" rel="stylesheet" href="<%=basePath%>/css/main.css" />
<link type="text/css" rel="stylesheet"
	href="<%=basePath%>/css/report.css" />
<script type="text/javascript"
	src="<%=basePath%>/js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>
<!--日期控件-->
<script type="text/javascript"
	src="<%=basePath%>/js/datePicker/WdatePicker.js"></script>
<!--月历控件-->
<script type="text/javascript"
	src="<%=basePath%>/js/datePicker/My98MonthPicker.js"></script>
</head>
<script type="text/javascript">
    window.onload=function()
    {
	    if(window.parent.document.getElementById('rsearchFrom')!=null)
	    {
	    	 window.parent.document.getElementById('rsearchFrom').height=document.getElementById("queryConditionTable").offsetHeight+"px";
	    }
    }
	//清空数据
	function resetForm()
	{
		var allInputObj=document.forms[0].elements;
		if(allInputObj!=null&&allInputObj.length>0)
		{
			for(var i=0;i<allInputObj.length;i++)
			{
				//if(allInputObj[i]==null||allInputObj[i].getAttribute("queryWhere")==null) continue;
				//if(typeof(allInputObj[i].queryWhere)=="undefined") continue;
				allInputObj[i].value="";
			}
		}
	}
	//根据一级维度查询二级维度
	function getDetailSec(id,countId){
		var selObj=document.getElementById("dimensionDetailSecId"+countId);
		 selObj.length=0;
		 $.ajax({
			    type: "POST",                                                                 
			    url:"<%=basePath%>/NewReport/getDimensionDetailSecList.html?parentId="+id+"&random="+Math.random(),
			    success: function(msg){
			   	var msg=eval(msg);
			   	selObj.options.add(new Option("== 请选择 ==",""));
			    for(var i=0;i<msg.length;i++){
			        selObj.options.add(new Option(msg[i].value,msg[i].key));
		        }  
		      }
		}); 
	}
</script>
<body style="overflow: no;" scroll="no">
	<form method="post" action="" name="searchForm" id="searchForm"
		target='result'>
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			height="auto" id="queryConditionTable">
			<tr>
				<td>
					<table width="100%" border="0" cellspacing="0" cellpadding="3"
						style="margin-top: 5px; margin-bottom: 5px">
						<c:set var="columnCount">0</c:set>
						<c:forEach var="config" items="${configList}" varStatus="status">
							<c:if test="${status.index==0}">
								<!-- 第一行 -->
								<tr>
							</c:if>
							<c:if test="${columnCount==3}">
								<!-- 结束上一行 -->
								</tr>
								<!-- 新的一行 -->
								<c:set var="columnCount">0</c:set>
								<tr>
							</c:if>
							<td width="12%" align='right'>${config.columnLabel}：</td>
							<td align='left'><c:choose>
									<c:when test="${config.controlType eq 0 }">
										<!--字符型 -->
										<input type="text" name="${config.columnCode}"
											id="config${status.count}" value="${config.defaultValue }"
											style="width: 100px" />
									</c:when>
									<c:when test="${config.controlType eq 1 }">
										<!-- 整数型 -->
										<input type="text" name="${config.columnCode}"
											id="config${status.count}" value="${config.defaultValue }"
											style="width: 100px"
											onkeyup="this.value=this.value.replace(/\D/g,'')"
											onafterpaste="this.value=this.value.replace(/\D/g,'')"
											title='只能输入整数' />
									</c:when>
									<c:when test="${config.controlType eq 2 }">
										<!-- 小数型 -->
										<input type="text" name="${config.columnCode}"
											id="config${status.count}" value="${config.defaultValue }"
											style="width: 100px"
											onkeyup="if(isNaN(value))execCommand('undo')"
											onafterpaste="if(isNaN(value))execCommand('undo')"
											title='只能输入数字' />
									</c:when>
									<c:when test="${config.controlType eq 3 }">
										<!-- 年月日 -->
										<input class="Wdate" type="text" name="${config.columnCode}"
											id="config${status.count}" value="${config.defaultValue }"
											style="width: 110px; cursor:pointer; text-align: center;"
											readonly title='请选择'
											onFocus="WdatePicker()" /> 
									</c:when>
									<c:when test="${config.controlType eq 4 }">
										<!-- 年月 -->
										<input class="Wdate" type="text" name="${config.columnCode}"
											id="config${status.count}" value="${config.defaultValue }"
											style="width: 100px; cursor: pointer; text-align: center;"
											onclick="setMonth(this,this);" readonly title='请选择' />
									</c:when>
									<c:when test="${config.controlType eq 5 }">
										<!-- 年 -->
										<select name="${config.columnCode}"
											id="config${status.count}" style='width: 100px;'>
											<option value=''>-- 请选择 --</option>
										</select>
									</c:when>
									<c:when test="${config.controlType eq 6 }">
										<!-- 一级维度（下拉项）-->
										<select name="${config.columnCode}"
											id="config${status.count}" style="width: 100px"
											field="${config.columnCode}">
											<option value="">-- 请选择 --</option>
											<c:forEach items="${config.dimensionDetailList}" var="detail"
												varStatus="v">
												<option value="${detail.key}" id="${detail.primaryKeyId}"
													<c:if test="${detail.primaryKeyId == config.defaultValue}">selected="selected"</c:if>>${detail.value}</option>
											</c:forEach>
										</select>
									</c:when>
									<c:when test="${config.controlType eq 7 }">
										<!-- 二级维度（下拉项）-->
										<select name="config${status.count}"
											id="config${status.count}" style="width: 100px"
											field="${config.columnCode}"
											onchange="getDetailSec(this.options[this.options.selectedIndex].id,${status.count});">
											<option value="">-- 请选择 --</option>
											<c:forEach items="${config.dimensionDetailList}" var="detail"
												varStatus="v">
												<option value="${detail.key}" id="${detail.primaryKeyId}"
													<c:if test="${detail.primaryKeyId == config.defaultValue}">selected="selected"</c:if>>${detail.value}</option>
											</c:forEach>
										</select>

										<select name="${config.columnCode}"
											id="dimensionDetailSecId${status.count}" style="width: 100px">
											<option value="">-- 请选择 --</option>
											<c:forEach items="${config.dimensionDetailSecList}"
												var="detail" varStatus="v">
												<option value="${detail.key}"
													<c:if test="${detail.primary_id == config.defaultValueSec}">selected="selected"</c:if>>${detail.value}</option>
											</c:forEach>
										</select>
									</c:when>
									<c:otherwise>
										<!--默认字符型 -->
										<input type="text" name="${config.columnCode}"
											id="config${status.count}" value="${config.defaultValue }"
											style="width: 100px" />
										<script language='javascript'>
											   //查询条件
											   document.getElementById("config${status.count}").setAttribute("queryWhere","${config.columnCode} ='@'");
											</script>
									</c:otherwise>
								</c:choose></td>
							<c:if test="${status.index+1==fn:length(configList)}">
								<!-- 最后一行 -->
								</tr>
							</c:if>
							<c:set var="columnCount">${columnCount+1}</c:set>
						</c:forEach>
					</table></td>
			</tr>
			<!-- 日期控件 end-->
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>
					<div align="center">
						<c:if test="${not empty configList}">
							<a class="myBtn" href="javascript:void(0);"><em>查 询</em></a>&nbsp;&nbsp;
							<a class="myBtn" href="javascript:resetForm()"><em>清 空</em></a>
						</c:if>
					</div></td>
			</tr>
		</table>
	</form>
</body>
</html>