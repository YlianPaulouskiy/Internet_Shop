<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Catalog</title>
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

<h3>Product Catalog</h3>

<table>
    <tr>
        <td>№</td>
        <td>Product name</td>
        <td>Description</td>
        <td>Price</td>
        <td>Seller name</td>
        <td>Phone</td>
        <td>Email</td>
        <td></td>
    </tr>
    <c:forEach var="product" items="${requestScope.get('products')}">
        <tr>
            <td>${product.getId()}</td>
            <td>${product.getName()}</td>
            <td>${product.getDescription()}</td>
            <td>${product.getPrice()}</td>
            <td>${product.getUser().getLastName().concat(" ").concat(product.getUser().getName())}</td>
            <td>${product.getUser().getPhone()}</td>
            <td>${product.getUser().getEmail()}</td>
            <td>
                <form action="${pageContext.request.contextPath}/orders/product/add" method="get">
                    <button type="submit" name="addId" value="${product.getId()}">Add</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

<c:if test="${not empty sessionScope.get('orderProducts')}">
    <h3>Products in Order</h3>

    <table>
        <tr>
            <td>№</td>
            <td>Product name</td>
            <td>Description</td>
            <td>Price</td>
            <td>Seller name</td>
            <td>Phone</td>
            <td>Email</td>
            <td></td>
        </tr>
        <c:forEach var="product" items="${sessionScope.get('orderProducts')}">
            <tr>
                <td>${product.getId()}</td>
                <td>${product.getName()}</td>
                <td>${product.getDescription()}</td>
                <td>${product.getPrice()}</td>
                <td>${product.getUser().getLastName().concat(" ").concat(product.getUser().getName())}</td>
                <td>${product.getUser().getPhone()}</td>
                <td>${product.getUser().getEmail()}</td>
                <td>
                    <form action="${pageContext.request.contextPath}/orders/product/remove" method="get">
                        <button type="submit" name="removeId" value="${product.getId()}">Remove</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
<br>
    <form action="${pageContext.request.contextPath}/orders/save" method="post">
        <button type="submit">Create Order</button>
    </form>
</c:if>

<br>
<form action="${pageContext.request.contextPath}/menu" method="get">
    <button type="submit">
        <b>Back</b>
    </button>
</form>

</body>
</html>
