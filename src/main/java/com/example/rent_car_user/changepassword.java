package com.example.rent_car_user;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        int userID = (int) session.getAttribute("UserId");
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmNewPassword = request.getParameter("confirmNewPassword");

        // Verify old password
        if (!verifyOldPassword(userID, oldPassword)) {
            session.setAttribute("errorMessage", "Ancien mot de passe incorrect.");
            response.sendRedirect("changepassword.jsp");
            return;
        }

        // Check if new password matches the confirmation
        if (!newPassword.equals(confirmNewPassword)) {
            session.setAttribute("errorMessage", "Les nouveaux mots de passe ne correspondent pas.");
            response.sendRedirect("changepassword.jsp");
            return;
        }

        String hashedPassword = hashPassword(newPassword);

        // Update the password
        if (updateUserPassword(userID, hashedPassword)) {
            // Redirect to changepassword with success message
            session.setAttribute("successMessage", "Le mot de passe a été mis à jour avec succès.");
            response.sendRedirect("Dashboard.jsp");
        } else {
            // Redirect to changepassword with error message
            session.setAttribute("errorMessage", "Le mot de passe n'a pas été mis à jour.");
            response.sendRedirect("changepassword.jsp");
        }
    }

    private boolean verifyOldPassword(int userID, String oldPassword) {
        try (Connection conn = DatabaseConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT password FROM users WHERE id = ?")) {

            stmt.setInt(1, userID);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                String hashedPassword = resultSet.getString("password");
                return BCrypt.checkpw(oldPassword, hashedPassword);
            }

            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
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