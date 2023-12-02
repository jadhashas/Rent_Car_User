package com.example.rent_car_user;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.mindrot.jbcrypt.BCrypt;

@WebServlet(name = "changepassword", urlPatterns = {"/changepassword"})
public class changepassword extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public changepassword() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RequestDispatcher dispatcher = request.getRequestDispatcher("changepassword.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
         int IDUSER = (int) session.getAttribute("UserId");
        String newPassword = request.getParameter("newPassword");
        String confirmNewPassword = request.getParameter("confirmNewPassword");

        if (!newPassword.equals(confirmNewPassword)) {
            // Handle password mismatch
            session.setAttribute("errorMessage", "Ne  sont  pas  egaux.");
            response.sendRedirect("changepassword.jsp");
            return;
        }

        String hashedPassword = hashPassword(newPassword);

        if (updateUserPassword(IDUSER, hashedPassword)) {
            // Redirect to changepassword with success message

            response.sendRedirect("Dashboard.jsp");
        } else {
            // Redirect to changepassword with error message
            session.setAttribute("errorMessage", "Le password  n'a pas  etait Updated.");
            response.sendRedirect("changepassword.jsp");
        }
    }

    private String hashPassword(String password) {
        String salt = BCrypt.gensalt();
        return BCrypt.hashpw(password, salt);
    }

    private boolean updateUserPassword(int IDUSER, String hashedPassword) {
        try (Connection conn = DatabaseConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE users SET password = ? WHERE id = ?")) {

            stmt.setString(1, hashedPassword);
            stmt.setInt(2, IDUSER);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}