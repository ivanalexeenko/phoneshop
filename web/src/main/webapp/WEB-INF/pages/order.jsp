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
    <spring:message code="input.field.placeholder" var="inputPlaceholder"/>
    <spring:message code="search.field.placeholder" var="searchPlaceholder"/>

    <link href="${plpStyles}" rel="stylesheet"/>
    <link rel="stylesheet" href="webjars/bootstrap/4.1.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="webjars/font-awesome/4.7.0/css/font-awesome.min.css">
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
        <h1 class="jumbotron-heading">Your Order</h1>
    </div>
</section>
<c:choose>
    <c:when test="${empty cartItems}">
        <section class="jumbotron text-center">
            <div class="container text-center">
                <h1 class="jumbotron-heading text-center">Oops,Your Order is Empty</h1>
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
                                <th class="text-center" scope="col"><spring:message
                                        code="phones.field.model"/></th>
                                <th class="text-center" scope="col"><spring:message
                                        code="phones.field.colors"/></th>
                                <th class="text-center" scope="col"><spring:message
                                        code="column.cart.page.stock"/></th>
                                <th class="text-center" scope="col"><spring:message
                                        code="column.cart.page.actual.quantity"/></th>
                                <th class="text-center" scope="col"><spring:message
                                        code="column.cart.page.price"/></th>
                                <th class="text-center"></th>
                                <th>Message</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${phones}" var="phone" varStatus="i">
                                <tr>
                                    <td class="text-center">${phone.model}</td>
                                    <td>
                                        <c:forEach var="color" items="${phone.colors}">
                                            <p>${color.code}</p>
                                        </c:forEach>
                                    </td>
                                    <td class="text-center">${stocks[i.index].stock}</td>
                                    <td class="text-center">${cartItems[i.index].quantity}</td>
                                    <td class="text-right">${phone.price} <i class="fa fa-dollar"></i></td>
                                    <td>
                                        <a href="<c:url value="/productDetails/${phone.id}"/>" type="button"
                                           class="btn btn-primary">
                                            <i class="fa fa-info-circle"></i> <spring:message
                                                code="button.details"/>
                                        </a>
                                    </td>
                                    <td class="text-center text-info">${messages[i.index]}</td>
                                    <td></td>
                                </tr>
                            </c:forEach>
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
                                    <h3 class="text-center"><strong>Customer and Order Information:</strong></h3>
                                </td>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <th>First Name:</th>
                                <td class="text-center">
                                    <label>
                                        <input name="firstName" class="form-control text-left" type="text"/>
                                    </label>
                                    <p class="text-center text-info"></p>
                                </td>
                            </tr>
                            <tr>
                                <th>Last Name:</th>
                                <td class="text-center">
                                    <label>
                                        <input name="lastName" class="form-control text-left" type="text"/>
                                    </label>
                                    <p class="text-center text-info"></p>
                                </td>
                            </tr>
                            <tr>
                                <th>Address:</th>
                                <td class="text-center">
                                    <label>
                                        <input name="address" class="form-control text-left" type="text"/>
                                    </label>
                                    <p class="text-center text-info"></p>
                                </td>
                            </tr>
                            <tr>
                                <th>Phone:</th>
                                <td class="text-center">
                                    <label>
                                        <input name="phone" class="form-control text-left" type="text"/>
                                    </label>
                                    <p class="text-center text-info"></p>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <div class="form-group">
                                        <label for="comment">Description:</label>
                                        <textarea class="form-control" rows="5" id="comment"></textarea>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <a type="button"
                                       class="btn btn-primary text-white">
                                        <i class="fa fa-hand-grab-o"></i> I'll take it!
                                    </a>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </c:otherwise>
</c:choose>
<footer class="container-fluid text-center">
    <p><spring:message code="footer.message"/></p>
</footer>
</body>
</html>