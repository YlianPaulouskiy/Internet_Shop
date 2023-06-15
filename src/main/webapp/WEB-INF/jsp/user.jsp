<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>User</title>


    <script>
        const button = document.querySelector('button');
        const form = document.querySelector('#changeBut');

        button.addEventListener('click', () => {
            form.classList.add('open');
        });
    </script>

    <style>
        #changeBut {
            display: none;
            animation-duration: 1.5s;
            animation-fill-mode: both;
            animation-name: fadeIn;
        }

        #changeBut.open {
            display: block;
        }

        @keyframes fadeIn {
            from {
                opacity: 0;
            }
            to {
                opacity: 1;
            }
        }
    </style>
</head>
<p>

<h4>Personal Info</h4>
<form action="/user" method="post">
    <strong>Name:</strong> ${sessionScope.get("user").getName()}
    <input type="text" name="name">
    <br>
    <strong>Lastname:</strong> ${sessionScope.get("user").getLastName()}
    <input type="text" name="lastname">
    <br>
    <strong>Phone:</strong> ${sessionScope.get("user").getPhone()}
    <input type="text" name="phone">
    <br>
    <strong>Email:</strong> ${sessionScope.get("user").getEmail()}
    <input type="text" name="email">
    <br>
    <strong>Password:</strong> ${sessionScope.get("user").getPassword()}
    <input type="text" name="password">
    <br>
    <button type="submit">Change Personal Info</button>
</form>

<form action="${pageContext.request.contextPath}/user/delete" method="post">
    <button type="submit">Delete Account</button>
</form>

<h4>Address:</h4>
<form action="/address/save" method="post">
    <strong>City:</strong> ${sessionScope.get("address").getCity()}
    <input type="text" name="city">
    <br>
    <strong>Street:</strong> ${sessionScope.get("address").getStreet()}
    <input type="text" name="street">
    <br>
    <strong>House:</strong> ${sessionScope.get("address").getHouse()}
    <input type="text" name="house">
    <br>
    <strong>Flat:</strong> ${sessionScope.get("address").getFlat()}
    <input type="text" name="flat">
    <br>
    <button type="submit">Change Address</button>
</form>

<form action="address/delete" method="post">
    <button type="submit">Delete Address</button>
</form>

<form action="${pageContext.request.contextPath}/menu" method="get">
    <button type="submit">
        <b>Back</b>
    </button>
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
