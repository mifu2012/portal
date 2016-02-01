<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!-- 
<link href="<%=request.getContextPath()%>/common/css/cssz.css" rel="stylesheet"　type="text/css" />
 -->
<style type="text/css">
form,ul,li,p,h1,h2,h3,h4,h5,h6 {
	margin: 0;
	padding: 0;
}

img {
	border: 0;
}

ul,li {
	list-style-type: none;
}

.layer {
	width: 1001px;
	margin: 0 auto;
	height: auto;
	padding-left:120px;
	position: absolute;
}

.layer2 {
	position: absolute;
	top: 100px;
	height: 310px;
	border: 2px solid #73726e;
	width: 820px;
	background-color: #FFFFFF
}

.layer444 {
	position: absolute;
	top: 100px;
	height: 310px;
	border: 2px solid #73726e;
	width: 820px;
	background-color: #FFFFFF
}

.layer2 h2 {
	height: 30px;
	background: #d8e1e6;
	width: 790px;
	padding-left: 30px;
	line-height: 30px;
	color: #e85507;
	font-size: 16px
}

.pop_h2 {
	height: 30px;
	background: #d8e1e6;
	width: 790px;
	padding-left: 30px;
	line-height: 30px;
	color: #e85507;
	font-size: 16px
}

.layer2 span {
	position: absolute;
	right: 20px;
	font-size: 12px;
	font-weight: normal;
	color: #333333;
	top: 0px
}

.pop_span {
	position: absolute;
	right: 20px;
	font-size: 12px;
	font-weight: normal;
	color: #333333;
	top: 0px
}

.layer2 ul {
	padding: 20px 0px 0px 30px;
	_padding: 20px 0px 0px 30px;
	width: 800px;
	_width: 800px
}

.layer2 ul li {
	width: 80px;
	float: left;
	margin-right: 30px;
	_margin-right: 30px;
	height: 80px;
	text-align: center;
	line-height: 18px
}

.layer2 ul li img {
	margin-bottom: 5px
}

.divContent {
	border: 0px solid;
	border-top:1px solid gray; 
	height: 310px; 
	overflow: auto; 
	overflow-x: hidden; 
	width: 100%;
}
.divnone {
	display: none
}
</style>
<script type="text/javascript">
//显示进度条
function showLoadingDiv()
{
	var loadingDiv=window.parent.document.getElementById("loading_wait_Div");
	if(loadingDiv==null) loadingDiv=document.getElementById("loading_wait_Div");
	if(loadingDiv==null) return;
	//显示进度条
	loadingDiv.style.display="";
}
//选择产品
var xmlHttp;
var prdListUl;
function callback(){
   if(xmlHttp.readyState == 4)
         {
             if(xmlHttp.status == 200)
             {
            	 prdInfoList = eval(xmlHttp.responseText);
                 if(prdInfoList==null||prdInfoList.length==0) return;
	       		 var prdInfoLi=null;
	       		 var prdInfoImgSrc="<%=request.getContextPath()%>/common/images/no-icon.png";
	       		 for(var i=0;i<prdInfoList.length;i++)
	       		 {
	       			 if(prdInfoList[i]==null) continue;
	       			 prdInfoLi=document.createElement("li");
	       			 prdInfoLi.className="layer2_ul_li";
	       			 //是否URL
	       			 if(prdInfoList[i].iconUrl==null||prdInfoList[i].iconUrl.length==0)
	       			  {
	       				 prdInfoImgSrc="<%=request.getContextPath()%>/common/images/no-icon.png";
	       			  }else
	       			  { 
	       				 prdInfoImgSrc="<%=request.getContextPath()%>/"+prdInfoList[i].iconUrl;
	       			  }
	       			 prdInfoLi.innerHTML="<a style='cursor:hand;' href='javascript:void(0)' onclick=\"document.getElementById('productCheck').style.display='none';showLoadingDiv();changeProduct('"+prdInfoList[i].productId+"')\"><img src='"+prdInfoImgSrc+"' style='margin-bottom:5px' onError='this.src=\"<%=basePath%>"+prdInfoImgSrc+"\"'><div>"+prdInfoList[i].productName+"</div></a>";
					 //
	       			 prdListUl.appendChild(prdInfoLi);
	       		 }
				 //alert(document.getElementById("loadingDivImg"));
				 document.getElementById("loadingDivImg").style.display="none";
             }
         }
}
function sendService()
{      
	 xmlHttp = window.ActiveXObject? new ActiveXObject("Microsoft.XMLHTTP"): new XMLHttpRequest();
	 xmlHttp.onreadystatechange=callback;
	 xmlHttp.open("POST","<%=request.getContextPath()%>/ProdoductInfo/choiceProductInfo.html",true);
	 xmlHttp.send(null); 
}
//显示遮罩层
function showMaskLayer()
{
	var newMask=document.getElementById("maskLayerDiv");
	if(newMask!=null)
	{
       newMask.style.display='';
	   return;
	}
	//alert(newMask);
	newMask=document.createElement("div"); 
	newMask.id="maskLayerDiv"; 
	newMask.style.position="absolute"; 
	newMask.style.zIndex="1"; 
	_scrollWidth=Math.max(document.body.scrollWidth,document.documentElement.scrollWidth); 
	_scrollHeight=Math.max(document.body.scrollHeight,document.documentElement.scrollHeight); 
	// _scrollHeight = Math.max(document.body.offsetHeight,document.documentElement.scrollHeight); 
	newMask.style.width=_scrollWidth+"px"; 
	newMask.style.height=_scrollHeight+"px"; 
	newMask.style.top="0px"; 
	newMask.style.left="0px"; 
	newMask.style.background="#33393C"; 
	//newMask.style.background = "#FFFFFF"; 
	newMask.style.filter="alpha(opacity=40)"; 
	newMask.style.opacity="0.40"; 
	newMask.style.display='';
	document.body.appendChild(newMask); 
}
//选择产品
function choiceRrdInfo()
{  
	 //显示遮罩层
	 showMaskLayer();
     var divTop=0;
     //var clientHeight=window.parent.document.getElementById('center_iframe').height;
	 if(window.parent!=null)
	 {
		divTop=window.parent.document.body.scrollTop+window.parent.document.documentElement.scrollTop+100;
	 }else
	 {
		divTop=document.body.scrollTop+document.documentElement.scrollTop;
	 }
	 var productCheck=document.getElementById("productCheck");
	 //显示产品选择框
	 document.getElementById("productCheck").style.display="";
	 productCheck.style.position="absolute";
     productCheck.style.top=(divTop-170)+"px";
	　//如果有数据，则不再查询数据
	 prdListUl=document.getElementById("prdListUl");
	 if(prdListUl==null||prdListUl.childNodes.length!=0)  return;
	 document.getElementById("loadingDivImg").style.display="";
	 //显示进度条
	 var loadingDiv=window.parent.document.getElementById("loading_wait_Div");
	 if(loadingDiv==null) loadingDiv=document.getElementById("loading_wait_Div");
	 if(loadingDiv!=null) loadingDiv.style.display="";
	 sendService();
	 //不显示进度条
	 if(loadingDiv!=null) loadingDiv.style.display="none";
	 //显示选择产品框
	 document.getElementById("productCheck").style.display="";
}
//隐藏
function closeDivWindow(divId)
{
   document.getElementById(divId).style.display='none';
   var newMask=document.getElementById("maskLayerDiv");
   if(newMask!=null) newMask.style.display="none";
}

var isDrag=0;//是否可拖动标志，可拖动为1，不可拖动为2
var divAndMouseX;//鼠标落点距离div左上角x坐标的差距
var divAndMouseY;//鼠标落点距离div左上角y坐标的差距
var divMove=null;

//鼠标按下时
function mouseDown(divId){//鼠标按下
	isDrag=1;//将div设置为可拖动
	divMove=document.getElementById(divId);//用于下边拖动时的定位(新的位置的坐标)
	//首先计算鼠标与拖动对象左上角的坐标差，然后在下边拖动方法中，用鼠标的坐标减坐标差就是新位置的坐标了
	divAndMouseX=event.clientX-parseInt(document.getElementById(divId).style.left);
	divAndMouseY=event.clientY-parseInt(document.getElementById(divId).style.top);
}
//拖动过程计算div坐标
function mouseMove(){
	var event=window.event||event; 
	if(isDrag==1){
		//alert('sss');
		divMove.style.position="absolute";
		divMove.style.left=(event.clientX-divAndMouseX)+"px";
		var top=event.clientY-divAndMouseY<=-50?-50:event.clientY-divAndMouseY;
		divMove.style.top=top+"px";
	}
}
//放开鼠标将div设置为不可拖动
function mouseUp(){
	//alert('up');
	isDrag=0;
}

//创建新窗口
function openDivWindow(divId,title,width,height,url)
{
    if(divId==null||divId.length==0) return;
    if(url==null||url.length==0) return;
    //显示遮罩层
    showMaskLayer();
	//弹窗
    var divTop=0;
    //var clientHeight=window.parent.document.getElementById('center_iframe').height;
	if(window.parent!=null)
	{
		divTop=window.parent.document.body.scrollTop+window.parent.document.documentElement.scrollTop+100;
	}else
	{
		divTop=document.body.scrollTop+document.documentElement.scrollTop;
	}
	//alert(window.parent.getPageScroll());
	var showDivTop=window.parent.getPageScroll()-170;
	showDivTop=showDivTop<=0?-50:showDivTop;
   //alert(showDivTop);
   //打开窗口
   var newOpenDiv=document.getElementById(divId);
   if(newOpenDiv!=null)
	{
	   newOpenDiv.style.display="";
       newOpenDiv.style.position="absolute";
       newOpenDiv.style.top=showDivTop+"px";//(divTop-170)+"px";
	   return;
	}
   //alert('创建新窗口:'+title);
   //弹窗全部内容
   var bodyDiv=document.createElement("div");
	   bodyDiv.className="layer2";
	   bodyDiv.style.height=height+"px";
   //标题
   var headerDiv=document.createElement("div");
	   headerDiv.innerHTML="<h2>"+title+"<span><a href='javascript:void(0);' onclick='Javascript:closeDivWindow(\""+divId+"\");'>关闭</a></span></h2>";
	   //alert(headerDiv.innerHTML);
   //添加标题
   bodyDiv.appendChild(headerDiv);
   
   //alert(bodyDiv.innerHTML);
   //正文
   var contentDiv=document.createElement("div");
       contentDiv.className="divContent";
	   //contentDiv.style.border="1px solid red";
	   contentDiv.style.height=height+"px";
	   contentDiv.innerHTML="<iframe marginwidth=0 marginheight=0 width='100%' height='"+(height-31)+"px' scrolling='no' src='"+url+"' frameborder=0 style='margin-top:0px;'></iframe>";
   //添加正文
   bodyDiv.appendChild(contentDiv);
   //新窗口
   newOpenDiv=document.createElement("div");
   newOpenDiv.id=divId;
   newOpenDiv.className="layer";
   //宽度
   //alert(width);
   newOpenDiv.style.width=width+"px";
   newOpenDiv.style.position="absolute";
   newOpenDiv.style.top=showDivTop+"px";//(divTop-170)+"px";
   newOpenDiv.style.zIndex="999";
   newOpenDiv.style.display="";
   //alert(bodyDiv.innerHTML);
   //弹窗全部内容
   newOpenDiv.appendChild(bodyDiv);
   //添加到BODY
   document.body.appendChild(newOpenDiv);
}

//创建新窗口
function createDivWindow(divId,title,width,height,contentHtml)
{
    if(divId==null||divId.length==0) return;
    if(contentHtml==null||contentHtml.length==0) return;
    //显示遮罩层
    showMaskLayer();
    var divTop=0;
    //var clientHeight=window.parent.document.getElementById('center_iframe').height;
	if(window.parent!=null)
	{
		divTop=window.parent.document.body.scrollTop+window.parent.document.documentElement.scrollTop+100;
	}else
	{
		divTop=document.body.scrollTop+document.documentElement.scrollTop;
	}
   //alert(divTop);
   	//alert(window.parent.getPageScroll());
   var showDivTop=window.parent.getPageScroll()-170;
	   showDivTop=showDivTop<=0?-50:showDivTop;
   //打开窗口
   var newOpenDiv=document.getElementById(divId);
   if(newOpenDiv!=null)
	{
	   newOpenDiv.style.display="";
	   document.getElementById("divContent_"+divId).innerHTML=contentHtml;//内容
       newOpenDiv.style.position="absolute";
       newOpenDiv.style.top=showDivTop+"px";//(divTop-170)+"px";
	   return;
	}
   //alert('创建新窗口:'+title);
   //弹窗全部内容
   var bodyDiv=document.createElement("div");
	   bodyDiv.className="layer444";
	   bodyDiv.style.height=height+"px";
   //标题
   var headerDiv=document.createElement("div");
	   headerDiv.innerHTML="<div class='pop_h2'>"+title+"<div class='pop_span'><a href='javascript:void(0);' onclick='Javascript:closeDivWindow(\""+divId+"\");'>关闭</a></div></div>";
	   //alert(headerDiv.innerHTML);
   //添加标题
   bodyDiv.appendChild(headerDiv);
   
   //alert(bodyDiv.innerHTML);
   //正文
   var contentDiv=document.createElement("div");
       contentDiv.className="divContent";
	   contentDiv.id="divContent_"+divId;
	   //contentDiv.style.border="1px solid red";
	   contentDiv.style.height=height+"px";
	   contentDiv.innerHTML=contentHtml;//内容
   //添加正文
   bodyDiv.appendChild(contentDiv);
   //新窗口
   newOpenDiv=document.createElement("div");
   newOpenDiv.id=divId;
   newOpenDiv.className="layer";
   //宽度
   //alert(width);
   newOpenDiv.style.width=width+"px";
   newOpenDiv.style.position="absolute";
   newOpenDiv.style.top=showDivTop+"px";//(divTop-170)+"px";
   newOpenDiv.style.zIndex="999";
   newOpenDiv.style.display="";
   //alert(bodyDiv.innerHTML);
   //弹窗全部内容
   newOpenDiv.appendChild(bodyDiv);
   //添加到BODY
   document.body.appendChild(newOpenDiv);
}
var lastRolledOverChartId=null;
//趋势图或面积图或堆积图
function amRolledOver(chart_id, date, period, data_object)
{
   lastRolledOverChartId=chart_id;
}
//Column & Bar 
function amRolledOverBullet(chart_id, graph_index, value, series, url, description)
{
    lastRolledOverChartId=chart_id;
}
//line
function amRolledOverSeries(chart_id, series)
{
   lastRolledOverChartId=chart_id;
}
//隐藏或显示大事记
function hideEvent(isShow)
{
	//alert(lastRolledOverChartId);
	var chartObj=document.getElementById(lastRolledOverChartId);
	//alert(chartObj);
	if(chartObj!=null)
	{
		if(isShow==1) chartObj.hideEvents();
		if(isShow==0) chartObj.showEvents();
	}
}
//弹窗显示大效果
function zoomInShow()
{
	var chartObj=document.getElementById(lastRolledOverChartId);
	//alert(chartObj);
	if(chartObj==null)
	{
		alert('未选择图表');
		return;
	}
	//alert('0');
	//newObj.setParam("height", "100%");
	//alert(chartObj.getVariable("columnName"));
	//栏目名称
	var columnName=null;
	try
	{
		columnName=chartObj.getVariable("columnName");
	}
	catch(e)
	{
		columnName=null;
	}
	//alert(columnName);
	if(columnName==null||columnName.length==0)
	{
		var relModuleId=chartObj.getVariable("moduleId");
		var tmpModuleNameObj=document.getElementById("td_header_"+relModuleId);
		if(tmpModuleNameObj!=null)
		{
		   columnName=tmpModuleNameObj.innerHTML;
		   //var reg=/&nbsp;/g;
		   //columnName=columnName.replace(reg,"");
		}
	}
	//alert(columnName);
	if(columnName==null||columnName.length==0) columnName="栏目大图";
	//chartObj.exportImage('<%=basePath%>/stockChart/exportChart.html');
	var cWidth=document.body.clientWidth;
	var cHeight=document.body.clientHeight;
	//弹窗显示
	var newWindow=window.open("","newWindow","height="+cHeight+",width="+cWidth+",toolbar=no,,menubar=no");
		newWindow.document.write("<HTML>" );
		newWindow.document.write("<TITLE>"+columnName+"</TITLE>");
		
		newWindow.document.write("<BODY BGCOLOR=#FFFFFF>");
		newWindow.document.write(chartObj.parentNode.innerHTML);
		newWindow.document.write("</BODY>" );
		newWindow.document.write("</HTML>" );
		newWindow.document.write("<script language='javascript'>");
		newWindow.document.write("document.getElementById('"+lastRolledOverChartId+"').height='100%';");
		newWindow.document.write("document.getElementById('"+lastRolledOverChartId+"').play();");
		newWindow.document.write("<\/script>");
}
//导出
function exportAsImg()
{
	var chartObj=document.getElementById(lastRolledOverChartId);
	//alert(chartObj);
	if(chartObj==null)
	{
		alert('未选择图表');
		return;
	}
	if(!confirm("确定导出为图片吗")) return;
    chartObj.exportImage('<%=basePath%>/stockChart/exportChart.html');
    window.setTimeout(function(){
		//alert(56);
		var loadingDiv=window.parent.parent.document.getElementById("loading_wait_Div");
		if(loadingDiv==null) loadingDiv=document.parent.getElementById("loading_wait_Div");
	    if(loadingDiv==null) loadingDiv=document.getElementById("loading_wait_Div");
	    if(loadingDiv==null) return;
	    //不显示进度条
	    loadingDiv.style.display="none";
	},10000);
}
//以什么开始
String.prototype.startWith=function(str)
{
	if(str==null||str==""||this.length==0||str.length>this.length)
	{
	  return false;
	}
	if(this.substr(0,str.length)==str)
	{
	  return true;
	}
	else
	{
	  return false;
	}
	return true;
}
String.prototype.replaceAll = function(s1,s2) { 
    return this.replace(new RegExp(s1,"gm"),s2); 
}

//图的初始化
function amChartInited (chart_id)
{
  //如果是折线，默认显示七天的数据
  if(chart_id.startWith("line"))
  {
	  var nowTime="${date}";
	  //alert(nowTime.length);
	  if(nowTime.length<=7)
	  {
         zoomLineChart(chart_id,12);//默认显示一年的数据
	  }else
	  {
		 //alert('10');
         zoomLineChart(chart_id,7);//默认显示七天的数据
	  }
  }
}
//异常提示一下
function amError(chart_id, message)
{
    //alert(message);
}  
//折线图：显示最近的数据
function selectInterval(dayCount)
{
    zoomLineChart(lastRolledOverChartId,dayCount);
}
//折线所用
function zoomLineChart(chartId,dayCount)
{
	//alert(weekCount);
	var chartObj=document.getElementById(chartId);
	//alert(chartObj);
	if(chartObj==null) return;
	//按月查看
	var nowTime="${date}";
    if(nowTime.length<=7)
	{
		var newTime="${date}";
		for(var i=0;i<dayCount;i++)
		{
           newTime=getLastMonth(newTime);//得到下个月
		}
        chartObj.setZoom(newTime,"${date}");
		return;
	}
	//按日查看
	var now=new Date("${date}");
	now.setDate(now.getDate()-dayCount);
    var LINT_MM=now.getMonth(); 
    LINT_MM++; 
	//alert(LINT_MM>10);
	var LSTR_MM=LINT_MM >= 10?LINT_MM:("0"+LINT_MM);
	var LINT_DD=now.getDate(); 
	var LSTR_DD=LINT_DD >= 10?LINT_DD:("0"+LINT_DD);
	 //得到最终结果 
	now = now.getFullYear() + "-" + LSTR_MM + "-" + LSTR_DD; 
	//alert(now);
	//跳转到相应的日期
    chartObj.setZoom(now,"${date}");
}
//得到上个月的时间
function getLastMonth(yearMonth)
{
    var date = new Date(yearMonth);
	var year = date.getFullYear();
	var month = date.getMonth()+1;
	if (month == 1) {
		year--;
		month = 12;
	}
	else {
		month--;
	}
	var newmonth = month < 10 ? "0" + month : month;
	return year + "-" + newmonth;
}
//显示所有数据
function showAll()
{
   var chartObj=document.getElementById(lastRolledOverChartId);
   if(chartObj!=null) chartObj.showAll();
}

/* 导出数据 */
function exportAsExcel(kpiCodes,kpiType){
	if(kpiCodes==null||kpiCodes.length==0)
	{
       	var chartObj=document.getElementById(lastRolledOverChartId);
		if(chartObj==null)
		{
			alert('未选择图表');
			return;
		}
		try
		{
			kpiCodes=chartObj.getVariable("kpiCodes");
			kpiType=chartObj.getVariable("kpiType");
		}
		catch (e)
		{
			alert("读取图表关联属性失败:"+e.message);
			return;
		}
	}
	if(kpiCodes==null||kpiCodes.length==0)
	{
		alert('获取关联指标失败');
		return;
	}
	if(!confirm("确定导出数据吗?")) return;
    //var productId=chartObj.getVariable("productId");
	var url="<%=path%>/AnalyzeDownload/downloadData?&downloadkpiCodes="+kpiCodes+"&kpiType="+kpiType;
	//alert(url);
	//document.location =url;
	//下载数据
    document.exportAsExcelForm.action=url;
    document.exportAsExcelForm.submit();
}
//下载进度条
function downloading(crtFrm)
{
	//alert(crtFrm.readyState);
}

/* 查看详细数据 */
function detailData(kpiCodes,kpiType){
	//alert(kpiCodes);
	if(kpiCodes==null||kpiCodes.length==0)
	{
		var chartObj=document.getElementById(lastRolledOverChartId);
		if(chartObj==null)
		{
			alert('未选择图表');
			return;
		}
		try
	  	{	  kpiCodes=chartObj.getVariable("kpiCodes");
		 	  kpiType=chartObj.getVariable("kpiType");
	  	}
	 	 catch (e)
	  	{
		 	alert('对不起!加载数据指标失败:'+e.message);
		 	return;
	  	}
	}	 
	if(kpiCodes==null||kpiCodes.length==0)
	{
		alert('获取关联指标失败');
		return;
	}
	var url="<%=path%>/stockChart/getDetailData.html?kpiCodes="+kpiCodes+"&kpiType="+kpiType+"&random="+Math.random();
	var detailDataDivId="detailDataDivId_"+kpiCodes.replaceAll(";","");
	openDivWindow(detailDataDivId,"指标详细数据",821,430,url);//openDivWindow 为通用的方法
}
//大事记详细数据
function amClickedOnEvent(chart_id, date, description, id, url)
{
	 //弹出新窗口
	 try
		{
			var url="<%=basePath%>/stockChart/getDetailEvent.html?eventId="+id+"&random="+Math.random();
			var tempEvengDivId="eventItemDiv_"+id;
			openDivWindow(tempEvengDivId,"大事记详情",1000,400,url);//divId,title,width,height,url
	} catch (e)
	{
		 //alert('对不起!加载数据失败:'+e.message);
	}
}
</script>
<!-- 产品选择 start-->
<div class="layer" id="productCheck"
	style="z-index: 999; display: none; top: 0px;">
	<div class="layer2" id="div_layer2">
		<h2>
			<div style="width:90%;cursor: move;text-align:left;border:0px solid;" onmousedown="mouseDown('productCheck')" onmousemove="mouseMove()"
			onmouseup="mouseUp()">选择产品或渠道</div><span><a href="javascript:void(0);"
				onclick="Javascript:closeDivWindow('productCheck');">关闭</a>
			</span>
		</h2>
		<img src="<%=basePath%>/common/images/loading.gif" style="position: absolute;left:40%;display:none;" id="loadingDivImg">
		<div
			style='border: 0px inset;border-top:1px solid gray;height: 280px; overflow: auto; overflow-x: hidden; width: 100%;'>
			<ul id="prdListUl"></ul>
		</div>
	</div>
</div>
<!-- 产品选择 end-->
<!--导出为EXCEL数据-->
<form action="" name="exportAsExcelForm" id="exportAsExcelForm" target="export_as_excel_frm" method="post">
</form>
<iframe name="export_as_excel_frm" id="export_as_excel_frm" src="about:blank" frameborder="0" width="0" height="0" onreadystatechange="downloading(this);"></iframe>
<!--导出为EXCEL数据-->
