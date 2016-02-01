<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改报表</title>
<link type="text/css" rel="stylesheet" href="../css/main.css"/>
<style type="text/css">
body{width:100%;height:100%;background-color: #FFFFFF;text-align: center;}
.input_txt{width:200px;height:20px;line-height:20px;}
.opt_txt{width:205px;height:20px;line-height:20px;}
.info{height:40px;line-height:40px;}
.info th{text-align: right;width:65px;color: #4f4f4f;padding-right:5px;font-size: 13px;}
.info td{text-align:left;}
</style>
</head>
<body>
	<form  action="save" name="reportForm" id="reportForm" target="result" method="post" onsubmit="return checkInfo();">
		<input type="hidden" name="reportId" id="reportId" value="${report.reportId }"/>
		<input type="hidden" name="isReport" id="isReport" value="${report.isReport }"/>
	<table border="0" cellpadding="0" cellspacing="0">
		<tr class="info">
			<th>从属目录:</th>
			<td>
				<select name="parentId" id="parentId" class="opt_txt"  ><!-- onchange="changeMUR()" -->
					<option value="">请选择</option>
					<c:forEach items="${reportList}" var="report1">
					<c:if test="${ report.reportId  != report1.reportId }">
					<option value="${report1.reportId }">${report1.reportName }</option>
					</c:if>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr class="info">
			<th>名　称:</th>
			<td><input type="text" name="reportName" id="reportName" class="input_txt" value="${report.reportName }"/></td>
		</tr>
			<tr class="info">
			<th>报表结构:</th>
			<td>
				<select disabled="disabled" name="isReport" id="isReport" value="${report.isReport }"class="opt_txt" onchange="setMUR()">
					<option value="">请选择</option>
					<option value="0" <c:if test="${report.isReport eq 0}">selected="selected"</c:if>>目录</option>
					<option value="1" <c:if test="${report.isReport eq 1}">selected="selected"</c:if>>报表</option>
				</select>
			</td>
		</tr>
		<tr class="info" id="remark_tr">
			<th>备注:</th>
			<td><textarea style="width: 200px;height: 80px;" name="remark" id="remark">${report.remark }</textarea>
			</td>
		</tr>
		<%-- <tr class="info">
			<th>报表类型:${report.reportType}</th>
			<td>
				<select name="reportType" id="reportType" value="${report.reportType }"class="input_txt" onchange="setMUR()">
					<option value="">请选择</option>
					<option value="1" <c:if test="${report.reportType eq 1}">selected="selected"</c:if>>内部报表</option>
					<option value="2" <c:if test="${report.reportType eq 2}">selected="selected"</c:if>>外部报表</option>
				</select>
			</td>
		</tr> --%>
		<%-- <tr class="info">
			<th>URL:</th>
			<td><input type="text" name="URL" id="URL" class="input_txt" value="${report.URL }"/></td>
		</tr> --%>
	</table>
	</form>
	<iframe name="result" id="result" src="about:blank" frameborder="0" width="0" height="0"></iframe>
	
	<script type="text/javascript" src="../js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript">
		var dg;
		$(document).ready(function(){
			dg = frameElement.lhgDG;
			dg.addBtn('ok','保存',function(){
				/* 为获值设置disabled=false */
				$("#parentId").attr("disabled",false);
				$("#reportForm").submit();
			});
			if($("#reportId").val()!=""){
				var parentId = "${report.parentId}";
				if(parentId==""){
				}else{
					$("#parentId").val(parentId);
				}
			}
			/* disabled只能看，但是获取不到值 */
			$("#parentId").attr("disabled",true);
			setMUR();
		});
		
		//提交校验
		function checkInfo(){
			if($("#reportName").val()==""){
				alert("请输入报表名称!");
				$("#reportName").focus();
				return false;
			}
			 /* 校验是报表是否为目录 
			 if ($("#isReport").val() == "") {
				alert("请选择报表结构!");
				$("#isReport").focus();
				return false;
			}*/
			 
			/* if($("#reportType").val()=="" ){
				if($("#parentId").val()!=""){
					alert("请选择报表类型!");
					$("#reportType").focus();
					return false;
				}
			} */
			return true;
		}
		
		function success(){
			alert("操作成功！");
			if(dg.curWin.document.forms[0]){
				dg.curWin.document.forms[0].action = dg.curWin.location+"";
				dg.curWin.document.forms[0].submit();
			}else{
				dg.curWin.location.reload();
			}
			dg.cancel();
		}
		
		function failed(){
			alert("操作成功！");
			dg.cancel();
		}
		
		 function setMUR(){
			 /* 设置备注 */
			var isReport = document.getElementById("isReport").value;
			if(isReport=="0"){
				$("#remark_tr").hide();
			}else if(isReport=="1"){//报表
				$("#remark_tr").show();
			}
				
			 /* if($("#reportType").val()=="1" ||$("#reportType").val()==""){
				$("#URL").attr("readonly",true);
				$("#URL").val("");
				$("#URL").addClass("input_disabled");
			}else{
				$("#URL").attr("readonly",false);
				$("#URL").removeClass("input_disabled");
			} */
		} 
		
		/* function changeMUR(){
			if($("#parentId").val()!=""){
				$("#reportType").attr("readonly",false);
				$("#reportType").attr("disabled",false);
				$("#reportType").removeClass("input_disabled");
			}else{
				$("#reportType").attr("disabled",true);
				$("#reportType").val("");
				$("#reportType").addClass("input_disabled");
				$("#URL").attr("readonly",true);
				$("#URL").val("");
				$("#URL").addClass("input_disabled");
			}
		} */
	</script>
</body>
</html>