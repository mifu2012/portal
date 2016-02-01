<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<title>My Test</title>
<link type="text/css" rel="stylesheet" href="../css/main.css"/>
<style type="text/css">
body{width:100%;height:100%;background-color: #FFFFFF;text-align: center;}
.input_txt{width:200px;height:20px;line-height:20px;}
.info{height:40px;line-height:40px;}
.info th{text-align: right;width:65px;color: #4f4f4f;padding-right:5px;font-size: 13px;}
.info td{text-align:left;}
</style>
</head>
<body>
	<form  action="save" name="menuForm" id="menuForm" target="result" method="post" onsubmit="return checkInfo();">
		<input type="hidden" name="menuId" id="menuId" value="${menu.menuId }"/>
	<table border="0" cellpadding="0" cellspacing="0">
		<tr class="info">
			<th>从属菜单:</th>
			<td>
				<select name="parentId" id="parentId" class="input_txt" onchange="setMUR()">
					<option value="">请选择</option>
					<c:forEach items="${menuList}" var="menu">
					<option value="${menu.menuId }">${menu.menuName }</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr class="info">
			<th>名　称:</th>
			<td><input type="text" name="menuName" id="menuName" class="input_txt" value="${menu.menuName }" onblur="validateMenuName(this.value);"/></td>
		</tr>
		<tr class="info">
			<th>资源路径:</th>
			<td><input type="text" name="menuUrl" id="menuUrl" class="input_txt" value="${menu.menuUrl }"/></td>
		</tr>
	</table>
	</form>
	<iframe name="result" id="result" src="about:blank" frameborder="0" width="0" height="0"></iframe>
	
	<script type="text/javascript" src="../js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript">
		var dg;
		$(document).ready(function(){
			dg = frameElement.lhgDG;
			dg.addBtn('ok','保存',function(){
				var menuName=document.getElementById("menuName").value;
				if(menuName==null||menuName.length==0)
				{
					alert('菜单名不能为空');
					return;
				}
			/* 	if(document.getElementById("parentId").disabled==true)
				{
					var menuUrl=document.getElementById("menuUrl").value;
					if(menuUrl==null||menuUrl.length==0)
					{
						alert('菜单URL不能为空');
						return;
					}
				} */
				$("#menuForm").submit();
			});
			if($("#menuId").val()!=""){
				var parentId = "${menu.parentId}";
				if(parentId==""){
					$("#parentId").attr("disabled",true);
				}else{
					$("#parentId").val(parentId);
				}
			}
			setMUR();
		});
		
		function checkInfo(){
			if($("#menuName").val()==""){
				alert("请输入菜单名称!");
				$("#menuName").focus();
				return false;
			}
			return true;
		}
		
		function success(){
			alert("操作成功！");
			if(dg.curWin.document.forms[0]){
				dg.curWin.document.forms[0].action = dg.curWin.location+"";
				dg.curWin.document.forms[0].submit();
			}else{
				dg.curWin.location.reload();
			}
			dg.cancel();
		}
		
		function failed(){
			alert("操作失败！");
		}
		
		function setMUR(){
			if($("#parentId").val()==""){
				$("#menuUrl").attr("readonly",true);
				$("#menuUrl").val("");
				$("#menuUrl").addClass("input_disabled");
			}else{
				$("#menuUrl").attr("readonly",false);
				$("#menuUrl").removeClass("input_disabled");
			}
		}
		function validateMenuName(val) {  
		       // 如果为空或者输入空格执行   
		       if(val==null||val.length==0) return;
		       $.ajax({
			        type: "POST",                                                                 
			        url:encodeURI("<%=path%>/menu/alidateMenuName.html?menuName="
									+ val + "&random=" + Math.random()),
							success : function(msg) {
								if(msg=="1"){
				             		alert("菜单名: "+val+" 已存在，请重新输入！");
				             		$("#menuName").val("");
				             	 }  
							}
		       });  
		   }
	</script>
</body>
</html>