<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<title>数据管理平台</title>
<link type="text/css" rel="stylesheet" href="<%=path %>/css/main.css"/>
<link href="<%=basePath%>css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=path %>/js/jquery-1.5.1.min.js"></script>
</head>
<body style="background-color: #f7f7f7;" >
<form  target="result"  name="commonForm" id="commonForm">
 <div class="wrap22" style="width: 380px; height: 360px; overflow: hidden;border:0px solid red;" >
	<div class="common-box" style="width: 360px;height: 350px; overflow: hidden;border:0px solid blue;">
       <div class="left-box"  >
       <p style="margin-left:-52px;width: 350px; "  >
		  <input type="text" id="J-searchval-f" style=" width:180px; height:20px; "  onkeyup="javascript:butOnClick();"/>
          &nbsp;&nbsp;<input type="button" id="J-search-f" value="搜索" style="  width:60px; height:24px;" />
                	
      </p>

        
        
       <ul class="zb-ul" id="J-searchul-f" style="height:300px; width: 350px; margin-left: 5px; ">
                
                	<c:forEach items="${kpiinfoList}" var="common">
						<li><a style="width:500px;text-align: left; "  ondblclick="check(event.type,'${common.kpiCode}','${common.kpiName }');" onclick="check(event.type,'${common.kpiCode}','${common.kpiName }');"   href="javascript:void(0);" >
						<font id="${common.kpiCode }">${common.kpiCode} - ${common.kpiName}</font></a>
						</li>
					</c:forEach>
				
				
		
       </ul>
   
    </div>
  </div>
</div>
<input id="kpiCode" type="hidden" value="" />
<input id="kpiName" type="hidden" value="" />
</form>
		<script type="text/javascript">
		
		var dg;
		$(document).ready(function(){
			$(".main_info:even").addClass("main_table_even");
			dg = frameElement.lhgDG;
			dg.addBtn('ok','确定',function(){
				var sign = '${sign}';
				var kpiNameId ='${kpiName}';

				var kpiCode=$("#kpiCode").val();
				var kpiName=$("#kpiName").val();
				
			    //var content = dg.curWin.document.getElementById(sign).value;
			    //if(content!=''){
			    //	array.push(content);
			    //}
			    dg.curWin.document.getElementById(sign).value=kpiCode;
				dg.curWin.document.getElementById(kpiNameId).innerHTML=kpiName;
			    dg.cancel();
			});
		});
		function check(type,kpiCode,kpiName){
			
			switch (type) {  
	        case "click" : 
	        	$("#kpiCode").val(kpiCode);
				$("#kpiName").val(kpiName);
	        	$("#J-searchul-f li a font").removeClass("active");
				$("#"+kpiCode).addClass("active");
	            break;  
	        case "dblclick" : 
	        	var sign = '${sign}';
				var kpiNameId ='${kpiName}';
				dg.curWin.document.getElementById(sign).value=kpiCode;
			    //dg.curWin.document.getElementById(kpiNameId).setAttribute('value',kpiName);
				dg.curWin.document.getElementById(kpiNameId).innerHTML=kpiName;
				dg.cancel();
	            break;  
	        default: 		
		}
		}
		function butOnClick() { 
			
			document.getElementById("J-search-f").click(); 
	        }
		
            $("#J-search-f").click(function () {
            	
            	var val = jQuery.trim($("#J-searchval-f").val());
                
                val = val.toUpperCase();
                var lilist = $("#J-searchul-f>li");
                if (val) {
                    for (var i = 0; i < lilist.length; i++) {
                        if ($(lilist[i]).text().indexOf(val) >= 0) {
                            $(lilist[i]).show();
                        } else {
                        	$(lilist[i]).find("a").removeClass("active");
                            $(lilist[i]).hide();
                        }
                    }
                } else {
                    for (var i = 0; i < lilist.length; i++) {
                        $(lilist[i]).show();
                    }
                }
            });
		
</script>
</body>
</html>