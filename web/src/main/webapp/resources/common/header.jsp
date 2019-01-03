<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand" href="<c:url value="/productList"/>"><spring:message code="head.app.name"/></a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExample07"
                aria-controls="navbarsExample07" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse justify-content-end" id="navbarsExample07">
            <ul class="navbar-nav m-auto">
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value="/productList"/>"><spring:message code="link.productlist.name"/><span
                            class="sr-only"></span></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value="/cart"/>"><spring:message code="link.cart.name"/></a>
                </li>
            </ul>
            <div class="inline my-2 my-lg-0">
                <a class="btn btn-success btn-md ml-3" href="<c:url value="/cart"/>">
                    <i class="fa fa-shopping-cart"></i> <spring:message code="link.cart.name"/>
                    <span id="cartSize" class="badge badge-light">${cartSize}</span>
                </a>
                <button class="btn btn-info btn-md ml-3" style="opacity: 1" disabled="disabled">
                    <i class="text-center text-white"><spring:message code="cart.price"/></i> <span id="cartPrice"
                                                                                                    class="badge badge-light">${cartPrice}</span>
                    <i class="fa fa-dollar"></i>
                </button>
            </div>
        </div>
    </div>
</nav>
</html>