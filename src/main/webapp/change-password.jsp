<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<t:head title="Change password"/>
<body>
<t:header/>
<div class="container">
  <div class="row">
    <table class="table table-condensed">
      <form action="/controller" method="post">
        <tr>
          <td> Current password : </td>
          <td> <input type="password" name="currentPassword"/> </td>
          <td> <p style="color:#ff0000">${errorCurrent}</p> </td>
        </tr>
        <tr>
          <td> Enter new password : </td>
          <td> <input type="password" name="newPassword"/> </td>
          <td> <p style="color:#ff0000">${errorNew}</p> </td>
        </tr>
        <tr>
          <td>Re-enter new password : </td>
          <td><input type="password" name="RePassword"/></td>
          <td> <p style="color:#ff0000">${errorNew}</p> </td>
        </tr>
        <tr>
          <td>
            <input type="hidden" name="command" value="changeClientPassword"/>
            <input type="submit" value="change"/>
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