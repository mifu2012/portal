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
<link href="<%=path%>/common/css/lwt.css" rel="stylesheet"
	type="text/css" />
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
 var amchart_key = "${amchartKey}";
  window.onload = function () {
     //趋势图
	 var so1 = new SWFObject("<%=path%>/common/amchart/stock/amstock/amstock.swf", "amline2", "997", "400", "9", "#EFF0F2");
	 so1.addVariable("path", "<%=path%>/common/amchart/stock/amstock/");
	 so1.addVariable("chart_id", "amline2");
	 so1.addParam("wmode","transparent");
	 var url1="<%=path%>/misStockChart/showStockChart?1=1&kpiCodes=${MisKpiCodes}&needPercent=0&yesOrNoDetailsAnalysis=yes&haveEvent=1&isMIS=isMIS&rid="+Math.random();
	 so1.addVariable("settings_file", escape(url1));
	 so1.addVariable("key", amchart_key);
	 so1.write("chartdiv");
	 
	//弹窗趋势图
	 var so2 = new SWFObject("<%=path%>/common/amchart/stock/amstock/amstock.swf", "amline_new_window", "700", "300", "9", "#EFF0F2");
	 so2.addVariable("path", "<%=path%>/common/amchart/stock/amstock/");
	 so2.addVariable("chart_id", "amline_new_window");
	 so2.addParam("wmode","transparent");
	 var url2="<%=path%>/misStockChart/showStockChart?1=1&kpiCodes=${MisKpiCodes}&needPercent=0&yesOrNoDetailsAnalysis=yes&haveEvent=1&isMIS=isMIS&rid="+Math.random();
	 so2.addVariable("settings_file", escape(url2));
	 so2.addVariable("key", amchart_key);
	 so2.write("chartdiv1");
	 
	 //自适应
	 parent.changeIframeSize(document.body.scrollHeight);

}
	
$(document).ready(function(){
	 $('.son_ul').hide(); 
	 $('.select_box span').hover(function(){ 
										  $(this).parent().find('ul.son_ul').slideDown(); 
										  $(this).parent().find('li').hover(function(){$(this).addClass('hb')},function(){$(this).removeClass('hover')}); 
										  $(this).parent().hover(function(){},
																 function(){
																	 $(this).parent().find("ul.son_ul").slideUp(); 
																	 }
																 );
										  },function(){}
										  );
	 $('ul.son_ul li').click(function(){
									  $(this).parents('li').find('span').html($(this).html());
									  $(this).parents('li').find('ul').slideUp();
									  });
});
function pop_func(eventTitle,eventTypeName,eventStartDate,eventId){
	document.getElementById('dsj_pop').className='pop_block';
	document.getElementById('eventDate').innerHTML=eventStartDate;
	document.getElementById('eventTypeName').innerHTML=eventTypeName;
	document.getElementById('eventTitle').innerHTML=eventTitle;
	document.getElementById('eventContent').innerHTML=document.getElementById("evnetContent_"+eventId).innerHTML;
	
	 //设置DIV高度
    var divTop=0;
	var clientHeight=window.parent.document.getElementById('center_iframe').height;
	if(window.parent!=null)
	{
		
		divTop=window.parent.document.body.scrollTop+window.parent.document.documentElement.scrollTop;
	}else
	{
        divTop=document.body.scrollTop+document.documentElement.scrollTop;
	}
	
	document.getElementById("dsj_pop").style.top=divTop-100+"px";
	document.getElementById("dsj_pop").style.position="absolute";
}

function changeEventTypeMessage(eventType){
	document.getElementById("eventType").value = eventType;
// 	document.getElementById("selectEventForm").submit();
	$("#selectEventForm").submit();
// 	var title = document.getElementById('selectEventTitle').value;
<%-- 	location.href = '<%=path%>/MisEvent/showEventMessage?&eventType='+ eventType+'&title='+ title; --%>
<%-- 	location.href ='<%=path%>/stockChart/showStockChart?1=1&kpiCodes=APY4010000701D&needPercent=1&isMIS=isMIS&eventType='+eventType+'&eventSearchKey='+title; --%>
}


function amClickedOnEvent(chart_id, date, description, id, url){  
	date= date.trim();
	 if(chart_id=="amline_new_window") return;
    $.ajax({
		        type: "POST",                                                                 
		        url:encodeURI("<%=path%>/MisEvent/showEventMessageByEventId.html?1=1&eventId="+ id),
				success : function(msg) {
					var msg = eval(msg);
						var eventTypeName = msg.eventTypeName;
						var eventTitle = msg.title;
						var content = msg.content;
						document.getElementById('dsjtck_pop').className='pop_block';
						var dateDesc = "";
						if (date.length == 6) {
							dateDesc = date.substring(0, 4) + "年"
									+ date.substring(4, 6) + "月";
						} else if (date.length == 8) {
							dateDesc = date.substring(0, 4) + "年"
									+ date.substring(4, 6) + "月"
									+ date.substring(6, 8) + "日";
						}
						document.getElementById('eventInfoDate').innerHTML = dateDesc;
						document.getElementById('eventInfoTypeName').innerHTML = eventTypeName;
						document.getElementById('eventInfoTitle').innerHTML = eventTitle;					
						document.getElementById('eventInfoContent').innerHTML = content;
				}
			});

};

function restartOpen(queryDate){
	location.href="<%=path%>/MisEvent/showEventMessage.html?1=1&queryDate="+queryDate;
}


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
			  location.href="<%=path%>/MisEvent/showEventMessage.html?&queryDate="+changedDate;
	  }
}
</script>

</head>
<body class="indexbody">

	<!--大事件弹窗开始-->
	<div class=" pop_none" id="dsj_pop">
		<div class="layer">
			<div class="layer2" style="background-color: #f8fcf8">
				<h2>
					查看详情<b><a href="Javascript:void(0)"
						onclick="javascript:getElementById('dsj_pop').className='lwtpop_gb'">关闭</a></b>
				</h2>

				<p style="font-size: 14px" id="eventTitle">
					<a href="Javascript:void(0)" onclick="pop_func()"></a>
				</p>
				<div class="layer2_tt">
					发生时间:<span id="eventDate"></span>&nbsp;&nbsp;&nbsp;11事件类别：<span id="eventTypeName" ></span>
				</div>
				<div class="layer2_mm">
					<div align="left" style="overflow-y:auto; height: 65px;"><span id="eventContent"></span>
				
					</div>
					
				</div>
				<div align="center" id="chartdiv1" style="padding-top:10px;"></div>
			</div>
		</div>
	</div>
	<!--弹窗结束-->

	<!--头部结束-->
	<div class="index_home">
		<div>
			<img src="<%=path%>/common/images/wph_8.gif" />
		</div>
		<div class="ddy_channel1">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				style="margin-top: -2px">
				<tr>
					<td width="67%">当前位置：首页&gt;<a href="#">大事件</a></td>
					<td width="10%" valign="top"><div align="right"
							style="margin-top: 4px">报告时间：</div></td>
					<td width="15%">
					<a href="javascript:void(0);" onclick="changeTheDate(-1);"><img style="vertical-align: middle;" align="middle"  src="<%=path%>/common/images/ddy_2.gif" width="11" height="23" border="0" /></a>
					<input id="maxDate" name="maxDate" type="text" class="ddy_index_input" readonly="readonly" value="${queryDate}"
						onclick="WdatePicker({isShowWeek:true,isShowClear:false,readOnly:true,startDate:'${queryDate}',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true,doubleCalendar:true,onpicked:function(dp){restartOpen(dp.cal.getNewDateStr());}})"  style="cursor: pointer; " />		
					<a href="javascript:void(0);" onclick="changeTheDate(1);"><img style="vertical-align: middle;" align="middle"  src="<%=path%>/common/images/ddy_1.gif" width="11" height="23" border="0" /></a>
					</td>
				</tr>
			</table>

		</div>

		<!--正文-->

		<div class="index_title">
			<div class="index_title_pd2">大事件</div>
		</div>
		<div class="index_title_yy"></div>
		<form action="<%=path%>/MisEvent/showEventMessage" id="selectEventForm" method="post">
			<input type="hidden" name="eventType" id="eventType" value="${misEventPo.eventType }"/>
			<table width="100%" height="30" border="0" cellpadding="0"
				cellspacing="0"
				style="color: #505050; margin-top: 10px; margin-bottom: 10px">
				<tr>
					<td width="9%" style="text-align: right">搜索大事件：</td>
					<td width="11%">
						<ul id="main_box">
							<li class="select_box">
								<span><a href="#" id="allType" onclick="changeEventTypeMessage(this.id);">全部动态</a></span>
								<ul class="son_ul" style="display: none" id='ul_select'>
									<c:forEach items="${eventTypeList}" var="eventType"
										varStatus="vs">
										<li ><a href="#"  id="eventName_${eventType.typeId}"
											onclick="changeEventTypeMessage('${eventType.typeId}');">${eventType.typeName}</a></li>
									</c:forEach>
								</ul></li>
						</ul>
					</td>
					<td width="17%">&nbsp;&nbsp;&nbsp;<input id="selectEventTitle"
						type="text" name="title" class="lwt_dsj_input"  value="${misEventPo.title}"/>

					</td>
					<td width="63%"><input type="button" name="botton" value="搜索"
						onclick="changeEventTypeMessage('');"  /></td>
				</tr>
			</table>
		</form>

		<div id="chartdiv"></div>

		<div class="index_report">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				style="background-color: #e8f0f0">
				<tr>
					<td valign="top">
						<table width="100%" height="0" border="1" align="right"
							cellpadding="0" cellspacing="0" bordercolor="#FFFFFF">
							<tr class="report_divblue" style="font-weight: bold; color: #666">
								<td width="17%" height="40" bgcolor="#999999"><div
										align="center" style="color: #FFF">事件发生时间</div></td>
								<td width="22%" bgcolor="#999999"><div align="center"
										style="color: #FFF">事件类型</div></td>
								<td width="61%" bgcolor="#999999"><div align="center"
										style="color: #FFF">
										<div align="left" style="margin-left: 30px">事件标题</div>
									</div></td>
							</tr>
							<!--循环以TR-->
							<c:forEach items="${eventList}" var="event">
								<tr>
									<td height="30"><div align="center">${event.eventStartDate}</div></td>
									<td><div align="center">${event.eventTypeName}</div></td>
									<td><div align="left" style="margin-left: 30px">
									        <div id="evnetContent_${event.eventId}" style="display:none;">${event.content}</div>
											${event.title}<a href="#" id="${event.eventId}" onclick="pop_func('${event.title}','${event.eventTypeName}','${event.eventStartDate}','${event.eventId}')">【详情】</a>
										</div>
										
										</td>
								</tr>
							</c:forEach>
							<!--循环结束-->

							<!--以下为无效测试代码-->


							<!--测试代码结束-->
						</table>
					</td>
				</tr>
			</table>
			<div class="page_and_btn">${misEventPo.page.pageStr}</div>
		</div>
		<!--正文结束-->

	</div>
</body>
</html>