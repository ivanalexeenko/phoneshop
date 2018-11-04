<html>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
  <link rel="stylesheet" href="webjars/bootstrap/4.1.0/css/bootstrap.min.css">
  <title>Product List Page</title>
  <style>
    /* Remove the navbar's default margin-bottom and rounded borders */
    .navbar {
      margin-bottom: 0;
      border-radius: 0;
    }

    /* Set height of the grid so .sidenav can be 100% (adjust as needed) */
    .row.content {height: 450px}

    /* Set gray background color and 100% height */
    .sidenav {
      padding-top: 20px;
      background-color: #f1f1f1;
      height: 100%;
    }

    /* Set black background color, white text and some padding */
    footer {
      background-color: #555;
      color: white;
      padding: 15px;
    }

    /* On small screens, set height to 'auto' for sidenav and grid */
    @media screen and (max-width: 767px) {
      .sidenav {
        height: auto;
        padding: 15px;
      }
      .row.content {height:auto;}
    }
  </style>
</head>
<body>
<nav class="navbar navbar-expand-md navbar-dark bg-dark">
  <div class="container">
    <a class="navbar-brand" href="<c:url value="/productList"/>">Phoneshop Spring</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse justify-content-end" id="navbarsExampleDefault">
      <ul class="navbar-nav m-auto">
        <li class="nav-item active">
          <a class="nav-link" href="<c:url value="/productList"/>">Product List<span class="sr-only">(current)</span></a>
        </li>
        <li class="nav-item m-auto">
          <a class="nav-link" href="<c:url value="/cart"/>">Cart</a>
        </li>
      </ul>

    </div>
  </div>
</nav>

<header class="jumbotron text-center ">
  <h1>Welcome to the Product List Page!</h1>
  <p>Feel free to select yours =)</p>
</header>

<div class="panel panel-info">
  <div class="panel-heading bg-secondary text-center text-white font-italic">Found <c:out value="${phones.size()}"/> phones:</div>
</div>

<div class="container mb-4">
  <div class="row">
    <div class="col-12">
      <div class="table-responsive">
        <table class="table table-hover text-center">
          <thead>
          <tr>
            <th scope="col">Image</th>
            <th scope="col">Brand</th>
            <th scope="col">Model</th>
            <th scope="col">OS</th>
            <th scope="col">Colors</th>
            <th scope="col" class="text-right">Price</th>
          </tr>
          </thead>
          <tbody>
          <c:forEach var="phone" items="${phones}">
            <tr>
              <td>
                <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}">
              </td>
              <td class="align-middle">${phone.brand}</td>
              <td class="align-middle">${phone.model}</td>
              <td class="align-middle">${phone.os}</>
              <td class="align-middle">
              <c:forEach var="color" items="${phone.colors}">
                <p>${color.code}</p>
              </c:forEach>
              </>
              <td class="align-middle text-right">$ ${phone.price}</>
            </tr>
          </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
</div>
</div>

<footer class="container-fluid text-center">
  <p>Expert Soft feat. Ivan Alexeenko Spring Project</p>
</footer>

</body>
</html>







