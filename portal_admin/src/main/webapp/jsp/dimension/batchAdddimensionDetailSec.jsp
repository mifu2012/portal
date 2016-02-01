<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<title>批量新增</title>
<link href="<%=basePath%>css/css.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>js/jquery-1.5.1.min.js" type="text/javascript"></script>
</head>
<script type="text/javascript">
<!--
	var isOk=false;
//-->
</script>
<body class="indexbody" style="background: #ffffff" >
			<!-- 维度从表信息添加或修改 -->
			<form id="dimensionDetaiform" method="post"	action="<%=basePath%>/Dimensionality/batchSaveDimensionDetailSec.html" target="result">
					
						<div style="height: 35px"><input type="hidden" id="primaryKeyId" name="id"
							value="" style="display: none;"></div>
						<table style="width:100%;height:100%;" border="0" width="100%" height="100%">
							<tr>
								<td valign="top">
									<table  border="0" width="100%" height="100%"  cellspacing="3" cellpadding="3">
										<tr width="20%">
											<td  align='center'><font color="red">*</font>维度id:</td>
											<td ><input type="text" id="parent_id" style="background-color: #f1f1f1"
												name="parent_id" maxlength="50" value="${parentId}"
												readonly />
													 <input type='button' name="testQutyBtn" style="width:50px;" value="执行SQL" id="testQutyBtn" onclick="testSQL();" title='执行SQL语句，选择维度值/显示列'>
												</td>
										</tr>
										<tr>
											<td  align='center'><font color="red">*</font>查询SQL:</td>
											<td >
											 <textarea style="width:79%;height:80px;" id="querySql" name="querySql" onchange="isOk=false;"></textarea>
											</td>
										</tr>
										<tr>
											<td   align='center' height="25px"><font color="red">*</font>维度数值:</td>
											<td >
											  <select name="key" id="key" style="width:80%;">
											  </select>
										</tr>
										<tr>
											<td  align='center'><font color="red">*</font>维度名称:</td>
											<td >
											  <select name="value" id="value" style="width:80%;">
											  </select>
										</tr>
									</table>
								</td>
							</tr>
						</table>
				</form>
				<iframe name="result" id="result" src="about:blank" frameborder="0" width="0" height="0"></iframe>
	<script type="text/javascript">
	var dg;
	$(document).ready(function() {
		dg = frameElement.lhgDG;
		dg.addBtn('ok', '保存', function() {
			if(isOk==false)
			{
				alert("请重新执行SQL语句，再选择维度值的字段");
				return;
			}
			var parent_id=$("#parent_id").val();
			if(parent_id==null||parent_id.length==0)
			{
				alert("没有选择维度");
				return;
			}
			var dimensionKey = $("#key").val();
			var dimensionValue = $("#value").val();
			
			if(dimensionKey==""){
				alert("详细维度key不能为空!");
				return false;
			}
			if(dimensionValue==""){
				alert("详细维度名称不能为空!");
				return false;
			}
			$("#dimensionDetaiform").submit();
		});
		
	});

function testSQL(querySql)
	{
	   var querySql=$("#querySql").val();
	   if(querySql==null||querySql.length==0)
		{
		    alert("请输入SQL语句");
			return;
		}
       $.ajax({
	        type: "POST",                                                                 
	        url:encodeURI("<%=basePath%>/Dimensionality/testReportSql.html?1=1&querySql="
							+ querySql + "&random=" + Math.random()),
					success : function(columnConfigList) {
						var dimensionKey1=document.getElementById("key");
						    dimensionKey1.length=0;
						var dimensionValue1=document.getElementById("value");
						    dimensionValue1.length=0;
                       if(columnConfigList==null||columnConfigList.length==0)
						{
						      alert('测试失败，请检查SQL语句');
							  isOk=false;
							  return;
						}
						for(var i=0;i<columnConfigList.length;i++)
						{
						   var column=columnConfigList[i];
                           dimensionKey1.add(new Option(column.columnLabel,column.columnLabel));
						   dimensionValue1.add(new Option(column.columnLabel,column.columnLabel));
						}
						isOk=true;
					}
       });
	}
	
	</script>
	<script type="text/javascript">
	function success(){
		alert("操作成功！");
		try
		{
			//重新查询数据
		   dg.curWin.showDimensionInfoSec("${parentId}");
		}catch (e)
		{
			if(dg.curWin.document.forms[0]){
				dg.curWin.document.forms[0].action = dg.curWin.location+"";
				dg.curWin.document.forms[0].submit();
			}else{
				dg.curWin.location.reload();
			}
		}
		dg.cancel();
	}
	
	function failed(){
		alert("操作失败！");
	}
	</script>
	
</body>
</html>
