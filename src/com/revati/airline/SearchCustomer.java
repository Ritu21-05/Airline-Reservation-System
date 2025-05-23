package com.revati.airline;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class SearchCustomer {
    public SearchCustomer(JDesktopPane desktopPane) {
        JInternalFrame internalFrame = new JInternalFrame("Search Customer", true, true, true, true);
        internalFrame.setSize(750, 400);
        internalFrame.setLayout(null);
        internalFrame.getContentPane().setBackground(Color.BLUE);
     // Center the internal frame in the desktop pane
        Dimension desktopSize = desktopPane.getSize();
        Dimension frameSize = internalFrame.getSize();
        internalFrame.setLocation((desktopSize.width - frameSize.width) / 2, 
                                  (desktopSize.height - frameSize.height) / 2);

        JLabel title = new JLabel("Welcome to the Search Panel", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setOpaque(true);
        title.setBackground(Color.BLUE);
        title.setForeground(Color.WHITE);
        title.setBounds(200, 10, 350, 30);
        internalFrame.add(title);

        JPanel leftPanel = new JPanel(null);
        leftPanel.setBackground(Color.BLUE);
        leftPanel.setBounds(30, 60, 340, 250);
        internalFrame.add(leftPanel);

        JPanel rightPanel = new JPanel(null);
        rightPanel.setBackground(Color.BLUE);
        rightPanel.setBounds(400, 60, 300, 250);
        internalFrame.add(rightPanel);

        // === Left Panel ===
        int y = 10;
        JLabel customerIdLbl = new JLabel("Customer ID");
        customerIdLbl.setForeground(Color.WHITE);
        customerIdLbl.setBounds(10, y, 100, 25);
        leftPanel.add(customerIdLbl);

        JTextField customerIdField = new JTextField();
        customerIdField.setBounds(110, y, 120, 25);
        leftPanel.add(customerIdField);

        JButton searchBtn = new JButton("Search");
        searchBtn.setBounds(240, y, 80, 25);
        leftPanel.add(searchBtn);
        y += 40;

        JLabel fnameLbl = new JLabel("First Name");
        fnameLbl.setForeground(Color.WHITE);
        fnameLbl.setBounds(10, y, 100, 25);
        leftPanel.add(fnameLbl);

        JTextField fnameField = new JTextField();
        fnameField.setBounds(110, y, 210, 25);
        leftPanel.add(fnameField);
        y += 35;

        JLabel lnameLbl = new JLabel("Last Name");
        lnameLbl.setForeground(Color.WHITE);
        lnameLbl.setBounds(10, y, 100, 25);
        leftPanel.add(lnameLbl);

        JTextField lnameField = new JTextField();
        lnameField.setBounds(110, y, 210, 25);
        leftPanel.add(lnameField);
        y += 35;

        JLabel passportLbl = new JLabel("Passport No.");
        passportLbl.setForeground(Color.WHITE);
        passportLbl.setBounds(10, y, 100, 25);
        leftPanel.add(passportLbl);

        JTextField passportField = new JTextField();
        passportField.setBounds(110, y, 210, 25);
        leftPanel.add(passportField);
        y += 35;

        JLabel nicLbl = new JLabel("National ID");
        nicLbl.setForeground(Color.WHITE);
        nicLbl.setBounds(10, y, 100, 25);
        leftPanel.add(nicLbl);

        JTextField nicField = new JTextField();
        nicField.setBounds(110, y, 210, 25);
        leftPanel.add(nicField);
        y += 35;

        JLabel addressLbl = new JLabel("Address");
        addressLbl.setForeground(Color.WHITE);
        addressLbl.setBounds(10, y, 100, 25);
        leftPanel.add(addressLbl);

        JTextArea addressArea = new JTextArea();
        JScrollPane sp = new JScrollPane(addressArea);
        sp.setBounds(110, y, 210, 60);
        leftPanel.add(sp);

        // === Right Panel ===
        JLabel contactLbl = new JLabel("Contact");
        contactLbl.setForeground(Color.WHITE);
        contactLbl.setBounds(10, 10, 100, 25);
        rightPanel.add(contactLbl);

        JTextField contactField = new JTextField();
        contactField.setBounds(120, 10, 150, 25);
        rightPanel.add(contactField);

        JLabel genderLbl = new JLabel("Gender");
        genderLbl.setForeground(Color.WHITE);
        genderLbl.setBounds(10, 50, 100, 25);
        rightPanel.add(genderLbl);

        JRadioButton male = new JRadioButton("Male");
        JRadioButton female = new JRadioButton("Female");
        male.setBounds(120, 50, 70, 25);
        female.setBounds(190, 50, 80, 25);
        male.setBackground(Color.BLUE);
        female.setBackground(Color.BLUE);
        male.setForeground(Color.WHITE);
        female.setForeground(Color.WHITE);
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(male);
        genderGroup.add(female);
        rightPanel.add(male);
        rightPanel.add(female);

        JLabel dobLbl = new JLabel("Date of Birth");
        dobLbl.setForeground(Color.WHITE);
        dobLbl.setBounds(10, 90, 100, 25);
        rightPanel.add(dobLbl);

        JTextField dobField = new JTextField();
        dobField.setBounds(120, 90, 150, 25);
        rightPanel.add(dobField);

        // === Search Button Action ===
        searchBtn.addActionListener(e -> {
            String custID = customerIdField.getText();

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/airline_project", "ritu", "ritu123");

                PreparedStatement pst = con.prepareStatement("SELECT * FROM Customer WHERE CustomerID = ?");
                pst.setString(1, custID);
                ResultSet rs = pst.executeQuery();

                if (rs.next()) {
                    fnameField.setText(rs.getString("FirstName"));
                    lnameField.setText(rs.getString("LastName"));
                    passportField.setText(rs.getString("Passpoort"));
                    nicField.setText(rs.getString("NationalID"));
                    addressArea.setText(rs.getString("Address"));
                    contactField.setText(rs.getString("Contact"));
                    dobField.setText(rs.getString("DOB"));

                    String gender = rs.getString("Gender");
                    if ("Male".equalsIgnoreCase(gender)) {
                        male.setSelected(true);
                    } else {
                        female.setSelected(true);
                    }
                } else {
                    JOptionPane.showMessageDialog(internalFrame, "Customer not found!", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(internalFrame, "Error: " + ex.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
            }
        });

        desktopPane.add(internalFrame);
        internalFrame.setVisible(true);
    }
}
