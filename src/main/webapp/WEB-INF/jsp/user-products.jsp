<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Products</title>
    <style type="text/css">
        table {
            width: 300px; /* Ширина таблицы */
            border-collapse: collapse; /* Убираем двойные линии между ячейками */
        }

        td, th {
            padding: 3px; /* Поля вокруг содержимого таблицы */
            border: 1px solid black; /* Параметры рамки */
        }

        th {
            background: #b0e0e6; /* Цвет фона */
        }
    </style>
</head>
<body>

<h3>Products by ${sessionScope.get("user").getEmail()}</h3>

<table style="">
    <tr>
        <td>№</td>
        <td>name</td>
        <td>description</td>
        <td>price</td>
        <td>-</td>
    </tr>
    <c:forEach var="product" items="${requestScope.get('products')}">
        <tr>
            <td>${product.getId()}</td>
            <td>${product.getName()}</td>
            <td>${product.getDescription()}</td>
            <td>${product.getPrice()}</td>
            <td>
                <form action="${pageContext.request.contextPath}/products/delete" method="post">
                    <button type="submit" name="id" value="${product.getId()}">delete</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

<br>
<form action="${pageContext.request.contextPath}/menu" method="get">
    <button type="submit">
        <b>Back</b>
    </button>
</form>

<form action="${pageContext.request.contextPath}/products/save" method="post">
    <h4>Add product</h4>
    <label for="name">Name:
        <input type="text" name="name" id="name">
    </label><br/>
    <label for="description">Description:
        <input type="text" name="description" id="description">
    </label><br/>
    <label for="price">Price:
        <input type="number" step="0.01" name="price" id="price">
    </label><br/>
    <br>
    <button type="submit">Create Product</button>
</form>

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
</html>
