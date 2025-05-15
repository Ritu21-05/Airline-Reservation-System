package com.revati.airline;


	import javax.swing.*;

import java.awt.Color;
import java.awt.event.*;

	public class Main {
	    public static void main(String[] args) {
	        // Create main frame
	        JFrame frame = new JFrame("Airline System");
	        
	        

	        // Create menu bar
	        JMenuBar menuBar = new JMenuBar();

	        // Create menus
	        JMenu customerMenu = new JMenu("Customer");
	        JMenu flightMenu = new JMenu("Flight");
	        JMenu ticketMenu = new JMenu("Ticket");
	        JMenu adminMenu = new JMenu("Admin");

	        // Create menu items
	        JMenuItem addCustomer = new JMenuItem("Add Customer");
	        JMenuItem searchCustomer = new JMenuItem("Search Customer");
	        JMenuItem addFlight = new JMenuItem("Add Flight");
	        JMenuItem bookFlight = new JMenuItem("Book Flight");
	        JMenuItem viewTicket = new JMenuItem("View Ticket");
	        JMenuItem addAdmin = new JMenuItem("Add Admin");

	        // Add items to menus
	        customerMenu.add(addCustomer);
	        customerMenu.add(searchCustomer);
	        flightMenu.add(addFlight);
	        flightMenu.add(bookFlight);
	        ticketMenu.add(viewTicket);
	        adminMenu.add(addAdmin);

	        // Add menus to menu bar
	        menuBar.add(customerMenu);
	        menuBar.add(flightMenu);
	        menuBar.add(ticketMenu);
	        menuBar.add(adminMenu);

	        // Set menu bar to frame
	        frame.setJMenuBar(menuBar);

	        // Create desktop pane
	        JDesktopPane desktopPane = new JDesktopPane();
	        desktopPane.setBackground(new Color(135,206,250));

	        // Event handlers for menu items
	        addCustomer.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		AddCustomer a=new AddCustomer();
	        		desktopPane.add(a.getAddCustomerFrame(desktopPane));
	        		
	        		
	        	}
	        });
	        addFlight.addActionListener(new ActionListener(){
	        	public void actionPerformed(ActionEvent e) {
	        		AddFlight b=new AddFlight(desktopPane);
	        }
	        });
	        addAdmin.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		AddAdmin c=new AddAdmin(desktopPane);
	        	}
	        });
	        searchCustomer.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		SearchCustomer d=new SearchCustomer(desktopPane);
	        	}
	        });
	        bookFlight.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		BookFlight f=new BookFlight(desktopPane);
	        		desktopPane.add(f);
	        		
	        		
	        		
	        	}
	        });
	        viewTicket.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		ViewTicket g=new ViewTicket(desktopPane);
	        		desktopPane.add(g);
	        		g.setVisible(true);

	        	}
	        });
	        		        
	        // Add desktop pane to frame
	        frame.add(desktopPane);

	        // Frame settings
	        frame.setSize(1000, 800);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setVisible(true);	      
	        frame.setLocationRelativeTo(null);
	        frame.setContentPane(desktopPane);
	    }

		

	        
	    
	}



