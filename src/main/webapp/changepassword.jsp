<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 12/2/2023
  Time: 7:28 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <!-- Optional: Include the Bootstrap JS and its dependencies -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body>



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
    <form action="changepassword" method="post" class="form-group">
        <div class="form-group">
            <label for="oldPassword">Old Password:</label>
            <input type="password" class="form-control" id="oldPassword" name="oldPassword" required>
        </div>

        <div class="form-group">
            <label for="newPassword">New Password:</label>
            <input type="password" class="form-control" id="newPassword" name="newPassword" required>
        </div>

        <div class="form-group">
            <label for="confirmNewPassword">Confirm New Password:</label>
            <input type="password" class="form-control" id="confirmNewPassword" name="confirmNewPassword" required>
        </div>

        <button type="submit" class="btn btn-primary">Update Password</button>
    </form>

</body>
</html>
