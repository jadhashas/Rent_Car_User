package com.example.rent_car_user;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "Signup", urlPatterns = {"/Signup"})
public class Signup extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public Signup() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("Signup.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String prenom = request.getParameter("prenom");
        int age = Integer.parseInt(request.getParameter("age"));
        String login = request.getParameter("login");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmpassword = request.getParameter("confpassword");
        String tel = request.getParameter("tel");

        if (age < 0 || age > 90) {
            HttpSession session = request.getSession();
            session.setAttribute("errorMessage", "L'âge doit être compris entre 0 et 90 ans.");
            response.sendRedirect("Signup.jsp");
            return;
        }

        if (!isValidEmail(email)) {
            HttpSession session = request.getSession();
            session.setAttribute("errorMessage", "Format de l'email invalide.");
            response.sendRedirect("Signup.jsp");
            return;
        }

        if (name.isEmpty() || prenom.isEmpty() || login.isEmpty() || email.isEmpty() || password.isEmpty() || tel.isEmpty()) {
            response.sendRedirect("Signup.jsp?error=missingFields");
            return;
        }

        if (!confirmpassword.equals(password)) {
            HttpSession session = request.getSession();
            session.setAttribute("errorMessage", "Les mots de passe ne correspondent pas.");
            response.sendRedirect("Signup.jsp");
            return;
        }

        String hashedPassword = hashPassword(password);

        if (insertUser(name, prenom, age, login, email, hashedPassword, tel)) {
            HttpSession session = request.getSession();
            session.setAttribute("successMessage", "Inscription réussie. Connectez-vous maintenant !");
            response.sendRedirect("Login.jsp");
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("errorMessage", "Erreur lors de l'inscription. Veuillez réessayer.");
            response.sendRedirect("Signup.jsp");
        }
    }

    private boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }

    private String hashPassword(String password) {
        String salt = BCrypt.gensalt();
        return BCrypt.hashpw(password, salt);
    }

    private boolean insertUser(String name, String prenom, int age, String login, String email, String password, String tel) {
        try {
            Connection connection = DatabaseConn.getConnection();
            String query = "INSERT INTO users (name, prenom, age, login, email, password, tel) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, prenom);
                preparedStatement.setInt(3, age);
                preparedStatement.setString(4, login);
                preparedStatement.setString(5, email);
                preparedStatement.setString(6, password);
                preparedStatement.setString(7, tel);

                int rowsAffected = preparedStatement.executeUpdate();
                connection.close();
                return rowsAffected > 0;
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
