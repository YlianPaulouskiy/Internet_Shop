<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Orders</title>
    <style type="text/css">
        table {
            width: auto;
            min-width: 300px;
            border-collapse: collapse;
        }

        td, th {
            padding: 3px;
            border: 1px solid black;
        }
    </style>
</head>
<body>

<c:if test="${not empty requestScope.get('orders')}">
    <c:forEach var="order" items="${requestScope.get('orders')}">
        <h3>Order# ${order.getId()}
            <form action="${pageContext.request.contextPath}/orders/delete" method="post">
                <button type="submit" name="id" value="${order.getId()}">Delete</button>
            </form>
        </h3>
        <table>
            <tr>
                <td>№</td>
                <td>Product name</td>
                <td>Description</td>
                <td>Price</td>
                <td>Seller name</td>
                <td>Phone</td>
                <td>Email</td>
            </tr>
            <c:forEach var="product" items="${order.getProducts()}">
                <tr>
                    <td>${product.getId()}</td>
                    <td>${product.getName()}</td>
                    <td>${product.getDescription()}</td>
                    <td>${product.getPrice()}</td>
                    <td>${product.getUser().getLastName().concat(" ").concat(product.getUser().getName())}</td>
                    <td>${product.getUser().getPhone()}</td>
                    <td>${product.getUser().getEmail()}</td>
                </tr>
            </c:forEach>
        </table>
    </c:forEach>
</c:if>
<c:if test="${empty requestScope.get('orders')}">
    <h3>Order list is empty.</h3>
</c:if>
<br>
<form action="${pageContext.request.contextPath}/menu" method="get">
    <button type="submit">
        <b>Back</b>
    </button>
</form>

</body>
</html>
