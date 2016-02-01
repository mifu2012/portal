<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<title>数据管理平台-龙虎榜</title>
<link type="text/css" rel="stylesheet" href="<%=path %>/css/layer.css"/>
<link type="text/css" rel="stylesheet" href="<%=path%>/css/main.css" />
<script type="text/javascript" src="<%=path %>/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>  
<script type="text/javascript">
function formSubmit(){
	var menuUrl=document.getElementById("menuUrl").value;
	if(menuUrl==null||menuUrl.length==0)
	{
		document.getElementById("menuUrl").value="/Champion/getChampions.html?1=1";
	}
	var obj=document.getElementById("isShow")
	if(obj.checked){
		obj.value=1;
	}else{
		obj.value=0;
	}
	$("#tigerForm").submit();
}
//切换URL
function setMenuUrl(menuOpt)
{
	var platformType=menuOpt.options[menuOpt.selectedIndex].id;
	//alert(platformType);
	if(platformType=="platformType_1")
	{
	   //无线
	   document.getElementById("menuUrl").value="/Champion/getChampions.html?1=1&platformType=1";	
	}else
	{
	   document.getElementById("menuUrl").value="/Champion/getChampions.html?1=1";
	}
}
</script>
</head>
<body>
<form action="<%=path %>/dwpas/updateTiger" name="tigerForm" id="tigerForm" target="result"  method="post">
 <input name="menuId" id="menuId" value="${menu.menuId}" type="hidden" />
 <input name="menuUrl" id="menuUrl" value="${menu.menuUrl}" type="hidden" />
 <div class="search_div">
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td align="left" width="80%"><div style="font-size:12px;font-family:'宋体';">当前位置:&nbsp;${templateName}&nbsp;&gt;&nbsp;<font color="red">${menuName}</font>&nbsp;
			</div>
		    </td>
		    <td align="left">
		         <input type="button" value="保存" onclick="formSubmit()"/>
		    	 <input type="button" value="返回" onclick="javascrit:location.href='<%=path%>/dwpas/listJingWeiYiMenus${menu.templateId}.html?templateName=${templateName}';"/>
		    </td>
		</tr>
	</table>
	</div>
	<div class="jwy_layer">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left">
						<fieldset>
	   <legend style='color:red;'>菜单信息</legend>
		<table border="0" width="100%">
		  <tr>
			 <td width="15%" align="center">菜&nbsp;单&nbsp;名：</td>
			 <td width="17%">
			   <select name="menuName" id="menuName" onchange="setMenuUrl(this);">
			      <option value="产品龙虎榜" id="platformType_0" <c:if test="${menu.menuName eq '产品龙虎榜'}">selected</c:if>>产品龙虎榜</option>
			      <option value="无线龙虎榜" id="platformType_1" style='color:blue;' <c:if test="${menu.menuName eq '无线龙虎榜'}">selected</c:if>>无线龙虎榜</option>
			   </select>
			 </td>
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
								<div style="clear:both;"></div>
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
											<td align="center" width="15%"><c:if test="${module.moduleCode eq 'LHB_PRODUCT_RANKLIST'}"><input type="button" value="关联指标配置" onclick="popwin('${module.moduleId}')"/></c:if></td>
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
							</fieldset>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
</form>
<iframe name="result" id="result" src="about:blank" frameborder="0" width="0" height="0"></iframe>
<script type="text/javascript">
function popwin(moduleId){
	if(moduleId==null||moduleId.length==0){
		alert('请先保存该菜单信息');
		return;
	}
	var dg = new $.dialog({
		title:'产品排行关联指标配置',
		id:'show_commoncode',
		width:600,
		height:450,
		iconTitle:false,
		cover:true,
		maxBtn:true,
		xButton:true,
		resize:true,
		page:'<%=path%>/comkpiinfo1/getTigerComKpiCodes.html?moduleId='+moduleId
		});
	dg.ShowDialog();
}
function success(){
	alert('修改成功!');
}
</script>
</body>
</html>
