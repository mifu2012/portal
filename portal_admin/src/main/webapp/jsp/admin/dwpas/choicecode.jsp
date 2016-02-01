<%@page import="javax.servlet.jsp.tagext.TryCatchFinally"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	//已选择的指标
	String selectedKpiCodes=request.getParameter("selectedKpiCodes");
	//可选择的指标数
	int kpiCodeCount=9999;//默认没限制
	int minKpiCount=1;//至少选择的指标个数
	try
	{
		kpiCodeCount=Integer.parseInt(String.valueOf(request.getParameter("kpiCodeCount")));
	}catch(Exception e){
		kpiCodeCount=9999;
	}
	try
	{
		minKpiCount=Integer.parseInt(String.valueOf(request.getParameter("minKpiCount")));
	}catch(Exception e){
		minKpiCount=1;
	}	
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
			//如果只能选择一个指标
			if(<%=kpiCodeCount%>==1)
			{
				//不可全选或全不选
                document.getElementById("selectAllBtn").disabled=true;
				document.getElementById("unSelectAllBtn").disabled=true;
			}
			dg = frameElement.lhgDG;
			dg.addBtn('ok', '确定', function() {
				var obj1=document.getElementById("u3");
				var array = new Array();
				var array2 = new Array();
				var sign = '${sign}';
				for(var i=0;i<=obj1.options.length-1;i++){
					array.push(obj1.options[i].value);
					array2.push(obj1.options[i].text.substring(obj1.options[i].text.indexOf('---')+3));
				}
                try{
                	if(array.length<<%=minKpiCount%>)
                	{
                	   alert('至少选择 <%=minKpiCount%> 个指标');
                	   return;
                	}
					dg.curWin.document.getElementById(sign).setAttribute('value',array.toString());
				    dg.curWin.document.getElementById('remark'+sign.slice(2)).value=array2.toString();
				}catch(e){
                   try
                   {
					  //模块设计，ID改了
					  if(dg.curWin.document.getElementById('<%=request.getParameter("relCommKpiOptId")%>')!=null)
					  {
						  var relCommKpiOpt=dg.curWin.document.getElementById('<%=request.getParameter("relCommKpiOptId")%>');
						      //清空数据
						      relCommKpiOpt.length=0;
						  for(var i=0;i<array.length;i++)
						  {
                             relCommKpiOpt.add(new Option(array2[i],array[i]));
						  }
					  }
					  //relCommKpiCode
					  if(dg.curWin.document.getElementById('<%=request.getParameter("relCommKpiCode")%>')!=null)
					   {
						  var relCommKpiCode=dg.curWin.document.getElementById('<%=request.getParameter("relCommKpiCode")%>');
                              relCommKpiCode.setAttribute('value',array.toString());
					   }
					   //初始化图表类型
					  if(dg.curWin.document.getElementById('<%=request.getParameter("chartType")%>')!=null)
					   {
                          var chartTypeOpt=dg.curWin.document.getElementById('<%=request.getParameter("chartType")%>');
                          dg.curWin.initChartType(chartTypeOpt,array.length,null);
					   }
                   }
                   catch (e)
                   {
					   //alert(e.message);
                   }
				}
				dg.cancel();
			});
			//已选择的指标
			var selectedKpiCodes="<%=selectedKpiCodes%>";
			var selectedKpiCodeArray=selectedKpiCodes.split(",");
			if(selectedKpiCodes.length>0&&selectedKpiCodeArray.length>0)
			{
				for(var i=0;i<selectedKpiCodeArray.length;i++)
				{
				   if(selectedKpiCodeArray[i]==null) continue;
				   if(document.getElementById("opt_"+selectedKpiCodeArray[i])!=null) document.getElementById("opt_"+selectedKpiCodeArray[i]).selected=true;	
				}
				moveTo("u1","u3");
			}
		});
		 var left=new Array();
		function moveTo(id1, id2) {
			//可选的指标数
			var kpiCodeCount=<%=kpiCodeCount%>;
		    if(kpiCodeCount==1)
		    {
		    	if(document.getElementById("u3").length>0)
		    	{
		    		var u3=document.getElementById("u3").options[0];
		    		document.getElementById("u1").options.add(new Option(u3.text,u3.value));
		    		document.getElementById("u3").length=0;
		    		//moveToAll('u3','u1');
		    	}
		    	//只能选择一个指标,清空所有的指标
		    	//document.getElementById("u3").length=0;
		    }else
		    {
		    	if(document.getElementById("u3").length>kpiCodeCount)
		    	{
		    		alert('此栏目最多只能选择<%=kpiCodeCount%>个指标');
		    		return;
		    	}
		    }
			var obj1 = document.getElementById(id1);
			var obj2 = document.getElementById(id2);
			var flag = true;
			for (var i = obj1.options.length - 1; i >= 0; i--) {
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
		
		function moveToAll(id1, id2){
			//可选的指标数
			var kpiCodeCount=<%=kpiCodeCount%>;
		    if(kpiCodeCount==1)
		    {
		    	//只能选择一个指标,清空所有的指标
		    	document.getElementById("u3").length=0;
		    }else
		    {
		    	if(document.getElementById("u3").length>kpiCodeCount)
		    	{
		    		alert('此栏目最多只能选择<%=kpiCodeCount%>个指标');
		    		return;
		    	}
		    }			
			var obj1 = document.getElementById(id1);
			var obj2 = document.getElementById(id2);
			var flag = true;
			for (var i = obj1.options.length - 1; i >= 0; i--) {
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
		function removeOne(arr, index) {
			for ( var i = index; i < arr.length - 1; i++) {
				arr[i] = arr[i + 1];
			}
			arr.length = arr.length - 1;
		}
		
		
		function seekCommonCode(){
			var kpiType = '${kpiType}';
			document.body.disabled=true;
			var comKpiName=document.getElementById("comKpiName").value.toUpperCase();
			$.post("<%=path%>/dwpas/queryByComKpiName.html",{comKpiName:comKpiName,kpiType:kpiType},function(comlist){
			   if(comlist==null||comlist.length==0)
				{
				   document.getElementById("u1").length=0;
					document.body.disabled=false;
					$("#comKpiName").focus();				   
				   return;
				}
				var kpiInfo=null;
				var noRelOpt=document.getElementById("u1");
				if(noRelOpt!=null) noRelOpt.length=0;//清空
				for(var i=0;i<comlist.length;i++)
				{
					kpiInfo=comlist[i];
				   if(kpiInfo==null) continue;
						   noRelOpt.add(new Option(kpiInfo.comKpiCode+'---'+kpiInfo.comKpiName,kpiInfo.comKpiCode));//text,value
				}
				document.body.disabled=false;
				$("#comKpiName").focus();
			});
		}
		function keySelectCode(){
            if (event.keyCode == 13) { 
            	seekCommonCode();
              } 
		}
	</script>
</head>
<body>
	<form id="choiceCodeForm"  name="choiceCodeForm" method="post"
		action=" " target="result" >
		<div class="fn-clear">
			<div class="sel-list sel-list-left">
				<input type="text" id="comKpiName"
					class="ui-input ui-input-mini" style="width: 170px" name="comKpiName" onkeydown="javascript:keySelectCode();"/> <span
					class="ui-round-btn ui-round-btn-mini"><input type="button"
					class="ui-round-btn-text" value="搜索" onclick="seekCommonCode();" id="prodComCode"></span><br /> <label for="u1">指标列表：</label> 
				<select id="u1"
					name="allCommCodes" size="2" multiple="multiple"
					ondblclick="moveTo('u1','u3')" style="height:250px">
					<c:forEach items="${commonkpiCodeList}" var="common">
						<option value="${common.comKpiCode }" id="opt_${common.comKpiCode }">${common.comKpiCode }---${common.comKpiName }</option>
					</c:forEach>
				</select>
			</div>
			<div class="sel-act">
				<div class="row">
					<span class="ui-round-btn"><input type="button" title='选择'
						class="ui-round-btn-text" onClick="moveTo('u1','u3')"
						value=" &gt; "  style="width:35px"/></span>
				</div>
				<div class="row">
					<span class="ui-round-btn"><input type="button" title='反选'
						class="ui-round-btn-text" onClick="moveTo('u3','u1')"
						value=" &lt; "  style="width:35px"/></span>
				</div>
				<div class="row">
					<span class="ui-round-btn"><input type="button" id="selectAllBtn"
						class="ui-round-btn-text" onClick="moveToAll('u1','u3')" title='全选'
						value=" &gt;&gt; " style="width:35px"/></span>
				</div>
				<div class="row">
					<span class="ui-round-btn"><input type="button" id="unSelectAllBtn"
						class="ui-round-btn-text" onClick="moveToAll('u3','u1')" title='全不选'
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