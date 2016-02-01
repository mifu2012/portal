<%@ page language="java" contentType="text/html; charset=UTF-8" %>
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
<title>绑定指标</title>
<link charset="utf-8" rel="stylesheet" href="<%=basePath%>/css/alice.css"
	media="all" />
<link type="text/css" charset="utf-8" rel="stylesheet"
	href="<%=basePath%>css/css.css" />
<script src="<%=basePath%>js/jquery-1.5.1.min.js" type="text/javascript"></script>
<script type="text/javascript">
    var left=new Array();
	function moveTo(id1,id2)
			{
				var obj1=document.getElementById(id1);
				var obj2=document.getElementById(id2);
				var flag=true;
				for(var i=obj1.options.length-1;i>=0;i--)
				{
					var curr=obj1.options[i];
					if(curr.selected)
					{
						if(obj1.id=='u3'){
							var v1=curr.value;
							var txt1=curr.text;
							var op1=new Option(txt1,v1);

							op1.setAttribute("optGroupVal",curr.getAttribute("optGroupVal"));//optGroupVal;
							op1.setAttribute("optGroupId",curr.getAttribute("optGroupId"));//optGroupVal;
							var groupId=curr.getAttribute("optGroupId");//curr.getAttribute("optGroup_"+curr);
                            var group=document.getElementById(groupId);
                            if(group==null)
                            {
                            	var groupVal=curr.getAttribute("optGroupVal");//optGroupVal;
    		            		group = document.createElement("optgroup");
    		            		group.id=groupId;
    		            		group.label=groupVal;
    		            		document.getElementById("u1").appendChild(group);
                            }
                            if(group!=null) 
                            {
                            	group.appendChild(op1);
                            }else
                            {
                            	document.getElementById("u3").appendChild(op1);
                            }
                            try
        	            	{
                            	op1.innerHTML=txt1;
        	            	}catch(e)
        	            	{
        	            	}
							//obj2.options[obj2.options.length]=op1;
							try
							{
								obj1.options[i]=null;
							}catch(e)
							{
								alert(e.message);
								obj1.options[i].parentNode.removeChild(obj1.options[i]);
							}
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
							op1.setAttribute("optGroupVal",curr.getAttribute("optGroupVal"));//optGroupVal;
							op1.setAttribute("optGroupId",curr.getAttribute("optGroupId"));//optGroupVal;
							obj2.options[obj2.options.length]=op1;
							//obj1.options[i]=null;
							obj1.options[i].parentNode.removeChild(obj1.options[i]);
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
	//保存关联指标
	function saveAll(){
		var kpiCode=$("#codeValue").val();
		if(kpiCode=="" || kpiCode==null){
			alert("请选择指标!");
			return false;
		}
		var obj1=document.getElementById("u3");
		obj1.options.selected=true;
		var linkedKpiCode="";
		for(var i=0;i<=obj1.options.length-1;i++){
			linkedKpiCode=linkedKpiCode+","+obj1.options[i].value;
		}	
	    $.ajax({
	        type: "POST",                                                                 
	        url:"<%=basePath%>/kpiRelateManage/update.html?kpiCode="+kpiCode+"&linkedKpiCode="+linkedKpiCode,
	        success: function(msg){ 
	        	if(msg=="success"){
	        		alert("保存成功");
	        	}else{
	        		alert("保存失败");
	        	}
	        	obj1.options.selected=false;
	          }
	    }); 
	}
	
	function butOnKeyUp() {
		//根据输入字符查询
		 var val = jQuery.trim($("#J-searchval-f1").val());
// 			 val=val.toUpperCase();//字符转为大写
			var lilist = $("#J-searchul-f1>li");
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
	
	function searchf1(){
		var val=document.getElementById("J-searchval-f1").value;
		val=$.trim(val);
		  $.ajax({
		        type: "POST",                                                                 
		        url:encodeURI("<%=basePath%>/kpiRelateManage/searchKpi.html?kpiCode="+val+"&random="+Math.random()),
		        success: function(msg){ 
		        	var msg=eval(msg);
		        	var listHtml="";
		            for(var i=0;i<msg.length;i++){
		            listHtml+="<li style='width: 1000px' title='"+msg[i].kpiNameShow+"'><a href='javascript:;' onclick=setValue('"+msg[i].kpiCode+"')><font id='"+msg[i].kpiCode+"'>"
		            	+msg[i].kpiCode+'-'+msg[i].kpiName+'-' +msg[i].kpiNameShow+"</font></a></li>";
	        	   }  
		           document.getElementById("J-searchul-f1").innerHTML=listHtml;
		       }
		  });
	}
	function searchf2(){
		var baseKpiCode=$("#codeValue").val();
		if(baseKpiCode=="" || baseKpiCode==null){
			alert("请选择指标!");
			return false;
		}
		document.getElementById("loading_wait_Div").style.display="";
		var obj1=document.getElementById("u3");
		obj1.options.selected=true;
		var linkedKpiCode="";
		for(var i=0;i<=obj1.options.length-1;i++){
			linkedKpiCode=linkedKpiCode+","+obj1.options[i].value;
		}	
		obj1.options.selected=false;
		var val=$.trim($("#J-searchval-f2").val());
		
		  $.ajax({
		        type: "POST",                                                                 
		        url:encodeURI("<%=basePath%>/kpiRelateManage/searchNotRelate.html?kpiCode="+val+"&baseKpiCode="+baseKpiCode+"&linkedKpiCode="+linkedKpiCode+"&random="+Math.random()),
		        success: function(msg){ 
		        	var msg=eval(msg);
		        	try
		        	{
		        		document.getElementById("u1").length=0;
		        	}catch(e)
		        	{
		        		var noRelOpt=document.getElementById("u1");
						while (noRelOpt.hasChildNodes()) { 
						   noRelOpt.removeChild(noRelOpt.firstChild); 
						}
		        	}
		        	var u1=document.getElementById("u1");
		            for(var i=0;i<msg.length;i++){
		            	var group=document.getElementById("optGroup_"+msg[i].goalType);
		            	if(group==null)
		            	{
		            		group = document.createElement("optgroup");
		            		group.id="optGroup_"+msg[i].goalType;
		            		group.label=msg[i].goalTypeDesc;
		            		u1.appendChild(group);
		            	}
		            	var value=msg[i].kpiCode;
		            	var text=msg[i].kpiCode+"-"+msg[i].kpiName;
		            	var option=new Option(text,value);
		            	try
		            	{
		            		option.innerHTML=text;
		            	}catch(e)
		            	{
		            	}
		            	//GROUP_ID
		            	option.setAttribute("optGroupId",group.id);
		            	option.setAttribute("optGroupVal",msg[i].goalType);
		             	option.title=msg[i].kpiName;
		            	//document.getElementById("u1").options.add(option);
		            	group.appendChild(option);
	        	   }  
		       document.getElementById("loading_wait_Div").style.display="none";
		       }
		  }); 
	}
	//根据指标查询关联指标数据为指标Code赋值
	function setValue(val){
		$.ajax({
	        type: "POST",                                                                 
	        url:encodeURI("<%=basePath%>/kpiRelateManage/searchRelate.html?kpiCode="+val+"&random="+Math.random()),
	        success: function(msg){ 
	        	var msg=eval(msg);
	        	$("#codeValue").val(val);
	        	try
	        	{
	        		document.getElementById("u3").length=0;
	        	}catch(e)
	        	{
	        		var noRelOpt=document.getElementById("u3");
					while (noRelOpt.hasChildNodes()) { 
					   noRelOpt.removeChild(noRelOpt.firstChild); 
					}
	        	}
	            for(var i=0;i<msg.length;i++){
	            	var value=msg[i].kpiCode;
	            	var text=msg[i].kpiCode+"-"+msg[i].kpiName;
	            	var option=new Option(text,value);
	            	//option.innerHTML=text;
	            	option.setAttribute("optGroupId","optGroup_"+msg[i].goalType);
	            	option.setAttribute("optGroupVal",msg[i].goalTypeDesc);
	            	option.title=msg[i].kpiName;
	            	document.getElementById("u3").add(option);
        	   }  
	       }
	  }); 
		var baseKpiCode="";
		document.getElementById("loading_wait_Div").style.display="";
		$.ajax({
	        type: "POST",                                                                 
	        url:encodeURI("<%=basePath%>/kpiRelateManage/searchNotRelate.html?kpiCode="+val+"&baseKpiCode="+baseKpiCode+"&random="+Math.random()),
	        success: function(msg){ 
	        	var msg=eval(msg);
	        	try
	        	{
	        		document.getElementById("u1").length=0;
	        	}catch(e)
	        	{
	        		var noRelOpt=document.getElementById("u1");
					while (noRelOpt.hasChildNodes()) { 
					   noRelOpt.removeChild(noRelOpt.firstChild); 
					}
	        	}
	        	var u1=document.getElementById("u1");
	            for(var i=0;i<msg.length;i++){
	            	var group=document.getElementById("optGroup_"+msg[i].goalType);
	            	if(group==null)
	            	{
	            		group = document.createElement("optgroup");
	            		group.id="optGroup_"+msg[i].goalType;
	            		group.label=msg[i].goalTypeDesc;
	            		u1.appendChild(group);
	            	}	
	            	var text=msg[i].kpiCode+"-"+msg[i].kpiName;
	            	var value=msg[i].kpiCode;
	            	var option=new Option(text,value);
	            	try
	            	{
	            		option.innerHTML=text;
	            	}catch(e)
	            	{
	            	}
	            	//GROUP_ID
	            	option.setAttribute("optGroupId",group.id);
	            	option.setAttribute("optGroupVal",msg[i].goalTypeDesc);
	             	option.title=msg[i].kpiName;
	            	//document.getElementById("u1").options.add(option);
	            	group.appendChild(option);
        	   }  
	            document.getElementById("loading_wait_Div").style.display="none";
	       }
	  }); 
		 $("#J-searchul-f1 li a font").removeClass("active");
		 $("#"+val).addClass("active");
	}
	
	
	function isIE(){ //ie? 
	   if (window.navigator.userAgent.toLowerCase().indexOf("msie")>=1) 
	    return true; 
	   else 
	    return false; 
	} 

	if(!isIE()){ //firefox innerText define
	   HTMLElement.prototype.__defineGetter__("innerText", 
	    function(){
	     var anyString = "";
	     var childS = this.childNodes;
	     for(var i=0; i<childS.length; i++) {
	      if(childS[i].nodeType==1)
	       anyString += childS[i].tagName=="BR" ? '\n' : childS[i].textContent;
	      else if(childS[i].nodeType==3)
	       anyString += childS[i].nodeValue;
	     }
	     return anyString;
	    } 
	   ); 
	   HTMLElement.prototype.__defineSetter__("innerText", 
	    function(sText){ 
	     this.textContent=sText; 
	    } 
	   ); 
	}
	//清空
	function clearValue(){
		$("#J-searchval-f2").val("");
	}
	
	//回车搜索
	function butOnClick2(e) { 
    	var event=(navigator.appName=="Netscape")?e.which:e.keyCode;   
        if (event == 13) { 
        var button = document.getElementById("J-search-f2"); //bsubmit 为botton按钮的id 
        button.click(); 
        return false; 
        } 
       } 
	
</script>
</head>
<body>
	<br />
	<div style=" position: absolute;right:0px;top:0px;">提示：<font color='red' size='2'>请先选择被绑定的指标，再进行关联操作</font></div>
	<form class="form-wrapper" name="form2" method="post" action="">
		<div class="left-box"
			style="width: 283px; float: left; margin-right: 5px">
			<p style="width: 240px; float: left;">
				<input type="text" id="J-searchval-f1" onkeyup="butOnKeyUp()"
					style="width: 150px; height: 20px; float: left; margin-right: 10px; line-height: 20px" />
				<input type="button" id="J-search-f1" value="搜索"
					style="float: left; width: 60px; height: 24px; line-height: 24px" onclick="searchf1();"
					/>
			</p>
			<p style="width: 240px; float: left;">
				<span
					style="width: 160px; height: 20px; float: left; display: inline-block;"><b>指标列表</b></span>
			</p>
			<ul class="zb-ul" id="J-searchul-f1" style="height: 400px">
				<c:forEach items="${dwmisKpiInfoList}" var="item">
					<li style="width: 1000px"  title="${item.kpiNameShow}"><a href="javascript:;"
						onclick="setValue('${item.kpiCode}')">
						<font id="${item.kpiCode}">
						${item.kpiCode} -${item.kpiName} - ${item.kpiNameShow}</font></a>
					</li>
				</c:forEach>
			</ul>
		</div>
		<div class="left-box" style="width: 283px; float: left;">
			<p style="width: 240px; float: left;">
				<input type="text" id="J-searchval-f2" onfocus="clearValue()" onkeydown="javascript:butOnClick2(event);"
					style="width: 150px; height: 20px; float: left; margin-right: 10px; line-height: 20px" />
				<input type="button" id="J-search-f2" value="搜索"
					style="float: left; width: 60px; height: 24px; line-height: 24px"
					onclick="searchf2()" />
				<input type="button" value="保存" onClick="saveAll();" 
				style="position: absolute;margin-left:100px;width: 60px; height: 24px; line-height: 24px"/>
			</p>
			<p style="width: 240px; float: left;">
				<span
					style="width: 160px; height: 20px; float: left; display: inline-block;"><b>未关联指标</b></span>
			</p>
			<div id='loading_wait_Div' name='loading_wait_div' style='border:0px solid red;position: absolute; left: 400px; top: 35%;display:none' ><img src='<%=request.getContextPath()%>/images/loading.gif' title='数据正在加载中...'></div>
			<select style="border: 1px solid #999;  font-size: 14px;height:403px;width:282px;;"
				id="u1" name="kpiUnRelInfo" multiple="true"
				ondblclick="moveTo('u1','u3')" >
			</select>
			</div>
		<div style="with:36px; height:100%; float:left">
		<div align="center">
	<div style="margin:200px 10px 0px 10px">
				<span class="ui-round-btn"><input type="button"
					class="ui-round-btn-text" onClick="moveTo('u1','u3')"
					value=" &gt; " /> </span>
		<br><br>
				<span class="ui-round-btn"><input type="button"
					class="ui-round-btn-text" onClick="moveTo('u3','u1')"
					value=" &lt; " /> </span></div>
					
		</div>
		</div>
		<div class="left-box"
			style="width: 283px; float: left; margin-left: 0px;position: Relative;margin-top:35px;">
			<input type="text" id="codeValue" style="display: none;"/>
			 <label for="u3">
				<p style="width: 240px; float: left;">
					<span style="width: 160px; height: 20px; float: left; display: inline-block;"><b>已关联指标</b></span>
				</p>
			 </label> 
			 <select
				style="border: 1px solid #999; width: 282px; height: 402px; font-size: 14px;"
				name="linkedKpiCode" size="2" id="u3" multiple="true"
				ondblclick="moveTo('u3','u1')">
			</select> 
		</div>
	</form>
</body>
</html>