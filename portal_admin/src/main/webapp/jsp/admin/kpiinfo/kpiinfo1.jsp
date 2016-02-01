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
<title>Example</title>
<link href="<%=basePath%>css/css.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>js/jquery-1.5.1.min.js" type="text/javascript"></script>
<script src="<%=basePath%>js/collection/HashMap.js"
	type="text/javascript"></script>
<script src="<%=basePath%>js/collection/JavaString.js"
	type="text/javascript"></script>
</head>
<script type="text/javascript">
var kpiCodeMap=new HashMap();
var kpiCodeArray=new Array();
var kpiCodeCount=0;
var flag = true;
//转换选择的指标
function inputRoleFormula(roleFormula)
{
  if(roleFormula==null||roleFormula.length==0) return;
  var formulaArray=roleFormula.split(";");
  var kpiCode="";
  var kpiName="";
  for(var i=0;i<formulaArray.length;i++)
  {
  	if(formulaArray[i].length==0) continue;
  	if(kpiCode.length>0) kpiCode+=" ";
  	if(kpiName.length>0) kpiName+=" ";
  	kpiCodeArray[kpiCodeCount]=formulaArray[i].split("|")[0];
  	kpiCodeMap.put(new JavaString(formulaArray[i].split("|")[0]),new JavaString(formulaArray[i].split("|")[1]));
  	kpiCode+=" ["+formulaArray[i].split("|")[0]+"]";
  	kpiName+=" ["+formulaArray[i].split("|")[1]+"]";
  	kpiCodeCount=kpiCodeCount+1;
  }
  document.getElementById("roleFormula").value+=" "+kpiCode;
  document.getElementById("roleFormulaDesc").value+=" "+kpiName;
}
String.prototype.replaceAll = function(reallyDo, replaceWith, ignoreCase) {
    if (!RegExp.prototype.isPrototypeOf(reallyDo)) {
        return this.replace(new RegExp(reallyDo, (ignoreCase ? "gi": "g")), replaceWith);
    } else {
        return this.replace(reallyDo, replaceWith);
    }
   } 
function conversion()
{
	var roleFormula=document.getElementById("roleFormula").value;
	var newRoleFormulaDesc=roleFormula;
  for(var i=0;i<kpiCodeArray.length;i++)
  {
  	var kpiCode=kpiCodeArray[i];
  	var kpiName=kpiCodeMap.get(new JavaString(kpiCode));
  	newRoleFormulaDesc=newRoleFormulaDesc.replaceAll(kpiCode,kpiName,false); 	
  }  
  document.getElementById("roleFormulaDesc").value=newRoleFormulaDesc;
} 
  function validateFormula()
  {  
	  var roleFormula=document.getElementById("roleFormula").value;
	  var roleFormulaVal=roleFormula;
	  for(var i=0;i<kpiCodeArray.length;i++)
	  {
		  var kpiCode=kpiCodeArray[i];
		  if(kpiCode==null) continue;
		  roleFormulaVal=roleFormulaVal.replace("["+kpiCode+"]","10");
	  }
	  if(roleFormulaVal==null||roleFormulaVal.length==0)
	  {
		  alert('请选择指标');
		  return;
	  }
	  if(roleFormulaVal.indexOf("|")!=-1)
	  {
		 alert('验证规则失败:'+roleFormula);
		 return false;
	  }
	  if(roleFormulaVal.indexOf("%")!=-1)
	  {
		 alert('验证规则失败:'+roleFormula);
		 return false;
	  }	  
	  try
	  {
		 var tempVal=eval(roleFormulaVal);
		 if(tempVal=="Infinity") return false;
	  }catch(e)
      {
		  alert("验证规则失败:"+roleFormula);
		  return false;
      }
	  return true;
  }
  function validateKpiCode(val) {  
       // 如果为空或者输入空格执行   
       if(val==null||val.length==0) return;
       if(flag){
    	   $.ajax({
   	        type: "POST",                                                                 
   	        url:encodeURI("<%=path%>/kpiinfo1/alidateKpiCode.html?kpiCode="
   							+ val + "&random=" + Math.random()),
   					success : function(msg) {
   						if(msg=="1"){
   		             		alert("指标Code[ "+val+" ]已存在，请重新输入！");
   		             		$("#kpiCode").val("");
   		             	    $("#kpiCode").focus();
   		             	 }  
   					}
          });
       }
         
   } 
</script>
<body class="indexbody" style="height: 600px">
	<div class="common-box">
		<div style="position: absolute; right: 10px; top: 10px;">
			提示：<font color='red' size='2'>请先选择指标，再进行修改</font>
		</div>
		<div class="left-box">
			<p>
				<input type="text" id="J-searchval-f"
					style="width: 180px; height: 20px; float: left; margin-right: 10px; line-height: 20px"
					value="${kpiinfo.keyWord}" onkeydown="javascript:butOnClick();" />
				<input type="button" id="J-search-f" value="搜索"
					style="float: left; width: 60px; height: 24px; margin-right: 10px" />
			<div style="position: absolute; top: 20px; left: 360px"
				id="buttonDiv">
				<input type="button" id="J-add" value="保存"
					style="position: absolute; margin-left: 50px; width: 60px; height: 24px;" />
			</div>
			<span
				style="width: 160px; height: 20px; float: left; display: inline-block; margin-left: 10px"><b>指标列表</b>
			</span>
			</p>
			<ul
				style="border: 1px solid #999; width: 280px; height: 300px; overflow-x: hidden; overflow-y: auto; font-size: 14px; height: 400px; margin-left: 10px"
				id="J-searchul-f">
				<c:forEach items="${kpiList}" var="item">
					<li>
						<table border="1" width="100%">
							<tr>
								<td style="border-bottom: 1px solid #f0f0f0;"><a
									href='javascript:void(0);' id="li_${item.kpiCode}"
									onclick="showInfo('${item.kpiCode}')" style='width: 800px;'
									title="${item.kpiName}"><font id="${item.kpiCode}">
									<c:choose>
									  <c:when test="${item.kpiType==3}">【月指标】</c:when>
									  <c:when test="${item.kpiType==2}">【周指标】</c:when>
									  <c:otherwise>【日指标】</c:otherwise>
									</c:choose>
									<c:if test="">
									</c:if>
									${item.kpiCode}- ${item.kpiName}</font>
								</a>
								</td>
							</tr>
							<tr height="8px">
								<td></td>
							</tr>
						</table></li>
				</c:forEach>
			</ul>
		</div>
		<form id="form1" method="post" action="<%=basePath%>kpiinfo1/add.html"
			target="result">
			<div style="height: 60px"></div>
			<table border="0">
				<tr>
					<td valign="top">
						<table class="kpiManage">
							<tr>
								<td class="mytb"><font color="red">*</font>指标CODE:</td>
								<td class="mytb" id="mytb"><div id="kpiCodeDiv"
										align="center">
										<input type="text" id="kpiCode" onkeyup="convertUpper();"
											onblur="validateKpiCode(this.value);" name="kpiCode"
											maxlength="50" />
									</div></td>
							</tr>
							<tr>
								<td class="mytb">显示名称:</td>
								<td class="mytb"><input type="text" id="dispName"
									name="dispName"  maxlength="50" />
								</td>
							</tr>
							<tr>
								<td class="mytb">是否大盘指标:</td>
								<td class="mytb"><select name="isOverall" id="isOverall"
									style="width: 70">
										<option value="0">否</option>
										<option value="1">是</option>
								</select></td>
							</tr>
							<tr style="display: none;">
								<td class="mytb">是否显示:</td>
								<td class="mytb"><select name="isShow" id="isShow">
										<option value="1">是</option>
										<option value="0">否</option>
								</select></td>
							</tr>
							<tr style="display: none;">
								<td class="mytb">是否可用:</td>
								<td class="mytb"><select name="isUse" id="isUse">
										<option value="1">是</option>
										<option value="0">否</option>
								</select></td>
							</tr>
							<tr style="display:none;">
								<td class="mytb">是否峰值:</td>
								<td class="mytb"><select name="isMax" id="isMax">
										<option value="0">否</option>
										<option value="1">是</option>
								</select>
								</td>
							</tr>
							<tr>
								<td class="mytb">是否百分比:</td>
								<td class="mytb"><select name="isPercent" id="isPercent">
										<option value="0">否</option>
										<option value="1">是</option>
								</select></td>
							</tr>
							<tr>
								<td class="mytb">显示小数位数:</td>
								<td class="mytb"><input type="text" id="decimalNum"
									name="decimalNum" value="${kpiinfo.decimalNum}" maxlength="3"
									onkeyup='this.value=this.value.replace(/[^0-9]D*$/,"")'
									onkeypress='this.value=this.value.replace(/[^0-9]D*$/,"")'
									style="ime-mode: disabled;" /></td>
							</tr>
							<tr>
								<td class="mytb">换算位数:</td>
								<td class="mytb"><input type="text" id="convertNum"
									name="convertNum" value="${kpiinfo.convertNum}" maxlength="3"
									onkeyup='this.value=this.value.replace(/[^0-9]D*$/,"")'
									onkeypress='this.value=this.value.replace(/[^0-9]D*$/,"")'
									style="ime-mode: disabled;" /></td>
							</tr>
							
						</table></td>
					<td valign="top">
						<table class="kpiManage">
							<tr>
								<td class="mytb"><font color="red">*</font>指标名称:</td>
								<td class="mytb"><input type="text" id="kpiName"
									name="kpiName" value="${kpiinfo.kpiName}" maxlength="50" />
								</td>
							</tr>
							<tr>
								<td class="mytb">指标大类:</td>
								<td class="mytb"><select name="parentCode" id="parentCode">
								<option value="">--请选择--</option>
										<c:forEach items="${cSysTypeDOList}" var="item">
											<option value="${item.typeId}">${item.typeName}</option>
										</c:forEach>
								</select></td>
							</tr>
							<tr>
								<td class="mytb">指标类型:</td>
								<td class="mytb"><select name="kpiType" id="kpiType">
										<option value="1">日统计指标</option>
										<option value="2">周统计指标</option>
										<option value="3">月统计指标</option>
								</select></td>
							</tr>
							<tr style="display:none;">
								<td class="mytb">显示顺序:</td>
								<td class="mytb"><input type="text" id="showOrder"
									name="showOrder" value="${kpiinfo.showOrder}" maxlength="3"
									onkeyup='this.value=this.value.replace(/[^0-9]D*$/,"")'
									onkeypress='this.value=this.value.replace(/[^0-9]D*$/,"")'
									style="ime-mode: disabled;" /></td>
							</tr>
							<tr style="display:none;">
								<td class="mytb">是否均值:</td>
								<td class="mytb"><select name="isAverage" id="isAverage">
										<option value="0">否</option>
										<option value="1">是</option>
								</select></td>
							</tr>
							<tr style="display:none;">
								<td class="mytb">是否变化幅度值:</td>
								<td class="mytb"><select name="isVariation"
									id="isVariation">
										<option value="0">否</option>
										<option value="1">是</option>
								</select></td>
							</tr>
							<tr>
								<td class="mytb">换算单位:</td>
								<td class="mytb"><input type="text" id="unit" name="unit"
									value="${kpiinfo.unit}" maxlength="5" /></td>
							</tr>
							<tr>
								<td class="mytb">换算方式:</td>
								<td class="mytb"><select name="convertType"
									id="convertType">
										<option value="0">不换算</option>
										<option value="1">乘</option>
										<option value="2">除</option>
								</select></td>
							</tr>
                            <tr>
								<td class="mytb">是否计算指标:</td>
								<td class="mytb">
									<select name="isCalKpi" id="isCalKpi" onchange="setIsCalKpi(this.value);">
										<option value="1">是</option>
										<option value="0" selected="selected">否</option>
								   </select>
							  </td>
							</tr>
						</table></td>
				</tr>
				<tr>
					<td colspan="2">
						<div align="left"
							style="margin-left: 48px; margin-top: 5px; font-size: 14px">
							<table width="100%">
								<tr id="tr_roleFormula" style="display:none;">
									<td width="20%" align='right' style="margin-right: 100px;"><font
										color="red">*</font>指标计算规则:</td>
									<td class="mytb" align='left'><textarea name="roleFormula"
											id="roleFormula"
											style="width: 400px; height: 80px;"
											onkeyup="conversion(this);">${kpiinfo.roleFormula}</textarea>
										<input type="button" value="选择指标"
										onclick="popwin('roleFormula');" id="choiceKpiBtn"></td>
								</tr>
								<tr id="tr_roleFormulaDesc" style="display:none;">
									<td class="mytb" align='right'>计算规则描述:</td>
									<td class="mytb" align='left'><textarea
											name="roleFormulaDesc" id="roleFormulaDesc"
											style="width: 400px; height: 80px; border-bottom: 0px; font-weight: bold;"
											readonly>${kpiinfo.roleFormulaDesc}</textarea></td>
								</tr>
								<tr>
									<td class="mytb" align='right' id="instorduct">说明:</td>
									<td class="mytb" align='left'><font color='red' size='2'>指标code必须用[]括起，操作符只能为加,减,乘,除,小括号</font>
									</td>
								</tr>
								<tr>
									<td class="mytb" align='right' width="20%" style="margin-right: 100px;">备注:</td>
									<td class="mytb" align='left'><textarea id="remark"
											name="remark" style="width: 400px; height: 80px;">${kpiinfo.remark}</textarea>
									</td>
								</tr>
							</table>
						</div></td>
				</tr>
			</table>

		</form>
	</div>
	<div class="fn-clear"></div>
	<div class="footer22"></div>
	<iframe name="result" id="result" src="about:blank" frameborder="0"
		width="0" height="0"></iframe>
	<script type="text/javascript">
	function setIsCalKpi(isCal)
	{
		//alert(isCal);
        document.getElementById("tr_roleFormula").style.display=isCal==1?"":"none";
		document.getElementById("tr_roleFormulaDesc").style.display=isCal==1?"":"none";
	}
	function butOnClick() { 
        if (event.keyCode == 13) { 
        var button = document.getElementById("J-search-f"); //bsubmit 为botton按钮的id 
        button.click(); 
        return false; 
        } 
/*         var button = document.getElementById("J-search-f"); //bsubmit 为botton按钮的id 
        button.click();  */
        }    
	$(function () {
            //第一个搜索
            $("#J-search-f").click(function () {
                var val = jQuery.trim($("#J-searchval-f").val());
                val = val.toUpperCase();
                var lilist = $("#J-searchul-f>li");
                if (val) {
                    for (var i = 0; i < lilist.length; i++) {
                        if ($(lilist[i]).text().indexOf(val) >= 0) {
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
            });
            var cond1 = jQuery.trim($("#J-searchval-f").val());
            if(cond1 != ""){
            	$("#J-search-f").click();
            }
            var ali = $("#J-searchul-f>li>a.active").parent("li");
            if (ali != null && ali.html() != null) {
                $("#J-searchul-f").prepend('<li>'+ali.html()+'</li>');
                ali.remove();
            }
      //添加      
			$("#J-add").bind("click",function(){
				var jishuqi=0;
				var showOrder1 = jQuery.trim($("#showOrder").val());
				var decimalNum1 = jQuery.trim($("#decimalNum").val());
				var convertNum1 = jQuery.trim($("#convertNum").val());
				var roleFormula = jQuery.trim($("#roleFormula").val());
				var kpiCode = jQuery.trim($("#kpiCode").val());
				var kpiName1 = jQuery.trim($("#kpiName").val());
				var is_cal_kpi =$("#isCalKpi").val();
				if(kpiCode==""){
					alert("指标code不能为空!");
					jishuqi++;
				}
				if(kpiName1==""){
					alert("指标名称不能为空!");
					jishuqi++;
				}
				if(is_cal_kpi!= null && is_cal_kpi == '1'){
					if(roleFormula=="" || roleFormula==null){
	 					alert("计算规则不能为空!");
	 					jishuqi++;
	 				}else{
	 					var isOk=validateFormula();
	 					if(!isOk)
	 					 {
							alert('计算规则验证失败 ,请核实');
							jishuqi++;
	 					 }
	 				}
					
				} 

				if(showOrder1==""){
					$("#showOrder").val(0);
				}
				if(decimalNum1==""){
					$("#decimalNum").val(0);
				}
				if(convertNum1==""){
					$("#convertNum").val(0);
				}
				if(jishuqi==0){
					$("#form1").submit();
				}
			});
	});
	//修改
			function modifyKpiCode(){
	            var jishuqi=0;
				var showOrder2 = jQuery.trim($("#showOrder").val());
				var decimalNum2 = jQuery.trim($("#decimalNum").val());
				var convertNum2 = jQuery.trim($("#convertNum").val());
				var roleFormula = jQuery.trim($("#roleFormula").val());
				var isCalKpi = jQuery.trim($("#isCalKpi").val());
				var kpiName2 = jQuery.trim($("#kpiName").val());
				if(kpiName2==""){
					alert("指标名称不能为空!");
					jishuqi++;
				}
				if(isCalKpi==1){
					if(roleFormula==""){
						alert("计算规则不能为空!");
						jishuqi++;
					}else
					{
						var isOk=validateFormula();
						if(!isOk)
						 {
							alert('计算规则验证失败 ,请核实');
							jishuqi++;
						 }
					}
				}
				if(showOrder2==""){
					$("#showOrder").val(0);
				}
				if(decimalNum2==""){
					$("#decimalNum").val(0);
				}
				if(convertNum2==""){
					$("#convertNum").val(0);
				}
				document.getElementById("form1").action ="<%=basePath%>/kpiinfo1/update.html";
				if(jishuqi==0){
					$("#form1").submit();
				}
				//alert("修改成功");
				return false;
			};

//删除
    function deleteKpicode(){
		if(confirm("确定要删除该记录？")){
			var val = $("#kpiCode").val();
			$.ajax({
		        type: "POST",                                                                 
		        url:encodeURI("<%=basePath%>/kpiinfo1/del.html?kpiCode="+val+"&random="+Math.random()),
		        success: function(msg){ 
		        if(msg=="success"){
		        	alert("删除成功");
		        	  window.location.href='<%=basePath%>/kpiinfo1.html';
		        }else if(msg=="failed"){
		        	alert("删除失败");
		        	return false;
		        }else{
		        	alert(msg);
		        	return false;
		        }
		      }
		   }); 
		}
	}
    function showInfo(val){
    	flag = false;
    	document.body.disabled=false;
		$.ajax({
	        type: "POST",                                                                 
	        url:encodeURI("<%=basePath%>/kpiinfo1/select.html?kpiCode="
							+ val + "&random=" + Math.random()),
					success : function(msg) {
						 
						//指标编码
						$("#kpiCode").val(msg.kpiCode);
						//指标名称
						$("#kpiName").val(msg.kpiName);
						//展现名称
						$("#dispName").val(msg.dispName);
						//指标大类
						$("#parentCode").val(msg.parentCode);
						//是否大盘指标
						$("#isOverall").val(msg.isOverall);
						//指标类型
						$("#kpiType").val(msg.kpiType);
						//是否显示
						$("#isShow").val(msg.isShow);
						//显示顺序
						$("#showOrder").val(msg.showOrder);
						//是否可用
						$("#isUse").val(msg.isUse);
						//是否均值
						$("#isAverage").val(msg.isAverage);
						//是否峰值
						$("#isMax").val(msg.isMax);
						//是否变化幅度值
						$("#isVariation").val(msg.isVariation);
						//是否百分比
						$("#isPercent").val(msg.isPercent);
						//换算单位
						$("#unit").val(msg.unit);
						//显示小数位数
						$("#decimalNum").val(msg.decimalNum);
						//换算方式
						$("#convertType").val(msg.convertType);
						//换算位数
						$("#convertNum").val(msg.convertNum);
						//是否计算指标
						$("#isCalKpi").val(msg.isCalKpi);
						//填写计算规则
						$("#roleFormula").val(msg.roleFormula);
						//填写计算规则描述
						$("#roleFormulaDesc").val(msg.roleFormulaDesc);
						//非计算指标
						if(msg.isCalKpi != null && msg.isCalKpi == '0'){
						  //tr_roleFormula
						  document.getElementById("tr_roleFormula").style.display="none";
						  //tr_roleFormulaDesc
						  document.getElementById("tr_roleFormulaDesc").style.display="none";
						  //document.getElementById("roleFormula").parentNode.parentNode.style.display="none";
						  //document.getElementById("roleFormulaDesc").parentNode.parentNode.style.display="none";
						  //document.getElementById("instorduct").parentNode.style.display="none";
						}else
						{
							document.getElementById("tr_roleFormula").style.display="";
							document.getElementById("tr_roleFormulaDesc").style.display="";
						}
						//填写备注
						$("#remark").val(msg.remark);
						var buttonHtml =
							"<input type='button' value='保存' style='position: absolute; margin-left: 50px; width: 60px; height: 24px;' id='J-modify' onclick='modifyKpiCode()'/><input type='button' value='删除' style='position: absolute; margin-left: 120px;width: 60px; height: 24px;' id='J-delete' onclick='deleteKpicode()'/><input type='button' value='去新增' style='position: absolute; margin-left: 190px;width: 60px; height: 24px;' onclick='clearValue()'/><input type='button' value='导入指标数据' style='position: absolute; margin-left: 270px;width: 100px; height: 24px;' onclick='importData()' id='imp_data_btn'/>";
							$("#buttonDiv").html(buttonHtml);
							//
                            if(msg.isCalKpi != null && msg.isCalKpi == '1'){
                               document.getElementById("imp_data_btn").disabled=true;
							}
							 var rule=msg.roleFormula;
							 var ruleDesc=msg.roleFormulaDesc;
							 var newRule=rule;
							 var newRuleDesc=ruleDesc;
							 while(newRule.indexOf("[")!=-1)
							 {
								 //kpicode
							     var kpiCode=newRule.substring(newRule.indexOf("[")+1,newRule.indexOf("]"));
							     newRule=newRule.replace("[","<");
								 newRule=newRule.replace("]",">");
								 kpiCodeArray[kpiCodeCount]=kpiCode;
								 kpiCodeCount++;
								 //kpiName
							     var kpiName=newRuleDesc.substring(newRuleDesc.indexOf("[")+1,newRuleDesc.indexOf("]"));
							     newRuleDesc=newRuleDesc.replace("[","<");
							     newRuleDesc=newRuleDesc.replace("]",">");	
							     kpiCodeMap.put(new JavaString(kpiCode),new JavaString(kpiName));
							 }
						document.body.disabled=false;
					}
				});
		//点击后设置样式
		var preKpiCode=document.getElementById("kpiCode").value;
		if(document.getElementById("li_"+preKpiCode)!=null) document.getElementById("li_"+preKpiCode).style.background="#ffffff";
		document.getElementById("li_"+val).style.background="#3399ff";
	}
    function clearValue(){
    	flag = true;
    	window.location.href="<%=basePath%>kpiinfo1.html";
		return false;
    }
    function convertUpper(){
		var kpiCode=$("#kpiCode").val();
		kpiCode=kpiCode.toUpperCase();
		$("#kpiCode").val(kpiCode);
	}
	</script>
	<script type="text/javascript"
		src="<%=basePath%>js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>
	<script type="text/javascript">
	var dg;
	$(document).ready(function(){
		dg = frameElement.lhgDG;
		if(dg!=null)
		{
			dg.addBtn('ok','保存',function(){
				$("#form1").submit();
			});
		}
	});
	function success(){
		alert("保存成功");
		clearValue();
		if(dg!=null)
		{
		    if(dg.curWin.document.forms[0]){
			    dg.curWin.document.forms[0].action = dg.curWin.location+"";
			    dg.curWin.document.forms[0].submit();
		    }else{
			    dg.curWin.location.reload();
		    }
		dg.cancel();
	    }
	}
	function failed(){
		alert("保存失败");
	}
	function popwin(sign){
		var dg = new $.dialog({
			title:'指标搜索',
			id:'show_commoncode',
			width:600,
			height:400,
			iconTitle:false,
			cover:true,
			maxBtn:true,
			xButton:true,
			fixed:true,
			resize:true,
			page:'<%=path%>/kpiinfo1/choicecode.html' + sign
				});
				dg.ShowDialog();
	}
	function importData(){
		var kpiCode=document.getElementById("kpiCode").value;
		var kpiType=document.getElementById("kpiType").value;
		var dg = new $.dialog({
			title:'导入',
			id:'导入',
			width:600,
			height:400,
			iconTitle:false,
			cover:true,
			maxBtn:true,
			xButton:true,
			resize:true,
			page:'<%=path%>/kpiinfo1/goToImportPage.html?kpiCode=' + kpiCode+'&kpiType='+kpiType
		});
			dg.ShowDialog();
	}
	function failed() {
		alert("新增失败，该指标CODE已存在！");
	}
	</script>
</body>
</html>
