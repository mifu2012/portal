<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<title>Example</title>
<link href="<%=basePath%>css/css.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>js/jquery-1.5.1.min.js" type="text/javascript"></script>
<style type="text/css">
.input_txt {
	width: 160px;
	height: 20px;
	line-height: 20px;
}
</style>
</head>

<body class="indexbody" style="background: #ffffff;overflow: hidden">
	<!-- 维度主表信息添加或修改 -->
	<form id="eventTypeForm" method="post"
		action="<%=basePath%>/BigEvent/saveEventType.html" target="result">
		<c:choose>
			<c:when test="${eventType eq 'add'}">
				<div style="height: 30px">
					<input type="hidden" id="groupId" name="groupId" value="7000" style="display: none;"> 
					<input type="hidden" id="estimate" value="1111" style="display: none;">
				</div>
				<table style="padding: 20px 10px; margin-left: -60px">
					<tr>
						<td valign="top">
							<table class="kpiManage">
								<tr>
									<td class="mytb" height="40"><font color="red">*</font>类型ID:</td>
									<td style="padding-left: 5px;">
										<input type="text" id="typeId" height="20px" name="typeId" class="input_txt"
										value="${typeId}" readOnly />
									</td>
								</tr>
								<tr>
									<td class="mytb" height="40"><font color="red">*</font>类型名称:</td>
									<td style="padding-left: 5px;">
										<input type="text" id="typeName" class="input_txt" name="typeName" value="" /></td>
								</tr>
								<tr>
									<td class="mytb">类型描述:</td>
									<td style="padding-left: 5px;"><textarea id="detail"
											name="detail" style="width: 162px; height: 80px"></textarea>${detail}</td>
								</tr>
								<!-- 										<tr> -->
								<!-- 											<td align="center" colspan="2"><input type="button" -->
								<!-- 												value="添加" -->
								<!-- 												style="width: 50px; height: 24px; margin-top: 10px;" -->
								<!-- 												id="J-add-dimension" /></td> -->
								<!-- 										</tr> -->
								<tr height="30">
							</table>
						</td>
					</tr>
				</table>
			</c:when>
			<c:when test="${eventType eq 'select'}">
				<input type="hidden" id="groupId" name="groupId" value="7000"
					style="display: none;">
				<input type="hidden" id="estimate" value="2222"
					style="display: none;">
				<div style="height: 30px"></div>
				<table style="padding: 20px 10px; margin-left: -60px">
					<tr>
						<td valign="top">
							<table class="kpiManage">
								<tr>
									<td class="mytb" height="30"><font color="red">*</font>类型ID:</td>
									<td style="padding-left: 5px;"><input type="text"
										id="typeId1" name="typeId" class="input_txt"
										value="${dwpasCSysType.typeId}" readOnly /></td>
								</tr>
								<tr>
									<td class="mytb" height="30"><font color="red">*</font>类型名称:</td>
									<td style="padding-left: 5px;"><input type="text"
										id="typeName1" name="typeName" class="input_txt"
										value="${dwpasCSysType.typeName}" />
										
										<input type="hidden"
										id="typeNameHidden" name="typeNameHidden" class="input_txt"
										value="${dwpasCSysType.typeName}" />
										</td>
								</tr>
								<tr>
									<td class="mytb">类型描述:</td>
									<td style="padding-left: 5px;"><textarea class="ckeditor"
											id="detail1" name="detail" style="width: 162px; height: 80px">${dwpasCSysType.detail}</textarea>
									</td>
								</tr>
								<!-- 										<tr> -->
								<!-- 					                        <td class="mytb" align="left" valign="top"><input -->
								<!-- 												type="button" value="修改" -->
								<!-- 												style="width: 50px; height: 24px; margin-top: 10px;" -->
								<!-- 												id="J-modify-dimension" /></td> -->
								<!-- 										</tr> -->
								<tr height="30"></tr>
							</table>
						</td>
					</tr>
				</table>

			</c:when>
			<c:otherwise>
			</c:otherwise>
		</c:choose>
	</form>
	<iframe name="result" id="result" src="about:blank" frameborder="0"
		width="0" height="0"></iframe>
	<script type="text/javascript">
		var dg;
		$(document).ready(function() {
			dg = frameElement.lhgDG;
			dg.addBtn('ok', '保存', function() {
				var typeId =$.trim($("#typeId").val())
				if (typeId == "")
					typeId = $.trim($("#typeId1").val())
				var typeName =$.trim($("#typeName1").val())//$("#typeName1").val().trim();
				var typeNameHidden = $.trim($("#typeNameHidden").val()) //
				if (typeName == "")
					typeName =$.trim($("#typeName").val())
				if (typeId == "") {
					alert("类型ID不能为空!");
					return false;
				}
				if (typeName == "") {
					alert("类型名称不能为空!");
					return false;
				}
				//如果类型名称未修改则不需要校验
				if(typeNameHidden==typeName){
					$("#eventTypeForm").submit();
				}else{
					//验证类型名称是否已存在
					   $.ajax({
		                      type: "POST",                                                                 
		                      url:encodeURI("<%=basePath%>/BigEvent/checkTypeName.html?typeName="+typeName),
		                      success: function(msg){ 
		        	            if(msg=="success"){
		        	            	$("#eventTypeForm").submit();
		        	             }else{
		        		          alert("类型名称已存在");
		        	           }
		                  }
		              }); 
				}
				
			});

		});

		function success() {
			alert("操作成功！");
			if (dg.curWin.document.forms[0]) {
				dg.curWin.document.forms[0].action = dg.curWin.location + "";
				dg.curWin.document.forms[0].submit();
			} else {
				dg.curWin.location.reload();
			}
			dg.cancel();
		}

		function failed() {
			alert("操作失败！");
		}
	</script>
</body>
</html>
