<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="com.infosmart.po.DwpasCmoduleInfo"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	//定义位置常量
	session.setAttribute("POSITION_TOP", DwpasCmoduleInfo.POSITION_TOP);
	session.setAttribute("POSITION_LEFT",
			DwpasCmoduleInfo.POSITION_LEFT);
	session.setAttribute("POSITION_RIGHT",
			DwpasCmoduleInfo.POSITION_RIGHT);
	session.setAttribute("POSITION_BOTTOM",
			DwpasCmoduleInfo.POSITION_BOTTOM);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="x-ua-compatible" content="IE=8" />
<title>修改首页模板</title>
<link type="text/css" rel="stylesheet" href="<%=path%>/css/layer.css" />
<link type="text/css" rel="stylesheet" href="<%=path%>/css/main.css" />
<script type="text/javascript"
	src="<%=basePath%>/js/jquery-1.5.1.min.js"></script>
<!-- 弹窗JS -->
<script type="text/javascript"
	src="<%=basePath%>/js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>
<style type="text/css">
/*body*/
body {
	margin: 0px;
	padding: 0px;
	font-size: 12px;
	text-align: left;
}
/*div*/
body div {
	text-align: left;
	margin-right: auto;
	margin-left: auto;
}
/*table*/
body table {
	font-size: 12px;
}
/* 让链接在 hover 状态下显示下划线 */
a:hover {
	text-decoration: underline;
}

/* 默认显示下划线，保持页面简洁 */
ins,a {
	color: #004fba;
	text-decoration: none;
}

a:visited {
	color: #004fba;
	text-decoration: none
}

a:hover {
	color: #004fba;
	text-decoration: none; #
	background-color: #717575;
	font-size: 12px
}

a:active {
	color: #004fba;
	text-decoration: underline
}
/*面板*/
.panel {
	width: 910px;
	border: 0px solid red;
	height: 100%;
}
/*面板左边容器*/
.panel .left_container {
	float: left;
	padding-top: 0px;
	width: 59%;
	border: 1px dashed #FF0000;
	_height: 160px; /*只对IE6有效*/
	*height: 160px; /*只对IE7,IE8有效*/
	/*IE9_改为自适应，最小高度为160px*/
	min-height: 160px;
	height: expression_r(this.offsetHeight < 160 ?   '160px' :   'auto');
	/*改为自适应(结束)，最小高度为160px*/
	margin: 6px;
}
/*面板右边容器*/
.panel .right_container {
	float: left;
	padding-top: 3px;
	border: 1px dashed #FF0000;
	margin: 3px;
	margin-left: 6px;
	_height: 160px; /*只对IE6有效*/
	*height: 160px; /*只对IE7,IE8有效*/
	/*IE9_改为自适应，最小高度为160px*/
	min-height: 160px;
	height: expression_r(this.offsetHeight < 160 ?   '160px' :   'auto');
	/*改为自适应(结束)，最小高度为160px*/
	width: 38%
}
/*面板底部容器*/
.panel .bottom_container {
	float: left;
	width: 99%;
	_height: 160px; /*只对IE6有效*/
	*height: 160px; /*只对IE7,IE8有效*/
	/*IE9_改为自适应，最小高度为160px*/
	min-height: 160px;
	height: expression_r(this.offsetHeight < 160 ?   '160px' :   'auto');
	/*改为自适应(结束)，最小高度为160px*/
	border: 1px dashed #FF0000;
	margin: 3px;
}
/*模块*/
.module {
	height: auto;
	border: 1px solid #CCC;
	margin: 3px;
	background: #FFF
}

/*模块标题*/
.module_title {
	background: #ECF9FF;
	height: 18px;
	padding: 3px;
	font-weight: bold;
	border-bottom: 1px solid #CCC;
}
/*标题*/
.module_title_header {
	float: left;
	cursor: move;
	width: 85%;
	text-align: left;
}
/*最小化*/
.module_title_min {
	float: right;
	cursor: pointer;
	padding-right: 5px;
	color: red;
}
/*关闭*/
.module_title_close {
	float: right;
	cursor: pointer;
	padding-right: 10px;
	color: red;
}
/*模块正文*/
.module .module_content {
	height: 125px;
	border: 0px solid red;
	width: 100%;
	text-align: left;
	margin: 2px
}
</style>
<script language='javascript'>
    //左边窗口的个数
    var leftContainerCount=0;
    //右边窗口的个数
    var rightContainerCount=0;
    //底边窗口的个数
    var bottomContainerCount=0;
	//当前拖曳的对象
	var dragobj = {}
	window.onerror = function() {
		return false;
	}
	function on_ini() {
		String.prototype.inc = function(s) {
			return this.indexOf(s) > -1 ? true : false;
		}
		var agent = navigator.userAgent
		window.isOpr = agent.inc("Opera")
		window.isIE = agent.inc("IE") && !isOpr
		window.isMoz = agent.inc("Mozilla") && !isOpr && !isIE
		if (isMoz) {
			Event.prototype.__defineGetter__("x", function() {
				return this.clientX + 2
			})
			Event.prototype.__defineGetter__("y", function() {
				return this.clientY + 2
			})
		}
		basic_ini()
	}
	function basic_ini() {
		window.$$ = function(obj) {
			return typeof (obj) == "string" ? document.getElementById(obj)
					: obj;
		}
		window.oDel = function(obj) {
			if ($$(obj) != null) {
				$$(obj).parentNode.removeChild($$(obj));
			}
		}
	}
	window.onload = function() {
		on_ini();
		var o = document.getElementsByTagName("h1");
		var allDiv = document.getElementsByTagName("div");
		for ( var i = 0; i < allDiv.length; i++) {
			if (allDiv[i].className.toLocaleLowerCase() != "module_title_header")
				continue;
			var moveHeaderDiv=allDiv[i];
			if(moveHeaderDiv==null) continue;
			//鼠标按下，可拖动
			moveHeaderDiv.onmousedown = function(e) {
				if (dragobj.o != null)
					return false;
				e = e || event;
				dragobj.o = this.parentNode.parentNode;
				dragobj.xy = getxy(dragobj.o);
				dragobj.xx = new Array((e.x - dragobj.xy[1]),
						(e.y - dragobj.xy[0]));
				dragobj.o.style.width = dragobj.xy[2] + "px";
				dragobj.o.style.height = dragobj.xy[3] + "px";
				dragobj.o.style.left = (e.x - dragobj.xx[0]) + "px";
				dragobj.o.style.top = (e.y - dragobj.xx[1]) + "px";
				dragobj.o.style.position = "absolute";
				var om = document.createElement("div");
				dragobj.otemp = om;
				om.style.width = dragobj.xy[2] + "px";
				om.style.height = dragobj.xy[3] + "px";
				dragobj.o.parentNode.insertBefore(om, dragobj.o);
				//return false
			}
		}
	}
	document.onselectstart = function() {
		return false;
	}
	window.onfocus = function() {
		document.onmouseup();
	}
	window.onblur = function() {
		document.onmouseup();
	}
	document.onmouseup = function() {
		if (dragobj.o != null) {
			dragobj.o.style.width = "auto"
			dragobj.o.style.height = "auto"
			dragobj.otemp.parentNode.insertBefore(dragobj.o, dragobj.otemp)
			dragobj.o.style.position = ""
			oDel(dragobj.otemp)
			dragobj = {}
		}
	}
	document.onmousemove = function(e) {
		e = e || event
		if (dragobj.o != null) {
			dragobj.o.style.left = (e.x - dragobj.xx[0]) + "px"
			dragobj.o.style.top = (e.y - dragobj.xx[1]) + "px"
			createtmpl(e)
		}
	}
	function getxy(e) {
		var a = new Array()
		var t = e.offsetTop;
		var l = e.offsetLeft;//控件左侧绝对位置
		var w = e.offsetWidth;
		var h = e.offsetHeight;
		while (e = e.offsetParent) {
			t += e.offsetTop;
			l += e.offsetLeft;
		}
		//document.title = l + "|" + w;
		a[0] = t;
		a[1] = l;
		a[2] = w;
		a[3] = h
		return a;
	}
	function inner(o, e) {
		var a = getxy(o)
		if (e.x > a[1] && e.x < (a[1] + a[2]) && e.y > a[0]
				&& e.y < (a[0] + a[3])) {
			if (e.y < (a[0] + a[3] / 2))
				return 1;
			else
				return 2;
		} else {
			return 0;
		}
	}
	function createtmpl(e) {
		allMinShow(true);
		document.getElementById("chk_show_min").checked=true;
		var allDiv = document.getElementsByTagName("div");
		for ( var i = 0; i < allDiv.length; i++) {
			if (allDiv[i] == null)
				continue;
			if (allDiv[i].className.toLocaleLowerCase() != "module")
				continue;
			if (allDiv[i] == dragobj.o)
				continue;
			var b = inner(allDiv[i], e);
			if (b == 0)
				continue;
			dragobj.otemp.style.width = allDiv[i].offsetWidth;
			if (b == 1) {
				//增加新的窗口
				allDiv[i].parentNode.insertBefore(dragobj.otemp, allDiv[i]);
			} else {
				if (allDiv[i].nextSibling == null) {
					//增加新的窗口
					allDiv[i].parentNode.appendChild(dragobj.otemp);
				} else {
					//增加新的窗口
					allDiv[i].parentNode.insertBefore(dragobj.otemp,
							allDiv[i].nextSibling);
				}
			}
			var containerId = allDiv[i].parentNode.id;//容器为dom_left/dom_right/dom_bottom
			//位置(左边，右边，底部)
			var position = containerId.substring(containerId.indexOf("_") + 1,containerId.length);
			var moduleId = dragobj.o.id;
			var id = moduleId.substring(moduleId.indexOf("_") + 1,moduleId.length);
			if (position == "left") {
				if ($$('moduleWidth' + id) != null)
					$$('moduleWidth' + id).value = "540";
				//左边
				if(document.getElementById("module_position_"+id)!=null) document.getElementById("module_position_"+id).value="<%=DwpasCmoduleInfo.POSITION_LEFT%>";					
			} else if (position == "right") {
				if ($$('moduleWidth' + id) != null)
					$$('moduleWidth' + id).value = "360";
				//右边
				if(document.getElementById("module_position_"+id)!=null) document.getElementById("module_position_"+id).value="<%=DwpasCmoduleInfo.POSITION_RIGHT%>";					
			} else {
				if ($$('moduleWidth' + id) != null)
					$$('moduleWidth' + id).value = "900";
				//右边
				if(document.getElementById("module_position_"+id)!=null) document.getElementById("module_position_"+id).value="<%=DwpasCmoduleInfo.POSITION_BOTTOM%>";					
			}
			//退出
			return;
		}

		var childNodes = $$('contentDiv').childNodes;
		for ( var j = 0; j < childNodes.length; j++) {
			if (childNodes[j].className != "left_container"
					&& childNodes[j].className != "right_container"
					&& childNodes[j].className != "bottom_container")
				continue;
			var op = getxy(childNodes[j])
			//判断X坐标
			if (e.x > (op[1] + 10) && e.x < (op[1] + op[2] - 10)) {
				//判断Y坐标
				if (e.y > (op[0] + 10) && e.y < (op[0] + op[3] - 10)) {
					//增加新的窗口
					childNodes[j].appendChild(dragobj.otemp);
					dragobj.otemp.style.width = (op[2] - 10) + "px";
					//容器ID
					var containerId = childNodes[j].id;//容器为dom_left/dom_right/dom_bottom
					//位置(左边，右边，底部)
					var position = containerId.substring(containerId.indexOf("_") + 1, containerId.length);
					var moduleId = dragobj.o.id;
					var id = moduleId.substring(moduleId.indexOf("_") + 1,moduleId.length);
					if (position == "left") {
						if ($$('module_width_' + id) != null)
							$$('module_width_' + id).value = "540";
						//左边
						if(document.getElementById("module_position_"+id)!=null) document.getElementById("module_position_"+id).value="<%=DwpasCmoduleInfo.POSITION_LEFT%>";							
					} else if (position == "right") {
						if ($$('module_width_' + id) != null)
							$$('module_width_' + id).value = "360";
						//右边
						if(document.getElementById("module_position_"+id)!=null) document.getElementById("module_position_"+id).value="<%=DwpasCmoduleInfo.POSITION_RIGHT%>";						
					} else {
						if ($$('module_width_' + id) != null)
							$$('module_width_' + id).value = "900";
						//底部
						if(document.getElementById("module_position_"+id)!=null) document.getElementById("module_position_"+id).value="<%=DwpasCmoduleInfo.POSITION_BOTTOM%>";
					}
					break;
				}
			}
		}
		//自适应高度
		var childNodes = $$("dom_left").childNodes;
		var totalHeight = 0;
		for ( var j = 0; j < childNodes.length; j++) {
			if (childNodes[j].className != "left_container"
					&& childNodes[j].className != "right_container"
					&& childNodes[j].className != "bottom_container")
				continue;
			totalHeight += getxy(childNodes[j])[3];
			totalHeight = totalHeight <= 160 ? 160 : totalHeight;
			$$("dom_left").style.height = totalHeight;
			//
		}
		var childNodes = $$("dom_right").childNodes;
		var totalHeight = 0;
		for ( var j = 0; j < childNodes.length; j++) {
			if (childNodes[j].className != "left_container"
					&& childNodes[j].className != "right_container"
					&& childNodes[j].className != "bottom_container")
				continue;
			totalHeight += getxy(childNodes[j])[3];
			totalHeight = totalHeight <= 160 ? 160 : totalHeight;
			$$("dom_right").style.height = totalHeight+"px";
		}
		var childNodes = $$("dom_bottom").childNodes;
		var totalHeight = 0;
		for ( var j = 0; j < childNodes.length; j++) {
			if (childNodes[j].className != "left_container"
					&& childNodes[j].className != "right_container"
					&& childNodes[j].className != "bottom_container")
				continue;
			totalHeight += getxy(childNodes[j])[3];
			totalHeight = totalHeight <= 160 ? 160 : totalHeight;
			$$("dom_bottom").style.height = totalHeight;
		}
	}
	//最小化或最大化
	function divMinOrMax(moduleId) {
		if ($$('module_content_' + moduleId).style.display == "none") {
			$$('module_content_' + moduleId).style.display = "block";
			$$('min_' + moduleId).innerHTML = "<img src='<%=basePath%>/images/action-min.png'>";
			$$('min_' + moduleId).title = "最小化";
		} else {
			$$('module_content_' + moduleId).style.display = "none";
			$$('min_' + moduleId).innerHTML = "<img src='<%=basePath%>/images/action-max.png'>";
			$$('min_' + moduleId).title = "还原";
		}
	}
	//重新设置窗口大小
	function reSetHeight(moduleId, height) {
		if($$("module_content_"+moduleId)==null) return;
		if(height==null||height<=0) 
		{
			height=300;
			$$('module_height_'+moduleId).value=300;
		}
		$$("module_content_"+moduleId).style.height = height+"px";
	}
	//保存模块信息
	function saveModuleInfo()
	{
		//先关闭所有的模块,方便计算实际坐标值
		allMinShow(true);
		var childNodes =null;
		var div_moduleId=null;
		var moduleId="";
		childNodes= $$("dom_left").childNodes;
		//所有子节点
		for ( var j = 0; j < childNodes.length; j++) {
			div_moduleId=childNodes[j].id;
			if(div_moduleId!=null&&div_moduleId.length>0) moduleId=div_moduleId.substring(div_moduleId.indexOf("_") + 1,div_moduleId.length);
			if($$("module_sort_"+moduleId)!=null)  $$("module_sort_"+moduleId).value=j+1;
			//判断模块名不能为空
            if($$('moduleName_'+moduleId).value==null||$$('moduleName_'+moduleId).value.length==0)
			{
				alert('左边窗口第'+(j+1)+'个模块名称不能为空');
				$$('module_content_'+moduleId).style.display="block";//显示当前窗口
				return;
			}
            var xy=getxy(childNodes[j]);
			$$("module_positionX_"+moduleId).value=Math.round(xy[1]);
			$$("module_positionY_"+moduleId).value=Math.round(xy[0]);//转整数
		}
		childNodes= $$("dom_right").childNodes;
		//所有子节点
		for ( var j = 0; j < childNodes.length; j++) {
			div_moduleId=childNodes[j].id;
			if(div_moduleId!=null&&div_moduleId.length>0) moduleId=div_moduleId.substring(div_moduleId.indexOf("_") + 1,div_moduleId.length);
			if($$("module_sort_"+moduleId)!=null)  $$("module_sort_"+moduleId).value=j+1;
			//判断模块名不能为空
            if($$('moduleName_'+moduleId).value==null||$$('moduleName_'+moduleId).value.length==0)
			{
				alert('右边窗口第'+(j+1)+'个模块名称不能为空');
				$$('module_content_'+moduleId).style.display="block";//显示当前窗口
				return;
			}
            var xy=getxy(childNodes[j]);
			$$("module_positionX_"+moduleId).value=Math.round(xy[1]);
			$$("module_positionY_"+moduleId).value=Math.round(xy[0]);//转整数
		}
		childNodes= $$("dom_bottom").childNodes;
		//所有子节点
		for ( var j = 0; j < childNodes.length; j++) {
			div_moduleId=childNodes[j].id;
			if(div_moduleId!=null&&div_moduleId.length>0) moduleId=div_moduleId.substring(div_moduleId.indexOf("_") + 1,div_moduleId.length);
			if($$("module_sort_"+moduleId)!=null)  $$("module_sort_"+moduleId).value=j+1;
			//判断模块名不能为空
            if($$('moduleName_'+moduleId).value==null||$$('moduleName_'+moduleId).value.length==0)
			{
				alert('底边窗口第'+(j+1)+'个模块名称不能为空');
				$$('module_content_'+moduleId).style.display="block";//显示当前窗口
				return;
			}
            var xy=getxy(childNodes[j]);
			$$("module_positionX_"+moduleId).value=Math.round(xy[1]);
			$$("module_positionY_"+moduleId).value=Math.round(xy[0]);//转整数
		}
		if(!confirm("确认保存吗")) return;
		$$("indexMenuForm").submit();
	}
	//回调函数
	function success(){
		alert("操作成功！");
		document.location.reload();
	}
	function failed(){
		alert("保存失败！");
	}
	function allMinShow(minFlag)
	{
		//所有的DIV全部关闭,方面拖曳DIV
		var allContentDiv= document.getElementsByTagName("div");
		for ( var k = 0; k < allContentDiv.length; k++) {
		   if (allContentDiv[k].className.toLocaleLowerCase() != "module_content") continue;
		   var divId=allContentDiv[k].id;
		   var moduleId=divId.substring(divId.lastIndexOf("module_content_")+15);
		   if($$("min_"+moduleId)!=null)
			{
			   $$('min_' + moduleId).innerHTML =minFlag?"<img src='<%=basePath%>/images/action-max.png'>":"<img src='<%=basePath%>/images/action-min.png'>";
			   $$('min_' + moduleId).title=minFlag?"还原":"最小化";
			}
		   allContentDiv[k].style.display=minFlag?"none":"block";
		}
		//结束--所有的DIV全部关闭,方面拖曳DIV
	}
	//隐藏当前模块
	function dispayThisModule(moduleId,flag)
	{
       var isAllShow=document.getElementById("chk_show_module").checked?true:false;
	   if(flag==0)
		{
		   //如果非显示所有模块，则隐藏
		   if(isAllShow==false) document.getElementById("module_"+moduleId).style.display="none";
		   document.getElementById("title_"+moduleId).innerHTML+="---<font color=red>已隐藏</font>";
		}else
		{
           document.getElementById("module_"+moduleId).style.display="block";
		   var title=document.getElementById("title_"+moduleId).innerHTML;
		   document.getElementById("title_"+moduleId).innerHTML=title.substring(0,title.indexOf("---"));
		}
	}
	//显示或隐藏所有已隐藏的模块
	function allHideShow(showFlag)
	{
		var allContentDiv= document.getElementsByTagName("div");
		for ( var k = 0; k < allContentDiv.length; k++) {
		   if (allContentDiv[k].className.toLocaleLowerCase() != "module") continue;
		   if(document.getElementById(allContentDiv[k].id+"_opt").value!=0) continue;
		   allContentDiv[k].style.display=showFlag?"block":"none";
		}
	}
	//删除模块信息
	function deleteModule(moduleId)
	{
       if(moduleId==null||moduleId.length==0)
		{
		   alert("未知模块ID，请选择模块");
		   return;
		}
       if(confirm("确定要删除该模块？")){
			var url = "<%=basePath%>/designTemplate/deleteModuleById.html?moduleId="+moduleId;
			$.get(url,function(data){
				if(data=="1"){
					alert('删除成功');
					document.getElementById("module_"+moduleId).style.display="none";
				}else
				{
					alert('删除失败');
					return;
				}
			});
	   }        
	}
	//显示实际高度
	function showOffsetHeight(isNormal)
	{
        var childNodes =null;
		var div_moduleId=null;
		var moduleId="";
		//左侧窗口
		childNodes= $$("dom_left").childNodes;
		//所有子节点
		for ( var j = 0; j < childNodes.length; j++) {
			div_moduleId=childNodes[j].id;
			if(div_moduleId!=null&&div_moduleId.length>0) moduleId=div_moduleId.substring(div_moduleId.indexOf("_") + 1,div_moduleId.length);
			if($$('module_height_'+moduleId)!=null)
			{
				if(isNormal==true)
				{
					$$('label_show_offsetHeight').innerHTML='还原默认高度';
					reSetHeight(moduleId,$$('module_height_'+moduleId).value);//显示实际高度
				}else
				{
                    $$('label_show_offsetHeight').innerHTML='显示实际高度';
					reSetHeight(moduleId,125);//还原默认高度
				}
			}
		}
		//右侧窗口
		childNodes= $$("dom_right").childNodes;
		//所有子节点
		for ( var j = 0; j < childNodes.length; j++) {
			div_moduleId=childNodes[j].id;
			if(div_moduleId!=null&&div_moduleId.length>0) moduleId=div_moduleId.substring(div_moduleId.indexOf("_") + 1,div_moduleId.length);
			if($$('module_height_'+moduleId)!=null)
			{
				if(isNormal==true)
				{
					$$('label_show_offsetHeight').innerHTML='还原默认高度';
					reSetHeight(moduleId,$$('module_height_'+moduleId).value);//显示实际高度
				}else
				{
                    $$('label_show_offsetHeight').innerHTML='显示实际高度';
					reSetHeight(moduleId,125);//还原默认高度
				}
			}
		}
		//底边窗口
		childNodes= $$("dom_bottom").childNodes;
		//所有子节点
		for ( var j = 0; j < childNodes.length; j++) {
			div_moduleId=childNodes[j].id;
			if(div_moduleId!=null&&div_moduleId.length>0) moduleId=div_moduleId.substring(div_moduleId.indexOf("_") + 1,div_moduleId.length);
			if($$('module_height_'+moduleId)!=null)
			{
				if(isNormal==true)
				{
					$$('label_show_offsetHeight').innerHTML='还原默认高度';
					reSetHeight(moduleId,$$('module_height_'+moduleId).value);//显示实际高度
				}else
				{
                    $$('label_show_offsetHeight').innerHTML='显示实际高度';
					reSetHeight(moduleId,125);//还原默认高度
				}
			}
		}
	}
	
</script>
</head>
<body>
	<form action="<%=basePath%>/designTemplate/saveIndexPageConfig.html"
		name="indexMenuForm" id="indexMenuForm" method="post" target="result">
		<div>
			<table width="910px" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td align="left" width="80%"><div
							style="font-size: 12px; font-family: '宋体';">
							当前位置:&nbsp;${templateName}&nbsp;&gt;&nbsp;<font color="red">${menuName}</font>&nbsp;
						</div></td>
					<td align="right"><input type="button" value="返回"
						onclick="javascrit:location.href='<%=path%>/dwpas/listJingWeiYiMenus${systemMenu.templateId}.html?templateName=${templateName}';" />
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<hr size='1' style='width: 910px;' style='text-align:left;' />
					</td>
				</tr>
			</table>
		</div>
		<input type="hidden" name="menuId" value="${systemMenu.menuId}">
		<input type="hidden" name="menuCode" value="${systemMenu.menuCode}">
		<input type="hidden" name="menuPid" value="${systemMenu.menuPid}">
		<input type="hidden" name="menuUrl" value="${systemMenu.menuUrl}">
		<input type="hidden" name="templateId"
			value="${systemMenu.templateId}"> <input type="hidden"
			name="menuOrder" value="${systemMenu.menuOrder}">
		<table width="910px" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td align="left" width="23%">&nbsp; <input type="button"
					value="新增模块" onclick="editRelColumn();"> &nbsp; &nbsp; <input
					type="button" value="保存模板" onclick="saveModuleInfo();">
					&nbsp;
				</td>
				<td align="center" width="53%"><input type="checkbox" value="1"
					id="chk_show_module" name="chk_show_module"
					onclick='allHideShow(this.checked)' /><label for="chk_show_module"
					style='cursor: pointer;'>显示已隐藏的模块</label> &nbsp;<input type="checkbox"
					value="1" id="chk_show_min" name="chk_show_min"
					onclick='allMinShow(this.checked)' /><label for="chk_show_min"
					style='cursor: pointer;'>所有模块最小化</label> &nbsp;<input type="checkbox"
					value="1" id="chk_show_offsetHeight" name="chk_show_offsetHeight"
					onclick='showOffsetHeight(this.checked)' /><label
					for="chk_show_offsetHeight" style='cursor: pointer;'
					id='label_show_offsetHeight'>显示实际高度</label></td>
				<td align='right'><img alt="系统模块" src="../images/blue.jpg"
					width="15px;"><label>系统模块</label> &nbsp; <img alt="自定义模块"
					src="../images/black.jpg" width="15px;"><label>自定义模块</label>
					&nbsp;</td>
			</tr>
		</table>
		<fieldset style="width: 910px;">
			<legend style='color: red;'>菜单信息</legend>
			<table border="0" width="100%">
				<tr>
					<td width="15%" align="center">菜&nbsp;单&nbsp;名：</td>
					<td width="17%"><input type="text" name="menuName"
						value="${systemMenu.menuName}"></td>
					<td width="15%" align="center">日期范畴：</td>
					<td width="17%"><select name="dateType" style="width: 120px;"
						id="dateTypeOption" onchange="this.value='${systemMenu.dateType}'">
							<option value="D"
								<c:if test="${systemMenu.kpiType eq 1}">selected</c:if>>日</option>
							<option value="M"
								<c:if test="${systemMenu.kpiType eq 3}">selected</c:if>>月</option>
					</select></td>
					<td width="15%" align="center">是否隐藏：</td>
					<td><select name="isShow" style="width: 120px;" <c:if test="${systemMenu.menuCode == '01'}">onchange='this.value=1;'</c:if>>
							<option value="1"
								<c:if test="${systemMenu.isShow eq 1}">selected</c:if>>显示</option>
							<option value="0" style="color: red;"
								<c:if test="${systemMenu.isShow eq 0}">selected</c:if>>隐藏</option>
					</select></td>
				</tr>
			</table>
		</fieldset>
		<fieldset style="width: 910px;">
			<legend style='color: red; align: center;'>模块列表</legend>
			<!--容器-->
			<div class="panel" id="contentDiv">
				<!--左边-->
				<div class='left_container' id='dom_left'></div>
				<!--右边-->
				<div class='right_container' id='dom_right'></div>
				<!-- 底部 -->
				<div class='bottom_container' id='dom_bottom'></div>
			</div>
		</fieldset>
		<!-- 所有的模块 -->
		<c:if test="${not empty systemMenu.moduleInfoList}">
			<c:forEach items="${systemMenu.moduleInfoList}" var="moduleInfo"
				varStatus="vs">
				<div class="module" id="module_${moduleInfo.moduleId}"
					style='display:<c:if test="${moduleInfo.isShow eq 0}">none</c:if>'>
					<!-- 模块HEADER -->
					<div class='module_title' id="module_header_${moduleInfo.moduleId}"
						ondblclick="$$('min_${moduleInfo.moduleId}').click();">
						<div class="module_title_header" id="title_${moduleInfo.moduleId}"
							title='按住鼠标可拖动模块' >
							<!--0：系统模块 1：自定义模块-->
							<c:choose>
								<c:when test='${moduleInfo.moduleType eq 1}'>
									<font color="black">${moduleInfo.moduleName}</font>
								</c:when>
								<c:when test='${moduleInfo.moduleType eq 0}'>
									<font color="green">${moduleInfo.moduleName}</font>
								</c:when>
								<c:otherwise>
									<font color="black">${moduleInfo.moduleName}</font>
								</c:otherwise>
							</c:choose>
							<!--0：已隐藏-->
							<c:if test="${moduleInfo.isShow eq 0}">---<font color=red>已隐藏</font>
							</c:if>
						</div>
						<!--不为系统模块，可删除-->
						<c:if test="${moduleInfo.moduleType!=0}">
							<!--关闭-->
							<div class="module_title_min" title='删除'
								id="close_${moduleInfo.moduleId}"
								onclick='deleteModule("${moduleInfo.moduleId}")'><img src="<%=basePath%>/images/action-close.png"></div>
						</c:if>
						<!--不为系统模块，可删除-->
						<c:if test="${moduleInfo.moduleType==0}">
							<!--关闭-->
							<div class="module_title_min" title='系统模块'
								id="close_${moduleInfo.moduleId}"
								onclick='deleteModule("${moduleInfo.moduleId}")'><img src="<%=basePath%>/images/action-refresh.png"></div>
						</c:if>
						<!--最小化-->
						<div class="module_title_close" title='最小化'
							id="min_${moduleInfo.moduleId}"
							onclick="divMinOrMax('${moduleInfo.moduleId}');"><img src="<%=basePath%>/images/action-min.png"></div>
					</div>
					<div class="module_content" name="moduleContent"
						id="module_content_${moduleInfo.moduleId}" style='height: 125px;'>
						<input type="hidden" name="moduleInfoList[${vs.index}].tabShow" value="${moduleInfo.tabShow}"/>
						<table width="330" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="25%" align='center'><font size='2'>模块名称：</font></td>
								<td><input type="text" value="${moduleInfo.moduleName}"
									name="moduleInfoList[${vs.index}].moduleName"
									style="width: 200px;" id="moduleName_${moduleInfo.moduleId}"
									onblur="$$('title_${moduleInfo.moduleId}').innerText=this.value"
									onfocus="this.select();" autocomplete="off"></td>
							</tr>
							<tr>
								<td width="25%" align='center'><font size='2'>模块高度：</font></td>
								<td>
									<!-- 模块ID --> <input type="hidden"
									name="moduleInfoList[${vs.index}].moduleId"
									value="${moduleInfo.moduleId}"> <!-- 关联菜单ID --> <input
									type="hidden" name="moduleInfoList[${vs.index}].menuId"
									value="${moduleInfo.menuId}"> <!-- 日期类型 --> <input
									type="hidden" name="moduleInfoList[${vs.index}].dateType"
									value="${systemMenu.dateType}"> <!-- 模块编码 --> <input
									type="hidden" value="${moduleInfo.moduleCode}"
									name="moduleInfoList[${vs.index}].moduleCode"
									style="width: 200px;" id="moduleCode_${moduleId}" readonly>
									<!-- 位置 --> <input type="hidden"
									name="moduleInfoList[${vs.index}].position"
									value="${moduleInfo.position}"
									id="module_position_${moduleInfo.moduleId}"> <!--X坐标-->
									<input type="hidden"
									name="moduleInfoList[${vs.index}].positionX"
									value="${moduleInfo.positionX}"
									id="module_positionX_${moduleInfo.moduleId}"> <!--Y坐标-->
									<input type="hidden"
									name="moduleInfoList[${vs.index}].positionY"
									value="${moduleInfo.positionY}"
									id="module_positionY_${moduleInfo.moduleId}"> <!-- 宽度 -->
									<input type="hidden" name="moduleInfoList[${vs.index}].width"
									value="${moduleInfo.width}"
									id="module_width_${moduleInfo.moduleId}"> <!-- 高度 --> <input
									type="text" name="moduleInfoList[${vs.index}].height"
									value="${moduleInfo.height}"
									id="module_height_${moduleInfo.moduleId}"
									onkeyup="value=value.replace(/[^\d]/g,'')"
									onblur="if(this.value.length==0||isNaN(this.value)) this.value=this.defaultValue;"
									style='width: 60px; ime-mode: disabled;'
									onfocus='this.select();'> <!-- 排序 --> <input
									type="hidden" name="moduleInfoList[${vs.index}].moduleSort"
									value="${moduleInfo.moduleSort}"
									id="module_sort_${moduleInfo.moduleId}"> <!-- 类型 --> <input
									type="hidden" name="moduleInfoList[${vs.index}].moduleType"
									value="${moduleInfo.moduleType}">
								</td>
							</tr>
							<tr>
								<td align='center'><font size='2'>是否显示：</font></td>
								<td><select name="moduleInfoList[${vs.index}].isShow"
									onchange="dispayThisModule('${moduleInfo.moduleId}',this.value)"
									id="module_${moduleInfo.moduleId}_opt">
										<option value="1"
											<c:if test="${moduleInfo.isShow eq 1}">selected</c:if>>显示</option>
										<option value="0" style="color: red;"
											<c:if test="${moduleInfo.isShow eq 0}">selected</c:if>>隐藏</option>
								</select></td>
							</tr>
							<tr>
								<td align='center'><font size='2'>关联栏目：</font></td>
								<td><a href='javascript:void(0)'
									onclick='editRelColumn("${moduleInfo.moduleId}")'>修改关联栏目</a></td>
							</tr>
							<tr>
								<td align='center'><font size='2'>模块说明：</font></td>
								<td><textarea name="moduleInfoList[${vs.index}].remark"
										style="width: 90%; height: 36px;"
										id="remark_${moduleInfo.moduleId}">${moduleInfo.remark}</textarea></td>
							</tr>
						</table>
					</div>
				</div>
				<script language='javascript'>
					//判断位置 //0：顶部  1：左边 2：右边 3:底部
					<c:choose>
					  <c:when test="${moduleInfo.position eq POSITION_LEFT }">
					      //左边
					      document.getElementById("dom_left").appendChild(document.getElementById("module_${moduleInfo.moduleId}"));
						  //宽度
                          document.getElementById("module_width_${moduleInfo.moduleId}").value="560";
                          //左边窗口个数
                          leftContainerCount++;
					  </c:when>
					  <c:when test="${moduleInfo.position eq POSITION_RIGHT }">
					     //右边
					     document.getElementById("dom_right").appendChild(document.getElementById("module_${moduleInfo.moduleId}"));
						  //宽度
                          document.getElementById("module_width_${moduleInfo.moduleId}").value="340";
                          //右边窗口个数
                          rightContainerCount++;
					  </c:when>
					  <c:otherwise>
					     //底边 //POSITION_BOTTOM
					     document.getElementById("dom_bottom").appendChild(document.getElementById("module_${moduleInfo.moduleId}"));
						  //宽度
                          document.getElementById("module_width_${moduleInfo.moduleId}").value="900";
						  //底边窗口个数
                          bottomContainerCount++;
					  </c:otherwise>
					</c:choose>
				</script>
			</c:forEach>
		</c:if>
	</form>
	<iframe name="result" id="result" src="about:blank" frameborder="0"
		width="0" height="0"></iframe>
</body>
</html>
<script type="text/javascript">
<!--
  $(document).ready(function(){
	$(".main_info:even").addClass("main_table_even");
	/*
	document.getElementById("dom_left").style.height=160*leftContainerCount+"px";
	document.getElementById("dom_right").style.height=160*rightContainerCount+"px";
	document.getElementById("dom_bottom").style.height=160*bottomContainerCount+"px";
	*/
  });
 //修改关联栏目
 function editRelColumn(moduleId){
	var moduleName="新的模块";
	if(document.getElementById("title_"+moduleId)!=null)
	 {
        moduleName=document.getElementById("title_"+moduleId).innerHTML;
	 }
	//默认为新增的模块
	if(moduleId==null)
	 {
        moduleName="新的模块";
		moduleId="";
	 }
	var dateType=document.getElementById("dateTypeOption").value;
	var dg = new $.dialog({
		title:'修改模块< '+moduleName+' >关联栏目',
		id:'editRelColumn',
		width:800,
		height:480,
		iconTitle:false,
		cover:false,
		maxBtn:true,
		xButton:true,
		resize:false,
		top:5,
		page:'<%=basePath%>/designTemplate/listColumnInfo.html?moduleId='+ moduleId + "&menuId=${systemMenu.menuId}&dateType="+ dateType,
		dgOnLoad:function(){
              dg.maxSize();
			}
		});
		dg.ShowDialog();
	}
//-->
</script>