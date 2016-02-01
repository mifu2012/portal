<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.infosmart.portal.pojo.DwpasCKpiInfo"%>	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="UTF-8">
<head>
<link href="<%=path%>/common/css/cssz.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="<%=path%>/common/js/amcharts.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/amfallback.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/arale.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/swfobject.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		var heigth=0;
		if(navigator.userAgent.indexOf("MSIE")>0){
		//IE
			height=document.body.scrollHeight;
		}else{
		//非IE
			height=document.documentElement.scrollHeight;
		}
	});
</script>
<script type="text/javascript">
	var amchart_key ="${amchartKey}";
E.domReady(function(){
	 var comKpiCode=document.getElementById("J_com_kpi_code_select").value;
		var so2 = new SWFObject("<%=path%>/common/amchart/stock/amstock/amstock.swf?", "amline1", "620", "300", "9", "#EFF0F2"); 
		so2.addVariable("path", "<%=path%>/common/amchart/stock/amstock/");
		so2.addVariable("chart_id", "amline1");
		so2.addParam("wmode","transparent");
		so2.addVariable("settings_file", escape("<%=path%>/stackedChart/showStackedChart.html?kpiType=<%=DwpasCKpiInfo.KPI_TYPE_OF_MONTH%>&reportDate=${date}&commKpiCode="+comKpiCode+"&showRightMenu=0"));
		so2.addVariable("key", amchart_key);
		so2.write("prdRanklistChartDiv");//prdRanklistDiv
});

/* 指定类型 */
function reBulidProRankStock(){
    var comKpiCode=document.getElementById("J_com_kpi_code_select").value;
	var so2 = new SWFObject("<%=path%>/common/amchart/stock/amstock/amstock.swf?", "amline1", "620", "300", "9", "#EFF0F2"); 
	so2.addVariable("path", "<%=path%>/common/amchart/stock/amstock/");
	so2.addVariable("chart_id", "amline1");
	so2.addParam("wmode","transparent");
	so2.addVariable("settings_file", escape("<%=path%>/stackedChart/showStackedChart.html?kpiType=<%=DwpasCKpiInfo.KPI_TYPE_OF_MONTH%>&reportDate=${date}&commKpiCode="+comKpiCode+"&showRightMenu=0"));
	so2.addVariable("key", amchart_key);
	so2.write("prdRanklistChartDiv");//prdRanklistDiv
}

//产品ID
var prodIdArray=new Array();
<c:forEach items="${productInfoList}" var="productInfo" varStatus="sel">
	 prodIdArray[${sel.index}]="${productInfo.productId}";
</c:forEach>

/* 趋势图初始化*/
var chartId=null;
/*
function amChartInited(chart_id){
	//alert(chart_id);
	for(var i=0;i<prodIdArray.length;i++)
	{
		var tmpChkObj=document.getElementById("chk_"+prodIdArray[i]);
		if(tmpChkObj!=null&&!tmpChkObj.checked) 
		{
		   window.setTimeout("hideLine('"+tmpChkObj.value+"',100)"); 
		}
	}
  }
  */
  function hideLine(lineCode)
  {
	  document.getElementById(chartId).hideGraph("0", lineCode); 
  }
//全选或全不选
function checkAllOrNo(isChecked)
{
   for(var i=0;i<prodIdArray.length;i++)
	  {
       var tmpChkObj=document.getElementById("chk_"+prodIdArray[i]);
		 if(tmpChkObj!=null)
		  {
			 tmpChkObj.click();
		  }
	  }
   /*
	  if(!isChecked)
	  {
       document.getElementById("label_checkAllOrNo").innerHTML="<b><font color='red'>全选</font></b>";
	  }else
	  {
       document.getElementById("label_checkAllOrNo").innerHTML="<b><font color='red'>全不选</font></b>";
	  }
   */
}

//隐藏线条
function hideOrShowGraph(chkObj)
{
  if(chkObj.checked==true)
	  {
       document.getElementById(chartId).showGraph("0", chkObj.value);
	  }else
	  {
       document.getElementById(chartId).hideGraph("0", chkObj.value); 
	  }
}
//自动调用的函数
function amProcessCompleted(chart_id, process_name)
{
	chartId=chart_id;
	var count=0;
	//alert(prodIdArray.length);
	for(var i=0;i<prodIdArray.length;i++)
	{
		var tmpChkObj=document.getElementById("chk_"+prodIdArray[i]);
		//可用
		tmpChkObj.disabled=false;
		if(tmpChkObj!=null&&tmpChkObj.checked==true) 
		{
		   count++;
		   if(count>4)
			{
			   //window.setTimeout("hideLine('"+tmpChkObj.value+"',100)");
			   tmpChkObj.checked=false;
			   document.getElementById(chart_id).hideGraph("0", tmpChkObj.value); 
			} 
		}else if(tmpChkObj!=null&&tmpChkObj.checked==false)
		{
			   tmpChkObj.checked=false;
			   document.getElementById(chart_id).hideGraph("0", tmpChkObj.value); 
		}
	} 
	//alert(count);
}
</script>
<title>详细事件</title>
</head>
<body>
	<!-- 选择产品 -->
	<jsp:include page="../common/ChoiceProduct.jsp" />
		<div style="width: 820px; height: auto; float: left;">
				<div class="trend-sel">
					<table border="0" cellpadding="0" cellspacing="5">
						<tr>
							<td>&nbsp;&nbsp;指标： <select id="J_com_kpi_code_select"
								style="width: 120px;" onchange="reBulidProRankStock();">
									<c:forEach items="${columnRComKpiList}" var="comKpiInfo"
										varStatus="sel">
										<option value="${comKpiInfo.comKpiCode}">${comKpiInfo.comKpiName}</option>
									</c:forEach>
							</select>
							</td>
							<td rowspan="2">
								<div  style="margin-left: 5px;" id="prdRanklistChartDiv"></div>
							</td>
						</tr>
						<tr>
							<td>
								<div style="border: 1px solid #f0f0f0; overflow: auto; height: 280px; width: 153px;margin-left: 15px;">
									<!--
									<input type="checkbox" value="-1" id="chk_checkAllOrNo"
										onclick="checkAllOrNo(this.checked);" checked /><label
										for="chk_checkAllOrNo" style="cursor: hand;"
										id="label_checkAllOrNo" disabled><b><font color='red'>反选</font>
									</b> </label><br />
									-->
									<c:forEach items="${productInfoList}" var="productInfo"
										varStatus="sel">
										<input type="checkbox" value="${productInfo.productId}"
											id="chk_${productInfo.productId}"
											onclick="hideOrShowGraph(this);" checked disabled/>
										<label for="chk_${productInfo.productId}"
											style="cursor: pointer;">${productInfo.productName}</label>
										<br />
									</c:forEach>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
</body>
</html>