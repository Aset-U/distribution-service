
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<html>
<t:head title="My orders"/>
<body>
<t:header/>
<section id="cart_items">
  <div class="container">
    <div class="breadcrumbs">
      <ol class="breadcrumb">
        <li class="active"><h2>My orders</h2></li>
      </ol>
    </div>
    <h3>Client - <c:out value="${client.firstName} ${client.lastName}"/></h3>
    <div class="table-responsive cart_info">
      <table class="table table-condensed">
        <thead>
        <tr class="cart_menu">
          <td class="item">Order id</td>
          <td class="item">Items quantity</td>
          <td class="item">Order total</td>
          <td class="item">Order status</td>
          <td></td>
        </tr>
        </thead>
        <tbody>
      <c:forEach var="order" items="${orders}" varStatus="iter">
        <tr>
          <td>
            ${order.id}
          </td>
          <td>
            ${order.numberOfItems}
          </td>
          <td>
            ${order.subtotal}
          </td>
          <td>
            ${order.status}
          </td>
          <td>
            <a href="
                            <c:url value="/controller">
                                <c:param name="command" value="order"/>
                                <c:param name="orderId" value="${order.id}"/>
                            </c:url>">
              details...
            </a>
          </td>
        </tr>
        </c:forEach>
        </tbody>
      </table>
    </div>
  </div>
</section> <!--/#cart_items-->

</body>
</html>
