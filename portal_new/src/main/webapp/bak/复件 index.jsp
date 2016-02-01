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
<script type="text/javascript"
	src="<%=path%>/common/js/jquery-1.4.3.min.js"></script>
<script type="text/javascript">
	function changeIframeSize(iframeContentHeight) {
		document.getElementById('center_iframe').height = iframeContentHeight;
	}
	function iframeOnload() {

		var frms = document.getElementById('center_iframe');
		var height = ($.browser.msie || $.browser.opera) ? frms.contentWindow.document.body.scrollHeight
				: frms.contentDocument.body.offsetHeight;
		changeIframeSize(height);
	}
    //显示或关闭进度条
	function showLoading(crtFrm)
	{
		var loadingDiv=document.getElementById("loading_wait_Div");
		if(crtFrm.readyState == "complete")
		{
			//如果加载完成
			if(loadingDiv!=null) loadingDiv.style.display="none";
		}else
		{
			if(loadingDiv!=null) loadingDiv.style.display="block";
		}
	}
</script>
</head>
<body class="indexbody">
	<div>
		<!-- 顶部 -->
		<div>
			<%@ include file="top.jsp"%>
		</div>
		<div>
			<iframe name="center_iframe" marginwidth=0 marginheight=0
				width="100%" scrolling="no"
				src="<%=homePage%>/index/getInto.html"
				frameborder=0 id="center_iframe" onload="iframeOnload();" onreadystatechange="showLoading(this);"></iframe>
		</div>
		<!--底部 -->
		<div>
			<%@ include file="foot.jsp"%>
		</div>
	</div>

</body>
</html>