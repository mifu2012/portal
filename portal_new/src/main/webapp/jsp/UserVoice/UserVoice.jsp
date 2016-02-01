<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import="com.infosmart.portal.pojo.DwpasStKpiData"%>
<%@ page import="com.infosmart.portal.pojo.DwpasCKpiInfo"%>
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
<link href="<%=path%>/common/css/cssz.css" rel="stylesheet" type="text/css"  /> 
<%-- <link href="<%=path%>/common/css/css.css" rel="stylesheet" type="text/css"  /> 
<link href="<%=path%>/common/css/style.css" rel="stylesheet" type="text/css"  />  --%>
<script type="text/javascript" src="<%=path%>/common/js/My97DatePicker/My98MonthPicker.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/jquery-1.4.3.min.js"></script>
<script src="<%=path%>/common/amchart/stock/amstock/swfobject.js"
			type="text/javascript"></script>
<script type="text/javascript" src="<%=path%>/common/js/amcharts.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/amfallback.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/raphael.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/arale.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/ddycom.js"></script>
<!-- jquery -->
<!-- 
<script type="text/javascript" src="<%=path%>/common/js/jquery-1.4.3.min.js"></script>
 -->
<script type="text/javascript">

  function showloading(){
	  var loadingDiv=window.parent.document.getElementById("loading_wait_Div");
	  if(loadingDiv!=null){
		  loadingDiv.style.display="block";
	  }
  }

  function restartOpen(queryDay){
	  showloading();
	  if(queryDay==null||queryDay.length==0) queryDay=document.getElementById('dayDate').value;
	  var queryMonth=document.getElementById('monthDate').value;
	  var kpiType=document.getElementById('kpiType').value;
	  if(document.getElementById('theDate').value!=""){
		  if(kpiType==<%=DwpasCKpiInfo.KPI_TYPE_OF_DAY%>){
            location.href='<%=path%>/userVoice/showUserVoice?menuId=${menuId}&queryDate='+queryDay+'&kpiType='+kpiType;
		  }
		  else{
			  location.href='<%=path%>/userVoice/showUserVoice?menuId=${menuId}&queryMonth='+queryMonth+'&kpiType='+kpiType;
		  }
	  }
  }
  function viewByDay(){
	  showloading();
	  var kpiType=<%=DwpasCKpiInfo.KPI_TYPE_OF_DAY%>;
      location.href='<%=path%>/userVoice/showUserVoice?menuId=${menuId}&kpiType='+kpiType;
  }
  function viewByMonth(){
	  showloading();
	  var kpiType=<%=DwpasCKpiInfo.KPI_TYPE_OF_MONTH%>;
      location.href='<%=path%>/userVoice/showUserVoice?menuId=${menuId}&kpiType='+kpiType;
  }
  function goJiankang(){
	  showloading();
		location.href='<%=path%>/productHealth/showProductHealth.html?menuId=${parentMenu.menuId}';
	}
  function shouye(){
	  showloading();
	  location.href='<%=path%>/index/gotoHomePage.html?menuId=<%=indexMenuId%>';
	}
  //选择产品
 function changeProduct(newProdId)
 {
	 showloading();
	 document.location.href="<%=path%>/userVoice/showUserVoice.html?menuId=${menuId}&productId="+newProdId+"&kpiType=${kpiType}";
 }
  
  function changeTheDate(n){
	  showloading();
	  var kpiType=document.getElementById('kpiType').value;
	  var reg=new RegExp("-","g");
	  if(kpiType==<%=DwpasCKpiInfo.KPI_TYPE_OF_DAY%>){
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
		  document.getElementById('dayDate').value=changedDate; 
		  if(changedDate!=''){
			  location.href='<%=path%>/userVoice/showUserVoice?menuId=${menuId}&queryDate='+changedDate+'&kpiType='+kpiType;
		  }
	  }
	  else if(kpiType==<%=DwpasCKpiInfo.KPI_TYPE_OF_MONTH%>){
		  var dt=document.getElementById('theDate').value.replace(reg,"/")+"/01";
		  var today=new Date(new Date(dt).valueOf() );
		  today.setMonth(today.getMonth()+n);
		  var monthDate=today.getMonth()+1;
		  if((today.getMonth() + 1)<10){ 
	    	  monthDate="0"+(today.getMonth() + 1);
		  }
		  var changedDate=today.getFullYear() + "-" + monthDate;
		  document.getElementById('monthDate').value=changedDate; 
		  if(changedDate!=''){
			  location.href='<%=path%>/userVoice/showUserVoice?menuId=${menuId}&queryMonth='+changedDate+'&kpiType='+kpiType;
		  }
	  }
  }
</script>

</head>
<body class="indexbody2">
<!--产品选择页面  -->
<jsp:include page="../common/ChoiceProduct.jsp"/>
<!-- 当前位置 -->
<div class="kpi_position" id="navigationDiv">
		<div style="padding-top: 2px">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>

					<td width="54%">
					<div style="padding:3px 0px 0px 11px">
						<div style="float:left">
					 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 当前位置：
						<a href="javascript:void(0);" onclick="shouye();">首页</a>&gt; 
						<a href="javascript:void(0);" onclick="goJiankang();">${parentMenu.menuName}</a>&gt;
						<a href="<%=path%>/userVoice/showUserVoice.html?menuId=${menuId}&kpiType=${kpiType}">${moduleInfoMap.USER_VOICE_USER_HELP_PIE_CHART.menuName }</a>&gt; 
						<span><font color='red'>${productInfo.productName}</font></span></div>
						<div style="float:left">&nbsp;&nbsp;
	                        <a href="javascript:void(0);" onclick="choiceRrdInfo();"><img src="<%=path%>/common/images/jwy_button_01.gif" border="0" /></a></div>					
				         </div>
				    </td>
					<td width="21%">

						<div id="byDay" style="width: 84px; height: 22px; text-align: center; line-height: 22px; color: #fff; float: left; margin-right: 15px;display: none">
							<a href="javascript:void(0);" onclick="viewByDay();"> 
								<span> 按日查看</span></a>
						</div>

						<div id="byMonth" style="width: 84px; height: 22px; text-align: center; line-height: 22px; color: #fff; float: left; display: none">
							<a href="javascript:void(0);" onclick="viewByMonth();">
								<span>按月查看</span></a>
						</div>
					</td>
					<td width="25%" style="text-align: center;">数据时间：
					<a href="javascript:void(0);" onclick="changeTheDate(-1);">
						<img style="vertical-align: middle;" align="middle" src="<%=path%>/common/images/ddy_2.gif" width="11" height="23"
							border="0" /></a> 
					<input id="dayDate" name="dayDate" type="text" class="ddy_index_input" readonly="readonly" value=""
						onclick="WdatePicker({isShowWeek:true,isShowClear:false,readOnly:true,startDate:'${date} ',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true,doubleCalendar:true,onpicked:function(dp){restartOpen(dp.cal.getNewDateStr());}})" style="cursor: pointer;" /> 
					<input id="monthDate" name="monthDate" type="text" class="ddy_index_input" readonly="readonly"
						onchange="restartOpen();" onclick="setMonth(this,this,'restartOpen();')" value=" "
						style="cursor: pointer;" /> 
					<a href="javascript:void(0);" onclick="changeTheDate(1);">
						<img style="vertical-align: middle;" align="middle"
							src="<%=path%>/common/images/ddy_1.gif" width="11" height="23"
							border="0" /></a></td>

				</tr>
			</table>
		</div>
	</div>
<!-- 当前位置结束 -->
<div class="clear"></div>
		
<div class="ddy_channel2" ></div>
<div class="index_home" >
 <div style="margin-top: 0px;*margin-top: -8px;_margin-top: -8px;">
			<img src="<%=path%>/common/images/wph_8.gif" />
		</div>


  <!--第一部分-->

		<div class="index_title">
			<div class="index_title_pd">
				<table>
					<tr>
						<td><img src="<%=path%>/common/images/new_jwy_4.gif" /></td>
						<td>&nbsp;${moduleInfoMap.USER_VOICE_USER_HELP_THREAD_CHART.moduleName }</td>
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
       
<div class="pop_x">							<p>
							<b>${moduleInfoMap.USER_VOICE_USER_HELP_THREAD_CHART.moduleName}：</b>
						</p>
						<p style="font-weight: normal;">
							${moduleInfoMap.USER_VOICE_USER_HELP_THREAD_CHART.remark}</p>
</div>
</div></div>
			</div>
		</div>
		<div class="index_report"></div>
    	<div class="clear">
			<div id="chartdate-ampie"
				style="position: absolute; width: 120px; padding-left: 56px; padding-top: 40px; display: none;">
				<c:choose>
					<c:when test="${kpiType == 1}">
						   ${showDate} 
						    </c:when>
					<c:otherwise>
							${showDate}
							</c:otherwise>
				</c:choose>
			</div>
			<div id="UserHelpRate" style="width:997px; height:350px; margin:0 5px;"></div>
    
    <div class="clear"></div>
    <!--正文内容-->
    <!--内容结束-->
  </div>
  <!--第一部分结束-->
  <div style="background: #EFF0F2;height: 4px;"></div>
    <!--第二部分-->
  <div class="ddy_one_home"></div>
		<div class="index_title">
			<div class="index_title_pd">
				<table>
					<tr>
						<td><img src="<%=path%>/common/images/new_jwy_4.gif" /></td>
						<td>&nbsp;${moduleInfoMap.USER_VOICE_USER_HELP_PIE_CHART.moduleName}</td>
					</tr>
				</table>


			</div>
			<span class="box-top-right"> 
				<em style="background: url(<%=path%>/common/images/askicon5.png); cursor: pointer"
					class="box-askicon" onmouseover="helpShow2();"
					onmouseout="helpHidden2();"></em>
			</span>
			<div id="help2" style="display: none; font-size: 12px;">
				<div style="position:relative;z-index:99999999;  margin:0px 0px 0px 310px">
										<div class="pop_w">
										  <div class="pop_z" style="z-index:99999; left:260px;">	<img src="<%=path%>/common/images/pop_tip.png" /></div>
       
<div class="pop_x">						<p>
							<b>${moduleInfoMap.USER_VOICE_USER_HELP_PIE_CHART.moduleName}：</b>
						</p>
						<p style="font-weight: normal;">
							${moduleInfoMap.USER_VOICE_USER_HELP_PIE_CHART.remark}</p>
</div>
</div></div>
			</div>
		</div>

		<div class="clear">
      
    </div>
  <div class="index_report">
    <div class="clear">
      
    </div>
    <div align="center">
      <!--正文内容-->
      <div style="width:997px; height:333px; background-color: #EFF0F2 ">
	  <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" >
        <tr>
          <td width="468" valign="top">         
            <div id="chart-helptop10" style=" width:450px; margin:0px; height:325px;"></div>
                      <h3>&nbsp;</h3></td>
          <td  valign="top">
          
          <div id="all">
									<c:if test="${not empty defaultTopUserAsks}">
										<div style="padding-left: 20px; padding-top: 5px;">
											<div align="left" style="padding-bottom: 5px">
												<h4
													style="font-family: 微软雅黑; color: #606460; font-weight: bold"
													align="left">整体求助问题列表</h4>
											</div>
											<table width="95%" border="0" cellpadding="0" cellspacing="0">
												<tr class="report_divblue" style="font-family: 微软雅黑">
													<th width="12%" height="30"><div align="center">序号</div></th>
													<th width="75%"><div align="center">用户咨询的问题</div></th>
													<th width="13%"><div align="center">求助量</div></th>
												</tr>
											</table>
											<table width="95%" border="0" cellpadding="0" cellspacing="0"
												id="myTable">
												<c:set var="num" value="0"></c:set>
												<c:forEach var="helpitem" items="${defaultTopUserAsks}">
													<c:set var="num" value="${num+1}"></c:set>
													<c:if test="${num<11 }">
														<tr>
															<td width="12%"
																style="padding-left: 10px; padding-right: 10px;"
																height="25" align="left">${num}</td>
															<td width="75%"
																style="padding-left: 10px; padding-right: 10px;"
																align="left">${helpitem.question}</td>
															<td width="13%"
																style="padding-left: 10px; padding-right: 10px;"
																align="right">${helpitem.sortId}</td>
														</tr>
													</c:if>
												</c:forEach>
												<script type="text/javascript">
													var a = document
															.getElementById(
																	"myTable")
															.getElementsByTagName(
																	"tr");
													for ( var i = 0; i < a.length; i++) {
														a[i].style.background = (i % 2 == 1) ? "#f7f7f7"
																: "#FFFFFF";
													}
												</script>
											</table>
										</div>
									</c:if>
								</div>

								<div id="oneUser" style="display: none">
									<c:if test="${not empty oneUser}">
										<div style="padding-left: 20px; padding-top: 5px;">
											<div align="left" style="padding-bottom: 5px">
												<h4
													style="font-family: 微软雅黑; color: #606460; font-weight: bold"
													align="left">一次使用会员求助问题列表</h4>
											</div>
											<table width="95%" border="0" cellpadding="0" cellspacing="0">
												<tr class="report_divblue" style="font-family: 微软雅黑">
													<th width="12%" height="30"><div align="center">序号</div></th>
													<th width="75%"><div align="center">用户咨询的问题</div></th>
													<th width="13%"><div align="center">求助量</div></th>
												</tr>
											</table>

											<table width="95%" border="0" cellpadding="0" cellspacing="0"
												id="myTable1">
												<c:set var="num" value="0"></c:set>
												<c:forEach var="helpitem" items="${oneUser}">
													<c:set var="num" value="${num+1}"></c:set>
													<c:if test="${num<11 }">
														<tr>
															<td width="12%"
																style="padding-left: 10px; padding-right: 10px;"
																height="25" align="left">${num}</td>
															<td width="75%"
																style="padding-left: 10px; padding-right: 10px;"
																align="left">${helpitem.question}</td>
															<td width="13%"
																style="padding-left: 10px; padding-right: 10px;"
																align="right">${helpitem.sortId}</td>
														</tr>
													</c:if>
												</c:forEach>

												<script type="text/javascript">
													var a = document
															.getElementById(
																	"myTable1")
															.getElementsByTagName(
																	"tr");
													for ( var i = 0; i < a.length; i++) {
														a[i].style.background = (i % 2 == 1) ? "#f7f7f7"
																: "#FFFFFF";
													}
												</script>
											</table>
										</div>
									</c:if>
								</div>


								<div id="manyUser" style="display: none">
									<c:if test="${not empty manyUser}">
										<div style="padding-left: 20px; padding-top: 5px;">
											<div align="left" style="padding-bottom: 5px">
												<h4
													style="font-family: 微软雅黑; color: #606460; font-weight: bold"
													align="left">多次使用会员求助问题列表</h4>
											</div>
											<table width="95%" border="0" cellpadding="0" cellspacing="0">
												<tr class="report_divblue" style="font-family: 微软雅黑">
													<th width="12%" height="30"><div align="center">序号</div></th>
													<th width="75%"><div align="center">用户咨询的问题</div></th>
													<th width="13%"><div align="center">求助量</div></th>
												</tr>
											</table>

											<table width="95%" border="0" cellpadding="0" cellspacing="0"
												id="myTable2">
												<c:set var="num" value="0"></c:set>
												<c:forEach var="helpitem" items="${manyUser}">
													<c:set var="num" value="${num+1}"></c:set>
													<c:if test="${num<11 }">
														<tr>
															<td width="12%"
																style="padding-left: 10px; padding-right: 10px;"
																height="25" align="left">${num}</td>
															<td width="75%"
																style="padding-left: 10px; padding-right: 10px;"
																align="left">${helpitem.question}</td>
															<td width="13%"
																style="padding-left: 10px; padding-right: 10px;"
																align="right">${helpitem.sortId}</td>
														</tr>
													</c:if>
												</c:forEach>

												<script type="text/javascript">
													var a = document
															.getElementById(
																	"myTable2")
															.getElementsByTagName(
																	"tr");
													for ( var i = 0; i < a.length; i++) {
														a[i].style.background = (i % 2 == 1) ? "#f7f7f7"
																: "#FFFFFF";
													}
												</script>
											</table>
										</div>
									</c:if>
								</div>

								<div id="unUser" style="display: none">
									<c:if test="${not empty unUser}">
										<div style="padding-left: 20px; padding-top: 5px;">
											<div align="left" style="padding-bottom: 5px">
												<h4
													style="font-family: 微软雅黑; color: #606460; font-weight: bold"
													align="left">未使用会员求助问题列表</h4>
											</div>
											<table width="95%" border="0" cellpadding="0" cellspacing="0">
												<tr class="report_divblue" style="font-family: 微软雅黑">
													<th width="12%" height="30"><div align="center">序号</div></th>
													<th width="75%"><div align="center">用户咨询的问题</div></th>
													<th width="13%"><div align="center">求助量</div></th>
												</tr>
											</table>

											<table width="95%" border="0" cellpadding="0" cellspacing="0"
												id="myTable3">
												<c:set var="num" value="0"></c:set>
												<c:forEach var="helpitem" items="${unUser}">
													<c:set var="num" value="${num+1}"></c:set>
													<c:if test="${num<11 }">
														<tr>
															<td width="12%"
																style="padding-left: 10px; padding-right: 10px;"
																height="25" align="left">${num}</td>
															<td width="75%"
																style="padding-left: 10px; padding-right: 10px;"
																align="left">${helpitem.question}</td>
															<td width="13%"
																style="padding-left: 10px; padding-right: 10px;"
																align="right">${helpitem.sortId}</td>
														</tr>
													</c:if>
												</c:forEach>

												<script type="text/javascript">
													var a = document
															.getElementById(
																	"myTable3")
															.getElementsByTagName(
																	"tr");
													for ( var i = 0; i < a.length; i++) {
														a[i].style.background = (i % 2 == 1) ? "#f7f7f7"
																: "#FFFFFF";
													}
												</script>
											</table>
										</div>
									</c:if>
								</div>

								<div id="unUserbyMonth" style="display: none">
									<c:if test="${not empty unUserbyMonth}">
										<div style="padding-left: 20px; padding-top: 5px;">
											<div align="left" style="padding-bottom: 5px">
												<h4
													style="font-family: 微软雅黑; color: #606460; font-weight: bold"
													align="left">未使用会员求助问题列表</h4>
											</div>
											<table width="95%" border="0" cellpadding="0" cellspacing="0">
												<tr class="report_divblue" style="font-family: 微软雅黑">
													<th width="12%" height="30"><div align="center">序号</div></th>
													<th width="75%"><div align="center">用户咨询的问题</div></th>
													<th width="13%"><div align="center">求助量</div></th>
												</tr>
											</table>

											<table width="95%" border="0" cellpadding="0" cellspacing="0"
												id="myTable4">
												<c:set var="num" value="0"></c:set>
												<c:forEach var="helpitem" items="${unUserbyMonth}">
													<c:set var="num" value="${num+1}"></c:set>
													<c:if test="${num<11 }">
														<tr>
															<td width="12%"
																style="padding-left: 10px; padding-right: 10px;"
																height="25" align="left">${num}</td>
															<td width="75%"
																style="padding-left: 10px; padding-right: 10px;"
																align="left">${helpitem.question}</td>
															<td width="13%"
																style="padding-left: 10px; padding-right: 10px;"
																align="right">${helpitem.sortId}</td>
														</tr>
													</c:if>
												</c:forEach>

												<script type="text/javascript">
													var a = document
															.getElementById(
																	"myTable4")
															.getElementsByTagName(
																	"tr");
													for ( var i = 0; i < a.length; i++) {
														a[i].style.background = (i % 2 == 1) ? "#f7f7f7"
																: "#FFFFFF";
													}
												</script>
											</table>
										</div>
									</c:if>
								</div>


								<div id="newUser" style="display: none">
									<c:if test="${not empty newUser}">
										<div style="padding-left: 20px; padding-top: 5px;">
											<div align="left" style="padding-bottom: 5px">
												<h4
													style="font-family: 微软雅黑; color: #606460; font-weight: bold"
													align="left">新会员求助问题列表</h4>
											</div>
											<table width="95%" border="0" cellpadding="0" cellspacing="0">
												<tr class="report_divblue" style="font-family: 微软雅黑">
													<th width="12%" height="30"><div align="center">序号</div></th>
													<th width="75%"><div align="center">用户咨询的问题</div></th>
													<th width="13%"><div align="center">求助量</div></th>
												</tr>
											</table>

											<table width="95%" border="0" cellpadding="0" cellspacing="0"
												id="myTable5">
												<c:set var="num" value="0"></c:set>
												<c:forEach var="helpitem" items="${newUser}">
													<c:set var="num" value="${num+1}"></c:set>
													<c:if test="${num<11 }">
														<tr>
															<td width="12%"
																style="padding-left: 10px; padding-right: 10px;"
																height="25" align="left">${num}</td>
															<td width="75%"
																style="padding-left: 10px; padding-right: 10px;"
																align="left">${helpitem.question}</td>
															<td width="13%"
																style="padding-left: 10px; padding-right: 10px;"
																align="right">${helpitem.sortId}</td>
														</tr>
													</c:if>
												</c:forEach>

												<script type="text/javascript">
													var a = document
															.getElementById(
																	"myTable5")
															.getElementsByTagName(
																	"tr");
													for ( var i = 0; i < a.length; i++) {
														a[i].style.background = (i % 2 == 1) ? "#f7f7f7"
																: "#FFFFFF";
													}
												</script>
											</table>
										</div>
									</c:if>
								</div>
								
								


								<div id="oldUser" style="display: none">
									<c:if test="${not empty oldUser}">
										<div style="padding-left: 20px; padding-top: 5px;">
											<div align="left" style="padding-bottom: 5px">
												<h4
													style="font-family: 微软雅黑; color: #606460; font-weight: bold"
													align="left">老会员求助问题列表</h4>
											</div>
											<table width="95%" border="0" cellpadding="0" cellspacing="0">
												<tr class="report_divblue" style="font-family: 微软雅黑">
													<th width="12%" height="30"><div align="center">序号</div></th>
													<th width="75%"><div align="center">用户咨询的问题</div></th>
													<th width="13%"><div align="center">求助量</div></th>
												</tr>
											</table>

											<table width="95%" border="0" cellpadding="0" cellspacing="0"
												id="myTable6">
												<c:set var="num" value="0"></c:set>
												<c:forEach var="helpitem" items="${oldUser}">
													<c:set var="num" value="${num+1}"></c:set>
													<c:if test="${num<11 }">
														<tr>
															<td width="12%"
																style="padding-left: 10px; padding-right: 10px;"
																height="25" align="left">${num}</td>
															<td width="75%"
																style="padding-left: 10px; padding-right: 10px;"
																align="left">${helpitem.question}</td>
															<td width="13%"
																style="padding-left: 10px; padding-right: 10px;"
																align="right">${helpitem.sortId}</td>
														</tr>
													</c:if>
												</c:forEach>

												<script type="text/javascript">
													var a = document
															.getElementById(
																	"myTable6")
															.getElementsByTagName(
																	"tr");
													for ( var i = 0; i < a.length; i++) {
														a[i].style.background = (i % 2 == 1) ? "#f7f7f7"
																: "#FFFFFF";
													}
												</script>
											</table>
										</div>
									</c:if>
								</div>

								<div id="sleepUser" style="display: none">
									<c:if test="${not empty sleepUser}">
										<div style="padding-left: 20px; padding-top: 5px;">
											<div align="left" style="padding-bottom: 5px">
												<h4
													style="font-family: 微软雅黑; color: #606460; font-weight: bold"
													align="left">休眠会员求助问题列表</h4>
											</div>
											<table width="95%" border="0" cellpadding="0" cellspacing="0">
												<tr class="report_divblue" style="font-family: 微软雅黑">
													<th width="12%" height="30"><div align="center">序号</div></th>
													<th width="75%"><div align="center">用户咨询的问题</div></th>
													<th width="13%"><div align="center">求助量</div></th>
												</tr>
											</table>

											<table width="95%" border="0" cellpadding="0" cellspacing="0"
												id="myTable7">
												<c:set var="num" value="0"></c:set>
												<c:forEach var="helpitem" items="${sleepUser}">
													<c:set var="num" value="${num+1}"></c:set>
													<c:if test="${num<11 }">
														<tr>
															<td width="12%"
																style="padding-left: 10px; padding-right: 10px;"
																height="25" align="left">${num}</td>
															<td width="75%"
																style="padding-left: 10px; padding-right: 10px;"
																align="left">${helpitem.question}</td>
															<td width="13%"
																style="padding-left: 10px; padding-right: 10px;"
																align="right">${helpitem.sortId}</td>
														</tr>
													</c:if>
												</c:forEach>

												<script type="text/javascript">
													var a = document
															.getElementById(
																	"myTable7")
															.getElementsByTagName(
																	"tr");
													for ( var i = 0; i < a.length; i++) {
														a[i].style.background = (i % 2 == 1) ? "#f7f7f7"
																: "#FFFFFF";
													}
												</script>
											</table>
										</div>
									</c:if>
								</div>


								<div id="awayUser" style="display: none">
									<c:if test="${not empty awayUser}">
										<div style="padding-left: 20px; padding-top: 5px;">
											<div align="left" style="padding-bottom: 5px">
												<h4
													style="font-family: 微软雅黑; color: #606460; font-weight: bold"
													align="left">流失会员求助问题列表</h4>
											</div>
											<table width="95%" border="0" cellpadding="0" cellspacing="0">
												<tr class="report_divblue" style="font-family: 微软雅黑">
													<th width="12%" height="30"><div align="center">序号</div></th>
													<th width="75%"><div align="center">用户咨询的问题</div></th>
													<th width="13%"><div align="center">求助量</div></th>
												</tr>
											</table>

											<table width="95%" border="0" cellpadding="0" cellspacing="0"
												id="myTable8">
												<c:set var="num" value="0"></c:set>
												<c:forEach var="helpitem" items="${awayUser}">
													<c:set var="num" value="${num+1}"></c:set>
													<c:if test="${num<11 }">
														<tr>
															<td width="12%"
																style="padding-left: 10px; padding-right: 10px;"
																height="25" align="left">${num}</td>
															<td width="75%"
																style="padding-left: 10px; padding-right: 10px;"
																align="left">${helpitem.question}</td>
															<td width="13%"
																style="padding-left: 10px; padding-right: 10px;"
																align="right">${helpitem.sortId}</td>
														</tr>
													</c:if>
												</c:forEach>

												<script type="text/javascript">
													var a = document
															.getElementById(
																	"myTable8")
															.getElementsByTagName(
																	"tr");
													for ( var i = 0; i < a.length; i++) {
														a[i].style.background = (i % 2 == 1) ? "#f7f7f7"
																: "#FFFFFF";
													}
												</script>
											</table>
										</div>
									</c:if>
								</div>
								<c:if test="${not empty allUser }" >
								<div style="height: 3px;"></div>
								<div align="left" style="margin-left: 36px;"  >
	  <a href="javascript:viod(0)" onclick="downExcel();" >
	  <img src="<%=path%>/common/images/ddy_22.gif" border="0" /></a>
									</div>
								</c:if>
							</td>
        </tr>
      </table>
	  
	  </div>
      <!--内容结束-->
      
	  
    </div>
  </div>
  <!--第二部分结束-->
  
</div>

<iframe src="" id="downloadFrm" name="downloadFrm" style="width: 100%; height: 100%; border: none;display:none;" scrolling="no" frameborder="0"> </iframe>

<script type="text/javascript" src="<%=path%>/common/js/jquery-1.4.3.min.js"></script>
<script type="text/javascript">

function downExcel(){
	if(!confirm("确定导出数据吗?")) return;
	var productName = encodeURI("${productInfo.productName}");
	document.getElementById("downloadFrm").src="<%=path%>/userVoice/excelDown.html?productName="+productName;
	document.getElementById("downloadFrm").submit();
}

var amchart_key = "${amchartKey}";//"110-037eaff57f02320aee1b8576e4f4062a"
    window.onload = function () {
    	var myKpiType=document.getElementById('kpiType').value;
    	var buttonByday=document.getElementById('byDay');
    	var buttonByMonth=document.getElementById('byMonth');
    	var dayDate=document.getElementById('dayDate');
    	var monthDate=document.getElementById('monthDate'); 
    	var dateType="";
    	if(myKpiType==<%=DwpasCKpiInfo.KPI_TYPE_OF_DAY%>){
    		document.getElementById('dayDate').value=document.getElementById('theDate').value;
    		buttonByday.className='bgwd';
    		buttonByMonth.className='bgwd2';
    		buttonByday.style.color="#fff";
     		dayDate.style.display="";
    		monthDate.style.display="none"; 
    		dateType='<%=DwpasStKpiData.DATE_TYPE_OF_DAY%>';
    	}else if(myKpiType==<%=DwpasCKpiInfo.KPI_TYPE_OF_MONTH%>){
    		document.getElementById('monthDate').value=document.getElementById('theDate').value;
    		buttonByMonth.className='bgwd';
    		buttonByday.className='bgwd2';
     		dayDate.style.display="none";
    		monthDate.style.display="";
    		dateType='<%=DwpasStKpiData.DATE_TYPE_OF_MONTH%>';
    	}
    	//趋势图
        var charttread = new SWFObject("<%=path%>/common/amchart/stock/amstock/amstock.swf?id=<%=new Date().getTime()%>", "amstock", "990", "350", "8", "#EFF0F2");//#EFF0F2
        charttread.addVariable("path", "<%=path%>/common/amchart/stock/amstock/");
		charttread.addVariable("chart_id","amstock");
        charttread.addVariable("settings_file", escape("<%=path%>/stockChart/showStockChart?1=1&isPrd=1&kpiCodes=${kpiCode}&needPercent=1&kpiType=${kpiType}&reportDate=${date}"));
        charttread.addParam("wmode", "opaque");
		charttread.addVariable("bg_color","#EFF0F2");
		charttread.addVariable("key", amchart_key);
		//指标Codes
		charttread.addVariable("kpiCodes","${kpiCode}");
		//日期类型
		charttread.addVariable("kpiType","${kpiType}");
		
        charttread.write("UserHelpRate");
        
        //饼图
    	var charttop10 = new SWFObject("<%=path%>/common/amchart/pie/ampie/ampie1.swf", "ampie1", "450", "325", "8", "#EFF0F2");
    	charttop10.addVariable("path", "<%=path%>/common/amchart/pie/ampie/");
    	charttop10.addVariable("chart_id","ampie1");
    	charttop10.addVariable("settings_file", escape("<%=path%>/common/amchart/pie/ampie/commonpie-settings-legends.xml"));
    	charttop10.addVariable("data_file",escape("<%=path%>/PieChart/showPieChart?kpiCode=${userConstituteCodes}&reportDate=${date}&dateType="+dateType));
    	charttop10.addParam("wmode", "transparent");
    	charttop10.addVariable("key", amchart_key);
    	charttop10.write("chart-helptop10");
    	//自适应
    	parent.changeIframeSize(document.body.scrollHeight);       
    }
    
	var unUserbyMonthCount=newUserCount=oldUserCount=sleepUserCount=awayUserCount=0;
	var unUserCount=oneUserCount=manyUserCount=0;
    function amSliceClick(chart_id, index, title, value, percents, color, description){
    	
    	var kpiType=document.getElementById('kpiType').value;
    	var url= '<%=path%>/userVoice/getUserType?kpiCode='+description+'&kpiType='+kpiType+'&menuId=${menuId}&moduleCode=USER_VOICE_USER_HELP_PIE_CHART';
    	//submit
    	
    	$.post(url,function(userType){
    		var type=parseInt(userType);
    		//
    		switch(type){
    		case 0:
    			unUserbyMonthCount++;
    				document.getElementById('unUserbyMonth').style.display='';
        			document.getElementById('sleepUser').style.display='none';
        			document.getElementById('newUser').style.display='none';
        			document.getElementById('oldUser').style.display='none';
        			document.getElementById('awayUser').style.display='none';
        			document.getElementById('all').style.display='none';
        			if(unUserbyMonthCount%2==0 && newUserCount%2==0 && oldUserCount%2==0 && sleepUserCount%2==0 && awayUserCount%2==0){
            			document.getElementById('all').style.display='';
            			document.getElementById('awayUser').style.display='none';
            			document.getElementById('newUser').style.display='none';
            			document.getElementById('unUserbyMonth').style.display='none';
            			document.getElementById('oldUser').style.display='none';
            			document.getElementById('sleepUser').style.display='none';
            		}
    			
    			break;
    		case 1:
    			newUserCount++;
    			
    				document.getElementById('newUser').style.display='';
        			document.getElementById('sleepUser').style.display='none';
        			document.getElementById('unUserbyMonth').style.display='none';
        			document.getElementById('oldUser').style.display='none';
        			document.getElementById('awayUser').style.display='none';
        			document.getElementById('all').style.display='none';
        			if(unUserbyMonthCount%2==0 && newUserCount%2==0 && oldUserCount%2==0 && sleepUserCount%2==0 && awayUserCount%2==0){
            			document.getElementById('all').style.display='';
            			document.getElementById('awayUser').style.display='none';
            			document.getElementById('newUser').style.display='none';
            			document.getElementById('unUserbyMonth').style.display='none';
            			document.getElementById('oldUser').style.display='none';
            			document.getElementById('sleepUser').style.display='none';
            		}
    			
    			break;
    		case 2:
    			oldUserCount++;
    			
    			
    				document.getElementById('oldUser').style.display='';
        			document.getElementById('sleepUser').style.display='none';
        			document.getElementById('newUser').style.display='none';
        			document.getElementById('unUserbyMonth').style.display='none';
        			document.getElementById('awayUser').style.display='none';
        			document.getElementById('all').style.display='none';
        			if(unUserbyMonthCount%2==0 && newUserCount%2==0 && oldUserCount%2==0 && sleepUserCount%2==0 && awayUserCount%2==0){
            			document.getElementById('all').style.display='';
            			document.getElementById('awayUser').style.display='none';
            			document.getElementById('newUser').style.display='none';
            			document.getElementById('unUserbyMonth').style.display='none';
            			document.getElementById('oldUser').style.display='none';
            			document.getElementById('sleepUser').style.display='none';
            		}
    			
    			
    			break;
    		case 3:
    			sleepUserCount++;
    				document.getElementById('sleepUser').style.display='';
        			document.getElementById('newUser').style.display='none';
        			document.getElementById('unUserbyMonth').style.display='none';
        			document.getElementById('oldUser').style.display='none';
        			document.getElementById('awayUser').style.display='none';
        			document.getElementById('all').style.display='none';
        			if(unUserbyMonthCount%2==0 && newUserCount%2==0 && oldUserCount%2==0 && sleepUserCount%2==0 && awayUserCount%2==0){
            			document.getElementById('all').style.display='';
            			document.getElementById('awayUser').style.display='none';
            			document.getElementById('newUser').style.display='none';
            			document.getElementById('unUserbyMonth').style.display='none';
            			document.getElementById('oldUser').style.display='none';
            			document.getElementById('sleepUser').style.display='none';
            		}
    			
    			break;
    		case 4:
    			awayUserCount++;
    				document.getElementById('awayUser').style.display='';
        			document.getElementById('newUser').style.display='none';
        			document.getElementById('unUserbyMonth').style.display='none';
        			document.getElementById('oldUser').style.display='none';
        			document.getElementById('sleepUser').style.display='none';
        			document.getElementById('all').style.display='none';
        			if(unUserbyMonthCount%2==0 && newUserCount%2==0 && oldUserCount%2==0 && sleepUserCount%2==0 && awayUserCount%2==0){
            			document.getElementById('all').style.display='';
            			document.getElementById('awayUser').style.display='none';
            			document.getElementById('newUser').style.display='none';
            			document.getElementById('unUserbyMonth').style.display='none';
            			document.getElementById('oldUser').style.display='none';
            			document.getElementById('sleepUser').style.display='none';
            		}
    			break;
    	  //day
    		case 5:
    			unUserCount++;
    			
    				document.getElementById('unUser').style.display='';
        			document.getElementById('oneUser').style.display='none';
        			document.getElementById('manyUser').style.display='none';
        			document.getElementById('all').style.display='none';
        			if(unUserCount%2==0 && oneUserCount%2==0 && manyUserCount%2==0){
        				document.getElementById('all').style.display='';
            			document.getElementById('manyUser').style.display='none';
            			document.getElementById('unUser').style.display='none';
            			document.getElementById('oneUser').style.display='none';
            		}
    			break;
    		case 6:
    			oneUserCount++;
    				document.getElementById('oneUser').style.display='';
        			document.getElementById('unUser').style.display='none';
        			document.getElementById('manyUser').style.display='none';
        			document.getElementById('all').style.display='none';
        			if(unUserCount%2==0 && oneUserCount%2==0 && manyUserCount%2==0){
        				document.getElementById('all').style.display='';
            			document.getElementById('manyUser').style.display='none';
            			document.getElementById('unUser').style.display='none';
            			document.getElementById('oneUser').style.display='none';
            		}
    			break;
    		case 7:
    			manyUserCount++;
    				document.getElementById('manyUser').style.display='';
        			document.getElementById('unUser').style.display='none';
        			document.getElementById('oneUser').style.display='none';
        			document.getElementById('all').style.display='none';
        			if(unUserCount%2==0 && oneUserCount%2==0 && manyUserCount%2==0){
        				document.getElementById('all').style.display='';
            			document.getElementById('manyUser').style.display='none';
            			document.getElementById('unUser').style.display='none';
            			document.getElementById('oneUser').style.display='none';
            		}
    			
    			break;
    		}
    		
    		
    		
    	 });
    }
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
</script>
<input id="theDate" name="theDate" type="hidden" value="${date}" />
<input id="kpiType" name="kpiType" type="hidden" value="${kpiType}" />
<input id="myProductId" name="myProductId" type="hidden" value="${productId}" />
</body>
</html>