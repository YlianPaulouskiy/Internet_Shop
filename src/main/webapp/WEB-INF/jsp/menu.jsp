<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Menu</title>
</head>
<body>
<h2>User ${sessionScope.get("user").getEmail()}</h2>
<form action="${pageContext.request.contextPath}/products" method="get">
    <button type="submit">Product Catalog</button>
</form>
<form action="${pageContext.request.contextPath}/products" method="get">
    not complete
    <button type="submit">Your products</button>
</form>
<form action="${pageContext.request.contextPath}/orders" method="get">
    not complete
    <button type="submit">Orders</button>
</form>
<form action="${pageContext.request.contextPath}/user" method="get">
    <button type="submit">Settings</button>
</form>
<form action="${pageContext.request.contextPath}/logout" method="post">
    <button type="submit">Logout</button>
</form>

</body>
</html>
