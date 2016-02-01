<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.infosmart.po.DwpasCColumnInfo"%>
<%@ page import="java.util.Date"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String columnTmpId = request.getParameter("columnTmpId");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>产品-通用指标选择</title>
<style type="text/css">
body {
	width: 100%;
	height: 100%;
	background-color: #f0f0f0;
	text-align: center;
}
.box {
	border-top-width: 1px;
	border-right-width: 0px;
	border-bottom-width: 0px;
	border-left-width: 1px;
	border-top-style: solid;
	border-right-style: solid;
	border-bottom-style: solid;
	border-left-style: solid;
	border-top-color: #000000;
	border-right-color: #000000;
	border-bottom-color: #000000;
	border-left-color: #000000;
}

.box td {
	border-top-width: 0px;
	border-right-width: 1px;
	border-bottom-width: 1px;
	border-left-width: 0px;
	border-top-style: solid;
	border-right-style: solid;
	border-bottom-style: solid;
	border-left-style: solid;
	border-top-color: #000000;
	border-right-color: #000000;
	border-bottom-color: #000000;
	border-left-color: #000000;
	font-size: 13px;
	word-break: break-all;
}
.td_input
{
    border-right:0px #253E7A solid;
    border-left:0px #253E7A solid;
    border-top:0px #253E7A solid;
    border-bottom:1px #253E7A solid;
    background-color:#ffffff;
	width:95%;
	height:99%;
    font-size:9pt;
}
.td_label
{
    border-right:0px #253E7A solid;
    border-left:0px #253E7A solid;
    border-top:0px #253E7A solid;
    border-bottom:0px #253E7A solid;
    background-color:#ffffff;
    font-size:9pt;
}
</style>
<link type="text/css" rel="stylesheet"
	href="<%=basePath%>/css/main.css" />
<script type="text/javascript" src="<%=path%>/js/jquery-1.5.1.min.js""></script>
<script type="text/javascript">
var columnTmpId="<%=columnTmpId%>";
var dg;
$(document).ready(function() {
	dg = frameElement.lhgDG;
	dg.addBtn('ok', '确定', function() {
		//已选择的数据
		var oTable = document.getElementById("addComKpiTab");
		if(oTable.rows.length<=1)
		{
			alert('至少配置1个指标');
			return;
		}
		
		if(oTable.rows.length>1){
			for(var i=1;i<oTable.rows.length;i++){
				
				if(document.getElementById("comKpi_"+oTable.rows[i].id).value=="n"){
					alert("请选择指标！");
					document.getElementById("comKpi_"+oTable.rows[i].id).focus();
					return false;
				}
				if(document.getElementById("prd_"+oTable.rows[i].id).value=="n"){
					alert("请选择产品！");
					document.getElementById("prd_"+oTable.rows[i].id).focus();
					return false;
				}
				for(var j=i+1;j<oTable.rows.length;j++){
					if(document.getElementById("comKpi_"+oTable.rows[i].id).value+document.getElementById("prd_"+oTable.rows[i].id).value==document.getElementById("comKpi_"+oTable.rows[j].id).value+document.getElementById("prd_"+oTable.rows[j].id).value){
						alert("请不要配置相同的通用指标和产品！");
						return false;
					}
				
				}
			}
		}
		
		//关联指标类型
		var relCommKpiOptOfColumn=dg.curWin.document.getElementById("relCommKpiOpt_<%=columnTmpId%>");
		if(relCommKpiOptOfColumn!=null) relCommKpiOptOfColumn.length=0;//先清空数据
		var relCommKpiCodes="";
		for(var i=1;i<oTable.rows.length;i++){
			var row=oTable.rows[i];
			var productId=document.getElementById("prd_"+row.id).value;
			var productName = document.getElementById("prd_"+row.id).options[document.getElementById("prd_"+row.id).selectedIndex].text;
			var comKpiCode=document.getElementById("comKpi_"+row.id).value;
			var comKpiName = document.getElementById("comKpi_"+row.id).options[document.getElementById("comKpi_"+row.id).selectedIndex].text;
			var ComKpiAndPrd =comKpiCode+"^"+productId;
			if(relCommKpiCodes.length>0) relCommKpiCodes+=",";
			relCommKpiCodes+=ComKpiAndPrd;
			relCommKpiOptOfColumn.options.add(new Option(comKpiName+"——"+productName,comKpiCode+"-"+productId));//text,value
			
		}
		//relCommKpiCodes
		//关联的指标编码
		dg.curWin.document.getElementById("relCommKpiCodes_<%=columnTmpId%>").value=relCommKpiCodes;
		//如果为产品全图
		if("${moduleCode}"=="INDEX_PRODUCT_CHART"){
			dg.cancel();
		}
		var graphSelect = dg.curWin.document.getElementById("chartTypeDesc_<%=columnTmpId%>");
		var graphImg = dg.curWin.document.getElementById("graphic_<%=columnTmpId%>");
		graphSelect.length=0;
		var graphValues = "";
		var graphNames ="";
		if(document.getElementById("graphType").value=="<%=DwpasCColumnInfo.CHART_TYPE_DESC_LINE_COLUMN%>"){
			for(var i=1;i<oTable.rows.length;i++){
				var row=oTable.rows[i];
				var graphId = document.getElementById("graph_"+row.id).value;
				var graphName = document.getElementById("graph_"+row.id).options[document.getElementById("graph_"+row.id).selectedIndex].text;
				if(i==1){
					graphValues=graphId;
					graphNames=graphName;
				}else{
					graphValues+=";"+graphId;
					graphNames+="+"+graphName;
				}
			}
			if(oTable.rows.length==2){
				var row=oTable.rows[1];
				if(document.getElementById("graph_"+row.id).value=="<%=DwpasCColumnInfo.CHART_TYPE_DESC_LINE%>"){
					graphImg.src="<%=path%>/images/chart/line.png";
				}else if(document.getElementById("graph_"+row.id).value=="<%=DwpasCColumnInfo.CHART_TYPE_DESC_COLUMN%>"){
					graphImg.src="<%=path%>/images/chart/column.png";
				}
			}else{
				graphImg.src="<%=path%>/images/chart/column_line.jpg";
			}
			
		}else{
			graphValues=document.getElementById("graphType").value;
			if(graphValues=="<%=DwpasCColumnInfo.CHART_TYPE_DESC_STOCK%>"){
				graphImg.src="<%=path%>/images/chart/stocked.png";
			 }else if(graphValues=="<%=DwpasCColumnInfo.CHART_TYPE_DESC_PIE%>"){
				 graphImg.src="<%=path%>/images/chart/pie.png";
			 }else if(graphValues=="<%=DwpasCColumnInfo.CHART_TYPE_DESC_BAR%>"){
				 graphImg.src="<%=path%>/images/chart/bar.png";
			 }else if(graphValues=="<%=DwpasCColumnInfo.CHART_TYPE_DESC_AREA%>"){
				 graphImg.src="<%=path%>/images/chart/area.png";
			 }else if(graphValues=="<%=DwpasCColumnInfo.CHART_TYPE_DESC_LINE%>"){
				 graphImg.src="<%=path%>/images/chart/line.png";
			 }else if(graphValues=="<%=DwpasCColumnInfo.CHART_TYPE_DESC_COLUMN%>"){
				 graphImg.src="<%=path%>/images/chart/column.png";
			 }
			
			
			graphNames=document.getElementById("graphType").options[document.getElementById("graphType").selectedIndex].text;
		}
		graphSelect.options.add(new Option(graphNames,graphValues));
		dg.cancel();
	});
});

window.onload = function (){
	
	var oTable = document.getElementById("addComKpiTab");
	var graphValues = "${graphValues}";
	
	//如果是产品全图
	if("${moduleCode}"=="INDEX_PRODUCT_CHART"){
		graphValues="";
		for(var i=1;i<oTable.rows.length;i++){
			var row=oTable.rows[i];
			//var graphName=graphValue[i-1];
			document.getElementById("graph_"+row.id).parentNode.innerHTML="——";	
		}
		return;
		
	}
	if(graphValues!=null && graphValues.length>0){
		var graphValue = graphValues.split(";");
		if(graphValue.length>1){
			document.getElementById("graphType").value ="<%=DwpasCColumnInfo.CHART_TYPE_DESC_LINE_COLUMN%>";
			//示例图标
			document.getElementById("graphicImg").src ="<%=basePath%>/images/column_line.jpg";//graphicImg
			for(var i=1;i<oTable.rows.length;i++){
				var row=oTable.rows[i];
				//var graphName=graphValue[i-1];
				//document.getElementById("graph_"+row.id).parentNode.innerHTML="<select name='choiceGraph' id='graph_"+row.id+"'>"+graphName+"</select>";
				document.getElementById("graph_"+row.id).length=0;
				document.getElementById("graph_"+row.id).options.add(new Option("矩形图","<%=DwpasCColumnInfo.CHART_TYPE_DESC_COLUMN%>"));
				document.getElementById("graph_"+row.id).options.add(new Option("折线图","<%=DwpasCColumnInfo.CHART_TYPE_DESC_LINE%>"));
				document.getElementById("graph_"+row.id).value=graphValue[i-1];
			}
		}else {
			if(graphValue=="<%=DwpasCColumnInfo.CHART_TYPE_DESC_COLUMN%>" || graphValue=="<%=DwpasCColumnInfo.CHART_TYPE_DESC_COLUMN%>"){
				document.getElementById("graphType").value ="<%=DwpasCColumnInfo.CHART_TYPE_DESC_LINE_COLUMN%>";
			    //示例图标
			    document.getElementById("graphicImg").src ="<%=basePath%>/images/column_line.jpg";//graphicImg
				for(var i=1;i<oTable.rows.length;i++){
					var row=oTable.rows[i];
					document.getElementById("graph_"+row.id).length=0;
					document.getElementById("graph_"+row.id).options.add(new Option("矩形图","<%=DwpasCColumnInfo.CHART_TYPE_DESC_COLUMN%>"));
					document.getElementById("graph_"+row.id).options.add(new Option("折线图","<%=DwpasCColumnInfo.CHART_TYPE_DESC_LINE%>"));
					document.getElementById("graph_"+row.id).value=graphValue;
				}
			}
			
			var graphName = "";
			
			document.getElementById("graphType").value =graphValue;
			
			if(graphValue=="<%=DwpasCColumnInfo.CHART_TYPE_DESC_STOCK%>" ){
				graphName ="趋势图";
			    //示例图标
			    document.getElementById("graphicImg").src ="<%=basePath%>/images/stock.jpg";//graphicImg
			}else if(graphValue=="<%=DwpasCColumnInfo.CHART_TYPE_DESC_PIE%>" ){
				graphName ="饼图";
			    //示例图标
			    document.getElementById("graphicImg").src ="<%=basePath%>/images/pie2.jpg";//graphicImg
			}else if(graphValue=="<%=DwpasCColumnInfo.CHART_TYPE_DESC_BAR%>" ){
				graphName ="条形图";
			    //示例图标
			    document.getElementById("graphicImg").src ="<%=basePath%>/images/bar.jpg";//graphicImg
			}else if(graphValue=="<%=DwpasCColumnInfo.CHART_TYPE_DESC_AREA%>" ){
				graphName ="面积图";
			    //示例图标
			    document.getElementById("graphicImg").src ="<%=basePath%>/images/area.jpg";//graphicImg
			}
			for(var i=1;i<oTable.rows.length;i++){
				var row=oTable.rows[i];
				document.getElementById("graph_"+row.id).length=0;
				document.getElementById("graph_"+row.id).options.add(new Option(graphName,graphValue));
				document.getElementById("graph_"+row.id).value=graphValue;
			}
		}
	}
	
}



function addNewRow(){
	 var oTable = document.getElementById("addComKpiTab");
	 var graphType = document.getElementById("graphType").value;
	 var row = oTable.insertRow(-1);
	 row.id="row_"+new Date().getTime();
	 row.style.height="30px";
	 var cell1 = row.insertCell(-1);
	 var comKpiListHTML="";
	 <c:forEach items="${commonKpiCodeList}" var="comKpiInfo">
	    comKpiListHTML+='<option value="${comKpiInfo.comKpiCode}">${comKpiInfo.comKpiName }</option>';
	 </c:forEach>
	 cell1.innerHTML="<select name='comKpi' id='comKpi_"+row.id+"' style='width:99%;'>"+ "<option value='n' style='color:red;'>请选择</option>"+comKpiListHTML+"</select>";
	
	 var cell2 = row.insertCell(-1);
	 var productListHTML="";
	 <c:forEach items="${productInfoList}" var="productInfo">
	     productListHTML+='<option value="${productInfo.productId}">${productInfo.productName }</option>';
	 </c:forEach>
	 cell2.innerHTML="<select name='prdId' id='prd_"+row.id+"' style='width:99%;'>"+"<option value='n' style='color:red;'>请选择</option>"+ productListHTML+"</select>";
	
	 var cell3 = row.insertCell(-1);
	 var graphName="";
	 if(graphType=="<%=DwpasCColumnInfo.CHART_TYPE_DESC_STOCK%>"){
		 graphName="<option value='<%=DwpasCColumnInfo.CHART_TYPE_DESC_STOCK%>'>趋势图</option>";
	 }else if(graphType=="<%=DwpasCColumnInfo.CHART_TYPE_DESC_PIE%>"){
		 graphName="<option value='<%=DwpasCColumnInfo.CHART_TYPE_DESC_PIE%>'>饼图</option>";
	 }else if(graphType=="<%=DwpasCColumnInfo.CHART_TYPE_DESC_BAR%>"){
		 graphName="<option value='<%=DwpasCColumnInfo.CHART_TYPE_DESC_BAR%>'>条形图</option>";
	 }else if(graphType=="<%=DwpasCColumnInfo.CHART_TYPE_DESC_AREA%>"){
		 graphName="<option value='<%=DwpasCColumnInfo.CHART_TYPE_DESC_AREA%>'>面积图</option>";
	 }else if(graphType=="<%=DwpasCColumnInfo.CHART_TYPE_DESC_LINE_COLUMN%>"){
		 graphName="<option value='<%=DwpasCColumnInfo.CHART_TYPE_DESC_COLUMN%>'>矩形图</option><option value='<%=DwpasCColumnInfo.CHART_TYPE_DESC_LINE%>'>折线图</option>";
	 }
	 cell3.innerHTML="<select name='choiceGraph' id='graph_"+row.id+"' style='width:99%;'>"+graphName+"</select>";
	 var cell4 = row.insertCell(-1);
	 cell4.innerHTML="<label style='cursor:pointer;' onclick='delRow(\""+row.id+"\")'>删除</label>";
}

function delRow(rowId){
	document.getElementById(rowId).parentNode.removeChild(document.getElementById(rowId));
}

function changeGraph(graphType){
	var oTable = document.getElementById("addComKpiTab");
	if(oTable.rows.length>1){
		
		var graphName="";
		 if(graphType=="<%=DwpasCColumnInfo.CHART_TYPE_DESC_STOCK%>"){
			 graphName="<option value='<%=DwpasCColumnInfo.CHART_TYPE_DESC_STOCK%>'>趋势图</option>";
			 document.getElementById("graphicImg").src="<%=basePath%>/images/stock.jpg";
		 }else if(graphType=="<%=DwpasCColumnInfo.CHART_TYPE_DESC_PIE%>"){
			 graphName="<option value='<%=DwpasCColumnInfo.CHART_TYPE_DESC_PIE%>'>饼图</option>";
			 document.getElementById("graphicImg").src="<%=basePath%>/images/pie2.jpg";
		 }else if(graphType=="<%=DwpasCColumnInfo.CHART_TYPE_DESC_BAR%>"){
			 graphName="<option value='<%=DwpasCColumnInfo.CHART_TYPE_DESC_BAR%>'>条形图</option>";
			 document.getElementById("graphicImg").src="<%=basePath%>/images/bar.jpg";
		 }else if(graphType=="<%=DwpasCColumnInfo.CHART_TYPE_DESC_AREA%>"){
			 graphName="<option value='<%=DwpasCColumnInfo.CHART_TYPE_DESC_AREA%>'>面积图</option>";
			 document.getElementById("graphicImg").src="<%=basePath%>/images/area.jpg";
		 }else if(graphType=="<%=DwpasCColumnInfo.CHART_TYPE_DESC_LINE_COLUMN%>"){
			 graphName="<option value='<%=DwpasCColumnInfo.CHART_TYPE_DESC_COLUMN%>'>矩形图</option><option value='<%=DwpasCColumnInfo.CHART_TYPE_DESC_LINE%>' style='color:blue;'>折线图</option>";
			 document.getElementById("graphicImg").src="<%=basePath%>/images/column_line.jpg";
			}
			for ( var i = 1; i < oTable.rows.length; i++) {
				var row = oTable.rows[i];
				document.getElementById("graph_" + row.id).parentNode.innerHTML = "<select name='choiceGraph' id='graph_"+row.id+"' style='width:99%;'>"
						+ graphName + "</select>";
			}
		}

	}
</script>
</head>
<body>
	<div
		<c:if test="${moduleCode=='INDEX_PRODUCT_CHART'}">style='display: none'</c:if>>
		<table border='0' width="100%">
			<tr height="30" align="left">
				<td width="40%">图形类型选择： <select id="graphType"
					onchange="changeGraph(this.value);">
						<option value="<%=DwpasCColumnInfo.CHART_TYPE_DESC_STOCK%>">趋势图</option>
						<option value="<%=DwpasCColumnInfo.CHART_TYPE_DESC_PIE%>">饼图</option>
						<option value="<%=DwpasCColumnInfo.CHART_TYPE_DESC_BAR%>">条形图</option>
						<option value="<%=DwpasCColumnInfo.CHART_TYPE_DESC_AREA%>">面积图</option>
						<option value="<%=DwpasCColumnInfo.CHART_TYPE_DESC_LINE_COLUMN%>">组合图 / 矩形图 / 折线图</option>
				</select></td>
				<td width="55%" rowspan="2" align='left'>
				    <img src="<%=basePath%>/images/stock.jpg" border='0' width='100px' height="60px" id="graphicImg">
				</td>
			</tr>
			<tr>
			   <td>
					<div style="height: 30px;" align="left">
						<a href="javascript:addNewRow();" class="myBtn"
							<c:if test="${moduleCode=='INDEX_PRODUCT_CHART'}">style='display: none'</c:if>><em>新增</em>
						</a>
					</div>		
	          </td>
			</tr>
		</table>
	</div>
	<div style="position: absolute;left:10px;bottom:5px;">
			   &nbsp;&nbsp;注：<b>配置时请注意通用指标已关联在产品下，选择组合图时，可分别配置矩形图、折线图。</b>
	</div>
	<form id="choiceCodeForm" name="choiceCodeForm" method="post"
		action=" " target="result">
		<table width='100%' border='1' cellpadding='0' cellspacing='0'
			id="addComKpiTab" class='box'>
			<tr height="30" style='background: #ECF9FF; font-weight: bold;'>
				<td width="40%">指标</td>
				<td width="30%">产品</td>
				<td width="20%">图形</td>
				<td width="10%">删除</td>
			</tr>
			<c:choose>
				<c:when test="${not empty selectComKpiInfoList }">

					<c:forEach items="${selectComKpiInfoList}" var="selectComKpiInfo"
						varStatus="sta">
						<c:set var="rowId" value="<%=new Date().getTime()%>"></c:set>
						<tr height="30" id="${rowId+sta.index}">
							<td><select style="width:99%;" name="comKpi"
								id="comKpi_${rowId+sta.index}">
									<option value="n" style='color:red;'>请选择</option>
									<c:forEach items="${commonKpiCodeList}" var="comKpiInfo">
										<option value="${comKpiInfo.comKpiCode}"
											<c:if test="${selectComKpiInfo.comKpiCode eq comKpiInfo.comKpiCode}">selected</c:if>>${comKpiInfo.comKpiName
											}</option>
									</c:forEach>
							</select></td>
							<td><select name="prdId" id="prd_${rowId+sta.index}" style='width:99%;'>
									<option value="n" style='color:red;'>请选择</option>
									<c:forEach items="${productInfoList}" var="productInfo">
										<option value="${productInfo.productId}"
											<c:if test="${selectComKpiInfo.productId eq productInfo.productId}">selected</c:if>>${productInfo.productName
											}</option>
									</c:forEach>
							</select></td>
							<td><select name="choiceGraph" id="graph_${rowId+sta.index}" style='width:120px;'></select>
							</td>
							<td><c:choose>
									<c:when test="${moduleCode=='INDEX_PRODUCT_CHART'}">——</c:when>
									<c:otherwise>
										<label style="cursor: pointer;"
											onclick="delRow('${rowId+sta.index}')">删除</label>
									</c:otherwise>
								</c:choose></td>
						</tr>
					</c:forEach>

				</c:when>
				<c:otherwise>
					<c:if test="${moduleCode=='INDEX_PRODUCT_CHART'}">
						<c:set var="rowId" value="<%=new Date().getTime()%>"></c:set>
						<tr height="30" id="${rowId+sta.index}">
							<td><select name="comKpi" id="comKpi_${rowId+sta.index}" style='width:99%;'>
									<c:forEach items="${commonKpiCodeList}" var="comKpiInfo">
										<option value="${comKpiInfo.comKpiCode}"
											<c:if test="${selectComKpiInfo.comKpiCode eq comKpiInfo.comKpiCode}">selected</c:if>>${comKpiInfo.comKpiName
											}</option>
									</c:forEach>
							</select></td>
							<td><select name="prdId" id="prd_${rowId+sta.index}" style='width:99%;'>
									<c:forEach items="${productInfoList}" var="productInfo">
										<option value="${productInfo.productId}"
											<c:if test="${selectComKpiInfo.productId eq productInfo.productId}">selected</c:if>>${productInfo.productName
											}</option>
									</c:forEach>
							</select></td>
							<td><select name="choiceGraph" id="graph_${rowId+sta.index}" style='width:120px;'></select>
							</td>
							<td>——</td>
						</tr>
					</c:if>
				</c:otherwise>
			</c:choose>
		</table>
	</form>
	<iframe name="result" id="result" src="about:blank" frameborder="0"
		width="0" height="0"></iframe>
</body>
</html>