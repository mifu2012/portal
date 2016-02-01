<%@ page language="java" pageEncoding="UTF-8"%>
  //这个不能删除,用于正加载中
 if(window.parent!=null&&window.parent.document.getElementById("loading_wait_div")!=null)
 {
    //如果父级提示消息框存在，则显示
    window.parent.document.getElementById("loading_wait_div").style.display="";
 }else
 {
    document.write("<div id='loading_wait_Div' name='loading_wait_div' style='border:0px solid red;position: absolute; left: 45%; top: 40%;display:;' ><img src='<%=request.getContextPath()%>/common/images/loading.gif' title='数据正在加载中...'></div>");
	window.setTimeout(function(){
	  document.getElementById('loading_wait_Div').style.display="none";
	},3000);
 }
 //公用
 document.onreadystatechange  =function()
 {
	 window.status=document.readyState;
	//加载页面提示
    if(document.readyState=="complete")
    {
       var loadingDiv=document.getElementById("loading_wait_Div");
	   if(loadingDiv!=null) loadingDiv.style.display="none";
       if(window.parent!=null&&window.parent.document.getElementById("loading_wait_div")!=null)
       {
           //如果父级提示消息框存在，则不显示
           window.parent.document.getElementById("loading_wait_div").style.display="none";
       }   
    }
 }