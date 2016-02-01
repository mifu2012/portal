<%@ page contentType="text/css; charset=GBK" %>
<%
  String commonPath=request.getContextPath()+"/common";
%>
body{
  background-color:white;
  font-size:9pt;
  font-family: 宋体;
  margin:0px 0px 0px 0px;  /*top right bottom left*/
  padding:0px 0px 0px 0px;
  scrollbar-face-color: #DEE3E7;
  scrollbar-highlight-color: #FFFFFF;
  scrollbar-shadow-color: #DEE3E7;
  scrollbar-3dlight-color: #D1D7DC;
  scrollbar-arrow-color:  #006699;
  scrollbar-track-color: #EFEFEF;
  scrollbar-darkshadow-color: #98AAB1;
}
td{
  font-size:9pt;
  font-family: 宋体;
}
.subBody{
  margin:3px 5px 0px 5px;
}
.Default_menuFrame{  /*对应MenuAndButtonbar.css里的menu1_tr和menu2_tr*/
  width:100%;
  height:82px;
}
.Default_menu1Frame{  /*对应MenuAndButtonbar.css里的menu1_tr*/
  width:100%;
  height:52px;
}
.Default_menu2Frame{  /*对应MenuAndButtonbar.css里的menu2_tr*/
  width:100%;
  height:30px;
}
.Default_searchFrame{  /*对应searchTable*/
  width:100%;
  height:78px;
}
.Default_listFrame{   /*对应QueryList.css里的listUtil*/
  width:100%;
  height:355px;
}
.Default_contentTr{
  background-color:#ffffff;
}
.Default_contentTd{
  padding:6px;
  border-right:1px #ffffff solid;
  border-left:1px #ffffff solid;
}
.Default_frameTr{
  height:9px;
}
.Default_frame_topLeftTd{
  width:14px;
  background-image:url('<%=commonPath%>/Images/frame_corner_topleft.gif');
  background-repeat:no-repeat;
  background-position:left;
}
.Default_frame_topMiddleTd{
  width:97%;
  background-image:url('<%=commonPath%>/Images/frame_topbg.gif');
  background-repeat:repeat-x;
}
.Default_frame_topRightTd{
  width:14px;
  background-image:url('<%=commonPath%>/Images/frame_corner_topright.gif');
  background-repeat:no-repeat;
  background-position:right;
}
.Default_frame_bottomLeftTd{
  width:14px;
  background-image:url('<%=commonPath%>/Images/frame_corner_bottomleft.gif');
  background-repeat:no-repeat;
  background-position:left;
}
.Default_frame_bottomMiddleTd{
  width:97%;
  background-image:url('<%=commonPath%>/Images/frame_bottombg.gif');
  background-repeat:repeat-x;
}
.Default_frame_bottomRightTd{
  width:14px;
  background-image:url('<%=commonPath%>/Images/frame_corner_bottomright.gif');
  background-repeat:no-repeat;
  background-position:right;
}
.Default_frame_outBorder{
  width:100%;
  border-right:1px #d4d4d4 solid;
  border-left:1px #d4d4d4 solid;
	background-color:#ffffff;
}
.Default_frame_inBorder{
  width:100%;
  padding:0px 5px 5px 5px;
  border-right:2px #e4e2dc solid;
  border-left:2px #e4e2dc solid;
  background-color:#f4f2f2;
}
.searchUtil{
  /*height:78px;*/
  border-bottom:1px #d4d4cc solid;
  background-image:url('<%=commonPath%>/Images/search_bg.gif');
  background-repeat:no-repeat;
  background-position:left;
}
.searchTable{
  height:78px;
  border-bottom:1px #d4d4cc solid;
  background-image:url('<%=commonPath%>/Images/search_bg.gif');
  background-repeat:no-repeat;
  background-position:left;
  background-color:#f4f2f2;
}
.searchUtil_input{
  width:120px;
  height:22px;
  border:1px #859bb5 solid;
}
.searchUtil_select{
  width:120px;
  height:22px;
}
.searchUtil_btnOut{
  position:relative;
  top:0px;
  width:50px;
  border:1px #939EB2 solid;
}
.searchUtil_btnIn{
  width:48px;
  height:18px;
  padding-top:3px;
  border-right:1px #dcdcd9 solid;
  border-bottom:1px #dcdcd9 solid;
  text-align:center;
  background-color:white;
  background-image:url('<%=commonPath%>/Images/Btn_united_bg.gif');
  cursor:hand;
}
/*
.searchUtil_btnOut{
  position:relative;
  top:-1px;
  width:50px;
  height:18px;
  border:1px #949794 solid;
}
.searchUtil_btnIn{
  width:48px;
  height:16px;
  padding-top:3px;
  border-right:1px #dcdcd9 solid;
  border-bottom:1px #dcdcd9 solid;
  text-align:center;
  background-color:white;
  cursor:hand;
}
*/
/**
* showpage.jsp页面 用于减少td的显示宽度
*/
.showpage_td{
	padding-left:4px;
	padding-right:4px;
}
/**
* 导航条：用户 模块
*/
.top_navigation{
	border:#d4d4d4 1px solid;
	background-color:white;
}
/**
* EditFile.jsp
*/
.EditFile_bg{
  /*height:78px;*/
  /*border-bottom:1px #d4d4cc solid;*/
  background-image:url('<%=commonPath%>/Images/content_block_bg.gif');
  background-repeat:no-repeat;
  background-position:left top;
  border-right:1px #646c94 solid;
  border-bottom:1px #646c94 solid;
  border-left:1px #646c94 solid;
  border-top:1px #646c94 solid;
	background-color:#ffffff;
}
/**弹出界面**/
.pop_body{
      background-color:#C2CEEB;
      font-size:9pt;
      font-family: \u00CB\u00CE\u00CC\u00E5;
         margin:8px 8px 8px 8px;
      padding:0px 0px 0px 0px;
      scrollbar-face-color: #DEE3E7;
      scrollbar-highlight-color: #FFFFFF;
      scrollbar-shadow-color: #DEE3E7;
      scrollbar-3dlight-color: #D1D7DC;
      scrollbar-arrow-color:  #006699;
      scrollbar-track-color: #EFEFEF;
      scrollbar-darkshadow-color: #98AAB1;
}
.pop_btn
{
    background-color:#ffcc99;
    border:1px #939EB2 solid;
    width:55px;
    height:20px;
    padding-top:4px;
    padding-left: 0px;
    text-align:center;
	text-valign:middle;
    cursor:hand;
    font-size: 9pt;
}
.pop_edit
{
    border-top: 1px solid #ffffff;
    border-left: 1px solid #fffffff;
    border-right: 2px ridge #95A8D7;
    border-bottom: 2px ridge #95A8D7;
    background-color:#E0E7F8;
    padding-top:6px;
    padding-left:8px;
    padding-bottom:6px;
    padding-right:8px;
    vertical-align:top;
}
.pop_submit_td
{
    height:30px;
    text-align:right;
}
.pop_input
{
    border-right:1px #253E7A solid;
     border-left:1px #253E7A solid;
    border-top:1px #253E7A solid;
     border-bottom:1px #253E7A solid;
    background-color:#ffffff;
    font-size:9pt;
    height:18px;
}
.pop_file{
    height:18px;
    width:55px;
    text-align:center;
    padding-top:1px;
    border:1px solid #253E7A;
    background-color:#D4D0C8;
}
.pop_select
{
    font-size:9pt;
}
.must_input
{
    font-size:9pt;
    color:red;
}
.Default_text_input
{
	border-right:0px #B2AFAF solid;
 	border-left:0px #B2AFAF solid;
	border-top:0px #B2AFAF solid;
 	border-bottom:0px #B2AFAF solid;
	background-color:#ffffff;
}
.Default_block_input
{
	border-right:0px #B2AFAF solid;
 	border-left:0px #B2AFAF solid;
	border-top:0px #B2AFAF solid;
 	border-bottom:1px #B2AFAF solid;
	background-color:#ffffff;
        width:150px;
}
.Default_block_multiinput
{
	border-right:1px #B2AFAF solid;
 	border-left:1px #B2AFAF solid;
	border-top:1px #B2AFAF solid;
 	border-bottom:1px #B2AFAF solid;
	background-color:#ffffff;
	padding-left:1px;
}
.Default_block_title
{
	padding-top:6px;
  	padding-left:5px;
  	padding-bottom:2px;
	background-color:#F4F2F2;
	vertical-align:top;
	word-wrap:break-word;
}
.Default_block_inputbg
{
	background-color:#ffffff;
	padding-left:2px;
	padding-bottom:1px;
}
.Default_block_linecolor
{
	background-color:#666666;
	vertical-align:top;
}
.Btn_chosen_bg
{
	padding-top:4px;
  	padding-bottom:2px;
	background-color:#FCF3A4;
	text-align: center;
	border-right:1px #CDCBCB solid;
 	border-left:1px #666666 solid;
	border-top:1px #666666 solid;
 	border-bottom:1px #CDCBCB solid;
}
.Btn_normal_bg
{
	padding-top:4px;
  	padding-bottom:2px;
	text-align: center;
	border-right:1px #ffffff solid;
 	border-left:1px #ffffff solid;
	border-top:1px #ffffff solid;
 	border-bottom:1px #ffffff solid;
	cursor:hand;
}
.Btn_under_linebg
{
	background-image:url(../Images/btn_under_linebg.gif);
	background-repeat:repeat-x;
	background-position:top;
}

.box 
{ 
border: 1px solid #C0C0C0; 
width: 182px; 
height: 20px; 
clip: rect( 0px, 181px, 20px, 0px ); 
overflow: hidden; 
} 
.box2 
{ 
border: 1px solid #F4F4F4; 
width: 180px; 
height: 18px; 
clip: rect( 0px, 179px, 18px, 0px ); 
overflow: hidden; 
} 
select 
{ 
position: relative; 
left: -2px; 
top: -2px; 
width: 183px; 
line-height: 14px; color: #909993; 
border-style: none; 
border-width: 0px; 
} 
.Out_border
{
        border-top: 1px solid #ffffff;
        border-left: 1px solid #fffffff;
        border-right: 2px ridge #95A8D7;
        border-bottom: 2px ridge #95A8D7;
        background-color:#E0E7F8;
        padding-top:1px;
        padding-left:8px;
        padding-bottom:0px;
        padding-right:8px;
        vertical-align:top;
}