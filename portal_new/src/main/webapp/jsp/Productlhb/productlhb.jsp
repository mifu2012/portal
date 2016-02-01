<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page import="com.infosmart.portal.pojo.DwpasCKpiInfo"%>
<%@ page import="java.util.Date"%>
<%@page import="com.infosmart.portal.util.Const"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String indexMenuId = (String) session
			.getAttribute(Const.INDEX_MENU_ID);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>${systemName}龙虎榜</title>
<link href="<%=path%>/common/css/cssz.css" rel="stylesheet"
	type="text/css" />
<link href="<%=path%>/common/css/base.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript"
	src="<%=path%>/common/js/My97DatePicker/My98MonthPicker.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/arale.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/ddycom.js"></script>
<!-- chart -->
<script src="<%=path%>/common/js/amcharts.js" type="text/javascript"></script>
<script src="<%=path%>/common/js/raphael.js" type="text/javascript"></script>
<script src="<%=path%>/common/js/FusionCharts_Trial.js"
	type="text/javascript"></script>
<script src="<%=path%>/common/js/amfallback.js" type="text/javascript"></script>
<!-- chart -->
<script type="text/javascript">
//大事记详细数据
/*
function amClickedOnEvent(chart_id, date, description, id, url)
{
   document.getElementById("detailEventDiv").style.display="";
   //////////////////////////////////////新加的
   //设置DIV高度s
    var divTop=0;
	var clientHeight=window.parent.document.getElementById('center_iframe').height;
	if(window.parent!=null)
	{
		divTop=window.parent.document.body.scrollTop+window.parent.document.documentElement.scrollTop+100;
	}else
	{
        divTop=document.body.scrollTop+document.documentElement.scrollTop;
	}
	//alert(divTop);
	detailEventDiv.style.top=divTop+90+"px";
	detailEventDiv.style.position="absolute";
	detailEventDiv.style.zIndex=9999;
	/////////////////////////////新加的结束
   document.getElementById("eventIframe").src="<%=path%>/stockChart/getDetailEvent.html?eventId="+id+"&random="+Math.random();
}
*/
E.domReady(function () {
    E.on($$(".J-image"), "onmouseover", function (e) {
        $A($$(".J-image")).each(function (el) {
            el.removeClass("noopacity");
        });
        var target = $(e.target);
        target.addClass('noopacity');
        if (target.hasClass("three-image-tb")) {
            active("jt");
        }
        if (target.hasClass("three-image-sh")) {
            active("js");
        }
        if (target.hasClass("three-image-zn")) {
            active("jz");
        }
        if (target.hasClass("three-image-all")) {
            active("all");
        }
    });
    E.on($$(".J-image"), "onmouseout", function (e) {
        $A($$(".J-image")).each(function (el) {
            el.removeClass("noopacity");
        });
        active("all");
    });
   
    function active(type) {
    	if(type!="all"){
            $A($("J-threeTbody").query("tr")).each(function (el) {
                el.removeClass("activebg");
                el.addClass("danhuabg");
                el.removeClass("whitebg");
            });
          	  $A($$("." + type)).each(function (el) {
          		el.removeClass("activebg");
                el.removeClass("danhuabg");
          		  if(el.attr("id").indexOf(type)<0){
          			 el.addClass("danhuabg");//隐藏掉该tr
          		  }
             }); 
           var i=0;
          	$A($("J-threeTbody").query("tr")).each(function (el) {
          		 if(el.attr("class").indexOf("danhuabg")<0){
          			el.removeClass("activebg");//灰色
                    el.removeClass("whitebg");//白色
          			 //未隐藏tr
          			 i++;
          			 if(i%2==0){
          				 el.addClass("whitebg");
          			 }else{
          				 el.addClass("activebg");
          			 }
          		  }
            });
        }else{
        	var j=0;
        	$A($("J-threeTbody").query("tr")).each(function (el) {
        		 el.removeClass("activebg");
                 el.removeClass("danhuabg");
                 el.removeClass("whitebg");
        		 j++; 
        		 if(j%2==0){
     				 el.addClass("whitebg");
     			 }else{
     				 el.addClass("activebg");
     			 }
            });
        }
    }
    var table = $("J-sortTable");
    var tbody = table.query("tbody")[0];
    var thead = table.query("thead")[0];
    
    A($("J-sortTable-tbody").query("tr")).each(function(el){
    	el.mouseover(function(){
    		el.addClass("active");
    	});
    	el.mouseout(function(){
    		el.removeClass("active");
    	});
    	el.click(function(){
    		location.href="<%=path%>/productHealth/showProductHealth.html?menuId=${menumap.m_03}&productId="+el.attr("data");
    	});
    });
 
    A($$(".J-sortColoum")).each(function(el){
    	el.click(function(e){
    		var _th = el;
            var colnum = _th.prevAll("th").length;
           var tdLength = document.getElementById("J-sortTable").rows[0].cells.length;
        for(var i=1;i<=tdLength-1;i++){
          if(colnum==i){
        	  document.getElementById("img"+i).src="<%=path%>/common/images/arrow.png";
        	  document.getElementById("lhb_"+i).style.color='#40709f';
        
          }else{
        	  document.getElementById("img"+i).src="<%=path%>/common/images/arrow2.png";
        	  document.getElementById("lhb_"+i).style.color='#999';
        	
          }
        }

            if (el.hasClass("desc")) {
                sortTable(colnum, "asc");
                document.getElementById("img"+colnum).src="<%=path%>/common/images/arrow1.png";
                _th.addClass("asc");
            } else {
                sortTable(colnum, "desc");
                _th.addClass("desc");
                document.getElementById("img"+colnum).src="<%=path%>/common/images/arrow.png";
            }
            _th.addClass("sortcolumn");
            return false;
    	});
    });
    var thf = $("J-sortTable").query("th")[1];
    if(thf){
    	sortTable(1,"desc");
    	thf.addClass("desc");
    	thf.addClass("sortcolumn");
    }
    function sortTable(colnum, type) {
        $A($$(".sortcolumn")).each(function (el) {
            el.removeClass("sortcolumn");
        });

        var trarr = $A(tbody.query("tr"));
        trarr.each(function (el) {
            el.query("td")[colnum].addClass("sortcolumn");
        });
        trarr[0].sort(function (a, b) {
            var anum = a.query("td")[colnum].query(".data-div")[0].attr("data-num");
            var bnum = b.query("td")[colnum].query(".data-div")[0].attr("data-num");

            if (type == "asc") {
                return anum - bnum;
            }
            if (type == "desc") {
                return bnum - anum;
            }
        });
        for (var i = 0; i < trarr[0].length; i++) {
            trarr[0][i].inject(tbody, 'bottom');
        }
        removeSort();
        return;
    }
    function removeSort() {
        $A(thead.query("th")).each(function (el) {
            el.removeClass("desc");
            el.removeClass("asc");
        });
    }
    var iframeContentHeight = document.body.offsetHeight;
	parent.changeIframeSize(iframeContentHeight);
    
});

  function restartOpen(){
	  var queryMonth=document.getElementById('queryDate').value;
       location.href='<%=path%>/Champion/getChampions?menuId=${menuId}&queryMonth='+queryMonth;
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
		  location.href='<%=path%>/Champion/getChampions?1=1&menuId=${menuId}&queryMonth='+changedDate+'&menuId=${menuId}';
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
 
  /* 平台交叉趋势图弹出Div */
function crossPlatformStock(){
	try
	  {
		var url="<%=path%>/Champion/crossPlatformStock.html?date=${date}";
		var crossPlatformDivId="crossPlatformDivId";
	 	openDivWindow(crossPlatformDivId,"平台交叉趋势图 ",1000,370,url);//openDivWindow 为通用的方法
	  }
	catch (e){
		  alert('对不起!加载数据失败:'+e.message);
	  }
} 

  /* 产品趋势图 弹出Div */
function prdRankStock(){
	try
	  {
		var url="<%=path%>/Champion/prdRankStock.html?date=${date}";
		var prdRankDivId="prdRankDivId";
	 	openDivWindow(prdRankDivId,"产品趋势图 ",1200,350,url);//openDivWindow 为通用的方法
	  }
	catch (e){
		  alert('对不起!加载数据失败:'+e.message);
	  }	
}
  
  //导出EXCEL
  function downExcel(){
	  //location.href="<%=path%>/Champion/excelDown.html";
	if(!confirm("确定导出数据吗?")) return;
	document.getElementById("downloadFrm").src="<%=path%>/Champion/excelDown.html";
	document.getElementById("downloadFrm").submit();
  }
  function downExcelPlatform(){
	  if(!confirm("确定导出数据吗?")) return;
	  document.getElementById("downloadFrm").src="<%=path%>/Champion/excelDownPlatform.html";
	  document.getElementById("downloadFrm").submit();
  }
</script>
</head>
<body class="indexbody2">
	<!-- 选择产品 -->
	<jsp:include page="../common/ChoiceProduct.jsp" />
	<div class="new_jwy_yy">
		<!-- 当前位置 -->
		<div class="kpi_position" id="navigationDiv" style="z-index:9999999">
			<div style="padding-top: 2px">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="78%">
							<div style="color: #6a6a6a; padding-left: 30px; padding-top: 5px">
								当前位置： <a
									href="<%=path%>/index/gotoHomePage.html?menuId=<%=indexMenuId%>">首页</a>&gt;
								<a href="<%=path%>/Champion/getChampions.html?menuId=${menuId}">${modulemap.LHB_PLATFORM_CROSS_CHART.menuName
									}</a>
							</div>
						</td>
						<td width="22%">
							<table width="100%" border="0" cellspacing="0" cellpadding="0"
								style="padding-top: 5px">
								<tr>
									<td><span
										style="line-height: 20px; vertical-align: middle;">数据时间:</span>
										<a href="javascript:void(0);" onclick="changeTheDate(-1);">
											<img style="vertical-align: middle;" align="middle"
											src="<%=path%>/common/images/ddy_2.gif" width="11"
											height="23" border="0" /> </a> <input id="queryDate"
										name="queryDate" type="text" class="ddy_index_input"
										readonly="readonly" onclick="setMonth(this,this)"
										value="${dateTime}" onchange="restartOpen()"
										style="cursor: pointer;" /> <a href="javascript:void(0);"
										onclick="changeTheDate(1);"> <img
											style="vertical-align: middle;" align="middle"
											src="<%=path%>/common/images/ddy_1.gif" width="11"
											height="23" border="0" /> </a>
									</td>
								</tr>
							</table></td>
					</tr>
				</table>
			</div>
		</div>
		<!-- 当前位置结束 -->
		<div class="index_home">
			<div class="ddy_channel2"></div>
			<div style="padding-top: 3px"></div>
			<div class="normalbox mt15">
				<div class="index_title">
					<div class="index_title_pd">
						<table>
							<tr>
								<td><img src="<%=path%>/common/images/new_jwy_4.gif" /></td>
								<td>&nbsp; ${modulemap.LHB_PLATFORM_CROSS_CHART.moduleName
									} <a href="javascript:void(0);" onclick="crossPlatformStock();">【查看趋势】</a>
								</td>
							</tr>
						</table>

					</div>
					<span class="box-top-right"> <em
						style="background: url(<%=path%>/common/images/askicon5.png); cursor: pointer"
						class="box-askicon" onmouseover="helpShow1();"
						onmouseout="helpHidden1();"></em> </span>
					<div id="help1" style="display: none; font-size: 12px;">
					<div style="position:relative;z-index:99999999;  margin:0px 0px 0px 310px">
										<div class="pop_w">
										  <div class="pop_z" style="z-index:99999; left:260px;">	<img src="<%=path%>/common/images/pop_tip.png" /></div>
       
<div class="pop_x">		<p>
									<b>${modulemap.LHB_PLATFORM_CROSS_CHART.moduleName}：</b>
								</p>
								<p style="font-weight: normal;">
									${modulemap.LHB_PLATFORM_CROSS_CHART.remark}</p>
   
</div>
</div></div>
					</div>
				</div>

				<div class="box-conent box-three">
					<div class="three-image three-image-all J-image">
						<div class="three-image-tb J-image" title="WAP相关"></div>
						<div class="three-image-sh J-image" title="主站相关"></div>
						<div class="three-image-zn J-image" title="合作平台相关"></div>
					</div>
					<div class="three-table">
						<table style="float: left">
							<thead>
								<tr class="whitebg">
									<th class="noborder"></th>
									<c:forEach items="${kpiNameUnitRelationList}"
										var="kpiNameUnitRelation">
										<th>${kpiNameUnitRelation.kpiName}(${kpiNameUnitRelation.unit})
										</th>
									</c:forEach>
								</tr>
							</thead>
							<tbody id="J-threeTbody">
								<c:forEach items="${crossUserKpiDatas}" var="crossUserKpiData"
									varStatus="sel">
									<c:if test="${sel.index!=0}">
										<c:if test="${sel.index%2 eq 0}">
											<tr class="whitebg ${crossUserKpiData.type}"
												id="${crossUserKpiData.type}">
										</c:if>
										<c:if test="${sel.index%2 eq 1}">
											<tr class="${crossUserKpiData.type}"
												id="${crossUserKpiData.type}">
										</c:if>
										<td style="text-align: left; width: 127px">${crossUserKpiData.flatName}</td>
										<c:forEach items="${crossUserKpiData.kpiDatas}" var="kpidate">
											<td><fmt:formatNumber value="${kpidate.showValue}" /> <%--  <c:if
												test="${not empty kpidate.showValue && kpidate.dwpasCKpiInfo.isPercent eq 1}">
										%
										</c:if> --%>
											</td>
										</c:forEach>
									</c:if>
								</c:forEach>
							</tbody>
						</table>
						
						<div>
						<a href="javascript:viod(0)" onclick="downExcelPlatform();" ><img
											src="<%=path%>/common/images/ddy_22.gif" border="0" /></a>
						</div>
					</div>
				</div>

				<div class="box-bottom">
					<div class="box-bottom-rt">
						<div class="box-bottom-ct"></div>
					</div>
				</div>
			</div>

			<div class="box-conent sort-box">
				<!--产品排行-->
				<div class="index_title">
					<div class="index_title_pd">

						<table>
							<tr>
								<td><img src="<%=path%>/common/images/new_jwy_4.gif" /></td>
								<td>&nbsp;${modulemap.LHB_PRODUCT_RANKLIST.moduleName }<a
									href="javascript:void(0);" onclick="prdRankStock();">【查看趋势】</a>
								</td>
							</tr>
						</table>
					</div>
					<span class="box-top-right"> <em
						style="background: url(<%=path%>/common/images/askicon5.png); cursor: pointer"
						class="box-askicon" onmouseover="helpShow2();"
						onmouseout="helpHidden2();"></em> </span>
					<div id="help2" style="display: none; font-size: 12px;">
											<div style="position:relative;z-index:99999999;  margin:0px 0px 0px 310px ">
										<div class="pop_w">
										  <div class="pop_z" style="z-index:99999; left:260px;">	<img src="<%=path%>/common/images/pop_tip.png" /></div>
       
<div class="pop_x">		<p>
									<b>${modulemap.LHB_PRODUCT_RANKLIST.moduleName}：</b>
								</p>
								<p style="font-weight: normal;">
									${modulemap.LHB_PRODUCT_RANKLIST.remark}</p>
   
</div>
</div></div>
					
					
					
					
					
					</div>
				</div>
			</div>


			<!-- 没有数据 -->
			<c:if test="${fn:length(prodKpiDatas)==0}">
				<div style="color: red;font-size:13px;" align='center'>没有产品排行数据或该模块未配置关联指标</div>
			</c:if>

			<div style="background: #eff0f2; border:1px solid #d6d6d6">
				<!-- 如果有数据 -->
				<c:if test="${fn:length(prodKpiDatas)!=0}">
					<div style="width: 100%; height: 10px;"></div>
					<table class="sort-table" id="J-sortTable" >
						<thead>
							<tr id="myId" class="lhb_tab">
								<th class="noborder"  style="border-left:0; border-bottom:0">产品名称</th>
								<c:forEach items="${kpiNameUnitRelations}"
									var="kpiNameUnitRelation" varStatus="sel">
									<th class="J-sortColoum"  style="border:border:1px solid #ccc; border-bottom:0; height:28px" id="lhb_${sel.index+1}">${kpiNameUnitRelation.kpiName} <c:if
											test="${not empty kpiNameUnitRelation.unit && kpiNameUnitRelation.unit!='%'}">
									   ${kpiNameUnitRelation.unit}
								 	</c:if> <img name="imgs" id="img${sel.index+1}"
										<c:if test="${sel.index eq '0'}">src="<%=path%>/common/images/arrow.png"</c:if>
										src="<%=path%>/common/images/arrow2.png" />
									</th>
								</c:forEach>
							</tr>
						</thead>
						
						
						<tbody id="J-sortTable-tbody">
							<c:forEach items="${prodKpiDatas}" var="prodKpiData"
								varStatus="sel">
								<c:if test="${sel.index >0}">
									<tr data="${prodKpiData.prodId}">
										<td class="noborder">${prodKpiData.prodName}</td>
										<c:forEach items="${prodKpiData.kpiDatas}" var="kpiData">
											<td>
												<div class="data-div" data-num="${kpiData.baseValue}">
													<span class="data-num" style="width:${kpiData.maxValue}%;"></span>
													<span class="data-value"><fmt:formatNumber
															value="${kpiData.showValue}" /> <%-- <c:if test="${kpiData.dwpasCKpiInfo.isPercent eq 1}">%</c:if>  --%>
													</span>
												</div></td>
										</c:forEach>
									</tr>
								</c:if>
							</c:forEach>
							
						</tbody>
					</table>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td><div style="margin: 10px 0px 10px 10px" >
										<a href="javascript:viod(0)" onclick="downExcel();" ><img
											src="<%=path%>/common/images/ddy_22.gif" border="0" /></a>
									</div></td>
							</tr>
						</table>
				</c:if>
			</div>

			<div class="box-bottom">
				<div class="box-bottom-rt">
					<div class="box-bottom-ct"></div>
				</div>
			</div>
		</div>
	</div>
	<!-- 大事件详细信息 -->
	<!--
	<script type="text/javascript">
      function changeEventDivSize(divHeight){
	    document.getElementById("trend-sel").style.height=divHeight+"px";
     }
  </script>
	<div
		style="width: 825px; height: auto; margin: auto; border: 3px solid #73726e; position: absolute; top: 230px; left: 534px; margin: -150px 0 0 -450px; display: none; background: #fff"
		id="detailEventDiv">
		<div style="width: 820px; height: auto; float: left;">
			<h3
				style="background: #d8e1e6; width: 815px; padding-left: 10px; line-height: 30px; color: #e85507; font-size: 16px;">
				<label id="windowOpenDiv_title"><b>大事记</b> </label> <span
					style="color: #333333; position: absolute; right: 20px; font-size: 12px; font-weight: normal; color: #333333; top: 0px;">
					<a href="javascript:void(0);"
					onclick="Javascript:document.getElementById('detailEventDiv').style.display='none';"><font
						color='red'>关闭</font> </a> </span>
			</h3>
			<div class="trend-sel" id="trend-sel">
				<iframe src="" id="eventIframe"
					style="width: 100%; height: 100%; border: none;" scrolling="no"
					frameborder="0"> </iframe>
			</div>
		</div>
	</div>
	-->
	<iframe src="" id="downloadFrm" name="downloadFrm" style="width: 100%; height: 100%; border: none;display:none;" scrolling="no" frameborder="0"> </iframe>
</body>
</html>
