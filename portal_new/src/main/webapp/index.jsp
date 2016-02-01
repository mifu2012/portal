<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${systemName}</title>
<script type="text/javascript" src="<%=basePath%>/common/Common.jsp"></script>
<link href="<%=path%>/common/css/css.css" rel="stylesheet" type="text/css" />
</head>
<body class="indexbody">
  <script language='javascript'>
  	document.location.href="<%=basePath%>/UserLogin/mainPage.html";
  </script>
</body>
</html>