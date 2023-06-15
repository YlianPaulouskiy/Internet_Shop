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
<form>

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

</body>
</html>
