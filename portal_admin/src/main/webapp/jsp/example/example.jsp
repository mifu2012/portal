<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Example</title>
<link type="text/css" rel="stylesheet" href="css/main.css"/>
</head>
<body>
	<form action="example.html" method="post" name="exampleForm" id="exampleForm">
	<div class="search_div">
		用户名：<input type="text" name="name" value="${example.name }"/>
		出生日期：<input type="text" name="birthday" value="<fmt:formatDate value="${example.birthday}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker()" readonly="readonly" />
		<a href="javascript:search();" class="myBtn"><em>查询</em></a>
	</div>
	</form>
	<form action="example/delete.html" method="post" name="exampleForm1" id="exampleForm1">
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="main_table">
		<tr class="main_head">
			<th><input type="checkbox" name="sltAll" id="sltAll" onclick="sltAllExample()"/></th>
			<th>序号</th>
			<th>姓名</th>
			<th width="160">出生日期</th>
			<th>操作</th>
		</tr>
		<c:choose>
			<c:when test="${not empty examples}">
				<c:forEach items="${examples}" var="example" varStatus="vs">
				<tr class="main_info">
				<td><input type="checkbox" name="ids" id="ids${example.id }" value="${example.id }"/></td>
				<td>${vs.index+1}</td>
				<td>${example.name }</td>
				<td><fmt:formatDate value="${example.birthday}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td><a href="javascript:editExample(${example.id });">修改</a> | <a href="javascript:delExample(${example.id });">删除</a></td>
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
			<a href="javascript:addExample();" class="myBtn"><em>新增</em></a>
			<a href="javascript:delExamples();" class="myBtn"><em>删除</em></a>
		</div>
		${example.page.pageStr }
	</div>
	</form>
	<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="js/datePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$(".main_info:even").addClass("main_table_even");
		});
		
		function sltAllExample(){
			if($("#sltAll").attr("checked")){
				$("input[name='ids']").attr("checked",true);
			}else{
				$("input[name='ids']").attr("checked",false);
			}
		}
		
		function addExample(){
			var dg = new $.dialog({
				title:'新增用户',
				id:'example_new',
				width:330,
				height:300,
				iconTitle:false,
				cover:true,
				maxBtn:true,
				xButton:true,
				resize:true,
				page:'example/add.html'
				});
    		dg.ShowDialog();
		}
		
		function editExample(userId){
			var dg = new $.dialog({
				title:'修改用户',
				id:'example_edit',
				width:330,
				height:300,
				iconTitle:false,
				cover:true,
				maxBtn:true,
				resize:true,
				page:'example/edit'+userId+'.html'
				});
    		dg.ShowDialog();
		}
		
		function delExample(id){
			if(confirm("确定要删除该记录？")){
				var url = "example/delete"+id+".html";
				$.get(url,function(data){
					if(data=="success"){
						document.location.href="example.html";
					}
				});
			}
		}
		
		function delExamples(){
			$("#exampleForm1").submit();
		}
		
		function search(){
			$("#exampleForm").submit();
		}
	</script>
</body>
</html>