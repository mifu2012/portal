<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="UTF-8">
<head>
<title>没有权限</title>
<style type="text/css">
body {
	background-color: #fff;
	color: #666;
	text-align: center;
	font-family: arial, sans-serif;
}

div.dialog {
	width: 80%;
	height: 300px;
	padding: 1em 4em;
	margin: 4em auto 0 auto;
	border: 0px solid #ccc;
	border-right-color: #999;
	border-bottom-color: #999;
}

h1 {
	font-size: 100%;
	color: #f00;
	line-height: 1.5em;
}
</style>
</head>
<body>
	<script language='javascript'>
		function reload()
		{
			document.location.reload();
		}
	</script>
	<div class="dialog">
		<h1>加载模块失败</h1>
		<c:choose>
		<c:when test="${not empty errorMsg}">
		${errorMsg}
       </c:when>
       <c:otherwise>
       <p>抱歉！加载该模块失败，请重试！</p>
		
       </c:otherwise>
		</c:choose>
		<p><a href="javascript:reload()">重试</a>
		</p>
	</div>
</body>
</html>
