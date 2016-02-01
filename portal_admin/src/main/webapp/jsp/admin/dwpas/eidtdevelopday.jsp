<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>数据管理平台</title>
<link type="text/css" rel="stylesheet" href="<%=path %>/css/layer.css"/>
<link type="text/css" rel="stylesheet" href="<%=path%>/css/main.css" />
<script type="text/javascript" src="<%=path %>/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>
<script type="text/javascript">
function formSubmit(){
	var obj=document.getElementById("isShow")
	if(obj.checked){
		obj.value=1;
	}else{
		obj.value=0;
	}
	var codes = document.getElementsByName("commCodes");
	var sign=0;
	for(var i=0;i<codes.length;i++){
		if(codes[i].value == ''){
			sign++;
		}
	}
	 if(sign>0){
		 alert('所有指标配置不能为空!');
	 }else{
		$("#developForm").submit();
	 }
	
}
</script>
</head>
<body>
<form action="<%=path %>/dwpas/updateDevelop" name="developForm" id="developForm" target="result"  method="post">
  <div class="search_div">
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td align="left" width="80%"><div style="font-size:12px;font-family:'宋体';">当前位置:&nbsp;${templateName}&nbsp;&gt;&nbsp;<font color="red">${menuName}</font>&nbsp;</div>
		    </td>
		    <td align="left">
		         <input type="button" value="保存" onclick="formSubmit()"/>&nbsp;
		    	 <input type="button" value="返回" onclick="javascrit:location.href='<%=path%>/dwpas/listJingWeiYiMenus${menu.templateId}.html?templateName=${templateName}';"/>
		    </td>
		</tr>
	</table>
	</div>
<div class="jwy_layer">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="center">
						<fieldset>
	   <legend style='color:red;'>菜单信息</legend>
		<table border="0" width="100%">
		  <tr>
			 <td width="15%" align="center">菜&nbsp;单&nbsp;名：<input name="menuId" id="menuId" value="${menu.menuId}" type="hidden" /></td>
			 <td width="17%"><input name="menuName" id="menuName" value="${menu.menuName }" /></td>
			 <td width="15%" align="center">日期范畴：</td>
			 <td width="17%">
				<select  disabled="disabled"  style="width:120px;">
													<option value=" ">请选择</option>
													<c:choose>
														<c:when test="${dateType eq 'D' }">
															<option value="D" selected="selected">日</option>
															<option value="M">月</option>
														</c:when>
														<c:when test="${dateType eq 'M' }">
															<option value="D">日</option>
															<option value="M" selected="selected">月</option>
														</c:when>
													</c:choose>
			   </select>
			 </td>
			 <td width="15%" align="center">是否可见：</td>
			 <td align="left">
				<c:set var="ss"  value=""></c:set> 
				<c:if test="${menu.isShow ==1 }">
				 <c:set var="ss" value="checked"></c:set>
				</c:if> 
				<input type="checkbox" id="isShow" value="${menu.isShow}" name="isShow" ${ss }/>
			 </td>
		  </tr>
	   </table>
	</fieldset>
					</td>
				</tr>
				<c:forEach items="${modulelist}" var="module" varStatus="mm">
					<tr class="jwy_layer_nn">
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td>
							<fieldset>
								<legend style='color: red; text-align: center;' align="left">${module.moduleName}</legend>
								<div style="clear: both;"></div>
								<fieldset>
									<legend>
										<b>模块信息</b>
									</legend>
									<table border="0" width="800px" cellspacing="3" cellpadding="0">
										<tr>
											<td align="center" width="15%">模块名称：</td>
											<td width="30%"><input type="hidden" name="moduleIds"
												id="moduleIds" value="${module.moduleId}" /> <input
												name="moduleNames" id="moduleNames"
												value="${module.moduleName}" class="jwy_layer_input"
												size="22" style='width: 90%;' /></td>
											<td align="center" width="15%"></td>
											<td></td>
										</tr>
										<tr>
											<td align="center" width="15%">帮助信息：</td>
											<td width="50%"><textarea name="moduleRemark" rows="4" cols="80" style='width: 90%;'>${module.remark }</textarea></td>
											<td align="center" width="15%"></td>
											<td></td>
										</tr>
									</table>
								</fieldset>
								<fieldset>
									<legend>
										<b>关联栏目</b>
									</legend>
									<c:if test="${not empty module.columnlist}">
										<c:forEach items="${module.columnlist}" var="column"
											varStatus="sy">
											<c:if test="${sy.index>=1}">
												<hr style='border: 1px dashed #EED5B7;' />
											</c:if>
<%-- 											  <c:choose> --%>
<%-- 											    <c:when test="${sy.index+1 == 1}"> --%>
<!-- 											    <div style="margin-left:180px"> -->
<%-- 											    <strong>大类名称</strong><input value="${column.columnKind}"  name="columnKinds" onkeyup="tongbu(this.value,${mm.index+1},${fn:length(module.columnlist)});" onblur="tongbu(this.value,${mm.index+1},${fn:length(module.columnlist)});" id="columnKinds${mm.index+1}${sy.index+1}"/> --%>
<!-- 											    </div> -->
<!-- 											      <hr style='border: 1px dashed #EED5B7;' /> -->
<%-- 											    </c:when> --%>
<%-- 											    <c:otherwise> --%>
<%-- 											    <input value="${column.columnKind}"  name="columnKinds" type="hidden" id="columnKinds${mm.index+1}${sy.index+1}"/> --%>
<%-- 											    </c:otherwise> --%>
<%-- 											    </c:choose> --%>
											<table border="0" width="800px" cellspacing="3" cellpadding="0">
												<tr>
													<td align="center" width="15%">栏目名称：</td>
													<td width="50%"><input type="hidden" name="columnNames" value="${column.columnName }" /><input name="displayNames"
														value="${column.columnDisplayName }"
														class="jwy_layer_input" size="60" style='width: 90%;' /></td>
													<td align="center" width="15%" ></td>
													<td ></td>	
												</tr>
												

												
												<tr>
													<td align="center" width="15%">指标编码：</td>
													<td width="50%"><input type="hidden" value="${column.columnId }" name="columnIds" /> <input name="commCodes"
														class="jwy_layer_input" id="sy${mm.index+1}${sy.index+1}"
														value="${column.comkpilist }" size="60"
														readonly="readonly" style="width:90%;color:gray;"/>
														</td>
													<td align="center" width="15%">&nbsp;&nbsp;<input
														type="button" class="jwy_layer_button" value="选择指标"
														onclick="popwin('sy${mm.index+1}${sy.index+1}','${module.dateType}');"
														size="6" /></td>
													<td></td>
												</tr>
												<tr>
													<td align="center" width="15%">指标名称：</td>
													<td width="50%"><input name="remarks" type="text" class="jwy_layer_input" value="${column.remark}" size="60"
														id="remark${mm.index+1}${sy.index+1}" style="width:90%; color:gray;" readonly="readonly"/>
													</td>
												</tr>
											</table>
										</c:forEach>
									</c:if>
								</fieldset>

							</fieldset>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
</form>
<iframe name="result" id="result" src="about:blank" frameborder="0" width="0" height="0"></iframe>
<script type="text/javascript">
//只能选择一个指标
function popwin(sign,kpiType){
	var dg = new $.dialog({
		title:'选择指标',
		id:'show_commoncode',
		width:600,
		height:400,
		iconTitle:false,
		cover:true,
		maxBtn:true,
		xButton:true,
		resize:true,
		page:'<%=path%>/dwpas/getAllComKpiInfo'+sign+'_'+kpiType+'.html?selectedKpiCodes='+document.getElementById(sign).value+"&kpiCodeCount=1"
		});
	dg.ShowDialog();
}
function success(){
	alert('修改成功!');
}
function tongbu(content,mo,cl){
	for(var i=2;i<=cl;i++){
		document.getElementById("columnKinds"+mo+i).value = content;
	}
}
</script>
</body>
</html>
