<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="UTF-8">
<head>
<link charset="utf-8" rel="stylesheet"
	href="<%=basePath%>css/alice1.css" />
<link type="text/css" charset="utf-8" rel="stylesheet"
	href="<%=basePath%>css/css.css" />
<script type="text/javascript"
	src="<%=path%>/js/datePicker/WdatePicker.js"></script>
<style type="text/css">
.upload-panel {
	display: inline;
}

.upload-panel .file {
	margin-right: 10px;
}

.ui-fm-item .hide {
	display: none;
}
</style>
<script type="text/javascript">
	araleConfig = {
		combo_host : 'https://static.alipay.com',
		combo_path : '/min/?b=javascript/arale_v101&f=',
		corex : true
	};
</script>
</head>
<body>

	<form id="fmEvent" action="saveEvent.html" method="post"
		target="result" onsubmit="return checkInfo();">
		<input type="hidden" id="eventId" name="eventId"
			value="${mEventInfo.eventId}" />
		<div class="ui-fm-item" style="margin-left: -24px">
			<label for="eventTitle" class="ui-fm-label"><span
				class="ui-fm-required">*</span>标题：</label> <input id="title" name="title"
				maxlength="100" type="text" class="ui-input2"
				value="${mEventInfo.title}" />
		</div>

		<div class="ui-fm-item"
			style="width: 270px; float: left; margin-left: 5px">
			<label for="eventStartDate" class="ui-fm-label"><span
				class="ui-fm-required">*</span>起始时间：</label> <input id="eventStartDate"
				name="eventStartDate" readonly="readonly" type="text"
				class="ui-input"
				onclick="WdatePicker({startDate:'${mEventInfo.eventStartDate}',dateFmt:'yyyy-MM-dd ',alwaysUseStartDate:true})"
				value="${mEventInfo.eventStartDate}" />
		</div>
		<div class="ui-fm-item"
			style="width: 260px; float: left; margin-left: -15px">
			<label for="eventEndDate" class="ui-fm-label">结束时间：</label> <input
				id="eventEndDate" name="eventEndDate" type="text" class="ui-input"
				readonly="readonly"
				onclick="WdatePicker({startDate:'${mEventInfo.eventEndDate}',dateFmt:'yyyy-MM-dd ',alwaysUseStartDate:true})"
				value="${mEventInfo.eventEndDate}" />

		</div>

		<div class="ui-fm-item"
			style="width: 268px; float: left; margin-left: 5px">
			<label for="eventType" class="ui-fm-label"><span
				class="ui-fm-required">*</span>事件类别：</label> <select id="eventType"
				name="eventType" style="width: 157px">
				<c:choose>
				<c:when test="${mEventInfo.eventTypeName != null}">
				<option value="${mEventInfo.eventType}">${mEventInfo.eventTypeName}</option>
				</c:when>
				<c:otherwise>
				<option value="">请选择类别</option>
				</c:otherwise>
				</c:choose>				
				<c:forEach items="${eventTypeList}" var="eventType">
					<option value="${eventType.typeId}">${eventType.typeName}</option>
				</c:forEach>
			</select>
		</div>

		<div class="ui-fm-item"
			style="width: 240px; float: left; margin-left: -12px">
			<label for="isPublic" class="ui-fm-label">是否公开：</label> <span
				class="ui-fm-other ui-fm-item2"> <c:choose>
					<c:when test="${mEventInfo.isPublic !=1 }">
						<input type="radio" name="isPublic" value="1" />
						<span class="ui-fm-item3">公开</span>
						<input type="radio" name="isPublic" value="0" checked="checked" />不公开
				   </c:when>
					<c:otherwise>
						<input type="radio" name="isPublic" value="1" checked="checked" />
						<span class="ui-fm-item3">公开</span>
						<input type="radio" name="isPublic" value="0" />不公开
				   </c:otherwise>
				</c:choose>
			</span>
		</div>
		<div
			style="width: 540px; float: left; margin-left: 10px; height: 24px; line-height: 24px; font-size: 14px;">内容：</div>
		<div class="ui-fm-item"
			style="width:95%; float: left; margin-left: 10px">

			<div>
				<textarea class="ckeditor" id="editor1" name="content"
					style="width: 176px; height: 80px"
					onpropertychange="if (this.value.length>2000){this.value=this.value.substr(0,2000);}"
					oninput="if (this.value.length>2000){this.value=this.value.substr(0,2000);}">${mEventInfo.content}</textarea>
			</div>
		</div>


		<!-- 		<div class="ui-fm-item"> -->
		<!-- 			<label class="ui-fm-label"></label> <span class="ui-round-btn"><input -->
		<!-- 				type="reset" class="ui-round-btn-text" value="重置" /></span> -->
		<!-- 		</div> -->
	</form>
	<iframe name="result" id="result" src="about:blank" frameborder="0"
		width="0" height="0"></iframe>
	<script type="text/javascript" src="../js/ckeditor/ckeditor.js"></script>
	<script type="text/javascript" src="../js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript">
		var dg;
		$(document).ready(function() {
			dg = frameElement.lhgDG;
			dg.addBtn('ok', '保存', function(data) {
				$("#fmEvent").submit();
			});
			// 			if($("#eventId").val()!=""){
			// 				$("#title").attr("readonly","readonly");
			// 				$("#title").css("color","gray");
			// 			}
		});
		
		function trim(str){
		      return str.replace(/(^\s*)|(\s*$)/g, "");
		    }
		
		function checkInfo() {
			if ($("#title").val() == "") {
				alert("请输入事件标题!");
				$("#title").focus();
				return false;
			}
			if ($("#eventType").val() == "") {
				alert("请选择事件的类型");
				$("#eventType").focus();
				return false;
			}
			if ($("#eventStartDate").val() == "") {
				alert("请输入事件开始时间!");
				$("#eventStartDate").focus();
				return false;
			}

			
			if ($("eventEndDate").val() != "") {
				var v_EnterDate = document.getElementById("eventStartDate").value;
				var v_LeaveDate = document.getElementById("eventEndDate").value;
				var sp_EnterDate = v_EnterDate.split("-");
				var d_StartDate = new Date(sp_EnterDate[0], sp_EnterDate[1],
						sp_EnterDate[2]);
				var d_StartDates = d_StartDate.getTime();

				var sp_LeaveDate = v_LeaveDate.split("-");
				var d_LeaveDate = new Date(sp_LeaveDate[0], sp_LeaveDate[1],
						sp_LeaveDate[2]);
				var d_LeaveDates = d_LeaveDate.getTime();

				if (d_StartDates > d_LeaveDates) {
					alert("结束日期必须晚于开始日期！");
					document.getElementById("eventStartDate").focus();
					return false;
				}
			}
			 var reg1=new RegExp("<p>","g");
		     var reg2=new RegExp("</p>","g");
		     var reg3=new RegExp("&nbsp;","g");
		     var str = CKEDITOR.instances.editor1.getData();
		     //alert(CKEDITOR.instances.editor1.getData());
		     str=str.replace(reg1,"");
		     str=str.replace(reg2,"");
		     str=str.replace(reg3,"");
		     str=trim(str);
			if(str==null || str.length==0){
				alert("请输入事件内容!");
				return false;
			}			

			return true;
		}

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
			return false;
		}
	</script>
</body>
</html>
