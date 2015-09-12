
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<t:head title="Account"/>
<body>
<t:header/>

    <div class="container">
        <div class="row">
            <table class="table table-condensed">
                <tr>
                    <td> User : </td>
                    <td> ${client.firstName} ${client.lastName}</td>
                    <td></td>
                </tr>
                <tr>
                    <td>Login : </td>
                    <td>${client.username}</td>
                    <td> </td>
                </tr>
                <tr>
                    <td>Password : </td>
                    <td>
                        <form action="/controller" method="GET">
                            <input type="password" value="${client.password}"/>
                            <input  type="hidden" name="page" value="change-password"/>
                            <input  type="submit" value="change"/>
                        </form>
                    </td>
                    <td>
                    </td>
                </tr>
                <tr>
                    <td>Shops :</td>
                    <td>
                        <menu>
                            <select name="clientShop">
                                <c:forEach var="shop" items="${shops}" varStatus="iter">
                                    <option value="${shop.id}">${shop.name}</option>
                                </c:forEach>
                            </select>
                        </menu>
                    </td>
                    <td>
                        <form action="/controller" method="GET">
                            <input  type="hidden" name="command" value="shopList"/>
                            <input  type="submit" value="edit"/>
                        </form>
                    </td>
                    <td></td>
                </tr>
            </table>
        </div>
    </div>
</body>

</html>