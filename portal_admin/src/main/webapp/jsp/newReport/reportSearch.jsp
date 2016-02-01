<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查询条件</title>
<link type="text/css" rel="stylesheet" href="<%=basePath%>css/main.css" />
<script type="text/javascript"
	src="<%=basePath%>/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/js/datePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/js/datePicker/My98MonthPicker.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>
<script type="text/javascript">
<!--
(function($){
	//插件
	$.extend($,{
		//命名空间
		sortTable:{
			sort:function(tableId,Idx){
				var table = document.getElementById(tableId);
				var tbody = table.tBodies[0];
				var tr = tbody.rows; 
		
				var trValue = new Array();
				for (var i=0; i<tr.length; i++ ) {
					trValue[i] = tr[i];  //将表格中各行的信息存储在新建的数组中
				}
		
				if (tbody.sortCol == Idx) {
					trValue.reverse(); //如果该列已经进行排序过了，则直接对其反序排列
				} else {
					//trValue.sort(compareTrs(Idx));  //进行排序
					trValue.sort(function(tr1, tr2){
						var value1 = tr1.cells[Idx].innerHTML;
						var value2 = tr2.cells[Idx].innerHTML;
						return value1.localeCompare(value2);
					});
				}
		
				var fragment = document.createDocumentFragment();  //新建一个代码片段，用于保存排序后的结果
				for (var i=0; i<trValue.length; i++ ) {
					fragment.appendChild(trValue[i]);
				}
		
				tbody.appendChild(fragment); //将排序的结果替换掉之前的值
				tbody.sortCol = Idx;
			}
		}
	});		  
})(jQuery);

function sortColumn(configId,sortType)
{
  //alert(configId);
  var table=document.getElementById("contentTable");
  var rows = table.rows.length ;
  var crtTr=document.getElementById(configId);
  //
  var isUp=sortType<0?true:false;
  var configIds="";//已排序的ID
  if(isUp)
	{
	    //前移
	    if(crtTr.rowIndex<=1)
		{
			alert('已是第一个，不能前移');
			return;
		}else
		{
           //得到前一行的ID
		   //alert(table.rows[crtTr.rowIndex-1].id)
           configIds=configId+","+table.rows[crtTr.rowIndex-1].id;//当前ID，前一个ID
           var url = "<%=path%>/NewReport/updateColumnSort.html?ids=" + configIds;
			$.get(url, function(data) {
				if (data == "success") {
					document.location.reload();					
				}else{
					alert("前移失败!");
				}
			});
           
		}
	}else
	{
	   //后移
       if(crtTr.rowIndex>=rows-1)
		{
			alert('已是最后一个，不能后移');
			return;
		}else
		{
           configIds=table.rows[crtTr.rowIndex+1].id + "," +configId;//后一个ID,当前ID
           var url = "<%=path%>/NewReport/updateColumnSort.html?ids=" + configIds;
			$.get(url, function(data) {
				if (data == "success") {
					document.location.reload();					
				}else{
					alert("后移失败!");
				}
			});
		}
	}
    //alert(configIds);//修改顺序

}
//-->
</script>
</head>
<body>
<div style="position: absolute; left: 10px; bottom: 10px;">
			提示：<font color='red' size='2'>根据数据源定义的SQL语句查询条件参数（格式为$｛abc｝），自动生成表单控件（参数编码即为控件ID）</font>
</div>
	<form action="user" method="post" name="reportRearchForm"
		id="reportRearchForm">
		<%-- <div align="left">
			<a href="javascript:add(${reportId});" class="myBtn"><em>新增</em></a>
		</div> --%>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="main_table" id="contentTable">
			<tr class="main_head">
				<th width="5%">序号</th>
				<th align='left'>控件ID</th>
				<th align='left'>控件类型</th>
				<th align='left'>显示名称</th>
				<th align='left'>默认值</th>
				<th align='left'>二级维度</th>
				<th width='20%'>操作</th>
			</tr>
			<c:choose>
				<c:when test="${not empty reportReachList}">
					<c:forEach items="${reportReachList}" var="config" varStatus="vs">
						<tr class="main_info" id="${config.configId}" onmouseover="this.style.backgroundColor='#D2E9FF';" onmouseout="this.style.backgroundColor='';">
							<td width="5%">${vs.index+1}</td>
							<td align='left'><b>${config.columnCode}</b><input type="hidden"
								id="column_code" name="column_code" value="${config.columnCode}" />
							</td>
							<td align='left'><c:if test="${config.controlType==0 }">字符型</c:if> <c:if
									test="${config.controlType==1 }">整数型</c:if> <c:if
									test="${config.controlType==2 }">小数型</c:if> <c:if
									test="${config.controlType==3 }">年月日--日历控件型</c:if> <c:if
									test="${config.controlType==4 }">年月--日历控件型</c:if> <c:if
									test="${config.controlType==5 }">年--日历控件型</c:if> <c:if
									test="${config.controlType==11 }">年周--日历控件型</c:if><c:if
									test="${config.controlType==6 }">一级维度型</c:if>
									<c:if test="${config.controlType=='7' }">二级级维度型</c:if>
									 <c:if
									test="${config.controlType=='' }">其他类型</c:if></td>
							<td align='left'>${config.columnLabel }</td>
							<c:choose>
								<c:when test="${config.controlType eq '6' }">
									<td align='left'>${config.codeName}</td>
									<td></td>
								</c:when>
								<c:when test="${config.controlType eq '7' }">
									<td align='left'>${config.codeName}</td>
									<td>${config.codeNameSec}</td>
								</c:when>
								<c:otherwise>
									<td align='left'>${config.defaultValue}</td>
									<td></td>
								</c:otherwise>
							</c:choose>
							<td width="20%"><a
								href="javascript:edit(${config.configId },${config.reportId});">修改</a>
								| <a
								href="javascript:delConfig(${config.configId },${config.reportId });">删除</a>
								|
								<a
								href="javascript:sortColumn(${config.configId },-1);"><img src="<%=basePath%>/images/pic15.gif" title='前移' border='0'></a>
								|
								<a
								href="javascript:sortColumn(${config.configId },1);"><img src="<%=basePath%>/images/pic8.gif" title='后移' border='0'></a>
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
		<br>
		<!--
		<div align="left">
			<a href="javascript:add(${reportId});" class="myBtn"><em>新增</em>
			</a>
		</div>
		-->
		<%-- <div class="page_and_btn">
			${reportConfig.page.pageStr }
		</div> --%>
	</form>

	<script type="text/javascript">
		$(document).ready(function() {
			$(".main_info:even").addClass("main_table_even");
		});
		
		/* 添加控件 hgt */
		function add(reportId) {
			//var url='<%=basePath%>/NewReport/add.html?reportId=${reportId}';
			var dg = new $.dialog({
				title : '新增信息',
				id : 'new',
				width : 330,
				height : 350,
				top : 0,
				iconTitle : false,
				cover : true,
				//fixed:true,
				maxBtn : true,
				xButton : true,
				resize : true,
				page : 'add' + reportId
			});
			dg.ShowDialog();
		}
		/* 删除  hgt*/
		function delConfig(configId, reportId) {
			var tr = document.getElementById(configId);
			if (confirm("确定要删除该记录？")) {
				var url = "del" + configId;
				$.get(url, function(data) {
					if (data == "success") {
						//document.location.href = "reportSearch" + reportId;
						tr.parentNode.removeChild(tr);
					}
				});
			}
		}

		/* 修改控件 hgt */
		function edit(configId, reportId) {
			var dg = new $.dialog({
				title : '修改信息',
				id : 'edit',
				width : 330,
				height : 350,
				top : 0,
				iconTitle : false,
				//fixed:true,
				cover : true,
				maxBtn : false,
				resize : true,
				page : 'edit' + configId + "-" + reportId
			});
			dg.ShowDialog();
		}
	</script>
</body>
</html>