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
<title>关联产品</title>
<link type="text/css" charset="utf-8" rel="stylesheet"
	href="<%=basePath%>css/css.css" />
<script type="text/javascript" src="<%=path %>/js/jquery-1.5.1.min.js""></script>
<script type="text/javascript">
		var dg;
		$(document).ready(function() {
			dg = frameElement.lhgDG;
			dg.addBtn('ok', '保存', function() {
				var obj1=document.getElementById("u3");
				for(i=0;i<=obj1.options.length-1;i++){
					obj1.options[i].selected=true;
					
				}
				$("#tempProdeForm").submit();
			});
			document.getElementById("prodType").value=${prodType};
		});
		 var left=new Array();
		function moveTo(id1, id2) {
			var obj1 = document.getElementById(id1);
			var obj2 = document.getElementById(id2);
			var flag = true;
			for (i = obj1.options.length - 1; i >= 0; i--) {
				var curr = obj1.options[i];
				if (curr.selected) {
					if (obj1.id == 'u3') {
						var v1 = curr.value;
						var txt1 = curr.text;
						var op1 = new Option(txt1, v1);
						obj2.options[obj2.options.length] = op1;
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
						obj1.options[i] = null;
						flag = false;
					}
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
		
		function success(){
			if(dg.curWin.document.forms[0]){
				dg.curWin.document.forms[0].action = dg.curWin.location+"";
				dg.curWin.document.forms[0].submit();
			}else{
				dg.curWin.location.reload();
			}
			dg.cancel();
		}

		function moveToAll(id1, id2){
			var obj1 = document.getElementById(id1);
			var obj2 = document.getElementById(id2);
			var flag = true;
			for (i = obj1.options.length - 1; i >= 0; i--) {
				var curr = obj1.options[i];
					if (obj1.id == 'u3') {
						var v1 = curr.value;
						var txt1 = curr.text;
						var op1 = new Option(txt1, v1);
						obj2.options[obj2.options.length] = op1;
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
						obj1.options[i] = null;
						flag = false;
					}
			}
			if (flag) {
				alert('请至少选择一项指标');
			}
		}
		
		function choiceChange(obj){
			var id = document.getElementById("templateId").value;
		    var selectValue = obj.value;
			var selectText = obj.options[obj.selectedIndex].text;
			document.body.disabled=true;
			if(confirm("您确定要更换产品类型吗？此操作将会删除"+selectText+"已经关联的产品")){
				var url="<%=path%>/dwpas/deleteConnectProd"+id+".html";
			  $.post(url,function(msg){
				  if(msg=="success"){
					  $.post("<%=path%>/dwpas/queryByProdId.html",{prodType:selectValue},function(prodlist){
						  document.getElementById("u3").length=0;
						  document.getElementById("u1").length=0;
							var prodInfo=null;
							var noRelOpt=document.getElementById("u1");
							for(var i=0;i<prodlist.length;i++)
							{
								prodInfo = prodlist[i];
							   if(prodInfo==null) continue;
								 noRelOpt.add(new Option(prodInfo.productId+'--'+prodInfo.productName,prodInfo.productId));//text,value
							}
						});
					}
				});
			}
			document.body.disabled=false;
		}
	</script>
</head>
<body>
	<form id="tempProdeForm"  name="tempProdeForm" method="post"
		action="<%=path %>/dwpas/saveConnectProd.html" target="result" >
		<input id="templateId" type="hidden" name="templateId"
			value="${templateId}" />
		<div style="position: absolute; margin:3px 0px 15px 0px; ">
			<b>产品类型</b>&nbsp;:&nbsp;&nbsp;<select name="productMarks" style="width:80px;" id="prodType" onchange="choiceChange(this);">
			        <c:forEach items="${misTypelist }" var="mistype">
					　<c:if test="${mistype.typeId!='4003'}">
					     <!--暂不支持用户行为-->
			             <option value="${mistype.typeId }">${mistype.typeName }</option>
					  </c:if>
			        </c:forEach>
			      </select>
			     
		</div>
		<div class="fn-clear">
			<div class="sel-list sel-list-left">
				<br /> <label for="u1">选择产品：</label> 
				<select id="u1"
					name="allProduects" size="2" multiple="multiple" 
					ondblclick="moveTo('u1','u3')">
					<c:forEach items="${prodlist}" var="prod">
						<option value="${prod.productId}">${prod.productId}--${prod.productName}</option>
					</c:forEach>
				</select>
			</div>
			<div class="sel-act">
				<div class="row">
					<span class="ui-round-btn"><input type="button"
						class="ui-round-btn-text" onClick="moveTo('u1','u3')"
						value=" &gt; " style="width:35px"/></span>
				</div>
				<div class="row">
					<span class="ui-round-btn"><input type="button"
						class="ui-round-btn-text" onClick="moveTo('u3','u1')"
						value=" &lt; " style="width:35px"/></span>
				</div>
				<div class="row">
					<span class="ui-round-btn"><input type="button"
						class="ui-round-btn-text" onClick="moveToAll('u1','u3')"
						value=" &gt;&gt; " style="width:35px"/></span>
				</div>
				<div class="row">
					<span class="ui-round-btn"><input type="button"
						class="ui-round-btn-text" onClick="moveToAll('u3','u1')"
						value=" &lt;&lt; " style="width:35px"/></span>
				</div>
			</div>
			<div class="sel-list sel-list-right">
				<br /> <label for="u3">关联产品：</label> 
				<select name="proIds" size="2" id="u3" multiple="multiple" ondblclick="moveTo('u3','u1')">
					<c:forEach items="${connlist}" var="conn">
						<option value="${conn.productId}">${conn.productId}--${conn.productName}</option>
					</c:forEach>
				</select>
			</div>
		</div>
	</form>
	<iframe name="result" id="result" src="about:blank" frameborder="0" width="0" height="0"></iframe>
</body>
</html>