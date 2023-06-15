<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Menu</title>
</head>
<body>
<h4>User ${sessionScope.get("user").getEmail()}</h4>

<br>
<form action="${pageContext.request.contextPath}/products" method="get">
    <button type="submit">Product Catalog</button>
</form>
<br>
<form action="${pageContext.request.contextPath}/products" method="get">
    not complete
    <button type="submit">Your products</button>
</form>
<br>
<form action="${pageContext.request.contextPath}/orders" method="get">
    not complete
    <button type="submit">Orders</button>
</form>
<br>
<form action="${pageContext.request.contextPath}/user" method="get">
    <button type="submit">Personal Info</button>
</form>
<br>
<form action="${pageContext.request.contextPath}/logout" method="post">
    <button type="submit">Logout</button>
</form>


</body>
</html>
