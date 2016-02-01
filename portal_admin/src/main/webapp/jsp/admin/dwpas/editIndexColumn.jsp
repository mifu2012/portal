<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="com.infosmart.po.DwpasCmoduleInfo" %>
<%@ page import="com.infosmart.po.DwpasCColumnInfo" %>
<%@ page import="com.infosmart.po.DwpasCKpiInfo" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	//定义位置常量
	request.setAttribute("POSITION_TOP",DwpasCmoduleInfo.POSITION_TOP);
	request.setAttribute("POSITION_LEFT",DwpasCmoduleInfo.POSITION_LEFT);
	request.setAttribute("POSITION_RIGHT",DwpasCmoduleInfo.POSITION_RIGHT);
	request.setAttribute("POSITION_BOTTOM",DwpasCmoduleInfo.POSITION_BOTTOM);
	request.setAttribute("CHART_TYPE_URL",DwpasCColumnInfo.CHART_TYPE_URL);
	request.setAttribute("CHART_TYPE_REPORT_URL",DwpasCColumnInfo.CHART_TYPE_REPORT_URL);
	//当前模块ID
	String moduleId=request.getParameter("moduleId");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改关联栏目--${moduleInfo.moduleName}</title>
<link type="text/css" rel="stylesheet" href="../css/main.css" />
<script type="text/javascript" src="<%=basePath%>/js/jquery-1.5.1.min.js"></script>
<!-- 弹窗JS -->
<script type="text/javascript" src="<%=basePath%>/js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>
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
<script type="text/javascript">
<!--
(function($){
	//插件
	$.extend($,{
		//命名空间
		sortTable:{
			sort:function(tableId,Idx){
				var table = document.getElementById(tableId);
				var tbody = table.tBodies[0];
				var tr = tbody.rows; 
		
				var trValue = new Array();
				for (var i=0; i<tr.length; i++ ) {
					trValue[i] = tr[i];  //将表格中各行的信息存储在新建的数组中
				}
		
				if (tbody.sortCol == Idx) {
					trValue.reverse(); //如果该列已经进行排序过了，则直接对其反序排列
				} else {
					//trValue.sort(compareTrs(Idx));  //进行排序
					trValue.sort(function(tr1, tr2){
						var value1 = tr1.cells[Idx].innerHTML;
						var value2 = tr2.cells[Idx].innerHTML;
						return value1.localeCompare(value2);
					});
				}
		
				var fragment = document.createDocumentFragment();  //新建一个代码片段，用于保存排序后的结果
				for (var i=0; i<trValue.length; i++ ) {
					fragment.appendChild(trValue[i]);
				}
		
				tbody.appendChild(fragment); //将排序的结果替换掉之前的值
				tbody.sortCol = Idx;
			}
		}
	});		  
})(jQuery);
//定义通用的函数交换两个结点的位置
function swapNode(node1, node2) {
	//获取父结点
	var _parent = node1.parentNode;
	//获取两个结点的相对位置
	var _t1 = node1.nextSibling;
	var _t2 = node2.nextSibling;
	//将node2插入到原来node1的位置
	if (_t1) _parent.insertBefore(node2, _t1);
	else _parent.appendChild(node2);
	//将node1插入到原来node2的位置
	if (_t2) _parent.insertBefore(node1, _t2);
	else _parent.appendChild(node1);
}
function sortColumn(configId,columnCount,sortType)
{
  //alert(configId);
  var table=document.getElementById("listTable");
  var rows = table.rows.length ;
  var crtTr=document.getElementById(configId);
  //
  var isUp=sortType<0?true:false;
  var configIds="";//已排序的ID
  if(isUp)
	{
	    //前移
	    if(crtTr.rowIndex<=1)
		{
			alert('已是第一个，不能前移');
			return;
		}else
		{
			
           //得到前一行的ID
           //alert(crtTr.cells[0].innerHTML);
           swapNode(crtTr,table.rows[crtTr.rowIndex-1]);//当前行，交换行  
           //重新生成序号
           for(var i=1;i<= table.rows.length;i++)
           {
        	   table.rows[i].cells[0].innerHTML=i;
        	   if(i==crtTr.rowIndex)
        	    {
        		    //alert(5);
        		   crtTr.style.backgroundColor='#D2E9FF';
        		}else
        	    {
        		   table.rows[i].style.backgroundColor='#f0f0f0';
        		}
           }
		}
	}else
	{
	   //后移
       if(crtTr.rowIndex>=rows-1)
		{
			alert('已是最后一个，不能后移');
			return;
		}else
		{
			swapNode(crtTr,table.rows[crtTr.rowIndex+1]);//当前行，交换行
	        //重新生成序号
	        for(var i=1;i<= table.rows.length;i++)
	        {
	            table.rows[i].cells[0].innerHTML=i;
	        	if(i==crtTr.rowIndex)
	        	{
	        		    //alert(5);
	        		crtTr.style.backgroundColor='#D2E9FF';
	            }else
	        	{
	        		table.rows[i].style.backgroundColor='#f0f0f0';
	            }	            
	        }			
		}
	}
    //alert(configIds);//修改顺序
}
//-->
</script>

<script type="text/javascript">
<!--
//栏目列
/*栏目ID，关联模块ID，栏目编码，栏目名称，显示名称，栏目大类，图的类型，图的类型描述，图的URL,关联通用指标，关联指标名称*/
function ColumnInfo(columnId,moduleId,columnCode,columnName,columnDisplayName,columnKind,chartType,chartTypeDesc,columnUrl,relCommKpiCodes,relCommKpiNames,remark,relKpiKind)
{
   this.columnId=columnId;
   this.moduleId=moduleId;
   //
   this.columnCode=columnCode;
   this.columnName=columnName;
   //
   this.columnDisplayName=columnDisplayName;
   this.columnKind=columnKind;
   //
   this.chartType=chartType;
   this.chartTypeDesc=chartTypeDesc;
   //
   this.columnUrl=columnUrl;
   //
   this.relCommKpiCodes=relCommKpiCodes;
   this.relCommKpiNames=relCommKpiNames;
   //
   this.remark=remark;
   this.relKpiKind=relKpiKind;//关联指标类型,0:通用指标,1:关联大盘指标
}
window.onload=function()
{
	//默认选中
	if(document.getElementById("column_Type_${moduleInfo.moduleType}")!=null)
	{
		document.getElementById("column_Type_${moduleInfo.moduleType}").checked=true;
	}
	if(dg!=null&&dg.curWin!=null)
	{
		if(dg.curWin.document.getElementById("moduleName_<%=moduleId%>")!=null)
		{
			document.getElementById("moduleName").value=dg.curWin.document.getElementById("moduleName_<%=moduleId%>").value;
			document.getElementById("moduleHeight").value=dg.curWin.document.getElementById("module_height_<%=moduleId%>").value;			
		}
	}
	//初始化数据
	<c:if test="${fn:length(columnInfoList)>0}">
	   <c:forEach items="${columnInfoList}" var="column" varStatus="sel">
		  var columnInfo=new ColumnInfo();
	          columnInfo.columnId="${column.columnId}";
			  columnInfo.moduleId="${column.moduleId}";
			  //
			  columnInfo.columnCode="${column.columnCode}";
			  columnInfo.columnName="${column.columnName}";
			  //
			  columnInfo.columnDisplayName="${column.columnDisplayName}";
			  columnInfo.columnKind="${column.columnKind}";
			  //
			  columnInfo.chartType="${column.chartType}";
			  columnInfo.chartTypeDesc="${column.chartTypeDesc}";
			  //
			  columnInfo.columnUrl="${column.columnUrl}";
			  //后台已经将指标和产品设置在一起
			  columnInfo.relCommKpiCodes="${column.relCommKpiCodes}";
			  columnInfo.relCommKpiNames="${column.relCommKpiNames}";
			  //
			  columnInfo.remark="${column.remark}";
             // columnInfo.relKpiKind="${column.relKpiKind}";//关联指标类型:0:通用指标,1:大盘指标
             columnInfo.relKpiKind="0";  //没有大盘，所以直接定义 0;
			  //addNewRow
			  //新增一行　${column.chartType}
			   <c:choose>
				   <c:when test="${column.chartType eq CHART_TYPE_URL}">
					  addNewURL(columnInfo);
				   </c:when>
				   <c:when test="${column.chartType eq CHART_TYPE_REPORT_URL}">
					   addNewReportURL(columnInfo);
				   </c:when>
				   <c:otherwise>
					   addNewRow(columnInfo);
				   </c:otherwise>
			   </c:choose>
	   </c:forEach>
    </c:if>
	<c:if test="${fn:length(columnInfoList)==0}">
		document.getElementById("column_Type_<%=DwpasCmoduleInfo.MODULE_TYPE_SELF%>").click();
	</c:if>

}
//增加新的一行URL
function addNewURL(columnConfig){
	  if(columnConfig==null) return;
	   //列数
	   var columnCount=parseInt(document.getElementById("columnCount").value);
	   //
	   var oTable = document.getElementById("listTable");
	   //新的一行
	   var oRow=oTable.insertRow(oTable.rows.length);
		   oRow.id="row_"+new Date().getTime();
		   oRow.style.width = "100%";
		   oRow.style.height = "30px";
	   var rownum = oTable.rows.length - 1;
	   //列
	   var oCell=null;
	   //序号
	   oCell=oRow.insertCell(0);
	   oCell.width="5%";
	   oCell.align="center";
	   oCell.innerHTML="<input type='hidden' name='columnlist["+columnCount+"].columnOrder' value='"+rownum+"'>"+rownum;

	   //栏目ID,关联模块ID,栏目编码，栏目名称/显示名称/栏目大类
	   oCell=oRow.insertCell(1);
	   oCell.width="25%";
	   oCell.align="left";
	   //栏目ID
	   var cellHTML="<input type='hidden' name='columnlist["+columnCount+"].columnId' value="+columnConfig.columnId+">";
	        //关联模块ID
	       cellHTML+="<input type='hidden' name='columnlist["+columnCount+"].moduleId' value="+columnConfig.moduleId+">";
	        //栏目编码
	       cellHTML+="<input type='hidden' name='columnlist["+columnCount+"].columnCode' value="+columnConfig.columnCode+">";
	        //栏目显示名称
	       cellHTML+="<input type='hidden' name='columnlist["+columnCount+"].columnDisplayName' value="+columnConfig.columnDisplayName+">";
	        //栏目大类
	       cellHTML+="<input type='hidden' name='columnlist["+columnCount+"].columnKind' value="+columnConfig.columnKind+">";
	        //栏目关联指标类型：0:通用指标,1:大盘指标
	       cellHTML+="<input type='hidden' name='columnlist["+columnCount+"].relKpiKind' value="+columnConfig.relKpiKind+" id='relKpiKind_"+columnCount+"'>";
	        //栏目名称
	   oCell.innerHTML="&nbsp;<input type='text' name='columnlist["+columnCount+"].columnName' value='"+columnConfig.columnName+"'  class='td_input' onfocus='this.select();'  max='255'>"+cellHTML;

	   //URL
	   oCell=oRow.insertCell(2);
	   //oCell.width="20%";
	   oCell.align="left";
	   //oCell.innerHTML="&nbsp;<input type='text' name='columnlist["+columnCount+"].columnUrl' value='"+columnConfig.columnUrl+"' class='td_input' style='width:75%;ime-mode:disabled;' max='255' onfocus='this.select();'/><label><font color='#1E90FF'>&nbsp;请输入URL</font></label>";
	   var tempHTML="&nbsp;<select id='columnlist["+columnCount+"].columnUrl' name='columnlist["+columnCount+"].columnUrl' style='width:75%;'><c:forEach items='${systemUrlList}' var='systemUrl'><option value='${systemUrl.systemUrl}'>${systemUrl.systemName}</option></c:forEach></select>";
	   oCell.innerHTML=tempHTML+"<label><font color='#1E90FF'>&nbsp;请选择URL</font></label>";
	   document.getElementById('columnlist['+columnCount+'].columnUrl').value=columnConfig.columnUrl;
	   

	   //图类型/图类型描述/图的URL
	   oCell=oRow.insertCell(3);
	   oCell.width="20%";
	   oCell.align="center";
	   //图类型
	   var chartType="<input type='hidden' name='columnlist["+columnCount+"].chartType' value='${CHART_TYPE_URL}'>";
	   //图的URL
	   //选择图类型
	   oCell.innerHTML=chartType+"&nbsp;无";

	   //操作
	   oCell=oRow.insertCell(4);
	   oCell.style.display='<c:if test="${moduleInfo.moduleType==0}">none</c:if>';//默认系统模块，不显示
	   oCell.width="8%";
	   oCell.align="center";
	   oCell.innerHTML="<label style='cursor:pointer;' onclick='delRow(\""+oRow.id+"\")'>删除</label>";
	   //
	   document.getElementById("columnCount").value=columnCount+1;
}
//选择报表类
function addNewReportURL(columnConfig){
	 if(columnConfig==null) return;
	   //列数
	   var columnCount=parseInt(document.getElementById("columnCount").value);
	   //
	   var oTable = document.getElementById("listTable");
	   //新的一行
	   var oRow=oTable.insertRow(oTable.rows.length);
		   oRow.id="row_"+new Date().getTime();
		   oRow.style.width = "100%";
		   oRow.style.height = "30px";
	   var rownum = oTable.rows.length - 1;
	   //列
	   var oCell=null;
	   //序号
	   oCell=oRow.insertCell(0);
	   oCell.width="5%";
	   oCell.align="center";
	   oCell.innerHTML="<input type='hidden' name='columnlist["+columnCount+"].columnOrder' value='"+rownum+"'>"+rownum;

	   //栏目ID,关联模块ID,栏目编码，栏目名称/显示名称/栏目大类
	   oCell=oRow.insertCell(1);
	   oCell.width="25%";
	   oCell.align="left";
	   //栏目ID
	   var cellHTML="<input type='hidden' name='columnlist["+columnCount+"].columnId' value="+columnConfig.columnId+">";
	        //关联模块ID
	       cellHTML+="<input type='hidden' name='columnlist["+columnCount+"].moduleId' value="+columnConfig.moduleId+">";
	        //栏目编码
	       cellHTML+="<input type='hidden' name='columnlist["+columnCount+"].columnCode' value="+columnConfig.columnCode+">";
	        //栏目显示名称
	       cellHTML+="<input type='hidden' name='columnlist["+columnCount+"].columnDisplayName' value="+columnConfig.columnDisplayName+">";
	        //栏目大类
	       cellHTML+="<input type='hidden' name='columnlist["+columnCount+"].columnKind' value="+columnConfig.columnKind+">";
	        //栏目关联指标类型：0:通用指标,1:大盘指标
	       cellHTML+="<input type='hidden' name='columnlist["+columnCount+"].relKpiKind' value="+columnConfig.relKpiKind+" id='relKpiKind_"+columnCount+"'>";
	        //栏目名称
	   oCell.innerHTML="&nbsp;<input type='text' name='columnlist["+columnCount+"].columnName' value='"+columnConfig.columnName+"'  class='td_input' onfocus='this.select();'>"+cellHTML;

	   //URL
	   oCell=oRow.insertCell(2);
	   //oCell.width="20%";
	   oCell.align="left";
	   var content = "&nbsp;<select name='columnlist["+columnCount+"].columnUrl' style='width:75%;' id='columnlist["+columnCount+"].columnUrl'>";
	   <c:forEach items="${reportlist}" var="report">
	         <c:if test="${report.isReport eq 0}">
	             content+="<optgroup label='${report.reportName}'>";
	             <c:forEach items="${childlist}" var="r">
					 <c:if test="${r.parentId eq report.reportId}">
						content+="<option value='/NewReport/previewReport.html?reportId=${r.reportDesId}' id='${r.reportName}' >${r.reportName}</option>";
					 </c:if>
	            </c:forEach>
	            content+="</optgroup>";
	         </c:if>
	   </c:forEach>
	   content+="</select><a id='selectReportBtn' href='javascript:selectReport("+columnCount+");'><font color='#1E90FF'>&nbsp;&nbsp;&nbsp;选择报表</font></a>";
	   oCell.innerHTML=content;
	   document.getElementById("columnlist["+columnCount+"].columnUrl").value=columnConfig.columnUrl;//赋值
	   
	   //图类型/图类型描述/图的URL
	   oCell=oRow.insertCell(3);
	   oCell.width="20%";
	   oCell.align="center";
	   //图类型
	   var chartType="<input type='hidden' name='columnlist["+columnCount+"].chartType' value='${CHART_TYPE_REPORT_URL}'>";
	   //图的URL
	   //选择图类型
	   oCell.innerHTML=chartType+"&nbsp;无";

	   //操作
	   oCell=oRow.insertCell(4);
	   oCell.style.display='<c:if test="${moduleInfo.moduleType==0}">none</c:if>';//默认系统模块，不显示
	   oCell.width="8%";
	   oCell.align="center";
	   oCell.innerHTML="<label style='cursor:pointer;' onclick='delRow(\""+oRow.id+"\")'>删除</label>";
	   //
	   document.getElementById("columnCount").value=columnCount+1;	
}
//选择指标类
function addNewRow(columnConfig)
{
   if(columnConfig==null) return;
   //列数
   var columnCount=parseInt(document.getElementById("columnCount").value);
   //
   var oTable = document.getElementById("listTable");
   //新的一行
   var oRow=oTable.insertRow(oTable.rows.length);
	   oRow.id="row_"+new Date().getTime();
	   oRow.style.width = "100%";
	   oRow.style.height = "30px";
	   //oRow.onmouseover=function(){this.style.backgroundColor='#FFFFFF'};
	   //oRow.onmouseout=function(){this.style.backgroundColor='#FFFFFF'};
   var rownum = oTable.rows.length - 1;
   //列
   var oCell=null;
   //序号
   oCell=oRow.insertCell(0);
   oCell.width="5%";
   oCell.align="center";
   oCell.innerHTML=rownum;

   //栏目ID,关联模块ID,栏目编码，栏目名称/显示名称/栏目大类
   oCell=oRow.insertCell(1);
   oCell.width="25%";
   oCell.align="left";
   //栏目ID
   var cellHTML="<input type='hidden' name='columnlist["+columnCount+"].columnId' value="+columnConfig.columnId+">";
       cellHTML+="<input type='hidden' name='columnlist["+columnCount+"].columnOrder' value='"+rownum+"' id='SEQ_"+columnCount+"'>";
        //关联模块ID
       cellHTML+="<input type='hidden' name='columnlist["+columnCount+"].moduleId' value="+columnConfig.moduleId+">";
        //栏目编码
       cellHTML+="<input type='hidden' name='columnlist["+columnCount+"].columnCode' value="+columnConfig.columnCode+">";
        //栏目显示名称
       cellHTML+="<input type='hidden' name='columnlist["+columnCount+"].columnDisplayName' value="+columnConfig.columnDisplayName+">";
        //栏目大类
       cellHTML+="<input type='hidden' name='columnlist["+columnCount+"].columnKind' value="+columnConfig.columnKind+">";
	    //栏目关联指标类型：0:通用指标,1:大盘指标
	   cellHTML+="<input type='hidden' name='columnlist["+columnCount+"].relKpiKind' value="+columnConfig.relKpiKind+" id='relKpiKind_"+columnCount+"'>";
        //栏目名称
   oCell.innerHTML="&nbsp;<input type='text' name='columnlist["+columnCount+"].columnName' value='"+columnConfig.columnName+"'  class='td_input' onfocus='this.select();'>"+cellHTML;

   //关联指标
   oCell=oRow.insertCell(2);
   //oCell.width="20%";
   oCell.align="left";
   oCell.innerHTML="&nbsp;<input type='hidden' name='columnlist["+columnCount+"].relCommKpiCodes' value='"+columnConfig.relCommKpiCodes+"' readonly  class='td_label' id='relCommKpiCodes_"+columnCount+"' style='width:90%;' onchange='alert(3);'>&nbsp;<input type='hidden' name='columnlist["+columnCount+"].remark' value='"+columnConfig.remark+"' readonly  class='td_label'><input type='hidden' name='columnlist["+columnCount+"].relCommKpiNames' value='"+columnConfig.relCommKpiNames+"' readonly  class='td_label' id='relCommKpiNames_"+columnCount+"' style='width:90%;'><select name='relCommKpiOpt_"+columnCount+"' style='width:70%;' id='relCommKpiOpt_"+columnCount+"'></select>&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:void(0);' onclick='addCommKpiCode("+columnCount+");'>配置</a>&nbsp;&nbsp;<a href='javascript:void(0);' onclick='delCommKpiCode("+columnCount+");'>删除</a>";
   //oCell.title=document.getElementById("relCommKpiCodes_"+columnCount).value;

   //增加下拉项
   var relComKpiCodes=columnConfig.relCommKpiCodes;
   if(relComKpiCodes!=null&&relComKpiCodes.length>0)
	{
	   var relCommKpiOpt=document.getElementById("relCommKpiOpt_"+columnCount);
	   //
	   var relComKpiNameArray=columnConfig.relCommKpiNames.split(",");
       var relComKpiCodeArray=relComKpiCodes.split(",");
	   for(var i=0;i<relComKpiCodeArray.length;i++)
		{
		   if(columnConfig.relKpiKind=="1")
			{
               relCommKpiOpt.options.add(new Option("* "+relComKpiNameArray[i],relComKpiCodeArray[i]));
			}else
			{
               relCommKpiOpt.options.add(new Option(relComKpiNameArray[i],relComKpiCodeArray[i]));
			}
		}
	}

   //图类型/图类型描述/图的URL
   oCell=oRow.insertCell(3);
   oCell.width="20%";
   oCell.align="left";
   oCell.valign='bottom';
   oCell.style.display='<c:if test="${moduleInfo.moduleType==0}">none</c:if>';//默认系统模块，不显示
   //图类型
   var chartType="<input type='hidden' name='columnlist["+columnCount+"].chartType' value='"+columnConfig.chartType+"'>";
   //图的URL
   var columnUrl="<input type='hidden' name='columnlist["+columnCount+"].columnUrl' value='"+columnConfig.columnUrl+"'>";
   //选择图类型
   oCell.innerHTML="&nbsp;<img src='<%=path%>/images/chart/pie.png' border='0' id='graphic_"+columnCount+"'>"+chartType+columnUrl+"&nbsp;<select read name='columnlist["+columnCount+"].chartTypeDesc' id='chartTypeDesc_"+columnCount+"' style='width:80%;'></select>";

   //非系统模块
   <c:if test="${moduleInfo.moduleType!=0}">
	   var chartTypeDesc=columnConfig.chartTypeDesc;
	   if(chartTypeDesc!=null&&chartTypeDesc.length>0)
		{
		   var graphTypeArry=chartTypeDesc.split(";");
		   //
		   var graphValues="";
		   var graphNames="";
		   for(var i=0;i<graphTypeArry.length; i++){
			   var name="";
			   if(graphTypeArry[i]=="<%=DwpasCColumnInfo.CHART_TYPE_DESC_STOCK%>"){
					 name="趋势图";
					 document.getElementById("graphic_"+columnCount).src="<%=path%>/images/chart/stocked.png";
				 }else if(graphTypeArry[i]=="<%=DwpasCColumnInfo.CHART_TYPE_DESC_PIE%>"){
					 name="饼图";
					 document.getElementById("graphic_"+columnCount).src="<%=path%>/images/chart/pie.png";
				 }else if(graphTypeArry[i]=="<%=DwpasCColumnInfo.CHART_TYPE_DESC_BAR%>"){
					 name="条形图";
					 document.getElementById("graphic_"+columnCount).src="<%=path%>/images/chart/bar.png";
				 }else if(graphTypeArry[i]=="<%=DwpasCColumnInfo.CHART_TYPE_DESC_AREA%>"){
					 name="面积图";
					 document.getElementById("graphic_"+columnCount).src="<%=path%>/images/chart/area.png";
				 }else if(graphTypeArry[i]=="<%=DwpasCColumnInfo.CHART_TYPE_DESC_LINE%>"){
					 name="折线图";
					 document.getElementById("graphic_"+columnCount).src="<%=path%>/images/chart/line.png";
				 }else if(graphTypeArry[i]=="<%=DwpasCColumnInfo.CHART_TYPE_DESC_COLUMN%>"){
					 name="矩形图";
					 document.getElementById("graphic_"+columnCount).src="<%=path%>/images/chart/column.png";
				 }
			   if(i==0){ 
				   graphValues=graphTypeArry[i];
				   graphNames= name;
				   }else{
					   graphValues+=";"+graphTypeArry[i];
					   graphNames+="+"+name;
					   document.getElementById("graphic_"+columnCount).src="<%=path%>/images/chart/column_line.jpg";
			   }
			   
			   
		   }
		   document.getElementById("chartTypeDesc_"+columnCount).options.add(new Option(graphNames,graphValues));
           //initChartType(document.getElementById("chartTypeDesc_"+columnCount),relComKpiCodeArray.length,columnConfig.chartTypeDesc,columnConfig.relKpiKind);
		}
	</c:if>

   //操作
   <c:if test="${moduleInfo.moduleType!=0}">
	   oCell=oRow.insertCell(4);
	   oCell.style.display='<c:if test="${moduleInfo.moduleType==0}">none</c:if>';//默认系统模块，不显示
	   oCell.width="8%";
	   oCell.align="center";
	   oCell.innerHTML="<label style='cursor:pointer;' onclick='delRow(\""+oRow.id+"\")'>删除</label>|<a	 href='javascript:sortColumn(\""+oRow.id+"\","+columnCount+",-1);'><img src='<%=basePath%>/images/pic15.gif' title='前移' border='0'></a>|<a href='javascript:sortColumn(\""+oRow.id+"\","+columnCount+",1);'><img src='<%=basePath%>/images/pic8.gif' title='后移' border='0'></a>";
   </c:if>
   <c:if test="${moduleInfo.moduleType==0}">
	   oCell=oRow.insertCell(4);
	   oCell.width="20%";
	   oCell.align="left";
	   oCell.innerHTML=columnConfig.remark;
   </c:if>
   //
   document.getElementById("columnCount").value=columnCount+1;
}
//删除一行
function delRow(rowId)
{
  document.getElementById(rowId).parentNode.removeChild(document.getElementById(rowId));
}
//增加新的一行
function addNewEmptyRow(moduleType,sign)
{  
  /*
  var e = window.event||e;
  var srcElement = e.srcElement||e.target; 
  var moduleType=srcElement.value;
  */
  //暂只能新增一个系统栏目
  var oTable = document.getElementById("listTable");
  var crtModuleType=document.getElementById("moduleType").value;
  if(moduleType==<%=DwpasCmoduleInfo.MODULE_TYPE_SYSTEM%>)
  {
	 if(oTable.rows.length>=2)
	 {
		 if(!confirm("暂只支持一个系统栏目!\n确定清空所有已定义栏目"))
		 {
             document.getElementById("column_Type_"+crtModuleType).checked=true;
			 return;
		 }
	 }
	  //清空所有的栏目
	 for(var i=oTable.rows.length;i>=1;i--) 
	 {
		if(oTable.rows[i]==null) continue;
		document.getElementById(oTable.rows[i].id).parentNode.removeChild(oTable.rows[i]);
	 }
	 //系统栏目
	 document.getElementById("moduleType").value="<%=DwpasCmoduleInfo.MODULE_TYPE_SYSTEM%>";
  }else if(moduleType==<%=DwpasCmoduleInfo.MODULE_TYPE_REPORT%>)
  {
	 if(oTable.rows.length>=2)
	 {
		 if(!confirm("暂只支持一个报表栏目!\n确定清空所有已定义栏目"))
		 {
			 document.getElementById("column_Type_"+crtModuleType).checked=true;
			 return;
		 }
	 }
	 //清空所有的栏目
	 for(var i=oTable.rows.length;i>=1;i--) 
	 {
		if(oTable.rows[i]==null) continue;
		document.getElementById(oTable.rows[i].id).parentNode.removeChild(oTable.rows[i]);
	 }
     //报表栏目
	 document.getElementById("moduleType").value="<%=DwpasCmoduleInfo.MODULE_TYPE_REPORT%>";
  }else
  {
	  if(oTable.rows.length > 4){
		  alert("最多只能增加四个栏目");
		  return;
	  }
	  //删除非图表栏目
	  if(document.getElementById("moduleType").value!=<%=DwpasCmoduleInfo.MODULE_TYPE_SELF%>)
	  {
		 if(oTable.rows.length>=2)
		 {
			 if(!confirm("暂不支持不同类型栏目组合!\n确定清空所有已定义栏目"))
			 {
				 document.getElementById("column_Type_"+crtModuleType).checked=true;
				 return;
			 }
		 }
		  //清空所有的栏目
		 for(var i=oTable.rows.length;i>=1;i--) 
		 {
			if(oTable.rows[i]==null) continue;
			document.getElementById(oTable.rows[i].id).parentNode.removeChild(oTable.rows[i]);
		 }
	  }
	  //图表栏目
	  document.getElementById("moduleType").value="<%=DwpasCmoduleInfo.MODULE_TYPE_SELF%>";
  }
  //图表栏目
  //暂只能新增一个报表栏目
  //新栏目
  var columnInfo=new ColumnInfo();
	  columnInfo.columnId="";
	  columnInfo.moduleId="${moduleInfo.moduleId}";
	  //
	  columnInfo.columnCode="";
	  if(moduleType==<%=DwpasCmoduleInfo.MODULE_TYPE_SYSTEM%>)
	  {
		//手工输入URL
	    columnInfo.columnName="系统栏目_"+document.getElementById("columnCount").value;
		columnInfo.columnUrl="";
	  }else if(moduleType==<%=DwpasCmoduleInfo.MODULE_TYPE_REPORT%>)
	  {
		//报表
	    columnInfo.columnName="报表栏目_"+document.getElementById("columnCount").value;
	  　//
	  　columnInfo.columnUrl="/selfReport/previewReport.html";
	  }else
	  {
		//图表URL
	    columnInfo.columnName="图表栏目_"+document.getElementById("columnCount").value;
	  　//
	  　columnInfo.columnUrl="/homePage/showChartPage.html";
	  }
	  //
	  columnInfo.columnDisplayName="";
	  columnInfo.columnKind="";
	  //
	  columnInfo.chartType="0";
	  columnInfo.chartTypeDesc="stock";
	  columnInfo.relCommKpiCodes="";
	  columnInfo.relCommKpiNames="";
	  //
	  columnInfo.remark="";
	  columnInfo.relKpiKind="0";//关联指标类型:0:通用指标,1:大盘指标
	 //sign作为标识符  a:增加栏目   b:增加URL,c:增加报表
     //新增空行
     if(sign!=null&&parseInt(sign)>0){
    	  if(sign == ${CHART_TYPE_URL}){
    		  addNewURL(columnInfo);
    	  }else if(sign == ${CHART_TYPE_REPORT_URL}){
    		  addNewReportURL(columnInfo);
    	  }
     }else{
    	 addNewRow(columnInfo);
     }
}
//初始化图的类型
/*
chartTypeOpt：下拉选项控件
kpiCodeCount：已选指标个数
defaultVal：默认值
relKpiKind：关联指标类型
*/
function initChartType(chartTypeOpt,kpiCodeCount,defaultVal,relKpiKind)
{
	if(chartTypeOpt==null) return;
	//先清空数据
	chartTypeOpt.length=0;
	//趋势图
	chartTypeOpt.options.add(new Option("趋势图","<%=DwpasCColumnInfo.CHART_TYPE_DESC_STOCK%>"));
	if(kpiCodeCount>1)
	{
	  //饼图
	  chartTypeOpt.options.add(new Option("饼图","<%=DwpasCColumnInfo.CHART_TYPE_DESC_PIE%>"));
	}
	//条型图
	chartTypeOpt.options.add(new Option("条型图","<%=DwpasCColumnInfo.CHART_TYPE_DESC_BAR%>"));
	//面积图
	chartTypeOpt.options.add(new Option("面积图","<%=DwpasCColumnInfo.CHART_TYPE_DESC_AREA%>"));
	//全是矩形
	var columnChart="";
	for(var j=1;j<=kpiCodeCount;j++)
	{
	      columnChart+="<%=DwpasCColumnInfo.CHART_TYPE_DESC_COLUMN%>;";//column
	}
	if(kpiCodeCount>1)
	{
	   chartTypeOpt.options.add(new Option(kpiCodeCount+"矩形",columnChart));
	}else
	{
	   chartTypeOpt.options.add(new Option("矩形",columnChart));
	}
	//如果是指定指标（大盘指标）,因为通用指标，不确认关联指标个数，暂不支持矩形+折线
	if(relKpiKind==1)
	{
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
		   chartTypeOpt.options.add(new Option(i+"矩形"+" + "+(kpiCodeCount-i)+"折线",columnChart+lineChart));
		   //chartTypeOpt.add(new Option(columnChart+" + "+lineChart,""));
		}
	}
	//全是折线
	var lineChart="";
	for(var j=1;j<=kpiCodeCount;j++)
	{
		  lineChart+="<%=DwpasCColumnInfo.CHART_TYPE_DESC_LINE%>;";//line
	}
	if(kpiCodeCount>1)
	{
	   chartTypeOpt.options.add(new Option(kpiCodeCount+"折线",lineChart));
	}else
	{
	   chartTypeOpt.options.add(new Option("折线",lineChart));	    
	}
	//设置默认值
	if(defaultVal!=null&&defaultVal.length>0)
	{
	  chartTypeOpt.value=defaultVal;
	}
}
//改变位置
function changePosition(crtObj)
{
	<c:if test="${not empty moduleInfo.moduleId}">
		crtObj.value="${moduleInfo.position}";
	    return;
    </c:if>
	var crtVal=crtObj.value;
	if(crtVal==<%=DwpasCmoduleInfo.POSITION_LEFT%>)
	{
	   //修改坐标XY位置
       document.getElementById("module_positionX_${moduleInfo.moduleId}").value="227";
	   document.getElementById("module_positionY_${moduleInfo.moduleId}").value="999";
	   //修改宽度
	   document.getElementById("module_width_${moduleInfo.moduleId}").value="540";//
	}else if(crtVal==<%=DwpasCmoduleInfo.POSITION_RIGHT%>)
	{
       document.getElementById("module_positionX_${moduleInfo.moduleId}").value="776";
	   document.getElementById("module_positionY_${moduleInfo.moduleId}").value="999";
	   document.getElementById("module_width_${moduleInfo.moduleId}").value="360";//
	}else 
	{
       document.getElementById("module_positionX_${moduleInfo.moduleId}").value="227";
	   document.getElementById("module_positionY_${moduleInfo.moduleId}").value="999";
	   document.getElementById("module_width_${moduleInfo.moduleId}").value="900";//
	}
}

// 选择报表
function selectReport(columnCount) {
	//alert(document.getElementById("columnlist[1].columnUrl").value);//columnlist[1].columnUrl
	columnListId = columnCount;
	var url = '<%=basePath%>/NewReport/selectChildReport.html?reportId=${reportId}';
	var left = $("#selectReportBtn")[0].offsetLeft + 100;
	var selectRptDg = new $.dialog({
		title : '选择子报表',
		id : 'selectChildReport_${reportId}',
		width : 250,
		height : 400,
		left : left,
		top:0, 
		iconTitle : false,
		cover : true,
		maxBtn : true,
		xButton : true,
		resize : true,
		page : url
	});
	selectRptDg.ShowDialog();
}
// 获取报表参数
var columnListId;
function getReportParameters(id, name) {
	var id = "columnlist[" + columnListId + "].columnUrl";
	document.getElementById(id).value = $("select[id="+id+"] option[id='"+name+"']").val();
}
//-->
</script>
</head>
<body scroll="no">
	<form action="<%=basePath%>/designTemplate/saveColumnConfig.html" name="indexMenuForm" id="indexMenuForm" target="result" method="post">
		<!--如果是系统栏目(比如产品全图，预警图等)，则不能增加栏目-->
		<c:if test="${moduleInfo.moduleType!=0}">
			<div style="text-align:left;">
			&nbsp;&nbsp;
			
			<!-- 增加图表栏目 -->
			<input type='radio' name="addColumnBtn" value="<%=DwpasCmoduleInfo.MODULE_TYPE_SELF%>" id="column_Type_<%=DwpasCmoduleInfo.MODULE_TYPE_SELF%>" onclick="addNewEmptyRow(this.value,null);"><label style='cursor:pointer;FONT-SIZE:14px;BORDER-RIGHT: #000000 2px solid; BORDER-TOP: #FFFFFF 2px solid; BORDER-LEFT: #FFFFFF 2px solid; BORDER-BOTTOM: #000000 2px solid; BACKGROUND-COLOR: #d8d8d0;' for="column_Type_<%=DwpasCmoduleInfo.MODULE_TYPE_SELF%>" title='新增自定义的图表栏目'>新增图表栏目</label>
			<!-- 增加系统栏目 -->
			<input type="radio" name="addColumnBtn" value="<%=DwpasCmoduleInfo.MODULE_TYPE_SYSTEM%>" id="column_Type_<%=DwpasCmoduleInfo.MODULE_TYPE_SYSTEM%>" onclick="addNewEmptyRow(this.value,${CHART_TYPE_URL});"/><label style='color:green;cursor:pointer;FONT-SIZE:14px;BORDER-RIGHT: #000000 2px solid; BORDER-TOP: #FFFFFF 2px solid; BORDER-LEFT: #FFFFFF 2px solid;BORDER-BOTTOM: #000000 2px solid; BACKGROUND-COLOR: #d8d8d0;' for="column_Type_<%=DwpasCmoduleInfo.MODULE_TYPE_SYSTEM%>" title='新增指定系统的栏目'>新增系统栏目</label>
			<!-- 增加报表栏目 -->
			<input type="radio" name="addColumnBtn" value="<%=DwpasCmoduleInfo.MODULE_TYPE_REPORT%>" id="column_Type_<%=DwpasCmoduleInfo.MODULE_TYPE_REPORT%>" onclick="addNewEmptyRow(this.value,${CHART_TYPE_REPORT_URL});"/><label style='color:blue;cursor:pointer;FONT-SIZE:14px;BORDER-RIGHT: #000000 2px solid; BORDER-TOP: #FFFFFF 2px solid; BORDER-LEFT: #FFFFFF 2px solid;BORDER-BOTTOM: #000000 2px solid; BACKGROUND-COLOR: #d8d8d0;' for="column_Type_<%=DwpasCmoduleInfo.MODULE_TYPE_REPORT%>" title='新增选定的报表栏目'>新增报表栏目</label>
			</div>
			<div style="position: absolute;right:10px;top:5px;">
			   &nbsp;&nbsp;注：<font color='red'>如果自定义模块有多个栏目，前台用<b>Tab</b>进行切换显示</font>
			</div>
		</c:if>
		<div style="position: absolute;left:10px;bottom:5px;">
			   &nbsp;&nbsp;注：<b>新增栏目，请点击“新增图表栏目”/“新增系统栏目”/“新增报表栏目”。</b>
		</div>
		<!--关联菜单ID-->
		<input type="hidden" name="menuId" value="${moduleInfo.menuId}"/>
		<!--日期类型-->
		<input type="hidden" name="dateType" value="${moduleInfo.dateType}"/>
		<!--模块ID-->
		<input type="hidden" name="moduleId" value="${moduleInfo.moduleId}"/>
		<!--模块编码-->
		<input type="hidden" name="moduleCode" value="${moduleInfo.moduleCode}"/>
		<!--栏目个数-->
		<input type="hidden" value="0" name="columnCount" id="columnCount">
		<!--模块类型-->
		<input type="hidden" name="moduleType" value="${moduleInfo.moduleType}" id="moduleType">
		<!--坐标-->
        <input type="hidden" name="positionX" value="${moduleInfo.positionX}" id="module_positionX_${moduleInfo.moduleId}">
		<input type="hidden" name="positionY" value="${moduleInfo.positionY}" id="module_positionY_${moduleInfo.moduleId}">
		<!--宽度-->
		<input type="hidden" name="width" value="${moduleInfo.width}" id="module_width_${moduleInfo.moduleId}">
		<input type="hidden" name="moduleSort" value="${moduleInfo.moduleSort}" id="module_sort_${moduleInfo.moduleId}">
		<!--是否显示-->
		<input type="hidden" value="${moduleInfo.isShow}" name="isShow" id="module_is_show">
		<!--下面的对新增的有效-->
		<fieldset style='BACKGROUND-COLOR: #f0f0f0;'>
			<legend>
				<b>模块信息</b>
			</legend>
			<div>
			  <table width='100%' border='0' cellpadding='0' cellspacing='3'>
			     <tr>
				   <td width="12%" align='right'>模块名称：</td>
				   <td align='left'><input type="text" name="moduleName" value="${moduleInfo.moduleName}" onfocus='this.select();' id="moduleName" style='width:99%;'></td>
				   <td width="12%" align='right'>模块高度：</td>
				   <td width="12%" align='left'><input type="text" name="height" value="${moduleInfo.height}" onfocus='this.select();' onkeyup="value=value.replace(/[^\d]/g,'')" id="moduleHeight" style='ime-mode:disabled;width:99%;'></td>
				   <!--如果非新增，则不能修改-->
				   <td width="12%" align='right'>显示位置：</td>
				   <td align='left' width="12%">
                     <select name="position" style="width:99%;" onchange="changePosition(this);">
					    <!--
					    <option value="<%=DwpasCmoduleInfo.POSITION_TOP%>">顶部</option>
						-->
					    <option value="<%=DwpasCmoduleInfo.POSITION_LEFT%>" style="color:#000000;" <c:if test="${moduleInfo.position eq POSITION_LEFT}">selected</c:if>>左边</option>
						<option value="<%=DwpasCmoduleInfo.POSITION_RIGHT%>" style="color:blue;" <c:if test="${moduleInfo.position eq POSITION_RIGHT}">selected</c:if>>右边</option>
						<option value="<%=DwpasCmoduleInfo.POSITION_BOTTOM%>" style="color:red;" <c:if test="${moduleInfo.position eq POSITION_BOTTOM}">selected</c:if>>底边</option>
					 </select>
				   </td>
				   <c:choose>
				   <c:when test="${moduleInfo.moduleType eq 0}">
				   <td width="12%" align='right'>&nbsp;</td>
				  <td align='left' width="12%">
                     &nbsp;
				   </td>
				   </c:when>
				   <c:otherwise>
				   <td width="12%" align='right'>显示方式：</td>
				  <td align='left' width="12%">
                     <select name="tabShow" style="width:99%;">
					    <option value="0"  <c:if test="${moduleInfo.tabShow eq 0}">selected</c:if>>TAB切换</option>
					    <option value="1"  <c:if test="${moduleInfo.tabShow eq 1}">selected</c:if>>并排显示</option>
					 </select>
				   </td>
				   </c:otherwise>
				   </c:choose>
				  
				 </tr>
			     <tr>
				   <td align='right'>模块说明：</td>
				   <td colspan="7" align='left'>
				     <textarea style="width:99%;height:30px;" name="remark" <c:if test="${not empty moduleInfo.moduleId}"></c:if>>${moduleInfo.remark}</textarea>
				   </td>
				 </tr>
			  </table>
			</div>
        </fieldset>
		<fieldset style='BACKGROUND-COLOR: #f0f0f0;'>
			<legend>
				<b>Tab页面（栏目）</b>
			</legend>
			<div style="width:100%; height:220px;border:0px;" id="reportColumnDiv">
				<table width='100%' border='1' cellpadding='0' cellspacing='0' class="box" id="listTable" name="listTable">
					<tr style='background: #ECF9FF; font-weight: bold;' id="reportColumnRow">
						<td width="5%" align="center" class="query_list_meta_td"
							style="height: 23px">序</td>
						<td width="25%" align="center" snowrap nowrap
							class="query_list_meta_td">栏目名称</td>
						<td snowrap nowrap align="center"
							class="query_list_meta_td">关联指标/URL</td>
						<!--系统模块不显示-->
						<c:if test="${moduleInfo.moduleType!=0}">
							<td width="20%" align="center" snowrap nowrap
								class="query_list_meta_td">图表类型</td>
							<td width="8%" align="center" class="query_list_meta_td">删除
						</td>	
						</c:if>
						<!--系统模块则显示-->
						<c:if test="${moduleInfo.moduleType==0}">
							<td width="20%" align="center" class="query_list_meta_td">备注</td>
						</c:if>
					</tr>
				</table>
			</div>
		</fieldset>
	</form>
	<iframe name="result" id="result" src="about:blank" frameborder="0"
		width="0" height="0"></iframe>
</body>
</html>
<script type="text/javascript">
<!--
	var dg;
	$(document).ready(function(){
		dg = frameElement.lhgDG;
		if(dg!=null)
		{
			dg.addBtn('ok','保存',function(){
				var oTable = document.getElementById("listTable");
				if(oTable.rows.length<=1)
				{
					alert("当前模块未关联任何栏目");
					reeturn;
				}
				var moduleNameVal=document.getElementById("moduleName").value;
				if(moduleNameVal==null||moduleNameVal.length==0)
				{
					alert("模块名不能为空");
                    return;
				}
				var moduleHeight=document.getElementById("moduleHeight").value;
				if(moduleHeight==null||moduleHeight.length==0||parseInt(moduleHeight)<=0)
				{
					alert("高度必须大于零");
					//return;
				}
				var rowNo=0;//行数
                var columnCount=parseInt(document.getElementById("columnCount").value);
				var isOk=true;
				for(var i=0;i<columnCount;i++)
				{
					var relCommKpiCodes=document.getElementById("relCommKpiCodes_"+i);
					if(relCommKpiCodes!=null)
					{
                       rowNo=rowNo+1;
					   if(relCommKpiCodes.value==null||relCommKpiCodes.value.length==0)
						{
						   alert('第'+rowNo+'行关联指标不能为空');
						   isOk=false;
						   break;
						}
					   var orderSeqObj=document.getElementById("SEQ_"+i);
					   //alert(orderSeqObj.parentNode.parentNode.rowIndex);
					   if(orderSeqObj!=null) orderSeqObj.value=orderSeqObj.parentNode.parentNode.rowIndex;
					   //alert(orderSeqObj.value);
					}
				}
				if(!isOk) return;
				if(!confirm("确认保存")) return;
				$("#indexMenuForm").submit();
			});
		}
	});
	//选择指标
	function addCommKpiCode(id){
		var relCommKpiOpt=document.getElementById("relCommKpiOpt_"+id);
		var selectedComKpiCodes=document.getElementById("relCommKpiCodes_"+id).value;
		var graphValues = document.getElementById("chartTypeDesc_"+id).value;
		/*
		if(relCommKpiOpt!=null)
		{
			for(var i=0;i<relCommKpiOpt.options.length;i++)
			{
              if(selectedComKpiCodes.length>0) selectedComKpiCodes+=",";
			  selectedComKpiCodes+=relCommKpiOpt.options[i].value;
			}
		}
		*/
		var dateType="${moduleInfo.dateType}";
		var kpiType="<%=DwpasCKpiInfo.KPI_TYPE_OF_DAY%>";
		if(dateType.toUpperCase()=="M")
		{
			kpiType="<%=DwpasCKpiInfo.KPI_TYPE_OF_MONTH%>";
		}
		//关联指标类型：0:关联产品(通用指标),1:非关联产品(大盘指标)
		var relKpiKind=document.getElementById("relKpiKind_"+id).value;
		if(relKpiKind==null||relKpiKind.length==0||typeof(relKpiKind)=="undefined") relKpiKind="0";
		relKpiKind = "0"; //后来修改的，指标都为通用指标
		var page='<%=basePath%>/designTemplate/showChoiceKpi.html?selectedKpi='+selectedComKpiCodes+'&kpiType='+kpiType+'&relKpiKind='+relKpiKind+'&columnTmpId='+id+'&graphValues='+graphValues+'&moduleCode=${moduleCode}';
		var dg = new $.dialog({
			title:'选择通用指标—产品',
			id:'show_commoncode',
			width:650,
			height:380,
			iconTitle:false,
			cover:true,
			maxBtn:false,
			xButton:true,
			resize:true,
		    top:0,
			page:page
			});
		dg.ShowDialog();
	}
    Array.prototype.indexOf = function(val) {
            for (var i = 0; i < this.length; i++) {
                if (this[i] == val) return i;
            }
            return -1;
        };
	Array.prototype.remove = function(val) {
		var index = this.indexOf(val);
		if (index > -1) {
			this.splice(index, 1);
		}
	};
	//删除指标
	function delCommKpiCode(id)
	{
       var relCommKpiOpt=document.getElementById("relCommKpiOpt_"+id);
	   if(relCommKpiOpt==null||relCommKpiOpt.length==0) return;
	   var relCommKpiCodes=document.getElementById("relCommKpiCodes_"+id);
	   //当前选中值
	   var crtCommKpiCode=relCommKpiOpt.value;
       //删除选择项
	   relCommKpiOpt.remove(relCommKpiOpt.selectedIndex);
	   //如果全部删除了
	   if(relCommKpiOpt.options.length==0)
		{
		   document.getElementById("chartTypeDesc_"+id).length=0;//清空数据
		   relCommKpiCodes.value="";
		   return;
		}
        var comKpiCodeArray=relCommKpiCodes.value.split(",");
        //删除当前选中值
        comKpiCodeArray.remove(crtCommKpiCode);
		document.getElementById("relCommKpiCodes_"+id).value=comKpiCodeArray.toString();
		//关联指标类型
		var relKpiKind=document.getElementById("relCommKpiOpt_"+id).value;
	    //初始化图表项
	    //initChartType(document.getElementById("chartTypeDesc_"+id),comKpiCodeArray.length,null,relKpiKind);
	}
    //保存成功
	function success(){
	   alert('保存成功');
	   //刷新父页面
	   dg.curWin.location.reload();
	   //关闭窗口
	   dg.cancel();
	}
	//保存失败
	function failed(){
	   alert('保存失败');
	}
//-->
</script>