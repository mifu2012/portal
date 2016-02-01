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
<title>报表栏目</title>
<link href="<%=basePath %>common/css/report.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/common/css/css.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="<%=basePath%>common/js/zTree/zTreeStyle.css"/>
<script type="text/javascript" src="<%=basePath%>common/js/jquery-1.4.3.min.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/zTree/jquery.ztree-2.6.min.js"></script>

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
			//zTree.expandAll(true);//默认打开所有结点
			//默认显示第一个报表
			<%-- var nodes=zTree.getNodes();
			if(nodes!=null&&nodes.length>0)
			{
				//报表
				for(var i=0;i<=nodes.length;i++)
				{
					if(nodes[i].isReport==1)
					{
						//报表
						document.getElementById('report_iframe').src = "<%=basePath%>/newReport/previewReport"+nodes[i].id;
						showLoading();
						break;
					}
				}
			} --%>
		});
		function zTreeOnClick(event, treeId, treeNode) {
			var showValue ="";
			var node = treeNode;
			var i=0;
			while(node.parentNode!=null){
				node = node.parentNode;
				showValue = node.name + ">"+showValue;
			}
			showValue=">"+showValue+treeNode.name;
			document.getElementById("showValue").innerHTML=showValue;
			if(treeNode.isReport==1){
				if(treeNode.rptFlag500w==1){
					
					document.getElementById("reportId").value=treeNode.reportId;
					document.getElementById("reportName").value=treeNode.name;
					var but = document.getElementById("saveBut");
					
					$.ajax({
				        type: "POST",                                                                 
				        url:encodeURI("<%=basePath%>/selfApply/getSaveReports.html?&reportId="+ treeNode.reportId),
						success : function(msg) {
							if(msg=="0")
							{
								but.disabled = false;
							    but.value ="收藏报表";
							    but.title = "点击收藏为个人报表";
							    but.style.cursor = "pointer";
							    document.getElementById("saveDiv").style.display='';
								
							}else {
								but.disabled = true;
								but.value ="已收藏";
								but.title = "此报表已收藏";
								but.style.cursor = "text";
								document.getElementById("saveDiv").style.display='';
									
								} 
							}
				   		});
					
					//alert(treeNode.reportId);
					var rptUrl="<%=basePath%>"+treeNode.rptUrl500w;
					if(rptUrl.indexOf("?")!=-1)
					{
						rptUrl+="&rptId="+treeNode.reportId;
					}else
					{
						rptUrl+="?rptId="+treeNode.reportId;
					}
					document.getElementById('report_iframe').src = rptUrl;
					showLoading();
				}else{
					document.getElementById("saveDiv").style.display='none';
					document.getElementById('report_iframe').src = "<%=basePath%>/NewReport/previewReport.html?reportId="+treeNode.id;
				}
			}else if(treeNode.isReport==0){
				//目录(欢迎页面)
				document.getElementById('report_iframe').src = "<%=basePath%>/jsp/newReport/welcomeReport.jsp";
			}
		}
		function changeIframeSize(iframeContentHeight) {
			document.getElementById('report_iframe').height = iframeContentHeight;
			parent.changeIframeSize(iframeContentHeight+60);
		}
		//显示进度条
		function showLoading()
		{
			var loadingDiv=document.getElementById("loading_wait_Div");
			if(loadingDiv!=null) loadingDiv.style.display="";
			if(loadingDiv==null)
			{
				loadingDiv=document.createElement("div");
				loadingDiv.id="loading_wait_Div";
				loadingDiv.style.position="absolute";
				loadingDiv.style.left="45%";
				loadingDiv.style.top="40%";
				loadingDiv.innerHTML="<img src='<%=request.getContextPath()%>/common/images/loading.gif'>";
					document.body.appendChild(loadingDiv);
				}
				var iframe = document.getElementById('report_iframe');
				if (iframe.attachEvent) {
					iframe.attachEvent("onload", function() {
						loadingDiv.style.display = "none";
					});
				} else {
					iframe.onload = function() {
						loadingDiv.style.display = "none";
					};
				}
			}
		/* 查询报表 */
		function indexSearch(){
			var state="1,2,3,4";
			var reportName=document.getElementById('reportName').value.Trim();
			location.href = '<%=path%>/selfApply/indexSearch?reportName='+reportName+'&state='+state;
		}
		//隐藏或显示条件查询模块 
		String.prototype.Trim = function() 
		{ 
		  return this.replace(/(^\s*)|(\s*$)/g, ""); 
		}
		function changit(obj)
		{
           var tree=document.getElementById("treeDivTd");
		   if(tree.style.display=="none")
			{
               tree.style.display="";//显示
               obj.src="<%=basePath%>/common/images/jiao-3.gif";
			}else
			{
               tree.style.display="none";//显示
               obj.src="<%=basePath%>/common/images/jiao-4.gif";
			}
		}
		
		function collect(){
			var reportId = document.getElementById("reportId").value;
			var reportName = document.getElementById("reportName").value;
 			if (confirm("确定要收藏？")) {
				$.ajax({
			        type: "POST",                                                                 
			        url:encodeURI("<%=basePath%>/selfApply/collectReport.html?&reportId="
									+ reportId + "&reportName=" + reportName),
					success : function(msg) {
						if(msg=="success")
						{
							alert("收藏成功");
							document.getElementById("saveBut").disabled = true;
						}else if(msg=="failed")
							{
								alert("收藏失败");
							} 
						}
			   		});
			 	} 

		}
		</script>
</head>

<body  style="background:#fff">

<input value="" type="hidden" id="reportId" />
<!-- 导航栏 -->
	<div class="kpi_position" >
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="78%"><div
						style="color: #6a6a6a; padding-left: 30px; padding-top: 5px; font-size:12px">当前位置：${reportMenuName}<span id="showValue"></span></div></td>
				<td width="22%"><table width="100%" border="0" cellspacing="0"
						cellpadding="0" style="padding-top: 5px">
						<tr>
							<td width="37%"><input type="text" id="reportName" value="" title="输入报表名称"
								style="width: 99px" /></td>
							<td width="63%">
								<div style="width: 24px; height: 21px; overflow: hidden">
									<a href="javascript:void(0);" onclick="indexSearch();" title="查询报表"
										target="center_iframe"> <img
										src="../common/images/search_tb.gif" width="24" height="21"
										border="0" />
									</a>
								</div>
							</td>
						</tr>
					</table></td>
			</tr>
		</table>
	</div>
	<!-- 导航栏 -->
	<div class="report_home">
	
	
	
<div style=" margin-top:20px"></div>
  <table width="98%" height="100%"  style="border:1px solid #a5a9b4" align="center" cellpadding="0" cellspacing="0" border="0">
    <tr>
      <td width="18%" valign="top" style="overflow:auto; border-right:1px solid #a5a9b4;display:;" id="treeDivTd">
	  	<ul id="tree" class="tree"></ul>
	  </td>
	  <td width="1px">
	     <img src="<%=basePath%>/common/images/jiao-3.gif" onclick="javascript:changit(this);" style="cursor:pointer;" id="midDivImg"/>
	  </td>
      <td  align="center" valign="top">
      	<div style="margin-left:2px">
      	<div id="saveDiv" style="position: absolute; right: 28px; top: 60px;display: none;">
		
				<input id="saveBut"  type="button" Name="collect_report" Value="收藏报表"
					title='点击收藏为个人报表' style='cursor: pointer;'
					onclick="collect()" />
			
		
		
	</div>
	    <iframe id='report_iframe' src="<%=basePath%>/jsp/newReport/welcomeReport.jsp" height="100%" width="98%" scrolling="no" frameborder="0"></iframe>
	    </div>
	  </td>
    </tr>
  </table>
</div>
</body>
</html>
