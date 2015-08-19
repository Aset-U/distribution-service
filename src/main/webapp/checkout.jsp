
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
                <li class="active"><h2>Check out</h2></li>
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
        <c:forEach var="checkoutItem" items="${checkout.items}" varStatus="iter">
            <c:set var="product" value="${checkoutItem.product}"/>
            <tr>
               <td class="cart_description">
               <p>${product.name}</p>
               </td>
               <td class="cart_price">
               <p>$${product.price}</p>
               </td>
               <td class="cart_quantity">
               <div class="cart_quantity_button">
               <p> ${checkoutItem.quantity}</p>
               </div>
               </td>
               <td class="cart_category">
               <p>${product.category.name}</p>
               </td>
               <td class="cart_total">
               <p>$ ${checkoutItem.total}</p>
               </td>
            </tr>
        </c:forEach>
            </tbody>
                <tr>
                <td>
                    <p>Order sub Total  -  <strong>$${checkout.subtotal}</strong></p>
                </td>
               </tr>
            </table>
        </div>
        <div>
        <tr>
            <form action="<c:url value='/controller'/>" method="post">
                <input type="hidden" name="command" value="confirmOrder">
                <menu>
                    Please choose a delivery location
                </menu>
                <menu>
                    <p><select name="clientShop">
                        <c:forEach var="shop" items="${shops}" varStatus="iter">
                            <option value="${shop.id}">${shop.name}</option>
                        </c:forEach>
                    </select></p>
                </menu>
                <menu>
                    <button type="submit" class="btn btn-fefault">
                        <i class="fa fa-order"></i>
                        Confirm order
                    </button>
                </menu>
            </form>
        </tr>
            <tr>${noItems}</tr>
    </div>
    </div>
</section>
</body>
</html>
