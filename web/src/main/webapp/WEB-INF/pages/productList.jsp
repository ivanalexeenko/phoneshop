<html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <link rel="stylesheet" href="webjars/bootstrap/4.1.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="webjars/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="webjars/jquery/3.1.0/jquery.min.js"></script>
    <script src="webjars/bootstrap/4.1.0/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="http://botmonster.com/jquery-bootpag/jquery.bootpag.js"></script>
    <title>Product List Page</title>
    <jsp:useBean id="data" type="java.util.List" scope="session"/>
    <jsp:include page="/WEB-INF/pages/styles/PLP_styles.jsp"/>
    <jsp:include page="/WEB-INF/pages/sorting/iconClickHandler.jsp"/>
    <style type="text/css">
        span.error {
            color: darkred;
        }

        span.success {
            color: darkgreen;
        }
    </style>


</head>
<body>
<nav class="navbar navbar-expand-md navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand" href="<c:url value="/productList"/>">Phoneshop Spring</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault"
                aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse justify-content-end" id="navbarsExampleDefault">
            <ul class="navbar-nav m-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="<c:url value="/productList"/>">Product List<span
                            class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item m-auto">
                    <a class="nav-link" href="<c:url value="/cart"/>">Cart</a>
                </li>
            </ul>
            <form method="post" class="form-inline my-2 my-lg-0">
                <div class="input-group input-group-lg">
                    <input type="text" id="searchField" name="searchField" value="${searchField}" class="form-control"
                           aria-label="Small" aria-describedby="inputGroup-sizing-lg" placeholder="Search...">
                    <div class="input-group-append">
                        <button type="submit" class="btn btn-info btn-number">
                            <i class="fa fa-search"></i>
                        </button>
                    </div>
                </div>
                <a class="btn btn-success btn-md ml-3" href="<c:url value="/cart"/>">
                    <i class="fa fa-shopping-cart"></i> Cart
                    <span id="cartSize" class="badge badge-light">${cartSize}</span>
                </a>
            </form>
        </div>
    </div>
</nav>

<header class="jumbotron text-center ">
    <h1>Welcome to the Product List Page!</h1>
    <p>Feel free to select yours =)</p>
</header>

<div class="panel panel-info">
    <div class="panel-heading bg-secondary text-center text-white font-italic">Found <c:out value="${phoneAmount}"/>
        phones:
    </div>
</div>

<div class="container-fluid">
    <div class="row">
        <div class="col-sm-auto">
            <form method="post" name="orderByForm">
                <table class="table table-hover text-center ">
                    <thead>
                    <tr>
                        <th class="text-center">Image</th>
                        <th class="text-center">Brand
                            <button onclick="handleClick(this)" id="brand" name="buttonOrder" style="background: none">
                                <a>
                                    <div class="fa fa-chevron-up rotate"></div>
                                </a>
                            </button>
                        </th>
                        <th class="text-center">Model
                            <button onclick="handleClick(this)" id="model" name="buttonOrder" style="background: none">
                                <a>
                                    <div class="fa fa-chevron-up rotate"></div>
                                </a>
                            </button>
                        </th>
                        <th class="text-center">OS
                            <button onclick="handleClick(this)" id="os" name="buttonOrder" style="background: none">
                                <a>
                                    <div class="fa fa-chevron-up rotate"></div>
                                </a>
                            </button>
                        </th>
                        <th class="text-center">Display(inches)
                            <button onclick="handleClick(this)" id="displaySizeInches" name="buttonOrder"
                                    style="background: none">
                                <a>
                                    <div class="fa fa-chevron-up rotate"></div>
                                </a>
                            </button>
                        </th>
                        <th class="text-center">Colors</th>
                        <th class="text-center">Price
                            <button onclick="handleClick(this)" id="price" name="buttonOrder" style="background: none">
                                <a>
                                    <div class="fa fa-chevron-up rotate"></div>
                                </a>
                            </button>
                        </th>
                        <th class="text-center">Quantity</th>
                        <th class="text-center"></th>
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
                                <input id="input${phone.id}" type="text" class="form-control" aria-label="Small"
                                       aria-describedby="inputGroup-sizing-lg" placeholder="Enter quantity...">
                            </td>
                            <td>
                                <button type="submit" id="add${phone.id}" class="btn btn-success btn-md ml-3">
                                    <i class="fa fa-plus-circle"></i> Add to Cart
                                </button>
                                <script>
                                    $("#add${phone.id}").click(function (event) {
                                        event.preventDefault();
                                        var cartItem = {};
                                        cartItem["phoneId"] = ${phone.id};
                                        cartItem["quantity"] = $("#input${phone.id}").val();
                                        $("#input${phone.id}").find("p").find("span").text("");
                                        $.ajax({
                                            type: "POST",
                                            data: JSON.stringify(cartItem),
                                            contentType: "application/json",
                                            dataType: 'json',
                                            url: "ajaxCart",
                                            success: function (res) {
                                                if (res.isValidated) {
                                                    $("#input${phone.id}").after('<p><span class="success">' + res.successMessage + '</span></p>');
                                                }
                                                else {
                                                    $.each(res.errorMessages, function (key, value) {
                                                        $("#input${phone.id}").after('<p><span class="error">' + value + '</span></p>');
                                                    });
                                                }
                                                $("#cartSize").text(res.cartSize);
                                            }
                                        })
                                    });

                                </script>
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
    <div class="panel-heading bg-secondary text-center text-white font-italic">Page
        <c:out value="${currentPage}"/>/${totalPages}</div>
</div>

<jsp:include page="/WEB-INF/pages/pagination/paginator.jsp"/>

<footer class="container-fluid text-center">
    <p>Expert Soft feat. Ivan Alexeenko Spring Project</p>
</footer>
</body>
</html>