package com.example.rent_car_user;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;

@WebServlet(name = "User", urlPatterns = {"/User"})
public class User extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public User() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("Login.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("txtNom");
        String inputPassword = request.getParameter("txtPswd");
        HttpSession session = request.getSession();
        // Update your SQL query to also select the user's ID
        String sql = "SELECT id, password, Admin FROM users WHERE login = ?";

        try (Connection conn = DatabaseConn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString("password");
                    boolean isAdmin = rs.getBoolean("Admin");
                    int userId = rs.getInt("id"); // Retrieve the user's ID

                    if (BCrypt.checkpw(inputPassword, storedHash)) {
                        session.setAttribute("Login", name);
                        session.setAttribute("UserId", userId); // Store the user's ID in the session

                        // Check if the user is an admin and redirect accordingly
                        if (isAdmin) {
                            response.sendRedirect("dashboard_Admin.jsp");
                        } else {
                            response.sendRedirect("Dashboard.jsp");
                        }
                    } else {
                        session.setAttribute("errorMessage", "Invalid username or password");
                        response.sendRedirect("Login.jsp");
                    }
                } else {
                    session.setAttribute("errorMessage", "User n'existe PAS !!");
                    response.sendRedirect("Login.jsp");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Better error handling
            // e.g., response.sendRedirect("Error.jsp");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

