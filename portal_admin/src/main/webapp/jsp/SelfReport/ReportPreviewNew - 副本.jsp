<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="java.util.Date"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>报表预览-NEW</title>
<meta name="Generator" content="EditPlus">
<meta name="Author" content="">
<meta name="Keywords" content="">
<meta name="Description" content="">
<link type="text/css" rel="stylesheet"
	href="<%=basePath%>/css/report.css" />
<!--日期控件-->
<script type="text/javascript"
	src="<%=basePath%>/js/datePicker/WdatePicker.js"></script>
<!--月历控件-->
<script type="text/javascript"
	src="<%=basePath%>/js/datePicker/My98MonthPicker.js"></script>
<%-- <script type="text/javascript" src="<%=basePath%>/js/jquery-1.5.1.min.js"></script> --%>
<link href="<%=path%>/js/jqgrid/css/jquery-ui-1.8.2.custom.css"
	media="screen" type="text/css" rel="stylesheet" />
<link href="<%=path%>/js/jqgrid/css/ui.jqgrid.css" media="screen"
	type="text/css" rel="stylesheet" />
<script type="text/javascript"
	src="<%=path%>/js/jqgrid/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript"
	src="<%=path%>/js/jqgrid/i18n/grid.locale-cn.js"></script>
<script type="text/javascript"
	src="<%=path%>/js/jqgrid/js/jquery.jqGrid.min.js"></script>
<!-- 上面是GRID控件相关的JS和CSS -->
</head>
<script type="text/javascript">
	$.jgrid.no_legacy_api = true;
	$.jgrid.useJSON = true; 
	var isLoad=false;
	//是否合并表格
    var useColSpanStyle=false;
	var mydata=[
	   <c:forEach items="${reportDataList}" var="reportDataMap" varStatus="status">
	      <c:if test="${status.index!=0}">,</c:if>
		  {
			   <c:forEach items="${reportDataMap}" var="reportData" varStatus="vs">
				   <c:if test="${vs.index!=0}">,</c:if>
				   "${reportData.key}":"${reportData.value}"
               </c:forEach>
		  }
	   </c:forEach>
	]
   jQuery(document).ready(function(){ 
	//测试数据
	jQuery("#list").jqGrid({
	    url:'<%=basePath%>/selfReportConfig/loadReportDataByPaging.html?reportId=${selfReportConfig.reportId}&queryWhere=',
		datatype: "json",
		//mtype: 'GET',
		data:mydata,
		datatype:"local",
		//表头
	   colNames: [
	          <c:forEach items="${reportCellList}" var="reportCell" varStatus="status">
	             //如果非跨列
	     	     <c:if test="${reportCell.colSpan==1}">
	     	    	<c:if test="${status.index!=0}">,</c:if>
	     	    	'${reportCell.content}'
	     	     </c:if>//
				 <c:if test="${reportCell.colSpan>1}">
					 useColSpanStyle=true;
				 </c:if>
	     	  </c:forEach>
	            ],
	   //colNames: ['Date', 'Client', 'Amount', 'Tax', 'Total', 'Closed', 'Shipped via', 'Notes'],
	   //样式
	   colModel: [
		  <c:forEach items="${reportCellList}" var="reportCell" varStatus="status">
			<c:if test="${reportCell.dataType==1}">
			     //数据列
				 <c:if test="${status.index!=0}">,</c:if>
				   {name: '${reportCell.dataField}' , index: '${reportCell.dataField}' , width :200}
			</c:if>
		   </c:forEach>  
	    ],
	   	rowNum:10,//每页显示记录数 
	   	rowList:[10,20,30],//可调整每页显示的记录数
	    jsonReader:{
	    	totalpages: "total",    
	    	currpage: "page",   
	    	totalrecords: "records",
            repeatitems : false
    	}, 
	    //pager: '#pghcs',
	    pager: 'pager', //分页工具栏 
	   	sortname: 'parent_code',
	    viewrecords: true,//是否显示行数
	    //scroll:true,
	    sortorder: "desc",
		jsonReader: {
			repeatitems : false
		},
		caption: "测试报表",
		width: 1300,//宽度
		height: '100%'//高度
	});
	//表格头合并
	<c:if test="${fn:length(mergeCellList)>=1}">
		jQuery("#grid_datalist").jqGrid('setGroupHeaders', {
		  useColSpanStyle: true, //二级表头合并
		  groupHeaders:[
			  <c:forEach items="${mergeCellList}" var="reportCell" varStatus="status">
				  <c:if test="${status.index!=0}">,</c:if>
				  {startColumnName: '${reportCell.firstChildCol}', numberOfColumns: ${reportCell.colSpan}, titleText: '${reportCell.content}'}
			  </c:forEach>
		  ]	
		});
	</c:if>
 	 //定义工具条
 	jQuery("#grid_datalist").navGrid('#grid_pager',{edit:false, add:false,del:false,search:false,view:true})
 					.navButtonAdd('#grid_pager',{caption:"打印",buttonicon:"ui-icon-del",
 											onClickButton:function(){alert("打印");}})
 					.navButtonAdd('#grid_pager',{caption:"导出",buttonicon:"ui-icon-del",
 											onClickButton:function(){ location.href='<%=basePath%>/exportAllList.html';}});
 	//jQuery("#list").jqGrid('jqGridExport', {exptype:xmlstring});
 	// jQuery("#list").jqGrid('navGrid','#gridpager',prmEdit, prmAdd, prmDel, prmSearch, prmView);
 	//if(isLoad==false) queryReportData();
});
</script>
<body>
   <form method="post" action="" name="reportForm" id="reportForm">
        <input type='hidden' name="jsonData" value="${jsonData}">
	  	<table id="grid_datalist"></table>
	    <!-- style="height: 200px; width: 700px;" -->
	    <div id="grid_pager"></div>
   </form>
</body>
</html>
