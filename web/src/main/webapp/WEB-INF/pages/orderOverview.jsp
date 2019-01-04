<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="style" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title><spring:message code="title.order.overview"/></title>
    <script src="${pageContext.request.contextPath}/webjars/jquery/3.1.0/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/webjars/bootstrap/4.1.0/js/bootstrap.min.js"></script>
    <spring:url value="/resources/styles/plpStyles.css" var="plpStyles"/>
    <link href="${plpStyles}" rel="stylesheet"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/bootstrap/4.1.0/css/bootstrap.min.css">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/webjars/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>
<jsp:include page="/resources/common/header.jsp"/>
<section class="jumbotron text-center">
    <div class="container">
        <h1 class="jumbotron-heading"><spring:message code="order.overview.page.header"/></h1>
    </div>
</section>
<section class="jumbotron text-center">
    <div class="container">
        <h1 class="jumbotron-heading">${orderMessage}</h1>
    </div>
</section>
<jsp:include page="/resources/common/footer.jsp"/>
</body>
</html>