<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
<title>${systemName}</title>
<link href="<%=path%>/common/css/lwt.css" rel="stylesheet" type="text/css" />

<script src="<%=path%>/common/amchart/column/amcolumn/swfobject.js"
	type="text/javascript"></script>

<script src="<%=path%>/common/amchart/stock/amstock/swfobject.js"
	type="text/javascript"></script>
<script type="text/javascript" src="<%=path%>/common/js/amcharts.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/amfallback.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/raphael.js"></script>
<script type="text/javascript"
	src="<%=path%>/common/js/jquery-1.4.3.min.js"></script>
	
<script type="text/javascript"
	src="<%=path%>/common/js/json.js"></script>
<script type="text/javascript">
  window.onload = function () {
	  if("${flag}"=="1004"){
		  document.getElementById("dateButtons").style.display="none";
	  }
	  
	  var kpiCode = document.getElementById("kpiInfoCode").value;

	  if("${kpiChartType}" == "1")	  
	  {
		  var kpiCodes = "${windowLocalKpiCodes}";
	      var kpiType = ${kpiType};
		  var quaryDate = ${quaryDate};
		  var swfLine = new SWFObject("<%=path%>/common/amchart/stock/amstock/amstock.swf", "amline3", "997", "400", "8", "#FFFFFF");
			swfLine.addParam("wmode", "transparent");
			swfLine.addVariable("path", "/amchart/stock/amstock/");
			swfLine.addVariable("chart_id", "amline3");
			swfLine.addVariable("key", "${amchartKey}");
	      var StockChartUrl="<%=path%>/misStockChart/showStockChart?1=1&kpiCodes="+kpiCodes+";&needPercent=1&lastYearValue=0&kpiType="+kpiType+"&yesOrNoDetailsAnalysis=yes&quaryDate="+quaryDate+"&haveEvent=1&rid="+Math.random();
	      swfLine.addVariable("settings_file", escape(StockChartUrl))
		  swfLine.write("threadDetailChartContentDiv");	     
	      
	      //关联指标 无去年同期 隐藏
	      
	      document.getElementById("samePeriodPastPear").style.display='none';
	      document.getElementById("samePeriodPastPearShow").style.display='none';
	      document.getElementById("samePeriodPastPearHide").style.display='none';
	      
	      //显示归一化按钮
	      document.getElementById("normalization").style.display="";
	      document.getElementById("normalizationOpen").style.display="";     
	      document.getElementById("normalizationClose").style.display="";
	      
	      document.getElementById("openNormalizationButton").disabled = "true";
	      document.getElementById("closeNormalizationButton").disabled = null;

	     
	  }else
		  {
		  var kpiType = ${kpiType};
		  var quaryDate = ${quaryDate};
		  var swfLine = new SWFObject("<%=path%>/common/amchart/stock/amstock/amstock.swf", "amline3", "997", "400", "8", "#FFFFFF");
			swfLine.addParam("wmode", "transparent");
			swfLine.addVariable("path", "/amchart/stock/amstock/");
			swfLine.addVariable("chart_id", "amline3");
			swfLine.addVariable("key", "${amchartKey}");
	      var StockChartUrl="<%=path%>/misStockChart/showStockChart?1=1&kpiCodes="+kpiCode+";&needPercent=0&lastYearValue=1&kpiType="+kpiType+"&yesOrNoDetailsAnalysis=yes&isProphaseValue=yes&quaryDate="+quaryDate+"&haveEvent=1&rid="+Math.random();
	      swfLine.addVariable("settings_file", escape(StockChartUrl))
		  swfLine.write("threadDetailChartContentDiv");
		  
		  }
	  
	  
	  
 
    //自适应
		var iframeContentHeight = document.body.scrollHeight;
		parent.changeIframeSize(iframeContentHeight);
	 
}
//amcharts图表生成时，自动调用函数
  function amChartInited(chart_id){
  	flashMovie = document.getElementById(chart_id);
  	
  	window.setTimeout(function(){
  		var kpicode = document.getElementById("kpiInfoCode").value;
        document.getElementById("amline3").hideGraph("0", kpicode+"_QNTQ");
  	},1);
  	
  	showLoading(false);	
  }
  
//   function amRolledOver(chart_id, date, period, data_object)
//   {
// 	  alert(JSON.stringify(data_object));
//   }
  
  
//显示/隐藏趋势图大事件
  function hideOrShowEvent(Obj)
  {
	     
	  if(Obj == 1 || Obj == undefined)
	     {
	    	 document.getElementById("showEventButton").disabled = "true";
	    	 document.getElementById("hideEventButton").disabled = null;
	     }
	     else
	     {
	    	 document.getElementById("showEventButton").disabled = null;
	    	 document.getElementById("hideEventButton").disabled = "true";
	     }
	  
  	 if(Obj == 1)
  	 {
  		 document.getElementById("amline3").showEvents(); 
  	 }else{
  		 document.getElementById("amline3").hideEvents();
  	 }
  	 
  }

  //显示/隐藏趋势图去年同期值
  function hideOrShowHistoryGraph(Obj)
  {
	  var kpicode = document.getElementById("kpiInfoCode").value;
	  
	  if(Obj == 1 || Obj == undefined)
	     {
	    	 document.getElementById("showGraphButton").disabled = "true";
	    	 document.getElementById("hideGraphButton").disabled = null;
	     }
	     else
	     {
	    	 document.getElementById("showGraphButton").disabled = null;
	    	 document.getElementById("hideGraphButton").disabled = "true";
	     }
	  
  	 if(Obj == 1)
  	 {
  		 document.getElementById("amline3").showGraph("0", kpicode+"_QNTQ");
  	 }else{
  		 document.getElementById("amline3").hideGraph("0", kpicode+"_QNTQ"); 
  	 }
  	 
  }

  function timeChangeEvents(kpiType)
  {   
	  if("${kpiChartType}" == "1")	  
	  {
		  var kpiCodes = "${windowLocalKpiCodes}";
		  window.location = '<%=path%>/dwmisDeptKpiData/detailsAnalysis?1=1&kpiCodes='+kpiCodes+'&kpiType='+kpiType;
		  
	  } else
	   {
		  var kpiCode = document.getElementById("kpiInfoCode").value;
		  window.location = '<%=path%>/dwmisDeptKpiData/detailsAnalysis?1=1&kpiCode='+kpiCode+'&kpiType='+kpiType;
	   }
  }
  
  
  function ShowChildPointer(obj){
	  var quaryDate = ${quaryDate};
	  var kpiCodes =  "${windowLocalKpiCodes}";
     if(obj == '1' || obj == undefined)
     {
    	 document.getElementById("openNormalizationButton").disabled = "true";
    	 document.getElementById("closeNormalizationButton").disabled = null;
     }
     else
     {
    	 document.getElementById("openNormalizationButton").disabled = null;
    	 document.getElementById("closeNormalizationButton").disabled = "true";
     }     
     var kpiType = ${kpiType};
   //关联指标趋势图  
     if(obj== '0' )
     {
    	var swfLine = new SWFObject("<%=path%>/common/amchart/stock/amstock/amstock.swf", "amline3", "997", "400", "8", "#FFFFFF");
 		swfLine.addParam("wmode", "transparent");
 		swfLine.addVariable("path", "/amchart/stock/amstock/");
 		swfLine.addVariable("chart_id", "amline3");
 		swfLine.addVariable("key", "${amchartKey}");
 		var StockChartUrl="<%=path%>/misStockChart/showStockChart?1=1&kpiCodes="+kpiCodes+";&needPercent=0&lastYearValue=0&kpiType="+kpiType+"&yesOrNoDetailsAnalysis=yes&quaryDate="+quaryDate+"&haveEvent=1&rid="+Math.random();
        swfLine.addVariable("settings_file", escape(StockChartUrl))
 		swfLine.write("threadDetailChartContentDiv");
     }
     else
     {
    	var swfLine = new SWFObject("<%=path%>/common/amchart/stock/amstock/amstock.swf", "amline3", "997", "400", "8", "#FFFFFF");
  		swfLine.addParam("wmode", "transparent");
  		swfLine.addVariable("path", "/amchart/stock/amstock/");
  		swfLine.addVariable("chart_id", "amline3");
  		swfLine.addVariable("key", "${amchartKey}");
        var StockChartUrl="<%=path%>/misStockChart/showStockChart?1=1&kpiCodes="+kpiCodes+";&needPercent=1&lastYearValue=0&kpiType="+kpiType+"&yesOrNoDetailsAnalysis=yes&quaryDate="+quaryDate+"&haveEvent=1&rid="+Math.random();
        swfLine.addVariable("settings_file", escape(StockChartUrl))
  		swfLine.write("threadDetailChartContentDiv");
     }     
  }  
  
  function ShowChildPointer1(){
	  var kpiCodes = document.getElementById("kpiInfoCode").value + ";";  
	  var kpiCode = document.getElementById("kpiInfoCode").value;  
	  <c:forEach items="${listLinkKpiInfoMassageList}" var="stock">
		if(document.getElementById("${stock.kpiCode}").checked==true){
			kpiCodes = kpiCodes + "${stock.kpiCode};";
		}
     </c:forEach>     

     window.location = '<%=path%>/dwmisDeptKpiData/detailsAnalysis?1=1&kpiCodes='+kpiCodes;
     }  
  
  
  
  
  amClickedOnEvent = function (chart_id, date, description, id, url) {    
	  date= date.trim();
	    $.ajax({
			        type: "POST",                                                                 
			        url:encodeURI("<%=path%>/MisEvent/showEventMessageByEventId.html?1=1&eventId="+ id),
					success : function(msg) {
						var msg = eval(msg);
							var eventTypeName = msg.eventTypeName;
							var eventTitle = msg.title;
							var content = msg.content;
							document.getElementById('dsjtck_pop').className = 'pop_block';
							var dateDesc = "";
							if (date.length == 6) {
								dateDesc = date.substring(0, 4) + "年"
										+ date.substring(4, 6) + "月";
							} else if (date.length == 8) {
								dateDesc = date.substring(0, 4) + "年"
										+ date.substring(4, 6) + "月"
										+ date.substring(6, 8) + "日";
							}
							document.getElementById('eventDate').innerHTML = dateDesc;
							document.getElementById('eventTypeName').innerHTML = eventTypeName;
							document.getElementById('eventTitle').innerHTML = eventTitle;					
							document.getElementById('bigEventContent').innerHTML = content;

					}
				});

	};
</script>

</head>
<body class="indexbody" style="height: 100%">
	<input type="hidden" id="kpiInfoCode" value="${dwmisKpiInfo.kpiCode}" />
	<!--大事件弹窗开始-->
	<div class="pop_none" id="dsjtck_pop">
		<div class="layer33" style="z-index: 999">
			<div class="layer3"
				style="background-color: #fff; z-index: 9999; margin-top: 10px"
				id="dsjtck_pop_title">
				<div style="margin:-3px 0px 0px -3px">
				<table border="0" style="height: 100%;"  width="765">
					<tr>
						<td colspan="3"  valign="top">
							<h2>
								查看详情 <b> <a href="Javascript:void(0)"
									onclick="javascript:getElementById('dsjtck_pop').className='lwtpop_gb'">关闭</a>
								</b>							</h2>						</td>
					</tr>
					<tr>
						<td colspan="3">
							<div class="layer3_mm">
								<div align="left"></div>
							</div>						</td>
					</tr>
					<tr>
						<td align="center" colspan="3">
							<div style="width: 700px; font-size: 14px; font-weight: bold;"
								id="eventTitle"></div>						</td>
					</tr>
					<tr>
						<td colspan="3">
							<div class="layer3_mm">
								<div align="left"></div>
							</div>						</td>
					</tr>
					<tr>
						<td colspan="3">
						<div style="margin:auto; width:340px">
						  <div style="float: left; padding: 0 10px">
						    <div align="center">发生时间:</div>
						  </div>
						  <div id="eventDate" style="float: left; padding: 0 10px"></div>
						  <div style="float: left; padding: 0 10px">
						    <div align="center">事件类别：</div>
					  </div>						  <div id="eventTypeName" style="float: left; padding: 0 10px"></div>
					  </div>
					  </td></tr>
					<tr>
						<td colspan="3">
								<div align="left"></div>
								</td>
							<td width="200">
						
						</td>
					</tr>
					<tr>
						<td colspan="3">
							<div id="bigEventContent"
								style="position: relative; overflow-x: hidden; ;width: 765px; height: 200px; overflow-y: auto; margin: 0px 0px 0px 0px">							</div>						</td>
					</tr>
				</table>
				
		  </div>
			</div>
		</div>
	</div>

	<!--头部结束-->
	<div class="index_home">
		<div>
			<img src="<%=path%>/common/images/wph_8.gif" />
		</div>
		
		<div class="ddy_channel1">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				style="margin-top: -2px">
				<tr>
					<td width="67%">当前位置：<a href="<%=path%>/dwmisDeptKpiData/showDeptKpiData.html" style="font-weight: bold;" >指标监控</a><span style="font-weight: bold;">    &gt;    </span>详情分析-${dwmisKpiInfo.kpiNameShow}
					</td>
					<td width="33%" valign="top"><div align="right"
							style="margin-top: 4px">报告时间：${dataDate}</div></td>
				</tr>
			</table>

		</div>

		<!--正文-->

		<div class="index_title">
			<div class="index_title_pd2">指标名称-${dwmisKpiInfo.kpiNameShow}</div>
		</div>
		<div class="index_title_yy"></div>
		<form action="" name="selectEventForm" method="post">
			<table width="100%" height="30" border="0" cellpadding="0"
				cellspacing="0"
				style="color: #505050; margin-top: 10px; margin-bottom: 10px">
				<tr>


					<td width="22%">
						<ul id="main_box">
							<li class="select_box" onclick="divblackID()" style="right:100px;"><span><a
									href="#">关联指标</a></span></li> <script>
								
										function divblackID() {
											document.getElementById('divnoneID').style.display = 'block';
										}
										function divnoneID() {
											document.getElementById('divnoneID').style.display = 'none';
										}
								
									</script>
								<div style="position: relative" id="wd" >
									<div id="divnoneID"
										style="position: absolute; z-index: 9999; margin:20px 0px 0px 20px;width: 400px; background: #f0f3ea; border: 1px solid #babdb4; display: none" >
										<div style="padding-top: 3px">
											<label for="" style="cursor: hand; float: left;">关联子指标:</label>
										</div>
										<div class="clear"></div>
										<div align="center">
											<table cellspacing="0" cellpadding="0"
												style="background: #f7fcf8; width: 380px; border: 1px solid #babdb4; margin-bottom: 5px">
										
											<c:if test="${yesOrNoLinkKpiInfoMassage == 1}">
												<c:forEach items="${listLinkKpiInfoMassageList}"
													var="kpiInfoMassage" varStatus="status">
													<tr>
														<td width="4%" height="33"><input type="checkbox"
																	name="checkbox" id="${kpiInfoMassage.kpiCode}"
																	value="${kpiInfoMassage.kpiCode}" /></td>
														<td width="96%"><div align="left">
															<label for="${kpiInfoMassage.kpiCode}"
																			style="cursor:pointer; float: left;">${kpiInfoMassage.kpiNameShow}</label>
															</div></td>
														</tr>	
												</c:forEach>
											</c:if>	
											<c:if test="${yesOrNoLinkKpiInfoMassage == 0}">
											    <tr>
													<td><span style="font-size: 20px;color: #FF0000;">该指标无关联子指标</span></td>
												</tr>												
											</c:if>	
																					
												<tr>
													<td></td>
												</tr>
											</table>
										</div>
										<div style="margin: 0px 0px 5px 3px">
										<c:if test="${yesOrNoLinkKpiInfoMassage == 1}">
										<input type="button" id="confirmButton" name="confirmButton"
												value="确定" onclick="ShowChildPointer1();divnoneID();" /> <input
												type="button" value="关闭" onclick="divnoneID()" />
										</c:if>
											<c:if test="${yesOrNoLinkKpiInfoMassage == 0}">
											<input type="button" value="关闭" onclick="divnoneID()" />
											</c:if>
										</div>
									</div>
								</div>
						</ul>
					</td>
					<td width="11%"></td>

					<td width="18%">
						<div id="dateButtons" style="display: block;">
						<input type="button" id="dayButton" style='cursor:pointer;'
							name="dayButton" value="日" onclick="timeChangeEvents(1002);"
							<c:if test="${kpiType==1002}">disabled</c:if> /> |<input
							type="button" id="weekButton" name="weekButton" value="周" style='cursor:pointer;'
							onclick="timeChangeEvents(1003);"
							<c:if test="${kpiType==1003}">disabled</c:if> /> |<input
							type="button" id="monthButton" name="monthButton" value="月" style='cursor:pointer;'
							onclick="timeChangeEvents(1004);"
							<c:if test="${kpiType==1004}">disabled</c:if> />
							</div>
						</td>

					<td id="normalization" width="8%" style="display: none;">归一化：</td>
					<td id="normalizationOpen" width="4%" style="display: none;">
						<input type="button" id="openNormalizationButton"
						name="openNormalizationButton" value="打开" style='cursor:pointer;'
						onclick="ShowChildPointer('1');" />

					</td>
					<td id="normalizationClose" width="13%" style="display: none;">
						<input type="button" id="closeNormalizationButton"
						name="closeNormalizationButton" value="关闭" style='cursor:pointer;'
						onclick="ShowChildPointer('0');" />

					</td>
					<td width="7%">大事记：</td>
					<td width="4%"><input type="button" id="showEventButton"
						name="showButton" value="显示" onclick="hideOrShowEvent(1);" style='cursor:pointer;'
						disabled /></td>
					<td width="13%"><input type="button" id="hideEventButton" style='cursor:pointer;'
						name="hideButton" value="隐藏" onclick="hideOrShowEvent(2);" /></td>

					<td id="samePeriodPastPear" width="8%">去年同期值：</td>
					<td id="samePeriodPastPearShow" width="4%"><input style='cursor:pointer;'
						type="button" id="showGraphButton" name="showGraphButton"
						value="显示" onclick="hideOrShowHistoryGraph(1);" /></td>
					<td id="samePeriodPastPearHide" width="13%"><input style='cursor:pointer;'
						type="button" id="hideGraphButton" name="hideGraphButton"
						value="隐藏" onclick="hideOrShowHistoryGraph(2);" disabled /></td>


				</tr>
			</table>
		</form>

		<div id="threadDetailChartContentDiv" style="margin: auto;"></div>

		<div style=" margin-top: 10px;">
			<table width="100%" height="100%" border="0" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="87%"><table width="100%" border="0" cellspacing="0"
							cellpadding="0">
							<tr>
								<td></td>
							</tr>
						</table></td>
					<td width="13%"><script type=text/javascript>
						function show_hiddendiv() {
							document.getElementById("hidden_div").style.display = 'none';
							document.getElementById("_strHref").href = 'javascript:hidden_showdiv();';
							document.getElementById("_strSpan").innerHTML = "显示数据";
						}
						function hidden_showdiv() {
							document.getElementById("hidden_div").style.display = 'block';
							document.getElementById("_strHref").href = 'javascript:show_hiddendiv();';
							document.getElementById("_strSpan").innerHTML = "隐藏数据";
						}
					</script>
						<div
							style="background-image: url(../common/images/yb_044.gif); width: 90px; height: 22px;">
							<div
								style="padding-top: 4px; padding-left: 24px; font-size: 12px;">
								<a id="_strHref" href="javascript:show_hiddendiv();"><span
									id="_strSpan">隐藏数据</span></span> </a>
							</div>
						</div></td>
				</tr>
			</table>
		</div>
		<div class="index_report" id="hidden_div">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				style="background-color: #e8f0f0">
				<tr>
					<td valign="top">
					<c:choose>
					<c:when test="${dwmisKpiInfoList == null}">
						<table width="100%" height="0" border="1" align="right"
							cellpadding="0" cellspacing="0" bordercolor="#FFFFFF">
							<tr class="report_divblue" style="font-weight: bold; color: #666">

								<td width="18%" height="40" bgcolor="#999999"><div
										align="center" class="STYLE1" style="color: #000000">日期</div></td>
							    <td width="82%" height="40" bgcolor="#999999"><div
										align="center" class="STYLE1" style="color: #000000">${dwmisKpiInfo.kpiNameShow}(<c:if test="${dwmisKpiInfo.unitId == 5001}">元</c:if><c:if test="${dwmisKpiInfo.unitId == 5002}">笔</c:if><c:if test="${dwmisKpiInfo.unitId == 5003}">个</c:if><c:if test="${dwmisKpiInfo.unitId == 5004}">%</c:if><c:if test="${dwmisKpiInfo.unitId == 5005}">基点</c:if>)</div></td>
								<!-- 循环以TR -->
							</tr>
							<c:forEach items="${dwmisKpiDataList}" var="dwmisKpiData">
								<tr>
									<td height="30"><div align="center">${dwmisKpiData.showReportDate}</div></td>
									<td><div align="center">${dwmisKpiData.showKpiValue}</div></td>
								</tr>
							</c:forEach>
						</table>
						</c:when>
						<c:otherwise>
						<table width="100%" height="0" border="1" align="right"
							cellpadding="0" cellspacing="0" bordercolor="#FFFFFF">
							<tr class="report_divblue" style="font-weight: bold; color: #666">

								<td width="18%" height="40" bgcolor="#999999"><div
										align="center" class="STYLE1" style="color: #000000">日期</div></td>
							    <c:forEach items="${dwmisKpiInfoList}" var="dwmisKpiInfo">
							
							 <td  height="40" bgcolor="#999999"><div
										align="center" class="STYLE1" style="color: #000000">${dwmisKpiInfo.kpiNameShow}(<c:if test="${dwmisKpiInfo.unitId == 5001}">元</c:if><c:if test="${dwmisKpiInfo.unitId == 5002}">笔</c:if><c:if test="${dwmisKpiInfo.unitId == 5003}">个</c:if><c:if test="${dwmisKpiInfo.unitId == 5004}">%</c:if><c:if test="${dwmisKpiInfo.unitId == 5005}">基点</c:if>)</div></td>
							</c:forEach>
							</tr>
									<c:forEach items="${kpiDataByCodesMap}" var="kpiDataMap">
										<tr>
											<td height="30"><div align="center">${kpiDataMap.key}</div></td>
											<c:forEach items="${kpiDataMap.value}" var="kpiDataList">
												<td><div align="center">${kpiDataList.showKpiValue}</div></td>
											</c:forEach>
										</tr>
									</c:forEach>
								</table>							
							</c:otherwise>
						</c:choose>		
					</td>
				</tr>
			</table>
			
		</div>

	</div>




</body>
</html>