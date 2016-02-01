<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>数据管理平台</title>
<link type="text/css" rel="stylesheet" href="<%=path%>/css/main.css" /> 
<script type="text/javascript" src="<%=path%>/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>
<script type="text/javascript"> 
$(document).ready(function(){
    $(":input").css("height","20px");
});
</script>
</head>
<body>
	<div style="left: 10%; top: 0%; position: absolute;">
		<form action="<%=path%>/reportChart/saveTableFiled.html" method="post"
			target="result" id="iform" name="iform">
			<input type="hidden" name="left" value="${left}" /> <input
				type="hidden" name="top" value="${top}" /> <input type="hidden"
				name="chartId" id="chartId" value="${reportChartFiled.chartId}" />
			<c:if test="${reportChartFiled.chartType eq '1'}">
				<img src="<%=path%>/images/pie.png"
					style='width: 100px; height: 100px;' id='imgDiv'>
			</c:if>
			<c:if test="${reportChartFiled.chartType eq '2'}">
				<img src="<%=path%>/images/spline.jpg"
					style='width: 320px; height: 160px;' id='imgDiv'>
			</c:if>
			<c:if test="${reportChartFiled.chartType eq '3'}">
				<img src="<%=path%>/images/column.jpg"
					style='width: 320px; height: 160px;' id='imgDiv'>
			</c:if>	
			<c:if test="${reportChartFiled.chartType eq '4'}">
				<img src="<%=path%>/images/line.jpg"
					style='width: 320px; height: 160px;' id='imgDiv'>
			</c:if>	
			<c:if test="${reportChartFiled.chartType eq '5'}">
				<img src="<%=path%>/images/column_line.jpg"
					style='width: 320px; height: 160px;' id='imgDiv'>
			</c:if>										
			<br /> <br />
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align='right'>图表类型：</td>
					<td align="left"><select name="chartType" id="chartType"
						style="width: 206px; height: 25px" disabled="disabled"
						onchange='this.value=${reportChartFiled.chartType}'>
							<option value="1"
								<c:if test="${reportChartFiled.chartType eq '1'}"> selected="selected" </c:if>>饼图</option>
							<option value="2"
								<c:if test="${reportChartFiled.chartType eq '2'}"> selected="selected" </c:if>>趋势图</option>
							<option value="3"
								<c:if test="${reportChartFiled.chartType eq '3'}"> selected="selected" </c:if>>柱状图</option>
							<option value="4"
								<c:if test="${reportChartFiled.chartType eq '4'}"> selected="selected" </c:if>>折线图</option>
							<option value="5"
								<c:if test="${reportChartFiled.chartType eq '5'}"> selected="selected" </c:if>>折线+柱状图</option>
					</select></td>
				</tr>
				<tr>
					<td style="height: 5px"></td>
				</tr>
					<tr style='display:<c:if test="${reportChartFiled.chartType ne '1'}">none</c:if>;'>
					<td align='left'> &nbsp;汇总方式：</td>
					<td align="left">
					   <select name="summaryType" style="width: 206px; height: 25px">
					      <option value="1"　<c:if test="${reportChartFiled.summaryType eq '1'}"> selected="selected" </c:if>>求和</option>
						  <option value="2"  <c:if test="${reportChartFiled.summaryType eq '2'}"> selected="selected" </c:if>>个数</option>
					   </select>
					</td>
				</tr>
				
				
				<tr>
					<td style="height: 5px"></td>
				</tr>
				<tr>
					<td align='right'>类别系列<c:if test="${reportChartFiled.chartType ne '1'}">（X轴）</c:if>：</td>
					<td align="left"><input type="text" name="xFields"
						value="${reportChartFiled.xFields}" id="chartX"
						readonly="readonly" /> <input type="button" value="配置"
						onclick="popwinX(${reportChartFiled.chartId})" /></td>
				</tr>
				<tr>
					<td style="height: 5px"></td>
				</tr>
				<tr>
					<td align='right'>值系列<c:if test="${reportChartFiled.chartType ne '1'}">（Y轴）</c:if>：</td>
					<td align="left"><input type="text" name="fields"
						value="${reportChartFiled.fields}" id="chartY" readonly="readonly" />
						<input type="button" value="配置"
						onclick="popwinY(${reportChartFiled.chartId})" /></td>
				</tr>
				<tr>
					<td style="height: 5px"></td>
				</tr>
				<tr>
					<td align='right'>宽度：</td>
					<td align="left"><input type="text" name="width" id="width"
						value="${width}" onkeyup="value=value.replace(/[^\d]/g,'')"
						onblur='if(isNaN(this.value)==true) this.value=${width};'></input>px</td>
				</tr>
				<tr>
					<td style="height: 5px"></td>
				</tr>
				<tr>
					<td align='right'>高度：</td>
					<td align="left"><input type="text" name="height" id="height"
						value="${height}" onkeyup="value=value.replace(/[^\d]/g,'')"
						onblur='if(isNaN(this.value)==true) this.value=${height};'></input>px</td>
				</tr>
				<tr style='display: none;'>
					<td align="right"><font color="red">注：</font></td>
				</tr>
				<tr style='display: none;'>
					<td><td align="left"><font color="red">高、宽度为图表所占DIV的百分比</font></td>
				</tr>
			</table>
		</form>
		<iframe name="result" id="result" src="about:blank" frameborder="0"
			width="0" height="0"></iframe>
	</div>
	<script type="text/javascript">
	dg = frameElement.lhgDG;
	dg.addBtn('ok', '保存', function() {
		if($("#chartType")=="0"){
			alert("请选择图表类型");
			return false;
		}
		//高、宽度校验
		var reg=/^([1-9]\d*)$/;
		if(reg.test($("#width").val())==false){
			alert("宽度值只能为正整数");
			return false;
		}
		if(reg.test($("#height").val())==false){
			alert("高度值只能为正整数");
			return false;
		}
		//y轴字段数据  若图表类型为饼图，则y轴字段只能选择一个
		if($("#chartType").val()=="1"){
			var fields=$("#chartY").val();
			var arry=new Array();
			arry=fields.split(",");
			if(arry.length>1){
				alert("饼图值系列只能选择一个字段");
				return false;
			}
		}
		if($("#chartY").val()==""){
			alert("请选择值系列字段");
			return false;
		}
		if($("#chartX").val()==""){
			alert("请选择类别系列字段");
			return false;
		}
		$("#iform").submit();
	});
function success()
{
	alert('保存成功');
    if(dg!=null)
	{
		dg.curWin.document.location.reload();
	}
	dg.cancel();
}
function failed()
{
	alert('保存失败');
}
function popwinY(chartId){
	var chartType=$("#chartType").val();
	var dg = new $.dialog({
		title:'图表值系列字段选择',
		id:'show_commoncode',
		width:450,
		height:350,
		iconTitle:false,
		cover:true,
		maxBtn:true,
		xButton:true,
		resize:true,
		page:'<%=path%>/reportChart/getIsSelOrNotTableFiled.html?selY=1'+"&chartType="+chartType+"&chartId="+chartId+"&reportId=${reportId}"
			});
			dg.ShowDialog();
		}
function popwinX(chartId){
	var chartType=$("#chartType").val();
	var dg = new $.dialog({
		title:'图表类别系列字段选择',
		id:'show_commoncode',
		width:450,
		height:350,
		iconTitle:false,
		cover:true,
		maxBtn:true,
		xButton:true,
		resize:true,
		page:'<%=path%>/reportChart/getIsSelOrNotTableFiled.html?selY=0'+"&chartId="+chartId+"&chartType="+chartType+"&reportId=${reportId}"
			});
			dg.ShowDialog();
		}
		
	</script>
</body>
</html>
