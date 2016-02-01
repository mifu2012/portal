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
<title>绑定指标</title>
<link charset="utf-8" rel="stylesheet"
	href="<%=basePath%>css/alice1.css" />
<link type="text/css" charset="utf-8" rel="stylesheet" href="<%=basePath%>css/css.css" />
<script type="text/javascript" src="../js/jquery-1.5.1.min.js"></script>
<script type="text/javascript">
		var dg;
		$(document).ready(function() {
			dg = frameElement.lhgDG;
			dg.addBtn('ok', '保存', function() {
				var obj1=document.getElementById("u3");
				for(var i=0;i<=obj1.options.length-1;i++){
					obj1.options[i].selected=true;
				}
				document.getElementById("form-wrapper").action="updateKPIRelativeByKPICode.html";
				$("#form-wrapper").submit();
			});
			//
			<c:forEach items="${kpiRelList}" var="kpiRelInfo">
			   var optObj=document.getElementById("unrel_${kpiRelInfo.kpiCode}");
			   if(optObj!=null)
			   {
				   document.getElementById("u1").removeChild(optObj); 
			   }
			</c:forEach>
		});
		
		function success() {
			alert("绑定指标保存成功！");
			if (dg.curWin.document.forms[0]) {
				dg.curWin.document.forms[0].action = dg.curWin.location + "";
				dg.curWin.document.forms[0].submit();
			} else {
				dg.curWin.location.reload();
			}
			dg.cancel();
		}

		function failed() {
			alert("操作失败！");
		}
	</script>
<script type="text/javascript">
araleConfig = {
	combo_host: 'https://static.alipay.com',
	combo_path: '/min/?b=javascript/arale_v101&f=',
	corex: true
};
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
				for(var i=obj1.options.length-1;i>=0;i--)
				{
					var curr=obj1.options[i];
					if(curr.selected)
					{
						if(obj1.id=='u3'){
							var v1=curr.value;
							var txt1=curr.text;
							groupId=curr.getAttribute("groupId");
							var group = document.getElementById(groupId);
							var titleValue = curr.title;
							var op1=new Option(txt1,v1);	
							op1.title = titleValue;
							if(group==null)
							{	
			 					group=document.createElement("optgroup");
								group.id= curr.getAttribute("groupId");
//			  					group = creatGroup(obj2,groupId);
								obj2.appendChild(group);
							}
							if(group!=null)
							{
								group.appendChild(op1);
							}else
							{
								document.getElementById("u1").appendChild(op1);
							}
							
							try
				            {
			                    op1.innerHTML=txt1;
				            }catch(e)
				            {
				            }
							curr.parentNode.removeChild(curr);
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
							var titleValue = curr.title;
							var op1=new Option(txt1,v1);
							op1.title = titleValue;
							
							 try
					            {
					            	option.innerHTML=text;
					            }catch(e)
					            {
					            }
							op1.setAttribute("groupId", curr.parentNode.id);
							obj1.options[i].parentNode.removeChild(obj1.options[i]);
							obj2.options[obj2.options.length]=op1;
								//obj1.options[i]=null;
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
</script>
<script type="text/javascript">
var Sys = {}; 
var ua = navigator.userAgent.toLowerCase(); 
if (window.ActiveXObject) 
    Sys.ie = ua.match(/msie ([\d.]+)/)[1] 
else if (document.getBoxObjectFor) 
    Sys.firefox = ua.match(/firefox\/([\d.]+)/)[1] 
else if (window.MessageEvent && !document.getBoxObjectFor) 
    Sys.chrome = ua.match(/chrome\/([\d.]+)/)[1] 
else if (window.opera) 
    Sys.opera = ua.match(/opera.([\d.]+)/)[1] 
else if (window.openDatabase) 
    Sys.safari = ua.match(/version\/([\d.]+)/)[1]; 
 function queryByKpiCode()
 {
	 //IE浏览器
	 if(Sys.ie)
	 {
		if (event.keyCode == 13) { 
			   /*
			   document.getElementById("J-search-f").focus();
			   document.getElementById("J-search-f").click();
			   */
			selectKpiCodeOfKpiName();
		}
	 }else
	 {
		    var val = jQuery.trim($("#SelectContentByKpiCode").val());
		    val=val.toUpperCase();//字符转为大写
			var lilist = $("#u1>option");
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

 }
function selectKpiCodeOfKpiName() {
	     document.body.disabled=true;
		 var val = jQuery.trim($("#SelectContentByKpiCode").val());
		     val=val.toUpperCase();//字符转为大写
		 alert(val);
		//加载所有OPTION
		var u1=document.getElementById("u1");
			u1.length=0;
		var optClone=document.getElementById("kpiUnRelInfo_clone");
		alert(optClone.options.length);
		var totalCount=0;
		for(var i=0;i<optClone.options.length;i++)
		{
			var optObj=optClone.options[i];
			if(totalCount>=100)
			{
				 break;
			}
			totalCount++;
			if(val.length>0&&optObj.text.indexOf(val)>=0)
			{
				u1.add(new Option(optObj.text,optObj.value));	 
			}else if(val==0)
			{
				u1.add(new Option(optObj.text,optObj.value));
			}
	   }
	   document.body.disabled=false;
	   document.getElementById("SelectContentByKpiCode").focus();
			 /*
			var lilist = $("#u1>option");
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
			*/
        }
        /*
	$(function () {
        $("#J-search-f").click(function () {
            var val = jQuery.trim($("#SelectContentByKpiCode").val());
                val=val.toUpperCase();//字符转为大写
            var lilist = $("#u1>option");
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
	});
        */ //updateKPIRelativeByKPICode.html
        
        
        function searchKpiCodes(){
        	document.body.disabled=true;
        	document.getElementById("loading_wait_Div").style.display="";
        	//var eventId=$("#eventIdByHidden").val();
        	var kpiCode=$.trim($("#SelectContentByKpiCode").val());
        	var obj1=document.getElementById("u3");
    		obj1.options.selected=true;
    		var linkedKpiCode="";
    		for(var i=0;i<=obj1.options.length-1;i++){
    			linkedKpiCode=linkedKpiCode+","+obj1.options[i].value;
    		}	
    		obj1.options.selected=false;
        	  $.ajax({
  		        type: "POST",                                                                 
  		        url:encodeURI("<%=basePath%>/BigEventRelKpi/searchEventRelKpi.html?keyCode="+kpiCode+"&linkedKpiCode="+linkedKpiCode+"&random="+Math.random()+"&kpiType=${kpiType}" ),
  		        success: function(data){ 
  		        	var msg = eval(data);
  		        	var p1 = document.getElementById("0_1");
  		        	    p1.innerHTML="";
  		        	var p2 = document.getElementById("0_3"); 
  		        	    p2.innerHTML="";
  		        	var p3 = document.getElementById("1_1"); 
		        	    p3.innerHTML="";
		        	var p4 = document.getElementById("1_3"); 
  		        	    p4.innerHTML="";
           for(var i=0;i<msg.length;i++){
  		            	var value=msg[i].kpiCode;
  		            	var text=msg[i].kpiCode+"--"+msg[i].kpiName;
  		            	
  		             	var option=new Option(text,value);
		            	option.title=msg[i].kpiName;
		            	option.innerHTML=text;
		            	if(msg[i].isOverall == '0'&& msg[i].kpiType == '1'){
		            		p1.appendChild(option);
		            	}
		            	if(msg[i].isOverall == '0'&& msg[i].kpiType == '3'){
		            		p2.appendChild(option);
		            	}
		            	if(msg[i].isOverall == '1'&& msg[i].kpiType == '1'){
		            		p3.appendChild(option);
		            	}
		            	if(msg[i].isOverall == '1'&& msg[i].kpiType == '3'){
		            		p4.appendChild(option);
		            	}
	        	   }  
  		          document.getElementById("loading_wait_Div").style.display="none";
    		        document.body.disabled=false;
  		       }
  		  }); 
        }
        
      //回车搜索
		function butOnClick() {
			if (event.keyCode == 13) {
				searchKpiCodes();
				return false;
			}
		}
      function changeType(type){
    	  location.href= '<%=basePath%>/BigEventRelKpi/showEventRelKpi${eventId}.html?kpiType='+type.value;
      }
        </script>
</head>
<body>
<!-- 加载图片 -->
<div id='loading_wait_Div' name='loading_wait_div' style='border:0px solid red;position: absolute; left: 100px; top: 200px;display: none' ><img src='<%=request.getContextPath()%>/images/loading.gif' title='数据正在加载中...'></div>
	<div style="width:100%;height: 100%">
	<h3 class="cus-title">事件标题：${title}</h3>
	<div style="padding-left: 20px;">
	<select id="kpiType" onchange="changeType(this);" name="kpiType" style="width: 50px;height: 20px;display:none;">
	  <option value="1" <c:if test="${kpiType eq 1 }">selected="selected"</c:if> >日</option>
	  <option value="3" <c:if test="${kpiType eq 3 }">selected="selected"</c:if> >月</option>
	</select>
	&nbsp;<input type="text" id="SelectContentByKpiCode" onkeydown="javascript:butOnClick();"
					class="ui-input ui-input-mini" style="width: 140px"> <span
					class="ui-round-btn ui-round-btn-mini"><input type="button" id="J-search-f"
					class="ui-round-btn-text" value="搜索" onclick="searchKpiCodes();"
				   ></span>
	</div>
	<form id="form-wrapper" class="form-wrapper" name="form2" method="post"
		action="" target="result">
		
		<input id="eventIdByHidden" type="hidden" name="eventId" value="${eventId}" style="display: none"/>
		<input id="eventTitleByHidden" type="hidden" name="title" value="${title}" style="display: none"/>
		<div class="fn-clear" style="padding-top: 3px;" >
			<div class="sel-list">
			
			
				<label for="u1">指标列表：</label>
				<select  id="kpiUnRelInfo_clone" name="kpiUnRelInfo_clone" style="display:none; ">					
					<c:forEach items="${kpiUnRelList}" var="kpiUnRelInfo">
					<option title="${kpiUnRelInfo.kpiName}"  value="${kpiUnRelInfo.kpiCode}">${kpiUnRelInfo.kpiCode} - ${kpiUnRelInfo.kpiName}</option>
                    </c:forEach>
				</select>
				<select  id="u1" name="kpiUnRelInfo"  size="2" multiple="true" ondblclick="moveTo('u1','u3')" style="height: 260px">	
				    <optgroup label="产品-日" id="0_1">
				     <c:forEach items="${kpiUnRelList}" var="kpiUnRelInfo">
				     <c:if test="${kpiUnRelInfo.isOverall == 0 && kpiUnRelInfo.kpiType==1}">
					<option title="${kpiUnRelInfo.kpiName}"  value="${kpiUnRelInfo.kpiCode}" id="unrel_${kpiUnRelInfo.kpiCode}">${kpiUnRelInfo.kpiCode} -- ${kpiUnRelInfo.kpiName}
					</option>
                    </c:if>
                    </c:forEach>
				    </optgroup>	
				    <optgroup label="产品-月" id="0_3">
				    <c:forEach items="${kpiUnRelList}" var="kpiUnRelInfo1">
				    <c:if test="${kpiUnRelInfo1.isOverall == 0 && kpiUnRelInfo1.kpiType==3 }">
					<option title="${kpiUnRelInfo1.kpiName}"  value="${kpiUnRelInfo1.kpiCode}" id="unrel_${kpiUnRelInfo1.kpiCode}">${kpiUnRelInfo1.kpiCode} -- ${kpiUnRelInfo1.kpiName}
                    </option>
                    </c:if>
                    </c:forEach>
				    </optgroup>
				    <optgroup label="大盘-日" id="1_1">
				    <c:forEach items="${kpiUnRelList}" var="kpiUnRelInfo2">
				    <c:if test="${kpiUnRelInfo2.isOverall == 1 && kpiUnRelInfo2.kpiType==1 }">
					<option title="${kpiUnRelInfo2.kpiName}"  value="${kpiUnRelInfo2.kpiCode}" id="unrel_${kpiUnRelInfo2.kpiCode}">${kpiUnRelInfo2.kpiCode} -- ${kpiUnRelInfo2.kpiName}
                    </option>
                    </c:if>
                    </c:forEach>
				    </optgroup>	
				    <optgroup label="大盘-月" id="1_3">
				    <c:forEach items="${kpiUnRelList}" var="kpiUnRelInfo3">
				    <c:if test="${kpiUnRelInfo3.isOverall == 1 && kpiUnRelInfo3.kpiType==3 }">
					<option title="${kpiUnRelInfo3.kpiName}"  value="${kpiUnRelInfo3.kpiCode}" id="unrel_${kpiUnRelInfo3.kpiCode}">${kpiUnRelInfo3.kpiCode} -- ${kpiUnRelInfo3.kpiName}
                    </option>
                    </c:if>
                    </c:forEach>
				    </optgroup>				
				</select>
				<script type="text/javascript"></script>
			</div>
			<div class="sel-act" style="position: absolute;left: 280px;margin-top: 50px">
				 <div class="row"><span class="ui-round-btn"><input type="button" class="ui-round-btn-text" onClick="moveTo('u1','u3')" value=" &gt; " /></span></div>
				 <div class="row"><span class="ui-round-btn"><input type="button" class="ui-round-btn-text" onClick="moveTo('u3','u1')" value=" &lt; " /></span></div>
			</div>
			<div class="sel-list sel-list-right" style="position: absolute;left:300px">
				<label for="u3">关联指标：</label>
				<select name="kpiRelInfo"  size="2" id="u3"  multiple="true" ondblclick="moveTo('u3','u1')" style="height: 260px" >
					<c:forEach items="${kpiRelList}" var="kpiRelInfo">
					<option title="${kpiRelInfo.kpiName}" value="${kpiRelInfo.kpiCode}" id='rel_${kpiRelInfo.kpiCode}'>${kpiRelInfo.kpiCode} -- ${kpiRelInfo.kpiName}
					(<c:choose> <c:when test="${kpiRelInfo.kpiType =='1'}">日</c:when>
					 <c:otherwise>月</c:otherwise>
					</c:choose>)
					</option>
					<script language='javascript'>
							  document.getElementById("rel_${kpiRelInfo.kpiCode}").setAttribute("groupId", "${kpiRelInfo.isOverall}_${kpiRelInfo.kpiType}");
					</script>
					 </c:forEach>
				</select>
				
			</div>
		</div>
	</form>	
	</div>
	<iframe name="result" id="result" src="about:blank" frameborder="0" width="0" height="0"></iframe>
</body>
</html>