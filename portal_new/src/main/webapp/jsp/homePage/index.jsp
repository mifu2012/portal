<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@page import="com.infosmart.portal.pojo.DwpasCKpiInfo"%>
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
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>${systemName}首页</title>
<style>
#tagr {
	background-color: #eff0f2
}
</style>
<link href="<%=path%>/common/css/base.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/common/css/lwt.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/common/css/cssz.css" rel="stylesheet" type="text/css" />
<script src="<%=path%>/common/js/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="<%=path%>/common/js/miaov.js" type="text/javascript"></script>
<script src="<%=path%>/common/js/amcharts.js" type="text/javascript"></script>
<script src="<%=path%>/common/js/raphael.js" type="text/javascript"></script>
<script src="<%=path%>/common/js/arale.js" type="text/javascript"></script>
<script src="<%=path%>/common/js/FusionCharts_Trial.js" type="text/javascript"></script>
<!-- <script src="https://static.alipay.com/ar/arale.core-1.1.js" type="text/javascript" charset="utf-8"></script> -->
<script src="<%=path%>/common/amchart/stock/amstock/swfobject.js" type="text/javascript"></script>
<script src="<%=path%>/common/js/amfallback.js" type="text/javascript"></script>
<script type="text/javascript">
var amchart_key ="${amchartKey};";
//业务笔数监控
var flashMovie;
var chartIsLoad=false;
function amChartInited (chart_id){
	flashMovie = document.getElementById("amline3");
	flashMovie.hideGraph(2);
}
E.domReady(function(){
	//风向标
	var xmlHttp;
	function callback(){
	   if(xmlHttp.readyState == 4){
       		if(xmlHttp.status == 200){
           		var tagcloud = new SWFObject("<%=path%>/common/js/my99tagcloud.swf", "tagcloud", "300", "270", "8", "#EFF0F2","high");
           		tagcloud.addParam("tplayername", "SWF");
           		tagcloud.addParam("splayername", "SWF");
           		tagcloud.addParam("type", "application/x-shockwave-flash");
           		tagcloud.addParam("mediawrapchecked", "true");
           		tagcloud.addParam("pluginspage", "http://www.macromedia.com/go/getflashplayer");
           		tagcloud.addParam("name", "tagcloudflash");
           		tagcloud.addParam("wmode", "transparent");
           		tagcloud.addParam("allowscriptaccess", "always");
           		tagcloud.addVariable("tcolor", "0x333333");
           		tagcloud.addVariable("mode", "tags");
           		tagcloud.addVariable("distr", "true");
           		tagcloud.addVariable("tspeed", "100");
           		tagcloud.addVariable("tagcloud", xmlHttp.responseText);
        		tagcloud.write("showWind");
             }
         }
	}
	function sendService()
	{      
	         if(window.XMLHttpRequest)
	         {
	             xmlHttp = new XMLHttpRequest();
	         }
	         xmlHttp.onreadystatechange=callback;
	         xmlHttp.open("POST","<%=path%>/index/showAskWind?queryDate=${date}",true);
	         xmlHttp.send(null); 
	}
	sendService();
	
   	//产品全图
    var so = new SWFObject("<%=path%>/common/amchart/pie/ampie/ampie1.swf", "ampie", "200", "180", "8.0.0", "#EFF0F2");
   	so.addVariable("path", "<%=path%>/common/amchart/pie/ampie/");
   	so.addVariable("settings_file", escape("<%=path%>/common/amchart/pie/ampie/commonpie-settings-showone-rate.xml"));
    so.addVariable("data_file",escape("<%=path%>/productAmchart/chartShow?queryDate=${date}&random="+Math.random()));
   	so.addVariable("key", amchart_key);
    so.addParam("wmode", "transparent");	
   	so.write("platFormUserPie");
	//业务笔数监控
<%-- 	var healthtre = new SWFObject("<%=path%>/common/amchart/amline/amline.swf", "amline3","684", "170", "8", "#FFFFFF");
	healthtre.addParam("wmode", "transparent");
	healthtre.addVariable("path", "<%=path%>/common/amchart/amline/");
	healthtre.addVariable("chart_id", "amline3");
	healthtre.addVariable("chart_settings", encodeURIComponent("#SLITERAL(${transAmchartSettings})"));
	healthtre.addVariable("chart_data", encodeURIComponent("#SLITERAL(${transAmchartData})"));
	healthtre.addVariable("key", amchart_key);
	healthtre.write("prodTrans");
	
	$A($$(".J-fjd")).each(function(el){
		el.click(function(){
				if(el.hasClass("active")){
					el.removeClass("active");
					flashMovie.hideGraph(el.attr('data-id'));
				}else{
					el.addClass("active");
					flashMovie.showGraph(el.attr('data-id'));
				} 
		});
	}); --%>
	//销售数量监控
	var kpiCodes="CUS3001010401D;CUS300101A101D;CUS2001030101D";
	/*
	 var healthtre = new SWFObject("<%=path%>/common/amchart/stock/amstock/amstock.swf", "amline3", "684", "263", "8", "#EFF0F2");
		healthtre.addParam("wmode", "opaque");
		healthtre.addVariable("path", "<%=path%>/common/amchart/stock/amstock/");
		healthtre.addVariable("chart_id", "amline3");
		var url="<%=basePath%>/stockChart/showStockChart?1=1&kpiCodes="+kpiCodes+";&needPercent=0&kpiType=<%=DwpasCKpiInfo.KPI_TYPE_OF_DAY%>&reportDate=${date}&rid="+Math.random();
		healthtre.addVariable("settings_file", escape(url));//月统计
		healthtre.addVariable("key", amchart_key);
		healthtre.write("prodTrans");
		*/
	 var healthtre = new SWFObject("<%=path%>/common/amchart/column/amcolumn/amcolumn.swf", "chartdiv", "684", "263", "8", "#EFF0F2");
		healthtre.addParam("wmode", "transparent");
		healthtre.addVariable("path", "<%=path%>/common/js");
		healthtre.addVariable("chart_id", "chartdiv");
		var url="<%=basePath%>/columnOrLineChart/showChartSetting.html?1=1&kpiCodes="+kpiCodes+";&needPercent=0&kpiType=<%=DwpasCKpiInfo.KPI_TYPE_OF_DAY%>&reportDate=${date}&chartTypes=column;column;line&rid="+Math.random();
		healthtre.addVariable("settings_file", escape(url));//设置文件
		var dataUrl="<%=basePath%>/columnOrLineChart/showChartData.html?1=1&kpiCodes="+kpiCodes+";&needPercent=0&kpiType=<%=DwpasCKpiInfo.KPI_TYPE_OF_DAY%>&reportDate=${date}&chartTypes=column;column;line&rid="+Math.random();
		healthtre.addVariable("data_file", escape(dataUrl));//数据文件
		healthtre.addVariable("key", amchart_key);
		healthtre.write("prodTrans");
	//流量监控趋势图
	$A($$(".box-nav-li")).each(function(el){
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
	//ip
    var charttread = new SWFObject("<%=path%>/common/amchart/stock/amstock/amstock.swf", "ampie", "684", "300", "8", "#EFF0F2");
    charttread.addVariable("path", "<%=path%>/common/amchart/stock/amstock/");
    charttread.addVariable("settings_file", escape("<%=path%>/stockChart/showStockChart?1=1&kpiCodes=CUS3001010401D&needPercent=0&kpiType=<%=DwpasCKpiInfo.KPI_TYPE_OF_DAY%>&reportDate=${date}"));
    charttread.addParam("wmode", "transparent");
	charttread.addVariable("key", amchart_key);
	charttread.addVariable("chart_id","ampie");
    charttread.write("J-useAnlbox");
    //pv
    var charttread = new SWFObject("<%=path%>/common/amchart/stock/amstock/amstock.swf", "ampie", "684", "300", "8", "#EFF0F2");
    charttread.addVariable("path", "<%=path%>/common/amchart/stock/amstock/");
    charttread.addVariable("settings_file", escape("<%=path%>/stockChart/showStockChart?1=1&kpiCodes=CUS1020002601D&needPercent=0&kpiType=<%=DwpasCKpiInfo.KPI_TYPE_OF_DAY%>&reportDate=${date}"));
    charttread.addParam("wmode", "transparent");
	charttread.addVariable("key", amchart_key);
	charttread.addVariable("chart_id","ampie");
    charttread.write("J-peopleAnlbox");
    //iv
    var charttread = new SWFObject("<%=path%>/common/amchart/stock/amstock/amstock.swf", "ampie", "684", "300", "8", "#EFF0F2");
    charttread.addVariable("path", "<%=path%>/common/amchart/stock/amstock/");
    charttread.addVariable("settings_file", escape("<%=path%>/stockChart/showStockChart?1=1&kpiCodes=CUS200101BD01D&needPercent=0&kpiType=<%=DwpasCKpiInfo.KPI_TYPE_OF_DAY%>&reportDate=${date}"));
    charttread.addParam("wmode", "transparent");
	charttread.addVariable("key", amchart_key);
	charttread.addVariable("chart_id","ampie");
    charttread.write("J-areabox");
    
    //注册用户分析趋势图
     var kpiCodes="CUS5031200301D;CUS5031300301D;CUS5031100301D;CUS5031400301D";
	 var healthtre = new SWFObject("<%=path%>/common/amchart/stock/amstock/amstock.swf", "amline3", "684", "280", "8", "#EFF0F2");
		healthtre.addParam("wmode", "opaque");
		healthtre.addVariable("path", "<%=path%>/common/amchart/stock/amstock/");
		healthtre.addVariable("chart_id", "amline3");
		var url="<%=basePath%>/stockChart/showStockChart?1=1&kpiCodes="+kpiCodes+";&needPercent=0&kpiType=<%=DwpasCKpiInfo.KPI_TYPE_OF_DAY%>&reportDate=${date}&rid="+Math.random();
		healthtre.addVariable("settings_file", escape(url));//月统计
		healthtre.addVariable("key", amchart_key);
		healthtre.write("ProductDevDiv");
		
	//自适应
		var iframeContentHeight = document.body.scrollHeight;
		parent.changeIframeSize(iframeContentHeight);
    
});

	/* 修改日期刷新界面 */
	function restartOpen(){
		  var queryDate=document.getElementById('maxDate').value;
		  if(queryDate!=""){
	       location.href='<%=path%>/index/getInto?queryDate='+queryDate+'&menuId=${menuId}';
		  }
	}
	//显示与隐藏业务笔数明细与支付宝趋势图
	/* function showDisplay(dispalyDiv1,dispalyDiv2){
	  document.getElementById(dispalyDiv2).style.display = "none";
	  document.getElementById(dispalyDiv1).style.display = "block";
	}
	function showHide(hideDiv){
	  document.getElementById(hideDiv).style.display = "none";
	} */
	//帮助div的显示与隐藏
	function showHelpDiv(modelName){
		  document.getElementById('disyc'+modelName).style.display="block";
		}
   function hideHelpDiv(modelName){
		  document.getElementById('disyc'+modelName).style.display="none";
		}
   //产品聚焦大事件谈窗
   function pop_func(eventTitle,eventTypeName,eventStartDate,eventId){
	     var eventRelCodes= document.getElementById("div_"+eventId).innerHTML;
		 document.getElementById('dsjtck_pop').className='pop_block';
		 document.getElementById('eventDate').innerHTML=eventStartDate;
		 document.getElementById('eventTypeName').innerHTML=eventTypeName;
		 document.getElementById('eventTitle').innerHTML=eventTitle;
		 document.getElementById('bigEventContent').innerHTML=document.getElementById("evnetContent_"+eventId).innerHTML;
		
		 var so4 = new SWFObject("<%=path%>/common/amchart/stock/amstock/amstock.swf", "amline2", "800", "300", "9", "#EFF0F2");
		 so4.addVariable("path", "<%=path%>/common/amchart/stock/amstock/");
		 so4.addVariable("chart_id", "amline2");
		 so4.addParam("wmode","transparent");
		 var url4="<%=path%>/stockChart/showStockChart?1=1&kpiCodes="+eventRelCodes+"&needPercent=1&isMIS=isMIS&rid="+Math.random();
		 so4.addVariable("settings_file", escape(url4));
		 so4.addVariable("key", amchart_key);
		 so4.write("chartdiv4");
   }
   
   
   //日期控件左右点击事件
   function changeTheDate(n){
		  var reg=new RegExp("-","g");
		  var dt=document.getElementById('theDate').value.replace(reg,"/");
		  var today=new Date(new Date(dt).valueOf() + n*24*60*60*1000);
		  var dayDate=today.getDate();
		  var monthDate=(today.getMonth() + 1);
		  if(today.getDate()<10){
			  dayDate="0"+today.getDate();
		  }
	      if((today.getMonth() + 1)<10){ 
	    	  monthDate="0"+(today.getMonth() + 1);
		  }
		  var changedDate=today.getFullYear() + "-" + monthDate + "-" + dayDate;
		  document.getElementById('maxDate').value=changedDate; 
		  if(changedDate!=''){
			  location.href='<%=path%>/index/getInto?queryDate='+changedDate+'&menuId=${menuId}';
		  }
	  }
</script>
<style>
.ddy_index_input {
	width: 110px;
	height: 20px;
	border: 0px;
	background-repeat: no-repeat;
	text-align: center;
	background-image: url('<%=path%>/common/images/input_button_1.gif');
}
</style>
</head>
<body class="indexbody2">
	<!--大事件弹窗开始-->
	<div class="pop_none" id="dsjtck_pop">
		<div class="layer" style="z-index: 999">
			<div class="layer2"
				style="background-color: #fff; z-index: 9999; margin-top: 310px"
				id="dsjtck_pop_title">
				<table border="0">
					<tr>
						<td colspan="3">
							<h2>
								查看详情 <b> <a href="Javascript:void(0)"
									onclick="javascript:getElementById('dsjtck_pop').className='lwtpop_gb'">关闭</a>
								</b>
							</h2>
						</td>
					</tr>
					<tr>
						<td colspan="3">
							<div class="layer2_mm">
								<div align="left"></div>
							</div>
						</td>
					</tr>
					<tr>
						<td  align="center" colspan="3">
							<div style="width:700px;font-size: 14px;font-weight: bold;" id="eventTitle">
							</div>
						</td>
					</tr>
					<tr>
						<td colspan="3">
							<div class="layer2_mm">
								<div align="left"></div>
							</div>
						</td>
					</tr>
					<tr>
						<td width="200">
							<div style="float: left; padding: 0 10px">发生时间:</div>
							<div id="eventDate" style="float: left; padding: 0 10px"></div>
						</td>
						<td width="200">
							<div style="float: left; padding: 0 10px">事件类别：</div>
							<div id="eventTypeName" style="float: left; padding: 0 10px"></div>
						</td>
						<td>&nbsp;
						</td>
					</tr>
					<tr>
						<td colspan="3">
							<div class="layer2_mm">
								<div align="left"></div>
							</div>
						</td>
					</tr>
					<tr>
						<td colspan="3">
							<div  id="bigEventContent"  style="position: relative;width: 800px; height:100px;overflow-y:auto;margin: 0px 0px 0px 10px">
							    
							</div>
						</td>
					</tr>
					<tr>
						<td colspan="3">
							<div class="layer2_mm">
								<div align="left"></div>
							</div>
						</td>
					</tr>
					<tr>
						<td colspan="3">
							<div align="center" id="chartdiv4" style="padding-top: 0px"></div>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<div class="new_jwy_yy">
		<!-- 当前位置 -->
		<div class="kpi_position">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="78%">
						<div style="color: #6a6a6a; padding-left: 30px; padding-top: 5px">
							当前位置： <a href='<%=path%>/index/getInto.html?&menuId=${menuId}'>首页</a>
						</div>
					</td>
					<td width="22%">
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							style="padding-top: 5px">
							<tr>
								<td>
									<div style="padding-top: 4px">
										<span style="vertical-align: middle;"> 数据时间: </span> <a
											href="javascript:void(0);" onclick="changeTheDate(-1);">
											<img style="vertical-align: middle;" align="middle"
											src="<%=path%>/common/images/ddy_2.gif" width="11"
											height="23" border="0" />
										</a> <input id="maxDate" name="maxDate" type="text"
											class="ddy_index_input" value="${date}" readonly="readonly"
											onclick="WdatePicker({isShowClear:false,readOnly:true,startDate:'${date}',dateFmt:'yyyy-MM-dd ',alwaysUseStartDate:true})"
											onchange="restartOpen();" style="cursor: pointer;" /> <a
											href="javascript:void(0);" onclick="changeTheDate(1);"> <img
											style="vertical-align: middle;" align="middle"
											src="<%=path%>/common/images/ddy_1.gif" width="11"
											height="23" border="0" />
										</a>
									</div>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		<!-- 当前位置结束 -->
		<div class="index_home">
			<div class="ddy_channel2"></div>
			<input id="theDate" value="${date }" type="hidden" />
			<!--用户求助-->
			<div class="clear" style="margin-top: 7px"></div>
			<div style="width: 1000px; height: 300px; overflow: hidden">
				<div class="tabdata02_cdy">
					<div class="datat02_new">
						<div class="index_title_pd " style="float: left; width: 600px">
							<table>
								<tr>
									<td><img src="<%=path%>/common/images/new_jwy_4.gif" /></td>
									<td>&nbsp;${moduleInfoMap.INDEX_ALERT_DASHBOARD_CHART.moduleName}</td>
								</tr>
							</table>
						</div>
						<div class="box-askicon"
							onmousemove="showHelpDiv('INDEX_ALERT_DASHBOARD_CHART')"
							onmouseout="hideHelpDiv('INDEX_ALERT_DASHBOARD_CHART')"
							style="cursor: pointer">
							<img src="<%=path%>/common/images/askicon5.png" />
						</div>
						<!-- 销售数量监控帮助信息 -->
						<div id="disycINDEX_ALERT_DASHBOARD_CHART"
							style="display: none; font-size: 12px;">
							<div style="padding: 32px 0px 0px 420px;">
								<div style="width: 250px" class="tip_33">
									<p>
										<b>${moduleInfoMap.INDEX_ALERT_DASHBOARD_CHART.moduleName}：</b>
									</p>
									<p style="font-weight: normal;">
										${moduleInfoMap.INDEX_ALERT_DASHBOARD_CHART.remark}</p>
								</div>
							</div>
						</div>
					</div>
					<div id="prodTrans"></div>
				</div>
				<div class="tabdata01"
					style="overflow: hidden; width: 300px; height: 295px;">
					<div class="datat012">
						<div class="index_title_pd">
							<table>
								<tr>
									<td><img src="<%=path%>/common/images/new_jwy_4.gif" /></td>
									<td>&nbsp;${moduleInfoMap.INDEX_USER_HELP_KEYWORD_LIST.moduleName}</td>
								</tr>
							</table>
						</div>
						<div class="box-askicon"
							onmousemove="showHelpDiv('INDEX_USER_HELP_KEYWORD_LIST')"
							onmouseout="hideHelpDiv('INDEX_USER_HELP_KEYWORD_LIST')"
							style="cursor: pointer">
							<img src="<%=path%>/common/images/askicon5.png" />
						</div>
						<!-- 用户咨询风向标帮助信息 -->
						<div id="disycINDEX_USER_HELP_KEYWORD_LIST"
							style="display: none; font-size: 12px;">
							<div style="padding: 32px 0px 0px 35px">
								<div style="position: relative">
									<div style="width: 250px" class="tip_33">
										<p>
											<b>${moduleInfoMap.INDEX_USER_HELP_KEYWORD_LIST.moduleName}：</b>
										</p>
										<p style="font-weight: normal;">
											${moduleInfoMap.INDEX_USER_HELP_KEYWORD_LIST.remark}</p>
									</div>
								</div>
							</div>
						</div>
					</div>

					<!--风向标JS-->
					<div id="div"
						style="border: 1px solid #dbe1e6; border-top: 0px; border-bottom: 0px; height: auto;"
						class="fxb_bgcolor">
						<div id="showWind"></div>
						<!--风向标结束-->
					</div>
					<table
						style="height: 32px; border: 1px solid #dbe1e6; border-top: 0px; width: 302px; float: left; background: #eff0f2;">
						<tr>
							<td>&nbsp;</td>
						</tr>
					</table>
					<div class="clear"></div>
				</div>
			</div>
			<div class="clear"></div>
			<!--第一部分结束-->
			<!--第二部分-->
			<div class="clear"></div>
			<div class="tabdata02_cdy">
				<!--业务笔数监控结束-->
				<div class="datat02">
					<div class="index_title_pd" style="float: left; width: 600px">
						<table>
							<tr>
								<td><img src="<%=path%>/common/images/new_jwy_4.gif" /></td>
								<td>&nbsp;${moduleInfoMap.INDEX_RECTANGLE_CHART.moduleName}</td>
							</tr>
						</table>
					</div>
					<div class="box-askicon"
						onmousemove="showHelpDiv('INDEX_RECTANGLE_CHART')"
						onmouseout="hideHelpDiv('INDEX_RECTANGLE_CHART')"
						style="cursor: pointer">
						<img src="<%=path%>/common/images/askicon5.png" />
					</div>
					<!-- 流量监控帮助信息-->
					<div id="disycINDEX_RECTANGLE_CHART"
						style="display: none; font-size: 12px;">
						<div style="padding: 32px 0px 0px 420px">
							<div style="width: 250px" class="tip_33">
								<p>
									<b>${moduleInfoMap.INDEX_RECTANGLE_CHART.moduleName}：</b>
								</p>
								<p style="font-weight: normal;">
									${moduleInfoMap.INDEX_RECTANGLE_CHART.remark}</p>
							</div>
						</div>
					</div>
				</div>

				<div class="box-nav">
					<ul class="box-nav-ul">
						<li class="box-nav-li box-li-act" id="J-useAnl">IP</li>
						<li class="box-nav-li" id="J-peopleAnl">PV</li>
						<li class="box-nav-li" id="J-area">UV</li>
					</ul>
				</div>
				<div class="normalbox mt15 J-tabbox" id="J-useAnlbox"></div>
				<div class="normalbox mt15 J-tabbox hidden" id="J-peopleAnlbox">
				</div>
				<div class="normalbox mt15 J-tabbox hidden" id="J-areabox"></div>
			</div>
			<!--产品全图开始-->
			<div class="tabdata01_cdy"
				style="width: 302px; background: #eff0f2; overflow: hidden; height: auto;">
				<div class="datat012">
					<div class="index_title_pd" style="float: left; width: 200px">
						<table>
							<tr>
								<td><img src="<%=path%>/common/images/new_jwy_4.gif" /></td>
								<td>&nbsp;${moduleInfoMap.INDEX_PRODUCT_CHART.moduleName}</td>
							</tr>
						</table>

					</div>

					<div class="box-askicon"
						onmousemove="showHelpDiv('INDEX_PRODUCT_CHART')"
						onmouseout="hideHelpDiv('INDEX_PRODUCT_CHART')"
						style="cursor: pointer">
						<img src="<%=path%>/common/images/askicon5.png" />
					</div>
					<!-- 产品全图帮助信息 -->
					<div id="disycINDEX_PRODUCT_CHART"
						style="display: none; font-size: 12px;">
						<div style="padding: 32px 0px 0px 35px">
							<div style="position: relative">
								<div style="width: 250px" class="tip_33">
									<p>
										<b>${moduleInfoMap.INDEX_PRODUCT_CHART.moduleName}：</b>
									</p>
									<p style="font-weight: normal;">
										${moduleInfoMap.INDEX_PRODUCT_CHART.remark}</p>
								</div>
							</div>
						</div>
					</div>
				</div>


				<div id="tagr"
					style="height: auto; width: 300px; background: #eff0f2; border: 1px solid #dbe1e6; border-top: 0px; padding-bottom: 5px; overflow: hidden;">
					<table border="0" cellspacing="0" cellpadding="0"
						style="margin-left: 12px; margin-bottom: 10px; line-height: 25px;height:75px; ">
						<tr>
							<td width="100px">
								<table border="0" cellspacing="0" cellpadding="0" width="100px"
									class="index-box">
									<tr style="line-height: 20px">
										<td width="100px" style="border-bottom: 1px solid #fff"
											class="<c:if test="${platFormDataTB.trend eq 'up'}">proall-top-left-up</c:if>
								<c:if test="${platFormDataTB.trend eq 'down'}"> proall-top-left-down</c:if>
								<c:if test="${platFormDataTB.trend eq 'bal'}">proall-top-left-equal</c:if> fchencolor"><span
											style="font-size: 16px; font-weight: bold; padding-left: 10px">WAP</span><br />
											<span style="font-size: 16px; padding-left: 10px">${platFormDataTB.value}</span>
										</td>
										<td width="20px" style="border-bottom: 1px solid #fff"><c:if
												test="${platFormDataTB.trend eq 'up'}">

												<img src="<%=path%>/common/images/lwt_5.png" alt="上升" />

											</c:if> <c:if test="${platFormDataTB.trend eq 'down'}">

												<img src="<%=path%>/common/images/lwt_6 .png" alt="下降" />

											</c:if></td>
									</tr>
									<tr style="line-height: 20px">
										<td width="100px" style="border-bottom: 1px solid #fff"
											class="<c:if test="${platFormDataOUT.trend eq 'up'}">proall-top-left-up</c:if>
								<c:if test="${platFormDataOUT.trend eq 'down'}"> proall-top-left-down</c:if>
								<c:if test="${platFormDataOUT.trend eq 'bal'}">proall-top-left-equal</c:if> fqincolor"><span
											style="font-size: 16px; font-weight: bold; padding-left: 10px">外部</span><br />
											<span style="font-size: 16px; padding-left: 10px">${platFormDataOUT.value}</span>
										</td>
										<td width="20px" style="border-bottom: 1px solid #fff"><c:if
												test="${platFormDataOUT.trend eq 'up'}">

												<img src="<%=path%>/common/images/lwt_5.png" alt="上升" />

											</c:if> <c:if test="${platFormDataOUT.trend eq 'down'}">

												<img src="<%=path%>/common/images/lwt_6 .png" alt="下降" />

											</c:if></td>
									</tr>
									<tr style="line-height: 20px">
										<td width="100px" style="border-bottom: 1px solid #fff"
											class="<c:if test="${platFormDataIN.trend eq 'up'}">proall-top-left-up</c:if>
								<c:if test="${platFormDataIN.trend eq 'down'}"> proall-top-left-down</c:if>
								<c:if test="${platFormDataIN.trend eq 'bal'}">proall-top-left-equal</c:if> fzicolor"
											style="border-bottom:none;"><span
											style="font-size: 16px; font-weight: bold; padding-left: 10px">站内</span><br />
											<span style="font-size: 16px; padding-left: 10px">${platFormDataIN.value}</span>
										</td>
										<td width="20px" style="border-bottom: 1px solid #fff"><c:if
												test="${platFormDataIN.trend eq 'up'}">

												<img src="<%=path%>/common/images/lwt_5.png" alt="上升" />

											</c:if> <c:if test="${platFormDataIN.trend eq 'down'}">

												<img src="<%=path%>/common/images/lwt_6 .png" alt="下降" />

											</c:if></td>
									</tr>
								</table>
							</td>
							<td width="135px" style="text-align: left">
								<div id="platFormUserPie"></div>
							</td>
						</tr>
					</table>
					<br /> <br />
					<div style="width: 150px; float: left; background: #eff0f2;">
						<table border="0" cellspacing="0" cellpadding="0"
							style="line-height: 26px; margin-left: 3px">
							<tr>
								<td
									style="width: 90px; border-bottom: 1px solid #ccc; overflow: hidden;">&nbsp;今日销售量</td>
								<td style="width: 50px; border-bottom: 1px solid #ccc">100,456
								</td>
							</tr>

						</table>
					</div>


					<div style="width: 150px; float: left; background: #eff0f2;">
						<table border="0" cellspacing="0" cellpadding="0"
							style="line-height: 26px; margin-left: 3px">
							<tr>
								<td
									style="width: 90px; border-bottom: 1px solid #ccc; overflow: hidden;">目标销售额</td>
								<td style="width: 50px; border-bottom: 1px solid #ccc">156,000
								</td>
							</tr>

						</table>
					</div>
					 <br />
					<div style="width: 150px; float: left; background: #eff0f2;">
						<table border="0" cellspacing="0" cellpadding="0"
							style="line-height: 26px; margin-left: 3px">
							<tr>
								<td
									style="width: 90px; border-bottom: 1px solid #ccc; overflow: hidden;">&nbsp;月累计销售</td>
								<td style="width: 50px; border-bottom: 1px solid #ccc">567,903
								</td>
							</tr>

						</table>
					</div>


					<div style="width: 150px; float: left; background: #eff0f2;">
						<table border="0" cellspacing="0" cellpadding="0"
							style="line-height: 26px; margin-left: 3px">
							<tr>
								<td
									style="width: 90px; border-bottom: 1px solid #ccc; overflow: hidden;">目标销售额</td>
								<td style="width: 50px; border-bottom: 1px solid #ccc">604,097
								</td>
							</tr>

						</table>
					</div>

					<br />


					<div style="width: 150px; float: left; background: #eff0f2;">
						<table border="0" cellspacing="0" cellpadding="0"
							style="line-height: 26px; margin-left: 3px">
							<tr>
								<td
									style="width: 90px; border-bottom: 1px solid #ccc; overflow: hidden;">&nbsp;年累计销售</td>
								<td style="width: 50px; border-bottom: 1px solid #ccc">1,987,346,090
								</td>
							</tr>

						</table>
					</div>


					<div style="width: 150px; float: left; background: #eff0f2;">
						<table border="0" cellspacing="0" cellpadding="0"
							style="line-height: 26px; margin-left: 3px">
							<tr>
								<td
									style="width: 90px; border-bottom: 1px solid #ccc; overflow: hidden;">&nbsp;目标销售额</td>
								<td style="width: 50px; border-bottom: 1px solid #ccc">8,007,340,000
								</td>
							</tr>

						</table>
					</div>
					<br />
					<div style="width: 150px; float: left; background: #eff0f2;">
						<table border="0" cellspacing="0" cellpadding="0"
							style="line-height: 26px; margin-left: 3px">
							<tr>
								<td
									style="width: 90px; border-bottom: 1px solid #ccc; overflow: hidden;">&nbsp;总用户数</td>
								<td style="width: 50px; border-bottom: 1px solid #ccc">4,028,360,000
								</td>
							</tr>

						</table>
					</div>
					<div style="width: 150px; float: left; background: #eff0f2;">
						<table border="0" cellspacing="0" cellpadding="0"
							style="line-height: 26px; margin-left: 3px">
							<tr>
								<td
									style="width: 90px; border-bottom: 1px solid #ccc; overflow: hidden;">总销量数</td>
								<td style="width: 50px; border-bottom: 1px solid #ccc">10,106,354,000
								</td>
							</tr>

						</table>
					</div>
				</div>

			</div>
			<div class="clear"></div>
			<!--产品全图结束-->
			<!--第二部分结束-->
			<!--第三部分-->
			<div class="clear"></div>
			<div class="tabdata02_cdy" style="height: 350px">
				<div class="datat02">
					<div class="index_title_pd">
						<table>
							<tr>
								<td><img src="<%=path%>/common/images/new_jwy_4.gif" /></td>
								<td>&nbsp;${moduleInfoMap.INDEX_USER_COUNT_TABLE.moduleName}</td>
							</tr>
						</table>

					</div>
					<div class="box-askicon"
						onmousemove="showHelpDiv('INDEX_USER_COUNT_TABLE')"
						onmouseout="hideHelpDiv('INDEX_USER_COUNT_TABLE')"
						style="cursor: pointer">
						<img src="<%=path%>/common/images/askicon5.png" />
					</div>
					<!--用户数帮助信息-->
					<div id="disycINDEX_USER_COUNT_TABLE"
						style="display: none; font-size: 12px;">
						<div style="padding: 32px 0px 0px 420px">
							<div style="width: 250px" class="tip">
								<p>
									<b>${moduleInfoMap.INDEX_USER_COUNT_TABLE.moduleName}：</b>
								</p>
								<p style="font-weight: normal;">
									${moduleInfoMap.INDEX_USER_COUNT_TABLE.remark}</p>
							</div>
						</div>
					</div>
				</div>
				<div id="tagr"
					style="padding: 10px 0px 0px 0px; height: 300px; background: #eff0f2; border: 1px solid #dbe1e6; border-top: 0px;">
					<div id="ProductDevDiv"></div>
				</div>
			</div>
			<!--销量报表结束-->
			<div class="tabdata01_cdy"
				style="width: 302px; background: #eff0f2; overflow: hidden; height: 350px">
				<div class="datat012">
					<div class="index_title_pd">

						<table>
							<tr>
								<td><img src="<%=path%>/common/images/new_jwy_4.gif" /></td>
								<td>&nbsp;${moduleInfoMap.INDEX_IMPORTANT_EVENT_LIST.moduleName}</td>
							</tr>
						</table>
					</div>
					<div class="box-askicon"
						onmousemove="showHelpDiv('INDEX_IMPORTANT_EVENT_LIST')"
						onmouseout="hideHelpDiv('INDEX_IMPORTANT_EVENT_LIST')"
						style="cursor: pointer">
						<img src="<%=path%>/common/images/askicon5.png" />
					</div>
					<!--产品聚焦即大事件帮助信息-->
					<div id="disycINDEX_IMPORTANT_EVENT_LIST"
						style="display: none; font-size: 12px; z-index: 999">
						<div style="padding: 32px 0px 0px 35px">
							<div style="position: relative">
								<div style="width: 250px" class="tip">
									<p>
										<b>${moduleInfoMap.INDEX_IMPORTANT_EVENT_LIST.moduleName}：</b>
									</p>
									<p style="font-weight: normal;">
										${moduleInfoMap.INDEX_IMPORTANT_EVENT_LIST.remark}</p>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div id="tagr"
					style="padding: 10px 0px 0px 0px; height: 350px; background: #eff0f2; border: 1px solid #dbe1e6; border-top: 0px;">
					<c:forEach items="${profocuslist}" var="profocus">
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							style="color: #666; line-height: 30px">

							<tr>
								<td>【${profocus.eventTypeName}】<a href="#"
								id="${profocus.eventId}"
								onclick="pop_func('${profocus.title}','${profocus.eventTypeName}','${profocus.eventStartDate}','${profocus.eventId}')">${profocus.titleShow}</a>
								<div id="evnetContent_${profocus.eventId}"
									style="display: none;">${profocus.content}</div>
							</td>
							</tr>
						</table>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							style="color: #666"></table>
					</c:forEach>

				</div>
			</div>
			<c:forEach items="${profocuslist}" var="erkl">
				<script language='javascript'>
			  var obj=document.getElementById("div_${erkl.eventId}");
			  if(obj==null)
			   {
				  obj=document.createElement("div");
				  obj.id="div_${erkl.eventId}";
				  obj.innerHTML="${erkl.relateKpiCodes}";
				  obj.style.display="none";
				  document.body.appendChild(obj);
			   }else
				{
				   obj.innerHTML=obj.innerHTML+";"+"${erkl.relateKpiCodes}";
				}
			</script>
			</c:forEach>
			<div class="clear"></div>
		</div>
	</div>
</body>
</html>
