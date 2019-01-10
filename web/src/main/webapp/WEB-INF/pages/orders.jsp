<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="style" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title><spring:message code="title.orders"/></title>
    <jsp:include page="/resources/common/includes/incheader.jsp"/>
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
                    <a class="nav-link" href="<c:url value="/admin/orders"/>"><spring:message code="title.orders"/><span
                            class="sr-only"></span></a>
            </ul>
        </div>
    </div>
</nav>
<c:choose>
    <c:when test="${empty orders}">
        <c:choose>
            <c:when test="${not empty order}">
                <section class="jumbotron text-center">
                    <div class="container text-center">
                        <h1 class="jumbotron-heading text-center"><spring:message
                                code="orders.order.number"/>:${order.id}</h1>
                    </div>
                    <div class="container text-center">
                        <h1 class="jumbotron-heading text-center">
                            <spring:message code="orders.status"/>:
                            <button class="btn btn-info btn-lg ml-3" style="opacity: 1" disabled="disabled">
                                <i class="text-center text-white"></i> <span
                                    class="badge badge-light">${order.status}</span>
                            </button>
                        </h1>
                    </div>
                </section>
                <div class="container mb-4">
                    <div class="row">
                        <div class="col-sm-auto">
                            <div class="table-responsive">
                                <table class="table table-hover">
                                    <thead>
                                    <tr>
                                        <th class="text-center" scope="col"><spring:message
                                                code="order.item.number"/></th>
                                        <th class="text-center" scope="col"><spring:message
                                                code="phones.field.brand"/></th>
                                        <th class="text-center" scope="col"><spring:message
                                                code="phones.field.model"/></th>
                                        <th class="text-center" scope="col"><spring:message
                                                code="phones.field.colors"/></th>
                                        <th class="text-center" scope="col"><spring:message
                                                code="phones.field.display"/></th>
                                        <th class="text-center" scope="col"><spring:message
                                                code="column.cart.page.actual.quantity"/></th>
                                        <th class="text-center" scope="col"><spring:message
                                                code="column.cart.page.price"/></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${phones}" var="phone" varStatus="i">
                                        <tr>
                                            <td class="text-center">${i.index+1}</td>
                                            <td class="text-center">${phone.brand}</td>
                                            <td class="text-center">${phone.model}</td>
                                            <td class="text-center">
                                                <c:forEach var="color" items="${phone.colors}">
                                                    <p>${color.code}</p>
                                                </c:forEach>
                                            </td>
                                            <td class="text-center">${phone.displaySizeInches}</td>
                                            <td class="text-center">${order.orderItems[i.index].quantity}</td>
                                            <td class="text-center">${phone.price} <i class="fa fa-dollar"></i></td>
                                        </tr>
                                    </c:forEach>
                                    <tr>
                                        <td class="text-center"></td>
                                        <td class="text-center"></td>
                                        <td class="text-center"></td>
                                        <td class="text-center"></td>
                                        <td class="text-center"></td>
                                        <td class="text-center"><strong><spring:message code="order.subtotal"/></strong>
                                        </td>
                                        <td class="text-center"><strong>${order.subtotal} <i
                                                class="fa fa-dollar"></i></strong>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-center"></td>
                                        <td class="text-center"></td>
                                        <td class="text-center"></td>
                                        <td class="text-center"></td>
                                        <td class="text-center"></td>
                                        <td class="text-center"><strong><spring:message code="order.delivery"/></strong>
                                        </td>
                                        <td class="text-center"><strong>${order.deliveryPrice} <i
                                                class="fa fa-dollar"></i></strong>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="text-center"></td>
                                        <td class="text-center"></td>
                                        <td class="text-center"></td>
                                        <td class="text-center"></td>
                                        <td class="text-center"></td>
                                        <td class="text-center"><strong><spring:message code="order.total"/></strong>
                                        </td>
                                        <td class="text-center"><strong>${order.totalPrice} <i
                                                class="fa fa-dollar"></i></strong></td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="container mb-4">
                    <div class="row">
                        <div class="col-sm-auto">
                            <div class="table-responsive">
                                <table class="table table-hover align-middle">
                                    <thead>
                                    <tr>
                                        <td colspan="2">
                                            <h3 class="text-center"><strong><spring:message
                                                    code="order.confirm.header"/></strong></h3>
                                        </td>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <th><spring:message code="order.confirm.first.name"/></th>
                                        <td class="text-center">
                                            <label>
                                                <input name="orderInput" value="${order.firstName}"
                                                       class="form-control text-left" type="text" disabled/>
                                            </label>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th><spring:message code="order.confirm.last.name"/></th>
                                        <td class="text-center">
                                            <label>
                                                <input name="orderInput" value="${order.lastName}"
                                                       class="form-control text-left" type="text" disabled/>
                                            </label>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th><spring:message code="order.confirm.address"/></th>
                                        <td class="text-center">
                                            <label>
                                                <input name="orderInput" value="${order.deliveryAddress}"
                                                       class="form-control text-left" type="text" disabled/>
                                            </label>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th><spring:message code="order.confirm.phone"/></th>
                                        <td class="text-center">
                                            <label>
                                                <input name="orderInput" value="${order.contactPhoneNo}"
                                                       class="form-control text-left" type="text" disabled/>
                                            </label>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="2">
                                            <div class="form-group">
                                                <label for="comment"><spring:message
                                                        code="order.confirm.description"/></label>
                                                <textarea class="form-control" rows="5" name="orderInput"
                                                          disabled id="comment">${order.description} </textarea>
                                            </div>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                            <c:choose>
                                <c:when test="${not statusSet}">
                                    <form method="post">
                                        <div class="btn-group col-6 float-right">
                                            <a class="btn btn-outline-warning btn-md ml-3 col-12 text-center"
                                               href="<c:url value="/admin/orders"/>" type="button" style="color: black">
                                                <i class="fa fa-arrow-left"></i> <spring:message
                                                    code="back.to.orders.text"/>
                                            </a>
                                            <button name="newStatus" value="0"
                                                    class="btn btn-outline-success btn-md ml-3 col-12 text-center"
                                                    type="submit">
                                                <i class="fa fa-truck"></i> <spring:message
                                                    code="button.set.delivered"/>
                                            </button>
                                            <button name="newStatus" value="1"
                                                    class="btn btn-outline-danger btn-md ml-3 col-12 text-center"
                                                    type="submit">
                                                <i class="fa fa-ban"></i> <spring:message code="button.set.rejected"/>
                                            </button>
                                        </div>
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <div class="btn-group col-6 float-right">
                                        <a class="btn btn-outline-warning btn-md ml-3 col-12 text-center"
                                           href="<c:url value="/admin/orders"/>" type="button" style="color: black">
                                            <i class="fa fa-arrow-left"></i> <spring:message
                                                code="back.to.orders.text"/>
                                        </a>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>

                </div>
            </c:when>
            <c:otherwise>
                <section class="jumbotron text-center">
                    <div class="container text-center">
                        <h1 class="jumbotron-heading text-center"><spring:message code="orders.page.empty"/></h1>
                    </div>
                </section>
            </c:otherwise>
        </c:choose>
    </c:when>
    <c:otherwise>
        <section class="jumbotron text-center">
            <div class="container">
                <h1 class="jumbotron-heading"><spring:message code="orders.page.header"/></h1>
            </div>
        </section>
        <div class="container mb-4">
            <div class="row">
                <div class="col-sm-auto">
                    <div class="table-responsive">
                        <table class="table table-hover">
                            <thead>
                            <tr>
                                <th class="text-center" scope="col"><spring:message code="orders.order.number"/></th>
                                <th class="text-center" scope="col"><spring:message
                                        code="orders.customer"/></th>
                                <th class="text-center" scope="col"><spring:message
                                        code="orders.phone"/></th>
                                <th class="text-center" scope="col"><spring:message
                                        code="orders.address"/></th>
                                <th class="text-center" scope="col"><spring:message
                                        code="orders.price"/></th>
                                <th class="text-center" scope="col"><spring:message
                                        code="orders.status"/></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${orders}" var="order" varStatus="i">
                                <tr>
                                    <td class="text-center">
                                        <a href="<c:url value="orders/${order.id}"/>" type="button"
                                           class="btn btn-primary">
                                            <i class="fa fa-info-circle"></i>
                                                ${order.id}
                                        </a>
                                    </td>
                                    <td class="text-center">${order.firstName} ${order.lastName}</td>
                                    <td class="text-center">${order.contactPhoneNo}</td>
                                    <td class="text-center">${order.deliveryAddress}</td>
                                    <td class="text-center">${order.totalPrice} <i class="fa fa-dollar"></i></td>
                                    <td class="text-center">${order.status}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </c:otherwise>
</c:choose>
<jsp:include page="/resources/common/footer.jsp"/>
</body>
</html>