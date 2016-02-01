<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="com.infosmart.portal.pojo.DwpasCKpiInfo"%>
<%@ page import="java.util.Date"%>
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
<title>唯品会瞭望塔分析系统-首页</title>
<link href="css/lwt.css" rel="stylesheet" type="text/css" />
<script src="<%=path%>/common/amchart/column/amcolumn/swfobject.js"
	type="text/javascript"></script>
<script src="<%=path%>/common/amchart/column/amcolumn/swfobject1.js"
	type="text/javascript"></script>
<script src="<%=path%>/common/amchart/stock/amstock/swfobject.js"
	type="text/javascript"></script>
<script type="text/javascript" src="<%=path%>/common/js/amcharts.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/amfallback.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/raphael.js"></script>
<script type="text/javascript"
	src="<%=path%>/common/js/jquery-1.4.3.min.js"></script>
	
<script>
function $(ID){
return document.getElementById(ID)
}
function showTab(ID){
for(var i=1;i<7;i++){
if(ID==i){
$('tab'+i).blur();
$("tab"+i).className="on";
$("cont"+i).style.display="block";
}else{
$("tab"+i).className="off";
$("cont"+i).style.display="none";
}
}
return false;
}
</script>

<script>
function $(ID){
return document.getElementById(ID)
}
function showTap(ID){
for(var i=1;i<7;i++){
if(ID==i){
$('tap'+i).blur();
$("tap"+i).className="on";
$("conb"+i).style.display="block";
}else{
$("tap"+i).className="off";
$("conb"+i).style.display="none";
}
}
return false;
}
</script>


<style type="text/css">
<!--
body {
	background-color: #f7f7f7;
}
-->
</style>
</head>
<body class="indexbody">
	<!--头部结束-->
	<div class="index_home">
		<div>
			<img src="images/wph_8.gif" />
		</div>
		<!--第一部分-->
		<div class="clear"></div>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bgcolor="#f7f7f7" class="lwt_tag">
			<tr>
				<td height="25" valign="top"><span style="color: #0984ab">仪表盘&gt;</span>日报仪表盘</td>
				<td valign="top"><div align="right">报告时间：2012年2月7日</div></td>
			</tr>
		</table>


		<!--仪表盘切换-->
		<script type="text/javascript">
function tab(o, s, cb, ev){ //tab切换类
var $ = function(o){return document.getElementById(o)};
var css = o.split((s||'_'));
if(css.length!=4)return;
this.event = ev || 'onclick';
o = $(o);
if(o){
this.ITEM = [];
o.id = css[0];
var item = o.getElementsByTagName(css[1]);
var j=1;
for(var i=0;i<item.length;i++){
if(item[i].className.indexOf(css[2])>=0 || item[i].className.indexOf(css[3])>=0){
if(item[i].className == css[2])o['cur'] = item[i];
item[i].callBack = cb||function(){};
item[i]['css'] = css;
item[i]['link'] = o;
this.ITEM[j] = item[i];
item[i]['Index'] = j++;
item[i][this.event] = this.ACTIVE;
}
}
return o;
}
}
tab.prototype = {
ACTIVE:function(){
var $ = function(o){return document.getElementById(o)};
this['link']['cur'].className = this['css'][3]; 
this.className = this['css'][2];
try{
$(this['link']['id']+'_'+this['link']['cur']['Index']).style.display = 'none';
$(this['link']['id']+'_'+this['Index']).style.display = 'block';
}catch(e){}
this.callBack.call(this);
this['link']['cur'] = this;
}
}
</script>
		
		<div class="lwttab1">
			<ul id="test1_li_now_">
				<li class="now">日报仪表盘</li>
				<li>金融看板</li>
				<li>用户看板</li>

			</ul>
		</div>
		<div id="test1_1" class="lwt_tablist block"
			style="display: block; margin-top: -10px">
			<!--日报仪表盘-->
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				bgcolor="#f7f7f7">
				<tr>
					<td width="3%" height="38"><div align="center">
							<img src="images/lwt_3.gif" width="19" height="17" />
						</div></td>
					<td width="71%"><span class="lwt_index_title">唯品会日报概览</span></td>
					<td width="26%"><table width="100%" border="0" cellspacing="0"
							cellpadding="0" class="ddy_index_link">
							<tr>
								<td width="61%"><div align="right" style="font-size: 12px">数据时间：</div></td>
								<td width="5%"><div align="center">
										<a href="#"><img src="images/ddy_2.gif" width="11"
											height="23" border="0" /></a>
									</div></td>
								<td width="29%"><input name="textfield4" type="text"
									class="ddy_index_input" value="2012年1月20日" /></td>
								<td width="5%"><div align="center">
										<a href="#"><img src="images/ddy_1.gif" width="11"
											height="23" border="0" /></a>
									</div></td>
							</tr>
						</table></td>
				</tr>
			</table>
			<div class="clear"></div>
			<!--业务量开始-->
			<div class="tabtop58">
				<div class="datattop57">
					<div class="index_title_pd">各省交易状况实时播报</div>
				</div>
				<div class="index_title_yytop52"></div>
				<div class="lwt_tablist22">
					<img src="images/ddy_352.gif" />
				</div>
				<div class="lwt_index_zs">注：排名按照各省交易金额笔数排名</div>
			</div>
			<div class="clear"></div>
			<div class="tabtop5 datattop5_tt3">
				<div class="datattop5">
					<div class="index_title_pd">成交金额</div>
				</div>
				<div class="index_title_yytop5"></div>
				<div class="lwt_tablist22">
					<img src="images/lwt_4.gif" />
				</div>
				<table width="487" border="0" cellpadding="0" cellspacing="0"
					class="f12">
					<tr class="report_listlink">
						<td width="27%" height="30"><div align="center">成交额（万）</div></td>
						<td width="14%"><div align="center">当日值</div></td>
						<td width="14%"><div align="center">日均值</div></td>
						<td width="17%"><div align="center">变化幅度</div></td>
						<td width="14%"><div align="center">日峰值</div></td>
						<td width="14%"><div align="center">峰值日期</div></td>
					</tr>
					<!--循环以下2条TR一条蓝一条拜白-->
					<tr class="report_divblue">
						<td height="30" style="border-right: 3px solid #fff"><div
								align="center">1200</div></td>
						<td style="border-right: 1px solid #fff"><div align="center">-</div></td>
						<td style="border-right: 1px solid #fff"><div align="center">10</div></td>
						<td style="border-right: 1px solid #fff"><div align="center">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="40%"><div align="right">
												<img src="images/lwt_5.gif" width="13" height="17" />
											</div></td>
										<td width="60%"><div align="left">1.5%</div></td>
									</tr>
								</table>
							</div></td>
						<td><div align="center">12:00</div></td>
						<td><div align="center">20110516</div></td>
					</tr>
					<tr class="report_divwhite">
						<td height="30"><div align="center">900</div></td>
						<td><div align="center">-</div></td>
						<td><div align="center">15</div></td>
						<td><div align="center">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="40%"><div align="right">
												<img src="images/lwt_6.gif" width="13" height="17" />
											</div></td>
										<td width="60%"><div align="left">1.5%</div></td>
									</tr>
								</table>
							</div></td>
						<td><div align="center">13:00</div></td>
						<td><div align="center">20110515</div></td>
					</tr>
					<!--循环结束-->
					<!--以下为无效测试代码-->
					<tr class="report_divblue">
						<td height="30" style="border-right: 3px solid #fff"><div
								align="center">1300</div></td>
						<td style="border-right: 1px solid #fff"><div align="center">-</div></td>
						<td style="border-right: 1px solid #fff"><div align="center">17</div></td>
						<td style="border-right: 1px solid #fff"><div align="center">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="40%"><div align="right">
												<img src="images/lwt_5.gif" width="13" height="17" />
											</div></td>
										<td width="60%"><div align="left">1.5%</div></td>
									</tr>
								</table>
							</div></td>
						<td><div align="center">14:24</div></td>
						<td><div align="center">20110514</div></td>
					</tr>
					<tr class="report_divwhite">
						<td height="30"><div align="center">1100</div></td>
						<td><div align="center">-</div></td>
						<td><div align="center">23</div></td>
						<td><div align="center">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="40%"><div align="right">
												<img src="images/lwt_6.gif" width="13" height="17" />
											</div></td>
										<td width="60%"><div align="left">1.5%</div></td>
									</tr>
								</table>
							</div></td>
						<td><div align="center">10:50</div></td>
						<td><div align="center">20110513</div></td>
					</tr>



					<!--测试代码结束-->
				</table>
			</div>
			<!--业务量结束-->
			<!--业务笔数开始-->
			<div class="tabtop5">
				<div class="datattop5">
					<div class="index_title_pd">成交笔数</div>
				</div>
				<div class="index_title_yytop5"></div>
				<div class="lwt_tablist22">
					<img src="images/lwt_4.gif" />
				</div>
				<table width="487" border="0" cellpadding="0" cellspacing="0"
					class="f12">
					<tr class="report_listlink">
						<td width="27%" height="30"><div align="center">成交笔数</div></td>
						<td width="14%"><div align="center">当日值</div></td>
						<td width="14%"><div align="center">日均值</div></td>
						<td width="17%"><div align="center">变化幅度</div></td>
						<td width="14%"><div align="center">日峰值</div></td>
						<td width="14%"><div align="center">峰值日期</div></td>
					</tr>
					<!--循环以下2条TR一条蓝一条拜白-->
					<tr class="report_divblue">
						<td height="30" style="border-right: 3px solid #fff"><div
								align="center">113010</div></td>
						<td style="border-right: 1px solid #fff"><div align="center">-</div></td>
						<td style="border-right: 1px solid #fff"><div align="center">-</div></td>
						<td style="border-right: 1px solid #fff"><div align="center">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="40%"><div align="right">
												<img src="images/lwt_5.gif" width="13" height="17" />
											</div></td>
										<td width="60%"><div align="left">1.5%</div></td>
									</tr>
								</table>
							</div></td>
						<td><div align="center">11:30</div></td>
						<td><div align="center">20110516</div></td>
					</tr>
					<tr class="report_divwhite">
						<td height="30"><div align="center">91000</div></td>
						<td><div align="center">-</div></td>
						<td><div align="center">-</div></td>
						<td><div align="center">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="40%"><div align="right">
												<img src="images/lwt_6.gif" width="13" height="17" />
											</div></td>
										<td width="60%"><div align="left">1.5%</div></td>
									</tr>
								</table>
							</div></td>
						<td><div align="center">15:28</div></td>
						<td><div align="center">20110515</div></td>
					</tr>
					<!--循环结束-->
					<!--以下为无效测试代码-->
					<tr class="report_divblue">
						<td height="30" style="border-right: 3px solid #fff"><div
								align="center">130000</div></td>
						<td style="border-right: 1px solid #fff"><div align="center">-</div></td>
						<td style="border-right: 1px solid #fff"><div align="center">-</div></td>
						<td style="border-right: 1px solid #fff"><div align="center">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="40%"><div align="right">
												<img src="images/lwt_5.gif" width="13" height="17" />
											</div></td>
										<td width="60%"><div align="left">1.5%</div></td>
									</tr>
								</table>
							</div></td>
						<td><div align="center">9:39</div></td>
						<td><div align="center">20110514</div></td>
					</tr>
					<tr class="report_divwhite">
						<td height="30"><div align="center">120050</div></td>
						<td><div align="center">-</div></td>
						<td><div align="center">-</div></td>
						<td><div align="center">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="40%"><div align="right">
												<img src="images/lwt_6.gif" width="13" height="17" />
											</div></td>
										<td width="60%"><div align="left">1.5%</div></td>
									</tr>
								</table>
							</div></td>
						<td><div align="center">12:48</div></td>
						<td><div align="center">20110513</div></td>
					</tr>



					<!--测试代码结束-->
				</table>
			</div>
			<!--业务笔数结束-->

			<div class="clear"></div>
			<!--注释开始-->
			<!--
<div class="lwt_index_zs">注：各个值都是当日值</div>-->
			<!--注释结束-->

			<!--付款成功率开始-->
			<div class="tabtop5 datattop5_tt3">
				<div class="datattop5">
					<div class="index_title_pd">交易成功率</div>
				</div>
				<div class="index_title_yytop5"></div>
				<div align="center">

					<table width="487" border="0" cellspacing="0" cellpadding="0"
						class="f12">
						<tr>
							<td width="38%" height="20">
								<div align="right">
									<div class="lwt_index_blue"></div>
								</div>
							</td>
							<td width="12%"><div align="left">&nbsp;&nbsp;老用户</div></td>
							<td width="6%">
								<div align="right">
									<div class="lwt_index_yellow"></div>
								</div>
							</td>
							<td width="44%"><div align="left">&nbsp;&nbsp;新用户</div></td>
						</tr>
					</table>
					<table width="100%" border="0" cellspacing="0" cellpadding="0"
						class="f12">
						<tr>
							<td><div align="center">&nbsp;</div></td>
							<td><div align="center">&nbsp;</div></td>
							<td><div align="center">&nbsp;</div></td>
						</tr>
						<tr>
							<td width="157"><img src="images/lwt_8.gif" width="157"
								height="150" /></td>
							<td width="157"><img src="images/lwt_8.gif" width="157"
								height="150" /></td>
							<td width="157"><img src="images/lwt_8.gif" width="157"
								height="150" /></td>
						</tr>
					</table>

				</div>

			</div>
			<!--付款成功率结束-->
			<!--快捷支付开始-->
			<div class="tabtop5">
				<div class="datattop5">
					<div class="index_title_pd">交易支付率</div>
				</div>
				<div class="index_title_yytop5"></div>

				<table width="487" border="0" cellspacing="0" cellpadding="0"
					class="f12">
					<tr>
						<td width="5%" height="20"><div align="right">
								<div class="lwt_index_blue"></div>
							</div></td>
						<td width="12%"><div align="left">&nbsp;&nbsp;老用户</div></td>
						<td width="5%"><div align="right">
								<div class="lwt_index_yellow"></div>
							</div></td>
						<td width="12%"><div align="left">&nbsp;&nbsp;新用户</div></td>
						<td width="5%">&nbsp;</td>
						<td width="20%">&nbsp;&nbsp;</td>
						<td width="5%">&nbsp;</td>
						<td width="12%">&nbsp;&nbsp;</td>
						<td width="6%">&nbsp;</td>
						<td width="21%">&nbsp;&nbsp;</td>
					</tr>
				</table>

				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					class="f12">
					<tr>
						<td width="51%"><div align="center">老用户支付率</div></td>
						<td width="49%"><div align="center">新用户支付率</div></td>
					</tr>

					<tr>
						<td height="168" style="border-right: 3px solid #fff"><img
							src="images/lwt_9.gif" width="231" height="168" /></td>
						<td style="border-right: 1px solid #fff"><img
							src="images/lwt_9.gif" width="231" height="168" /></td>
					</tr>

				</table>
			</div>
			<!--快捷支付结束-->


			<div class="clear"></div>
			<!--注释开始-->
			<!--
<div class="lwt_index_zs">注：各个值都是当日值</div>-->
			<!--注释结束-->

			<!--账户注册发布开始-->
			<div class="tabtop5 datattop5_tt3">
				<div class=datattop5>
					<div class="index_title_pd">账户注册发布</div>
				</div>
				<div class="index_title_yytop5"></div>
				<div align="center">


					<table width="100%" border="0" cellspacing="0" cellpadding="0"
						class="f12">
						<tr>
							<td width="45%"><div align="right">当日值较日均值计划</div></td>
							<td width="4%"><img src="images/lwt_6.gif" width="13"
								height="17" /></td>
							<td width="51%"><div align="left">-9.7%</div></td>
						</tr>
					</table>

					<table width="487" border="0" cellspacing="0" cellpadding="0"
						class="f12">
						<tr>
							<td width="235"><div align="center">当日值：33.4万</div></td>
							<td><div align="center">日均值37万</div></td>
						</tr>
						<tr>
							<td><table width="100%" border="0" cellspacing="0"
									cellpadding="0">
									<tr>
										<td><table width="100%" border="0" cellspacing="0"
												cellpadding="0">
												<tr>
													<td width="33%"><div align="center">
															<div class="lwt_index_blue"></div>
														</div></td>
													<td width="67%" height="15" align="left">奢侈品81.51%</td>
												</tr>
												<tr>
													<td><div align="center">
															<div class="lwt_index_green"></div>
														</div></td>
													<td height="15" align="left">闪购12.6%</td>
												</tr>
												<tr>
													<td><div align="center">
															<div class="lwt_index_yellow"></div>
														</div></td>
													<td height="15" align="left">旅行4.5%</td>
												</tr>
												<tr>
													<td><div align="center">
															<div class="lwt_index_red"></div>
														</div></td>
													<td height="15" align="left">其他1.9%</td>
												</tr>
											</table></td>
										<td width="113" height="113"><img src="images/lwt_10.gif"
											width="113" height="113" /></td>
									</tr>
								</table></td>
							<td width="252"><table width="100%" border="0"
									cellspacing="0" cellpadding="0">
									<tr>
										<td><table width="100%" border="0" cellspacing="0"
												cellpadding="0">
												<tr>
													<td width="33%"><div align="center">
															<div class="lwt_index_blue"></div>
														</div></td>
													<td width="67%" height="15" align="left">奢侈品81.51%</td>
												</tr>
												<tr>
													<td><div align="center">
															<div class="lwt_index_green"></div>
														</div></td>
													<td height="15" align="left">闪购12.6%</td>
												</tr>
												<tr>
													<td><div align="center">
															<div class="lwt_index_yellow"></div>
														</div></td>
													<td height="15" align="left">旅行4.5%</td>
												</tr>
												<tr>
													<td><div align="center">
															<div class="lwt_index_red"></div>
														</div></td>
													<td height="15" align="left">其他1.9%</td>
												</tr>
											</table></td>
										<td width="113" height="113"><img src="images/lwt_10.gif"
											width="113" height="113" /></td>
									</tr>
								</table></td>
						</tr>
					</table>

				</div>

			</div>
			<!--账户注册发布结束-->
			<!--首次活跃账户开始-->
			<div class=tabtop5>
				<div class=datattop5>
					<div class="index_title_pd">首次活跃账户</div>
				</div>
				<div class="index_title_yytop5"></div>

				<table width="487" border="0" cellspacing="0" cellpadding="0"
					class="f12">
					<tr>
						<td width="5%" height="20"><div align="right">
								<div class="lwt_index_blue"></div>
							</div></td>
						<td width="12%"><div align="left">&nbsp;&nbsp;老用户</div></td>
						<td width="5%"><div align="right">
								<div class="lwt_index_yellow"></div>
							</div></td>
						<td width="12%"><div align="left">&nbsp;&nbsp;新用户</div></td>
						<td width="5%">&nbsp;</td>
						<td width="20%">&nbsp;&nbsp;</td>
						<td width="5%">&nbsp;</td>
						<td width="12%">&nbsp;&nbsp;</td>
						<td width="6%">&nbsp;</td>
						<td width="21%">&nbsp;&nbsp;</td>
					</tr>
				</table>

				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="51%" height="154" class="lwt_tablist22"><img
							src="images/lwt_11.gif" width="425" height="154" /></td>
					</tr>
				</table>
			</div>
			<!--首次活跃账户结束-->

			<div class="clear"></div>
			<!--注释开始-->
			<!--
<div class="lwt_index_zs">注：各个值都是当日值</div>-->
			<!--注释结束-->
			<!--日报仪表盘结束-->
		</div>
		<!--金融看板开始-->
		<div id="test1_2" class="lwttablist">
			<div id="tab">
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					class="lwttablist3">
					<tr>
						<td width="85%"></td>
						<td width="15%">
							<ul class="lwt_mouseover">
								<li><a href="javascript:void(0)" class="on" id="tab1"
									onclick="showTab('1')">
										<div class="report_list_ftpd">日</div>
								</a></li>
								<li><a href="javascript:void(0)" id="tab2"
									onclick="showTab('2')">
										<div class="report_list_ftpd">周</div>
								</a></li>
								<li><a href="javascript:void(0)" id="tab3"
									onclick="showTab('3')">
										<div class="report_list_ftpd">月</div>
								</a></li>
							</ul>
						</td>


					</tr>
				</table>
			</div>
			<div id="cont">
				<div id="cont1" style="display: block;">
					<div class="yb_bg">
						<div class="yb_bg_tt">男士产品交易情况（按周）</div>
						<div class="yb_bg_tt2"></div>
						<div class="yb_bg_text">&nbsp;</div>
						<div class="yb_bg_but">
							<input name="" type="button" / class="yb_bg_but2" value="收起">
						</div>
					</div>

					<div class="tabtop5 datattop5_tt3 datattop5_tt4">
						<div class=datattop56>
							<div class="index_title_pdt2">
								阿迪达斯本周交易金额（万元）：<span>105.68</span>
							</div>
						</div>

						<div class="lwt_tablist33">
							<img src="images/lw_bg.gif" />
						</div>
					</div>

					<div class="tabtop5 datattop5_tt4">
						<div class=datattop56>
							<div class="index_title_pdt2">
								马基堡本周交易金额完成情况（万元）：<span>2536.32</span>
							</div>
						</div>
						<div class="lwt_tablist33">
						<div id="line1"></div>
<!-- 							<img src="images/wph_04.gif" /> -->
						</div>
					</div>
					<div class="yb_bg">
						<div class="yb_bg_tt">女士产品产品交易情况（按周）</div>
						<div class="yb_bg_tt2"></div>
						<div class="yb_bg_text">&nbsp;</div>
						<div class="yb_bg_but">
							<input name="" type="button" / class="yb_bg_but2" value="收起">
						</div>
					</div>

					<div class="tabtop5 datattop5_tt3 datattop5_tt4">
						<div class=datattop56>
							<div class="index_title_pdt2">
								东之澜本周交易金额（万元）：<span>27.30</span>
							</div>
						</div>

						<div class="lwt_tablist33">
							<img src="images/wph_05.gif" />
						</div>
					</div>
					<div class="tabtop5 datattop5_tt4">
						<div class=datattop56>
							<div class="index_title_pdt2">
								美莎帕本周交易金额完成情况（万元）：<span>538.67</span>
							</div>
						</div>
						<div class="lwt_tablist33">
							<img src="images/wph_06.gif" />
						</div>
					</div>
					<div class="yb_bg">
						<div class="yb_bg_tt">儿童产品交易情况（按周）</div>
						<div class="yb_bg_tt2"></div>
						<div class="yb_bg_text">&nbsp;</div>
						<div class="yb_bg_but">
							<input name="" type="button" / class="yb_bg_but2" value="收起">
						</div>
					</div>
					<div class="yb_bg2">
						<ul>
							<li><div class="yb_bg2_tt">
									<div class="index_title_pdt2">
										爱法贝本周交易金额（万元）：<span>19.38</span>
									</div>
								</div>
								<div class="yb_bg2_text">
									<div id="pie1"></div>
<!-- 									<img src="images/wph_07.gif" /> -->
								</div></li>
							<li><div class="yb_bg2_tt">
									<div class="index_title_pdt2">
										贝贝依依本周交易金额（万元）：<span>29.38</span>
									</div>
								</div>
								<div class="yb_bg2_text">
									<img src="images/wph_08.gif" />
								</div></li>
							<li><div class="yb_bg2_tt">
									<div class="index_title_pdt2">
										奥克汤姆本周交易金额（万元）：<span>32.38</span>
									</div>
								</div>
								<div class="yb_bg2_text">
									<img src="images/wph_09.gif" />
								</div></li>
							<li><div class="yb_bg2_tt">
									<div class="index_title_pdt2">
										柏迪小熊本周交易金额（万元）：<span>12.38</span>
									</div>
								</div>
								<div class="yb_bg2_text">
									<img src="images/wph_090.gif" />
								</div></li>
						</ul>

					</div>
					<div class="yb_bg3 datattop5_tt4">
						<li><img src="images/dot_01.jpg" />周一</li>
						<li><img src="images/dot_06.jpg" />周二</li>
						<li><img src="images/dot_04.jpg" />周三</li>
						<li><img src="images/dot_02.gif" />周四</li>
						<li><img src="images/dot_05.jpg" />周五</li>

					</div>




					<div class="yb_bg">
						<div class="yb_bg_tt">家居产品交易情况（按周）</div>
						<div class="yb_bg_tt2"></div>
						<div class="yb_bg_text">&nbsp;</div>
						<div class="yb_bg_but">
							<input name="" type="button" / class="yb_bg_but2" value="收起">
						</div>
					</div>
					<div class="tabtop5_tt datattop5_tt2">
						<div class=datattop5_tt>
							<div class="index_title_pdt2">
								维科家纺本周交易金额（万元）：<span>59.38</span>
							</div>
						</div>

						<div align="center">
							<div class="datattop5_ttp">
								<img src="images/yb_bg.jpg" />
							</div>



						</div>

					</div>
					<div class="tabtop5_tt datattop5_tt2">
						<div class=datattop5_tt>
							<div class="index_title_pdt2">
								奔腾本周交易金额（万元）：<span>28.53</span>
							</div>
						</div>

						<div align="center">
							<div class="datattop5_ttp">
								<img src="images/yb_bg.jpg" />
							</div>



						</div>

					</div>
					<div class="tabtop5_tt">
						<div class=datattop5_tt>
							<div class="index_title_pdt2">
								竹印象交易金额（万元）：<span>23.63</span>
							</div>
						</div>

						<div align="center">
							<div class="datattop5_ttp">
								<img src="images/yb_bg.jpg" />
							</div>



						</div>

					</div>
				</div>
				<div id="cont2" style="display: none;">
					<div class="yb_bg">
						<div class="yb_bg_tt">男士产品交易情况（按周）</div>
						<div class="yb_bg_tt2"></div>
						<div class="yb_bg_text">&nbsp;</div>
						<div class="yb_bg_but">
							<input name="" type="button" / class="yb_bg_but2" value="收起">
						</div>
					</div>

					<div class="tabtop5 datattop5_tt3 datattop5_tt4">
						<div class=datattop56>
							<div class="index_title_pdt2">
								阿迪达斯本周交易金额（万元）：<span>105.68</span>
							</div>
						</div>

						<div class="lwt_tablist33">
							<img src="images/lw_bg.gif" />
						</div>
					</div>

					<div class="tabtop5 datattop5_tt4">
						<div class=datattop56>
							<div class="index_title_pdt2">
								马基堡本周交易金额完成情况（万元）：<span>2536.32</span>
							</div>
						</div>
						<div class="lwt_tablist33">
							<img src="images/wph_04.gif" />
						</div>
					</div>
					<div class="yb_bg">
						<div class="yb_bg_tt">女士产品产品交易情况（按周）</div>
						<div class="yb_bg_tt2"></div>
						<div class="yb_bg_text">&nbsp;</div>
						<div class="yb_bg_but">
							<input name="" type="button" / class="yb_bg_but2" value="收起">
						</div>
					</div>

					<div class="tabtop5 datattop5_tt3 datattop5_tt4">
						<div class=datattop56>
							<div class="index_title_pdt2">
								东之澜本周交易金额（万元）：<span>27.30</span>
							</div>
						</div>

						<div class="lwt_tablist33">
							<img src="images/wph_05.gif" />
						</div>
					</div>
					<div class="tabtop5 datattop5_tt4">
						<div class=datattop56>
							<div class="index_title_pdt2">
								美莎帕本周交易金额完成情况（万元）：<span>538.67</span>
							</div>
						</div>
						<div class="lwt_tablist33">
							<img src="images/wph_06.gif" />
						</div>
					</div>
					<div class="yb_bg">
						<div class="yb_bg_tt">儿童产品交易情况（按周）</div>
						<div class="yb_bg_tt2"></div>
						<div class="yb_bg_text">&nbsp;</div>
						<div class="yb_bg_but">
							<input name="" type="button" / class="yb_bg_but2" value="收起">
						</div>
					</div>
					<div class="yb_bg2">
						<ul>
							<li><div class="yb_bg2_tt">
									<div class="index_title_pdt2">
										爱法贝本周交易金额（万元）：<span>19.38</span>
									</div>
								</div>
								<div class="yb_bg2_text">
									<img src="images/wph_07.gif" />
								</div></li>
							<li><div class="yb_bg2_tt">
									<div class="index_title_pdt2">
										贝贝依依本周交易金额（万元）：<span>29.38</span>
									</div>
								</div>
								<div class="yb_bg2_text">
									<img src="images/wph_08.gif" />
								</div></li>
							<li><div class="yb_bg2_tt">
									<div class="index_title_pdt2">
										奥克汤姆本周交易金额（万元）：<span>32.38</span>
									</div>
								</div>
								<div class="yb_bg2_text">
									<img src="images/wph_09.gif" />
								</div></li>
							<li><div class="yb_bg2_tt">
									<div class="index_title_pdt2">
										柏迪小熊本周交易金额（万元）：<span>12.38</span>
									</div>
								</div>
								<div class="yb_bg2_text">
									<img src="images/wph_090.gif" />
								</div></li>
						</ul>

					</div>
					<div class="yb_bg3 datattop5_tt4">
						<li><img src="images/dot_01.jpg" />周一</li>
						<li><img src="images/dot_06.jpg" />周二</li>
						<li><img src="images/dot_04.jpg" />周三</li>
						<li><img src="images/dot_02.gif" />周四</li>
						<li><img src="images/dot_05.jpg" />周五</li>

					</div>




					<div class="yb_bg">
						<div class="yb_bg_tt">家居产品交易情况（按周）</div>
						<div class="yb_bg_tt2"></div>
						<div class="yb_bg_text">&nbsp;</div>
						<div class="yb_bg_but">
							<input name="" type="button" / class="yb_bg_but2" value="收起">
						</div>
					</div>
					<div class="tabtop5_tt datattop5_tt2">
						<div class=datattop5_tt>
							<div class="index_title_pdt2">
								维科家纺本周交易金额（万元）：<span>59.38</span>
							</div>
						</div>

						<div align="center">
							<div class="datattop5_ttp">
								<img src="images/yb_bg.jpg" />
							</div>



						</div>

					</div>
					<div class="tabtop5_tt datattop5_tt2">
						<div class=datattop5_tt>
							<div class="index_title_pdt2">
								奔腾本周交易金额（万元）：<span>28.53</span>
							</div>
						</div>

						<div align="center">
							<div class="datattop5_ttp">
								<img src="images/yb_bg.jpg" />
							</div>



						</div>

					</div>
					<div class="tabtop5_tt">
						<div class=datattop5_tt>
							<div class="index_title_pdt2">
								竹印象交易金额（万元）：<span>23.63</span>
							</div>
						</div>

						<div align="center">
							<div class="datattop5_ttp">
								<img src="images/yb_bg.jpg" />
							</div>



						</div>

					</div>
				</div>
				<div id="cont3" style="display: none;">
					<div class="yb_bg">
						<div class="yb_bg_tt">男士产品交易情况（按周）</div>
						<div class="yb_bg_tt2"></div>
						<div class="yb_bg_text">&nbsp;</div>
						<div class="yb_bg_but">
							<input name="" type="button" / class="yb_bg_but2" value="收起">
						</div>
					</div>

					<div class="tabtop5 datattop5_tt3 datattop5_tt4">
						<div class=datattop56>
							<div class="index_title_pdt2">
								阿迪达斯本周交易金额（万元）：<span>105.68</span>
							</div>
						</div>

						<div class="lwt_tablist33">
							<img src="images/lw_bg.gif" />
						</div>
					</div>

					<div class="tabtop5 datattop5_tt4">
						<div class=datattop56>
							<div class="index_title_pdt2">
								马基堡本周交易金额完成情况（万元）：<span>2536.32</span>
							</div>
						</div>
						<div class="lwt_tablist33">
							<img src="images/wph_04.gif" />
						</div>
					</div>
					<div class="yb_bg">
						<div class="yb_bg_tt">女士产品产品交易情况（按周）</div>
						<div class="yb_bg_tt2"></div>
						<div class="yb_bg_text">&nbsp;</div>
						<div class="yb_bg_but">
							<input name="" type="button" / class="yb_bg_but2" value="收起">
						</div>
					</div>

					<div class="tabtop5 datattop5_tt3 datattop5_tt4">
						<div class=datattop56>
							<div class="index_title_pdt2">
								东之澜本周交易金额（万元）：<span>27.30</span>
							</div>
						</div>

						<div class="lwt_tablist33">
							<img src="images/wph_05.gif" />
						</div>
					</div>
					<div class="tabtop5 datattop5_tt4">
						<div class=datattop56>
							<div class="index_title_pdt2">
								美莎帕本周交易金额完成情况（万元）：<span>538.67</span>
							</div>
						</div>
						<div class="lwt_tablist33">
							<img src="images/wph_06.gif" />
						</div>
					</div>
					<div class="yb_bg">
						<div class="yb_bg_tt">儿童产品交易情况（按周）</div>
						<div class="yb_bg_tt2"></div>
						<div class="yb_bg_text">&nbsp;</div>
						<div class="yb_bg_but">
							<input name="" type="button" / class="yb_bg_but2" value="收起">
						</div>
					</div>
					<div class="yb_bg2">
						<ul>
							<li><div class="yb_bg2_tt">
									<div class="index_title_pdt2">
										爱法贝本周交易金额（万元）：<span>19.38</span>
									</div>
								</div>
								<div class="yb_bg2_text">
									<img src="images/wph_07.gif" />
								</div></li>
							<li><div class="yb_bg2_tt">
									<div class="index_title_pdt2">
										贝贝依依本周交易金额（万元）：<span>29.38</span>
									</div>
								</div>
								<div class="yb_bg2_text">
									<img src="images/wph_08.gif" />
								</div></li>
							<li><div class="yb_bg2_tt">
									<div class="index_title_pdt2">
										奥克汤姆本周交易金额（万元）：<span>32.38</span>
									</div>
								</div>
								<div class="yb_bg2_text">
									<img src="images/wph_09.gif" />
								</div></li>
							<li><div class="yb_bg2_tt">
									<div class="index_title_pdt2">
										柏迪小熊本周交易金额（万元）：<span>12.38</span>
									</div>
								</div>
								<div class="yb_bg2_text">
									<img src="images/wph_090.gif" />
								</div></li>
						</ul>

					</div>
					<div class="yb_bg3 datattop5_tt4">
						<li><img src="images/dot_01.jpg" />周一</li>
						<li><img src="images/dot_06.jpg" />周二</li>
						<li><img src="images/dot_04.jpg" />周三</li>
						<li><img src="images/dot_02.gif" />周四</li>
						<li><img src="images/dot_05.jpg" />周五</li>

					</div>




					<div class="yb_bg">
						<div class="yb_bg_tt">家居产品交易情况（按周）</div>
						<div class="yb_bg_tt2"></div>
						<div class="yb_bg_text">&nbsp;</div>
						<div class="yb_bg_but">
							<input name="" type="button" / class="yb_bg_but2" value="收起">
						</div>
					</div>
					<div class="tabtop5_tt datattop5_tt2">
						<div class=datattop5_tt>
							<div class="index_title_pdt2">
								维科家纺本周交易金额（万元）：<span>59.38</span>
							</div>
						</div>

						<div align="center">
							<div class="datattop5_ttp">
								<img src="images/yb_bg.jpg" />
							</div>



						</div>

					</div>
					<div class="tabtop5_tt datattop5_tt2">
						<div class=datattop5_tt>
							<div class="index_title_pdt2">
								奔腾本周交易金额（万元）：<span>28.53</span>
							</div>
						</div>

						<div align="center">
							<div class="datattop5_ttp">
								<img src="images/yb_bg.jpg" />
							</div>



						</div>

					</div>
					<div class="tabtop5_tt">
						<div class=datattop5_tt>
							<div class="index_title_pdt2">
								竹印象交易金额（万元）：<span>23.63</span>
							</div>
						</div>

						<div align="center">
							<div class="datattop5_ttp">
								<img src="images/yb_bg.jpg" />
							</div>



						</div>

					</div>
				</div>
			</div>
		</div>
		<div class="clear"></div>
		<!--金融看板结束-->
		<div id="test1_3" class="lwttablist">

			<div id="tap">
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					class="lwttablist3">
					<tr>
						<td width="85%"></td>
						<td width="15%">
							<ul class="lwt_mouseover">
								<li><a href="javascript:void(0)" class="on" id="tap1"
									onclick="showTap('1')">
										<div class="report_list_ftpd">日</div>
								</a></li>
								<li><a href="javascript:void(0)" id="tap2"
									onclick="showTap('2')">
										<div class="report_list_ftpd">周</div>
								</a></li>
								<li><a href="javascript:void(0)" id="tap3"
									onclick="showTap('3')">
										<div class="report_list_ftpd">月</div>
								</a></li>
							</ul>
						</td>


					</tr>
				</table>
			</div>
			<div id="conb">
				<div id="conb1" style="display: block">
					<div class="yb_bg4">
						<div class="yb_bg">
							<div class="yb_bg_tt">注册账户分析</div>
							<div class="yb_bg_tt2"></div>
							<div class="yb_bg_text">历史累计有效账户数：41737.19万</div>
						</div>

						<div class="tabtop5_tt datattop5_tt2">
							<div class=datattop5_tt>
								<div class="index_title_pdt2">新增有效注册账户数（万人）</div>
							</div>
							<div align="center">
								<div class="datattop5_ttp">
									<!-- 		<img src="images/lwt_03.gif" /> -->
									<div id="chartdiv1"></div>
								</div>



							</div>

						</div>
						<div class="tabtop5_tt">
							<div class=datattop5_tt>
								<div class="index_title_pdt2">新增有效注册账户来源分析（万人）</div>
							</div>
							<div align="center">
								<div class="datattop5_ttp">
									<div id="chartAreaDiv1"></div>
								</div>



							</div>

						</div>
					</div>
					<div class="yb_bg5">
						<div class="yb_bg">
							<div class="yb_bg_tt">登录账户分析</div>
							<div class="yb_bg_tt2"></div>
						</div>

						<div class=tabtop5_tt>

							<div class=datattop5_tt>
								<div class="index_title_pdt2">登录账户分析（万人）</div>
							</div>

							<div class="datattop5_ttp" id="show_column_div">
								
							</div>

						</div>
					</div>
					<div class="yb_bg">
						<div class="yb_bg_tt">账户活跃情况分析</div>
						<div class="yb_bg_tt2"></div>
						<div class="yb_bg_text">&nbsp;</div>

					</div>
					<div class="tabtop5_tt datattop5_tt2">
						<div class=datattop5_tt>
							<div class="index_title_pdt2">资金变动账户数（万人）</div>
						</div>
						<div align="center">
							<div class="datattop5_ttp">
								<img src="images/lwt_04.gif" />
							</div>



						</div>

					</div>
					<div class="tabtop5_tt datattop5_tt2">
						<div class=datattop5_tt>
							<div class="index_title_pdt2">活跃账户对比动态一年活跃账户分析（万人）</div>
						</div>
						<div align="center">
							<div class="datattop5_ttp">
								<img src="images/lwt_05.gif" />
							</div>



						</div>

					</div>
					<div class="tabtop5_tt">
						<div class=datattop5_tt>
							<div class="index_title_pdt2">动态一年深度活跃占比分析（万人）</div>
						</div>
						<div align="center">
							<div class="datattop5_ttp">
								<img src="images/lwt_06.gif" />
							</div>



						</div>

					</div>
					<div class="yb_bg">
						<div class="yb_bg_tt">账户质量分析</div>
						<div class="yb_bg_tt2"></div>
						<div class="yb_bg_text">&nbsp;</div>

					</div>
					<div class="tabtop5_tt datattop5_tt2">
						<div class=datattop5_tt>
							<div class="index_title_pdt2">新增有效注册账户、新增手机绑定账户（万人）</div>
						</div>
						<div align="center">
							<div class="datattop5_ttp">
								<img src="images/wph_01.gif" />
							</div>



						</div>

					</div>
					<div class="tabtop5_tt datattop5_tt2">
						<div class=datattop5_tt>
							<div class="index_title_pdt2">新增有效注册账户、新增认证账户分析（万人）</div>
						</div>
						<div align="center">
							<div class="datattop5_ttp">
								<img src="images/wph_02.gif" />
							</div>



						</div>

					</div>
					<div class="tabtop5_tt">
						<div class=datattop5_tt>
							<div class="index_title_pdt2">新增认证账户-按认证方式分析（万人）</div>
						</div>
						<div align="center">
							<div class="datattop5_ttp">
								<img src="images/wph_03.gif" />
							</div>



						</div>

					</div>
					<div class="yb_bg">
						<div class="yb_bg_tt">网站流量分析（只提供日数据）</div>
						<div class="yb_bg_tt2"></div>
						<div class="yb_bg_text">&nbsp;</div>
					</div>
					<div class="tabtop5_tt datattop5_tt2">
						<div class=datattop5_tt>
							<div class="index_title_pdt2">唯品会全站流量分析（百万）</div>
						</div>
						<div align="center">
							<div class="datattop5_ttp">
								<img src="images/lwt_07.gif" />
							</div>



						</div>

					</div>
					<div class="tabtop5_tt datattop5_tt2">
						<div class=datattop5_tt>
							<div class="index_title_pdt2">全站PV分布情况（百万）</div>
						</div>
						<div align="center">
							<div class="datattop5_ttp">
								<img src="images/lwt_08.gif" />
							</div>



						</div>

					</div>
					<div class="tabtop5_tt">
						<div class=datattop5_tt>
							<div class="index_title_pdt2">全站UV分布情况（百万）</div>
						</div>
						<div align="center">
							<div class="datattop5_ttp">

								<img src="images/lwt_09.gif" />
							</div>



						</div>

					</div>
				</div>
				<div id="conb2" style="display: none">
					<div class="yb_bg4">
						<div class="yb_bg">
							<div class="yb_bg_tt">注册账户分析</div>
							<div class="yb_bg_tt2"></div>
							<div class="yb_bg_text">历史累计有效账户数：41737.19万</div>
						</div>

						<div class="tabtop5_tt datattop5_tt2">
							<div class=datattop5_tt>
								<div class="index_title_pdt2">新增有效注册账户数（万人）</div>
							</div>
							<div align="center">
								<div class="datattop5_ttp">
<!-- 									<img src="images/lwt_03.gif" /> -->
									<div id="chartdiv1"></div>
								</div>



							</div>

						</div>
						<div class="tabtop5_tt">
							<div class=datattop5_tt>
								<div class="index_title_pdt2">新增有效注册账户来源分析（万人）</div>
							</div>
							<div align="center">
								<div class="datattop5_ttp">
									<img src="images/lwt_02.gif" />
								</div>



							</div>

						</div>
					</div>
					<div class="yb_bg5">
						<div class="yb_bg">
							<div class="yb_bg_tt">登录账户分析</div>
							<div class="yb_bg_tt2"></div>
						</div>

						<div class=tabtop5_tt>

							<div class=datattop5_tt>
								<div class="index_title_pdt2">登录账户分析（万人）</div>
							</div>

							<div class="datattop5_ttp">
								<img src="images/lwt_01.gif" />
							</div>

						</div>
					</div>
					<div class="yb_bg">
						<div class="yb_bg_tt">账户活跃情况分析</div>
						<div class="yb_bg_tt2"></div>
						<div class="yb_bg_text">&nbsp;</div>

					</div>
					<div class="tabtop5_tt datattop5_tt2">
						<div class=datattop5_tt>
							<div class="index_title_pdt2">资金变动账户数（万人）</div>
						</div>
						<div align="center">
							<div class="datattop5_ttp">
								<img src="images/lwt_04.gif" />
							</div>



						</div>

					</div>
					<div class="tabtop5_tt datattop5_tt2">
						<div class=datattop5_tt>
							<div class="index_title_pdt2">活跃账户对比动态一年活跃账户分析（万人）</div>
						</div>
						<div align="center">
							<div class="datattop5_ttp">
								<img src="images/lwt_05.gif" />
							</div>



						</div>

					</div>
					<div class="tabtop5_tt">
						<div class=datattop5_tt>
							<div class="index_title_pdt2">动态一年深度活跃占比分析（万人）</div>
						</div>
						<div align="center">
							<div class="datattop5_ttp">
								<img src="images/lwt_06.gif" />
							</div>



						</div>

					</div>
					<div class="yb_bg">
						<div class="yb_bg_tt">账户质量分析</div>
						<div class="yb_bg_tt2"></div>
						<div class="yb_bg_text">&nbsp;</div>

					</div>
					<div class="tabtop5_tt datattop5_tt2">
						<div class=datattop5_tt>
							<div class="index_title_pdt2">新增有效注册账户、新增手机绑定账户（万人）</div>
						</div>
						<div align="center">
							<div class="datattop5_ttp">
								<img src="images/wph_01.gif" />
							</div>



						</div>

					</div>
					<div class="tabtop5_tt datattop5_tt2">
						<div class=datattop5_tt>
							<div class="index_title_pdt2">新增有效注册账户、新增认证账户分析（万人）</div>
						</div>
						<div align="center">
							<div class="datattop5_ttp">
								<img src="images/wph_02.gif" />
							</div>



						</div>

					</div>
					<div class="tabtop5_tt">
						<div class=datattop5_tt>
							<div class="index_title_pdt2">新增认证账户-按认证方式分析（万人）</div>
						</div>
						<div align="center">
							<div class="datattop5_ttp">
								<img src="images/wph_03.gif" />
							</div>



						</div>

					</div>
					<div class="yb_bg">
						<div class="yb_bg_tt">网站流量分析（只提供日数据）</div>
						<div class="yb_bg_tt2"></div>
						<div class="yb_bg_text">&nbsp;</div>
					</div>
					<div class="tabtop5_tt datattop5_tt2">
						<div class=datattop5_tt>
							<div class="index_title_pdt2">唯品会全站流量分析（百万）</div>
						</div>
						<div align="center">
							<div class="datattop5_ttp">
								<img src="images/lwt_07.gif" />
							</div>



						</div>

					</div>
					<div class="tabtop5_tt datattop5_tt2">
						<div class=datattop5_tt>
							<div class="index_title_pdt2">全站PV分布情况（百万）</div>
						</div>
						<div align="center">
							<div class="datattop5_ttp">
								<img src="images/lwt_08.gif" />
							</div>



						</div>

					</div>
					<div class="tabtop5_tt">
						<div class=datattop5_tt>
							<div class="index_title_pdt2">全站UV分布情况（百万）</div>
						</div>
						<div align="center">
							<div class="datattop5_ttp">

								<img src="images/lwt_09.gif" />
							</div>



						</div>

					</div>
				</div>
				<div id="conb3" style="display: none">
					<div class="yb_bg4">
						<div class="yb_bg">
							<div class="yb_bg_tt">注册账户分析</div>
							<div class="yb_bg_tt2"></div>
							<div class="yb_bg_text">历史累计有效账户数：41737.19万</div>
						</div>

						<div class="tabtop5_tt datattop5_tt2">
							<div class=datattop5_tt>
								<div class="index_title_pdt2">新增有效注册账户数（万人）</div>
							</div>
							<div align="center">
								<div class="datattop5_ttp">
									<img src="images/lwt_03.gif" />
								</div>



							</div>

						</div>
						<div class="tabtop5_tt">
							<div class=datattop5_tt>
								<div class="index_title_pdt2">新增有效注册账户来源分析（万人）</div>
							</div>
							<div align="center">
								<div class="datattop5_ttp">
									<img src="images/lwt_02.gif" />
								</div>



							</div>

						</div>
					</div>
					<div class="yb_bg5">
						<div class="yb_bg">
							<div class="yb_bg_tt">登录账户分析</div>
							<div class="yb_bg_tt2"></div>
						</div>

						<div class=tabtop5_tt>

							<div class=datattop5_tt>
								<div class="index_title_pdt2">登录账户分析（万人）</div>
							</div>

							<div class="datattop5_ttp">
								<img src="images/lwt_01.gif" />
							</div>

						</div>
					</div>
					<div class="yb_bg">
						<div class="yb_bg_tt">账户活跃情况分析</div>
						<div class="yb_bg_tt2"></div>
						<div class="yb_bg_text">&nbsp;</div>

					</div>
					<div class="tabtop5_tt datattop5_tt2">
						<div class=datattop5_tt>
							<div class="index_title_pdt2">资金变动账户数（万人）</div>
						</div>
						<div align="center">
							<div class="datattop5_ttp">
								<img src="images/lwt_04.gif" />
							</div>



						</div>

					</div>
					<div class="tabtop5_tt datattop5_tt2">
						<div class=datattop5_tt>
							<div class="index_title_pdt2">活跃账户对比动态一年活跃账户分析（万人）</div>
						</div>
						<div align="center">
							<div class="datattop5_ttp">
								<img src="images/lwt_05.gif" />
							</div>



						</div>

					</div>
					<div class="tabtop5_tt">
						<div class=datattop5_tt>
							<div class="index_title_pdt2">动态一年深度活跃占比分析（万人）</div>
						</div>
						<div align="center">
							<div class="datattop5_ttp">
								<img src="images/lwt_06.gif" />
							</div>



						</div>

					</div>
					<div class="yb_bg">
						<div class="yb_bg_tt">账户质量分析</div>
						<div class="yb_bg_tt2"></div>
						<div class="yb_bg_text">&nbsp;</div>

					</div>
					<div class="tabtop5_tt datattop5_tt2">
						<div class=datattop5_tt>
							<div class="index_title_pdt2">新增有效注册账户、新增手机绑定账户（万人）</div>
						</div>
						<div align="center">
							<div class="datattop5_ttp">
								<img src="images/wph_01.gif" />
							</div>



						</div>

					</div>
					<div class="tabtop5_tt datattop5_tt2">
						<div class=datattop5_tt>
							<div class="index_title_pdt2">新增有效注册账户、新增认证账户分析（万人）</div>
						</div>
						<div align="center">
							<div class="datattop5_ttp">
								<img src="images/wph_02.gif" />
							</div>



						</div>

					</div>
					<div class="tabtop5_tt">
						<div class=datattop5_tt>
							<div class="index_title_pdt2">新增认证账户-按认证方式分析（万人）</div>
						</div>
						<div align="center">
							<div class="datattop5_ttp">
								<img src="images/wph_03.gif" />
							</div>



						</div>

					</div>
					<div class="yb_bg">
						<div class="yb_bg_tt">网站流量分析（只提供日数据）</div>
						<div class="yb_bg_tt2"></div>
						<div class="yb_bg_text">&nbsp;</div>
					</div>
					<div class="tabtop5_tt datattop5_tt2">
						<div class=datattop5_tt>
							<div class="index_title_pdt2">唯品会全站流量分析（百万）</div>
						</div>
						<div align="center">
							<div class="datattop5_ttp">
								<img src="images/lwt_07.gif" />
							</div>



						</div>

					</div>
					<div class="tabtop5_tt datattop5_tt2">
						<div class=datattop5_tt>
							<div class="index_title_pdt2">全站PV分布情况（百万）</div>
						</div>
						<div align="center">
							<div class="datattop5_ttp">
								<img src="images/lwt_08.gif" />
							</div>



						</div>

					</div>
					<div class="tabtop5_tt">
						<div class=datattop5_tt>
							<div class="index_title_pdt2">全站UV分布情况（百万）</div>
						</div>
						<div align="center">
							<div class="datattop5_ttp">

								<img src="images/lwt_09.gif" />
							</div>



						</div>

					</div>
				</div>
			</div>
		</div>

		<!--切换结束-->
	</div>
	</div>

	<script type="text/javascript">
	var amchart_key ="110-037eaff57f02320aee1b8576e4f4062a"; //"110-037eaff57f02320aee1b8576e4f4062a";"${amchartKey}"
		window.onload = function() {
			new tab('test1_li_now_', '_', null, 'onmouseover');
			new tab('test2_li_now_');
			new tab('test3_li_now_', '_', function() {
				alert('您现在单的是第:' + this['Index'] + '个!');
			});
			new tab('test4-input-input_tab1-input_tab2', '-');
			//高度自适应
			parent.changeIframeSize(document.documentElement.scrollHeight);
			//用户看板图形显示 _注册账户分析趋势图
			var kpiCode_1="APY10100007;APY10220107";
			var staCode_1 = "2001";
			var kpiType_1 = "1002";
			var quaryDate = "2010-11-11";
			var so4 = new SWFObject("<%=path%>/common/amchart/stock/amstock/amstock.swf", "amline1", "320", "245", "9", "#EFF0F2");
			 so4.addVariable("path", "<%=path%>/common/amchart/stock/amstock/");
			 so4.addVariable("chart_id", "amline1");
			 so4.addParam("wmode","transparent");
			 var url1="<%=path%>/misStockChart/showStockChart?1=1&kpiCodes="+kpiCode_1+";&needPercent=0&kpiType="+kpiType_1+"&staCode="+staCode_1+"&quaryDate="+quaryDate+"&rid="+Math.random();
			 so4.addVariable("settings_file", escape(url1));
			 so4.addVariable("key", amchart_key);
			 so4.write("chartdiv1");
			 //矩形折现图
				var column0 = new SWFObject("<%=basePath%>/common/amchart/column/amcolumn/amcolumn.swf", "column0", "322", "249", "8", "#EFF0F2");
				//healthtre.addParam("wmode", "opaque");
				column0.addVariable("path", "<%=basePath%>	/common/js");
				column0.addVariable("chart_id", "column0");
				var url="<%=basePath%>/DwmisColumnOrLineChart/showChartSetting.html?1=1&kpiCodes=APY10100007;APY10110107;APY10210307;&needPercent=0&dateType=1002&reportDate=20120210&chartTypes=column;column;line&lookOut=y&dateType=2001";
				column0.addVariable("settings_file", escape(url));//设置文件
				var dataUrl="<%=basePath%>/DwmisColumnOrLineChart/showChartData.html?1=1&kpiCodes=APY10100007;APY10110107;APY10210307;&needPercent=0&dateType=1002&reportDate=20120210&chartTypes=column;column;line&lookOut=y&dateType=2001";
				column0.addVariable("data_file", escape(dataUrl));//数据文件
				column0.addVariable("key", amchart_key);
				column0.addParam("wmode", "transparent");
				column0.write("show_column_div");
			 
			//面积图
				var stock=new SWFObject("<%=path%>/common/amchart/stock/amstock/amstock.swf?id=<%=new Date().getTime()%>", "stock", "320", "245", "8", "#EFF0F2");
					stock.addParam("wmode", "transparent");
					stock.addVariable("path", "<%=path%>/common/amchart/stock/amstock/");
					stock.addVariable("chart_id", "stock");
					var url="<%=path%>/misStockChart/showStockAreaChart?1=1&kpiCodes="+kpiCode_1+";&needPercent=0&kpiType="+kpiType_1+"&staCode="+staCode_1+"&quaryDate="+quaryDate+"&rid="+Math.random();
					stock.addVariable("settings_file", escape(url));
					stock.addVariable("key", amchart_key);
					stock.write("chartAreaDiv1");
					
			 //折线图
			var line1 = new SWFObject("<%=path%>/common/amchart/bundle/amline/amline.swf", "amline", "485", "291", "8", "#FFFFFF");
			line1.addParam("wmode", "opaque");
			line1.addVariable("path", "<%=path%>/amchart/bundle/amline/");
			line1.addVariable("chart_id", "amline");
			line1.addVariable("key", amchart_key);
			line1.addVariable("settings_file", escape("<%=path%>/DwmisLineGraph/ShowLineGraphSetting.htm?kpiCodes=CUS20010301;CUS20010401;CUS20010201&queryDate=20120112"));
			line1.addVariable("data_file", escape("<%=path%>/DwmisLineGraph/ShowLineGraph.htm?kpiCodes=CUS20010301;CUS20010401;CUS20010201&queryDate=20120112&dateType=1002&staCode=2001"));
			line1.write("line1");
					
			//饼图
			var pie1 = new SWFObject("<%=path%>/common/amchart/pie/ampie/ampie1.swf", "ampie1", "255", "177", "8.0.0", "#FFFFFF");
			pie1.addVariable("path", "<%=path%>/common/amchart/pie/ampie/");
			pie1.addVariable("settings_file", escape("<%=path%>/vm/dwmis/dwmis_pie-settings.xml"));
			pie1.addVariable("data_file",escape("<%=path%>/DwmisPieChart/PieChartByOneKpiCode.htm?kpiCode=FIN20200001;FIN20300001;FIN20400001;FIN20500001&reportDate=20111225&dateType=1003&staCode=2002"));
			pie1.addParam("wmode", "transparent");
			pie1.addVariable("key", amchart_key);
			pie1.write("pie1");					
		}
	</script>
	<div>
		<!--foot-->
		<div class="index_foot">
			<div align="center" style="padding: 20px 0px 10px 0px">
				版权所有广州唯品会信息科技有限公司 ICP证：<a target="_blank"
					href="http://www.500wan.com/pages/info/about/yueicp.shtml"
					rel="nofollow">粤ICP备08114786号-1</a> |使用本网站即表示接受唯品会用户协议。
			</div>
		</div>
		<!--foot结束-->
	</div>
	</div>
</body>
</html>
