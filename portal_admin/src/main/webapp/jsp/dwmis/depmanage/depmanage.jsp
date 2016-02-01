<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>部门信息</title>
<link type="text/css" rel="stylesheet" href="<%=basePath%>css/main.css" />
</head>
<body>

	<form action="<%=path%>/dwmisMisDptmnt/search.html" method="post"
		name="exampleForm" id="exampleForm">
		<div class="search_div" style="font-weight: ">
			部门名称：<input type="text" name="depName"  value="${dwmisMisDptmnt.depName}" id="secrchDep" /> 部门分类：<select
				name="typeName" id="typeName" style="vertical-align: middle;"
				onchange="javascript:search();">
				<option value="">请选择</option>
				<c:forEach items="${dwmisMisTypes}" var="dwmisMisType">
					<option  <c:if test="${dwmisMisDptmnt.typeName == dwmisMisType.typeName}">selected="selected"</c:if> value="${dwmisMisType.typeName}">${dwmisMisType.typeName}</option>
				</c:forEach>
			</select> <a href="javascript:search();" class="myBtn"><em>查询</em></a>
			<a href="javascript:clearValue();" class="myBtn"><em>清空</em></a>
		</div>
	</form>
	<form action="<%=path%>/dwmisMisDptmnt/delete.html" method="post"
		name="exampleForm1" id="exampleForm1">
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="main_table">
			<tr class="main_head">
				<th width="5%"><input type="checkbox" name="sltAll" id="sltAll"
					onclick="sltAllEvent()" /></th>
				<th width="15%" align="left">序号</th>
				<th width="25%" align="left" >部门名称</th>
				<th width="25%" align="left">部门分类</th>
				<th width="30%">操作</th>
			</tr>
			<c:choose>
				<c:when test="${not empty dwmisMisDptmnts}">
					<c:forEach items="${dwmisMisDptmnts}" var="dmd" varStatus="vs">
						<tr class="main_info" style="cursor: pointer;" title="双击修改部门信息" onmouseover="this.style.backgroundColor='#D2E9FF';" onmouseout="this.style.backgroundColor='';" ondblclick="ediDep(${dmd.depId});" >
							<td><input type="checkbox" name="depIds"
								id="depIds${dmd.depId}" value="${dmd.depId}" /></td>
							<td align="left" style="padding-left: 5px;" >${vs.index+1}</td>
							<td align="left">${dmd.depName}</td>
							<td align="left">${dmd.typeName}</td>
							<td><a href="javascript:ediDep(${dmd.depId});">修改</a> | <a
								href="javascript:delEvent(${dmd.depId});">删除</a> | <a
								href="javascript:bindingIndex(${dmd.depId});">绑定指标</a></td>
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
				<a href="javascript:ediDep();" class="myBtn"><em>新增</em></a> <a
					href="javascript:delDeps();" class="myBtn"><em>删除</em></a>
			</div>
			${dwmisMisDptmnt.page.pageStr}
		</div>
	</form>
	<script type="text/javascript"
		src="<%=basePath%>js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$(".main_info:even").addClass("main_table_even");
		});
		
		function sltAllEvent(){
			if($("#sltAll").attr("checked")){
				$("input[name='depIds']").attr("checked",true);
			}else{
				$("input[name='depIds']").attr("checked",false);
			}
		}
		//清空
		function clearValue(){
			$("#secrchDep").val("");
			$($("#typeName option"))[0].selected=true;
			search();
		}
		function addEvent(){
			var dg = new $.dialog({
				title:'添加部门信息',
				width:350,
				height:280,
				iconTitle:false,
				cover:true,
				maxBtn:true,
				xButton:true,
				resize:true,
				page:'<%=path%>/dwmisMisDptmnt/insert.html'
				});
    		dg.ShowDialog();
		}
		
		function ediDep(depId){
			var dg = new $.dialog({
				title:'编辑部门信息',				
				width:350,
				height:280,
				iconTitle:false,
				cover:true,
				maxBtn:true,
				resize:true,
				page:'<%=path%>/dwmisMisDptmnt/edit.html?depId='+depId
				});
    		dg.ShowDialog();
		}
		
		function bindingIndex(depId){
			var dg = new $.dialog({
				title:'绑定指标',				
				width:600,
				height:450,
				iconTitle:false,
				cover:true, 
				maxBtn:true,
				resize:true,
				page:'<%=path%>/dptmentRelKpi/showDptmentRelKpi'+depId+'.html'
				});
    		dg.ShowDialog();
		}
		
		function delEvent(depId){
			if(confirm("确定要删除该记录？")){
				var url = "<%=path%>/dwmisMisDptmnt/delete"+depId+".html";
				$.get(url,function(data){
					if(data=="success"){
						alert("删除成功！");
						document.location.href="<%=path%>/dwmisMisDptmnt/search.html";
									}
					else{
						alert("删除失败！");
					}
								});
			}
		}

		function delDeps() {
			var checks=document.getElementsByName("depIds");
			var ids="";
			for(var i=0;i<checks.length;i++){
				if(checks[i].checked){
					if(ids==""){
						ids=checks[i].value;
					}else{
						ids=ids+","+checks[i].value;
					}
					
				}
			}
 			if(confirm("确定要删除该记录？")){
				var url = "<%=path%>/dwmisMisDptmnt/deleteList.html?ids="+ids;
				$.get(url,function(data){
					if(data=="success"){
						alert("删除成功");
						document.location.href="<%=path%>/dwmisMisDptmnt/search.html";
					}else
					{
						alert("删除失败");
					}
				});
			} 
		}

		function search() {
			$("#exampleForm").submit();
		}
	</script>
</body>
</html>