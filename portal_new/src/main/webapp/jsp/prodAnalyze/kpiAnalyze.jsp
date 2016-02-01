<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>   
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
<title>${systemName}-首页</title>

<link href="<%=path%>/common/css/lwt.css" rel="stylesheet"
	type="text/css" />	
<link href="<%=path%>/common/css/cssz.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="<%=path%>/common/js/public.js"></script>
<script type="text/javascript"
	src="<%=path%>/common/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="<%=path%>/common/js/My97DatePicker/My98MonthPicker.js"></script>
<script src="<%=path%>/common/amchart/stock/amstock/swfobject.js"
	type="text/javascript"></script>
<script type="text/javascript" src="<%=path%>/common/js/amcharts.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/amfallback.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/raphael.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/arale.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/ddycom.js"></script>
<script type="text/javascript"
	src="<%=path%>/common/js/jquery-1.4.3.min.js"></script>
<!-- 下面是GRID控件相关的JS和CSS -->
<link rel="stylesheet" type="text/css" href="<%=path%>/common/grid/gt_grid.css?id=<%=new Date().getTime()%>" />
<link rel="stylesheet" type="text/css" href="<%=path%>/common/grid/skin/vista/skinstyle.css" />
<script type="text/javascript" src="<%=path%>/common/grid/gt_msg_cn.js"></script>
<script type="text/javascript" src="<%=path%>/common/grid/gt_grid_all.js"></script>
<!-- 上面是GRID控件相关的JS和CSS -->
<script language="javascript">
  //表格数据${fn:length(kpiNames)}
  var kpi_Data_table=[
      <c:forEach items="${showHistries}" var="showHistrie" varStatus="status">
        <c:if test="${status.index!=0}">,</c:if>
        ["${showHistrie.dateDesc}" <c:forEach items="${showHistrie.dataList}" var="td"> ,"${td.showValue}"</c:forEach>]
      </c:forEach>
     ];
  //表格头
  var dsOption= {
	fields :[
        <c:forEach items="${kpiNames}" var="kpiName" varStatus="status">
            <c:if test="${status.index!=0}">,</c:if>
            {name : 'column_${status.index}' ,type: 'float' }
        </c:forEach>		
	],
	recordType : 'array',
	data : kpi_Data_table
  }
  
  var colsOption = [
     <c:forEach items="${kpiNames}" var="kpiName" varStatus="status">
        <c:if test="${status.index!=0}">,</c:if>
        //日期
        <c:if test="${status.index==0}">{id: 'column_${status.index}' , header: "${kpiName}" , width :100,align:"center"}</c:if>
        //金额
        <c:if test="${fn:length(kpiNames)<=5}">
            <c:if test="${status.index!=0}">{id: 'column_${status.index}' , header: "${kpiName}" , width :${620/(fn:length(kpiNames)-1)},align:"right"}</c:if>
        </c:if>
        <c:if test="${fn:length(kpiNames)>5}">
            <c:if test="${status.index!=0}">{id: 'column_${status.index}' , header: "${kpiName}" , width :120,align:"right"}</c:if>
        </c:if>
     </c:forEach>                      
   ];  
  
  var gridOption={
		  id : "gridbox",
			width: "738",  //"100%", // 700,
			height: "380",  //"100%", // 330,
			
			container : 'gridbox', 
			replaceContainer : true, 
			
			dataset : dsOption ,
			columns : colsOption,
			pageSizeList : [15,30,50,100,200],
			pageSize : 15 ,
			showGridMenu : true,//显示菜单
			allowFreeze : true,//锁定列
            allowGroup : true,//按组排列
            allowHide : true,//列隐藏	
			toolbarContent : 'nav | pagesize | reload | print  state'
		};

  var mygrid=new Sigma.Grid( gridOption );
  Sigma.Util.onLoad( Sigma.Grid.render(mygrid) );
</script>
<script>
//趋势图
	var amchart_key = "${amchartKey};"//"110-037eaff57f02320aee1b8576e4f4062a";
		window.changeHealthDate = null;
		     //init
			$(document).ready(function(){
				//产品指标分析趋势图显示
				var analyzestock = new SWFObject("<%=path%>/common/amchart/stock/amstock/amstock.swf?id=<%=new Date().getTime()%>", "analyzestock", "720", "320", "8", "#EFF0F2");
				analyzestock.addVariable("rootPath", "<%=path%>/common/amchart/stock/amstock/");
				analyzestock.addVariable("chart_id", "analyzestock");
				analyzestock.addParam("wmode", "transparent");
				var url="<%=path%>/stockChart/showStockChart?1=1&isPrd=1&kpiCodes=${amchart}&needPercent=${ispercent}&kpiType=${kpiType}&reportDate=${queryDate}&rid="+Math.random();
				analyzestock.addVariable("settings_file", escape(url));//月统计
				analyzestock.addVariable("key", amchart_key);
				//指标Codes
				analyzestock.addVariable("kpiCodes", "${amchart}");
				//日期类型
				analyzestock.addVariable("kpiType", "${kpiType}");
				analyzestock.write("chartdiv2");
			});
		     
	//防止多选重复提交	     
	var lazyms=0;
	var olazyms=0;
	var checkevent=false;
	function codeChecked() {
		//增加进度条
		var loadingDiv=window.parent.document.getElementById("loading_wait_Div");
		if(loadingDiv!=null) loadingDiv.style.display="block";
		//
		olazyms=lazyms;
		lazyms++;
		if(checkevent==false){
			setTimeout(codeEventCheck,1000);
		}
		checkevent=true;
	}
	
	function codeEventCheck(){
		if(olazyms==lazyms){
			codeCheckedlazy();
		}else{
			olazyms++;
			setTimeout(codeEventCheck,1000);
		}
	}
	
	//选择指标Kpi
	function codeCheckedlazy(){
		var childKpis=document.getElementsByName("checkbox1");
		var childKpiCode="";
		for(var i=0;i<childKpis.length;i++){
			if(childKpis[i].checked==true){
					if(childKpiCode.length>0) childKpiCode+=";";
					childKpiCode +=  childKpis[i].value;
			 }
		}
		
		var overallKpis=document.getElementsByName("checkbox2");
		var overallKpiCode="";
		for(var i=0;i<overallKpis.length;i++){
			if(overallKpis[i].checked==true){
					if(overallKpiCode.length>0) overallKpiCode+=";";
					overallKpiCode +=  overallKpis[i].value;
			 }
		}
		var productId=document.getElementById('productId').value;
	 	var kpiType = document.getElementById('myType').value;
		//不能再选择啦
		document.body.disabled=true;
		//刷新本页
	 	if(kpiType==<%=DwpasCKpiInfo.KPI_TYPE_OF_DAY%>){
			var queryDate= document.getElementById('maxDate').value;
			location.href="<%=path%>/prodAnalyze/kpiAnalyze?menuId=${menuId}&childKpiCode="+childKpiCode+"&overallKpiCode="+overallKpiCode+"&queryDate="+queryDate+"&kpiType="+kpiType+"&productId="+productId;
	 	}else{
	 		var queryDate= document.getElementById('monthDate').value;
	 		queryMonth=queryDate.substr(0,7);
			location.href="<%=path%>/prodAnalyze/kpiAnalyze?menuId=${menuId}&childKpiCode="+childKpiCode+"&overallKpiCode="+overallKpiCode+"&queryMonth="+queryMonth+"&kpiType="+kpiType+"&productId="+productId;
	 	}
	}
	
	function restartOpen(queryDate) {
		var kpiType = document.getElementById('myType').value;
		var productId=document.getElementById('productId').value;
		var childKpiCode=document.getElementById('childKpiCode').value;
		var overallKpiCode=document.getElementById('overallKpiCode').value;
			if (kpiType == <%=DwpasCKpiInfo.KPI_TYPE_OF_DAY%>) {
				if(queryDate==null||queryDate.length==0) queryDate = document.getElementById('maxDate').value;
				location.href='<%=path%>/prodAnalyze/kpiAnalyze?menuId=${menuId}&queryDate='+queryDate+'&kpiType='+kpiType+'&productId='+productId+'&childKpiCode='+childKpiCode+'&overallKpiCode='+overallKpiCode;
			} else{
				queryDate = document.getElementById('monthDate').value;
				queryMonth=queryDate.substr(0,7);
				location.href='<%=path%>/prodAnalyze/kpiAnalyze?menuId=${menuId}&queryMonth='+queryMonth+'&kpiType='+kpiType+'&productId='+productId+'&childKpiCode='+childKpiCode+'&overallKpiCode='+overallKpiCode;
				}
	}
	
	//按日查看
	 function day(){
		 var kpiType=<%=DwpasCKpiInfo.KPI_TYPE_OF_DAY%>;
	     location.href='<%=path%>/prodAnalyze/kpiAnalyze?menuId=${menuId}&kpiType='+kpiType;
	  }
	 
	
	// 	按月查看
	 function month(){
		  var kpiType=<%=DwpasCKpiInfo.KPI_TYPE_OF_MONTH%>;
		  location.href='<%=path%>/prodAnalyze/kpiAnalyze?menuId=${menuId}&kpiType='+kpiType;
		}
	 
	//首页链接
	 function toIndex(){
			location.href='<%=path%>/index/gotoHomePage.html?menuId=<%=indexMenuId%>';
		}
	 //数据下载
	 function dataDown() { 
		//location.href="<%=path%>/AnalyzeDownload/downloadData?&downloadkpiCodes=${downloadkpiCodes}&queryDate=${queryDate}&productId=${productId}&kpiType=${kpiType}";
		//显示进度条
		if(typeof(document.getElementById('result').onreadystatechange) != "undefined") {
		   var loadingDiv=window.parent.document.getElementById("loading_wait_Div");
		   if(loadingDiv!=null) loadingDiv.style.display="block";	
		}
		document.getElementById("downloadKpiData").submit();
	 }   
	 //下载完成，不显示下载进度条
	 var isDownload=false;
	 function frmStatChange(crtFrm)
	 {
		 if(crtFrm.readyState=="loading") isDownload=false;
		 if(isDownload&&crtFrm.readyState=="complete")
		 {
			    //alert('下载完成');
				//显示进度条
				var loadingDiv=window.parent.document.getElementById("loading_wait_Div");
				if(loadingDiv!=null) loadingDiv.style.display="none";
				isDownload=true;
		 }else if(crtFrm.readyState!="loading")
		{
				//显示进度条
				var loadingDiv=window.parent.document.getElementById("loading_wait_Div");
				if(loadingDiv!=null) loadingDiv.style.display="none";
				isDownload=true;			 
		}
	 }
	 
	 //产品选择
	function changeProduct(productId){
		location.href='<%=path%>/prodAnalyze/kpiAnalyze?kpiType=${kpiType}&menuId=${menuId}&productId='+ productId ;
	}

	//自适应高度
	$(document).ready(function changeIframeSize() {
		//var iframeContentHeight = document.body.scrollHeight;
		parent.changeIframeSize(document.body.scrollHeight+180);
	});

	//按日按月插件选择
	window.onload = function() {
		//不可选择
// 		document.body.disabled=true;
		//显示进度条
		var loadingDiv=window.parent.document.getElementById("loading_wait_Div");
		if(loadingDiv!=null) loadingDiv.style.display="block";
		//
		var myKpiType = document.getElementById('myType').value;
		var d = document.getElementById('byDay');
		var m = document.getElementById('byMonth');
		var dayDate = document.getElementById('maxDate');
		var monthDate = document.getElementById('monthDate');
		var wda = document.getElementById('wd');
		var wdaa = document.getElementById('wd2');
		if (myKpiType ==<%=DwpasCKpiInfo.KPI_TYPE_OF_DAY%>) {
			document.getElementById('maxDate').value = document
					.getElementById('theDate').value;
			wda.className = 'bgwd';
			wdaa.className = "bgwd2";
			dayDate.style.display = "";
			monthDate.style.display = "none";
		} else if (myKpiType ==<%=DwpasCKpiInfo.KPI_TYPE_OF_MONTH%>) {
			document.getElementById('monthDate').value = document
					.getElementById('theDate').value;
			wdaa.className = 'bgwd';
			wda.className = "bgwd2";
			dayDate.style.display = "none";
			monthDate.style.display = "";
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
		if(document.getElementById("chartdate-"+chart_id)!=null) document.getElementById("chartdate-"+chart_id).innerText=dateDesc;
	}
	//趋势图加载完成自动调用的函数
	function amChartInited(chart_id){
	  //可选择
	  document.body.disabled=false;
	  //隐藏进度条
	  var loadingDiv=window.parent.document.getElementById("loading_wait_Div");
	  if(loadingDiv!=null) loadingDiv.style.display="none";
	}	 
	
	
	function changeTheDate(n){
		  var kpiType=document.getElementById('myType').value;
		  var childKpiCode=document.getElementById('childKpiCode').value;
		  var overallKpiCode=document.getElementById('overallKpiCode').value;
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
				  location.href="<%=path%>/prodAnalyze/kpiAnalyze?menuId=${menuId}&childKpiCode="+childKpiCode+"&overallKpiCode="+overallKpiCode+"&queryDate="+changedDate+"&kpiType="+kpiType;
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
				  location.href="<%=path%>/prodAnalyze/kpiAnalyze?menuId=${menuId}&childKpiCode="+childKpiCode+"&overallKpiCode="+overallKpiCode+"&queryMonth="+changedDate+"&kpiType="+kpiType;
			  }
		  }
		  
	  }
	
 	//帮助按钮
	  function helpShow(){
	  	document.getElementById('help').style.display="";
	  }
	  function helpHidden(){
	  	document.getElementById('help').style.display="none";
	  }
</script>
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
	<jsp:include page="../common/ChoiceProduct.jsp" />
		<!-- 当前位置 -->
<div class="kpi_position" id="navigationDiv" style="z-index:9999999;">
<div style="padding-top:2px">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-bottom: 8px">
				<tr>
					<td width="54%">
					<div style="padding:3px 0px 0px 11px">
                     <div style="float:left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;当前位置：<a href="javascript:void(0);"
						onclick=" toIndex();">首页</a>&gt;<a
						href="<%=path%>/prodAnalyze/kpiAnalyze.html?menuId=${menuId}">${modulemap.INPUT_MONITORING.menuName }</a>&gt; <font
						color='red'>${productInfo.productName}</font></div>
						<div style="float:left">&nbsp;&nbsp;
						<a href="javascript:void(0);" onclick="choiceRrdInfo();"><img src="<%=path%>/common/images/jwy_button_01.gif" border="0" /></a></div>
	                </div>
					</td>
					<td width="21%">
						<div align="right">
							<div id="wd"
								style="width: 84px; height: 22px; text-align: center; line-height: 22px; float: left; margin-right: 15px">
								<a href="javascript:void(0);" onclick="day();"><span
									id="byDay">按日查看</span> </a>
							</div>
							<div id="wd2"
								style="width: 84px; height: 22px; text-align: center; line-height: 22px; float: left;">
								<a href="javascript:void(0);" onclick="month();"> <span
									id="byMonth">按月查看 </span>
								</a>
							</div>
						</div>
					</td>

					<td width="25%" style="text-align: center;">数据时间：
					<a href="javascript:void(0);" onclick="changeTheDate(-1);"><img style="vertical-align: middle;" align="middle"  src="<%=path%>/common/images/ddy_2.gif" width="11" height="23" border="0" /></a>
					<input id="maxDate" name="maxDate" type="text" class="ddy_index_input" readonly="readonly" value=""
						onclick="WdatePicker({isShowWeek:true,isShowClear:false,readOnly:true,startDate:'${queryDate} ',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true,doubleCalendar:true,onpicked:function(dp){restartOpen(dp.cal.getNewDateStr());}})"  style="cursor: pointer; " />
					<input id="monthDate" name="monthDate" type="text" class="ddy_index_input" readonly="readonly" onchange="restartOpen();"
						onclick="setMonth(this,this,'restartOpen();')" value=""
						style="cursor: pointer; " />
					<a href="javascript:void(0);" onclick="changeTheDate(1);"><img style="vertical-align: middle;" align="middle"  src="<%=path%>/common/images/ddy_1.gif" width="11" height="23" border="0" /></a>
					</td>
				</tr>
			</table>			
					
 </div>
</div>
<!-- 当前位置结束 -->
<div class="clear"></div>
		
<div class="ddy_channel2" ></div>
	
	<div class="index_home_2">
		<div>
			<img src="<%=path%>/common/images/wph_8.gif" />
		</div>
		<div class="">
			<input id="productId" name="productId" type="hidden" value="${productId}" />
			<input id="myType" name="myType" type="hidden" value="${kpiType}" />
			<input id="theDate" name="theDate" type="hidden" value="${queryDate}" />
			<input type="hidden" value="${childKpiCode}" name="childKpiCode" id="childKpiCode" />
			<input type="hidden" value="${overallKpiCode}" name="overallKpiCode" id="overallKpiCode" />
			
		</div>

		<!--正文-->

		<div class="index_title">
			<div class="index_title_pd">
				<table>
					<tr>
						<td><img src="<%=path%>/common/images/new_jwy_4.gif" /></td>
						<td>&nbsp;<!-- 产品监控-->${modulemap.INPUT_MONITORING.moduleName }
						</td>
					</tr>
				</table>
			</div>
			<span class="box-top-right">
				<em style="background: url(<%=basePath%>/common/images/askicon5.png); cursor: pointer"
					class="box-askicon" onmouseover="helpShow();"
					onmouseout="helpHidden();"></em>
			</span>
			<div id="help" style="display: none; font-size: 12px;">
				<div style="position:relative;z-index:99999999; margin:0px 0px 0px 310px ">
										<div class="pop_w">
										  <div class="pop_z" style="z-index:99999; left:260px;">	<img src="<%=path%>/common/images/pop_tip.png" /></div>
       
<div class="pop_x">								<p>
							<b>${modulemap.INPUT_MONITORING.moduleName}：</b>
						</p>
						<p style="font-weight: normal;">
							${modulemap.INPUT_MONITORING.remark}</p>
</div>
</div></div>
			</div>
		</div>

		<div class="index_report">
			<div class="clear"></div>
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				style="background-color: #e8f0f0">
				<tr>
					<td width="25%" valign="top">
						<!-- 大类指标-->
						 <c:forEach items="${mapCodeKpiInfo}" var="syskpi">
							<div style="margin-left: 13px">
								<div class="zbfx_kj_1">
									<div style="padding: 10px 0px 0px 30px">${syskpi.key}</div>
								</div>
								<div class="zbfx_kj_2">
									<!--循环以下div-->
									<c:forEach items="${syskpi.value}" var="ccodes">
										<div class="zbfx_kj_float">
											<table width="100%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td width="24%" valign="top">
														<div align="center">
															<c:choose>
																<c:when test="${ccodes.marked == 'true'}">
																	<input type="checkbox" name="checkbox1"
																		value="${ccodes.kpiCode}" checked="checked"
																		id="${ccodes.kpiCode}"
																		onclick="javascrpit:codeChecked()" />
																</c:when>
																<c:otherwise>
																	<input type="checkbox" name="checkbox1"
																		value="${ccodes.kpiCode}" id="${ccodes.kpiCode}"
																		onclick="javascrpit:codeChecked()" />
																</c:otherwise>
															</c:choose>
														</div>
													</td>
													<td width="76%">
														<label for="${ccodes.kpiCode}" style='cursor: pointer;'>${ccodes.dispName}</label>
													</td>
												</tr>
											</table>
										</div>
									</c:forEach>
									<div class="clear"></div>
								</div>
								<div>
									<img src="<%=path%>/common/images/ddy_17.gif" />
								</div>
							</div>
						</c:forEach> 
					</td>

					<td width="75%" valign="top">
						<div style="margin-left: 13px">
							<div class="zbfx_kj_1right">
								<div style="padding: 10px 0px 0px 30px">大盘指标</div>
							</div>
							<div class="zbfx_kj_2right">
								<c:forEach items="${grailKpis}" var="staticzb">
									<div class="zbfx_kj_float2">
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
											<tr>
												<td width="20%" valign="top">
													<div align="center">
														<c:choose>
															<c:when test="${staticzb.marked eq true}">
																<input id=${staticzb.kpiCode } type="checkbox"
																	name="checkbox2" value="${staticzb.kpiCode}"
																	checked="checked" id="overallKpiCode"
																	onclick="codeChecked();" />
															</c:when>
															<c:otherwise>
																<input id="${staticzb.kpiCode}" type="checkbox"
																	name="checkbox2" value="${staticzb.kpiCode}"
																	onclick="codeChecked();" />
															</c:otherwise>
														</c:choose>
													</div>
												</td>
												<td width="80%"><label for="${staticzb.kpiCode}"
													style='cursor: pointer;'>${staticzb.dispName}</label></td>
											</tr>
										</table>
									</div>
								</c:forEach>
								<div class="clear"></div>
							</div>
							<div>
								<img src="<%=path%>/common/images/ddy_20.gif" />
							</div>
						</div> <!-- 趋势图 -->
						<div class="biz-right2" style="position: relative;">
							<div style="margin: 0px 0px 10px -40px;z-index:99;" id="chartdiv2"></div>
							<div id="chartdate-amline3" style="position: absolute; width: 120px;  top: 40px;display:none;">
							<c:choose>
								<c:when test="${kpiType == 1}">
						   			${queryDate}
						    	</c:when>
								<c:otherwise>
									${queryDate}
								</c:otherwise>
							</c:choose>
							</div>
						</div> 
						<!-- 下载数据 -->
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td><div style="margin: 10px 0px 10px 10px" >
										<a href="javascript:viod(0)" onclick="dataDown();" id="downloadBtn"><img
											src="<%=path%>/common/images/ddy_22.gif" border="0" /></a>
									</div></td>
							</tr>
						</table>
						<!-- 数据列表 -->
						<div id="gridbox" style="border: 0px solid #cccccc; background-color: #f3f3f3; padding: 5px; height: 200px; width: 700px;"/>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<form action="<%=path%>/AnalyzeDownload/downloadData?&downloadkpiCodes=${downloadkpiCodes}&queryDate=${queryDate}&productId=${productId}&kpiType=${kpiType}" name="downloadKpiData" id="downloadKpiData" method="post" target="result">
	</form>
	<iframe name="result" id="result" src="about:blank" frameborder="0" width="0" height="0" onreadystatechange="frmStatChange(this);"></iframe>
</body>
</html>
