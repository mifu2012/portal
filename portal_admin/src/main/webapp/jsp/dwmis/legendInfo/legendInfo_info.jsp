<%@page import="com.infosmart.po.DwpasCColumnInfo"%>
<%@page import="com.infosmart.util.dwmis.CoreConstant"%>
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
<title>My Test</title>
<link type="text/css" rel="stylesheet" href="<%=basePath%>css/main.css" />
<script type="text/javascript" src="<%=basePath%>js/jquery-1.5.1.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>
<style type="text/css">
body {
	width: 100%;
	height: 100%;
	background-color: #FFFFFF;
	text-align: center;
}

.input_txt {
	width: 200px;
	height: 20px;
	line-height: 20px;
}

.info {
	height: 30px;
	line-height: 30px;
}

.info th {
	text-align: right;
	width: 65px;
	color: #4f4f4f;
	padding-right: 5px;
	font-size: 13px;
}

.info td {
	text-align: left;
}
</style>
<script>
	window.onload = function() {
		var relCommKpiOpt = document
				.getElementById('relCommKpiOpt_${dwmisLegendInfo.legendId}');
		<c:forEach items="${kpiInfoList}" var="kpiInfo">
		relCommKpiOpt.options.add(new Option("${kpiInfo.kpiName}",
				"${kpiInfo.kpiCode}"));
		</c:forEach>
		initChartType(document.getElementById("chartTypeDesc"),relCommKpiOpt.length);
		var type = "${dwmisLegendInfo.chartType}";
		 if(parseInt(type)==<%=CoreConstant.CHART_RECTANGULAR_DISCOUNT%>){
			document.getElementById("chartTypeDesc").value="${dwmisLegendInfo.chartType},${dwmisLegendInfo.chartTypeDesc};";
		}else{
			document.getElementById("chartTypeDesc").value="${dwmisLegendInfo.chartType},${dwmisLegendInfo.chartTypeDesc}";
		} 
		<c:choose>
		  <c:when test="${dwmisLegendInfo.chartType==0}">
			//趋势图
            document.getElementById("imgDiv").innerHTML="<img src='<%=basePath%>images/stock.jpg' height='120px' width='209px'>";
		  </c:when>
		  <c:when test="${dwmisLegendInfo.chartType==1}">
			//饼图
            document.getElementById("imgDiv").innerHTML="<img src='<%=basePath%>images/pie.jpg' height='120px' width='120px'>";
		  </c:when>
		  <c:when test="${dwmisLegendInfo.chartType==2}">
			//矩形图
            document.getElementById("imgDiv").innerHTML="<img src='<%=basePath%>images/column.jpg' height='120px' width='209px'>";
		  </c:when>
		  <c:when test="${dwmisLegendInfo.chartType==3}">
			//折线图
            document.getElementById("imgDiv").innerHTML="<img src='<%=basePath%>images/line.jpg' height='120px' width='209px'>";
		  </c:when>
		  <c:when test="${dwmisLegendInfo.chartType==5}">
			//面积图
            document.getElementById("imgDiv").innerHTML="<img src='<%=basePath%>images/area.jpg' height='120px' width='209px'>";
		  </c:when>
	      <c:otherwise>
			//组合图
			document.getElementById("imgDiv").innerHTML="<img src='<%=basePath%>images/column_line.jpg' height='120px' width='209px'>";
		  </c:otherwise>
	    </c:choose>
		if(type==null||type.length==0) document.getElementById("imgDiv").innerHTML="<img src='<%=basePath%>images/stock.jpg' height='120px' width='209px'>";
	}
</script>
</head>
<body>
    <div style="position: absolute;right:25px;top:165px;" id='imgDiv'><img src="<%=basePath%>images/stock.jpg" height='120px' width='209px'></div>
	<form action="save" name="Form" id="Form" target="result" method="post" onsubmit="return checkInfo();">
		<input type="hidden" name="legendId" id="legendId"
			value="${dwmisLegendInfo.legendId }" />
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<tr class="info">
				<td width="20%" style='text-align:right;'><font color="red">*</font>&nbsp;图例名称：&nbsp;</td>
				<td><input type="text" name="legendName" id="legendName"
					class="input_txt" value="${dwmisLegendInfo.legendName }" style="width:208px;" maxlength="30"/></td>
			</tr>
			<tr class="info">
				<td  style='text-align:right;'>所属分类：&nbsp;</td>
				<td><select name="categoryId" id="categoryId" style='width:160px;width: 215px;'>
						<c:forEach items="${legendCategoryList }" var="category">
							<option value="${category.categoryId}"
								<c:if test="${dwmisLegendInfo.categoryId==category.categoryId}">selected</c:if>>${category.categoryName}</option>
						</c:forEach>
				</select></td>
			</tr>
			<tr class="info">
				<td  style='text-align:right;'><font color="red">*</font>&nbsp;指标关联：&nbsp;</td>
				<td><select name='kpiCodes' size="10"
					style='width: 70%; height: 100px;'
					id='relCommKpiOpt_${dwmisLegendInfo.legendId }' multiple="multiple" ondblclick="delKpiCode('${dwmisLegendInfo.legendId}');"></select>
					<a href="javascript:void(0)"
					onclick="javascript:addCommKpiCode('${dwmisLegendInfo.legendId}');">选择指标</a>
					&nbsp;&nbsp;<a href="javascript:void(0);"
					onclick="delKpiCode('${dwmisLegendInfo.legendId}');">删除指标</a></td>
			</tr>
			<tr class="info">
				<td  style='text-align:right;'><font color="red">*</font>&nbsp;图例类型：&nbsp;</td>
				<td><select name="chartTypeDesc" id="chartTypeDesc"
					onchange="showLine(this.options[this.options.selectedIndex].value)" style='width:215px;'>
				</select>
				</td>
			</tr>
			<tr class="info" id="tr_showWarnLine" <c:if test="${dwmisLegendInfo.chartType!=3 }"> style="display: none"</c:if>>
				<td  style='text-align:right;'>显示目标线：&nbsp;</td>
				<td><input type="checkbox" name="showWarnLine"
					id="showWarnLine" value="1" <c:if test="${dwmisLegendInfo.showWarnLine==1 }"> checked="checked"</c:if> /><label for='showWarnLine' style='cursor:pointer;'>显示目标线</label></td>
			</tr>
			<tr class="info">
				<td  style='text-align:right;'>统计方式：&nbsp;</td>
				<td><select id="statCode" name="statCode" style='width:215px;'>
						<c:forEach items="${misTypeList }" var="misType">
							<option value="${misType.typeId }"
								<c:if test="${misType.typeId==dwmisLegendInfo.statCode}">selected</c:if>>${misType.typeName
								}</option>
						</c:forEach>
				</select></td>
			</tr>
			<tr class="info">
				<td  style='text-align:right;'>备注：&nbsp;</td>
				<td><textarea name="remark" id="remark" rows="4" cols="24">${dwmisLegendInfo.remark }</textarea>
				</td>
			</tr>
		</table>
	</form>
	<iframe name="result" id="result" src="about:blank" frameborder="0"
		width="0" height="0" style="display:none;"></iframe>
</body>
</html>
<script type="text/javascript">
function showLine(chartType){
	var type = "<%=CoreConstant.CHART_DISCOUNT%>,<%=DwpasCColumnInfo.CHART_TYPE_DESC_LINE%>";
	if(chartType==type){
		if(document.getElementById("relCommKpiOpt_${dwmisLegendInfo.legendId}").length==1){
			document.getElementById("tr_showWarnLine").style.display = ""; // 显示tr
		}else{
			document.getElementById("showWarnLine").checked = false;
			document.getElementById("tr_showWarnLine").style.display = "none";  // 不显示tr
		}
	}else{
		document.getElementById("showWarnLine").checked = false;
		document.getElementById("tr_showWarnLine").style.display = "none";  // 不显示tr
	}
	var chartTypeVal=chartType.split(",")[0];
	if(chartTypeVal==0)
	{
	  //趋势图
	  document.getElementById("imgDiv").innerHTML="<img src='<%=basePath%>images/stock.jpg' height='120px' width='209px'>";
	}else if(chartTypeVal==3)
	{
	  //折线图
	  document.getElementById("imgDiv").innerHTML="<img src='<%=basePath%>images/line.jpg' height='120px' width='209px'>";
	}else if(chartTypeVal==1)
	{
	  //饼图
	  document.getElementById("imgDiv").innerHTML="<img src='<%=basePath%>images/pie.jpg' height='120px' width='120px'>";
	}else if(chartTypeVal==2)
	{
	  //矩形
	  document.getElementById("imgDiv").innerHTML="<img src='<%=basePath%>images/column.jpg' height='120px' width='209px'>";
	}else if(chartTypeVal==5)
	{
      document.getElementById("imgDiv").innerHTML="<img src='<%=basePath%>images/area.jpg' height='120px' width='209px'>";
	}else
	{
	   var chartTypeDesc=chartType.split(",")[1];
	   if(chartTypeDesc.indexOf("line")!=-1&&chartTypeDesc.indexOf("column")!=-1)
		{
		   //组合图
		   document.getElementById("imgDiv").innerHTML="<img src='<%=basePath%>images/column_line.jpg' height='120px' width='209px'>";
		}else if(chartTypeDesc.indexOf("line")!=-1)
		{
			//线图
			document.getElementById("imgDiv").innerHTML="<img src='<%=basePath%>images/line.jpg' height='120px' width='209px'>";
		}else
		{
			//矩形
			document.getElementById("imgDiv").innerHTML="<img src='<%=basePath%>images/column.jpg' height='120px' width='209px'>";
		}
	}
}
function initChartType(chartTypeOpt,kpiCodeCount){
	 if(kpiCodeCount==0){
		return;
	} 
	//先清空数据 	
	chartTypeOpt.length=0;
	//趋势图
	chartTypeOpt.options.add(new Option("趋势图","<%=CoreConstant.CHART_TREND%>"+","+"<%=DwpasCColumnInfo.CHART_TYPE_DESC_STOCK%>"));
	//折线图
	chartTypeOpt.options.add(new Option("折线图","<%=CoreConstant.CHART_DISCOUNT%>"+","+"<%=DwpasCColumnInfo.CHART_TYPE_DESC_LINE%>"));
	//面积图
	chartTypeOpt.options.add(new Option("面积图","<%=CoreConstant.CHART_AREA%>"+","+"<%=DwpasCColumnInfo.CHART_TYPE_DESC_AREA%>"));
	//矩形图
	chartTypeOpt.options.add(new Option("矩形图","<%=CoreConstant.CHART_RECTANGULAR%>"+","+"<%=DwpasCColumnInfo.CHART_TYPE_DESC_COLUMN%>"));
	if(kpiCodeCount>1){
		 //饼图
		 chartTypeOpt.options.add(new Option("饼图","<%=CoreConstant.CHART_PIE%>"+","+"<%=DwpasCColumnInfo.CHART_TYPE_DESC_PIE%>"));
		//组合图
		//全矩形
		var columnChart="";
		for(i=0;i<kpiCodeCount;i++){
			columnChart+="<%=DwpasCColumnInfo.CHART_TYPE_DESC_COLUMN%>;";//column
		}
		chartTypeOpt.options.add(new Option(kpiCodeCount+"矩形",<%=CoreConstant.CHART_RECTANGULAR_DISCOUNT%>+","+columnChart));
		//矩形+折线
		for(var i=1;i<kpiCodeCount;i++)
		{
		   var columnChart="";
		   for(var j=1;j<=i;j++)
		   {
			  columnChart+="<%=DwpasCColumnInfo.CHART_TYPE_DESC_COLUMN%>;";//column
		   }
		   var lineChart="";
		   for(var j=kpiCodeCount-i;j>=1;j--)
		   {
			  lineChart+="<%=DwpasCColumnInfo.CHART_TYPE_DESC_LINE%>;";//line
		   }
		   chartTypeOpt.options.add(new Option(i+"矩形"+" + "+(kpiCodeCount-i)+"折线",<%=CoreConstant.CHART_RECTANGULAR_DISCOUNT%>+","+columnChart+lineChart));
		}
		//全是折线
		var lineChart="";
		for(var j=1;j<=kpiCodeCount;j++){
			lineChart+="<%=DwpasCColumnInfo.CHART_TYPE_DESC_LINE%>;";//line
		}
		  chartTypeOpt.options.add(new Option(kpiCodeCount+"折线",<%=CoreConstant.CHART_RECTANGULAR_DISCOUNT%>+","+lineChart));
	}
}
</script>
	<script type="text/javascript">
		var dg;
		$(document).ready(function() {
			dg = frameElement.lhgDG;
			dg.addBtn('ok','保存',function() {
				var obj1 = document.getElementById('relCommKpiOpt_${dwmisLegendInfo.legendId }');
					for ( var i = 0; i <= obj1.options.length - 1; i++) {
						obj1.options[i].selected = true;
					}
				$("#Form").submit();
			});
		});

		function checkInfo() {
			if ($("#legendName").val() == "") {
				alert("请输入图例名!");
				$("#legendName").focus();
				return false;
			}if (document.getElementById("relCommKpiOpt_${dwmisLegendInfo.legendId}").length < 1) {
				alert("请选择关联指标!");
				return false;
			}if ($("#chartTypeDesc").val()==null||$("#chartTypeDesc").val().length==0) {
				alert("请选择图例类型!");
				return false;
			}if (document.getElementById("relCommKpiOpt_${dwmisLegendInfo.legendId}").length>1) {
				if(document.getElementById("showWarnLine").checked){
				alert("多个指标折线图选择显示目标线会影响图例的展示，请取消选择!");
				return false;
				}
			}
			

			return true;
		}

		function success() {
			alert("操作成功！");
			if (dg.curWin.document.forms[0]) {
				dg.curWin.document.forms[0].action = dg.curWin.location + "";
				dg.curWin.document.forms[0].submit();
			} else {
				dg.curWin.location.reload();
			}
			dg.cancel();
		}
		function failed()
		{
			alert('操作失败');
		}
		function addCommKpiCode(id) {
			var relCommKpiOpt = document.getElementById("relCommKpiOpt_" + id);
			var selectedComKpiCodes = "";
			if (relCommKpiOpt != null) {
				for ( var i = 0; i < relCommKpiOpt.options.length; i++) {
					if (selectedComKpiCodes.length > 0)
						selectedComKpiCodes += ",";
					selectedComKpiCodes += relCommKpiOpt.options[i].value;
				}
			}
			var dg = new $.dialog({
				title : '选择指标',
				id : 'show_commoncode',
				width : 600,
				height : 380,
				iconTitle : false,
				cover : true,
				maxBtn : true,
				xButton : true,
				resize : true,
				page : 'showKpi.html?id=' + id + '&selectedKpi='+ selectedComKpiCodes
			});
			dg.ShowDialog();
		};
	//删除指标
	function delKpiCode(id){
       var relCommKpiOpt=document.getElementById("relCommKpiOpt_"+id);
       var chartType = document.getElementById("chartTypeDesc");
	   if(relCommKpiOpt==null||relCommKpiOpt.length==0){
		   alert("该图例无关联指标!");
		   return;
	   }
	   if(relCommKpiOpt.selectedIndex==-1){
		   alert("请选中要删除的指标!");
		   return;
	   }
	   
	   for(var i = 0; i < relCommKpiOpt.length; i++){
		   if(relCommKpiOpt.options[i]==null) continue;
		   if(relCommKpiOpt.options[i].selected == true){
			   try
			   {
				 relCommKpiOpt.remove(i);
			   }
			   catch (e)
			   {
				 alert(e.message);
                 document.getElementById("relCommKpiOpt_"+id).removeChild(relCommKpiOpt.options[i]);
			   }
			   i--;
		   }
	   }
	   var defaultChartType=chartType.value;
	   initChartType(chartType,relCommKpiOpt.length);
	   document.getElementById("chartTypeDesc").value = defaultChartType;
	   //如果全部删除了
	   if(relCommKpiOpt.options.length==0){
		   document.getElementById("chartTypeDesc").length=0;//清空数据
		   return;
		}
	}
	</script>