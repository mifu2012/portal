<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.infosmart.portal.pojo.DwpasCKpiInfo"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	String homePage = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${systemName}</title>
<link href="<%=homePage%>/common/css/css.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="<%=homePage%>/common/js/public.js"></script>
<script type='text/javascript' src='<%=homePage%>/common/js/dropdown.js'></script>
</head>
<body class="indexbody">
	<div class="index_top">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="12%"><img
					src="<%=homePage%>/common/images/wph_1.gif" width="124" height="80" />
				</td>
				<td width="46%" valign="bottom" class="top_logo">经纬仪</td>
				<td width="35%" valign="top">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="70%" height="26"><div align="right">
									<img src="<%=homePage%>/common/images/wph_6.gif" width="17"
										height="18" />
								</div></td>
							<td width="17%"><a href="javascript:void(0);"
								onclick="openWindow('<%=homePage%>/pages/home_page/password_update.jsp','修改密码',175,350);">修改密码</a>
							</td>
							<td width="6%">
								<div align="right">
									<img src="<%=homePage%>/common/images/wph_5.gif" width="17"
										height="18" />
								</div></td>
							<td width="7%"><div align="right">
									<a
										　href="javascript:if(confirm('您确认要退出吗？'))location.href='UserController.do?method=logout'">退出</a>
								</div></td>
						</tr>
						<tr>
							<td height="25" colspan="4">&nbsp;</td>
						</tr>
						<tr>
							<td colspan="4"><div align="left">
									<div align="right">
										<table width="100%" border="0" cellspacing="0" cellpadding="0"
											class="ddy_index_link">
											<tr>
												<!--  <td width="61%"><div align="right">数据时间：</div></td>
                      <td width="5%"><div align="center"><a href="#"><img src="dwpas2/images/ddy_2.gif" width="11" height="23" border="0" /></a></div></td>
                      <td width="29%"><input name="textfield4" type="text" class="dwpas2/ddy_index_input" value="2012年1月20日" />
                      </td> -->
												<td width="5%"><div align="center">
														<a href="UserController.do?method=enterPortal"><img
															src="<%=homePage%>/common/images/fh_01.gif" width="78"
															height="23" border="0" /> </a>
													</div></td>

											</tr>
										</table>
									</div>
								</div></td>
						</tr>
					</table></td>
			</tr>
		</table>
	</div>
	<div class="index_nav">
		<div class="index_nav_center">
			<!--下拉导航开始-->
			<div id="nav">
				<ul id="navMenu">
					<li><a href='<%=homePage%>/index/getInto.html'
						target="center_iframe">首页</a></li>
					<li><a href='<%=homePage%>/Champion/getChampions.html'
						target="center_iframe">产品龙虎榜</a></li>
					<li><a
						href='<%=homePage%>/productHealth/showProductHealth.html'
						rel='dropmenu2' target="center_iframe">产品健康度</a></li>
					<li><a href='<%=homePage%>/prodAnalyze/kpiAnalyze.html'
						target="center_iframe">产品指标分析</a></li>
					<!--  
					<li><a href='KpiInfoController.do?method=queryKpiInfos'
						rel='dropmenu3' target="center_iframe">系统管理</a>
					</li>
					-->
				</ul>
			</div>
			<!--  
			<script type='text/javascript' src='js/dropdown.js'></script>
			<ul id="dropmenu1" class="dropMenu">
				<li><a href="KpiInfoController.do?method=queryKpiInfos"
					target="center_iframe">指标管理</a>
				</li>
				<li><a href="CommonKpiInfoController.do?method=searchCommonKPI"
					target="center_iframe">通用指标管理</a>
				</li>
				<li><a href="PrdMngController.do?method=qryPrdInfo"
					target="center_iframe">产品信息管理</a>
				</li>
				<li><a href="<%=homePage%>/productHealth/showProductHealth.html"
					target="center_iframe">产品健康度</a>
				</li>
				<li><a href="MISTypeController.do?method=showMISTYpe"
					target="center_iframe">系统类型配置</a>
				</li>
			</ul>
			-->
			<ul id="dropmenu2" class="dropMenu">

				<li><a href="<%=homePage%>/ProductDev/doPost.html"
					target="center_iframe">产品发展</a></li>
				<li><a href="<%=homePage%>/UserKeep/showUserKeep.html?kpiType=<%=DwpasCKpiInfo.KPI_TYPE_OF_MONTH%>"
					target="center_iframe">用户留存</a></li>
				<li><a href="<%=homePage%>/userExperience/showUserExperience.html"
					target="center_iframe">用户体验</a></li>
				<li><a
					href="<%=homePage%>/userVoice/showUserVoice.html"
					target="center_iframe">用户声音</a></li>
				<li><a
					href="<%=homePage%>/userFeature/PrepareUserFeature.html"
					target="center_iframe">用户特征</a></li>
				<li><a href="<%=homePage%>/CrossUser/doGet.html?kpiType=<%=DwpasCKpiInfo.KPI_TYPE_OF_MONTH%>"
					target="center_iframe">场景交叉</a></li>

			</ul>
			<!--
			<ul id="dropmenu3" class="dropMenu">
				<li><a href="KpiInfoController.do?method=queryKpiInfos"
					target="center_iframe">指标管理</a>
				</li>
				<li><a href="CommonKpiInfoController.do?method=searchCommonKPI"
					target="center_iframe">通用指标管理</a>
				</li>
				<li><a href="PrdMngController.do?method=qryPrdInfo"
					target="center_iframe">产品信息管理</a>
				</li>
				<li><a href="PrdDimController.do?method=qryPrdInfoList"
					target="center_iframe">产品健康度</a>
				</li>
				<li><a href="MISTypeController.do?method=showMISTYpe"
					target="center_iframe">系统类型配置</a>
				</li>
			</ul>
			-->
			<!-- <script type="text/javascript">
				cssdropdown.startchrome("navMenu")
			</script> -->
			<!--下拉导航结束-->
		</div>
	</div>
</body>
</html>
<script type="text/javascript">
<!--
	var objs = document.getElementsByTagName("a");
	if (objs != null && objs.length > 0) {
		for ( var i = 0; i < objs.length; i++) {
			if (objs[i].parentNode.nodeName.toLowerCase() == "li") {
				objs[i].onclick = function() {
					var loadingDiv=document.getElementById("loading_wait_Div");
					if(loadingDiv!=null) loadingDiv.style.display="";
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
					var iframe = document.getElementById('center_iframe');
                    try
                    {
					   if (iframe.attachEvent){    
							iframe.attachEvent("onload", function()
							{        
								loadingDiv.style.display="none";   
							});
						} else {    
							iframe.onload = function(){        
								loadingDiv.style.display="none";  
							};
						}
                    }
                    catch (e)
                    {
						if(iframe.readyState == "complete")
						{
							//如果加载完成
							if(loadingDiv!=null) loadingDiv.style.display="none";
						}else
						{
							if(loadingDiv!=null) loadingDiv.style.display="block";
						}
                    }
				}
			}
		}
	}
//-->
</script>