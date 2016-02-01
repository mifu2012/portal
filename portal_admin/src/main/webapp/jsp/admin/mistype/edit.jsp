<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>修改用户</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/My97DatePicker/WdatePicker.js"></script>
  </head>
  
  <body>
  
   <form action="<%=request.getContextPath()%>/mistype/editsave/${mistype.typeId}" method="post">
   	类型ID：<input name="typeId" value="${mistype.typeId}"/><br/>
   	类型组：<input name="groupId" value="${mistype.groupId}"/><br/><br/>
   	类型名称：<input name="typeName" value="${mistype.typeName}"/><br/>
   	描述：<input name="detail" value="${mistype.detail}"/><br/>
	<input type="submit" value="提交">
   </form>
  </body>
</html>
