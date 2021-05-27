package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.ArrayList;
import java.util.List;

import controller.CreateContractsController;
import controller.CreateSummaryController;
import controller.CustomerController;
import db.DBException;
import model.Customer;
import model.Order;
import model.OrderLine;



public class CreateContractsMenu {
	
	ShowSummaryMenu showSummaryMenu;
	
	private boolean isVisible;
    private JPanel createSummaryPanel;
    JTextField email;
    JTextField businessName;
    JTextField representativeName;
    JTextField vatInput;
    
    JLabel emailLbl;
    JLabel businessNameLbl;
    JLabel representativeNamebl;
    JLabel vatInputbl;
    JLabel nameLbl;
    JLabel cityLbl;
    JLabel streetLbl;
    JLabel streetNumberLbl;
    JLabel zipCodeLbl;
    JLabel phoneNumberLbl;
    JLabel nameDataLbl;
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
    
    CreateContractsController contractsController;
    CustomerController customerController;
    Customer customer;
    
    CreateContractsMenu(JFrame mainWindow){
    	contractsController = new CreateContractsController();
    	customerController = new CustomerController();
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
    	
    	nameLbl = new JLabel("Name: ");
    	nameLbl.setBounds(10, 60, 200, 20);
    	nameLbl.setVisible(false);
        nameLbl.setHorizontalAlignment(SwingConstants.RIGHT);
    	createSummaryPanel.add(nameLbl);   	
    	
        cityLbl = new JLabel("City: ");
        cityLbl.setBounds(10, 100, 200, 20);
        cityLbl.setVisible(false);
        cityLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        createSummaryPanel.add(cityLbl);
        
        streetLbl = new JLabel("Street: ");
        streetLbl.setBounds(10, 140, 200, 20);
        streetLbl.setVisible(false);
        streetLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        createSummaryPanel.add(streetLbl);
        
        streetNumberLbl = new JLabel("Street number: ");
        streetNumberLbl.setBounds(10, 180, 200, 20);
        streetNumberLbl.setVisible(false);
        streetNumberLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        createSummaryPanel.add(streetNumberLbl);
        
        zipCodeLbl = new JLabel("Zipcode: ");
        zipCodeLbl.setBounds(10, 220, 200, 20);
        zipCodeLbl.setVisible(false);
        zipCodeLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        createSummaryPanel.add(zipCodeLbl);
        
        phoneNumberLbl = new JLabel("Phone number: ");
        phoneNumberLbl.setBounds(10, 260, 200, 20);
        phoneNumberLbl.setVisible(false);
        phoneNumberLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        createSummaryPanel.add(phoneNumberLbl);
        
        
        businessNameLbl = new JLabel("Business name");
        businessNameLbl.setBounds(10, 300, 200, 20);
        businessNameLbl.setVisible(false);
        businessNameLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        createSummaryPanel.add(businessNameLbl);
        
        representativeNamebl = new JLabel("Representative name");
        representativeNamebl.setBounds(10, 340, 200, 20);
        representativeNamebl.setVisible(false);
        representativeNamebl.setHorizontalAlignment(SwingConstants.RIGHT);
        createSummaryPanel.add(representativeNamebl);
        
        vatInputbl = new JLabel("VAT Number");
        vatInputbl.setBounds(10, 380, 200, 20);
        vatInputbl.setVisible(false);
        vatInputbl.setHorizontalAlignment(SwingConstants.RIGHT);
        createSummaryPanel.add(vatInputbl);
        
        nameDataLbl = new JLabel("");
        nameDataLbl.setBounds(220, 60, 130, 20);
        nameDataLbl.setVisible(false);
        createSummaryPanel.add(nameDataLbl);
        
        cityDataLbl = new JLabel("");
        cityDataLbl.setBounds(220, 100, 130, 20);
        cityDataLbl.setVisible(false);
        createSummaryPanel.add(cityDataLbl);
        
        streetDataLbl = new JLabel("");
        streetDataLbl.setBounds(220, 140, 130, 20);
        streetDataLbl.setVisible(false);
        createSummaryPanel.add(streetDataLbl);
        
        streetNumberDataLbl = new JLabel("");
        streetNumberDataLbl.setBounds(220, 180, 130, 20);
        streetNumberDataLbl.setVisible(false);
        createSummaryPanel.add(streetNumberDataLbl);
        
        zipCodeDataLbl = new JLabel("");
        zipCodeDataLbl.setBounds(220, 220, 130, 20);
        zipCodeDataLbl.setVisible(false);
        createSummaryPanel.add(zipCodeDataLbl);
        
        phoneNumberDataLbl = new JLabel("");
        phoneNumberDataLbl.setBounds(220, 260, 130, 20);
        phoneNumberDataLbl.setVisible(false);
        createSummaryPanel.add(phoneNumberDataLbl);
                
        // Input
        
        buttonNext = new JButton("Confirm");
        buttonNext.setVisible(false);
        
        email = new JTextField();
        email.setBounds(220, 20, 200, 20);
        createSummaryPanel.add(email);
        
        businessName = new JTextField();
        businessName.setBounds(220, 300, 200, 20);
        businessName.setVisible(false);
        createSummaryPanel.add(businessName);
        
        representativeName = new JTextField();
        representativeName.setBounds(220, 340, 200, 20);
        representativeName.setVisible(false);
        createSummaryPanel.add(representativeName);
        
        vatInput = new JTextField();
        vatInput.setBounds(220, 380, 200, 20);
        vatInput.setVisible(false);
        createSummaryPanel.add(vatInput);
        
        button = new JButton("Show info");
        button.setBounds(440, 20, 120, 20);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String emailString = email.getText();
                if (emailString.equals("")){
                    JOptionPane.showMessageDialog(null, "Please Fill In All Fields");
                }else {
                    try {
                    	Customer customer = customerController.getCustomerByEmailFromDB(email.getText());
	                    nameLbl.setVisible(true);
	                    nameDataLbl.setText(customer.getEmail());
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
	                    nameDataLbl.setVisible(true);
	                    cityDataLbl.setVisible(true);
	                    streetDataLbl.setVisible(true);
	                    streetNumberDataLbl.setVisible(true);
	                    zipCodeDataLbl.setVisible(true);
	                    phoneNumberDataLbl.setVisible(true);
	                    businessNameLbl.setVisible(true);
	                    representativeNamebl.setVisible(true);
	                    vatInput.setVisible(true);
	                    businessName.setVisible(true);
	                    representativeName.setVisible(true);
	                    vatInputbl.setVisible(true);
	                    buttonNext.setVisible(true);
                }catch (DBException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
            }});
        createSummaryPanel.add(button);
        
        
        buttonNext.setBounds(440, 380, 120, 20);
        buttonNext.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        		String emailString = email.getText();
                if (emailString.equals("")){
                    JOptionPane.showMessageDialog(null, "Please Fill In All Fields");
                }else if(vatInput.getText().equals("") || businessName.getText().equals("") || representativeName.getText().equals("")) {
        			JOptionPane.showMessageDialog(null, "Please Fill In All Dates");
        		}else {
        			
        			try {
        				String bussinessNameLD = businessName.getText();
        				String bussinessRepresentativeLD = representativeName.getText();
        				String vatNumberLD = vatInput.getText();
        				
        				if (contractsController.writeTemplatedFile(bussinessNameLD, bussinessRepresentativeLD, vatNumberLD, bussinessRepresentativeLD, email.getText()) == true) {
        					JOptionPane.showMessageDialog(null, "Contract regarding providing services created successfully");
        				} else {
        					JOptionPane.showMessageDialog(null, "Contract regarding providing services creation failed!");
        				}
        				
        				if (contractsController.writeServiceFile(email.getText()) == true) {
        					JOptionPane.showMessageDialog(null, "Contract regarding providing services created successfully");
        				} else {
        					JOptionPane.showMessageDialog(null, "Contract regarding providing services creation failed!");
        				}
        				setIsVisible(false);
        				//contractsController.writeServiceFile(email.getText());
        				
        				
        				/*double earnings = csController.getEarnings();
        				int numberOfOrders = csController.getNumberOfOrders();
        				ArrayList<String> services = csController.getServices();
        				ArrayList<Integer> numberOfServicesPerType = csController.getNumberOfServicesPerType();
        				ArrayList<Double> earningsPerService = csController.getEarningsPerService();
        				new ShowSummaryMenu(earnings, numberOfOrders, services, 
        						numberOfServicesPerType, earningsPerService);*/
        			}catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                        setIsVisible(false);
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
            nameLbl.setVisible(false);
            cityLbl.setVisible(false);
            streetLbl.setVisible(false);
            streetNumberLbl.setVisible(false);
            zipCodeLbl.setVisible(false);
            phoneNumberLbl.setVisible(false);
            nameDataLbl.setVisible(false);
            cityDataLbl.setVisible(false);
            streetDataLbl.setVisible(false);
            streetNumberDataLbl.setVisible(false);
            zipCodeDataLbl.setVisible(false);
            phoneNumberDataLbl.setVisible(false);
            businessName.setVisible(false);
            businessNameLbl.setVisible(false);
            representativeName.setVisible(false);
            representativeNamebl.setVisible(false);
            vatInput.setVisible(false);
            vatInputbl.setVisible(false);
            buttonNext.setVisible(false);
        }
    }
}
    
