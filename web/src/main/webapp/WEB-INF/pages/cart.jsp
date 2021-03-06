<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="style" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title><spring:message code="title.cart"/></title>
    <jsp:include page="/resources/common/includes/incheader.jsp"/>
    <spring:url value="/resources/updateCart/updateCart.js?1" var="updateCart"/>
    <spring:url value="/resources/cartReady/cartReady.js?1" var="cartReady"/>
    <script>
        var phoneIds = ${phoneIds};
    </script>
</head>
<body>
<jsp:include page="/resources/common/header.jsp"/>
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
                        <form method="post">
                            <table class="table table-hover">
                                <thead>
                                <tr>
                                    <th class="text-center" scope="col"></th>
                                    <th class="text-center" scope="col"><spring:message
                                            code="column.cart.page.product"/></th>
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
                                            <button name="deleteId" value="${phone.id}" type="submit"
                                                    class="btn btn-danger">
                                                <i class="fa fa-trash"></i> <spring:message code="button.delete.text"/>
                                            </button>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </form>
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
                                <spring:message code="link.order.name"/> <i class="fa fa-arrow-right"></i>
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
<script src="${updateCart}"></script>
<script src="${cartReady}"></script>
<jsp:include page="/resources/common/footer.jsp"/>
</body>
</html>