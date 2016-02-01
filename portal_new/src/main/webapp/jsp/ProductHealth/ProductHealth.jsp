<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.infosmart.portal.pojo.DwpasCKpiInfo"%>
<%@page import="com.infosmart.portal.util.Const"%>
<%
	String rootPath = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ rootPath + "/";
	String indexMenuId = (String) session
			.getAttribute(Const.INDEX_MENU_ID);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>产品健康度</title>

<link href="<%=basePath%>/common/css/style.css" rel="stylesheet"
	type="text/css" />
<link href="<%=basePath%>/common/css/cssz.css" rel="stylesheet"
	type="text/css" />
<script src="<%=basePath%>/common/amchart/stock/amstock/swfobject.js"
	type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>/common/js/amcharts.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/common/js/amfallback.js"></script>
<script type="text/javascript" src="<%=basePath%>/common/js/raphael.js"></script>
<script type="text/javascript" src="<%=basePath%>/common/js/arale.js"></script>
<script type="text/javascript" src="<%=basePath%>/common/js/ddycom.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/common/js/jquery-1.4.3.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/common/js/My97DatePicker/My98MonthPicker.js"></script>
<script type="text/javascript">		
//amchartKey 在web.xml配置
var amchart_key = "${amchartKey};"//"110-037eaff57f02320aee1b8576e4f4062a";
window.changeHealthDate = null;
     //init
	$(document).ready(function(){
		
		//相对导航栏背景颜色加深
		var mastParent = window.parent.parent;
		var liList= mastParent.document.getElementsByName("nav_li");
		for(var i=0; i<liList.length; i++){
			var value = liList[i].value;
			if(value=="${menuId}"){
				mastParent.document.getElementById("li_"+value).style.backgroundImage="url(<%=basePath%>/common/images/top_click.gif)";
				
			}else{
				mastParent.document.getElementById("li_"+value).style.backgroundImage="";
			}
		}
		
		//八卦图显示
		var healthanl = new SWFObject("<%=basePath%>/common/amchart/radar/amradar.swf", "ampie", "500", "380", "8", "#EFF0F2");
		healthanl.addVariable("path", "/");
		healthanl.addVariable("settings_file", "<%=basePath%>/common/amchart/radar/settings.xml");
		healthanl.addVariable("data_file", escape("<%=basePath%>/productHealth/showProductHealthSixDimensionChar?productId=${prodInfo.productId}&crtYearMonth=${crtYearMonth}&nextYearMonth=${nextYearMonth}&rid="+Math.random()));
		healthanl.addParam("wmode","transparent");
		healthanl.addVariable("key", amchart_key);
		healthanl.write("chartdiv");
		//趋势图显示
		var healthtre = new SWFObject("<%=basePath%>/common/amchart/stock/amstock/amstock.swf", "amline3", "1000", "500", "8", "#EFF0F2");
		healthtre.addParam("wmode", "opaque");
		healthtre.addVariable("path", "<%=basePath%>/common/amchart/stock/amstock/");
		healthtre.addVariable("chart_id", "amline3");
		//默认查询第一个六维度指标的数据
		healthtre.addVariable("settings_file", escape("<%=basePath%>/stockChart/showMultiStockChart?1=1&isPrd=1&kpiCodes=${dwpasCPrdDim.dim1Code};${dwpasCPrdDim.dim2Code}&reportDate=${crtYearMonth}&needPercent=0&kpiType=<%=DwpasCKpiInfo.KPI_TYPE_OF_MONTH%>&rid="+Math.random()));//月统计
		healthtre.addVariable("key", amchart_key);
		
		//指标Codes
		healthtre.addVariable("kpiCodes", "${dwpasCPrdDim.dim1Code};${dwpasCPrdDim.dim2Code}");
		//日期类型
		healthtre.addVariable("kpiType", <%=DwpasCKpiInfo.KPI_TYPE_OF_MONTH%>);
		healthtre.write("chartdiv2");

		//先中指标重新查看趋势图
		$("input[name='tread']").click( function (){
			var kpiCode = $("input[name='tread']:checked").val();
			var kpiCodes="";
			var obj=null;
			var lastObj=null;
			obj=document.getElementById("chk_${dwpasCPrdDim.dim1Code}");
			if(obj!=null&&obj.checked)
			{
				 lastObj=obj;
                 if(kpiCodes.length>0) kpiCodes+=";";
				 kpiCodes+=obj.value;
			}
            obj=document.getElementById("chk_${dwpasCPrdDim.dim2Code}");
			if(obj!=null&&obj.checked)
			{
				 lastObj=obj;
                 if(kpiCodes.length>0) kpiCodes+=";";
				 kpiCodes+=obj.value;
			}
            obj=document.getElementById("chk_${dwpasCPrdDim.dim3Code}");
			if(obj!=null&&obj.checked)
			{
				 lastObj=obj;
                 if(kpiCodes.length>0) kpiCodes+=";";
				 kpiCodes+=obj.value;
			}
            obj=document.getElementById("chk_${dwpasCPrdDim.dim4Code}");
			if(obj!=null&&obj.checked)
			{
				 lastObj=obj;
                 if(kpiCodes.length>0) kpiCodes+=";";
				 kpiCodes+=obj.value;
			}
            obj=document.getElementById("chk_${dwpasCPrdDim.dim5Code}");
			if(obj!=null&&obj.checked)
			{
				 lastObj=obj;
                 if(kpiCodes.length>0) kpiCodes+=";";
				 kpiCodes+=obj.value;
			}
            obj=document.getElementById("chk_${dwpasCPrdDim.dim6Code}");
			if(obj!=null&&obj.checked)
			{
				 lastObj=obj;
                 if(kpiCodes.length>0) kpiCodes+=";";
				 kpiCodes+=obj.value;
			}
			//默认选中一个
			if(kpiCodes==null||kpiCodes.length==0)
			{
				obj=document.getElementById("chk_${dwpasCPrdDim.dim1Code}");
				obj.checked=true;
                kpiCodes=obj.value;
			}
			if(kpiCodes.split(";").length>3)
			{
				alert('最多可同时查看3个指标');
				lastObj.checked=false;
				return;
			}
			healthtre.addVariable("settings_file", escape("<%=basePath%>/stockChart/showMultiStockChart?1=1&isPrd=1&kpiCodes="+kpiCodes+"&reportDate=${crtYearMonth}&needPercent=0&kpiType=3&rid="+Math.random()));
			healthtre.addVariable("key", amchart_key);
			healthtre.write("chartdiv2");
		});
		
		var iframeContentHeight=document.body.offsetHeight;
		try
		{
			parent.changeIframeSize(iframeContentHeight);
		}
		catch (e)
		{
		}
		
	});
     
    //六维度链接
    function gotoUrl(url){
    	if(url==null||url==""){
    		alert("后台配置有误，无法链接到页面！");
    	}else {
    		location.href="<%=basePath%>/"+url;
    	}
    }

    //在趋势图滑动时，日期变化
	function amRolledOver(chart_id, date, period, data_object){
		var dateDesc="";
		if(date.length==6)
		{
			dateDesc=date.substring(0,4)+"年"+date.substring(4,6)+"月";
		}else if(date.length==8)
		{
            dateDesc=date.substring(0,4)+"年"+date.substring(4,6)+"月"+date.substring(6,8)+"日";
		}
		if(document.getElementById("chartdate-chartdiv2")!=null) document.getElementById("chartdate-chartdiv2").innerText=dateDesc;
	}

	//日期改变,重新加载数据
	function changeDateView(){
		 var sdate = $("#J-Health-Sdate").val();
         var edate = $("#J-Health-Edate").val();
		 location.href="<%=basePath%>/productHealth/showProductHealth?productId=${prodInfo.productId}&menuId=${menuId}&crtYearMonth="+sdate+"&nextYearMonth="+edate;
	}
	//选择产品
	function changeProduct(productId){
		 var sdate = $("#J-Health-Sdate").val();
         var edate = $("#J-Health-Edate").val();		
		 document.location.href="<%=basePath%>/productHealth/showProductHealth?menuId=${menuId}&productId="+productId+"&crtYearMonth="+sdate+"&nextYearMonth="+edate;
	}
			

			//帮助按钮
			function helpShow1() {
				document.getElementById('help1').style.display = "";
			}
			function helpHidden1() {
				document.getElementById('help1').style.display = "none";
			}
			function helpShow2() {
				document.getElementById('help2').style.display = "";
			}
			function helpHidden2() {
				document.getElementById('help2').style.display = "none";
			}
		</script>
</head>
<body class="indexbody2">

	<!-- 选择产品 -->
	<jsp:include page="../common/ChoiceProduct.jsp" />
	<!--头部结束-->

	<div class="new_jwy_yy" style="width: 1010px; background: #fff">

		<!-- 当前位置 -->
		<div class="kpi_position" id="navigationDiv"  style="z-index:9999999">
			<div style="padding-top: 2px">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="78%">
							<div style="padding: 3px 0px 0px 11px">
								<div style="float: left">
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;当前位置： <a
										href="<%=rootPath%>/index/gotoHomePage.html?menuId=<%=indexMenuId%>">首页</a>&gt;
									<a
										href="<%=rootPath%>/productHealth/showProductHealth?menuId=${menuId}">${modulemap.PRODUCT_HEALTH_SIX_DIMENSIONS.menuName}</a>&gt;
									<font color='red'>${productInfo.productName}</font>
								</div>
								<div style="float: left">
									&nbsp;&nbsp; <a href="javascript:void(0);"
										onclick="choiceRrdInfo();"><img
										src="<%=rootPath%>/common/images/jwy_button_01.gif" border="0" /></a>
								</div>
							</div>
						</td>
						<td width="22%"><table width="100%" border="0"
								cellspacing="0" cellpadding="0" style="padding-top: 5px">
								<tr>
									<td></td>
								</tr>
							</table></td>
					</tr>
				</table>
			</div>
		</div>
		<!-- 当前位置结束 -->
		<div class="clear"></div>

		<div class="ddy_channel2"></div>

		<div class="index_home">


			<div style="padding-top: 5px"></div>
			<!--第一部分-->
			<div class="ddy_one_home"></div>
			<div class="index_title">
				<div class="index_title_pd">
					<table>
						<tr>
							<td><img src="<%=rootPath%>/common/images/new_jwy_4.gif" />
							</td>
							<td>&nbsp;${modulemap.PRODUCT_HEALTH_SIX_DIMENSIONS.moduleName
								}</td>
						</tr>
					</table>
				</div>
				<span class="box-top-right"> <em
					style="background: url(<%=rootPath%>/common/images/askicon5.png); cursor: pointer"
					class="box-askicon" onmouseover="helpShow1();"
					onmouseout="helpHidden1();"></em>
				</span>
				<div id="help1" style="display: none; font-size: 12px;">
						<div style="position:relative;z-index:99999999;  margin:0px 0px 0px 310px ">
										<div class="pop_w">
										  <div class="pop_z" style="z-index:99999; left:260px;">	<img src="<%=rootPath%>/common/images/pop_tip.png" /></div>
       
<div class="pop_x">		<p>
									<b>${modulemap.PRODUCT_HEALTH_SIX_DIMENSIONS.moduleName}：</b>
							</p>
							<p style="font-weight: normal;">
								${modulemap.PRODUCT_HEALTH_SIX_DIMENSIONS.remark}</p>
   
</div>
</div></div>
				</div>
			</div>
			<div class="index_report" style="background: #e8f1f0">
				<div align="center">
					<div
						style="width: 500px; height: 383px; overflow: hidden; float: left; background: #e8f1f0">
						<table width="90%" border="0" align="right" cellpadding="0"
							cellspacing="0">
							<tr>
								<td width="29%" height="30">&nbsp;</td>
								<td width="35%">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td width="34%">
												<div align="right">
													<img src="<%=basePath%>/common/images/ddy_lb_2.gif"
														width="14" height="15" /> &nbsp;&nbsp;&nbsp;
												</div>
											</td>
											<td width="66%">
												<div align="right">
													<!--日期1-->
													<input type="text" value="${crtYearMonth}"
														style="width: 100px; cursor: pointer; vertical-align: middle; height: 18px;"
														id="J-Health-Sdate" onclick="setMonth(this,this)"
														readonly="readonly" onchange="changeDateView()" />
												</div>
											</td>
										</tr>
									</table>
								</td>
								<td width="36%">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td width="34%">
												<div align="right">
													<img src="<%=basePath%>/common/images/ddy_lb_3.gif"
														width="14" height="15" /> &nbsp;&nbsp;&nbsp;
												</div>
											</td>
											<td width="66%">
												<div align="right">
													<!--日期2-->
													<input type="text" value="${nextYearMonth}"
														style="width: 100px; cursor: pointer; vertical-align: middle; height: 18px;"
														onclick="setMonth(this,this)" readonly="readonly"
														id="J-Health-Edate" onchange="changeDateView()" />
												</div>
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<!--循环以下2条TR一条蓝一条拜白-->
							<!--prodHealthOfData1表示数据1（第一条数据）-->
							<!--prodHealthOfData2表示数据2（对比数据）-->
							<tr class="report_divblue">
								<!--去掉了原来的六纬度指标写死的名称-->
								<td height="30">
									<div align="left">
										${dwpasCPrdDim.dim1Name}
										<!--业务笔数数-->
										<%-- (${dwpasCPrdDim.dim1ValueUnit}) --%>
									</div>
								</td>
								<!--日期1的指标数据-->
								<td>
									<div align="center">
										<c:if test="${not empty prodHealthOfData1.dimValue1}">${prodHealthOfData1.dimValue1}${dwpasCPrdDim.dim1ValueUnit}</c:if>
									</div>
								</td>
								<!--日期2的指标数据-->
								<td>
									<div align="center">
										<c:if test="${not empty prodHealthOfData2.dimValue1}">${prodHealthOfData2.dimValue1}${dwpasCPrdDim.dim1ValueUnit}</c:if>
									</div>
								</td>
							</tr>
							<tr class="report_divwhite">
								<td height="30">
									<div align="left">
										${dwpasCPrdDim.dim2Name}
										<!--笔数成功率
												(${dwpasCPrdDim.dim2ValueUnit})-->
									</div>
								</td>
								<td>
									<div align="center">
										<c:if test="${not empty  prodHealthOfData1.dimValue2}">${prodHealthOfData1.dimValue2}${prodHealthOfData1.dimUnit2}</c:if>
									</div>
								</td>
								<td>
									<div align="center">
										<c:if test="${not empty  prodHealthOfData2.dimValue2}">${prodHealthOfData2.dimValue2}${prodHealthOfData2.dimUnit2}</c:if>
									</div>
								</td>
							</tr>
							<!--循环结束-->
							<!--以下为无效测试代码-->
							<tr class="report_divblue">
								<td height="30">
									<div align="left">
										${dwpasCPrdDim.dim3Name}
										<!--深度活跃会员占比
												(${dwpasCPrdDim.dim3ValueUnit})-->
									</div>
								</td>
								<td style="border-right: 1px solid #fff">
									<div align="center">
										<c:if test="${not empty prodHealthOfData1.dimValue3}">${prodHealthOfData1.dimValue3}${prodHealthOfData1.dimUnit3}</c:if>
									</div>
								</td>
								<td style="border-right: 1px solid #fff">
									<div align="center">
										<c:if test="${not empty prodHealthOfData2.dimValue3}">${prodHealthOfData2.dimValue3}${prodHealthOfData2.dimUnit3}</c:if>
									</div>
								</td>
							</tr>
							<tr class="report_divwhite">
								<td height="30">
									<div align="left">
										${dwpasCPrdDim.dim4Name}
										<!--流失率
												(${dwpasCPrdDim.dim4ValueUnit})-->
									</div>
								</td>
								<td>
									<div align="center">
										<c:if test="${not empty prodHealthOfData1.dimValue4}">${prodHealthOfData1.dimValue4}${prodHealthOfData1.dimUnit4}</c:if>
									</div>
								</td>
								<td>
									<div align="center">
										<c:if test="${not empty prodHealthOfData2.dimValue4}">${prodHealthOfData2.dimValue4}${prodHealthOfData2.dimUnit4}</c:if>
									</div>
								</td>
							</tr>
							<tr class="report_divblue">
								<td height="30">
									<div align="left">
										${dwpasCPrdDim.dim5Name}
										<!--求助率
												(${dwpasCPrdDim.dim5ValueUnit})-->
									</div>
								</td>
								<td style="border-right: 1px solid #fff">
									<div align="center">
										<c:if test="${not empty prodHealthOfData1.dimValue5}">${prodHealthOfData1.dimValue5}${prodHealthOfData1.dimUnit5}</c:if>
									</div>
								</td>
								<td style="border-right: 1px solid #fff">
									<div align="center">
										<c:if test="${not empty prodHealthOfData2.dimValue5}">${prodHealthOfData2.dimValue5}${prodHealthOfData2.dimUnit5}</c:if>
									</div>
								</td>
							</tr>
							<tr class="report_divwhite">
								<td height="30">
									<div align="left">
										${dwpasCPrdDim.dim6Name}
										<!--交叉场景数-->
										<%-- (${dwpasCPrdDim.dim6ValueUnit}) --%>
									</div>
								</td>
								<td>
									<div align="center">
										<c:if test="${not empty prodHealthOfData1.dimValue6}"> ${prodHealthOfData1.dimValue6}${dwpasCPrdDim.dim6ValueUnit}</c:if>
									</div>
								</td>
								<td>
									<div align="center">
										<c:if test="${not empty prodHealthOfData2.dimValue6}">${prodHealthOfData2.dimValue6}${dwpasCPrdDim.dim6ValueUnit}</c:if>
									</div>
								</td>
							</tr>
							<!--测试代码结束-->
						</table>
						<div class="clear"></div>
						<table width="90%" border="0" align="right" cellpadding="0"
							cellspacing="0"
							style="border-top: 2px solid #c8ccc8; color: #606460">
							<tr>
								<td height="115" align="left" valign="middle">
									${modulemap.PRODUCT_HEALTH_SIX_DIMENSIONS.remark}</td>
							</tr>
						</table>
					</div>

					<div
						style="width: 490px; height: 383px; overflow: hidden; float: left; background: #e8f1f0">
						<div id="chartdiv"></div>

						<div class="healthanl-right"
							style="margin: -365px 5px 0px 250px; *margin: -365px 5px 0px -100px;">
							<a href="javascript:gotoUrl('${dwpasCPrdDim.dim1Url}');"
								style="position: absolute; color: blue; left: 287px; top: 47px;">(${dwpasCPrdDim.dim1Name}<!--产品发展-->)
							</a> <a href="javascript:gotoUrl('${dwpasCPrdDim.dim2Url}');"
								style="position: absolute; color: blue; left: 415px; top: 112px;">(${dwpasCPrdDim.dim2Name}<!--用户体验-->)
							</a> <a href="javascript:gotoUrl('${dwpasCPrdDim.dim3Url}');"
								style="position: absolute; color: blue; left: 415px; top: 258px;">(${dwpasCPrdDim.dim3Name}<!--用户特征-->)
							</a> <a href="javascript:gotoUrl('${dwpasCPrdDim.dim4Url}');"
								style="position: absolute; left: 287px; color: blue; top: 320px;">(${dwpasCPrdDim.dim4Name}<!--用户留存-->)
							</a> <a href="javascript:gotoUrl('${dwpasCPrdDim.dim5Url}');"
								style="position: absolute; left: 130px; color: blue; top: 258px;">(${dwpasCPrdDim.dim5Name}<!--用户声音-->)
							</a> <a href="javascript:gotoUrl('${dwpasCPrdDim.dim6Url}');"
								style="position: absolute; left: 130px; color: blue; top: 112px;">(${dwpasCPrdDim.dim6Name}<!--场景交叉-->)
							</a>
						</div>
					</div>

				</div>
				<div class="clear"></div>
				<!--正文内容-->
				<!--内容结束-->
			</div>
			<!--第一部分结束-->
			<!--第二部分-->
			<div class="ddy_one_home"></div>
			<div class="index_title">
				<div class="index_title_pd">

					<table>
						<tr>
							<td><img src="<%=rootPath%>/common/images/new_jwy_4.gif" /></td>
							<td>&nbsp;
								${modulemap.PRODUCT_HEALTH_SIX_DIMENSIONS_THREAD_CHART.moduleName
								}</td>
						</tr>
					</table>

				</div>
				<span class="box-top-right"> <em
					style="background: url(<%=basePath%>/common/images/askicon5.png); cursor: pointer"
					class="box-askicon" onmouseover="helpShow2();"
					onmouseout="helpHidden2();"></em>
				</span>
				<div id="help2" style="display: none; font-size: 12px;">
					<div style="position:relative;z-index:99999999; margin:0px 0px 0px 310px">
										<div class="pop_w">
										  <div class="pop_z" style="z-index:99999; left:260px;">	<img src="<%=rootPath%>/common/images/pop_tip.png" /></div>
       
<div class="pop_x">		<p>
								<b>${modulemap.PRODUCT_HEALTH_SIX_DIMENSIONS_THREAD_CHART.moduleName}：</b>
							</p>
							<p style="font-weight: normal;">
								${modulemap.PRODUCT_HEALTH_SIX_DIMENSIONS_THREAD_CHART.remark}</p>
   
</div>
</div></div>
				</div>
			</div>
			<div class="index_report" style="background: #f3f3f3">
				<div class="clear"></div>
				<div align="center">
					<!--正文内容-->
					<table width="100%" border="0" cellspacing="0" cellpadding="0"
						style="color: #666; margin-top: 10px">
						<tr>
							<!--去掉了原来的六纬度指标写死的名称-->
							<td width="2%"><input type="checkbox" name="tread"
								value="${dwpasCPrdDim.dim1Code}" checked="checked"
								id="chk_${dwpasCPrdDim.dim1Code}" /></td>
							<td width="12%">
								<div align="left">
									<label for="chk_${dwpasCPrdDim.dim1Code}"
										style="cursor: pointer;">${dwpasCPrdDim.dim1Name}</label>
									<!--业务笔数-->
								</div>
							</td>
							<td width="2%"><input type="checkbox" name="tread"
								value="${dwpasCPrdDim.dim2Code}" checked="checked"
								id="chk_${dwpasCPrdDim.dim2Code}" /></td>
							<td width="12%">
								<div align="left">
									<label for="chk_${dwpasCPrdDim.dim2Code}"
										style="cursor: pointer;">${dwpasCPrdDim.dim2Name}</label>
									<!--深度活跃用户占比-->
								</div>
							</td>
							<td width="2%"><input type="checkbox" name="tread"
								value="${dwpasCPrdDim.dim3Code}"
								id="chk_${dwpasCPrdDim.dim3Code}" /></td>
							<td width="12%">
								<div align="left">
									<label for="chk_${dwpasCPrdDim.dim3Code}"
										style="cursor: pointer;">${dwpasCPrdDim.dim3Name}</label>
									<!--流失率-->
								</div>
							</td>
							<td width="2%"><input type="checkbox" name="tread"
								value="${dwpasCPrdDim.dim4Code}"
								id="chk_${dwpasCPrdDim.dim4Code}" /></td>
							<td width="12%">
								<div align="left">
									<label for="chk_${dwpasCPrdDim.dim4Code}"
										style="cursor: pointer;">${dwpasCPrdDim.dim4Name}</label>
									<!--求助率-->
								</div>
							</td>
							<td width="2%"><input type="checkbox" name="tread"
								value="${dwpasCPrdDim.dim5Code}"
								id="chk_${dwpasCPrdDim.dim5Code}" /></td>
							<td width="12%">
								<div align="left">
									<label for="chk_${dwpasCPrdDim.dim5Code}"
										style="cursor: pointer;">${dwpasCPrdDim.dim5Name}</label>
									<!--交叉场景数-->
								</div>
							</td>
							<td width="2%"><input type="checkbox" name="tread"
								value="${dwpasCPrdDim.dim6Code}"
								id="chk_${dwpasCPrdDim.dim6Code}" /></td>
							<td width="15%">
								<div align="left">
									<label for="chk_${dwpasCPrdDim.dim6Code}"
										style="cursor: pointer;">${dwpasCPrdDim.dim6Name}</label>
									<!--笔数成功率-->
								</div>
							</td>
							<td width="13%">
								<div align="left">
									<a
										href="<%=rootPath%>/prodAnalyze/kpiAnalyze.html?menuId=${menumap.m_04}">更多指标&gt;&gt;</a>
								</div>
							</td>
						</tr>
					</table>
					<!--趋势图-->
					<div id="chartdiv2"></div>
					<!--日期-->
					<div id="chartdate-chartdiv2"
						style="position: absolute; left: 250px; top: 550px; display: none;"></div>
					<!--内容结束-->
				</div>
			</div>
			<!--第二部分结束-->
			<div></div>
		</div>
	</div>
</body>
</html>