<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String columnTmpId = request.getParameter("columnTmpId");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>选择指标</title>
<link charset="utf-8" rel="stylesheet"
	href="<%=basePath%>css/alice1.css" />
<link type="text/css" charset="utf-8" rel="stylesheet"
	href="<%=basePath%>css/css.css" />
<script type="text/javascript" src="<%=path%>/js/jquery-1.5.1.min.js""></script>
<script type="text/javascript">
	var columnTmpId="${InfoId}";
	var dg;
	$(document).ready(function() {
		dg = frameElement.lhgDG;
		dg.addBtn('ok', '确定', function() {
			//已选择的数据
			var obj1=document.getElementById("u3");
			if(obj1.options.length==0)
			{
				alert('请选择指标');
				return;
			}
			//关联指标
            var relCommKpiOptOfColumn=dg.curWin.document.getElementById("relCommKpiOpt_${InfoId}");
			if(relCommKpiOptOfColumn!=null) relCommKpiOptOfColumn.length=0;//先清空数据
			for(var i=0;i<obj1.options.length;i++){
				if(relCommKpiOptOfColumn!=null)
				{
                    var opt=document.createElement("option");
					    opt.id=obj1.options[i].value;
						opt.value=obj1.options[i].value;
						opt.text=obj1.options[i].text;
						//IE8不支持　opt.text，用下面的语句支持IE8
						opt.innerHTML=obj1.options[i].text;//
					relCommKpiOptOfColumn.appendChild(opt);
					//relCommKpiOptOfColumn.options.add(new Option(obj1.options[i].text,obj1.options[i].value));//text,value
				}
			}
			//
			var chartTypeOpt=dg.curWin.document.getElementById("chartTypeDesc");
			var defaultChartType=chartTypeOpt.value;			
			dg.curWin.initChartType(dg.curWin.document.getElementById("chartTypeDesc"),obj1.options.length);
			dg.curWin.document.getElementById("chartTypeDesc").value=defaultChartType;
			dg.cancel();
		});
	});
	//初始化
	window.onload=function(){
					//加载指标
            $.post("<%=basePath%>LegendInfo/listAllCommKpiCode.html.html",function(comlist){
               if(comlist!=null&&comlist.length>0)
				{
				   var noRelOpt=document.getElementById("u1");
			       if(noRelOpt!=null) noRelOpt.length=0;//清空
				   var KpiInfo=null;
				   for(var i=0;i<comlist.length;i++)
					{
					   KpiInfo=comlist[i];
					   if(KpiInfo==null) continue;
					   var opt = document.createElement("option");
					   opt.id="opt_"+KpiInfo.kpiCode;
					   opt.value=KpiInfo.kpiCode;
					   opt.title=KpiInfo.kpiName;
					   opt.text=KpiInfo.kpiCode+'---'+KpiInfo.kpiNameShow;
                       noRelOpt.options.add(opt);
					}
					//已选择的指标
					var selectedKpiCodes="${selectedKpi}";//已选择指标
					var selectedKpiCodeArray=selectedKpiCodes.split(",");
					if(selectedKpiCodes.length>0&&selectedKpiCodeArray.length>0)
					{
						var selectedCount=0;
						for(var i=0;i<selectedKpiCodeArray.length;i++)
						{
						   if(selectedKpiCodeArray[i]==null||selectedKpiCodeArray[i]=="null"||selectedKpiCodeArray[i].length==0) continue;
						   if(document.getElementById("opt_"+selectedKpiCodeArray[i])!=null) 
							{
							   document.getElementById("opt_"+selectedKpiCodeArray[i]).selected=true;
							   selectedCount++;
							}
						}
						if(selectedCount>0)
						{
							moveTo("u1","u3");
						}else
						{
							noRelOpt.options[0].selected=true;
						}
					}
				}
			});
		}
	//重新加载数据
	function loadKpiOpt(relKpiKind)
	{
            //加载通用指标
            $.post("<%=basePath%>LegendInfo/listAllCommKpiCode.html.html",function(comlist){
               if(comlist!=null&&comlist.length>0)
				{
				   //清空已加载的数据
				   var noRelOpt=document.getElementById("u1");
			       if(noRelOpt!=null) noRelOpt.length=0;//清空
				   //已关联的，清空数据
				   var isRelOpt=document.getElementById("u3");
				   if(isRelOpt!=null) isRelOpt.length=0;//清空
				   var KpiInfo=null;
				   for(var i=0;i<comlist.length;i++)
					{
					   KpiInfo=comlist[i];
					   if(KpiInfo==null) continue;
					   var opt = document.createElement("option");
					   opt.id="opt_"+KpiInfo.kpiCode;
					   opt.value=KpiInfo.kpiCode;
					   opt.title=KpiInfo.kpiName;
					   opt.text=KpiInfo.kpiCode+'---'+KpiInfo.kpiNameShow;
                       noRelOpt.options.add(opt);
					}
				   //默认第一个选中
				   noRelOpt.focus();
				   noRelOpt.options[0].selected=true;
				}else
				{
					alert('加载数据失败或没有数据');
				}
			});
	}
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
			alert('没有要选的指标');
		}
	}
	function removeOne(arr, index) {
		for ( var i = index; i < arr.length - 1; i++) {
			arr[i] = arr[i + 1];
		}
		arr.length = arr.length - 1;
	}
	
	//startwith
	String.prototype.startWith=function(s)
	{
	  if(s==null||s==""||this.length==0||s.length>this.length)
	   return false;
	  if(this.substr(0,s.length)==s)
		 return true;
	  else
		 return false;
	  return true;
	}

	//查询数据
	function queryByCode(val,evt)
	{
	    var crtOpt=document.getElementById("u1");
		evt = (evt) ? evt : ((window.event) ? window.event : "");
		//数据太多，加载慢，故回车再查询
         if(evt.keyCode==40&&crtOpt.options.length>0)
		 {
		 　 //点击上下键选择
		    crtOpt.focus();
		    crtOpt.options[0].selected=true;
		 }else if(crtOpt.options.length>500&&evt.keyCode!=13)
		 {
		    //数据超过500条，按回车键查询
		    return;
		 }
		//显示所有数据
		$("#u1").children("span").each(function()
		{ 
		   $(this).children().clone().replaceAll($(this)); //use the content of the <span> replace the <span> 
		});
		if(val.length==0) return;
	　　//已选中的ID
        var optIdArray=new Array();
		//查找不显示的数据
		for(var i=0;i<crtOpt.options.length;i++)
		{
		   
		   //包括这字符
		   if((crtOpt.options[i].title.indexOf(val.trim().toUpperCase())==-1)
					&&(crtOpt.options[i].text.indexOf(val.trim())==-1))
		   {
			  optIdArray[optIdArray.length]=crtOpt.options[i].id;
		   }
		   
		   /*
		   //以字符开始
           if(crtOpt.options[i].text.startWith(val.toUpperCase())==false)
		   {
			  optIdArray[optIdArray.length]=crtOpt.options[i].id;
		   }
		   */
		}
        //不显示
		for(var i=0;i<optIdArray.length;i++)
		{

           $("#"+optIdArray[i]).wrap("<span style='display:none'></span>");
		}
	}
	</script>
</head>
<body>
	<form id="choiceCodeForm" name="choiceCodeForm" method="post"
		action=" " target="result">
		<div class="fn-clear">
			<div class="sel-list sel-list-left">
				<table border='0' width="270px">
					<tr>
						<td width="100px" align='center'>&nbsp;搜索待选指标：</td>
						<td><input type="text" id="comKpiName"
							class="ui-input ui-input-mini" style="width: 100px"
							name="comKpiName" onkeyup="queryByCode(this.value,event);"
							style="ime-mode:disabled;" /></td>
					</tr>
					<tr>
						<td colspan='4'><select id="u1" name="allCommCodes" size="2"
							multiple="multiple" ondblclick="moveTo('u1','u3')"
							style="height: 250px">

						</select></td>
					</tr>
				</table>
			</div>
			<div class="sel-act">
				<div class="row">
					<span class="ui-round-btn"><input type="button"
						class="ui-round-btn-text" onClick="moveTo('u1','u3')"
						value=" &gt; " style="width: 35px" /> </span>
				</div>
				<div class="row">
					<span class="ui-round-btn"><input type="button"
						class="ui-round-btn-text" onClick="moveTo('u3','u1')"
						value=" &lt; " style="width: 35px" /> </span>
				</div>
				<div class="row">
					<span class="ui-round-btn"><input type="button"
						class="ui-round-btn-text" onClick="moveToAll('u1','u3')"
						value=" &gt;&gt; " style="width: 35px" /> </span>
				</div>
				<div class="row">
					<span class="ui-round-btn"><input type="button"
						class="ui-round-btn-text" onClick="moveToAll('u3','u1')"
						value=" &lt;&lt; " style="width: 35px" /> </span>
				</div>
			</div>
			<div class="sel-list sel-list-right">
				 <label for="u3">已选择指标：</label> <select name="choiceCodes"
					size="2" id="u3" multiple="multiple" ondblclick="moveTo('u3','u1')"
					style="height: 250px">
				</select>
			</div>
		</div>
	</form>
	<iframe name="result" id="result" src="about:blank" frameborder="0"
		width="0" height="0"></iframe>
</body>
</html>