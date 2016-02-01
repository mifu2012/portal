<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>图例分类</title>
<link type="text/css" rel="stylesheet" href="../css/main.css" />
</head>
<body>
	<form action="list.html" method="post" name="Form" id="Form">
		<div class="search_div">
			图例类名: <input type="text" name="categoryName" id="categoryName"
				value="${dwmisLegendCategory.categoryName}" />&nbsp;&nbsp; 
				<a href="javascript:search();" class="myBtn"><em>查询</em></a> 
				<a href="javascript:clearValue();" class="myBtn"><em>清空</em></a>
		</div>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="main_table">
			<tr class="main_head">
				<th width="5%">序号</th>
				<th width="20%">图例类名</th>
				<th>备注</th>
				<th width="20%">创建时间</th>
				<th width="20%">操作</th>
			</tr>
			<c:choose>
				<c:when test="${not empty dwmisLegendCategory}">
					<c:forEach items="${list}" var="legend" varStatus="vs">
						<tr class="main_info" title='双击修改图例分类' style='cursor: pointer;'
							onmouseover="this.style.backgroundColor='#D2E9FF';"
							onmouseout="this.style.backgroundColor='';"
							ondblclick="editCategory('${legend.categoryId}')" >
							<td>${vs.index+1}</td>
							<td id="name_${legend.categoryId}">${legend.categoryName }</td>
							<td align='left'>&nbsp;&nbsp;&nbsp;&nbsp;${legend.remark }</td>
							<td><fmt:formatDate value="${legend.gmtDate}"
									pattern="yyyy-MM-dd" /></td>
							<td align="center"><a
								href="javascript:editCategory('${legend.categoryId }')">修改</a> |
								<a href="javascript:delCategory('${legend.categoryId }')">删除</a>
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
			${dwmisLegendCategory.page.pageStr }
		</div>
	</form>
	<script type="text/javascript" src="../js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript"
		src="../js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>
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
				title : '新增图例类别',
				id : 'category_new',
				width : 360,
				height : 300,
				iconTitle : false,
				cover : true,
				maxBtn : true,
				xButton : true,
				resize : true,
				page : 'add.html'
			});
			dg.ShowDialog();
		}
		function editCategory(Id) {
			var Name = $("#name_" + Id).text();
			var dg = new $.dialog({
				title : '修改图例类别：' + Name,
				id : 'category_edit',
				width : 360,
				height : 300,
				iconTitle : false,
				cover : true,
				maxBtn : true,
				resize : true,
				page : 'edit' + Id
			});
			dg.ShowDialog();
		}

		function delCategory(Id) {
			if (confirm("确定要删除该记录？")) {
				var url = "del" + Id;
				$.get(url, function(data) {
					if (data == "success") {
						alert("删除成功！");
						document.location.href = "list";
					} else {
						alert("删除失败！该图类下有关联图例！");
					}
				});
			}
		}
		//清空按钮
		function clearValue() {
			$("#categoryName").val("");
			search();
		}
		function search() {
			document.getElementById("Form").submit();
		}
	</script>
</body>
</html>