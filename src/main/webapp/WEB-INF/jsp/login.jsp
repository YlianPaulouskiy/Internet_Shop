<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Login</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/login" method="post">
    <label for="login">Login:
        <input type="text" name="login" id="login" value="${param.login}" required>
    </label><br>
    <label for="password">Password:
        <input type="password" name="password" id="password" required>
    </label><br>
    <button type="submit">Sign in</button>
    <a href="${pageContext.request.contextPath}/registration">
        <button type="button">Register</button>
    </a>
    <c:if test="${requestScope.get('logFail') != null}">
        <div style="color: red">
            <span>${requestScope.get("logFail")}</span>
        </div>
    </c:if>
</form>
</body>
</html>
