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
<script language='javascript'>
  function activeThis(crtObj,kpiCode)
  {
	  if(crtObj.checked)
	  {
		  document.getElementById("label_"+kpiCode).style.background="#0099ff";
	  }else
	  {
		  document.getElementById("label_"+kpiCode).style.background="#f7f7f7";
	  }
  }
</script>
</head>
<body style="background-color: #f7f7f7;" >
<form  target="result"  name="commonForm" id="commonForm">
 <div class="wrap22" style="width: 560px; height: 410px; overflow: hidden;" >
	<div class="common-box" style="width: 550px;height: 400px; overflow: hidden;">
       <div class="left-box"  >
       <p style="margin-left: -15px;width: 400px; "  >
		  <input type="text" id="J-searchval-f" style=" width:180px; height:20px; "  onkeyup="javascript:butOnClick();"/>
          &nbsp;&nbsp;<input type="button" id="J-search-f" value="搜索" style="  width:60px; height:24px;" />
                	
      </p>

        
        
       <ul class="zb-ul" id="J-searchul-f" style="height:350px; width: 500px; margin-left: 10px; " >
                
                	<c:forEach items="${kpiinfoList}" var="common">
						<li  style="width:1000px;text-align: left; " id="li_${common.kpiCode}">
						  &nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id='chk_${common.kpiCode}' value='${common.kpiCode}|${common.kpiName}' onclick='activeThis(this,"${common.kpiCode}")' name="chk_kpiCode"/>&nbsp;
						  <label for='chk_${common.kpiCode}' style='cursor:hand;' id='label_${common.kpiCode}'>${common.kpiCode} - ${common.kpiName}</label>
						</li>
					</c:forEach>
				
				
		
       </ul>
   
    </div>
  </div>
</div>
<input id="kpiCode" type="hidden" value="" />
</form>
		<script type="text/javascript">
		var dg;
		$(document).ready(function(){
			$(".main_info:even").addClass("main_table_even");
			dg = frameElement.lhgDG;
			dg.addBtn('ok','确定',function(){
				var sign = '${sign}';
				//var kpiCode=$("#kpiCode").val();
                var kpiCodeObjs=document.getElementsByName("chk_kpiCode");
                if(kpiCodeObjs==null||kpiCodeObjs.length==0)
                {
                  alert('没有指标');
                  return;
                }
                var selectedKpiCodes="";
                for(var i=0;i<kpiCodeObjs.length;i++)
                {
                   var tmpObj=kpiCodeObjs[i];
                   if(tmpObj==null||tmpObj.checked==false) continue;
                   if(selectedKpiCodes.length>0) selectedKpiCodes+=";";
                   selectedKpiCodes+=tmpObj.value;
                }
                if(selectedKpiCodes.length==0)
                {
                	   alert('请选择指标');
                	   return;
                }
                dg.curWin.inputRoleFormula(selectedKpiCodes);
			    //dg.curWin.document.getElementById(sign).setAttribute('value',selectedKpiCodes);
			    dg.cancel();
			});
		});
		function butOnClick() { 
            	
            	var val = jQuery.trim($("#J-searchval-f").val());
                
                val = val.toUpperCase();
                var lilist = $("#J-searchul-f>li");
                if (val) {
                    for (var i = 0; i < lilist.length; i++) {
                        if ($(lilist[i]).text().indexOf(val) >= 0) {
                            $(lilist[i]).show();
                        } else {
							if($(lilist[i]).find("input").attr("checked")==false)
							{
								$(lilist[i]).find("a").removeClass("active");
								$(lilist[i]).hide();
							}
                        }
                    }
                } else {
                    for (var i = 0; i < lilist.length; i++) {
                        $(lilist[i]).show();
                    }
                }
		}
    //li上移或下移
    function move(liId,moveFlag){   
        var num=$('#txt').val();   
        var pNode,cNode,nNode,index=-1;   
        $('#Thetag li').each(function (i){ 
		    //this.attr("tag",this.id);
			alert(this.id);
            if(this.id!=liId&&index==-1){   
                pNode=this;   
            }   
            else{   
                if(this.tag==num){   
                    index=i;   
                    cNode=this;   
                }   
                else{   
                    nNode=this;   
                    return false;   
                }   
            }   
        });  
		//上移
        if(moveFlag){   
            $(cNode).insertBefore($(pNode));   
        } 
		//下移
        else{   
            $(cNode).insertAfter($(nNode));   
        }   
    }  
</script>
</body>
</html>