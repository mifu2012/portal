<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>经营分析后台管理</title>
<link type="text/css" rel="stylesheet" href="css/index.css" />
<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
</head>
<script type="text/javascript">
<!--
    var isLoginOut=false;
	//最大化
	self.moveTo(0,0);
	self.resizeTo(window.screen.width,window.screen.availHeight);
	//退出
	window.onbeforeunload = onunload_handler;
	window.onunload = onunload_handler;
	//调整大小
	window.onresize=function()
	{
	   //alert('调整窗口了');
	    /*
	   var totalHight=document.body.clientHeight;
	   var mainWorkFrame=document.getElementById("mainFrame");
		   mainWorkFrame.height=totalHight-69;
		   mainWorkFrame.width=document.body.clientWidth-69;
	   */
	   //最大化
	    window.moveTo(-4,-4);
		window.resizeTo(screen.availWidth+9,screen.availHeight+9);
	}
	//退出前提示
	function onbeforeunload_handler(){
	   if(!isLoginOut)
	   {
          return "确定退出当前系统";
       }
    }
    //退出系统
	function onunload_handler(){
	  if(isLoginOut) return;
      //转到登陆界面
      //document.location = "logout.html";      
    }
//-->
</script>
<body scroll='no'>
	<div class="main_header">
		<div onclick="document.location = 'index.html'" class="header_left"></div>
		<div class="header_right">
			<span id="sysTime"></span> <span>${user.username }，您好！</span> <a
				href="javascript:logout();"><img src="images/out.gif" border="0" />
			</a>
		</div>
	</div>
	<table class="main_content">
		<tr>
			<td align="left" valign="top">
				<div class="content_left">
				<%int i = 0;
				  int j = 0;
				%>
					<c:forEach items="${menuList}" var="menu">
						<c:if test="${menu.hasMenu}">
						<%i++; %>
							<h1>
								<a>${menu.menuName }</a>
							</h1>
							<div class="menu_line">
								<ul>
									<c:forEach items="${menu.subMenu}" var="sub">
										<c:if test="${sub.hasMenu}">
											<c:choose>
												<c:when test="${not empty sub.menuUrl}">
												<%j++; %>
												<%if(i == 1 && j==1){
													%>
													<c:set var="url" scope="session" value="${sub.menuUrl }"/>
													<c:set var="name" scope="session" value="${sub.menuName }"/>
													<%
												} %>
													<li><a href="<%=basePath%>${sub.menuUrl }" target="mainFrame">${sub.menuName
															}</a>
													</li>
												</c:when>
												<c:otherwise>
													<li><a href="javascript:void(0);" target="mainFrame">${sub.menuName
															}</a>
													</li>
												</c:otherwise>
											</c:choose>
										</c:if>
									</c:forEach>
								</ul>
							</div>
						</c:if>
					</c:forEach>
				</div></td>
			<!-- 			<td>
				<div class="content_center">&nbsp;</div>
			</td>
 -->
			<td>
				<div class="content_right">
					<table cellspacing="0" cellpadding="0">
						<tr
							style="height: 29px; background: url('images/content-bg.gif') repeat-x;">
							<td
								style="height: 29px; width: 17px; background: url('images/left-top-right.gif') no-repeat;"></td>
							<td style="height: 29px; line-height: 29px;"><div
									class="content_title">首页</div>
							</td>
							<td
								style="height: 29px; width: 16px; background: url('images/nav-right-bg.gif') no-repeat;"></td>
						</tr>
						<tr>
							<td style="background: url('images/mail_leftbg.gif') repeat-y;"></td>
							<td style="background-color: #FAFBFD;"><iframe
									name="mainFrame" id="mainFrame" frameborder="0"
									src="default.html" scrolling="auto"
									style="width: 100%; height: 100%;"></iframe></td>
							<td style="background: url('images/mail_rightbg.gif') repeat-y;"></td>
						</tr>
						<tr style="background: url('images/buttom_bgs.gif') repeat-x;">
							<td
								style="height: 17px; background: url('images/buttom_left2.gif') no-repeat;"></td>
							<td></td>
							<td
								style="height: 17px; background: url('images/buttom_right2.gif') no-repeat;"></td>
						</tr>
					</table>
					<div class="sys_bottom">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Copyright
						&copy; 2012 Infosmart</div>
				</div>
			</td>
		</tr>
	</table>
	<script type="text/javascript">
		$(document)
				.ready(
						function() {
							getTime();
							window.setInterval(getTime, 1000);
							$(".content_title").html("${name}");
							$("h1 a").bind("click", function() {
								$("h1 a").each(function(i){ 
									var obj = $(this).parent().next();
									obj.slideUp();
								});
								var obj = $(this).parent().next();
								obj.slideToggle("fast","linear");
								//obj.fadeToggle("fast", "linear");
								initRightContentHeightAndWidth();
							}); 
							$("h1 a").each(function(i){ 
								var obj = $(this).parent().next();
								obj.slideUp();
							});
							$("h1 a:eq(0)").parent().next().slideToggle("fast","linear");
							$(".menu_line a")
									.bind(
											"click",
											function() {
												$(".content_title").html(
														$(this).html());
												//$("#mainFrame").attr("src","");
												$(".menu_line a")
														.css(
																"background-image",
																"url('images/menu_bg1.gif')");
												$(".menu_line a").css("color",
														"#333333");
												$(".menu_line a")
														.css("font-weight",
																"normal");
												$(".menu_line a")
														.bind(
																{
																	mouseover : function() {
																		$(this)
																				.css(
																						"background-image",
																						"url('images/menu_bg2.gif')");
																		$(this)
																				.css(
																						"color",
																						"#006600");
																		$(this)
																				.css(
																						"font-weight",
																						"bold");
																	},
																	mouseout : function() {
																		$(this)
																				.css(
																						"background-image",
																						"url('images/menu_bg1.gif')");
																		$(this)
																				.css(
																						"color",
																						"#333333");
																		$(this)
																				.css(
																						"font-weight",
																						"normal");
																	}
																})
												$(this)
														.css(
																"background-image",
																"url('images/menu_bg2.gif')");
												$(this).css("color", "#006600");
												$(this).css("font-weight",
														"bold");
												$(this).unbind("mouseout");
											});
							initRightContentHeightAndWidth();
						});

		function initRightContentHeightAndWidth() {
			var height = $(window).height() < $(document).height() ? $(window)
					.height() : $(document).height();
			var width = $(window).width() < $(document).width() ? $(window)
					.width() : $(document).width();
			$(".content_right table").width(
					width - $(".content_left").width()
							- $(".content_center").width());
			$(".content_right table tr:eq(1) td").height(
					height - $(".main_header").height()
							- $(".sys_bottom").height() - 46);
		}

		function getTime() {
			var date = new Date();
			var y = date.getFullYear();
			var m = date.getMonth() + 1;
			var d = date.getDate();
			var h = date.getHours();
			var i = date.getMinutes();
			var s = date.getSeconds();
			$("#sysTime").html(
					y + "年" + (m > 9 ? m : ("0" + m)) + "月"
							+ (d > 9 ? d : ("0" + d)) + "日 "
							+ (h > 9 ? h : ("0" + h)) + ":"
							+ (i > 9 ? i : ("0" + i)) + ":"
							+ (s > 9 ? s : ("0" + s)));
		}

		function logout() {
			if (confirm("确定要退出吗？")) {
				document.location = "logout.html";
			}
		}

		function changeIframeSize(iframeContentHeight) {
			$(".content_right table tr:eq(1) td").height(iframeContentHeight);
		}
	</script>
</body>
</html>