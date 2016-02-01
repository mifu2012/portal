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
<title>绑定指标</title>
<link charset="utf-8" rel="stylesheet"
	href="https://static.alipay.com/build/css/alice/alice.css?t=aa11"
	media="all" />
<link type="text/css" charset="utf-8" rel="stylesheet"
	href="<%=basePath%>css/css.css" />
	<script type="text/javascript" src="../js/jquery-1.5.1.min.js"></script>	
<script type="text/javascript">
araleConfig = {
	combo_host: 'https://static.alipay.com',
	combo_path: '/min/?b=javascript/arale_v101&f=',
	corex: true
};
var dg;
$(document).ready(function(){
	dg = frameElement.lhgDG;
	dg.addBtn('ok','保存',function(){
		var obj1=document.getElementById("u3");
		for(i=0;i<=obj1.options.length-1;i++){
			obj1.options[i].selected=true;
		}
		$("#form2").submit();
	});
});
 function disableEnterKey(e) {  
        var key;  
        if (window.event)  
            key = window.event.keyCode; //IE  
       else  
           key = e.which; //firefox       
  
        return (key != 13);  
    }
    var left=new Array();
	function getAll(){	
		var obj1=document.getElementById("u1");
		for(i=0;i<=obj1.options.length-1;i++){
			var v1=obj1.options[i].value;
			var txt1=obj1.options[i].text;
			var op1=new Option(txt1,v1);
			left[i]=op1;
		}
	}
	function moveTo(id1,id2)
			{
				var obj1=document.getElementById(id1);
				var obj2=document.getElementById(id2);
				var flag=true;
				for(i=obj1.options.length-1;i>=0;i--)
				{
					var curr=obj1.options[i];
					if(curr.selected)
					{
						if(obj1.id=='u3'){
							var v1=curr.value;
							var txt1=curr.text;
							var op1=new Option(txt1,v1);						
							obj2.options[obj2.options.length]=op1;
							obj1.options[i]=null;
							left[left.length]=op1;
							flag=false;
						}else{
							for(var j=0;j<left.length;j++){
								if(left[j].text==obj1.options[i].text){
									removeOne(left,j);
								}
							}
							var v1=curr.value;
							var txt1=curr.text;
							var op1=new Option(txt1,v1);						
							obj2.options[obj2.options.length]=op1;
							obj1.options[i]=null;
							flag=false;
						}
					}
				}
				if(flag)
				{
					alert('请至少选择一项指标');
				}
			}
		function removeOne(arr,index){
			for(var i=index;i<arr.length-1;i++){
				arr[i]=arr[i+1];
			}
			arr.length=arr.length-1;
		}
		function selectOne1(){
			var kpiCode = document.getElementById('kpiCode').value;
			var comKpiCode= document.getElementById('comKpiCode').value;
			 if(comKpiCode != null && comKpiCode != ""){
				 location.href='<%=path%>/ComkpiRelKpi/showRelKpi'+comKpiCode+'.html?&kpiCode=' + kpiCode;
		}
	}
		function selectAll(){
			var obj1=document.getElementById("u3");
	 		var comKpiCode = document.getElementById("comKpiCode");
	 		var url = "ComkpiRelKpi/updateKPIRelativeByKPICode"+obj1+".html";
			for(i=0;i<=obj1.options.length-1;i++){
				obj1.options[i].selected=true;
			}		
	 		$.get(url,function(data){
	 			if(data=="success"){
	 				document.location.href="showRelKpi";
	 			}
	 		});
			
		}
</script>
</head>
<body>
	<form class="form-wrapper" name="form2" id="form2" method="post"
		action="<%=path%>/ComkpiRelKpi/updateKPIRelativeByKPICode.html" onSubmit="selectAll();">
		<input id="comKpiCode" type="hidden" name="comKpiCode" value="${comKpiCode}" />
		<div class="fn-clear">
			<div class="sel-list">
				<input type="text" id="kpiCode" name="kpiCode"
					class="ui-input ui-input-mini" value="${kpiCode}"/> <span
					class="ui-round-btn ui-round-btn-mini"><input type="button"
					class="ui-round-btn-text" value="搜索" onClick="selectOne1();">
				</span> <br /> <label for="u1">指标列表：</label> <select id="u1"
					name="kpiUnRelInfo" size="2" multiple="true"
					ondblclick="moveTo('u1','u3')">
					<c:forEach items="${kpiUnRelList}" var="kpiUnRelInfo">
						<option value="${kpiUnRelInfo.kpiCode}">${kpiUnRelInfo.kpiCode}
							- ${kpiUnRelInfo.kpiName}</option>
					</c:forEach>
				</select>
			</div>
			<div class="sel-act">
				<div class="row">
					<span class="ui-round-btn"><input type="button"
						class="ui-round-btn-text" onClick="moveTo('u1','u3')"
						value=" &gt; " />
					</span>
				</div>
				<div class="row">
					<span class="ui-round-btn"><input type="button"
						class="ui-round-btn-text" onClick="moveTo('u3','u1')"
						value=" &lt; " />
					</span>
				</div>
			</div>
			<div class="sel-list sel-list-right">
				<br /> <label for="u3">关联指标：</label> <select name="kpiRelInfo"
					size="2" id="u3" multiple="true" ondblclick="moveTo('u3','u1')">
					<c:forEach items="${kpiRelList}" var="kpiRelInfo">
						<option value="${kpiRelInfo.kpiCode}">${kpiRelInfo.kpiCode}
							- ${kpiRelInfo.kpiName}</option>
					</c:forEach>
				</select>
			</div>
		</div>
	</form>
</body>
</html>