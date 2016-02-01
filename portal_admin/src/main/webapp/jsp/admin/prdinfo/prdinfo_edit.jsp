<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String rootPath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Example_Info</title>
<link type="text/css" rel="stylesheet" href="<%=rootPath%>/css/main.css" />
<style type="text/css">
body {
	width: 100%;
	height: 100%;
	background-color: #FFFFFF;
	text-align: center;
}

.input_txt {
	width: 150px;
	height: 20px;
	line-height: 20px;
}

.info {
	width: 570px;
	line-height: 40px;
	font-weight: bold;
	font-size: 14px;
	color: #666;
}

.info th {
	width: 80px;
	text-align: right;
	padding-right: 5px
}

.info td {
	width: 180px;
	text-align: left;
}

.select_txt {
	width: 150px;
	height: 20px;
	line-height: 20px;
}
</style>
</head>
<body>
  <div style="height: 30px;"></div>
	<form action="<%=rootPath%>/prdinfo1/save.html" name="prdinfoForm"
		id="prdinfoForm" target="result" method="post"
		onSubmit="return checkInfo();">
		<table border="0" cellpadding="0" cellspacing="0" class="info">
			<tr>
				<th><font color="red">*</font>产品ID:</th>
				<td><input type="text" name="productId" id="productId"
					class="input_txt" value="${prdinfo.productId }" style="ime-mode:disabled;" /></td>
				<th>产品名称:</th>
				<td><input type="text" name="productName" id="productName"
					class="input_txt" value="${prdinfo.productName}" /></td>
			</tr>
			<tr style="display:none;">
				<th>是否为目录:</th>
				<td><select name="isFolder" class="select_txt">
						<option value="0"
							<c:if test="${prdinfo.isFolder eq 0 }">selected </c:if>>否</option>
						<option value="1"
							<c:if test="${prdinfo.isFolder eq 1 }">selected </c:if>>是</option>
				</select></td>
				<th>所属目录:</th>
				<td><select name="parentId" class="select_txt">
						<option selected="selected" value="0">无目录</option>
						<c:forEach items="${prdFolderList}" var="item">
							<option value="${item.productId}"
								<c:if test="${prdinfo.parentId eq item.productId }">selected </c:if>>${item.productName}</option>
						</c:forEach>
				</select></td>
			</tr>


			<tr style="display:none">
				<th>是否在首页显示:</th>
				<td><select name="isIndexShow" class="select_txt">
						<option value="0"
							<c:if test="${prdinfo.isIndexShow eq 0 }">selected </c:if>>否</option>
						<option value="1"
							<c:if test="${prdinfo.isIndexShow eq 1 }">selected </c:if>>是</option>
				</select></td>
				<th>在首页显示的顺序:</th>
				<td><input type="text" id="J-indexShowOrder1"
					name="indexShowOrder" value="${prdinfo.indexShowOrder}"
					maxlength="3"
					onkeyup='this.value=this.value.replace(/[^0-9]D*$/,"")'
					onkeypress='this.value=this.value.replace(/[^0-9]D*$/,"")'
					/ class="input_txt"></td>

			</tr>

			<tr>

				<th>产品标记:</th>
				<td><select name="productMark" class="select_txt">
						<c:forEach items="${misTypeList }" var="misType" >
							<option value="${misType.typeId }" <c:if test="${prdinfo.productMark eq misType.typeId }">selected </c:if>>${misType.typeName}</option>
						   </c:forEach>
				</select></td>
				<th>是否可用:</th>
				<td><select name="isUse" class="select_txt">
						<option value="1"
							<c:if test="${prdinfo.isUse eq 1 }">selected </c:if>>是</option>
						<option value="0"
							<c:if test="${prdinfo.isUse eq 0 }">selected </c:if>>否</option>
				</select></td>
			</tr>
			<!--
		<tr >
			 <th >显示图标的URL:</th>
			<td ><input type="text" name="iconUrl" value="${prdinfo.iconUrl}" maxlength="80"/ class="input_txt"></td>
			
			<th >漏斗ID:</th>
			<td ><input type="text" name="funnelId" value="${prdinfo.funnelId}" maxlength="180"/ class="input_txt"></td>
		</tr>
		 -->
			<tr style="display:none">
				<th>是否显示求助率:</th>
				<td><select name="helpRate"
					onChange="isView(this,'helpRate1');" class="select_txt">
						<option value="0"
							<c:if test="${prdinfo.helpRate eq 0 }">selected </c:if>>否</option>
						<option value="1"
							<c:if test="${prdinfo.helpRate eq 1 }">selected</c:if>>是</option>
				</select></td>
				<!-- 
			<th >漏斗名称:</th>
			<td ><input type="text" name="funnelName" value="${prdinfo.funnelName}" maxlength="128"/ class="input_txt"></td>
			 -->
			</tr>


		</table>
		<div align="left"
			style="width: 570px; font-size: 14px; color: #666; font-weight: bold">
			<div
				style="display: 
                <c:choose>
                <c:when test="${prdinfo.helpRate eq 1 }"> block </c:when>
                <c:otherwise> none</c:otherwise>
                </c:choose>"
				id="helpRate1">
				<table border="0" cellpadding="0" cellspacing="0" class="info">
					<tr>
						<th>预警阈值:</th>
						<td><input type="text" id="J-preAlertValue1"
							name="preAlertValue" class="input_txt"
							value="${dwpasCHelprateShowDO.preAlertValue}" maxlength="30"
							onKeyUp="value=value.replace(/[^0-9^.^0]D*$/,'')"
							onKeyPress="value=value.replace(/[^0-9^.]D*$/,'')"/ ></td>
						<th>报警阈值:</th>
						<td><input type="text" id="J-alertValue1" name="alertValue"
							class="input_txt" value="${dwpasCHelprateShowDO.alertValue}"
							maxlength="30" onKeyUp="value=value.replace(/[^0-9^.]D*$/,'')"
							onKeyPress="value=value.replace(/[^0-9^.]D*$/,'')" /></td>
					</tr>
					<tr>
						<th>最大值:</th>
						<td><input type="text" id="J-maxValue1" name="maxValue"
							class="input_txt" value="${dwpasCHelprateShowDO.maxValue}"
							maxlength="30" onKeyUp="value=value.replace(/[^0-9^.]D*$/,'')"
							onKeyPress="value=value.replace(/[^0-9^.]D*$/,'')" /></td>
						<th>最小值:</th>
						<td><input type="text" id="J-minValue1" name="minValue"
							class="input_txt" value="${dwpasCHelprateShowDO.minValue}"
							maxlength="30" onKeyUp="value=value.replace(/[^0-9^.]D*$/,'')"
							onKeyPress="value=value.replace(/[^0-9^.]D*$/,'')" /></td>
					</tr>
					<tr>
						<th>显示顺序:</th>
						<td><input type="text" id="J-showOrder1" name="showOrder"
							class="input_txt" value="${dwpasCHelprateShowDO.showOrder}"
							maxlength="3"
							onkeyup='this.value=this.value.replace(/[^0-9]D*$/,"")'
							onkeypress='this.value=this.value.replace(/[^0-9]D*$/,"")' /></td>
					</tr>
				</table>
				<!-- 
                <p style="width: 600px;float: left;" >
                    <span  style="width: 130px; text-align: right;padding-right: 5px;float: left;">预警阈值:</span>
                    <span style="width: 180px;text-align: left;"><input type="text" id="J-preAlertValue1" name="preAlertValue" 
                    value="${dwpasCHelprateShowDO.preAlertValue}"
                     maxlength="30" onKeyUp="value=value.replace(/[^0-9^.^0]D*$/,'')" onKeyPress="value=value.replace(/[^0-9^.]D*$/,'')"/ class="input_txt"></span>    
                </p>
                <p style="width: 600px;float: left;">
                    <span style="width: 130px; text-align: right;padding-right: 5px;float: left;">报警阈值:</span>
                    <span style="width: 180px;text-align: left;"><input type="text" id="J-alertValue1" name="alertValue" 
                    value="${dwpasCHelprateShowDO.alertValue}" maxlength="30" onKeyUp="value=value.replace(/[^0-9^.]D*$/,'')" onKeyPress="value=value.replace(/[^0-9^.]D*$/,'')"/></span>    
                </p>
                <p style="width: 600px;float: left;">
                    <span style="width: 130px; text-align: right;padding-right: 5px;float: left;">最大值:</span>
                    <span style="width: 180px;text-align: left;"><input type="text" id="J-maxValue1" name="maxValue" 
                    value="${dwpasCHelprateShowDO.maxValue}"
                     maxlength="30" onKeyUp="value=value.replace(/[^0-9^.]D*$/,'')" onKeyPress="value=value.replace(/[^0-9^.]D*$/,'')"/></span>    
                </p>
                <p style="width: 600px;float: left;">
                    <span style="width: 130px; text-align: right;padding-right: 5px;float: left;">最小值:</span>
                    <span style="width: 180px;text-align: left;"><input type="text" id="J-minValue1" name="minValue" 
                    value="${dwpasCHelprateShowDO.minValue}"
                     maxlength="30" onKeyUp="value=value.replace(/[^0-9^.]D*$/,'')" onKeyPress="value=value.replace(/[^0-9^.]D*$/,'')"/></span>    
                </p>
                <p style="width: 600px;float: left;">
                    <span style="width: 130px; text-align: right;padding-right: 5px;float: left;">显示顺序:</span>
                    <span style="width: 180px;text-align: left;"><input type="text" id="J-showOrder1" name="showOrder" 
                    value="${dwpasCHelprateShowDO.showOrder}"
                    maxlength="3" onkeyup='this.value=this.value.replace(/[^0-9]D*$/,"")' onkeypress='this.value=this.value.replace(/[^0-9]D*$/,"")'/></span>    
                </p>
                 -->
			</div>
		</div>
	</form>
	<iframe name="result" id="result" src="about:blank" frameborder="0"
		width="0" height="0"></iframe>

	<script type="text/javascript"
		src="<%=rootPath%>/js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript">
		var dg;
		$(document).ready(function() {
			dg = frameElement.lhgDG;
			dg.addBtn('ok', '保存', function() {
				$("#prdinfoForm").submit();
			});
			if ($("#productId").val() != "") {
				$("#productId").attr("readonly", "readonly");
				$("#productId").css("color", "gray");
			}
		});

		function checkInfo() {
			if ($("#productId").val() == "") {
				alert("请输入产品ID!");
				$("#productId").focus();
				return false;
			}
			return true;
		}

		function success() {
			alert("修改成功！");
			if (dg.curWin.document.forms[0]) {
				dg.curWin.document.forms[0].action = dg.curWin.location + "";
				dg.curWin.document.forms[0].submit();
			} else {
				dg.curWin.location.reload();
			}
			dg.cancel();
		}
		function isView(obj, id) {
			if (obj.value == 0) {
				document.getElementById(id).style.display = 'none';
			} else {
				document.getElementById(id).style.display = 'block';
			}
		}
		function failed() {
			alert("修改失败！");
		}
	</script>
</body>
</html>