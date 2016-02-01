<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<title>Insert title here</title>
<link href="<%=path%>/common/css/cssz.css" rel="stylesheet" type="text/css"  /> 
<script type="text/javascript" src="<%=path%>/common/js/jquery-1.4.3.min.js"></script>
<script type="text/javascript">
function showMessage(id){
	//alert(document.getElementById(id).offsetTop);
	document.getElementById(id).style.display="";

	
}


function hiddenMessage(id){
	document.getElementById(id).style.display="none";
}

function downExcel(productId, queryDate){
	if(!confirm("确定导出数据吗?")) return;
	 location.href="<%=path%>/userExperience/excelDown.html?productId="+productId+"&queryDate="+queryDate;
	
}
</script>
</head>
<body>


					<div class="box-conent box-ued-table" style="margin-left: -5px;">
						<table class="ued-table" style="background:#EFF0F2; border-left:1px solid #fff; margin-top:-5px" >
							<thead>
								
							</thead>
							<tbody>
							<c:if test="${not empty channelInfo }">
								<c:forEach var="item" items="${channelInfo}" varStatus="num" >
								
									
										<tr>
											<td width="55px;">${num.index+1}</td>
											<td width="180px;">${item.campCode}</td>
											<td width="180px;" style="cursor: pointer;"
													onmouseover="showMessage('message'+${num.index+1});"
													onmouseout="hiddenMessage('message'+${num.index+1});">${item.campMethod}
													
													<div id="message${num.index+1}" class="tip J-tip"
													<c:choose>
													<c:when test="${num.index<3 }">
													style="width: 300px; height: 80px; margin-top: -15px; right: 280px;z-index:9999; display: none"</c:when>
													<c:otherwise>
													style="width: 300px; height: 80px; margin-top: -80px; right: 280px;z-index:9999; display: none"
													</c:otherwise>
												</c:choose> >
													<p align="left">
														<b>渠道编号：</b>${item.campCode}
													</p>
													<p align="left">
														<b>渠道名称：</b>${item.campName}
													</p>
													<p align="left">
														<b>渠道类型：</b>${item.campType}
													</p>
													<p align="left">
														<b>活动部门：</b>${item.campDept}
													</p>
												</div>
												
												</td>
											<td width="180px;">${item.campUv}</td>
											<td width="180px;">${item.createUser}</td>
											<td width="180px;">${item.succUser}</td>
										</tr>
									
								</c:forEach> 
								<tr>
								<td colspan="6" style="text-align: left;">
								<div style="margin: 10px 0px 10px 10px" >
										<a href="javascript:viod(0)" onclick="downExcel('${productId}','${queryDate}');" ><img
											src="<%=path%>/common/images/ddy_22.gif" border="0" /></a>
									</div>
								</td>
								</tr>
								</c:if>
								<c:if test="${empty channelInfo}">
					              <td colspan="6" style="color:red;"align="center">当前日期没有渠道引入情况数据</td>
				              </c:if>
							</tbody>
						</table>
						
				</div>
				
				


</body>
</html>