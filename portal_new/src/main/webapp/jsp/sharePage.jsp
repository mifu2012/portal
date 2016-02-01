<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>
<!--
弹出层-->#winpop {
	width: 200px;
	height: 0px;
	position: absolute;
	right: 0;
	bottom: 0;
	border: 1px solid #666;
	margin: 0;
	padding: 1px;
	overflow: hidden;
	display: none;
}

#winpop .title {
	width: 100%;
	height: 22px;
	line-height: 20px;
	background: #FFCC00;
	font-weight: bold;
	text-align: center;
	font-size: 12px;
}

#winpop .con {
	width: 100%;
	height: 90px;
	line-height: 80px;
	font-weight: bold;
	font-size: 12px;
	color: #FF0000;
	text-decoration: underline;
	text-align: center
} /* http://www.CsrCode.cn */
#silu {
	font-size: 12px;
	color: #666;
	position: absolute;
	right: 0;
	text-align: right;
	text-decoration: underline;
	line-height: 22px;
}

.close {
	position: absolute;
	right: 4px;
	top: -1px;
	color: #FFF;
	cursor: pointer
}

.tab {
	width: 400px;
	margin: 0 auto;
}

h6 {
	font-size: 12px;
	border-bottom: 1px solid #ccc;
	height: 21px;
}

h6 span {
	float: left;
	padding: 0 15px;
	display: inline;
	margin-left: 5px;
	cursor: pointer;
	line-height: 20px;
	border: 1px solid #ccc;
	border-bottom: 1px solid #ccc;
	position: relative;
	margin-bottom: -2px;
}

.current {
	color: #2974ae;
	position: relative;
	border-bottom: 1px solid #fff;
}

.tab div {
	clear: both;
	display: none;
}

.tab .block {
	display: block;
}

#glideDiv0 {
	position: fixed;
	top: 0px;
	width: 100%;
	z-index: 999;
}
</style>
<!--[if IE 6]> 
<style type="text/css">
#glideDiv0{position:absolute;}
</style>
<![endif]-->
<
<script type="text/javascript">
$(function(){
    $(".tab>h6>span").click(
        function(){
            $(this).addClass("current").siblings().removeClass("current");
            //$(".tab>div").eq($(".tab>h6>span").index(this)).show().siblings("div").hide();
        }
    )
})
        //<![CDATA[ 
        var tips; var theTop = 200/*这是默认高度,越大越往下*/; var old = theTop;
        function initFloatTips() {
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
		       //alert('sss');
			   goTopDiv.style.left=iFrameContentDiv.offsetLeft+iFrameContentDiv.offsetWidth+"px";
			}
        	//获得未读记录数 
        	receiveMessage();
<%--         	document.getElementById('receiveIframe').src='<%=path%>/userShare/showReceiveInfo.html'
 --%>      
 //进入页面，根据当前页使导航变深
            if("${type}"==2){
            	var liList = document.getElementsByName("nav_li");
            	for(var i=0; i<liList.length; i++){
            		if(liList[i].value==id){
            			document.getElementById("li_"+liList[i].value).style.backgroundImage="url(<%=basePath%>/common/images/top_click.gif)";
            		}else{
            			document.getElementById("li_"+liList[i].value).style.backgroundImage="";
            		}
            	}
            	
	          }	else if("${type}"==3){
	        	  var rptList = document.getElementsByName("nav_rpt");
	      		  for(var i=0;i<rptList.length; i++){
	      			if("${navId}"==rptList[i].value){
	      				document.getElementById("rpt_"+rptList[i].value).style.backgroundImage="url(<%=basePath%>/common/images/top_click.gif)";
	      			}else{
	      				document.getElementById("rpt_"+rptList[i].value).style.backgroundImage="";
	      			}
	      		}
	          }
            tips = document.getElementById('floatTips');
            moveTips();
        };
        function moveTips() {
            var tt = 50;
            if (window.innerHeight) {
                pos = window.pageYOffset
            }
            else if (document.documentElement && document.documentElement.scrollTop) {
                pos = document.documentElement.scrollTop
            }
            else if (document.body) {
                pos = document.body.scrollTop;
            }
            pos = pos - tips.offsetTop + theTop;
            pos = tips.offsetTop + pos / 10;
            if (pos < theTop) pos = theTop;
            if (pos != old) {
                tips.style.top = pos + "px";
                tt = 10;
            }
            old = pos;
            setTimeout(moveTips, tt);
        }
    </script>


<div class="pop_home" id="ad_pop"
	style="margin-top: 50px; display: none">
	<div class="pop_bg">
		<div class="pop_bg_1">
			<table width="100%" height="100%" border="0" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="85%" height="30"><div align="left">
							<div class="pop_yhxx">共享当前页给他人</div>
						</div></td>
					<td width="15%">
						<div style="padding-top: 5px">
							<a href="javaScript:void();" onclick="closeSendDiv();"><img
								src="../common/images/pop_1.gif" width="15" height="16"
								border="0" /> </a>
						</div>
					</td>
				</tr>
			</table>
		</div>
		<div class="pop_bg_center">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				style="color: #3382ca; font-size: 12px">
				<tr>
					<td height="30" colspan="2"><table width="333" border="0"
							cellspacing="0" cellpadding="0" style="margin-top: 5px">
							<tr>
								<td width="50%" rowspan="2" valign="top">
									<div align="center">
										<div align="left" style="margin-left: 22px">共享用户</div>
										<div
											style="width: 120px; height: 150px; border: 1px solid #ccc; overflow-x: hiiden; overflow-y: auto;">

											<table width="100%" border="0" cellpadding="0"
												cellspacing="0" id="shareUserTable">

											</table>
										</div>
									</div>
								</td>
								<td width="50%">共享说明</td>
							</tr>
							<tr>
								<td valign="top"><textarea id="shareRemark" name="textarea"
										style="height: 150px; width: 130px; font-size: 12px;"></textarea>

								</td>
							</tr>
						</table></td>
				</tr>
				<tr>
					<td width="33%">&nbsp;</td>
					<td width="67%" height="35"><table width="100%" border="0"
							cellspacing="0" cellpadding="0">
							<tr>
								<td align="right" colspan="2" style="padding-right: 30px"><a
									href="javaScript:void();" onclick="saveSend();"><img
										src="../common/images/pop_4.gif" width="64" height="23"
										border="0" /> </a> &nbsp;<a href="javaScript:void();"
									onclick="closeSendDiv();"><img
										src="../common/images/pop_close_1.gif" width="64" height="23" />
								</a></td>
							</tr>
						</table></td>
				</tr>
			</table>
		</div>
	</div>

	<img src="../common/images/pop_up6.png" />
</div>

<script>
//定时刷新未读信息数量
//window.setInterval('receiveMessage();',60000);
function receiveMessage(){
	$.ajax({
        type: "POST",                                                                 
        url:encodeURI("<%=path%>/userShare/getMessageNum.html?random=" + Math.random()),
				success : function(msg) {
						$("#messageNum").html(msg);
					}
			});
}

//显示要发送的用户
function showSendUsers(){
	
	$("#shareRemark").val("");
	$.ajax({
        type: "POST",                                                                 
        url:encodeURI("<%=path%>/userShare/showShareUsers.html?random=" + Math.random()),
				success : function(msg) { 
					var listHtml="";
					var allChoiceHtml="";
					if(msg!=null&&msg.length>0){
						allChoiceHtml="<tr><td width='15%'><input  type='checkbox' id='allChoice' name='allChoice' onclick='allChoice();' /></td><td width='85%' align='left' style='color:#000000 '><label for='allChoice'>全选</label></td></tr>";
			            for(var i=0;i<msg.length;i++){
			            listHtml+="<tr><td width='15%'><input type='checkbox' name='sendUsers' id='"+msg[i].userId+"' value='"+msg[i].userId+'-'+msg[i].userName+"' /></td><td width='85%' align='left'><label for='"+msg[i].userId+"'>"+msg[i].userName+"</label></td></tr> ";
		        	   } 
			            //$("#shareUserTable").html(allChoiceHtml+listHtml);
			            /*
			            try
			            {
			            	alert(allChoiceHtml+listHtml);
			            	document.getElementById("shareUserTable").innerHTML=allChoiceHtml+listHtml;
			            }catch(e){alert(e.message);}
						*/
			            $("#shareUserTable").html(allChoiceHtml+listHtml);
					}else{
						
						$("#shareUserTable").html("<tr><td colspan='2' align='center' >没有相关用户</td></tr>");
					}
					
				}
}); 
}

//全选
function allChoice(){
	var allChoiceId=document.getElementById('allChoice');
	var sendUsers=document.getElementsByName("sendUsers")
	if(allChoiceId.checked){
		for(var i=0;i<sendUsers.length;i++){
			sendUsers[i].checked=true;
		}
	}else{
		for(var i=0;i<sendUsers.length;i++){
			sendUsers[i].checked=false;
		}
	}
}
//显示用户的页面
function showSendDiv(){
	var scrollTop=document.documentElement.scrollTop+document.body.scrollTop;
	document.getElementById('ad_pop').style.top=scrollTop+120+"px";
	document.getElementById('ad_pop').style.left=(window.screen.width-334)/2 +"px";
	document.getElementById('windowpop').style.display='none';
	document.getElementById('replayInfo').style.display='none';
	showSendUsers();
	document.getElementById('ad_pop').style.display='block';
	
	 
}
//关闭发送页面
function closeSendDiv(){
	document.getElementById('ad_pop').style.display='none';
}

//关闭回复页面
function closeReplayDiv(){
	var replayWindow=window.frames['replayInfoIframe'];
	replayWindow.document.getElementById('replayMessage').value="";
	replayWindow.document.getElementById('allMessages').innerHTML="";
	document.getElementById('replayInfo').style.display='none';
}
//发送回复的信息
function sendReplayInfo(){
	var replayWindow=window.frames['replayInfoIframe'];
	var url=replayWindow.document.getElementById('replayUrl').value;
	var replayUserId=replayWindow.document.getElementById('replayUserId').value;
	var replayUserName=replayWindow.document.getElementById('replayUserName').value;
	var originId=replayWindow.document.getElementById('replayOriginId').value;
	var replayMessage=replayWindow.document.getElementById('replayMessage').value;
	var reg=new RegExp("&","g");
	url=url.replace(reg,"^");
	if(replayMessage==null||replayMessage==""){
		alert("不能发送空消息 ！");
		return;
	}else{
		$.ajax({
	        type: "POST",                                                                 
	        url:encodeURI("<%=path%>/userShare/saveReplayShare.html?replayUserId="
	        		+ replayUserId +"&url="+url+"&replayUserName="+replayUserName+"&originId=" +originId
	        		+ "&replayMessage="+replayMessage),
					success : function(msg) {
						if(msg=="success"){
		             		alert("回复成功！");
		             		document.getElementById('replayInfo').style.display='none';
		             		replayWindow.document.getElementById('replayMessage').value="";
		             		replayWindow.document.getElementById('allMessages').innerHTML="";
		             	 }else{
		             		 alert("回复失败！");
		             	 }
					}
	   }); 
	}
	
	
	
	
	
}

//显示接收信息
function showReceiveInfo(){
	document.getElementById('shareInfoIframe').src='<%=path%>/userShare/showReceiveInfo.html';
}

//显示用户发送的信息
function showSendInfo(){
	document.getElementById('shareInfoIframe').src='<%=path%>/userShare/showSendInfo.html';

}

function iFrameHeight(){
	var ifm= document.getElementById("shareInfoIframe");   
	var subWeb = document.frames ? document.frames["shareInfoIframe"].document : ifm.contentDocument;   
	if(ifm != null && subWeb != null) {
	   ifm.height = subWeb.body.scrollHeight+10+"px";
	   try
	   {
		  window.parent.iframeOnload();
	   }
	   catch (e)
	   {
	   }
	}  
}

function replayIframeHeight(){
	var ifm= document.getElementById("replayInfoIframe");   
	var subWeb = document.frames ? document.frames["replayInfoIframe"].document : ifm.contentDocument;   
	if(ifm != null && subWeb != null) {
	   ifm.height = subWeb.body.scrollHeight;
	   try
	   {
		  window.parent.iframeOnload();
	   }
	   catch (e)
	   {
	   }
	}  
}


//显示共享信息页面
function showShareInfoDiv(){
	
	var scrollTop=document.documentElement.scrollTop+document.body.scrollTop;
	document.getElementById('windowpop').style.top=scrollTop+320+"px";
	document.getElementById('windowpop').style.left=(window.screen.width-60)/2 +"px";
	
	closeSendDiv();
	document.getElementById('replayInfo').style.display='none';
	if(document.getElementById('receiveBut').className=="current"){
		showReceiveInfo();
	}else{
		showSendInfo();
	}
	/* $("#receiveBut").addClass("current");
	$("#sendBut").removeClass("current"); */
	//showReceiveInfo();
	document.getElementById('windowpop').style.display='block';
	 
}
function closeShareInfoDiv(){
	document.getElementById('windowOpenDiv').style.display='none';
	
}
//点击保存发送共享
function saveSend(){
	var sendUsers=document.getElementsByName("sendUsers");
	var shareRemark=document.getElementById("shareRemark").value;
	var sendUsersValue="";
	
	for(var i=0;i<sendUsers.length;i++){
		if(sendUsers[i].checked){
			if(sendUsersValue==""){
				sendUsersValue=sendUsers[i].value;
			}else{
				sendUsersValue=sendUsersValue+","+sendUsers[i].value;
			}
			
		}
		
	}
	if(sendUsersValue==""){
		alert("请选择要共享的用户！");
		return false;
	}
	if(shareRemark==""){
		alert("请添加共享说明！");
		return false;
	}
	
	var theUrl=(window.frames['center_iframe'].document.location.href);
	var reg=new RegExp("&","g");
	theUrl=theUrl.replace(reg,"^");
	$.ajax({
        type: "POST",                                                                 
        url:encodeURI("<%=path%>/userShare/saveSharePage.html?sendUsersValue="
        		+ sendUsersValue +"&url="+theUrl+"&shareRemark="+shareRemark+"&random=" + Math.random()),
				success : function(msg) {
					if(msg=="success"){
	             		alert("共享成功！");
	             		document.getElementById('ad_pop').style.display='none';
	             	 }else{
	             		 alert("共享失败！");
	             	 }
				}
   }); 
	
}

</script>
<div
	style="width: 825px; height: auto; margin: auto; border: 3px solid #73726e; position: absolute; top: 230px; left: 534px; margin: -150px 0 0 -450px; display: none; background: #fff"
	id="windowOpenDiv">
	<div style="width: 820px; height: auto; float: left;">
		<h3
			style="background: #d8e1e6; width: 815px; padding-left: 10px; line-height: 30px; color: #e85507; font-size: 16px;">
			<label id="windowOpenDiv_title"><b>弹窗标题</b> </label> <span
				style="color: #333333; position: absolute; right: 20px; font-size: 12px; font-weight: normal; color: #333333; top: 0px;">
				<a href="javascript:void(0);" onclick="closeShareInfoDiv();"><font
					color='red'>关闭</font> </a> </span>
		</h3>
		<div class="trend-sel" id="windowOpenDiv_content">这是弹出正文</div>
	</div>
</div>

<div id="floatTips" class="floatTips" style="display: none;">
	<a href="javascript:vold(0);" onclick="showSendDiv();"> <img
		src="../common/images/index_pop1.gif" />
	</a>
	<div class="floatTips2">
		<div align="center">
			<div style="width: 90px; height: auto">
				<a href="javascript:vold(0);" onclick="showShareInfoDiv();">您当前还有(<span
					id="messageNum" style="color: red"></span>)条未读分享短信</a>
			</div>
		</div>
	</div>

</div>
<!-- 弹窗结束 -->




<!-- 大弹窗开始 -->
<div class="dnapop_home" id="windowpop" style="display: none">
	<div class="dnapop_bg_1">
		<table width="100%" height="100%" border="0" cellpadding="0"
			cellspacing="0">
			<tr>
				<td width="90%" height="30"><div class="pop_yhxx">用户信息记录</div>
				</td>
				<td width="10%"><div style="padding-top: 3px">
						<a href="javascript:vold(0);"
							onclick="Javascript:document.getElementById('windowpop').style.display='none';"><img
							src="../common/images/pop_1.gif" width="15" height="16"
							border="0" />
						</a>
					</div>
				</td>
			</tr>
		</table>
	</div>

	<div class="dna_pop_centerbg">
		<div class="dna_pop_home">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="16%" height="40" valign="top">

						<div class="tab">
							<h6 style="margin-top: 5px">
								<span id="receiveBut" class="current"
									onclick="showReceiveInfo();">已接收共享</span> <span id="sendBut"
									onclick="showSendInfo();">已发送共享</span>
							</h6>

							<div class="block" style="margin-top: -20px;">
								<iframe id="shareInfoIframe" name="shareInfoIframe"
									scrolling="no" frameBorder="0" width="100%"
									onLoad="iFrameHeight();"></iframe>

							</div>

						</div></td>
				</tr>
			</table>
		</div>

		<!--     <div align="center" style=" margin-top:3px"><a href="javascript:vold(0);"><img src="../common/images/pop_close_1.gif" width="64" height="23" /></a></div> -->
	</div>
	<div>
		<img src="../common/images/pop_up9.png" />
	</div>
</div>


<!-- 大弹窗结束 -->



<!-- 大弹窗开始2-->
<div class="dnapop_home" id="replayInfo" style="display: none">
	<div class="dnapop_bg_1">
		<table width="100%" height="100%" border="0" cellpadding="0"
			cellspacing="0">
			<tr>
				<td width="90%" height="30"><div class="pop_yhxx">用户留言记录</div>
				</td>
				<td width="10%"><div style="padding-top: 3px">
						<a href="javascript:vold(0);"
							onclick="Javascript:document.getElementById('replayInfo').style.display='none';"><img
							src="../common/images/pop_1.gif" width="15" height="16"
							border="0" />
						</a>
					</div>
				</td>
			</tr>
		</table>
	</div>

	<div class="dna_pop_centerbg">
		<div class="dna_pop_home">
			<iframe id="replayInfoIframe" frameBorder="0" width="100%"
				onLoad="replayIframeHeight();" scrolling="no"
				name="replayInfoIframe"></iframe>

		</div>

	</div>
	<div class="index_pop_bottom">
		<div style="padding-top: 15px">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="75%"><div align="right">
							<a href="javascript:vold(0);" onclick="sendReplayInfo();"><img
								src="../common/images/index_pop_bottom2.gif" />
							</a>
						</div>
					</td>
					<td width="25%"><div align="left" style="padding-left: 10px"
							onclick="closeReplayDiv();">
							<a href="javascript:vold(0);"><img
								src="../common/images/index_pop_bottom3.gif" />
							</a>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</div>
</div>