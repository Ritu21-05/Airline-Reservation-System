package com.revati.airline;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AddAdmin {

    public AddAdmin(JDesktopPane desktopPane) {
        JInternalFrame internalFrame = new JInternalFrame("Admin Panel", true, true, true, true);
        internalFrame.setSize(700, 400);
        internalFrame.setLayout(null);
        internalFrame.getContentPane().setBackground(Color.LIGHT_GRAY);
     // Center the internal frame in the desktop pane
        Dimension desktopSize = desktopPane.getSize();
        Dimension frameSize = internalFrame.getSize();
        internalFrame.setLocation((desktopSize.width - frameSize.width) / 2, 
                                  (desktopSize.height - frameSize.height) / 2);

        // Title
        JLabel title = new JLabel("Welcome to the Admin Panel", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setOpaque(true);
        title.setBackground(Color.LIGHT_GRAY);
        title.setForeground(Color.BLUE);
        title.setBounds(180, 10, 350, 30);
        internalFrame.add(title);

        // Left Panel
        JPanel leftPanel = new JPanel(null);
        leftPanel.setBackground(Color.BLUE);
        leftPanel.setBounds(30, 60, 300, 250);
        internalFrame.add(leftPanel);

        // Right Panel
        JPanel rightPanel = new JPanel(null);
        rightPanel.setBackground(Color.BLUE);
        rightPanel.setBounds(350, 60, 300, 250);
        internalFrame.add(rightPanel);

        // Admin ID
        JLabel idLabel = new JLabel("Admin ID");
        idLabel.setForeground(Color.WHITE);
        idLabel.setBounds(10, 20, 100, 25);
        leftPanel.add(idLabel);

        JTextField adminIDField = new JTextField();
        adminIDField.setBounds(120, 20, 150, 25);
        adminIDField.setEditable(false);
        leftPanel.add(adminIDField);

        // First Name
        JLabel fnameLabel = new JLabel("First Name");
        fnameLabel.setForeground(Color.WHITE);
        fnameLabel.setBounds(10, 60, 100, 25);
        leftPanel.add(fnameLabel);

        JTextField fnameField = new JTextField();
        fnameField.setBounds(120, 60, 150, 25);
        leftPanel.add(fnameField);

        // Last Name
        JLabel lnameLabel = new JLabel("Last Name");
        lnameLabel.setForeground(Color.WHITE);
        lnameLabel.setBounds(10, 100, 100, 25);
        leftPanel.add(lnameLabel);

        JTextField lnameField = new JTextField();
        lnameField.setBounds(120, 100, 150, 25);
        leftPanel.add(lnameField);

        // Username
        JLabel usernameLbl = new JLabel("Username");
        usernameLbl.setForeground(Color.WHITE);
        usernameLbl.setBounds(10, 20, 100, 25);
        rightPanel.add(usernameLbl);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(120, 20, 150, 25);
        rightPanel.add(usernameField);

        // Password
        JLabel passwordLbl = new JLabel("Password");
        passwordLbl.setForeground(Color.WHITE);
        passwordLbl.setBounds(10, 60, 100, 25);
        rightPanel.add(passwordLbl);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(120, 60, 150, 25);
        rightPanel.add(passwordField);

        // Buttons
        JButton addBtn = new JButton("Add");
        addBtn.setBounds(30, 120, 100, 30);
        rightPanel.add(addBtn);

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setBounds(140, 120, 100, 30);
        rightPanel.add(cancelBtn);

        // Auto-ID Logic
        try {
            Connection con;
            PreparedStatement pre;
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/airline_project", "ritu", "ritu123");
            pre = con.prepareStatement("SELECT MAX(AdminID) FROM Admin");
            ResultSet rs = pre.executeQuery();
            rs.next();
            String maxId = rs.getString("MAX(AdminID)");
            if (maxId == null) {
                adminIDField.setText("AD001");
            } else {
                long id = Long.parseLong(maxId.substring(2));
                id++;
                adminIDField.setText("AD" + String.format("%03d", id));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Add button logic
        addBtn.addActionListener(e -> {
            String adminID = adminIDField.getText();
            String fname = fnameField.getText();
            String lname = lnameField.getText();
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/airline_project", "ritu", "ritu123");

                String query = "INSERT INTO Admin (AdminID, FirstName, LastName, UserName, Password) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement pst = con.prepareStatement(query);
                pst.setString(1, adminID);
                pst.setString(2, fname);
                pst.setString(3, lname);
                pst.setString(4, username);
                pst.setString(5, password);

                int result = pst.executeUpdate();
                if (result > 0) {
                    JOptionPane.showMessageDialog(internalFrame, "Admin Added Successfully with ID: " + adminID);
                } else {
                    JOptionPane.showMessageDialog(internalFrame, "Failed to add admin.");
                }

                con.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(internalFrame, "Error: " + ex.getMessage());
            }
        });

        // Cancel button clears fields
        cancelBtn.addActionListener(e -> {
            fnameField.setText("");
            lnameField.setText("");
            usernameField.setText("");
            passwordField.setText("");
        });

        desktopPane.add(internalFrame);
        internalFrame.setVisible(true);
    }
}
