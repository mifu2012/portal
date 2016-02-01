<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<title>My Test</title>
<link href="<%=basePath %>/css/report.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="<%=basePath%>css/main.css"/>
<link type="text/css" rel="stylesheet" href="<%=basePath%>js/zTree/zTreeStyle.css"/>
<script type="text/javascript" src="<%=basePath%>js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/zTree/jquery.ztree-2.6.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		var setting = {
		    showLine: true,
		    callback:{
		    	click: zTreeOnClick
		    }
		};
		var zn = '${zTreeNodes}';
		var zTreeNodes = eval(zn);
		var zTree = $("#tree").zTree(setting, zTreeNodes);
	   	zTree.expandAll(true);//默认打开所有结点
		//默认取屏幕高度
		//document.getElementById('center_iframe').height=(window.screen.availHeight-285)+"px";//
	});
	function zTreeOnClick(event, treeId, treeNode) {
		//if(!treeNode.checked){
		if(treeNode.isReport==1){
			//报表
		    //document.getElementById('center_iframe').src = "<%=basePath%>/reportConfig/collocate"+treeNode.id;
			//document.getElementById('center_iframe').src = "<%=basePath%>/reportConfig/toReportConfig"+treeNode.id;
			//document.getElementById('center_iframe').src = "<%=basePath%>/NewReport/designReportTemplate"+treeNode.id;
			window.open("<%=basePath%>/NewReport/designReportTemplate"+treeNode.id);
		}else{
			//报表目录
			document.getElementById('center_iframe').src ="<%=basePath%>/jsp/reportConfig/welcomeConfig.jsp";
		}
		//document.getElementById('center_iframe').height=(window.screen.availHeight-215)+"px";
	}
			
	function changeIframeSize(iframeContentHeight) {
		document.getElementById('center_iframe').height = iframeContentHeight;
	}
	
	$(document).ready(function(){
		var height = $("#mainFrame", parent.window.document).css("height").replace("px", "")-5;
		if (height > $("table").css("height").replace("px", "")) {
			$("table").css("height", height+"px");
			$("#tree").css("height", height-15+"px");
		}
		$("#tree").css("width", $("#treeTd").css("width"));
	});
</script>
</head>

<body>
<div class="report_home" style="width: 99.8%;height: 100%">
  <table width="100%" height="100%" style="border:1px solid #a5a9b4" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td width="18%" valign="top" style="border-right:1px solid #a5a9b4;" id="treeTd">
	  	<ul id="tree" class="tree" style="border:0px solid;width: 200px;overflow-x: scroll;height: 625px;"></ul>	  	
	  </td>
      <td width="82%" align="left" valign="top" height="100%">
      	<div style="margin-left:0px">
	    	<iframe id='center_iframe' src="<%=basePath%>/jsp/reportConfig/welcomeConfig.jsp" height="625" width="100%" scrolling="no" frameborder="0"></iframe>
	    </div>
	  </td>
    </tr>
  </table>
</div>
</body>
</html>
