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
<title>数据管理平台</title>
<link type="text/css" rel="stylesheet" href="<%=path %>/css/layer.css"/>
<script type="text/javascript" src="<%=path %>/js/jquery-1.5.1.min.js"></script>
</head>

<body>
	<form action="<%=path %>/role/saveAuthDwmis" name="accessForm" id="accessForm" target="result"  method="post">
	<input type="hidden" value="${role.roleId}" id="roleId"  name="roleId"/>
	<div class="qx_layer">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td colspan="2">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr class="jwy_layer_tt">
							<td width="33%"><strong>瞭望塔模板选择：</strong></td>
							<td width="33%">&nbsp;</td>
							<td width="34%">&nbsp;</td>
						</tr>
						<tr class="jwy_layer_tt">
							<td colspan="3">
								<c:forEach items="${dwmisTemplateInfoList}" var="template" varStatus="tt">
								<div class="juese_jsk">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td width="18%" valign="middle">
												<c:set var="ss" value=""></c:set>
												<c:if test="${template.templateId == role.dwmisRights }">
												<c:set var="ss" value="checked"></c:set>
												</c:if>
												<input type="radio" onclick="check(this);" name="templateIds" value="${template.templateId}" ${ss} 
													id="radio_${tt.index+1 }"/>
											</td>
											<td width="82%" valign="middle" title="${template.templateName}">
												<label for="radio_${tt.index+1 }" style="cursor: pointer;"><div style='width:120px; line-height:25px; text-overflow:ellipsis; white-space:nowrap; overflow:hidden;'>${template.templateName}</div></label></td>
										</tr>
									</table>
								</div>
								</c:forEach>
							</td>
						 </tr>
					</table>
				</td>
			 </tr>
		</table>
	</div>
</form>
<iframe name="result" id="result" src="about:blank" frameborder="0" width="0" height="0"></iframe>
<script type="text/javascript">
var tempradio= null;    
function check(checkedRadio)    
{    
    if(tempradio== checkedRadio){  
    	
        tempradio.checked=false;  
        tempradio=null;  
      }   
       else{  
    
           tempradio= checkedRadio;    
        }  
 } 

var dg;
$(document).ready(function(){
	dg = frameElement.lhgDG;
	dg.addBtn('ok','保存',function(){
		$("#accessForm").submit();
	});
});
function success(){
	alert("操作成功！");
	if(dg.curWin.document.forms[0]){
		dg.curWin.document.forms[0].action = dg.curWin.location+"";
		dg.curWin.document.forms[0].submit();
	}else{
		dg.curWin.location.reload();
	}
	dg.cancel();
}
function failed(){
	alert("操作失败！");
}
</script>
</body>
</html>