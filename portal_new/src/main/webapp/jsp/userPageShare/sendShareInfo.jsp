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
function deleteSendInfo(id){
	if (confirm("确定要删除该记录？")){
		
		$.ajax({
	        type: "POST",                                                                 
	        url:encodeURI("<%=path%>/userShare/deleteInfo.html?id="+id+"&random=" + Math.random()),
					success : function(msg) {
						if(msg=="success"){
							alert("删除成功！");
							document.location.href='<%=path%>/userShare/showSendInfo.html';
							
						}else{
							alert("删除失败！");
						}
					}
	   });
		
	}	
}
function readPage(url,id){	
	
	window.open(url);

}

</script>

<body>
 
  <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <thead>
    <tr >
       <th width="8%" align="left">序号</th>
       <th width="22%" align="left">被共享人</th>
       <th width="28%" align="left">共享说明</th>
       <th width="22%" align="center">共享时间</th>
       <th width="20%" align="center">操作</th>
    </tr>
    </thead>
    <tbody>
    <c:choose>
    <c:when test="${not empty sendShareInfoList }">
    <c:forEach items="${sendShareInfoList }" var="sendShareInfo" varStatus="vs" >
    <tr height="30" onmouseover="this.style.backgroundColor='#D2E9FF';" onmouseout="this.style.backgroundColor='';">
       <td width="8%" align="left">${vs.index+1}</td>
       <td width="22%" align="left">${sendShareInfo.receiveUserName}</td>
       <td width="28%" align="left">${sendShareInfo.remark}</td>
       <td width="22%" align="center"><fmt:formatDate value="${sendShareInfo.gmtSend}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
       <td width="20%" align="center"><a href="javascript:vold(0);" onclick="deleteSendInfo(${sendShareInfo.id});">删除</a>|<a href="javascript:vold(0);" onclick="readPage('${sendShareInfo.url}',${sendShareInfo.id });">查看页面</a></td>
       </tr>
       </c:forEach>
    </c:when>
     <c:otherwise>
     <tr><td colspan="5" align="center">没有相关信息</td></tr>
     </c:otherwise>
     </c:choose>
    </tbody>
  </table>
  
  <div class="page_and_btn">
		${userPageShareInfo.page.pageStr }
	</div>

</body>


</html>