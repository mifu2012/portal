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
<link type="text/css" rel="stylesheet" href="<%=basePath%>css/main.css"/>
<link type="text/css" rel="stylesheet" href="<%=basePath%>js/zTree/zTreeStyle.css"/>

</head>
<body>
	<div>
		<ul id="tree" class="tree" style="overflow:auto;"></ul>
	</div>
	
	<script type="text/javascript" src="<%=basePath%>js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/zTree/jquery.ztree-2.6.min.js"></script>
	
	<script type="text/javascript">
		var dg;
		$(document).ready(function(){
			dg = frameElement.lhgDG;
			 dg.addBtn('ok','审批',function(){
				 parent.location.href="selfApply";
			}); 
			var setting = {
			    showLine: true,
			    checked : true,
			    checkable: false
			};
			var zn = '${zTreeNodes}';
			/* 判断是否为空 */
			if(zn!=null&&zn.length>0)
			{
				var zTreeNodes = eval(zn);
				var zTree = $("#tree").zTree(setting, zTreeNodes);
			}else
			{
			  var flag = false;
			  if(confirm("此用户无自服务报表权限，是否进入审批页面？"))
			  {
			  	flag=true;
			  }
			  if(flag)
			  {
				  parent.location.href="<%=basePath%>/selfApply"; 
			  }else{
				  parent.location.href="<%=basePath%>/user";
			  }
			}
			/* 判断是否为空 */
		});
	</script>
</body>
</html>