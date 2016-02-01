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
<title>无自助服务报表</title> 
    <style type="text/css"> 
        body { background-color: #fff; color: #666; text-align: center; font-family: arial, sans-serif; }
        div.dialog {
            width: 80%;
            height:300px;
            padding: 1em 4em;
            margin: 4em auto 0 auto;
            border: 0px solid #ccc;
            border-right-color: #999;
            border-bottom-color: #999;
        }
        h1 { font-size: 100%; color: #f00; line-height: 1.5em; }
    </style> 
</head> 
 
<body> 
  <div class="dialog"> 
    <h1>无自助服务报表</h1> 
    <p>抱歉！您没有自助服务报表，请搜索您需要的报表！</p> 
    <p>
    <a href="javascript:applyReport();">搜索报表</a>&nbsp; 
	<a href="javascript:history.back(-1)">返 回</a> 
    </p> 
    <div style="display:none;text-align: left;" id="err">${exception }</div>
  </div>
  
  <script type="text/javascript">
  function applyReport(){
	  var reportName="";
	  var state="1,2,3,4";
      location.href='<%=path%>/selfApply/indexSearch?&reportName='+reportName+'&state='+state;
  }
  </script>
</body> 
</html>
