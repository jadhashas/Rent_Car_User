
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>DASHBOARD</title>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
  <!-- Optional: Include the Bootstrap JS and its dependencies -->
  <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body>
<h1>HELLO <%= session.getAttribute("Login")%></h1>
<a href="changepassword" class="btn btn-success">ModifierPWD</a>
</body>
</html>
