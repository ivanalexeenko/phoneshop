<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="style" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title><spring:message code="title.order.overview"/></title>
    <jsp:include page="/resources/common/includes/incheader.jsp"/>
</head>
<body>
<jsp:include page="/resources/common/header.jsp"/>
<c:choose>
    <c:when test="${not empty order}">
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
        <div class="container mb-4">
            <div class="row">
                <div class="col-sm-auto">
                    <div class="table-responsive">
                        <table class="table table-hover">
                            <thead>
                            <tr>
                                <th class="text-center" scope="col"><spring:message code="order.item.number"/></th>
                                <th class="text-center" scope="col"><spring:message
                                        code="phones.field.model"/></th>
                                <th class="text-center" scope="col"><spring:message
                                        code="phones.field.colors"/></th>
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
                                    <td class="text-center">${phone.model}</td>
                                    <td class="text-center">
                                        <c:forEach var="color" items="${phone.colors}">
                                            <p>${color.code}</p>
                                        </c:forEach>
                                    </td>
                                    <td class="text-center">${order.orderItems[i.index].quantity}</td>
                                    <td class="text-center">${phone.price} <i class="fa fa-dollar"></i></td>
                                </tr>
                            </c:forEach>
                            <tr>
                                <td class="text-center"></td>
                                <td class="text-center"></td>
                                <td class="text-center"></td>
                                <td class="text-center"><strong><spring:message code="order.subtotal"/></strong></td>
                                <td class="text-center"><strong>${order.subtotal} <i class="fa fa-dollar"></i></strong>
                                </td>
                            </tr>
                            <tr>
                                <td class="text-center"></td>
                                <td class="text-center"></td>
                                <td class="text-center"></td>
                                <td class="text-center"><strong><spring:message code="order.delivery"/></strong></td>
                                <td class="text-center"><strong>${order.deliveryPrice} <i
                                        class="fa fa-dollar"></i></strong>
                                </td>
                            </tr>
                            <tr>
                                <td class="text-center"></td>
                                <td class="text-center"></td>
                                <td class="text-center"></td>
                                <td class="text-center"><strong><spring:message code="order.total"/></strong></td>
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
                </div>
            </div>
        </div>
    </c:when>
    <c:otherwise>
        <section class="jumbotron text-center">
            <div class="container">
                <h1 class="jumbotron-heading"><spring:message code="order.not.found"/></h1>
            </div>
        </section>
    </c:otherwise>
</c:choose>
<jsp:include page="/resources/common/footer.jsp"/>
</body>
</html>