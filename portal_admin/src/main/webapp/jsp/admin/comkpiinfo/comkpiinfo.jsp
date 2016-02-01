<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>comkpiinfo</title>
<link type="text/css" rel="stylesheet" href="css/main.css"/>
</head>
<body>
	<form action="comkpiinfo.html" method="post" name="comKpiinfoForm" id="comKpiinfoForm">
	<div class="search_div">
		通用指标CODE：<input type="text" name="comKpiCode" value="${comkpiinfo.comKpiCode }"/>
		通用指标名称：<input type="text" name="comKpiName" value="${comkpiinfo.comKpiName }">
		<a href="javascript:search();" class="myBtn"><em>查询</em></a>
	</div>
	</form>
	<form action="comkpiinfo/delete.html" method="post" name="comKpiinfoForm1" id="comKpiinfoForm1">
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="main_table">
		<tr class="main_head">
			<th>序号</th>
			<th>通用指标CODE</th>
			<th>通用指标名称</th>
			<th>操作</th>
		</tr>
		<c:choose>
			<c:when test="${not empty comkpiinfos}">
				<c:forEach items="${comkpiinfos}" var="comkpiinfo" varStatus="vs">
				<tr class="main_info">
				<td>${vs.index+1}</td>
				<td>${comkpiinfo.comKpiCode }</td>
				<td>${comkpiinfo.comKpiName }</td>
				<td><a href="javascript:editComKpiinfo('${comkpiinfo.comKpiCode}');">修改</a> 
				| <a href="javascript:delComKpiinfo('${comkpiinfo.comKpiCode}');">删除</a>
				| <a href="javascript:bindingIndex('${comkpiinfo.comKpiCode}');">绑定指标</a>
				</td>
				</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr class="main_info">
					<td colspan="7">没有相关数据</td>
				</tr>
			</c:otherwise>
		</c:choose>
	</table>
	<div class="page_and_btn">
		<div>
			<a href="javascript:addComKpiinfo();" class="myBtn"><em>新增</em></a>
		</div>
		${comkpiinfo.page.pageStr }
	</div>
	</form>
	<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="js/datePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$(".main_info:even").addClass("main_table_even");
		});
		
		function addComKpiinfo(){
			var dg = new $.dialog({
				title:'新增通用指标信息',
				id:'comkpiinfo_new',
				width:430,
				height:300,
				iconTitle:false,
				cover:true,
				maxBtn:true,
				xButton:true,
				resize:true,
				page:'comkpiinfo/add.html'
				});
    		dg.ShowDialog();
		}
		
		function editComKpiinfo(paramId){
			var dg = new $.dialog({
				title:'修改通用指标信息',
				id:'comkpiinfo_edit',
				width:430,
				height:300,
				iconTitle:false,
				cover:true,
				maxBtn:true,
				resize:true,
				page:'comkpiinfo/edit'+paramId+'.html'
				});
    		dg.ShowDialog();
		}
		
		function delComKpiinfo(paramId){
			if(confirm("确定要删除该记录？")){
				var url = "comkpiinfo/delete"+paramId+".html";
				$.get(url,function(data){
					if(data=="success"){
						document.location.href="comkpiinfo.html";
					}
				});
			}
		}
		function bindingIndex(comKpiCode){
			var dg = new $.dialog({
				title:'绑定指标',				
				width:750,
				height:630,
				iconTitle:false,
				cover:true, 
				maxBtn:true,
				resize:true,
				page:'ComkpiRelKpi/showRelKpi'+comKpiCode+'.html'
				});
    		dg.ShowDialog();
		}
		function search(){
			$("#comKpiinfoForm").submit();
		}
	</script>
</body>
</html>