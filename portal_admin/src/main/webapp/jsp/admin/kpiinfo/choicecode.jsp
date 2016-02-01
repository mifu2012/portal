<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String selectedKpiCodes=request.getParameter("selectedKpiCodes");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>关联产品/渠道<%=selectedKpiCodes%></title>
<link charset="utf-8" rel="stylesheet"
	href="<%=basePath%>css/alice1.css" />
<link type="text/css" charset="utf-8" rel="stylesheet"
	href="<%=basePath%>css/css.css" />
<script type="text/javascript" src="<%=path %>/js/jquery-1.5.1.min.js""></script>
<script type="text/javascript">
		var dg;
		$(document).ready(function() {
			dg = frameElement.lhgDG;
			dg.addBtn('ok', '确定', function() {
				var obj1=document.getElementById("u3");
				var array = new Array();
				var array2 = new Array();
				var sign = '${sign}';
				for(i=0;i<=obj1.options.length-1;i++){
					array.push(obj1.options[i].value);
					array2.push(obj1.options[i].text.substring(obj1.options[i].text.indexOf('---')+3));
				}
			    //dg.curWin.document.getElementById('roleFormulaDesc'+sign.slice(1)).value=array2.toString();
				//已选择的指标
				var selectedKpiCodes="<%=selectedKpiCodes%>";
				var selectedKpiCodeArray=selectedKpiCodes.split(",");
				var returnKpiCodes="";//要返回的KPI_CODE|kpi_name;KPI_CODE|kpi_name;
				var u3=document.getElementById("u3");
				if(u3==null||u3.length==0)
				{
				   alert('请选择指标');
				   return;
				}
				for(var i=0;i<u3.length;i++)
				{
					var opt=u3.options[i];
					if(opt==null) continue;
					if(returnKpiCodes.length>0) returnKpiCodes+=";"
					returnKpiCodes+=opt.text.replace("---","|");
				}
				/*
                alert(returnKpiCodes);
				return;
				*/
				dg.curWin.inputRoleFormula(returnKpiCodes);
				dg.cancel();
			});
			
		});
		 var left=new Array();
		function moveTo(id1, id2) {
			var obj1 = document.getElementById(id1);
			var obj2 = document.getElementById(id2);
			var flag = true;
			for (var i = obj1.options.length - 1; i >= 0; i--) {
				var curr = obj1.options[i];
				if(curr==null) continue;
				if (curr.selected) {
					if (obj1.id == 'u3') {
						//向左移
						var v1 = curr.value;
						var txt1 = curr.text;
						var op1 = new Option(txt1, v1);
						op1.setAttribute("kpiType",curr.getAttribute("kpiType"));
						//因为右移后，不删除原始节占，故也不增加
						//obj2.options[obj2.options.length] = op1;
						obj1.options[i] = null;
						left[left.length] = op1;
						flag = false;
					} else {
						//向右移
						for ( var j = 0; j < left.length; j++) {
							if (left[j].text == obj1.options[i].text) {
								removeOne(left, j);
							}
						}
						var v1 = curr.value;
						var txt1 = curr.text;
						var op1 = new Option(txt1, v1);
                        //op1.title=txt1;
						op1.setAttribute("kpiType",curr.getAttribute("kpiType"));
						obj2.options[obj2.options.length] = op1;
						//考虑到可增加多次，不删除节点
						//obj1.options[i] = null;
						flag = false;
					}
				}
			}
			if (flag) {
				alert('请至少选择一项指标');
			}
		}
		
		function moveToAll(id1, id2){
			var obj1 = document.getElementById(id1);
			var obj2 = document.getElementById(id2);
			var flag = true;
			for (var i = obj1.options.length - 1; i >= 0; i--) {
				var curr = obj1.options[i];
					if (obj1.id == 'u3') {
						var v1 = curr.value;
						var txt1 = curr.text;
						var op1 = new Option(txt1, v1);
						//考虑到原始节点未删除，也不新增节点
						//obj2.options[obj2.options.length] = op1;
						try
	            		{
	            			option.innerHTML=txt1;
	            		}catch(e)
	            		{
	            		}
						obj1.options[i] = null;
						left[left.length] = op1;
						flag = false;
					} else {
						for ( var j = 0; j < left.length; j++) {
							if (left[j].text == obj1.options[i].text) {
								removeOne(left, j);
							}
						}
						var v1 = curr.value;
						var txt1 = curr.text;
						var op1 = new Option(txt1, v1);
						obj2.options[obj2.options.length] = op1;
						//考虑到可增加多次，不删除节点
						//obj1.options[i] = null;
						flag = false;
					}
			}
			if (flag) {
				alert('请至少选择一项指标');
			}
		}
		function removeOne(arr, index) {
			for ( var i = index; i < arr.length - 1; i++) {
				arr[i] = arr[i + 1];
			}
			arr.length = arr.length - 1;
		}
		
		
		function seelKpiCode(){
			//document.body.disabled=true;
			var kpiName=document.getElementById("kpiName").value.toUpperCase();
			$.post("<%=path%>/kpiinfo1/queryByKpiName.html",{kpiName:kpiName},function(comlist){
			   if(comlist==null||comlist.length==0)
				{
				    //document.getElementById("u1").length=0;
				    //清空所有内容
					var noRelOpt=document.getElementById("u1");
					while (noRelOpt.hasChildNodes()) { 
					   noRelOpt.removeChild(noRelOpt.firstChild); 
					}
					document.body.disabled=false;
					$("#kpiName").focus();				   
				   return;
				}
				var kpiInfo=null;
				var noRelOpt=document.getElementById("u1");
				//alert(noRelOpt);
				//if(noRelOpt!=null) noRelOpt.length=0;//清空
                //document.getElementById("u1").length=0;
				//清空所有内容
				while (noRelOpt.hasChildNodes()) { 
	               noRelOpt.removeChild(noRelOpt.firstChild); 
	            }
				var isRelOpt=document.getElementById("u3");//u3;
				for(var i=0;i<comlist.length;i++)
				{
					kpiInfo=comlist[i];
				   if(kpiInfo==null) continue;
				   var optGroup=document.getElementById("optGroup_"+kpiInfo.kpiType);
				   if(optGroup==null)
					{
					   optGroup=document.createElement("optgroup");
					   if(kpiInfo.kpiType==1)
						{
						    optGroup.label="日指标";
						}else if(kpiInfo.kpiType==2)
						{
							optGroup.label="周指标";
						}else 
						{
							optGroup.label="月指标";
						} 
		               optGroup.id="optGroup_"+kpiInfo.kpiType;
					   //
					   noRelOpt.appendChild(optGroup);
				   }
				   //noRelOpt.add(new Option(kpiInfo.kpiCode+'---'+kpiInfo.kpiName,kpiInfo.kpiCode));//text,value
				  var kpiInfoOpt=document.createElement("option");
				  kpiInfoOpt.value=kpiInfo.kpiCode;//code
				  kpiInfoOpt.id=kpiInfo.kpiCode;
				  kpiInfoOpt.setAttribute("kpiType",kpiInfo.kpiType);
 				  kpiInfoOpt.text=kpiInfo.kpiCode+'---'+kpiInfo.kpiName;//text
				  kpiInfoOpt.title=kpiInfo.kpiCode+'---'+kpiInfo.kpiName;//text
				  kpiInfoOpt.appendChild(document.createTextNode(kpiInfo.kpiCode+'---'+kpiInfo.kpiName));//text
				  //
				  optGroup.appendChild(kpiInfoOpt);				   
				}
				document.body.disabled=false;
				$("#kpiName").focus();
			});
		}
		function keySelectCode(){
            if (event.keyCode == 13) { 
            	seelKpiCode();
              } 
		}
	</script>
</head>
<body>
	<form id="choiceCodeForm"  name="choiceCodeForm" method="post"
		action=" " target="result" >
		<div class="fn-clear">
			<div class="sel-list sel-list-left">
				<input type="text" id="kpiName"
					class="ui-input ui-input-mini" style="width: 170px" name="kpiName" onkeydown="javascript:keySelectCode();"/> <span
					class="ui-round-btn ui-round-btn-mini"><input type="button"
					class="ui-round-btn-text" value="搜索" onclick="seelKpiCode();" id="prodComCode"></span><br /> <label for="u1">指标列表：</label> 
				<select id="u1"
					name="allCommCodes" size="2" multiple="multiple"
					ondblclick="moveTo('u1','u3')" style="height:250px">
					<c:set var="kpiType">-1</c:set>
					<c:forEach items="${kpiinfoList}" var="common">
					    <c:if test="${kpiType!=common.kpiType}">
					       <c:if test="${kpiType!=-1}"></optgroup></c:if>
					       <c:if test="${common.kpiType==1}"><optgroup label='日指标' id='optGroup_${common.kpiType}'></c:if>
					       <c:if test="${common.kpiType==2}"><optgroup label='周指标' id='optGroup_${common.kpiType}'></c:if>
					       <c:if test="${common.kpiType==3}"><optgroup label='月指标' id='optGroup_${common.kpiType}'></c:if>
					    </c:if>
					    <c:set var="kpiType">${common.kpiType}</c:set>
						<option value="${common.kpiCode }" id="opt_${common.kpiCode }" title="${common.kpiName }">${common.kpiCode }---${common.kpiName }</option>
					</c:forEach>
					<c:if test="${kpiType!=-1}"></optgroup></c:if>
				</select>
			</div>
			<div class="sel-act">
				<div class="row" title='选择'>
					<span class="ui-round-btn"><input type="button"
						class="ui-round-btn-text" onClick="moveTo('u1','u3')"
						value=" &gt; "  style="width:35px"/></span>
				</div>
				<div class="row" title='反选'>
					<span class="ui-round-btn"><input type="button"
						class="ui-round-btn-text" onClick="moveTo('u3','u1')"
						value=" &lt; "  style="width:35px"/></span>
				</div>
				<div class="row" title='全选'>
					<span class="ui-round-btn"><input type="button"
						class="ui-round-btn-text" onClick="moveToAll('u1','u3')"
						value=" &gt;&gt; " style="width:35px"/></span>
				</div>
				<div class="row" title='全不选'>
					<span class="ui-round-btn"><input type="button"
						class="ui-round-btn-text" onClick="moveToAll('u3','u1')"
						value=" &lt;&lt; " style="width:35px"/></span>
				</div>
			</div>
			<div class="sel-list sel-list-right">
				<br /> <label for="u3">选择指标：</label> 
				<select name="choiceCodes" size="2" id="u3" multiple="multiple" ondblclick="moveTo('u3','u1')" style="height:250px">
				</select>
			</div>
		</div>
	</form>
	<iframe name="result" id="result" src="about:blank" frameborder="0" width="0" height="0"></iframe>
</body>
</html>