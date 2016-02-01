<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
  String rootPath=request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>My Test</title>
<link type="text/css" rel="stylesheet" href="../css/main.css"/>
<style type="text/css">
body{width:100%;height:100%;background-color: #FFFFFF;text-align: center;}
.input_txt{width:200px;height:20px;line-height:20px;}
.info{height:40px;line-height:40px;}
.info th{text-align: right;width:65px;color: #4f4f4f;padding-right:5px;font-size: 13px;}
.info td{text-align:left;}
</style>
</head>
<body>
	<form action="<%=rootPath%>/comkpiinfo1/save?isUpdate=1" name="comkpiinfoForm" id="comkpiinfoForm" target="result" method="post" onsubmit="return checkInfo();">
	<table border="0" cellpadding="0" cellspacing="0" style="width: 400px; line-height: 40px">
	<tr style="height: 50px">
	</tr>
	<tr>
	<td style="width: 130px; text-align: right;font-weight: bold; font-size: 14px;color:#666; padding-right: 5px">通用指标CODE:</td>
	<td style="width: 270px;text-align: left;"><input type="text" name="comKpiCode" id="comKpiCode" class="input_txt" value="${comkpiinfo.comKpiCode}"/></td>
	</tr>
	<tr>
			<td style="width: 130px; text-align: right;font-weight: bold; font-size: 14px;color:#666; padding-right: 5px">通用指标名称:</td>
			<td style="width: 270px;text-align: left;"><input type="text" name="comKpiName" id="comKpiName" class="input_txt" value="${comkpiinfo.comKpiName}" onkeydown="butOnClick()"/></td>
		</tr>
		<tr style="display:none;">
			<td style="width: 130px; text-align: right;font-weight: bold; font-size: 14px;color:#666; padding-right: 5px">是否在龙虎榜显示:</td>
			<td style="width: 270px;text-align: left;"><select id="selectShow" onchange="change(this);" name="isShowRank" >
							<option value="1" <c:if test="${comkpiinfo.isShowRank eq 1 }">selected</c:if>>是</option>
							<option value="0" <c:if test="${comkpiinfo.isShowRank eq 0 }">selected</c:if>>否</option>
						</select>
			</td>
		</tr>
		<tr style="display: none">
			<td style="width: 130px; text-align: right;font-weight: bold; font-size: 14px;color:#666; padding-right: 5px">在龙虎榜显示顺序:</td>
			<td style="width: 270px;text-align: left;"><input type="text" name="rankShowOrder" id="rankShowOrder" class="input_txt" 
			maxlength="3" 
			value="${comkpiinfo.rankShowOrder}" onkeyup="value=value.replace(/[^0-9^.]D*$/,'')" onkeypress="value=value.replace(/[^0-9^.]D*$/,'')" style="ime-mode:disabled"/></td>
		</tr>
	</table>
	
	
	</form>
	<iframe name="result" id="result" src="about:blank" frameborder="0" width="0" height="0"></iframe>
	
	<script type="text/javascript" src="../js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript">
	window.onload = function (){
		if(document.getElementById("selectShow").value==0){
			document.getElementById("rankShowOrder").disabled=true;
		}
		
	}
	function change(obj){
		if(obj.value==0){
			document.getElementById("rankShowOrder").disabled=true;
			document.getElementById("rankShowOrder").value=0;
		}else{
			document.getElementById("rankShowOrder").disabled=false;
		}
		
	}
	function disableEnterKey(e) {  
        var key;  
        if (window.event)  
            key = window.event.keyCode; //IE  
       else  
           key = e.which; //firefox       
  
        return (key != 13);  
    }
    function butOnClick() { 
    if (event.keyCode == 13) { 
    	$("#comkpiinfoForm").submit(); 
       return false; 
     } 
    }
		var dg;
		$(document).ready(function(){
			dg = frameElement.lhgDG;
			dg.addBtn('ok','保存',function(){
				$("#comkpiinfoForm").submit();
			});
			if($("#comKpiCode").val()!=""){
				$("#comKpiCode").attr("readonly","readonly");
				$("#comKpiCode").css("color","gray");
			}
		});
		
		function checkInfo(){
			if($("#comKpiCode").val()==""){
				alert("请输入指标CODE!");
				$("#comKpiCode").focus();
				return false;
			}
			var rankShowOrderval=$("#rankShowOrder").val();
	    	var strP=/^\d+(\.\d+)?$/; 
	    	if(!strP.test(rankShowOrderval)){
	    		alert("显示顺序只能为数字");
	    		return false; 
	    	}
			if($("#comKpiCode").val()!="" && $("#comKpiName").val()==""){
				alert("请输入指标名称!");
				$("#comKpiName").focus();
				return false;
			}
			return true;
		}
		
		function success(){
			if(dg.curWin.document.forms[0]){
				dg.curWin.document.forms[0].action = dg.curWin.location+"";
				dg.curWin.document.forms[0].submit();
			}else{
				alert("修改成功");
				dg.curWin.location.reload();
			}
			dg.cancel();
		}
		
		function failed(){
			alert("修改失败！");
		}
	</script>
</body>
</html>