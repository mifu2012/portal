//当前拖曳的对象
var dragobj={}
window.onerror=function(){return false}
function on_ini(){
	String.prototype.inc=function(s){return this.indexOf(s)>-1?true:false}
	var agent=navigator.userAgent
	window.isOpr=agent.inc("Opera")
	window.isIE=agent.inc("IE")&&!isOpr
	window.isMoz=agent.inc("Mozilla")&&!isOpr&&!isIE
	if(isMoz){
	   Event.prototype.__defineGetter__("x",function(){return this.clientX+2})
	   Event.prototype.__defineGetter__("y",function(){return this.clientY+2})
	}
	basic_ini()
}
function basic_ini(){
	window.$=function(obj){return typeof(obj)=="string"?document.getElementById(obj):obj}
	window.oDel=function(obj){if($(obj)!=null){$(obj).parentNode.removeChild($(obj))}}
}
window.onload=function(){
	on_ini()
	var o=document.getElementsByTagName("h1");
	var allDiv=document.getElementsByTagName("div");
	//alert(childNodes.length);
	for(var i=0;i<allDiv.length;i++){
	    if(allDiv[i].className.toLocaleLowerCase()!="module_title") continue;
		allDiv[i].childNodes[0].onmousedown=function(e){
			if(dragobj.o!=null)
			 return false
			e=e||event
			dragobj.o=this.parentNode.parentNode;
			dragobj.xy=getxy(dragobj.o)
			dragobj.xx=new Array((e.x-dragobj.xy[1]),(e.y-dragobj.xy[0]))
			dragobj.o.style.width=dragobj.xy[2]+"px"
			dragobj.o.style.height=dragobj.xy[3]+"px"
			dragobj.o.style.left=(e.x-dragobj.xx[0])+"px"
			dragobj.o.style.top=(e.y-dragobj.xx[1])+"px"   
			dragobj.o.style.position="absolute"
			var om=document.createElement("div")
			dragobj.otemp=om
			om.style.width=dragobj.xy[2]+"px"
			om.style.height=dragobj.xy[3]+"px"
			dragobj.o.parentNode.insertBefore(om,dragobj.o)
			return false
	   }
	}
}
document.onselectstart=function(){return false}
window.onfocus=function(){document.onmouseup()}
window.onblur=function(){document.onmouseup()}
document.onmouseup=function(){
	if(dragobj.o!=null){
	   dragobj.o.style.width="auto"
	   dragobj.o.style.height="auto"
	   dragobj.otemp.parentNode.insertBefore(dragobj.o,dragobj.otemp)
	   dragobj.o.style.position=""
	   oDel(dragobj.otemp)
	   dragobj={}
	}
	}
	document.onmousemove=function(e){
	e=e||event
	if(dragobj.o!=null){
	   dragobj.o.style.left=(e.x-dragobj.xx[0])+"px"
	   dragobj.o.style.top=(e.y-dragobj.xx[1])+"px"
	   createtmpl(e)
	}
}
function getxy(e){
	var a=new Array()
	var t=e.offsetTop;
	var l=e.offsetLeft;//控件左侧绝对位置
	var w=e.offsetWidth;
	var h=e.offsetHeight;
	while(e=e.offsetParent){
	   t+=e.offsetTop;
	   l+=e.offsetLeft;
	}
	document.title = l + "|" + w;
	a[0]=t;a[1]=l;a[2]=w;a[3]=h
	return a;
}
function inner(o,e){
	var a=getxy(o)
	if(e.x>a[1]&&e.x<(a[1]+a[2])&&e.y>a[0]&&e.y<(a[0]+a[3]))
	{
	   if(e.y<(a[0]+a[3]/2))
		return 1;
	   else
		return 2;
	}else
	{
	   return 0;
	}
}
function createtmpl(e){
    var allDiv=document.getElementsByTagName("div");
	for(var i=0;i<allDiv.length;i++){
		if(allDiv[i]==null) continue;
		if(allDiv[i].className.toLocaleLowerCase()!="module") continue;
		if(allDiv[i]==dragobj.o) continue;
		var b=inner(allDiv[i],e);
		if(b==0) continue
		dragobj.otemp.style.width=allDiv[i].offsetWidth;
		if(b==1){
			allDiv[i].parentNode.insertBefore(dragobj.otemp,allDiv[i])
		}else{
			if(allDiv[i].nextSibling==null){
				allDiv[i].parentNode.appendChild(dragobj.otemp)
			}else{
				allDiv[i].parentNode.insertBefore(dragobj.otemp,allDiv[i].nextSibling)
			}
	    }
	    var containerId=allDiv[i].parentNode.id;//容器为dom_left/dom_right/dom_bottom
	    //位置(左边，右边，底部)
	    var position=containerId.substring(containerId.indexOf("_")+1,containerId.length);
		var moduleId=dragobj.o.id;
		var id=moduleId.substring(moduleId.indexOf("_")+1,moduleId.length);
		if(position=="left")
		{
		    if($('moduleWidth'+id)!=null) $('moduleWidth'+id).value="540";
		}else if(position=="right")
		{
		    if($('moduleWidth'+id)!=null) $('moduleWidth'+id).value="360";
		}else 
		{
		    if($('moduleWidth'+id)!=null) $('moduleWidth'+id).value="900";
		}
		//退出
	    return;
	}
	
	var childNodes=$('contentDiv').childNodes;
	for(var j=0;j<childNodes.length;j++){
	   if(childNodes[j].className!="left_container"&&childNodes[j].className!="right_container"&&childNodes[j].className!="bottom_container")
		continue
	   var op=getxy(childNodes[j])   
	   //判断X坐标
	   if(e.x>(op[1]+10)&&e.x<(op[1]+op[2]-10)){
			 //判断Y坐标
			 if(e.y>(op[0]+10)&&e.y<(op[0]+op[3]-10))
			 {
				childNodes[j].appendChild(dragobj.otemp)
				dragobj.otemp.style.width=(op[2]-10)+"px";
				//容器ID
				var containerId=childNodes[j].id;//容器为dom_left/dom_right/dom_bottom
				//位置(左边，右边，底部)
				var position=containerId.substring(containerId.indexOf("_")+1,containerId.length);
				var moduleId=dragobj.o.id;
				var id=moduleId.substring(moduleId.indexOf("_")+1,moduleId.length);
				if(position=="left")
				{
					if($('moduleWidth'+id)!=null) $('moduleWidth'+id).value="540";
				}else if(position=="right")
				{
					if($('moduleWidth'+id)!=null) $('moduleWidth'+id).value="360";
				}else 
				{
					if($('moduleWidth'+id)!=null) $('moduleWidth'+id).value="900";
				}
				break;		    
			 }
	   }
	}
	//自适应高度
	var childNodes=$("dom_left").childNodes;
	var totalHeight=0;
	for(var j=0;j<childNodes.length;j++){
	     if(childNodes[j].className!="left_container"&&childNodes[j].className!="right_container"&&childNodes[j].className!="bottom_container") continue;
	     totalHeight+=getxy(childNodes[j])[3];
		 totalHeight=totalHeight<=160?160:totalHeight;
         $("dom_left").style.height=totalHeight;
		 //
	}
	var childNodes=$("dom_right").childNodes;
	var totalHeight=0;
	for(var j=0;j<childNodes.length;j++){
	     if(childNodes[j].className!="left_container"&&childNodes[j].className!="right_container"&&childNodes[j].className!="bottom_container") continue;
	     totalHeight+=getxy(childNodes[j])[3];
		 totalHeight=totalHeight<=160?160:totalHeight;
         $("dom_right").style.height=totalHeight;
	}
	var childNodes=$("dom_bottom").childNodes;
	var totalHeight=0;
	for(var j=0;j<childNodes.length;j++){
	     if(childNodes[j].className!="left_container"&&childNodes[j].className!="right_container"&&childNodes[j].className!="bottom_container") continue;
	     totalHeight+=getxy(childNodes[j])[3];
		 totalHeight=totalHeight<=160?160:totalHeight;
         $("dom_bottom").style.height=totalHeight;
	}
}
//最小化或最大化
function divMinOrMax(moduleId)
{
    if($('module_content_'+moduleId).style.display=="none")
	{
      $('module_content_'+moduleId).style.display="block";
	  $('min_'+moduleId).innerHTML="_";
	  $('min_'+moduleId).title="最小化";
	}else
	{
      $('module_content_'+moduleId).style.display="none";
	  $('min_'+moduleId).innerHTML="+";
	  $('min_'+moduleId).title="还原";
	}
}
//重新设置窗口大小
function reSetHeight(moduleId,height)
{
   $(moduleId).style.height=height;
}
/*function saveForm()
{
   var dom_right=document.getElementById("dom_bottom");
   var childNodes=dom_right.childNodes;
   //alert(childNodes.length);
   var ids="";
   for(var i=0;i<childNodes.length;i++)
   {
     var moduleId = childNodes[i].id;
	  var id=moduleId.substring(moduleId.indexOf("_")+1,moduleId.length);
	  alert(id);
   }
}*/