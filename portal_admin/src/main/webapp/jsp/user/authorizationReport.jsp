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
<title>报表权限</title>
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
			dg.addBtn('ok','保存',function(){
				var nodes = zTree.getCheckedNodes();
				var tmpNode;
				var ids = "";
				for(var i=0; i<nodes.length; i++){
					tmpNode = nodes[i];
					if(i!=nodes.length-1){
						ids += tmpNode.id+",";
					}else{
						ids += tmpNode.id;
					}
				}
				var userId = "${userId}";
				var roleId = "${roleId}";
				var url;
				var postData;
				if(userId!=""){
					postData = {"userId":userId,"reportIds":ids};
					url = "authsaveReport";
				}else{
					postData = {"roleId":roleId,"reportIds":ids};
					url = "authsaveReport";
				}
				$.post(url,postData,function(data){
					if(data && data=="success"){
						alert("操作成功！");
						dg.cancel();
					}
					else{
						alert("操作失败！");
					}
				});
			});
			var setting = {
			    showLine: true,
			    checkable: true
			};
			var zn = '${zTreeNodes}';
			var zTreeNodes = eval(zn);
			var zTree = $("#tree").zTree(setting, zTreeNodes);
		});
	</script>
</body>
</html>