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
<title>报表预览</title>
<meta name="Generator" content="EditPlus">
<meta name="Author" content="">
<meta name="Keywords" content="">
<meta name="Description" content="">
<link type="text/css" rel="stylesheet" href="<%=basePath%>/css/report.css" />
<link href="<%=basePath%>js/jqgrid/css/jquery-ui-1.8.2.custom.css" media="screen" type="text/css" rel="stylesheet" />
<link href="<%=basePath%>js/jqgrid/css/ui.jqgrid.css" media="screen" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="<%=basePath%>js/jqgrid/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqgrid/i18n/grid.locale-cn.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqgrid/js/jquery.jqGrid.min.js"></script>
<!-- 上面是GRID控件相关的JS和CSS -->
</head>
<script type="text/javascript">
	$.jgrid.no_legacy_api = true;
	$.jgrid.useJSON = true; 
	var isLoad=false;
jQuery(document).ready(function(){ 
	//测试数据
	 var mydata = [ 
					{"kpi_code":"APY4072100301M","parent_code":null,"kpi_name":"销量_按性别_男","disp_name":null,"kpi_type":"3","is_overall":"1","is_show":"0","show_order":"1","is_average":"0","is_max":"0","is_variation":"0","is_percent":"0","unit":"万人"},
					{"kpi_code":"APY1011020701D","parent_code":null,"kpi_name":"新增注册账户数_按平台_支付宝_天","disp_name":"新增注册会员数","kpi_type":"1","is_overall":"1","is_show":"0","show_order":"1","is_average":"0","is_max":"0","is_variation":"0","is_percent":"0","unit":"万人"},
					{"kpi_code":"APY2310000302M","parent_code":null,"kpi_name":"公司活跃帐户数（使用用户数）动态一年_按活跃次数_低度_月","disp_name":"活跃会员数","kpi_type":"3","is_overall":"1","is_show":"0","show_order":"1","is_average":"0","is_max":"0","is_variation":"0","is_percent":"0","unit":"万人"},
					{"kpi_code":"APY4071200301M","parent_code":null,"kpi_name":"用户数_按是否使用无线业务_未使用无线","disp_name":null,"kpi_type":"3","is_overall":"1","is_show":"0","show_order":"1","is_average":"0","is_max":"0","is_variation":"0","is_percent":"0","unit":"万人"},
					{"kpi_code":"APY2320002802M","parent_code":null,"kpi_name":"公司活跃帐户数占比（使用用户数）动态一年_按活跃次数_深度_月","disp_name":"大盘深度活跃会员占比","kpi_type":"3","is_overall":"1","is_show":"0","show_order":"1","is_average":"0","is_max":"0","is_variation":"0","is_percent":"1","unit":"%"},
					{"kpi_code":"APY4071100301M","parent_code":null,"kpi_name":"用户数_按是否使用无线业务_使用无线","disp_name":null,"kpi_type":"3","is_overall":"1","is_show":"0","show_order":"1","is_average":"0","is_max":"0","is_variation":"0","is_percent":"0","unit":"万人"},
					{"kpi_code":"APY4072200301M","parent_code":null,"kpi_name":"销量_按性别_女","disp_name":null,"kpi_type":"3","is_overall":"1","is_show":"0","show_order":"1","is_average":"0","is_max":"0","is_variation":"0","is_percent":"0","unit":"万人"},
					{"kpi_code":"APY2320000302M","parent_code":null,"kpi_name":"公司活跃帐户数（使用用户数）动态一年_按活跃次数_深度_月","disp_name":"活跃会员数","kpi_type":"3","is_overall":"1","is_show":"0","show_order":"1","is_average":"0","is_max":"0","is_variation":"0","is_percent":"0","unit":"万人"},
					{"kpi_code":"APY3000000101D","parent_code":null,"kpi_name":"交易笔数_天","disp_name":"大盘业务笔数","kpi_type":"1","is_overall":"1","is_show":"1","show_order":"2","is_average":"0","is_max":"0","is_variation":"0","is_percent":"0","unit":"万笔"},
					{"kpi_code":"APY2000000302D","parent_code":null,"kpi_name":"公司活跃帐户数（使用用户数）动态一年_天","disp_name":"活跃会员数","kpi_type":"1","is_overall":"1","is_show":"1","show_order":"4","is_average":"0","is_max":"0","is_variation":"0","is_percent":"0","unit":"万人"}
					]; 
	jQuery("#list").jqGrid({
		 data:mydata,
		datatype:"local", 
		//表头
	   colNames: [
	              
	     	     
	     	    	
	     	    	'kpi_code'
	     	     
	     	    
	     	     
	     	    	,
	     	    	'parent_code'
	     	     
	     	    
	     	     
	     	    	,
	     	    	'kpi_name'
	     	     
	     	    
	     	     
	     	    	,
	     	    	'disp_name'
	     	     
	     	    
	     	     
	     	    	,
	     	    	'kpi_type'
	     	     
	     	    
	     	     
	     	    	,
	     	    	'is_show'
	     	     
	     	    
	     	     
	     	    	,
	     	    	'role_formula_desc'
	     	     
	     	    
	     	     
	     	    	,
	     	    	'show_order'
	     	     
	     	    
	     	     
	     	    	,
	     	    	'is_average'
	     	     
	     	    
	     	     
	     	    	,
	     	    	'is_max'
	     	     
	     	    
	     	     
	     	    	,
	     	    	'is_variation'
	     	     
	     	    
	     	     
	     	    	,
	     	    	'is_percent'
	     	     
	     	    
	     	     
	     	    	,
	     	    	'unit'
	     	     
	     	    
	     	     
	     	    	,
	     	    	'decimal_num'
	     	     
	     	    
	     	     
	     	    	,
	     	    	'convert_num'
	     	     
	     	    
	     	     
	     	    	,
	     	    	'convert_type'
	     	     
	     	    
	     	     
	     	    	,
	     	    	'is_cal_kpi'
	     	     
	     	    
	     	     
	     	    	,
	     	    	'role_formula'
	     	     
	     	    
	     	     
	     	    	,
	     	    	'is_use'
	     	     
	     	    
	     	     
	     	    	,
	     	    	'gmt_create'
	     	     
	     	    
	     	     
	     	    	,
	     	    	'gmt_modified'
	     	     
	     	    
	     	     
	     	    	,
	     	    	'remark'
	     	     
	     	    
	     	     
	     	    	,
	     	    	'is_overall'
	     	     
	     	    
	            ],
	   //样式
	   colModel: [
		  
			
				 
			       
			       
				    
				    
					
					
					  //其他类型 
				      {name: 'kpi_code' , index: 'kpi_code' , width :70}
					
					
		        
			
			
			
				 ,
			       
			       
				    
				    
					
					
					  //其他类型 
				      {name: 'parent_code' , index: 'parent_code' , width :70}
					
					
		        
			
			
			
				 ,
			       
			       
				    
				    
					
					
					  //其他类型 
				      {name: 'kpi_name' , index: 'kpi_name' , width :70}
					
					
		        
			
			
			
				 ,
			       
			       
				    
				    
					
					
					  //其他类型 
				      {name: 'disp_name' , index: 'disp_name' , width :70}
					
					
		        
			
			
			
				 ,
			       
			       
				    
				    
					
					
					  //其他类型 
				      {name: 'kpi_type' , index: 'kpi_type' , width :70}
					
					
		        
			
			
			
				 ,
			       
			       
				    
				    
					
					
					  //其他类型 
				      {name: 'is_show' , index: 'is_show' , width :70}
					
					
		        
			
			
			
				 ,
			       
			       
				    
				    
					
					
					  //其他类型 
				      {name: 'role_formula_desc' , index: 'role_formula_desc' , width :70}
					
					
		        
			
			
			
				 ,
			       
			       
				    
				    
					
					
					  //其他类型 
				      {name: 'show_order' , index: 'show_order' , width :70}
					
					
		        
			
			
			
				 ,
			       
			       
				    
				    
					
					
					  //其他类型 
				      {name: 'is_average' , index: 'is_average' , width :70}
					
					
		        
			
			
			
				 ,
			       
			       
				    
				    
					
					
					  //其他类型 
				      {name: 'is_max' , index: 'is_max' , width :70}
					
					
		        
			
			
			
				 ,
			       
			       
				    
				    
					
					
					  //其他类型 
				      {name: 'is_variation' , index: 'is_variation' , width :70}
					
					
		        
			
			
			
				 ,
			       
			       
				    
				    
					
					
					  //其他类型 
				      {name: 'is_percent' , index: 'is_percent' , width :70}
					
					
		        
			
			
			
				 ,
			       
			       
				    
				    
					
					
					  //其他类型 
				      {name: 'unit' , index: 'unit' , width :70}
					
					
		        
			
			
			
				 ,
			       
			       
				    
				    
					
					
					  //其他类型 
				      {name: 'decimal_num' , index: 'decimal_num' , width :70}
					
					
		        
			
			
			
				 ,
			       
			       
				    
				    
					
					
					  //其他类型 
				      {name: 'convert_num' , index: 'convert_num' , width :70}
					
					
		        
			
			
			
				 ,
			       
			       
				    
				    
					
					
					  //其他类型 
				      {name: 'convert_type' , index: 'convert_type' , width :70}
					
					
		        
			
			
			
				 ,
			       
			       
				    
				    
					
					
					  //其他类型 
				      {name: 'is_cal_kpi' , index: 'is_cal_kpi' , width :70}
					
					
		        
			
			
			
				 ,
			       
			       
				    
				    
					
					
					  //其他类型 
				      {name: 'role_formula' , index: 'role_formula' , width :70}
					
					
		        
			
			
			
				 ,
			       
			       
				    
				    
					
					
					  //其他类型 
				      {name: 'is_use' , index: 'is_use' , width :70}
					
					
		        
			
			
			
				 ,
			       
			       
				    
				    
					
					
					  //其他类型 
				      {name: 'gmt_create' , index: 'gmt_create' , width :70}
					
					
		        
			
			
			
				 ,
			       
			       
				    
				    
					
					
					  //其他类型 
				      {name: 'gmt_modified' , index: 'gmt_modified' , width :70}
					
					
		        
			
			
			
				 ,
			       
			       
				    
				    
					
					
					  //其他类型 
				      {name: 'remark' , index: 'remark' , width :70}
					
					
		        
			
			
			
				 ,
			       
			       
				    
				    
					
					
					  //其他类型 
				      {name: 'is_overall' , index: 'is_overall' , width :70}
					
					
		        
			
			  
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
		caption: "华南利润表",
		width: 1300,//宽度
		height: '100%'//高度
	});
	jQuery("#list").jqGrid('setGroupHeaders', {
	  useColSpanStyle: true, //没有二级表头合并
	  groupHeaders:[
		{startColumnName: 'kpi_name', numberOfColumns: 3, titleText: '<em>Price测试</em>'},
		{startColumnName: 'is_overall', numberOfColumns: 2, titleText: 'Shiping测试'}
	  ]	
	});
 	 //定义工具条
 	jQuery("#list").navGrid('#pager',{edit:false, add:false,del:false,search:false,view:true})
 					.navButtonAdd('#pager',{caption:"打印",buttonicon:"ui-icon-del",
 											onClickButton:function(){alert("打印");}})
 					.navButtonAdd('#pager',{caption:"导出",buttonicon:"ui-icon-del",
 											onClickButton:function(){ location.href='<%=basePath%>/exportAllList.html';}});
});
</script> 
<body>
	<table id="list"></table>
	<div id="pager"></div> 
 </body>
</html>
