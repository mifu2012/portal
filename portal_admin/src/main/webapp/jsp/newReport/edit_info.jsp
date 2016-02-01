<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<title>添加信息-new</title>
<link type="text/css" rel="stylesheet" href="<%=basePath %>/css/main.css"/>
<script type="text/javascript" src="<%=basePath%>js/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/datePicker/My98MonthPicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/datePicker/My98WeekPicker.js"></script>
<style type="text/css">
body{width:100%;height:100%;background-color: #FFFFFF;text-align: center;}
.input_txt{width:200px;height:20px;line-height:20px;}
.info{height:40px;line-height:40px;}
.info th{text-align: right;width:65px;color: #4f4f4f;padding-right:5px;font-size: 13px;}
.info td{text-align:left;}
</style>
</head>
<body>
<!-- 个级维度或二级维度 -->
	<form  action="<%=basePath%>/NewReport/save.html" name="addForm" id="addForm" target="result" method="post" onsubmit="return checkInfo();"><!--  -->
	    <input type="hidden" id="dimensionality" name="dimensionality"/>
		<input type="hidden" name="reportId" id="reportId" value="${config.reportId }"/>
		<input type="hidden" name="configId" id="configId" value="${config.configId }"/>
		<input type="hidden" name="code" id="code" value="${config.code}"/>  <!-- 维度code -->
		<input type="hidden" name="defaultValue" id="defaultValue" value="${config.defaultValue }"/> <!-- 默认值 -->
		<input type="hidden" name="defaultValueSec" id="defaultValueSec" value="${config.defaultValueSec}"/> <!--二级维度值  默认值 -->
		<input type="hidden" name="dateFormat" id="dateFormat" value="${config.dateFormat }"/> <!-- 日期格式 -->
		
	<table border="0" cellpadding="0" cellspacing="0" align="left">
		<tr class="info">
			<th>控件名称:</th>
			<td>
			   <input type="text" name="columnCode" id="columnCode" class="input_txt" value="${config.columnCode}" readonly style='background:#FFECE0;font-weight:bold;'/>
			</td>
		</tr>
		<tr class="info">
			<th>显示名称:</th>
			<td><input type="text" name="columnLabel" id="columnLabel" class="input_txt" value="${config.columnLabel }" onfocus="this.select();" maxlength="10"/></td>
		</tr>
		<tr class="info">
			<th>控件类型:</th>
			<td>
				<select name="controlType" id="controlType" class="input_txt" onChange="changeType(this.options[this.options.selectedIndex].value);"><!-- onchange="setMUR()" -->
					<option value="" <c:if test="${config.controlType=='' }">selected</c:if>></option>
					<option value="0" <c:if test="${config.controlType==0 }">selected</c:if>>输入框（字符型）</option>
					<option value="1" <c:if test="${config.controlType==1 }">selected</c:if>>输入框（整数型）</option>
					<option value="2" <c:if test="${config.controlType==2 }">selected</c:if>>输入框（小数型）</option>
					<option value="3" <c:if test="${config.controlType==3 }">selected</c:if>>日历控件（年-月-日）</option>
					<option value="4" <c:if test="${config.controlType==4 }">selected</c:if>>月历控件（年-月）</option>
					<option value="11" <c:if test="${config.controlType==11 }">selected</c:if>>周历控件（年-周）</option>
					<option value="5" <c:if test="${config.controlType==5 }">selected</c:if>>下拉框（年）</option>
					<option value="6" <c:if test="${config.controlType==6 }">selected</c:if>>下拉框（一级维度）</option>
					<option value="7" <c:if test="${config.controlType==7 }">selected</c:if>>二级联动（二级级维度）</option>
				<%-- 	<option value="-1" <c:if test="${config.controlType==-1 }">selected</c:if>>隐藏域</option> --%>
				</select>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<table border="0" cellpadding="0" cellspacing="0" id="iDBody6" style="display: none;">
					<!-- 维度 -->
					<tr class="info">
						<th>维度:</th>
						<td>
							<select name="ddlP1" id="ddlP1" class="input_txt" onChange="getDSDetail(this.options[this.options.selectedIndex].value);">
								<option value=""></option>
								<c:forEach items="${dimensionList}" var="dimension">
									<option value="${dimension.id}" <c:if test="${config.code ==dimension.id }">selected</c:if> >${dimension.dimensionName}</option>
									<!-- <c:forEach items="${dimensionDetailList}" var="report">
											<option <c:if test="${report.primaryKeyId ==config.defaultValue }">selected</c:if> value="${report.primaryKeyId}">${report.value}</option>
									</c:forEach>  -->
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr class="info">
						<th>默认值:</th>
						<td>
							<select name="ddlP2" id="ddlP2" class="input_txt">
								<option value=""></option>
								<c:forEach items="${dimensionDetailList}" var="dimension">
									<option <c:if test="${dimension.primaryKeyId ==config.defaultValue }">selected</c:if> value="${dimension.primaryKeyId}">${dimension.value}</option>
								</c:forEach> 
							</select>
						</td>
					</tr>
				</table>
				<table border="0" cellpadding="0" cellspacing="0" id="iDBody7" style="display: none;">
					<!-- 维度 -->
					<tr class="info">
						<th>维度:</th>
						<td>
							<select name="ddlP3" id="ddlP3" class="input_txt" onChange="getDSDetail(this.options[this.options.selectedIndex].value);">
								<option value=""></option>
								<c:forEach items="${dimensionList}" var="dimension">
									<option value="${dimension.id}" <c:if test="${config.code ==dimension.id }">selected</c:if> >${dimension.dimensionName}</option>
									<!-- <c:forEach items="${dimensionDetailList}" var="report">
											<option <c:if test="${report.primaryKeyId ==config.defaultValue }">selected</c:if> value="${report.primaryKeyId}">${report.value}</option>
									</c:forEach>  -->
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr class="info">
						<th>一级维度:</th>
						<td>
							<select name="ddlP4" id="ddlP4" class="input_txt" onChange="getDSDetailSel(this.options[this.options.selectedIndex].value);">
								<option value=""></option>
								<c:forEach items="${dimensionDetailList}" var="dimension">
									<option <c:if test="${dimension.primaryKeyId ==config.defaultValue }">selected</c:if> value="${dimension.primaryKeyId}">${dimension.value}</option>
								</c:forEach> 
							</select>
						</td>
					</tr>
					<tr class="info">
						<th>二级维度:</th>
						<td>
							<select name="ddlP5" id="ddlP5" class="input_txt">
								<option value="">== 请选择  ==</option>
								<c:forEach items="${dimensionDetailSecList}" var="dimension">
									<option <c:if test="${dimension.primary_id ==config.defaultValueSec}">selected</c:if> value="${dimension.primary_id}">${dimension.value}</option>
								</c:forEach> 
							</select>
						</td>
					</tr>
					<!-- 维度 -->
				</table>
				<table border="0" cellpadding="0" cellspacing="0" id="iDBody1" style="display: none;">
					<tr class="info">
						<th>默认值:</th>
						<td><input type="text" name="defaultValue1" id="defaultValue1" class="input_txt" value="" /></td>
					</tr>
				</table>
				<table border="0" cellpadding="0" cellspacing="0" id="iDBody5" style="display: none;">
					<tr class="info">
						<th>日期类型:</th>
						<td>
							<select name="defaultValue5" id="defaultValue5" class="input_txt">
								<option value="" <c:if test="${config.dateFormat=='' }">selected</c:if> ></option>
								<option value="2" <c:if test="${config.dateFormat==2 }">selected</c:if>>yyyy - MM -dd</option>
								<option value="1" <c:if test="${config.dateFormat==1 }">selected</c:if> >yyyy/ MM / dd</option>
								<option value="3" <c:if test="${config.dateFormat==3 }">selected</c:if>>yyyy MM dd</option>
							</select>
						</td>
					</tr>
				</table>
				<table border="0" cellpadding="0" cellspacing="0" id="iDBody2" style="display: none;">
					<tr class="info">
						<th>默认日期:</th>
						<td><input type="text" name="defaultValue2" id="defaultValue2" class="input_txt" value="" onclick="WdatePicker()"/></td>
					</tr>
				</table>
				<table border="0" cellpadding="0" cellspacing="0" id="iDBody3" style="display: none;">
					<tr class="info">
						<th>默认年月:</th>
						<td><input type="text" name="defaultValue3" id="defaultValue3" class="input_txt" value="" onclick="setMonth(this)"/></td>
					</tr>
				</table>
				<table border="0" cellpadding="0" cellspacing="0" id="iDBody11" style="display: none;">
					<tr class="info">
						<th>默认周次:</th>
						<td><input type="text" name="defaultValue11" id="defaultValue11" class="input_txt" value="" onclick="setWeek(this)"/></td>
					</tr>
				</table>
				<table border="0" cellpadding="0" cellspacing="0" id="iDBody4" style="display: none;">
					<tr class="info">
						<th>默认年:</th>
						<td>
							<select name="defaultValue4" id="defaultValue4" class="input_txt">
							</select>
						</td>
					</tr>
				</table>								
			</td>
		</tr>
		</table>
	</form>
	<iframe name="result" id="result" src="about:blank" frameborder="0" width="0" height="0"></iframe>
	
	<script type="text/javascript" src="../js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript">
		var dg;
		$(document).ready(function(){
			dg = frameElement.lhgDG;
			dg.addBtn('ok','保存',function(){
				$("#addForm").submit();
			});
			//设置默认值
			setMUR();
			//默认年份
			changYear();
			//设置控件类型
			setType('${config.controlType}');
		});
		function changYear(){
			var year = "";
			if(${config.controlType}==5){//年
				year="${config.defaultValue}";
			}
			for(var i=new Date().getFullYear();i>=2000;i--) 
			{ 
				document.getElementById("defaultValue4").add(new Option(i+"年",i)); 
			} 
			document.getElementById("defaultValue4").value=year;
		}
		
		function setType(id){
			document.getElementById("controlType").length =0;
			//if(id==0||id==1||id==2||id==6 || id==7){
				$("#controlType").append("<option value='"+0+"'>"+'输入框（字符型）'+"</option>");
				$("#controlType").append("<option value='"+1+"'>"+'输入框（整数型）'+"</option>");
				$("#controlType").append("<option value='"+2+"'>"+'输入框（小数型）'+"</option>");
				$("#controlType").append("<option value='"+6+"'>"+'下拉框（一级维度）'+"</option>");
				$("#controlType").append("<option value='"+7+"'>"+'二级联动（二级维度）'+"</option>");
				$("#controlType").append("<option value='"+3+"'>"+'日历控件（年-月-日）'+"</option>");
				$("#controlType").append("<option value='"+4+"'>"+'月历控件（年-月）'+"</option>");
				$("#controlType").append("<option value='"+11+"'>"+'周历控件（年-周）'+"</option>");
				$("#controlType").append("<option value='"+5+"'>"+'下拉框（年）'+"</option>");
			/* 	$("#controlType").append("<option value='-1'>隐藏域</option>"); */
				if(id==0){
					$("#controlType").val(0);
				}else if(id==1){
					$("#controlType").val(1);
				}else if(id==2){
					$("#controlType").val(2);
				}else if(id==6){
					$("#controlType").val(6);
				}else if(id==7){
					$("#controlType").val(7);
				}else if(id==3){
					$("#controlType").val(3);
				}else if(id==4){
					$("#controlType").val(4);
				}else if(id==11){
					$("#controlType").val(11);
				}else if(id==5){
					$("#controlType").val(5);
				}
				//changeType(id);
			/*
			}else if(id==3||id==4||id==5){
				$("#controlType").append("<option value='"+3+"'>"+'日历控件（年-月-日）'+"</option>");
				$("#controlType").append("<option value='"+4+"'>"+'月历控件（年-月）'+"</option>");
				$("#controlType").append("<option value='"+11+"'>"+'周历控件（年-周）'+"</option>");
				$("#controlType").append("<option value='"+5+"'>"+'下拉框（年）'+"</option>");
				if(id==3){
					$("#controlType").val(3);
				}else if(id==4){
					$("#controlType").val(4);
				}else if(id==5){
					$("#controlType").val(5);
				}
			}*/
		}
		//选择sql字段设置字段名称值
		function setValue(){
			var value=$("#columnCode").find("option:selected").text();
			$("#columnLabel").val(value);
			try
			{
				//0：输入框 1:整数型 2:小数型 3:日期型
				var columnOpt=document.getElementById("columnCode");
				document.getElementById("controlType").value=columnOpt.options[columnOpt.options.selectedIndex].id;
				setType(columnOpt.options[columnOpt.options.selectedIndex].id);
				changeType(columnOpt.options[columnOpt.options.selectedIndex].id);
			}catch(e){}
		}
		
		function setMUR(){
			//alert(1);
			/* 0 字符型INPUT_TEXT,
    		1：整数 
    		2，小数，
    		3 年月日日历控件型，
    		4：年月日历控件型
     		5：年(下拉控件) 
    		7：维度型（下拉控件）' */ 
			if(${config.controlType}==0 ){
				$("#iDBody1").show();
				$("#defaultValue1").val($("#defaultValue").val());
			}else if(${config.controlType}==1){
				$("#iDBody1").show();
				$("#defaultValue1").val($("#defaultValue").val());
			}else if(${config.controlType}==2){
				$("#iDBody1").show();
				$("#defaultValue1").val($("#defaultValue").val());
			}else if(${config.controlType}==3){//日
				$("#iDBody2").show();
				$("#iDBody5").show();
				$("#defaultValue2").val($("#defaultValue").val());
			}else if(${config.controlType}==4){//月
				$("#iDBody3").show();
				$("#iDBody5").show();
				$("#defaultValue3").val($("#defaultValue").val());
			}else if(${config.controlType}==5){//年
				$("#iDBody4").show();
				//$("#iDBody5").hide();
			}else if(${config.controlType}==6){
				$("#iDBody6").show();
				$("#dimensionality").val("1");
			}else if(${config.controlType}==7){
				$("#iDBody7").show();
				$("#dimensionality").val("2");
			}else if(${config.controlType}==11){
				
				$("#iDBody5").show();//iDBody11
				$("#iDBody11").show();
				//修改格式
				var defaultValue5=document.getElementById("defaultValue5");
				    defaultValue5.length=0;
					defaultValue5.add(new Option("yyyy - MM","1"));
				
				$("#defaultValue11").val($("#defaultValue").val());
			}
			
		}
		
		/* 维度xuanze */
		function getDSDetail(dimensionId){	
			if( $("#dimensionality").val()==1){
				var dimensionId = document.getElementById('ddlP1').value;
				var selObj=document.getElementById("ddlP2");
			   	selObj.length=0;
		        $.ajax({
					    type: "POST",                                                                 
					    url:"<%=basePath%>/NewReport/getDimensionDetailList?dimensionId="+dimensionId+"&random="+Math.random(),
					    success: function(msg){
					   	var msg=eval(msg);
					   	selObj.options.add(new Option("",""));
					    for(var i=0;i<msg.length;i++){
					        selObj.options.add(new Option(msg[i].value,msg[i].primaryKeyId));
				        }  
					          
				      }
			 		}); 
				}else{
					var dimensionId = document.getElementById('ddlP3').value;
					var selObj=document.getElementById("ddlP4");
				   	selObj.length=0;
			        $.ajax({
						    type: "POST",                                                                 
						    url:"<%=basePath%>/NewReport/getDimensionDetailList?dimensionId="+dimensionId+"&random="+Math.random(),
						    success: function(msg){
						   	var msg=eval(msg);
						   	selObj.options.add(new Option("",""));
						    for(var i=0;i<msg.length;i++){
						        selObj.options.add(new Option(msg[i].value,msg[i].primaryKeyId));
					        }  
						          
					      }
				 		}); 
				}
	 	}
		//二级维度
		function getDSDetailSel(parentId){
			var parentId = document.getElementById('ddlP4').value;
			var selObj=document.getElementById("ddlP5");
		   	selObj.length=0;
			 $.ajax({
				    type: "POST",                                                                 
				    url:"<%=basePath%>/NewReport/getDimensionDetailSecList.html?parentId="+parentId+"&random="+Math.random(),
				    success: function(msg){
				   	var msg=eval(msg);
				   	selObj.options.add(new Option("",""));
				    for(var i=0;i<msg.length;i++){
				        selObj.options.add(new Option(msg[i].value,msg[i].primary_id));
			        }  
			      }
		 	}); 
		}
		/* 选择类型变换隐藏域
		 0 字符型INPUT_TEXT,1：整数  2，小数，3 年月日日历控件型，4：年月日历控件型 5：年(下拉控件) 6：维度型（下拉控件）', 
		*/

		function changeType(id) {
			var controlTypy=${config.controlType};
			if(id == ""){
				$("#iDBody6").hide();
				$("#iDBody1").hide();
				$("#iDBody2").hide();
				$("#iDBody3").hide();
				$("#iDBody4").hide();
				$("#iDBody5").hide();
				$("#iDBody7").hide();
				$("#iDBody11").hide();
			}else if (id == 0) {//字符型INPUT_TEXT
				$("#iDBody6").hide();
				$("#iDBody1").show();
				$("#iDBody2").hide();
				$("#iDBody3").hide();
				$("#iDBody4").hide();
				$("#iDBody5").hide();
				$("#iDBody7").hide();
				$("#iDBody11").hide();
				if(controlTypy==id){
					$("#defaultValue1").val($("#defaultValue").val());
				}else{
					$("#defaultValue1").val("");
				}
			} else if (id == 1) {//整数
				$("#iDBody6").hide();
				$("#iDBody1").show();
				$("#iDBody2").hide();
				$("#iDBody3").hide();
				$("#iDBody4").hide();
				$("#iDBody5").hide();
				$("#iDBody7").hide();
				$("#iDBody11").hide();
				if(controlTypy==id){
					$("#defaultValue1").val($("#defaultValue").val());
				}else{
					$("#defaultValue1").val("");
				}
			} else if (id == 2) {//
				$("#iDBody6").hide();
				$("#iDBody1").show();
				$("#iDBody2").hide();
				$("#iDBody3").hide();
				$("#iDBody4").hide();
				$("#iDBody5").hide();
				$("#iDBody7").hide();
				$("#iDBody11").hide();
				if(controlTypy==id){
					$("#defaultValue1").val($("#defaultValue").val());
				}else{
					$("#defaultValue1").val();
				}
			} else if (id == 3) {//年月日日历控件型
				$("#iDBody6").hide();
				$("#iDBody1").hide();
				$("#iDBody5").show();
				var defaultValue5=document.getElementById("defaultValue5");
				    defaultValue5.length=0;
					defaultValue5.add(new Option("yyyy - MM- dd","2"));
					defaultValue5.add(new Option("yyyy / MM / dd","1"));
					defaultValue5.add(new Option("yyyy MM dd","3"));
				$("#iDBody2").show();
				$("#iDBody3").hide();
				$("#iDBody4").hide();
				$("#iDBody7").hide();
				$("#iDBody11").hide();
			} else if (id == 4) {//年月日历控件型
				$("#iDBody6").hide();
				$("#iDBody1").hide();
				$("#iDBody2").hide();
				$("#iDBody5").show();
				//修改格式
				var defaultValue5=document.getElementById("defaultValue5");
				    defaultValue5.length=0;
					defaultValue5.add(new Option("yyyy - MM","2"));
					defaultValue5.add(new Option("yyyy / MM","1"));
					defaultValue5.add(new Option("yyyy MM","3"));
				$("#iDBody3").show();
				$("#iDBody4").hide();
				$("#iDBody7").hide();
				$("#iDBody11").hide();
			} else if (id == 5) {//年
				$("#iDBody6").hide();
				$("#iDBody1").hide();
				$("#iDBody2").hide();
				$("#iDBody3").hide();
				$("#iDBody5").hide();
				$("#iDBody4").show();
				$("#iDBody7").hide();
				$("#iDBody11").hide();
			} else if (id == 6) {//维度型
				$("#iDBody6").show();
				$("#iDBody1").hide();
				$("#iDBody2").hide();
				$("#iDBody3").hide();
				$("#iDBody4").hide();
				$("#iDBody5").hide();
				$("#iDBody7").hide();
				$("#iDBody11").hide();
				$("#dimensionality").val("1");
			}  else if (id == 7) {//维度型
				$("#iDBody7").show();
				$("#iDBody1").hide();
				$("#iDBody2").hide();
				$("#iDBody3").hide();
				$("#iDBody4").hide();
				$("#iDBody5").hide();
				$("#iDBody6").hide();
				$("#iDBody11").hide();
				$("#dimensionality").val("2");
			} else if(id==11) //年周
			{
				//alert('ss');
				$("#iDBody6").hide();
				$("#iDBody1").hide();
				$("#iDBody2").hide();
				$("#iDBody5").show();//iDBody11
				$("#iDBody11").show();
				//修改格式
				var defaultValue5=document.getElementById("defaultValue5");
				    defaultValue5.length=0;
					defaultValue5.add(new Option("yyyy - MM","1"));
				$("#iDBody3").hide();
				$("#iDBody4").hide();
				$("#iDBody7").hide();
			}
		}

		function checkInfo() {
			clearDate();//清除隐藏域的值，重新赋值
			if ($("#columnCode").val() == "") {
				alert("请选择字段!");
				$("#columnCode").focus();
				return false;
			}
			if ($("#columnLabel").val() == "") {
				alert("请填写字段名称!");
				$("#columnLabel").focus();
				return false;
			}
			if ($("#controlType").val() == "") {
				alert("请选择类型!");
				$("#controlType").focus();
				return false;
			}else if($("#controlType").val() == 0){//字符
				if( $("#defaultValue1").val()==""){//判断是否为对应的字符型
					/*
					alert("请填写默认值!");
					$("#defaultValue1").focus();
					return false;
					*/
				}
				$("#defaultValue").val($("#defaultValue1").val());
			}else if($("#controlType").val() == 1){//整数
				if( $("#defaultValue1").val()==""){//判断是否为对应的字符型
					/*
					alert("请填写默认值!");
					$("#defaultValue1").focus();
					return false;
					*/
				}
				$("#defaultValue").val($("#defaultValue1").val());
			}else if($("#controlType").val() == 2){//小数
				if( $("#defaultValue1").val()==""){//判断是否为对应的字符型
					/*
					alert("请填写默认值!");
					$("#defaultValue1").focus();
					return false;
					*/
				}
				$("#defaultValue").val($("#defaultValue1").val());
			}else if($("#controlType").val() == 3){//年月日
				if( $("#defaultValue5").val()==""){//判断是否选择日期类型
					alert("请填选择日期类型!");
					$("#defaultValue5").focus();
					return false;
				}
				/*
				if( $("#defaultValue2").val()==""){//判断是否选择日期
					alert("请填选择日期!");
					$("#defaultValue2").focus();
					return false;
				}*/
				$("#dateFormat").val($("#defaultValue5").val());
				$("#defaultValue").val($("#defaultValue2").val());
			}else if($("#controlType").val() == 4){//年月
				if( $("#defaultValue5").val()==""){//判断是否选择日期类型
					alert("请填选择日期类型!");
					$("#defaultValue5").focus();
					return false;
				}
				if( $("#defaultValue3").val()==""){//判断是否选择日期
					/*
					alert("请填选择日期!");
					$("#defaultValue3").focus();
					return false;
					*/
				}
				$("#dateFormat").val($("#defaultValue5").val());
				$("#defaultValue").val($("#defaultValue3").val());
			}else if($("#controlType").val() == 5){//年
				if( $("#defaultValue5").val()==""){//判断是否选择日期类型
					alert("请填选择日期类型!");
					$("#defaultValue5").focus();
					return false;
				}
				/*
				if( $("#defaultValue4").val()==""){//判断是否选择日期
					alert("请填选择日期!");
					$("#defaultValue4").focus();
					return false;
				}
				*/
				$("#dateFormat").val($("#defaultValue5").val());
				$("#defaultValue").val($("#defaultValue4").val());
			}else if($("#controlType").val() == 6){//维度
				if($("#ddlP1").val()==""){
					alert("请选择维度!");
					$("#ddlP1").focus();
					return false;
				}
				$("#code").val($("#ddlP1").val());
				$("#defaultValue").val($("#ddlP2").val());
			}else if($("#controlType").val() == 7){//维度
				if($("#ddlP3").val()==""){
					alert("请选择维度!");
					$("#ddlP3").focus();
					return false;
				}
				$("#code").val($("#ddlP3").val());
				$("#defaultValue").val($("#ddlP4").val());
				if($("#ddlP4").val()==""){
					alert("请选择一级维度!");
					$("#ddlP4").focus();
					return false;
				}
				$("#defaultValueSec").val($("#ddlP5").val());
			}else if($("#controlType").val() == 11){
				$("#dateFormat").val($("#defaultValue5").val());
				$("#defaultValue").val($("#defaultValue11").val());
			}
			
			return true;
		}
		/* 清空隐藏域的值 */
		function clearDate(){
			$("#code").val()=="";
			$("#defaultValue").val()=="";
			$("#dateFormat").val()=="";
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

		function failed() {
			alert("新增失败！");
		}
	</script>
</body>
</html>