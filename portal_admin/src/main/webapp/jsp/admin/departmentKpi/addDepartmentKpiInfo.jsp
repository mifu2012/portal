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
<title>My Test</title>
<link type="text/css" rel="stylesheet" href="../css/main.css"/>
<script type="text/javascript" src="<%=basePath %>/js/datePicker/My98MonthPicker.jsp?id=123"></script>
<script type="text/javascript" src="<%=basePath %>/js/jquery-1.5.1.min.js"></script>
<style type="text/css">
body{width:100%;height:100%;background-color: #FFFFFF;text-align: center;}
.input_txt{width:120px;height:20px;line-height:20px;}
.info{height:40px;line-height:40px;}
.info th{text-align: right;color: #4f4f4f;padding-right:5px;font-size: 13px;}
.info td{text-align:left;}
</style>
</head>
<body>
<div style="height: 10px;"></div>
	<form action="save" name="deptInfoForm" id="deptInfoForm" target="result" method="post" onsubmit="return checkInfo();">
	<input type="hidden"  id="id" name="id" value="${departmentKpiInfo.id }"/>
	<table border="0" cellpadding="0" cellspacing="0">
		<tr class="info">
			<th width="20%">部门名称:</th>
			<td width="30%">
			<select id="deptName" name="deptName" onchange="" >
			<option value="" >请选择</option>
			<option value="运营部" <c:if test="${departmentKpiInfo.deptName  eq '运营部'}">selected</c:if>>运营部</option>
			<option value="产品部" <c:if test="${departmentKpiInfo.deptName  eq '产品部'}">selected</c:if>>产品部</option>
			<option value="财务部" <c:if test="${departmentKpiInfo.deptName  eq '财务部'}">selected</c:if>>财务部</option>
			<option value="市场部" <c:if test="${departmentKpiInfo.deptName  eq '市场部'}">selected</c:if>>市场部</option>
			<option value="客服部" <c:if test="${departmentKpiInfo.deptName  eq '客服部'}">selected</c:if>>客服部</option>
			</select>
			</td>
			<th width="20%">&nbsp;</th>
			<td width="30%">&nbsp;</td>
		</tr>
		
		<tr class="info">
			<th width="20%">指标CODE:</th>
			<td width="30%">
			<input class="input_txt" id="kpiCode" name="kpiCode" style="ime-mode:disabled"  type="text"  value="${departmentKpiInfo.kpiCode}"/>
			</td>
			<th width="20%">指标名称:</th>
			<td width="30%">
			<input  class="input_txt"  id="kpiName" name="kpiName" type="text" value="${departmentKpiInfo.kpiName }" />
			</td>
		</tr>
		<tr class="info">
			<th width="20%">实际值:</th>
			<td width="30%">
			<input class="input_txt" id="kpiFulfillValue" name="kpiFulfillValue" style="ime-mode:disabled"  type="text"  value="${departmentKpiInfo.kpiFulfillValue }" />
			</td>
			<th width="20%">目标值:</th>
			<td width="30%">
			<input  class="input_txt"  id="kpiTaskValue" name="kpiTaskValue" style="ime-mode:disabled" type="text" value="${departmentKpiInfo.kpiTaskValue }" />
			</td>
		</tr>
		<tr class="info">
		<th width="20%">单位:</th>
			<td width="30%">
			<input class="input_txt"  id="unit" name="unit" type="text" value="${departmentKpiInfo.unit }" />
		</td>
		<c:choose>
		<c:when test="${type eq 'Y' }">
		<th width="20%">年份:</th>
			<td width="30%">
			<select id="reportDate" name="reportDate">
			<option value="">请选择</option>
			<c:forEach items="${yearList}" var="year">
			<option value="${year}" <c:if test="${departmentKpiInfo.reportDate == year }">selected</c:if>>${year}</option>
			</c:forEach>
			</select>
			
		</c:when>
		<c:otherwise>
		<th width="20%">日期:</th>
			<td width="30%">
			<input class="input_txt"  id="reportDate" name="reportDate" type="text"  readonly="readonly"  onclick="setMonth(this,this)" 
						style="cursor: pointer;" value="${departmentKpiInfo.reportDate }"/> </td>
		</c:otherwise>
		</c:choose>
		
		</tr>
	</table>
	</form>
	<iframe name="result" id="result" src="about:blank" frameborder="0" width="0" height="0"></iframe>
	
	
	<script type="text/javascript">
	var n=0;
		var dg;
		$(document).ready(function(){
			dg = frameElement.lhgDG;
			dg.addBtn('ok','保存',function(){
				n=0;
				//checkInfo();
				$("#deptInfoForm").submit();
				
				
			});
			if("${departmentKpiInfo.id}"==null ||"${departmentKpiInfo.id}".length==0){
				if("${type}" !="Y"){
					dg.addBtn('into','插入',function(){
						n=1;
						//checkInfo();
						$("#deptInfoForm").submit();
					});
					var mydate = new Date();
					$("#reportDate").val(mydate.getFullYear()+"-01");
				}
			}else{
				//日期格式化
				var reportDate= "${departmentKpiInfo.reportDate}";
				if("${type}" !="Y"){
					$("#reportDate").val(reportDate.substring(0, 4)+"-"+reportDate.substring(4, 6));
				}
			}
		});
		
		function checkInfo(){
			if($("#deptName").val()==""){
				alert("请选择部门名称");
				$("#deptName").focus();
				return false;
			}
			if($("#kpiCode").val()==""){//新增
				alert("请输入指标CODE!");
				$("#kpiCode").focus();
				return false;
			}
			if($("#kpiName").val()==""){
				alert("请输入指标名称!");
				$("#kpiName").focus();
				return false;
			}
			if($("#kpiTaskValue").val()==""){
				alert("请输入目标值!");
				$("#kpiTaskValue").focus();
				return false;
			}
			if(isNaN($("#kpiTaskValue").val())){
				alert("目标值应为数字!");
				$("#kpiTaskValue").focus();
				return false;
			}
			if($("#unit").val()==""){
				alert("请输入单位!");
				$("#unit").focus();
				return false;
			}
			if($("#reportDate").val()==""){
				alert("请输入日期!");
				$("#reportDate").focus();
				return false;
			}
		
			return true;
		}
		
		function success(){
			alert("操作成功!");
			if(n==1){
				changeDate(1);
			}else{
				if(dg.curWin.document.forms[0]){
					dg.curWin.document.forms[0].action = dg.curWin.location+"";
					dg.curWin.document.forms[0].submit();
				}else{
					dg.curWin.location.reload();
				}
				dg.cancel();
			}
			
			
		}
		
		function failed(){
			alert("操作失败!");
			return false;
		}
		
		function changeDate(n){
			var reg=new RegExp("-","g");
			var dt=document.getElementById('reportDate').value.replace(reg,"/")+"/01";
			  var today=new Date(new Date(dt).valueOf() );
			  today.setMonth(today.getMonth()+n);
			  var monthDate=today.getMonth()+1;
			  if((today.getMonth() + 1)<10){ 
		    	  monthDate="0"+(today.getMonth() + 1);
			  }
			  var changedDate=today.getFullYear() + "-" + monthDate;
			  $("#reportDate").val(changedDate);
			  $("#kpiFulfillValue").val("");
			  $("#kpiTaskValue").val("");
			  $("#kpiTaskValue").focus();
			  if(monthDate=="01"){
				  if (confirm("新的一年,是否继续插入？")) {
						
					}else{
						alert("插入成功,关闭窗口！");
						if(dg.curWin.document.forms[0]){
							dg.curWin.document.forms[0].action = dg.curWin.location+"";
							dg.curWin.document.forms[0].submit();
						}else{
							dg.curWin.location.reload();
						}
						dg.cancel();
					}
			  }
		}
	</script>
</body>
</html>