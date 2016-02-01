<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.Date"%>
<%@ page import="com.infosmart.util.StringDes"%>
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
<title>数据源信息</title>
<link type="text/css" rel="stylesheet" href="<%=basePath%>/css/main.css" />
<link rel="stylesheet" href="<%=basePath%>/js/jui_custom/demos.css">
<style type="text/css">
.cover {
    display:none;
    position:absolute;
    top:0%;
    left:0%;
    width:100%;
    height:100%;
    z-index:999;
    background-color:gray;
    opacity: 0.2; 
    filter:alpha(opacity=20);
    }
</style>
<script type="text/javascript"
	src="<%=basePath%>/js/jquery-1.5.1.min.js"></script>
<!--jquery_ui_costom-->
<link rel="stylesheet"
	href="<%=basePath%>/js/jui_custom/jquery.ui.all.css">
<script src="<%=basePath%>/js/jui_custom/jquery.ui.core.js"></script>
<script src="<%=basePath%>/js/jui_custom/jquery.ui.widget.js"></script>
<script src="<%=basePath%>/js/jui_custom/jquery.ui.tabs.js"></script>
<script type="text/javascript"
	src="<%=basePath%>js/datePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="<%=basePath%>js/datePicker/My98MonthPicker.js"></script>
<script type="text/javascript">	$(function() {
		$( "#tabs" ).tabs();
	});
</script>
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

.input_select {
	width: 206px;
	height: 25px;
	line-height: 25px;
}

.info_txt {
	height: 60px;
	/* line-height: 60px; */
}

.info_txt th {
	text-align: right;
	width: 75px;
	color: #4f4f4f;
	padding-right: 5px;
	font-size: 13px;
}

.info_txt td {
	text-align: left;
}

.info {
	height: 40px;
	line-height: 40px;
}

.info th {
	text-align: right;
	width: 75px;
	color: #4f4f4f;
	padding-right: 5px;
	font-size: 13px;
}

.info td {
	text-align: left;
}
</style>
<style> 
 .test_box p{margin:0;}
 .test_box{text-align:left;line-height:20px;width: 99%; *width: 390px; min-height:120px; _height:120px; max-height:300px; margin-left:0px; margin-right:auto; margin-bottom:5px;padding:3px; outline:0; border:1px solid #a0b3d6; font-size:12px; word-wrap:break-word; overflow:auto;height:270px;}
 .test_box:focus{-moz-box-shadow:0 0 6px rgba(0, 100, 255, .45); -webkit-box-shadow:0 0 6px rgba(0, 100, 255, .45); box-shadow:0 0 6px rgba(0, 100, 255, .45); border-color:#34538b;}
</style>
</head>
<script type="text/javascript">
<!--
function isIE()
{ //ie? 
	if (window.navigator.userAgent.toLowerCase().indexOf("msie")>=1)
	{
		return true; 
	}
	else 
	{
		return false; 
	}
} 

if(!isIE()){ //firefox innerText define
    HTMLElement.prototype.__defineGetter__("innerText", 
    function(){
        var anyString = "";
        var childS = this.childNodes;
        for(var i=0; i<childS.length; i++) { 
            if(childS[i].nodeType==1)
                //anyString += childS[i].tagName=="BR" ? "\n" : childS[i].innerText;
                anyString += childS[i].innerText;
            else if(childS[i].nodeType==3)
                anyString += childS[i].nodeValue;
        }
        return anyString;
    } 
    ); 
    HTMLElement.prototype.__defineSetter__("innerText", 
    function(sText){
        this.textContent=sText; 
    } 
    ); 
}
//-->
</script>
<body>
    <div id= "cover" class="cover"></div>

	<form action="<%=basePath%>/NewReport/dataSourceSave.html"
		name="addForm" id="addForm" style="overflow: hidden;" target="result"
		method="post" onsubmit="return checkInfo();">
		<!-- onsubmit="return checkInfo();" -->
		<input type="hidden" name="reportId" id="reportId" value="${reportId}" />
		<input type="hidden" name="id" id="id" value="${dataSource.id}" /> <input
			type="hidden" name="dataSourceId" id="dataSourceId"
			value="${dataSource.id}" />
		<div class="demo"
			style='margin: 0, 0, 0, 0; padding-left: 5px; padding-right: 5px; padding-top: 0px; padding-bottom: 0px;'>
			<div id="tabs">
				<ul>
					<li><a href="#tabs-1" style='font-size: 13px;'>数据源配置</a></li>
					<li><a href="#tabs-2" style='font-size: 13px;'>报表SQL语句</a></li>
				</ul>
				<!--报表设置-->
				<div id="tabs-1"
					style='padding-left: 5px; padding-right: 5px; padding-top: 5px; height: 355px; *height: 345px;'>
					<table border="0" cellpadding="0" cellspacing="0" align="left">
						<tr>
							<td></td>
							<td align="left"><font color="red" size="2">注：带“ * ”项为必填项</font>
							</td>
						</tr>
						<tr class="info">
							<th>已有数据源:</th>
							<td><select name="driverName1" id="driverName1"
								class="input_select" onchange="setValue(this)" style="font-size:13px;">
									<option value="" style="color:red;">== 新数据源 ==</option>
									<c:forEach items="${dataSourceList}" var="list" varStatus="vs">
										<option value="${list.id}" id="opt_${list.id}"
											<c:if test="${dataSource.id ==list.id }">selected="selected"</c:if>>${list.driverName}</option>
										<script language='javascript'>
				   //查询条件
	               document.getElementById("opt_${list.id}").setAttribute("sourceId","${list.id}");
	               document.getElementById("opt_${list.id}").setAttribute("url","${list.url}");
	               document.getElementById("opt_${list.id}").setAttribute("driverName","${list.driverName}");
	               document.getElementById("opt_${list.id}").setAttribute("dataDriver","${list.dataDriver}");
	               document.getElementById("opt_${list.id}").setAttribute("userName","${list.userName}");
	               document.getElementById("opt_${list.id}").setAttribute("password","${list.pwdDesc}");
	               //document.getElementById("opt_${list.id}").setAttribute("querySql","${list.querySql}");
	            </script>
									</c:forEach>
							</select>
							</td>
							<td><a href="javascript:deleteDataSource();" class="myBtn" name="deleteBtn" 
								id="deleteBtn"><em>删除</em></a>
						</tr>
						<tr class="info">
							<th>数据源名称:</th>
							<td><input name="driverName" id="driverName"
								class="input_txt" value="${dataSource.driverName}"
								onblur="checkDriverName()">
							</td>
							<td>&nbsp;<font color="red">*</font></td>
						</tr>
						<tr class="info">
							<th>数据库驱动:</th>
							<td><select name="dataDriver" id="dataDriver"
								class="input_select"
								onChange="changeType(this.options[this.options.selectedIndex].value);">
									<option value="">== 请选择 ==</option>
									<option value="oracle.jdbc.driver.OracleDriver"
										<c:if test="${dataSource.dataDriver eq 'oracle.jdbc.driver.OracleDriver' }">selected="selected"</c:if>>Oracle</option>
									<option value="com.microsoft.sqlserver.jdbc.SQLServerDriver"
										<c:if test="${dataSource.dataDriver eq 'com.microsoft.sqlserver.jdbc.SQLServerDriver' }">selected="selected"</c:if>>SQL
										Server2005</option>
									<option value="com.microsoft.jdbc.sqlserver.SQLServerDriver"
										<c:if test="${dataSource.dataDriver eq 'com.microsoft.jdbc.sqlserver.SQLServerDriver' }">selected="selected"</c:if>>SQL
										Server</option>
									<option value="com.mysql.jdbc.Driver"
										<c:if test="${dataSource.dataDriver eq 'com.mysql.jdbc.Driver' }">selected="selected"</c:if>>MySQL</option>
									<option value="com.ibm.db2.jdbc.app.DB2Driver"
										<c:if test="${dataSource.dataDriver eq 'com.ibm.db2.jdbc.app.DB2Driver' }">selected="selected"</c:if>>DB2</option>
									<option value="org.postgresql.Driver"
										<c:if test="${dataSource.dataDriver eq 'org.postgresql.Driver' }">selected="selected"</c:if>>PostgreSQL</option>
									<option value="com.sysbase.jdbc.SybDriver"
										<c:if test="${dataSource.dataDriver eq 'com.sysbase.jdbc.SybDriver' }">selected="selected"</c:if>>Sybase</option>
							</select>
							</td>
							<td>&nbsp;<font color="red">*</font></td>
						</tr>
						<tr class="info_txt">
							<th>URL:</th>
							<td><textarea name="url" id="url" rows="3" cols="24" style="width: 230px;height: 80px;"
									onblur="checkUrl()" >${dataSource.url }</textarea></td>
							<td>&nbsp;<font color="red" style="height: 40px;line-height: 40px;">*</font></td>
						</tr>
						<tr class="info">
							<th>用户名:</th>
							<td><input type="text" name="userName" id="userName"
								class="input_txt" value="${dataSource.userName }" />
							</td>
							<td>&nbsp;<font color="red">*</font></td>
						</tr>
						<tr class="info">
							<th>密码:</th>
							<td><input type="password" name="password" id="password"
								class="input_txt" value="${dataSource.pwdDesc }" />
							</td>
						</tr>
						<tr class="info">
							<td colspan="2">
								<a href="javascript:testDataBaseLink();" class="myBtn" name="testQutyBtn" id="testQutyBtn" style="margin-top: 20px;"><em>测试连接</em></a>
							</td>
						</tr>
					</table>
				</div>
				<div id="tabs-2"
					style='padding-left: 5px; padding-right: 5px; padding-top: 5px; height: 355px; *height: 345px;'>
					<table border="0" cellpadding="0" cellspacing="0" style='text-align:left;'>
						<tr class="info">
							<td><textarea rows="20" cols="40" name="reportSql"
									id="reportSql"
									style="width: 100%; *width: 390px; overflow: auto; font-size: 14px; height: 170px; resize: none;display:none;" >${reportDesign.reportSql}</textarea>
								<!--用于显示的内容-->
                                <div contentEditable=true id="reportSqlHTML" class="test_box" onblur="showRptSql();toHighlighting();" oncopy="copyRptSql()"></div>
								<!--暂存报表SQL语句-->
								<div id="tempReportSql" style="display:none;"/>
								<div id="tempCopySql" style="display:none;"/>
							 </td>
						  </tr>
						  <tr>
						      <td>
								&nbsp;&nbsp;<a class="myBtn" name="testQutyBtn" id="testQutyBtn" href="javascript:testReportSql();" ><em>测试SQL</em></a>
							  </td>
						  </tr>
						  <tr>
						      <td style='font-size:12px;'>
							&nbsp;&nbsp;说明：可以键入<input id='notifySpan' style="width:60px;font-weight:bold;color:red;font-size:13px;background:transparent;border:1px solid #ffffff;" type='text' name="notifySpan" value="" readonly></input>作为参数，其中<font color='red' size='2'>‘abc’</font>为参数编码。<br/>&nbsp;&nbsp;如：selct * from table where id=<input id='paramValSpan' style="width:60px;font-weight:bold;color:red;font-size:13px;background:transparent;border:1px solid #ffffff;" type='text' name="notifySpan" value="" readonly> and code like <input id='paramLikeValSpan' style="width:90px;font-weight:bold;color:red;font-size:13px;background:transparent;border:1px solid #ffffff;" type='text' name="notifySpan" value="" readonly> 提示：参数名不支持<font color='red' size='2'>中文</font>命名
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</form>
	<iframe name="result" id="result" src="about:blank" frameborder="0"
		width="0" height="0"></iframe>
	<script type="text/javascript">
	var isOk=false;//是否测试报表SQL
	var isValidDriverName=true;//是否有效驱动名
	var isLink =false;
	var testSql="";//用来判断sql语句是否变化
	String.prototype.Trim = function() 
	{ 
	  return this.replace(/(^\s*)|(\s*$)/g, ""); 
	} 
	window.onload=function()
	{
		var paramName="abc";
		document.getElementById("notifySpan").value="$"+"{"+paramName+"}";
		document.getElementById("paramValSpan").value="$"+"{"+paramName+"}";
		document.getElementById("paramLikeValSpan").value="$"+"{%"+paramName+"%}";
		//SQL语句高亮显示
		toHighlighting();
	}
	var dg;
	$(document).ready(function(){
		//保存按钮
		dg = frameElement.lhgDG;
		dg.addBtn('ok','保存',function(){
			/*
			if(isLink==false){
				alert('请先测试数据源');	
				   return;
			}
			*/
			if(isOk==false)
			{
			   alert('请测试SQL语句');	
			   return;
			}
			if(isValidDriverName==false){
				alert('驱动名重复,请重新输入!');
				return;
			}
			 if(testSql!=document.getElementById("reportSql").value){
				 alert("sql语句已修改，请重新测试");
				 isOk=false;
				 return;
			 }
			$("#addForm").submit();
		});
	});
	/* 校验驱动名 */
	function checkDriverName(){
		var driverName1=document.getElementById("driverName1").value;
		var driverName=document.getElementById("driverName").value;
		$.ajax({
			type: "POST",                                                                 
			url:"<%=basePath%>/NewReport/checkDriverName?driverName="+driverName+"&driverName1="+driverName1+"&random="+Math.random(),
			success: function(message){ 
				if(message=="false"){
					alert("驱动名重复,请重新输入!");
					isValidDriverName=false;
				}else{
					isValidDriverName=true;
				}
		  }
		});
	}
	
	//选择sql字段设置控件名称值
	function setValue(crtObj){
	  var crtOpt=document.getElementById("opt_"+crtObj.value);
	  if(crtOpt==null)
	  {
		  document.getElementById("dataSourceId").value="";
		  document.getElementById("url").value="";
		  document.getElementById("driverName").value="";
		  document.getElementById("dataDriver").value="";
		  document.getElementById("userName").value="";
		  document.getElementById("password").value="";
		  return;
	  }
	  document.getElementById("dataSourceId").value=crtOpt.getAttribute("sourceId").Trim();
	  document.getElementById("url").value=crtOpt.getAttribute("url").Trim();
	  document.getElementById("driverName").value= crtOpt.getAttribute("driverName").Trim();
	  document.getElementById("dataDriver").value= crtOpt.getAttribute("dataDriver").Trim();
	  document.getElementById("userName").value= crtOpt.getAttribute("userName").Trim();
	  document.getElementById("password").value= crtOpt.getAttribute("password").Trim();
	  //document.getElementById("querySql").value= crtOpt.getAttribute("querySql").Trim();
	}
	//修改URL
	function changeType(driver) {
		////Informix 数据库
		//jdbc:informix-sqli://localhost:1533/testDB:INFORMIXSERVER=myserver,user=testuser,password=testpassword
		if (driver == "com.mysql.jdbc.Driver") {
			// mysql-->jdbc:mysql://localhost:3306/testDB?user = root&password=root&useUnicode=true&&characterEncoding=gb2312
		$("#url").val("jdbc:mysql://<machine_name>:<port>/<dbname>");
		} else if (driver == "oracle.jdbc.driver.OracleDriver") {
			// oracle
		$("#url").val("jdbc:oracle:thin:@<machine_name>:<port>:<dbname>");
		} else if (driver == "com.microsoft.sqlserver.jdbc.SQLServerDriver") {
			// sql server 2005
		$("#url").val("jdbc:microsoft:sqlserver://<machine_name>:<port>;DatabaseName=<dbname>");
		} else if (driver == "com.microsoft.jdbc.sqlserver.SQLServerDriver") {
			// sql server 7.0,2000
		$("#url").val("jdbc:microsoft:sqlserver://<machine_name>:<port>;DatabaseName=<dbname>");
		} else if (driver == "com.sysbase.jdbc.SybDriver") {
			// sybase
		$("#url").val("jdbc:sysbase:Tds:<machine_name>:<port>/<dbname>");
		} else if (driver == "com.ibm.db2.jdbc.app.DB2Driver") {
			// db2
		$("#url").val("jdbc:db2://<machine_name>:<port>/<dbname>");
		} else if (driver == "org.postgresql.Driver") {
			//PostgreSQL 数据库
		$("#url").val("jdbc:postgresql://<machine_name>/<dbname>");
		}else {
		$("#url").val("");
		}
	}
	//校验数据驱动和url是否匹配
	 function checkUrl(){
		var driver=document.getElementById("dataDriver").value;
		var url = document.getElementById("url").value.toLowerCase();
		if (driver == "com.mysql.jdbc.Driver" ) {// mysql
			if(url.indexOf("mysql")!='-1'){
			return true;
			}
			alert('数据库驱动与URL不匹配!');
			return false;
		} else if (driver == "oracle.jdbc.driver.OracleDriver") {// oracle
			if(url.indexOf("oracle")!='-1'){
				return true;
			}
			alert('数据库驱动与URL不匹配!');
			return false;
		} else if (driver == "com.microsoft.sqlserver.jdbc.SQLServerDriver") {// sql server 2005
			if(url.indexOf("sqlserver")!='-1'){
				return true;
			}
			alert('数据库驱动与URL不匹配!');
			return false;
		} else if (driver == "com.microsoft.jdbc.sqlserver.SQLServerDriver") {// sql server 7.0,2000
			if(url.indexOf("sqlserver")!='-1'){
				return true;
			}
			alert('数据库驱动与URL不匹配!');
			return false;
		} else if (driver == "com.sysbase.jdbc.SybDriver") {// sybase
			if(url.indexOf("sysbase")!='-1'){
				return true;
			}
			alert('数据库驱动与URL不匹配!');
			return false;
		} else if (driver == "com.ibm.db2.jdbc.app.DB2Driver") {// db2
			if(url.indexOf("db2")!='-1'){
				return true;
			}
			alert('数据库驱动与URL不匹配!');
			return false;
		} else if (driver == "org.postgresql.Driver") {//PostgreSQL 数据库
			if(url.indexOf("postgresql")!='-1'){
				return true;
			}
			alert('数据库驱动与URL不匹配!');
			return false;
		}else {
			alert('数据库驱动与URL不匹配!');
		return false;
		}
	} 
	
	function checkInfo() {
		if ($("#driverName").val() == "") {
			alert("请选填写驱动名称!");
			$("#driverName").focus();
			return false;
		}
		if ($("#dataDriver").val() == "") {
			alert("请选择数据库驱动!");
			$("#dataDriver").focus();
			return false;
		}
		if ($("#url").val() == "") {
			alert("请选填写URL!");
			$("#url").focus();
			return false;
		}
		if ($("#reportSql").val() == "") {
			alert("请选填写报表SQL语句!");
			$("#reportSql").focus();
			return false;
		}
		 document.getElementById("dataSourceId").value=document.getElementById("driverName1").value;
		return true;
	}
	//删除数据源
	function deleteDataSource(){
		//confirm("关联报表,确认删除？");
		var crtObj = document.getElementById("driverName1");
		var crtOpt=document.getElementById("opt_"+crtObj.value);
		var isDelete = false;
		if(crtOpt==null) {
			alert('请选择要删除的数据源!');
			return;
		}
		var dataSourceId=crtOpt.getAttribute("sourceId").Trim();
		$.ajax({
			type: "POST",                                                                 
			url:encodeURI("<%=basePath%>/NewReport/validateDataSource.html?dataSourceId=" + dataSourceId + "&random=" + Math.random()),
				success : function(msg) {
					if(msg=="false"){
						alert("关联报表,不能删除!");
						//confirm("关联报表,确认删除？");
						//isDelete=true;
					}else if(msg=="true"){
						if(confirm("确认删除？")==false) return ;
						isDelete=true;
						if(isDelete==true){
							$.ajax({
								type: "POST",                                                                 
								url:encodeURI("<%=basePath%>/NewReport/deleteDataSource.html?dataSourceId=" + dataSourceId + "&random=" + Math.random()),
									success : function(msg) {
										if(msg=="failed"){
											alert("删除失败!");
										}else if(msg=="success"){
											alert("删除成功!");
											if(crtObj.length>0) 
											{ 
												crtObj.remove(crtObj.selectedIndex); 
											}
											document.getElementById("driverName1").value="";
											document.getElementById("driverName").value="";
											document.getElementById("dataDriver").value="";
											document.getElementById("url").value="";
											document.getElementById("userName").value="";
											document.getElementById("password").value="";
										}
									} 
						   });
						}
					}
				} 
	   });
	}
	
	
	//测试数据库链接
	function testDataBaseLink() 
	{
	   /* var querySql=document.getElementById("querySql").value;
	   if(querySql==null||querySql.length==0)
		{
			alert("请输入SQL语句");
			return;
		} */
		//数据源名称
	   var driverName =document.getElementById("driverName").value;
		//数据库驱动
	   var dataDriver = document.getElementById("dataDriver").value;
		//url
	   var url=document.getElementById("url").value;
		//用户名
	   var userName=document.getElementById("userName").value;
	   var password = document.getElementById("password").value;
	   /* 校验必填项 */
	   if(driverName==""){
		   alert("请填写数据源名称!");
		   isLink=false;
		   return false;
	   }else if(dataDriver==""){
		   alert("请选择数据库驱动!");
		   isLink=false;
		   return false;
	   }else if(url==""){
		   alert("请填写URL"); 
		   isLink=false;
		   return false;
	   }else if(userName==""){
		   alert("请填写用户名!"); 
		   isLink=false;
		   return false;
	   }
	   $.ajax({
			type: "POST",                                                                 
			url:encodeURI("<%=basePath%>/NewReport/testDataBaseLink.html?1=1&dataDriver=" + dataDriver + "&url="+url+"&userName="+userName+"&password="+password+"&random=" + Math.random()),
				success : function(msg) {
					if(msg=="0"){
						alert("测试连接成功,\n\n请继续配置SQL语句!");
						isLink=true;
					}else if(msg=="1"){
						alert("测试连接失败!");
						isLink=false;
					}
				} 
	   });
	}
	//显示doading
	function showLoadingDiv(){
		var loadingDiv=document.getElementById("loading_wait_Div");
		if(loadingDiv!=null){
			loadingDiv.style.display="";
		}else{
			loadingDiv=document.createElement("div");
			loadingDiv.id="loading_wait_Div";
			loadingDiv.style.position="absolute";
			loadingDiv.style.left="40%";
			loadingDiv.style.top="30%";
			loadingDiv.innerHTML="<img src='<%=path%>/images/loading1.gif'>";
		  document.body.appendChild(loadingDiv);
		}
		document.getElementById( "cover").style.display= "block";
		document.getElementById( "cover").style.width=Math.max(document.documentElement.scrollWidth, document.documentElement.clientWidth) + "px";
		document.getElementById( "cover").style.height=Math.max(document.documentElement.scrollHeight, document.documentElement.clientHeight) + "px";

	}
	function closeLoadingDiv(){
		if(document.getElementById("loading_wait_Div")!=null){
			document.getElementById("loading_wait_Div").style.display="none";
		}
		document.getElementById( "cover").style.display= "none";
	}
	//测试报表SQL
	function testReportSql() 
	{
	   var reportSql=document.getElementById("reportSql").value;
	   if(reportSql==null||reportSql.length==0)
		{
			alert("请输入报表SQL语句");
			return;
		}else{
			showLoadingDiv();
		}
	   var dataDriver = document.getElementById("dataDriver").value;
	   var url=document.getElementById("url").value;
	   var userName=document.getElementById("userName").value;
	   var password = document.getElementById("password").value;
	   $.ajax({
			type: "POST",  
			data:{reportSql:reportSql,dataDriver:dataDriver,url:url,userName:userName,password:password},
			url:"<%=basePath%>/NewReport/testReportSql.html?1=1",
					success : function(msg) {
						closeLoadingDiv();
						if (msg == "0") {
							alert("测试成功!");
							testSql = reportSql;
							isOk = true;
						} else if (msg == "-1") {
							alert("测试失败!");
							isOk = false;
						} else if (msg == "-2") {
							alert("字段不能重复!");
							isOk = false;
						}
					}
				});
	}

	function success() {
		alert('操作成功');
		if (dg.curWin.document.forms[0]) {
			dg.curWin.document.forms[0].action = dg.curWin.location + "";
			dg.curWin.document.forms[0].submit();
		} else {
			dg.curWin.location.reload();
		}
		window.parent.location.reload();
		dg.cancel();
	}

	function failed() {
		alert("新增失败！");
	}
	var brChar="<br/>";
	if(!isIE()){ //firefox innerText define
       brChar="<br>";
	}
    //得到SQL语句
	function showRptSql()
	{
	   //String.fromCharCode(currKey);
	   //alert(document.getElementById("reportSqlHTML").innerText);
       var reportSql= document.getElementById("reportSqlHTML").innerHTML;
	   //alert(reportSql);
	   var tempSql="";
	   //<br/>
	   var array=reportSql.split(brChar);//判断是否有换行
       for(var i=0;i<array.length;i++)
	   {
         if(tempSql.length>0) tempSql+="\n";
		 tempSql+=array[i];
	   }
	   //alert(tempSql);
	   document.getElementById("tempReportSql").innerHTML=tempSql;
       document.getElementById("reportSql").value=document.getElementById("tempReportSql").innerText;
	}
    //高亮显示
	function toHighlighting()
	{
	   var tempHTML="";
	   var reportSql=document.getElementById("reportSql").value.Trim();
	   //alert(reportSql);
	   if(reportSql==null||reportSql.length==0) return;
	   var array=reportSql.split("\n");
	   for(var i=0;i<array.length;i++)
	   {
		 if(array[i]==null||array[i].length==0) continue;
		 //如果是文本域换行，则转成HTML换行
		 if(tempHTML.length>0) tempHTML+=brChar;////<br/>
		 tempHTML+=array[i];
	   }
	   var tmpKeyArray=new Array();

	   var tempParam="";
		   
	   var isOk=false;

	   for(var i=0;i<tempHTML.length;i++)
	   {
		   //alert(sql.charAt(i)=='$');
		   if(tempHTML.charAt(i)=='$')
		   {
			  isOk=true;
		   }
		   if(isOk)
		   {
			  tempParam+=tempHTML.charAt(i);
		   }
		   if(tempHTML.charAt(i)=='}')
		   {
			  tmpKeyArray[tmpKeyArray.length]=tempParam;
			  tempParam="";
			  isOk=false;
		   }
	   }
	   //replace(new RegExp("!","gm"), "#")
	   for(var i=0;i<tmpKeyArray.length;i++)
	   {
		  tempHTML=tempHTML.replace(tmpKeyArray[i],"<b>"+tmpKeyArray[i]+"</b>");
	   }
	   //alert(tempHTML);
	   document.getElementById("reportSqlHTML").innerHTML=tempHTML;
	}
	//copy //拷贝到剪贴板
	function copyRptSql()
	{
		var copyHtml="";
		if(window.getSelection) {
            copyHtml= window.getSelection().toString();
         } else if(document.selection && document.selection.createRange) {
            copyHtml= document.selection.createRange().text;
         }
		 if(copyHtml==null||copyHtml.length==0) return;
		 document.getElementById("tempCopySql").innerHTML=copyHtml;
		 //alert(copyHtml);
		 //拷贝到剪贴板
		 window.clipboardData.setData("Text",document.getElementById("tempCopySql").innerText);
	}
	</script>
</body>
</html>