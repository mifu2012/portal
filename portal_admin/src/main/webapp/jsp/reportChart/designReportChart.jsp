<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.Date"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>图表设计</title>
<meta name="Generator" content="EditPlus">
<meta name="Author" content="">
<meta name="Keywords" content="">
<meta name="Description" content="">
<script src="<%=basePath%>/js/jquery-1.5.1.min.js"
	type="text/javascript"></script>
<link type="text/css" rel="stylesheet" href="<%=basePath%>/css/main.css" />
<script type="text/javascript"
	src="<%=basePath%>/js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>
<!--用于保存图表位置或大小的变化-->
<script src="<%=basePath%>js/collection/HashMap.js"
	type="text/javascript"></script>
<script src="<%=basePath%>js/collection/JavaString.js"
	type="text/javascript"></script>
<script type="text/javascript">
//图表信息
var  dragresize;
//定义饼图的高宽度
var imgWidth=150;
var imgHeight=150;
//用来判断添加的是不是饼图
var addChartType=false;
function chartInfo(chartId,left,top,width,height)
{
	this.chartId=chartId;
	this.left=left;
	this.top=top;
	this.width=width;
	this.height=height;
}
//初始化画板
window.onload=function()
{
    var clientWidth = 765;//前台页面宽度原来800
    dragresize = new DragResize('dragresize', {
		minWidth: 200,
		minHeight: 160,
		minLeft: 10,
		minTop: 30,
		maxLeft: clientWidth,
		maxTop: 750
	});

	dragresize.isElement = function(elm) {
		if (elm.className && elm.className.indexOf('alixixi') > -1) return true;
	};
	dragresize.isHandle = function(elm) {
		if (elm.className && elm.className.indexOf('drsMoveHandle') > -1) return true;
	};

	dragresize.ondragfocus = function() {};
	dragresize.ondragstart = function(isResize) {};
	dragresize.ondragmove = function(isResize) {};
	dragresize.ondragend = function(isResize) {};
	dragresize.ondragblur = function() {};

	dragresize.apply(document);

    <c:forEach items="${reportChartFiledList}" var="reportField">
	  chartInfoMap.put(new JavaString("${reportField.chartId}"),new chartInfo("${reportField.chartId}","${reportField.left}","${reportField.top}","${reportField.width}","${reportField.height}"));
	</c:forEach>
}

</script>
<script language='javascript'>
//当前图表ID
var crtChartId=null;
var chartInfoMap=new HashMap();
if (typeof addEvent != 'function') {
    var addEvent = function(o, t, f, l) {
        var d = 'addEventListener',
        n = 'on' + t,
        rO = o,
        rT = t,
        rF = f,
        rL = l;
        if (o[d] && !l) return o[d](t, f, false);
        if (!o._evts) o._evts = {};
        if (!o._evts[t]) {
            o._evts[t] = o[n] ? {
                b: o[n]
            }: {};
            o[n] = new Function('e', 'var r=true,o=this,a=o._evts["' + t + '"],i;for(i in a){o._f=a[i];r=o._f(e||window.event)!=false&&r;o._f=null}return r');
            if (t != 'unload') addEvent(window, 'unload',
            function() {
                removeEvent(rO, rT, rF, rL)
            })
        }
        if (!f._i) f._i = addEvent._i++;
        o._evts[t][f._i] = f
    };
    addEvent._i = 1;
    var removeEvent = function(o, t, f, l) {
        var d = 'removeEventListener';
        if (o[d] && !l) return o[d](t, f, false);
        if (o._evts && o._evts[t] && f._i) delete o._evts[t][f._i]
    }
}
function cancelEvent(e, c) {
    e.returnValue = false;
    if (e.preventDefault) e.preventDefault();
    if (c) {
        e.cancelBubble = true;
        if (e.stopPropagation) e.stopPropagation()
    }
};
function DragResize(myName, config) {
    var props = {
        myName: myName,
        enabled: true,
        handles: ['tl', 'tm', 'tr', 'ml', 'mr', 'bl', 'bm', 'br'],
        isElement: null,
        isHandle: null,
        element: null,
        handle: null,
        minWidth: 10,
        minHeight: 10,
        minLeft: 0,
        maxLeft: 9999,
        minTop: 0,
        maxTop: 9999,
        zIndex: 1,
        mouseX: 0,
        mouseY: 0,
        lastMouseX: 0,
        lastMouseY: 0,
        mOffX: 0,
        mOffY: 0,
        elmX: 0,
        elmY: 0,
        elmW: 0,
        elmH: 0,
        allowBlur: true,
        ondragfocus: null,
        ondragstart: null,
        ondragmove: null,
        ondragend: null,
        ondragblur: null
    };
    for (var p in props) this[p] = (typeof config[p] == 'undefined') ? props[p] : config[p]
};
DragResize.prototype.apply = function(node) {
    var obj = this;
    addEvent(node, 'mousedown',
    function(e) {
        obj.mouseDown(e)
    });
    addEvent(node, 'mousemove',
    function(e) {
        obj.mouseMove(e)
    });
	addEvent(node, 'keydown',
	function(e) {
		obj.keyDown(e)
	});
    addEvent(node, 'mouseup',
    function(e) {
        obj.mouseUp(e)
    })
};
DragResize.prototype.select = function(newElement) {
	//当前选中的图表ID：crtChartId
	crtChartId=newElement.id.substring(newElement.id.indexOf("_")+1);//newElement.id
	window.status="［坐标信息］X："+newElement.offsetLeft+" Y："+newElement.offsetTop+"  W："+newElement.offsetWidth+" H："+newElement.offsetHeight;
	document.getElementById("positionDiv").innerHTML="［坐标信息］Left："+newElement.offsetLeft+"&nbsp;&nbsp;Top："+newElement.offsetTop+"&nbsp;&nbsp;Width："+(newElement.offsetWidth-2)+"&nbsp;&nbsp;Height："+(newElement.offsetHeight-2);
	if(document.getElementById("t_line")!=null)
	{
		document.getElementById("t_line").style.display="";
		document.getElementById("t_line").style.position="absolute";
		document.getElementById("t_line").style.top=(newElement.offsetTop-6)+"px";
	}

	if(document.getElementById("b_line")!=null)
	{
		document.getElementById("b_line").style.display="";
		document.getElementById("b_line").style.position="absolute";
		document.getElementById("b_line").style.top=(newElement.offsetTop+newElement.offsetHeight-7)+"px";
	}

	if(document.getElementById("l_line")!=null)
	{
		document.getElementById("l_line").style.display="";
		document.getElementById("l_line").style.position="absolute";
		document.getElementById("l_line").style.left=newElement.offsetLeft+"px";
	}

	if(document.getElementById("r_line")!=null)
	{
		document.getElementById("r_line").style.display="";
		document.getElementById("r_line").style.position="absolute";
		document.getElementById("r_line").style.left=(newElement.offsetLeft+newElement.offsetWidth-1)+"px";
	}
    with(this) {
        if (!document.getElementById || !enabled) return;
        if (newElement && (newElement != element) && enabled) {
            element = newElement;
            element.style.zIndex = ++zIndex;
            if (this.resizeHandleSet) this.resizeHandleSet(element, true);
            elmX = parseInt(element.style.left);
            elmY = parseInt(element.style.top);
            elmW = element.offsetWidth-2;
            elmH = element.offsetHeight-2;
            if (ondragfocus) this.ondragfocus()
        }
    }
};
DragResize.prototype.deselect = function(delHandles) {
    with(this) {
        if (!document.getElementById || !enabled) return;
        if (delHandles) {
            if (ondragblur) this.ondragblur();
            if (this.resizeHandleSet) this.resizeHandleSet(element, false);
            element = null;
			crtChartId=null;//清空已选择的图表ID
			document.getElementById("positionDiv").innerHTML="提示：请选择图表，再拖拽排版";
        }
        handle = null;
        mOffX = 0;
        mOffY = 0;
    }
};
DragResize.prototype.mouseDown = function(e) {
	if(document.getElementById("t_line")!=null) document.getElementById("t_line").style.display="none";
	if(document.getElementById("b_line")!=null) document.getElementById("b_line").style.display="none";
	if(document.getElementById("l_line")!=null) document.getElementById("l_line").style.display="none";
	if(document.getElementById("r_line")!=null) document.getElementById("r_line").style.display="none";
    with(this) {
        if (!document.getElementById || !enabled) return true;
        var elm = e.target || e.srcElement,
        newElement = null,
        newHandle = null,
        hRE = new RegExp(myName + '-([trmbl]{2})', '');
        while (elm) {
            if (elm.className) {
                if (!newHandle && (hRE.test(elm.className) || isHandle(elm))) newHandle = elm;
                if (isElement(elm)) {
                    newElement = elm;
                    break
                }
            }
            elm = elm.parentNode
        }
        if (element && (element != newElement) && allowBlur) deselect(true);
        if (newElement && (!element || (newElement == element))) {
            if (newHandle) cancelEvent(e);
            select(newElement, newHandle);
            handle = newHandle;
            if (handle && ondragstart) this.ondragstart(hRE.test(handle.className))
        }
    }
};
DragResize.prototype.mouseMove = function(e) {
    with(this) {
        if (!document.getElementById || !enabled) return true;
        mouseX = e.pageX || e.clientX + document.documentElement.scrollLeft;
        mouseY = e.pageY || e.clientY + document.documentElement.scrollTop;
        var diffX = mouseX - lastMouseX + mOffX;
        var diffY = mouseY - lastMouseY + mOffY;
        mOffX = mOffY = 0;
        lastMouseX = mouseX;
        lastMouseY = mouseY;
        if (!handle) return true;
        var isResize = false;
        if (this.resizeHandleDrag && this.resizeHandleDrag(diffX, diffY)) {
            isResize = true
        } else {
            var dX = diffX,
            dY = diffY;
            if (elmX + dX < minLeft) mOffX = (dX - (diffX = minLeft - elmX));
            else if (elmX + elmW + dX > maxLeft) mOffX = (dX - (diffX = maxLeft - elmX - elmW));
            if (elmY + dY < minTop) mOffY = (dY - (diffY = minTop - elmY));
            elmX += diffX;
            elmY += diffY
        }
        with(element.style) {
            left = elmX + 'px';
            width = elmW + 'px';
            top = elmY + 'px';
            height = elmH + 'px'
        }
		window.status="［坐标信息］X："+elmX+" Y："+elmY+"  W："+elmW+" H："+elmH;
	    document.getElementById("positionDiv").innerHTML="［坐标信息］Left："+elmX+"&nbsp;&nbsp;Top："+elmY+"&nbsp;&nbsp;Width："+elmW+"&nbsp;&nbsp;Height："+elmH;
		if(document.getElementById("t_line")!=null)
		{
			document.getElementById("t_line").style.position="absolute";
			document.getElementById("t_line").style.zIndex=-10;
			document.getElementById("t_line").style.top=(elmY-6)+"px";
		}

		if(document.getElementById("b_line")!=null)
		{
			document.getElementById("b_line").style.position="absolute";
			document.getElementById("b_line").style.zIndex=-10;
			document.getElementById("b_line").style.top=(elmY+elmH-5)+"px";
		}

		if(document.getElementById("l_line")!=null)
		{
			document.getElementById("l_line").style.position="absolute";
			document.getElementById("l_line").style.zIndex=-10;
			document.getElementById("l_line").style.left=elmX+"px";
		}

		if(document.getElementById("r_line")!=null)
		{
			document.getElementById("r_line").style.position="absolute";
			document.getElementById("r_line").style.zIndex=-10;
			document.getElementById("r_line").style.left=(elmX+elmW+1)+"px";
		}
        if (window.opera && document.documentElement) {
            var oDF = document.getElementById('op-drag-fix');
            if (!oDF) {
                var oDF = document.createElement('input');
                oDF.id = 'op-drag-fix';
                oDF.style.display = 'none';
                document.body.appendChild(oDF)
            }
            oDF.focus()
        }
        if (ondragmove) this.ondragmove(isResize);
        cancelEvent(e)
    }
};
DragResize.prototype.mouseUp = function(e) {
    with(this) {
        if (!document.getElementById || !enabled) return;
        var hRE = new RegExp(myName + '-([trmbl]{2})', '');
        if (handle && ondragend) this.ondragend(hRE.test(handle.className));
        deselect(false)
    }
	if(crtChartId!=null&&crtChartId.length>0)
	{
		var chartInfo=chartInfoMap.get(new JavaString(crtChartId));
		if(chartInfo!=null)
		{
			//判断位置或大小是否变化了
			var crtChartDiv=document.getElementById("myDiv_"+crtChartId);
			var isChanged=false;
			if(crtChartDiv!=null)
			{
				var crtLeft=crtChartDiv.offsetLeft;
				var crtTop=crtChartDiv.offsetTop;
				var crtWidth=crtChartDiv.offsetWidth;
				var crtHeight=crtChartDiv.offsetHeight;
				if(crtLeft!=chartInfo.left||crtTop!=chartInfo.top||crtWidth!=chartInfo.width||crtHeight!=chartInfo.height)
				{
                   isChanged=true;
				}
			}
			if(isChanged)
			{
				//设置图片的left top值
				var divWidth=document.getElementById("div_"+crtChartId).offsetWidth;
			    var divHeight=document.getElementById("div_"+crtChartId).offsetHeight;
			    var imgWidth=document.getElementById("img_"+crtChartId).offsetWidth;
			    var imgHeight=document.getElementById("img_"+crtChartId).offsetHeight;
			    //添加的是饼图
			    if(addChartType==true){
			    	 document.getElementById("img_"+crtChartId).style.position="absolute";
			    }
			    document.getElementById("img_"+crtChartId).style.left=(divWidth-imgWidth)/2+"px";
			    document.getElementById("img_"+crtChartId).style.top=(divHeight-imgHeight)/2+"px";
               //保存位置和大小 */
               editSlowChart(crtChartId);
			}
		}
	}
};
DragResize.prototype.keyDown = function(e) {
	with(this) {
	   //38 向上 40 向下 37 向左 39向右
	   if(crtChartId!=null&&crtChartId.length>0&&document.getElementById("myDiv_"+crtChartId)!=null)
		{
		   var crtLeft=document.getElementById("myDiv_"+crtChartId).offsetLeft;
		   var crtTop=document.getElementById("myDiv_"+crtChartId).offsetTop;
		   var crtWidth=document.getElementById("myDiv_"+crtChartId).offsetWidth;
		   var crtHeight=document.getElementById("myDiv_"+crtChartId).offsetHeight;
		   if(e.keyCode==46)
			{
			   //删除图表
			   delReportChart(crtChartId);
			}else if(e.keyCode==39)
			{
				if(crtLeft+crtWidth<maxLeft)
				{
				   //向右
				   document.getElementById("myDiv_"+crtChartId).style.left=(crtLeft+1)+"px";
				   document.getElementById("positionDiv").innerHTML="［坐标信息］Left："+document.getElementById("myDiv_"+crtChartId).offsetLeft+"&nbsp;&nbsp;Top："+document.getElementById("myDiv_"+crtChartId).offsetTop+"&nbsp;&nbsp;Width："+(document.getElementById("myDiv_"+crtChartId).offsetWidth-2)+"&nbsp;&nbsp;Height："+(document.getElementById("myDiv_"+crtChartId).offsetHeight-2);
				   document.getElementById("l_line").style.left=(document.getElementById("l_line").offsetLeft+1)+"px";
				   document.getElementById("r_line").style.left=(document.getElementById("r_line").offsetLeft+1)+"px";
				}
			}else if(e.keyCode==37)
			{
				//小于最小边界
			   if(crtLeft>minLeft)
				{
				   //向左
				   document.getElementById("myDiv_"+crtChartId).style.left=(crtLeft-1)+"px";
				   document.getElementById("positionDiv").innerHTML="［坐标信息］Left："+document.getElementById("myDiv_"+crtChartId).offsetLeft+"&nbsp;&nbsp;Top："+document.getElementById("myDiv_"+crtChartId).offsetTop+"&nbsp;&nbsp;Width："+(document.getElementById("myDiv_"+crtChartId).offsetWidth-2)+"&nbsp;&nbsp;Height："+(document.getElementById("myDiv_"+crtChartId).offsetHeight-2);
				   document.getElementById("l_line").style.left=(document.getElementById("l_line").offsetLeft-1)+"px";
				   document.getElementById("r_line").style.left=(document.getElementById("r_line").offsetLeft-1)+"px";
				}
			}else if(e.keyCode==38)
			{
				//小于顶部
			   if(crtTop>minTop)
				{
				   //向上
				   document.getElementById("myDiv_"+crtChartId).style.top=(crtTop-1)+"px";
				   document.getElementById("positionDiv").innerHTML="［坐标信息］Left："+document.getElementById("myDiv_"+crtChartId).offsetLeft+"&nbsp;&nbsp;Top："+document.getElementById("myDiv_"+crtChartId).offsetTop+"&nbsp;&nbsp;Width："+(document.getElementById("myDiv_"+crtChartId).offsetWidth-2)+"&nbsp;&nbsp;Height："+(document.getElementById("myDiv_"+crtChartId).offsetHeight-2);
				   document.getElementById("t_line").style.top=(document.getElementById("myDiv_"+crtChartId).offsetTop-6)+"px";
				   document.getElementById("b_line").style.top=(document.getElementById("myDiv_"+crtChartId).offsetTop+crtHeight-7)+"px";
				}
			}else if(e.keyCode==40)
			{
				//小于顶部
			   if(crtTop+crtHeight+30<maxTop)
				{
				   //向下
				   document.getElementById("myDiv_"+crtChartId).style.top=(crtTop+1)+"px";
				   document.getElementById("positionDiv").innerHTML="［坐标信息］Left："+document.getElementById("myDiv_"+crtChartId).offsetLeft+"&nbsp;&nbsp;Top："+document.getElementById("myDiv_"+crtChartId).offsetTop+"&nbsp;&nbsp;Width："+(document.getElementById("myDiv_"+crtChartId).offsetWidth-2)+"&nbsp;&nbsp;Height："+(document.getElementById("myDiv_"+crtChartId).offsetHeight-2);
				   document.getElementById("t_line").style.top=(document.getElementById("myDiv_"+crtChartId).offsetTop-6)+"px";
				   document.getElementById("b_line").style.top=(document.getElementById("myDiv_"+crtChartId).offsetTop+crtHeight-7)+"px";
				}
			}
		}
	}
};
DragResize.prototype.resizeHandleSet = function(elm, show) {
    with(this) {
        if (!elm._handle_tr) {
            for (var h = 0; h < handles.length; h++) {
                var hDiv = document.createElement('div');
                hDiv.className = myName + ' ' + myName + '-' + handles[h];
                elm['_handle_' + handles[h]] = elm.appendChild(hDiv)
            }
        }
        for (var h = 0; h < handles.length; h++) {
            elm['_handle_' + handles[h]].style.visibility = show ? 'inherit': 'hidden'
        }
    }
};
DragResize.prototype.resizeHandleDrag = function(diffX, diffY) {
    with(this) {
        var hClass = handle && handle.className && handle.className.match(new RegExp(myName + '-([tmblr]{2})')) ? RegExp.$1: '';
        var dY = diffY,
        dX = diffX,
        processed = false;
        if (hClass.indexOf('t') >= 0) {
            rs = 1;
            if (elmH - dY < minHeight) mOffY = (dY - (diffY = elmH - minHeight));
            else if (elmY + dY < minTop) mOffY = (dY - (diffY = minTop - elmY));
            elmY += diffY;
            elmH -= diffY;
            processed = true
        }
        if (hClass.indexOf('b') >= 0) {
            rs = 1;
            if (elmH + dY < minHeight) mOffY = (dY - (diffY = minHeight - elmH));
            else if (elmY + elmH + dY > maxTop) mOffY = (dY - (diffY = maxTop - elmY - elmH));
            elmH += diffY;
            processed = true
        }
        if (hClass.indexOf('l') >= 0) {
            rs = 1;
            if (elmW - dX < minWidth) mOffX = (dX - (diffX = elmW - minWidth));
            else if (elmX + dX < minLeft) mOffX = (dX - (diffX = minLeft - elmX));
            elmX += diffX;
            elmW -= diffX;
            processed = true
        }
        if (hClass.indexOf('r') >= 0) {
            rs = 1;
            if (elmW + dX < minWidth) mOffX = (dX - (diffX = minWidth - elmW));
            else if (elmX + elmW + dX > maxLeft) mOffX = (dX - (diffX = maxLeft - elmX - elmW));
            elmW += diffX;
            processed = true
        }
        return processed
    }
};
</script>
<style type="text/css">
.alixixi {
	position: absolute;
	border: 1px solid #333;
}

/*
The main mouse handle that moves the whole element.
You can apply to the same tag as alixixi if you want.
*/
.drsMoveHandle {
	cursor: move;
	background-color: white;
	text-align: center;
}
/*
The DragResize object name is automatically applied to all generated
corner resize handles, as well as one of the individual classes below.
*/
.dragresize {
	position: absolute;
	width: 5px;
	height: 5px;
	font-size: 1px;
	background: #EEE;
	border: 1px solid #333;
}

/*
Individual corner classes - required for resize support.
These are based on the object name plus the handle ID.
*/
.dragresize-tl {
	top: -8px;
	left: -8px;
	cursor: nw-resize;
}

.dragresize-tm {
	top: -8px;
	left: 50%;
	margin-left: -4px;
	cursor: n-resize;
}

.dragresize-tr {
	top: -8px;
	right: -8px;
	cursor: ne-resize;
}

.dragresize-ml {
	top: 50%;
	margin-top: -4px;
	left: -8px;
	cursor: w-resize;
}

.dragresize-mr {
	top: 50%;
	margin-top: -4px;
	right: -8px;
	cursor: e-resize;
}

.dragresize-bl {
	bottom: -8px;
	left: -8px;
	cursor: sw-resize;
}

.dragresize-bm {
	bottom: -8px;
	left: 50%;
	margin-left: -4px;
	cursor: s-resize;
}

.dragresize-br {
	bottom: -8px;
	right: -8px;
	cursor: se-resize;
}
</style>
<script type="text/javascript">
function editField(chartId){
	var left=document.getElementById("myDiv_"+chartId).offsetLeft;
	var top=document.getElementById("myDiv_"+chartId).offsetTop;
	var width=document.getElementById("myDiv_"+chartId).offsetWidth;
	var height=document.getElementById("myDiv_"+chartId).offsetHeight;
	var dg = new $.dialog({
		title:'图表数据配置',
		id:'show_commoncode',
		width:510,
		height:450,
		iconTitle:false,
		cover:true,
		maxBtn:true,
		xButton:true,
		resize:true,
		page:"<%=path%>/reportChart/slowChartConfig.html?left=" + left
					+ "&top=" + top + "&width=" + (width-2) + "&height=" + (height-2)
					+ "&chartId=" + chartId+"&reportId=${reportId}"
		});
		dg.ShowDialog();
	}
	
	function editSlowChart(chartId){
		var left=document.getElementById("myDiv_"+chartId).offsetLeft;
		var top=document.getElementById("myDiv_"+chartId).offsetTop;
		var width=(document.getElementById("myDiv_"+chartId).offsetWidth)//*100/document.body.offsetWidth;
		var height=(document.getElementById("myDiv_"+chartId).offsetHeight);
		$.ajax({
			type:"POST",
			url:"<%=path%>/reportChart/saveSlowChart.html?chartId="+chartId+"&left="+left+"&top="+top+"&width="+(width-2)+"&height="+(height-2),
			success:function(msg){
			  chartInfoMap.put(new JavaString(chartId),new chartInfo(chartId,left,top,width,height));
			}
		})
		
	}
	//返回按钮
	function historyBack(){
		$.ajax({
			type:"POST",
			url:"<%=path%>/reportChart/chartChartConfig.html?reportId=${reportId}&random="+Math.random(),
			success:function(msg){
			  if(msg=="ok"){
				  location.href="<%=path%>/NewReport/designReportTemplate"+${reportId};
			  }else{
				  alert(msg);
			  }
			}
		})
	}
	//删除图表
	function delReportChart(chartId){
		if(confirm("确定删除?")){
			 $.ajax({
			        type: "POST",                                                                 
			        url:"<%=path%>/reportChart/delReportChart.html?chartId="+chartId,
			        success: function(msg){ 
			        	if(msg=="success"){
							if(document.getElementById("t_line")!=null) document.getElementById("t_line").style.display="none";
							if(document.getElementById("b_line")!=null) document.getElementById("b_line").style.display="none";
							if(document.getElementById("l_line")!=null) document.getElementById("l_line").style.display="none";
							if(document.getElementById("r_line")!=null) document.getElementById("r_line").style.display="none";
			        		document.getElementById("myDiv_"+chartId).style.display="none";
			        	}else{
			        		alert("删除失败");
			        	}
			          }
			    }); 
		}
	}
	function addSlowReportChart() {
		if($("#chartType").val()==0){
			alert("请选择图表类型");
			return false;
		}
		 $("#myForm").submit();
   }
    //创建图表
	function addChartSuccess(id,width,height){
		var chartType=document.getElementById("chartType").value;
		var div=document.createElement('div');
		div.className="alixixi";
		div.id="myDiv_"+id;
		div.name="myDiv_"+id;
		div.style.zIndex=999;
		div.style.top="30px";
		div.style.left="20px";
		div.title = "双击进行编辑";
		div.style.width=width+"px";
		div.style.height=height+"px";
		var imgSrc="";
		var divHtml="";
		if(chartType==1)
		{
			addChartType=true;
		   //饼图
		   imgSrc="<%=path%>/images/pie.png";
		   divHtml="<input type='hidden' id=chartType_"+id+" value="+id+"/><span style='position: absolute; right: 5px; top: 5px; cursor: pointer'; title='删除' onclick=delReportChart('"+id+"')><img src='<%=path%>/images/pic12.gif' /></span><div class='drsMoveHandle' style='height: 100%;width: 100%' ondblclick=editField('"+id+"') id=div_"+id+"><img id=img_"+id+"  src="+imgSrc+" style=height:"+imgHeight+"px;width:"+imgWidth+"px;position: absolute;left:0px;top:0px></img></div>";
		}else
		{
			addChartType=false;
		   if(chartType==2)
		   {
			  imgSrc="<%=path%>/images/spline.jpg";
		   }else if(chartType==3)
		   {
			  imgSrc="<%=path%>/images/column.jpg";
		   }else if(chartType==4)
		   {
			  imgSrc="<%=path%>/images/line.jpg";
		   }else
		   {
			  imgSrc="<%=path%>/images/column_line.jpg";
		   }
		   divHtml="<input type='hidden' id=chartType_"+id+" value="+id+"/><span style='position: absolute; right: 5px; top: 5px; cursor: pointer'; title='删除' onclick=delReportChart('"+id+"')><img src='<%=path%>/images/pic12.gif' /></span><div class='drsMoveHandle' style='height: 100%;width: 100%' ondblclick=editField('"+id+"') id=div_"+id+"><img id=img_"+id+"  src="+imgSrc+" style='height: 100%;width: 100%'></img></div>";
		}
		div.style.border="1px solid red";
		div.innerHTML=divHtml;
		document.getElementById("chartContainerDiv").appendChild(div);
		chartInfoMap.put(new JavaString(id),new chartInfo(id,"20","30",div.style.width,div.style.height)); 
	}
	function addChartFailed(){
		alert("添加失败")
	}
	//新增图表
	function addNewChart(chartType)
	{
		document.getElementById("chartType").value=chartType;
		//设置添加图片的默认高、宽度
		if(chartType==1){
			document.getElementById("width").value=200;
			document.getElementById("height").value=160;
		}else{
			document.getElementById("width").value=300;
			document.getElementById("height").value=200;
		}
		$("#myForm").submit();
	} 
</script>
<body style="overflow: hidden;">
	<!--坐标信息-->
	<div style="position: absolute; right: 10px; top: 8px;"
		id="positionDiv">提示：请选择图表，再拖拽排版</div>
	<div style="float: left">
		<table style='display: ;'>
			<tr>
				<td>新增图表：</td>
				<td width='20px'><img src="<%=path%>/images/dwmis/pie_min.jpg"
					title='饼图' style='cursor: pointer;' onmousedown="addNewChart(1);">
				</td>
				<td width='20px'><img src="<%=path%>/images/dwmis/tendency.jpg"
					title='折线图' style='cursor: pointer;' onclick="addNewChart(4);">
				</td>
				<td width='20px'><img
					src="<%=path%>/images/dwmis/column_min.jpg" title='柱状图'
					style='cursor: pointer;' onclick="addNewChart(3);"></td>
				<td width='20px'><img src="<%=path%>/images/dwmis/thread.jpg"
					title='趋势图' style='cursor: pointer;' onclick="addNewChart(2);">
				</td>
				<td width='20px'><img
					src="<%=path%>/images/dwmis/column_line_min.jpg" title='组合图'
					style='cursor: pointer;' onclick="addNewChart(5);"></td>
				<td style='display: none;'><form
						action="<%=path%>/reportChart/addReportChart.html" id="myForm"
						name="myForm" method="post" target="result">
						<input type="hidden" name="reportId" value="${reportId}" /> <input
							type="hidden" name="width" id="width" /> <input type="hidden"
							name="height" id="height" /> <select name="chartType"
							id="chartType" onchange="addSlowReportChart()">
							<option value="0">===请选择===</option>
							<option value="1">饼图</option>
							<option value="2">折线图</option>
							<option value="3">柱状图</option>
							<option value="4">折线图</option>
							<option value="5">折线+柱状图</option>
						</select>
					</form>
				</td>
				<td>&nbsp;&nbsp;<a href="javascript:historyBack();"
					class="myBtn" id="designHeader"><em>返&nbsp;回</em> </a></td>
			</tr>
		</table>
	</div>
	<hr
		style="width: 99%; height: 0px; position: absolute; top: 20px; *top: 28px; left: 0px; border: 1px solid gray;" />
	<!--显示选中或拖拽的位置-->
	<hr
		style='position: absolute; left: 0px; top: 50px; *margin-top: 5px; width: 790px; z-index: -10; display: none; color: gray;'
		id='t_line' />
	<hr
		style='position: absolute; left: 0px; top: 50px; *margin-top: 6px; width: 790px; z-index: -10; display: none; color: gray;'
		id='b_line' />
	<hr size="1000"
		style='position: absolute; left: 0px; top: 20px; *top: 28px; border-left: 1px solid gray; z-index: -10; display: none;'
		id='l_line' />
	<!--右边距线-->
	<hr size="1000"
		style='position: absolute; left: 0px; top: 20px; *top: 28px; border-left: 1px solid gray; z-index: -10; display: none;'
		id='r_line' />
	<!--显示选中或拖拽的位置-->
		<div style='position: absolute; left: 780px; top: 26px; *top: 28px; border-left: 2px solid gray; z-index: -10;height: 1000px'></div>
	<div id="chartContainerDiv"
		style='border: 0px solid red; height: 100%; width: 790px;'>
		<c:forEach items="${reportChartFiledList}" var="reportField">
			<div class="alixixi" name="myDiv_${reportField.chartId}"
				id="myDiv_${reportField.chartId}"
				style=";left:${reportField.left}px; top: ${reportField.top}px; width:${reportField.width}px; height:${reportField.height}px;border:1px solid #babdb4;"
				title="双击进行编辑">
				<!-- 隐藏图表的类型 -->
				<input type="hidden" id="chartType_${reportField.chartId}"
					value="${reportField.chartType}" />
				<c:choose>
					<c:when test="${reportField.chartType eq '1'}">
						<span
							style='position: absolute; right: 5px; top: 5px; cursor: pointer;'
							title='删除' onclick='delReportChart(${reportField.chartId})'><img
							src="<%=path%>/images/pic12.gif"> </span>
						<div class="drsMoveHandle" style="height: 100%; width: 100%"
							ondblclick="editField(${reportField.chartId})"
							id='div_${reportField.chartId}'>
							<img id='img_${reportField.chartId}'
								src="<%=path%>/images/pie.png"
								style="height: 0px; width: 0px; position: absolute; left: 0px; top: 0px;"></img>
						</div>
						<script type="text/javascript">
									document.getElementById("img_${reportField.chartId}").style.left=(${reportField.width}-imgWidth)/2+"px";
									document.getElementById("img_${reportField.chartId}").style.top=(${reportField.height}-imgHeight)/2+"px";
									document.getElementById("img_${reportField.chartId}").style.height=imgHeight+"px";
									document.getElementById("img_${reportField.chartId}").style.width=imgWidth+"px";
							</script>

					</c:when>
					<c:when test="${reportField.chartType eq '2'}">
						<span
							style='position: absolute; right: 5px; top: 5px; cursor: pointer;'
							title='删除' onclick='delReportChart(${reportField.chartId})'><img
							src="<%=path%>/images/pic12.gif"> </span>
						<div class="drsMoveHandle" style="height: 100%; width: 100%"
							ondblclick="editField(${reportField.chartId})"
							id='div_${reportField.chartId}'>
							<img id='img_${reportField.chartId}'
								src="<%=path%>/images/spline.jpg"
								style="height: 100%; width: 100%"></img>
						</div>
					</c:when>
					<c:when test="${reportField.chartType eq '3'}">
						<span
							style='position: absolute; right: 5px; top: 5px; cursor: pointer;'
							title='删除' onclick='delReportChart(${reportField.chartId})'><img
							src="<%=path%>/images/pic12.gif"> </span>
						<div class="drsMoveHandle" style="height: 100%; width: 100%"
							ondblclick="editField(${reportField.chartId})"
							id='div_${reportField.chartId}'>
							<img id='img_${reportField.chartId}'
								src="<%=path%>/images/column.jpg"
								style="height: 100%; width: 100%"></img>
						</div>
					</c:when>
					<c:when test="${reportField.chartType eq '4'}">
						<span
							style='position: absolute; right: 5px; top: 5px; cursor: pointer;'
							title='删除' onclick='delReportChart(${reportField.chartId})'><img
							src="<%=path%>/images/pic12.gif"> </span>
						<div class="drsMoveHandle" style="height: 100%; width: 100%"
							ondblclick="editField(${reportField.chartId})"
							id='div_${reportField.chartId}'>
							<img id='img_${reportField.chartId}'
								src="<%=path%>/images/line.jpg"
								style="height: 100%; width: 100%"></img>
						</div>
					</c:when>
					<c:when test="${reportField.chartType eq '5'}">
						<span
							style='position: absolute; right: 5px; top: 5px; cursor: pointer;'
							title='删除' onclick='delReportChart(${reportField.chartId})'><img
							src="<%=path%>/images/pic12.gif"> </span>
						<div class="drsMoveHandle" style="height: 100%; width: 100%"
							ondblclick="editField(${reportField.chartId})"
							id='div_${reportField.chartId}'>
							<img id='img_${reportField.chartId}'
								src="<%=path%>/images/column_line.jpg"
								style="height: 100%; width: 100%"></img>
						</div>
					</c:when>
				</c:choose>
			</div>
		</c:forEach>
	</div>
	<iframe name="result" id="result" src="about:blank" frameborder="10"
		width="0" height="0"></iframe>
</body>
</html>