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
    var c1 = document.getElementsByName("commCodes1");
    var c2 = document.getElementsByName("commCodes2");
    var c3 = document.getElementsByName("commCodes3");
    var c4 = document.getElementsByName("commCodes4");
    var c5 = document.getElementsByName("commCodes5");
    var sign=0;
     for(var a=0;a<c1.length;a++){
    	 if(c1[a].value == ''){
    		 sign++;
    	 }
     }
     for(var b=0;b<c2.length;b++){
    	 if(c2[b].value == ''){
    		 sign++;
    	 }
     }
     for(var c=0;c<c3.length;c++){
    	 if(c3[c].value == ''){
    		 sign++;
    	 }
     }
     for(var d=0;d<c4.length;d++){
    	 if(c4[d].value == ''){
    		 sign++;
    	 }
     }
     for(var e=0;e<c5.length;e++){
    	 if(c5[e].value == ''){
    		 sign++;
    	 }
     }
     if(sign>0){
    	 alert('所有指标配置不能为空!');
     }else{
     	 $("#developMonthForm").submit();
     }

}

</script>
</head>
<body>
<form action="<%=path %>/dwpas/updateDevelopMonth" name="developMonthForm" id="developMonthForm" target="result"  method="post">
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
											<td align="right" width="15%">模块名称：</td>
											<td width="30%"><input type="hidden" name="moduleIds"
												id="moduleIds" value="${module.moduleId}" /> <input
												name="moduleNames" id="moduleNames"
												value="${module.moduleName}" class="jwy_layer_input"
												size="22" style='width: 90%;' /></td>
											<td align="center" width="15%"></td>
											<td></td>
										</tr>
										<tr>
											<td align="right" width="15%">帮助信息：</td>
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
											<table border="0" width="800px" cellspacing="3"
												cellpadding="0">
												<tr>
													<td align="right" width="15%">显示名称：</td>
													<td width="85%" colspan="3" align="left"><input type="hidden" value="${column.columnId }" name="columnIds" />
													<input name="displayNames"
														value="${column.columnDisplayName }" class="jwy_layer_input" size="30" />
													</td>
												</tr>
												
												<!-- 当月值 ，上月值，同比，去年同期，环比     开始-->
												<tr>
													<td align="right" width="15%">&nbsp;&nbsp;&nbsp;当月值：</td>
													<td width="85%" colspan="3" align="left">
													<input type="hidden" name="types1" value="1"/>
													<input name="commCodes1"
														class="jwy_layer_input" id="sykpi_a_code${mm.index+1}${sy.index+1}"
														value="${column.comcodeMap.com_code_1}" size="30"
														readonly="readonly" style="color:gray;width:160px;font-weight:bold;"/>
														&nbsp;&nbsp;指标名称:&nbsp;&nbsp;&nbsp;&nbsp;
														<input id="remarkkpi_a_code${mm.index+1}${sy.index+1}" name="value1" value="${column.comcodeMap.com_name_1}" size="40" readonly="readonly" style="color:gray;width:200px;font-weight:bold;"/>
														&nbsp;&nbsp;<input
														type="button" class="jwy_layer_button" value="选择指标"
														onclick="popwin('sykpi_a_code${mm.index+1}${sy.index+1}','${module.dateType}');"
														size="6" />
														</td>
												</tr>
												<tr>
													<td align="right" width="15%">&nbsp;&nbsp;&nbsp;上月值：</td>
													<td width="85%" align="left">
													<input type="hidden" name="types2" value="2"/>
													<input name="commCodes2"
														class="jwy_layer_input" id="sykpi_b_code${mm.index+1}${sy.index+1}"
														value="${column.comcodeMap.com_code_2}" size="30"
														readonly="readonly" style="color:gray;width:160px;font-weight:bold;"/>
														&nbsp;&nbsp;指标名称:&nbsp;&nbsp;&nbsp;&nbsp;
														<input id="remarkkpi_b_code${mm.index+1}${sy.index+1}" name="value2" value="${column.comcodeMap.com_name_2}" size="40" readonly="readonly" style="color:gray;width:200px;font-weight:bold;"/>
														&nbsp;&nbsp;<input
														type="button" class="jwy_layer_button" value="选择指标"
														onclick="popwin('sykpi_b_code${mm.index+1}${sy.index+1}','${module.dateType}');"
														size="6" />
														</td>
												</tr>
													<tr>
													<td align="right" width="15%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;环比：</td>
													<td width="85%" align="left">
													<input type="hidden" name="types3" value="3"/>
													<input name="commCodes3"
														class="jwy_layer_input" id="sykpi_c_code${mm.index+1}${sy.index+1}"
														value="${column.comcodeMap.com_code_3}" size="30"
														readonly="readonly" style="color:gray;width:160px;font-weight:bold;"/>
														&nbsp;&nbsp;指标名称:&nbsp;&nbsp;&nbsp;&nbsp;
														<input id="remarkkpi_c_code${mm.index+1}${sy.index+1}" name="value3" value="${column.comcodeMap.com_name_3}" size="40" readonly="readonly" style="color:gray;width:200px;font-weight:bold;"/>
														&nbsp;&nbsp;<input
														type="button" class="jwy_layer_button" value="选择指标"
														onclick="popwin('sykpi_c_code${mm.index+1}${sy.index+1}','${module.dateType}');"
														size="6" />
														</td>
												</tr>
												<tr>
													<td align="right" width="15%">&nbsp;&nbsp;去年同期：</td>
													<td width="85%" colspan="3" align="left">
													<input type="hidden" name="types4" value="4"/>
													<input name="commCodes4"
														class="jwy_layer_input" id="sykpi_d_code${mm.index+1}${sy.index+1}"
														value="${column.comcodeMap.com_code_4}" size="30"
														readonly="readonly" style="color:gray;width:160px;font-weight:bold;"/>
														&nbsp;&nbsp;指标名称:&nbsp;&nbsp;&nbsp;&nbsp;
														<input id="remarkkpi_d_code${mm.index+1}${sy.index+1}" name="value4" value="${column.comcodeMap.com_name_4}" size="40" readonly="readonly" style="color:gray;width:200px;font-weight:bold;"/>
														&nbsp;&nbsp;<input
														type="button" class="jwy_layer_button" value="选择指标"
														onclick="popwin('sykpi_d_code${mm.index+1}${sy.index+1}','${module.dateType}');"
														size="6" />
														</td>
												</tr>
											   	<tr>
													<td align="right" width="15%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;同比：</td>
													<td width="85%" align="left">
														<input type="hidden" name="types5" value="5"/>
													<input name="commCodes5"
														class="jwy_layer_input" id="sykpi_e_code${mm.index+1}${sy.index+1}"
														value="${column.comcodeMap.com_code_5}" size="30"
														readonly="readonly" style="color:gray;width:160px;font-weight:bold;"/>
														&nbsp;&nbsp;指标名称:&nbsp;&nbsp;&nbsp;&nbsp;
														<input id="remarkkpi_e_code${mm.index+1}${sy.index+1}" name="value5" value="${column.comcodeMap.com_name_5}" size="40" readonly="readonly" style="color:gray;width:200px;font-weight:bold;"/>
														&nbsp;&nbsp;<input
														type="button" class="jwy_layer_button" value="选择指标"
														onclick="popwin('sykpi_e_code${mm.index+1}${sy.index+1}','${module.dateType}');"
														size="6" />
														</td>
												</tr>
												<!-- 当月值 ，上月值，同比，去年同期，环比     结束-->
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
</script>
</body>
</html>
