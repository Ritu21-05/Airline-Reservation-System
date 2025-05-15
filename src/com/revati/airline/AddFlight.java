package com.revati.airline;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Properties;
import org.jdatepicker.impl.*;

public class AddFlight {
    private JTextField flightIDField, flightNameField, arrivalField, departureField, durationField, seatsField, fareField;
    private JDatePickerImpl datePicker;

    public AddFlight(JDesktopPane desktopPane) {
        JInternalFrame internalFrame = new JInternalFrame("Add Flight", true, true, true, true);
        internalFrame.setSize(700, 450);
        internalFrame.setLayout(null);
        internalFrame.getContentPane().setBackground(Color.LIGHT_GRAY);
     // Center the internal frame in the desktop pane
        Dimension desktopSize = desktopPane.getSize();
        Dimension frameSize = internalFrame.getSize();
        internalFrame.setLocation((desktopSize.width - frameSize.width) / 2, 
                                  (desktopSize.height - frameSize.height) / 2);

        JLabel title = new JLabel("Welcome to the Flight Panel", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setBounds(180, 10, 350, 30);
        title.setForeground(Color.BLUE);
        internalFrame.add(title);

        // Left Panel
        JPanel leftPanel = new JPanel(null);
        leftPanel.setBackground(Color.BLUE);
        leftPanel.setBounds(30, 60, 300, 280);
        internalFrame.add(leftPanel);

        // Right Panel
        JPanel rightPanel = new JPanel(null);
        rightPanel.setBackground(Color.BLUE);
        rightPanel.setBounds(350, 60, 300, 280);
        internalFrame.add(rightPanel);

        // Left Side Fields
        JLabel[] leftLabels = {
            new JLabel("Flight ID"), new JLabel("Flight Name"),
            new JLabel("Arrival"), new JLabel("Departure"), new JLabel("Duration")
        };

        JTextField[] leftFields = {
            flightIDField = new JTextField(),
            flightNameField = new JTextField(),
            arrivalField = new JTextField(),
            departureField = new JTextField(),
            durationField = new JTextField()
        };

        int y = 10;
        for (int i = 0; i < leftLabels.length; i++) {
            leftLabels[i].setForeground(Color.WHITE);
            leftLabels[i].setBounds(10, y, 100, 25);
            leftPanel.add(leftLabels[i]);

            leftFields[i].setBounds(120, y, 150, 25);
            leftPanel.add(leftFields[i]);
            y += 35;
        }

        // Right Side Fields
        JLabel seatsLbl = new JLabel("Seats");
        seatsLbl.setForeground(Color.WHITE);
        seatsLbl.setBounds(10, 10, 100, 25);
        rightPanel.add(seatsLbl);

        seatsField = new JTextField();
        seatsField.setBounds(120, 10, 150, 25);
        rightPanel.add(seatsField);

        JLabel fareLbl = new JLabel("Fare");
        fareLbl.setForeground(Color.WHITE);
        fareLbl.setBounds(10, 50, 100, 25);
        rightPanel.add(fareLbl);

        fareField = new JTextField();
        fareField.setBounds(120, 50, 150, 25);
        rightPanel.add(fareField);

        JLabel dateLbl = new JLabel("Date of Flight");
        dateLbl.setForeground(Color.WHITE);
        dateLbl.setBounds(10, 90, 100, 25);
        rightPanel.add(dateLbl);

        // Date Picker
        UtilDateModel model = new UtilDateModel();
        Properties props = new Properties();
        props.put("text.today", "Today");
        props.put("text.month", "Month");
        props.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, props);
        datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());
        datePicker.setBounds(120, 90, 150, 25);
        rightPanel.add(datePicker);

        // Add and Delete Buttons
        JButton addBtn = new JButton("Add");
        addBtn.setBounds(170, 360, 100, 30);
        internalFrame.add(addBtn);

        JButton deleteBtn = new JButton("Cancel");
        deleteBtn.setBounds(290, 360, 100, 30);
        internalFrame.add(deleteBtn);

        addBtn.addActionListener(e -> addFlight());
        deleteBtn.addActionListener(e -> clearFields());

        // Auto Generate Flight ID
        generateFlightID();

        // Add frame to desktop
        desktopPane.add(internalFrame);
        internalFrame.setVisible(true);
    }

    private void generateFlightID() {
        try {
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/airline_project", "ritu", "ritu123");
            PreparedStatement pre = con.prepareStatement("SELECT MAX(FlightID) FROM Flight");
            ResultSet rs = pre.executeQuery();
            rs.next();
            String maxId = rs.getString(1);
            if (maxId == null) {
                flightIDField.setText("FL001");
            } else {
                long id = Long.parseLong(maxId.substring(2));
                id++;
                flightIDField.setText("FL" + String.format("%03d", id));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void addFlight() {
        try {
            String id = flightIDField.getText();
            String name = flightNameField.getText();
            String arrival = arrivalField.getText();
            String departure = departureField.getText();
            String duration = durationField.getText();
            String seats = seatsField.getText();
            String fare = fareField.getText();
            java.util.Date selectedDate = (java.util.Date) datePicker.getModel().getValue();

            if (selectedDate == null) {
                JOptionPane.showMessageDialog(null, "Please select a valid date.");
                return;
            }

            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy");
            String dateStr = sdf.format(selectedDate);

            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/airline_project", "ritu", "ritu123");
            PreparedStatement pre = con.prepareStatement(
                "INSERT INTO Flight (FlightID, FlightName, Arrival, Departure, Duration, Seats, Fare, Date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
            );

            pre.setString(1, id);
            pre.setString(2, name);
            pre.setString(3, arrival);
            pre.setString(4, departure);
            pre.setString(5, duration);
            pre.setString(6, seats);
            pre.setString(7, fare);
            pre.setString(8, dateStr);

            pre.executeUpdate();
            JOptionPane.showMessageDialog(null, "Flight Added Successfully");
            generateFlightID();  // Refresh ID for next entry
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }
    }

    
    private void clearFields() {
        flightNameField.setText("");
        arrivalField.setText("");
        departureField.setText("");
        durationField.setText("");
        seatsField.setText("");
        fareField.setText("");
        datePicker.getModel().setValue(null);
    }
}
