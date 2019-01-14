<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title><spring:message code="title.login"/></title>
    <jsp:include page="/resources/common/includes/incheader.jsp"/>
</head>
<body class="bg-secondary">
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand" href="<c:url value="/productList"/>"><spring:message code="head.app.name"/></a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExample07"
                aria-controls="navbarsExample07" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
    </div>
</nav>
<section class="jumbotron text-center">
    <div class="container">
        <c:if test="${not isLogin}">
            <h3 style="color: darkred" class="jumbotron-heading"><spring:message code="login.message"/></h3>
        </c:if>
        <c:if test="${isError}">
            <h3 style="color: #c01508" class="jumbotron-heading"><spring:message code="login.error"/></h3>
        </c:if>
        <c:if test="${isLogout}">
            <h3 style="color: darkblue" class="jumbotron-heading"><spring:message code="logout.success"/></h3>
        </c:if>
        <c:if test="${not empty username}">
            <h3 style="color: darkblue" class="jumbotron-heading"><spring:message code="login.username"/>"${username}"</h3>
        </c:if>
    </div>
</section>
<div id="login">
    <div class="container">
        <div id="login-row" class="row justify-content-center align-items-center">
            <div id="login-column" class="col-md-6">
                <div id="login-box" class="col-md-12">
                    <form id="login-form" class="form" action="" method="post">
                        <h3 class="text-center text-info">Login</h3>
                        <div class="form-group">
                            <label for="username" class="text-info">Username:</label><br>
                            <input type="text" name="username" id="username" class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="password" class="text-info">Password:</label><br>
                            <input type="password" name="password" id="password" class="form-control">
                        </div>

                        <div class="form-group">
                            <input type="submit" name="submit" class="btn btn-info btn-lg" value="Log In">
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>