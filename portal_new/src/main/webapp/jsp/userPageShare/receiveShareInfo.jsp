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
<link href="<%=path%>/common/css/main.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=path%>/common/js/jquery-1.4.3.min.js"></script>
</head>
<script type="text/javascript">
//删除信息
function deleteReceiveInfo(id){
	if (confirm("确定要删除该记录？")){
		
		$.ajax({
	        type: "POST",                                                                 
	        url:encodeURI("<%=path%>/userShare/deleteInfo.html?id="+id+"&random=" + Math.random()),
					success : function(msg) {
						if(msg=="success"){
							alert("删除成功！");
							document.location.href='<%=path%>/userShare/showReceiveInfo.html';
							
						}else{
							alert("删除失败！");
						}
					}
	   });
		
	}	
}
//查看链接，并把未读的改成已读
function readPage(url,id){
	
	
	window.open(url);
	var imgId="img_"+id;
	//window.frames['center_iframe'].document.location.href=url;
	$.ajax({
        type: "POST",                                                                 
        url:encodeURI("<%=path%>/userShare/updateIsRead.html?id="+id+"&random=" + Math.random()),
				success : function(msg) {	
					document.getElementById(imgId).src='<%=path%>/common/images/isReaded.gif';
					receiveMessage1();
				}
   });
}

function receiveMessage1(){
	
	$.ajax({
        type: "POST",                                                                 
        url:encodeURI("<%=path%>/userShare/getMessageNum.html?random=" + Math.random()),
				success : function(msg) {
						//$("#messageNum").html(msg);
						var parentWin = window.parent ;
						parentWin.document.getElementById("messageNum").innerHTML=msg;
						
					}
			});
}
function showReplay(type,originId,url,sendUserId,sendUserName,id){
	if(type=="dblclick"){
	var reg=new RegExp("&","g");
	var replayUrl=url.replace(reg,"^");
	window.parent.document.getElementById('replayInfoIframe').src="<%=path%>/userShare/replayShareInfo.html?originId="+originId+"&replayUrl="+replayUrl+"&sendUserId="+sendUserId+"&sendUserName="+sendUserName;
	var popWin=window.parent.document.getElementById('replayInfo');
	popWin.style.top=window.parent.document.getElementById('windowpop').style.top;
	popWin.style.left=window.parent.document.getElementById('windowpop').style.left;
	popWin.style.display="block"; 
	$.ajax({
        type: "POST",                                                                 
        url:encodeURI("<%=path%>/userShare/updateIsRead.html?id="+id+"&random=" + Math.random()),
				success : function(msg) {	
					document.getElementById("img_"+id).src='<%=path%>/common/images/isReaded.gif';
					receiveMessage1();
				}
   });
	}
}

</script>
<body>

  <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <thead>
    <tr>
       <th width="5%"></th>
       <th width="10%" align="left">序号</th>
       <th width="13%" align="left">共享人</th>
       <th width="28%" align="left">共享说明</th>
       <th width="24%" align="center">共享时间</th>
       <th width="20%" align="center">操作</th>
    </tr>
    </thead>
    <tbody>
    <c:choose>
    <c:when test="${not empty receiveShareInfoList }">
    <c:forEach items="${receiveShareInfoList }" var="receiveShareInfo" varStatus="vs" >
    <tr height="30" style="cursor: pointer;" ondblclick="showReplay(event.type,${receiveShareInfo.originId},'${receiveShareInfo.url}',${receiveShareInfo.sendUserId},'${receiveShareInfo.sendUserName}',${receiveShareInfo.id});" title="双击回复" onmouseover="this.style.backgroundColor='#D2E9FF';" onmouseout="this.style.backgroundColor='';">
       <td width="5%"  align="left">
       <c:choose>
       <c:when test="${receiveShareInfo.isReaded==0}"><img id="img_${receiveShareInfo.id}" src="<%=path%>/common/images/unReaded.gif" /></c:when>
       <c:otherwise><img id="img_${receiveShareInfo.id}" src="<%=path%>/common/images/isReaded.gif" /></c:otherwise>
       </c:choose>
       </td>
       <td width="10%" align="left">${vs.index+1}</td>
       <td width="13%" align="left">${receiveShareInfo.sendUserName}</td>
       <td width="28%" align="left">${receiveShareInfo.remark}</td>
       <td width="24%" align="center"><fmt:formatDate value="${receiveShareInfo.gmtSend}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
       <td width="20%" align="center"><a href="javascript:vold(0);" onclick="deleteReceiveInfo(${receiveShareInfo.id});">删除</a>|<a href="javascript:vold(0);" onclick="readPage('${receiveShareInfo.url}',${receiveShareInfo.id });">查看页面</a></td>
       </tr>
    </c:forEach>
    </c:when>
     <c:otherwise>
     <tr><td colspan="6" align="center">没有相关信息</td></tr>
     </c:otherwise>
     </c:choose>
    </tbody>
  </table>
<div class="page_and_btn">
		${userPageShareInfo.page.pageStr }
	</div>


</body>


</html>