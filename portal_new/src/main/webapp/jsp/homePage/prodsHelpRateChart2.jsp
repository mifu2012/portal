<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<chart>
<series>
<c:forEach items="${prodsHelpRates}" var="prodsHelpRate">
 <value xid="${prodsHelpRate.productName}">${prodsHelpRate.productName}</value>
</c:forEach>
</series>
<graphs>
<graph gid="1">
<c:forEach items="${prodsHelpRates}" var="prodsHelpRate">
<value xid="${prodsHelpRate.productName}" color="${prodsHelpRate.color}">${prodsHelpRate.baseValue}</value>
</c:forEach>
</graph>
</graphs>
</chart>