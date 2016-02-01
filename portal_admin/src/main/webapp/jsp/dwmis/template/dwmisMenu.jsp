<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>瞭望塔菜单管理</title>
<link type="text/css" rel="stylesheet" href="<%=path%>/css/main.css" />
</head>
<body>
	<div class="search_div" style="margin-top: 3px;">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td align="left" width="70%">
					<div style="font-size: 12px; font-family: '宋体';">
						当前位置:&nbsp;${templateName}&nbsp;&gt;&nbsp;<font color="red">菜单信息</font>&nbsp;
					</div>
				</td>
				<td align="left"><a href="javascript:addmenu();" class="myBtn"><em>新增</em>
				</a>&nbsp; <a href="<%=path%>/dwmisTemplateInfo/list.html" class="myBtn"><em>返回</em>
				</a> <input type="checkbox" value="1" name="chk_show_menu"
						onclick='showMenu(this.checked)' id="chk_show_menu" />
					<label for="chk_show_menu" style='cursor: hand;' onclick='showHideMenu();'>显示已隐藏的菜单</label></td>
			</tr>
		</table>
	</div>
	<table width="100%" border="0" cellpadding="0" cellspacing="0"
		class="main_table" id="sys_menu_tb">
		<tr class="main_head">
			<th width="10%" align="left">&nbsp;&nbsp;序号</th>
			<th width="25%" align="left">名称</th>
			<th width="25%">备注</th>
			<th width="13%">是否可见</th>
			<th width="12%">操作</th>
		</tr>
		<c:choose>
			<c:when test="${not empty subjectList}">
				<c:forEach items="${subjectList}" var="menu" varStatus="vs">
					<tr
						style="background-color: #ffffff; height: 27px; text-align: center; color: #666666;">
						<td align="left">&nbsp;&nbsp;&nbsp;&nbsp;${vs.index+1}</td>
						<td align='left'>&nbsp;${menu.subjectName }</td>
						<td>${menu.remark }</td>
						<c:choose>
							<c:when test="${menu.isShow==1 }">
								<td><font color="blue">可见</font></td>
							</c:when>
							<c:otherwise>
								<td><font color="red">不可见</font></td>
							</c:otherwise>
						</c:choose>
						<td><a href="javascript:void(0);" onclick="editSuject('${menu.subjectId}');">修改</a></td>
					</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr class="main_info">
					<td colspan="4">没有相关数据</td>
				</tr>
			</c:otherwise>
		</c:choose>
	</table>
	<script type="text/javascript" src="<%=path%>/js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript"
		src="<%=path%>/js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>
	<script type="text/javascript">
	function editSuject(subjectId)
	{
		location.href="<%=path%>/dwmisModule/getModuleInfos.html?subjectId="
				+ subjectId;
	}
</script>
</body>
</html>