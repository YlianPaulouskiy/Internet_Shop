<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/registration" method="post">
    <label for="name">Name:
        <input type="text" name="name" id="name">
    </label><br/>
    <label for="lastname">Last Name:
        <input type="text" name="lastname" id="lastname">
    </label><br/>
    <label for="phone">Phone:
        <input type="text" name="phone" id="phone">
    </label><br/>
    <label for="email">Email:
        <input type="email" name="email" id="email">
    </label><br/>
    <label for="pwd">Password:
        <input type="password" name="password" id="pwd">
    </label><br/>
    <input type="submit" value="Send">
</form>
<%--отображение неправильно введенных значений--%>
<c:if test="${not empty requestScope.get('errors')}">
    <div style="color: red">
        <c:forEach var="error" items="${requestScope.get('errors')}">
            <span>${error}</span>
            <br>
        </c:forEach>
        <br>
    </div>
</c:if>
</body>

<%--<alert>предупреждение</alert>--%>
