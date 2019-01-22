<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><spring:message code="error.page.title"/></title>
    <jsp:include page="/resources/common/includes/incheader.jsp"/>
</head>
<body>
<section class="jumbotron text-center">
    <div class="container">
        <h1 class="jumbotron-heading">Error code:${code}</h1>
        <h2 class="jumbotron-heading">${message}</h2>
    </div>
</section>
</body>
<jsp:include page="/resources/common/footer.jsp"/>
</html>
