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
	<form action="<%=rootPath%>/comkpiinfo1/save" name="comkpiinfoForm" id="comkpiinfoForm" target="result" method="post" onsubmit="return checkInfo();">
	<table border="0" cellpadding="0" cellspacing="0" style="width: 400px; line-height: 40px">
	<tr>
	<td style="width: 135px; text-align: right;font-weight: bold; font-size: 14px;color:#666; padding-right: 5px"><font color="red">*</font>通用指标CODE:</td>
	<td style="width: 270px;text-align: left;"><input style="ime-mode:disabled" type="text" name="comKpiCode" id="comKpiCode" class="input_txt" onkeyup="convert()" onblur="validateComKpiCode(this.value);"/></td>
	</tr>
	<tr>
			<td style="width: 135px; text-align: right;font-weight: bold; font-size: 14px;color:#666; padding-right: 5px"><font color="red">*</font>通用指标名称:</td>
			<td style="width: 270px;text-align: left;"><input type="text" name="comKpiName" id="comKpiName" class="input_txt" onkeydown="butOnClick()"/></td>
		</tr>
		<tr style="display:none;">
			<td style="width: 135px; text-align: right;font-weight: bold; font-size: 14px;color:#666; padding-right: 5px"><font color="red">*</font>是否在龙虎榜显示:</td>
			<td style="width: 270px;text-align: left;"><select onchange="change(this);" name="isShowRank" >
							<option value="1">是</option>
							<option value="0">否</option>
						</select>
			</td>
		</tr>
		<tr style="display:none;">
			<td style="width: 135px; text-align: right;font-weight: bold; font-size: 14px;color:#666; padding-right: 5px"><font color="red">*</font>在龙虎榜显示顺序:</td>
			<td style="width: 270px;text-align: left;"><input type="text" name="rankShowOrder" id="rankShowOrder"   class="input_txt" 
			 onkeyup="value=value.replace(/[^0-9^.]D*$/,'')" onkeypress="value=value.replace(/[^0-9^.]D*$/,'')" style="ime-mode:disabled" value="0"/></td>
		</tr>
	</table>
	
	
	</form>
	<iframe name="result" id="result" src="about:blank" frameborder="0" width="0" height="0"></iframe>
	
	<script type="text/javascript" src="../js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript">
	function change(obj){
		if(obj.value==0){
			document.getElementById("rankShowOrder").value=0;
			document.getElementById("rankShowOrder").disabled=true;
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
		});
		//输入字母自动转为大写   输入comKpiCode时的操作
		function convert(){
			var comKpiCode=$("#comKpiCode").val();
			comKpiCode=comKpiCode.toUpperCase();
			$("#comKpiCode").val(comKpiCode);
		}
		//提交表单校验
		function checkInfo(){
			var comKpiCode=$("#comKpiCode").val();
			if(comKpiCode==""){
				alert("请输入指标CODE!");
				$("#comKpiCode").focus();
				return false;
			}
			 if(comKpiCode.match(/[^0-9A-Za-z]/g)){
				  alert("通用指标只能为数字和字母");
				  $("#comKpiCode").val("");
				  $("#comKpiCode").focus();
				  return false;
			 } 
		    	var rankShowOrderval=$("#rankShowOrder").val();
		    	var strP=/^\d+(\.\d+)?$/; 
		    	if(!strP.test(rankShowOrderval)){
		    		alert("显示顺序只能为数字");
		    		return false; 
		    	}
			if($("#comKpiName").val().replace(/(^\s*)/g, "")=="" ){
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
				alert("添加成功");
				dg.curWin.location.reload();
			}
			dg.cancel();
		}
		
		function failed(){
			alert("新增失败，该指标已存在！");
		}
		function validateComKpiCode(val) {  
		       // 如果为空或者输入空格执行   
		       if(val==null||val.length==0) return;
		       $.ajax({
			        type: "POST",                                                                 
			        url:encodeURI("<%=rootPath%>/comkpiinfo1/alidatecomKpiCode.html?comKpiCode="
									+ val + "&random=" + Math.random()),
							success : function(msg) {
								if(msg=="1"){
				             		alert("通用指标CODE[ "+val+" ]已存在，请重新输入！");
				             		$("#comKpiCode").val("");
				             	 }  
							}
		       });  
		   }
	</script>
</body>
</html>