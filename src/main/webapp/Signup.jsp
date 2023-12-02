<%--
  Created by IntelliJ IDEA.
  User: jouj9
  Date: 30/11/2023
  Time: 12:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Inscription</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <!-- Optional: Include the Bootstrap JS and its dependencies -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container mt-5">
    <h2>User Registration Form</h2>
    <%

        String errorMessage = (String) session.getAttribute("errorMessage");

    %>

    <%

        if (errorMessage != null && !errorMessage.isEmpty()) {
    %>
    <div class="alert alert-danger" role="alert">
        <%= errorMessage %>
    </div>
    <%


            session.removeAttribute("errorMessage");
        } %>
    <form action="Signup" method="post">
        <div class="form-row">
            <div class="form-group col-md-6">
                <label for="name">Name</label>
                <input type="text" class="form-control" id="name" name="name" required>
            </div>
            <div class="form-group col-md-6">
                <label for="prenom">Prenom</label>
                <input type="text" class="form-control" id="prenom" name="prenom" required>
            </div>
        </div>
        <div class="form-row">
            <div class="form-group col-md-6">
                <label for="age">Age</label>
                <input type="number" class="form-control" id="age" name="age" required>
            </div>
            <div class="form-group col-md-6">
                <label for="login">Login</label>
                <input type="text" class="form-control" id="login" name="login" required>
            </div>
        </div>
        <div class="form-row">
            <div class="form-group col-md-6">
                <label for="email">Email</label>
                <input type="email" class="form-control" id="email" name="email" required>
            </div>
            <div class="form-group col-md-6">
                <label for="password">Password</label>
                <input type="password" class="form-control" id="password" name="password" required>
            </div>
            <div class="form-group col-md-6">
                <label for="confpassword">Confirme Password</label>
                <input type="password" class="form-control" id="confpassword" name="confpassword" required>
            </div>
        </div>
        <div class="form-row">
            <div class="form-group col-md-6">
                <label for="tel">Telephone</label>
                <input type="tel" class="form-control" id="tel" name="tel" required>
            </div>
        </div>
        <div id="message"></div>
        <button type="submit" class="btn btn-primary">S'inscrire</button>
    </form>
</div>


<script>
    var password = document.getElementById("password");
    var confirmPassword = document.getElementById("confpassword");

    function validatePassword(){
        if(password.value != confirmPassword.value) {
            confirmPassword.style.borderColor = "red";
        } else {
            confirmPassword.style.borderColor = "green";
        }
    }

    password.addEventListener('keyup', validatePassword);
    confirmPassword.addEventListener('keyup', validatePassword);
</script>
</body>
</html>
