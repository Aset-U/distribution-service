<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<html>
<t:head title="Shopping cart"/>
<body>
<t:header/>
<section id="cart_items">
    <div class="container">
        <div class="breadcrumbs">
            <ol class="breadcrumb">
                <li class="active">Shopping Cart</li>
            </ol>
        </div>
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


                <c:forEach var="cartItem" items="${cart.items}" varStatus="iter">
                    <c:set var="product" value="${cartItem.product}"/>
                    <tr>
                    <td class="cart_description">
                        <a href="
                            <c:url value="/controller">
                                <c:param name="command" value="product"/>
                                <c:param name="productId" value="${product.id}"/>
                            </c:url>">
                            <url><p><c:out value="${product.name}"/></p></url>
                        </a>
                    </td>
                    <td class="cart_price">
                        <p>$${product.price}</p>
                    </td>
                    <td class="cart_quantity">
                        <div class="cart_quantity_button">
                            <p> ${cartItem.quantity}</p>
                        </div>
                    </td>
                    <td class="cart_category">
                        <p>${product.category.name}</p>
                    </td>
                    <td class="cart_total">
                        <p>$ ${cartItem.total}</p>
                    </td>
                    <td class="cart_delete">
                        <form action="/controller" method="POST">
                            <input type="hidden" name="command" value="deleteProductCart">
                            <input type="hidden" name="productDeleted" value="${product.id}">
                            <input type="submit"
                                   name="submit"
                                   value="Delete">
                        </form>
                    </td>
                </tr>

                </c:forEach>

            </table>
            <c:if test="${!empty cart && cart.numberOfItems != 0}">
                <div>
                    <c:url var="url" value="/controller?command=cart">
                        <c:param name="clear" value="true"/>
                    </c:url>

                    <a style="color:#ff0000" href="${url}"><strong><ins>clear cart</ins></strong></a>
                </div>
            </c:if>
        </div>
    </div>
</section> <!--/#cart_items-->

<section id="do_action">
    <div class="container">

        <div class="col-sm-6">
            <div class="total_area">
                <ul>
                    <li>Cart sub total <span>$ ${cart.subtotal}</span></li>
                </ul>
                <a class="btn btn-default check_out" href="/controller?command=checkout">Check Out</a>
            </div>
            <ins>${warning}</ins>
        </div>
    </div>
</section><!--/#do_action-->
</body>
</html>
