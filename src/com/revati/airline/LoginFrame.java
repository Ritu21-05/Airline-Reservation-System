package com.revati.airline;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, cancelButton;

    public LoginFrame() {
        initComponents();
        setTitle("Admin Login");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void initComponents() {
        setLayout(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(50, 50, 80, 25);
        add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(140, 50, 180, 25);
        add(usernameField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(50, 90, 80, 25);
        add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(140, 90, 180, 25);
        add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setBounds(80, 140, 100, 30);
        add(loginButton);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(200, 140, 100, 30);
        add(cancelButton);

        loginButton.addActionListener(e -> authenticateUser());
        cancelButton.addActionListener(e -> System.exit(0));
    }

    private void authenticateUser() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/airline_project", "ritu", "ritu123");

            PreparedStatement pre = con.prepareStatement(
                "SELECT * FROM Admin WHERE UserName = ? AND Password = ?");

            pre.setString(1, username);
            pre.setString(2, password);

            ResultSet rs = pre.executeQuery();

            if (rs.next()) {
                // Login success: open main frame and close login
                Main mainFrame = new Main();
                
                Main.main(null);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password", 
                                              "Login Failed", JOptionPane.ERROR_MESSAGE);
            }

            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to database", 
                                          "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}