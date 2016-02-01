<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>保存结果</title>
</head>
<body>
	<script type="text/javascript">
		var msg = "${msg}";
        try
        {
    		if(msg=="success" || msg==""){
    		    parent.addChartSuccess("${reportChartId}","${widgh}","${height}");
    		}else{
    			parent.addChartFailed();
    		}
    		
        }catch(e)
        {
        	alert("当前表单未定义success()或failed()函数:"+e.message);
        }
	</script>
</body>
</html>