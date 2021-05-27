package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.ArrayList;
import java.util.List;

import controller.CreateSummaryController;
import db.DBException;
import model.Customer;
import model.Order;
import model.OrderLine;



public class CreateSummaryMenu {
	
	ShowSummaryMenu showSummaryMenu;
	
	private boolean isVisible;
    private JPanel createSummaryPanel;
    JTextField email;
    JTextField startDate;
    JTextField endDate;
    JLabel emailLbl;
    JLabel startDateLbl;
    JLabel endDateLbl;
    JLabel cityLbl;
    JLabel streetLbl;
    JLabel streetNumberLbl;
    JLabel zipCodeLbl;
    JLabel phoneNumberLbl;
    JLabel cityDataLbl;
    JLabel streetDataLbl;
    JLabel streetNumberDataLbl;
    JLabel zipCodeDataLbl;
    JLabel phoneNumberDataLbl;
    JLabel earningsLbl;
    JLabel numberOfOrdersLbl;
    JScrollPane scrollServicesAndEarnings;
    JButton buttonNext;
    JButton button;
    String emailString;
    
    CreateSummaryController csController;
    Customer customer;
    
    CreateSummaryMenu(JFrame mainWindow){
    	csController = new CreateSummaryController();
    	isVisible = false;
    	createSummaryPanel = new JPanel();
    	
    	createSummaryPanel.setLayout(null);
    	createSummaryPanel.setVerifyInputWhenFocusTarget(false);
    	createSummaryPanel.setBounds(mainWindow.getBounds());
    	mainWindow.add(createSummaryPanel);
    	createSummaryPanel.setVisible(false);
    	
    	emailLbl = new JLabel("Email: ");
    	emailLbl.setBounds(10, 20, 200, 20);
        emailLbl.setHorizontalAlignment(SwingConstants.RIGHT);
    	createSummaryPanel.add(emailLbl);
    	
        cityLbl = new JLabel("City: ");
        cityLbl.setBounds(10, 60, 200, 20);
        cityLbl.setVisible(false);
        cityLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        createSummaryPanel.add(cityLbl);
        
        streetLbl = new JLabel("Street: ");
        streetLbl.setBounds(10, 100, 200, 20);
        streetLbl.setVisible(false);
        streetLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        createSummaryPanel.add(streetLbl);
        
        streetNumberLbl = new JLabel("Street number: ");
        streetNumberLbl.setBounds(10, 140, 200, 20);
        streetNumberLbl.setVisible(false);
        streetNumberLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        createSummaryPanel.add(streetNumberLbl);
        
        zipCodeLbl = new JLabel("Zipcode: ");
        zipCodeLbl.setBounds(10, 180, 200, 20);
        zipCodeLbl.setVisible(false);
        zipCodeLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        createSummaryPanel.add(zipCodeLbl);
        
        phoneNumberLbl = new JLabel("Phone number: ");
        phoneNumberLbl.setBounds(10, 220, 200, 20);
        phoneNumberLbl.setVisible(false);
        phoneNumberLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        createSummaryPanel.add(phoneNumberLbl);
        
        startDateLbl = new JLabel("Start date: (dd-mm-yyyy)");
        startDateLbl.setBounds(10, 300, 200, 20);
        startDateLbl.setVisible(false);
        startDateLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        createSummaryPanel.add(startDateLbl);
        
        endDateLbl = new JLabel("End date: (dd-mm-yyyy)");
        endDateLbl.setBounds(10, 340, 200, 20);
        endDateLbl.setVisible(false);
        endDateLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        createSummaryPanel.add(endDateLbl);
        
        cityDataLbl = new JLabel("");
        cityDataLbl.setBounds(220, 60, 130, 20);
        cityDataLbl.setVisible(false);
        createSummaryPanel.add(cityDataLbl);
        
        streetDataLbl = new JLabel("");
        streetDataLbl.setBounds(220, 100, 130, 20);
        streetDataLbl.setVisible(false);
        createSummaryPanel.add(streetDataLbl);
        
        streetNumberDataLbl = new JLabel("");
        streetNumberDataLbl.setBounds(220, 140, 130, 20);
        streetNumberDataLbl.setVisible(false);
        createSummaryPanel.add(streetNumberDataLbl);
        
        zipCodeDataLbl = new JLabel("");
        zipCodeDataLbl.setBounds(220, 180, 130, 20);
        zipCodeDataLbl.setVisible(false);
        createSummaryPanel.add(zipCodeDataLbl);
        
        phoneNumberDataLbl = new JLabel("");
        phoneNumberDataLbl.setBounds(220, 220, 130, 20);
        phoneNumberDataLbl.setVisible(false);
        createSummaryPanel.add(phoneNumberDataLbl);
                
        // Input
        
        buttonNext = new JButton("Confirm");
        buttonNext.setVisible(false);
        
        email = new JTextField();
        email.setBounds(220, 20, 200, 20);
        createSummaryPanel.add(email);
        
        startDate = new JTextField();
        startDate.setBounds(220, 300, 200, 20);
        startDate.setVisible(false);
        createSummaryPanel.add(startDate);
        
        endDate = new JTextField();
        endDate.setBounds(220, 340, 200, 20);
        endDate.setVisible(false);
        createSummaryPanel.add(endDate);
        
        button = new JButton("Show info");
        button.setBounds(440, 20, 120, 20);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String emailString = email.getText();
                if (emailString.equals("")){
                    JOptionPane.showMessageDialog(null, "Please Fill In All Fields");
                }else {
                    try {
                        Customer customer = csController.showCustomerInfo(email.getText());
	                    cityLbl.setVisible(true);
	                    cityDataLbl.setText(customer.getCity());
	                    streetLbl.setVisible(true);
	                    streetDataLbl.setText(customer.getStreet());
	                    streetNumberLbl.setVisible(true);
	                    streetNumberDataLbl.setText(customer.getStreetNumber());
	                    zipCodeLbl.setVisible(true);
	                    zipCodeDataLbl.setText(customer.getZipCode());
	                    phoneNumberLbl.setVisible(true);
	                    phoneNumberDataLbl.setText(customer.getPhoneNumber());
	                    cityDataLbl.setVisible(true);
	                    streetDataLbl.setVisible(true);
	                    streetNumberDataLbl.setVisible(true);
	                    zipCodeDataLbl.setVisible(true);
	                    phoneNumberDataLbl.setVisible(true);
	                    startDateLbl.setVisible(true);
	                    endDateLbl.setVisible(true);
	                    startDate.setVisible(true);
	                    endDate.setVisible(true);
	                    buttonNext.setVisible(true);
                }catch (DBException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
            }});
        createSummaryPanel.add(button);
        
        
        buttonNext.setBounds(440, 340, 120, 20);
        buttonNext.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        		String emailString = email.getText();
                if (emailString.equals("")){
                    JOptionPane.showMessageDialog(null, "Please Fill In All Fields");
                }else if(startDate.getText().equals("") || endDate.getText().equals("")) {
        			JOptionPane.showMessageDialog(null, "Please Fill In All Dates");
        		}else {
        			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        			formatter = formatter.withLocale(Locale.UK);
        			try {
        				LocalDate startDateLD = LocalDate.parse(startDate.getText(), formatter);
        				LocalDate endDateLD = LocalDate.parse(endDate.getText(), formatter);
        				
        				csController.setInformation(emailString, startDateLD, endDateLD);
        				double earnings = csController.getEarnings();
        				int numberOfOrders = csController.getNumberOfOrders();
        				ArrayList<String> services = csController.getServices();
        				ArrayList<Integer> numberOfServicesPerType = csController.getNumberOfServicesPerType();
        				ArrayList<Double> earningsPerService = csController.getEarningsPerService();
        				new ShowSummaryMenu(earnings, numberOfOrders, services, 
        						numberOfServicesPerType, earningsPerService);
        			} catch (DateTimeParseException ex) {
                        JOptionPane.showMessageDialog(null, "Please Enter Valid Date");
                    } catch (DBException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
        		}
        	}
        });
        createSummaryPanel.add(buttonNext);
    }
   

    private void updatePanelVisibility(){
        createSummaryPanel.setVisible(isVisible);
        if (isVisible == false)
            updateComponentsVisibility(false);
    }

    

    private void updateComponentsVisibility(boolean flag){

    }

    public boolean getIsVisible(){
        return isVisible;
    }

    public void showMenu() {
    	setIsVisible(true);
    }
    
    public void hideMenu() {
    	setIsVisible(false);
    }
    
    public void setIsVisible(boolean isVisible){
        this.isVisible=isVisible;

        if (isVisible)
            createSummaryPanel.setVisible(true);
        else{
        	createSummaryPanel.setVisible(false);
            cityLbl.setVisible(false);
            streetLbl.setVisible(false);
            streetNumberLbl.setVisible(false);
            zipCodeLbl.setVisible(false);
            phoneNumberLbl.setVisible(false);
            cityDataLbl.setVisible(false);
            streetDataLbl.setVisible(false);
            streetNumberDataLbl.setVisible(false);
            zipCodeDataLbl.setVisible(false);
            phoneNumberDataLbl.setVisible(false);
            startDateLbl.setVisible(false);
            endDateLbl.setVisible(false);
            startDate.setVisible(false);
            endDate.setVisible(false);
            buttonNext.setVisible(false);
        }
    }
}
    
