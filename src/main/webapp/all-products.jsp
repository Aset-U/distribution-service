<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<t:head title="Home"/>
<body>
<t:header/>
<section>
  <t:category/>

      <c:forEach var="product" items="${allProducts}">

        <div class="col-sm-4">
          <div class="product-image-wrapper">
            <div class="single-products">
              <div class="productinfo text-center">
                <a href="
                            <c:url value="/controller">
                                <c:param name="command" value="product"/>
                                <c:param name="productId" value="${product.id}"/>
                            </c:url>">
                <h4><c:out value="${product.name}"/></h4>
                <p><c:out value="$${product.price}"/></p>
                </a>
                  <form action="<c:url value='/controller'/>" method="post" >
                      <input type="hidden" name="command" value="addToCart"/>
                      <input type="hidden" name="page" value="all-products"/>
                      <input type="hidden" name="quantity" value="1" />
                      <input type="hidden" name="productId" value="${product.id}"/>
                      <button type="submit" class="btn btn-fefault cart">
                          <i class="fa fa-shopping-cart"></i>
                          Add to cart
                      </button>
                  </form>
                </form>
              </div>
            </div>
          </div>
        </div>

      </c:forEach>

</body>
</html>