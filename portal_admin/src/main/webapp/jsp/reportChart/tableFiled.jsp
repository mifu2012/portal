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
<title>图表类别系列字段选择</title>
<link href="<%=basePath%>css/css.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>js/jquery-1.5.1.min.js" type="text/javascript"></script>
<script type="text/javascript"
	src="<%=basePath%>js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>
</head>
<body class="indexbody"
	style="height: 100%; width: 100%; overflow: hidden;">
	<div class="common-box">
		<div class="fn-clear">
			<div class="left-box" style="width:150px; float: left;">
				<p>
					<span
						style="width: 170px; height: 20px; float: left; display: inline-block;"
						for="u1"><b>未选择字段</b> </span>
				</p>
				<select id="u1" name="kpiUnRelInfo"
					style="width:170px; height: 210px; overflow: auto; font-size: 13px;"
					size="2" multiple="true" ondblclick="moveTo('u1','u3')">
					<c:forEach items="${IsNotSelTableFiledList}" var="IsNotSelTableFiled">
						<option value="${IsNotSelTableFiled}">${IsNotSelTableFiled}
						</option>
					</c:forEach>
				</select>
			</div>
			<div class="sel-act"
				style="position: absolute; left:200px;top:160px">
				<div class="row">
					<span class="ui-round-btn"><input type="button" style="width: 30px"
						class="ui-round-btn-text" onClick="moveTo('u1','u3')"
						value=" &gt; " /> </span>
				</div>
				<div class="row">
					<span class="ui-round-btn"><input type="button" style="width: 30px"
						class="ui-round-btn-text" onClick="moveTo('u3','u1')"
						value=" &lt; " /> </span>
				</div>
			</div>
			<div class="left-box"
				style="width: 130px; position: absolute; left:220px; top: 20px">
				<p>
					<span for="u3"
						style="width: 160px; height: 20px; float: left; display: inline-block;"><b>已选择字段</b>
					</span>
				</p>
				<select name="u3" size="2" id="u3"
					style="width:170px; height:210px; overflow: auto; font-size: 13px;"
					multiple="true" ondblclick="moveTo('u3','u1')">
					<<c:forEach items="${IsSelTableFiledList}" var="IsSelTableFiled">
						<option value="${IsSelTableFiled}">${IsSelTableFiled}
						</option>
					</c:forEach> 
				</select>
			</div>
		</div>
	</div>
	<div class="fn-clear"></div>
	<div class="footer22"></div>
	<script type="text/javascript">
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
    				if("${selY}"=="0" || "${chartType}"=="1"){
    					//可选的指标数
    					var kpiCodeCount=document.getElementById("u3").length;
    				    if(kpiCodeCount==1)
    				    {
    				    	if(document.getElementById("u3").length>0)
    				    	{
    				    		var u3=document.getElementById("u3").options[0];
    				    		document.getElementById("u1").options.add(new Option(u3.text,u3.value));
    				    		document.getElementById("u3").length=0;
    				    	}
    				    }
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
    				}else{
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
    				}
    				if(flag)
    				{
    					alert('请至少选择一个字段');
    				}
    			}
    		function removeOne(arr,index){
    			for(var i=index;i<arr.length-1;i++){
    				arr[i]=arr[i+1];
    			}
    			arr.length=arr.length-1;
    		}
    		
    	var dg;
    		$(document).ready(function() {
    			dg = frameElement.lhgDG;
    			dg.addBtn('ok', '确定', function() {
    				var obj1=document.getElementById("u3");
    				//selY=0时代表x轴类别系统 selY=1时代表 y轴值系列
    				if("${selY}"=="0"){
    					//饼图
    					if("${chartType}"=="1"){
    						if(obj1.options.length==0){
    							alert("饼图类别系列至少选择一个字段");
    							return;
    						}
    						if(obj1.options.length>1){
    							alert("饼图类别系列只能选择一个字段");
    							obj1.options.length=0;
    							return;
    						}
    					}else{
    						if(obj1.options.length==0){
    							alert("类别系列(X轴)至少选择一个字段");
    							return;
    						}
    						if(obj1.options.length>1){
    							alert("类别系列(X轴)只能选择一个字段");
    							obj1.options.length=0;
    							return;
    						}
    					}
    				}
    				//判断值系列
    				if("${selY}"=="1"){
    					if("${chartType}"=="5"){
    						if(obj1.options.length<2){
        						alert("组合图值系列至少选择两个字段");
        						return;
        					}
    					}else if("${chartType}"=="1"){
    						if(obj1.options.length==0){
        						alert("饼图值系列至少选择一个字段");
        						return;
        					}
    						if(obj1.options.length>1){
        						alert("饼图值系列只能选择一个字段");
        						obj1.options.length=0;
        						return;
        					}
    					}else{
    						if(obj1.options.length==0){
        						alert("值系列(Y轴)至少选择一个字段");
        						return;
        					}
    					}
    				}
    				var array = "";
    				for(var i=0;i<=obj1.options.length-1;i++){
    					if (i != 0) {
							array += ",";
						}
    					array += obj1.options[i].value;
    				}
                    try{
                    	if("${selY}"=="1"){
    					dg.curWin.document.getElementById("chartY").setAttribute('value',array);
                    	}else{
                    	dg.curWin.document.getElementById("chartX").setAttribute('value',array);
                    	}
    				}catch(e){
    			}
    				dg.cancel();
    			});
    		});
	</script>
</body>
</html>
