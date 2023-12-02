
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
    <!-- Include Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <!-- Optional: Include the Bootstrap JS and its dependencies -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body>

<div class="container mt-5">
    <h1>Login</h1>
    <%
        String successMessage = (String) session.getAttribute("successMessage");
        String errorMessage = (String) session.getAttribute("errorMessage");
        if (successMessage != null && !successMessage.isEmpty()) {
    %>
    <div class="alert alert-success" role="alert">
        <%= successMessage %>
    </div>
    <%
        }
        if (errorMessage != null && !errorMessage.isEmpty()) {
    %>
    <div class="alert alert-danger" role="alert">
        <%= errorMessage %>
    </div>
    <%

        session.removeAttribute("successMessage");
        session.removeAttribute("errorMessage");
    } %>
    <form method="POST" action="User">
        <div class="form-group">
            <label for="txtNom">Nom :</label>
            <input type="text" class="form-control" id="txtNom" name="txtNom"/>
        </div>
        <div class="form-group">
            <label for="txtPswd">Password :</label>
            <input type="password" class="form-control" id="txtPswd" name="txtPswd"/>
        </div>
        <button type="submit" class="btn btn-primary" name="txtConnecter">Se Connecter</button>
<%--        <button type="submit" class="btn btn-primary" name="txtInscrire">S'inscrire</button>--%>
        <a href="Signup" class="btn btn-outline-primary">S'inscrire</a>
    </form>
</div>

</body>
</html>

