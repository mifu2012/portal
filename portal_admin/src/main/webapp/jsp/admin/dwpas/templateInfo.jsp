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
<title>数据管理平台</title>
<link type="text/css" rel="stylesheet" href="<%=path %>/css/main.css"/>
<script type="text/javascript" src="<%=path %>/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>
</head>
<body>
	<form action="<%=path%>/dwpas/listPageTemplateInfo.html" method="post" name="templateInfoForm" id="templateInfoForm">
	<div class="search_div">
		模板名称：<input type="text" name="templateName" value="${templateInfo.templateName }"/>
		<a href="javascript:search();" class="myBtn"><em>查询</em></a>&nbsp;&nbsp;
	</div>
	</form>
	<form action="" method="post" name="templateForm1" id="templateForm1">
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="main_table">
		<tr class="main_head">
			<th width="5%">序号</th>
			<th width="20%">模板名称</th>
			<th width="30%">备注</th>
			<th width="20%">创建时间</th>
			<th>操作</th>
		</tr>
		<c:choose>
			<c:when test="${not empty templateInfos}">
				<c:forEach items="${templateInfos}" var="template" varStatus="vs">
				<tr class="main_info" style="cursor: pointer;" title="双击进行修改" onmouseover="this.style.backgroundColor='#D2E9FF';" onmouseout="this.style.backgroundColor='';" ondblclick="location.href='<%=path%>/dwpas/listJingWeiYiMenus${template.templateId}.html?templateName=${template.templateName }' " >
				<td>${vs.index+1}</td>
				<td id="templateName${template.templateId }" >${template.templateName }</td>
				<td>${template.remark }</td>
				<td><fmt:formatDate value="${template.gmtCreate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>
				<c:choose>
				<c:when test="${template.templateId == 1}">
				<a href="<%=path%>/dwpas/listJingWeiYiMenus${template.templateId}.html?templateName=${template.templateName }" target="mainFrame">修改</a>|<a href="javascript:copyTemplateInfo(${template.templateId });">复制</a>|<a href="javascript:accessControl(${template.templateId });">权限</a>
				</c:when>
				<c:otherwise>
				<a href="<%=path%>/dwpas/listJingWeiYiMenus${template.templateId}.html?templateName=${template.templateName }">修改</a>|<a href="javascript:copyTemplateInfo(${template.templateId });">复制</a>|<a href="javascript:delTemplateInfo(${template.templateId });">删除</a>|<a href="javascript:accessControl(${template.templateId });">权限</a>
				</c:otherwise>
				</c:choose>
				|<a href="javascript:connectProd(${template.templateId });">关联产品</a>
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
		${templateInfo.page.pageStr }
	</div>
	</form>
	
	<script type="text/javascript">
		$(document).ready(function(){
			$(".main_info:even").addClass("main_table_even");
		});
		
		function search(){
			$("#templateInfoForm").submit();
		}
		function connectProd(templateId){
			var templateName=$("#templateName"+templateId).text();
				var dg = new $.dialog({
					title:'关联产品：'+templateName,
					id:'template_prod',
					width:600,
					height:450,
					iconTitle:false,
					cover:true,
					maxBtn:true,
					xButton:true,
					resize:true,
					page:'<%=path%>/dwpas/listAllProducts'+templateId+'.html'
					});
	    		dg.ShowDialog();
		}
	
		function copyTemplateInfo(templateId){
			var templateName=$("#templateName"+templateId).text();
			var dg = new $.dialog({
				title:'复制模板：'+templateName,
				id:'template_copy',
				width:350,
				height:200,
				iconTitle:false,
				cover:true,
				maxBtn:true,
				xButton:true,
				resize:true,
				page:'<%=path%>/dwpas/getTemplateInfoByID'+templateId+'.html'
				});
    		dg.ShowDialog();
		}
		function delTemplateInfo(templateId){
			if(confirm("确定要删除该记录？")){
				var url = "<%=path%>/dwpas/deleteTemplate"+templateId+".html";
				$.get(url,function(data){
					if(data=="success"){
						alert("删除成功！");
						document.location.href="<%=path%>/dwpas/listPageTemplateInfo.html";
					}else{
						alert("删除失败！");
					}
				});
			}
		}
		function accessControl(templateId){
			var templateName=$("#templateName"+templateId).text();
			var dg = new $.dialog({
				title:'修改权限：'+templateName,
				id:'access_control',
				width:600,
				height:400,
				iconTitle:false,
				cover:true,
				maxBtn:true,
				xButton:true,
				resize:true,
				page:'<%=path%>/dwpas/accessControl'+templateId+'.html'
				});
    		dg.ShowDialog();
		}
	</script>
</body>
</html>
