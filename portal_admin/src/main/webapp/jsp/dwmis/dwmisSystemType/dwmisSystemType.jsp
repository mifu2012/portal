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
<title>Example</title>
<link type="text/css" rel="stylesheet" href="<%=basePath%>/css/main.css"/>
</head>
<body>
	<div class="search_div">
	<select id="systemTypeId" name="systemTypeId" onchange="getsystemParamGroup(this.value);">
	<option value="0" <c:if test="${groupId=='0' }" >selected</c:if> >选择全部</option>
	<c:forEach items="${systemParamGroups }" var="systemParamGroup">
	<option value="${systemParamGroup.typeId }" <c:if test="${systemParamGroup.typeId==groupId }" >selected</c:if> >${systemParamGroup.typeName}</option>
	</c:forEach>
	</select>&nbsp;&nbsp;&nbsp;<a href="javascript:ddSystemParamType();" class="myBtn"><em>新增系统类型</em></a>
	&nbsp;<a href="javascript:addSystemParamTypeGroup();" class="myBtn"><em>新增系统类型组</em></a>
	</div>
	<c:forEach items="${dwmisSystemParamTypeList }" var="dwmisSystemGroup" >
	
	<form action="systemType/delete.html" method="post" name="exampleForm1" >
	
	<div align="left" style="background-color: #eee" ><h4>${dwmisSystemGroup.groupName }&nbsp;&nbsp;
	 <a href="javascript:delOneSystemTypeGroup(${dwmisSystemGroup.groupId});" class="myBtn"><em style="font-weight: lighter;">删除</em></a>
	 <a href="javascript:editSystemTypeGroup(${dwmisSystemGroup.groupId});" class="myBtn"><em style="font-weight: lighter;">修改</em></a></h4>
	</div>
    
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="main_table">
	
		<tr class="main_head">
			<th width="15%" style="padding-left: 20px" align="left">系统类型编号</th>
			<th width="15%" align="left">系统类型组类组</th>
			<th width="15%" align="left">系统类型名称</th>
			<th width="45%" >系统类型详情</th>
			<th width="10%">操作</th>
		</tr>
		       <c:choose>
			    <c:when test="${not empty dwmisSystemGroup.dwmisSystemParamTypeList}">
				<c:forEach items="${dwmisSystemGroup.dwmisSystemParamTypeList }" var="dwmisSystemParamType">
				<tr class="main_info" style="cursor: pointer;"
							onmouseover="this.style.backgroundColor='#D2E9FF';"
							onmouseout="this.style.backgroundColor='';"
							title='双击修改系统类型' ondblclick="updateSystemParamType(${dwmisSystemParamType.typeId });">
				<td style="padding-left: 20px;" align="left">${dwmisSystemParamType.typeId }</td>
				<td align="left">${dwmisSystemParamType.groupId}</td>
				<td align="left">${dwmisSystemParamType.typeName }</td>
				<td width="40%" style="padding-left: 40px;" align="left" >${dwmisSystemParamType.detail }</td>
				<td><a href="javascript:updateSystemParamType(${dwmisSystemParamType.typeId });">修改</a> | <a href="javascript:delSystemParamType(${dwmisSystemParamType.typeId },'${groupId}');">删除</a></td>
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
		<div style="height: 30px;">
			
		</div>
	</div>
	</form>
	
	</c:forEach>
	<script type="text/javascript" src="<%=basePath%>/js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>/js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>
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
		
		function addSystemParamTypeGroup(){
			var dg = new $.dialog({
				title:'新增系统类型组',
				id:'systemType_new',
				width:380,
				height:350,
				iconTitle:false,
				cover:true,
				fixed:true,
				maxBtn:true,
				xButton:true,
				resize:true,
				page:'systemType/addGroup.html'
				});
    		dg.ShowDialog();
		}
		
		function ddSystemParamType(){
			var dg = new $.dialog({
				title:'新增系统类型',
				id:'systemType_newType',
				width:380,
				height:350,
				iconTitle:false,
				cover:true,
				fixed:true,
				maxBtn:true,
				xButton:true,
				resize:true,
				page:'systemType/add.html'
				});
    		dg.ShowDialog();
		}
		
		function updateSystemParamType(typeId){
			var dg = new $.dialog({
				title:'修改系统类型指标',
				id:'systemType_edit',
				width:380,
				height:350,
				iconTitle:false,
				cover:true,
				fixed:true,
				maxBtn:true,
				resize:true,
				page:'systemType/edit'+typeId+'.html'
				});
    		dg.ShowDialog();
		}
		
		function editSystemTypeGroup(typeId){
			var dg = new $.dialog({
				title:'修改系统类型组',
				id:'systemTypeGroup_edit',
				width:380,
				height:350,
				iconTitle:false,
				cover:true,
				fixed:true,
				maxBtn:true,
				resize:true,
				page:'systemType/editGroup'+typeId+'.html'
				});
    		dg.ShowDialog();
		}
		
		
		function delSystemParamType(typeId,groupId){
			if(confirm("确定要删除该记录？")){
				var url = "systemType/delete"+typeId+".html";
				$.get(url,function(data){
					if(data=="success"){
						alert("操作成功");
						document.location.href="systemType.html?groupId="+groupId;
					}else
					{
						alert("操作失败");
					}
				});
			}
		}
		//删除组和节点
		function delOneSystemTypeGroup(groupId){
			if(confirm("确定要删除该记录？")){
				var url = "systemType/delete"+groupId+".html";
				$.get(url,function(data){
					if(data=="success"){
						alert("操作成功");
						document.location.href="systemType.html?";
					}else
					{
						alert("操作失败");
					}
				});
			}
		}
		
		
		function delExamples(){
			$("#exampleForm1").submit();
		}
		
/* 		function search(){
			$("#exampleForm").submit();
		} */
		function getsystemParamGroup(groupId){
			document.location.href="systemType.html?groupId="+groupId;
		}
	</script>
</body>
</html>