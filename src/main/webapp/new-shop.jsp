<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<t:head title="Add new shop"/>
<body>
<t:header/>
<div class="container">
    <div class="row">
        <table class="table table-condensed">
            <form action="/controller" method="post">
                <tr>
                    <td> Name: </td>
                    <td> <input type="text" name="name"/> </td>
                    <td> <p style="color:#ff0000">${nullNameError}</p> </td>
                </tr>
                <tr>
                    <td> Address : </td>
                    <td> <input type="text" name="address"/> </td>
                    <td> <p style="color:#ff0000">${nullAddressError}</p> </td>
                </tr>
                <tr>
                    <td>Phone : </td>
                    <td><input type="text" name="phone"/></td>
                    <td> <p style="color:#ff0000">${nullPhoneError}</p> </td>
                </tr>
                <tr>
                    <td>
                        <input type="hidden" name="command" value="addClientShop"/>
                        <input type="submit" value="add"/>
                    </td>
                    <td></td>
                    <td></td>
                </tr>
            </form>
        </table>
    </div>
</div>
</body>
</html>