<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
<style type="text/css">
.active {
	background: #3399ff;
	padding-left: 15px;
	display: inline-block;
	color: #fff;
}
;
</style>
</head>
<body>
<body class="indexbody">
	<div style="position: absolute; right: 10px; top: 10px;">
		提示：<font color='red' size='2'>请先选择维度，再新增或修改一级二级维度度</font>
	</div>
	<div class="common-box">
		<div class="left-box">
			<p>
				<span
					style="width: 160px; height: 20px; float: left; margin-right: 10px; display: inline-block;"><b>维度信息</b>
				</span>
			</p>
			<p>
				<input type="text" id="J-searchval-f"
					onkeyup="javascript:selectDimension();"
					style="width: 150px; height: 20px; float: left; margin-right: 10px; line-height: 20px" />
				<input type="button" id="J-search-f" value="搜索"
					style="width: 50px; height: 24px;" /> <input type="button"
					value="添加" style="width: 50px; height: 24px;" id="add-dimension" />
			</p>

			<ul
				style="border: 1px solid #999; width: 280px; height: 300px; overflow-x: hidden; overflow-y: auto; font-size: 14px; height: 400px; margin-left: 10px"
				id="J-searchul-f">
				<c:forEach items="${DimensionList}" var="item">
					<li id="li_prdInfo_${item.id}">
						<table border="1" width="262">
							<tr id="tr_${item.id}">
								<td colspan="3"><a href='javascript:void(0);'
									id="${item.id}" onclick="showDimensionInfo(${item.id});"> <font
										id="${item.id}">${item.dimensionCode}-
											${item.dimensionName}</font> </a></td>
							</tr>
							<tr>
								<td width="70%">&nbsp;</td>
								<td align="center"><a href='javascript:void(0);'
									onclick="javascript:editDimension(${item.id});">修改</a></td>
								<td align="center"><a href='javascript:void(0);'
									onclick="javascript:deleteDimsion(${item.id})">删除</a></td>
							</tr>
						</table>
					</li>
				</c:forEach>
			</ul>
		</div>
		<div class="fn-clear">
			<input type="hidden" id="comKpiCode3" style="display: none;" />
			<div class="left-box"
				style="width: 380px; float: left; position: absolute; left: 330px; border: 0px solid;">
				<p>
					<span
						style="width: 160px; height: 20px; float: left; display: inline-block;"
						for="u1"><b>一级维度</b> </span>
				</p>
				<p>
					<input type="text" id="J-searchval-ff"
						onkeyup="javascript:selectDimensionDetail();"
						style="width: 95px; height: 20px; float: left; margin-right: 5px; line-height: 20px" />
					<input type="hidden" id="dimensionId1" name="id"
						value="${dimension.id}" style="display: none;"> <input
						type="button" id="J-search-ff" value="搜索"
						style="width: 50px; height: 24px;" /> <input type="hidden"
						id="add-dimensionDetail" style="display: none;"> <input
						type="button" value="添加" style="width: 50px; height: 24px;"
						onclick="addDimensionDetail()" title='手工输入，单个添加' /> <input
						type="button" value="批量添加" style="width: 60px; height: 24px;"
						onclick="batchAddDimensionDetail()" title='通过执行SQL语句批量添加' />
				</p>
				<ul class="left-box" id="J-searchul-ff"
					style="border: 1px solid #999; width: 280px; height: 300px; overflow-x: hidden; overflow-y: auto; font-size: 14px; height: 400px; margin-left: 0px">
					<div style='text-align: center;'>
						<font color='red' size='2'>请先选择维度，再新增或修改一级维度</font>
					</div>
				</ul>
			</div>
			<div class="left-box"
				style="width: 380px; float: left; position: absolute; left: 620px; top: 55px; border: 0px solid;">
				<p style="position:relative;left: 20px;top: 0px;float: left;">
					<input type="text" id="J-searchvalSec"
						onkeyup="javascript:selectDimensionDetailSec();"
						style="width: 95px; height: 20px; line-height: 20px" /> <input
						type="button" id="J-search-ff2" value="搜索"
						style="width: 50px; height: 24px;" /> <input type="text"
						id="add-dimensionDetailSec" value="" style="display: none" /> <input
						type="button" value="添加" style="width: 50px; height: 24px;"
						onclick="addDimensionDetailSec()" title='手工输入，单个添加' /> <input
						type="button" value="批量添加" style="width: 60px; height: 24px;"
						onclick="batchAddDimensionDetailSec()" title='通过执行SQL语句批量添加' />
				</p>
				<ul class="left-box" id="J-searchulSecUl"
					style="border: 1px solid #999; width: 280px; height: 300px; overflow-x: hidden; overflow-y: auto; font-size: 14px; height: 400px; margin-left: 15px;">
					<div style='text-align: center;'>
						<font color='red' size='2'>请先选择一级维度，再新增或修改二级维度</font>
					</div>
				</ul>

			</div>
		</div>
	</div>
	<div class="fn-clear"></div>
	<div class="footer22"></div>
	<script type="text/javascript">
function selectDimension() {
		//根据输入字符查询
		 var val = jQuery.trim($("#J-searchval-f").val());
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
        }
	$(function () {
        //维度信息搜索
        $("#J-search-f").click(function () {
            var val = jQuery.trim($("#J-searchval-f").val());
               // val=val.toUpperCase();//字符转为大写
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
	});
	
	function selectDimensionDetail() {
		//根据输入字符查询
		 var val = jQuery.trim($("#J-searchval-ff").val());
		 var lilist = $("#J-searchul-ff>li");
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
        }
	function selectDimensionDetailSec() {
		//根据输入字符查询
		 var val = jQuery.trim($("#J-searchvalSec").val());
		 var lilist = $("#J-searchulSecUl>li");
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
        }
	
	$(function () {
        //维度信息搜索
        $("#J-search-ff").click(function () {
            var val = jQuery.trim($("#J-searchval-ff").val());
            var lilist = $("#J-searchul-ff>li");
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
	});
        </script>
	<script type="text/javascript">
	
//删除主表信息
			function deleteDimsion(id){
				if(confirm('确定删除该纬度？删除后其所有细项信息将一同被删除。确定删除该纬度细项？')){
					var url = "<%=basePath%>/Dimensionality/deleteDimension"+id+".html";
					$.get(url,function(data){
						if(data=="success"){
							alert("删除成功");
							var oDl = document.getElementById("li_prdInfo_"+id);
	    		        	  oDl.parentNode.removeChild(oDl);
	    		        	  //删除维度明细
	    		        	 $("#J-searchul-ff").html(""); 
	    		        	 $("#J-searchulSecUl").html(""); 
						}else{
							alert("删除失败");
							return;
						}
					});
				}
			}
	
	//删除 从表信息 一级维度
	function deleteDimsionDetail(dimensionId,primaryKeyId){
				if(confirm('确认删除该一级维度吗?')){
					var url = "<%=basePath%>/Dimensionality/deleteDimensionDetail"+primaryKeyId+".html";
					$.get(url,function(data){
						if(data=="success"){
							alert("删除成功");
							var oDl = document.getElementById("prim"+primaryKeyId);
	    		        	  oDl.parentNode.removeChild(oDl);
	    		        	  $("#add-dimensionDetailSec").val("");
	    		        	  $("#J-searchulSecUl").html("");  
						}else
						{
							alert("删除失败");
							return;
						}
					});
				}
			}
	//删除二级维度数据
	function deleteDimsionDetailSec(primary_id){
		if(confirm('确认删除该二级维度吗?')){
			var url = "<%=basePath%>/Dimensionality/deleteDimensionDetailSec.html?primary_id="+primary_id;
			$.get(url,function(data){
				if(data=="success"){
					alert("删除成功");
					var oDl = document.getElementById("primSec"+primary_id);
		        	  oDl.parentNode.removeChild(oDl);
				}else
				{
					alert("删除失败");
					return;
				}
			});
		}
	}
	//二级维度
	function showDimensionInfoSec(id){
		
		$.ajax({
	        type: "POST",                                                                 
	        url:"<%=basePath%>/NewReport/getDimensionDetailSecList.html?parentId="+id+"&random="+Math.random(),
	        success: function(msg){ 
	        	var msg=eval(msg);
	        	var listHtml="";
	        	if(msg.length<1){
	        		listHtml=listHtml+"<span style='color:red;' >&nbsp;&nbsp;该维度暂无详细信息</span>";
	        	}else{
	        		for(var i=0;i<msg.length;i++){
    	        		listHtml+="<li id='primSec"+msg[i].primary_id+"'><table border='1' width='262'><tr><td colspan='3'><a href='javascript:void(0);'>"
    	        		+msg[i].key+"-"+msg[i].value
    	        		+"</a></td></tr><tr><td width='70%'>&nbsp;</td><td align='center' ><a href='javascript:void(0);' onclick=editDimensionDetailSec('"+msg[i].primary_id
    	        		+"');>修改</a></td><td align='center'><a href='javascript:void(0);' onclick=deleteDimsionDetailSec('"+msg[i].primary_id
    	        		+"');>删除</a></td></tr></table></li>";
	        		}
	        	}
	           $("#J-searchul-ff li table tr td").removeClass("active");
	           $("#addClassId"+id).addClass("active")
               $("#J-searchulSecUl").html(listHtml);
               $("#add-dimensionDetailSec").val(id);
	        }
	     });  
	}
	//修改二级维度
	function editDimensionDetailSec(id){
		var dg = new $.dialog({
			title:'修改维度详细信息:',				
			width:340,
			height:240,
			iconTitle:false,
			cover:true,
			maxBtn:true,
			resize:true,
			page:'<%=basePath%>/Dimensionality/editDimensionDetailSec.html?primary_id='+id
			});
		dg.ShowDialog();
	}
    //根据id查询主表 一级维度
        function showDimensionInfo(id){
		 if(document.getElementById("add-dimensionDetail").value!=null&&document.getElementById("add-dimensionDetail").value.length>0)
		  {
			 $("#tr_"+document.getElementById("add-dimensionDetail").value).removeClass("active");
		  }
		  $("#tr_"+id).addClass("active");
	     $("#add-dimensionDetail").val(id);
        	$.ajax({
    	        type: "POST",                                                                 
    	        url:encodeURI("<%=basePath%>/Dimensionality/selectDimensionByAjax.html?id="+ id+"&random="+Math.random()),
    	        success: function(msg){ 
    	        	var msg=eval(msg);
    	        	var listHtml="";
    	        	if(msg.length<1){
    	        		listHtml=listHtml+"<span style='color:red;' >&nbsp;&nbsp;该维度暂无详细信息</span>";
    	        	}else{
    	        		for(var i=0;i<msg.length;i++){
        	        		listHtml+="<li id='prim"+msg[i].primaryKeyId+"'><table border='1' width='262'><tr><td colspan='3' id='addClassId"+msg[i].primaryKeyId+"' ><a href='javascript:showDimensionInfoSec("+msg[i].primaryKeyId+");'  >"
        	        		+msg[i].key+"-"+msg[i].value
        	        		+"</a></td></tr><tr><td width='70%'>&nbsp;</td><td align='center' ><a href='javascript:void(0);' onclick=editDimensionDetail('"+msg[i].dimensionId+"','"+msg[i].primaryKeyId
        	        		+"');>修改</a></td><td align='center'><a href='javascript:void(0);' onclick=deleteDimsionDetail('"+msg[i].dimensionId+"','"+msg[i].primaryKeyId
        	        		+"');>删除</a></td></tr></table></li>";
    	        		}
    	        	}
                   $("#J-searchul-ff").html(listHtml);
                   //清空二级维度
                   $("#J-searchulSecUl").html("");
                   $("#add-dimensionDetailSec").val("");
                  $("#J-searchul-f a").removeClass("active");
         		  $("#"+id).addClass("active");
    	        }
    	     });  
		}
        
	</script>
	<script type="text/javascript"
		src="<%=basePath%>js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>
	<script type="text/javascript">
	$("#add-dimension").bind("click",function(){ 
		var dg = new $.dialog({
			title:'添加维度信息',
			width:340,
			height:240,
			iconTitle:false,
			cover:true,
			maxBtn:false,
			xButton:true,
			resize:true,
			page:'<%=basePath%>/Dimensionality/addDimension.html'
			});
		dg.ShowDialog();		
	});
    //批量添加一级维度
	function batchAddDimensionDetail(){ 
		var id=$("#add-dimensionDetail").val();
		if( id =="" || id == undefined)
		{
			alert("请选择要添加详细维度的父类维度");
		    return false;
			
		}else
		{
			var dimensionName = $("#tr_"+id).text();
			var dg = new $.dialog({
				title:'批量添加详细维度信息:'+dimensionName,
				width:400,
				height:300,
				iconTitle:false,
				cover:true,
				maxBtn:false,
				xButton:true,
				resize:true,
				page:'<%=basePath%>/Dimensionality/batchAddDimension'+id+'.html'
				});
			dg.ShowDialog();
		}
				
	}
    //批量添加二级维度
    function batchAddDimensionDetailSec(){ 
		var id=$("#add-dimensionDetailSec").val();
		if( id =="" || id == undefined)
		{
			alert("请选择要添加详细维度的父类维度");
		    return false;
			
		}else
		{
			var dg = new $.dialog({
				title:'批量添加详细维度信息:',
				width:400,
				height:300,
				iconTitle:false,
				cover:true,
				maxBtn:false,
				xButton:true,
				resize:true,
				page:'<%=basePath%>/Dimensionality/batchAddDimensionSec.html?parentId='+id
				});
			dg.ShowDialog();
		}
				
	}
	//添加二级维度
	function addDimensionDetailSec(){ 
		var id=$("#add-dimensionDetailSec").val();
		if( id =="" || id == undefined)
		{
			alert("请选择要添加详细维度的父类维度");
		    return false;
			
		}else
		{
			var dg = new $.dialog({
				title:'批量添加详细维度信息:',
				width:400,
				height:300,
				iconTitle:false,
				cover:true,
				maxBtn:false,
				xButton:true,
				resize:true,
				page:'<%=basePath%>/Dimensionality/addDimensionDetailSec.html?parentId='+id
				});
			dg.ShowDialog();
		}
				
	}
	
	function addDimensionDetail(){ 
		var id=$("#add-dimensionDetail").val();
		if( id =="" || id == undefined)
		{
			alert("请选择要添加详细维度的父类维度");
		    return false;
			
		}else
		{
			var dimensionName = $("#tr_"+id).text();
			var dg = new $.dialog({
				title:'添加详细维度信息:'+dimensionName,
				width:340,
				height:240,
				iconTitle:false,
				cover:true,
				maxBtn:false,
				xButton:true,
				resize:true,
				page:'<%=basePath%>/Dimensionality/addDimensionDetail'+id+'.html'
				});
			dg.ShowDialog();
		}
				
	}
	
	function editDimension(id){
		var dimensionName = $("#tr_"+id).text();
		var dg = new $.dialog({
			title:'修改维度信息:'+dimensionName,				
			width:340,
			height:240,
			iconTitle:false,
			cover:true,
			maxBtn:false,
			resize:true,
			page:'<%=basePath%>/Dimensionality/editDimension'+id+'.html'
			});
		dg.ShowDialog();
	}
	
	function editDimensionDetail(dimensionId,id){
		var dimensionName = $("#tr_"+dimensionId).text();
		var dg = new $.dialog({
			title:'修改维度详细信息:'+dimensionName,				
			width:340,
			height:240,
			iconTitle:false,
			cover:true,
			maxBtn:false,
			resize:true,
			page:'<%=basePath%>/Dimensionality/editDimensionDetail'+id+'.html'
			});
		dg.ShowDialog();
	}
	</script>
</body>
</html>
