<html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<head>
    <title><spring:message code="title.plp"/></title>

    <script>
        var data = "${data}";
        var totalPages = "${totalPages}";
        var currentPage = "${currentPage}";
        var visiblePages = "${visiblePages}";
    </script>

    <script src="webjars/jquery/3.1.0/jquery.min.js"></script>
    <script src="webjars/bootstrap/4.1.0/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="http://botmonster.com/jquery-bootpag/jquery.bootpag.js"></script>

    <spring:url value="/resources/styles/plpStyles.css" var="plpStyles"/>
    <spring:url value="/resources/sorting/iconClickHandler.js?1" var="iconClickHandler"/>
    <spring:url value="/resources/pagination/pagination.js?1" var="pagination"/>
    <spring:url value="/resources/add2cart/addToCart.js?1" var="addToCart"/>
    <spring:url value="/resources/onready/ready.js?1" var="whenDocReady"/>
    <spring:url value="/resources/i18n/jquery.i18n.properties.js?1" var="i18nJS"/>

    <spring:message code="input.field.placeholder" var="inputPlaceholder"/>
    <spring:message code="search.field.placeholder" var="searchPlaceholder"/>

    <link href="${plpStyles}" rel="stylesheet"/>
    <link rel="stylesheet" href="webjars/bootstrap/4.1.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="webjars/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>
<nav class="navbar navbar-expand-md navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand" href="<c:url value="/productList"/>"><spring:message code="head.app.name"/></a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault"
                aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse justify-content-end" id="navbarsExampleDefault">
            <ul class="navbar-nav m-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="<c:url value="/productList"/>"><spring:message
                            code="link.productlist.name"/><span
                            class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item m-auto">
                    <a class="nav-link" href="<c:url value="/cart"/>"><spring:message code="link.cart.name"/></a>
                </li>
            </ul>
            <form method="post" class="form-inline my-2 my-lg-0">
                <div class="input-group input-group-lg">
                    <input type="text" id="searchField" name="searchField" value="${searchField}" class="form-control"
                           aria-label="Small" aria-describedby="inputGroup-sizing-lg"
                           placeholder="${searchPlaceholder}">
                    <div class="input-group-append">
                        <button type="submit" class="btn btn-info btn-number">
                            <i class="fa fa-search"></i>
                        </button>
                    </div>
                </div>
                <a class="btn btn-success btn-md ml-3" href="<c:url value="/cart"/>">
                    <i class="fa fa-shopping-cart"></i> <spring:message code="link.cart.name"/>
                    <span id="cartSize" class="badge badge-light">${cartSize}</span>
                </a>
                <button class="btn btn-info btn-md ml-3" style="opacity: 1" disabled="disabled">
                    <i class="text-center text-white"><spring:message code="cart.price"/></i> <span id="cartPrice" class="badge badge-light">${cartPrice}</span>
                    <i class="fa fa-dollar"></i>
                </button>
            </form>
        </div>
    </div>
</nav>
<header class="jumbotron text-center ">
    <h1><spring:message code="header.welcome.message"/></h1>
    <p><spring:message code="header.welcome.message.after"/></p>
</header>

<div class="panel panel-info">
    <div class="panel-heading bg-secondary text-center text-white font-italic"><spring:message
            code="info.found.message"/> <c:out value="${phoneAmount}"/>
        <spring:message code="info.phones.message"/>
    </div>
</div>
<script>
    var data = ${data};
</script>
<div class="container-fluid">
    <div class="row">
        <div class="col-sm-auto">
            <form method="post" name="orderByForm" id="orderByForm">
                <table class="table table-hover text-center ">
                    <thead>
                    <tr>
                        <th class="text-center"><spring:message code="phones.field.image"/></th>
                        <th class="text-center"><spring:message code="phones.field.brand"/>
                            <button id="brand" class="ordering">
                                <a>
                                    <div class="fa fa-chevron-up rotate"></div>
                                </a>
                            </button>
                        </th>
                        <th class="text-center"><spring:message code="phones.field.model"/>
                            <button id="model" class="ordering">
                                <a>
                                    <div class="fa fa-chevron-up rotate"></div>
                                </a>
                            </button>
                        </th>
                        <th class="text-center"><spring:message code="phones.field.os"/>
                            <button id="os" class="ordering">
                                <a>
                                    <div class="fa fa-chevron-up rotate"></div>
                                </a>
                            </button>
                        </th>
                        <th class="text-center"><spring:message code="phones.field.display"/>
                            <button id="displaySizeInches" class="ordering">
                                <a>
                                    <div class="fa fa-chevron-up rotate"></div>
                                </a>
                            </button>
                        </th>
                        <th class="text-center"><spring:message code="phones.field.colors"/></th>
                        <th class="text-center"><spring:message code="phones.field.price"/>
                            <button id="price" class="ordering">
                                <a>
                                    <div class="fa fa-chevron-up rotate"></div>
                                </a>
                            </button>
                        </th>
                        <th class="text-center"><spring:message code="phones.field.quantity"/></th>
                        <th class="text-center"><spring:message code="phones.field.add"/></th>
                        <th class="text-center"><spring:message code="phones.field.info"/></th>
                    </tr>
                    </thead>
                    <tbody>

                    <input type="hidden" name="orderBy" id="orderName" value="${orderBy}"/>
                    <input type="hidden" name="orderByAscend" id="orderAscend" value="${isAscend}"/>
                    <input type="hidden" name="orderDataString" id="orderData"/>

                    <c:forEach var="phone" items="${phones}">
                        <tr>
                            <td>
                                <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}">
                            </td>
                            <td>${phone.brand}</td>
                            <td>${phone.model}</td>
                            <td>${phone.os}</td>
                            <td>${phone.displaySizeInches}</td>
                            <td>
                                <c:forEach var="color" items="${phone.colors}">
                                    <p>${color.code}</p>
                                </c:forEach>
                            </td>
                            <td class="text-right"><i class="fa fa-dollar"></i>${phone.price}</td>
                            <td>
                                <input id="input${phone.id}" type="text" class="addition form-control"
                                       aria-label="Small"
                                       aria-describedby="inputGroup-sizing-lg" placeholder="${inputPlaceholder}">
                                <p><span class="success" id="messageSuccess${phone.id}">
                                </span></p>
                                <p><span class="error" id="messageError${phone.id}"></span></p>
                            </td>
                            <td>
                                <button type="submit" id="add${phone.id}" class="addition btn btn-success btn-md ml-3">
                                    <i class="fa fa-plus-circle"></i> <spring:message code="button.add"/>
                                </button>
                            </td>
                            <td>
                                <a href="<c:url value="/productDetails/${phone.id}"/>" type="button" class="btn btn-primary btn-md ml-3">
                                    <i class="fa fa-info-circle"></i> <spring:message code="button.details"/>
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </form>
        </div>
    </div>
</div>
<div class="panel panel-info">
    <div class="panel-heading bg-secondary text-center text-white font-italic"><spring:message
            code="info.current.page"/>
        <c:out value="${currentPage}"/><spring:message code="info.current.page.delimiter"/>${totalPages}</div>
</div>
<nav aria-label="Page navigation" id="show_paginator">
    <form method="post" name="numberForm">
        <input type="hidden" name="currentPage" id="currentPage" value="${currentPage}"/>
        <ul class="pagination pagination-lg justify-content-end">
        </ul>
    </form>
</nav>
<footer class="container-fluid text-center">
    <script src="${i18nJS}"></script>
    <script src="${iconClickHandler}"></script>
    <script src="${pagination}"></script>
    <script src="${addToCart}"></script>
    <script src="${whenDocReady}"></script>
    <p><spring:message code="footer.message"/></p>
</footer>
</body>
</html>
