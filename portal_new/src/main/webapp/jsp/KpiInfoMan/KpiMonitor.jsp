<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="com.infosmart.portal.pojo.DwpasCKpiInfo"%>
<%@ page import="java.util.Date"%>
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
<title>${systemName}监控</title>
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
<script>
//趋势图
	var amchart_key = "${amchartKey};"//"110-037eaff57f02320aee1b8576e4f4062a";
		window.changeHealthDate = null;
			$(document).ready(function(){
				var kpiCodes="CUS101000BA01M;CUS101000BB01M;MLD1020000308M;MLD2001020308M";
				var healthtre = new SWFObject("<%=path%>/common/amchart/column/amcolumn/amcolumn.swf", "chartdiv", "998", "260", "8", "#EFF0F2");
					//healthtre.addParam("wmode", "opaque");
					healthtre.addVariable("path", "<%=path%>/common/js");
					healthtre.addVariable("chart_id", "chartdiv");
					var url="<%=basePath%>/columnOrLineChart/showChartSetting.html?1=1&kpiCodes="+kpiCodes+";&needPercent=0&kpiType=<%=DwpasCKpiInfo.KPI_TYPE_OF_MONTH%>&reportDate=${tomonth}&chartTypes=column;column;line;line&rid="+Math.random();
					healthtre.addVariable("settings_file", escape(url));//设置文件
					var dataUrl="<%=basePath%>/columnOrLineChart/showChartData.html?1=1&kpiCodes="+kpiCodes+";&needPercent=0&kpiType=<%=DwpasCKpiInfo.KPI_TYPE_OF_MONTH%>&reportDate=${tomonth}&chartTypes=column;column;line;line&rid="+Math.random();
					healthtre.addVariable("data_file", escape(dataUrl));//数据文件
					healthtre.addVariable("key", amchart_key);
					healthtre.write("chartdiv2");
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
					if(childKpiCode.length>0) childKpiCode+=",";
					childKpiCode +=  childKpis[i].value;
			 }
		}
		
		var overallKpis=document.getElementsByName("checkbox2");
		var overallKpiCode="";
		for(var i=0;i<overallKpis.length;i++){
			if(overallKpis[i].checked==true){
					if(overallKpiCode.length>0) overallKpiCode+=",";
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

//选择日期重新加载
     function restartOpen(){
	    var queryDate=document.getElementById('maxDate').value;
	    if(queryDate!=""){
         location.href='<%=path%>/dwpasStDeptKpiData/showKpiMonitor.html?queryDate='+queryDate;
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
			location.href='<%=path%>/index/getInto.html?menuId=${menuId}';
		}
	 //数据下载
	 function dataDown() { 
		location.href="<%=path%>/AnalyzeDownload/downloadData?&downloadkpiCodes=${downloadkpiCodes}&queryDate=${queryDate}&productId=${productId}&kpiType=${kpiType}";
	 }   
	 
	 //产品选择
	function changeProduct(productId){
		location.href='<%=path%>/prodAnalyze/kpiAnalyze?kpiType=${kpiType}&menuId=${menuId}&productId='+ productId + '&queryDate=${queryDate}';
	}
/* 
	//自适应高度
	$(document).ready(function changeIframeSize() {
		var iframeContentHeight = document.body.scrollHeight;
		parent.changeIframeSize(iframeContentHeight);
	}); */

	//按日按月插件选择
	window.onload = function() {
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
	 //日期控件左右点击事件
	   function changeTheDate(n){
			  var reg=new RegExp("-","g");
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
				  location.href='<%=path%>/dwpasStDeptKpiData/showKpiMonitor.html?queryDate='+changedDate;
			  }
		  }
	
 	//帮助按钮
	  function helpShow(val){
	  	document.getElementById('help'+val).style.display="";
	  }
	  function helpHidden(val){
	  	document.getElementById('help'+val).style.display="none";
	  }
	  //切换tab
	  function switchTab(tabId,deptId) {
			var deptCount=10;
			for (i = 0; i<=10; i++) {
				if(document.getElementById("tab_" + i)==null) continue;
				document.getElementById("tab_" + i).getElementsByTagName("a")[0].className = "";
			}
			document.getElementById("tab_"+tabId).getElementsByTagName("a")[0].className = "on";
			document.getElementById("iframepage").src="<%=path%>/dwpasStDeptKpiData/loadDeptKpiData.html?deptId="+deptId;
		}
	  //自适应高度
		function iFrameHeight() {   
			var ifm= document.getElementById("iframepage");   
			var subWeb = document.frames ? document.frames["iframepage"].document : ifm.contentDocument;   
			if(ifm != null && subWeb != null) {
			   ifm.height = subWeb.body.scrollHeight;
			   try
			   {
				  window.parent.iframeOnload();
			   }
			   catch (e)
			   {
			   }
			}   
		}  
	  
	  
</script>
</head>
<body class="indexbody">
	<!-- 选择产品 -->
	<jsp:include page="../common/ChoiceProduct.jsp" />
	<div class="index_home_2">
		<div>
			<img src="<%=path%>/common/images/wph_8.gif" />
		</div>
		<div class="ddy_channel1">
			<input id="productId" name="productId" type="hidden" value="${productId}" /> 
			<input id="myType" name="myType" type="hidden" value="${kpiType}" />
			<input id="date" name="date" type="hidden" value="${tomonth}" />
			<input type="hidden" value="${childKpiCode}" name="childKpiCode" id="childKpiCode" /> 
			<input type="hidden" value="${overallKpiCode}" name="overallKpiCode" id="overallKpiCode" />
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				style="margin-bottom: 8px">
				<tr>
					<td width="56%">当前位置：<a href="javascript:void(0);"
						onclick=" toIndex();">首页</a>&gt;<a
						href="<%=path%>/prodAnalyze/kpiAnalyze.html?menuId=${menuId}">指标监控
					</a>&gt; <font color='red'>六合彩</font><a href="javascript:void(0);"
						onclick="choiceRrdInfo();">(更改分析产品)</a></td>

					<td width="24%" style="text-align: right;">数据时间： <a
						href="javascript:void(0);" onclick="changeTheDate(-1);"><img
							style="vertical-align: middle;" align="middle"
							src="<%=path%>/common/images/ddy_2.gif" width="11" height="23"
							border="0" /> </a> <input id="maxDate" name="maxDate" type="text"
						readonly="readonly" value="${date}"
						onclick="WdatePicker({startDate:'%y-%M-01 ',dateFmt:'yyyy-MM-dd ',alwaysUseStartDate:true})"
						onchange="restartOpen();"
						style="width: 120px; line-height: 18px; height: 18px; cursor: pointer;" />
						<a href="javascript:void(0);" onclick="changeTheDate(1);"><img
							style="vertical-align: middle;" align="middle"
							src="<%=path%>/common/images/ddy_1.gif" width="11" height="23"
							border="0" /> </a>
					</td>
				</tr>
			</table>
		</div>

		<!--正文-->
		<div class="index_title">
			<div class="index_title_pd">公司销售情况监控</div>
			<span class="box-top-right"> <em
				style="background: url(<%=basePath%>/common/images/askicon5.png); cursor: pointer"
				class="box-askicon" onmouseover="helpShow('1');"
				onmouseout="helpHidden('1');"></em>
			</span>
			<div id="help1" class="tip J-tip"
				style="width: 250px; right: 40px; margin-top: 32px; display: none;font-size: 12px;">
		<p>
			<b>${modulemap.INPUT_MONITORING.moduleName}公司销售情况监控：</b>
		</p>
		<p style="font-weight: normal;">
			${modulemap.INPUT_MONITORING.remark}公司销售情况监控公司销售情况监控公司销售情况监控公司销售情况监控公司销售情况监控
		</p>
			</div>
		</div>
		<div class="index_title_yy"></div>
		<!-- 趋势图 -->
		<div>
			<div style="margin: 0px 0px 0px 0px;" id="chartdiv2" ></div>
		</div>
		<div class="clear"></div>
		<div style="width:100%; height:500px">
			<div class="index_title">
			<div class="index_title_pd">部门销售情况监控</div>
			<span class="box-top-right"> <em
				style="background: url(<%=basePath%>/common/images/askicon5.png); cursor: pointer"
				class="box-askicon" onmouseover="helpShow('2');"
				onmouseout="helpHidden('2');"></em>
			</span>
			
			<div id="help2" class="tip J-tip"
				style="width: 250px; right: 40px; margin-top: 32px; display: none;font-size: 12px;">
		<p>
			<b>部门销售情况监控：</b>
		</p>
		<p style="font-weight: normal;">
			部门销售情况监控部门销售情况监控部门销售情况监控部门销售情况监控部门销售情况监控部门销售情况监控部门销售情况监控
		</p>
			</div>
			<div class="index_title_yy"></div>
			<div id="tabContainer">
			<ul class="mouseover">
				<!-- 列出所有的部门 -->
				<c:set var="firstDeptId">-1</c:set>
				<c:forEach items="${dwpasStDeptNames}" var="deptKpiDateList" step="1"
					varStatus="status">
					<c:if test="${status.index==0}">
						<c:set var="firstDeptId">${deptKpiDateList.deptId}</c:set>
					</c:if>
					<c:if test="${status.index<=6}">
						<li id="tab_${status.index}"><a href="javascript:void(0);"
							<c:if test="${status.index==0}">class="on"</c:if>
							onclick="switchTab(${status.index},'${deptKpiDateList.deptId}');this.blur();return false;">
								<div class="report_list_ftpd" id="${deptKpiDateList.deptId}">${deptKpiDateList.deptName}</div>
						</a></li>
					</c:if>
				</c:forEach>
			</ul>
			<div style="clear: both"></div>
			<div id="con1">
				<iframe
					src="<%=path%>/dwpasStDeptKpiData/loadDeptKpiData.html?deptId=${firstDeptId}"
					id="iframepage" name="iframepage" frameBorder="0" scrolling="no"
					width="100%" onLoad="iFrameHeight()"></iframe>
			</div>
		</div>
		</div>
		
		</div>
	</div>
</body>
</html>
