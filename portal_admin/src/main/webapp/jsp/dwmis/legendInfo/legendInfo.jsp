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
<title>图例维护</title>
<link type="text/css" rel="stylesheet" href="<%=basePath%>css/main.css" />
</head>
<body>
	<form action="list.html" method="post" name="Form" id="Form">
		<div class="search_div">
			图例分类:&nbsp;<select name="categoryId" id="categoryId" onchange="javascript:search();">
				<option value="">请选择</option>
				<c:forEach items="${legendCategoryList }" var="category">
					<option value="${category.categoryId}"
						<c:if test="${dwmisLegendInfo.categoryId==category.categoryId}">selected</c:if>>${category.categoryName}</option>
				</c:forEach>
			</select> &nbsp;&nbsp; 图例名称:&nbsp;<input type="text" name="legendName"
				id="legendName" value="${dwmisLegendInfo.legendName}" />&nbsp;&nbsp;
			<a href="javascript:search();" class="myBtn"><em>查询</em></a> 
			<a href="javascript:clearValue();" class="myBtn"><em>清空</em></a>
			
		</div>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="main_table">
			<tr class="main_head">
				<th width="5%">序号</th>
				<th width="20%">图例名称</th>
				<th width="10%">图例分类</th>
				<th width="10%">图类型</th>
				<th width="10%">统计方式</th>
				<th>备注</th>
				<th width="15%">操作</th>
			</tr>
			<c:choose>
				<c:when test="${not empty legendInfoList}">
					<c:forEach items="${legendInfoList}" var="legend" varStatus="vs">
						<tr class="main_info" style="cursor: pointer;"
							onmouseover="this.style.backgroundColor='#D2E9FF';"
							onmouseout="this.style.backgroundColor='';"
							title='双击修改图例' ondblclick="editLegend('${legend.legendId }')">
							<td>${vs.index+1}</td>
							<td id="name_${legend.legendId}" align="left">&nbsp;&nbsp;&nbsp;&nbsp;${legend.legendName }</td>
							<td>${legend.dwmisLegendCategory.categoryName }</td>
							<td>${legend.chartName }</td>
							<td>${legend.statCodeDesc}</td>
							<td align="left">&nbsp;&nbsp;&nbsp;&nbsp;${legend.remark }</td>
							<td align="center"><a
								href="javascript:editLegend('${legend.legendId }')">修改</a> | <a
								href="javascript:delLegend('${legend.legendId }')">删除</a>
							</td>
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
				<a href="javascript:add();" class="myBtn"><em>新增</em> </a>
			</div>
			${dwmisLegendInfo.page.pageStr }
		</div>
	</form>
	<script type="text/javascript" src="<%=basePath%>js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".main_info:even").addClass("main_table_even");
		});
		function butOnClick() {
			if (event.keyCode == 13) {
				search();
				return false;
			}
		}
		function add() {
			var dg = new $.dialog({
				title : '新增图例',
				id : 'legend_new',
				width : 700,
				height : 480,
				iconTitle : false,
				cover : true,
				maxBtn : true,
				resize : false,
				page : 'add.html'
			});
			dg.ShowDialog();
		}
		function editLegend(Id) {
			var Name = $("#name_" + Id).text();
			var dg = new $.dialog({
				title : '修改图例类别：' + Name,
				id : 'legend_edit',
				width : 700,
				height : 480,
				iconTitle : false,
				cover : true,
				maxBtn : true,
				resize : false,
				page : 'edit?legendId=' + Id
			});
			dg.ShowDialog();
		}

		function delLegend(Id) {
			if (confirm("确定要删除该记录？")) {
				var url = "del?legendId=" + Id;
				$.get(url, function(data) {
					if (data == "success") {
						alert("删除成功！");
						document.location.href = "list";
					} else {
						alert("删除失败！");
					}
				});
			}
		}
		//清空按钮
		function clearValue() {
			$("#legendName").val("");
			$("#categoryId").val("");
			search();
		}
		function search() {
			document.getElementById("Form").submit();
		}
	</script>
</body>
</html>