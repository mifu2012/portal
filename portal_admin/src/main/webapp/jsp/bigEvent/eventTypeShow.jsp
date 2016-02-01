<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<title>Example</title>
<link href="<%=basePath%>css/css.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>js/jquery-1.5.1.min.js" type="text/javascript"></script>
</head>
<body>
<body class="indexbody">
	<div >
		<div
			style="padding: 20px 10px; width: 370px; height: 300px; margin-left: 30px">
			<div class="left-box">
				<p>
					<input type="button" value="添加" style="width: 50px; height: 24px;"
						id="add-eventType" />
				</p>
<!--                 <div style="height: 250px;width:95%;overflow:auto;border:1px solid #000000;"> -->
<!-- 				   <table border="0" width="100%"> -->
<%-- 				      <c:forEach items="${eventTypeList}" var="item"> --%>
<!-- 				      <tr style='background:#ffffff;' onmouseover="this.style.background='#3399ff';"  onmouseout="this.style.background='#ffffff';"> -->
<!-- 					     <td> -->
<%-- 						    ${item.typeId} - ${item.typeName} --%>
<!-- 						 </td> -->
<!-- 						 <td width="15%"> -->
<%-- 						   <a style="width: 48px;"  href="javascript:void(0);" onclick="javascript:editEventTypeById(${item.typeId});" >【修改】</a> --%>
<!-- 						 </td> -->
<!-- 						 <td  width="15%"> -->
<%-- 						   <a style="width: 48px;" href="javascript:void(0);" onclick="deleteEventTypeById(${item.typeId})" >【删除】</a> --%>
<!-- 						 </td> -->
<!-- 					  </tr> -->
<%-- 					  </c:forEach> --%>
<!-- 				   </table> -->
<!-- 				</div> -->
				<ul class="zb-ul" id="J-searchul-f" style="height: 250px">
					<c:forEach items="${eventTypeList}" var="item">
						<li><a style="width: 128px;">${item.typeId} - ${item.typeName}</a>
							<a style="width: 48px;" href="javascript:editEventTypeById(${item.typeId});">【修改】</a>
							<a style="width: 48px;" href="javascript:deleteEventTypeById(${item.typeId})">【删除】</a>
						</li>
					</c:forEach>
				</ul>
				<!--
				<ul class="zb-ul" id="J-searchul-f" style="height: 250px;width:95%;">
					<c:forEach items="${eventTypeList}" var="item">
						<li><a style="width: 138px;background:#ffffff;" onmouseover="this.style.background='#3399ff';"  onmouseout="this.style.background='#ffffff';">${item.typeId} - ${item.typeName}</a><a style="width: 48px;" href="javascript:editEventTypeById(${item.typeId});" >【修改】</a><a style="width: 48px;" href="" onclick="deleteEventTypeById(${item.typeId})" >【删除】</a></li>
					</c:forEach>
				</ul>
				-->
			</div>
		</div>
		<div class="fn-clear"></div>
		<div class="footer22"></div>
	</div>
	<script type="text/javascript">
// 	删除 主表信息
				function deleteEventTypeById(id){
					if(!confirm("确定要删除该记录？")) return;
					
						var url = "<%=basePath%>BigEvent/deleteEventType"+id+".html";
						$.get(url,function(data){
							if(data=="success"){
								document.location.href="<%=basePath%>/BigEvent/showEventType.html";
							}
						});
						
					return;
				}
	</script>
	<script type="text/javascript" src="<%=basePath%>js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>
	<script type="text/javascript">
	$("#add-eventType").bind("click",function(){ 
		var dg = new $.dialog({
			title:'添加事件类型',
			width:400,
			height:300,
			iconTitle:false,
			cover:true,
			maxBtn:true,
			xButton:true,
			resize:true,
			page:'<%=basePath%>/BigEvent/addEventType.html'
			});
		dg.ShowDialog();		
	});
	
	function editEventTypeById(id){
		var dg = new $.dialog({
			title:'修改事件类型',				
			width:400,
			height:300,
			iconTitle:false,
			cover:true,
			maxBtn:true,
			resize:true,
			page:'<%=basePath%>/BigEvent/editEventType'+id+'.html'
			});
		dg.ShowDialog();
	}
	
	
	</script>
</body>
</html>
