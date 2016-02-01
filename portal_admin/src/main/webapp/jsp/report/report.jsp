<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<title>报表管理</title>
<link type="text/css" rel="stylesheet" href="css/main.css"/>
</head>
<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="main_table">
		<tr class="main_head">
			<th width="5%">序号</th>
			<th style="width:50%;">名称</th>
			<!-- <th>路径</th> -->
			<th>报表结构</th>
			<th>操作</th>
		</tr>
		<c:choose>
			<c:when test="${not empty reportList}">
				<c:forEach items="${reportList}" var="report" varStatus="vs">
					<tr class="main_info" id="tr${report.reportId }" onmouseover="this.style.backgroundColor='#D2E9FF';" onmouseout="this.style.backgroundColor='';" <c:if test="${not empty report.subReportList }">ondblclick="openCloseTree(${report.reportId },$('#operator_a_${report.reportId}'),${vs.index })" title='双击打开或关闭' style='cursor:pointer;'</c:if>>
						<td width="5%">${vs.index+1}</td>
						<td style="width:100px; line-height: 15px; text-overflow: ellipsis; white-space: nowrap; overflow: hidden;" title="${report.reportName}">${report.reportName}</td>
						<%-- <td>${report.URL}</td> --%>
						<c:choose>
							<c:when test="${report.isReport eq 1}">
								<td>报表</td>
							</c:when>
							<c:when test="${report.isReport eq 0}">
								<td><font color="red">目录</font></td>
							</c:when>
						</c:choose>
						
						
						<td>
						<c:choose> 
							<c:when test="${not empty report.subReportList }">
							   <a href="###" onclick="openCloseTree(${report.reportId },this,${vs.index })" id="operator_a_${report.reportId}"><b>展开</b></a> | 
							</c:when>
							<c:otherwise>
							   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</c:otherwise>
						</c:choose>
						
						<a href="###" onclick="editreport(${report.reportId })">修改</a>
						<c:choose>
							<c:when test="${report.rptFlag500w==1 }">
							 | <a style="cursor: text;" title="不可删除">删除</a>
							</c:when>
							<c:otherwise>
							 | <a href="###" onclick="delreport(${report.reportId },true)">删除</a>
							 </c:otherwise>
						 </c:choose>
						 </td>
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
			<a href="javascript:addreport();" class="myBtn"><em>新增</em></a>
		</div>
	</div>
	
	<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$(".main_info:even").addClass("main_table_even");
		});
		
		function addreport(){
			var dg = new $.dialog({
				title:'新增报表',
				id:'report_new',
				width:350,
				height:300,
				iconTitle:false,
				cover:true,
				maxBtn:true,
				xButton:true,
				//fixed:true,
				resize:true,
				page:'report/add'
				});
    		dg.ShowDialog();
		}
		
		function editreport(reportId){
			var dg = new $.dialog({
				title:'修改报表',
				id:'report_edit',
				width:350,
				height:300,
				iconTitle:false,
				cover:true,
				//fixed:true,
				maxBtn:true,
				xButton:true,
				resize:true,
				page:'report/edit'+reportId
				});
    		dg.ShowDialog();
		}

        //设计报表
		function designRpt(rptId)
		{
           window.open("<%=basePath%>/NewReport/designReportTemplate"+rptId);
		}
		//删除
		function delreport(reportId,isParent){
			var flag = false;
			if(isParent){
				if(confirm("确定要删除该目录吗？其下子目录或是报表将一并删除！")){
					flag = true;
				}
			}else{
				if(confirm("确定要删除该报表吗？")){
					flag = true;
				}
			}
			if(flag){
				var url = "report/del"+reportId;
				$.get(url,function(data){
					if(data=="success"){
						alert("删除成功！");
						document.location.reload();
					}else{
						alert("删除失败！");
					}
					
				});
			}
		}
		/* 展开二级菜单 */
		function openCloseTree(reportId,curObj,trIndex){
			var txt = $(curObj).text();
			if(txt=="展开"){
				//$(curObj).text("折叠");
				//
				$(curObj).html("<b>折叠</b>");
				$("#tr"+reportId).after("<tr class='main_info' id='tempTr"+reportId+"'><td colspan='4'>数据载入中</td></tr>");
				if(trIndex%2==0){
					$("#tempTr"+reportId).addClass("main_table_even");
				}
				var folderIdArray=new Array();
				var url = "report/sub"+reportId;
				$.get(url,function(data){
					if(data.length>0){
						var html = "";
						$.each(data,function(i){
							if(this.isReport=="0"){
								//目录
								html = "<tr style='height:24px;line-height:24px;' id='subTr"+this.reportId+"' name='subTr"+reportId+"' onmouseover='this.style.backgroundColor=\"#D2E9FF\"' onmouseout='this.style.backgroundColor=\"\"' ondblclick='document.getElementById(\"operator_a_"+this.reportId+"\").click()'>";
							}else
							{
								html = "<tr style='height:24px;line-height:24px;' id='subTr"+this.reportId+"' name='subTr"+reportId+"' onmouseover='this.style.backgroundColor=\"#D2E9FF\"' onmouseout='this.style.backgroundColor=\"\"' >";
							}
							html += "<td></td>";
							html += "<td><span style='width:80px;display:inline-block;'></span>";
							if(i==data.length-1)
								html += "<img src='images/joinbottom.gif' style='vertical-align: middle;'/>";
							else
								html += "<img src='images/join.gif' style='vertical-align: middle;'/>";
							html += "<span style='width: 100px; line-height: 15px; text-overflow: ellipsis; white-space: nowrap; overflow: hidden; text-align:left;display:inline-block;' title="+this.reportName+">"+this.reportName+"</span>";
							html += "</td>";
							if(this.isReport=="0"){
								//目录
								html += "<td><font color='red'>"+"目录" +"</font></td>";
								if(this.subReportList.length>0){
									html += "<td><a href='###'  id='operator_a_"+this.reportId+"' onclick='openClose("+this.reportId+",this,0)'>展开</a> |  <a href='###' onclick='editreport("+this.reportId+")'>修改</a>";
									if(this.rptFlag500w==1){
										html += " | <a style='cursor: text;color: #808080' title='不可删除' ><s>删除</s></a></td>";
									}else{
										html += " | <a href='###' onclick='delreport("+this.reportId+",true)'>删除</a></td>";
									}
									//
								    folderIdArray[folderIdArray.length]=this.reportId;
								}else{
									html += "<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  <a href='###' onclick='editreport("+this.reportId+")'>修改</a>";
									if(this.rptFlag500w==1){
										html += " | <a style='cursor: text;color: #808080' title='不可删除' >删除</a></td>";
									}else{
										html += " | <a href='###' onclick='delreport("+this.reportId+",true)'>删除</a></td>";
									}
								}
							}else if(this.isReport=="1"){
								//报表
								html += "<td>"+ "报表" +"</td>";
								if(this.rptFlag500w==1){
								    html += "<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='###' onclick='editreport("+this.reportId+")'>修改</a>";
									html += " | <a style='cursor: text;color: #808080' title='不可删除' >删除</a></td>";
								}else{
								    html += "<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='###' onclick='editreport("+this.reportId+")'>修改</a>";
									html += " | <a href='###' onclick='delreport("+this.reportId+",false)'>删除</a> | <a href='###' onclick='designRpt("+this.reportId+")'>设计</a></td>";
								}
							}
							html += "</tr>";
							$("#tempTr"+reportId).before(html);
						});
						if(folderIdArray.length>0)
						{
							//自动展开三级菜单
							for(var i=0;i<folderIdArray.length;i++)
							{
								if($("operator_a_"+folderIdArray[i])!=null)
								{
                                   document.getElementById("operator_a_"+folderIdArray[i]).click();
								}
							}
						}
						$("#tempTr"+reportId).remove();
						if(trIndex%2==0){
							$("tr[name='subTr"+reportId+"']").addClass("main_table_even");
						}
					}else{
						$("#tempTr"+reportId+" > td").html("没有相关数据");
					}
				},"json");
			}else{
				/* 折叠所有子节点 */
				var url = "report/sub"+reportId;
				$.get(url,function(data){
					if(data.length>0){
						$.each(data,function(i){
							$("#tempTreeTr"+this.reportId).remove();
							$("tr[name='subTreeTr"+this.reportId+"']").remove();
						});
					}
				},"json");
				/* 折叠当前节点 */
				$("#tempTr"+reportId).remove();
				$("tr[name='subTr"+reportId+"']").remove();
				//$(curObj).text("展开");
				$(curObj).html("<b>展开</b>");
			}
			//document.getElementById("operator_a_"+this.reportId).click();
		}
		/* 展开三级菜单 */
		function openClose(reportId,curObj,trIndex){
			var txt = $(curObj).text();
			if(txt=="展开"){
				//$(curObj).text("折叠");
				$(curObj).html("<b>折叠</b>");
				$("#subTr"+reportId).after("<tr class='main_info' id='tempTreeTr"+reportId+"'><td colspan='4'>数据载入中</td></tr>");
				$("#tempTreeTr"+reportId).addClass("main_table_evenreport");
				var url = "report/sub"+reportId;
				$.get(url,function(data){
					if(data.length>0){
						var html = "";
						$.each(data,function(i){
							html = "<tr style='height:24px;line-height:24px;' name='subTreeTr"+reportId+"' onmouseover='this.style.backgroundColor=\"#D2E9FF\"' onmouseout='this.style.backgroundColor=\"\"' >";
							html += "<td></td>";
							html += "<td><span style='width:140px;display:inline-block;'></span>";
							if(i==data.length-1)
								html += "<img src='images/joinbottom.gif' style='vertical-align: middle;'/>";
							else
								html += "<img src='images/join.gif' style='vertical-align: middle;'/>";
							html += "<span style='width: 100px; line-height: 15px; text-overflow: ellipsis; white-space: nowrap; overflow: hidden; text-align:left;display:inline-block;' title="+this.reportName+">"+this.reportName+"</span>";
							/* style='width:100px;text-align:left;display:inline-block;' */
							html += "</td>";
							/* html += "<td>"+this.URL+"</td>"; */
							if(this.isReport=="0"){
								//目录
								html += "<td><font color='red'>"+"目录" +"</font></td>";
							}else if(this.isReport=="1"){
								//报表
								html += "<td>"+ "报表" +"</td>";
							}
							if(this.rptFlag500w==1){
                               //500W报表
							   html += "<td&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='###' onclick='editreport("+this.reportId+")'>修改</a>";
							}else
							{
                               //设计的报表
							   html += "<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='###' onclick='editreport("+this.reportId+")'>修改</a>";
							}
							if(this.rptFlag500w==1){
								//500W报表
								html += " | <a style='cursor: text;color: #808080' title='不可删除' ><s>删除</s></a></td>";
							}else{
								//设计的报表
								html += " | <a href='###' onclick='delreport("+this.reportId+",false)'>删除</a> | <a href='###' onclick='designRpt("+this.reportId+")'>设计</a></td>";
							}
							html += "</tr>";
							$("#tempTreeTr"+reportId).before(html);
						});
						$("#tempTreeTr"+reportId).remove();
						$("tr[name='subTreeTr"+reportId+"']").addClass("main_table_evenreport");
					}else{
						$("#tempTreeTr"+reportId+" > td").html("没有相关数据");
					}
				},"json");
			}else{
				$("#tempTreeTr"+reportId).remove();
				$("tr[name='subTreeTr"+reportId+"']").remove();
				//$(curObj).text("展开");
				$(curObj).html("<b>展开</b>");
			}
		}
		
	</script>	
</body>
</html>