<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>菜单管理</title>
<link type="text/css" rel="stylesheet" href="css/main.css"/>
</head>
<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="main_table">
		<tr class="main_head">
			<th width="5%">序号</th>
			<th width="35%" style="padding-left: 80px;"  align="left">名称</th>
			<th width="40%" style="padding-left: 10px;" align="left">资源路径</th>
			<th width="20%" align="center">操作</th>
		</tr>
		<c:choose>
			<c:when test="${not empty menuList}">
				<c:forEach items="${menuList}" var="menu" varStatus="vs">
				<tr class="main_info" id="tr${menu.menuId }" onmouseover="this.style.backgroundColor='#D2E9FF';" onmouseout="this.style.backgroundColor='';" <c:if test="${not empty menu.subMenuList }">ondblclick="openClose(${menu.menuId },$(operator_a_${menu.menuId}),${vs.index })" title='双击打开或关闭' style='cursor:pointer;'</c:if>>
				<td>${vs.index+1}</td>
				<td style="padding-left: 80px;"  align="left">${menu.menuName }</td>
				<td style="padding-left: 20px;" align="left">${menu.menuUrl }</td>
				<td>
				<c:choose> 
				<c:when test="${not empty menu.subMenuList }">
				<a href="###" onclick="openClose(${menu.menuId },this,${vs.index })" id="operator_a_${menu.menuId}">展开</a> | 
				</c:when>
				<c:otherwise>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</c:otherwise>
				</c:choose>
				<a href="###" onclick="editmenu(${menu.menuId })">修改</a> | 
				<a href="###" onclick="delmenu(${menu.menuId },true)">删除</a></td>
				</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr class="main_info">
				<td colspan="4">没有相关数据</td>
				</tr>
			</c:otherwise>
		</c:choose>
	</table>
	
	<div class="page_and_btn">
		<div>
			<a href="javascript:addmenu();" class="myBtn"><em>新增</em></a>
		</div>
	</div>
	
	<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$(".main_info:even").addClass("main_table_even");
		});
		
		function addmenu(){
			var dg = new $.dialog({
				title:'新增菜单',
				id:'menu_new',
				width:330,
				height:220,
				iconTitle:false,
				cover:true,
				maxBtn:true,
				fixed:true,
				xButton:true,
				resize:true,
				page:'menu/add'
				});
    		dg.ShowDialog();
		}
		
		function editmenu(menuId){
			var dg = new $.dialog({
				title:'修改菜单',
				id:'menu_edit',
				width:330,
				height:220,
				iconTitle:false,
				fixed:true,
				cover:true,
				maxBtn:true,
				xButton:true,
				resize:true,
				page:'menu/edit'+menuId
				});
    		dg.ShowDialog();
		}
		
		function delmenu(menuId,isParent){
			var flag = false;
			if(isParent){
				if(confirm("确定要删除该菜单吗？其下子菜单将一并删除！")){
					flag = true;
				}
			}else{
				if(confirm("确定要删除该菜单吗？")){
					flag = true;
				}
			}
			if(flag){
				var url = "menu/del"+menuId;
				$.get(url,function(data){
					if(data=="success")
					{
						alert("删除成功");//tr
                        if(document.getElementById("tr"+menuId)!=null)
						{
							document.getElementById("tr"+menuId).style.display="none";
						}else
						{
							document.location.reload();
						}
					}else
					{
						alert("删除失败");
					}
					//document.location.reload();
				});
			}
		}

		function openClose(menuId,curObj,trIndex){
			var txt = $(curObj).text();
			if(txt=="展开"){
				$(curObj).text("折叠");
				$("#tr"+menuId).after("<tr class='main_info' id='tempTr"+menuId+"'><td colspan='4'>数据载入中</td></tr>");
				if(trIndex%2==0){
					$("#tempTr"+menuId).addClass("main_table_even");
				}
				var url = "menu/sub"+menuId;
				$.get(url,function(data){
					if(data.length>0){
						var html = "";
						$.each(data,function(i){
							html = "<tr style='height:24px;line-height:24px;cursor: pointer;' name='subTr"+menuId+"' id='tr"+menuId+"' title='双击修改' onmouseover='this.style.backgroundColor=\"#D2E9FF\"' onmouseout='this.style.backgroundColor=\"\"' ondblclick='editmenu("+this.menuId+")'>";
							html += "<td></td>";
							html += "<td align='left' style='padding-left: 80px;'><span style='width:0px;display:inline-block;'></span>";
							if(i==data.length-1)
								html += "<img src='images/joinbottom.gif' style='vertical-align: middle;'/>";
							else
								html += "<img src='images/join.gif' style='vertical-align: middle;'/>";
							html += "<span style='width:100px;text-align:left;display:inline-block;'>"+this.menuName+"</span>";
							html += "</td>";
							html += "<td align='left'>&nbsp;&nbsp;"+this.menuUrl+"</td>";
							html += "<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='###' onclick='editmenu("+this.menuId+")'>修改</a> | <a href='###' onclick='delmenu("+this.menuId+",false)'>删除</a></td>";
							html += "</tr>";
							$("#tempTr"+menuId).before(html);
						});
						$("#tempTr"+menuId).remove();
						if(trIndex%2==0){
							$("tr[name='subTr"+menuId+"']").addClass("main_table_even");
						}
					}else{
						$("#tempTr"+menuId+" > td").html("没有相关数据");
					}
				},"json");
			}else{
				$("#tempTr"+menuId).remove();
				$("tr[name='subTr"+menuId+"']").remove();
				$(curObj).text("展开");
			}
		}
		
	</script>	
</body>
</html>