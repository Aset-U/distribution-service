
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<html>
<t:head title="Check out"/>
<body>
<t:header/>
<section id="cart_items">
  <div class="container">
    <div class="breadcrumbs">
      <ol class="breadcrumb">
        <li class="active"><h2>Order deitalis</h2></li>
      </ol>
    </div>
    <h3>Client - <c:out value="${client.firstName} ${client.lastName}"/></h3>
    <div class="table-responsive cart_info">
      <table class="table table-condensed">
        <thead>
        <tr class="cart_menu">
          <td class="description">Item</td>
          <td class="price">Price</td>
          <td class="quantity">Quantity</td>
          <td class="category">Category</td>
          <td class="total">Total</td>
          <td></td>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="orderItem" items="${clientOrder.items}" varStatus="iter">
          <c:set var="product" value="${orderItem.product}"/>
          <tr>
            <td class="cart_description">
              <p>${product.name}</p>
            </td>
            <td class="cart_price">
              <p>$${product.price}</p>
            </td>
            <td class="cart_quantity">
              <div class="cart_quantity_button">
                <p> ${orderItem.quantity}</p>
              </div>
            </td>
            <td class="cart_category">
              <p>${product.category.name}</p>
            </td>
            <td class="cart_total">
              <p>$ ${orderItem.total}</p>
            </td>
          </tr>
        </c:forEach>
        </tbody>
        <tr>
          <td>
            <p>Order sub Total  -  <strong>$${clientOrder.subtotal}</strong></p>
          </td>
        </tr>
        <tr>
          <td>
            <p> Delivery location</p>
          </td>
          <td>
            <p>${clientOrder.shop.name}, ${clientOrder.shop.address}</p>
          </td>
        </tr>
      </table>
    </div>
  </div>
</section>
</body>
</html>

