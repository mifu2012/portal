<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${systemName}</title>

<script type="text/javascript" src="<%=basePath%>/common/Common.jsp"></script>
<link href="<%=path%>/common/css/css.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="<%=path%>/common/js/jquery-1.4.3.min.js"></script>
<script type="text/javascript">
var timeout = false;   
//滚动停止时，显示导航条
window.onscroll=function()
{
    if (timeout){window.clearTimeout(timeout);}   
    timeout = setTimeout(function(){   
        resetDivTop();
    },100); 
	var scrollTop=getPageScroll();
	var goTopDiv=document.getElementById("goTopDiv");
	if(goTopDiv!=null)
	{
       if(scrollTop>0)
		{
		   goTopDiv.style.display="";
		}else
		{
		   goTopDiv.style.display="none";
		}
	}
}
//窗口大小发生了变化
window.onresize=function()
{
  resetDivTop();
}
function initFloatTips() {
	
	
	//进入页面，根据当前页使导航变深
	if("${type}"==2){
		//经纬仪
		var liList = document.getElementsByName("nav_li");
		for(var i=0; i<liList.length; i++){
			if(liList[i].value=="${navId}"){
				document.getElementById("li_"+liList[i].value).style.backgroundImage="url(<%=basePath%>/common/images/top_click.gif)";
			}else{
				document.getElementById("li_"+liList[i].value).style.backgroundImage="";
			}
		}
		
	  }	else if("${type}"==3){
		  //报表
		  var rptList = document.getElementsByName("nav_rpt");
			  for(var i=0;i<rptList.length; i++){
				if("${navId}"==rptList[i].value){
					document.getElementById("rpt_"+rptList[i].value).style.backgroundImage="url(<%=basePath%>/common/images/top_click.gif)";
				}else{
					document.getElementById("rpt_"+rptList[i].value).style.backgroundImage="";
				}
			}
	  }else {
		  var lwtList = document.getElementsByName("nav_lwt");
		  for(var i=0;i<lwtList.length; i++){
			if("panel"==lwtList[i].value){
				document.getElementById("lwt_"+lwtList[i].value).style.backgroundImage="url(<%=basePath%>/common/images/top_click.gif)";
			}else{
				document.getElementById("lwt_"+lwtList[i].value).style.backgroundImage="";
			}
		}
	  }
	
	
	
	//alert(0);
	var iFrameContentDiv=document.getElementById("iFrameContentDiv");
	//alert(iFrameContentDiv.offsetLeft+iFrameContentDiv.offsetWidth);
	//如果屏幕宽度小于1000，不显示图标
	var width=document.body.clientWidth;
	//alert(width);
	var goTopDiv=document.getElementById("goTopDiv");
	if(goTopDiv!=null&&width<1000) 
	{
	   goTopDiv.style.display="none";
	}
	//修改宽度
    if(goTopDiv!=null&&iFrameContentDiv!=null)
	{
       var left=width>=1264?1264:iFrameContentDiv.offsetLeft+iFrameContentDiv.offsetWidth+2;
	   goTopDiv.style.left=left+"px";
	}
}
//回到顶部
function navigationTop()
{
  var navigationDiv=window.frames["center_iframe"].document.getElementById('navigationDiv');
  if(navigationDiv!=null) navigationDiv.style.top="0px";
}
//重新设置高度
function resetDivTop()
{
   var scrollTop=getPageScroll();
   var navigationDiv=window.frames["center_iframe"].document.getElementById('navigationDiv');
   if(navigationDiv!=null)
   {
	   if(scrollTop>=110)
	    {
		    navigationDiv.style.position="relative";
		    navigationDiv.style.top=(scrollTop-110)+"px";
			//navigationDiv.style.marginTop=scrollTop+"px";
		}else
	    {
		    navigationDiv.style.position="relative";
		    navigationDiv.style.top="0px";
	    }
	}
}
/*
//下面的方法，在IE下有点闪
window.onscroll=function()
{
   var scrollTop=getPageScroll();
   var navigationDiv=window.frames["center_iframe"].document.getElementById('navigationDiv');
   if(navigationDiv!=null)
   {
	   //alert(scrollTop);
	   if(scrollTop>=110)
	    {
		    navigationDiv.style.position="relative";
		    navigationDiv.style.top=(scrollTop-110)+"px";
			//navigationDiv.style.marginTop=scrollTop+"px";
		}else
	    {
		    navigationDiv.style.position="relative";
		    navigationDiv.style.top="0px";
	    }
	}
}
*/
function getPageScroll(){ 
	var yScroll; 
	if (self.pageYOffset) { 
	  yScroll = self.pageYOffset; 
			//xScroll = self.pageXOffset; 
	} else if (document.documentElement && document.documentElement.scrollTop){ 
	   yScroll = document.documentElement.scrollTop; 
	} else if (document.body) { 
	  yScroll = document.body.scrollTop; 
	} 
	//arrayPageScroll = new Array('',yScroll) 
	return yScroll; 
} 

</script>
<script type="text/javascript">
    var onload=true;
	function changeIframeSize(iframeContentHeight) {
		document.getElementById('center_iframe').height = iframeContentHeight;
		//设置滚动条重要回到顶部
		$('body,html').animate({scrollTop:0},800);
		onload=false;
	}
	
	 function iframeOnload() {
		$('body,html').animate({scrollTop:0},800);
		if(onload==true){
			//不显示进度条
			var loadingDiv=document.getElementById("loading_wait_Div");
			if(loadingDiv!=null) loadingDiv.style.display="none";
			var frms = document.getElementById('center_iframe');
			var height = ($.browser.msie || $.browser.opera) ? frms.contentWindow.document.body.scrollHeight
					: frms.contentDocument.body.offsetHeight;
			changeIframeSize(height);
		}
		
	}
	
    //显示或关闭进度条
	function showLoading(crtFrm)
	{
		var loadingDiv=document.getElementById("loading_wait_Div");
		if(crtFrm.readyState != "loading")
		{
			//如果加载完成
			if(loadingDiv!=null) loadingDiv.style.display="none";
		}else
		{
			if(loadingDiv!=null) loadingDiv.style.display="";
		}
	}
</script>
</head>
<body class="indexbody"  onload="initFloatTips()">
    <!--回到顶部-->
    <div style='position:fixed;bottom:60px;left:1264px;display:none;' id='goTopDiv'><img src='../common/images/goTop.png' title='猛击我，回顶部' style='cursor:pointer;' onclick="$('body,html').animate({scrollTop:0},800);navigationTop();"></div>
    <!--回到顶部结束-->
	<div>
		<!-- 顶部 id='glideDiv0' -->
		<div >
		<div style="width:100%; height:100%; background:#ebebeb">
			<div style="margin-left:-5px"><div style="text-align:center; width:1004px; margin:auto; "><%@ include file="top.jsp"%></div></div>
			
			</div>
			
		</div>
	
		<div>
		
		<div  class="kpi_bg">
		<div class="kpi_bg1">
		<!-- 当前位置栏 -->
					<!-- <div class="kpi_position">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="78%"><div
										style="color: #6a6a6a; padding-left: 30px; padding-top: 5px">当前位置：首页</div></td>
								<td width="22%"><table width="100%" border="0"
										cellspacing="0" cellpadding="0" style="padding-top: 5px">
										<tr>
											<td width="37%"><input type="text" id="reportName"
												value="" style="width: 99px" /></td>
											<td width="63%">
												<div style="width: 24px; height: 21px; overflow: hidden">
													<a href="javascript:void(0);" onclick="indexSearch();"
														target="center_iframe"> <img
														src="../common/images/search_tb.gif" width="24"
														height="21" border="0" />
													</a>
												</div>
											</td>
										</tr>
									</table></td>
							</tr>
						</table>

					</div> -->
					<div align="center" id='iFrameContentDiv'>
		 
			<iframe name="center_iframe" marginwidth=0 marginheight=0 id='center_iframe'
				width=1010px scrolling="no"
				src="<%=homePage%>${defaultUrl}"
				frameborder=0 id="center_iframe" onload="iframeOnload();" onreadystatechange="showLoading(this);" style='margin-top:0px;'></iframe>
				</div>
				</div>
		</div>
		</div>
		
		<!--底部 -->
		<div  class="kpi_bg">
		<div class="kpi_footbg">
			<%@ include file="foot.jsp"%>
		</div>
		</div>
	</div>
	

</body>
</html>