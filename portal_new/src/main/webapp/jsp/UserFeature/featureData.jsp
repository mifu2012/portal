<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>   
<%@ page import="com.infosmart.portal.pojo.DwpasCKpiInfo"%>
<%@ page import="java.util.Date"%>
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
<title>${systemName}-首页</title>

<!-- 下面是GRID控件相关的JS和CSS -->
<link rel="stylesheet" type="text/css" href="<%=path%>/common/grid/gt_grid.css?id=<%=new Date().getTime()%>" />
<link rel="stylesheet" type="text/css" href="<%=path%>/common/grid/skin/vista/skinstyle.css" />
<script type="text/javascript" src="<%=path%>/common/grid/gt_msg_cn.js"></script>
<script type="text/javascript" src="<%=path%>/common/grid/gt_grid_all.js"></script>
<!-- 上面是GRID控件相关的JS和CSS -->
<script language="javascript">
  //表格数据${fn:length(kpiNames)}
  var kpi_Data_table=[
      <c:forEach items="${excelDataList}" var="excelDates" varStatus="status">
        <c:if test="${status.index!=0}">,</c:if>
        [ <c:forEach items="${excelDates}" var="excelData" varStatus="sta"><c:if test="${sta.index!=0}">,</c:if>"${excelData}"</c:forEach>]
      </c:forEach>
     ];
  //表格头
  var dsOption= {
	fields :[
        <c:forEach items="${excelHeadInfos}" var="headInfo" varStatus="status">
            <c:if test="${status.index==0}">{name : 'column_${status.index}' ,type: 'float' }</c:if>
            <c:if test="${status.index!=0}">,{name : 'column_${status.index}' ,type: 'float' }</c:if>
            
        </c:forEach>		
	],
	recordType : 'array',
	data : kpi_Data_table
  }
  
  var colsOption = [
     <c:forEach items="${excelHeadInfos}" var="headInfo" varStatus="status">
        <c:if test="${status.index!=0}">,</c:if>
        //日期
        <c:if test="${status.index==0}">{id: 'column_${status.index}' , header: "${headInfo}" , width :100,align:"center",frozen : true }</c:if>
        //金额
        <c:if test="${fn:length(headInfo)<=5}">
            <c:if test="${status.index!=0}">{id: 'column_${status.index}' , header: "${headInfo}" , width :${620/(fn:length(headInfo)-1)},align:"right"}</c:if>
        </c:if>
        <c:if test="${fn:length(headInfo)>5}">
            <c:if test="${status.index!=0}">{id: 'column_${status.index}' , header: "${headInfo}" , width :200,align:"right"}</c:if>
        </c:if>
     </c:forEach>                      
   ];  
  
  var gridOption={
		    id : "gridbox1",
			width: "100%",  //"100%", // 700,
			height: "270",  //"100%", // 330,

			//remotePaging : true ,异步加载
			//loadURL : '<%=basePath%>/selfReport/loadReportDataByPaging?reportId=', //表示异步加载
			//remoteSort : true,//后台排序
			
			container : 'gridbox1', 
			replaceContainer : true, 
			
			dataset : dsOption ,//记录集
			columns : colsOption,//列
			pageSizeList : [10,30,50,100,200],
			pageSize : 10 ,
			showGridMenu : true,//显示菜单
			allowCustomSkin : false,//自定义皮肤
			allowFreeze : true,//锁定列
            allowGroup : true,//按组排列
            allowHide : true,//列隐藏	
			//pageInfo : {pageNum : 1},
			showIndexColumn : true,//显示序号
			toolbarContent : 'nav | pagesize | reload | print| xls  state',////'nav | goto | pagesize | reload | add del save | print | state' 
			onComplete : function(){  
				//alert('数据加载完成');
			}
		};

  var thegrid=new Sigma.Grid( gridOption );
  Sigma.Util.onLoad( Sigma.Grid.render(thegrid) );
  //kpiCodes:${kpiCodes}
  //mygrid.loadURL=  //异步加载
  //重新加载
  /*
  var pageInfo={pageNum : 1 };//页码归一
  mygrid.setPageInfo(pageInfo); 
  mygrid.reload();
  */
  //导出为EXCEL
  thegrid.exportURL="<%=path%>/userFeature/columnExcelDown.html?featureType=${featureType}";
</script>
</head>
<body >
	<div id="gridbox1"></div>
	
</body>
</html>
