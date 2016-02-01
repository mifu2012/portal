<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="com.infosmart.po.dwmis.DwmisModuleInfo"%>
<%@ page import="com.infosmart.po.dwmis.DwmisLegendInfo" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	//定义位置常量
	request.setAttribute("POSITION_TOP", DwmisModuleInfo.POSITION_TOP);
	request.setAttribute("POSITION_LEFT", DwmisModuleInfo.POSITION_LEFT);
	request.setAttribute("POSITION_RIGHT", DwmisModuleInfo.POSITION_RIGHT);
	request.setAttribute("POSITION_BOTTOM", DwmisModuleInfo.POSITION_BOTTOM);
	//当前模块ID
	String moduleId = request.getParameter("moduleId");
	//趋势图
	session.setAttribute("STOCK_CHART", DwmisLegendInfo.STOCK_CHART);
	//饼图
	session.setAttribute("PIE_CHART", DwmisLegendInfo.PIE_CHART);
	//柱状图
	session.setAttribute("COLUMN_CHART", DwmisLegendInfo.COLUMN_CHART);
	//折线图
	session.setAttribute("LINE_CHART", DwmisLegendInfo.LINE_CHART);
	//组合图
	session.setAttribute("COLUMNORLINE_CHART", DwmisLegendInfo.COLUMNORLINE_CHART);
	//位置图形
	session.setAttribute("AREA_CHART", DwmisLegendInfo.AREA_CHART);		
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改关联栏目--${moduleInfo.moduleName}</title>
<link type="text/css" rel="stylesheet" href="../css/main.css" />
<script src="<%=basePath%>js/jquery-1.5.1.min.js" type="text/javascript"></script>
<script type="text/javascript"
	src="<%=basePath%>/js/jquery-1.5.1.min.js"></script>
<!-- 弹窗JS -->
<!--  
<script type="text/javascript" src="<%=basePath%>/js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>
-->
<!-- 树形图表 -->
<link rel="stylesheet"
	href="<%=basePath%>/js/zTree/css/zTreeStyle/zTreeStyle.css"
	type="text/css">

<script type="text/javascript"
	src="<%=basePath%>/js/zTree/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/js/zTree/js/jquery.ztree.core-3.2.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/js/zTree/js/jquery.ztree.exedit-3.2.js"></script>
<style type="text/css">
body {
	width: 100%;
	height: 100%;
	background-color: #f0f0f0;
	text-align: center;
}

.box {
	border-top-width: 1px;
	border-right-width: 0px;
	border-bottom-width: 0px;
	border-left-width: 1px;
	border-top-style: solid;
	border-right-style: solid;
	border-bottom-style: solid;
	border-left-style: solid;
	border-top-color: #000000;
	border-right-color: #000000;
	border-bottom-color: #000000;
	border-left-color: #000000;
}

.box td {
	border-top-width: 0px;
	border-right-width: 1px;
	border-bottom-width: 1px;
	border-left-width: 0px;
	border-top-style: solid;
	border-right-style: solid;
	border-bottom-style: solid;
	border-left-style: solid;
	border-top-color: #000000;
	border-right-color: #000000;
	border-bottom-color: #000000;
	border-left-color: #000000;
	font-size: 13px;
	word-break: break-all;
}

.td_input {
	border-right: 0px #253E7A solid;
	border-left: 0px #253E7A solid;
	border-top: 0px #253E7A solid;
	border-bottom: 1px #253E7A solid;
	background-color: #ffffff;
	width: 95%;
	height: 99%;
	font-size: 9pt;
}

.td_label {
	border-right: 0px #253E7A solid;
	border-left: 0px #253E7A solid;
	border-top: 0px #253E7A solid;
	border-bottom: 0px #253E7A solid;
	background-color: #ffffff;
	font-size: 9pt;
}
</style>
<script type="text/javascript">
//改变位置
function changePosition(crtObj)
{
	<c:if test="${not empty moduleInfo.moduleId}">
		crtObj.value="${moduleInfo.position}";
	    return;
    </c:if>
	var crtVal=crtObj.value;
	if(crtVal==<%=DwmisModuleInfo.POSITION_LEFT%>)
	{
	   //修改坐标XY位置
       document.getElementById("module_positionX_${moduleInfo.moduleId}").value="13";
	   document.getElementById("module_positionY_${moduleInfo.moduleId}").value="999";
	   //修改宽度
	   document.getElementById("module_width_${moduleInfo.moduleId}").value="455";//
	}else if(crtVal==<%=DwmisModuleInfo.POSITION_RIGHT%>)
	{
       document.getElementById("module_positionX_${moduleInfo.moduleId}").value="465";
	   document.getElementById("module_positionY_${moduleInfo.moduleId}").value="999";
	   document.getElementById("module_width_${moduleInfo.moduleId}").value="455";//
	}else 
	{
       document.getElementById("module_positionX_${moduleInfo.moduleId}").value="13";
	   document.getElementById("module_positionY_${moduleInfo.moduleId}").value="999";
	   document.getElementById("module_width_${moduleInfo.moduleId}").value="999";//
	}
}

</script>
<style style='text/css'>
.imgDiv {
	text-align: left;
	margin-right: auto;
	margin-left: auto;
}

.content .left {
	float: left;
	width: 100%;
	border: 0px solid #FF0000;
	margin: 3px;
}

.content .center {
	float: left;
	border: 1px solid #FF0000;
	margin: 3px;
	width: 57%
}

.content .right {
	float: right;
	width: 20%;
	border: 1px solid #FF0000;
	margin: 3px
}

.mo {
	height: auto;
	border: 1px solid #CCC;
	margin: 3px;
	background: #FFF;
}

.mo .nr {
	height: 130px;
	border: 1px solid #F3F3F3;
	margin: 2px
}

h1 {
	margin: 0px;
	padding: 0px;
	text-align: left;
	font-size: 12px
}
</style>
<SCRIPT type="text/javascript">		
var MoveTest = {
			errorMsg: "此图例已存在",
			curTarget: null,
			curTmpTarget: null,
			noSel: function() {
				try {
					window.getSelection ? window.getSelection().removeAllRanges() : document.selection.empty();
				} catch(e){}
			},
			dragTree2Dom: function(treeId, treeNodes) {
				if(treeNodes[0].isParent) return false;
				var position=document.getElementById("position").value;
				if(position==<%=DwmisModuleInfo.POSITION_LEFT%>||position==<%=DwmisModuleInfo.POSITION_RIGHT%>){
					if(document.getElementById("imgDiv").childNodes!=null&&document.getElementById("imgDiv").childNodes.length>=2)
				     {
				    	 alert("侧边栏最多选两个图例");
				    	 return false;
				     }else
				     {
				    	 var legendNodes=document.getElementById("imgDiv").childNodes;
				    	 for(var i=0;i<legendNodes.length;i++)
				    	 {
				    		 if(legendNodes[i]==null) continue;
				    		 if(legendNodes[i].id==treeNodes[0].id)
				    		 {
				    			alert('此图例已选择');
				    			return false;
				    		 }
				    	 }
						return true;
				     }
				}else{
					if(document.getElementById("imgDiv").childNodes!=null&&document.getElementById("imgDiv").childNodes.length>=4)
				     {
						alert("底边栏最多选四个图例");
				    	 return false;
				     }else
				     {
				    	 var legendNodes=document.getElementById("imgDiv").childNodes;
				    	 for(var i=0;i<legendNodes.length;i++)
				    	 {
				    		 if(legendNodes[i]==null) continue;
				    		 if(legendNodes[i].id==treeNodes[0].id)
				    		 {
				    			alert('此图例已选择');
				    			return false;
				    		 }
				    	 }
						return true;
				     }
				}

			},
			prevTree: function(treeId, treeNodes, targetNode) {
				return !targetNode.isParent && targetNode.parentTId == treeNodes[0].parentTId;
			},
			nextTree: function(treeId, treeNodes, targetNode) {
				return !targetNode.isParent && targetNode.parentTId == treeNodes[0].parentTId;
			},
			innerTree: function(treeId, treeNodes, targetNode) {
				return targetNode!=null && targetNode.isParent && targetNode.tId == treeNodes[0].parentTId;
			},
			//选择图例
			dropTree2Dom: function(e, treeId, treeNodes, targetNode, moveType) {
			　　　//判断是否选择了,如果已存在的，不再新增
			     if(document.getElementById("imgDiv").childNodes!=null&&document.getElementById("imgDiv").childNodes.length>=4)
			     {
			    	 alert('最多只能有四个图例');
			    	 return;
			     }
				 //$("#imgDiv").append("<img src='"+treeNodes[0].chartType+"' width='150px' height='150px'>");//createGraphHTML
				 $("#imgDiv").append(createGraphHTML(treeNodes[0].id,treeNodes[0].name,treeNodes[0].chartType));
				 reInit();
			},
			bindSelect: function() {
				return false;
			}
		};


		$(document).ready(function(){
			var setting = {
					edit: {
						enable: true,
						showRemoveBtn: false,
						showRenameBtn: false,
						drag: {
							prev: MoveTest.prevTree,
							next: MoveTest.nextTree,
							inner: MoveTest.innerTree
						}
					},
					data: {
						keep: {
							parent: true,
							leaf: true
						},
						simpleData: {
							enable: true
						}
					},
					callback: {
						beforeDrag: MoveTest.dragTree2Dom,
						onDrop: MoveTest.dropTree2Dom
					},
					view: {
						selectedMulti: false
					}
				};

				var zNodes =[
				<c:forEach items="${legendCategoryList}" var="legendCategory">  
					{ id:'${legendCategory.categoryId}', pId:0, name:"${legendCategory.categoryName}", isParent: true, open:true,icon:"<%=basePath%>images/dwmis/folder.png"},
				</c:forEach >	
				<c:forEach items="${legendInfoUnRelList}" var="legendInfoUnRel">
				<c:choose>
			       <c:when test="${legendInfoUnRel.chartType==STOCK_CHART}">
			         { id:'${legendInfoUnRel.legendId}', pId:'${legendInfoUnRel.categoryId}', name:"${legendInfoUnRel.legendName}",chartType:"<%=basePath%>images/dwmis/stock.jpg",icon:"<%=basePath%>images/dwmis/tendency.jpg"},
			       </c:when>
			       <c:when test="${legendInfoUnRel.chartType==PIE_CHART}">
			         { id:'${legendInfoUnRel.legendId}', pId:'${legendInfoUnRel.categoryId}', name:"${legendInfoUnRel.legendName}",chartType:"<%=basePath%>images/dwmis/pie.jpg",icon:"<%=basePath%>images/dwmis/pie_min.jpg"},
			       </c:when>
				   <c:when test="${legendInfoUnRel.chartType==COLUMN_CHART}">
				         { id:'${legendInfoUnRel.legendId}', pId:'${legendInfoUnRel.categoryId}', name:"${legendInfoUnRel.legendName}",chartType:"<%=basePath%>images/dwmis/column.jpg",icon:"<%=basePath%>images/dwmis/column_min.jpg"},
				   </c:when>
				   <c:when test="${legendInfoUnRel.chartType==LINE_CHART}">
				         { id:'${legendInfoUnRel.legendId}', pId:'${legendInfoUnRel.categoryId}', name:"${legendInfoUnRel.legendName}",chartType:"<%=basePath%>images/dwmis/line.jpg",icon:"<%=basePath%>images/dwmis/thread.jpg"},
				   </c:when>
				   <c:when test="${legendInfoUnRel.chartType==COLUMNORLINE_CHART}">
					         { id:'${legendInfoUnRel.legendId}', pId:'${legendInfoUnRel.categoryId}', name:"${legendInfoUnRel.legendName}",chartType:"<%=basePath%>images/dwmis/column_line.jpg",icon:"<%=basePath%>images/dwmis/column_line_min.jpg"},
				   </c:when>
				   <c:when test="${legendInfoUnRel.chartType==AREA_CHART}">
					         { id:'${legendInfoUnRel.legendId}', pId:'${legendInfoUnRel.categoryId}', name:"${legendInfoUnRel.legendName}",chartType:"<%=basePath%>images/dwmis/area.jpg",icon:"<%=basePath%>images/dwmis/area_min.jpg"},
				   </c:when>				         
			       <c:otherwise>
			         { id:'${legendInfoUnRel.legendId}', pId:'${legendInfoUnRel.categoryId}', name:"${legendInfoUnRel.legendName}",chartType:"<%=basePath%>images/dwmis/pie.jpg",icon:"<%=basePath%>images/dwmis/pie_min.jpg"},
			       </c:otherwise>
			     </c:choose>			       
				</c:forEach >
				];
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
		});
		</SCRIPT>
<script type="text/javascript">
	var dragobj = {};
	window.onerror = function() {
		return false;
	}
	function on_ini() {
		String.prototype.inc = function(s) {
			return this.indexOf(s) > -1 ? true: false;
		};
		var agent = navigator.userAgent;
		window.isOpr = agent.inc("Opera");
		window.isIE = agent.inc("IE") && !isOpr;
		window.isMoz = agent.inc("Mozilla") && !isOpr && !isIE;
		if (isMoz) {
			Event.prototype.__defineGetter__("x",
			function() {
				return this.clientX + 2;
			});
			Event.prototype.__defineGetter__("y",
			function() {
				return this.clientY + 2;
			});
		}
		basic_ini();
	}
	function basic_ini() {
		window.$$ = function(obj) {
			return typeof(obj) == "string" ? document.getElementById(obj) : obj
		};
		window.oDel = function(obj) {
			if ($$(obj) != null) {
				$$(obj).parentNode.removeChild($$(obj))
			}
		};
	}
	window.onload = function() {
		on_ini();
		//默认加载图例
		initLoadGraph();
		var allDiv=document.getElementsByTagName("div");
		for (var i = 0; i < allDiv.length; i++) {
		    if(allDiv[i].className!="moveClass") continue;
			allDiv[i].onmousedown = function(e) {
				if (dragobj.o != null) return false;
				e = e || event;
				var left = this.parentNode.offsetLeft;
				var top = this.parentNode.offsetTop;
				dragobj.o = this.parentNode;
				dragobj.xy = getxy(this.parentNode);
				dragobj.xx = new Array((e.x - dragobj.xy[1]), (e.y - dragobj.xy[0]));
				dragobj.o.style.width = dragobj.xy[2] + "px";
				dragobj.o.style.height = dragobj.xy[3] + "px";
				dragobj.o.style.left = (e.x - dragobj.xx[0]) + "px";
				dragobj.o.style.top = (e.y - dragobj.xx[1]) + "px";
				dragobj.o.style.position = "absolute";
				var om = document.createElement("div");
				om.style.border = "0px dashed #000"; //border:1px dashed #000
				om.style.width = this.parentNode.style.width;
				om.style.float = "left";
				om.innerHTML = "";
				dragobj.otemp = om;
				om.style.width = dragobj.xy[2] + "px";
				om.style.height = dragobj.xy[3] + "px";
				dragobj.o.parentNode.insertBefore(om, dragobj.o);
				return false;
			}
		}
		//默认选中
		if(document.getElementById("column_Type_${moduleInfo.moduleType}")!=null)
		{
			document.getElementById("column_Type_${moduleInfo.moduleType}").checked=true;
		}
		if(dg!=null&&dg.curWin!=null)
		{
			if(dg.curWin.document.getElementById("moduleName_<%=moduleId%>")!=null)
			{
				document.getElementById("moduleName").value=dg.curWin.document.getElementById("moduleName_<%=moduleId%>").value;
				document.getElementById("moduleHeight").value=dg.curWin.document.getElementById("module_height_<%=moduleId%>").value;
				document.getElementById("remark").value=dg.curWin.document.getElementById("remark_<%=moduleId%>").value;
				document.getElementById("tabShow").value=dg.curWin.document.getElementById("module_<%=moduleId%>_tabShowopt").value;
				document.getElementById("position").value=dg.curWin.document.getElementById("module_position_<%=moduleId%>").value;
				document.getElementById("module_positionX_<%=moduleId%>").value=dg.curWin.document.getElementById("module_positionX_<%=moduleId%>").value;
				document.getElementById("module_positionY_<%=moduleId%>").value=dg.curWin.document.getElementById("module_positionY_<%=moduleId%>").value;
			}else{
				var moduleNameVal=document.getElementById("moduleName").value;
				if(moduleNameVal==null||moduleNameVal.length==0)
				{
					document.getElementById("moduleName").value="自定义模块";
				}
				var moduleHeight=document.getElementById("moduleHeight").value;
				if(moduleHeight==null||moduleHeight.length==0||parseInt(moduleHeight)<=0)
				{
					document.getElementById("moduleHeight").value=300;
				}
			}
		}
		if("${moduleId}"==null||"${moduleId}".length==0){
		       document.getElementById("module_positionX_${moduleInfo.moduleId}").value="455";
               document.getElementById("module_positionY_${moduleInfo.moduleId}").value="999";
               document.getElementById("position").value=<%=DwmisModuleInfo.POSITION_BOTTOM%>;
		}
	}
	document.onselectstart = function() {
		return false;
	}
	window.onfocus = function() {
		document.onmouseup();
	};
	window.onblur = function() {
		document.onmouseup();
	};
	document.onmouseup = function() {
		if (dragobj.o != null) {
			dragobj.o.style.height = "auto";
			dragobj.otemp.parentNode.insertBefore(dragobj.o, dragobj.otemp);
			dragobj.o.style.position = "";
			oDel(dragobj.otemp);
			dragobj = {};
		}
	}
	document.onmousemove = function(e) {
		e = e || event;
		if (dragobj.o != null) {
			dragobj.o.style.left = (e.x - dragobj.xx[0]) + "px";
			dragobj.o.style.top = (e.y - dragobj.xx[1]) + "px";
			createtmpl(e);
		}
	}
	function getxy(e) {
		var a = new Array();
		var t = e.offsetTop;
		var l = e.offsetLeft;
		var w = e.offsetWidth;
		var h = e.offsetHeight;
		while (e = e.offsetParent) {
			t += e.offsetTop;
			l += e.offsetLeft;
		}
		a[0] = t;
		a[1] = l;
		a[2] = w;
		a[3] = h;
		return a;
	}
	function inner(o, e) {
		var a = getxy(o);
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
		var allDiv=document.getElementsByTagName("div");
		for (var i = 0; i < allDiv.length; i++) {
			if(allDiv[i].className!="mo") continue;
			if (allDiv[i] == dragobj.o) continue;
			var b = inner(allDiv[i], e);
			if (b == 0) continue;
			dragobj.otemp.style.width = allDiv[i].offsetWidth;
			if (b == 1) {
				allDiv[i].parentNode.insertBefore(dragobj.otemp, allDiv[i]);
			} else {
				if (allDiv[i].nextSibling == null) {
					allDiv[i].parentNode.appendChild(dragobj.otemp);
				} else {
					allDiv[i].parentNode.insertBefore(dragobj.otemp, allDiv[i].nextSibling);
				}
			}
			return;
		}
	}
	function reInit() 
	{
		on_ini();
		var allDiv=document.getElementsByTagName("div");
		for (var i = 0; i < allDiv.length; i++) {
		    if(allDiv[i].className!="moveClass") continue;
			allDiv[i].onmousedown = function(e) {
				if (dragobj.o != null) return false;
				e = e || event;
				var left = this.parentNode.offsetLeft;
				var top = this.parentNode.offsetTop;
				dragobj.o = this.parentNode;
				dragobj.xy = getxy(this.parentNode);
				dragobj.xx = new Array((e.x - dragobj.xy[1]), (e.y - dragobj.xy[0]));
				dragobj.o.style.width = dragobj.xy[2] + "px";
				dragobj.o.style.height = dragobj.xy[3] + "px";
				dragobj.o.style.left = (e.x - dragobj.xx[0]) + "px";
				dragobj.o.style.top = (e.y - dragobj.xx[1]) + "px";
				dragobj.o.style.position = "absolute";
				var om = document.createElement("div");
				om.style.border = "0px dashed #000"; //border:1px dashed #000
				om.style.width = this.parentNode.style.width;
				om.style.float = "left";
				om.innerHTML = "";
				dragobj.otemp = om;
				om.style.width = dragobj.xy[2] + "px";
				om.style.height = dragobj.xy[3] + "px";
				dragobj.o.parentNode.insertBefore(om, dragobj.o);
				return false;
			}
		}
	}
	function removeGraph(id)
	{
		var tmpDiv=document.getElementById(id);
		if(tmpDiv!=null)
		{
			document.getElementById("imgDiv").removeChild(tmpDiv);
		}
	}
	function createGraphHTML(id,divName,imgSrc)
	{
	   var divHtml="<div class=mo id='"+id+"' style='float:left;width:135px;display:;'>"+
	   				"<span style='float: right;padding-right: 5px;padding-top:0px;top:5px;"+
	   				"position: relative;color: red;font-size:12px;cursor:pointer;'  title='删除'  onclick='removeGraph(\""+id+"\");'>X"+
	   				"</span><div class='moveClass' style='background: #ECF9FF;height: 18px;font-weight: bold;border-bottom: 1px solid #CCC;padding-top:5px;'>"+
	   				"<div class='h1' style='float: left;font-size:12px;vertical-align:middle;text-align:left;padding-left: 5px;cursor:move;border:0px solid red;width:110px;text-overflow:ellipsis; white-space:nowrap; overflow:hidden;' title='"+divName+"'>"
	   				+divName+"</div></div><div class='nr' style='border:0px solid red;'><img src='"
	   				+imgSrc+"' width='130px' height='130px' border='0' style='padding-top:-500px;'></div></div>";
	   return divHtml;
	}
	function initLoadGraph()
	{
	   //默认加载折线图
	   <c:forEach items="${legendInfoRelList}" var="legendInfoRel">
	     <c:choose>
	       <c:when test="${legendInfoRel.chartType==STOCK_CHART}">
	          //stock
	          $("#imgDiv").append(createGraphHTML("${legendInfoRel.legendId}","${legendInfoRel.legendName}","<%=path%>/images/dwmis/stock.jpg"));
	       </c:when>
	       <c:when test="${legendInfoRel.chartType==PIE_CHART}">
	          //pie
	          $("#imgDiv").append(createGraphHTML("${legendInfoRel.legendId}","${legendInfoRel.legendName}","<%=path%>/images/dwmis/pie.jpg"));
	       </c:when>
	       <c:when test="${legendInfoRel.chartType==COLUMN_CHART}">
	          //column
	          $("#imgDiv").append(createGraphHTML("${legendInfoRel.legendId}","${legendInfoRel.legendName}","<%=path%>/images/dwmis/column.jpg"));
	       </c:when>
	       <c:when test="${legendInfoRel.chartType==LINE_CHART}">
	          //line
	          $("#imgDiv").append(createGraphHTML("${legendInfoRel.legendId}","${legendInfoRel.legendName}","<%=path%>/images/dwmis/line.jpg"));
	       </c:when>
	       <c:when test="${legendInfoRel.chartType==COLUMNORLINE_CHART}">
	          //column_line
	          $("#imgDiv").append(createGraphHTML("${legendInfoRel.legendId}","${legendInfoRel.legendName}","<%=path%>/images/dwmis/column_line.jpg"));
	       </c:when>
	       <c:when test="${legendInfoRel.chartType==AREA_CHART}">
	          //area
	          $("#imgDiv").append(createGraphHTML("${legendInfoRel.legendId}","${legendInfoRel.legendName}","<%=path%>/images/dwmis/area.jpg"));
	       </c:when>	       
	       <c:otherwise>
	          $("#imgDiv").append(createGraphHTML("${legendInfoRel.legendId}","${legendInfoRel.legendName}","<%=path%>/images/dwmis/column_line.jpg"));
	       </c:otherwise>
	     </c:choose>
	   </c:forEach>
	}
</script>
</head>
<body>
	<!--模块类型-->
	<div style=" position: absolute;right:10px;margin-top: 395px;">提示：<font color='red' size='2'>请选择图例，并拖拽到右边空白处</font></div>
	<!--宽度-->
	<input type="hidden" name="width" value="${moduleInfo.width}"
		id="module_width_${moduleInfo.moduleId}">
		<input type="hidden" name="width" value="${moduleInfo.position}"
		id="module_width_${moduleInfo.position}">	
	<input type="hidden" name="moduleSort" value="${moduleInfo.moduleSort}"
		id="module_sort_${moduleInfo.moduleId}">
	<!--是否显示-->
	<input type="hidden" value="${moduleInfo.isShow}" name="isShow"
		id="module_is_show">
	<form action="<%=basePath%>dwmisModule/saveModuleInfo.html"
		name="indexMenuForm" id="indexMenuForm" target="result" method="post">
		<div style="position: absolute; left: 10px; bottom: 5px;"></div>
		<!--下面的对新增的有效-->
			<!--坐标-->
		<input type="hidden" name="positionX" value="${moduleInfo.positionX}" id="module_positionX_${moduleId}">
		<input type="hidden" name="positionY" value="${moduleInfo.positionY}" id="module_positionY_${moduleId}">
		<input type="hidden" name="subjectId" value="${subjectId}" /> 
		<input type="hidden" name="isShow" value="1" /> 
		<input type="hidden" name="moduleId" id="moduleId" value="${moduleId}" />
		<!-- legendIds -->
		<input type="hidden" name="legendIds" value="" id="legendIds" />
		<fieldset>
			<legend>
				<b>模块信息</b>
			</legend>
			<div>
				<table width='100%' border='0' cellpadding='0' cellspacing='3'>
					<tr>
						<td width="15%">模块名称：</td>
						<td><input type="text" name="moduleName"
							value="${moduleInfo.moduleName}" onfocus='this.select();'
							id="moduleName"></td>
						<td width="15%">模块高度：</td>
						<td width="15%"><input type="text" name="height"
							value="${moduleInfo.height}" onfocus='this.select();'
							onkeyup="value=value.replace(/[^\d]/g,'')" id="moduleHeight"
							style='ime-mode: disabled;width:100px;'></td>
						<!--如果非新增，则不能修改-->
						<td width="15%">显示位置：</td>
						<td><select name="position" style="width: 80px;"
							id="position" onchange="changePosition(this);">
								<option value="<%=DwmisModuleInfo.POSITION_LEFT%>"
									style="color: #000000;"
									<c:if test="${moduleInfo.position eq POSITION_LEFT}">selected</c:if>>左边</option>
								<option value="<%=DwmisModuleInfo.POSITION_RIGHT%>"
									style="color: blue;"
									<c:if test="${moduleInfo.position eq POSITION_RIGHT}">selected</c:if>>右边</option>
								<option value="<%=DwmisModuleInfo.POSITION_BOTTOM%>"
									style="color: red;"
									<c:if test="${moduleInfo.position eq POSITION_BOTTOM}">selected</c:if>>底边</option>
						</select></td>
						<td width="15%">显示方式：</td>
						<td><select name="tabShow" style="width: 80px;" id="tabShow"
							onchange="">
								<option value="0"
									<c:if test="${moduleInfo.tabShow eq 0}">selected</c:if>>并排显示</option>
								<option value="1"
									<c:if test="${moduleInfo.tabShow eq 1}">selected</c:if>>Tab显示</option>
						</select></td>
					</tr>
					<tr>
						<td>帮助信息：</td>
						<td colspan="8" align='left'><textarea
								style="width: 99%; height: 30px;" name="remark" id="remark"
								<c:if test="${not empty moduleInfo.moduleId}"></c:if>>${moduleInfo.remark}</textarea>
						</td>
					</tr>
				</table>
			</div>
		</fieldset>
		<fieldset >
			<legend>
				<b>关联图例</b>
			</legend>
			<table border='0' width='100%' height="300px;">
				<tr>
					<td width='14%' valign='top'>
						<div class="zTreeDemoBackground left"
							style='margin-left: 0px; border: 0px solid; overflow: auto; height: 295px;'>
							<ul id="treeDemo" class="ztree"></ul>
						</div>
					</td>
					<td width="85%">
						<div class="content" style='margin-top:-5px;'>
							<div id='imgDiv' class="left"
								style='border: 0px solid red; height: 295px; overflow: auto;width: 100%;'></div>
						</div>
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
	<iframe name="result" id="result" src="about:blank" frameborder="0"
		width="0" height="0"></iframe>
</body>
</html>
<script type="text/javascript">
var dg;
$(document).ready(function(){
	dg = frameElement.lhgDG;
	if(dg!=null)
	{
		dg.addBtn('ok','保存',function(){
				var moduleNameVal=document.getElementById("moduleName").value;
				if(moduleNameVal==null||moduleNameVal.length==0)
				{
					alert("模块名不能为空");
                    return;
				}
				var moduleHeight=document.getElementById("moduleHeight").value;
				if(moduleHeight==null||moduleHeight.length==0||parseInt(moduleHeight)<=0)
				{
					alert("高度必须大于零");
					return;
				}
				var relLegendInfos="";
				if(document.getElementById("imgDiv").childNodes==null||document.getElementById("imgDiv").childNodes.length==0)
				{
				   alert('必须选择一个图例');
				   return;
				}else 
				{
				   var legendNodes=document.getElementById("imgDiv").childNodes;
				   for(var i=0;i<legendNodes.length;i++)
				    {
					   if(legendNodes[i]==null) continue;
					   if(relLegendInfos.length>0) relLegendInfos+=",";
					   relLegendInfos+=legendNodes[i].id;//图例ID
					}
				}
	  	   		document.getElementById("legendIds").value=relLegendInfos;
// 	  	   		parent.saveModuleInfo();
	  	   		//obj1.options.selected = false;
			    if(!confirm("确认保存")) return;
			    $("#indexMenuForm").submit();
		});
	}
});
    Array.prototype.indexOf = function(val) 
    	{
            for (var i = 0; i < this.length; i++) 
            {
                if (this[i] == val) return i;
            }
            return -1;
        };
	Array.prototype.remove = function(val) 
	{
		var index = this.indexOf(val);
		if (index > -1) 
		{
			this.splice(index, 1);
		}
	};
    //保存成功
	function success(){
	   alert('保存成功');
	   //刷新父页面
	   dg.curWin.location.reload();
	   //关闭窗口
	   dg.cancel();
	}
	//保存失败
	function failed(){
	   alert('保存失败');
	}
</script>
