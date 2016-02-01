<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<title>经营分析系统</title>
<%-- <link href="<%=path %>/common/images/skin.css" rel="stylesheet" type="text/css" /> --%>
<style type="text/css">
	body{margin-left: 0px;margin-top: 0px;margin-right: 0px;margin-bottom: 0px;background-color: #7ea81a;}
	.header{width:100%;height:41px;no-repeat 0 56px;}
	.center{width:100%;height:532px;background: url(common/images/login_bg.jpg) repeat-x;}
	.login_right{float:right;width:50%;height:100%;background: url(common/images/login-wel.gif) bottom no-repeat;}
	.login_left{float:right;width:285px;height:100%;background: url(common/images/login-content-bg.gif) no-repeat;}
	.login_title{margin-left:35px;font-family: Arial, Helvetica, sans-serif;font-size: 14px;height:36px;line-height: 36px;color: #666666;font-weight: bold;}
	.login_info{margin-left:35px;font-family: Arial, Helvetica, sans-serif;font-size: 12px;height:36px;line-height: 36px;color: #333333;}
	.login_input{width:150px;height:20px;margin-left:30px;border:1px solid #7F9DB9;vertical-align: middle;}
	.login_code{width:80px;height:20px;margin-left:30px;border:1px solid #7F9DB9;vertical-align: middle;}
	.btn{width:60px;height:25px;border-width:0px;background-image: url(common/images/btn-bg2.gif);letter-spacing: 3px;margin-right:70px;cursor: pointer;}
	.login_info img{vertical-align: middle;cursor: pointer;}
	
	.errInfo{display:none;color:red;}
	
	.logo{margin-left:-120px; width:387px;height:86px;background: url(common/images/userLogo.png) no-repeat;_background:none;_filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='common/images/userLogo.gif';)}
	.left_txt{font-family: Arial, Helvetica, sans-serif;font-size: 12px;line-height: 25px;color: #666666;}
	
	.bottom{width:100%;height:auto;text-align:center;font-family: Arial, Helvetica, sans-serif;font-size: 10px;color: #ABCAD3;text-decoration: none;line-height: 20px;}
	.overlay {
    display:none;
    position:absolute;
    top:0%;
    left:0%;
    width:100%;
    height:100%;
    z-index:999;
    background-color:gray;
    opacity: 0.2; 
    filter:alpha(opacity=20);
    }
</style>
<script type="text/javascript">
/* 检查是否安装了FLASH播放器 */
//Powered By smvv @hi.baidu.com/smvv21
function flashChecker() {
    var hasFlash = 0;　　　　 //是否安装了flash
    var flashVersion = 0;　　 //flash版本
    if (document.all) {
        var swf = new ActiveXObject('ShockwaveFlash.ShockwaveFlash');
        if (swf) {
            hasFlash = 1;
            VSwf = swf.GetVariable("$version");
            flashVersion = parseInt(VSwf.split(" ")[1].split(",")[0]);
        }
    } else {
        if (navigator.plugins && navigator.plugins.length > 0) {
            var swf = navigator.plugins["Shockwave Flash"];
            if (swf) {
                hasFlash = 1;
                var words = swf.description.split(" ");
                for (var i = 0; i < words.length; ++i) {
                    if (isNaN(parseInt(words[i]))) continue;
                    flashVersion = parseInt(words[i]);
                }
            }
        }
    }
    return {
        f: hasFlash,
        v: flashVersion
    };
}

var fls = flashChecker();
var s = "";
if (fls.f) 
{
//    document.write("您安装了flash,当前flash版本为: " + fls.v + ".x");
}else
{
   alert("检查到您所用的浏览器没有安装flash，为更好的体验经营分析系统，请安装flash 8以上版本。");
}
</script>

<script type="text/javascript">
    window.onload=function()
    {
    	//${needVerification}
    	// 最顶层窗口显示
    	if (window.top != window) {
    		window.top.document.location.href = document.location.href;
    	}
    	if(document.getElementById("loginName").value==""){
    		document.getElementById("loginName").focus();
    		return;
    	}
    	if(document.getElementById("passWord").value==""){
    		document.getElementById("passWord").focus();
    		return;
    	}
    	if(document.getElementById("validateing").value==""){
    		document.getElementById("validateing").focus();
    		return;
    	}
    }
	//验证码点击刷新 hgt
	function changeValidateImg() {
		document.getElementById("validateImg").src = "<%=path%>/common/images/VerificationCodeImg.gif";
		document.getElementById("validateImg").title='正在生成验证码...';
		setTimeout("changeValidateImging()", 800);//时间
	}
	function changeValidateImging() {
		var id= Math.round((Math.random()) * 100000000);
		document.getElementById("validateImg").src = "jsp/Verification/CheckCode.jsp?id="+id;
		document.getElementById("validateImg").title='请输入验证码...';
		var validateing = document.sign.validate;
		validateing.value = "";
		validateing.focus(); 
	}
	function changImg(imgUrl){
		
		var img=document.getElementById('theImg');
		
	    img.src=imgUrl;
		
	}
     function returnImg(imgUrl){
		
		var img=document.getElementById('theImg');
		
	    img.src=imgUrl;
		
	}
     
     
	function login(){
        var loadingDiv=document.getElementById("loading_wait_Div");
    	if(loadingDiv!=null){ loadingDiv.style.display="";}
    	if(loadingDiv==null)
    	{
    		loadingDiv=document.createElement("div");
    		loadingDiv.id="loading_wait_Div";
    		loadingDiv.style.position="absolute";
    		loadingDiv.style.left="45%";
    		loadingDiv.style.top="40%";
    		loadingDiv.innerHTML="<img src='<%=request.getContextPath()%>/common/images/loading.gif'>";
    			document.body.appendChild(loadingDiv);
    		}
    	var overlay = document.getElementById("overlay");
    	if(overlay.style.display="none"){
    		var dd = document.documentElement;
            var db = document.body;
        	overlay.style.width=Math.max(document.documentElement.scrollWidth, document.documentElement.clientWidth) + "px";
            overlay.style.height=Math.max(document.documentElement.scrollHeight, document.documentElement.clientHeight) + "px";
            overlay.style.display="block";
            //document.body.style.overflow="hidden";//隐藏滚动条
    	}else{
    		overlay.style.display="none";
    	}
    	document.loginForm.submit();
	}
	window.onunload=function(){
    	var loadingDiv=document.getElementById("loading_wait_Div");
    	if(loadingDiv!=null){ 
    		loadingDiv.style.display="none";
    		}	
    	}

	if (document.addEventListener)  
    {//如果是Firefox   
        document.addEventListener("keypress", fireFoxHandler, true);   
    } else
    {   
        document.attachEvent("onkeypress", ieHandler);   
    }   
    function fireFoxHandler(evt)  
    {   
        //alert("firefox");   
        if (evt.keyCode == 13)  
        {   
            login();  
        }   
    }   
    function ieHandler(evt)  
    {   
        //alert("IE");   
        if (evt.keyCode == 13)  
        {   
            login();  
        }   
    }   
   
</script>
</head>

<body>
<div id="overlay" class="overlay"></div>
<div style="width:100%; height:127px;  repeat-x;)"></div>
<div style="width:100%;height:645px;position: absolute;top:50%;left:50%;margin-top:-320px;margin-left:-50%; background-color:#7ea81a">
	<div class="header"></div>
	<div class="center">
		<div class="login_right">
			<div style="width:100%;height:auto;margin-top:150px;">
			<form action="login" method="post" name="loginForm" onsubmit="return check();">
				<div class="login_title">
					用户登录
				</div>
				<div class="login_info">
					<label>用户名：</label><input type="text" name="loginName" style="ime-mode:disabled" id="loginName"  class="login_input" value="${loginName }"/>
					&nbsp;<span id="nameerr" class="errInfo"></span>
				</div>
				<div class="login_info">
					<label>密　码：</label><input type="password" name="passWord" id="passWord" class="login_input" value="${passWord }"/>
					&nbsp;<span id="pwderr" class="errInfo"></span>
				</div>
				<div class="login_info" <c:if test="${needVerification eq 0}">style="display: none"</c:if>>
					<label>验证码：</label><input type="text" name="validateing" id="validateing" style="ime-mode:disabled" class="login_code" size="5" maxlength="4"/>&nbsp;&nbsp;
					<img id="validateImg" align="absmiddle" style="cursor:pointer;" title="点击更换验证码." onclick="changeValidateImg();" src="jsp/Verification/CheckCode.jsp" id="validateImg" />
					&nbsp;<span id="codeerr" class="errInfo"></span>
				</div>
				<div class="login_info" style="padding:5px 0px 0px 78px">
					<a href="javascript:void(0);"  onclick="login();"  onMouseOut="returnImg('common/images/dl_1.gif');" onMouseOver="changImg('common/images/dl_2.png');"><img src="common/images/dl_1.gif" id="theImg"   border="0"/></a>
				</div>
			</form>
			</div>
		</div>
		<div class="login_left">
			<div style="width:100%;height:auto;margin-top:150px;">
				<div class="logo"></div>
				<div class="left_txt">
					<span style="margin-left: -80px;">1.瞭望塔：一目了然公司整体状况。</span><br/>
					<span style="margin-left: -80px;">2.经纬仪：产品粒度把握经营动态。</span><br/>
					<span style="margin-left: -80px;">3.自定义报表：灵活配置展现数据。</span><br/>
					<br/>
				</div>
			</div>
		</div>
	</div>
	<div class="bottom">
	Copyright &copy; 2012 Infosmart
	</div>
</div>






<!-- 
<script type="text/javascript">
		var errInfo = "${errInfo}";
		
		
		function resetErr(){
			$("#signInName").hide();
			$("#signInName").html("");
			$("#signInPassWord").hide();
			$("#signInPassWord").html("");
			$("#signInCode").hide();
			$("#signInCode").html("");
		}
		
		function check(){
			resetErr();
			if($("#loginName").val()==""){
				$("#signInName").show();
				$("#signInName").html("用户名不得为空！");
				$("#loginName").focus();
				return false;
			}
			if($("#passWord").val()==""){
				$("#signInPassWord").show();
				$("#signInPassWord").html("密码不得为空！");
				$("#passWord").focus();
				return false;
			}
			if($("#validateing").val()==""){
				$("#signInCode").show();
				$("#signInCode").html("验证码不得为空！");
				$("#validateing").focus();
				return false;
			}
			return true;
		}
	</script> -->

</body>
</html>
