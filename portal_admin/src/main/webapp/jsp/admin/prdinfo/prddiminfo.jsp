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
<title>数据管理平台</title>
<style type="text/css">
body{width:100%;height:100%;background-color: #FFFFFF;text-align: center;}
.input_txt{width:150px;height:20px;line-height:20px;}
.info_nn{ padding:20px;width:720px;}
.info_tt{width:720px; line-height: 45px;font-weight: bold; font-size: 14px;color:#666;}
.info_tt th{width:120px;text-align:right}
.info_tt td{text-align: left; width:240px}
.select_txt{width:80px;height:22px;line-height:22px; background: url(../images/input_txt.png) no-repeat; border:0px}
</style>

<script type="text/javascript" src="<%=path%>/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript"
	src="<%=path%>/js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>
</head>
<body>
<div class="info_nn">
	<form id="form1" name="form1" method="post"
		action="<%=basePath%>prddim/savePrddim1.html">
		<input type="hidden" name="productId" id="productId" value="${prdinfo.productId}"></input>
		<table width="700" border="0" cellspacing="0" cellpadding="0" class="info_tt">
  <tr>
    <td colspan="2" style="text-align:left; padding-left:18px">产品ID:${prdinfo.productId}</td>
    <th>&nbsp;</th>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <th><font color="red">*</font>用户路径CODE:</th>
    <td><input type="text"
				name="dim1Code" id="sy${1}" value="${prddim.dim1Code}" class="input_txt" 
				maxlength="50" />
				<input type="button" class="select_txt"
				value="选择指标" onClick="popwin('sy${1}');"  /></td><th><font color="red">*</font>用户留存CODE:</th>
    <td><input type="text"
				name="dim4Code" id="sy${4}" value="${prddim.dim4Code}" class="input_txt"
				maxlength="50" />
			<input type="button" class="select_txt" value="选择指标"
				onclick="popwin('sy${4}');" /></td>
  </tr>
  <tr>
    <th><font color="red">*</font>用户路径内层值:</th>
    <td><input type="text"
				name="dim1InValue" id="dim1InValue" value="${prddim.dim1InValue}"
				maxlength="30" onKeyUp="value=value.replace(/[^0-9^.]D*$/,'')"
				onkeypress="value=value.replace(/[^0-9^.]D*$/,'')" / class="input_txt"></td><th><font color="red">*</font>用户留存内层值:</th>
    <td><input type="text"
				name="dim4InValue" id="dim4InValue" value="${prddim.dim4InValue}"
				maxlength="30" onKeyUp="value=value.replace(/[^0-9^.]D*$/,'')"
				onkeypress="value=value.replace(/[^0-9^.]D*$/,'')" / class="input_txt"></td>
  </tr>
  <tr>
    <th><font color="red">*</font>用户路径外层值:</th>
    <td><input type="text"
				name="dim1OutValue" id="dim1OutValue" value="${prddim.dim1OutValue}"
				maxlength="30" onKeyUp="value=value.replace(/[^0-9^.]D*$/,'')"
				onkeypress="value=value.replace(/[^0-9^.]D*$/,'')" / class="input_txt"></td><th><font color="red">*</font>用户留存外层值:</th>
    <td><input type="text"
				name="dim4OutValue" id="dim4OutValue" value="${prddim.dim4OutValue}"
				maxlength="30" onKeyUp="value=value.replace(/[^0-9^.]D*$/,'')"
				onkeypress="value=value.replace(/[^0-9^.]D*$/,'')" / class="input_txt"></td>
  </tr>
  <tr>
    <th><font color="red">*</font>业务发展CODE:</th>
    <td><input type="text"
				name="dim2Code" id="sy${2}" value="${prddim.dim2Code}" class="input_txt"
				maxlength="50" />
			<input type="button" class="select_txt" value="选择指标"
				onclick="popwin('sy${2}');" /></td><th><font color="red">*</font>用户声音CODE:</th>
    <td><input type="text"
				name="dim5Code" id="sy${5}" value="${prddim.dim5Code}" class="input_txt"
				maxlength="50" />
			<input type="button" class="select_txt" value="选择指标"
				onclick="popwin('sy${5}');" /></td>
  </tr>
  <tr>
    <th><font color="red">*</font>业务发展内层值:</th>
    <td><input type="text"
				name="dim2InValue" id="dim2InValue" value="${prddim.dim2InValue}"
				maxlength="30" onKeyUp="value=value.replace(/[^0-9^.]D*$/,'')"
				onkeypress="value=value.replace(/[^0-9^.]D*$/,'')" / class="input_txt"></td><th><font color="red">*</font>用户声音内层值:</th>
    <td><input type="text"
				name="dim5InValue" id="dim5InValue" value="${prddim.dim5InValue}"
				maxlength="30" onKeyUp="value=value.replace(/[^0-9^.]D*$/,'')"
				onkeypress="value=value.replace(/[^0-9^.]D*$/,'')" / class="input_txt"></td>
  </tr>
  <tr>
    <th><font color="red">*</font>业务发展外层值:</th>
    <td><input type="text"
				name="dim2OutValue" id="dim2OutValue"
				value="${prddim.dim2OutValue }" maxlength="30"
				onkeyup="value=value.replace(/[^0-9^.]D*$/,'')"
				onkeypress="value=value.replace(/[^0-9^.]D*$/,'')" / class="input_txt"></td><th><font color="red">*</font>用户声音外层值:</th>
    <td><input type="text"
				name="dim5OutValue" id="dim5OutValue" value="${prddim.dim5OutValue}"
				maxlength="30" onKeyUp="value=value.replace(/[^0-9^.]D*$/,'')"
				onkeypress="value=value.replace(/[^0-9^.]D*$/,'')" / class="input_txt"></td>
  </tr>
  <tr>
    <th><font color="red">*</font>用户视图CODE:</th>
    <td><input type="text"
				name="dim3Code" id="sy${3}" value="${prddim.dim3Code}" class="input_txt"
				maxlength="50" />
			<input type="button" class="select_txt" value="选择指标"
				onclick="popwin('sy${3}');" /></td><th><font color="red">*</font>场景交叉CODE:</th>
    <td><input type="text"
				name="dim6Code" id="sy${6}" value="${prddim.dim6Code}" class="input_txt"
				maxlength="50" />
			<input type="button" class="select_txt" value="选择指标"
				onclick="popwin('sy${6}');" /></td>
  </tr>
  <tr>
    <th><font color="red">*</font>用户视图内层值:</th>
    <td><input type="text"
				name="dim3InValue" id="dim3InValue" value="${prddim.dim3InValue}"
				maxlength="30" onKeyUp="value=value.replace(/[^0-9^.]D*$/,'')"
				onkeypress="value=value.replace(/[^0-9^.]D*$/,'')" / class="input_txt"></td><th><font color="red">*</font>场景交叉内层值:</th>
    <td><input type="text"
				name="dim6InValue" id="dim6InValue" value="${prddim.dim6InValue}"
				maxlength="30" onKeyUp="value=value.replace(/[^0-9^.]D*$/,'')"
				onkeypress="value=value.replace(/[^0-9^.]D*$/,'')" / class="input_txt"></td>
  </tr>
  <tr>
    <th><font color="red">*</font>用户视图外层值:</th>
    <td><input type="text"
				name="dim3OutValue" id="dim3OutValue" value="${prddim.dim3OutValue}"
				maxlength="30" onKeyUp="value=value.replace(/[^0-9^.]D*$/,'')"
				onkeypress="value=value.replace(/[^0-9^.]D*$/,'')" / class="input_txt"></td><th><font color="red">*</font>场景交叉外层值:</th>
    <td><input type="text"
				name="dim6OutValue" id="dim6OutValue" value="${prddim.dim6OutValue}"
				maxlength="30" onKeyUp="value=value.replace(/[^0-9^.]D*$/,'')"
				onkeypress="value=value.replace(/[^0-9^.]D*$/,'')" / class="input_txt"></td>
  </tr>
</table>

		
		
		
		
		
		
		
	
		
		
	</form></div>
	<iframe name="result" id="result" src="about:blank" frameborder="0"
		width="0" height="0"></iframe>
	<script type="text/javascript">
var dg;
$(document).ready(function(){
	dg = frameElement.lhgDG;
	dg.addBtn('ok','保存',function(){
		$("#form1").submit();
	});
});
function success(){
	if(dg.curWin.document.forms[0]){
		dg.curWin.document.forms[0].action = dg.curWin.location+"";
		dg.curWin.document.forms[0].submit();
	}else{
		dg.curWin.location.reload();
	}
	dg.cancel();
}
function popwin(sign){
	var dg = new $.dialog({
		title:'指标搜索',
		id:'show_commoncode',
		width:600,
		height:500,
		iconTitle:false,
		cover:true,
		maxBtn:true,
		xButton:true,
		resize:true,
		page:'<%=path%>/prddim/kpilist' + sign + '.html'
			});
			dg.ShowDialog();
		}
		function success() {
			if (dg.curWin.document.forms[0]) {
				dg.curWin.document.forms[0].action = dg.curWin.location + "";
				dg.curWin.document.forms[0].submit();
			} else {
				dg.curWin.location.reload();
			}
			dg.cancel();
		}
		
	</script>
</body>
</html>
