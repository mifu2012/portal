<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
   Object webSystemType= session.getServletContext().getInitParameter("webSystemType");
   int systemType=7;
   //系统类型
   //1: 经纬仪，2：瞭望塔，3：报表：4:经纬仪+报表  5:瞭望塔+报表  6:经纬仪+瞭望塔 7:所有
   try
   {
	   systemType=Integer.parseInt(String.valueOf(webSystemType));
   }catch(Exception e)
   {
	   systemType=7; 
   }
   pageContext.setAttribute("systemType",systemType);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>User</title>
<link type="text/css" rel="stylesheet" href="css/main.css" />
</head>
<body>
	<form action="user" method="post" name="userForm" id="userForm">
		<div class="search_div" style="font-weight: ">
			&nbsp;&nbsp;登录名称：<input type="text" name="loginname" id="loginname"
				value="${user.loginname}" onkeydown="enterToSearch()"
				style="width: 120px;" /> &nbsp;&nbsp;&nbsp;&nbsp;角色：<select
				name="roleId" id="roleId" style="vertical-align: middle;"
				onchange="javascript:search();">
				<option value="">请选择</option>
				<c:forEach items="${roleList}" var="role">
					<option value="${role.roleId}"
						<c:if test="${user.roleId==role.roleId}">selected</c:if>>${role.roleName}</option>
				</c:forEach>
			</select> &nbsp;&nbsp;&nbsp;&nbsp;登录日期：<input type="text"
				name="lastLoginStart" id="lastLoginStart"
				value="<fmt:formatDate value="${user.lastLoginStart}" pattern="yyyy-MM-dd"/>"
				onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'lastLoginEnd\',{d:-1});}'})"
				readonly="readonly"
				style="width: 100px; text-align: center; cursor: pointer;"
				onchange="checkDate();" /> - <input type="text" name="lastLoginEnd"
				id="lastLoginEnd"
				value="<fmt:formatDate value="${user.lastLoginEnd}" pattern="yyyy-MM-dd"/>"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'lastLoginStart\',{d:1});}'})"
				readonly="readonly"
				style="width: 100px; text-align: center; cursor: pointer;" />
			<!-- onchange="checkDate();" -->
			&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:search();" class="myBtn"><em>查询</em>
			</a> <a href="javascript:clearValue();" class="myBtn"><em>清空</em>
			</a>
		</div>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="main_table">
			<tr class="main_head">
				<th width="8%" style="padding-left: 20px" align="left">序号</th>
				<th width="12%" align="left">登录名称</th>
				<th width="10%" align="left">用户名称</th>
				<th width="10%" align="left">角色名称</th>
				<th width="15%" align="center">最近登录</th>
				<th width="45%" align="center">操作</th>
			</tr>
			<c:choose>
				<c:when test="${not empty userList}">
					<c:forEach items="${userList}" var="user" varStatus="vs">
						<tr class="main_info" style="cursor: pointer;" title="双击进行修改"
							onmouseover="this.style.backgroundColor='#D2E9FF';"
							onmouseout="this.style.backgroundColor='';"
							ondblclick="editUser(${user.userId })">
							<td style="padding-left: 25px" align="left">${vs.index+1}</td>
							<td align="left">${user.loginname }</td>
							<td align="left" id="username_${user.userId }">${user.username
								}</td>
							<td align="left">${user.role.roleName }</td>
							<td align="center"><fmt:formatDate value="${user.lastLogin}"
									pattern="yyyy-MM-dd HH:mm:ss" />
							</td>
							<td align="center"><a
								href="javascript:editUser(${user.userId });">修改</a> | <a
								href="javascript:delUser(${user.userId });">删除</a> | <a
								href="javascript:editRights(${user.userId });">权限</a>
								<!-- 1: 经纬仪，2：瞭望塔，3：报表：4:经纬仪+报表  5:瞭望塔+报表  6:经纬仪+瞭望塔 7:所有 --> 
								<c:if test="${systemType eq 1 || systemType eq 4 || systemType eq 6 || systemType eq 7}">
								  | <a href="javascript:editDwpasRights(${user.userId });">经纬仪模板</a> 
								</c:if>
								<c:if test="${systemType eq 3 || systemType eq 4 || systemType eq 5 || systemType eq 7}">
								  | <a href="javascript:editReportRights(${user.userId });">报表权限</a>
								  | <a href="javascript:editSelfRights(${user.userId });">自服务报表权限</a>
								</c:if>
								<c:if test="${systemType eq 2 || systemType eq 5 || systemType eq 6 || systemType eq 7}">
								  | <a href="javascript:editDwmisRights(${user.userId });">瞭望塔模板</a>
								</c:if>
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
				<a href="javascript:addUser();" class="myBtn"><em>新增</em>
				</a> <a href="javascript:exportUser();" class="myBtn"><em>导出</em>
				</a>
			</div>
			${user.page.pageStr }
		</div>
	</form>
	<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="js/datePicker/WdatePicker.js"></script>
	<script type="text/javascript"
		src="js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".main_info:even").addClass("main_table_even");
		});

		function sltAllUser() {
			if ($("#sltAll").attr("checked")) {
				$("input[name='userIds']").attr("checked", true);
			} else {
				$("input[name='userIds']").attr("checked", false);
			}
		}

		function addUser() {
			//$(".shadow").show();
			var dg = new $.dialog({
				title : '新增用户',
				id : 'user_new',
				width : 330,
				height : 300,
				iconTitle : false,
				cover : true,
				maxBtn : false,
				xButton : true,
				resize : true,
				page : 'user/add'
			});
			dg.ShowDialog();
		}
		//清空按钮
		function clearValue() {
			$("#loginname").val("");
			$("#roleId").val("");
			$("#lastLoginStart").val("");
			$("#lastLoginEnd").val("");
			$($("#roleId option"))[0].selected = true;
			search();
		}
		function editUser(userId) {
			var userName = $("#username_" + userId).text();
			var dg = new $.dialog({
				title : '修改用户：' + userName,
				id : 'user_edit',
				width : 330,
				height : 300,
				iconTitle : false,
				cover : true,
				maxBtn : false,
				resize : true,
				page : 'user/edit' + userId
			});
			dg.ShowDialog();
		}

		function delUser(userId) {
			if (confirm("确定要删除该记录？")) {
				var url = "user/delete" + userId;
				$.get(url, function(data) {
					if (data == "success") {
						alert("删除成功！");
						document.location.href = "user";
					} else {
						alert("删除失败！");
					}
				});
			}
		}

		function editRights(userId) {
			var userName = $("#username_" + userId).text();
			var dg = new $.dialog({
				title : '修改用户对后台权限：' + userName,
				id : 'auth',
				width : 280,
				height : 370,
				iconTitle : false,
				cover : true,
				maxBtn : true,
				resize : true,
				page : 'user/auth' + userId
			});
			dg.ShowDialog();
		}
		function editReportRights(userId) {
			var userName = $("#username_" + userId).text();
			var dg = new $.dialog({
				title : '修改用户对报表权限：' + userName,
				id : 'auth',
				width : 280,
				height : 370,
				iconTitle : false,
				cover : true,
				maxBtn : true,
				resize : true,
				page : 'user/authReport' + userId
			});
			dg.ShowDialog();
		}
		/* 自服务报表权限 */
		function editSelfRights(userId) {
			var userName = $("#username_" + userId).text();
			var dg = new $.dialog({
				title : '自服务报表权限：' + userName,
				id : 'auth',
				width : 280,
				height : 370,
				iconTitle : false,
				cover : true,
				maxBtn : true,
				resize : true,
				page : 'user/authSelfReport' + userId
			});
			dg.ShowDialog();
		}
		function editDwpasRights(userId) {
			var userName = $("#username_" + userId).text();
			var dg = new $.dialog({
				title : '修改用户对经纬仪模板权限：' + userName,
				id : 'auth',
				width : 550,
				iconTitle : false,
				cover : true,
				maxBtn : false,
				resize : true,
				page : 'user/authDwpas' + userId
			});
			dg.ShowDialog();
		}

		function editDwmisRights(userId) {
			var userName = $("#username_" + userId).text();
			var dg = new $.dialog({
				title : '修改用户对瞭望塔模板权限：' + userName,
				id : 'auth',
				width : 550,
				iconTitle : false,
				cover : true,
				maxBtn : false,
				resize : true,
				page : 'user/authDwmis' + userId
			});
			dg.ShowDialog();
		}

		function search() {
			$("#userForm").submit();
		}

		function exportUser() {
			document.location = "user/excel";
		}

		function enterToSearch() {
			if (event.keyCode == 13) {
				search();
				return false;
			}
		}
		function checkDate() {
			var begin = document.getElementById('lastLoginStart').value;//开始日期
			var over = document.getElementById('lastLoginEnd').value;//结束日期
			var ass, aD, aS;
			var bss, bD, bS;
			ass = begin.split("-");//以"-"分割字符串，返回数组；
			aD = new Date(ass[0], ass[1] - 1, ass[2]);//格式化为Date对像;
			aS = aD.getTime();
			bss = over.split("-");
			bD = new Date(bss[0], bss[1] - 1, bss[2]);
			bS = bD.getTime();
			if (aS > bS) {
				alert("结束日期不能小于开始日期");
				document.getElementById('lastLoginEnd').value = ""
				return false;
			}

			return true;//根据情况适当加
		}
	</script>
</body>
</html>