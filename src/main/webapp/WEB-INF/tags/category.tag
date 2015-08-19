<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <div class="col-sm-3">
        <div class="left-sidebar">
            <h2>Category</h2>
            <c:set var="categories" value="${categories}" scope="page"/>
            <div class="panel-group category-products" id="accordian"><!--category-productsr-->
                <div class="panel panel-default">
                    <div class="panel-heading">
                <c:forEach var="category" items="${categories}">
                <h4 class="panel-title">
                            <a href="
                            <c:url value="/controller">
                                <c:param name="command" value="category"/>
                                <c:param name="categoryId" value="${category.id}"/>
                            </c:url>">
                            ${category.name}
                            </a>
                        </h4>
                    <br/>
                </c:forEach>
                    </div>
                </div>
            </div><!--/category-products-->

        </div>
    </div>
</section>