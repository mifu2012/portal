<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>表格数据</title>
<link href="<%=basePath%>common/js/jqgrid/css/jquery-ui-1.8.21.custom.css"
	media="screen" type="text/css" rel="stylesheet" />
<style type="text/css">
/* 修改grid pager以上部分的字体大小 */
.ui-jqgrid .ui-jqgrid-view {
	font-size: 14px;
}
/* 修改grid pager部分的字体大小 */
.ui-jqgrid .ui-pager-control {
	font-size: 14px;
} 
/* 隔行换色样式 */
.myAltRowClass {
	background-color: #E6E8FA;
	background-image: none;
	border: 1px solid #a6c9e2;
}
/* 链接样式 */
/* .ui-widget-content a {
    text-decoration: none;
	color: #0000FF;
} */
</style>
<link href="<%=basePath%>common/js/jqgrid/css/ui.jqgrid.css" media="screen"
	type="text/css" rel="stylesheet" />
<!-- 用于IE8之前的版本，处理JSON数据 -->
<script type="text/javascript" src="<%=basePath%>common//js/json2.js"></script>

<script src="<%=basePath%>common/js/jquery-1.5.1.min.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>common/js/jquery.PrintArea.js"></script>
<script type="text/javascript"
	src="<%=basePath%>common/js/jqgrid/i18n/grid.locale-cn.js"></script>
<c:if test="${reportDefine.grouping == 1}">
	<script type="text/javascript" src="<%=basePath%>common/js/jqgrid/js/jquery.jqGrid.src.js"></script>
</c:if>
<c:if test="${reportDefine.grouping == 0}">
	<script type="text/javascript" src="<%=basePath%>common/js/jqgrid/js/jquery.jqGrid.min.js"></script>
</c:if>
<script type="text/javascript" src="<%=basePath%>common/js/jqgrid/js/jquery.jqGrid.groupHeader-0.2.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/jqgrid/js/tui.tablespan.js"></script>
<!-- 上面是GRID控件相关的JS和CSS -->
<script type="text/javascript"
	src="<%=basePath%>common/js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>
<style type="text/css" media="print">
#accordion h3,#vcol,div.loading,div.ui-tabs-hide,ul.ui-tabs-nav li,td.HeaderRight
	{
	display: none
}

.ui-jqgrid-titlebar,.ui-jqgrid-title {
	display: none
}

.ui-jqgrid-bdiv_self {
	position: relative;
	margin: 0em;
	padding: 0;
	text-align: left;
}

#pager {
	display: none;
	z-index: -1;
}
</style>
<style>
@media screen {
	p.test {
		font-family: verdana, sans-serif;
		font-size: 14px
	}
}

@media print {
	p.test {
		font-family: times, serif;
		font-size: 10px
	}
}

@media screen , print {
	p.test {
		font-weight: bold
	}
}
</style>
</head>

<script type="text/javascript">
	$.jgrid.no_legacy_api = true;
	$.jgrid.useJSON = true; 
	var isLoad=false;
	var isOpen = false;//列表打开状态
	var lockFlag = 1;
	//查询参数
	var urlParam="1=1";
	var dataFieldFormat = new Array();
	<c:forEach items="${reportCellList}" var="reportCell" varStatus="status">
		<c:if test="${reportCell.dataType==1}">
			dataFieldFormat['${reportCell.dataField}'] = {
					formatter : '${reportCell.formatter}',
					scale : ${reportCell.scale},
					commas : ${reportCell.commas},
					currency : ${reportCell.currency},
					warningValue : '${reportCell.warningValue}'
					};
		</c:if>
	</c:forEach>
	
 jQuery(document).ready(function(){
<c:if test="${not empty reportCellList}">
	 //取控件默认参数值
	var allInputObj = window.parent.document.forms[0].elements;
	if(allInputObj!=null&&allInputObj.length>0)
	{
		for(var i=0;i<allInputObj.length;i++)
		{
			if(allInputObj[i]==null) continue;
			//alert(allInputObj[i].nodeName);
			if(allInputObj[i].nodeName.toUpperCase()=="INPUT"||allInputObj[i].nodeName.toUpperCase()=="SELECT")
			{
				if(allInputObj[i].type.toUpperCase()=="BUTTON") continue;
			    urlParam+="&"+allInputObj[i].name+"="+allInputObj[i].value;
			}
		}
	}
	var newUrl="<%=basePath%>/NewReport/loadReportDataByPaging.html?reportId=${reportDesign.reportId}&"+encodeURI(urlParam,'utf-8');
	jQuery("#grid_datalist").jqGrid({
		page : 1, 
		url : newUrl, 
		datatype : "json",
		//<c:if test="${reportDefine.dynamicColNum > 0}">loadonce: true,</c:if>
		//表头
	   colNames: [ 
	              	<c:forEach items="${reportCellList}" var="reportCell" varStatus="status">
	              		//如果没有子表头 ：${reportCell.content}
	              		<c:if test="${reportCell.firstChildCol==null || reportCell.firstChildCol==''}">
	              			<c:if test="${status.index!=0}">,</c:if>
	     	    			'${reportCell.content}'
	     	     		</c:if>
	     	     	</c:forEach>
	     	     	<c:forEach items="${reportDefine.groupFieldList}" var="groupField" varStatus="status">
	     	     		<c:if test="${groupField.isDataColumn == 0}">,'分组${status.index+1}'</c:if>
	     	     	</c:forEach>
	            ],
	   //样式 //
	   colModel: [
		  <c:forEach items="${reportCellList}" var="reportCell" varStatus="status">
			<c:if test="${reportCell.dataType==1}">
			     //数据列
				 <c:if test="${status.index!=0}">,</c:if>
				 {name: '${reportCell.dataField}' , index: '${reportCell.dataField}' ,align:"${reportCell.align}",
					 <c:if test="${reportCell.link == null}">
						 <c:if test="${reportCell.formatter=='date'}">formatter:'date', formatoptions: {newformat: 'Y-m-d'},</c:if>
						 sorttype: '${reportCell.formatter}',
					 </c:if>
					 width :${reportCell.width}
					 <c:if test="${reportCell.groupSum==1}">,summaryType:
					 	<c:choose>
					 		<c:when test="${reportCell.groupSummaryType == 'sum'}">groupSummarySum</c:when>
					 		<c:when test="${reportCell.groupSummaryType == 'avg'}">groupSummaryAvg</c:when>
					 		<c:when test="${reportCell.groupSummaryType == 'min'}">groupSummaryMin</c:when>
					 		<c:when test="${reportCell.groupSummaryType == 'max'}">groupSummaryMax</c:when>
					 		<c:when test="${reportCell.groupSummaryType == 'count'}">'count'</c:when>
					 	</c:choose>,
					 </c:if>
					 <c:if test="${reportCell.groupSummaryType=='sum'}">summaryTpl:'<b>小计: {0}</b>'</c:if>
					 <c:if test="${reportCell.groupSummaryType=='count'}">summaryTpl:'<b>总个数: {0}</b>'</c:if>
					 <c:if test="${reportCell.groupSummaryType=='avg'}">summaryTpl:'<b>平均值: {0}</b>'</c:if>
					 <c:if test="${reportCell.groupSummaryType=='min'}">summaryTpl:'<b>最小值: {0}</b>'</c:if>
					 <c:if test="${reportCell.groupSummaryType=='max'}">summaryTpl:'<b>最大值: {0}</b>'</c:if>
					 <c:if test="${status.index<reportDefine.lockColumnCount}">,frozen : true</c:if>
					 <c:if test="${reportCell.dynamicHeaderFieldList != null}">,sortable:false</c:if>}
			</c:if>
		   </c:forEach>
		   <c:forEach items="${reportDefine.groupFieldList}" var="groupField" varStatus="status">
     	   <c:if test="${groupField.isDataColumn == 0}">,{name: '${groupField.fieldName}', id:'${groupField.fieldName}'}</c:if>
     	   </c:forEach>
	    ],
		<c:if test="${reportDefine.pageSize<=0}">
			rowNum:　100,//每页显示记录数 
		</c:if>
		<c:if test="${reportDefine.pageSize>0}">
			rowNum:${reportDefine.pageSize},//每页显示记录数
	　　　	</c:if>
	　　　	height: 330,
	   	rowList:[10,20,30,50,100,200,300,500],//可调整每页显示的记录数
	    jsonReader:{
	    	totalpages: "total",    
	    	currpage: "page",   
	    	totalrecords: "records",
            repeatitems : false
    	}, 
	    viewrecords: true,//显示总的记录数
		//是否显示行号
		<c:if test="${reportDefine.showRowNum==1}">
	   	　　rownumbers:true,//是否显示序号 ,分组后无效
　　　　</c:if>
	　　shrinkToFit: false, //是否自适应宽度
		//是否本页合计
		<c:if test="${reportDefine.summary==1}">
		   footerrow:true,//底部合计
　　　　 </c:if>
		<c:set var="columnCount">0</c:set>
		   	//加载完成调用数据
		   	gridComplete: function () {
		   		// 自适应高度
		    	if ($("#grid_datalist").css("height").replace("px", "") < 330) {
					$(".ui-jqgrid-bdiv").css("height", "auto");
		    	} else {
					$(".ui-jqgrid-bdiv").css("height", "330px");
				}
			   //本页统计
			   if(${reportDefine.summary}==1 && jQuery(this).getGridParam('reccount')!=0){
				   var sumData={
				   		<c:forEach items="${reportCellList}" var="reportCell" varStatus="status">
							<c:if test="${reportCell.pageSum==1}">
								<c:set var="columnCount">${columnCount+1}</c:set>
								<c:if test="${columnCount!=1}">,</c:if>	    
								//本页合计
								<c:if test="${reportCell.pageSummaryType=='sum'}">'${reportCell.dataField}':"合计：" + getPageSummary('${reportCell.dataField}', 'sum')</c:if>
								<c:if test="${reportCell.pageSummaryType=='count'}">'${reportCell.dataField}':"总个数："+ jQuery(this).getGridParam('reccount')</c:if>
								<c:if test="${reportCell.pageSummaryType=='avg'}">'${reportCell.dataField}':"平均值：" + getPageSummary('${reportCell.dataField}', 'avg')</c:if>
								<c:if test="${reportCell.pageSummaryType=='min'}">'${reportCell.dataField}':"最小值：" + getPageSummary('${reportCell.dataField}', 'min')</c:if>
								<c:if test="${reportCell.pageSummaryType=='max'}">'${reportCell.dataField}':"最大值："+ getPageSummary('${reportCell.dataField}', 'max')</c:if>
							</c:if>
						 </c:forEach>};
					jQuery("#grid_datalist").footerData("set",sumData,false);
			   }else if(${reportDefine.summary}==1 && jQuery(this).getGridParam('reccount')==0){
				   <c:set var="columnCount">0</c:set>
				   var sumData={
					   		<c:forEach items="${reportCellList}" var="reportCell" varStatus="status">
								<c:if test="${reportCell.pageSum==1}">
								<c:set var="columnCount">${columnCount+1}</c:set>
							<c:if test="${columnCount!=1}">,</c:if>	    
								//本页合计
								<c:if test="${reportCell.pageSummaryType=='sum'}">'${reportCell.dataField}':""</c:if>
								<c:if test="${reportCell.pageSummaryType=='count'}">'${reportCell.dataField}':""</c:if>
								<c:if test="${reportCell.pageSummaryType=='avg'}">'${reportCell.dataField}':""</c:if>
								<c:if test="${reportCell.pageSummaryType=='min'}">'${reportCell.dataField}':""</c:if>
								<c:if test="${reportCell.pageSummaryType=='max'}">'${reportCell.dataField}':""</c:if>
								</c:if>
							 </c:forEach>};
						jQuery("#grid_datalist").footerData("set",sumData,false);
			   }
			   //合并数据
			   if (${reportDefine.grouping}!=1 && ${reportDefine.mergeColumnCount}>0){
			        if(${reportDefine.showRowNum}==1){
			        	for(var i=${reportDefine.mergeColumnCount};i>0;i--){
							$("#grid_datalist").tuiTableRowSpan(i);
						}
					}else if(${reportDefine.showRowNum}==0){
						for(var i=${reportDefine.mergeColumnCount}-1;i>=0;i--){
							$("#grid_datalist").tuiTableRowSpan(i);
						}
					}
			   }
				//隔行换色 
				<c:if test="${reportDefine.altRows==1}">
					$("tr.jqgrow:odd").removeClass('ui-widget-content');
					$("tr.jqgrow:odd").addClass('myAltRowClass');
		　　　　	</c:if>
		　　　　	//修改表格的按钮图表
		    	$("#first_pager span").css({"background-image": "url('<%=basePath%>common/js/jqgrid/css/images/page_first.gif')", "background-position": "0 0"});
		　　　　	$("#prev_pager span").css({"background-image": "url('<%=basePath%>common/js/jqgrid/css/images/page_prev.gif')", "background-position": "0 0"});
		　　　　	$("#next_pager span").css({"background-image": "url('<%=basePath%>common/js/jqgrid/css/images/page_next.gif')", "background-position": "0 0"});
		　　　　	$("#last_pager span").css({"background-image": "url('<%=basePath%>common/js/jqgrid/css/images/page_last.gif')", "background-position": "0 0"});
		    	$("#refresh_grid_datalist span").css({"background-image": "url('<%=basePath%>common/js/jqgrid/css/images/tool_reload.gif')", "background-position": "0 0"});
		　　　　	$("#buttonFilter span").css({"background-image": "url('<%=basePath%>common/js/jqgrid/css/images/tool_filter.gif')", "background-position": "0 0"});
		　　　　	$("#buttonExport span").css({"background-image": "url('<%=basePath%>common/js/jqgrid/css/images/tool_xls.gif')", "background-position": "0 0"});
		　　　　	$("#buttonPrint span").css({"background-image": "url('<%=basePath%>common/js/jqgrid/css/images/tool_print.gif')", "background-position": "0 0"});
		　　　　	//解决锁定列，显示行号，多重表头在一起时的BUG
		　　　　	if (${reportDefine.lockColumnCount} > 0 && lockFlag == 1 && ${reportDefine.showRowNum} == 1 && ${fn:length(secondLevelHeaderList)} >= 1) {
		　　　　		var thirdRow = 0;
		　　　　		<c:forEach items="${reportCellList}" var="reportCell" varStatus="status">
		    			<c:if test="${status.index < reportDefine.lockColumnCount && reportCell.rowSpan == 1}">
		    				thirdRow++;
		    			</c:if>
		    		</c:forEach>
		    		if (thirdRow == 0) {
		    			$("div[class^='frozen-div']:first tr[class$='jqg-third-row-header'] th").remove();
					} else {
						$("div[class^='frozen-div']:first tr[class$='jqg-third-row-header'] th:gt("+(thirdRow-1)+")").remove();
					}
					lockFlag = 0;//只进行一次
				}
		　　　　	// 解决锁定列与三级表头引起的BUG
		　　　　	if (${reportDefine.lockColumnCount} > 0 && ${fn:length(thirdLevelHeaderList)} >= 1 && $("div[class^='frozen-div'] tr[class$='jqg-four-row-header'] th").length > 0) {
		　　　　		var fourthCount = 0;
		　　　　		var fourthLock = -1 + ${reportDefine.showRowNum};
		　　　　		var thirdLock = ${reportDefine.lockColumnCount} - 1;
		　　　　		var secondLock = ${reportDefine.lockColumnCount};
		　　　　		<c:forEach items="${firstRowList}" var="fourthLevel" varStatus="status">
		　　　　			if (fourthCount < ${reportDefine.lockColumnCount}) {
		　　　　				fourthCount += ${fourthLevel.colSpan};
		　　　　				fourthLock++;
		　　　　				if (${fourthLevel.rowSpan} == 3) {
								thirdLock--;
							}
		　　　　				if (${fourthLevel.rowSpan} >= 2) {
		　　　　					secondLock -= ${fourthLevel.colSpan};
							}
						}
		　　　　		</c:forEach>
		　　　　		// 移除header中无用的列
		　　　　		$("div[class^='frozen-div'] tr[class$='jqg-four-row-header'] th:gt("+fourthLock+")").remove();
		　　　　		$("div[class^='frozen-div'] tr[class$='jqg-third-row-header'] th:gt("+thirdLock+")").remove();
		　　　　		if (secondLock == 0) {
		　　　　			$("div[class^='frozen-div'] tr[class$='jqg-second-row-header'] th").remove();
					} else {
		　　　　			$("div[class^='frozen-div'] tr[class$='jqg-second-row-header'] th:gt("+(--secondLock)+")").remove();
					}
		　　　　		// 根据锁定div设置锁定表格高度
		　　　　		$("div[class^='frozen-div'] table").css("height",$("div[class^='frozen-div']").css("height"));
		　　　　	}
		　　　　	
		　　　　	//自适应
				var height = document.getElementById("printArea").offsetHeight + document.getElementById("printArea").offsetTop+5;
				window.parent.changeIframeSize(height);
				
				// 处理loading
				window.parent.manageLoading();
		     },
		     onPaging: function(pgButton) {
		    	 var pageNo=$("#grid_datalist").getGridParam("page");
		  		 var rowNum=$("#grid_datalist").getGridParam("rowNum");
		    	 window.parent.reportChartFollowGrid(pageNo, rowNum);//图表数据跟着变化
             },
             //调整列大小后重新加载,防止锁定列的BUG
             resizeStop: function () {
            	 $("#grid_datalist").trigger("reloadGrid");
             },
             //点击显示隐藏表格后的自适应
             onHeaderClick: function() {
            	 /* if(isOpen == false) {
            		 document.getElementById("bodyDiv").style.height=(document.body.scrollHeight + 10)+"px";
            		 isOpen = true;
            	 } else {
            		 document.getElementById("bodyDiv").style.height="auto";
            		 isOpen = false;
            	 } */
            	//自适应
 				var height = document.getElementById("printArea").offsetHeight + document.getElementById("printArea").offsetTop+5;
 				window.parent.changeIframeSize(height);
             },
             // 清空分组统计数据
             beforeProcessing: function() {
            	 avgSum = [];
            	 avgCount = [];
             },

	    //pager: '#pghcs',
	    pager: 'pager', //分页工具栏 
	    scroll:false,
	    sortorder: "desc",
		//分组显示
		<c:if test="${reportDefine.grouping==1}">
			grouping:true,
			groupingView : 
			{ 
			   	groupField : [<c:forEach items="${reportDefine.groupFieldList}" var="groupField" varStatus="vs"><c:if test="${vs.index!=0}">,</c:if>'${groupField.fieldName}'</c:forEach>], //要分组的字段
			   	groupColumnShow : [
			   	                   	<c:forEach items="${reportDefine.groupFieldList}" var="groupField" varStatus="vs">
			   	                   	<c:if test="${vs.index!=0}">,</c:if>
			   	                 	<c:if test="${groupField.isDataColumn==0}">false</c:if>
			   	                 	<c:if test="${groupField.isDataColumn==1}">true</c:if>
			   	                 	</c:forEach>
			   	                 	], //分组的字段是否显示
			   	//判断是否进行分组统计
			   	<c:set var="isGroupSummary">0</c:set>
			   	<c:forEach items="${reportCellList}" var="reportCell" varStatus="status">
			   		<c:if test="${reportCell.dataType == 1 && reportCell.groupSummaryType != ''}">
			   			groupSummary : [true],
			   			<c:set var="isGroupSummary">1</c:set>
			   		</c:if>
			   	</c:forEach>
			   	//${isGroupSummary}
			   	<c:if test="${isGroupSummary == 0}">
			   		groupSummary : [false],
			   	</c:if>
			   groupText : ['<b>分组{0} - {1} 条记录</b>'] 
			}, 
　　　　	</c:if>
		gridview : true,
		caption: "${reportDefine.reportName}",
		width: document.body.clientWidth-5//780//document.body.clientWidth-10,//宽度 ,为适应报表系统前台显示，高度固定为800px
	});
	//二级表头合并
	<c:if test="${fn:length(secondLevelHeaderList)>=1}">
		jQuery("#grid_datalist").jqGrid('setGroupHeaders', {
		  useColSpanStyle: true, //二级表头合并
		  groupHeaders:[
			  <c:forEach items="${secondLevelHeaderList}" var="reportCell" varStatus="status">
				  <c:if test="${status.index!=0}">,</c:if>
				  {startColumnName: '${reportCell.firstChildCol}', numberOfColumns: ${reportCell.colSpan}, titleText: '${reportCell.content}'}
			  </c:forEach>
		  ]	
		});
	</c:if>
	//三级表头合并
	<c:if test="${fn:length(thirdLevelHeaderList)>=1}">
		jQuery("#grid_datalist").jqGrid("setComplexGroupHeaders",{
		 complexGroupHeaders:[
		                      <c:forEach items="${thirdLevelHeaderList}" var="reportCell" varStatus="status">
								<c:if test="${status.index!=0}">,</c:if>
								{startColumnName: '${reportCell.firstChildCol}', numberOfColumns: ${reportCell.colSpan}, titleText: '${reportCell.content}'}
							  </c:forEach>
		 ]
		});
	</c:if>
	//锁定列
	<c:if test="${reportDefine.grouping!=1}">
		<c:if test="${reportDefine.lockColumnCount>0}">
			jQuery("#grid_datalist").jqGrid('setFrozenColumns');
			//jQuery("#grid_datalist")[0].p._complete.call(jQuery("#grid_datalist")[0]);
		</c:if>
	</c:if>
 	 //定义工具条

 	jQuery("#grid_datalist").navGrid('#pager',{edit:false, add:false,del:false,search:false,view:false})
 					/* .navButtonAdd('#pager',{caption:"",buttonicon:"ui-icon-print",title:"打印",
 											id: "buttonPrint",
 											onClickButton:function(){
 												$("#printArea").printArea();
 												//window.focus();
 												//window.print();
 												//return false;
 												}}) */
 					.navButtonAdd('#pager',{caption:"",buttonicon:"ui-icon-arrowthickstop-1-s",title:"导出excel",
 											id: "buttonExport",
 											onClickButton:function(){ 
						                        //导出
 												location.href='<%=basePath%>/NewReport/exportReport.html?reportId=${reportDesign.reportId}&'+encodeURI(urlParam,'utf-8');}})
 												//exportReport\exportAllList
 	 				.navButtonAdd('#pager',{
 	 					caption: "",
 	                    buttonicon: "ui-icon-calculator",
 	                    //buttonicon: "filter",
 	                    title: "栏目显示配置",
 	                    id: "buttonFilter",
 	                    onClickButton: function(){
 	                    	columnChooser();
 	                    }});
</c:if>
});
    //栏目显示配置
    function columnChooser(){
    	var event = getEvent();
		if(document.getElementById("reportColumnList").value == ''){//第一次获取栏目属性
			var columnArray = new Array();
			var column = new reportColumn();
			var lock = ${reportDefine.lockColumnCount};
			if (${reportDefine.dynamicColNum} > 0) {
				<c:forEach items="${reportCellList}" var="reportCell" varStatus="status">
          			column = new reportColumn();
					column.id = "${reportCell.dataField}";
					<c:if test="${reportCell.dynamicHeaderFieldList == null}">
						column.pId = "${reportCell.pId}";</c:if>
					<c:if test="${reportCell.dynamicHeaderFieldList != null}">
						column.pId = "${reportCell.pDataField}";</c:if>
					column.isShow = true;//初始状态为显示
					column.name = "${reportCell.content}";//显示名
					column.dataField = "${reportCell.dataField}";//绑定字段
					column.isChanged = 0;//初始值，未改变
					if(${status.index} < lock){//判断是否为锁定列
						column.frozen = 1;
						lock -= ${reportCell.colSpan} - 1;
					}
					columnArray[columnArray.length] = column;//保存该列属性到数组
 	     		</c:forEach>
 	     		<c:forEach items="${secondLevelHeaderList}" var="reportCell" varStatus="status">
	      			column = new reportColumn();
					column.id = "${reportCell.content}";
					column.pId = "${reportCell.pId}";
					column.isShow = true;//初始状态为显示
					column.name = "${reportCell.content}";//显示名
					column.dataField = "${reportCell.dataField}";//绑定字段
					column.isChanged = 0;//初始值，未改变
					/* if(${status.index} < lock){//判断是否为锁定列
						column.frozen = 1;
						lock -= ${reportCell.colSpan} - 1;
					} */
					columnArray[columnArray.length] = column;//保存该列属性到数组
	     		</c:forEach>
			} else {
				<c:forEach items="${reportDefine.reportCellList}" var="reportCell" varStatus="status">//获取列属性
					column = new reportColumn();
					column.id = "${reportCell.id}";
					column.pId = "${reportCell.pId}";
					column.isShow = true;//初始状态为显示
					column.name = "${reportCell.content}";//显示名
					column.dataField = "${reportCell.dataField}";//绑定字段
					column.isChanged = 0;//初始值，未改变
					if(${status.index} < lock){//判断是否为锁定列
						column.frozen = 1;
						lock -= ${reportCell.colSpan} - 1;
					}
					columnArray[columnArray.length] = column;//保存该列属性到数组
				</c:forEach>
			}
			var reportColumnList = JSON.stringify(columnArray);
		}else{
			var reportColumnList = document.getElementById("reportColumnList").value;
		}
		document.getElementById("reportColumnList").value = reportColumnList;
		//获取鼠标位置
		if(event.pageX || event.pageY) { //支持pageX、Y属性的浏览器(包括FF)
			var x = event.pageX;
			var y = event.pageY;  
		} else if (document.documentElement) { // IE浏览器
			var x = document.documentElement.scrollLeft + event.clientX; 
			var y = document.documentElement.scrollTop + event.clientY;
		}
		window.parent.columnChooserDialog(x, y);
    }
  	//栏目配置定义
  	/**
	  id  ID
	  pId 父级ID
	  isShow 是否显示
	  name 显示名
	  dataField 绑定字段
	  frozen 是否锁定
	  isChanged 是否改变
	 */
	function reportColumn(id, pId, isShow, name, dataField, frozen, isChanged) {
		this.id = id;
		this.pId = pId;
		this.isShow = isShow;
		this.name = name;
		this.dataField = dataField;
		this.frozen = frozen;
		this.isChanged = isChanged;
  	}
  	//显示隐藏列
  	function hideShowColumns(){
  		var columnArray = eval(document.getElementById("reportColumnList").value);
  		var isChanged = 0;
  		var hideCols = new Array();	//隐藏列数组
		var showCols = new Array();	//显示列数组
  		for(var i = 0; i < columnArray.length; i++){
  			if (columnArray[i].isChanged == 1 && columnArray[i].dataField != "") {
  				if(columnArray[i].isShow == false){
  					hideCols[hideCols.length] = columnArray[i].dataField;
  	  			}else{
  	  				showCols[showCols.length] = columnArray[i].dataField;
  	  			}
  				isChanged = 1;
  				columnArray[i].isChanged = 0;
			}
  		}
  		jQuery("#grid_datalist").hideCol(hideCols);
		jQuery("#grid_datalist").showCol(showCols);
  		//是否改变
  		if (isChanged == 1) {
  			// 取消锁定列
  	  		<c:if test="${reportDefine.lockColumnCount>0}">
  	  			jQuery("#grid_datalist").jqGrid('destroyFrozenColumns');
  	  		</c:if>
  			// 对isChanged清空
  			var reportColumnList = JSON.stringify(columnArray);
			document.getElementById("reportColumnList").value = reportColumnList;
			// 三级表头合并
  			<c:if test="${fn:length(thirdLevelHeaderList)>=1}">
	  			setThirdLevelHeader();
  			</c:if>
  			// 重新开启锁定列
  			<c:if test="${reportDefine.lockColumnCount>0}">
  				jQuery("#grid_datalist").jqGrid('setFrozenColumns');
	  			jQuery("#grid_datalist").trigger("reloadGrid");
	  		</c:if>
		}
  	}
  	//自定义显示
  	function customDisplay(dataField, checked) {
  		if (checked == false) {
  			$("#grid_datalist").hideCol(dataField);
		} else {
			$("#grid_datalist").showCol(dataField);
		}
  		// 取消锁定列
	  	<c:if test="${reportDefine.lockColumnCount>0}">
	  		jQuery("#grid_datalist").jqGrid('destroyFrozenColumns');
	  	</c:if>
  		setThirdLevelHeader();
  		// 重新开启锁定列
		<c:if test="${reportDefine.lockColumnCount>0}">
			jQuery("#grid_datalist").jqGrid('setFrozenColumns');
  			jQuery("#grid_datalist").trigger("reloadGrid");
  		</c:if>
  	}
  	//设置三级表头
  	function setThirdLevelHeader() {
  		$("#grid_datalist").jqGrid("setComplexGroupHeaders",{
 			 complexGroupHeaders:[
 			                      <c:forEach items="${thirdLevelHeaderList}" var="reportCell" varStatus="status">
 									<c:if test="${status.index!=0}">,</c:if>
 									{startColumnName: '${reportCell.firstChildCol}', numberOfColumns: ${reportCell.colSpan}, titleText: '${reportCell.content}'}
 								  </c:forEach>
 			 ]
 			});
  	}
    //同时兼容ie和ff的写法
    function getEvent() 
    { 
        if(document.all)  return window.event;   
        func=getEvent.caller;       
        while(func!=null){ 
            var arg0=func.arguments[0];
            if(arg0)
            {
              if((arg0.constructor==Event || arg0.constructor ==MouseEvent) || (typeof(arg0)=="object" && arg0.preventDefault && arg0.stopPropagation))
              { 
              return arg0;
              }
            }
            func=func.caller;
        }
        return null;
    }
    
    /*小计*/
    // 分组求和
     function groupSummarySum(val, name, record){
    	var tempString = parseFloat(replaceValue(val)||0)
	    		+ parseFloat(replaceValue(record[name])||0);
    	tempString = formatter(name, tempString);
    	return tempString;
    }
	// 分组求平均值
	var avgSum = new Array();
	var avgCount = new Array();
	<c:forEach items="${reportDefine.groupFieldList}" var="groupField" varStatus="vs">var groupField = '${groupField.fieldName}'</c:forEach>
	function groupSummaryAvg(val, name, record){
		if (record[groupField] == null) {
			record[groupField] = "null";
		}
		var avgName = replaceValue(record[groupField]) + name;
		if (avgCount[avgName] != null) {
			avgCount[avgName] ++;
		} else {
			avgCount[avgName] = 1;
			avgSum[avgName] = 0;
		}
		avgSum[avgName] += parseFloat(replaceValue(record[name])||0);
		var tempValue = avgSum[avgName] / avgCount[avgName];
		tempValue = formatter(name, tempValue);
		return tempValue;
    }
	// 分组求最大值
	function groupSummaryMax(val, name, record){
		var tempString = Math.max(parseFloat(replaceValue(val)||0)
	    		,parseFloat(replaceValue(record[name])||0));
		tempString = formatter(name, tempString);
    	return tempString;
    }
	// 分组求最小值
	function groupSummaryMin(val, name, record){
		var tempString;
		if (val == "") {
			tempString = parseFloat(replaceValue(record[name])||0);
		} else {
			tempString = Math.min(parseFloat(replaceValue(val)||0)
		    		,parseFloat(replaceValue(record[name])||0));
		}
		tempString = formatter(name, tempString);
    	return tempString;
    }
	
    
    /**
    	页面合计
    	dataField 数据列字段
    	type 类型		sum,avg,min,max
    */
    function getPageSummary(dataField, type) {
    	var value;
    	if (type == 'sum' || type == 'avg') {
    		// 求和
    		value = columnSum(dataField);
    		if (type == 'avg') {
    			// 求平均值
				value = value / jQuery("#grid_datalist").getGridParam('reccount');
			}
		} else {
			value = parseFloat(replaceValue(jQuery("#grid_datalist").getCell(1, dataField)) || 0);
			for(var i=2; i <= jQuery("#grid_datalist").getGridParam('reccount'); i++){
	    		var temp = parseFloat(replaceValue(jQuery("#grid_datalist").getCell(i, dataField)) || 0);
	    		if (type == 'min') {
	    			// 求最小值
	    			if(!isNaN(temp) && temp < value){
	    				value = temp;
		   			}
				} else {
					// 求最大值
					if(!isNaN(temp) && temp > value){
						value = temp;
		   			}
				}
	   			
	   		}
		}
    	value = formatter(dataField, value);
    	return value;
    }
    
    /**
    	根据字段名求和
    	dataField 字段名
    */
    function columnSum(dataField) {
    	var sum = 0
    	for(var i=1; i <= jQuery("#grid_datalist").getGridParam('reccount'); i++) {
    		// 替换千分符，货币符，字体html，链接html
    		var temp = parseFloat(replaceValue(jQuery("#grid_datalist").getCell(i, dataField)));
    		if (!isNaN(temp)) {
				sum += temp;
			}
		}
    	return sum;
    }
    
    /** 
    	数据格式化
    	dataField 数据字段
    	value 数据
    */
    var commasReg = /(\d{1,3})(?=(\d{3})+(?:$|\.))/g;
    function formatter(dataField, value) {
    	var temp = value;
    	if (dataFieldFormat[dataField].scale > -1) {
    		value = value.toFixed(dataFieldFormat[dataField].scale);
		}
    	// 货币型加￥符号
    	if (dataFieldFormat[dataField].currency == 1) {
    		value = '￥' + value;
		}
    	// 千分符
    	if (dataFieldFormat[dataField].commas == 1) {
			value = value.replace(commasReg, "$1,");  
		}
    	// 预警值
    	/* if (dataFieldFormat[dataField].warningValue != null
    			&& dataFieldFormat[dataField].warningValue != "") {
			if (temp >= parseFloat(dataFieldFormat[dataField].warningValue)) {
				value = "<font color='green'>"+value+"</font>";
			} else {
				value = "<font color='red'>"+value+"</font>";
			}
		} */
    	return value;
    }
    
    // 替换千分符，货币符，预警html，下钻html
    function replaceValue(value) {
    	if (value == null) {
			return "";
		}
    	return value.toString().replace(/<(.*)>(.*)<\/font>/, "$2").replace(/<(.*)>(.*)<\/a>/, "$2")
    		.replace(/,/g, "").replace("￥", "");
    }
    
    var GridHeight;
    var GridWidth;
    window.onbeforeprint= function onbeforeprint() {   //打印前事件    
    	var jqgridObj=jQuery("#grid_datalist");  
    	GridHeight = jqgridObj.jqGrid('getGridParam', 'height');//获取高度    
    	GridWidth = jqgridObj.jqGrid('getGridParam', 'width');//获取宽度    
    	qgridObj.jqGrid('setGridHeight', '100%');//将其高度设置成100%,主要是为了jqgrid 中有Scroll条时  能把该scroll条内内容都打印出来    
    	qgridObj.jqGrid('setGridWidth', '100%');//将其高度设置成100%,主要是为了jqgrid 中有Scroll条时  能把该scroll条内内容都打印出来
    	$("#grid_datalist.ui-jqgrid-bdiv").removeClass().addClass("ui-jqgrid-bdiv_self");//去除掉overflow属性
    	}
    window.onafterprint=function onafterprint() {//打印后事件    
    	//放开隐藏的元素   
    	$("#grid_datalist.ui-jqgrid-bdiv_self").removeClass().addClass("ui-jqgrid-bdiv");
    	//恢复overflow属性，否则会导致jqgrid中scroll条消失   
    	jQuery("#grid_datalist").jqGrid('setGridHeight', GridHeight);//设置成打印前的高度
    	jQuery("#grid_datalist").jqGrid('setGridWidth', GridWidth);//设置成打印前的宽度
    }
    
    <c:if test="${empty reportCellList}">
    	window.parent.manageLoading();
	</c:if>
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
	//调整大小
	window.onresize=function()
	{
	  $("#grid_datalist").setGridWidth(document.body.clientWidth-5);
	}

	// 打开下钻页面
	function openDrill(url){
		window.open(encodeURI(url, 'utf-8'), '', '');
	}
</Script>
<body style="margin:0px;padding:0px;">
	<input type="hidden" name="reportColumnList" id='reportColumnList' />
	<div style="width: 100%;">
		<c:if test="${not empty reportCellList}">
			<c:if test="${not empty thirdLevelHeaderList}">
				<iframe src="<%=basePath%>/NewReport/customPlatform.html?reportId=${reportDesign.reportId}" id="customPlatform" name="customPlatform"
					frameborder='0' width='100%' height='212' marginheight="0" marginwidth="0" style="margin-top: 5px;"></iframe>
			</c:if>
			<div style='float: left;margin-top: 5px;' id="printArea">
				<table id="grid_datalist" style="position: relative;"></table>
				<div id="pager"></div>
			</div>
		</c:if>
	</div>
</body>
</html>