<%@ page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="UTF-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String rootPath = request.getContextPath();
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>数据分页DEMO</title>
		<script type="text/javascript" src="<%=rootPath%>/common/Common.jsp"></script>
		<link href="<%=rootPath%>/common/css/cssz.css" rel="stylesheet" type="text/css" />
		<link href="<%=rootPath%>/common/css/style.css" rel="stylesheet" type="text/css" />
	</head>
	<body >
		<div class="layer"  id="productCheck" style="z-index:999;"><!-- display: none -->
	<div class="layer2">
	<h2>选择产品<span><a href="javascript:void(0);" onclick="Javascript:document.getElementById('productCheck').style.display='none';" >关闭</a></span></h2>
	<ul>
		<c:forEach items="${prodInfos}" var="prodInfo">
			<li><a href="javascript:void(0);"
					onclick="changeProduct(${prodInfo.productId});">
					 <c:if test="${prodInfo.iconUrl eq null}">
							<img src="<%=rootPath%>/common/images/prodana/no-icon.png"></img>
						</c:if> <c:if test="${not empty prodInfo.iconUrl}">
							<img src="${prodInfo.iconUrl}"></img>
						</c:if>
				</a> <div>${prodInfo.productName}</div></li>
			</c:forEach>
	</ul>
	</div>
	</div>
	</body>
</html>
