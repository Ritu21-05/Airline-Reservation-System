package com.revati.airline;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ViewTicket extends JInternalFrame {

    private Connection con;
    private PreparedStatement pre;

    // UI Components
    private JTextField ticketIDField;
    private JButton searchButton;
    private JButton clearButton;

    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField genderField;
    private JTextField arrivalField;
    private JTextField departureField;
    private JTextField contactField;
    private JTextField flightNameField;

    public ViewTicket() {
        initComponents();
        connectDatabase();
    }

    private void initComponents() {
        setClosable(true);
        setTitle("View Ticket");
        setSize(600, 400);
        setLayout(null);

        JLabel ticketIDLabel = new JLabel("Ticket ID:");
        ticketIDLabel.setBounds(30, 30, 80, 25);
        add(ticketIDLabel);

        ticketIDField = new JTextField();
        ticketIDField.setBounds(120, 30, 150, 25);
        add(ticketIDField);

        searchButton = new JButton("Search");
        searchButton.setBounds(290, 30, 90, 25);
        add(searchButton);

        clearButton = new JButton("Clear");
        clearButton.setBounds(390, 30, 90, 25);
        add(clearButton);

        // Labels and TextFields
        add(createLabel("First Name:", 30, 80));
        firstNameField = createTextField(150, 80);

        add(createLabel("Last Name:", 30, 110));
        lastNameField = createTextField(150, 110);

        add(createLabel("Gender:", 30, 140));
        genderField = createTextField(150, 140);

        add(createLabel("Arrival:", 30, 170));
        arrivalField = createTextField(150, 170);

        add(createLabel("Departure:", 30, 200));
        departureField = createTextField(150, 200);

        add(createLabel("Contact:", 30, 230));
        contactField = createTextField(150, 230);

        add(createLabel("Flight Name:", 30, 260));
        flightNameField = createTextField(150, 260);

        add(firstNameField);
        add(lastNameField);
        add(genderField);
        add(arrivalField);
        add(departureField);
        add(contactField);
        add(flightNameField);

        // Actions
        searchButton.addActionListener(e -> searchTicket());
        clearButton.addActionListener(e -> clearFields());
    }

    private JLabel createLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 120, 25);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        return label;
    }

    private JTextField createTextField(int x, int y) {
        JTextField textField = new JTextField();
        textField.setBounds(x, y, 200, 25);
        textField.setEditable(false); // Non-editable (view-only)
        return textField;
    }

    private void connectDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/airline_project", "ritu", "ritu123");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Connection Failed");
        }
    }

    private void searchTicket() {
        String ticketID = ticketIDField.getText().trim();

        if (ticketID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Ticket ID");
            return;
        }

        try {
            pre = con.prepareStatement("SELECT * FROM Ticket WHERE TicketID = ?");
            pre.setString(1, ticketID);
            ResultSet rs = pre.executeQuery();

            if (rs.next()) {
                firstNameField.setText(rs.getString("FirstName"));
                lastNameField.setText(rs.getString("LastName"));
                genderField.setText(rs.getString("Gender"));
                arrivalField.setText(rs.getString("Arrival"));
                departureField.setText(rs.getString("Departure"));
                contactField.setText(rs.getString("Contact"));
                flightNameField.setText(getFlightName(rs.getString("FlightID")));
            } else {
                JOptionPane.showMessageDialog(this, "Ticket ID not found");
                clearFields();
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching ticket data");
        }
    }

    private String getFlightName(String flightID) {
        try {
            PreparedStatement pst = con.prepareStatement("SELECT FlightName FROM Flight WHERE FlightID = ?");
            pst.setString(1, flightID);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getString("FlightName");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Unknown";
    }

    private void clearFields() {
        firstNameField.setText("");
        lastNameField.setText("");
        genderField.setText("");
        arrivalField.setText("");
        departureField.setText("");
        contactField.setText("");
        flightNameField.setText("");
    }
}
