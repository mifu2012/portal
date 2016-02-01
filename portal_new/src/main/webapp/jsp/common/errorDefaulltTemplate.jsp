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
<title>没有权限</title> 
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
</head> 
 
<body style="background:#f6f6f6; ">
<div class="apply_03"></div> 
  <div class="dialog" style="border-bottom:1px soild #333; "> 
  <div align="center" style="padding-top:100px">
  <table width="431" height="176" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="145" valign="top"><div class="apply_05"></div> </td>
    <td width="286"  align="left" valign="top">
    <div style="line-height:150%" class="apply_link">
       <span style="color:#526fc2; font-weight:bold">系统参数设置错误</span> <br/>抱歉，${var_template_msg}，<br />请联系系统管理员！  <p>
        <a href="<%=basePath %>/quit.html">退出</a> 
    </p>
    </div>
    </td>
  </tr>
</table>
  
    </div>
   
    <div style="display:none;text-align: left;" id="err">${exception }</div>
  </div>
  <div style="width:100%; height:1px; background:#ccc; font-size:1px"></div>
</body> 
</html>
