<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="com.infosmart.portal.pojo.DwpasCKpiInfo"%>
<%@ page import="java.util.Date"%>
<%@page import="com.infosmart.portal.util.Const"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String indexMenuId =(String) session.getAttribute(Const.INDEX_MENU_ID);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>${systemName}</title>
<%-- <link href="<%=path%>/common/css/css.css" rel="stylesheet"
	type="text/css" /> --%>
<link href="<%=path%>/common/css/cssz.css" rel="stylesheet"
	type="text/css" />
<link href="<%=path%>/common/css/style.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript"
	src="<%=path%>/common/js/My97DatePicker/My98MonthPicker.js"></script>
<script type="text/javascript"
	src="<%=path%>/common/js/My97DatePicker/WdatePicker.js"></script>
<script src="<%=path%>/common/amchart/column/amcolumn/swfobject.js"
	type="text/javascript"></script>
<script src="<%=path%>/common/amchart/column/amcolumn/swfobject1.js"
	type="text/javascript"></script>
<script src="<%=path%>/common/amchart/stock/amstock/swfobject.js"
	type="text/javascript"></script>
<script type="text/javascript" src="<%=path%>/common/js/amcharts.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/amfallback.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/raphael.js"></script>
<script type="text/javascript"
	src="<%=path%>/common/js/jquery-1.4.3.min.js"></script>
<script type="text/javascript">
 var amchart_key ="${amchartKey}"; //"110-037eaff57f02320aee1b8576e4f4062a";
 window.onload = function () {
	 var myKpiType=document.getElementById('myType').value;
 	var d=document.getElementById('byDay');
 	var m=document.getElementById('byMonth');
 	var dayDate=document.getElementById('maxDate');
 	var monthDate=document.getElementById('monthDate'); 
 	var wda=document.getElementById('wd');
 	var wdaa=document.getElementById('wd2');
 	if(myKpiType==<%=DwpasCKpiInfo.KPI_TYPE_OF_DAY%>){
 		
 		document.getElementById('maxDate').value=document.getElementById('theDate').value;
 		wda.className='bgwd';
 		wda.style.color="#fff";
 		wdaa.className="bgwd2";
  		dayDate.style.display="";
 		monthDate.style.display="none"; 
 	}else if(myKpiType==<%=DwpasCKpiInfo.KPI_TYPE_OF_MONTH%>){
 		document.getElementById('monthDate').value=document.getElementById('theDate').value;
 		wdaa.className='bgwd';
 		wda.className="bgwd2";
  		dayDate.style.display="none";
 		monthDate.style.display=""; 
 	}	
	 var queryDate =${productData.endDate};
	 var kpitype = ${productData.kpiType};
	 //产品使用增长趋势图
	 var kpiCode_1="${productKpiCode.PRODUCT_TOTAL_TRANSACTIONS_AMOUNT.kpiCode};${productKpiCode.PRODUCT_TOTAL_TRANSACTIONS_TIMES.kpiCode}";
	 if(myKpiType==<%=DwpasCKpiInfo.KPI_TYPE_OF_MONTH%>){
		 //新：${productKpiCode.PRODUCT_MONTH_TOTAL_TRANSACTIONS_AMOUNT_NEW_1.kpiCode};
         kpiCode_1="${productKpiCode.PRODUCT_MONTH_TOTAL_TRANSACTIONS_AMOUNT_NEW_1.kpiCode};${productKpiCode.PRODUCT_MONTH_TOTAL_TRANSACTIONS_TIMES_NEW_1.kpiCode}";
	 }
	 var so1 = new SWFObject("<%=path%>/common/amchart/stock/amstock/amstock.swf?id=<%=new Date().getTime()%>", "amline1", "688", "295", "8", "#EFF0F2");
	 so1.addVariable("path", "<%=path%>/common/amchart/stock/amstock/");
	 so1.addVariable("chart_id", "amline1");
	 so1.addParam("wmode","transparent");
	 //业务量、业务笔数
	 var url1="<%=path%>/stockChart/showStockChart?1=1&isPrd=1&kpiCodes="+kpiCode_1+"&needPercent=1&kpiType=${productData.kpiType}&reportDate=";
	 if(kpitype == 1)
	 {
	 url1+=queryDate;
	 }
	 else
	 {
	 url1+=queryDate;
	 }
	 url1+="&color=8aac57;dc3c28&rnd="+Math.random();
	 so1.addVariable("settings_file", escape(url1));
	 so1.addVariable("key", amchart_key);
	 //指标Codes
	 so1.addVariable("kpiCodes", kpiCode_1);
	 //日期类型
	 so1.addVariable("kpiType", "${productData.kpiType}");
	 //产品Id
	 so1.addVariable("productId", "${productId}");
	 
	 so1.write("chartdiv1");

     //产品使用用户数趋势图
	 var kpiCode_2="${productKpiCode.PRODUCT_DAY_USER_COUNT.kpiCode};${productKpiCode.PRODUCT_TOTAL_USER_COUNT.kpiCode}";
	 if(myKpiType==<%=DwpasCKpiInfo.KPI_TYPE_OF_MONTH%>){
         kpiCode_2="${productKpiCode.PRODUCT_MONTH_USER_COUNT_NEW_1.kpiCode};${productKpiCode.PRODUCT_MONTH_TOTAL_USER_COUNT_NEW_1.kpiCode}";
	 }
	 var so2 = new SWFObject("<%=path%>/common/amchart/stock/amstock/amstock.swf?id=<%=new Date().getTime()%>", "amline2", "688", "295", "8", "#EFF0F2");
	 so2.addVariable("path", "<%=path%>/common/amchart/stock/amstock/");
	 so2.addVariable("chart_id", "amline2");
	 so2.addParam("wmode","transparent");
	 //产品用户数、累计产品用户数
	 var url2="<%=path%>/stockChart/showStockChart?1=1&isPrd=1&kpiCodes="+kpiCode_2+"&needPercent=1&kpiType=${productData.kpiType}&reportDate=";
	 if(kpitype == 1)
	 {
	 url2+=queryDate;
	 }
	 else
	 {
	 url2+=queryDate;
	 }
	 url2+="&color=8aac57;dc3c28&rnd="+Math.random();
	 so2.addVariable("settings_file", escape(url2));
	 so2.addVariable("key", amchart_key);
	 //指标Codes
	 so2.addVariable("kpiCodes", kpiCode_2);
	 //日期类型
	 so2.addVariable("kpiType", "${productData.kpiType}");
	 //产品Id
	 so2.addVariable("productId", "${productId}");
	 
	 so2.write("chartdiv2");
     //用户贡献变化
	 var kpiCode_3="${productKpiCode.PRODUCT_PER_USER_ADMOUNT.kpiCode};${productKpiCode.PRODUCT_PER_USER_TIMES.kpiCode}";
	 if(myKpiType==<%=DwpasCKpiInfo.KPI_TYPE_OF_MONTH%>){
         kpiCode_3="${productKpiCode.PRODUCT_MONTH_PER_USER_ADMOUNT_NEW_1.kpiCode};${productKpiCode.PRODUCT_MONTH_PER_USER_TIMES_NEW_1.kpiCode}";
	 }
    var so3 = new SWFObject("<%=path%>/common/amchart/stock/amstock/amstock.swf?id=<%=new Date().getTime()%>", "amline3", "688", "295", "8", "#EFF0F2");
	 so3.addVariable("path", "<%=path%>/common/amchart/stock/amstock/");
	 so3.addVariable("chart_id", "amline3");
	 so3.addParam("wmode","transparent");
	 //户均业务量、户均业务金额
	 var url3="<%=path%>/stockChart/showStockChart?1=1&isPrd=1&kpiCodes="+kpiCode_3+"&needPercent=1&kpiType=${productData.kpiType}&reportDate=";
	 if(kpitype == 1)
	 {
	 url3+=queryDate;
	 }
	 else
	 {
	 url3+=queryDate;
	 }
	 url3+="&color=8aac57;dc3c28&rnd="+Math.random();
	 so3.addVariable("settings_file", escape(url3));
	 so3.addVariable("key", amchart_key);
	 //指标Codes
	 so3.addVariable("kpiCodes", kpiCode_3);
	 //日期类型
	 so3.addVariable("kpiType", "${productData.kpiType}");
	 //产品Id
	 so3.addVariable("productId", "${productId}");
	 so3.write("chartdiv3");
	 
	 var iframeContentHeight=document.body.scrollHeight;
	 parent.changeIframeSize(iframeContentHeight);
	 
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
		if(document.getElementById("chartdate-"+chart_id)!=null) document.getElementById("chartdate-"+chart_id).innerText=dateDesc;
	}

 	function restartOpen(queryDay){
 		  if(queryDay==null||queryDay.length==0) queryDay=document.getElementById('maxDate').value;
 		  var queryMonth=document.getElementById('monthDate').value;
 		  var kpiType=document.getElementById('myType').value;
 		  if(document.getElementById('theDate').value!=""){
 			  if(kpiType==1){
 	            location.href='<%=path%>/ProductDev/doPost?1=1&menuId=${menuId}&queryDate='+queryDay+'&kpiType='+kpiType;
 			  }
 			  else{
 				  
 				  location.href='<%=path%>/ProductDev/doPost?1=1&menuId=${menuId}&queryDate='+queryMonth+'&kpiType='+kpiType;
 			  }
 		  }
 	  }
 	
 	 function day(){
 		  var kpiType=1;
/*  		  var queryDay=document.getElementById('theDate').value;
 		  if(queryDay.length<10){
 			  queryDay=queryDay+"-01";
 		  } */
 	      location.href='<%=path%>/ProductDev/doPost?1=1&menuId=${menuId}&kpiType='+kpiType;
 	  }
 	  function month(){
 		  var kpiType=3;
/*  		  var queryDate=document.getElementById('theDate').value; 		 
 		  queryDate=queryDate.substr(0, 7); */
 	      location.href='<%=path%>/ProductDev/doPost?1=1&menuId=${menuId}&kpiType='+kpiType;
 	  }
 	  
 	 function changeProduct(productId){
 		location.href='<%=path%>/ProductDev/doPost?1=1&menuId=${menuId}&kpiType=${productData.kpiType}&productId='+productId;
 	 }
 	 
 	function changeTheDate(n){
 		  var kpiType=document.getElementById('myType').value;
 		  var reg=new RegExp("-","g");
 		  if(kpiType==<%=DwpasCKpiInfo.KPI_TYPE_OF_DAY%>){
 			  var dt=document.getElementById('maxDate').value.replace(reg,"/");
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
 				 location.href='<%=path%>/ProductDev/doPost?1=1&menuId=${menuId}&queryDate='+changedDate+'&kpiType='+kpiType;
 			  }
 		  }
 		  else if(kpiType==<%=DwpasCKpiInfo.KPI_TYPE_OF_MONTH%>){
 			  var dt=document.getElementById('monthDate').value.replace(reg,"/")+"/01";
 			  var today=new Date(new Date(dt).valueOf() );
 			  today.setMonth(today.getMonth()+n);
 			  var monthDate=today.getMonth()+1;
 			  if((today.getMonth() + 1)<10){ 
 		    	  monthDate="0"+(today.getMonth() + 1);
 			  }
 			  var changedDate=today.getFullYear() + "-" + monthDate;
 			  document.getElementById('monthDate').value=changedDate; 
 			  if(changedDate!=''){
 				 location.href='<%=path%>/ProductDev/doPost?1=1&menuId=${menuId}&queryDate='+changedDate+'&kpiType='+kpiType;
 			  }
 		  }
 		  
 	  }
 	//帮助按钮
 	  function helpShow1(){
 	  	document.getElementById('help1').style.display="";
 	  }
 	  function helpHidden1(){
 	  	document.getElementById('help1').style.display="none";
 	  }
 	  function helpShow2(){
 	  	document.getElementById('help2').style.display="";
 	  }
 	  function helpHidden2(){
 	  	document.getElementById('help2').style.display="none";
 	  }
 	  function helpShow3(){
 	 	document.getElementById('help3').style.display="";
 	 }
 	  function helpHidden3(){
 	 	document.getElementById('help3').style.display="none";
 	 }
</script>

</head>

<body class="indexbody2">
  <jsp:include page="../common/ChoiceProduct.jsp"/>
	<input type="hidden" id="productId" value="${productData.productId}" />
	<input id="myType" name="myType" type="hidden" value="${productData.kpiType}" />
	<input id="theDate" name="theDate" type="hidden" value="${productData.date}" />
	<div class="new_jwy_yy" style="width:1010px; background:#fff">
			<!-- 当前位置 -->
<div class="kpi_position"  id="navigationDiv"  style="z-index:9999999">
			<div style="padding-top: 2px">
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					style="padding-top: 8px; padding-bottom: 8px">
					<tr>
						<td width="54%">
<div style="padding:0px 0px 0px 11px">
<div style="float:left;padding-top:3px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;当前位置：
						<a href="<%=path%>/index/gotoHomePage.html?menuId=<%=indexMenuId%>">首页</a>&gt;
						<a href="<%=path%>/productHealth/showProductHealth?menuId=${parentMenu.menuId}">${parentMenu.menuName }</a>&gt;
						<a href="<%=path%>/ProductDev/doPost?menuId=${menuId}">${modulemap.PRODUCT_DEVELOP_AMOUNT_RISE_THREAD_CHART.menuName }</a>&gt; 
							<font color='red'>${productData.productName}</font> </div>
	<div style="float:left">&nbsp;&nbsp;<a href="javascript:void(0);" onclick="choiceRrdInfo();"><img 
								src="<%=path%>/common/images/jwy_button_01.gif" 
								border="0" /></a></div>					
				</div>
						</td>
						<td width="21%" style="text-align: right;">
							<div id="wd" style="width: 84px; height: 22px; text-align: center; line-height: 22px; color: #fff; float: left; margin-right: 15px">
								<a href="javascript:void(0);" onclick="day();">
									<span id="byDay">按日查看</span> </a>
							</div>

							<div id="wd2" style="width: 84px; height: 22px; text-align: center; line-height: 22px; color: #fff; float: left;">
								<a href="javascript:void(0);" onclick="month();"> 
									<span id="byMonth">按月查看 </span>
								</a>
							</div>


						</td>

						<td width="25%" style="text-align: center;">数据时间： 
						<a href="javascript:void(0);" onclick="changeTheDate(-1);"> 
							<img style="vertical-align: middle;" align="middle"
								src="<%=path%>/common/images/ddy_2.gif" width="11" height="23"
								border="0" /></a> 
						<input id="maxDate" name="maxDate" type="text" class="ddy_index_input" readonly="readonly"
							value="${productData.date}" onclick="WdatePicker({isShowWeek:true,isShowClear:false,readOnly:true,startDate:'${productData.date}',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true,alwaysUseStartDate:true,doubleCalendar:true,onpicked:function(dp){restartOpen(dp.cal.getNewDateStr());}})"  style="cursor: pointer;" /> 
						<input id="monthDate" name="monthDate" type="text" class="ddy_index_input" readonly="readonly"
							onchange="restartOpen();" onclick="setMonth(this,this)" value="${productData.date}" style="cursor: pointer;" /> 
						<a href="javascript:void(0);" onclick="changeTheDate(1);"> 
							<img style="vertical-align: middle;" align="middle" src="<%=path%>/common/images/ddy_1.gif" width="11" height="23"
								border="0" /></a>
						</td>
					</tr>
				</table>
			</div>
		</div>
<!-- 当前位置结束 -->
<div class="clear"></div>
		
<div class="ddy_channel2" ></div>
	<div class="index_home">
	   <div style="padding-top:5px"></div>
		<!--业务量与业务笔数趋势 -->
		<div class=""></div>
			<div class="index_title">
				<div class="index_title_pd">
					<table>
						<tr>
							<td><img src="<%=basePath%>/common/images/new_jwy_4.gif" /></td>
							<td>&nbsp;<!--产品使用量增长趋势-->${modulemap.PRODUCT_DEVELOP_AMOUNT_RISE_THREAD_CHART.moduleName }
							</td>
						</tr>
					</table>


				</div>
				<span class="box-top-right"> 
				<em style="background: url(<%=basePath%>/common/images/askicon5.png); cursor: pointer"
					class="box-askicon" onmouseover="helpShow1();"
					onmouseout="helpHidden1();"></em>
				</span>
				<div id="help1" style="display: none; font-size: 12px;">
					<div style="position:relative;z-index:99999999; margin:0px 0px 0px 310px">
										<div class="pop_w">
										  <div class="pop_z" style="z-index:99999; left:260px;">	<img src="<%=basePath%>/common/images/pop_tip.png" /></div>
       
<div class="pop_x">			<p>
								<b>${modulemap.PRODUCT_DEVELOP_AMOUNT_RISE_THREAD_CHART.moduleName}：</b>
							</p>
							<p style="font-weight: normal;">
								${modulemap.PRODUCT_DEVELOP_AMOUNT_RISE_THREAD_CHART.remark}</p>
   
</div>
</div></div>
				</div>
			</div>

			<div class="clear"></div>
		<div class="index_report" style="overflow: hidden">

			<div class="box-conent box-biz" style="overflow: hidden">
				<div class="biz-left">
					<c:choose>
						<c:when test="${productData.kpiType == 1}">
						    <!--按日统计-->
							<div class="biz-left-top">
								<div class="biz-cleft">
									<ul>
									    <!--业务量-->
										<li class="lucolor size28">${modulemap.PRODUCT_TOTAL_TRANSACTIONS_AMOUNT.columnDisplayName}</li>
										<!--单位-->
										<li class="lh12">(${productKpiCode.PRODUCT_TOTAL_TRANSACTIONS_AMOUNT.unit})</li>
									</ul>
								</div>
								<div class="biz-cmiddle">
									<ul>
										<li>当期值</li>
										<li>均 值</li>
										<li>峰 值</li>									
									</ul>
								</div>
								
								<div class="biz-cright">
									<ul>
										<c:if
											test="${not empty productKpiData.PRODUCT_TOTAL_TRANSACTIONS_AMOUNT.showValue }">
											<li><fmt:formatNumber
													value="${productKpiData.PRODUCT_TOTAL_TRANSACTIONS_AMOUNT.showValue}"
													type="number" pattern="#,#00.00#" /></li>
										</c:if>
										<c:if
											test="${empty productKpiData.PRODUCT_TOTAL_TRANSACTIONS_AMOUNT.showValue}">
											<li>00.00</li>
										</c:if>
										<c:if
											test="${not empty productKpiData.PRODUCT_TOTAL_TRANSACTIONS_AMOUNT.averageShow }">
											<li><fmt:formatNumber
													value="${productKpiData.PRODUCT_TOTAL_TRANSACTIONS_AMOUNT.averageShow}"
													type="number" pattern="#,#00.00#" /></li>
										</c:if>
										<c:if
											test="${empty productKpiData.PRODUCT_TOTAL_TRANSACTIONS_AMOUNT.averageShow}">
											<li>00.00</li>
										</c:if>
										<c:if
											test="${not empty productKpiData.PRODUCT_TOTAL_TRANSACTIONS_AMOUNT.maxShow }">
											<li><fmt:formatNumber
													value="${productKpiData.PRODUCT_TOTAL_TRANSACTIONS_AMOUNT.maxShow}"
													type="number" pattern="#,#00.00#" /></li>
										</c:if>
										<c:if
											test="${empty productKpiData.PRODUCT_TOTAL_TRANSACTIONS_AMOUNT.maxShow}">
											<li>00.00</li>
										</c:if>
									</ul>
									<div style="position: relative;">
									<div  style="position:absolute; width:200px; margin-left:-130px ">峰值日期&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<c:if test="${not empty productKpiData.PRODUCT_TOTAL_TRANSACTIONS_AMOUNT.gmtMaxTimeWeekly}">
									${productKpiData.PRODUCT_TOTAL_TRANSACTIONS_AMOUNT.gmtMaxTimeWeekly}
									</c:if>
									<c:if test="${empty productKpiData.PRODUCT_TOTAL_TRANSACTIONS_AMOUNT.gmtMaxTimeWeekly }">
										无峰值日期
									</c:if>										
									</div>
									</div>
								</div>
							</div>
							<div class="biz-left-bottom">
								<div class="biz-cleft">
									<ul>
									    <!--业务笔数-->
										<li class="fcolor size28">${modulemap.PRODUCT_TOTAL_TRANSACTIONS_TIMES.columnDisplayName}</li>
										<!--单位-->
										<li class="lh12">(${productKpiCode.PRODUCT_TOTAL_TRANSACTIONS_TIMES.unit})</li>
									</ul>
								</div>
								<div class="biz-cmiddle">
									<ul>
										<li>当期值</li>
										<li>均 值</li>
										<li>峰 值</li>
									
									</ul>
								</div>

								<div class="biz-cright">
								<ul>
										<c:if
											test="${not empty productKpiData.PRODUCT_TOTAL_TRANSACTIONS_TIMES.showValue }">
											<li><fmt:formatNumber
													value="${productKpiData.PRODUCT_TOTAL_TRANSACTIONS_TIMES.showValue}"
													type="number" pattern="#,#00.00#" /></li>
										</c:if>
										<c:if
											test="${empty productKpiData.PRODUCT_TOTAL_TRANSACTIONS_TIMES.showValue}">
											<li>00.00</li>
										</c:if>
										<c:if
											test="${not empty productKpiData.PRODUCT_TOTAL_TRANSACTIONS_TIMES.averageShow }">
											<li><fmt:formatNumber
													value="${productKpiData.PRODUCT_TOTAL_TRANSACTIONS_TIMES.averageShow}"
													type="number" pattern="#,#00.00#" /></li>
										</c:if>
										<c:if
											test="${empty productKpiData.PRODUCT_TOTAL_TRANSACTIONS_TIMES.averageShow}">
											<li>00.00</li>
										</c:if>
										<c:if
											test="${not empty productKpiData.PRODUCT_TOTAL_TRANSACTIONS_TIMES.maxShow }">
											<li><fmt:formatNumber
													value="${productKpiData.PRODUCT_TOTAL_TRANSACTIONS_TIMES.maxShow}"
													type="number" pattern="#,#00.00#" /></li>
										</c:if>
										<c:if
											test="${empty productKpiData.PRODUCT_TOTAL_TRANSACTIONS_TIMES.maxShow}">
											<li>00.00</li>
										</c:if>
									</ul>
									<div style="position: relative;">
									<div  style="position:absolute; width:200px; margin-left:-130px ">峰值日期&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<c:if test="${not empty productKpiData.PRODUCT_TOTAL_TRANSACTIONS_TIMES.gmtMaxTimeWeekly}">
									${productKpiData.PRODUCT_TOTAL_TRANSACTIONS_TIMES.gmtMaxTimeWeekly}
									</c:if>
									<c:if test="${empty productKpiData.PRODUCT_TOTAL_TRANSACTIONS_TIMES.gmtMaxTimeWeekly}">
										无峰值日期
									</c:if>										
									</div>
									</div>
								</div>
							</div>
						</c:when>
						<c:otherwise>
						    <!--按月统计-->
							<div class="biz-left-top">
								<div class="biz-cleft">
									<ul>
									    <!--产品使用量增长趋势_业务量_月指标-->
										<li class="lucolor size28">${modulemap.PRODUCT_MONTH_TOTAL_TRANSACTIONS_AMOUNT_NEW.columnDisplayName}</li>
										<!--单位-->
										<li class="lh12">(${productKpiCode.PRODUCT_MONTH_TOTAL_TRANSACTIONS_AMOUNT_NEW_1.unit})</li>
									</ul>
								</div>
								<div class="biz-cmiddle">
									<ul>
										<li>当 月 值</li>
										<li class="mt18 lh">上 月 值</li>
										<li class="lh">环比<!--环比CKC0085${modulemap.PRODUCT_MONTH_TOTAL_TRANSACTIONS_AMOUNT_LINK_RELATIVE.columnDisplayName}--></li>
										<li class="mt18 lh">去年同期值<!--去年同期值CKC0084${modulemap.PRODUCT_MONTH_TOTAL_TRANSACTIONS_AMOUNT_SAME_PERIOD_LAST_YEAR.columnDisplayName}--></li>
										<li class="lh">同比<!--同比CKC0086${modulemap.PRODUCT_MONTH_TOTAL_TRANSACTIONS_AMOUNT_COMPARED_TO_THE_SAME_PERIOD.columnDisplayName}--></li>
									</ul>
								</div>
								<div class="biz-cright">
								<ul>
								    <!--当月值-->
									<c:if test="${not empty productKpiData.PRODUCT_MONTH_TOTAL_TRANSACTIONS_AMOUNT_NEW_1 }">
										<li><fmt:formatNumber
													value="${productKpiData.PRODUCT_MONTH_TOTAL_TRANSACTIONS_AMOUNT_NEW_1.showValue}"type="number"
													pattern="#,#00.00#" /></li>
									</c:if>
									<c:if test="${empty productKpiData.PRODUCT_MONTH_TOTAL_TRANSACTIONS_AMOUNT_NEW_1}"><li>0.00</li></c:if>
									<!--上月值-->
									<c:if test="${not empty productKpiData.PRODUCT_MONTH_TOTAL_TRANSACTIONS_AMOUNT_NEW_2}">	
										<li class="mt18 lh"><fmt:formatNumber
													value="${productKpiData.PRODUCT_MONTH_TOTAL_TRANSACTIONS_AMOUNT_NEW_2.showValue}" type="number"
													pattern="#,#00.00#"/></li>
									</c:if>
									<c:if test="${empty productKpiData.PRODUCT_MONTH_TOTAL_TRANSACTIONS_AMOUNT_NEW_2}"><li class="mt18 lh">0.00</li></c:if>
									<!--环比-->
									<c:if test="${not empty productKpiData.PRODUCT_MONTH_TOTAL_TRANSACTIONS_AMOUNT_NEW_3}">
										<li class="lh"><fmt:formatNumber
													value="${productKpiData.PRODUCT_MONTH_TOTAL_TRANSACTIONS_AMOUNT_NEW_3.showValue/100}" type="percent"/></li>
									</c:if>	
									<c:if test="${empty productKpiData.PRODUCT_MONTH_TOTAL_TRANSACTIONS_AMOUNT_NEW_3}"><li class="lh">0.00</li></c:if>
									<!--去年同期值-->
									<c:if test="${not empty productKpiData.PRODUCT_MONTH_TOTAL_TRANSACTIONS_AMOUNT_NEW_4}">
										<li class="mt18 lh"><fmt:formatNumber
													value="${productKpiData.PRODUCT_MONTH_TOTAL_TRANSACTIONS_AMOUNT_NEW_4.showValue}" type="number"
													pattern="#,#00.00#"/></li>
									</c:if>	
									<c:if test="${empty productKpiData.PRODUCT_MONTH_TOTAL_TRANSACTIONS_AMOUNT_NEW_4}"><li class="mt18 lh">0.00</li></c:if>
									<!--同比-->
									<c:if test="${not empty productKpiData.PRODUCT_MONTH_TOTAL_TRANSACTIONS_AMOUNT_NEW_5}">
										<li class="lh"><fmt:formatNumber
													value="${productKpiData.PRODUCT_MONTH_TOTAL_TRANSACTIONS_AMOUNT_NEW_5.showValue/100}" type="percent"/></li>
									</c:if>
									<c:if test="${empty productKpiData.PRODUCT_MONTH_TOTAL_TRANSACTIONS_AMOUNT_NEW_5}"><li class="lh">0.00</li></c:if>
									</ul>
								</div>
							</div>
							<div class="biz-left-bottom">
								<div class="biz-cleft">
									<ul>
									    <!--产品使用量增长趋势_业务笔数_月指标-->
										<li class="fcolor size28">${modulemap.PRODUCT_MONTH_TOTAL_TRANSACTIONS_TIMES_NEW.columnDisplayName}</li>
										<!--单位-->
										<li class="lh12">(${productKpiCode.PRODUCT_MONTH_TOTAL_TRANSACTIONS_TIMES_NEW_1.unit})</li>
									</ul>
								</div>
								<div class="biz-cmiddle">
									<ul>
										<li>当 月 值<!--当 月 值CKC0010${modulemap.PRODUCT_MONTH_TOTAL_TRANSACTIONS_TIMES.columnDisplayName}--></li>
										<li class="mt18 lh">上 月 值<!--上 月 值CKC0088${modulemap.PRODUCT_MONTH_TOTAL_TRANSACTIONS_TIMES_LAST_MONTH.columnDisplayName}--></li>
										<li class="lh">环比<!--环比CKC0090${modulemap.PRODUCT_MONTH_TOTAL_TRANSACTIONS_TIMES_LINK_RELATIVE.columnDisplayName}--></li>
										<li class="mt18 lh">去年同期值<!--去年同期值CKC0089${modulemap.PRODUCT_MONTH_TOTAL_TRANSACTIONS_TIMES_SAME_PERIOD_LAST_YEAR.columnDisplayName}--></li>
										<li class="lh">同比<!--同比CKC0091${modulemap.PRODUCT_MONTH_TOTAL_TRANSACTIONS_TIMES_COMPARED_TO_THE_SAME_PERIOD.columnDisplayName}--></li>
									</ul>
								</div>
								<div class="biz-cright">
									<ul>
									<!--当月-->
									<c:if test="${not empty productKpiData.PRODUCT_MONTH_TOTAL_TRANSACTIONS_TIMES_NEW_1 }">
										<li><fmt:formatNumber
													value="${productKpiData.PRODUCT_MONTH_TOTAL_TRANSACTIONS_TIMES_NEW_1.showValue}" type="number"
													pattern="#,#00.00#" /></li>
									</c:if>	
									<c:if test="${empty productKpiData.PRODUCT_MONTH_TOTAL_TRANSACTIONS_TIMES_NEW_1 }"><li>0.00</li></c:if>
									<!--上月-->
									<c:if test="${not empty productKpiData.PRODUCT_MONTH_TOTAL_TRANSACTIONS_TIMES_NEW_2 }">
										<li class="mt18 lh"><fmt:formatNumber
													value="${productKpiData.PRODUCT_MONTH_TOTAL_TRANSACTIONS_TIMES_NEW_2.showValue}" type="number"
													pattern="#,#00.00#" /></li>
									</c:if>	
									<c:if test="${empty productKpiData.PRODUCT_MONTH_TOTAL_TRANSACTIONS_TIMES_NEW_2 }"><li class="mt18 lh">0.00</li></c:if>
									<!--环比-->
									<c:if test="${not empty productKpiData.PRODUCT_MONTH_TOTAL_TRANSACTIONS_TIMES_NEW_3 }">
										<li class="lh"><fmt:formatNumber
													value="${productKpiData.PRODUCT_MONTH_TOTAL_TRANSACTIONS_TIMES_NEW_3.showValue/100}" type="percent"/></li>
									</c:if>	
									<c:if test="${empty productKpiData.PRODUCT_MONTH_TOTAL_TRANSACTIONS_TIMES_NEW_3 }"><li class="lh">0.00</li></c:if>
									<!--去年同期-->
									<c:if test="${not empty productKpiData.PRODUCT_MONTH_TOTAL_TRANSACTIONS_TIMES_NEW_4 }">
										<li class="mt18 lh"><fmt:formatNumber
													value="${productKpiData.PRODUCT_MONTH_TOTAL_TRANSACTIONS_TIMES_NEW_4.showValue}" type="number"
													pattern="#,#00.00#" /></li>
									</c:if>	
									<c:if test="${empty productKpiData.PRODUCT_MONTH_TOTAL_TRANSACTIONS_TIMES_NEW_4 }"><li class="mt18 lh">0.00</li></c:if>
									<!--同比-->
									<c:if test="${not empty productKpiData.PRODUCT_MONTH_TOTAL_TRANSACTIONS_TIMES_NEW_5 }">
										<li class="lh"><fmt:formatNumber
													value="${productKpiData.PRODUCT_MONTH_TOTAL_TRANSACTIONS_TIMES_NEW_5.showValue/100}" type="percent"/></li>
									</c:if>	
									<c:if test="${empty productKpiData.PRODUCT_MONTH_TOTAL_TRANSACTIONS_TIMES_NEW_5 }"><li class="lh">0.00</li></c:if>
									</ul>
								</div>
							</div>
						</c:otherwise>
					</c:choose>
				</div>
				<div style="position: relative;">
					<div id="chartdiv1"></div>
					<div id="chartdate-amline1" style="position: absolute; width: 120px; left: 14px; top: 28px;display:none;">
						<c:choose>
							<c:when test="${productData.kpiType == 1}">
						   ${productData.date}
						    </c:when>
							<c:otherwise>
							${productData.date}
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>

		</div>
		<div class="clear"></div>

		<!--业务量与业务笔数趋势结束 -->

		<!--产品使用会员数趋势 -->
		<div class="ddy_one_home"></div>
			<div class="index_title">
				<div class="index_title_pd">
					<table>
						<tr>
							<td><img src="<%=basePath%>/common/images/new_jwy_4.gif" /></td>
							<td>&nbsp;<!--产品使用用户趋势-->${modulemap.PRODUCT_DEVELOP_USER_RISE_THREAD_CHART.moduleName}
							</td>
						</tr>
					</table>
				</div>
				<span class="box-top-right"> 
				<em style="background: url(<%=basePath%>/common/images/askicon5.png); cursor: pointer"
					class="box-askicon" onmouseover="helpShow2();"
					onmouseout="helpHidden2();"></em>
				</span>
				<div id="help2" style="display: none; font-size: 12px;">
					<div style="position:relative;z-index:99999999;margin:0px 0px 0px 310px">
										<div class="pop_w">
										  <div class="pop_z" style="z-index:99999; left:260px;">	<img src="<%=basePath%>/common/images/pop_tip.png" /></div>
       
<div class="pop_x">				<p>
								<b>${modulemap.PRODUCT_DEVELOP_USER_RISE_THREAD_CHART.moduleName}：</b>
							</p>
							<p style="font-weight: normal;">
								${modulemap.PRODUCT_DEVELOP_USER_RISE_THREAD_CHART.remark}</p>
</div>
</div></div>
				</div>
			</div>

			<div class="clear"></div>
		<div class="index_report" style="overflow: hidden">
			<div class="box-conent box-biz">
				<div class="biz-left">
					<c:choose>
						<c:when test="${productData.kpiType == 1}">
						   <!--按日-->
							<div class="biz-left-top">
								<div class="biz-cleft">
									<ul>
									    <!--产品用户数-->
										<li class="lucolor size28">${modulemap.PRODUCT_DAY_USER_COUNT.columnDisplayName}</li>
										<!--单位-->
										<li class="lh12">(${productKpiCode.PRODUCT_DAY_USER_COUNT.unit})</li>
									</ul>
								</div>
								<div class="biz-cmiddle">
									<ul>
										<li>当期值</li>
										<li>均 值</li>
										<li>峰 值</li>
									</ul>
								</div>
								<div class="biz-cright">
									<ul>
									<c:if test="${not empty productKpiData.PRODUCT_DAY_USER_COUNT.showValue }">
										<li><fmt:formatNumber
												value="${productKpiData.PRODUCT_DAY_USER_COUNT.showValue}" type="number"
												pattern="#,#00.00#" /></li>
									</c:if>
									<c:if test="${empty productKpiData.PRODUCT_DAY_USER_COUNT.showValue  }">
										<li>00.00</li>
									</c:if>	
									<c:if test="${not empty productKpiData.PRODUCT_DAY_USER_COUNT.averageShow }">		
										<li><fmt:formatNumber
												value="${productKpiData.PRODUCT_DAY_USER_COUNT.averageShow}" type="number"
												pattern="#,#00.00#" /></li>
									</c:if>
									<c:if test="${empty productKpiData.PRODUCT_DAY_USER_COUNT.averageShow  }">
										<li>00.00</li>
									</c:if>			
									<c:if test="${not empty productKpiData.PRODUCT_DAY_USER_COUNT.maxShow }">			
										<li><fmt:formatNumber
												value="${productKpiData.PRODUCT_DAY_USER_COUNT.maxShow}" type="number"
												pattern="#,#00.00#" /></li>
									</c:if>
									<c:if test="${empty productKpiData.PRODUCT_DAY_USER_COUNT.maxShow  }">
										<li>00.00</li>
									</c:if>			
									</ul>
									<div style="position: relative;">
									<div  style="position:absolute; width:200px; margin-left:-130px ">峰值日期&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<c:if test="${not empty productKpiData.PRODUCT_DAY_USER_COUNT.gmtMaxTimeWeekly}">
									${productKpiData.PRODUCT_DAY_USER_COUNT.gmtMaxTimeWeekly}
									</c:if>
									<c:if test="${empty productKpiData.PRODUCT_DAY_USER_COUNT.gmtMaxTimeWeekly }">
										无峰值日期
									</c:if>
									</div>
								    </div>
								</div> 
							</div>
							<div class="biz-left-bottom">
								<div class="biz-cleft">
									<ul>
									    <!--产品用户总数/累计产品用户数-->
										<li class="fcolor size28">${modulemap.PRODUCT_TOTAL_USER_COUNT.columnDisplayName}</li>
										<!--单位-->
										<li class="lh12">(${productKpiCode.PRODUCT_TOTAL_USER_COUNT.unit})</li>
									</ul>
								</div>
								<div class="biz-cmiddle">
									<ul>
										<li>当期值</li>
										<li>均 值</li>
										<li>峰 值</li>
									</ul>
								</div>
								<div class="biz-cright">
									<ul>
									<c:if test="${not empty productKpiData.PRODUCT_TOTAL_USER_COUNT.showValue }">
										<li><fmt:formatNumber
												value="${productKpiData.PRODUCT_TOTAL_USER_COUNT.showValue}" type="number"
												pattern="#,#00.00#" /></li>
									</c:if>
									<c:if test="${empty productKpiData.PRODUCT_TOTAL_USER_COUNT.showValue }">
										<li>0.00</li>
									</c:if>
									<c:if test="${not empty productKpiData.PRODUCT_TOTAL_USER_COUNT.averageShow }">		
										<li><fmt:formatNumber
												value="${productKpiData.PRODUCT_TOTAL_USER_COUNT.averageShow}" type="number"
												pattern="#,#00.00#" /></li>
									</c:if>
									<c:if test="${empty productKpiData.PRODUCT_TOTAL_USER_COUNT.averageShow  }">
										<li>00.00</li>
									</c:if>			
									<c:if test="${not empty productKpiData.PRODUCT_TOTAL_USER_COUNT.maxShow }">			
										<li><fmt:formatNumber
												value="${productKpiData.PRODUCT_TOTAL_USER_COUNT.maxShow}" type="number"
												pattern="#,#00.00#" /></li>
									</c:if>
									<c:if test="${empty productKpiData.PRODUCT_TOTAL_USER_COUNT.maxShow  }">
										<li>00.00</li>
									</c:if>			
									</ul>
									<div style="position: relative;">
									<div  style="position:absolute; width:200px; margin-left:-130px ">峰值日期&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<c:if test="${not empty productKpiData.PRODUCT_TOTAL_USER_COUNT.gmtMaxTimeWeekly}">
									${productKpiData.PRODUCT_TOTAL_USER_COUNT.gmtMaxTimeWeekly}
									</c:if>
									<c:if test="${empty productKpiData.PRODUCT_TOTAL_USER_COUNT.gmtMaxTimeWeekly }">
										无峰值日期
									</c:if>									
									</div>
								    </div>
								</div>
							</div>
						</c:when>
						<c:otherwise>
						    <!--按月-->
							<div class="biz-left-top">
								<div class="biz-cleft">
									<ul>
									    <!--产品使用量增长趋势_产品用户数_月指标-->
										<li class="lucolor size28">${modulemap.PRODUCT_MONTH_USER_COUNT_NEW.columnDisplayName}</li>
										<!--单位-->
										<li class="lh12">(${productKpiCode.PRODUCT_MONTH_USER_COUNT_NEW_1.unit})</li>
									</ul>
								</div>
								<div class="biz-cmiddle">
									<ul>
										<li>当 月 值<!--当 月 值CKC0092${modulemap.PRODUCT_MONTH_USER_COUNT.columnDisplayName}--></li>
										<li class="mt18 lh">上 月 值<!--上 月 值CKC0093${modulemap.PRODUCT_MONTH_USER_COUNT_LAST_MONTH.columnDisplayName}--></li>
										<li class="lh">环比<!--环比CKC0095${modulemap.PRODUCT_MONTH_USER_COUNT_TIMES_LINK_RELATIVE.columnDisplayName}--></li>
										<li class="mt18 lh">去年同期值<!--去年同期值CKC0094${modulemap.PRODUCT_MONTH_USER_COUNT_SAME_PERIOD_LAST_YEAR.columnDisplayName}--></li>
										<li class="lh">同比<!--同比CKC0096${modulemap.PRODUCT_MONTH_USER_COUNT_COMPARED_TO_THE_SAME_PERIOD.columnDisplayName}--></li>
									</ul>
								</div>
								<div class="biz-cright">
									<ul>
									<c:if test="${not empty productKpiData.PRODUCT_MONTH_USER_COUNT_NEW_1 }">
										<li><fmt:formatNumber
													value="${productKpiData.PRODUCT_MONTH_USER_COUNT_NEW_1.showValue}" type="number"
													pattern="#,#00.00#" /></li>
									</c:if>	
									<c:if test="${empty productKpiData.PRODUCT_MONTH_USER_COUNT_NEW_1 }"><li>0.00</li></c:if>
									<!--上 月 值-->
									<c:if test="${not empty productKpiData.PRODUCT_MONTH_USER_COUNT_NEW_2 }">
										<li class="mt18 lh"><fmt:formatNumber
													value="${productKpiData.PRODUCT_MONTH_USER_COUNT_NEW_2.showValue}" type="number"
													pattern="#,#00.00#" /></li>
									</c:if>
									<c:if test="${empty productKpiData.PRODUCT_MONTH_USER_COUNT_NEW_2 }"><li class="mt18 lh">0.00</li></c:if>
									<!--环比-->
									<c:if test="${not empty productKpiData.PRODUCT_MONTH_USER_COUNT_NEW_3 }">
										<li class="lh"><fmt:formatNumber
													value="${productKpiData.PRODUCT_MONTH_USER_COUNT_NEW_3.showValue/100}" type="percent" /></li>
									</c:if>	
									<c:if test="${empty productKpiData.PRODUCT_MONTH_USER_COUNT_NEW_3 }"><li class="lh">0.00</li></c:if>
									<!--去年同期值-->
									<c:if test="${not empty productKpiData.PRODUCT_MONTH_USER_COUNT_NEW_4 }">
										<li class="mt18 lh"><fmt:formatNumber
													value="${productKpiData.PRODUCT_MONTH_USER_COUNT_NEW_4.showValue}" type="number"
													pattern="#,#00.00#" /></li>
									</c:if>	
									<c:if test="${empty productKpiData.PRODUCT_MONTH_USER_COUNT_NEW_4 }"><li class="mt18 lh">0.00</li></c:if>
									<!--同比-->
									<c:if test="${not empty productKpiData.PRODUCT_MONTH_USER_COUNT_NEW_5 }">
										<li class="lh"><fmt:formatNumber
													value="${productKpiData.PRODUCT_MONTH_USER_COUNT_NEW_5.showValue/100}" type="percent"/></li>
									</c:if>	
									<c:if test="${empty productKpiData.PRODUCT_MONTH_USER_COUNT_NEW_5 }"><li class="lh">0.00</li></c:if>
									</ul>
								</div>
							</div>
							<div class="biz-left-bottom">
								<div class="biz-cleft">
									<ul>
									    <!--产品使用量增长趋势_累计产品用户数_月指标-->
										<li class="fcolor size28">${modulemap.PRODUCT_MONTH_TOTAL_USER_COUNT_NEW.columnDisplayName}</li>
										<!--单位-->
										<li class="lh12">(${productKpiCode.PRODUCT_MONTH_TOTAL_USER_COUNT_NEW_1.unit})</li>
									</ul>
								</div>
								<div class="biz-cmiddle">
									<ul>
										<li>当 月 值<!--当 月 值CKC0097${modulemap.PRODUCT_MONTH_TOTAL_USER_COUNT.columnDisplayName}--></li>
										<li class="mt18 lh">上 月 值<!--上 月 值CKC0098${modulemap.PRODUCT_MONTH_TOTAL_USER_COUNT_LAST_MONTH.columnDisplayName}--></li>
										<li class="lh">环比<!--环比CKC0100${modulemap.PRODUCT_MONTH_TOTAL_USER_COUNT_LINK_RELATIVE.columnDisplayName}--></li>
										<li class="mt18 lh">去年同期值<!--去年同期值CKC0099>${modulemap.PRODUCT_MONTH_TOTAL_USER_COUNT_SAME_PERIOD_LAST_YEAR.columnDisplayName}--></li>
										<li class="lh">同比<!--同比CKC0101${modulemap.PRODUCT_MONTH_TOTAL_USER_COUNT_COMPARED_TO_THE_SAME_PERIOD.columnDisplayName}--></li>
									</ul>
								</div>
								<div class="biz-cright">
									<ul>
									<!--当月值-->
									<c:if test="${not empty productKpiData.PRODUCT_MONTH_TOTAL_USER_COUNT_NEW_1 }">
										<li><fmt:formatNumber
													value="${productKpiData.PRODUCT_MONTH_TOTAL_USER_COUNT_NEW_1.showValue}" type="number"
													pattern="#,#00.00#" /></li>
									</c:if>	
										<c:if test="${empty productKpiData.PRODUCT_MONTH_TOTAL_USER_COUNT_NEW_1 }"><li>0.00</li></c:if>
									<!--上月值-->
									<c:if test="${not empty productKpiData.PRODUCT_MONTH_TOTAL_USER_COUNT_NEW_2 }">
										<li class="mt18 lh"><fmt:formatNumber
													value="${productKpiData.PRODUCT_MONTH_TOTAL_USER_COUNT_NEW_2.showValue}" type="number"
													pattern="#,#00.00#" /></li>
									</c:if>	
									<c:if test="${empty productKpiData.PRODUCT_MONTH_TOTAL_USER_COUNT_NEW_2 }"><li class="mt18 lh">0.00</li></c:if>
									<!--环比-->
									<c:if test="${not empty productKpiData.PRODUCT_MONTH_TOTAL_USER_COUNT_NEW_3 }">
										<li class="lh"><fmt:formatNumber
													value="${productKpiData.PRODUCT_MONTH_TOTAL_USER_COUNT_NEW_3.showValue/100}" type="percent"/></li>
									</c:if>	
									<c:if test="${empty productKpiData.PRODUCT_MONTH_TOTAL_USER_COUNT_NEW_3 }"><li class="lh">0.00</li></c:if>
									<!--去年同期值-->
									<c:if test="${not empty productKpiData.PRODUCT_MONTH_TOTAL_USER_COUNT_NEW_4 }">
										<li class="mt18 lh"><fmt:formatNumber
													value="${productKpiData.PRODUCT_MONTH_TOTAL_USER_COUNT_NEW_4.showValue}" type="number"
													pattern="#,#00.00#" /></li>
									</c:if>	
									<c:if test="${empty productKpiData.PRODUCT_MONTH_TOTAL_USER_COUNT_NEW_4 }"><li class="mt18 lh">0.00</li></c:if>
									<!--同比-->
									<c:if test="${not empty productKpiData.PRODUCT_MONTH_TOTAL_USER_COUNT_NEW_5 }">
										<li class="lh"><fmt:formatNumber
													value="${productKpiData.PRODUCT_MONTH_TOTAL_USER_COUNT_NEW_5.showValue/100}" type="percent"/></li>
									</c:if>	
									<c:if test="${empty productKpiData.PRODUCT_MONTH_TOTAL_USER_COUNT_NEW_5 }"><li class="lh">0.00</li></c:if>
									</ul>
								</div>
							</div>
						</c:otherwise>
					</c:choose>
				</div>
				<div style="position: relative;">

					<div id="chartdiv2"></div>
					<div id="chartdate-amline2"
						style="position: absolute; width: 120px; left: 14px; top: 28px;display:none;">
						<c:choose>
							<c:when test="${productData.kpiType == 1}">
						   ${productData.date}
						    </c:when>
							<c:otherwise>
							${productData.date}
							</c:otherwise>
						</c:choose>
					</div>
				</div>

				<div class="box-bottom">
					<div class="box-bottom-rt">
						<div class="box-bottom-ct"></div>
					</div>
				</div>
			</div>

			<div class="clear"></div>

			<!--产品使用会员数趋势结束 -->
			<!--户均项献变化趋势 -->
			<div class="ddy_one_home"></div>
				<div class="index_title">
					<div class="index_title_pd">
						<table>
							<tr>
								<td><img src="<%=basePath%>/common/images/new_jwy_4.gif" /></td>
								<td>&nbsp;<!--户均贡献变化趋势-->${modulemap.PRODUCT_DEVELOP_PER_RISE_THREAD_CHART.moduleName}
								</td>
							</tr>
						</table>
					</div>
					<span class="box-top-right"> 
					<em style="background: url(<%=basePath%>/common/images/askicon5.png); cursor: pointer"
						class="box-askicon" onmouseover="helpShow3();"
						onmouseout="helpHidden3();"></em>
					</span>
					<div id="help3" style="display: none; font-size: 12px;">
						<div style="position: relative; margin: 32px 0px 0px 730px">
							<div style="width: 250px" class="tip">
								<p>
									<b>${modulemap.PRODUCT_DEVELOP_PER_RISE_THREAD_CHART.moduleName}：</b>
								</p>
								<p style="font-weight: normal;">
									${modulemap.PRODUCT_DEVELOP_PER_RISE_THREAD_CHART.remark}</p>
							</div>
						</div>
					</div>
				</div>

				<div class="clear"></div>
			<div class="index_report" style="overflow: hidden">
				<div class="box-conent box-biz">
					<div class="biz-left">
						<c:choose>
							<c:when test="${productData.kpiType == 1}">
							    <!--按日-->
								<div class="biz-left-top">
									<div class="biz-cleft">
										<ul>
										    <!--户均业务金额-->
											<li class="lucolor size28">${modulemap.PRODUCT_PER_USER_ADMOUNT.columnDisplayName}</li>
											<!--单位-->
											<li class="lh12">(${productKpiCode.PRODUCT_PER_USER_ADMOUNT.unit})</li>
										</ul>
									</div>
									<div class="biz-cmiddle">
										<ul>
											<li>当期值</li>
											<li>均 值</li>
											<li>峰 值</li>
										</ul>
									</div>
									<div class="biz-cright">
										<ul>
										<c:if
											test="${not empty productKpiData.PRODUCT_PER_USER_ADMOUNT.showValue }">
											<li><fmt:formatNumber
													value="${productKpiData.PRODUCT_PER_USER_ADMOUNT.showValue}"
													type="number" pattern="#,#00.00#" /></li>
										</c:if>
										<c:if
											test="${empty productKpiData.PRODUCT_PER_USER_ADMOUNT.showValue}">
											<li>00.00</li>
										</c:if>
										<c:if
											test="${not empty productKpiData.PRODUCT_PER_USER_ADMOUNT.averageShow }">
											<li><fmt:formatNumber
													value="${productKpiData.PRODUCT_PER_USER_ADMOUNT.averageShow}"
													type="number" pattern="#,#00.00#" /></li>
										</c:if>
										<c:if
											test="${empty productKpiData.PRODUCT_PER_USER_ADMOUNT.averageShow}">
											<li>00.00</li>
										</c:if>
										<c:if
											test="${not empty productKpiData.PRODUCT_PER_USER_ADMOUNT.maxShow }">
											<li><fmt:formatNumber
													value="${productKpiData.PRODUCT_PER_USER_ADMOUNT.maxShow}"
													type="number" pattern="#,#00.00#" /></li>
										</c:if>
										<c:if
											test="${empty productKpiData.PRODUCT_PER_USER_ADMOUNT.maxShow}">
											<li>00.00</li>
										</c:if>
									</ul>	
									<div style="position: relative;">
									<div  style="position:absolute; width:200px; margin-left: -130px ">峰值日期&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<c:if test="${not empty productKpiData.PRODUCT_PER_USER_ADMOUNT.gmtMaxTimeWeekly}">
									${productKpiData.PRODUCT_PER_USER_ADMOUNT.gmtMaxTimeWeekly}
									</c:if>
									<c:if test="${empty productKpiData.PRODUCT_PER_USER_ADMOUNT.gmtMaxTimeWeekly }">
										无峰值日期
									</c:if>										
									</div>								
									</div>
									</div>
								</div>
								
								<div class="biz-left-bottom">
									<div class="biz-cleft">
										<ul>
										    <!--户均业务笔数-->
											<li class="fcolor size28">${modulemap.PRODUCT_PER_USER_TIMES.columnDisplayName}</li>
											<!--单位-->
											<li class="lh12">(${productKpiCode.PRODUCT_PER_USER_TIMES.unit})</li>
										</ul>
									</div>
									<div class="biz-cmiddle">
										<ul>
											<li>当期值</li>
											<li>均 值</li>
											<li>峰 值</li>
										</ul>
									</div>
									<div class="biz-cright">
										<ul>
										<c:if
											test="${not empty productKpiData.PRODUCT_PER_USER_TIMES.showValue }">
											<li><fmt:formatNumber
													value="${productKpiData.PRODUCT_PER_USER_TIMES.showValue}"
													type="number" pattern="#,#00.00#" /></li>
										</c:if>
										<c:if
											test="${empty productKpiData.PRODUCT_PER_USER_TIMES.showValue}">
											<li>00.00</li>
										</c:if>
										<c:if
											test="${not empty productKpiData.PRODUCT_PER_USER_TIMES.averageShow}">
											<li><fmt:formatNumber
													value="${productKpiData.PRODUCT_PER_USER_TIMES.averageShow}"
													type="number" pattern="#,#00.00#" /></li>
										</c:if>
										<c:if
											test="${empty productKpiData.PRODUCT_PER_USER_TIMES.averageShow}">
											<li>00.00</li>
										</c:if>
										<c:if test="${not empty productKpiData.PRODUCT_PER_USER_TIMES.maxShow }">
											<li><fmt:formatNumber
													value="${productKpiData.PRODUCT_PER_USER_TIMES.maxShow}"
													type="number" pattern="#,#00.00#" /></li>
										</c:if>
										<c:if test="${empty productKpiData.PRODUCT_PER_USER_TIMES.maxShow}">
											<li>00.00</li>
										</c:if>
									</ul>
										<div style="position: relative;">
									<div  style="position:absolute; width:200px; margin-left:-130px ">峰值日期&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<c:if test="${not empty productKpiData.PRODUCT_PER_USER_TIMES.gmtMaxTimeWeekly}">
									${productKpiData.PRODUCT_PER_USER_TIMES.gmtMaxTimeWeekly}
									</c:if>
									<c:if test="${empty productKpiData.PRODUCT_PER_USER_TIMES.gmtMaxTimeWeekly }">
										无峰值日期
									</c:if>	
									</div>
									</div>
									
									</div>
								</div>
							</c:when>
							<c:otherwise>
							    <!---按月-->
								<div class="biz-left-top">
									<div class="biz-cleft">
										<ul>
										    <!--产品使用量增长趋势_户均业务量_月指标-->
											<li class="lucolor size28">${modulemap.PRODUCT_MONTH_PER_USER_ADMOUNT_NEW.columnDisplayName}</li>
											<!--单位-->
											<li class="lh12">(${productKpiCode.PRODUCT_MONTH_PER_USER_ADMOUNT_NEW_1.unit})</li>
										</ul>
									</div>
									<div class="biz-cmiddle">
										<ul>
											<li>当 月 值<!--当 月 值CKC0021${modulemap.PRODUCT_MONTH_PER_USER_ADMOUNT.columnDisplayName}--></li>
											<li class="mt18 lh">上 月 值<!--上 月 值CKC0103${modulemap.PRODUCT_MONTH_PER_USER_ADMOUNT_LAST_MONTH.columnDisplayName}--></li>
											<li class="lh">环比<!--环比CKC0105${modulemap.PRODUCT_MONTH_PER_USER_ADMOUNT_LINK_RELATIVE.columnDisplayName}--></li>
											<li class="mt18 lh">去年同期值<!--去年同期值CKC0104${modulemap.PRODUCT_MONTH_PER_USER_ADMOUNT_SAME_PERIOD_LAST_YEAR.columnDisplayName}--></li>
											<li class="lh">同比<!--同比CKC0106${modulemap.PRODUCT_MONTH_PER_USER_ADMOUNT_COMPARED_TO_THE_SAME_PERIOD.columnDisplayName}--></li>
										</ul>
									</div>
									<div class="biz-cright">
										<ul>
										<c:if test="${not empty productKpiData.PRODUCT_MONTH_PER_USER_ADMOUNT_NEW_1 }">
											<li><fmt:formatNumber
													value="${productKpiData.PRODUCT_MONTH_PER_USER_ADMOUNT_NEW_1.showValue}" type="number"
													pattern="#,#00.00#" /></li>
										</c:if>
										<c:if test="${empty productKpiData.PRODUCT_MONTH_PER_USER_ADMOUNT_NEW_1}"><li>0.00</li></c:if>
										<!--上月值-->
										<c:if test="${not empty productKpiData.PRODUCT_MONTH_PER_USER_ADMOUNT_NEW_2}">
											<li class="mt18 lh"><fmt:formatNumber
													value="${productKpiData.PRODUCT_MONTH_PER_USER_ADMOUNT_NEW_2.showValue}" type="number"
													pattern="#,#00.00#" /></li>
										</c:if>	
										<c:if test="${empty productKpiData.PRODUCT_MONTH_PER_USER_ADMOUNT_NEW_2}"><li  class="mt18 lh">0.00</li></c:if>
										<!--环比-->
										<c:if test="${not empty productKpiData.PRODUCT_MONTH_PER_USER_ADMOUNT_NEW_3 }">
											<li class="lh"><fmt:formatNumber
													value="${productKpiData.PRODUCT_MONTH_PER_USER_ADMOUNT_NEW_3.showValue/100}" type="percent"/></li>
										</c:if>	
										<c:if test="${empty productKpiData.PRODUCT_MONTH_PER_USER_ADMOUNT_NEW_3}"><li  class="lh">0.00</li></c:if>
										<!--去年同期值-->
										<c:if test="${not empty productKpiData.PRODUCT_MONTH_PER_USER_ADMOUNT_NEW_4}">
											<li class="mt18 lh"><fmt:formatNumber
													value="${productKpiData.PRODUCT_MONTH_PER_USER_ADMOUNT_NEW_4.showValue}" type="number"
													pattern="#,#00.00#" /></li>
										</c:if>	
										<c:if test="${empty productKpiData.PRODUCT_MONTH_PER_USER_ADMOUNT_NEW_4}"><li  class="mt18 lh">0.00</li></c:if>
										<!--同比-->
										<c:if test="${not empty productKpiData.PRODUCT_MONTH_PER_USER_ADMOUNT_NEW_5 }">
											<li class="lh"><fmt:formatNumber
													value="${productKpiData.PRODUCT_MONTH_PER_USER_ADMOUNT_NEW_5.showValue/100}" type="percent"/></li>
										</c:if>	
										<c:if test="${empty productKpiData.PRODUCT_MONTH_PER_USER_ADMOUNT_NEW_5}"><li  class="lh">0.00</li></c:if>
										</ul>
									</div>
								</div>
								<div class="biz-left-bottom">
									<div class="biz-cleft">
										<ul>
										    <!--产品使用量增长趋势_户均笔数_月指标-->
											<li class="fcolor size28">${modulemap.PRODUCT_MONTH_PER_USER_TIMES_NEW.columnDisplayName}</li>
											<!--单位-->
											<li class="lh12">(${productKpiCode.PRODUCT_MONTH_PER_USER_TIMES_NEW_1.unit})</li>
										</ul>
									</div>
									<div class="biz-cmiddle">
										<ul>
											<li>当 月 值<!--当 月 值CKC0020${modulemap.PRODUCT_MONTH_PER_USER_TIMES.columnDisplayName}--></li>
											<li class="mt18 lh">上 月 值<!--上 月 值CKC0108${modulemap.PRODUCT_MONTH_PER_USER_TIMES_LAST_MONTH.columnDisplayName}--></li>
											<li class="lh">环比<!--环比CKC0110${modulemap.PRODUCT_MONTH_PER_USER_TIMES_LINK_RELATIVE.columnDisplayName}--></li>
											<li class="mt18 lh">去年同期值<!--去年同期值CKC0109${modulemap.PRODUCT_MONTH_PER_USER_TIMES_SAME_PERIOD_LAST_YEAR.columnDisplayName}--></li>
											<li class="lh">同比<!--同比CKC0111${modulemap.PRODUCT_MONTH_PER_USER_TIMES_COMPARED_TO_THE_SAME_PERIOD.columnDisplayName}--></li>
										</ul>
									</div>
									<div class="biz-cright">
										<ul>
										<c:if test="${not empty productKpiData.PRODUCT_MONTH_PER_USER_TIMES_NEW_1 }">
											<li><fmt:formatNumber
													value="${productKpiData.PRODUCT_MONTH_PER_USER_TIMES_NEW_1.showValue}" type="number"
													pattern="#,#0.00#" /></li>
										</c:if>
										<c:if test="${empty productKpiData.PRODUCT_MONTH_PER_USER_TIMES_NEW_1}"><li>0.00</li></c:if>
										<!--上月值-->
										<c:if test="${not empty productKpiData.PRODUCT_MONTH_PER_USER_TIMES_NEW_2 }">
											<li class="mt18 lh"><fmt:formatNumber
													value="${productKpiData.PRODUCT_MONTH_PER_USER_TIMES_NEW_2.showValue}" type="number"
													pattern="#,#0.00#" /></li>
										</c:if>	
										<c:if test="${empty productKpiData.PRODUCT_MONTH_PER_USER_TIMES_NEW_2}"><li class="mt18 lh">0.00</li></c:if>
										<!--环比--->
										<c:if test="${not empty productKpiData.PRODUCT_MONTH_PER_USER_TIMES_NEW_3 }">
											<li class="lh"><fmt:formatNumber
													value="${productKpiData.PRODUCT_MONTH_PER_USER_TIMES_NEW_3.showValue/100}" type="percent"/></li>
										</c:if>	
										<c:if test="${empty productKpiData.PRODUCT_MONTH_PER_USER_TIMES_NEW_3}"><li class="lh">0.00</li></c:if>
										<!--去年同期值-->
										<c:if test="${not empty productKpiData.PRODUCT_MONTH_PER_USER_TIMES_NEW_4 }">
											<li class="mt18 lh"><fmt:formatNumber
													value="${productKpiData.PRODUCT_MONTH_PER_USER_TIMES_NEW_4.showValue}" type="number"
													pattern="#,#0.00#" /></li>
										</c:if>	
										<c:if test="${empty productKpiData.PRODUCT_MONTH_PER_USER_TIMES_NEW_4}"><li class="mt18 lh">0.00</li></c:if>
										<!--同比-->
										<c:if test="${not empty productKpiData.PRODUCT_MONTH_PER_USER_TIMES_NEW_5 }">
											<li class="lh"><fmt:formatNumber
													value="${productKpiData.PRODUCT_MONTH_PER_USER_TIMES_NEW_5.showValue/100}" type="percent"/></li>
										</c:if>	
										<c:if test="${empty productKpiData.PRODUCT_MONTH_PER_USER_TIMES_NEW_5}"><li class="lh">0.00</li></c:if>	
										</ul>
									</div>
								</div>
							</c:otherwise>
						</c:choose>
					</div>
					<div style="position: relative;">
						<div id="chartdiv3"></div>
						<div id="chartdate-amline3"
							style="position: absolute; width: 120px; left: 14px; top: 28px;display:none;">
							<c:choose>
								<c:when test="${productData.kpiType == 1}">
						   ${productData.date}
						    </c:when>
								<c:otherwise>
							${productData.date}
							</c:otherwise>
							</c:choose>
						</div>
					</div>
					<div class="box-bottom">
						<div class="box-bottom-rt">
							<div class="box-bottom-ct"></div>
						</div>
					</div>
				</div>
				<div class="clear"></div>

				<!--户均项献变化趋势 -->

			</div>
		</div>
	</div>
  </div>
  </body>
</html>