<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import="com.infosmart.portal.pojo.DwpasCKpiInfo"%>
<%@page import="com.infosmart.portal.util.Const"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String indexMenuId = (String)session.getAttribute(Const.INDEX_MENU_ID);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${systemName}</title>
<link href="<%=path%>/common/css/cssz.css" rel="stylesheet" type="text/css"  /> 
<script type="text/javascript" src="<%=path%>/common/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/jquery-1.4.3.min.js"></script>
<script type="text/javascript">

function showloading(){
	var loadingDiv=window.parent.document.getElementById("loading_wait_Div");
	if(loadingDiv!=null){
		  loadingDiv.style.display="block";
	  }
}
function helpShow(){
	document.getElementById('help').style.display="";
}
function helpHidden(){
	document.getElementById('help').style.display="none";
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


function restartOpen(queryDate){
	
	showloading();
    if(queryDate==null||queryDate.length==0) queryDate=document.getElementById('maxDate').value;
    location.href='<%=path%>/userExperience/showUserExperience?menuId=${menuId}&queryDate='+queryDate;
      
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
function changeProduct(newProdId){
	showloading();
	document.location.href="<%=path%>/userExperience/showUserExperience.html?menuId=${menuId}&productId="+newProdId+"&kpiType=${kpiType}";
}

function changeTheDate(n){
	showloading();
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
		location.href='<%=path%>/userExperience/showUserExperience?menuId=${menuId}&queryDate='+changedDate;
	}
}
window.onload=function(){
	parent.changeIframeSize(document.body.scrollHeight);
}





function sortTable(orderId,imgId,num)
{

    var iframeDiv = document.getElementById("userExperienceTableIframe");
    var allImg = document.getElementsByName("my_img");
    var theImg = document.getElementById(imgId);
    
    if(theImg.alt=="desc"){
    	theImg.src='<%=path%>/common/images/arrow1.png';
    	iframeDiv.src='<%=path%>/userExperience/showChannelInfo.html?orderId='+orderId+'&ascOrDesc=asc'
    	theImg.alt="asc";
    }else if(theImg.alt=="asc"){
    	theImg.src='<%=path%>/common/images/arrow.png';
    	iframeDiv.src='<%=path%>/userExperience/showChannelInfo.html?orderId='+orderId+'&ascOrDesc=desc'
    	theImg.alt="desc";
    }else{
    	theImg.src='<%=path%>/common/images/arrow.png';
    	iframeDiv.src='<%=path%>/userExperience/showChannelInfo.html?orderId='+orderId+'&ascOrDesc=desc'
    	theImg.alt="desc";
    }
    for(var i=1; i<=allImg.length; i++){
    	if(i!=num){
    		document.getElementById(i+"_img").src='<%=path%>/common/images/arrow2.png';
    		document.getElementById(i+"_img").alt="";
    		
    	}
    }

   
}


function iframeHeight(){
	var ifm= document.getElementById("userExperienceTableIframe");   
	var subWeb = document.frames ? document.frames["userExperienceTableIframe"].document : ifm.contentDocument;   
	if(ifm != null && subWeb != null) {
	   ifm.height = subWeb.body.scrollHeight;
	   try
	   {
		  window.iframeOnload();
	   }
	   catch (e)
	   {
	   }
	}  
}



</script>

</head>
<body class="indexbody2">
  
<!--产品选择页面  -->
<jsp:include page="../common/ChoiceProduct.jsp"/>

<!--头部结束-->
<!-- 当前位置 -->
	<div class="kpi_position" id="navigationDiv"  style="z-index:9999999">
		<div style="padding-top: 2px">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="78%">
					<div style="padding:3px 0px 0px 11px">
                     <div style="float:left">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						当前位置：<a href="javascript:void(0);" onclick="shouye();">首页</a>&gt;
						<a href="javascript:void(0);" onclick="goJiankang();">${parentMenu.menuName}</a>&gt;
						<a href="<%=path%>/userExperience/showUserExperience?menuId=${menuId}">
							${modulemap.USER_EXPERIENCE_USER_CONVERT_CHART.menuName }</a>&gt; 
						<span><font color="red">${productInfo.productName}</font></span> </div>
						<div style="float:left">&nbsp;&nbsp;
						<a href="javascript:void(0);" onclick="choiceRrdInfo();"><img src="<%=path%>/common/images/jwy_button_01.gif" border="0" /></a></div>				
				    </div>
					</td>
					<td width="22%"><table width="100%" border="0" cellspacing="0"
							cellpadding="0" style="padding-top: 5px">
							<tr>
								<td>
									<span style="vertical-align: middle;"> 数据时间：</span> 
										<a href="javascript:void(0);" onclick="changeTheDate(-1);">
										<img style="vertical-align: middle;" align="middle"
											src="<%=path%>/common/images/ddy_2.gif" width="11" height="23"
										border="0" /></a> 
										<input id="maxDate" name="maxDate" type="text" class="ddy_index_input" value="${queryDate}"
											onclick="WdatePicker({isShowWeek:true,isShowClear:false,readOnly:true,startDate:'${queryDate}',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true,doubleCalendar:true,onpicked:function(dp){restartOpen(dp.cal.getNewDateStr());}})"
											onchange="restartOpen();" style="cursor: pointer;" /> 
										<a href="javascript:void(0);" onclick="changeTheDate(1);">
										<img style="vertical-align: middle;" align="middle"
											src="<%=path%>/common/images/ddy_1.gif" width="11" height="23"
											border="0" /></a>
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
		
<div class="ddy_channel2" ></div>
	<div class="index_home">
		<div style="margin-top: 0px; *margin-top: -8px; _margin-top: -8px;">
			<img src="<%=path%>/common/images/wph_8.gif" />
		</div>
		<!--第一部分-->
		<div style="padding: 0px 0px 3px 0px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				style="padding-top: 3px">
				<tr>
					<td width="67%"></td>
					<td width="33%" style="text-align: right;"></td>
				</tr>
			</table>
		</div>

		<!--第二部分-->
		<div class="content" style="width: 997px; float: right; background-color: #EFF0F2">
			<div class="normalbox">
				<div class="box-conent box-ued-change"
					style="margin-top: -5px; _margin-top: 0px; margin-left: 0px; *margin-left: -6px; _margin-left: 0px; background: #EFF0F2;">
					<c:if test="${modulemap.USER_EXPERIENCE_USER_CONVERT_CHART.isShow==1 }">
					<div class="index_title">
						<div class="index_title_pd">
							<table>
								<tr>
									<td><img src="<%=path%>/common/images/new_jwy_4.gif" /></td>
									<td>&nbsp;${modulemap.USER_EXPERIENCE_USER_CONVERT_CHART.moduleName }</td>
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
										<b>${modulemap.USER_EXPERIENCE_USER_CONVERT_CHART.moduleName}：</b>
									</p>
									<p style="font-weight: normal;">
										${modulemap.USER_EXPERIENCE_USER_CONVERT_CHART.remark}</p>
</div>
</div></div>
						</div>
					</div>

					<div class="clear"></div>
					<div class="ued-change-top">

						<h3 class="uedgcol"
							style="width: 1.5em; padding-top: 6px; padding-left: 50px; padding-right: 30px; background: #EFF0F2;">${modulemap.KPI_COM_MEMBER_TOTAL_IN.columnKind}</h3>
						<div class="change-chart"
							style="float: left; width: 830px; height: 200px; position: relative;">
							<img src="<%=path%>/common/images/ngreen-yuan.png"
								style="height: 150px; width: 150px; top: 23px; position: absolute;"
								alt="" /> <span
								style=" background:url(<%=path%>/common/images/ngreen-xian.png) repeat-x; top:96px; left:150px; width:570px; line-height:1px; font-size:1px; height:4px; position:absolute;"></span>
							<c:set var="lef11" value="${400-vo.totalTryPie }">
							</c:set>
							<c:set var="lef12" value="${410-vo.totalTryPie }">
							</c:set>
							<c:set var="top11" value="${98-vo.totalTryPie }">
							</c:set>
							<c:set var="r11" value="${2*vo.totalTryPie }">
							</c:set>
							<span
								style=" background:url(<%=path%>/common/images/ngreen-jiantou.png) repeat-x; top:90px; left:${lef11}px; width:10px; line-height:1px; font-size:1px; height:15px; position:absolute;"></span>
							<img src="<%=path%>/common/images/ngreen-yuan.png"
								style="height:${r11}px; width:${r11}px; top:${top11}px; left:${lef12}px; position:absolute;"
								alt="" />
							<c:set var="lef21" value="${710-vo.totalSucPie }">
							</c:set>
							<c:set var="lef22" value="${720-vo.totalSucPie }">
							</c:set>
							<c:set var="top21" value="${98-vo.totalSucPie }">
							</c:set>
							<c:set var="r21" value="${2*vo.totalSucPie }">
							</c:set>
							<span
								style=" background:url(<%=path%>/common/images/ngreen-jiantou.png) repeat-x; top:90px; left:${lef21}px; width:10px; line-height:1px; font-size:1px; height:15px; position:absolute;"></span>
							<img src="<%=path%>/common/images/ngreen-yuan.png"
								style="height:${r21}px; width:${r21}px; top:${top21}px; left:${lef22}px; position:absolute;"
								alt="" /> <span class="uedgcol"
								style="position: absolute; font-size: 14px; left: 240px; top: 77px;">${vo.totalTryPer}
								%</span> <span class="uedgcol"
								style="position: absolute; font-size: 14px; left: 550px; top: 77px;">${vo.totalSucPer}
								%</span> <span class="uedgcol"
								style="position: absolute; font-size: 16px; left: 155px; top: 100px; width: 96px; line-height: 1.1em;">${modulemap.KPI_COM_MEMBER_TOTAL_IN.columnDisplayName}${vo.totalMemberIn}</span>
							<span class="uedgcol"
								style="position: absolute; font-size: 14px; left: 500px; top: 100px; width: 96px; line-height: 1.1em;">${modulemap.KPI_COM_MEMBER_TOTAL_TRY.columnDisplayName}${vo.totalTry}</span>
							<span class="uedgcol"
								style="position: absolute; font-size: 14px; left: 760px; top: 100px; width: 96px; line-height: 1.1em;">${modulemap.KPI_COM_MEMBER_TOTAL_SUC.columnDisplayName}${vo.totalSuccess}</span>
						</div>
					</div>
					</c:if>
					<div class="" style="clear: both;"></div>
					<c:if test="${modulemap.USER_EXPERIENCE__NEW_OLD_USER_CONVERT_CHART.isShow==1 }">
					<div class="index_title">
						<div class="index_title_pd">
							<table>
								<tr>
									<td><img src="<%=path%>/common/images/new_jwy_4.gif" /></td>
									<td>&nbsp;${modulemap.USER_EXPERIENCE__NEW_OLD_USER_CONVERT_CHART.moduleName }</td>
								</tr>
							</table>

						</div>
						<span class="box-top-right"> <em
							style="background: url(<%=path%>/common/images/askicon5.png); cursor: pointer"
							class="box-askicon" onmouseover="helpShow2();"
							onmouseout="helpHidden2();"></em>
						</span>
						<div id="help2" style="display: none; font-size: 12px;">
							<div style="position:relative;z-index:99999999; margin:0px 0px 0px 310px">
										<div class="pop_w">
										  <div class="pop_z" style="z-index:99999; left:260px;">	<img src="<%=path%>/common/images/pop_tip.png" /></div>
       
<div class="pop_x">							<p>
										<b>${modulemap.USER_EXPERIENCE__NEW_OLD_USER_CONVERT_CHART.moduleName}：</b>
									</p>
									<p style="font-weight: normal;">
										${modulemap.USER_EXPERIENCE__NEW_OLD_USER_CONVERT_CHART.remark}</p>
</div>
</div></div>
						</div>
					</div>


					<div class="ued-change-top" style="background: #EFF0F2;">
						<h3 class="uedrcol"
							style="width: 1.5em; padding-top: 6px; padding-left: 50px; padding-right: 30px; background: #EFF0F2;">
							${modulemap.KPI_COM_MEMBER_OLD_TRY.columnKind }</h3>
						<div class="change-chart"
							style="float: left; width: 830px; height: 200px; position: relative; background: #EFF0F2;">
							<img src="<%=path%>/common/images/nred-yuan.png" style="height: 150px; width: 150px; top: 23px; position: absolute;"
								alt="" /> 
							<span style=" background:url(<%=path%>/common/images/nred-xian.png) repeat-x; top:60px; left:140px; width:580px; line-height:1px; font-size:1px; height:4px; position:absolute;"></span>
							<span style=" background:url(<%=path%>/common/images/nred-xian.png) repeat-x; top:136px; left:140px; width:580px; line-height:1px; font-size:1px; height:4px; position:absolute;"></span>
							<c:set var="lef31" value="${400-vo.oldMemberTryPie }">
							</c:set>
							<c:set var="lef32" value="${410-vo.oldMemberTryPie }">
							</c:set>
							<c:set var="top31" value="${62-vo.oldMemberTryPie }">
							</c:set>
							<c:set var="r31" value="${2*vo.oldMemberTryPie }">
							</c:set>
							<span
								style=" background:url(<%=path%>/common/images/nred-jiantou.png) repeat-x; top:54px; left:${lef31}px; width:10px; line-height:1px; font-size:1px; height:15px; position:absolute;"></span>
							<img src="<%=path%>/common/images/nred-yuan.png"
								style="height:${r31}px; width:${r31}px; top:${top31}px; left:${lef32}px; position:absolute;"
								alt="" />
							<c:set var="lef41" value="${710-vo.oldMemberSucPie }">
							</c:set>
							<c:set var="lef42" value="${720-vo.oldMemberSucPie }">
							</c:set>
							<c:set var="top41" value="${62-vo.oldMemberSucPie }">
							</c:set>
							<c:set var="r41" value="${2*vo.oldMemberSucPie }">
							</c:set>
							<span style=" background:url(<%=path%>/common/images/nred-jiantou.png) repeat-x; top:54px; left:${lef41}px; width:10px; line-height:1px; font-size:1px; height:15px; position:absolute;"></span>
							<img src="<%=path%>/common/images/nred-yuan.png" style="height:${r41}px; width:${r41}px; top:${top41}px; left:${lef42}px; position:absolute;"
									alt="" />
							<c:set var="lef51" value="${400-vo.newMemberTryPie }">
							</c:set>
							<c:set var="lef52" value="${410-vo.newMemberTryPie }">
							</c:set>
							<c:set var="top51" value="${138-vo.newMemberTryPie }">
							</c:set>
							<c:set var="r51" value="${2*vo.newMemberTryPie }">
							</c:set>
							<span style=" background:url(<%=path%>/common/images/nred-jiantou.png) repeat-x; top:130px; left:${lef51}px; width:10px; line-height:1px; font-size:1px; height:15px; position:absolute;"></span>
							<img src="<%=path%>/common/images/nred-yuan.png"
								style="height:${r51}px; width:${r51}px; top:${top51}px; left:${lef52}px; position:absolute;"
								alt="" />
							<c:set var="lef61" value="${710-vo.newMemberSucPie }">
							</c:set>
							<c:set var="lef62" value="${720-vo.newMemberSucPie }">
							</c:set>
							<c:set var="top61" value="${138-vo.newMemberSucPie }">
							</c:set>
							<c:set var="r61" value="${2*vo.newMemberSucPie }">
							</c:set>
							<span style=" background:url(<%=path%>/common/images/nred-jiantou.png) repeat-x; top:130px; left:${lef61}px; width:10px; line-height:1px; font-size:1px; height:15px; position:absolute;"></span>
							<img src="<%=path%>/common/images/nred-yuan.png"
								style="height:${r61}px; width:${r61}px; top:${top61}px; left:${lef62}px; position:absolute;"
								alt="" /> 
							<span class="uedrcol" style="position: absolute; font-size: 14px; left: 240px; top: 40px;">${vo.oldMemberTryPer} %</span> 
							<span class="uedrcol" style="position: absolute; font-size: 14px; left: 550px; top: 40px;">${vo.oldMemberSucPer} %</span> 
							<span class="uedrcol" style="position: absolute; font-size: 14px; left: 240px; top: 115px;">${vo.newMemberTryPer} %</span> 
							<span class="uedrcol" style="position: absolute; font-size: 14px; left: 550px; top: 115px;">${vo.newMemberSucPer} %</span> 
							<span class="uedrcol" style="position: absolute; font-size: 16px; left: 155px; top: 80px; width: 96px; line-height: 1.1em;">
								${modulemap.KPI_COM_MEMBER_TOTAL_OLDANDNEW.columnDisplayName}${vo.totalOldAndNew}</span>
							<span class="uedrcol" style="position: absolute; font-size: 14px; left: 500px; top: 65px; width: 115px; line-height: 1.1em;">
								${modulemap.KPI_COM_MEMBER_OLD_TRY.columnDisplayName}${vo.oldMemberTry}</span>
							<span class="uedrcol" style="position: absolute; font-size: 14px; left: 760px; top: 65px; width: 115px; line-height: 1.1em;">
								${modulemap.KPI_COM_MEMBER_OLD_SUC.columnDisplayName}${vo.oldMemberSuccess}</span>
							<span class="uedrcol" style="position: absolute; font-size: 14px; left: 500px; top: 140px; width: 115px; line-height: 1.1em;">
								${modulemap.KPI_COM_MEMBER_NEW_TRY.columnDisplayName} ${vo.newMemberTry}</span> 
							<span class="uedrcol" style="position: absolute; font-size: 14px; left: 760px; top: 140px; width: 115px; line-height: 1.1em;">${modulemap.KPI_COM_MEMBER_NEW_SUC.columnDisplayName}${vo.newMemberSuccess}</span>
						</div>
					</div>
				</c:if>
				</div>
				<div class="box-bottom">
					<div class="box-bottom-rt">
						<div class="box-bottom-ct"></div>
					</div>
				</div>
			</div>
			<!--第二部分结束-->
			<!--第三部分-->
			<!--tab切换开始-->
			<c:if test="${modulemap.USER_EXPERIENCE_CHANNEL_INTO_SITUATION.isShow==1}">
			<div class="normalbox mt15" id="J-userchangebox">

				<div class="index_title">
					<div class="index_title_pd">
						<table>
							<tr>
								<td><img src="<%=path%>/common/images/new_jwy_4.gif" /></td>
								<td>&nbsp;${modulemap.USER_EXPERIENCE_CHANNEL_INTO_SITUATION.moduleName}</td>
							</tr>
						</table>

					</div>
					<span class="box-top-right"> <em
						style="background: url(<%=path%>/common/images/askicon5.png); cursor: pointer"
						class="box-askicon" onmouseover="helpShow();"
						onmouseout="helpHidden();"></em>
					</span>
					<div id="help" style="display: none; font-size: 12px;">
						<div style="position:relative;z-index:99999999; margin:0px 0px 0px 310px ">
										<div class="pop_w">
										  <div class="pop_z" style="z-index:99999; left:260px;">	<img src="<%=path%>/common/images/pop_tip.png" /></div>
       
<div class="pop_x">								<p>
									<b>${modulemap.USER_EXPERIENCE_CHANNEL_INTO_SITUATION.moduleName}：</b>
								</p>
								<p style="font-weight: normal;">
									${modulemap.USER_EXPERIENCE_CHANNEL_INTO_SITUATION.remark}</p>
</div>
</div></div>
					</div>						
				</div>

				<div class="clear"></div>
				
					<div class="box-conent box-ued-table">
						<table class="ued-table" id="J-uedTable">
							<thead>
								<tr>
									<th style="width: 55px;">序号</th>
									<th style="width: 180px;cursor: pointer;" >渠道编号<!--&nbsp;<img src="<%=path%>/common/images/arrow2.png" style="vertical-align: middle;" align="middle" alt="" /> --></th>
									<th style="width: 180px;cursor: pointer;" >渠道描述<!--&nbsp;<img src="<%=path%>/common/images/arrow2.png" style="vertical-align: middle;" align="middle" alt="" />--></th>
									<th style="width: 180px;cursor: pointer;" onclick="sortTable('camp_uv','1_img',1);">引入会员数&nbsp;<img id="1_img" name="my_img" src="<%=path%>/common/images/arrow2.png" style="vertical-align: middle;" align="middle" alt="" /></th>
									<th style="width: 180px;cursor: pointer;" onclick="sortTable('create_user','2_img',2);">尝试使用会员数&nbsp;<img id="2_img" name="my_img" src="<%=path%>/common/images/arrow2.png" style="vertical-align: middle;" align="middle" alt="" /></th>
									<th style="width: 180px;cursor: pointer;" onclick="sortTable('succ_user','3_img',3);">成功使用会员数&nbsp;<img id="3_img" name="my_img" src="<%=path%>/common/images/arrow.png" style="vertical-align: middle;" align="middle" alt="desc" /></th>
								</tr>
							</thead>
							<tbody>
								<%-- <c:set var="num" value="0"></c:set>
								<c:forEach var="item" items="${channelInfo}">
									<c:set var="num" value="${num+1}"></c:set>
									<c:if test="${num<11 }">
										<tr>
											<td>${num}</td>
											<td>${item.campCode}</td>
											<td style="cursor: pointer;"
													onmouseover="showMessage('message'+${num});"
													onmouseout="hiddenMessage('message'+${num});">${item.campMethod}
												<div id="message${num}" class="tip J-tip"
													style="width: 300px; height: 80px; margin-top: -100px; right: 310px; display: none">
													<p align="left">
														<b>渠道编号：</b>${item.campCode}
													</p>
													<p align="left">
														<b>渠道名称：</b>${item.campName}
													</p>
													<p align="left">
														<b>渠道类型：</b>${item.campType}
													</p>
													<p align="left">
														<b>活动部门：</b>${item.campDept}
													</p>
												</div></td>
											<td>${item.campUv}</td>
											<td>${item.createUser}</td>
											<td>${item.succUser}</td>
										</tr>
									</c:if>
								</c:forEach> --%>
							</tbody>
						</table>
						<div>
						<iframe id="userExperienceTableIframe" src="<%=path %>/userExperience/showChannelInfo.html" frameBorder="0" width="100%" onLoad="iframeHeight();"  scrolling="no" name="userExperienceTableIframe" ></iframe>
						
						
						
						</div>
						
						
					</div>
<%-- 				
				<c:if test="${empty channelInfo}">
					<div style="color: red; height: 20px;" align='center'></div>
					<div style="color: red; height: 25px;" align='center'>当前日期没有渠道引入情况数据</div>
				</c:if> --%>
				<div class="box-bottom">
					<div class="box-bottom-rt">
						<div class="box-bottom-ct"></div>
					</div>
				</div>
			</div>
		</c:if>
		</div>
	</div>
	
	<div style="height: 5px;"></div>
	<input id="theDate" type="hidden" value="${queryDate }" />
	<input id="myProductId" type="hidden" value="${productId }" />
</body>
</html>