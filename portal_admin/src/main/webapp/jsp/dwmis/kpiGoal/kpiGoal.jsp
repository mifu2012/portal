<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<title>KPI信息</title>
<link type="text/css" rel="stylesheet" href="<%=path%>/css/main.css" />
<link type="text/css" rel="stylesheet" href="<%=path%>/css/dwmis.css" />
</head>
<body>
	<form id="fmInfo" name="fmInfo" class="rela-wrapper" action="save"
		method="post" target="result">
		<!-- #set($f = $formManager.newForm($request))
	#addFormToken($f) -->
		<input type="hidden" name="actionType" value="${actionType} " /> <input
			type="hidden" name="goalType" value="${goalType }" /> <input
			type="hidden" name="kpiName" value="${kpiInfo.kpiName }" /> <input
			type="hidden" name="kpiCode" value="${kpiInfo.kpiCode }" /> <input
			name="decemberValue" id="nianchuzhi" type="hidden"
			value="${decemberValue }" />
		<div class="goal-data">
			<label>指标名称：${kpiInfo.kpiName }</label> &nbsp;&nbsp;&nbsp; <label>指标code：${kpiInfo.kpiCode}</label>
			<c:if test="${not empty kpiInfo.kpiCode}">
				<!--  #if(!$stringUtil.equals("$!kpiInfo.kpiCode","")) -->
<%-- 				<c:choose> --%>
<%-- 					<c:when test="${goalType != 'YEAR'}"> --%>
<!-- 						<span class="ui-round-btn ui-round-btn-mini"><input -->
<!-- 							class="ui-round-btn-text" id="btnFenJie" name="pingjunfenjie" -->
<!-- 								type="button" value="平均分解" onclick="toAverage();" /></span>					 -->
<%-- 					</c:when> --%>
<%-- 					<c:otherwise> --%>
						<span class="ui-round-btn ui-round-btn-mini"><input
						class="ui-round-btn-text" id="btnFenJie" name="pingjunfenjie"
						type="button" value="新增一行" onclick="addRow();" /></span>				
<%-- 					</c:otherwise> --%>
<%-- 				</c:choose> --%>
			</c:if>
		</div>
		<div class="goal-data">
			<label></label>
<%-- 			<c:if test="${goalType != 'YEAR'}"> --%>
<!-- 				<label for="fen35">3.5分：<input style="ime-mode: Disabled" -->
<!-- 					name="fen35" id="fen35" -->
<!-- 					class="ui-input ui-input-mini ui-input-len8" type="text" -->
<%-- 					value="${goalLowValue}" --%>
<%-- 				onkeypress="var k=event.keyCode; return (k>=48&&k<=57||k==46)" --%> 
<!-- 					onkeypress="return checkNum(event);"	 -->
<!-- 					onkeyup="return value=value.replace(/^(\d+\.\d{1,2})(.*)?$/,'$1')" -->
<!-- 					onpaste="return !clipboardData.getData('text').match(/\D/)" -->
<!-- 					ondragenter="return false" style="ime-mode:Disabled" /></label> -->
<!-- 				<label for="fen5">5分：<input style="ime-mode: Disabled" -->
<!-- 					name="fen5" id="fen5" class="ui-input ui-input-mini ui-input-len8" -->
<%-- 					type="text" value="${goalHighValue }" --%>
<%--				onkeypress="var k=event.keyCode; return (k>=48&&k<=57||k==46)" --%>
<!-- 					onkeypress="return checkNum(event);"	 -->
<!-- 					onkeyup="return value=value.replace(/^(\d+\.\d{1,2})(.*)?$/,'$1')" -->
<!-- 					onpaste="return !clipboardData.getData('text').match(/\D/)" -->
<!-- 					ondragenter="return false" style="ime-mode:Disabled" "/></label> -->
<!-- 				#end -->
<%-- 			</c:if> --%>
		</div>
		<div class="goal-act"></div>
		<table class="tbl-sheet tbl-performance" summary="绩效信息管理" id="goalTable">
			<thead>
				<tr>
					<th class="date">时间</th>
					<th class="lastyear">去年</th>
					<th class="three">3.5分</th>
					<th class="five">5分</th>
					<th class="five">操作</th>
				</tr>
			</thead>
			<tbody>
				<!-- #set($i=1)
		#foreach($kpiGoalDO in $goalList ) -->
				<c:forEach items="${goalList}" var="kpiGoalDO" varStatus="vs">
					<tr id="tr_${kpiGoalDO.kpiCode }_${kpiGoalDO.goalDate}" >
						<td >
						<input type="text" id="goalDate${vs.index+1 }" class="txt"
							name="goalDate" value="${kpiGoalDO.goalDate}" onchange="checkGoalDate(this)"
							onkeypress="return checkNum(event);"	
							onkeyup="return value=value.replace(/^(\d+\.\d{1,2})(.*)?$/,'$1')"
							onpaste="return !clipboardData.getData('text').match(/\D/)"
							ondragenter="return false" style="ime-mode: Disabled" />
						</td>
						<td><input type="text" id="last${vs.index+1 }" class="txt"
							name="lastYearKPI" value="${kpiGoalDO.lastYearKPI }"
							onkeypress="return checkNum(event);"	
							onkeyup="return value=value.replace(/^(\d+\.\d{1,2})(.*)?$/,'$1')"
							onpaste="return !clipboardData.getData('text').match(/\D/)"
							ondragenter="return false" style="ime-mode: Disabled" /></td>
						<!-- $!numberTool.format('0.00',$!kpiGoalDO.lastYearKPI)" -->
						<td><input type="text" id="fen35${vs.index+1}" class="txt"
							name="score35" value="${kpiGoalDO.score35}"
							onkeypress="return checkNum(event);"	
							onkeyup="return value=value.replace(/^(\d+\.\d{1,2})(.*)?$/,'$1');"
							onpaste="return !clipboardData.getData('text').match(/\D/)"
							ondragenter="return false" style="ime-mode: Disabled" /></td>
						<td><input type="text" name="score5" class="txt"
							id="fen5${vs.index+1}" value="${kpiGoalDO.score5}"
							onkeypress="return checkNum(event);"	
							onkeyup="return value=value.replace(/^(\d+\.\d{1,2})(.*)?$/,'$1')"
							onpaste="return !clipboardData.getData('text').match(/\D/)"
							ondragenter="return false" style="ime-mode: Disabled" /></td>
						<td><span class="ui-round-btn ui-round-btn-mini"><input 
						class="ui-round-btn-text"  name="pingjunfenjie"
						type="button" value="删除" onclick="deleteGoal('${kpiGoalDO.kpiCode}','${kpiGoalDO.goalDate}',this);" /></span></td>	
					</tr>
					<input type="hidden" id="temp" value="${vs.index }" />
				</c:forEach>
			</tbody>
		</table>
	</form>
	<iframe name="result" id="result" src="about:blank" frameborder="0"
		width="0" height="0"></iframe>

	<script type="text/javascript" src="<%=path%>/js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript">
		var dg;
		$(document).ready(function() 
			{
				dg = frameElement.lhgDG;
				dg.addBtn('ok','保存',function() 
				{
					var trCount = $("#goalTable tr").length - 1;
					for ( var i = 1; i <= trCount; i++) 
					{
						if (i > 1)
						{
							if (document.getElementById("fen35"+ i) == null)
								continue;
							var month35 = document.getElementById("fen35"+ i).value;
							if (document.getElementById("fen5"+ i) == null)
								continue;
							var month5 = document.getElementById("fen5"	+ i).value;
							if(parseFloat(month5) < parseFloat(month35)){
								alert(document.getElementById("goalDate"+ i).innerText	+ "5分值必须大于3.5分值");
								return false;
							}
// 							var lastMonth35 = document.getElementById("fen35"+ (i - 1)).value;
// 							if (parseFloat(month35) < parseFloat(lastMonth35)) 
// 							{
// 								alert(document.getElementById("goalDate"+ i).innerText	+ "3.5分值必须大于"
// 										+ document.getElementById("goalDate"+ (i - 1)).innerText+ "的值");
// 									return false;
// 							}
// 							if (document.getElementById("fen5"+ i) == null)
// 								continue;
							
// 								var lastMonth5 = document.getElementById("fen5"+ (i - 1)).value;
// 							if (parseFloat(month5) < parseFloat(lastMonth5)) 
// 							{
// 								alert(document.getElementById("goalDate"+ i).innerText	+ "5分值必须大于"
// 									+ document.getElementById("goalDate"+ (i - 1)).innerText+ "的值");
// 									return false;
// 							}
						}
					}
					$("#fmInfo").submit();
				});
				
			});

		//新增一行
		function addRow(){
			var goalTable=document.getElementById("goalTable");	
			// 获取表格中的最后一行对象TR	
			var sampleRow = goalTable.rows[goalTable.rows.length - 1];
			// 获取表格中的最后一行对象TR中的所有TD对象集合，注意，获取的是一个含有多个TD对象的数组集合
			var sampleCell = sampleRow.getElementsByTagName("td");
			// 获取当前表格中的行数序号，为了给每行的每个TD中的对象命名
			var row = goalTable.rows.length;
			// 新创建一个TR对象，也是将要增加到表格中的新行
			var newRow = document.createElement("tr");
			// 遍历表格中的最后一行中TD对象集合，用来复制新增加的行中的每一个新的TD对象
// 			for(var i = 0; i < sampleCell.length; i ++ )
// 			{
			    // 创建一个新的TD对象
			    var goalDateCell = document.createElement("td");
			    var lastYearCell = document.createElement("td");
			    var score35Cell = document.createElement("td");
			    var score50Cell = document.createElement("td");
			    var buttonCell = document.createElement("td");
			    // 将最后一行中第i列的TD对象的内码赋给新行中第i列的新TD对象中
			    goalDateCell.innerHTML = "<input type='text' id='goalDate${vs.index+1 }' class='txt' name='goalDate' value='${kpiGoalDO.goalDate}' onchange='checkGoalDate(this)' onkeypress='return checkNum(event);' onkeyup='return value=value.replace(/^(\d+\.\d{1,2})(.*)?$/,'$1')'	onpaste='return !clipboardData.getData('text').match(/\D/)'	ondragenter='return false' style='ime-mode: Disabled' />";
			    lastYearCell.innerHTML ="<input type='text' id='last${vs.index+1 }' class='txt'	name='lastYearKPI' value='${kpiGoalDO.lastYearKPI }'onkeypress='return checkNum(event);'onkeyup='return value=value.replace(/^(\d+\.\d{1,2})(.*)?$/,'$1')' onpaste='return !clipboardData.getData('text').match(/\D/)'ondragenter='return false' style='ime-mode: Disabled' />";
			    score35Cell.innerHTML = "<input type='text' id='fen35${vs.index+1}' class='txt'	name='score35' value='${kpiGoalDO.score35}'onkeypress='return checkNum(event);'onkeyup='return value=value.replace(/^(\d+\.\d{1,2})(.*)?$/,'$1');'onpaste='return !clipboardData.getData('text').match(/\D/)'ondragenter='return false' style='ime-mode: Disabled' />";
			    score50Cell.innerHTML ="<input type='text' name='score5' class='txt'id='fen5${vs.index+1}' value='${kpiGoalDO.score5}'onkeypress='return checkNum(event);'onkeyup='return value=value.replace(/^(\d+\.\d{1,2})(.*)?$/,'$1')'onpaste='return !clipboardData.getData('text').match(/\D/)'	ondragenter='return false' style='ime-mode: Disabled' />";
			    buttonCell.innerHTML ="<span class='ui-round-btn ui-round-btn-mini'><input 	class='ui-round-btn-text' type='button' value='删除' onclick='deleteGoal(null,null,this);' /></span>";
			    
			    // 			    newCell.innerHTML = sampleCell[i].innerHTML;
			    // 给新行中第i列TD对象中的对象赋名
// 			    newCell.childNodes.item(0).name = "cell" + row + i;
			    // 在新增加的行中追加一个新的TD子对象
			    newRow.appendChild(goalDateCell);
			    newRow.appendChild(lastYearCell);
			    newRow.appendChild(score35Cell);
			    newRow.appendChild(score50Cell);
			    newRow.appendChild(buttonCell);
// 			 }
			   // 将新行增加到表格最后一行的父对象中，TR的父对象是TBODY，很多时候会被遗忘的......
			   sampleRow.parentNode.appendChild(newRow);	
		}
		
		function checkGoalDate(obj){
			var goalDate=obj.value;
			if("${goalType}"=="YEAR"){
				if(goalDate.length!=4){
					alert("此为年绩效!请输入四位年份!");
					obj.value="";
					obj.focus();
					return ;
				}
			}else{
				if(goalDate.length!=6){
					alert("此为年绩效!请输入六位位月份!");
					obj.value="";
					obj.focus();
					return ;
				}
			}
			var trCount = $("#goalTable tr").length - 1;
			for ( var i = 1; i <= trCount; i++) 
			{
				if(document.getElementById("goalDate"+ i) == null)
					continue;
				var otherGoalDate=document.getElementById("goalDate"+ i).value;
				if(obj!=document.getElementById("goalDate"+ i)){
					if(parseFloat(goalDate)== parseFloat(otherGoalDate))
					{
						alert("该绩效日期已经存在!请重新输入!");
						obj.value="";
						obj.focus();
						return ;
					}
				}
			}
		}
		
		function checkNum(e){
			var key = window.event ? e.keyCode: e.which;
			if(key>=48&&key<=57||key==46||key==8){
				return true;
			}else{
				return false;
			}
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

		function failed() {
			alert("操作失败！");
		}

		function toAverage() {
			var fen35 = document.getElementById("fen35").value;
			var fen5 = document.getElementById("fen5").value;
			if (fen35 != null && fen35 != "" && fen5 != null && fen5 != "") {
				if (parseFloat(fen5) < parseFloat(fen35)) {
					alert("5分值必须大于3.5分值");
					document.getElementById("fen5").value = "";
					return;
				}
			}
			var trCount = $("#goalTable tr").length - 1;
			for ( var i = 1; i <= trCount; i++) {
				var month35 = document.getElementById("fen35" + i);
				if (month35 != null)
					month35.value = (fen35 / trCount * i).toFixed(2);
				var month5 = document.getElementById("fen5" + i);
				if (month5 != null)
					month5.value = (fen5 / trCount * i).toFixed(2);
			}

		}
		
		function deleteGoal(kpiCode,goalDate,obj){
// 			var goalTable = document.getElementById("goalTable");	
// 			var tableTr = document.getElementById("tr_"+kpiCode+"_"+goalDate);	
// 			tableTr.parentNode.removeChild(tableTr);
			if(confirm("确定要删除该记录？")){
			   	var span=obj.parentNode;
			   	var td=span.parentNode;
			   	var tableTr=td.parentNode;
				tableTr.parentNode.removeChild(tableTr);
			   	if(kpiCode==null || goalDate==null){
			   		return;
			   	}
				var url = "<%=path%>/kpiGoal/deleteKpiGoal.html?1=1&kpiCode="+kpiCode+"&goalDate="+goalDate;
				$.get(url,function(data){
					if(data=="success"){
						alert("删除成功");

					}else
					{
						alert("删除失败");
					}
				});
			}
		}
	</script>
</body>
</html>