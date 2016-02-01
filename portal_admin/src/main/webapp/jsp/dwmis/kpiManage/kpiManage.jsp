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
<link href="<%=basePath%>css/css.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>js/jquery-1.5.1.min.js" type="text/javascript"></script>
</head>
<body>
<body class="indexbody">
	<div class="wrap22">
		<div class="common-box" style="height: 100px;">
			<div class="left-box">
				<p>
					<input type="text" id="J-searchval-f"
						onkeydown="javascript:butOnClick(event);" onkeyup="butOnKeyUp()"
						style="width: 160px; height: 20px; float: left; margin-right: 10px; line-height: 20px" />
					<input type="button" id="J-search-f" value="搜索"
						style="float: left; width: 60px; height: 24px; line-height: 24px"
						value="${kpiCode}" />
				<div style="position: absolute; top: 20px; left: 360px"
					id="buttonDiv">
					<input type="button" id="J-add" value="添加" onclick="addKpiCode()"
						style="margin-left:100px;width: 60px; height: 24px; line-height: 24px">
				</div>
				<span
						style="width: 160px; height: 20px; float: left; display: inline-block;">指标列表</span>
				</p>
				<ul class="zb-ul" id="J-searchul-f" style="height: 400px">
					<c:forEach items="${dwmisKpiInfoList}" var="item">
						<li><a style="width: 1000px;" href="javascript:"
							onclick="showInfo('${item.kpiCode}')"> <font
								id="${item.kpiCode}"> ${item.kpiCode} - ${item.kpiName} -
									${item.kpiNameShow}</font>
						</a></li>
					</c:forEach>
				</ul>
			</div>
			<div style="padding-left: 10px;"> 
			<form id="form1" method="post">
				<div style="height: 60px"></div>
				<table>
					<tr>
						<td valign="top">
							<table class="kpiManage">
								<tr>
									<td class="mytb"><font color="red" style="font-size: 20px">*</font>指标code:</td>
									<td class="mytb" id="mytb"><div id="kpiCodeDiv" align="center"><input type="text" id="kpiCode" 
										name="kpiCode" maxlength="50"  onkeyup="convert()"/></div></td>
								</tr>
								<tr>
									<td class="mytb"><font color="red">*</font>指标名称:</td>
									<td class="mytb"><input type="text" id="kpiName"
										name="kpiName" maxlength="50"/></td>
								</tr>
								<tr>
									<td class="mytb"><font color="red">*</font>展现名称:</td>
									<td class="mytb"><input type="text" id="kpiNameShow"
										name="kpiNameShow" maxlength="50" />
									</td>
								</tr>
								<tr>
									<td style="display: none" class="mytb">KPI绩效名称:</td>
									<td style="display: none" class="mytb"><input type="text" maxlength="50" />
									</td>
								</tr>
								<tr>
									<td style="display: none" class="mytb">维度成员名称:</td>
									<td style="display: none" class="mytb"><input type="text" maxlength="50" />
									</td>
								</tr>
								<tr>
									<td class="mytb">指标类型:</td>
									<td class="mytb"><select name="typeId" id="typeId">
											<c:forEach items="${type}" var="type">
												<option value="${type.typeId}">${type.typeName}</option>
											</c:forEach>
									</select></td>
								</tr>
								<tr>
									<td class="mytb">指标单位:</td>
									<td class="mytb"><select name="unitId" id="unitId">
											<c:forEach items="${unit}" var="unit">
												<option value="${unit.typeId}">${unit.typeName}</option>
											</c:forEach>
									</select>
									</td>
								</tr>
								<tr>
									<td class="mytb">指标数量级:</td>
									<td class="mytb"><select name="sizeId" id="sizeId">
											<c:forEach items="${size}" var="size">
												<option value="${size.typeId}">${size.typeName}</option>
											</c:forEach>
									</select>
									</td>
								</tr>
								<tr>
									<td class="mytb">关联表名:</td>
									<td class="mytb"><input type="text" id="relateTable"
										name="relateTable" />
									</td>
								</tr>
								<tr>
									<td class="mytb">关联表名描述:</td>
									<td class="mytb"><input type="text" id="relateTableDes"
										name="relateTableDes" /></td>
								</tr>
								<tr>
									<td class="mytb">绩效类型:</td>
									<td class="mytb"><select name="goalType" id="goalType">
											<c:forEach items="${goal}" var="goal">
												<option value="${goal.typeId}">${goal.typeName}</option>
											</c:forEach>
									</select></td>
								</tr>
								<tr>
									<td class="mytb">一级指标:</td>
									<td class="mytb"><select name="levelType1" id="levelType1">
											<c:forEach items="${level}" var="level">
												<option value="${level.typeId}">${level.typeName}</option>
											</c:forEach>
									</select></td>
								</tr>
								<tr>
									<td class="mytb">二级指标:</td>
									<td class="mytb"><select name="levelType2" id="levelType2">
											<c:forEach items="${level2}" var="level2">
												<option value="${level2.typeId}">${level2.typeName}</option>
											</c:forEach>
									</select></td>
								</tr>
							</table>
						</td>
						<td valign="top">
							<table class="kpiManage">
								<tr>
									<td width="100" height="30" align="right" valign="middle"
										class="mytb">首页展示:</td>
									<td valign="middle" class="myradiotb"><input type="radio"
										checked="checked" id="isShow" name="isShow" value="1" />是 <input
										type="radio" id="isShow" name="isShow" value="0" />否</td>
								</tr>
								<tr>
									<td height="30" align="right" valign="middle" class="mytb">首页展示描述:</td>
									<td valign="middle" class="myradiotb"><input type="radio"
										checked="checked" id="isShowDesc" name="isShowDesc" value="1" />是
										<input type="radio" id="isShowDesc" name="isShowDesc"
										value="0" />否</td>
								</tr>
								<tr>
									<td height="30" align="right" valign="middle" class="mytb">时间粒度:</td>
									<td valign="middle" class="myradiotb"><input
										type="checkbox" id="period1btn1" name="period" />年 <input
										type="checkbox" id="period1btn2" name="period" />季 <input
										type="checkbox" id="period1btn3" name="period" />月 <input
										type="checkbox" id="period1btn4" name="period" />周 <input
										type="checkbox" id="period1btn5" name="period" />日</td>
								</tr>
								<tr>
									<td style="display: none" height="30" align="right" valign="middle" class="mytb" >业务展示类型:</td>
									<td style="display: none" valign="middle" class="myradiotb"><input
										type="checkbox" value=""> <input type="checkbox"
										value="">
									</td>
								</tr>
								<tr>
									<td height="30" align="right" valign="middle" class="mytb">展现顺序:</td>
									<td valign="middle" class="mytb"><input type="text"
										id="showOrder1" name="showOrder"></input>
									</td>
								</tr>
								<tr>
									<td height="30" align="right" valign="middle" class="mytb">数据日偏差:</td>
									<td valign="middle" class="mytb"><input type="text"
										id="dayOffset" name="dayOffset"></input>
									</td>
								</tr>
							</table></td>
					</tr>
				</table>
			</form>
			</div>
			
		</div>
		<div class="fn-clear"></div>
		<div class="footer22"></div>
	</div>
	<script type="text/javascript">
	var isAdd=true;
	//搜索
	function butOnKeyUp() {
		//根据输入字符查询
		 var val = jQuery.trim($("#J-searchval-f").val());
// 			 val=val.toUpperCase();//字符转为大写
			var lilist = $("#J-searchul-f>li");
			if (val) {
				for (var i = 0; i < lilist.length; i++) {
					if (($(lilist[i]).text().indexOf(val) >= 0)||($(lilist[i]).text().indexOf(val.toUpperCase()) >= 0)) {
						$(lilist[i]).show();
					} else {
						$(lilist[i]).find("a").removeClass("active");
						$(lilist[i]).hide();
					}
				}
			} else {
				for (var i = 0; i < lilist.length; i++) { 
					$(lilist[i]).show();
				}
			}
        } 
	
	$("#J-search-f").bind("click",function(){
				var val = $("#J-searchval-f").val();
				 $.ajax({
				        type: "POST",                                                                 
				        url:encodeURI("<%=basePath%>/kpiRelateManage/searchKpi.html?kpiCode="+val+"&isShowRelateKpi=1&random="+Math.random()),
				        success: function(msg){ 
				        	var msg=eval(msg);
				        	var listHtml=" ";
				            for(var i=0;i<msg.length;i++){
				            listHtml+="<li style='width: 1000px' title='"+msg[i].kpiNameShow+"'><a href='javascript:;' onclick=showInfo('"+msg[i].kpiCode+"')>"
				            	+msg[i].kpiCode+'-'+msg[i].kpiName+'-' +msg[i].kpiNameShow+"</a></li>"
			        	   }  
				           document.getElementById("J-searchul-f").innerHTML=listHtml;
				       }
				  }); 
     });
	//输入字母自动转为大写   输入comKpiCode时的操作
	function convert(){
		var kpiCode=$("#kpiCode").val();
		kpiCode=kpiCode.toUpperCase();
		$("#kpiCode").val(kpiCode);
	}
	//添加
			function addKpiCode(){
				var kpiCode = jQuery.trim($("#kpiCode").val());
				var kpiName1 = jQuery.trim($("#kpiName").val());
				var kpiNameShow1 = jQuery.trim($("#kpiNameShow").val());
				var str=document.getElementsByName("period"); 
				if(kpiCode==""){
					alert("指标code不能为空!");
					return false;
				}
				//指标校验
				 if(kpiCode.match(/[^0-9A-Za-z]/g)){
					  alert("指标只能为数字和字母");
					  $("#kpiCode").val("");
					  $("#kpiCode").focus();
					  return false;
				 } 
				if(kpiName1==""){
					alert("指标名称不能为空!");
					return false;
				}
				if(kpiNameShow1==""){
					alert("展现名称不能为空!");
					return false;

				}
				for (var i=0;i<str.length;i++) 
				{ 
				  if(str[i].checked == true) 
				  { 
				     document.getElementsByName("period")[i].value=1;
				  }else{
					  document.getElementsByName("period")[i].value=0;
				  }
				} 
				$("input[name='period']").attr("checked",true);
				
				 $.ajax({
				        type: "POST",      
				        cache:false,
				        url:encodeURI("<%=basePath%>/kpiManage/checkKpiCode.html?kpiCode="+kpiCode+"&isAdd=1&kpiName="+kpiName1+"&myKpiName=''&random="+Math.random()),
				        success: function(msg){ 
				        	if(msg=="success"){
				        		$("#form1").submit();
								window.location.href="<%=basePath%>/kpiManage/getAllKpi.html";
				        		
				        	}else if(msg=="faild1"){
				        		alert("指标code已存在，请重新填写");
				        		$("#kpiCode").focus();
				        	}else if(msg=="faild2"){
				        		alert("指标名称已存在，请重新填写");
				        		$("#kpiName").focus();
				        	}
				       }
				  }); 
				
			};
	//修改
			function modifyKpiCode(){
		//隐藏的kpiName
	         	var myKpiName=jQuery.trim($("#myKpiName").val())
				var kpiNameShow2 = jQuery.trim($("#kpiNameShow").val());
				var kpiName2 = jQuery.trim($("#kpiName").val());
				var str=document.getElementsByName("period"); 
				for (var i=0;i<str.length;i++) 
				{ 
				  if(str[i].checked == true) 
				  { 
				     document.getElementsByName("period")[i].value=1;
				  }else{
					  document.getElementsByName("period")[i].value=0;
				  }
				} 
				$("input[name='period']").attr("checked",true);
				if(kpiNameShow2==""){
					alert("展现名称不能为空!");
					return false;
				}
				if(kpiName2==""){
					alert("指标名称不能为空!");
					return false;
				}
				
				var kpiCode="";
				 $.ajax({
				        type: "POST",      
				        cache:false,
				        url:encodeURI("<%=basePath%>/kpiManage/checkKpiCode.html?kpiCode="+kpiCode+"&kpiName="+kpiName2+"&myKpiName="+myKpiName+"&random="+Math.random()),
				        success: function(msg){ 
				        	if(msg=="success"){
								$("#form1").submit();
				        	}else if(msg=="faild1"){
				        		alert("指标code已存在，请重新填写");
				        		$("#kpiCode").focus();
				        	}else if(msg=="faild2"){
				        		alert("指标名称已存在，请重新填写");
				        		$("#kpiName").focus();
				        	}
				       }
				  }); 
			};
	
				$('#form1').submit(function() {
					if(isAdd)
					{
						$.ajax({
							url : 'add.html',
							data : $('#form1').serialize(),
							type : "POST",
							cache : false,
							dataType: "text",
							error : function()
							{
								alert('添加失败');
							},
							success : function(msg) {
								if(msg=="success"){
									alert("添加成功");
								}else{
									alert("添加失败");
								}
							}
						});
					}else
					{
						$.ajax({
							url : 'update.html',
							data : $('#form1').serialize(),
							type : "post",
							cache : false,
							success : function(msg) {
								if(msg=="success"){
									alert("修改成功");
								}else{
									alert("修改失败");
								}
							}
						});						
					}
				});
			
	//删除
			function deleteKpicode(){
				var val = $("#kpiCode").val();
				var flag = false;
					flag = window.confirm('确认删除该指标吗?');
					if(flag){
						$.ajax({
							url : 'del.html?kpiCode='+val,
							type : "post",
							cache : false,
							success : function(msg) {
								if(msg=="success"){
									alert("删除成功");
									window.location.href='<%=basePath%>/kpiManage/getAllKpi.html';
								}else{
									alert("删除失败");
								}
							}
						});	
					}
        };
//根据kpiCode查询数据
        function showInfo(val){
			$.ajax({
		        type: "POST",                                                                 
		        url:encodeURI("<%=basePath%>/kpiManage/select.html?kpiCode="
								+ val + "&random=" + Math.random()),
						success : function(msg) {
							isAdd=false;
							var msg = eval('(' + msg + ')');
							//指标code
							var inputHtml="<input type='text' id='kpiCode'  name='kpiCode'  value='"+msg.kpiCode+"'style='display:none;'/><font><b>"+msg.kpiCode+"</b></font>"
							$("#kpiCodeDiv").html(inputHtml);
							//指标名称
							$("#kpiName").val(msg.kpiName);
							//展现名称
							$("#kpiNameShow").val(msg.kpiNameShow);
							//指标类型
							var typeIdOption = $("#typeId option");
							for ( var i = 0; i < typeIdOption.length; i++) {
								if (typeIdOption[i].value == msg.typeId) {
									typeIdOption[i].selected = true;
								}
							}
							//指标单位
							var unitIdOption = $("#unitId option");
							for ( var i = 0; i < unitIdOption.length; i++) {
								if (unitIdOption[i].value == msg.unitId) {
									unitIdOption[i].selected = true;
								}
							}
							//指标数量级
							var sizeIdOption = $("#sizeId option");
							for ( var i = 0; i < sizeIdOption.length; i++) {
								if (sizeIdOption[i].value == msg.sizeId) {
									sizeIdOption[i].selected = true;
								}
							}
							//关联表名
							$("#relateTable").val(msg.relateTable);
							//关联表名描述
							$("#relateTableDes").val(msg.relateTableDes);
							//绩效类型
							var goalTypeOption = $("#goalType option");
							for ( var i = 0; i < goalTypeOption.length; i++) {
								if (goalTypeOption[i].value == msg.goalType) {
									goalTypeOption[i].selected = true;
								}
							}
							//一级指标
							var levelType1Option = $("#levelType1 option");
							for ( var i = 0; i < levelType1Option.length; i++) {
								if (levelType1Option[i].value == msg.levelType1) {
									levelType1Option[i].selected = true;
								}
							}
							//二级指标
							var levelType2Option = $("#levelType2 option");
							for ( var i = 0; i < levelType2Option.length; i++) {
								if (levelType2Option[i].value == msg.levelType2) {
									levelType2Option[i].selected = true;
								}
							}
							//首页是否展示
							var isShow = document.getElementsByName("isShow");
							for ( var i = 0; i < isShow.length; i++) {
								if (isShow[i].value == msg.isShow) {
									isShow[i].checked = true;
								}
							}
							//首页是否展示描述
							var isShowDesc = document
									.getElementsByName("isShowDesc");
							for ( var i = 0; i < isShowDesc.length; i++) {
								if (isShowDesc[i].value == msg.isShowDesc) {
									isShowDesc[i].checked = true;
								}
							}
							//时间粒度
							var periodValue=msg.period;
							var period = document.getElementsByName("period");
							for(var i=0;i<period.length;i++){
								if(periodValue.charAt(i)==1){
									period[i].checked = true;
								}else{
									period[i].checked = false;
								}
							}
							//数据日偏差
							$("#dayOffset").val(msg.dayOffset);
							$("#showOrder1").val(msg.showOrder);
							var buttonHtml = "<input type='button' value='修改' style='margin-left:35px;width: 60px; height: 24px; line-height: 24px' id='J-modify' onclick='modifyKpiCode()'/><input type='button' value='删除' style='margin-left:10px;width: 60px; height: 24px; line-height: 24px' id='J-delete' onclick='deleteKpicode()'><input type='button' value='去添加' style='margin-left:10px;width: 60px; height: 24px; line-height: 24px' id='J-addButton' onclick='addButton()'><input type='text' id='myKpiName' style='display:none'  value='"+msg.kpiName+"'/>";
							$("#buttonDiv").html(buttonHtml);
						}
					});
			//点击后设置样式
			$("#J-searchul-f li a font").removeClass("active");
			$("#"+val).addClass("active");
		}
           //点击去添加按钮显示添加界面
          function addButton(){
        	  isAdd=true;
				var inputHtml="<input type='text' id='kpiCode' name='kpiCode' value=''  onkeyup='convert()'/>"
				$("#kpiCodeDiv").html(inputHtml);
				//指标名称
				$("#kpiName").val("");
				//展现名称
				$("#kpiNameShow").val("");
				//关联表名
				$("#relateTable").val("");
				//关联表名描述
				$("#relateTableDes").val("");
				var buttonHtml ="<input type='button' id='J-add' value='添加' onclick='addKpiCode()' style='margin-left:100px;width: 60px; height: 24px; line-height: 24px'>";
				$("#buttonDiv").html(buttonHtml);
       }
		//回车搜索
		function butOnClick(e) {
		 var event=(navigator.appName=="Netscape")?e.which:e.keyCode;   
			if (event == 13) {
				var button = document.getElementById("J-search-f"); //bsubmit 为botton按钮的id 
				button.click();
				return false;
			}
		}
	</script>
</body>
</html>
