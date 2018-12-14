<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Product Details Page</title>
    <script src="${pageContext.request.contextPath}/webjars/jquery/3.1.0/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/webjars/bootstrap/4.1.0/js/bootstrap.min.js"></script>

    <spring:url value="/resources/styles/plpStyles.css" var="plpStyles"/>
    <spring:url value="/resources/add2cart/addToCart.js?1" var="addToCart"/>
    <spring:url value="/resources/onready/ready.js?1" var="whenDocReady"/>
    <spring:url value="/resources/i18n/jquery.i18n.properties.js?1" var="i18nJS"/>
    <spring:message code="input.field.placeholder" var="inputPlaceholder"/>
    <spring:message code="search.field.placeholder" var="searchPlaceholder"/>

    <link href="${plpStyles}" rel="stylesheet"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/bootstrap/4.1.0/css/bootstrap.min.css">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/webjars/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand" href="<c:url value="/productList"/>">Phoneshop Spring</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExample07"
                aria-controls="navbarsExample07" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse justify-content-end" id="navbarsExample07">
            <ul class="navbar-nav m-auto">
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value="/productList"/>">Product List<span
                            class="sr-only"></span></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value="/cart"/>">Cart</a>
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
<div class="container">
    <div class="row">
        <div class="col-sm-10">
            <h1>${phone.model} (ID#${phone.id})</h1>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-3"><!--left col-->
            <div class="text-center">
                <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}"
                     class="avatar img-rounded img-thumbnail" alt="Device#${phone.id}">
            </div>
            <div class="card">
                <div class="card-header text-center">Description:</div>
                <div class="card-body">
                    ${phone.description}
                </div>
            </div>
        </div><!--/col-3-->
        <div class="col-sm-9">
            <ul class="nav nav-pills nav-justified">
                <li class="nav-item"><a class="nav-link active" data-toggle="tab" href="#common">Common</a></li>
                <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#colors">Colors</a></li>
                <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#display">Display</a></li>
                <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#physical">Physical</a></li>
                <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#additional">Additional</a></li>
            </ul>
            <div class="tab-content">
                <div class="tab-pane active" id="common">
                    <table class="table table-responsive-sm table-bordered">
                        <tr>
                            <th class="text-center">Announcement</th>
                            <td class="text-center">${phone.announced}</td>
                        </tr>
                        <tr>
                            <th class="text-center">Brand</th>
                            <td class="text-center">${phone.brand}</td>
                        </tr>
                        <tr>
                            <th class="text-center">Model</th>
                            <td class="text-center">${phone.model}</td>
                        </tr>
                        <tr>
                            <th class="text-center">Type</th>
                            <td class="text-center">${phone.deviceType}</td>
                        </tr>
                        <tr>
                            <th class="text-center">Operating System</th>
                            <td class="text-center">${phone.os}</td>
                        </tr>
                        <tr>
                            <th class="text-center">Back Camera</th>
                            <td class="text-center">${phone.backCameraMegapixels} MP</td>
                        </tr>
                        <tr>
                            <th class="text-center">Front Camera</th>
                            <td class="text-center">${phone.frontCameraMegapixels} MP</td>
                        </tr>
                        <tr>
                            <th class="text-center">RAM</th>
                            <td class="text-center">${phone.ramGb} GB</td>
                        </tr>
                        <tr>
                            <th class="text-center">Internal Storage</th>
                            <td class="text-center">${phone.internalStorageGb} GB</td>
                        </tr>
                        <tr>
                            <th class="text-center">Price:</th>
                            <td class="text-center">${phone.price} <a class="fa fa-dollar"></a></td>
                        </tr>
                    </table>
                </div><!--/tab-pane-->
                <div class="tab-pane" id="colors">
                    <table class="table table-responsive-sm table-bordered">
                        <c:choose>
                            <c:when test="${empty phone.colors}">
                                <tr>
                                    <th class="text-center">No Available Colors</th>
                                </tr>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <th class="text-center">Available Colors</th>
                                </tr>
                                <c:forEach var="color" items="${phone.colors}">
                                    <tr>
                                        <td class="text-center">${color.code}</td>
                                    </tr>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </table>
                </div><!--/tab-pane-->
                <div class="tab-pane" id="display">
                    <table class="table table-responsive-sm table-bordered">
                        <tr>
                            <th class="text-center">Display Size</th>
                            <td class="text-center">${phone.displaySizeInches}''</td>
                        </tr>
                        <tr>
                            <th class="text-center">Display Resolution</th>
                            <td class="text-center">${phone.displayResolution}</td>
                        </tr>
                        <tr>
                            <th class="text-center">Pixel Density</th>
                            <td class="text-center">${phone.pixelDensity}</td>
                        </tr>
                        <tr>
                            <th class="text-center">Display Technology</th>
                            <td class="text-center">${phone.displayTechnology}</td>
                        </tr>
                    </table>
                </div><!--/tab-pane-->
                <div class="tab-pane" id="physical">
                    <table class="table table-responsive-sm table-bordered">
                        <tr>
                            <th class="text-center">Weight</th>
                            <td class="text-center">${phone.weightGr} Gr</td>
                        </tr>
                        <tr>
                            <th class="text-center">Length</th>
                            <td class="text-center">${phone.lengthMm} Mm</td>
                        </tr>
                        <tr>
                            <th class="text-center">Width</th>
                            <td class="text-center">${phone.widthMm} Mm</td>
                        </tr>
                        <tr>
                            <th class="text-center">Height</th>
                            <td class="text-center">${phone.heightMm} Mm</td>
                        </tr>
                    </table>
                </div><!--/tab-pane-->
                <div class="tab-pane" id="additional">
                    <table class="table table-responsive-sm table-bordered">
                        <tr>
                            <th class="text-center">Battery Capacity</th>
                            <td class="text-center">${phone.batteryCapacityMah} Mah</td>
                        </tr>
                        <tr>
                            <th class="text-center">Talk Time</th>
                            <td class="text-center">${phone.talkTimeHours} h</td>
                        </tr>
                        <tr>
                            <th class="text-center">Stand By Time</th>
                            <td class="text-center">${phone.standByTimeHours} h</td>
                        </tr>
                        <tr>
                            <th class="text-center">Bluetooth</th>
                            <td class="text-center">${phone.bluetooth}</td>
                        </tr>
                        <tr>
                            <th class="text-center">Positioning</th>
                            <td class="text-center">${phone.positioning}</td>
                        </tr>
                    </table>
                </div><!--/tab-pane-->

            </div><!--/tab-pane-->
            <form method="post">
                <div class="form-group">
                    <div class="col-sm-9">
                        <table class="table table-responsive-sm">
                            <tr>
                                <td>
                                    <input id="input${phone.id}" type="text"
                                           class="addition form-control col-12 text-right"
                                           aria-label="Small"
                                           aria-describedby="inputGroup-sizing-lg"
                                           placeholder="${inputPlaceholder}">
                                    <p class="text-center"><span class="success" id="messageSuccess${phone.id}"></span></p>
                                    <p class="text-center"><span class="error" id="messageError${phone.id}"></span></p>
                                </td>
                                <td>
                                    <button type="submit" id="add${phone.id}"
                                            class="addition btn btn-success btn-md ml-3 col-12 text-center"><i
                                            class="fa fa-plus-circle"></i><spring:message code="button.add"/>
                                    </button>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </form>
        </div><!--/tab-content-->

    </div><!--/col-9-->
</div>
<!--/row-->
<footer class="container-fluid text-center">

    <script src="${i18nJS}"></script>
    <script src="${addToCart}"></script>
    <script src="${whenDocReady}"></script>
    <p><spring:message code="footer.message"/></p>
</footer>
</body>
</html>