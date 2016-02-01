<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SysParam</title>
<link type="text/css" rel="stylesheet" href="css/main.css"/>
</head>
<body>
	<form action="sysparam.html" method="post" name="sysParamForm" id="sysParamForm">
	<div class="search_div">
		参数名称：<input type="text" name="paramName" id="paramName" value="${sysparam.paramName }" onkeydown="javascript:if(event.keyCode==13){search();}"/>
		&nbsp;&nbsp;&nbsp;&nbsp;参数值：<input type="text" name="paramValue" id="paramValue" value="${sysparam.paramValue }" onkeydown="javascript:if(event.keyCode==13){search();}">
		&nbsp;&nbsp;&nbsp;&nbsp;创建时间：<input type="text" name="gmtCreate" id="gmtCreate" readonly  style='text-align:center;width:100px;cursor: pointer;' value="<fmt:formatDate value="${sysparam.gmtCreate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker()" />
		<a href="javascript:search();" class="myBtn"><em>查询</em></a>
		<a href="javascript:clearValue();" class="myBtn"><em>清空</em></a>
	</div>
	</form>
	<form action="sysparam/delete.html" method="post" name="sysParamForm1" id="sysParamForm1">
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="main_table">
		<tr class="main_head">
			<th width="5%">序号</th>
			<th width="10%">参数名称</th>
			<th width="30%">参数值</th>
			<th width="20%">创建时间</th>
			<th width="20%">操作</th>
		</tr>
		<c:choose>
			<c:when test="${not empty sysparams}">
				<c:forEach items="${sysparams}" var="sysparam" varStatus="vs">
				<tr class="main_info" style="cursor: pointer;" title="双击进行修改" onmouseover="this.style.backgroundColor='#D2E9FF';" onmouseout="this.style.backgroundColor='';" ondblclick="editSysParam(${sysparam.paramId });" >
				<td>${vs.index+1}</td>
				<td align='left'>${sysparam.paramName }</td>
				<td align='left'>&nbsp;&nbsp;${sysparam.paramValue }</td>
				<td><fmt:formatDate value="${sysparam.gmtCreate}" pattern="yyyy-MM-dd"/></td>
				<td><a href="javascript:editSysParam(${sysparam.paramId });">修改</a> | <a href="javascript:delSysParam(${sysparam.paramId });">删除</a></td>
				</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr class="main_info">
					<td colspan="7">没有相关数据</td>
				</tr>
			</c:otherwise>
		</c:choose>
	</table>
	<div class="page_and_btn">
		<div>
			<a href="javascript:addSysParam();" class="myBtn"><em>新增</em></a>
		</div>
		${sysparam.page.pageStr }
	</div>
	</form>
	<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="js/datePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$(".main_info:even").addClass("main_table_even");
		});
		
		function addSysParam(){
			var dg = new $.dialog({
				title:'新增系统参数信息',
				id:'sysparam_new',
				width:330,
				height:180,
				iconTitle:false,
				cover:true,
				maxBtn:false,
				xButton:true,
				resize:true,
				page:'sysparam/add.html'
				});
    		dg.ShowDialog();
		}
		//清空
		function clearValue(){
			$("#paramName").val("");
			$("#paramValue").val("");
			$("#gmtCreate").val("");
			search();
		}
		function editSysParam(paramId){
			var dg = new $.dialog({
				title:'修改系统参数信息',
				id:'sysparam_edit',
				width:330,
				height:180,
				iconTitle:false,
				cover:true,
				maxBtn:false,
				xButton:true,
				resize:true,
				page:'sysparam/edit'+paramId+'.html'
				});
    		dg.ShowDialog();
		}
		
		function delSysParam(paramId){
			if(confirm("确定要删除该记录？")){
				var url = "sysparam/delete"+paramId+".html";
				$.get(url,function(data){
					if(data=="success"){
						alert("操作成功");
						document.location.href="sysparam.html";
					}else
					{
						alert("操作失败");
					}
				});
			}
		}
		
		function search(){
			$("#sysParamForm").submit();
		}
	</script>
</body>
</html>