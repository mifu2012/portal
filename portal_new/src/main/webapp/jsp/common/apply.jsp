<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml" lang="UTF-8"> 
  <head>
<title>无权限访问子报表</title> 
<link type="text/css" rel="stylesheet" href="../common/css/report.css" />
<link href="<%=path%>/common/css/css.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>common/css/report.css" rel="stylesheet" type="text/css" />
    <style type="text/css"> 
        body {  color: #666; text-align: center; font-family: arial, sans-serif; }
        div.dialog {
            width: 100%;
            height:300px;
     
            border-bottom:1px soild #333; 
        }
        h1 { font-size: 100%; color: #f00; line-height: 1.5em; }
    </style> 
<script type="text/javascript">
/* 查询报表 */
	function indexSearch(){
		var state="1,2,3,4";
		var reportName=document.getElementById('reportName').value.Trim();
		location.href = '<%=path%>/selfApply/indexSearch?&reportName='+reportName+'&state='+state;
	}
	//隐藏或显示条件查询模块 
	String.prototype.Trim = function() 
	{ 
	  return this.replace(/(^\s*)|(\s*$)/g, ""); 
	}

</script>
</head> 
 
<body style="background:#f6f6f6; ">
<!-- 导航栏 -->
	<div class="kpi_position" >
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="78%">
			   <div align="left">
				<div
						style="color: #6a6a6a; padding-left: 30px; padding-top: 5px; font-size:12px">当前位置：系统报表</div></div>
						</td>
				<td width="22%"><table width="100%" border="0" cellspacing="0"
						cellpadding="0" style="padding-top: 5px">
						<tr>
							<td width="37%"><input type="text" id="reportName" value="" title="输入报表名称"
								style="width: 99px" /></td>
							<td width="63%">
								<div style="width: 24px; height: 21px; overflow: hidden">
									<a href="javascript:void(0);" onclick="indexSearch();" title="查询报表"
										target="center_iframe"> <img
										src="<%=path%>/common/images/search_tb.gif" width="24" height="21"
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
<div class="apply_03"></div> 
  <div class="dialog" style="border-bottom:1px soild #333; "> 
  <div align="center" style="padding-top:100px">
  <table width="431" height="176" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="145" valign="top"><div class="apply_05"></div> </td>
    <td width="286"  align="left" valign="top">
    <div style="line-height:150%" class="apply_link">
              无权限访问子报表<br>抱歉！您没有该报表的<span style="color:#526fc2; font-weight:bold">访问权限</span>，请搜索您需要的报表来申请权限！</br>  <p>
    <a href="javascript:applyReport();">搜索报表</a> 
		<a href="javascript:history.back(-1)">返 回</a> 
    </p>
    </div>
    </td>
  </tr>
</table>
  
    </div>
   
    <div style="display:none;text-align: left;" id="err">${exception }</div>
  </div>
  <div style="width:100%; height:1px; background:#ccc; font-size:1px"></div>
  <script type="text/javascript">
 // function applyReport(){
 //     parent.location.href='<%=path%>/UserLogin/mainPage.html?type=1';
 // }
 function applyReport(){
	  var reportName="";
	  var state="1,2,3,4";
      location.href='<%=path%>/selfApply/indexSearch?&reportName='+reportName+'&state='+state;
  }
  </script>
</body> 
</html>
