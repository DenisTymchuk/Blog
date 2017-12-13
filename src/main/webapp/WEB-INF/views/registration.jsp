<%--
  Created by IntelliJ IDEA.
  User: Oksa
  Date: 27.11.2017
  Time: 5:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8" />
    <title>Registration</title>
</head>
<body>

<h3>Registration new user</h3>
<p style="color : red" ;>${error}</p>
<p style="color : blue" ;>${success}</p>

<form method="POST" action=${pageContext.request.contextPath}/registr>
    <table border="0">
        <tr>
            <td>Login</td>
            <td><input type="text" name="userName" value="${login}"/></td>
            <td><p style="color: red;"> ${errorUserLogin}</p> </td>
        </tr>
        <tr>
            <td>Password</td>
            <td><input type="password" name="password" value=""/></td>
            <td><p style="color: red;"> ${errorConfirmPassword}</p> </td>
        </tr>
        <tr>
            <td>Confirm your password</td>
            <td><input type="password" name="confirmPassword" value=""/></td>
        </tr>
        <tr>
            <td><input type="submit" value="Sign up"/></td>
        </tr>
    </table>
</form>
</body>
</html>
