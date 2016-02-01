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
<form action="<%=path %>/dwpas/managePopedom" name="accessForm" id="accessForm" target="result"  method="post">
<input type="hidden" value="${templateId}" id="templateId"  name="templateId"/>
<div class="qx_layer">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td colspan="2"><table width="440" border="0" cellspacing="0" cellpadding="0">
  <tr class="jwy_layer_tt">
    <td width="33%"><strong>角色</strong></td>
    <td width="33%">&nbsp;</td>
    <td width="34%">&nbsp;</td>
  </tr>
  <tr class="jwy_layer_tt">
    <td colspan="3">
	
	<!--循环以下DIV-->
	<c:forEach items="${rlist}" var="role">
	<div class="juese_jsk">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="18%" valign="top">
    <div class="qx_layer_checkbox">
     <c:set var="ss" value=""></c:set>
     <c:forEach items="${role.dwpasRightList }" var="dwpasRight">
      <c:if test="${templateId == dwpasRight}">
      <c:set var="ss" value="checked"></c:set>
      </c:if>
      </c:forEach>
      <input type="checkbox" name="roleIds" value="${role.roleId}" ${ss}/>
    </div></td>
    <td width="82%" valign="top"><span class="qx_layer_text">${role.roleName}</span></td>
  </tr>
</table>
	</div>
	</c:forEach>
	<!--循环结束--></td>
    </tr>
  <tr class="jwy_layer_nn jwy_layer_mm2">
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr class="jwy_layer_nn">
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr class="jwy_layer_tt">
    <td><strong>用户</strong></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  
  <tr class="jwy_layer_tt">
  <td colspan="3">
  <!--循环以下DIV-->
  <c:forEach items="${ulist}" var="user">
  <div class="juese_jsk">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="18%" valign="top"><div class="qx_layer_checkbox">
        
         <c:set var="tt" value=""></c:set>
      <c:forEach items="${user.dwpasRightList }" var="dwpasRight">
      <c:if test="${dwpasRight == templateId}">
      <c:set var="tt" value="checked"></c:set>
      </c:if>
      </c:forEach>
            <input type="checkbox" name="userIds" value="${user.userId}" ${tt}/>
        </div></td>
        <td width="82%" valign="top"><span class="qx_layer_text">${user.username}</span></td>
      </tr>
    </table>
  </div>
  </c:forEach>
  <!--循环结束-->
  </td>
    </tr>
</table></td>
    </tr>
</table>
</div>
</form>
<iframe name="result" id="result" src="about:blank" frameborder="0" width="0" height="0"></iframe>
<script type="text/javascript">
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
