<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.Date"%>
<%@ page import="com.infosmart.portal.pojo.DwpasStKpiData"%>
<%@ page import="com.infosmart.portal.pojo.DwpasCKpiInfo"%>
<%@ page import="com.infosmart.portal.util.SystemColumnEnum"%>
<%@page import="com.infosmart.portal.util.Const"%>
<%
	String path = request.getContextPath();
    String indexMenuId = (String)session.getAttribute(Const.INDEX_MENU_ID);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${systemName}</title>

<link href="<%=path%>/common/css/lwt.css" rel="stylesheet"
	type="text/css" />
	<link href="<%=path%>/common/css/cssz.css" rel="stylesheet" type="text/css" />
<style>
.ddy_index_input{
width:110px;
height:20px;
border:0px;
background-repeat:no-repeat;
text-align:center;
background-image:url('<%=path%>/common/images/input_button_1.gif');
}

</style>
</head>
<body class="indexbody2">
<!-- 选择产品 -->
<jsp:include page="../common/ChoiceProduct.jsp" flush="true"/>
<!-- 产品选择 end-->
	<div style="width: 1010px; overflow: hidden; margin: auto">
		<!-- 当前位置 -->
		<div class="kpi_position" id="navigationDiv" style="z-index:9999999">
			<div style="padding-top: 2px">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="78%">
						<div style="padding:3px 0px 0px 11px">
						<div style="float:left">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;当前位置：
						<a href="<%=path%>/index/gotoHomePage.html?menuId=<%=indexMenuId%>">首页</a>&gt;
						<a href="<%=path%>/productHealth/showProductHealth?menuId=${parentMenu.menuId}">${parentMenu.menuName }</a>&gt;
						<a href="<%=path%>/userFeature/PrepareUserFeature?menuId=${menuId}">${modulemap.USER_FEATURE_DEEP_USER_RATE_CHART.menuName}</a>&gt;
							<font color='red'> ${prodInfo.productName}</font></div>
							<div style="float:left">&nbsp;&nbsp;
	                        <a href="javascript:void(0);" onclick="choiceRrdInfo();"><img src="<%=path%>/common/images/jwy_button_01.gif" border="0" /></a></div>					
				         </div>
					    </td>
						<td width="22%">
							<table width="100%" border="0" cellspacing="0" cellpadding="0" style="padding-top: 5px">
								<tr>
									<td>
										<span style="vertical-align: middle;"> 数据时间：</span> 
										<a href="javascript:void(0);" onclick="changeTheDate(-1);">
											<img style="vertical-align: middle;" align="middle" src="<%=path%>/common/images/ddy_2.gif" width="11"
												height="23" border="0" /></a> 
											<input id="queryDate" name="queryDate" type="text" class="ddy_index_input"
												readonly="readonly" onchange="restartOpen()" onclick="setMonth(this,this)" value="${date}"
												style="cursor: pointer;" /> <a href="javascript:void(0);" onclick="changeTheDate(1);">
											<img style="vertical-align: middle;" align="middle" src="<%=path%>/common/images/ddy_1.gif" width="11"
												height="23" border="0" /></a>&nbsp;
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<!-- 当前位置结束 -->
		<div class="clear"></div>

		<div class="ddy_channel2"></div>

		<div class="content">
			<div style="margin-top: -5px">
				<img src="<%=path%>/common/images/wph_8.gif" />
			</div>

			<!--活跃用户构成变化 -->
			<div class="normalbox">
				<div class="index_title">
					<!-- 活跃用户构成变化 -->
					<div class="index_title_pd">
						<table>
							<tr>
								<td><img src="<%=path%>/common/images/new_jwy_4.gif" /></td>
								<td>&nbsp;<!-- 活跃用户构成变化 -->${modulemap.USER_FEATURE_DEEP_USER_RATE_CHART.moduleName}
								</td>
							</tr>
						</table>
					</div>
					<span class="box-top-right"> 
					<em style="background: url(<%=path%>/common/images/askicon5.png); cursor: pointer"
						class="box-askicon" onmouseover="helpShow1();"
						onmouseout="helpHidden1();"></em>
					</span>
					<div id="help1" style="display: none; font-size: 12px;">
						<div style="position:relative;z-index:99999999; margin:0px 0px 0px 310px">
										<div class="pop_w">
										  <div class="pop_z" style="z-index:99999; left:260px;">	<img src="<%=path%>/common/images/pop_tip.png" /></div>
       
<div class="pop_x">			<p>
									<b>${modulemap.USER_FEATURE_DEEP_USER_RATE_CHART.moduleName}：</b>
								</p>
								<p style="font-weight: normal;">
									${modulemap.USER_FEATURE_DEEP_USER_RATE_CHART.remark}</p>
</div>
</div></div>
					</div>
				</div>

				<div class="clear"></div>
				<div class="box-conent box-view-change">
					<div class="view-change-left" style="position: relative;">
						<div id="chart-column-change" style="width: 995px; height: 300px;"></div>
						<div id="chartdate-amline3"
							style="position: absolute; width: 120px; left: 25px; top: 28px; display: none;">${date}</div>
					</div>
				</div>
				<div class="box-bottom">
					<div class="box-bottom-rt">
						<div class="box-bottom-ct"></div>
					</div>
				</div>
			</div>
			<!--活跃用户构成变化结束 -->

			<!--tab切换 -->
			<div class="box-nav">
				<ul class="box-nav-ul">
					<li class="box-nav-li box-li-act" id="J-useAnl">${modulemap.USER_FEATURE_USER_USED_CHART.moduleName}</li>
					<li class="box-nav-li" id="J-peopleAnl">${modulemap.USER_FEATURE_USER_AGE_CHART.moduleName}</li>
					<li class="box-nav-li" id="J-area">${modulemap.USER_FEATURE_USER_AREA_CHART.moduleName}</li>
				</ul>
			</div>
			<!--使用特征 -->
			<div class="normalbox mt15 J-tabbox" id="J-useAnlbox">
				<div class="index_title">
					<div class="index_title_pd">
						<table>
							<tr>
								<td><img src="<%=path%>/common/images/new_jwy_4.gif" /></td>
								<td>&nbsp;${modulemap.USER_FEATURE_USER_USED_CHART.moduleName}</td>
							</tr>
						</table>
					</div>
					<span class="box-top-right"> <em
						style="background: url(<%=path%>/common/images/askicon5.png); cursor: pointer"
						class="box-askicon" onmouseover="helpShow2();"
						onmouseout="helpHidden2();"></em>
					</span>
					<div id="help2" style="display: none; font-size: 12px;">
						<div style="position:relative;z-index:99999999;  margin:0px 0px 0px 310px">
										<div class="pop_w">
										  <div class="pop_z" style="z-index:99999; left:260px;">	<img src="<%=path%>/common/images/pop_tip.png" /></div>
       
<div class="pop_x">							<p>
									<b>${modulemap.USER_FEATURE_USER_USED_CHART.moduleName}：</b>
								</p>
								<p style="font-weight: normal;">
									${modulemap.USER_FEATURE_USER_USED_CHART.remark}</p>
</div>
</div></div>
					</div>
				</div>

				<div class="clear"></div>
				<div class="box-conent box-view-prop">
					<div class="view-prop-left">
						<h3 style="padding-left: 15px;">使用次数分布(万人)</h3>
						<div id="chart-column-num" style="width: 745px; height: 220px;"></div>
						<h3 style="padding-left: 15px;"> 时间偏好(万人) 
						    <!--
							<em style="background: #89A958; 　vertical-align: middle; width: 12px; height: 12px; display: inline-block;"></em>
							-->
							<!--产品-->
							<!--<em>${prodInfo.productName} </em> -->
							<!--
							<em style="background: #D43E28; 　vertical-align: middle; width: 12px; height: 12px; display: inline-block;"></em>
							-->
							<!--大盘-->
							<!--<em>${productDp.productName}</em>-->
						</h3>
						<div id="chart-column-time" style="width: 745px; height: 220px;"></div>
					</div>
					<!--无线-->
					<div class="view-prop-right" style="overflow: hidden;">
						<h3 style="height: 25px; padding-left: 15px;">${modulemap.USER_CHARACTER_USED_WIRELESS_RATE_PIE_CHART.columnKind}</h3>
						<div id="chart-wuxian-prod" style="width: 250px; height: 225px; margin: 0 0 0 35px; _margin: 0 0 0 15px; float: left;"></div>
						<div id="chart-wuxian-dapan" style="width: 250px; height: 225px; margin: 25px 0 0 35px; _margin: 0 0 0 15px; float: left;"></div>
						<!--产品-->
						<div style="position: absolute; top: 30px; left: 15px; color: #666; font-size: 12px; font-weight: normal;">${prodInfo.productName}</div>
						<!--大盘-->
						<div style="position: absolute; top: 280px; left: 15px; color: #666; font-size: 12px; font-weight: normal;">${productDp.productName}</div>
					</div>

				</div>
				<div class="box-bottom">
					<div class="box-bottom-rt">
						<div class="box-bottom-ct"></div>
					</div>
				</div>
			</div>
			<!--使用特征结束 -->
			<!--人口特征 -->
			<div class="normalbox mt15 J-tabbox hidden" id="J-peopleAnlbox">
				<div class="index_title">
					<div class="index_title_pd">
						<table>
							<tr>
								<td><img src="<%=path%>/common/images/new_jwy_4.gif" /></td>
								<td>&nbsp;${modulemap.USER_FEATURE_USER_AGE_CHART.moduleName}</td>
							</tr>
						</table>
					</div>
					<span class="box-top-right"> <em
						style="background: url(<%=path%>/common/images/askicon5.png); cursor: pointer"
						class="box-askicon" onmouseover="helpShow3();"
						onmouseout="helpHidden3();"></em>
					</span>
					<div id="help3" style="display: none; font-size: 12px;">
						<div style="position: relative; margin: 32px 0px 0px 730px">
							<div style="width: 250px" class="tip">
								<p>
									<b>${modulemap.USER_FEATURE_USER_AGE_CHART.moduleName}：</b>
								</p>
								<p style="font-weight: normal;">
									${modulemap.USER_FEATURE_USER_AGE_CHART.remark}</p>
							</div>
						</div>
					</div>					
				</div>

				<div class="clear"></div>
				<div class="box-conent box-view-prop">
					<div class="view-prop-left">
						<h3 style="height: 25px; padding-left: 15px">性别(万人)</h3>
						<!--
						<h3 style="color: #666; font-size: 12px; font-weight: normal;"> 销量(万笔)</h3>
						-->
						<!--大盘-->
						<div id="chart-age-dapan" style="width: 745px; height: 210px; float: right;"></div>
						<!--font-weight: normal;-->
						<h3 style="color: #666;  padding-left: 15px "> 年龄<!--${prodInfo.productName}-->(万人)</h3>
						<!--年龄-->
						<div id="chart-age-prouct" style="width: 745px; height: 210px; float: right;"></div>
					</div>
					<div class="view-prop-right">
						<h3 style="height: 20px; padding-left: 15px">${modulemap.USER_CHARACTER_MAN_USER_OVERALL_PIE_CHART.columnKind }</h3>
						<!--性别占比-->
						<div id="chart-sex-prouct"
							style="width: 250px; height: 215px; margin: 25px 0 0 35px; _margin: 0 0 0 15px; float: left;"></div>
						<!--大盘用户-->
						<div id="chart-sex-dapan"
							style="width: 250px; height: 215px; margin: 25px 0 0 35px; _margin: 0 0 0 15px; float: left;"></div>
						<div style="position: absolute; top: 280px; left: 15px; color: #666; font-size: 12px; font-weight: normal;">${prodInfo.productName}</div>
						<div style="position: absolute; top: 30px; left: 15px; color: #666; font-size: 12px; font-weight: normal;">${productDp.productName}</div>
					</div>
				</div>
				<div class="box-bottom">
					<div class="box-bottom-rt">
						<div class="box-bottom-ct"></div>
					</div>
				</div>
			</div>
			<!--人口特征结束 -->
			<!--中国地图 -->
			<div class="normalbox mt15 J-tabbox hidden" id="J-areabox">
				<div class="index_title">
					<div class="index_title_pd">
						<table>
							<tr>
								<td><img src="<%=path%>/common/images/new_jwy_4.gif" /></td>
								<td>&nbsp;${modulemap.USER_FEATURE_USER_AREA_CHART.moduleName}</td>
							</tr>
						</table>
					</div>
					<span class="box-top-right"> 
						<em style="background: url(<%=path%>/common/images/askicon5.png); cursor: pointer"
							class="box-askicon" onmouseover="helpShow4();" onmouseout="helpHidden4();"></em>
					</span>
					<div id="help4" style="display: none; font-size: 12px;">
						<div style="position: relative; margin: 32px 0px 0px 730px">
							<div style="width: 250px" class="tip">
								<p>
									<b>${modulemap.USER_FEATURE_USER_AREA_CHART.moduleName}：</b>
								</p>
								<p style="font-weight: normal;">
									${modulemap.USER_FEATURE_USER_AREA_CHART.remark}</p>
							</div>
						</div>
					</div>					
				</div>

				<div class="clear"></div>
				<div style="width: 100%; height: 40px; background: #f7f7f7"></div>
				<div style="padding-top: 0px">
					<div align="center" style="width:998px; height:517px; margin:auto;background-image:url(<%=path%>/common/images/test_2.gif);">
						<div style="width: 998px; height: 517px; background-color: #f7f7f7;">
							<div style="width: 998px; height: 517px;" align="center">
								<div align="center" style="float: left; width: 620px; height: 517px; overflow: hidden; background: #f7f7f7"
									id="chinaMap"></div>
								<c:if test="${not empty mapdata}">
									<div align="center"
										style="float:left; width:359px; height:428px; background-image:url(<%=path%>/common/images/49.gif);">
										<div align="center" style="width: 317px; height: 366px; padding-top: 40px">
											<div style="width: 313px; height: 46px">
												<div class="font" style="width: 315px">
													<div style="width: 75px; float: left; padding-top: 8px">排名</div>
													<div style="width: 70px; float: left; padding-top: 8px">省份</div>
													<div style="width: 100px; float: left; padding-top: 8px">会员数</div>
													<div style="width: 70px; float: left; padding-top: 8px">销量</div>
												</div>
											</div>
											<c:forEach items="${mapdata }" var="map" varStatus="m">
												<c:if test="${m.index lt 10 }">
													<div style="width: 315px">
														<div align="center" class="bg">
															<div class="bg4">${map.ranking }</div>
														</div>
														<div align="center" class="bg2" style="color: #8075a0">
															<div class="index_wz">${map.featureName }</div>
														</div>
														<div align="center" class="bg3" style="color: #8075a0">
															<div class="index_wz">${map.userCnt }</div>
														</div>
														<div align="center" class="bg6" style="color: #8075a0">
															<div class="index_wz">${map.salesVolume }</div>
														</div>
													</div>
												</c:if>
											</c:forEach>
											
											<div style="width: 315px;margin-top: 330px;" align="right">
											<a  href="javascript:viod(0)" onclick="excelDown('${prodInfo.productName}','${enddate}')" ><img
											src="<%=path%>/common/images/ddy_22.gif" border="0"  /></a>
											</div>
										</div>
									</div>
								</c:if>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<iframe src="" id="downloadFrm" name="downloadFrm" style="width: 100%; height: 100%; border: none;display:none;" scrolling="no" frameborder="0"> </iframe>
	<!--中国地图结束 -->

<script src="<%=path%>/common/js/swfobject.js" type="text/javascript"></script>
<script src="<%=path%>/common/amchart/column/amcolumn/swfobject.js" type="text/javascript"></script>
<script src="<%=path%>/common/amchart/column/amcolumn/swfobject1.js" type="text/javascript"></script>
<script src="<%=path%>/common/amchart/stock/amstock/swfobject.js"
	type="text/javascript"></script>
<script src="<%=path%>/common/js/arale.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=path%>/common/js/amcharts.js"></script>   
<script src="<%=path%>/common/ammap/ammap/swfobject.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=path%>/common/js/My97DatePicker/My98MonthPicker.js"></script>
<script type="text/javascript">	
function excelDown(productName,reportDate){
	productName = encodeURI(productName);
	if(!confirm("确定导出数据吗?")) return;
	document.getElementById("downloadFrm").src="<%=path%>/userFeature/areaExcelDown.html?productName="+productName+"&reportDate="+reportDate;
	document.getElementById("downloadFrm").submit();
}

function columnExcel(featureType){
	var chartObj=document.getElementById(lastRolledOverChartId);
	if(chartObj==null)
	{
		alert('未选择图表');
		return;
	}
	if(!confirm("确定导出数据吗?")) return;
	document.getElementById("downloadFrm").src="<%=path%>/userFeature/columnExcelDown.html?featureType="+featureType+"&reportDate=${enddate}";
	document.getElementById("downloadFrm").submit();
	
}

function showData(featureType){
	var chartObj=document.getElementById(lastRolledOverChartId);
	if(chartObj==null)
	{
		alert('未选择图表');
		return;
	}
	var url="<%=path%>/userFeature/showExcelDiv.html?featureType="+featureType+"&reportDate=${enddate}&random="+Math.random();
	var detailDataDivId="detailDataDivId_"+featureType;
	openDivWindow(detailDataDivId,"指标详细数据",821,300,url);//openDivWindow 为通用的方法
}
//amchartKey 在web.xml配置
var amchart_key = "${amchartKey};"//"110-037eaff57f02320aee1b8576e4f4062a";
E.domReady(function () {
	//修改说明，跟产品选择有脚本冲突（点击TAB,内容不显示），暂去掉	    
	A($$(".box-nav-li")).each(function(el){
		el.click(function(){
			var did = el.attr("id");
			A($$(".box-nav-li")).each(function(es){
				es.removeClass("box-li-act");
			});
			el.addClass("box-li-act");
			A($$(".J-tabbox")).each(function(es){
				es.addClass("hidden");
			});
			$(did+"box").removeClass("hidden");
		});
	});		
	//活跃用户构成变化
	var userAline = new SWFObject("<%=path%>/common/amchart/stock/amstock/amstock.swf?id=<%=new Date().getTime()%>", "userAline", "995", "300", "8", "#EFF0F2");
	userAline.addVariable("path", "<%=path%>/common/amchart/stock/amstock/");
	userAline.addVariable("settings_file", escape("<%=path%>/stockChart/showStockChart?1=1&isPrd=1&kpiCodes=${queryRealKpiCode.USER_CHARACTER_DEEP_USER_RATE_THREAD_CHART};${queryRealKpiCode.USER_CHARACTER_DEEP_USER_RATE_OVERALL_THREAD_CHART}&needPercent=1&kpiType=<%=DwpasCKpiInfo.KPI_TYPE_OF_MONTH%>&reportDate=${enddate}&rid="+Math.random()));
	userAline.addParam("wmode", "transparent");
	userAline.addVariable("key", amchart_key);
	
	userAline.addVariable("kpiCodes", "${queryRealKpiCode.USER_CHARACTER_DEEP_USER_RATE_THREAD_CHART};${queryRealKpiCode.USER_CHARACTER_DEEP_USER_RATE_OVERALL_THREAD_CHART}");
	userAline.addVariable("kpiType", <%=DwpasCKpiInfo.KPI_TYPE_OF_MONTH%>);
	
	userAline.addVariable("chart_id", "userAline");
	userAline.write("chart-column-change");
	//使用次数分布
	var params ={wmode:"transparent"};
	var flashVars ={
		path: "<%=path%>/common/amchart/column/amcolumn/",
		settings_file: escape("<%=path%>/userFeature/showUserFeatureChart?productId=${prodInfo.productId}&types=column&reportDate=${enddate}&featureType=1&rid="+Math.random()),
		chart_id:"chart-column-num",
		key:amchart_key
	};
	swfobject.embedSWF("<%=path%>/common/amchart/column/amcolumn/amcolumn.swf", "chart-column-num", "745", "220", "8.0.0", "<%=path%>/common/amchart/column/amcolumn/expressInstall.swf", flashVars, params);

    //时间偏好
    var sjph = new SWFObject("<%=path%>/common/amchart/amline/amline.swf", "sjph", "745", "220", "8", "#EFF0F2");
	sjph.addVariable("path", "<%=path%>/common/amchart/pie/ampie/");
	sjph.addVariable("settings_file", escape("<%=path%>/userFeature/showColumnLine?productId=${prodInfo.productId}&featureType=2"));
	sjph.addVariable("data_file",escape("<%=path%>/userFeature/showUserFeatureChart?1=1&showfor=2&productId=${prodInfo.productId};${productId_dp}&types=column;line&reportDate=${enddate}&featureType=2&rid="+Math.random()));
	sjph.addVariable("preloader_color", "#999999");
	sjph.addParam("wmode", "transparent");		
	sjph.addVariable("key", amchart_key);
	sjph.addVariable("chart_id", "sjph");
	sjph.write("chart-column-time");
	//是否使用无线
	var so7prod = new SWFObject("<%=path%>/common/amchart/pie/ampie/ampie1.swf", "ampie2", "200", "200", "8", "#EFF0F2");
	so7prod.addVariable("path", "<%=path%>/common/amchart/pie/ampie/");
	so7prod.addVariable("settings_file", escape("<%=path%>/common/amchart/pie/ampie/commonpie-settings-shownone.xml"));
	so7prod.addVariable("data_file",escape("<%=path%>/PieChart/showPieChart?kpiCode=${queryRealKpiCode.USER_CHARACTER_USED_WIRELESS_RATE_PIE_CHART};${queryRealKpiCode.USER_CHARACTER_NO_USE_WIRELESS_RATE_PIE_CHART}&color=B4C697;8AAA58&reportDate=${enddate}&dateType=<%=DwpasStKpiData.DATE_TYPE_OF_MONTH%>"));
	so7prod.addVariable("preloader_color", "#999999");
	so7prod.addParam("wmode", "transparent");		
	so7prod.addVariable("key", amchart_key);
	so7prod.write("chart-wuxian-prod");
	//是否使用无线-大盘 
	var so7dp = new SWFObject("<%=path%>/common/amchart/pie/ampie/ampie1.swf", "ampie2", "200", "200", "8", "#EFF0F2");
	so7dp.addVariable("path", "<%=path%>/common/amchart/pie/ampie/");
	so7dp.addVariable("settings_file", escape("<%=path%>/common/amchart/pie/ampie/commonpie-settings-shownone.xml"));
	so7dp.addVariable("data_file",escape("<%=path%>/PieChart/showPieChart?kpiCode=${queryRealKpiCode.USER_CHARACTER_USED_WIRELESS_OVERALL_PIE_CHART};${queryRealKpiCode.USER_CHARACTER_NO_USE_WIRELESS_OVERALL_PIE_CHART}&color=AF504C;CC9796&reportDate=${enddate}&dateType=<%=DwpasStKpiData.DATE_TYPE_OF_MONTH%>"));
	so7dp.addVariable("preloader_color", "#999999");
	so7dp.addParam("wmode", "transparent");	
	so7dp.addVariable("key", amchart_key);
	so7dp.write("chart-wuxian-dapan");
	
	//男女用户数大盘
	var so1dp = new SWFObject("<%=path%>/common/amchart/pie/ampie/ampie1.swf", "ampie2", "200", "190", "8", "#EFF0F2");
	so1dp.addVariable("path", "<%=path%>/common/amchart/pie/ampie/");
	so1dp.addVariable("settings_file", escape("<%=path%>/common/amchart/pie/ampie/commonpie-settings-shownone.xml"));
	so1dp.addVariable("data_file",escape("<%=path%>/PieChart/showPieChart?kpiCode=${queryRealKpiCode.USER_CHARACTER_FEMALE_USER_OVERALL_PIE_CHART};${queryRealKpiCode.USER_CHARACTER_MAN_USER_OVERALL_PIE_CHART}&reportDate=${enddate}&dateType=<%=DwpasStKpiData.DATE_TYPE_OF_MONTH%>"));
	so1dp.addParam("wmode", "transparent");	
	so1dp.addVariable("key", amchart_key);
	so1dp.write("chart-sex-dapan");
	//男女用户数产品
	var so1prod = new SWFObject("<%=path%>/common/amchart/pie/ampie/ampie1.swf", "so1prod", "200", "190", "8", "#EFF0F2");
	so1prod.addVariable("path", "<%=path%>/common/amchart/pie/ampie/");
	so1prod.addVariable("settings_file", escape("<%=path%>/common/amchart/pie/ampie/commonpie-settings-shownone.xml"));
	so1prod.addVariable("data_file",escape("<%=path%>/PieChart/showPieChart?kpiCode=${queryRealKpiCode.USER_CHARACTER_FEMALE_USER_RATE_PIE_CHART};${queryRealKpiCode.USER_CHARACTER_MAN_USER_RATE_PIE_CHART}&reportDate=${enddate}&dateType=<%=DwpasStKpiData.DATE_TYPE_OF_MONTH%>"));
	so1prod.addParam("wmode", "transparent");	
	so1prod.addVariable("key", amchart_key);
	so1prod.write("chart-sex-prouct");
	//大盘
	var params ={wmode:"transparent"};
	var flashVars ={
		path: "<%=path%>/common/amchart/column/amcolumn/",//${productDp.productId}
		settings_file: escape("<%=path%>/userFeature/showUserFeatureChart?productId=${prodInfo.productId}&types=column&reportDate=${enddate}&featureType=5"),
		chart_id:"chart-age-dapan",
		key:amchart_key
	};
	swfobject.embedSWF("<%=path%>/common/amchart/column/amcolumn/amcolumn.swf", "chart-age-dapan", "745", "210", "8.0.0", "<%=path%>/common/amchart/column/amcolumn/expressInstall.swf", flashVars, params);
	//年龄
	var params ={wmode:"transparent"};
	var flashVars ={
		path: "<%=path%>/common/amchart/column/amcolumn/",
		settings_file: escape("<%=path%>/userFeature/showUserFeatureChart?productId=${prodInfo.productId}&types=column&reportDate=${enddate}&featureType=3"),
		chart_id:"chart-age-prouct",
		key:amchart_key
	};
	swfobject.embedSWF("<%=path%>/common/amchart/column/amcolumn/amcolumn.swf", "chart-age-prouct", "745", "210", "8.0.0", "<%=path%>/common/amchart/column/amcolumn/expressInstall.swf", flashVars, params);
	
	//地区特征---中国地图
	var so = new SWFObject("<%=path%>/common/ammap/ammap/ammap.swf", "ammap", "800", "428", "8","f7f7f7");
		so.addVariable("path", "<%=path%>/common/ammap/ammap/");
		so.addVariable("data_file",escape("<%=path%>/userFeature/showAmmap?productId=${prodInfo.productId}&reportDate=${enddate}"));
		so.addVariable("settings_file", escape("<%=path%>/common/ammap/ammap_settings.xml"));
		so.addVariable("key", "破-解-4960-3541-中国-lele");//AMCHART-NETL-Cracked-10-10-1244
		so.write("chinaMap");
		
	var iframeContentHeight=document.body.scrollHeight;
	parent.changeIframeSize(iframeContentHeight);
});
function restartOpen(){
	  var queryDate=document.getElementById('queryDate').value;
	  if(queryDate!=""){
       location.href='<%=path%>/userFeature/PrepareUserFeature?queryMonth='+queryDate+'&menuId=${menuId}';           
	  }
}
//选择产品
function changeProduct(newProdId){
	document.location.href="<%=path%>/userFeature/PrepareUserFeature?menuId=${menuId}&queryMonth=${date}&productId="+newProdId;
}

function changeTheDate(n){
	  var reg=new RegExp("-","g");
	  var dt=document.getElementById('queryDate').value.replace(reg,"/")+"/01";
	  var today=new Date(new Date(dt).valueOf() );
	  today.setMonth(today.getMonth()+n);
	  var monthDate=today.getMonth()+1;
	  if((today.getMonth() + 1)<10){ 
  	  monthDate="0"+(today.getMonth() + 1);
	  }
	  var changedDate=today.getFullYear() + "-" + monthDate;
	  document.getElementById('queryDate').value=changedDate; 
	  if(changedDate!=''){
		  location.href='<%=path%>/userFeature/PrepareUserFeature?queryMonth='+changedDate+'&menuId=${menuId}';  
	  }
}

//帮助信息
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
	function helpShow3() {
		document.getElementById('help3').style.display = "";
	}
	function helpHidden3() {
		document.getElementById('help3').style.display = "none";
	}
	function helpShow4() {
		document.getElementById('help4').style.display = "";
	}
	function helpHidden4() {
		document.getElementById('help4').style.display = "none";
	}
</script>
</body>
</html>