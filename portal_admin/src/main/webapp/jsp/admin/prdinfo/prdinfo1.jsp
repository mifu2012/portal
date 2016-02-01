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
<div style=" position: absolute;right:10px;top:10px;">提示：<font color='red' size='2'>请先选择产品，再修改产品关联指标</font></div>
        <div class="common-box">
            <div class="left-box">
                <p>
                    <input type="text" id="J-searchval-f" style=" width:180px; height:20px; float:left; margin-right: 10px" onkeyup="butOnKeyUp()" onkeydown="javascript:butOnClick();"/>
                    <input type="button" id="J-search-f" value="搜索" style="float: left; width: 60px; height: 24px;" />
                </p>
                <p>
                    <span style=" width:160px; height:20px; float: left;margin-right:10px; display:inline-block;"><b>产品列表</b></span>
                	<input type="button" value="新增" style="position: absolute; margin-left: 24px; width: 60px; height: 24px;" onclick="javascript:addPrd();" />
                </p>
                <ul style="border:1px solid #999; width:280px; height:300px; overflow-x:hidden;overflow-y:auto; font-size:14px;height: 400px;margin-left: 10px" id="J-searchul-f">
					  <c:forEach items="${prdList}" var="item" varStatus="vel">
							<li id="li_prdInfo_${item.productId}">
								<table border="1" width="262">
								   <tr>
									 <td colspan="3" ><a href='javascript:void(0);' onclick="showInfo('${item.productId}')"><font id="${item.productId}">【${item.productMarkDesc}】&nbsp;${item.productId} - ${item.productName}</font></a></td>
								   </tr>
								   <tr>
									 <td width="70%">&nbsp;</td>
									 <td align="center"><a href='javascript:void(0);' onclick="javascript:editPrd('${item.productId }');">修改</a></td>
									 <td align="center"><a href='javascript:void(0);' onclick="javascript:delPrd('${item.productId }')">删除</a></td>
								   </tr>
								</table>
							</li>
						</c:forEach>
				    </ul>
            </div>
		<div class="fn-clear">
		<input type="hidden" id="productId3" style="display: none;" />
			<div class="left-box" style="width: 283px; float: left;position: absolute;left:330px">
			<p >
				<input type="text" id="kpiCode" name="kpiCode" style=" width:180px; height:20px; float:left; margin-right: 10px" value="指标列表未显示完全，请选择搜索"  onfocus="clearValue()"   onkeydown="javascript:butOnClick2();"/> 
				<input type="button" style=" float:left; width:60px; height:24px; margin-right: 10px" value="搜索"  id="J-search-f2">
			</p>
			<p>
                 <span style=" width:160px; height:20px; float:left; display:inline-block;" for="u1" ><b>未关联指标</b></span>
                 <input style=" float:left; width:60px; height:24px; margin-left: 33px;" type="button" value="保存" onClick="saveAll();" />
            </p>
				  <select id="u1" 
					name="kpiUnRelInfo" style="width: 282px; height: 400px;overflow: auto; font-size: 13px;" size="2" multiple="true"
					ondblclick="moveTo('u1','u3')">
				</select>
				
			</div>
			<div id='loading_wait_Div' name='loading_wait_div' style='border:0px solid red;position: absolute; left: 420px; top: 30%;display:none' ><img src='<%=request.getContextPath()%>/images/loading.gif' title='数据正在加载中...'></div>
			  <div class="sel-act" style="position: absolute;left:630px;top:250px">
			<div class="row">
				<span class="ui-round-btn"><input type="button"
					class="ui-round-btn-text" onClick="moveTo('u1','u3')"
					value=" &gt; " /> </span>
			</div>
			<div class="row" style="margin-top: 50px">
				<span class="ui-round-btn"><input type="button"
					class="ui-round-btn-text" onClick="moveTo('u3','u1')"
					value=" &lt; " /> </span>
			</div>
		</div>
			
			<div class="left-box"
			style="width: 283px; position: absolute;left:650px;top: 55px">
				<p>
                    <span for="u3" style=" width:160px; height:20px; float:left; display:inline-block;"><b>已关联指标</b></span>
                </p>
				<select name="kpiRelInfo"
					size="2" id="u3" style="width: 282px; height: 400px;overflow: auto; font-size: 13px;" multiple="true" ondblclick="moveTo('u3','u1')">
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
    function showValue(){
    	$("#kpiCode").val("指标列表未显示完全，请选择搜索");
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
    
    function creatGroup(selectObj,groupVal){
		var group=document.createElement("optgroup");
		group.id="optGroup_"+groupVal;
		if(groupVal==1){
    		group.label = "日指标";
    	}
    	if(groupVal==3){
    		group.label = "月指标";
    	}
    	
    	selectObj.appendChild(group);
    	return group;
	}
            //指标搜索
        function showInfo(val){
        	document.body.disabled=true;
        	document.getElementById("loading_wait_Div").style.display="";
        	//设置隐藏的通用指标值
        	$("#productId3").val(val);
        	$("#kpiCode").val("指标列表未显示完全，请选择搜索");
        	var kpiCode="";
        	var baseProductId="";
        	
        	
        	//设置列表点击后背景颜色
        	$("#J-searchul-f li a font").removeClass("myactive");
    		$("#"+val).addClass("myactive");
    		//设置已关联指标数据
        	$.ajax({
	        type: "POST",                                                                 
	        url:encodeURI("<%=basePath%>prdinfo1/showRelateKpi.html?productId="+val+"&random="+Math.random()),
	        success: function(msg){ 
	        	var msg=eval(msg);
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
	            	
	            	option.setAttribute("optGroupId","optGroup_"+msg[i].kpiType);
	            	option.setAttribute("optGroupVal",msg[i].kpiType);
	            	option.title=msg[i].kpiName;
	            	document.getElementById("u3").add(option);
        	   }  
	           
	       }
	   }); 
        	
        	//设置未关联指标列表数据
        	$.ajax({
	        type: "POST",                                                                 
	        url:encodeURI("<%=basePath%>prdinfo1/showKpi.html?productId="+val+"&kpiCode="+kpiCode+"&baseProductId="+baseProductId+"&random="+Math.random()),
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
	            for(var i=0;i<msg.length;i++){
	            	
	            	var group=document.getElementById("optGroup_"+msg[i].kpiType);
                    if(group == null){
	            		
	            		group = creatGroup(u1,msg[i].kpiType);
	            	}
                    var value=msg[i].kpiCode;
	            	var text=msg[i].kpiCode+"-"+msg[i].kpiName;
	            	var option=new Option(text,value);
	            	
	            	option.innerHTML= text;
	            	option.setAttribute("optGroupId",group.id);
	            	option.setAttribute("optGroupVal",msg[i].kpiType);
	            	option.title=msg[i].kpiName;
	            	group.appendChild(option);
	            	
        	   }  
	            document.getElementById("loading_wait_Div").style.display="none";
	            document.body.disabled=false;
	       }
	   }); 
        	
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
    				for(var i=obj1.options.length-1;i>=0;i--)
    				{
    					var curr=obj1.options[i];
    					if(curr.selected)
    					{
    						if(obj1.id=='u3'){
    							var v1=curr.value;
    							var txt1=curr.text;
    							var op1=new Option(txt1,v1);
    							op1.title = curr.title;
    							op1.setAttribute("optGroupVal",curr.getAttribute("optGroupVal"));//optGroupVal;
    							op1.setAttribute("optGroupId",curr.getAttribute("optGroupId"));//optGroupId;
    							var groupId = curr.getAttribute("optGroupId");
    							var group = document.getElementById(groupId);
    							if(group == null){
    								var groupVal = curr.getAttribute("optGroupVal");
    								group = creatGroup(document.getElementById("u1"),groupVal);
    							}
    							if(group!=null){
    								group.appendChild(op1);
    							}else
                                {
                                	document.getElementById("u3").appendChild(op1);
                                }
    							op1.innerHTML=txt1;
    							
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
    							op1.title = curr.title;
    							op1.setAttribute("optGroupVal",curr.getAttribute("optGroupVal"));//optGroupVal;
    							op1.setAttribute("optGroupId",curr.getAttribute("optGroupId"));//optGroupId;
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
    		//未关联指标搜索 J-search-f2
    		 $("#J-search-f2").click(function () {
    			var baseProductId = $.trim($("#productId3").val());
            	if(baseProductId == ""){
            		alert('请先选择产品!');
            		return false;
            	}
            	document.getElementById("loading_wait_Div").style.display="";
    			var kpiCode =$.trim($("#kpiCode").val());
    			var obj1=document.getElementById("u3");
    			obj1.options.selected=true;
    			var linkedKpiCode="";
    			for(var i=0;i<=obj1.options.length-1;i++){
    				linkedKpiCode=linkedKpiCode+","+obj1.options[i].value;
    			}	
    			obj1.options.selected=false;
    			$.ajax({
    		        type: "POST",                                                                 
    		        url:encodeURI("<%=path%>/prdinfo1/showKpi.html?&kpiCode="+kpiCode+"&baseProductId="+baseProductId+"&linkedKpiCode="+linkedKpiCode+"&random="+Math.random()),
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
    		            for(var i=0;i<msg.length;i++){
    		            	var group=document.getElementById("optGroup_"+msg[i].kpiType);
    		            	if(group==null)
    		            	{
    		            		group = creatGroup(u1,msg[i].kpiType);
    		            	}
    		            	var value=msg[i].kpiCode;
    		            	var text=msg[i].kpiCode+"-"+msg[i].kpiName;
    		            	var option=new Option(text,value);
    		            	option.title=msg[i].kpiName;
    		            	
    		            	try
    		            	{
    		            		option.innerHTML=text;
    		            	}catch(e)
    		            	{
    		            	}
    		            	//GROUP_ID
    		            	option.setAttribute("optGroupId",group.id);
    		            	option.setAttribute("optGroupVal",msg[i].kpiType);
    		             	
    		            	group.appendChild(option);
    	        	   }  
    		            document.getElementById("loading_wait_Div").style.display="none";
    		       }
    		   }); 
    		 });
    	
    		function selectAll(){
    			var obj1=document.getElementById("u3");
    	 		var productId = document.getElementById("productId3");
    	 		var url = "prdinfo1/updateKPIRelativeByKPICode"+obj1+".html";
    			for(i=0;i<=obj1.options.length-1;i++){
    				obj1.options[i].selected=true;
    			}		
    	 		$.get(url,function(data){
    	 			if(data=="success"){
    	 				document.location.href="showRelKpi";
    	 			}
    	 		});
    			
    		}
    		//保存关联指标
    		function saveAll(){
    			var productId =$("#productId3").val();
    			if(productId==null || productId==""){
    				alert("请选择产品");
    				return false;
    			}
    			var obj1=document.getElementById("u3");
    			obj1.options.selected=true;
    			var kpiCode="";
    			for(var i=0;i<=obj1.options.length-1;i++){
    				kpiCode=kpiCode+","+obj1.options[i].value;
    			}	
    		    $.ajax({
    		        type: "POST",                                                                 
    		        url:"<%=basePath%>/prdinfo1/update.html?productId="+productId+"&kpiCode="+kpiCode,
    		        success: function(msg){ 
    		        	if(msg=="success"){
    		        	alert("保存关联指标成功");
    		        	}else{
    		        		alert("保存关联指标失败");
    		        	}
    		        	obj1.options.selected=false;
    		          }
    		    }); 
    		} 
    		$(document).ready(function(){
    			$(".main_info:even").addClass("main_table_even");
    		});
    		
    		function addPrd(){
    			var dg = new $.dialog({
    				title:'新增产品信息',
    				id:'prdinfo_new',
    				width:600,
    				height:300,
    				iconTitle:false,
    				cover:true,
    				maxBtn:false,
    				xButton:true,
    				resize:true,
    				page:'<%=basePath%>prdinfo1/add.html'
    				});
        		dg.ShowDialog();
    		}
    		
    		function editPrd(productId){
    			var dg = new $.dialog({
    				title:'修改产品信息',
    				id:'prdinfo_edit',
    				width:600,
    				height:300,
    				iconTitle:false,
    				cover:true,
    				maxBtn:false,
    				resize:true,
    				page:'<%=basePath%>prdinfo1/edit'+productId+'.html'
    				});
        		dg.ShowDialog();
    		}
    		//删除产品
    		function delPrd(productId){
    			if(confirm("确定要删除该记录？")){
    				$.ajax({
        		        type: "POST",                                                                 
        		        url:encodeURI("<%=basePath%>prdinfo1/delete"+productId+".html"),
        		        success: function(msg){ 
        		        if(msg=="success"){
        		        	alert("删除成功")
        		        	  var oDl = document.getElementById("li_prdInfo_"+productId);
        		        	  oDl.parentNode.removeChild(oDl);
     
        		        }else{
        		        	alert("删除失败");
        		        	return false;
        		        }
        		      }
        		   }); 
    			}
    			}
	
	
		</script>
</body>
</html>
