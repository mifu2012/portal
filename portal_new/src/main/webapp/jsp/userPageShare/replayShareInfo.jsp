<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
<title>Insert title here</title>
<script type="text/javascript" src="<%=path%>/common/js/jquery-1.4.3.min.js"></script>
</head>
<script type="text/javascript">
window.onload=function(){
	document.getElementById('allMessages').scrollTop = document.getElementById('allMessages').scrollHeight;
}
</script>
<body >
<div id="allMessages" style="width:380px;height:220px;overflow-y:auto; border:1px solid #e2e3ea; cursor: text;font-family:微软雅黑;font-size: 14px;line-height: 2px; "  >
<c:forEach items="${replayShareList}" var="replayShareInfo">
<c:choose>
<c:when test="${sendUserId eq replayShareInfo.sendUserId}">
<div style="width:100%; height:20px; background:#e0eff8; color:#159ade; font-weight:bold">
<div style="padding:10px 0px 0px 10px">${replayShareInfo.sendUserName}&nbsp;&nbsp;<fmt:formatDate value="${replayShareInfo.gmtSend}" pattern="yyyy-MM-dd HH:mm:ss" /></div>
</div>
<div style="padding-top:10px;padding-bottom:10px;padding-left: 16px;line-height:140%">${replayShareInfo.remark}</div><br/>
</c:when>
<c:otherwise>
<div style="width:100%; height:20px; background:#e8ffd9; color:#51d955; font-weight:bold"><div style="padding:10px 0px 0px 10px">${replayShareInfo.sendUserName}&nbsp;&nbsp;<fmt:formatDate value="${replayShareInfo.gmtSend}" pattern="yyyy-MM-dd HH:mm:ss" /></div></div>
<div style="padding-top:10px;padding-bottom:10px;padding-left: 16px; line-height:140%">${replayShareInfo.remark}</div><br/>
</c:otherwise>
</c:choose>
</c:forEach>

</div>
<div style="height: 10px;" ></div>
<textarea id="replayMessage"  style='width:380px; height:70px;font-family:微软雅黑; font-size: 14px;'></textarea>
<div style="height: 10px;"></div>
<input id="replayUrl" type="text" value="${url}" style="display: none;" />
<input id="replayUserId" type="text" value="${sendUserId}" style="display: none;" />
<input id="replayUserName" type="text" value="${sendUserName}" style="display: none;" />
<input id="replayOriginId" type="text" value="${originId}" style="display: none;" />
</body>
</html>