<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加报表</title>
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
	<table border="0" cellpadding="0" cellspacing="0">
		<tr class="info">
			<th>从属目录:</th>
			<td>
				<select name="parentId" id="parentId" class="opt_txt"  onchange="changeSelect(this);">
					<option value="" >= 请选择 =</option>
					<c:forEach items="${reportList}" var="report">
					<c:if test="${report.isReport eq 0 && report.rptFlag500w!=1}">
					<option  value="${report.reportId }" id="${report.parentId }">${report.reportName }</option>
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
			<th>目录/报表:</th>
			<td>
				<select  name="isReport" id="isReport" value="${report.isReport }"class="opt_txt" onchange="setMUR()">
				    <!--
					<option value="">请选择</option>
					-->
					<option  id="mulu" value="0" <c:if test="${report.isReport eq 0}">selected="selected"</c:if> style="color: red;">目录</option>
					<!--
					<option value="1" <c:if test="${report.isReport eq 1}">selected="selected"</c:if>>报表</option>
					-->
				</select>
			</td>
		</tr>
		<tr class="info" id="remark_tr">
			<th>备注:</th>
			<td><textarea style="width: 200px;height: 80px;" name="remark" id="remark"></textarea>
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
		</tr>
		<tr class="info">
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
				$("#reportForm").submit();
			});
			/* if($("#reportId").val()!=""){
				var parentId = "${report.parentId}";
				var reportType = "${report.reportType}";
				if(parentId==""){
					$("#parentId").attr("disabled",true);
					$("#reportType").attr("disabled",true);
				}else{
					$("#parentId").val(parentId);
				}
			} */
			
			setMUR();
		});
		function changeSelect(obj){
			var isReport=document.getElementById("isReport");
                isReport.length=0;
			if(obj.value==0||obj.value.length==0)
			{
				var text="目录";
				var value="0";
				var option=new Option(text,value);
				option.id=text;
                isReport.add(option);
                $("#"+text).css("color","red");
                $("#remark_tr").hide();
				return;
			}
			//父级报表
			if(obj.options[obj.selectedIndex].id=="")
			{
				var text="目录";
				var value="0";
				var option=new Option(text,value);
				option.id=text;
                isReport.add(option);
               
				var text="报表";
				var value="1";
			    var option=new Option(text,value);
				option.id=text;
			   isReport.add(option);
			}else
			{
			  //为二级目录
					var text="报表";
					var value="1"
				    var option=new Option(text,value);
					option.id=text;
				    isReport.add(option);
			}
			for(var i=0;i<isReport.length;i++){
				if(isReport[i].text=="目录"){
					$("#"+isReport[i].text).css("color","red");
				}
			}
			/*
			for(var i=0;i<obj.length;i++){
				if(obj[i].selected==true){
					if(obj[i].id==""){
						document.getElementById("mulu").disabled=false;
					}else{
						document.getElementById("mulu").disabled=true;
					}
				}
			}
			*/
		}
		
		function setMUR() {
			/* 设置备注 */
			var isReport = document.getElementById("isReport").value;
			if(isReport=="0"){
				$("#remark").val("");
				$("#remark_tr").hide();
				//$("#remark").attr("disabled", true);
			}else if(isReport=="1"){//报表
				//$("#remark").attr("disabled", false);
				$("#remark_tr").show();
			}
			
			/* if ($("#parentId").val() == "") {
				$("#isReport").val("0");
			}else if($("#parentId").val() != ""){
				$("#isReport").val("");
			} */
			
			
			/* //丛属报表为空:报表类型、URL不可输入
			if ($("#parentId").val() == "") {
				$("#reportType").attr("disabled", true);
				$("#reportType").val("");
				//$("#URL").attr("disabled", true);
				$("#URL").attr("readonly", true);
				$("#URL").val("");
				$("#URL").addClass("input_disabled");
			} else {
				$("#reportType").attr("disabled", false);
			}
			//报表类型
			if ($("#reportType").val() == "1" || $("#reportType").val() == "") {
				$("#URL").attr("readonly", true);
				$("#URL").val("");
				$("#URL").addClass("input_disabled");
			} else {
				$("#URL").attr("readonly", false);
				$("#URL").removeClass("input_disabled");
			} */
		}

		//保存校验
		function checkInfo() {
			//报表名称
			if ($("#reportName").val() == "") {
				alert("请输入报表名称!");
				$("#reportName").focus();
				return false;
			}
			if ($("#isReport").val() == "") {
				alert("请选择报表结构!");
				$("#isReport").focus();
				return false;
			}
			//从属报表不为空
			/* if ($("#parentId").val() != "") {
				if ($("#reportType").val() == "") {
					alert("请选择报表类型");
					$("#reportType").focus();
					return false;
				}
				//外部报表
				if ($("#reportType").val() == "2" && $("#URL").val() == "") {
					alert("请输入报表URL");
					$("#URL").focus();
					return false;
				}
			} */
			return true;
		}

		function success() {
			alert("操作成功！");
			if (dg.curWin.document.forms[0]) {
				dg.curWin.document.forms[0].action = dg.curWin.location + "";
				dg.curWin.document.forms[0].submit();
			} else {
				dg.curWin.location.reload();
			}
			dg.cancel();
		}

		function failed() {
			alert("操作失败！");
		}
	</script>
</body>
</html>