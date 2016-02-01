<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>kpiinfo</title>
<link type="text/css" rel="stylesheet" href="css/main.css"/>
</head>
<body>
	<form action="kpiinfo.html" method="post" name="kpiinfoForm" id="kpiinfoForm">
	<div class="search_div">
		指标CODE：<input type="text" name="kpiCode" value="${kpiinfo.kpiCode }"/>
		指标名称：<input type="text" name="kpiName" value="${kpiinfo.kpiName }"/>
		显示名称：<input type="text" name="dispName" value="${kpiinfo.dispName }"/>
		<a href="javascript:search();" class="myBtn"><em>查询</em></a>
	</div>
	</form>
	<form action="kpiinfo/delete.html" method="post" name="kpiinfoForm1" id="kpiinfoForm1">
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="main_table">
		<tr class="main_head">
			<th>序号</th>
			<th>指标CODE</th>
			<th>指标名称</th>
			<th>显示名称</th>
			<th>操作</th>
		</tr>
		<c:choose>
			<c:when test="${not empty kpiList}">
				<c:forEach items="${kpiList}" var="kpiinfo" varStatus="vs">
				<tr class="main_info">
				<td>${vs.index+1}</td>
				<td>${kpiinfo.kpiCode }</td>
				<td>${kpiinfo.kpiName }</td>
				<td>${kpiinfo.dispName }</td>
				<td><a href="javascript:editkpiinfo('${kpiinfo.kpiCode}');">修改</a> | <a href="javascript:delkpiinfo('${kpiinfo.kpiCode}');">删除</a></td>
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
			<a href="javascript:addkpiinfo();" class="myBtn"><em>新增</em></a>
		</div>
		${kpiinfo.page.pageStr }
	</div>
	</form>
	<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="js/datePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$(".main_info:even").addClass("main_table_even");
		});
		
		function sltAllkpiinfo(){
			if($("#sltAll").attr("checked")){
				$("input[name='ids']").attr("checked",true);
			}else{
				$("input[name='ids']").attr("checked",false);
			}
		}
		
		function addkpiinfo(){
			var dg = new $.dialog({
				title:'新增指标',
				id:'kpiinfo_new',
				width:600,
				height:600,
				iconTitle:false,
				cover:true,
				maxBtn:true,
				xButton:true,
				resize:true,
				page:'kpiinfo/add.html'
				});
    		dg.ShowDialog();
		}
		
		function editkpiinfo(kpiCode){
			var dg = new $.dialog({
				title:'修改指标',
				id:'kpiinfo_edit',
				width:600,
				height:600,
				iconTitle:false,
				cover:true,
				maxBtn:true,
				resize:true,
				page:'kpiinfo/edit'+kpiCode+'.html'
				});
    		dg.ShowDialog();
		}
		
		function delkpiinfo(kpiCode){
			if(confirm("确定要删除该记录？")){
				var url = "kpiinfo/delete"+kpiCode+".html";
				$.get(url,function(data){
					if(data=="success"){
						document.location.href="kpiinfo.html";
					}
				});
			}
		}
		
		function search(){
			$("#kpiinfoForm").submit();
		}
	</script>
</body>
</html>