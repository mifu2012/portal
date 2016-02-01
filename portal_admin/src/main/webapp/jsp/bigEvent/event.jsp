<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<title>大事件信息</title>
<link type="text/css" rel="stylesheet" href="<%=basePath%>css/main.css"/>
</head>
<body>
	<form action="BigEvent.html" method="post" name="eventForm"
		id="eventForm">
		<div class="search_div">
			&nbsp;&nbsp;标题：<input type="text" name="title" id="title"
				value="${bigEventPo.title }" /> 
			&nbsp;&nbsp;
			事件类型：<select name="eventTypeName"
				id="eventTypeName" style="vertical-align: middle;"
				onchange="javascript:search();">
				<option value="">请选择</option>
				<c:forEach items="${eventTypeList}" var="type">
					<option <c:if test="${type.typeId ==bigEventPo.eventTypeName}">selected</c:if> value="${type.typeId}">${type.typeName}</option>
				</c:forEach>
			</select> 
		    &nbsp;&nbsp;
			<a href="javascript:search();" class="myBtn"><em>查询</em></a>
			<a href="javascript:clearValue();" class="myBtn"><em>清空</em></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:editEventType();" class="myBtn"><em>事件类型维护</em></a>
		</div>
		
	</form>
	<form action="BigEvent/delete.html" method="post" name="exampleForm1" id="exampleForm1">
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="main_table">
		<tr class="main_head">
			<th width="5%"><input type="checkbox" name="sltAll" id="sltAll" onclick="sltAllEvent()"/></th>
			<th width="5%">序号</th>
			<th align='left'>&nbsp;&nbsp;标题</th>
			<th width="13%">事件类型</th>
			<th width="13%">开始时间</th>
			<th width="13%">结束时间</th>
			<th width="15%">操作</th>
		</tr>
		<c:choose>
			<c:when test="${not empty misEventList}">
				<c:forEach items="${misEventList}" var="bigEventPo" varStatus="vs">
				<tr class="main_info" style="cursor: pointer;" title="双击进行修改" onmouseover="this.style.backgroundColor='#D2E9FF';" onmouseout="this.style.backgroundColor='';" ondblclick="editEvent(${bigEventPo.eventId});" >
				<td><input type="checkbox" name="ids" id="ids${bigEventPo.eventId}" value="${bigEventPo.eventId}"/></td>
				<td>${vs.index+1}</td>
				<td align="left">&nbsp;&nbsp;${bigEventPo.title }</td>
				<td>${bigEventPo.eventTypeName}</td>
				<td>${bigEventPo.eventStartDate}</td>
				<td>${bigEventPo.eventEndDate}</td>
				<td><a href="javascript:editEvent(${bigEventPo.eventId});">修改</a> | <a href="javascript:delEvent(${bigEventPo.eventId});">删除</a>
				 | <a href="javascript:bindingIndex(${bigEventPo.eventId});">绑定指标</a> 
				 </td>
				</tr>
<%-- 				<a href="BigEvent/editEvent?eventId=${bigEventPo.eventId}&actionType=edit">修改</a> --%>
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
			<a href="javascript:addEvent();" class="myBtn"><em>新增</em></a>
			<a href="javascript:delExamples();" class="myBtn"><em>删除</em></a> 
		</div>
		${bigEventPo.page.pageStr}
	</div>
	</form>
	<script type="text/javascript" src="<%=basePath%>js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$(".main_info:even").addClass("main_table_even");
		});
		
		function sltAllEvent(){
			if($("#sltAll").attr("checked")){
				$("input[name='ids']").attr("checked",true);
			}else{
				$("input[name='ids']").attr("checked",false);
			}
		}
		//清空 
		function clearValue(){
			$("#title").val("");
			$($("#eventTypeName option"))[0].selected=true;
			search();
		}
		
		function addEvent(){
			var dg = new $.dialog({
				title:'添加大事件',
				width:600,
				height:450,
				iconTitle:false,
				cover:true,
				maxBtn:true,
				xButton:true,
				resize:true,
				page:'<%=basePath%>/BigEvent/addEvent.html',
				dgOnLoad:function(){
				     //最大化
				       dg.maxSize();
				}
				});
    		dg.ShowDialog();
		}
		
		function editEventType(){
			var dg = new $.dialog({
				title:'事件类型维护',
				width:450,
				height:450,
				iconTitle:false,
				cover:true,
				maxBtn:true,
				xButton:true,
				resize:true,
				page:'<%=basePath%>/BigEvent/showEventType.html'
				});
    		dg.ShowDialog();
		}
		
		function editEvent(eventId){
			var dg = new $.dialog({
				title:'修改大事件',				
				width:600,
				height:450,
				iconTitle:false,
				cover:true,
				maxBtn:true,
				resize:true,
				page:'<%=basePath%>/BigEvent/editEvent'+eventId+'.html',
				dgOnLoad:function(){
				     //最大化
				       dg.maxSize();
				}
				});
    		dg.ShowDialog();
		}
		
		function bindingIndex(eventId){
			var dg = new $.dialog({
				title:'绑定指标',				
				width:600,
				height:450,
				iconTitle:false,
				cover:true, 
				maxBtn:true,
				resize:true,
				page:'<%=basePath%>/BigEventRelKpi/showEventRelKpi'+eventId+'.html'
				});
    		dg.ShowDialog();
		}
		
		function delEvent(id){
			if(confirm("确定要删除该记录？")){
				var url = "<%=basePath%>BigEvent/delete"+id+".html";
				$.get(url,function(data){
					if(data=="success"){
						alert("删除成功");
						document.location.href="<%=basePath%>/BigEvent.html";
					}else
					{
						alert("删除失败");
					}
				});
			}
		}
		
		function delExamples(){
			var checks=document.getElementsByName("ids");
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
				var url = "<%=basePath%>BigEvent/deleteList.html?idList="+ids;
				$.get(url,function(data){
					if(data=="success"){
						alert("删除成功");
						document.location.href="<%=basePath%>/BigEvent.html";
					}else
					{
						alert("删除失败");
					}
				});
			} 
		}
		
		function search(){
			$("#eventForm").submit();
		}
		

	</script>
</body>
</html>