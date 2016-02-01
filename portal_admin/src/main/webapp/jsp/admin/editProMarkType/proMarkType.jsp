<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	<script type="text/javascript" src="<%=basePath%>js/datePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>
	<style type="text/css">
	.myactive{
	 background:#3399ff;
      display:inline-block; 
      color:#fff;
	}
	
	</style>
</head>
<body>
<body class="indexbody">
<div style=" position: absolute;right:10px;top:10px;">提示：<font color='red' size='2'>请先选择产品类型，再修改产品类型的关联产品</font></div>
        <div class="common-box">
            <div class="left-box">
                <p>
                    <input type="text" id="J-searchval-f" style=" width:180px; height:20px; float:left; margin-right: 10px" onkeyup="butOnKeyUp()" onkeydown="javascript:butOnClick();"/>
                    <input type="button" id="J-search-f" value="搜索" style="float: left; width: 60px; height: 24px;" />
                </p>
                <p>
                    <span style=" width:160px; height:20px; float: left;margin-right:10px; display:inline-block;"><b>产品类型列表</b></span>
                </p>
                <ul style="border:1px solid #999; width:280px; height:250px; overflow-x:hidden;overflow-y:auto; font-size:14px;height: 300px;margin-left: 10px" id="J-searchul-f">
					  <c:forEach items="${misTypelist}" var="item" varStatus="vel">
							<li id="li_prdInfo_${item.typeId}">
								<table border="1" width="100%">
								   <tr>
									 <td colspan="3" height="30"><a href='javascript:void(0);' onclick="showInfo('${item.typeId}')"><font id="${item.typeId}">${item.typeId} - ${item.typeName}</font></a></td>
								   </tr>
								</table>
							</li>
						</c:forEach>
				    </ul>
            </div>
		<div class="fn-clear">
		<input type="hidden" id="typeId3" style="display: none;" />
			<div class="left-box" style="width: 283px; float: left;position: absolute;left:330px">
			<p >
				<input type="text" id="kpiCode" name="kpiCode" style=" width:180px; height:20px; float:left; margin-right: 10px" onkeyup="butOnKeyUpPro()"   onkeydown="javascript:butOnClick2();"/> 
				<input type="button" style=" float:left; width:60px; height:24px; margin-right: 10px" value="搜索"  id="J-search-f2">
			</p>
			<p>
                 <span style=" width:160px; height:20px; float:left; display:inline-block;" for="u1" ><b>未关联产品</b></span>
            </p>
				  <select id="u1" name="prodInfo" style="width: 282px; height: 300px;overflow: auto; font-size: 13px;" size="2" multiple="true"
					ondblclick="moveTo('u1','u3')">
				</select>
				
			</div>
			<div id='loading_wait_Div' name='loading_wait_div' style='border:0px solid red;position: absolute; left: 420px; top: 30%;display:none' ><img src='<%=request.getContextPath()%>/images/loading.gif' title='数据正在加载中...'></div>
			  <div class="sel-act" style="position: absolute;left:630px;top:215px">
			<div class="row">
				<span class="ui-round-btn"><input type="button"
					class="ui-round-btn-text" onClick="moveTo('u1','u3')"
					value=" &gt; "  title="设为已关联产品"/> </span>
			</div>
			<div class="row" style="margin-top: 50px">
				<span class="ui-round-btn"><input type="button"
					class="ui-round-btn-text" onClick="moveTo('u3','u1')"
					value=" &lt; " title="设为未关联产品"/> </span>
			</div>
			
		</div>
			
			<div class="left-box"
			style="width: 283px; position: absolute;left:650px;top: 55px">
				<p>
                    <span for="u3" style=" width:160px; height:20px; float:left; display:inline-block;"><b>已关联产品</b></span>
                </p>
				<select name="kpiRelInfo"
					size="2" id="u3" style="width: 282px; height: 300px;overflow: auto; font-size: 13px;" multiple="true" ondblclick="moveTo('u3','u1')">
					<%-- <c:forEach items="${kpiRelList}" var="kpiRelInfo">
						<option value="${kpiRelInfo.kpiCode}">${kpiRelInfo.kpiCode}
							- ${kpiRelInfo.kpiName}</option>
					</c:forEach> --%>
				</select>
			</div>
		</div>
        </div>
        <div class="fn-clear"></div>
		<div class="footer22"></div>
    <script type="text/javascript">
    function disableEnterKey(e) {  
        var key;  
        if (window.event)  
            key = window.event.keyCode; //IE  
       else  
           key = e.which; //firefox       
  
        return (key != 13);  
    }
    function butOnClick() { 
    if (event.keyCode == 13) { 
     var button = document.getElementById("J-search-f"); //bsubmit 为botton按钮的id 
     button.click(); 
     return false; 
     } 
   }
    function butOnClick2() { 
        if (event.keyCode == 13) { 
        var JSearchButton= document.getElementById("J-search-f2"); //bsubmit 为botton按钮的id 
        JSearchButton.click(); 
        return false;
        } 
      }
    function clearValue(){
    	$("#kpiCode").val("");
    }
    function butOnKeyUp() {
		//根据输入字符查询
		 var val = jQuery.trim($("#J-searchval-f").val());
			 val=val.toUpperCase();//字符转为大写
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
        } 
            //产品搜索
        function showInfo(val){
        	document.body.disabled=true;
        	document.getElementById("loading_wait_Div").style.display="";
        	//设置隐藏的通用产品值
        	$("#typeId3").val(val);
//         	$("#kpiCode").val("产品列表未显示完全，请选择搜索");
        	//设置未关联产品列表数据
        	$.ajax({
	        type: "POST",                                                                 
	        url:encodeURI("<%=basePath%>proMarked/getUnMarkedPro.html?typeId="+val+"&random="+Math.random()),
	        success: function(msg){ 
	        	var msg=eval(msg);
	        	document.getElementById("u1").length=0;
	            for(var i=0;i<msg.length;i++){
	            	var value=msg[i].productId;
	            	var text=msg[i].productId+"-"+msg[i].productName;
	            	var option=new Option(text,value);
	            	option.title=msg[i].productName;
	            	document.getElementById("u1").add(option);
        	   }  
	            document.getElementById("loading_wait_Div").style.display="none";
	            document.body.disabled=false;
	       }
	   }); 
        	//设置列表点击后背景颜色
        	$("#J-searchul-f li a font").removeClass("myactive");
    		$("#"+val).addClass("myactive");
    		//设置已关联产品数据
        	$.ajax({
	        type: "POST",                                                                 
	        url:encodeURI("<%=basePath%>proMarked/getMarkedPro.html?typeId="+val+"&random="+Math.random()),
	        success: function(msg){ 
	        	var msg=eval(msg);
	        	document.getElementById("u3").length=0;
	            for(var i=0;i<msg.length;i++){
	            	var value=msg[i].productId;
	            	var text=msg[i].productId+"-"+msg[i].productName;
	            	var option=new Option(text,value);
	            	option.title=msg[i].productName;
	            	document.getElementById("u3").add(option);
        	   }  
	       }
	   }); 
    }
        var left=new Array();
    	function getAll(){	
    		var obj1=document.getElementById("u1");
    		for(var i=0;i<=obj1.options.length-1;i++){
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
    				for(var i=obj1.options.length-1;i>=0;i--)
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
    					alert('请至少选择一项产品');
    				}
    			}
    		function removeOne(arr,index){
    			for(var i=index;i<arr.length-1;i++){
    				arr[i]=arr[i+1];
    			}
    			arr.length=arr.length-1;
    		}
    		//未关联产品搜索 J-search-f2
    		 $("#J-search-f2").click(function () {
    			var typeId = $.trim($("#typeId3").val());
            	if(typeId == ""){
            		alert('请先选择产品!');
            		return false;
            	}
            	document.getElementById("loading_wait_Div").style.display="";
            	var keyCode =$.trim($("#kpiCode").val());
            	
    			var obj1=document.getElementById("u3");
    			obj1.options.selected=true;
    			var markedPro="";
    			for(var i=0;i<=obj1.options.length-1;i++){
    				markedPro+=obj1.options[i].value+",";
    			}
    			$.ajax({
    		        type: "POST",                                                                 
    		        url:encodeURI("<%=path%>/proMarked/getUnMarkedPro.html?&keyCode="+keyCode+"&typeId="+typeId+"&markedPro="+markedPro+"&random="+Math.random()),
    		        success: function(msg){ 
    		        	var msg=eval(msg);
    		        	document.getElementById("u1").length=0;
    		        	document.getElementById("loading_wait_Div").style.display="none";
    		            for(var i=0;i<msg.length;i++){
    		            	var value=msg[i].productId;
    		            	var text=msg[i].productId+"-"+msg[i].productName;
    		            	var option=new Option(text,value);
    		            	option.title=msg[i].productName;
    		            	document.getElementById("u1").add(option);
    	        	   }  
    		       }
    		   }); 
    		 });
    	
    		//保存关联产品
    	var dg;
		$(document).ready(function(){
			dg = frameElement.lhgDG;
			dg.addBtn('ok','保存',function(){
				saveAll();
			});
		});
    		function saveAll(){
    			var typeId =$("#typeId3").val();
    			if(typeId==null || typeId==""){
    				alert("请选择产品");
    				return false;
    			}
    			var obj1=document.getElementById("u3");
    			obj1.options.selected=true;
    			var markedPro="";
    			for(var i=0;i<=obj1.options.length-1;i++){
    				markedPro+=obj1.options[i].value+",";
    			}
    			
    			var obj2=document.getElementById("u1");
    			obj2.options.selected=true;
    			var unMarkePro="";
    			for(var j=0;j<=obj2.options.length-1;j++){
    				unMarkePro+=obj2.options[j].value+",";
    			}
    		    $.ajax({
    		        type: "POST",                                                                 
    		        url:"<%=path%>/proMarked/saveMarkedPro.html?&typeId="+typeId+"&markedPro="+markedPro+"&unMarkePro="+unMarkePro+"&random="+Math.random(),
    		        success: function(msg){ 
    		        	if(msg=="success"){
    		        	alert("保存关联产品成功");
    		        	dg.curWin.location.reload();
    		        	dg.cancel();
    		        	}else{
    		        		alert("保存关联产品失败");
    		        	}
    		        	obj1.options.selected=false;
    		        	obj2.options.selected=false;
    		        	
    		          }
    		    }); 
    		} 
    		
    		$(document).ready(function(){
    			$(".main_info:even").addClass("main_table_even");
    		});
    		
		</script>
</body>
</html>
