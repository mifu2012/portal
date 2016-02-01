<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<title>部门指标监控</title>
<link type="text/css" rel="stylesheet" href="css/main.css" />
<script type="text/javascript" src="<%=basePath %>/js/datePicker/My98MonthPicker.jsp?id=123"></script>
<script type="text/javascript" src="<%=basePath %>/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>
</head>
<body>
	<form action="departmentMonitor" method="post" name="departmentForm" id="departmentForm">
		<div class="search_div" style="font-weight:">
			&nbsp;&nbsp;部门名称：
			<select id="deptName" name="deptName" onchange="search();" style="vertical-align: middle;height: 21px;" >
			<option value="">请选择</option>
			<option value="运营部" <c:if test="${departmentKpiInfo.deptName eq '运营部'}">selected</c:if> >运营部</option>
			<option value="产品部" <c:if test="${departmentKpiInfo.deptName eq '产品部'}">selected</c:if> >产品部</option>
			<option value="财务部" <c:if test="${departmentKpiInfo.deptName eq '财务部'}">selected</c:if> >财务部</option>
			<option value="市场部" <c:if test="${departmentKpiInfo.deptName eq '市场部'}">selected</c:if> >市场部</option>
			<option value="客服部" <c:if test="${departmentKpiInfo.deptName eq '客服部'}">selected</c:if> >客服部</option>
			</select>&nbsp;&nbsp;&nbsp;&nbsp;日期：
				<input  id="reportDate" name="reportDate" type="text"  readonly="readonly"  onclick="setMonth(this,this)" value="${reportDate}"
						style="cursor: pointer;width: 100px;" /> 
			&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:search();" class="myBtn"><em>查询</em></a>
			<a href="javascript:clearValue();" class="myBtn"><em>清空</em></a>
		</div>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="main_table">
			<tr class="main_head">
				<th width="8%" style="padding-left: 20px" align="left">序号</th>
				<th width="15%" align="left">部门名称</th>
				<th width="15%" align="left">指标名称</th>
				<th width="11%" align="left">单位</th>
				<th width="11%" align="left">实际值</th>
				<th width="10%" align="left">目标值</th>
				<th width="15%" align="center">日期</th>
				<th width="15%" align="center">操作</th>
			</tr>
			<c:choose>
				<c:when test="${not empty dwpasDepartmentKpiList}">
					<c:forEach items="${dwpasDepartmentKpiList}" var="dwpasDepartmentKpi" varStatus="vs">
						<tr class="main_info" style="cursor: pointer;" 
							onmouseover="this.style.backgroundColor='#D2E9FF';"
							onmouseout="this.style.backgroundColor='';" ondblclick="javascript:updateDepartmentKpiInfo(${dwpasDepartmentKpi.id});" title="双击修改">
							<td style="padding-left: 25px" align="left">${vs.index+1}</td>
							<td align="left">${dwpasDepartmentKpi.deptName }</td>
							<td align="left" >${dwpasDepartmentKpi.kpiName}</td>
							<td align="left">${dwpasDepartmentKpi.unit }</td>
							<td align="left">${dwpasDepartmentKpi.kpiFulfillValue }</td>
							<td align="left">${dwpasDepartmentKpi.kpiTaskValue }</td>
							<td align="center" id="reportDateDesc_${dwpasDepartmentKpi.id}">${dwpasDepartmentKpi.reportDateDesc}</td>
							<td align="center">
								<a href="javascript:updateDepartmentKpiInfo(${dwpasDepartmentKpi.id});">修改</a> | 
								<a href="javascript:delDepartmentKpiInfo(${dwpasDepartmentKpi.id});">删除</a>
							</td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr class="main_info">
						<td colspan="8">没有相关数据</td>
					</tr>
				</c:otherwise>
			</c:choose>
		</table>
		<div class="page_and_btn">
		<div>
		<a href="javascript:addDeptInfo('M');" class="myBtn"><em>新增(月)</em></a>
		&nbsp;&nbsp;<a href="javascript:addDeptInfo('Y');" class="myBtn"><em>新增(年)</em></a>
		</div>
			
			${departmentKpiInfo.page.pageStr }
		</div>
	</form>
	<script type="text/javascript"
		src="js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".main_info:even").addClass("main_table_even");
		});


		
		//清空按钮
 		function clearValue() {
			$("#deptName").val("");
			$("#reportDate").val("");
			//$($("#roleId option"))[0].selected = true;
			search();
		} 
		
		function delDepartmentKpiInfo(id) {
 			if (confirm("确定要删除该记录？")) {
				var url = "departmentMonitor/delete.html?id=" + id;
				$.get(url, function(data) {
					if (data == "success") {
						alert("删除成功！");
						document.location.href = "departmentMonitor.html";
					} else {
						alert("删除失败！");
					}
				});
			} 
		}
		
		//修改指标部门信息
		function updateDepartmentKpiInfo(id){
			var reportDateDesc=document.getElementById("reportDateDesc_"+id).innerHTML;
			var value = "月";
			if(reportDateDesc.length == 4){
				value = "年";
			}
			var dg = new $.dialog({
				title : '修改部门指标信息(' +value+')' ,
				id : 'dept_new',
				width : 480,
				height : 350,
				iconTitle : false,
				cover : true,
				maxBtn : true,
				xButton : true,
				resize : true,
				page : 'departmentMonitor/update'+id
			});
			dg.ShowDialog();			
		}

		function search() {
			$("#departmentForm").submit();
		}

		function enterToSearch() {
			if (event.keyCode == 13) {
				search();
				return false;
			}
		}
		
		function addDeptInfo(type){
			var value = "月";
			if(type == "Y"){
				value = "年";
			}
			var dg = new $.dialog({
				title : '添加部门指标信息(' +value+')' ,
				id : 'dept_new',
				width : 480,
				height : 350,
				iconTitle : false,
				cover : true,
				maxBtn : true,
				xButton : true,
				resize : true,
				page : 'departmentMonitor/add.html?type='+type
			});
			dg.ShowDialog();
		}
		
	</script>
</body>
</html>