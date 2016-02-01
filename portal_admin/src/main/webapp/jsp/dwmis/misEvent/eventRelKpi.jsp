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
	href="<%=basePath%>css/alice1.css" />
<link type="text/css" charset="utf-8" rel="stylesheet"
	href="<%=basePath%>css/css.css" />
<script type="text/javascript" src="<%=basePath%>js/jquery-1.5.1.min.js"></script>
<script type="text/javascript">
var dg;
$(document).ready(function() 
{
	dg = frameElement.lhgDG;
	dg.addBtn('ok', '保存', function() 
	{
		var obj1=document.getElementById("u3");
		for(var i=0;i<=obj1.options.length-1;i++)
		{
			obj1.options[i].selected=true;
		}
		document.getElementById("form-wrapper").action="updateKPIRelativeByKPICode.html";
		$("#form-wrapper").submit();
	});
});
		
function success() 
{
	alert("绑定指标保存成功");
	if (dg.curWin.document.forms[0]) 
	{
		dg.curWin.document.forms[0].action = dg.curWin.location + "";
		dg.curWin.document.forms[0].submit();
	} else 
	{
		dg.curWin.location.reload();
	}
	dg.cancel();
}

function failed() 
{
	alert("保存失败");
}

</script>
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
			if(obj1.id=='u3')
			{
				var v1=curr.value;
				var txt1=curr.text;
				groupId=curr.getAttribute("groupId");
				var group=document.getElementById(groupId);
				var op1=new Option(txt1,v1);
				if(group==null)
				{	
 					group=document.createElement("optgroup");
					group.id= curr.getAttribute("groupId");
 					group.label=curr.getAttribute("lableVal");
//  					group = creatGroup(obj2,groupId);
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
				for(var j=0;j<left.length;j++)
				{
					if(left[j].text==obj1.options[i].text)
					{
						removeOne(left,j);
					}
				}
				var v1=curr.value;
				var txt1=curr.text;
				var op1=new Option(txt1,v1);
	            try
	            {
	            	option.innerHTML=text;
	            }catch(e)
	            {
	            }
				op1.setAttribute("groupId", curr.parentNode.id);
				op1.setAttribute("lableVal",curr.parentNode.label);
				curr.parentNode.removeChild(curr);
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

function removeOne(arr,index)
{
	for(var i=index;i<arr.length-1;i++)
	{
		arr[i]=arr[i+1];
	}
	arr.length=arr.length-1;
}	
</script>
<script type="text/javascript">

function searchKpiCodes()
{
	document.body.disabled=true;
    document.getElementById("loading_wait_Div").style.display="";
    var eventId=$("#eventIdByHidden").val();
    var kpiCode=$("#SelectContentByKpiCode").val();
    $.ajax({
  		type: "POST",                                                                 
  		 url:encodeURI("<%=basePath%>/MisEventRelKpi/searchEventRelKpi.html?eventId="
						+ eventId
						+ "&keyCode="
						+ kpiCode
						+ "&random="
						+ Math.random()),
				success : function(msg) {
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
    					for(var i=0;i<msg.length;i++)
    					{
    						var group=document.getElementById(msg[i].goalType);
    						if(group==null)
    						{
    							group= document.createElement("optgroup");
    							group.id=msg[i].goalType;
    							group.label=msg[i].goalTypeDesc;
								u1.appendChild(group);
    						}
    						var value=msg[i].kpiCode;
    						var text=msg[i].kpiCode+"-"+msg[i].kpiName;
    						var option=new Option(text,value);
    						option.setAttribute("groupId",msg[i].goalType);
    						option.setAttribute("lableVal",msg[i].goalTypeDesc);
    						if(group!=null)
    						{
    							group.appendChild(option);
    						}else
    						{
    							document.getElementById("u1").appendChild(option);
    						}
    						try
    						{
    							option.innerHTML=text;
    						}catch(e)
    						{	
    						}
    						//GROUP_ID

     						option.title=msg[i].kpiName;
  						}  
					document.getElementById("loading_wait_Div").style.display = "none";
					document.body.disabled = false;
					}
			});
}

	//回车搜索
function butOnClick(e) 
{
	var event=(navigator.appName=="Netscape")?e.which:e.keyCode;   
	if (event == 13) 
	{
		searchKpiCodes();
		return false;
	}
}
</script>
</head>
<body>
	<!-- 加载图片 -->
	<div id='loading_wait_Div' name='loading_wait_div'
		style='border: 0px solid red; position: absolute; left: 100px; top: 200px; display: none'>
		<img src='<%=request.getContextPath()%>/images/loading.gif'
			title='数据正在加载中...'>
	</div>
	<div style="width: 100%; height: 100%">
		<h3 class="cus-title">事件标题：${title}</h3>
		<form id="form-wrapper" class="form-wrapper" name="form2"
			method="post" action="" target="result">
			<input id="eventIdByHidden" type="hidden" name="eventId"
				value="${eventId}" /> <input id="eventTitleByHidden" type="hidden"
				name="title" value="${title}" />
			<div class="fn-clear">
				<div class="sel-list">
					<input type="text" id="SelectContentByKpiCode"
						onkeydown="javascript:butOnClick(event);"
						class="ui-input ui-input-mini" style="width: 170px"> <span
						class="ui-round-btn ui-round-btn-mini"><input type="button"
						id="J-search-f" class="ui-round-btn-text" value="搜索"
						onclick="searchKpiCodes();"></span> <br /> <label for="u1">指标列表：</label>
					<select id="u1" name="kpiUnRelInfo" size="2" multiple="true"
						ondblclick="moveTo('u1','u3')" style="height: 260px">
						<c:forEach items="${groupMap}" var="group">
							<optGroup id="${group.key}" label="${group.value}">
								<c:forEach items="${kpiUnRelList}" var="kpiUnRelInfo">
									<c:if test="${group.key == kpiUnRelInfo.goalType }">
										<option title="${kpiUnRelInfo.kpiName}"
											value="${kpiUnRelInfo.kpiCode}">${kpiUnRelInfo.kpiCode}
											- ${kpiUnRelInfo.kpiName}</option>
									</c:if>
								</c:forEach>
							</optGroup>
						</c:forEach>
					</select>
				</div>
				<div class="sel-act"
					style="position: absolute; left: 278px; top: 240px">
					<div class="row">
						<span class="ui-round-btn"><input type="button"
							class="ui-round-btn-text" onClick="moveTo('u1','u3')"
							value=" &gt; " /></span>
					</div>
					<div class="row">
						<span class="ui-round-btn"><input type="button"
							class="ui-round-btn-text" onClick="moveTo('u3','u1')"
							value=" &lt; " /></span>
					</div>
				</div>
				<div class="sel-list sel-list-right"
					style="position: absolute; left: 298px;">
					<br /> <label for="u3">关联指标：</label> <select name="kpiRelInfo"
						size="2" id="u3" multiple="true" ondblclick="moveTo('u3','u1')"
						style="height: 260px">
						<c:forEach items="${kpiRelList}" var="kpiRelInfo">
							<option title="${kpiRelInfo.kpiName}"
								value="${kpiRelInfo.kpiCode}" id="${kpiRelInfo.kpiCode}">${kpiRelInfo.kpiCode}
								- ${kpiRelInfo.kpiName}</option>
							<script language='javascript'>
							  document.getElementById("${kpiRelInfo.kpiCode}").setAttribute("groupId", "${kpiRelInfo.goalType }");
							  document.getElementById("${kpiRelInfo.kpiCode}").setAttribute("lableVal", "${kpiRelInfo.goalTypeDesc }");
							</script>
						</c:forEach>
					</select>

				</div>
			</div>
		</form>
	</div>
	<iframe name="result" id="result" src="about:blank" frameborder="0"
		width="0" height="0"></iframe>
</body>
</html>