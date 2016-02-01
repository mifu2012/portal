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
<title>报表链接配置（下钻）</title>
<script src="<%=basePath%>/js/jquery-1.5.1.min.js"
	type="text/javascript"></script>
<link type="text/css" rel="stylesheet" href="<%=basePath%>/css/main.css" />
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
	var columnFieldsHtml;	// 列字段
	var configList = new Array();	// 参数数组
	var relRptId;
	$(document).ready(function(){
		// 添加确定按钮
		var dg = frameElement.lhgDG;
		dg.addBtn('ok','确定',function(){
			var url = $("#childReportUrl").val();
			var relRptName = $("#childReportName").val();
			for ( var i = 0;  i < configList.length; i++) {
				var columnField = $("#columnFieldSelect"+i).val();
				var defaultValue = $("#defaultValue"+i).val();
				if (columnField != '') {
					url = url.replace("#"+configList[i].columnCode+"#", "#"+columnField+"#");
				} else {
					url = url.replace("#"+configList[i].columnCode+"#", defaultValue);
				}
			}
			window.parent.document.getElementById('lhgfrm_${reportId}').contentWindow.saveLinkSetting(url, relRptId, relRptName);
			dg.cancel();
		});
		// 报表字段
		columnFieldsHtml = "<option value=''></option>";
		<c:forEach items="${reportFieldList}" var="reportField" varStatus="vs">
			columnFieldsHtml += "<option value='${reportField.columnCode}'>${reportField.columnCode}</option>";
		</c:forEach>
		columnFieldsHtml += "</select></td>";
		//　选中绑定字段
		<c:forEach var="config" items="${configList}" varStatus="vs">
			$("#columnFieldSelect${vs.index}").val('${config.bandColumnField}');
		</c:forEach>
		<c:if test="${relRptId != null}">
			relRptId = ${relRptId};
		</c:if>
		<c:if test="${configListJson != null}">
			configList = eval(${configListJson});
		</c:if>
	});
	
	/**
	       改变绑定字段
	  value : 绑定字段选中的值
	  i : 表格行数
	*/
	function changeColumnField(value, i) {
		if (value == '') {
			$("#defaultValue"+i).removeAttr("disabled");
		} else {
			$("#defaultValue"+i).val("");
			$("#defaultValue"+i).attr("disabled", "disabled");
		}
	}
	
	// 选择子报表
	function selectChildReport() {
		var url = '<%=basePath%>/NewReport/selectChildReport.html?reportId=${reportId}';
		var left = $("#btnTd")[0].offsetLeft + 100;
		var selectRptDg = new $.dialog({
			title : '选择子报表',
			id : 'selectChildReport_${reportId}',
			width : 250,
			height : 400,
			left : left,
			top:0, 
			iconTitle : false,
			cover : true,
			maxBtn : true,
			xButton : true,
			resize : true,
			page : url
		});
		selectRptDg.ShowDialog();
	}
	
	// 删除子报表
	function deleteChildReport() {
		relRptId = "";
		$("#childReportUrl").val("");
		$("#childReportName").val("");
		$(".main_table_even").remove();
	}
	
	// 选中子报表，获取子报表参数
	function getReportParameters(reportId, reportName) {
		$.ajax({
	        type: "POST",                                                                 
	        url: "<%=path%>/NewReport/getReportParameters.html?&reportId="+ reportId,
			success : function(msg) {
				if (msg == "fail") {
					alert("未查到该报表！");
				} else {
					configList = eval(msg);
					if (configList.length > 0) {
						// 重新设置各参数
						relRptId = reportId;
						$("#childReportName").val(reportName);
						$("#childReportUrl").val("");
						$(".main_table_even").remove();
						var html = "";
						rowNum = configList.length;
						for ( var i = 0; i < configList.length; i++) {
							if (i != 0) {
								$("#childReportUrl").val($("#childReportUrl").val()+"&");
							}
							/*
							$("#childReportUrl").val($("#childReportUrl").val()+configList[i].columnCode
									+ "=" + configList[i].defaultValue);
							*/
							$("#childReportUrl").val($("#childReportUrl").val()+configList[i].columnCode
									+ "=#" +configList[i].columnCode+"#") ;	
							html += "<tr class='main_info main_table_even' align='center'><td>"+configList[i].columnCode+"</td>";
							html += "<td><select id='columnFieldSelect"+i+"' onchange=\"changeColumnField(this.value, "+i+")\">";
							html += columnFieldsHtml;
							html += "<td><input type='text' value='"+configList[i].defaultValue+"' id='defaultValue"+i+"'";
							/* if (configList[i].controlType == 1) {
								// 整数型
								html += "onkeyup='this.value=this.value.replace(/\D/g,'')' onafterpaste='this.value=this.value.replace(/\D/g,'')' title='只能输入整数'";
							} else if (configList[i].controlType == 2) {
								// 小数型
								html += "onkeyup='if(isNaN(value))execCommand('undo')' onafterpaste='if(isNaN(value))execCommand('undo')' title='只能输入数字'";
							} else if (configList[i].controlType == 3) {
								// 年月日
								html += "class='Wdate' style='cursor:pointer; text-align: center;' readonly title='请选择' onFocus='WdatePicker()'";
							} else if (configList[i].controlType == 4) {
								// 年月
								html += "class='Wdate' style='cursor:pointer; text-align: center;' readonly title='请选择' onclick='setMonth(this,this);'";
							} else if (configList[i].controlType == 5) {
								// 年
								
							} */
							html += " /></td></tr>";
						}
						$(".main_head").after(html);
					} else {
						alert("所选取的子报表必须含有参数，请重新选择！");
					}
				}
			  }
	   	 });
	}
</script>
<body style="overflow: no;" scroll="no">
	<table width="99%" style="text-align: left;margin-left: 5px;margin-top: 5px;" cellspacing="10">
		<tr>
			<td width="10%">子报表名：</td>
			<td width="20%"><input type="text" id="childReportName" disabled="disabled" value="${relRptName}"/></td>
			<td width="75%" id="btnTd">
				&nbsp;&nbsp;<a class="myBtn" href="javascript:selectChildReport();"><em>选择子报表</em></a>
				&nbsp;<a class="myBtn" href="javascript:deleteChildReport();"><em>删除子报表</em></a>
			</td>
		</tr>
		<tr>
			<td width="10%">报表URL：</td>
			<td width="95%" colspan="2"><input type="text" id="childReportUrl"
				value="<c:forEach var="config" items="${configList}" varStatus="vs"><c:if test="${vs.index != 0}">&</c:if>${config.columnCode}=#${config.columnCode}#</c:forEach>"
				disabled="disabled" style="width: 100%"/></td>
		</tr>
		<tr>
			<td width="100%" colspan="3">
				<table class="main_table" width="100%"  border="0" cellpadding="0" cellspacing="0">
					<tr class="main_head" align="center">
						<td width="33%">参数名</td>
						<td width="33%">绑定值</td>
						<td width="33%">默认值</td>
					</tr>
					<c:forEach var="config" items="${configList}" varStatus="vs">
						<tr class="main_info main_table_even" align="center">
							<td>${config.columnCode}</td>
							<td>
								<select id="columnFieldSelect${vs.index}" onchange="changeColumnField(this.value, ${vs.index})">
									<option value=""></option>
									<c:forEach items="${reportFieldList}" var="reportField">
										<option value='${reportField.columnCode}'>${reportField.columnCode}</option>
									</c:forEach>
								</select>
							</td>
							<td><input type="text" value="${config.defaultValue}" id="defaultValue${vs.index}"
								<c:if test="${config.bandColumnField != null}">disabled</c:if>/></td>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
		<tr>
			<td width="100%" colspan="3" ><font color="red">提示：参数绑定值为空时，方可输入默认值</font>
			</td>
		</tr>
	</table>
	<%-- <form method="post" action="" name="searchForm" id="searchForm"
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
											id="config${status.count}" value=""
											style="width: 110px; cursor:pointer; text-align: center;"
											readonly title='请选择'
											onFocus="WdatePicker()" /> 
									</c:when>
									<c:when test="${config.controlType eq 4 }">
										<!-- 年月 -->
										<input class="Wdate" type="text" name="${config.columnCode}"
											id="config${status.count}" value=""
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
							<input type="button" Name="search_ok" Value="查 询" />
							<!-- onClick="queryReportData()"  -->
								 &nbsp;&nbsp;
								<input type="button" Name="search_rest" Value="清空"
								onclick="resetForm()" />
						</c:if>
					</div></td>
			</tr>
		</table>
	</form> --%>
</body>
</html>