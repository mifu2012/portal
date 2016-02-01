<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Example_Info</title>
<link type="text/css" rel="stylesheet" href="../css/main.css"/>
<style type="text/css">
body{margin:0;padding:0;font-size:12px;line-height:22px;font-family:"宋体","Arial Narrow",HELVETICA;background:#fff;}
form,ul,li,p,h1,h2,h3,h4,h5,h6,table,tr,td{margin:0;padding:0;}
.input_txt{width:150px;height:20px;line-height:20px;}
.infot{height:auto;line-height:40px; width:600px;font-size: 12px; margin:0 auto}
.infotn{text-align:right; width:150px}
.infotm{text-align:left; width:150px}
</style>
</head>
<body>
	<form action="save.html" name="kpiinfoForm" id="kpiinfoForm" target="result" method="post" onSubmit="checkInfo();">
	<table width="600" border="0" cellpadding="0" cellspacing="0"  class="infot" style="font-size:14px; color:#666; font-weight:bold">
  <tr>
    <td class="infotn" align="left">指标CODE:</td>
			<td class="infotm" align="left"><input type="text" name="kpiCode" id="kpiCode" class="input_txt" value="${kpiinfo.kpiCode }"/></td>
  			<td class="infotn">是否大盘指标:</td>
			<td class="infotm"><select name="isOverall" id="isOverall">
							<option value="0"  <c:if test="${kpiinfo.isOverall eq 0 }">selected</c:if>>否</option>
							<option value="1"  <c:if test="${kpiinfo.isOverall eq 1 }">selected</c:if>>是</option>
				</select>			</td>
  </tr>
  <tr>
  
  <td class="infotn" align="left">指标名称:</td>
			<td class="infotm" align="left"><input type="text" name="kpiName" id="kpiName" class="input_txt"  value="${kpiinfo.kpiName}" /></td>
	<td class="infotn">是否均值:</td>
			<td class="infotm"><select name="isAverage">
							<option value="0" <c:if test="${kpiinfo.isAverage eq 0 }">selected</c:if>>否</option>
							<option value="1" <c:if test="${kpiinfo.isAverage eq 1 }">selected </c:if>>是</option>
				</select>			</td>	
  </tr>
  <tr>
   <td class="infotn">显示名称:</td>
			<td class="infotm"><input type="text" name="dispName" id="dispName" class="input_txt" value="${kpiinfo.dispName }"/></td>
			<td class="infotn">是否显示:</td>
			<td class="infotm"><select name="isShow">
							<option value="1" <c:if test="${kpiinfo.isShow eq 1 }">selected </c:if>>是</option>
							<option value="0" <c:if test="${kpiinfo.isShow eq 0 }">selected </c:if>>否</option>
				</select>			</td>
  </tr>
  <tr>
   <td class="infotn">指标类型:</td>
			<td class="infotm"><select name="kpiType">
							<option value="1" <c:if test="${kpiinfo.kpiType eq 1 }">selected</c:if>>日统计指标</option>
							<option value="2" <c:if test="${kpiinfo.kpiType eq 2 }">selected</c:if>>周统计指标</option>
							<option value="3" <c:if test="${kpiinfo.kpiType eq 3 }">selected</c:if>>月统计指标</option>
				</select>			</td>
			<td class="infotn">是否可用:</td>
			<td class="infotm"><select name="isUse">
                        	<option value="1" <c:if test="${kpiinfo.isUse eq 1 }">selected </c:if>>是</option>
							<option value="0" <c:if test="${kpiinfo.isUse eq 0 }">selected </c:if>>否</option>
				</select>			</td>
  </tr>
  <tr>
   <td class="infotn">显示顺序:</td>
			<td class="infotm"><input type="text" id="showOrder1" name="showOrder"  value="${cKpiInfoForm.showOrder}" maxlength="3" onkeyup='this.value=this.value.replace(/[^0-9]D*$/,"")' onkeypress='this.value=this.value.replace(/[^0-9]D*$/,"")'/></td>
			<td class="infotn">是否峰值:</td>
			<td class="infotm"><select name="isMax">
							<option value="0" <c:if test="${kpiinfo.isMax eq 0 }">selected </c:if>>否</option>
							<option value="1" <c:if test="${kpiinfo.isMax eq 1 }">selected </c:if>>是</option>
				</select>			</td>
  </tr>
  <tr>
    <td class="infotn">换算单位:</td>
			<td class="infotm"> <input type="text" id="unit" name="unit" value="${kpiinfo.unit}" maxlength="5"/></td>
			<td class="infotn">是否百分比:</td>
			<td class="infotm"><select name="isPercent">
							<option value="0"  <c:if test="${kpiinfo.isPercent eq 0 }">selected </c:if>>否</option>
							<option value="1"  <c:if test="${kpiinfo.isPercent eq 1 }">selected </c:if>>是</option>
				</select>			</td>
  </tr>
  <tr>
  <td class="infotn">显示小数位数:</td>
			<td class="infotm"><input type="text" id="decimalNum" name="decimalNum"  value="${cKpiInfoForm.decimalNum}" maxlength="3" onkeyup='this.value=this.value.replace(/[^0-9]D*$/,"")' onkeypress='this.value=this.value.replace(/[^0-9]D*$/,"")'/></td>
    
  			<td class="infotn">指标大类:</td>
			<td class="infotm"> <select name="parentCode">
							<c:forEach items="${cSysTypeDOList}" var="item">
								<option value="${item.typeId}" selected >${item.typeName}</option>
							</c:forEach>
						</select>	</td>
  </tr>
  
  <tr>
  <td class="infotn">换算方式:</td>
			<td class="infotm"><select name="convertType" id="convertType">
							    <option value="0" <c:if test="${kpiinfo.convertType eq 0}">selected</c:if>>不换算</option>
                                <option value="1" <c:if test="${kpiinfo.convertType eq 1}">selected</c:if>>乘</option>
								<option value="2" <c:if test="${kpiinfo.convertType eq 2}">selected</c:if>>除</option>
						</select></td>
    
  			<td class="infotn">是否变化幅度值:</td>
			<td class="infotm"> <select name="isVariation">
							<option value="0"  <c:if test="${kpiinfo.isVariation eq 0 }">selected</c:if> >否</option>
							<option value="1"  <c:if test="${kpiinfo.isVariation eq 1 }">selected </c:if>>是</option>
				</select>			</td>
  </tr>
  <tr>
  <td class="infotn">是否计算指标:</td>
			<td class="infotm">是<input type="hidden" id="isCalKpi" name="isCalKpi" value="1"/></td>
    
  			<td class="infotn">&nbsp;</td>
			<td class="infotm"> &nbsp;	</td>
  </tr>
  <tr>
    <td class="infotn">填写计算规则:</td>
                    	<td colspan="2"><textArea name="roleFormula" id="roleFormula" style="width:300px" rows="6">${kpiinfo.roleFormula}</textArea></td>
  </tr>
   <tr>
    <td>&nbsp; </td>
			<td colspan="3"> <span style="color: #fd0534;width:400px; float:left; margin-left:5px; text-align:left; ">
                    	 指标code必须用[]括起，操作符只能为加,减,乘,除,小括号
                    </span></td>
  </tr>
   
</table>
	</form>
	<iframe name="result" id="result" src="about:blank" frameborder="0" width="0" height="0"></iframe>
	
	<script type="text/javascript" src="../js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="../js/datePicker/WdatePicker.js"></script>
	<script type="text/javascript">
		var dg;
		$(document).ready(function(){
			dg = frameElement.lhgDG;
			if(dg!=null)
			{
				dg.addBtn('ok','保存',function(){
					$("#kpiinfoForm").submit();
				});
			}
		});
		
		function checkInfo(){
			if($("#kpiCode").val()==""){
				alert("请输入指标代码!");
				$("#kpiCode").focus();
				return false;
			}
			if($("#kpiCode").val()=="" && $("#kpiName").val()==""){//新增
				alert("请输入指标名称!");
				$("#kpiName").focus();
				return false;
			}
			if($("#roleFormula").val()==""){
				alert("请输入计算规则!");
				$("#roleFormula").focus();
				return false;
			}
			return true;
		}
		
		function success(){
			if(dg.curWin.document.forms[0]){
				dg.curWin.document.forms[0].action = dg.curWin.location+"";
				dg.curWin.document.forms[0].submit();
			}else{
				dg.curWin.location.reload();
			}
			dg.cancel();
		}
		
		function failed(){
			alert("新增失败，该指标CODE已存在！");
			$("#kpiCode").select();
			$("#kpiCode").focus();
		}
	</script>
</body>
</html>