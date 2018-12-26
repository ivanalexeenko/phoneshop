<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="style" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <script src="webjars/jquery/3.1.0/jquery.min.js"></script>
    <script src="webjars/bootstrap/4.1.0/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="http://botmonster.com/jquery-bootpag/jquery.bootpag.js"></script>

    <spring:url value="/resources/styles/plpStyles.css" var="plpStyles"/>
    <spring:url value="/resources/updateCart/updateCart.js?1" var="updateCart"/>
    <spring:url value="/resources/cartReady/cartReady.js?1" var="cartReady"/>
    <spring:message code="input.field.placeholder" var="inputPlaceholder"/>
    <spring:message code="search.field.placeholder" var="searchPlaceholder"/>

    <link href="${plpStyles}" rel="stylesheet"/>
    <link rel="stylesheet" href="webjars/bootstrap/4.1.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="webjars/font-awesome/4.7.0/css/font-awesome.min.css">
    <script>
        var phoneIds = ${phoneIds};
    </script>
</head>
<body>
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
                    <a class="nav-link" href="<c:url value="/productList"/>"><spring:message
                            code="link.productlist.name"/><span
                            class="sr-only"></span></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" href="<c:url value="/cart"/>"><spring:message code="link.cart.name"/></a>
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
<section class="jumbotron text-center">
    <div class="container">
        <h1 class="jumbotron-heading"><spring:message code="header.cart.page"/></h1>
    </div>
</section>
<c:choose>
    <c:when test="${empty cartItems}">
        <section class="jumbotron text-center">
            <div class="container text-center">
                <h1 class="jumbotron-heading text-center"><spring:message code="head.cart.page.items.empty"/></h1>
            </div>
        </section>
    </c:when>
    <c:otherwise>
        <div class="container mb-4">
            <div class="row">
                <div class="col-sm-auto">
                    <div class="table-responsive">
                        <form:form method="post" modelAttribute="deleteId">
                            <table class="table table-hover">
                                <thead>
                                <tr>
                                    <th class="text-center" scope="col"></th>
                                    <th class="text-center" scope="col"><spring:message
                                            code="column.cart.page.product"/></th>
                                    <th class="text-center" scope="col"><spring:message
                                            code="column.cart.page.stock"/></th>
                                    <th class="text-center" scope="col"><spring:message
                                            code="column.cart.page.actual.quantity"/></th>
                                    <th class="text-center" scope="col"><spring:message
                                            code="column.cart.page.quantity"/></th>
                                    <th class="text-center" scope="col"><spring:message
                                            code="column.cart.page.price"/></th>
                                    <th class="text-center"></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${phones}" var="phone" varStatus="i">
                                    <tr>
                                        <td><img
                                                src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}">
                                        </td>
                                        <td class="text-center">${phone.model}</td>
                                        <td class="text-center">${stocks[i.index].stock}</td>
                                        <td class="text-center">${cartItems[i.index].quantity}</td>
                                        <td class="text-center">
                                            <label>
                                                <input name="inputQuantity" class="form-control  text-right" type="text"
                                                       value="${quantityStrings[i.index]}"
                                                />
                                            </label>
                                            <p class="text-center text-info">${messages[i.index]}</p>
                                        </td>
                                        <td class="text-right">${phone.price} <i class="fa fa-dollar"></i></td>
                                        <td>
                                            <a href="<c:url value="/productDetails/${phone.id}"/>" type="button"
                                               class="btn btn-primary">
                                                <i class="fa fa-info-circle"></i> <spring:message
                                                    code="button.details"/>
                                            </a>
                                            <button name="buttonDelete" type="submit"
                                                    class="btn btn-danger">
                                                <i class="fa fa-trash"></i> <spring:message code="button.delete.text"/>
                                            </button>
                                        <form:input type="hidden" value="${phone.id}" path="id"/>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </form:form>
                    </div>
                    <form id="updateForm" method="post">
                        <input type="hidden" name="_method" value="put">
                        <div class="btn-group col-6 float-right">
                            <a class="btn btn-outline-warning btn-md ml-3 col-12 text-center"
                               href="<c:url value="/productList"/>" type="button" style="color: black">
                                <i class="fa fa-arrow-left"></i> <spring:message code="back.to.plp.text"/>
                            </a>
                            <button id="submitUpdate" class="btn btn-outline-primary btn-md ml-3 col-12 text-center"
                                    type="submit">
                                <i class="fa fa-repeat"></i> <spring:message code="button.update"/>
                            </button>
                            <a class="btn btn-outline-warning btn-md ml-3 col-12 text-center" type="button"
                               style="color: black" href="<c:url value="/order"/>">
                                <spring:message code="link.order.name"/><i class="fa fa-arrow-right"></i>
                            </a>
                        </div>
                        <c:forEach items="${phones}" var="phone">
                            <input name="hiddenQuantity" type="hidden" value="">
                            <input name="hiddenPhoneId" type="hidden" value="">
                        </c:forEach>
                    </form>
                </div>
            </div>
        </div>
    </c:otherwise>
</c:choose>
<footer class="container-fluid text-center">
    <script src="${updateCart}"></script>
    <script src="${cartReady}"></script>
    <p><spring:message code="footer.message"/></p>
</footer>
</body>
</html>