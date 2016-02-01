<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="UTF-8">
<head>
<script type="text/javascript" src="<%=path%>/common/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		var heigth=0;
		if(navigator.userAgent.indexOf("MSIE")>0){
		//IE
			height=document.body.scrollHeight;
		}else{
		//非IE
			height=document.documentElement.scrollHeight;
		}
	});
</script>
<title>详细事件</title>
</head>
<body>



<table border="0" width="100%">
			<tr>
				<td colspan="3">
					<div class="layer2_mm">
						<div align="left"></div>
					</div>
				</td>
			</tr>
			<tr>
				<td  align="center" colspan="3">
					<div style="width:700px;font-size: 16px;font-weight: bold;font-family: 微软雅黑; ">${bigEvent.title}</div>
				</td>
			</tr>
			<tr>
				<td colspan="3">
					<div class="layer2_mm">
						<div align="left"></div>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="3" align='center' style="font-size: 14px;font-family: 微软雅黑; ">
					<label>发生时间:</label>
					<label >${bigEvent.eventStartDate}</label>
					&nbsp;&nbsp;&nbsp;
					<label>事件类别：</label>
					<label >${bigEvent.eventTypeName}</label>
				</td>
			</tr>
			<tr>
				<td colspan="3">
					<div class="layer2_mm">
						<div align="left"></div>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="3" >
				    <!--事件详情-->
					<div    style="position: relative;width: 99%; height:300px;overflow-y:auto;margin: 0px 0px 0px 10px">
					${bigEvent.content}
					</div>
				</td>
			</tr>
			
		</table>

</body>
</html>