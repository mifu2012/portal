<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>经营分析后台管理</title>
<style type="text/css">
	body{margin-left: 0px;margin-top: 0px;margin-right: 0px;margin-bottom: 0px;background-color: #1B3142;}
	.header{width:100%;height:41px;background: url(images/login-top-bg.gif) repeat-x;}
	.center{width:100%;height:532px;background: url(images/login_bg.jpg) repeat-x;}
	.login_right{float:right;width:50%;height:100%;background: url(images/login-wel.gif) bottom no-repeat;}
	.login_left{float:right;width:295px;height:100%;background: url(images/login-content-bg.gif) no-repeat;}
	.login_title{margin-left:35px;font-family: Arial, Helvetica, sans-serif;font-size: 14px;height:36px;line-height: 36px;color: #666666;font-weight: bold;}
	.login_info{margin-left:35px;font-family: Arial, Helvetica, sans-serif;font-size: 12px;height:36px;line-height: 36px;color: #333333;}
	.login_input{width:150px;height:20px;margin-left:30px;border:1px solid #7F9DB9;vertical-align: middle;}
	.login_code{width:70px;height:20px;margin-left:30px;border:1px solid #7F9DB9;vertical-align: middle;}
	.btn{width:60px;height:25px;border-width:0px;background-image: url(images/btn-bg2.gif);letter-spacing: 3px;margin-right:70px;cursor: pointer;}
	.login_info img{vertical-align: middle;cursor: pointer;}
	
	.errInfo{display:none;color:red;}
	
	.logo{width:100%;height:68px;background: url(images/logo2.png) no-repeat;_background:none;_filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='images/logo2.png';)}
	.left_txt{font-family: Arial, Helvetica, sans-serif;font-size: 12px;line-height: 25px;color: #666666;}
	
	.bottom{width:100%;height:auto;text-align:center;font-family: Arial, Helvetica, sans-serif;font-size: 10px;color: #ABCAD3;text-decoration: none;line-height: 20px;}
</style>
<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
</head>
<body>
<div style="width:100%;height:645px;position: absolute;top:50%;left:50%;margin-top:-320px;margin-left:-50%;">
	<div class="header"></div>
	<div class="center">
		<div class="login_right">
			<div style="width:100%;height:auto;margin-top:150px;">
			<form action="login" method="post" name="loginForm" onsubmit="return check();">
				<div class="login_title">
					用户登录
				</div>
				<div class="login_info">
					<label>用户名：</label><input type="text" name="loginname" style="ime-mode:disabled" id="loginname" class="login_input" value="${loginname }"/>
					&nbsp;<span id="nameerr" class="errInfo"></span>
				</div>
				<div class="login_info">
					<label>密　码：</label><input type="password" name="password" id="password" class="login_input" value="${password }"/>
					&nbsp;<span id="pwderr" class="errInfo"></span>
				</div>
				<div class="login_info" <c:if test="${needVerification eq 0}">style="display: none"</c:if> >
					<label>验证码：</label><input type="text" name="code" style="ime-mode:disabled" id="code" class="login_code"/>&nbsp;&nbsp;
					<img id="codeImg" alt="点击更换" title="点击更换" src=""/>
					&nbsp;<span id="codeerr" class="errInfo"></span>
				</div>
				<div class="login_info" style="padding:5px 0px 0px 78px">
					<a href="javascript:void(0);" onclick="login();" onMouseOut="returnImg('images/dl_1.gif');" onMouseOver="changImg('images/dl_2.png');"><img src="images/dl_1.gif" id="theImg"  border="0"/></a>
					
				</div>
			</form>
			</div>
		</div>
		<div class="login_left">
			<div style="width:100%;height:auto;margin-top:150px;">
				<div class="logo"></div>
				<div class="left_txt">
					<span style="margin-left: 60px;">1.瞭望塔：一目了然公司整体状况。</span><br/>
					<span style="margin-left: 60px;">2.经纬仪：产品粒度把握经营动态。</span><br/>
					<span style="margin-left: 60px;">3.自定义报表：灵活配置展现数据。</span><br/>
					<br/>
				</div>
			</div>
		</div>
	</div>
	<div class="bottom">
	Copyright &copy; 2012 Infosmart
	</div>
</div>
	<script type="text/javascript">
		var errInfo = "${errInfo}";
		$(document).ready(function(){
			//如果是弹窗，显示登录窗口，则关闭窗口，父级页面转到登录页面
			try
			{
			  var dg=frameElement.lhgDG;
			  if(dg!=null)
			  {
				  dg.cancel();
				  frameElement.lhgDG.curWin.document.location.reload();  
				  return;
			  }
			}catch(e){}
			changeCode();
			$("#codeImg").bind("click",changeCode);
			if(errInfo!=""){
				if(errInfo.indexOf("验证码")>-1){
					$("#codeerr").show();
					$("#codeerr").html(errInfo);
					$("#code").focus();
				}else{
					$("#nameerr").show();
					$("#nameerr").html(errInfo);
				}
			}
			$("#loginname").focus();
		});
	
		function genTimestamp(){
			var time = new Date();
			return time.getTime();
		}
	
		function changeCode(){
			$("#codeImg").attr("src","code?random="+Math.random());
		}
		
		function resetErr(){
			$("#nameerr").hide();
			$("#nameerr").html("");
			$("#pwderr").hide();
			$("#pwderr").html("");
			$("#codeerr").hide();
			$("#codeerr").html("");
		}
		
		function check(){
			resetErr();
			if($("#loginname").val()==""){
				$("#nameerr").show();
				$("#nameerr").html("用户名不得为空！");
				$("#loginname").focus();
				return false;
			}
			if($("#password").val()==""){
				$("#pwderr").show();
				$("#pwderr").html("密码不得为空！");
				$("#password").focus();
				return false;
			}
			if($("#code").val()==""){
				$("#codeerr").show();
				$("#codeerr").html("验证码不得为空！");
				$("#code").focus();
				return false;
			}
			return true;
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
		document.loginForm.submit();
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
            document.loginForm.submit();   
        }   
    }   
    function ieHandler(evt)  
    {   
        //alert("IE");   
        if (evt.keyCode == 13)  
        {   
            document.loginForm.submit();   
        }   
    } 
    window.onload=function()
    {
    	if(window.parent!=null&&window.parent.frames["mainFrame"]!=null)
    	{
    		window.parent.document.location.href=document.location.href;	
    	}
    }
    
	</script>
</body>
</html>