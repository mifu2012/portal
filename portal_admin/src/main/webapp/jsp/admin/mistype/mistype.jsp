<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>mistype</title>
<link type="text/css" rel="stylesheet" href="css/main.css"/>
</head>
<body>
	<div class="search_div">
	<select name="groupId" id="groupId" style="vertical-align: middle;" onchange='dplay(this);'>
				<option value='' selected >选择全部</option>
				<c:forEach items="${mistypes1}" var="mistype">
				<c:if test="${mistype.groupId == 0 }">
				<option value='${mistype.typeId}'  >${mistype.typeId } - ${mistype.typeName}</option>
				</c:if>
				</c:forEach>
			</select>
		<a href="javascript:addmistype();" class="myBtn" style="vertical-align: right;"><em >新增系统类型</em></a>
			<a href="javascript:addmisgroup();" class="myBtn" style="vertical-align: right;"><em >新增系统类型组</em></a>
	</div>
	<c:forEach items="${mistypes1}" var="mistype1" >
	<c:if test="${mistype1.groupId==0}">
	<form action="systemType/delete.html" method="post" name="exampleForm1" >
	
	<div id="${mistype1.typeId}" > 
    <div align="left" style="background-color: #eee; margin-top: 20px;" ><span style="font-weight:bold; font-size:14px">${mistype1.typeName}</span>
    &nbsp;&nbsp;
	 <a href="javascript:editmisgroup(${mistype1.typeId});" class="myBtn"><em style="font-weight: lighter;">修改</em></a>
	 <c:if test="${mistype1.isDelete eq 1 }">
    &nbsp;&nbsp;
	 <a href="javascript:delmisGroup(${mistype1.typeId});" class="myBtn"><em style="font-weight: lighter;">删除</em></a> 
	 </c:if>
	 <c:if test="${mistype1.typeId==1000 }">
	&nbsp;&nbsp;	 
	 <a href="javascript:systemKpi();" class="myBtn"><em style="font-weight: lighter;">指标分类管理</em></a>
	 </c:if>
	 	 <c:if test="${mistype1.typeId==4000 }">
	&nbsp;&nbsp;	 
	 <a href="javascript:editProductInfo();" class="myBtn"><em style="font-weight: lighter;">产品类型管理</em></a>
	 </c:if>
	  </div>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="main_table">
	
		<tr class="main_head">
			<th width="15%" style="padding-left: 20px" align="left">系统类型ID</th>
			<th width="15%" align="left">系统类型组</th>
			<th width="15%" align="left">系统类型名称</th>
			<th width="45%" >系统类型详情</th>
			<th width="10%" >操作</th>
		</tr>
		       <c:choose>
			    <c:when test="${not empty mistypes1}">
				<c:forEach items="${mistypes1}" var="mistype2">
				<c:if test="${mistype2.groupId !=0 && mistype1.typeId == mistype2.groupId }">
				<tr class="main_info" style="cursor: pointer;" title="双击进行修改" onmouseover="this.style.backgroundColor='#D2E9FF';" onmouseout="this.style.backgroundColor='';" ondblclick="editmistype(${mistype2.typeId });" >
				<td style="padding-left: 20px;" align="left">${mistype2.typeId }</td>
				<td align="left">${mistype2.groupId}</td>
				<td align="left">${mistype2.typeName }</td>
				<td align="left" style="padding-left: 40px;" >${mistype2.detail }</td>
				<td><a href="javascript:editmistype(${mistype2.typeId });">修改</a><c:if test="${mistype2.isDelete eq 1 }" > | <a href="javascript:delmistype(${mistype2.typeId });">删除</a></c:if></td>
				</tr>
				</c:if>	
	       </c:forEach>	
	       </c:when>
	       <c:otherwise>
				<tr class="main_info">
					<td colspan="7">没有相关数据</td>
				</tr>
			</c:otherwise>
	        </c:choose>
	</table>
	</div>
	</form>
	</c:if>
	</c:forEach>
	<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$(".main_info:even").addClass("main_table_even");
		});
		
		function sltAllmistype(){
			if($("#sltAll").attr("checked")){
				$("input[name='ids']").attr("checked",true);
			}else{
				$("input[name='ids']").attr("checked",false);
			}
		}
		
		function addmistype(){
			var dg = new $.dialog({
				title:'新增系统类型信息',
				id:'mistype_new',
				width:340,
				height:330,
				iconTitle:false,
				cover:true,
				maxBtn:true,
				xButton:true,
				fixed:true,
				resize:true,
				page:'mistype/add.html'
				});
    		dg.ShowDialog();
		}
		function addmisgroup(){
			var dg = new $.dialog({
				title:'新增系统类型组信息',
				id:'mistype_new',
				width:340,
				height:310,
				iconTitle:false,
				cover:true,
				maxBtn:true,
				fixed:true,
				xButton:true,
				resize:true,
				page:'mistype/addgroup.html'
				});
    		dg.ShowDialog();
		}
		function editmisgroup(typeId){
			var dg = new $.dialog({
				title:'修改系统类型组信息',
				id:'mistype_new',
				width:340,
				height:310,
				iconTitle:false,
				cover:true,
				maxBtn:true,
				fixed:true,
				xButton:true,
				resize:true,
				page:'mistype/editgroup'+typeId+'.html'
				});
    		dg.ShowDialog();
		}
		
		function editmistype(typeId){
			var dg = new $.dialog({
				title:'修改系统类型信息',
				id:'mistype_edit',
				width:340,
				height:330,
				iconTitle:false,
				fixed:true,
				cover:true,
				maxBtn:true,
				resize:true,
				page:'mistype/edit'+typeId+'.html'
				});
    		dg.ShowDialog();
		}
		
		function delmistype(id){
			if(confirm("系统类型配置关系到整个系统的运行情况！\n请慎重考虑确定要删除该类型？")){
				var url = "mistype/delete"+id+".html";
				$.get(url,function(data){
					if(data=="success"){
						alert("删除成功");
						document.location.href="mistype.html";
					}
				});
			}
		}
		function delmisGroup(typeId,groupId){
			if(confirm("系统类型配置关系到整个系统的运行情况！\n请慎重考虑确定删除该类型组以及其子类型吗?")){
				var url = "mistype/deleteGroup"+typeId+".html";
				$.get(url,function(data){
					if(data=="success"){
						alert("删除成功");
						document.location.href="mistype.html?groupId="+groupId;
					}
				});
			}
		}
		function needDelel(){
			var flag = false;
			try{
				flag=confirm2( '删除该选项将连带删除子类型,确定删除吗? ');
			}catch(e){
				flag=confirm( '删除该选项将连带删除子类型,确定删除吗? ');
			}
			return  flag ;
		}
		function   confirm2(str){   
		    execScript("n   =   msgbox('"+   str   +"',   257,   '自定的的   confirm')",   "vbscript");   
		    return(n   ==   1);   
		} 
		function search(){
			$("#mistypeForm").submit();
		}
		function dplay(object){	
			 for(var i=1;i<object.options.length;i++){
				if(object.value!=0&&object.value!=object.options[i].value){
					document.getElementById(object.options[i].value+"").style.display="none";
				}else{
					document.getElementById(object.options[i].value).style.display="";
				}
			}
	}
		
		function systemKpi(){
			var dg = new $.dialog({
				title:'指标大类关联',
				id:'to_systemKpi',
				width:1040,
				height:470,
				iconTitle:false,
				cover:true,
				fixed:true,
				xButton:true,
				resize:true,
				page:'typeRelKpi/allParentType.html?&random="+Math.random()'
				});
			dg.ShowDialog();
		}
		
		function editProductInfo(templateId){
			var dg = new $.dialog({
				title:'产品类型维护',				
				width:1040,
				height:470,
				iconTitle:false,
				cover:true, 
				maxBtn:true,
				xButton:true,
				fixed:true,
				resize:true,
				page:'proMarked/getProductInfo.html?&random="+Math.random()'
				});
			dg.ShowDialog();
		}
	</script>
</body>
</html>