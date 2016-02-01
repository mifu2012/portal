<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<title>角色</title>
<link type="text/css" rel="stylesheet" href="css/main.css"/>
</head>
<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="main_table">
		<tr class="main_head">
			<th width="15%" style="padding-left: 20px" align="left">序号</th>
			<th width="40%" style="padding-left: 50px" align="left">角色名称</th>
			<th width="45%">操作</th>
		</tr>
		<c:choose>
			<c:when test="${not empty roleList}">
				<c:forEach items="${roleList}" var="role" varStatus="vs">
				<tr class="main_info" style="cursor: pointer;" title="双击进行修改"  onmouseover="this.style.backgroundColor='#D2E9FF';" onmouseout="this.style.backgroundColor='';" ondblclick="editRole(${role.roleId });" >
				<td style="padding-left: 25px" align="left">${vs.index+1}</td>
				<td style="padding-left: 50px" align="left" id="roleNameTd${role.roleId }">${role.roleName }</td>
				<td><a href="javascript:editRole(${role.roleId });">修改</a> 
				| <a href="javascript:delUser(${role.roleId });">删除</a> 
				| <a href="javascript:editRights(${role.roleId });">权限</a>
				<!-- 1: 经纬仪，2：瞭望塔，3：报表：4:经纬仪+报表  5:瞭望塔+报表  6:经纬仪+瞭望塔 7:所有 -->
				<c:if test="${systemType eq 1 || systemType eq 4 || systemType eq 6 || systemType eq 7}">
				  | <a href="javascript:editDwpasRights(${role.roleId });">经纬仪模板</a>
				</c:if>
				<c:if test="${systemType eq 3 || systemType eq 4 || systemType eq 5 || systemType eq 7}">
				  | <a href="javascript:editReportRights(${role.roleId });">报表权限</a>
				</c:if>
				<c:if test="${systemType eq 2 || systemType eq 5 || systemType eq 6 || systemType eq 7}">
				  | <a href="javascript:editDwmisRights(${role.roleId });">瞭望塔模板</a>
				</c:if>
				</td>
				</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr class="main_info">
				<td colspan="3">没有相关数据</td>
				</tr>
			</c:otherwise>
		</c:choose>
	</table>
	<div class="page_and_btn">
		<div>
			<a href="javascript:addRole();" class="myBtn"><em>新增</em></a>
		</div>
	</div>
	
	<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>
	
	<script type="text/javascript">
		$(document).ready(function(){
			$(".main_info:even").addClass("main_table_even");
		});
		
		function addRole(){
			var dg = new $.dialog({
				title:'新增角色',
				id:'role_new',
				width:300,
				height:130,
				iconTitle:false,
				cover:true,
				maxBtn:false,
				xButton:true,
				resize:true,
				html:'<div style="width:100%;height:40px;line-height:40px;text-align:center;"><span style="color: #4f4f4f;font-size: 13px;font-weight: bolder;display:inline-block;vertical-align:middle;">角色名称：</span><input type="text" name="roleName" id="roleName" style="vertical-align: middle;"/></div>'
				});
    		dg.ShowDialog();
    		dg.addBtn('ok','保存',function(){
    			var url = "role/save";
    			var postData = {roleName:$("#roleName").val()};
				if($("#roleName").val()==null||$("#roleName").val().length==0)
				{
					alert('角色名不能为空');
					return;
				}
    			$.post(url,postData,function(data){
    				if(data=='success'){
    					alert("操作成功！");
    					dg.curWin.location.reload();
    					dg.cancel();
    				}else{
    					alert('角色名重复，保存失败！');
    					$("#roleName").focus();
    					$("#roleName").select();
    				}
    			});
    		});
		}
		
		function editRole(roleId){
			var roleName = $("#roleNameTd"+roleId).text();
			var dg = new $.dialog({
				title:'修改角色：'+roleName,
				id:'role_edit',
				width:300,
				height:130,
				iconTitle:false,
				cover:true,
				maxBtn:false,
				xButton:true,
				resize:true,
				html:'<div style="height:40px;line-height:40px;text-align:center;"><span style="color: #4f4f4f;font-size: 13px;font-weight: bold;display:inline-block;vertical-align:middle;">角色名称：</span><input type="text" name="roleName" id="roleName" value="'+roleName+'" style="vertical-align: middle;"/></div>'
				});
    		dg.ShowDialog();
    		dg.addBtn('ok','保存',function(){
    			var url = "role/save";
    			var postData = {roleId:roleId,roleName:$("#roleName").val()};
    			$.post(url,postData,function(data){
    				if(data=='success'){
    					alert("操作成功！");
    					dg.curWin.location.reload();
    					dg.cancel();
    				}else{
    					alert('角色名重复，保存失败！');
    					$("#roleName").focus();
    					$("#roleName").select();
    				}
    			});
    		});
		}
		
		function editRights(roleId){
			var roleName = $("#roleNameTd"+roleId).text();
			var dg = new $.dialog({
				title:'修改角色对后台权限：'+roleName,
				id:'auth',
				width:280,
				height:370,
				iconTitle:false,
				cover:true,
				maxBtn:true,
				resize:true,
				page:'role/auth'+roleId
				});
    		dg.ShowDialog();
		}
		function editReportRights(roleId){
			var roleName = $("#roleNameTd"+roleId).text();
			var dg = new $.dialog({
				title:'修改角色对报表权限：'+roleName,
				id:'auth',
				width:280,
				height:370,
				iconTitle:false,
				cover:true,
				maxBtn:true,
				resize:true,
				page:'role/authReport'+roleId
				});
    		dg.ShowDialog();
		}
		function editDwpasRights(roleId){
			var roleName = $("#roleNameTd"+roleId).text();
			var dg = new $.dialog({
				title:'修改角色对经纬仪模板：'+roleName,
				id:'auth',
				width:550,
				iconTitle:false,
				cover:true,
				maxBtn:false,
				xButton:true,
				resize:true,
				page:'role/authDwpas'+roleId
				});
    		dg.ShowDialog();
		}
		
		function editDwmisRights(roleId){
			var roleName = $("#roleNameTd"+roleId).text();
			var dg = new $.dialog({
				title:'修改角色对瞭望塔模板：'+roleName,
				id:'auth',
				width:550,
				iconTitle:false,
				cover:true,
				maxBtn:false,
				xButton:true,
				resize:true,
				page:'role/authDwmis'+roleId
				});
    		dg.ShowDialog();
		}
		
		
		function delUser(roleId){
			if(confirm("确定要删除该角色？\n该角色以及角色拥有的所有用户将被删除!")){
				var url = "role/delete"+roleId;
				$.get(url,function(data){
					if(data=="success"){
						alert('删除成功');
						document.location.href="role";
					}else
					{
					   alert('删除失败');
					}
				});
			}
		}
	</script>
</body>
</html>