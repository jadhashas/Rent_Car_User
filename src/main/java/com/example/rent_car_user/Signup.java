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
            request.setAttribute("errorMessage", "L'âge doit être compris entre 0 et 90 ans.");
            request.getRequestDispatcher("Signup.jsp").forward(request, response);
            return;
        }

        if (!isValidEmail(email)) {
            HttpSession session = request.getSession();
            request.setAttribute("errorMessage", "Format de l'email invalide.");
            request.getRequestDispatcher("Signup.jsp").forward(request, response);
            return;
        }

        if (name.isEmpty() || prenom.isEmpty() || login.isEmpty() || email.isEmpty() || password.isEmpty() || tel.isEmpty()) {
            request.setAttribute("errorMessage", "Tout les champs sont obligatoire !!");
            request.getRequestDispatcher("Signup.jsp").forward(request, response);
            return;
        }

        if (!confirmpassword.equals(password)) {
            request.setAttribute("errorMessage", "Les mots de passe ne correspondent pas.");
            request.getRequestDispatcher("Signup.jsp").forward(request, response);
            return;
        }

        String hashedPassword = hashPassword(password);

        if (insertUser(name, prenom, age, login, email, hashedPassword, tel)) {
            request.setAttribute("successMessage", "Inscription réussie. Connectez-vous maintenant !");
//            request.getRequestDispatcher("User").forward(request, response);
            request.getRequestDispatcher("Login.jsp").forward(request, response);
        } else {
            request.setAttribute("errorMessage", "Erreur lors de l'inscription. Veuillez réessayer.");
            request.getRequestDispatcher("Signup.jsp").forward(request, response);
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
