<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String moduleId=request.getParameter("moduleId");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Example</title>
<link href="<%=basePath%>css/css.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>js/jquery-1.5.1.min.js" type="text/javascript"></script>
<script type="text/javascript"
	src="<%=basePath%>js/datePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="<%=basePath%>js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>
	
<script language="javascript">  
function   GetTop(){  
	  var   i;  
	  i=document.getElementById("u3").selectedIndex;  
	  if(i>0){  
	  Temp_Text=$($("#u3 option"))[i].text;  
	  Temp_ID=$($("#u3 option"))[i].value;  
	  for(j=i;j>0;j--){  
		  $($("#u3 option"))[j].text=$($("#u3 option"))[j-1].text;  
		  $($("#u3 option"))[j].value=$($("#u3 option"))[j-1].value;  
	  }  
	  $($("#u3 option"))[0].value=Temp_ID;  
	  $($("#u3 option"))[0].text=Temp_Text;  
	  document.getElementById("u3").selectedIndex=0;  
	  }  
	  } 
	  
  function   GetUp(){  
  var   i,j;  
  i=document.getElementById("u3").selectedIndex; 
  j=i-1  
  if(i>0){  
  Temp_Text=$($("#u3 option"))[j].text;  
  Temp_ID=$($("#u3 option"))[j].value;  
  $($("#u3 option"))[j].text=$($("#u3 option"))[i].text;  
  $($("#u3 option"))[j].value=$($("#u3 option"))[i].value;  
   
  $($("#u3 option"))[i].text=Temp_Text;  
  $($("#u3 option"))[i].value=Temp_ID;  
   
  document.getElementById("u3").selectedIndex=j;  
  }  
  }  
 
  function   GetDown(){  
  var   i,j;  
  i=document.getElementById("u3").selectedIndex;  
  if   (i!=document.getElementById("u3").length-1){  
  j=i+1;  
  if(i<document.getElementById("u3").length){  
  Temp_Text=$($("#u3 option"))[j].text;  
  Temp_ID=$($("#u3 option"))[j].value;  
   
  $($("#u3 option"))[j].text=$($("#u3 option"))[i].text;  
  $($("#u3 option"))[j].value=$($("#u3 option"))[i].value;  
   
  $($("#u3 option"))[i].text=Temp_Text;  
  $($("#u3 option"))[i].value=Temp_ID;  
   
  document.getElementById("u3").selectedIndex=j;  
   }  
  }  
  } 
  function   GetTail(){  
	  var   i,j;  
	  i=document.getElementById("u3").selectedIndex;  
	  j=document.getElementById("u3").length-1  
	  if(i<j){  
	  Temp_Text= $($("#u3 option"))[i].text;  
	  Temp_ID= $($("#u3 option"))[i].value;  
	  for(k=i+1;k<=j;k++){  
		  $($("#u3 option"))[k-1].text=$($("#u3 option"))[k].text;  
		  $($("#u3 option"))[k-1].value=$($("#u3 option"))[k].value;  
	  }  
	   
	  $($("#u3 option"))[j].text=Temp_Text;  
	  $($("#u3 option"))[j].value=Temp_ID;  
	   
	  document.getElementById("u3").selectedIndex=j;  
	  }  
	  } 
  </script>
</head>
<body>
<body class="indexbody"
	style="height: 100%; width: 100%; overflow: hidden;">
	<div class="common-box">
		<div class="fn-clear">
			<div class="left-box" style="width: 283px; float: left;">
				<p>
					<input type="text" id="J-searchval-f"
						style="width: 180px; height: 20px; float: left; margin-right: 10px"
						onkeydown="butOnClick()" onfocus="clearValue()" /> <input
						type="button"
						style="float: left; width: 60px; height: 24px; margin-right: 10px"
						value="搜索" id="J-search-f1">
				</p>
				<p>
					<span
						style="width: 160px; height: 20px; float: left; display: inline-block;"
						for="u1"><b>待选通用指标列表</b> </span> <input
						style="float: left; width: 60px; height: 24px; margin-left: 33px;display:none;"
						type="button" value="保存" onClick="saveAll();" />
				</p>
				<select id="u1" name="kpiUnRelInfo"
					style="width: 250px; height: 260px; overflow: auto; font-size: 13px;"
					size="2" multiple="true" ondblclick="moveTo('u1','u3')">
					<c:forEach items="${tigerNotShowList}" var="tigerNotShow">
						<option value="${tigerNotShow.comKpiCode}"
							title="${tigerNotShow.comKpiName}">${tigerNotShow.comKpiCode}
							- ${tigerNotShow.comKpiName}</option>
					</c:forEach>
				</select>
			</div>
			<div class="sel-act"
				style="position: absolute; left: 278px;top:160px">
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
				<div class="row2">
					<span class="ui-round-btn"><input type="button"
						class="ui-round-btn-text" onClick="GetTop()"
						value="置顶" /> </span>
				</div>
				<div class="row2">
					<span class="ui-round-btn"><input type="button"
						class="ui-round-btn-text" onClick="GetUp()"
						value="上移" /> </span>
				</div>
				<div class="row2">
					<span class="ui-round-btn"><input type="button"
						class="ui-round-btn-text" onClick="GetDown()"
						value="下移" /> </span>
				</div>
				<div class="row2">
					<span class="ui-round-btn"><input type="button"
						class="ui-round-btn-text" onClick="GetTail()"
						value="置底" /> </span>
				</div>
			</div>
			<div class="left-box"
				style="width: 250px; position: absolute; left: 297px; top: 55px">
				<p>
					<span for="u3"
						style="width: 160px; height: 20px; float: left; display: inline-block;"><b>产品排行关联指标</b>
					</span>
				</p>
				<select name="u3" size="2" id="u3"
					style="width: 250px; height: 260px; overflow: auto; font-size: 13px;"
					multiple="true" ondblclick="moveTo('u3','u1')">
					<c:forEach items="${tigerShowList}" var="tigerShow">
						<option value="${tigerShow.comKpiCode}"
							title="${tigerShow.comKpiName}">${tigerShow.comKpiCode}
							- ${tigerShow.comKpiName}</option>
					</c:forEach>
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
     var button = document.getElementById("J-search-f1"); //bsubmit 为botton按钮的id 
     button.click(); 
     return false; 
     } 
   }
    //清空按钮
    function clearValue(){
    	$("#J-searchval-f").val("");
    }
    
          //通用指标搜索
       $("#J-search-f1").click(function () {
    	   var comKpiCode=$.trim($("#J-searchval-f").val());
    	   //获取已显示的指标
    	   var obj1=document.getElementById("u3");
   		    obj1.options.selected=true;
   		   var linkedKpiCode="";
   		    for(var i=0;i<=obj1.options.length-1;i++){
   			linkedKpiCode=linkedKpiCode+","+obj1.options[i].value;
   		     }
    			$.ajax({
    		        type: "POST",                                                                 
    		        url:encodeURI("<%=path%>/comkpiinfo1/searchTigerComKpiCodes.html?&comKpiCode="+comKpiCode+"&linkedKpiCode="+linkedKpiCode+"&random="+Math.random()),
    		        success: function(msg){ 
    		        	var msg=eval(msg);
    		        	document.getElementById("u1").length=0;
    		            for(var i=0;i<msg.length;i++){
    		            	var value=msg[i].comKpiCode;
    		            	var text=msg[i].comKpiCode+"-"+msg[i].comKpiName;
    		            	var option=new Option(text,value);
    		            	option.title=msg[i].comKpiName;
    		            	document.getElementById("u1").options.add(option);
    	        	   }  
    		       }
    		   }); 
            }); 
            
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
    		//保存关联指标
    		var dg = frameElement.lhgDG;
    		function saveAll(){
    			  var obj1=document.getElementById("u3");
    			  var obj2=document.getElementById("u1");
    	   		    obj1.options.selected=true;
    	   		    obj2.options.selected=true;
    	   		   var showKpiCode="";
    	   		   var notShowKpiCode="";
    	   		    for(var i=0;i<=obj1.options.length-1;i++){
    	   			showKpiCode=showKpiCode+","+obj1.options[i].value;
    	   		     }
    	   		    for(var i=0;i<=obj2.options.length-1;i++){
    	   		     notShowKpiCode=notShowKpiCode+","+obj2.options[i].value;
    	   		     }
    	   			obj1.options.selected = false;
    	   			obj2.options.selected = false;
    		    $.ajax({
    		        type: "POST",                                                                 
    		        url:"<%=basePath%>/comkpiinfo1/updateShowTigerComKpiCodes.html?showKpiCode="+showKpiCode+"&moduleId=<%=moduleId%>&random="+Math.random(),
				success : function(msg) {
					if (msg == "success") {
						alert("保存成功");
						dg.cancel();
					} else {
						alert("保存失败");
					}
				}
			});
		}
		$(document).ready(function() {
			dg = frameElement.lhgDG;
			dg.addBtn('ok', '确定', function() {
                  var obj1=document.getElementById("u3");
    			  var obj2=document.getElementById("u1");
    	   		      obj1.options.selected=true;
    	   		      obj2.options.selected=true;
    	   		   var showKpiCode="";
    	   		   var notShowKpiCode="";
    	   		   for(var i=0;i<=obj1.options.length-1;i++){
    	   			   showKpiCode=showKpiCode+","+obj1.options[i].value;
    	   		   }
    	   		   for(var i=0;i<=obj2.options.length-1;i++){
    	   		     notShowKpiCode=notShowKpiCode+","+obj2.options[i].value;
    	   		   }
				   if(showKpiCode==null||showKpiCode.length==0)
				   {
						 alert('请选择指标');
						 return;
				   }
				   if(!confirm("确定保存吗")) return;
    	   		   obj1.options.selected = false;
    	   		   obj2.options.selected = false;
				   $.ajax({
						type: "POST",                                                                 
						url:"<%=basePath%>/comkpiinfo1/updateShowTigerComKpiCodes.html?showKpiCode="+showKpiCode+"&moduleId=<%=moduleId%>&random="+Math.random(),
					success : function(msg) {
						if (msg == "success") {
							alert("保存成功");
							dg.cancel();
						} else {
							alert("保存失败");
						}
					}
				  });
			});
		});
	</script>
</body>
</html>
