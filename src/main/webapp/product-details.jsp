
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<html>
<t:head title="Product details"/>
<body>

<t:header/>
<t:category/>
    <div class="product-information"><!--/product-information-->
        <h2>${product.name}</h2>
        <span>
            <h5> $ ${product.price} </h5>
            <label>Quantity:</label>

        <form action="<c:url value='/controller'/>" method="post" >
            <input type="hidden" name="command" value="addToCart"/>
            <input type="hidden" name="page" value="product-details"/>
            <input type="hidden" name="productId" value="${product.id}"/>
            <input type="text" name="quantity" value="1" />
            <button type="submit" class="btn btn-fefault cart">
                <i class="fa fa-shopping-cart"></i>
                Add to cart
            </button>
        </form>
        </span>
        <p><b>weight: </b> <c:out value="${product.weight}"></c:out></p>
        <p><b>category: </b> <c:out value="${product.category.name}"></c:out></p>
    </div><!--/product-information-->

</body>
</html>
