<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html> 
<head> 
<title> Table   Object   method </title> 
<script   language= "JavaScript"> 
var intRowIndex = 0; 
function   insertRow(tbIndex){
	  //alert(tbIndex); 
      var objRow = myTable.insertRow(tbIndex); 
      var objCel = objRow.insertCell(0); 
      objCel.innerText = document.myForm.myCell1.value; 
      var objCel = objRow.insertCell(1); 
      objCel.innerText = document.myForm.myCell2.value; 
      objRow.attachEvent( "onclick",getIndex); 
      objRow.style.background = "pink"; 
} 
function   deleteRow(tbIndex){ 
      myTable.deleteRow(tbIndex); 
} 
function   getIndex(){ 
      intRowIndex = event.srcElement.parentElement.rowIndex; 
      pos.innerText = intRowIndex; 
} 
</script> 
</head> 
<body   onload= "pos.innerText=intRowIndex; "> 
<h2> Table   Object   method </h2> 

Current Loction:<span id="pos"> </span> 
<table id= "myTable" border="1"> 
<tr   onclick= "getIndex() "> 
      <td> HTML </td> 
      <td> CSS </td> 
</tr> 
<tr onclick= "getIndex()"> 
      <td> JavaScript </td> 
      <td> VBScript </td> 
</tr> 
</table> 
<form id=""  name= "myForm "> 
The   First   Line:   <input type= "text" name="myCell1"value= "CGI"/> <br/> 
The   Second   Line:   <input type= "text" name="myCell2"value= "ASP"/> <br/> 
<input   type= "button"  onclick="insertRow(intRowIndex)"value= "AddRow"/> 
<input   type= "button"  onclick="deleteRow(intRowIndex)"value= "DelRow"/> 
<input   type= "button"  onclick="insertRow(myTable.rows.length);"value= "AddRow"/> 
</form> 
</body> 
</html>