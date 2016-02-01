<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="java.util.Date"%>
<%@ page import="com.infosmart.po.report.ReportDataSource"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	//db2
	request.setAttribute("DB_TYPE_DB2", ReportDataSource.DB_TYPE_DB2);
	//mysql
	request.setAttribute("DB_TYPE_MYSQL",
			ReportDataSource.DB_TYPE_MYSQL);
	//oracle
	request.setAttribute("DB_TYPE_ORACLE",
			ReportDataSource.DB_TYPE_ORACLE);
	//PostgreSQL
	request.setAttribute("DB_TYPE_POSTGRESQL",
			ReportDataSource.DB_TYPE_POSTGRESQL);
	//sql server 2000以下版本
	request.setAttribute("DB_TYPE_SQLSERVER_2000",
			ReportDataSource.DB_TYPE_SQLSERVER_2000);
	//sql server 2005以上版本
	request.setAttribute("DB_TYPE_SQLSERVER_2005",
			ReportDataSource.DB_TYPE_SQLSERVER_2005);
	//sybase
	request.setAttribute("DB_TYPE_SYBASE",
			ReportDataSource.DB_TYPE_SYBASE);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>${title}</title>
<meta name="Generator" content="EditPlus">
<meta name="Author" content="">
<meta name="Keywords" content="">
<meta name="Description" content="">
<!-- <style type="text/css">
.span{cursor:pointer;color:#666;}
.up{background:url(../images/more_down.gif) 0 0 no-repeat;width:150px;height:17px;display:block;cursor:pointer;clear:both;margin:0 auto;z-index:999;}
.down{background:url(../images/more_up.gif) 0 0 no-repeat;width:150px;height:17px;display:block;cursor:pointer;clear:both;margin:10 auto;z-index:999;}
</style> -->
<%-- <link type="text/css" rel="stylesheet"
	href="<%=basePath%>/css/report.css" /> --%>
<link href="<%=path%>/css/main.css" rel="stylesheet" type="text/css"  />
<style type="text/css">
.cover {
    display:none;
    position:absolute;
    top:0%;
    left:0%;
    width:100%;
    height:100%;
    z-index:999;
    background-color:gray;
    opacity: 0.2; 
    filter:alpha(opacity=20);
    }
</style>
<!-- 用于IE8之前的版本，处理JSON数据 -->
<script type="text/javascript" src="<%=basePath%>/js/json2.js"></script>

<script src="<%=basePath%>js/jquery-1.5.1.min.js" type="text/javascript"></script>
<script src="<%=basePath%>js/highcharts/highcharts.js" type="text/javascript"></script>
<script src="<%=basePath%>js/highstock/highstock.js" type="text/javascript"></script>
<%-- <script src="<%=basePath%>js/highstock/modules/exporting.js" type="text/javascript"></script> --%>
<script src="<%=basePath%>js/ajaxQueue.js" type="text/javascript"></script>
<script type="text/javascript"
	src="<%=basePath%>/js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>
<!--日期控件-->
<script type="text/javascript"
	src="<%=basePath%>/js/datePicker/WdatePicker.js"></script>
<!--月历控件-->
<script type="text/javascript"
	src="<%=basePath%>/js/datePicker/My98MonthPicker.js"></script>
<!-- 周历控件 -->
<script type="text/javascript"
	src="<%=basePath%>/js/datePicker/My98WeekPicker.js"></script>
</head>

<script type="text/javascript">
	Highcharts.setOptions({
		lang: {
			months: ['一月', '二月', '三月', '四月', '五月', '六月', 
				'七月', '八月', '九月', '十月', '十一月', '十二月'],
			weekdays: ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'],
			shortMonths: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12'],
			rangeSelectorZoom: '缩放',
			rangeSelectorFrom: '日期范围:',
			rangeSelectorTo: '-'
		}
	});

	if (window.parent.document.getElementById("isLink") != null) {
		window.document.title="钻取报表";
	}

	//自适应
	function changeIframeSize(gridHeight) {
		document.getElementById("datalist").height = gridHeight;
		if (window.parent.document.getElementById("report_iframe") != null) {
			var height = document.getElementById("datalist").offsetHeight
				+ document.getElementById("datalist").offsetTop;
			if (height > 549) {
				parent.changeIframeSize(height);
			} else {
				parent.changeIframeSize(549);
			}
		}
	}
	
	// 显示loading
	function showLoading() {
		document.getElementById("loading_wait_Div").style.display="";
		document.getElementById("cover").style.display= "block";
		document.getElementById("cover").style.width=Math.max(document.documentElement.scrollWidth, document.documentElement.clientWidth) + "px";
		document.getElementById("cover").style.height=Math.max(document.documentElement.scrollHeight, document.documentElement.clientHeight) + "px";
	}
	
	//查询参数
	var urlParam="1=1";
	jQuery(document).ready(function(){
		// 生成loading图
		var loadingDiv=document.getElementById("loading_wait_Div");
		loadingDiv=document.createElement("div");
		loadingDiv.id="loading_wait_Div";
		loadingDiv.style.position="absolute";
		loadingDiv.style.left="45%";
		loadingDiv.style.top="30%";
		loadingDiv.innerHTML="<img src='<%=path%>/images/loading1.gif'>";
	  	document.body.appendChild(loadingDiv);
	  	showLoading();
		//取控件默认参数值
		getQueryParameters();
		document.getElementById("datalist").src = "<%=basePath%>/NewReport/gridData.html?reportId=${reportDesign.reportId}&"+encodeURI(urlParam,'utf-8');
	    //定位左上角
		self.moveTo(0,0);   
		//调整屏幕
		self.resizeTo(screen.availWidth,screen.availHeight);
		// 查询框添加回车键查询
		$("input:text").bind("keydown", function(e){
			var keynum;
			if(window.event) {// IE
			  keynum = e.keyCode;
			} else if(e.which) {// Netscape/Firefox/Opera(FF低版本不支持event.keyCode)
			  keynum = e.which;
			}
			if (keynum == 13) {
				queryReportData();
			}
		});
	});
</script>
<Script LANGUAGE=javaScript>       
	//隐藏或显示条件查询模块 
	String.prototype.Trim = function()
	{ 
	  return this.replace(/(^\s*)|(\s*$)/g, ""); 
	} 
	//替换函数
	String.prototype.replaceAll = function(s1,s2) { 
	    return this.replace(new RegExp(s1,"gm"),s2); 
	}
	//根据一级维度查询二级维度
	function getDetailSec(id,countId){
		var selObj=document.getElementById("dimensionDetailSecId"+countId);
		 selObj.length=0;
		 $.ajax({
			    type: "POST",                                                                 
			    url:"<%=basePath%>/NewReport/getDimensionDetailSecList.html?parentId="+id+"&random="+Math.random(),
			    success: function(msg){
			   	var msg=eval(msg);
			   	selObj.options.add(new Option("",""));
			    for(var i=0;i<msg.length;i++){
			        selObj.options.add(new Option(msg[i].value,msg[i].key));
		        }  
		      }
		}); 
	}

	// 获取查询参数值
	function getQueryParameters() {
		//重新取查询参数值
		urlParam="1=1";
		var allInputObj=document.forms[0].elements;
		//alert(allInputObj.length);
		if(allInputObj!=null&&allInputObj.length>0)
		{
			for(var i=0;i<allInputObj.length;i++)
			{
				if(allInputObj[i]==null) continue;
				//alert(allInputObj[i].nodeName.toUpperCase);
                if(allInputObj[i].nodeName.toUpperCase()=="INPUT"||allInputObj[i].nodeName.toUpperCase()=="SELECT")
				{
				   if(allInputObj[i].type.toUpperCase()=="BUTTON") continue;
                   urlParam+="&"+allInputObj[i].name+"="+allInputObj[i].value.replace(/(^\s*)/g, "");;
				}
			}
		}
	}
	
	/**
		异步加载图表数据
		option 图表属性配置
		chartType 图表类型
		id 图表计数
		chartId 图表ID
		reportId 报表ID
		pageNo 页码
		rowNum 行数
	*/
	function loadChart(option, chartType, id, chartId, reportId, pageNo, rowNum) {
		rowNum=rowNum<=0?10:rowNum;
		var url;
		if (chartType == 1) {
			url = encodeURI("<%=basePath%>/reportChartDate/getPieChartDate.html?chartId="
					+chartId+"&reportId="+reportId+"&"+urlParam+"&random="+Math.random());
		} else if (chartType == 2) {
			url = encodeURI("<%=basePath%>/reportChartDate/getReportChartDate.html?chartType=2&chartId="
					+chartId+"&reportId="+reportId+"&"+urlParam+"&pageNo="+pageNo+"&rowNum="+rowNum+"&random="+Math.random());
		} else {
			url = encodeURI("<%=basePath%>/reportChartDate/getReportChartDate.html?chartId="
					+chartId+"&reportId="+reportId+"&"+urlParam+"&pageNo="+pageNo+"&rowNum="+rowNum+"&random="+Math.random());
		}
		//alert(url);
		$.ajaxQueue.offer({
			type: "POST",
			async:true,
			url: url,
		    success: function(msg){
		    	manageLoading();
		    	if(msg==null || msg==''){
		    		$("#container"+id).html("<font color='red'>该图表无显示数据</font>");
		    	}else{
		    		if (chartType == 1) {
		    			msg=eval(msg);
		    			option.series=msg;
			        	chart=new Highcharts.Chart(option);
					} else if (chartType == 2) {
						msg = eval(msg.split("&")[0]);
						option.series = msg;
						chart = new Highcharts.StockChart(option);
					} else {
						option.series =  eval(msg.split("&")[0]);
			      		option.xAxis.categories = eval(msg.split("&")[1]);
			      		chart = new Highcharts.Chart(option);
					}
		    	 }
		    }
		});
	}
	
	// 查询图表数据
	function queryChartData(){
		var option;
		<c:forEach items="${reportFieldList}" var="reportField" varStatus="vel">
			option = option${vel.index+1};
			loadChart(option, ${reportField.chartType}, ${vel.index+1}, '${reportField.chartId}', '${reportField.reportId}', 1, '${reportDefine.pageSize}');
		</c:forEach>
	}
	
	// 处理loading
    function manageLoading() {
    	document.getElementById("loadCompleteNum").value = parseInt(document.getElementById("loadCompleteNum").value) + 1;
		if (document.getElementById("loadCompleteNum").value == parseInt(document.getElementById("chartNum").value) +1) {
			document.getElementById("cover").style.display= "none";
			document.getElementById("loading_wait_Div").style.display="none";
		}
    }

	//取得查询条件，并提交； 
	function queryReportData() {
		showLoading();
		$("#loadCompleteNum").val(0);
		// 获取查询条件值
		getQueryParameters();
		document.getElementById("datalist").src = "<%=basePath%>/NewReport/gridData.html?reportId=${reportDesign.reportId}&"
			+encodeURI(urlParam,'utf-8');
		queryChartData();
	}
	
	//列表翻页，图表数据随着变化 
	function reportChartFollowGrid(pageNo, rowNum){
		<c:forEach items="${reportFieldList}" var="reportField" varStatus="vel">
		<c:if test="${reportField.chartType !=1}">
			option = option${vel.index+1};
			loadChart(option, ${reportField.chartType}, ${vel.index+1}, '${reportField.chartId}', '${reportField.reportId}', pageNo, rowNum);
		</c:if>
		</c:forEach>
	}


	//清空数据
	function resetForm()
	{
		var allInputObj=document.forms[0].elements;
		if(allInputObj!=null&&allInputObj.length>0)
		{
			for(var i=0;i<allInputObj.length;i++)
			{
	                if(allInputObj[i].nodeName.toUpperCase()=="INPUT"&&allInputObj[i].type.toUpperCase()=="TEXT")
					{
						if(allInputObj[i].type.toUpperCase()=="BUTTON"||allInputObj[i].type.toUpperCase()=="HIDDEN") continue;
	                    allInputObj[i].value="";
					} else if (allInputObj[i].nodeName.toUpperCase()=="SELECT") {
	                	 allInputObj[i].value="";
					}
			}
		}
		//queryReportData();
	}

	function  download_new()
	{
		var content=document.getElementById("searchFieldSet");
		var down_btn=document.getElementById("s_btn");
		if(content.style.display=="none")
		{
			content.style.display="block"
			down_btn.className='down';
		}
	    else 
	    {
			content.style.display="none"
			down_btn.className='up';
		}
	}
	
	/**
	*	栏目显示配置
	*	x 鼠标在iframe中的横坐标 
	*	y　鼠标在iframe中的纵坐标 
	*/
    function columnChooserDialog(x, y){
		x += Math.max(document.getElementById("datalist").offsetLeft, document.getElementById("datalist").offsetParent.offsetLeft);
		y += document.getElementById("datalist").offsetTop;
		y = (y-350) > 0 ? y-350 : y;
    	var url = "<%=basePath%>/jsp/newReport/columnChooser.jsp";
		var dg = new $.dialog({
			//title : '栏目显示配置',
			id : '栏目显示配置',
			titleBar : false,
			width : 220,
			height : 350,
			iconTitle : false,
			cover : true,
			fixed: false,
			//maxBtn : false,
			//xButton : false,
			resize : true,
			page : url,
			left : x,
			top : y
		});
		dg.ShowDialog();
    }
</Script>
<body>
	<input type="hidden" id="isLink" value="1"/>
	<input type="hidden" name="reportColumnList" id='reportColumnList' />
	<div id= "cover" class="cover"></div>
	<!-- 加载完成个数（图表和表格） -->
	<input type="hidden" id="loadCompleteNum" value="0" />
	<!-- 图表个数 -->
	<input type="hidden" id="chartNum" value="${fn:length(reportFieldList)}"/>
	<div style="width: 800px; overflow-x: hidden; overflow: hidden;text-align: center; margin-right: auto; margin-left: auto;"
		align="center" id="bodyDiv">
		<!-- <script type="text/javascript">
	//设置div的宽度  为了实现与前台宽度相同
		var width=0;
		if(navigator.userAgent.indexOf("MSIE")>0){
		//IE
			width=document.body.scrollWidth;
		}else{
		//非IE
			width=document.documentElement.scrollWidth;
		}
		document.getElementById("bodyDiv").style.left=(width-800)/2+"px";
	</script> -->
		<div style='float: left;' id='queryConditionDiv'>
			<table width="100%" align="center" border="0" cellspacing="0"
				cellpadding="0" style="table-layout: fixed;">
				<tr>
					<td>
						<div align="center"
							style="font-size: 16px; font-weight: bold; padding-top: 10px; padding-bottom: 10px">
							${reportDesign.reportName}
						</div>
						<div id="search">
							<form action="" name="searchFrom" id="searchFrom" method="post">
							    <!-- 隐藏域-->
								<input type="hidden" name="reportId" id="reportId"
									value="${reportDesign.reportId}" /> <input type="hidden"
									name="queryWhere" id="queryWhere" value="${queryWhere}" />
								<!-- 控件个数 -->
								<c:set var="controlsCount">0</c:set>
								<c:if test="${not empty configList}">
								   <c:forEach var="config" items="${configList}" varStatus="status">
								     <c:if test="${config.controlType eq -1 }">
								       <!-- ${config.columnLabel} -->
								       <input type="hidden" name="${config.columnCode}" id="${config.columnCode}" value="${config.defaultValue}" />
								     </c:if>
								     <c:if test="${config.controlType ne -1 }">
								        <c:set var="controlsCount">${controlsCount+1}</c:set>
								     </c:if>
								   </c:forEach>
								</c:if>
								<c:if test="${controlsCount>0}">
									<!-- <div class="" id="c2" onclick="download_new()" style="cursor:pointer; height:20px;">
								<span id="s_btn" class="up"></span>
					  		 </div> -->
									<fieldset id="searchFieldSet">
										<!-- style='display:none' -->
										<legend style='font-size: 12px;' align="left">查询条件</legend>

										<table width="100%" border="0" cellspacing="0" cellpadding="0">
											<tr>
												<td>
													<table width="100%" border="0" cellspacing="0"
														cellpadding="3"
														style="margin-top: 5px; margin-bottom: 5px">
														<c:set var="columnCount">0</c:set>
														<c:forEach var="config" items="${configList}"
															varStatus="status">
															<c:if test="${status.index==0}">
																<!-- 第一行 -->
																<tr>
															</c:if>
															<c:if test="${columnCount==3}">
																<!-- 结束上一行 -->
																</tr>
																<!-- 新的一行 -->
																<c:set var="columnCount">0</c:set>
																<tr>
															</c:if>
															<td width="12%" align='right'><b>${config.columnLabel}：</b></td>
															<td align='left'><c:choose>
																	<c:when test="${config.controlType eq 0 }">
																		<!--字符型 -->
																		<input type="text" name="${config.columnCode}"
																			id="config${status.count}"
																			value="${config.defaultValue }" style="width: 100px" />
																	</c:when>
																	<c:when test="${config.controlType eq 1 }">
																		<!-- 整数型 -->
																		<input type="text" name="${config.columnCode}"
																			id="config${status.count}"
																			value="${config.defaultValue }" style="width: 100px"
																			onkeyup="this.value=this.value.replace(/\D/g,'')"
																			onafterpaste="this.value=this.value.replace(/\D/g,'')"
																			title='只能输入整数' />
																	</c:when>
																	<c:when test="${config.controlType eq 2 }">
																		<!-- 小数型 -->
																		<input type="text" name="${config.columnCode}"
																			id="config${status.count}"
																			value="${config.defaultValue }" style="width: 100px"
																			onkeyup="if(isNaN(value))execCommand('undo')"
																			onafterpaste="if(isNaN(value))execCommand('undo')"
																			title='只能输入数字' />
																	</c:when>
																	<c:when test="${config.controlType eq 3 }">
																		<!-- 年月日 -->
																		<input class="Wdate" type="text"
																			name="${config.columnCode}"
																			id="${config.columnCode}_startTime" value="${config.defaultValue}"
																			style="width: 110px; cursor: pointer; text-align: center;"
																			readonly title='请选择'
																			onFocus="WdatePicker()" /> 
																	</c:when>
																	<c:when test="${config.controlType eq 4 }">
																		<!-- 年月 -->
																		<input class="Wdate" type="text"
																			name="${config.columnCode}"
																			id="${config.columnCode}_startTime" value="${config.defaultValue}"
																			style="width: 100px; cursor: pointer; text-align: center;"
																			onclick="setMonth(this,this);" readonly title='请选择' />		
																	</c:when>
																	<c:when test="${config.controlType eq 11 }">
																	  <!-- 年周 -->
																	  <input class="Wdate" type="text"
																			name="${config.columnCode}"
																			id="${config.columnCode}_startTime" value="${config.defaultValue}"
																			style="width: 100px; cursor: pointer; text-align: center;"
																			onclick="setWeek(this,this);" readonly title='请选择' />
																	</c:when>
																	<c:when test="${config.controlType eq 5 }">
																		<!-- 年 -->
																		<select name="${config.columnCode}"
																			id="config${status.count}" style='width: 100px;'>
																			<option value=''></option>
																		</select>
																		<script language='javascript'>
																   var config${status.count}=document.getElementById("config${status.count}");
																   var crtYear=new Date().getYear();
																   for(var i=crtYear;i>=1900;i--)
																   {
																	   config${status.count}.add(new Option(i+"年"),i);
																   }
																   //默认值
																   config${status.count}.value="${config.defaultValue}";									  
																</script>
																	</c:when>
																	<c:when test="${config.controlType eq 6 }">
																		<!-- 维度（下拉项）-->
																		<select name="${config.columnCode}"
																			id="config${status.count}" style="width: 100px"
																			field="${config.columnCode}">
																			<option value=""></option>
																			<c:forEach items="${config.dimensionDetailList}"
																				var="detail" varStatus="v">
																				<option value="${detail.key}" id="${detail.primaryKeyId}"
																					<c:if test="${detail.primaryKeyId == config.defaultValue}">selected="selected"</c:if>>${detail.value}</option>
																			</c:forEach>
																		</select>
																	</c:when>
																	<c:when test="${config.controlType eq 7 }">
																		<!-- 维度（下拉项）-->
																		<select name="config${status.count}"
																			id="config${status.count}" style="width: 100px"
																			field="${config.columnCode}" onchange="getDetailSec(this.options[this.options.selectedIndex].id,${status.count});">
																			<c:forEach items="${config.dimensionDetailList}"
																				var="detail" varStatus="v">
																				<option value="${detail.key}" id="${detail.primaryKeyId}"
																					<c:if test="${detail.primaryKeyId == config.defaultValue}">selected="selected"</c:if>>${detail.value}</option>
																			</c:forEach>
																		</select>
																		
																		<select name="${config.columnCode}"
																			id="dimensionDetailSecId${status.count}" style="width: 100px">
																			<option value=""></option>
																			<c:forEach items="${config.dimensionDetailSecList}"
																				var="detail" varStatus="v">
																				<option value="${detail.key}"
																					<c:if test="${detail.primary_id == config.defaultValueSec}">selected="selected"</c:if>>${detail.value}</option>
																			</c:forEach>
																		</select>
																	</c:when>
																	<c:otherwise>
																		<!--默认字符型 -->
																		<input type="text" name="${config.columnCode}"
																			id="config${status.count}"
																			value="${config.defaultValue }" style="width: 100px" />
																	</c:otherwise>
																</c:choose>
															</td>
															<c:if test="${status.index+1==fn:length(configList)}">
																<!-- 最后一行 -->
																</tr>
															</c:if>
															<c:set var="columnCount">${columnCount+1}</c:set>
														</c:forEach>
													</table></td>
											</tr>
											<!-- 日期控件 end-->
											<tr>
												<td>&nbsp;</td>
											</tr>
											<tr>
												<td>
													<div align="center">
														<c:if test="${not empty configList}">
															<a class="myBtn" id="search_ok" href="javascript:queryReportData()" >
																<em>查询</em></a>&nbsp;&nbsp;
															<a class="myBtn" id="search_rest" href="javascript:resetForm()">
																<em>清空</em></a>&nbsp;&nbsp;
														</c:if>
													</div></td>
										</table>
									</fieldset>
								</c:if>
							</form>
						</div></td>
				</tr>
			</table>
		</div>
		<c:if test="${fn:length(reportFieldList)!=0}">
			<fieldset id="searchFieldSet">
				<legend style='font-size: 12px;' align="left">图表展现</legend>
				<div
					style='float: left; height: 0px; border: 0px solid; width: 100%;'
					id='chartContainerDiv'>
					<c:forEach items="${reportFieldList}" var="reportField"
						varStatus="vel">
						<!-- float:left; -->
						<div id="container${vel.index+1}"
							style="position: absolute;left:0px;top:0px;;border:1px dotted  #CCCCCC;width:${reportField.width}px;height:${reportField.height}px;"></div>
					</c:forEach>
				</div>
			</fieldset>

			<script type="text/javascript">
		//自定义图表的top值
		var queryDivHeight = document.getElementById("queryConditionDiv").offsetHeight;
		var queryDivLeft = document.documentElement.clientWidth/2 - 400;
		 <c:forEach items="${reportFieldList}" var="reportField"
				varStatus="vel">
		 document.getElementById("container${vel.index+1}").style.top=(queryDivHeight+${reportField.top})+"px";
		 document.getElementById("container${vel.index+1}").style.left=${reportField.left}+queryDivLeft+"px";
		</c:forEach>
		
	    //查询条件
	    getQueryParameters()
<c:forEach items="${reportFieldList}" var="reportField" varStatus="vel">
var option${vel.index+1};
<c:choose>
<c:when test="${reportField.chartType eq '1'}">
//pie 
option${vel.index+1} ={
		chart: {
			renderTo: 'container${vel.index+1}',
			width:${reportField.width},
			height:${reportField.height}
		//	marginLeft: 20,
		//	defaultSeriesType: 'column',
		//	backgroundColor: '#FCFFC5'
		//	marginRight: 130,
		//	marginBottom: 25
		},
		title: {
			text: '',
			x: -20,//center
			style: {
                color: 'black',
                fontSize: '15px'
            }
		},
		subtitle: {
			text: '',
			x: -20
		},
		tooltip: {
				formatter: function() {
					 return '<b>'+ this.point.name +'</b>: '+ this.y;
					}
		},
		credits:{
			enabled:false
	},
	  plotOptions: {
		  pie: {
					allowPointSelect: true,
					cursor: 'pointer',
					 dataLabels: {
	                  enabled: true,
	                  distance: 20,//距离
	                  cursor: 'pointer',
	                  formatter: function() {
	                	  return '<b>'+ this.point.name +'</b>: '+Highcharts.numberFormat(this.percentage, 2)  +' %';
	                  }
	              },
				//	showInLegend: true,
			        size: 120
				},
				series: {
	              cursor: 'pointer',
	              point: {
	                  events: {
	                      click: function() {
	                       //   location.href ="Http://www.baidu.com";
	                      }
	                  }
	              }
	          }
	  },
	  series: [{
				type: 'pie',
				name: 'Browser share',
				data: []
		      }
			]
	};
</c:when>
<c:when test="${reportField.chartType eq '2'}">
	// 趋势图
	option${vel.index+1} ={
		chart: {
		    renderTo: 'container${vel.index+1}',
		    width: ${reportField.width},
			height: ${reportField.height},
			zoomType: 'x',
			style: {
				fontFamily: 'Microsoft Yahei,Arial,sans-serif', // default font
				fontSize: '12px'
			}
		},
		rangeSelector: {
		    selected: 6,	//初始化zoom范围
		    inputDateFormat: '%Y年%m月%d日', //输入框格式
		    inputEditDateFormat : '%Y年%m月%d日',
		    buttonTheme: {
		    	width: 30
		    },
		    buttons: [
						{
							type: 'day',
							count: 7,
							text: '7天'
						},
						{
							type: 'month',
							count: 1,
							text: '30天'
						}, {
							type: 'month',
							count: 3,
							text: '90天'
						}, {
							type: 'month',
							count: 6,
							text: '半年'
						}, {
							type: 'ytd',
							text: '今年'
						}, {
							type: 'year',
							count: 1,
							text: '1年'
						}, {
							type: 'all',
							text: '全部'
						}
		              ]
		},
		scrollbar: {
			barBackgroundColor: 'silver',
			barBorderRadius: 7,
			barBorderWidth: 0,
			buttonBackgroundColor: 'silver',
			buttonBorderWidth: 0,
			buttonBorderRadius: 7,
			trackBackgroundColor: 'none',
			trackBorderWidth: 1,
			trackBorderRadius: 8,
			trackBorderColor: '#CCC'
	    },
	    xAxis: {
	    	type:"datatime",
           	tickPixelInterval: 200,//x轴上的间隔 
            minorTickWidth : 200,
	       dateTimeLabelFormats:{
	        	second: '%H点%M分%S秒',
	        	minute: '%H点%M分',
	        	hour: '%H点',
	        	day: '%Y年%m月%d日',
	        	week: '%Y年%m月%d日',
	        	month: '%Y年%m月%d日',
	        	year: '年'
	        }
	            
        },
        legend: {
        	enabled: true,
			layout: 'horizontal',
			align: 'right',
			verticalAlign: 'top',
			floating:true,
			x: 0,
			y: 25,
			borderWidth: 0
		},
		tooltip: {
			xDateFormat: '%Y年%m月%d日, %A',//鼠标移动到趋势线上时显示的日期格式  
			pointFormat: '<span style="color:{series.color}">{series.name}</span>:<b>{point.y}</b><br/>'
		},
		// 网址显示
		credits:{
			enabled:false
		},
		series: []
	}
</c:when>
<c:otherwise>
//line/column/
option${vel.index+1} ={
		chart: {
			renderTo: 'container${vel.index+1}',
			width:${reportField.width},
			height:${reportField.height},
			zoomType:'x'
		//	defaultSeriesType: 'column',
		//	backgroundColor: '#FCFFC5',
			//marginRight: 130,
			//marginBottom: 25
		},
		title: {
			text: '',
			x: -20 //center
		},
		subtitle: {
			text: '',
			x: -20
		},
		xAxis: {
			categories: []
		},
		yAxis: {
			title: {
				text: ''
			}/*,
		 	plotLines: [{
				value: 12.5,
				width: 1,
				color: 'red'
			}] */
		},
		tooltip: {
				formatter: function() {
						return this.x+':'+this.y;
					}
		},
		legend: {
			layout: 'horizontal',
			align: 'right',
			verticalAlign: 'top',
			floating:true,
			x: 0,
			y: 0,
			borderWidth: 0
		},
		credits:{
			enabled:false
	},
	  plotOptions: {
         series: {
             dataLabels: {
                 enabled: true,
                 formatter: function() {
                     return this.y;
                 }
             }
         }
     },
		series:[]
	};
</c:otherwise>
</c:choose>
</c:forEach>
queryChartData();

	//设置图表容器的高度
	$(document).ready(function(){
		var heigth=0;
		var chartCollectionDiv=document.getElementById("chartContainerDiv");
		 var queryDivHeight=document.getElementById("queryConditionDiv").offsetHeight;
		var chartDiv=chartCollectionDiv.childNodes;
		for(var i=0;i<chartDiv.length;i++)
		{
			if(heigth<chartDiv[i].offsetTop+chartDiv[i].offsetHeight)
			{
				heigth=chartDiv[i].offsetTop+chartDiv[i].offsetHeight;	
			}
		}
		document.getElementById("chartContainerDiv").style.height=(heigth-queryDivHeight)+"px";
	});

</script>
		</c:if>
		<iframe id="datalist" src=""
			style="width: 100%;border: none;" scrolling="no" frameborder="0"></iframe>
		<textarea name="testResult" rows="15" cols="4"
			style='width: 99%; height: 200px; display: none;' id='testResult'
			readonly></textarea>
	</div>
</body>
</html>