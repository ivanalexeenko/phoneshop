<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title><spring:message code="title.pdp"/></title>
    <jsp:include page="/resources/common/includes/incheader.jsp"/>
    <spring:url value="/resources/add2cart/addToCart.js?1" var="addToCart"/>
    <spring:url value="/resources/onready/ready.js?1" var="whenDocReady"/>
    <spring:url value="/resources/i18n/jquery.i18n.properties.js?1" var="i18nJS"/>
</head>
<body>
<jsp:include page="/resources/common/header.jsp"/>
<div class="container">
    <div class="row">
        <div class="col-sm-10">
            <h1>${phone.model} (ID#${phone.id})</h1>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-3">
            <div class="text-center">
                <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}"
                     class="avatar img-rounded img-thumbnail" alt="Device#${phone.id}">
            </div>
            <div class="card">
                <div class="card-header text-center"><spring:message code="phone.description.head"/></div>
                <div class="card-body">
                    ${phone.description}
                </div>
            </div>
        </div>
        <div class="col-sm-9">
            <ul class="nav nav-pills nav-justified">
                <li class="nav-item"><a class="nav-link active" data-toggle="tab" href="#common"><spring:message
                        code="tab.name.common"/></a></li>
                <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#colors"><spring:message
                        code="tab.name.colors"/></a></li>
                <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#display"><spring:message
                        code="tab.name.display"/></a></li>
                <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#physical"><spring:message
                        code="tab.name.physical"/></a></li>
                <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#additional"><spring:message
                        code="tab.name.additional"/></a></li>
            </ul>
            <div class="tab-content">
                <div class="tab-pane active" id="common">
                    <table class="table table-responsive-sm table-bordered">
                        <tr>
                            <th class="text-center"><spring:message code="tab.name.common.announce"/></th>
                            <td class="text-center">${phone.announced}</td>
                        </tr>
                        <tr>
                            <th class="text-center"><spring:message code="tab.name.common.brand"/></th>
                            <td class="text-center">${phone.brand}</td>
                        </tr>
                        <tr>
                            <th class="text-center"><spring:message code="tab.name.common.model"/></th>
                            <td class="text-center">${phone.model}</td>
                        </tr>
                        <tr>
                            <th class="text-center"><spring:message code="tab.name.common.type"/></th>
                            <td class="text-center">${phone.deviceType}</td>
                        </tr>
                        <tr>
                            <th class="text-center"><spring:message code="tab.name.common.os"/></th>
                            <td class="text-center">${phone.os}</td>
                        </tr>
                        <tr>
                            <th class="text-center"><spring:message code="tab.name.common.camera.back"/></th>
                            <td class="text-center">${phone.backCameraMegapixels} <spring:message
                                    code="tab.name.common.camera.back.measure"/></td>
                        </tr>
                        <tr>
                            <th class="text-center"><spring:message code="tab.name.common.camera.front"/></th>
                            <td class="text-center">${phone.frontCameraMegapixels} <spring:message
                                    code="tab.name.common.camera.front.measure"/></td>
                        </tr>
                        <tr>
                            <th class="text-center"><spring:message code="tab.name.common.ram"/></th>
                            <td class="text-center">${phone.ramGb} <spring:message
                                    code="tab.name.common.ram.measure"/></td>
                        </tr>
                        <tr>
                            <th class="text-center"><spring:message code="tab.name.common.storage"/></th>
                            <td class="text-center">${phone.internalStorageGb} <spring:message
                                    code="tab.name.common.storage.measure"/></td>
                        </tr>
                        <tr>
                            <th class="text-center"><spring:message code="tab.name.common.price"/></th>
                            <td class="text-center">${phone.price} <a class="fa fa-dollar"></a></td>
                        </tr>
                    </table>
                </div>
                <div class="tab-pane" id="colors">
                    <table class="table table-responsive-sm table-bordered">
                        <c:choose>
                            <c:when test="${empty phone.colors}">
                                <tr>
                                    <th class="text-center"><spring:message code="tab.name.colors.not.available"/></th>
                                </tr>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <th class="text-center"><spring:message code="tab.name.colors.available"/></th>
                                </tr>
                                <c:forEach var="color" items="${phone.colors}">
                                    <tr>
                                        <td class="text-center">${color.code}</td>
                                    </tr>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </table>
                </div>
                <div class="tab-pane" id="display">
                    <table class="table table-responsive-sm table-bordered">
                        <tr>
                            <th class="text-center"><spring:message code="tab.name.display.size"/></th>
                            <td class="text-center">${phone.displaySizeInches}<spring:message
                                    code="tab.name.display.size.measure"/></td>
                        </tr>
                        <tr>
                            <th class="text-center"><spring:message code="tab.name.display.resolution"/></th>
                            <td class="text-center">${phone.displayResolution}</td>
                        </tr>
                        <tr>
                            <th class="text-center"><spring:message code="tab.name.display.density"/></th>
                            <td class="text-center">${phone.pixelDensity}</td>
                        </tr>
                        <tr>
                            <th class="text-center"><spring:message code="tab.name.display.tech"/></th>
                            <td class="text-center">${phone.displayTechnology}</td>
                        </tr>
                    </table>
                </div>
                <div class="tab-pane" id="physical">
                    <table class="table table-responsive-sm table-bordered">
                        <tr>
                            <th class="text-center"><spring:message code="tab.name.physical.weight"/></th>
                            <td class="text-center">${phone.weightGr} <spring:message
                                    code="tab.name.physical.weight.measure"/></td>
                        </tr>
                        <tr>
                            <th class="text-center"><spring:message code="tab.name.physical.length"/></th>
                            <td class="text-center">${phone.lengthMm} <spring:message
                                    code="tab.name.physical.length.measure"/></td>
                        </tr>
                        <tr>
                            <th class="text-center"><spring:message code="tab.name.physical.width"/></th>
                            <td class="text-center">${phone.widthMm} <spring:message
                                    code="tab.name.physical.width.measure"/></td>
                        </tr>
                        <tr>
                            <th class="text-center"><spring:message code="tab.name.physical.height"/></th>
                            <td class="text-center">${phone.heightMm} <spring:message
                                    code="tab.name.physical.height.measure"/></td>
                        </tr>
                    </table>
                </div>
                <div class="tab-pane" id="additional">
                    <table class="table table-responsive-sm table-bordered">
                        <tr>
                            <th class="text-center"><spring:message code="tab.name.additional.battery.capacity"/></th>
                            <td class="text-center">${phone.batteryCapacityMah} <spring:message
                                    code="tab.name.additional.battery.capacity.measure"/></td>
                        </tr>
                        <tr>
                            <th class="text-center"><spring:message code="tab.name.additional.talk.time"/></th>
                            <td class="text-center">${phone.talkTimeHours} <spring:message
                                    code="tab.name.additional.talk.time.measure"/></td>
                        </tr>
                        <tr>
                            <th class="text-center"><spring:message code="tab.name.additional.standby.time"/></th>
                            <td class="text-center">${phone.standByTimeHours} <spring:message
                                    code="tab.name.additional.standby.time.measure"/></td>
                        </tr>
                        <tr>
                            <th class="text-center"><spring:message code="tab.name.additional.bluetooth"/></th>
                            <td class="text-center">${phone.bluetooth}</td>
                        </tr>
                        <tr>
                            <th class="text-center"><spring:message code="tab.name.additional.positioning"/></th>
                            <td class="text-center">${phone.positioning}</td>
                        </tr>
                    </table>
                </div>
            </div>
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
                                    <p class="text-center"><span class="success" id="messageSuccess${phone.id}"></span>
                                    </p>
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
        </div>
        <div class="col-sm-3">
            <div class="text-center">
                <h1>Comments</h1>
            </div>
            <div class="card">
                <div class="text-center text-info">
                    <c:if test="${empty comments}">
                        <h2>No comments yet, be the first one to comment!</h2>
                    </c:if>
                </div>
                <div class="list-group list-group-flush" style="flex-grow: 1;overflow-y: scroll">
                    <c:if test="${not empty comments}">
                        <c:forEach items="${comments}" var="comment">
                            <p>Name:${comment.name}</p>
                            <p>Rating:${comment.rating}</p>
                            <a class="list-group-item list-group-item-action"><b>${comment.comment}</b></a>
                        </c:forEach>
                    </c:if>
                </div>
            </div>
        </div>
        <form method="post">
            <div class="col-sm-3">
                <div class="container mb-4">
                    <div class="row">
                        <div class="col-sm-auto">
                            <div class="table-responsive">
                                <table class="table table-hover align-middle">
                                    <thead>
                                    <tr>
                                        <td colspan="2">
                                            <h3 class="text-center"><strong>
                                                Leave Your Comment Here
                                            </strong></h3>
                                        </td>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <th>Name:</th>
                                        <td class="text-center">
                                            <label>
                                                <input name="commentInput" value="${commentInputs[0]}"
                                                       class="form-control text-left" type="text"/>
                                            </label>
                                            <c:if test="${not empty inputMessages[0]}">
                                                <p class="text-center text-danger">${inputMessages[0]}</p>
                                            </c:if>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>Rating:</th>
                                        <td class="text-center">
                                            <label>
                                                <input name="commentInput" value="${commentInputs[1]}"
                                                       class="form-control text-left" type="text"/>
                                            </label>
                                            <c:if test="${not empty inputMessages[1]}">
                                                <p class="text-center text-danger">${inputMessages[1]}</p>
                                            </c:if>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="2">
                                            <div class="form-group">
                                                <label for="comment">Comment:</label>
                                                <textarea class="form-control" rows="5" name="commentInput"
                                                          id="comment">${commentInputs[2]}</textarea>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <button type="submit"
                                                    class="btn btn-primary text-white">
                                                <i class="fa fa-hand-grab-o"></i> Leave comment
                                            </button>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>

<script src="${i18nJS}"></script>
<script src="${addToCart}"></script>
<script src="${whenDocReady}"></script>
<jsp:include page="/resources/common/footer.jsp"/>
</body>
</html>