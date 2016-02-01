<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="UTF-8">
<head>
<title>报表欢迎页</title>
<link type="text/css" rel="stylesheet"
	href="<%=basePath%>/common/css/report.css" />
<script type="text/javascript"
	src="<%=basePath%>/common/js/jquery-1.4.3.min.js"></script>
<style type="text/css">
body {
	color: #666;
	text-align: center;
	font-family: arial, sans-serif;
}

div.dialog {
	width: 100%;
	height: 300px;
	border-bottom: 1px soild #333;
}

h1 {
	font-size: 100%;
	color: #f00;
	line-height: 1.5em;
}
</style>
<script type="text/javascript">
	$(document).ready(function changeIframeSize() {
		$(document).ready(function(){
			var heigth=0;
			if(navigator.userAgent.indexOf("MSIE")>0){
			//IE
				height=document.body.scrollHeight;
			}else{
			//非IE
				height=document.documentElement.scrollHeight;
			}
			if (height > 516) {
				parent.changeIframeSize(height+5);
			} else {
				parent.changeIframeSize(516);
			}
			
		});
	});
</script>
</head>

<body style="background: #f6f6f6;">
	<div class="apply_03"></div>
	<div class="dialog" style="border-bottom: 1px soild #333;">
		<table width="100%" height="500" border="0" cellpadding="0"
			cellspacing="0">
			<tr>
				<td width="145" valign="center"><div>
						<img src="<%=basePath%>/common/images/welcom_report.gif" />
					</div>
				</td>
			</tr>
		</table>
	</div>
	<div style="width: 100%; height: 1px; background: #ccc; font-size: 1px"></div>
</body>
</html>
