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
	<div>
		<div
			style="padding: 20px 10px; width: 370px; height: 300px; margin-left: 30px">
			<div class="left-box">
				<p>
					<input type="button" value="添加" style="width: 50px; height: 24px;"
						id="add-eventType" />
				</p>

				<ul class="zb-ul" id="J-searchul-f" style="height: 250px">
					<c:forEach items="${eventTypeList}" var="item">
						<li><a style="width: 138px;">${item.typeId} -
								${item.typeName}</a><a style="width: 48px;"
							href="javascript:editEventTypeById(${item.typeId});">【修改】</a><a
							style="width: 48px;" href=""
							onclick="deleteEventTypeById(${item.typeId})">【删除】</a></li>
					</c:forEach>
				</ul>
			</div>
		</div>
		<div class="fn-clear"></div>
		<div class="footer22"></div>
	</div>
	<script type="text/javascript">
	//删除 主表信息
			function deleteEventTypeById(id){
					if(confirm("确定要删除该记录？")){
						var url = "<%=basePath%>MisEvent/deleteEventType"+id+".html";
						$.get(url,function(data){
							if(data=="success"){
								alert("删除成功！");
								document.location.href="<%=basePath%>/MisEvent/showEventType.html";
							}else{
								alert("删除失败！");
							}
						});
					}
				}
	</script>
	<script type="text/javascript"
		src="<%=basePath%>js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>
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
			page:'<%=basePath%>/MisEvent/addEventType.html'
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
			page:'<%=basePath%>/MisEvent/editEventType'+id+'.html'
			});
		dg.ShowDialog();
	}
	
	
	</script>
</body>
</html>
