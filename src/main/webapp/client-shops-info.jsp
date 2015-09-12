<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<html>
<t:head title="Client shops"/>
<body>
<t:header/>

<section id="cart_items">
  <div class="container">
    <div class="breadcrumbs">
      <ol class="breadcrumb">
        <li class="active"><h2>Client shops</h2></li>
      </ol>
    </div>
    <h3>Client - <c:out value="${client.firstName} ${client.lastName}"/></h3>
    <div class="table-responsive cart_info">
      <table class="table table-condensed">
        <thead>
        <tr class="cart_menu">
          <td class="item">id</td>
          <td class="item">name</td>
          <td class="item">address</td>
          <td class="item">phone</td>
          <td></td>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="shop" items="${shops}" varStatus="iter">
          <tr>
            <td>
                ${shop.id}
            </td>
            <td>
                ${shop.name}
            </td>
            <td>
                ${shop.address}
            </td>
            <td>
                ${shop.phone}
            </td>
            <td>
              <form action="/controller" method="POST">
                <input type="hidden" name="command" value="deleteClientShop">
                <input type="hidden" name="shopDeleted" value="${shop.id}">
                <input type="submit"
                       name="submit"
                       value="Delete">
              </form>
            </td>
          </tr>
        </c:forEach>
        <tr>
          <td>
            <form action="/controller" method="get">
              <input type="hidden" name="page" value="new-shop"/>
              <input type="submit" value="Add new shop"/>
            </form>
          </td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</section>
</body>
</html>
