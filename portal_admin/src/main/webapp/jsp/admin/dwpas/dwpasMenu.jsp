<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>经纬仪菜单管理</title>
<link type="text/css" rel="stylesheet" href="<%=path%>/css/main.css" />
</head>
<body>
	<div class="search_div" style="margin-top:3px;">
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td align="left" width="70%"><div style="font-size:12px;font-family:'宋体';">当前位置:&nbsp;${templateName}&nbsp;&gt;&nbsp;<font color="red">菜单信息</font>&nbsp;</div>
		    </td>
		    <td align="left">
		        <a href="javascript:addmenu();" class="myBtn"><em>新增</em></a>&nbsp;
		    	<a href="<%=path%>/dwpas/listPageTemplateInfo.html" class="myBtn"><em>返回</em></a>
		    	<input type="checkbox" value="1"  name="chk_show_menu"  onclick='showMenu(this.checked)' id="chk_show_menu"/><label for="chk_show_menu" style='cursor:pointer;'>显示已隐藏的菜单</label>
		    </td>
		</tr>
	</table>
	</div>
	<table width="100%" border="0" cellpadding="0" cellspacing="0"
		class="main_table" id="sys_menu_tb">
		<tr class="main_head">
			<th width="10%" align="left">&nbsp;&nbsp;序号</th>
			<th width="25%" align="left">名称</th>
			<th width="25%">备注</th>
			<th width="15%">创建时间</th>
			<th width="13%">是否可见</th>
			<th width="12%">操作</th>
		</tr>
		<c:choose>
			<c:when test="${not empty parentlist}">
				<c:forEach items="${parentlist}" var="menu" varStatus="vs">
					<tr style="background-color: #ffffff;height:27px;text-align: center;color:#666666;" id="tr_menu_${menu.menuId}">
						<td align="left">&nbsp;&nbsp;&nbsp;&nbsp;${vs.index+1}</td>
						<td align='left'>&nbsp;${menu.menuName }</td>
						<td>${menu.remark }</td>
						<td><fmt:formatDate value="${menu.createTime}"
								pattern="yyyy-MM-dd" /></td>
						<td>&nbsp;</td>
						<td id="${menu.menuCode}"><c:if test="${menu.templateId != 1}">
				<a href="###" onclick="delmenu('${menu.menuId }',true)">删除</a>
							</c:if>
							</td>
							<script language='javascript'>
							 	if("${menu.templateId == 1}" && "${menu.menuCode}".indexOf("01")<=-1 &&
										"${menu.menuCode}".indexOf("02")<=-1 && "${menu.menuCode}".indexOf("03")<=-1 &&
										"${menu.menuCode}".indexOf("04")<=-1){
									document.getElementById("${menu.menuCode}").innerHTML="<a href='###' onclick=delmenu('${menu.menuId }',true)>删除</a>";
								} 
							  
	                         </script>
					</tr>
					        <!-- 二级菜单是否显示：0，不显示 -->
					        <c:set var="secondMenuHide">1</c:set>
							<c:forEach items="${parentlist}" var="p" varStatus="pc">
								<c:if test="${menu.menuId == p.menuId}">
								<c:choose>
								<c:when test="${p.dateType == '0' }">
								<tr style='height: 24px; line-height: 24px;<c:if test="${p.isShow==0}">display:none;</c:if>' onmouseover="this.style.backgroundColor='#D2E9FF';" onmouseout="this.style.backgroundColor='';">
										<td>&nbsp;</td>
										<td><span style='width: 20px; display: inline-block;'></span>
													<img src='../images/join.gif'
														style='vertical-align: middle;' />
											 <span
											style='width:150px; text-align: left; display: inline-block;'>${p.menuName}(日)
										</span></td>
										<td>${p.remark }</td>
										<td>&nbsp;</td>
										<c:choose>
										<c:when test="${p.isShow==1 }">
										   <td><font color="blue">可见</font></td>
										   <c:set var="secondMenuHide">1</c:set>
										</c:when>
										<c:otherwise>
										  <td><font color="red">不可见</font></td>
										  <c:set var="secondMenuHide">0</c:set>
										</c:otherwise>
										</c:choose>
										<td><a href="###" onclick="editmenu('${p.menuId }','D','${templateName}','${p.menuName}','${p.menuCode }')">修改</a>
										</td>
									</tr>
								</c:when>
								<c:when test="${p.dateType == '1' }">
								<tr style='height: 24px; line-height: 24px;<c:if test="${p.isShow==0}">display:none;</c:if>' onmouseover="this.style.backgroundColor='#D2E9FF';" onmouseout="this.style.backgroundColor='';">
										<td>&nbsp;</td>
										<td><span style='width: 20px; display: inline-block;'></span>
													<img src='../images/join.gif'
														style='vertical-align: middle;' />
											 <span
											style='width:150px; text-align: left; display: inline-block;'>${p.menuName}(月)
										</span></td>
										<td>${p.remark }</td>
										<td>&nbsp;</td>
										<c:choose>
										<c:when test="${p.isShow==1 }">
										<td><font color="blue">可见</font></td>
										<c:set var="secondMenuHide">1</c:set>
										</c:when>
										<c:otherwise>
										<td><font color="red">不可见</font></td>
										<c:set var="secondMenuHide">0</c:set>
										</c:otherwise>
										</c:choose>
										<td><a href="###" onclick="editmenu('${p.menuId }','M','${templateName}','${p.menuName}','${p.menuCode }')">修改</a>
										</td>
									</tr>
								</c:when>
								<c:when test="${p.dateType == '2' }">
								<tr style='height: 24px; line-height: 24px;<c:if test="${p.isShow==0}">display:none;</c:if>' onmouseover="this.style.backgroundColor='#D2E9FF';" onmouseout="this.style.backgroundColor='';">
										<td>&nbsp;</td>
										<td><span style='width: 20px; display: inline-block;'></span>
													<img src='../images/join.gif'
														style='vertical-align: middle;' />
											 <span
											style='width:150px; text-align: left; display: inline-block;'>${p.menuName}(日)
										</span></td>
										<td>${p.remark }</td>
										<td>&nbsp;</td>
										<c:choose>
										<c:when test="${p.isShow==1 }">
										<td><font color="blue">可见</font></td>
										<c:set var="secondMenuHide">1</c:set>
										</c:when>
										<c:otherwise>
										<td><font color="red">不可见</font></td>
										<c:set var="secondMenuHide">0</c:set>
										</c:otherwise>
										</c:choose>
										<td><a href="###" onclick="editmenu('${p.menuId }','D','${templateName}','${p.menuName}','${p.menuCode }')">修改</a>
										</td>
									</tr>
									<tr style='height: 24px; line-height: 24px;<c:if test="${p.isShow==0}">display:none;</c:if>' onmouseover="this.style.backgroundColor='#D2E9FF';" onmouseout="this.style.backgroundColor='';">
										<td>&nbsp;</td>
										<td><span style='width: 20px; display: inline-block;'></span>
													<img src='../images/join.gif'
														style='vertical-align: middle;' />
											 <span
											style='width:150px; text-align: left; display: inline-block;'>${p.menuName}(月)
										</span></td>
										<td>${p.remark }</td>
										<td>&nbsp;</td>
										<c:choose>
										<c:when test="${p.isShow==1 }">
										<td><font color="blue">可见</font></td>
										<c:set var="secondMenuHide">1</c:set>
										</c:when>
										<c:otherwise>
										<td><font color="red">不可见</font></td>
										<c:set var="secondMenuHide">0</c:set>
										</c:otherwise>
										</c:choose>
										<td><a href="###" onclick="editmenu('${p.menuId }','M','${templateName}','${p.menuName}','${p.menuCode }')">修改</a>
										</td>
									</tr>
								</c:when>
								</c:choose>
								</c:if>
							</c:forEach>
							<script language='javascript'>
							    //${menu.menuName }
							    <c:if test="${secondMenuHide==0}">document.getElementById("tr_menu_${menu.menuId}").style.display="none";</c:if>
							</script>
							<c:set var="threeMenuHide">1</c:set>
					<c:forEach items="${childlist}" var="child" varStatus="vc">
						<c:if test="${child.menuPid == menu.menuId}">
							<!-- 模块:${child.menuName} -->
							<tr style='height: 24px; line-height: 24px;' id="tr_second_menu_${child.menuId}">
								<td></td>
								<td><span style='width: 20px; display: inline-block;'></span>
											<img src='../images/join.gif' style='vertical-align: middle;' />
									<span
									style='width: 150px; text-align: left; display: inline-block;'><font color="#666666">${child.menuName}</font></span></td>
								<td></td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td id="${child.menuCode}"><c:if test="${child.templateId != 1}">
				                         <a href="###" onclick="delmenu('${child.menuId }',false)">删除</a>
											</c:if></td>
								<script language='javascript'>
							 	if("${child.templateId == 1}" && "${child.menuCode}".indexOf("01")<=-1 &&
										"${child.menuCode}".indexOf("02")<=-1 && "${child.menuCode}".indexOf("03")<=-1 &&
										"${child.menuCode}".indexOf("04")<=-1){
									document.getElementById("${child.menuCode}").innerHTML="<a href='###' onclick=delmenu('${child.menuId }',true)>删除</a>";
								} 
							  
	                         </script>			
							</tr>
							<!-- 子模块 -->
							<c:forEach items="${childlist}" var="c" varStatus="cc">
								<c:if test="${child.menuId == c.menuId}">
									<c:choose>
								<c:when test="${c.dateType == '0' }">
									<tr onmouseover="this.style.backgroundColor='#D2E9FF';" onmouseout="this.style.backgroundColor='';" style="<c:if test='${c.isShow==0}'>display:none;</c:if>">
										<td>&nbsp;</td>
										<td><span style='width:50px; display: inline-block;'></span>
													<img src='../images/join.gif'
														style='vertical-align: middle;' />
												<span
											style='width:100px; text-align: left; display: inline-block;'>${c.menuName}(日)
										</span></td>
										<td>${c.remark }</td>
										<td>&nbsp;</td>
										<c:choose>
										<c:when test="${c.isShow==1 }">
										<td><font color="blue">可见</font></td>
										<c:set var="threeMenuHide">1</c:set>
										</c:when>
										<c:otherwise>
										<td id='td_${c.menuId}'><font color="red">不可见</font></td>
										<c:set var="threeMenuHide">0</c:set>
										</c:otherwise>
										</c:choose>
										<td><a href="###" onclick="editmenu('${c.menuId }','D','${templateName}','${c.menuName}','${c.menuCode }')">修改</a>
										</td>
									</tr>
									</c:when>
								<c:when test="${c.dateType == '1' }">
								<tr onmouseover="this.style.backgroundColor='#D2E9FF';" onmouseout="this.style.backgroundColor='';" style="<c:if test='${c.isShow==0}'>display:none;</c:if>">
										<td>&nbsp;</td>
										<td><span style='width:50px; display: inline-block;'></span>
													<img src='../images/join.gif'
														style='vertical-align: middle;' />
												<span
											style='width:100px; text-align: left; display: inline-block;'>${c.menuName}(月)
										</span></td>
										<td>${c.remark }</td>
										<td>&nbsp;</td>
										<c:choose>
										<c:when test="${c.isShow==1 }">
										<td><font color="blue">可见</font></td>
										<c:set var="threeMenuHide">1</c:set>
										</c:when>
										<c:otherwise>
										<td id='td_${c.menuId}'><font color="red">不可见</font></td>
										<c:set var="threeMenuHide">0</c:set>
										</c:otherwise>
										</c:choose>
										<td><a href="###" onclick="editmenu('${c.menuId }','M','${templateName}','${c.menuName}','${c.menuCode }')">修改</a>
										</td>
									</tr>
								</c:when>
								<c:when test="${c.dateType == '2' }">
								<tr onmouseover="this.style.backgroundColor='#D2E9FF';" onmouseout="this.style.backgroundColor='';" style="<c:if test='${c.isShow==0}'>display:none;</c:if>">
										<td>&nbsp;</td>
										<td><span style='width:50px; display: inline-block;'></span>
													<img src='../images/join.gif'
														style='vertical-align: middle;' />
												<span
											style='width:100px; text-align: left; display: inline-block;'>${c.menuName}(日)
										</span></td>
										<td>${c.remark }</td>
										<td>&nbsp;</td>
										<c:choose>
										<c:when test="${c.isShow==1 }">
										<td><font color="blue">可见</font></td>
										<c:set var="threeMenuHide">1</c:set>
										</c:when>
										<c:otherwise>
										<td id='td_${c.menuId}'><font color="red">不可见</font></td>
										<c:set var="threeMenuHide">0</c:set>
										</c:otherwise>
										</c:choose>
										<td><a href="###" onclick="editmenu('${c.menuId }','D','${templateName}','${c.menuName}','${c.menuCode }')">修改</a>
										</td>
									</tr>
									<tr onmouseover="this.style.backgroundColor='#D2E9FF';" onmouseout="this.style.backgroundColor='';" style="<c:if test='${c.isShow==0}'>display:none;</c:if>">
										<td>&nbsp;</td>
										<td><span style='width:50px; display: inline-block;'></span>
													<img src='../images/join.gif'
														style='vertical-align: middle;' />
												<span
											style='width:100px; text-align: left; display: inline-block;'>${c.menuName}(月)
										</span></td>
										<td>${c.remark }</td>
										<td>&nbsp;</td>
										<c:choose>
										<c:when test="${c.isShow==1 }">
										<td><font color="blue">可见</font></td>
										<c:set var="threeMenuHide">1</c:set>
										</c:when>
										<c:otherwise>
										<td id='td_${c.menuId}'><font color="red">不可见</font></td>
										<c:set var="threeMenuHide">0</c:set>
										</c:otherwise>
										</c:choose>
										<td><a href="###" onclick="editmenu('${c.menuId }','M','${templateName}','${c.menuName}','${c.menuCode }')">修改</a>
										</td>
									</tr>
								</c:when>
								</c:choose>
								</c:if>
							</c:forEach>
							<script language='javascript'>
							  //${child.menuName}
							  <c:if test="${threeMenuHide==0}">document.getElementById("tr_second_menu_${child.menuId}").style.display="none";</c:if>
							</script>
						</c:if>
					</c:forEach>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr class="main_info">
					<td colspan="6">没有相关数据</td>
				</tr>
			</c:otherwise>
		</c:choose>
	</table>
	<script type="text/javascript" src="<%=path%>/js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript"
		src="<%=path%>/js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$(".main_info:even").addClass("main_table_even");
		});
		function editmenu(menuId,dateType,templateName,menuName,menuCode){
			//02:产品龙虎榜
			//03:产品健康度
			//04:产品指标分析
			//05:用户行为
			if(menuCode.substring(0,2)=="02"||menuCode.substring(0,2)=="03"||menuCode.substring(0,2)=="04"||menuCode.substring(0,2)=="05")
			{   
				    if(menuCode == '0301' && dateType == 'M'){
				    	  var url='/portal/dwpas/editDesMonth'+menuId+'_'+dateType+'.html?templateName='+templateName+'&menuName='+menuName; 
					      location.href=url;
				    }else{
				      var url='/portal/dwpas/editMudole'+menuId+'_'+dateType+'.html?templateName='+templateName+'&menuName='+menuName; 
				      location.href=url;
				    }
			}else
			{
				//01 首页或新增的模块
				var url='<%=path%>/designTemplate/designIndexMenu.html?menuId='+menuId+'&dateType='+dateType+'&menuName='+menuName+'&templateName='+templateName;
				location.href=url;
			}
		}
		
		function delmenu(menuId,isParent){
			if(isParent){
				if(confirm("确定要删除该菜单吗？其下子菜单将一并删除！")){
					var url = "<%=path%>/dwpas/delParentAndChildMenu"+menuId;
					$.get(url,function(data){
						if(data=="success"){
							alert("删除成功！");
							document.location.reload();
							}
						else{
							alert("删除失败！");
						}
						
					});
				}
			}else{
				if(confirm("确定要删除该菜单吗？")){
					var url = "<%=path%>/dwpas/delSystemMenu"+menuId;
					$.get(url,function(data){
						if(data=="success"){
							alert("删除成功！");
							document.location.reload();
							}
						else{
							alert("删除失败！");
						}
					});
				}
			}
		}
		
		function openClose(menuId,curObj,trIndex){
			var txt = $(curObj).text();
			if(txt=="展开"){
				$(curObj).text("折叠");
				$("#tr"+menuId).after("<tr class='main_info' id='tempTr"+menuId+"'><td colspan='5'>数据载入中</td></tr>");
				if(trIndex%2==0){
					$("#tempTr"+menuId).addClass("main_table_even");
				}
				var url = "<%=path%>/dwpas/childMenus" + menuId;
				$
						.get(
								url,
								function(data) {
									if (data.length > 0) {
										var html = "";
										$
												.each(
														data,
														function(i) {
															html = "<tr style='height:24px;line-height:24px;' name='subTr"+menuId+"'>";
															html += "<td></td>";
															html += "<td><span style='width:80px;display:inline-block;'></span>";
															if (i == data.length - 1)
																html += "<img src='../images/joinbottom.gif' style='vertical-align: middle;'/>";
															else
																html += "<img src='../images/join.gif' style='vertical-align: middle;'/>";
															html += "<span style='width:100px;text-align:left;display:inline-block;'>"
																	+ this.menuName
																	+ "</span>";
															html += "</td>";
															html += "<td>"
																	+ this.remark
																	+ "</td>";
															html += "<td></td>";
															html += "<td><a href='###' onclick=editmenu('"
																	+ this.menuId
																	+ "')>修改</a>";
															if (this.templateId != 1) {
																html += " | <a href='###' onclick=delmenu('"
																		+ this.menuId
																		+ "',false)>删除</a>";
															}
															html += "</td>";
															html += "</tr>";
															$(
																	"#tempTr"
																			+ menuId)
																	.before(
																			html);
														});
										$("#tempTr" + menuId).remove();
										if (trIndex % 2 == 0) {
											$("tr[name='subTr" + menuId + "']")
													.addClass("main_table_even");
										}
									} else {
										$("#tempTr" + menuId + " > td").html(
												"没有相关数据");
									}
								}, "json");
			} else {
				$("#tempTr" + menuId).remove();
				$("tr[name='subTr" + menuId + "']").remove();
				$(curObj).text("展开");
			}
		}

		function addmenu(){
			var dg = new $.dialog({
				title:'新增菜单',
				id:'dwpasmenu_new',
				width:330,
				height:250,
				iconTitle:false,
				cover:true,
				maxBtn:false,
				xButton:true,
				resize:true,
				page:'<%=path%>/dwpas/addMenu${templateId}.html' 
				});
    		dg.ShowDialog();
		}
		
		function  showMenu(showFlag){
			var menutable = document.getElementById('sys_menu_tb');
		    for(var i=0;i<menutable.rows.length;i++){
		    	var row = menutable.rows[i];
		    	var content = row.cells[4].childNodes[0].innerHTML;
		    	if(content == null) continue;
		    	if(content == '不可见') 
		    	{
		    		row.style.display=showFlag?"":"none";
		    	    if(showFlag)
		    	    {
		    	       //alert(document.getElementById("tr_second_menu_"+row.cells[4].id.substring(3)));
		    	       if(document.getElementById("tr_second_menu_"+row.cells[4].id.substring(3))!=null) document.getElementById("tr_second_menu_"+row.cells[4].id.substring(3)).style.display="";
		    	       //alert(id.substring)
		    	       //row.parentNode.previousSibling.style.display="";	
		    	    }else
		    	    {
		    	    	 if(document.getElementById("tr_second_menu_"+row.cells[4].id.substring(3))!=null) document.getElementById("tr_second_menu_"+row.cells[4].id.substring(3)).style.display="none";
		    	    }
		    	}
		    		
		    }
		}
		$(document).ready(function(){
			showMenu(false);
		});
	
	</script>
</body>
</html>