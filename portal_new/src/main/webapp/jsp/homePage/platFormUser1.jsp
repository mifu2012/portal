<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:forEach items="${platFormUsers}" var="platFormUserVO">
${platFormUserVO.title};${platFormUserVO.value};;${platFormUserVO.color}
</c:forEach>