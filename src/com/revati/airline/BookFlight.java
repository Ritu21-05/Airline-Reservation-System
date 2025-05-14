package com.revati.airline;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Vector;

public class BookFlight extends JInternalFrame {

    private Connection con;
    private PreparedStatement pre;
    private String flightID;

    // UI Components
    private JComboBox<String> arrival;
    private JComboBox<String> departure;
    private JButton searchFlightButton;
    private JTable flightTable;

    private JTextField fare;
    private JTextField custIDField;
    private JButton searchCustomerButton;
    private JTextField firstName;
    private JTextField lastName;
    private JTextField contact;
    private JTextField gender;

    private JTextField totalTickets;
    private JButton calculateFareButton;
    private JTextField totalFareField;
    private JTextField discountField;

    private JButton bookButton;
    private JButton cancelButton;
    private JTextField ticketIDField;

    public BookFlight() {
        initComponents();
        connectDatabase();
        autoID();
    }

    private void initComponents() {
        setClosable(true);
        setTitle("Book The Dream Flight Ticket For You");
        setSize(900, 600);
        setLayout(null);
        setVisible(true);

        // Left Panel (Flight Search)
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.BLUE);
        leftPanel.setBounds(10, 10, 530, 550);
        leftPanel.setLayout(null);

        JLabel searchLabel = new JLabel("Search your Flights");
        searchLabel.setForeground(Color.WHITE);
        searchLabel.setFont(new Font("Arial", Font.BOLD, 18));
        searchLabel.setBounds(180, 10, 200, 30);
        leftPanel.add(searchLabel);

        JLabel arrivalLabel = new JLabel("Arrival");
        arrivalLabel.setForeground(Color.WHITE);
        arrivalLabel.setBounds(50, 60, 60, 25);
        leftPanel.add(arrivalLabel);

        arrival = new JComboBox<>(new String[]{"Delhi", "Kolkata", "Chennai", "Mumbai", "Bangalore"});
        arrival.setBounds(120, 60, 150, 25);
        leftPanel.add(arrival);

        JLabel departureLabel = new JLabel("Departure");
        departureLabel.setForeground(Color.WHITE);
        departureLabel.setBounds(300, 60, 70, 25);
        leftPanel.add(departureLabel);

        departure = new JComboBox<>(new String[]{"Delhi", "Kolkata", "Chennai", "Mumbai", "Bangalore"});
        departure.setBounds(380, 60, 130, 25);
        leftPanel.add(departure);

        searchFlightButton = new JButton("Search");
        searchFlightButton.setBounds(210, 100, 100, 30);
        leftPanel.add(searchFlightButton);

        // Table for flights
        flightTable = new JTable();
        flightTable.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Flight ID", "Flight Name", "Arrival", "Departure", "Duration", "Date"}
        ));
        JScrollPane scrollPane = new JScrollPane(flightTable);
        scrollPane.setBounds(20, 150, 490, 380);
        leftPanel.add(scrollPane);

        add(leftPanel);

        // Right Panel (Booking Details)
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.BLUE);
        rightPanel.setBounds(550, 10, 320, 550);
        rightPanel.setLayout(null);

        JLabel ticketLabel = new JLabel("Ticket ID");
        ticketLabel.setForeground(Color.RED);
        ticketLabel.setFont(new Font("Arial", Font.BOLD, 16));
        ticketLabel.setBounds(130, 10, 80, 25);
        rightPanel.add(ticketLabel);

        ticketIDField = new JTextField();
        ticketIDField.setBounds(210, 10, 90, 25);
        ticketIDField.setEditable(false);
        rightPanel.add(ticketIDField);

        JLabel custIDLabel = new JLabel("Customer ID");
        custIDLabel.setForeground(Color.WHITE);
        custIDLabel.setBounds(20, 60, 90, 25);
        rightPanel.add(custIDLabel);

        custIDField = new JTextField();
        custIDField.setBounds(120, 60, 120, 25);
        rightPanel.add(custIDField);

        searchCustomerButton = new JButton("Search");
        searchCustomerButton.setBounds(250, 60, 60, 25);
        rightPanel.add(searchCustomerButton);

        JLabel firstNameLabel = new JLabel("First Name");
        firstNameLabel.setForeground(Color.WHITE);
        firstNameLabel.setBounds(20, 110, 90, 25);
        rightPanel.add(firstNameLabel);

        firstName = new JTextField();
        firstName.setBounds(120, 110, 190, 25);
        firstName.setEditable(false);
        rightPanel.add(firstName);

        JLabel lastNameLabel = new JLabel("Last Name");
        lastNameLabel.setForeground(Color.WHITE);
        lastNameLabel.setBounds(20, 150, 90, 25);
        rightPanel.add(lastNameLabel);

        lastName = new JTextField();
        lastName.setBounds(120, 150, 190, 25);
        lastName.setEditable(false);
        rightPanel.add(lastName);

        JLabel contactLabel = new JLabel("Contact");
        contactLabel.setForeground(Color.WHITE);
        contactLabel.setBounds(20, 190, 90, 25);
        rightPanel.add(contactLabel);

        contact = new JTextField();
        contact.setBounds(120, 190, 190, 25);
        contact.setEditable(false);
        rightPanel.add(contact);

        JLabel genderLabel = new JLabel("Gender");
        genderLabel.setForeground(Color.WHITE);
        genderLabel.setBounds(20, 230, 90, 25);
        rightPanel.add(genderLabel);

        gender = new JTextField();
        gender.setBounds(120, 230, 190, 25);
        gender.setEditable(false);
        rightPanel.add(gender);

        JLabel fareLabel = new JLabel("Fare");
        fareLabel.setForeground(Color.WHITE);
        fareLabel.setBounds(20, 270, 90, 25);
        rightPanel.add(fareLabel);

        fare = new JTextField();
        fare.setBounds(120, 270, 190, 25);
        fare.setEditable(false);
        rightPanel.add(fare);

        JLabel totalTicketsLabel = new JLabel("Total Tickets");
        totalTicketsLabel.setForeground(Color.WHITE);
        totalTicketsLabel.setBounds(20, 310, 90, 25);
        rightPanel.add(totalTicketsLabel);

        totalTickets = new JTextField();
        totalTickets.setBounds(120, 310, 120, 25);
        rightPanel.add(totalTickets);

        calculateFareButton = new JButton("Calculate Fare");
        calculateFareButton.setBounds(250, 310, 60, 25);
        rightPanel.add(calculateFareButton);

        JLabel totalFareLabel = new JLabel("Total Fare");
        totalFareLabel.setForeground(Color.WHITE);
        totalFareLabel.setBounds(20, 350, 90, 25);
        rightPanel.add(totalFareLabel);

        totalFareField = new JTextField();
        totalFareField.setBounds(120, 350, 190, 25);
        totalFareField.setEditable(false);
        rightPanel.add(totalFareField);

        JLabel discountLabel = new JLabel("Discount");
        discountLabel.setForeground(Color.WHITE);
        discountLabel.setBounds(20, 390, 90, 25);
        rightPanel.add(discountLabel);

        discountField = new JTextField();
        discountField.setBounds(120, 390, 190, 25);
        rightPanel.add(discountField);

        bookButton = new JButton("Book");
        bookButton.setBounds(60, 440, 90, 30);
        rightPanel.add(bookButton);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(180, 440, 90, 30);
        rightPanel.add(cancelButton);

        add(rightPanel);

        // Event Listeners
        searchFlightButton.addActionListener(e -> searchFlights());
        flightTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                flightTableMouseClicked(evt);
            }
        });
        searchCustomerButton.addActionListener(e -> searchCustomer());
        calculateFareButton.addActionListener(e -> calculateFare());
        bookButton.addActionListener(e -> bookTicket());
        cancelButton.addActionListener(e -> clearFields());
    }

    private void connectDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/airline_project", "ritu", "ritu123");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void autoID() {
        try {
            pre = con.prepareStatement("SELECT MAX(TicketID) FROM Ticket");
            ResultSet rs = pre.executeQuery();
            rs.next();
            if (rs.getString(1) == null) {
                ticketIDField.setText("TK001");
            } else {
                long idNum = Long.parseLong(rs.getString(1).substring(2));
                idNum++;
                ticketIDField.setText("TK" + String.format("%03d", idNum));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void searchFlights() {
        try {
            String arr = arrival.getSelectedItem().toString();
            String dep = departure.getSelectedItem().toString();

            pre = con.prepareStatement("SELECT FlightID, FlightName, Arrival, Departure, Duration, Date FROM Flight WHERE Arrival = ? AND Departure = ?");
            pre.setString(1, arr);
            pre.setString(2, dep);

            ResultSet rs = pre.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int cols = rsmd.getColumnCount();

            DefaultTableModel dtm = (DefaultTableModel) flightTable.getModel();
            dtm.setRowCount(0);

            while (rs.next()) {
                Vector<String> v = new Vector<>();
                for (int i = 1; i <= cols; i++) {
                    v.add(rs.getString(i));
                }
                dtm.addRow(v);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void flightTableMouseClicked(MouseEvent evt) {
        try {
            int row = flightTable.getSelectedRow();
            DefaultTableModel dtm = (DefaultTableModel) flightTable.getModel();
            flightID = dtm.getValueAt(row, 0).toString();

            pre = con.prepareStatement("SELECT Fare FROM Flight WHERE FlightID = ?");
            pre.setString(1, flightID);
            ResultSet rs = pre.executeQuery();

            if (rs.next()) {
                fare.setText(rs.getString("Fare"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void searchCustomer() {
        try {
            String custID = custIDField.getText();

            pre = con.prepareStatement("SELECT * FROM Customer WHERE CustomerID = ?");
            pre.setString(1, custID);

            ResultSet rs = pre.executeQuery();

            if (rs.next()) {
                firstName.setText(rs.getString("firstName"));
                lastName.setText(rs.getString("lastName"));
                contact.setText(rs.getString("contact"));
                gender.setText(rs.getString("gender"));
            } else {
                JOptionPane.showMessageDialog(null, "Customer does not exist");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void calculateFare() {
        try {
            int baseFare = Integer.parseInt(fare.getText());
            int tickets = Integer.parseInt(totalTickets.getText());
            int totalFare = baseFare * tickets;
            totalFareField.setText(String.valueOf(totalFare));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Enter valid number of tickets");
        }
    }

    private void bookTicket() {
        try {
            String ticketID = ticketIDField.getText();
            String custID = custIDField.getText();
            String arr = arrival.getSelectedItem().toString();
            String dep = departure.getSelectedItem().toString();
            String fName = firstName.getText();
            String lName = lastName.getText();
            String cont = contact.getText();
            String gen = gender.getText();

            pre = con.prepareStatement("INSERT INTO Ticket(TicketID, FlightID, CustomerID, Arrival, Departure, FirstName, LastName, Contact, Gender) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            pre.setString(1, ticketID);
            pre.setString(2, flightID);
            pre.setString(3, custID);
            pre.setString(4, arr);
            pre.setString(5, dep);
            pre.setString(6, fName);
            pre.setString(7, lName);
            pre.setString(8, cont);
            pre.setString(9, gen);

            pre.executeUpdate();
            JOptionPane.showMessageDialog(null, "Ticket booked successfully");
            autoID();
            clearFields();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        custIDField.setText("");
        firstName.setText("");
        lastName.setText("");
        contact.setText("");
        gender.setText("");
        fare.setText("");
        totalTickets.setText("");
        totalFareField.setText("");
        discountField.setText("");
        DefaultTableModel dtm = (DefaultTableModel) flightTable.getModel();
        dtm.setRowCount(0);
    }
}