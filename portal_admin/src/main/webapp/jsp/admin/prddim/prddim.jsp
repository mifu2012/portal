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
<title>Example</title>
<link href="<%=basePath%>css/css.css" rel="stylesheet" type="text/css" />
    <script src="<%=basePath%>js/jquery-1.5.1.min.js" type="text/javascript"></script>
    <script type="text/javascript"
	src="<%=path%>/js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>
</head>

<body class="indexbody" >
    <div style=" position: absolute;right:10px;top:10px;">提示：<font color='red' size='2'>请先选择产品，再修改产品健康度配置</font></div>
        <div class="common-box" style="height: auto" >
            <div class="left-box">
                <p>
                    <input type="text" id="J-searchval-f" style=" width:180px; height:20px; float:left; margin-right: 10px" value="${prddim.keyWord}" onkeyup="javascript:butOnClick();"/>
                    <input type="button" id="J-search-f" value="搜索" style=" float:left; width:60px; height:24px;margin-right: 10px" />
                	<input type="button" value="保存"  style="position: absolute; margin-left: 164px; width: 60px; height: 24px; line-height: 24px;display: none; " id="J-modify"/> 
                </p>
                <p>
                    <span style=" width:160px; height:20px; float:left; display:inline-block;"><b>产品列表</b></span>
                </p>
                <ul style="border:1px solid #999; width:280px; height:300px; overflow-x:hidden;overflow-y:auto; font-size:14px;height: 400px;margin-left: 10px" id="J-searchul-f">
                	<c:forEach items="${prdList}" var="item">
						<li >
						<table border="1" width="100%">
								   <tr>
									 <td style="border-bottom:1px solid #f0f0f0;" ><a style='width:800px;' onclick="showInfo('${item.productId}','${item.productName}');" href="javascript:void(0);" >
										<font id="li_${item.productId }">${item.productId} - ${item.productName}</font></a></td>
								   </tr>
								   <tr>
									 <td height="8px"></td>
									 </tr>
						</table>
						</li>
					</c:forEach>
                </ul>
                
                
            </div>
			<div class="left-box" style="width:500px; margin-left:30px; ">
				<div style="padding-top:60px ">
				<form id="form1" target="result" method="post" action="<%=basePath%>prddim1/savePrddim1.html">
				<%-- <input type="hidden" name="productId" id="productId" value="${prdinfo.productId}"/> --%>
                <table>
                	<tr>
                		<td><span class="left-box-left">产品ID:</span></td>
                		<td><span class="left-box-right" ><input id="productId" name="productId" readonly="readonly" style="background: #eee" value="${prddim.productId}" tabindex="1" /></span> </td>
                		<td>&nbsp;</td>
                		<td><span class="left-box-left">产品名称:</span></td>
                		<td><span class="left-box-right" ><input id="productName" readonly="readonly" style="background: #eee" tabindex="2"  /></span></td>
                		<td>&nbsp;</td>
                	</tr>
                	<tr><td>&nbsp;</td>
                		<td>&nbsp;</td>
                		<td>&nbsp;</td>
                		<td>&nbsp;</td>
                		<td>&nbsp;</td>
                		<td>&nbsp;</td>
                	</tr>
                	<tr>
                		<td height="30" ><span class="left-box-left"><font color="red">*</font>维度一CODE:</span></td>
                		<td><span class="left-box-right">
                    	<input type="text" name="dim1Code" id="dim1Code" value="${prddim.dim1Code}" readonly="readonly" tabindex="3" maxlength="50"/>
                    	</span></td>
                		<td><input type="button" value="查找"  onClick="popwin('dim1Code','dim1Name');"  style=" float:left; width:50px; height:24px; margin-top:3px;"/>  </td>
                		<td><span class="left-box-left"><font color="red">*</font>维度二CODE:</span></td>
                		<td><span class="left-box-right"><input type="text" name="dim2Code" id="dim2Code" tabindex="6" readonly="readonly" value="${prddim.dim2Code}" maxlength="50"/></span>    </td>
                		<td><input type="button" value="查找" onClick="popwin('dim2Code','dim2Name');" style=" float:left; width:50px; height:24px; margin-top:3px;"/> </td>
                	</tr>
                	<tr>
                		<td><span class="left-box-left">指标名称:</span></td>
                		<td><span class="left-box-right"  >
                			<div id="dim1Name" maxlength="50"  />
                            </span> 
                       </td>
                       <td></td>
                       <td><span class="left-box-left">指标名称:</span></td>
                		<td><span class="left-box-right"  >
                			<div id="dim2Name" maxlength="50"  />
                            </span> 
                       </td>
                       <td></td>
                	</tr>
                	<tr>
                		<td height="30" ><span class="left-box-left"><font color="red">*</font>维度一内层值:</span></td>
                		<td><span class="left-box-right"><input type="text" tabindex="4" name="dim1InValue" id="dim1InValue" value="${prddim.dim1InValue}" maxlength="30" onkeyup="value=value.replace(/[^0-9^.]D*$/,'')" onkeypress="value=value.replace(/[^0-9^.]D*$/,'')" style="ime-mode:disabled"/></span>    </td>
                		<td></td>
                		<td><span class="left-box-left"><font color="red">*</font>维度二内层值:</span>
                    	</td>
                		<td><span class="left-box-right"><input type="text" name="dim2InValue" id="dim2InValue" tabindex="7" value="${prddim.dim2InValue}" maxlength="30" onkeyup="value=value.replace(/[^0-9^.]D*$/,'')" onkeypress="value=value.replace(/[^0-9^.]D*$/,'')" style="ime-mode:disabled"/></span>    </td>
                		<td></td>
                	</tr>
                	<tr>
                		<td height="30" ><span class="left-box-left"><font color="red">*</font>维度一外层值:</span>
                   		  </td>
                		<td> <span class="left-box-right"><input type="text" name="dim1OutValue" tabindex="5" id="dim1OutValue" value="${prddim.dim1OutValue}" maxlength="30" onkeyup="value=value.replace(/[^0-9^.]D*$/,'')" onkeypress="value=value.replace(/[^0-9^.]D*$/,'')" style="ime-mode:disabled"/></span>  </td>
                		<td></td>
                		<td><span class="left-box-left"><font color="red">*</font>维度二外层值:</span>
                    	 </td>
                		<td><span class="left-box-right"><input type="text" name="dim2OutValue" id="dim2OutValue" tabindex="8" value="${prddim.dim2OutValue }" maxlength="30" onkeyup="value=value.replace(/[^0-9^.]D*$/,'')" onkeypress="value=value.replace(/[^0-9^.]D*$/,'')" style="ime-mode:disabled"/></span>   </td>
                		<td></td>
                	</tr>
                	<tr style="display:;">
                		<td height="30" ><span class="left-box-left"><font color="red">*</font>维度一url链接:</span>
                   		  </td>
                		<td> <span class="left-box-right">
                		<select id="dim1Url" name="dim1Url">
                		<option value="" >请选择</option>
                		<option value="0301" >产品发展</option>
                		<option value="0303" >用户体验</option>
                		<option value="0305" >用户特征</option>
                		<option value="0302" >用户留存</option>
                		<option value="0304" >用户声音</option>
                		<option value="0306" >场景交叉</option>
                		</select>
                		</span>  </td>
                		<td></td>
                		<td><span class="left-box-left"><font color="red">*</font>维度二url链接:</span>
                    	 </td>
                		<td><span class="left-box-right">
                		<select id="dim2Url" name="dim2Url">
                		<option value="" >请选择</option>
                		<option value="0301" >产品发展</option>
                		<option value="0303" >用户体验</option>
                		<option value="0305" >用户特征</option>
                		<option value="0302" >用户留存</option>
                		<option value="0304" >用户声音</option>
                		<option value="0306" >场景交叉</option>
                		</select>
                		</span>   </td>
                		<td></td>
                	</tr>
                	<tr><td>&nbsp;</td>
                		<td>&nbsp;</td>
                		<td>&nbsp;</td>
                		<td>&nbsp;</td>
                		<td>&nbsp;</td>
                		<td>&nbsp;</td>
                	</tr>
                	<tr>
                		<td height="30" ><span class="left-box-left"><font color="red">*</font>维度三CODE:</span>
                		<td> <span class="left-box-right"><input type="text" name="dim3Code" id="dim3Code" tabindex="9" readonly="readonly" value="${prddim.dim3Code}" maxlength="50"/></span>   </td>
                		<td><input type="button" value="查找" onClick="popwin('dim3Code','dim3Name');" style=" float:left; width:50px; height:24px; margin-top:3px;"/>  </td></td>
                		<td><span class="left-box-left"><font color="red">*</font>维度四CODE:</span>
                		<td><span class="left-box-right"><input type="text" name="dim4Code" id="dim4Code" readonly="readonly" tabindex="12" value="${prddim.dim4Code}" maxlength="50"/></span> </td>
                		<td><input type="button" value="查找" onClick="popwin('dim4Code','dim4Name');" style=" float:left; width:50px; height:24px; margin-top:3px;"/>    </td></td>
                	</tr>
                	<tr>
                		<td><span class="left-box-left">指标名称:</span></td>
                		<td><span class="left-box-right" >
                			<div id="dim3Name" maxlength="50"  />
                            </span> 
                       </td>
                       <td></td>
                       <td><span class="left-box-left">指标名称:</span></td>
                		<td><span class="left-box-right"  >
                			<div id="dim4Name" maxlength="50"  />
                            </span> 
                       </td>
                       <td></td>
                	</tr>
                	<tr>
                		<td height="30" ><span class="left-box-left"><font color="red">*</font>维度三内层值:</span>
                    	 </td>
                		<td><span class="left-box-right"><input type="text" name="dim3InValue" id="dim3InValue" tabindex="10" value="${prddim.dim3InValue}" maxlength="30" onkeyup="value=value.replace(/[^0-9^.]D*$/,'')" onkeypress="value=value.replace(/[^0-9^.]D*$/,'')" style="ime-mode:disabled"/></span>   </td>
                		<td></td>
                		<td><span class="left-box-left"><font color="red">*</font>维度四内层值:</span>
                    	  </td>
                		<td><span class="left-box-right"><input type="text" name="dim4InValue" id="dim4InValue" tabindex="13" value="${prddim.dim4InValue}" maxlength="30" onkeyup="value=value.replace(/[^0-9^.]D*$/,'')" onkeypress="value=value.replace(/[^0-9^.]D*$/,'')" style="ime-mode:disabled"/></span>  </td>
                		<td></td>
                	</tr>
                	<tr>
                		<td height="30" ><span class="left-box-left"><font color="red">*</font>维度三外层值:</span>
                    	   </td>
                		<td><span class="left-box-right"><input type="text" name="dim3OutValue" id="dim3OutValue" tabindex="11" value="${prddim.dim3OutValue}" maxlength="30" onkeyup="value=value.replace(/[^0-9^.]D*$/,'')" onkeypress="value=value.replace(/[^0-9^.]D*$/,'')" style="ime-mode:disabled"/></span> </td>
                		<td></td>
                		<td><span class="left-box-left"><font color="red">*</font>维度四外层值:</span>
                    	  </td>
                		<td><span class="left-box-right"><input type="text" name="dim4OutValue" id="dim4OutValue" tabindex="14" value="${prddim.dim4OutValue}" maxlength="30" onkeyup="value=value.replace(/[^0-9^.]D*$/,'')" onkeypress="value=value.replace(/[^0-9^.]D*$/,'')" style="ime-mode:disabled"/></span>  </td>
                		<td></td>
                	</tr>
                	
                	<tr style="display:;">
                		<td height="30" ><span class="left-box-left"><font color="red">*</font>维度三url链接:</span>
                   		  </td>
                		<td> <span class="left-box-right">
                		<select id="dim3Url" name="dim3Url">
                		<option value="" >请选择</option>
                		<option value="0301" >产品发展</option>
                		<option value="0303" >用户体验</option>
                		<option value="0305" >用户特征</option>
                		<option value="0302" >用户留存</option>
                		<option value="0304" >用户声音</option>
                		<option value="0306" >场景交叉</option>
                		</select>
                		</span>  </td>
                		<td></td>
                		<td><span class="left-box-left"><font color="red">*</font>维度四url链接:</span>
                    	 </td>
                		<td><span class="left-box-right">
                		<select id="dim4Url" name="dim4Url">
                		<option value="" >请选择</option>
                		<option value="0301" >产品发展</option>
                		<option value="0303" >用户体验</option>
                		<option value="0305" >用户特征</option>
                		<option value="0302" >用户留存</option>
                		<option value="0304" >用户声音</option>
                		<option value="0306" >场景交叉</option>
                		</select>
                		</span>   </td>
                		<td></td>
                	</tr>
                	
                	<tr><td>&nbsp;</td>
                		<td>&nbsp;</td>
                		<td>&nbsp;</td>
                		<td>&nbsp;</td>
                		<td>&nbsp;</td>
                		<td>&nbsp;</td>
                	</tr>
                	<tr>
                		<td height="30" ><span class="left-box-left"><font color="red">*</font>维度五CODE:</span>
                		<td><span class="left-box-right"><input type="text" name="dim5Code" id="dim5Code" readonly="readonly" tabindex="15" value="${prddim.dim5Code}" maxlength="50"/></span> </td>
                		<td><input type="button" value="查找"  onClick="popwin('dim5Code','dim5Name');" style=" float:left; width:50px; height:24px; margin-top:3px;"/>    </td></td>
                		<td><span class="left-box-left"><font color="red">*</font>维度六CODE:</span></td>
                		<td><span class="left-box-right"><input type="text" name="dim6Code" id="dim6Code" tabindex="18" readonly="readonly" value="${prddim.dim6Code}" maxlength="50"/></span>  </td>
                		<td><input type="button" value="查找" onClick="popwin('dim6Code','dim6Name');" style=" float:left; width:50px; height:24px; margin-top:3px;"/> </td>
                	</tr>
                	<tr>
                		<td><span class="left-box-left">指标名称:</span></td>
                		<td><span class="left-box-right"  >
                			<div id="dim5Name" maxlength="50"  />
                            </span> 
                       </td>
                       <td></td>
                       <td><span class="left-box-left">指标名称:</span></td>
                		<td><span class="left-box-right"  >
                			<div id="dim6Name" maxlength="50"  />
                            </span> 
                       </td>
                       <td></td>
                	</tr>
                	<tr>
                		<td height="30" ><span class="left-box-left"><font color="red">*</font>维度五内层值:</span>
                    	 </td>
                		<td><span class="left-box-right"><input type="text" name="dim5InValue" id="dim5InValue" tabindex="16" value="${prddim.dim5InValue}" maxlength="30" onkeyup="value=value.replace(/[^0-9^.]D*$/,'')" onkeypress="value=value.replace(/[^0-9^.]D*$/,'')" style="ime-mode:disabled"/></span>   </td>
                		<td></td>
                		<td><span class="left-box-left"><font color="red">*</font>维度六内层值:</span>
                    	 </td>
                		<td><span class="left-box-right"><input type="text" name="dim6InValue" id="dim6InValue" tabindex="19" value="${prddim.dim6InValue}" maxlength="30" onkeyup="value=value.replace(/[^0-9^.]D*$/,'')" onkeypress="value=value.replace(/[^0-9^.]D*$/,'')" style="ime-mode:disabled"/></span>   </td>
                		<td></td>
                	</tr>
                	<tr>
                		<td height="30" > <span class="left-box-left"><font color="red">*</font>维度五外层值:</span>
                    	  </td>
                		<td><span class="left-box-right"><input type="text" name="dim5OutValue" id="dim5OutValue" tabindex="17" value="${prddim.dim5OutValue}" maxlength="30" onkeyup="value=value.replace(/[^0-9^.]D*$/,'')" onkeypress="value=value.replace(/[^0-9^.]D*$/,'')" style="ime-mode:disabled"/></span>  </td>
                		<td></td>
                		<td><span class="left-box-left"><font color="red">*</font>维度六外层值:</span>
                    	  </td>
                		<td><span class="left-box-right"><input type="text" name="dim6OutValue" id="dim6OutValue" tabindex="20" value="${prddim.dim6OutValue}" maxlength="30" onkeyup="value=value.replace(/[^0-9^.]D*$/,'')" onkeypress="value=value.replace(/[^0-9^.]D*$/,'')" style="ime-mode:disabled"/></span>  </td>
                		<td></td>
                	</tr>
                	
                	<tr style="display:;">
                		<td height="30" ><span class="left-box-left"><font color="red">*</font>维度五url链接:</span>
                   		  </td>
                		<td> <span class="left-box-right">
                		<select id="dim5Url" name="dim5Url">
                		<option value="" >请选择</option>
                		<option value="0301" >产品发展</option>
                		<option value="0303" >用户体验</option>
                		<option value="0305" >用户特征</option>
                		<option value="0302" >用户留存</option>
                		<option value="0304" >用户声音</option>
                		<option value="0306" >场景交叉</option>
                		</select>
                		</span>  </td>
                		<td></td>
                		<td><span class="left-box-left"><font color="red">*</font>维度六url链接:</span>
                    	 </td>
                		<td><span class="left-box-right">
                		<select id="dim6Url" name="dim6Url">
                		<option value="" >请选择</option>
                		<option value="0301" >产品发展</option>
                		<option value="0303" >用户体验</option>
                		<option value="0305" >用户特征</option>
                		<option value="0302" >用户留存</option>
                		<option value="0304" >用户声音</option>
                		<option value="0306" >场景交叉</option>
                		</select>
                		</span>   </td>
                		<td></td>
                	</tr>
                	
                	<tr><td>&nbsp;</td>
                		<td>&nbsp;</td>
                		<td>&nbsp;</td>
                		<td>&nbsp;</td>
                		<td>&nbsp;</td>
                		<td>&nbsp;</td>
                	</tr>
                </table>
				</form>
				</div>
            </div>
        </div>
        <div class="hidden">
        	<ul class="zb-ul" id="J-searchul-h" style="height:400px">
					<c:forEach items="${kpiList}" var="item">
						<li>${item.kpiCode}  - ${item.kpiName} - ${item.dispName}</li>
					</c:forEach>
           </ul>
        </div>
        <div class="fn-clear"></div>
        <div class="footer22"></div>
    <iframe name="result" id="result" src="about:blank" frameborder="0" width="0" height="0"></iframe>
    <script type="text/javascript">
    function butOnClick() { 
/*         if (event.keyCode == 13) { 
        var button = document.getElementById("J-search-f"); //bsubmit 为botton按钮的id 
        button.click(); 
        return false; 
        }  */
    	var button = document.getElementById("J-search-f"); //bsubmit 为botton按钮的id 
        button.click(); 
        }     
    $(function () {
            //第一个搜索
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
            
            var cond1 = jQuery.trim($("#J-searchval-f").val());
            if(cond1 != ""){
            	$("#J-search-f").click();
            }
            var ali = $("#J-searchul-f>li>a.active").parent("li");
            if (ali != null && ali.html() != null) {
                $("#J-searchul-f").prepend('<li>'+ali.html()+'</li>');
                ali.remove();
            }
           
			$("#J-modify").bind("click",function(){
				var productId = $("#productId").val();
	        	if(productId == ""){
	        		alert('配置产品健康度前需先在产品列表中指定相应的产品!');
	        		return false;
	        	}
				$("#keyWord").val($("#J-searchval-f").val());
				var dim1Code = jQuery.trim($("#dim1Code").val());
				var dim1InValue = jQuery.trim($("#dim1InValue").val());
				var dim1OutValue = jQuery.trim($("#dim1OutValue").val());
				var dim1Url = jQuery.trim($("#dim1Url").val());
				if(dim1Code==""){
					alert("维度一CODE不能为空!");
					return false;
				}
				if(dim1InValue==""){
					alert("维度一内层值不能为空!");
					return false;
				}
				if(dim1OutValue==""){
					alert("维度一外层值不能为空!");
					return false;
				}
				if(dim1Url==""){
					alert("维度一url链接不能为空!");
					return false;
				}
				var dim2Code = jQuery.trim($("#dim2Code").val());
				var dim2InValue = jQuery.trim($("#dim2InValue").val());
				var dim2OutValue = jQuery.trim($("#dim2OutValue").val());
				var dim2Url = jQuery.trim($("#dim2Url").val());
				if(dim2Code==""){
					alert("维度二CODE不能为空!");
					return false;
				}
				if(dim2InValue==""){
					alert("维度二内层值不能为空!");
					return false;
				}
				if(dim2OutValue==""){
					alert("维度二外层值不能为空!");
					return false;
				}
				if(dim2Url==""){
					alert("维度二url链接不能为空!");
					return false;
				}
				var dim3Code = jQuery.trim($("#dim3Code").val());
				var dim3InValue = jQuery.trim($("#dim3InValue").val());
				var dim3OutValue = jQuery.trim($("#dim3OutValue").val());
				var dim3Url = jQuery.trim($("#dim3Url").val());
				if(dim3Code==""){
					alert("维度三CODE不能为空!");
					return false;
				}
				if(dim3InValue==""){
					alert("维度三内层值不能为空!");
					return false;
				}
				if(dim3OutValue==""){
					alert("维度三外层值不能为空!");
					return false;
				}
				if(dim3Url==""){
					alert("维度三url链接不能为空!");
					return false;
				}
				var dim4Code = jQuery.trim($("#dim4Code").val());
				var dim4InValue = jQuery.trim($("#dim4InValue").val());
				var dim4OutValue = jQuery.trim($("#dim4OutValue").val());
				var dim4Url = jQuery.trim($("#dim4Url").val());
				if(dim4Code==""){
					alert("维度四CODE不能为空!");
					return false;
				}
				if(dim4InValue==""){
					alert("维度四内层值不能为空!");
					return false;
				}
				if(dim4OutValue==""){
					alert("维度四外层值不能为空!");
					return false;
				}
				if(dim4Url==""){
					alert("维度四url链接不能为空!");
					return false;
				}
				var dim5Code = jQuery.trim($("#dim5Code").val());
				var dim5InValue = jQuery.trim($("#dim5InValue").val());
				var dim5OutValue = jQuery.trim($("#dim5OutValue").val());
				var dim5Url = jQuery.trim($("#dim5Url").val());
				if(dim5Code==""){
					alert("维度五CODE不能为空!");
					return false;
				}
				if(dim5InValue==""){
					alert("维度五内层值不能为空!");
					return false;
				}
				if(dim5OutValue==""){
					alert("维度五外层值不能为空!");
					return false;
				}
				if(dim5Url==""){
					alert("维度五url链接不能为空!");
					return false;
				}
				var dim6Code = jQuery.trim($("#dim6Code").val());
				var dim6InValue = jQuery.trim($("#dim6InValue").val());
				var dim6OutValue = jQuery.trim($("#dim6OutValue").val());
				var dim6Url = jQuery.trim($("#dim6Url").val());
				if(dim6Code==""){
					alert("维度六CODE不能为空!");
					return false;
				}
				if(dim6InValue==""){
					alert("维度六内层值不能为空!");
					return false;
				}
				if(dim6OutValue==""){
					alert("维度六外层值不能为空!");
					return false;
				}
				if(dim6Url==""){
					alert("维度六url链接不能为空!");
					return false;
				}
				
				
				$("#form1").submit();
				return false;
			});

        });   
    	
    	function showInfo(val,productName){
    		document.getElementById("J-modify").style.display="";
			$.ajax({
		        type: "POST",    
		        
		        url:encodeURI("<%=basePath%>prddim1/getPrddimInfo.html?productId="
								+ val + "&random=" + Math.random()),			
						success : function(msg) {
							
							var msg = eval('(' + msg + ')');
							
							;
							if(msg==null||msg==""){
								alert("该产品Id没有六维度配置参数");
								//维度一CODE
								$("#dim1Code").val("");
								//维度一指标名称
								$("#dim1Name").val("");
								//维度一内层值
								$("#dim1InValue").val("");
								document.getElementById("dim1Name").innerHTML="";
								//维度一外层值
								$("#dim1OutValue").val("");
								//维度一链接
								$("#dim1Url").val("");
								
								//维度二CODE
								$("#dim2Code").val("");
								//维度二指标名称
								//$("#dim2Name").val(msg.dim2Name);
								document.getElementById("dim2Name").innerHTML="";
								//维度二内层值
								$("#dim2InValue").val("");
								//维度二外层值
								$("#dim2OutValue").val("");
								//维度二链接
								$("#dim2Url").val("");
								
								//维度三CODE
								$("#dim3Code").val("");
								//维度三指标名称
								//$("#dim3Name").val(msg.dim3Name);
								document.getElementById("dim3Name").innerHTML="";
								//维度三内层值
								$("#dim3InValue").val("");
								//维度三外层值
								$("#dim3OutValue").val("");
								//维度三链接
								$("#dim3Url").val("");
								
								//维度四CODE
								$("#dim4Code").val("");
								//维度四指标名称
								//$("#dim4Name").val(msg.dim4Name);
								document.getElementById("dim4Name").innerHTML="";
								//维度四内层值
								$("#dim4InValue").val("");
								//维度四外层值
								$("#dim4OutValue").val("");
								//维度四链接
								$("#dim4Url").val("");
								
								//维度五CODE
								$("#dim5Code").val("");
								//维度五指标名称
								//$("#dim5Name").val(msg.dim5Name);
								document.getElementById("dim5Name").innerHTML="";
								//维度五内层值
								$("#dim5InValue").val("");
								//维度五外层值
								$("#dim5OutValue").val("");
								//维度五链接
								$("#dim5Url").val("");
								
								//维度六CODE
								$("#dim6Code").val("");
								//维度六指标名称
								//$("#dim6Name").val(msg.dim6Name);
								document.getElementById("dim6Name").innerHTML="";
								//维度六内层值
								$("#dim6InValue").val("");
								//维度六外层值
								$("#dim6OutValue").val("");	
								//维度六链接
								$("#dim6Url").val("");
							}
							//产品ID
							$("#productId").val(val);
							//产品名称
							$("#productName").val(productName);
							//维度一CODE
							$("#dim1Code").val(msg.dim1Code);
							//维度一指标名称
							$("#dim1Name").val(msg.dim1Name);
							//维度一内层值
							$("#dim1InValue").val(msg.dim1InValue);
							document.getElementById("dim1Name").innerHTML=msg.dim1Name;
							//维度一外层值
							$("#dim1OutValue").val(msg.dim1OutValue);
							//维度一链接
							$("#dim1Url").val(msg.dim1Url);
							
							//维度二CODE
							$("#dim2Code").val(msg.dim2Code);
							//维度二指标名称
							//$("#dim2Name").val(msg.dim2Name);
							document.getElementById("dim2Name").innerHTML=msg.dim2Name;
							//维度二内层值
							$("#dim2InValue").val(msg.dim2InValue);
							//维度二外层值
							$("#dim2OutValue").val(msg.dim2OutValue);
							//维度二链接
							$("#dim2Url").val(msg.dim2Url);
							
							//维度三CODE
							$("#dim3Code").val(msg.dim3Code);
							//维度三指标名称
							//$("#dim3Name").val(msg.dim3Name);
							document.getElementById("dim3Name").innerHTML=msg.dim3Name;
							//维度三内层值
							$("#dim3InValue").val(msg.dim3InValue);
							//维度三外层值
							$("#dim3OutValue").val(msg.dim3OutValue);
							//维度三链接
							$("#dim3Url").val(msg.dim3Url);
							
							//维度四CODE
							$("#dim4Code").val(msg.dim4Code);
							//维度四指标名称
							//$("#dim4Name").val(msg.dim4Name);
							document.getElementById("dim4Name").innerHTML=msg.dim4Name;
							//维度四内层值
							$("#dim4InValue").val(msg.dim4InValue);
							//维度四外层值
							$("#dim4OutValue").val(msg.dim4OutValue);
							//维度四链接
							$("#dim4Url").val(msg.dim4Url);
							
							//维度五CODE
							$("#dim5Code").val(msg.dim5Code);
							//维度五指标名称
							//$("#dim5Name").val(msg.dim5Name);
							document.getElementById("dim5Name").innerHTML=msg.dim5Name;
							//维度五内层值
							$("#dim5InValue").val(msg.dim5InValue);
							//维度五外层值
							$("#dim5OutValue").val(msg.dim5OutValue);
							//维度五链接
							$("#dim5Url").val(msg.dim5Url);
							
							//维度六CODE
							$("#dim6Code").val(msg.dim6Code);
							//维度六指标名称
							//$("#dim6Name").val(msg.dim6Name);
							document.getElementById("dim6Name").innerHTML=msg.dim6Name;
							//维度六内层值
							$("#dim6InValue").val(msg.dim6InValue);
							//维度六外层值
							$("#dim6OutValue").val(msg.dim6OutValue);	
							//维度六链接
							$("#dim6Url").val(msg.dim6Url);
							
						}
					});
			//点击后设置样式
			//$("#J-searchul-f li a font").removeClass("active");
			//$("#"+val).addClass("active");
			var productId=document.getElementById("productId").value;
			if(document.getElementById("li_"+productId)!=null) document.getElementById("li_"+productId).style.background="#ffffff";
			document.getElementById("li_"+val).style.background="#3399ff";
		}
    	
    	
    	
function NeatDialog(sHTML, sTitle, bCancel)
{
  window.neatDialog = null;
  this.elt = null;
  if (document.createElement  &&  document.getElementById)
  {
    var dg = document.createElement("div");
    dg.className = "neat-dialog";
    if (sTitle)
      sHTML = '<div class="neat-dialog-title">'+sTitle+
              ((bCancel)?
                '<img src="x.gif" alt="Cancel" class="nd-cancel" />':'')+
                '</div>\n' + sHTML;
    dg.innerHTML = sHTML;
    var dbg = document.createElement("div");
    dbg.id = "nd-bdg";
    dbg.className = "neat-dialog-bg";
    var dgc = document.createElement("div");
    dgc.className = "neat-dialog-cont";
    dgc.appendChild(dbg);
    dgc.appendChild(dg);
    if (document.body.offsetLeft > 0)
    dgc.style.marginLeft = document.body.offsetLeft + "px";
    document.body.appendChild(dgc);
    if (bCancel) document.getElementById("nd-cancel").onclick = function()
    {
      window.neatDialog.close();
    };
    this.elt = dgc;
    window.neatDialog = this;
  }
}
NeatDialog.prototype.close = function()
{
  if (this.elt)
  {
    this.elt.style.display = "none";
    this.elt.parentNode.removeChild(this.elt);
  }
  window.neatDialog = null;
}

function openDialog(code,name){
    var obj = document.getElementById("J-searchul-h"); 
    var vArray = obj.getElementsByTagName("li");
    var sHTML = '<div><p><input type="text" id="J-searchval-k" style=" width:200px; height:20px; float:left;" value=""/><input type="button" id="J-search-k" value="搜索" onclick="serchkpi();" style=" float:right; width:60px; height:24px;" /></p></div>'
    sHTML = sHTML + '<br/><br/><div class="left-box"><ul class="zb-ul" id="J-searchul-k" style="height:400px;width:480px;"> '
    for(var i=0;i<vArray.length;i++){
    	sHTML = sHTML + '<li id="'+code+i+'" style="width:1000px;" onmousemove="this.style.cursor= \'pointer\';" onclick="showValue(\''+code+i+'\',\''+code+'\',\''+name+'\');window.neatDialog.close()" >'+vArray[i].innerHTML+'</li>';	
    }
    sHTML = sHTML + '</ul></div><p><button onclick="window.neatDialog.close()">关闭</button></p>';
    new NeatDialog(sHTML, "指标列表查询", false);
 }
 function showValue(id,code,name){
  	var idValue = document.getElementById(id);
 	var array = idValue.innerHTML.split('-');
 	document.getElementById(code).value=jQuery.trim(array[0]);
 	document.getElementById(name).innerHTML=array[1];
 }

 function serchkpi(){
 	 var val = jQuery.trim($("#J-searchval-k").val());
     var lilist = $("#J-searchul-k>li");
     if (val) {
         for (var i = 0; i < lilist.length; i++) {
             if ($(lilist[i]).text().indexOf(val) >= 0) {
                  $(lilist[i]).show();
             } else {
                  $(lilist[i]).hide();
             }
         }
     } else {
         for (var i = 0; i < lilist.length; i++) {
            $(lilist[i]).show();
         }
     }
 }
</script>

<script type="text/javascript">
var dg;
$(document).ready(function(){
	dg = frameElement.lhgDG;
	if(dg!=null)
	{
		dg.addBtn('ok','保存',function(){
			$("#form1").submit();
		});
	}
});
function success(){
	alert("保存成功");
	if(dg!=null)
	{
	    if(dg.curWin.document.forms[0]){
		    dg.curWin.document.forms[0].action = dg.curWin.location+"";
		    dg.curWin.document.forms[0].submit();
	    }else{
		    dg.curWin.location.reload();
	    }
	dg.cancel();
    }
}
function failed(){
	alert("保存失败");
	dg.cancel();
}
function popwin(sign,kpiName){
	var productId = $("#productId").val();
 	if(productId == ""){
		alert('配置产品健康度前需先在产品列表中指定相应的产品!');
		return false;
	} 
	var dg = new $.dialog({
		title:'指标搜索',
		id:'show_commoncode',
		width:420,
		height:440,
		iconTitle:false,
		cover:true,
		maxBtn:true,
		fixed:true,
		xButton:true,
		resize:true,
		page:'<%=path%>/prddim1/kpilist.html?sign='+sign+'&kpiName='+kpiName+'&productId='+productId
			});
			dg.ShowDialog();
		}
	</script>
</body>
</html>
