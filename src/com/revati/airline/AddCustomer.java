package com.revati.airline;

import java.awt.*;

import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import java.text.*;
import java.util.*;
import org.jdatepicker.impl.*;

public class AddCustomer {

    public static JInternalFrame getAddCustomerFrame(JDesktopPane desktop) {
        JInternalFrame internalFrame = new JInternalFrame("Add Customer", true, true, true, true);
        internalFrame.setSize(700, 400);
        internalFrame.setLayout(null);
        internalFrame.getContentPane().setBackground(Color.BLUE);
     // Center the internal frame in the desktop pane
        Dimension desktopSize = desktop.getSize();
        Dimension frameSize = internalFrame.getSize();
        internalFrame.setLocation((desktopSize.width - frameSize.width) / 2, 
                                  (desktopSize.height - frameSize.height) / 2);
      

        JLabel title = new JLabel("Welcome to the Customer Panel", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setOpaque(true);
        title.setBackground(Color.BLUE);
        title.setForeground(Color.WHITE);
        title.setBounds(150, 10, 450, 30);
        internalFrame.add(title);

        JPanel leftPanel = new JPanel(null);
        leftPanel.setBackground(Color.BLUE);
        leftPanel.setBounds(30, 60, 300, 250);
        internalFrame.add(leftPanel);

        JPanel rightPanel = new JPanel(null);
        rightPanel.setBackground(Color.BLUE);
        rightPanel.setBounds(350, 60, 300, 250);
        internalFrame.add(rightPanel);

        // Fields
        JTextField cusID = new JTextField();
        JTextField firstName = new JTextField();
        JTextField lastName = new JTextField();
        JTextField passportNo = new JTextField();
        JTextField nationalID = new JTextField();
        JTextArea address = new JTextArea();
        JTextField contactField = new JTextField();
        JRadioButton male = new JRadioButton("Male");
        JRadioButton female = new JRadioButton("Female");

        // Date Picker
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

        // Auto-ID Logic
        try {
            Connection con;
            PreparedStatement pre;
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/airline_project", "ritu", "ritu123");
            pre = con.prepareStatement("SELECT MAX(CustomerID) FROM Customer");
            ResultSet rs = pre.executeQuery();
            rs.next();
            String maxId = rs.getString("MAX(CustomerID)");
            if (maxId == null) {
                cusID.setText("CS001");
            } else {
                long id = Long.parseLong(maxId.substring(2));
                id++;
                cusID.setText("CS" + String.format("%03d", id));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Left panel form
        int y = 10;
        String[] labels = {"Customer ID", "First Name", "Last Name", "Passport No.", "National ID", "Address"};
        JComponent[] components = {cusID, firstName, lastName, passportNo, nationalID, new JScrollPane(address)};

        for (int i = 0; i < labels.length; i++) {
            JLabel lbl = new JLabel(labels[i]);
            lbl.setForeground(Color.WHITE);
            lbl.setBounds(10, y, 100, 25);
            leftPanel.add(lbl);

            JComponent comp = components[i];
            if (comp instanceof JScrollPane) {
                comp.setBounds(120, y, 150, 60);
                y += 35;
            } else {
                comp.setBounds(120, y, 150, 25);
            }
            leftPanel.add(comp);
            y += 35;
        }

        // Right panel form
        JLabel contactLbl = new JLabel("Contact");
        contactLbl.setForeground(Color.WHITE);
        contactLbl.setBounds(10, 10, 100, 25);
        rightPanel.add(contactLbl);
        contactField.setBounds(120, 10, 150, 25);
        rightPanel.add(contactField);

        JLabel genderLbl = new JLabel("Gender");
        genderLbl.setForeground(Color.WHITE);
        genderLbl.setBounds(10, 50, 100, 25);
        rightPanel.add(genderLbl);
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
        datePicker.setBounds(120, 90, 150, 30);
        rightPanel.add(datePicker);

        // Buttons
        JButton addBtn = new JButton("Add");
        addBtn.setBounds(200, 330, 100, 30);
        internalFrame.add(addBtn);

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setBounds(320, 330, 100, 30);
        internalFrame.add(cancelBtn);

        // Add Button Action
        addBtn.addActionListener(e -> {
            String id = cusID.getText();
            String fname = firstName.getText();
            String lname = lastName.getText();
            String passport = passportNo.getText();
            String natID = nationalID.getText();
            String addr = address.getText();
            String contact = contactField.getText();
            String gender = male.isSelected() ? "Male" : female.isSelected() ? "Female" : "";

            if (fname.isEmpty() || lname.isEmpty() || contact.isEmpty() || gender.isEmpty()) {
                JOptionPane.showMessageDialog(internalFrame, "Please fill all required fields.");
                return;
            }

            java.util.Date selectedDate = (java.util.Date) datePicker.getModel().getValue();
            if (selectedDate == null) {
                JOptionPane.showMessageDialog(internalFrame, "Please select a valid date of birth.");
                return;
            }
            java.sql.Date sqlDate = new java.sql.Date(selectedDate.getTime());

            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/airline_project", "ritu", "ritu123");
                PreparedStatement pre = con.prepareStatement("INSERT INTO Customer(CustomerID, FirstName, LastName, Passpoort, NationalID, Address, Contact, Gender, DOB) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
                pre.setString(1, id);
                pre.setString(2, fname);
                pre.setString(3, lname);
                pre.setString(4, passport);
                pre.setString(5, natID);
                pre.setString(6, addr);
                pre.setString(7, contact);
                pre.setString(8, gender);
                pre.setDate(9, sqlDate);
                pre.executeUpdate();

                JOptionPane.showMessageDialog(internalFrame, "Customer Added Successfully!");
                internalFrame.dispose();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(internalFrame, "Error: " + ex.getMessage());
            }
        });

        
     // Cancel button clears fields
        cancelBtn.addActionListener(e -> {
        
                        // Clear the form
                        firstName.setText("");
                        lastName.setText("");
                        passportNo.setText("");
                        nationalID.setText("");
                        address.setText("");
                        contactField.setText("");
                        genderGroup.clearSelection();
                        model.setValue(null);
        });

        internalFrame.setVisible(true);
        return internalFrame;
    }

    // Date formatting inner class
    public static class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
        private final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parse(text);
        }

        @Override
        public String valueToString(Object value) {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }
            return "";
        }
    }
}
