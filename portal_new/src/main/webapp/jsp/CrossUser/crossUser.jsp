<%@page import="com.infosmart.portal.util.Const"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.Date"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String indexMenuId =(String) session.getAttribute(Const.INDEX_MENU_ID);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${systemName}产品健康度-用户交叉</title>
<link href="<%=path%>/common/css/cssz.css" rel="stylesheet"
	type="text/css" />
<link href="<%=path%>/common/css/css.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="<%=path%>/common/js/arale.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/excanvas.js"></script>
<script type="text/javascript"
	src="<%=path%>/common/js/FusionCharts_Trial.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/FusionCharts.js"></script>
<script type="text/javascript"
	src="<%=path%>/common/js/jquery-1.4.3.min.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/raphael.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/swfobject.js"></script>
<script type="text/javascript"
	src="<%=path%>/common/js/My97DatePicker/My98MonthPicker.js"></script>
<script type="text/javascript">
//场景交叉分析趋势图
function loadShow(){
	document.getElementById("userDispaly").style.display="";
	var amchart_key ="${amchartKey}";
	var repid = document.getElementById("J-cross-f-sel").value;
	var datatype = document.getElementById("J-cross-s-sel").value;
	var so2 = new SWFObject("<%=path%>/common/amchart/stock/amstock/amstock.swf?id=<%=new Date().getTime()%>", "amline1", "825", "300", "9", "#EFF0F2"); 
    so2.addVariable("path", "<%=path%>/common/amchart/stock/amstock/");
	so2.addVariable("chart_id", "amline1");
	so2.addParam("wmode","transparent");	
	so2.addVariable("settings_file", escape("<%=path%>/stockChart/getPrdCrossStock?prdId=${productId}&relPrdId="+repid+"&dataType="+datatype+"&endDate=${date}"));
	so2.addVariable("key", amchart_key);
	so2.write("chartdiv2");
}
function showHide(hideDiv){
	  document.getElementById(hideDiv).style.display = "none";
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
	var chartDateDiv=document.getElementById("chartdate-chartdiv2");
	if(chartDateDiv!=null){
		chartDateDiv.innerHTML=dateDesc;
	 }
}
</script>
<script type="text/javascript">
//产品选择 js start
function changeProduct(productId){
	location.href="<%=path%>/CrossUserController.do?method=doGet&productId="+productId;
}
//产品选择 js end
</script>
<script type="text/javascript">
function restartOpen() {
	var queryDate = document.getElementById('maxDate').value;
	var productId = document.getElementById('productId').value;
	if (queryDate != "") {
		location.href = '<%=path%>/CrossUser/doGet?menuId=${menuId}&productId='+productId+'&kpiType=3&queryDate='+queryDate;
	}
}
function changeProduct(newProdId){
	if(newProdId==null)
	{
		  alert('newProdId is null');
		  return;
	}
	document.location.href="<%=path%>/CrossUser/doGet.html?menuId=${menuId}&productId="+newProdId+"&queryDate=${date}";
}

function changeTheDate(n){
	  var reg=new RegExp("-","g");
	  var dt=document.getElementById('maxDate').value.replace(reg,"/")+"/01";
	  var today=new Date(new Date(dt).valueOf() );
	  today.setMonth(today.getMonth()+n);
	  var monthDate=today.getMonth()+1;
	  if((today.getMonth() + 1)<10){ 
	  monthDate="0"+(today.getMonth() + 1);
	  }
	  var changedDate=today.getFullYear() + "-" + monthDate;
	  document.getElementById('maxDate').value=changedDate; 
	  if(changedDate!=''){
		  location.href = '<%=path%>/CrossUser/doGet?menuId=${menuId}&kpiType=3&queryDate='+changedDate;  
	  }
}

function showStock(){
	try
	  {
		var url="<%=path%>/crossUser/showProductStock.html?productId=${productId}&queryDate=${date}";
		var productDivId="productDivId";
	 	openDivWindow(productDivId,"产品交叉情况趋势图 ",1000,370,url);//openDivWindow 为通用的方法
	  }
	catch (e){
		  alert('对不起!加载数据失败:'+e.message);
	  }
}
</script>
<style>
.index_ycys {
	display: none
}

.index_xsys {
	display: block
}

﻿body {
	font: 12px/1.5 '微软雅黑';
	color: #333;
}

.wrap {
	width: 800px;
	overflow: X-scroll;
	height: 600px;
	margin: 0px auto 0px;
	font: 12px/1.5 '微软雅黑';
	margin: 0px auto 0px;
}

.trend-banner {
	font-size: 16px;
	line-height: 34px;
	padding: 0 30px;
	font-weight: bold;
	height: 31px;
	color: #ff5500;
	background: url(/images/prodana/box-top-bg.png) repeat-x;
}

.J_fsl {
	margin-left: 20px;
}

.trend-sel {
	height: 20px;
	margin: 5px 0 0 10px;
}
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
	<!-- 产品选择 end-->
	<!-- 当前位置 -->
	<div class="kpi_position" id="navigationDiv"  style="z-index:9999999">
		<div style="padding-top: 2px">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="78%">
					<div style="padding:3px 0px 0px 11px">
                     <div style="float:left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;当前位置：
					<a href="<%=path%>/index/gotoHomePage.html?menuId=<%=indexMenuId%>">首页</a>&gt;
					<a href="<%=path%>/productHealth/showProductHealth.html?menuId=${parentMenu.menuId}">
							${parentMenu.menuName }</a>&gt;
					<a href="<%=path%>/CrossUser/doGet.html?kpiType=3&menuId=${menuId}">
							${modulemap.PROD_CROSS_CHART.menuName }</a>&gt; 
							<font color="red">${productInfo.productName}</font> </div>
<!-- 	           <div style="float:left">&nbsp;&nbsp;<a href="javascript:void(0);" onclick="choiceRrdInfo();"> -->
<%-- 	           <img src="<%=path%>/common/images/jwy_button_01.gif" border="0" /></a></div>					 --%>
				</div>
					
					</td>
					<td width="22%">
						<table width="100%" border="0" cellspacing="0" cellpadding="0" style="padding-top: 5px">
							<tr>
								<td><span style="vertical-align: middle;">数据时间：</span>
									<a href="javascript:void(0);" onclick="changeTheDate(-1);">
									<img style="vertical-align: middle;" align="middle"
										src="<%=path%>/common/images/ddy_2.gif" width="11" height="23"
										border="0" /></a> 
									<input id="maxDate" name="maxDate" type="text" class="ddy_index_input" value="${date}"
										onclick="setMonth(this,this,'restartOpen();');"
										onchange="restartOpen();" style="cursor: pointer;" /> 
									<a href="javascript:void(0);" onclick="changeTheDate(1);">
										<img style="vertical-align: middle;" align="middle"
											src="<%=path%>/common/images/ddy_1.gif" width="11" height="23"
											border="0" /></a>
									</td>
							</tr>
						</table></td>
				</tr>
			</table>
		</div>
	</div>
	<!-- 当前位置结束 -->
<div class="clear"></div>
		
<div class="ddy_channel2" ></div>
	<div class="index_home_2">
		<div style="padding-top:5px">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				style="padding: 0px 0px 3px 0px;">
				<tr>
					<td width="50%">
					</td>
					<td width="50%" align="right">
							
					</td>
				</tr>
			</table>
			<input type="hidden" id="productId" value="${productInfo.productId}" />
		</div>
		<div class="index_report"
			style="width: 760px; float: left; margin-right: 10px; height: auto; background:#f7f7f7">
			<!--左边KPI图-->
			<div class=index_title style="width: 760px;">
				<div class="index_title_pd" style="width: 760px; float: left;">
					<table>
						<tr>
							<td><img src="<%=path%>/common/images/new_jwy_4.gif"></td>
							<td>&nbsp;${modulemap.PROD_CROSS_CHART.moduleName }</td>
						</tr>
					</table>
				</div>

				<em style="background: url(<%=path%>/common/images/askicon5.png);width:22px; height:25px;margin-top:-23px;float:right;margin-right: 10px;cursor: pointer;"
					onmouseover="help();" onmouseout="helpHidden();"></em>
				<div id="help" style="display: none; font-size: 12px;">
					<div style="position:relative;z-index:99999999;  margin:0px 0px 0px 80px">
										<div class="pop_w">
										  <div class="pop_z" style="z-index:99999; left:260px;">	<img src="<%=path%>/common/images/pop_tip.png" /></div>
       
<div class="pop_x">						<p>
								<b>${modulemap.PROD_CROSS_CHART.moduleName}：</b>
							</p>
							<p style="font-weight: normal;">
								${modulemap.PROD_CROSS_CHART.remark}</p>
</div>
</div></div>

				</div>
			</div>
			<div style="padding-top:75px;" >
			<canvas width="750" height="560" id="group1"></canvas>
			</div>
		</div>
		<div class="index_title_cross" style="width: 225px; float: left;">
			<div class="index_title_pd" style="width: 225px;">
				 ${modulemap.PROD_CROSS_INFO.moduleName}<a href="javascript:void(0);" onclick="showStock();">(查看趋势)</a>
			</div>
			<em
				style="background: url(<%=path%>/common/images/askicon5.png);width:22px; height:25px;margin-top:-23px;float:right;margin-right: 20px;cursor: pointer;"
				onmouseover="helpStock();" onmouseout="StockHidden();"></em>
			<div id="helpStock" style="display:none; font-size: 12px;">
				<div style="position:relative;z-index:99999999; ">
										<div class="pop_ww" style="margin:50px 0px 0px -80px">
										  <div class="pop_z" style="z-index:99999; left:260px;">	<img src="<%=path%>/common/images/pop_tip.png" /></div>
       
<div class="pop_x">							<p>
							<b>${modulemap.PROD_CROSS_INFO.moduleName}：</b>
						</p>
						<p style="font-weight: normal;">
</div>
</div></div>

			</div>
		</div>


		<table border="0" cellpadding="0" cellspacing="0"
			style="width: 225px; float: left; background:#f7f7f7">
			<tr class="report_listlink">
				<td width="100px" height="40">
					<div align="center"></div>
				</td>
				<td width="65px">
					<div align="center">交叉用户数</div>
				</td>
				<td width="60px">
					<div align="center">交叉度</div>
				</td>
			</tr>
			<!--循环以下2条TR一条蓝一条白-->
			<tbody id="J-tbody">
			<c:if test="${not empty crossUsers}">
				<c:forEach items="${crossUsers}" var="relateitem" varStatus="sel">
				<tr>
					<c:if test="${sel.index%2 eq 0}">
						<tr class="report_divblue">
					</c:if>
					<c:if test="${sel.index%2 eq 1}">
						<tr class="report_divwhite">
					</c:if>
					
					<td height="30" >
						<div align="left" id="${relateitem.relProductId }+1">${relateitem.relProductName}</div>
					</td>
					<td >
						<div align="right" style="padding-right: 10px;" id="${relateitem.relProductId }+2" >
							<c:choose>
								<c:when test="${relateitem.crossUserCnt<0}">-</c:when>
								<c:otherwise>${relateitem.crossUserCnt}</c:otherwise>
							</c:choose>
						</div>
					</td>
					<td >
						<div align="right" style="padding-right: 10px;" id="${relateitem.relProductId }+3">
							<c:choose>
								<c:when test="${relateitem.crossUserRate==null}">-</c:when>
								<c:otherwise>${relateitem.crossUserRate}%</c:otherwise>
							</c:choose>
						</div>
					</td>
					</tr>
					
				</c:forEach>
				<tr>
					<td colspan="3" >
					<div align="left" style="margin-left: 0px;"  >
					<a href="javascript:viod(0)" onclick="downExcel();" >
	  <img src="<%=path%>/common/images/ddy_22.gif" border="0" /></a>
									</div>
					</td>
					</tr>
				</c:if>
			</tbody>
			<!--循环结束-->
		</table>
		
	</div>
	
	<iframe src="" id="downloadFrm" name="downloadFrm" style="width: 100%; height: 100%; border: none;display:none;" scrolling="no" frameborder="0"> </iframe>
	
	<script type="text/javascript">
	
	function downExcel(){
		if(!confirm("确定导出数据吗?")) return;
		var productName = encodeURI("${productInfo.productName}");
		document.getElementById("downloadFrm").src="<%=path%>/crossUser/excelDown.html?productName="+productName;
		document.getElementById("downloadFrm").submit();
	}
	
	function move(proid){
		var id_1 = proid+"+1";
		var id_2 = proid+"+2";
		var id_3 = proid+"+3";
    	document.getElementById(id_1).style.color="red";
    	document.getElementById(id_2).style.color="red";
    	document.getElementById(id_3).style.color="red";
    }
    function remove(proid){
    	var id_1 = proid+"+1";
		var id_2 = proid+"+2";
		var id_3 = proid+"+3";
		document.getElementById(id_1).style.color="#656668";
    	document.getElementById(id_2).style.color="#656668";
    	document.getElementById(id_3).style.color="#656668";
    	
    }
        E.domReady(function(){
            var il = [
			<c:forEach items="${crossUsers}" var="relateitem" varStatus="sel">
				<c:choose>
				<c:when test="${sel.index==0}">
				{ "r": <c:choose><c:when test="${relateitem.crossUserRate==null}">"-1"</c:when><c:otherwise>"${relateitem.crossUserRate}"</c:otherwise></c:choose>,"pid":"${relateitem.relProductId}", "dx": ${sel.index},"name":"${relateitem.relProductName}","r_rate":"${relateitem.crossUserRate}" }
				</c:when>
				<c:otherwise>
				,{ "r": <c:choose><c:when test="${relateitem.crossUserRate==null}">"-1"</c:when><c:otherwise>"${relateitem.crossUserRate}"</c:otherwise></c:choose>,"pid":"${relateitem.relProductId}", "dx": ${sel.index},"name":"${relateitem.relProductName}","r_rate":"${relateitem.crossUserRate}" }
				</c:otherwise>
				</c:choose>
            </c:forEach>
            ];
            var prodname = "${productInfo.productName}";
            if(il.length>0){
	            var mo = il[0].r / 25;
	            if(mo <= 0){
	            	mo = 90;
	            }
	            il = il.sort(function (a, b) {
	                return a.r % 3 - b.r % 3;
	            });
	            var yuli = [];
	            var ctx = document.getElementById('group1').getContext('2d');
	            for (var i = 0; i < il.length; i++) {
	                var rr = il[i].r / mo;
	                if(rr<0){
	                	rr = -1;
	                }
	                var x = (340 - rr * 10) * Math.sin(Math.PI / il.length * (i + 1) * 2 - il[i].r / 200);
	                var y = (260 - rr * 5) * Math.cos(Math.PI / il.length * (i + 1) * 2 - il[i].r / 200);
	                if(x>340){
	                	x=340;
	                }
	                if(y>240){
	                	y = 240;
	                }
	                yuli.push({ "x": 775 / 2 + x, "y": 536 / 2 + y, "r": rr,"pid":il[i].pid, "dx": il[i].dx,"name":il[i].name,"r_rate":il[i].r_rate });
	            }
	
	
	            for (var i = 0; i < yuli.length; i++) {
	            	if(yuli[i].r>=0){
		                ctx.strokeStyle = '#e4c8a9';
		                ctx.lineWidth = yuli[i].r < 2 ? 2 : yuli[i].r;
		                ctx.beginPath();
		                ctx.moveTo(775 / 2, 536 / 2);
		                ctx.lineTo(yuli[i].x, yuli[i].y);
		                ctx.closePath();
		                ctx.stroke();
	                }
	            }
	            for (var i = 0; i < yuli.length; i++) {
	            	var textToDraw = "<a style=\"cursor:pointer;\" onclick=changeProduct(\""+yuli[i].pid+"\"); onmouseover=move(\""+yuli[i].pid+"\") onmouseout=remove(\""+yuli[i].pid+"\")>"+yuli[i].name+"<br/>("+yuli[i].r_rate+"%)"+"</a>"
	                ctx.beginPath();
	                ctx.arc(yuli[i].x, yuli[i].y, yuli[i].r < 5 ? 5 : yuli[i].r, 0, Math.PI * 2, true);
	                ctx.fillStyle = "#ca6c00";
	                //小圈
	                ctx.fill();
	
	                ctx.fillStyle = '#FF8000';
	                ctx.font = '13px 雅黑';
	                var len = (yuli[i].name.length*14)/2;
					//解决原FF浏览器的链接不解析的问题
	                try
	                {
					     ctx.fillHref(textToDraw, yuli[i].x -len<0?0:yuli[i].x -len, yuli[i].y + (yuli[i].r < 5 ? 5 : yuli[i].r)+14);
	                }catch(e)
	                {
						 var canvasSpan=document.createElement('span');
							canvasSpan.id=yuli[i].pid;
							canvasSpan.innerHTML=textToDraw;
							canvasSpan.style.position="absolute";
							canvasSpan.style.left=(yuli[i].x-len)+"px";
							canvasSpan.style.top=yuli[i].y + (yuli[i].r < 5 ? 5 : yuli[i].r)+85+"px";   
						document.body.appendChild(canvasSpan);
	               }
	            }
	            ctx.beginPath();
	            ctx.arc(775 / 2, 536 / 2, 50, 0, Math.PI * 2, true);
	            ctx.fillStyle = "#ca6c00";
	            //大圈
	            ctx.fill();
	            ctx.fillStyle = '#fff';
	            ctx.font = '15px 雅黑 ';
	            ctx.fillText(prodname, 775 / 2-(prodname.length/2*16), 536 / 2+8);
            }
            //自适应
            try
            {
				window.parent.document.getElementById('center_iframe').height=document.documentElement.scrollHeight;
				//parent.changeIframeSize(document.documentElement.offsetHeight);
            }
            catch (e)
            {
				alert(e.message);
            }
            //判断浏览器类型设置自适应
            if(navigator.userAgent.indexOf("MSIE")>0) { 
            	window.parent.document.getElementById('center_iframe').height=document.body.scrollHeight;
            } 
            if(isFirefox=navigator.userAgent.indexOf("Firefox")>0){
            	window.parent.document.getElementById('center_iframe').height=document.documentElement.offsetHeight;
            } 

        });
        
        function help(){
        	document.getElementById('help').style.display="";
        }
        function helpStock(){
        	document.getElementById('helpStock').style.display="";
        }
        function helpHidden(){
        	document.getElementById('help').style.display="none";
        }
        function StockHidden(){
        	document.getElementById('helpStock').style.display="none";
        }
    </script>

</body>
</html>