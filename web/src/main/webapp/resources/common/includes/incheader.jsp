<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
    <script src="${pageContext.request.contextPath}/webjars/jquery/3.1.0/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/webjars/bootstrap/4.1.0/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="http://botmonster.com/jquery-bootpag/jquery.bootpag.js"></script>
    <spring:url value="/resources/styles/plpStyles.css" var="plpStyles"/>
    <link href="${plpStyles}" rel="stylesheet"/>
    <spring:url value="/resources/styles/loginStyles.css" var="loginStyles"/>
    <link href="${loginStyles}" rel="stylesheet"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/bootstrap/4.1.0/css/bootstrap.min.css">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/webjars/font-awesome/4.7.0/css/font-awesome.min.css">
    <spring:message code="input.field.placeholder" var="inputPlaceholder"/>
    <spring:message code="search.field.placeholder" var="searchPlaceholder"/>
</html>
