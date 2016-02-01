<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>维度</title>
<link type="text/css" rel="stylesheet" href="css/main.css"/>
</head>
<body>
	<form action="dmnsn.html" method="post" name="dmnsnForm" id="dmnsnForm">
	<div class="search_div">
		维度名称：<input type="text" name="dmnsnName" value="${dmnsn.dmnsnName }"/>
		<%-- 创建日期：<input type="text" name="birthday" value="<fmt:formatDate value="${example.birthday}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker()" readonly="readonly" /> --%>
		<a href="javascript:search();" class="myBtn"><em>查询</em></a>
	</div>
	</form>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="main_table">
		<tr class="main_head">
			<!-- <th><input type="checkbox" name="sltAll" id="sltAll" onclick="sltAllExample()"/></th> -->
			<th>序号</th>
			<th>维度名称</th>
			<th width="160">创建日期</th>
			<th>操作</th>
		</tr>
		<c:choose>
			<c:when test="${not empty dmnsns}">
				<c:forEach items="${dmnsns}" var="dmnsn" varStatus="vs">
				<tr class="main_info">
				<%-- <td><input type="checkbox" name="ids" id="ids${example.id }" value="${example.id }"/></td> --%>
				<td>${vs.index+1}</td>
				<td>${dmnsn.dmnsnName }</td>
				<td><fmt:formatDate value="${dmnsn.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td><a href="javascript:editDmnsn(${dmnsn.dmnsnId });">修改${dmnsn.dmnsnId }</a> | <a href="javascript:delDmnsn(${dmnsn.dmnsnId });">删除</a></td>
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
			<a href="javascript:addDmnsn();" class="myBtn"><em>新增</em></a>
			<!-- <a href="javascript:delExamples();" class="myBtn"><em>删除</em></a> -->
		</div>
		${dmnsn.page.pageStr }
	</div>
	<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="js/datePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$(".main_info:even").addClass("main_table_even");
		});
		
		/* function sltAllExample(){
			if($("#sltAll").attr("checked")){
				$("input[name='ids']").attr("checked",true);
			}else{
				$("input[name='ids']").attr("checked",false);
			}
		} */
		
		function addDmnsn(){
			var dg = new $.dialog({
				title:'新增维度',
				id:'dmnsn_new',
				width:330,
				height:300,
				iconTitle:false,
				cover:true,
				maxBtn:true,
				xButton:true,
				resize:true,
				page:'dmnsn/add.html'
				});
    		dg.ShowDialog();
		}
		
		function editDmnsn(dmnsnId){
			var dg = new $.dialog({
				title:'修改维度',
				id:'dmnsn_edit',
				width:330,
				height:300,
				iconTitle:false,
				cover:true,
				maxBtn:true,
				resize:true,
				page:'dmnsn/edit'+dmnsnId+'.html'
				});
    		dg.ShowDialog();
		}
		
		function delDmnsn(dmnsnId){
			if(confirm("确定要删除该记录？")){
				var url = "dmnsn/delete"+dmnsnId+".html";
				$.get(url,function(data){
					if(data=="success"){
						document.location.href="dmnsn.html";
					}
				});
			}
		}
		
		function search(){
			$("#dmnsnForm").submit();
		}
	</script>
</body>
</html>