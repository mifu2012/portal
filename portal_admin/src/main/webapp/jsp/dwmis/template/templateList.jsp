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
<title>瞭望塔模块列表</title>
<link type="text/css" rel="stylesheet" href="<%=path%>/css/main.css" />
</head>
<body>
	<form action="list.html" method="post" name="Form" id="Form">
		<div class="search_div">
			模块名称: <input type="text" name="templateName" id="templateName"
				value="${dwmisTemplateInfo.templateName}" />&nbsp;&nbsp; 
				<a href="javascript:search();" class="myBtn"><em>查询</em></a> 
				<a href="javascript:clearValue();" class="myBtn"><em>清空</em></a>
		</div>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="main_table">
			<tr class="main_head">
				<th width="5%">序号</th>
				<th width="20%">名称</th>
				<th align="center">备注</th>
				<th width="15%">创建时间</th>
				<th width="35%">操作</th>
			</tr>
			<c:choose>
				<c:when test="${not empty templateInfoList}">
					<c:forEach items="${templateInfoList}" var="template" varStatus="vs">
						<tr class="main_info" id="tr${template.templateId }" style="cursor: pointer;" onmouseover="this.style.backgroundColor='#D2E9FF';" onmouseout="this.style.backgroundColor='';" ondblclick="openClose('${template.templateId }',$(operator_a_${template.templateId}),${vs.index })" title='双击打开或关闭' style='cursor:hand;'>
							<td>${vs.index+1}</td>
							<td id="templateName_${template.templateId}" align="center" ><div style='width:120px; line-height:25px; text-overflow:ellipsis; white-space:nowrap; overflow:hidden;' title="${template.templateName }">${template.templateName }</div></td>
							<td align="center">&nbsp;&nbsp;&nbsp;&nbsp;${template.remark }</td>
							<td><fmt:formatDate value="${template.gmtDate}"
									pattern="yyyy-MM-dd" />
							</td>
							<td align="center"><a id="operator_a_${template.templateId}"
								onclick="javascript:openClose('${template.templateId }',this,'${vs.index }')">展开</a><%--  | <a
								href="<%=path%>/dwmisTemplateInfo/listMenus.html?templateId=${template.templateId }&templateName=${template.templateName }" target="mainFrame">修改</a> --%> | <a
								href="javascript:copyTemplate('${template.templateId }')">复制</a> | <a
								href="javascript:accessControl('${template.templateId }')">权限</a> 
								<c:if test="${template.templateId!='vw866dMjb1vuVdhL1LNH8ztG24dUY7FY' }">| <a href="javascript:delTemplate('${template.templateId }')">删除</a> </c:if>
								| <a
								href="javascript:editSubject('','${template.templateId }')">增加主题</a>
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
			${dwmisTemplateInfo.page.pageStr }
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
		function copyTemplate(templateId){
			var templateName=$("#templateName"+templateId).text();
			var dg = new $.dialog({
				title:'复制模板：'+templateName,
				id:'template_copy',
				width:350,
				height:200,
				iconTitle:false,
				cover:true,
				maxBtn:true,
				xButton:true,
				resize:true,
				page:'<%=path%>/dwmisTemplateInfo/toCopy.html?templateId='+templateId
				});
    		dg.ShowDialog();
		}
		function editTemplate(Id) {
			var Name = $("#name_" + Id).text();
			var dg = new $.dialog({
				title : '修改模板：' + Name,
				id : 'category_edit',
				width : 330,
				height : 300,
				iconTitle : false,
				cover : true,
				maxBtn : true,
				resize : true,
				page : 'edit' + Id
			});
			dg.ShowDialog();
		}

		function delTemplate(Id) {
			if (confirm("确定要删除该记录？")) {
				var url = "del.html?templateId=" + Id;
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
		function accessControl(templateId){
			var templateName=$("#templateName_"+templateId).text();
			var dg = new $.dialog({
				title:'修改权限：'+templateName,
				id:'access_control',
				width:600,
				height:400,
				iconTitle:false,
				cover:true,
				maxBtn:true,
				xButton:true,
				resize:true,
				page:'<%=path%>/dwmisTemplateInfo/accessControl'+templateId+'.html'
				});
    		dg.ShowDialog();
		}
		//清空按钮
		function clearValue() {
			$("#templateName").val("");
			search();
		}
		function search() {
			document.getElementById("Form").submit();
		}
		function openClose(templateId,curObj,trIndex){
			var txt = $(curObj).text();
			if(txt=="展开"){
				$(curObj).text("折叠");
				$("#tr"+templateId).after("<tr class='main_info' id='tempTr"+templateId+"'><td colspan='4'>数据载入中</td></tr>");
				if(trIndex%2==0){
					$("#tempTr"+templateId).addClass("main_table_even");
				}
				var url = "<%=path%>/subjectInfo/getSubjectByTemplateId.html?templateId="+templateId;
				$.get(url,function(data){
					if(data.length>0){
						var html = "";
						$.each(data,function(i){
							html = "<tr style='height:24px;line-height:24px;' name='subTr"+templateId+"' id='tr"+templateId+"' onmouseover='this.style.backgroundColor=\"#D2E9FF\"' onmouseout='this.style.backgroundColor=\"\"' ondblclick='editSubject(\""+this.subjectId+"\",\""+templateId+"\")' title='双击编辑主题'>";
							html += "<td></td>";
							html += "<td align='center' style='padding-left: 80px;'><span style='width:0px;display:inline-block;float: center;'></span>";
							if(i==data.length-1)
								html += "<img src='<%=basePath%>images/joinbottom.gif' style='vertical-align: middle;'/>";
							else
								html += "<img src='<%=basePath%>images/join.gif' style='vertical-align: middle;'/>";
							html += "<span style='width:100px;text-align:left;display:inline-block;'>"+this.subjectName+"</span>";
							html += "</td>";
							if(this.isShow==0){
								html += "<td align='center'>&nbsp;&nbsp;"+this.remark+"(<font color='red'>隐藏</font>)</td>";
							}else{
								html += "<td align='center'>&nbsp;&nbsp;"+this.remark+"</td>";
							}
							html += "<td align='center'></td>";
							html += "<td><a href='###' onclick='editSubject(\""+this.subjectId+"\",\""+templateId+"\")'>编辑</a> | <a href='###' onclick='delSubject(\""+this.subjectId+"\",\""+templateId+"\",false)'>删除</a>| <a href='###' onclick='editSuject(\""+this.subjectId+"\",\""+templateId+"\",false)'>设计主题</a></td>";
							html += "</tr>";
							$("#tempTr"+templateId).before(html);
						});
						$("#tempTr"+templateId).remove();
						if(trIndex%2==0){
							$("tr[name='subTr"+templateId+"']").addClass("main_table_even");
						}
					}else{
						$("#tempTr"+templateId+" > td").html("没有相关数据");
					}
				},"json");
			}else{
				$("#tempTr"+templateId).remove();
				$("tr[name='subTr"+templateId+"']").remove();
				$(curObj).text("展开");
			}
		}
		function editSubject(subjectId,templateId){
			var titleName="";
			if(subjectId==null ||subjectId==""){
					titleName="新增主题";
			}else{
				titleName="编辑主题";
			}
			var dg = new $.dialog({
				title:titleName,
				id:'menu_edit',
				width:400,
				height:310,
				iconTitle:false,
				cover:true,
				maxBtn:true,
				fixed:true,
				xButton:true,
				resize:true,
				page:'<%=path%>/subjectInfo/editSubject.html?templateId='+templateId+'&subjectId='+subjectId
				});
    		dg.ShowDialog();
		}
		
		function editSuject(subjectId,templateId)
		{
			location.href="<%=path%>/dwmisModule/getModuleInfos.html?subjectId="
					+ subjectId+"&templateId="+templateId;
		}
		
		function delSubject(subId,templateId,isParent){
			var flag = false;
			if(isParent){
				if(confirm("确定要删除该主题吗？其下关联数据将一并删除！")){
					flag = true;
				}
			}else{
				if(confirm("确定要删除该主题吗？")){
					flag = true;
				}
			}
			if(flag){
				var url = "<%=path%>/subjectInfo/del"+subId;
				$.get(url,function(data){
					if(data=="success")
					{
						alert("删除成功");//tr
                        if(document.getElementById("tr"+templateId)==null)
						{
							document.getElementById("tr"+templateId).style.display="none";
						}else
						{
							document.location.href = "list";
						}
					}else
					{
						alert("删除失败");
					}
				});
			}
		}
	</script>
</body>
</html>