<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="style" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title><spring:message code="title.order"/></title>
    <jsp:include page="/resources/common/includes/incheader.jsp"/>
</head>
<body>
<jsp:include page="/resources/common/header.jsp"/>
<section class="jumbotron text-center">
    <div class="container">
        <h1 class="jumbotron-heading"><spring:message code="order.page.header"/></h1>
    </div>
</section>
<c:choose>
    <c:when test="${empty cartItems}">
        <section class="jumbotron text-center">
            <div class="container text-center">
                <h1 class="jumbotron-heading text-center"><spring:message code="order.page.empty"/></h1>
            </div>
        </section>
    </c:when>
    <c:otherwise>
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
                                <th class="text-center"></th>
                                <th class="text-center"><spring:message code="order.item.message"/></th>
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
                                    <td class="text-center">${cartItems[i.index].quantity}</td>
                                    <td class="text-center">${phone.price} <i class="fa fa-dollar"></i></td>
                                    <td class="text-center">
                                        <a href="<c:url value="/productDetails/${phone.id}"/>" type="button"
                                           class="btn btn-primary">
                                            <i class="fa fa-info-circle"></i> <spring:message
                                                code="button.details"/>
                                        </a>
                                    </td>
                                    <td class="text-center text-info">${messages[i.index]}</td>
                                </tr>
                            </c:forEach>
                            <tr>
                                <td class="text-center"></td>
                                <td class="text-center"></td>
                                <td class="text-center"></td>
                                <td class="text-center"></td>
                                <td class="text-center"></td>
                                <td class="text-center"><strong><spring:message code="order.subtotal"/></strong></td>
                                <td class="text-center"><strong>${subtotalPrice} <i class="fa fa-dollar"></i></strong>
                                </td>
                            </tr>
                            <tr>
                                <td class="text-center"></td>
                                <td class="text-center"></td>
                                <td class="text-center"></td>
                                <td class="text-center"></td>
                                <td class="text-center"></td>
                                <td class="text-center"><strong><spring:message code="order.delivery"/></strong></td>
                                <td class="text-center"><strong>${deliveryPrice} <i class="fa fa-dollar"></i></strong>
                                </td>
                            </tr>
                            <tr>
                                <td class="text-center"></td>
                                <td class="text-center"></td>
                                <td class="text-center"></td>
                                <td class="text-center"></td>
                                <td class="text-center"></td>
                                <td class="text-center"><strong><spring:message code="order.total"/></strong></td>
                                <td class="text-center"><strong>${totalPrice} <i class="fa fa-dollar"></i></strong></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <form method="post">
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
                                            <input name="orderInput" value="${orderInputs[0]}"
                                                   class="form-control text-left" type="text"/>
                                        </label>
                                        <c:if test="${not empty inputMessages[0]}">
                                            <p class="text-center text-danger">${inputMessages[0]}</p>
                                        </c:if>
                                    </td>
                                </tr>
                                <tr>
                                    <th><spring:message code="order.confirm.last.name"/></th>
                                    <td class="text-center">
                                        <label>
                                            <input name="orderInput" value="${orderInputs[1]}"
                                                   class="form-control text-left" type="text"/>
                                        </label>
                                        <c:if test="${not empty inputMessages[1]}">
                                            <p class="text-center text-danger">${inputMessages[1]}</p>
                                        </c:if>
                                    </td>
                                </tr>
                                <tr>
                                    <th><spring:message code="order.confirm.address"/></th>
                                    <td class="text-center">
                                        <label>
                                            <input name="orderInput" value="${orderInputs[2]}"
                                                   class="form-control text-left" type="text"/>
                                        </label>
                                        <c:if test="${not empty inputMessages[2]}">
                                            <p class="text-center text-danger">${inputMessages[2]}</p>
                                        </c:if>
                                    </td>
                                </tr>
                                <tr>
                                    <th><spring:message code="order.confirm.phone"/></th>
                                    <td class="text-center">
                                        <label>
                                            <input name="orderInput" value="${orderInputs[3]}"
                                                   class="form-control text-left" type="text"/>
                                        </label>
                                        <c:if test="${not empty inputMessages[3]}">
                                            <p class="text-center text-danger">${inputMessages[3]}</p>
                                        </c:if>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2">
                                        <div class="form-group">
                                            <label for="comment"><spring:message
                                                    code="order.confirm.description"/></label>
                                            <textarea class="form-control" rows="5" name="orderInput"
                                                      id="comment">${orderInputs[4]}</textarea>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <button type="submit"
                                                class="btn btn-primary text-white">
                                            <i class="fa fa-hand-grab-o"></i> <spring:message code="order.submit"/>
                                        </button>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </c:otherwise>
</c:choose>
<jsp:include page="/resources/common/footer.jsp"/>
</body>
</html>