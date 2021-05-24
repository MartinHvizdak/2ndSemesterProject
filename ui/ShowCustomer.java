package ui;

import controller.CustomerController;
import db.DBException;
import model.*;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class ShowCustomer extends JDialog {
    private final JPanel contentPanel = new JPanel();
    private CustomerController customerController =  new CustomerController();
    private ArrayList<Owner> ownersInDB = null;
    private ArrayList<CustomerEmployee> employeesInDB =  null;

    public ShowCustomer() {
        super(null,"Show customer",ModalityType.APPLICATION_MODAL);
        setBounds(100, 100, 700, 1000);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setLayout(null);
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        //Labels
        JLabel emailLbl = new JLabel("Email:");
        emailLbl.setBounds(30, 20, 200, 20);
        emailLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(emailLbl);

        JLabel nameLbl = new JLabel("Name:");
        nameLbl.setBounds(30, 60, 200, 20);
        nameLbl.setVisible(false);
        nameLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(nameLbl);

        JLabel zipcodeLbl = new JLabel("Zipcode:");
        zipcodeLbl.setBounds(30, 100, 200, 20);
        zipcodeLbl.setVisible(false);
        zipcodeLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(zipcodeLbl);

        JLabel cityLbl = new JLabel("City:");
        cityLbl.setBounds(30, 140, 200, 20);
        cityLbl.setVisible(false);
        cityLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(cityLbl);

        JLabel streetLbl = new JLabel("Street:");
        streetLbl.setBounds(30, 180, 200, 20);
        streetLbl.setVisible(false);
        streetLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(streetLbl);

        JLabel streetNumberLbl = new JLabel("Street number:");
        streetNumberLbl.setBounds(30, 220, 200, 20);
        streetNumberLbl.setVisible(false);
        streetNumberLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(streetNumberLbl);

        JLabel phoneNumberLbl = new JLabel("Phone number:");
        phoneNumberLbl.setBounds(30, 260, 200, 20);
        phoneNumberLbl.setVisible(false);
        phoneNumberLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(phoneNumberLbl);

        JLabel individualIDLbl = new JLabel("ID:");
        individualIDLbl.setBounds(30, 300, 200, 20);
        individualIDLbl.setVisible(false);
        individualIDLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(individualIDLbl);

        JLabel individualVATLbl = new JLabel("VAT number:");
        individualVATLbl.setBounds(30, 340, 200, 20);
        individualVATLbl.setVisible(false);
        individualVATLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(individualVATLbl);

        JLabel selfMarketNumberLbl = new JLabel("Market number:");
        selfMarketNumberLbl.setBounds(30, 300, 200, 20);
        selfMarketNumberLbl.setVisible(false);
        selfMarketNumberLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(selfMarketNumberLbl);

        JLabel selfVATLbl = new JLabel("VAT number:");
        selfVATLbl.setBounds(30, 340, 200, 20);
        selfVATLbl.setVisible(false);
        selfVATLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(selfVATLbl);

        JLabel ltdRegistrationNumberLbl = new JLabel("Market registration number:");
        ltdRegistrationNumberLbl.setBounds(30, 300, 200, 20);
        ltdRegistrationNumberLbl.setVisible(false);
        ltdRegistrationNumberLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(ltdRegistrationNumberLbl);

        JLabel ltdMarketNumberLbl = new JLabel("Market number:");
        ltdMarketNumberLbl.setBounds(30, 340, 200, 20);
        ltdMarketNumberLbl.setVisible(false);
        ltdMarketNumberLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(ltdMarketNumberLbl);

        JLabel ltdArePayersLbl = new JLabel("Are payers:");
        ltdArePayersLbl.setBounds(30, 380, 200, 20);
        ltdArePayersLbl.setVisible(false);
        ltdArePayersLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(ltdArePayersLbl);

        JLabel ownersInDBLbl = new JLabel("Owners:");
        ownersInDBLbl.setBounds(30, 420, 200, 200);
        ownersInDBLbl.setVisible(false);
        ownersInDBLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(ownersInDBLbl);

        JLabel employeesInDBLbl = new JLabel("Employees:");
        employeesInDBLbl.setBounds(30, 640, 200, 200);
        employeesInDBLbl.setVisible(false);
        employeesInDBLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(employeesInDBLbl);


        //Fields
        JTextField emailTxt = new JTextField();
        emailTxt.setBounds(240, 20, 200, 20);
        contentPanel.add(emailTxt);

        JLabel nameDataLBL = new JLabel();
        nameDataLBL.setBounds(240, 60, 200, 20);
        nameDataLBL.setVisible(false);
        contentPanel.add(nameDataLBL);

        JLabel zipcodeDataLBL = new JLabel();
        zipcodeDataLBL.setBounds(240, 100, 200, 20);
        zipcodeDataLBL.setVisible(false);
        contentPanel.add(zipcodeDataLBL);

        JLabel cityDataLBL = new JLabel();
        cityDataLBL.setBounds(240, 140, 200, 20);
        cityDataLBL.setVisible(false);
        contentPanel.add(cityDataLBL);

        JLabel streetDataLBL = new JLabel();
        streetDataLBL.setBounds(240, 180, 200, 20);
        streetDataLBL.setVisible(false);
        contentPanel.add(streetDataLBL);

        JLabel streetNumberDataLBL = new JLabel();
        streetNumberDataLBL.setBounds(240, 220, 200, 20);
        streetNumberDataLBL.setVisible(false);
        contentPanel.add(streetNumberDataLBL);

        JLabel phoneNumberDataLBL = new JLabel();
        phoneNumberDataLBL.setBounds(240, 260, 200, 20);
        phoneNumberDataLBL.setVisible(false);
        contentPanel.add(phoneNumberDataLBL);

        JLabel individualIDDataLBL = new JLabel();
        individualIDDataLBL.setBounds(240, 300, 200, 20);
        individualIDDataLBL.setVisible(false);
        contentPanel.add(individualIDDataLBL);

        JLabel individualVATDataLBL = new JLabel();
        individualVATDataLBL.setBounds(240, 340, 200, 20);
        individualVATDataLBL.setVisible(false);
        contentPanel.add(individualVATDataLBL);

        JLabel selfVATDataLBL = new JLabel();
        selfVATDataLBL.setBounds(240, 300, 200, 20);
        selfVATDataLBL.setVisible(false);
        contentPanel.add(selfVATDataLBL);

        JLabel selfMarketNumberDataLBL = new JLabel();
        selfMarketNumberDataLBL.setBounds(240, 340, 200, 20);
        selfMarketNumberDataLBL.setVisible(false);
        contentPanel.add(selfMarketNumberDataLBL);

        JLabel ltdRegistrationNumberDataLBL = new JLabel();
        ltdRegistrationNumberDataLBL.setBounds(240, 300, 200, 20);
        ltdRegistrationNumberDataLBL.setVisible(false);
        contentPanel.add(ltdRegistrationNumberDataLBL);

        JLabel ltdMarketNumberDataLBL = new JLabel();
        ltdMarketNumberDataLBL.setBounds(240, 340, 200, 20);
        ltdMarketNumberDataLBL.setVisible(false);
        contentPanel.add(ltdMarketNumberDataLBL);

        JLabel ltdArePayersDataLBL = new JLabel();
        ltdArePayersDataLBL.setBounds(240, 380, 200, 20);
        ltdArePayersDataLBL.setVisible(false);
        contentPanel.add(ltdArePayersDataLBL);

        JScrollPane scrollPaneOwners = new JScrollPane();
        scrollPaneOwners.setBounds(240, 420, 200, 200);
        scrollPaneOwners.setVisible(false);
        contentPanel.add(scrollPaneOwners);

        JScrollPane scrollPaneEmployees = new JScrollPane();
        scrollPaneEmployees.setBounds(240, 640, 200, 200);
        scrollPaneEmployees.setVisible(false);
        contentPanel.add(scrollPaneEmployees);

        JButton showBtn = new JButton();
        showBtn.setText("Show");
        showBtn.setBounds(450, 20, 80, 20);
        showBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PrivateIndividual privateIndividual = null;
                SelfEmployed selfEmployed = null;
                LTD ltd = null;
                Customer customer =  null;
                String customerType = "";

                if(!emailTxt.getText().trim().equals("")) {
                    try {
                        customerType = customerController.getCustomerTypeByEmailFromDB(emailTxt.getText().trim());
                        if (customerType.equals("Private_individual")){
                            privateIndividual = customerController.getPrivateIndividual(emailTxt.getText().trim());
                            customer = privateIndividual;
                        }else if(customerType.equals("Self_employed")){
                            selfEmployed = customerController.getSelfEmployed(emailTxt.getText().trim());
                            customer = selfEmployed;
                        }else if(customerType.equals("LTD")){
                            ltd = customerController.getLTDByEmailFromDB(emailTxt.getText().trim());
                            customer = ltd;
                        }
                    } catch (DBException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                        return;
                    }

                    nameDataLBL.setText(customer.getName());
                    zipcodeDataLBL.setText(customer.getZipCode());
                    cityDataLBL.setText(customer.getCity());
                    streetDataLBL.setText(customer.getStreet());
                    streetNumberDataLBL.setText(customer.getStreetNumber());
                    phoneNumberDataLBL.setText(customer.getPhoneNumber());

                    nameLbl.setVisible(true);
                    nameDataLBL.setVisible(true);
                    zipcodeLbl.setVisible(true);
                    zipcodeDataLBL.setVisible(true);
                    cityLbl.setVisible(true);
                    cityDataLBL.setVisible(true);
                    streetLbl.setVisible(true);
                    streetDataLBL.setVisible(true);
                    streetNumberLbl.setVisible(true);
                    streetNumberDataLBL.setVisible(true);
                    phoneNumberLbl.setVisible(true);
                    phoneNumberDataLBL.setVisible(true);

                    if (customerType.equals("Private_individual")) {
                        individualIDDataLBL.setText(privateIndividual.getId());
                        individualVATDataLBL.setText(privateIndividual.getVat());

                        individualIDLbl.setVisible(true);
                        individualIDDataLBL.setVisible(true);
                        individualVATLbl.setVisible(true);
                        individualVATDataLBL.setVisible(true);

                        ltdMarketNumberLbl.setVisible(false);
                        ltdMarketNumberDataLBL.setVisible(false);
                        ltdRegistrationNumberLbl.setVisible(false);
                        ltdRegistrationNumberDataLBL.setVisible(false);
                        ltdArePayersLbl.setVisible(false);
                        ltdArePayersDataLBL.setVisible(false);
                        ownersInDBLbl.setVisible(false);
                        scrollPaneOwners.setVisible(false);
                        employeesInDBLbl.setVisible(false);
                        scrollPaneEmployees.setVisible(false);

                        selfVATLbl.setVisible(false);
                        selfVATDataLBL.setVisible(false);
                        selfMarketNumberLbl.setVisible(false);
                        selfMarketNumberDataLBL.setVisible(false);
                    } else if (customerType.equals("Self_employed")) {
                        selfVATDataLBL.setText(selfEmployed.getVat());
                        selfMarketNumberDataLBL.setText(selfEmployed.getMarketNumber());

                        selfVATLbl.setVisible(true);
                        selfVATDataLBL.setVisible(true);
                        selfMarketNumberLbl.setVisible(true);
                        selfMarketNumberDataLBL.setVisible(true);

                        ltdMarketNumberLbl.setVisible(false);
                        ltdMarketNumberDataLBL.setVisible(false);
                        ltdRegistrationNumberLbl.setVisible(false);
                        ltdRegistrationNumberDataLBL.setVisible(false);
                        ltdArePayersLbl.setVisible(false);
                        ltdArePayersDataLBL.setVisible(false);
                        ownersInDBLbl.setVisible(false);
                        scrollPaneOwners.setVisible(false);
                        employeesInDBLbl.setVisible(false);
                        scrollPaneEmployees.setVisible(false);

                        individualIDLbl.setVisible(false);
                        individualIDDataLBL.setVisible(false);
                        individualVATLbl.setVisible(false);
                        individualVATDataLBL.setVisible(false);
                    } else if (customerType.equals("LTD")) {

                        ltdRegistrationNumberDataLBL.setText(ltd.getMarketRegistrationNumber());
                        ltdMarketNumberDataLBL.setText(ltd.getMarketNumber());
                        ltdArePayersDataLBL.setText(String.valueOf(ltd.arePayers()));

                        DefaultListModel<String> listModelOwners =  new DefaultListModel<>();
                        for (Owner owner : ltd.getOwners()){
                            listModelOwners.addElement(owner.getFirstName() + " " + owner.getSurName() + " (" + owner.getId() + ")");
                        }
                        JList<String> ownersBox = new JList<>(listModelOwners);
                        scrollPaneOwners.setViewportView(ownersBox);

                        DefaultListModel<String> listModelEmployees =  new DefaultListModel<>();
                        for (CustomerEmployee employee : ltd.getEmployees()){
                            listModelEmployees.addElement(employee.getFirstName() + " " + employee.getSurName() + " (" + employee.getId() + ")");
                        }
                        JList<String> employeesBox = new JList<>(listModelEmployees);
                        scrollPaneEmployees.setViewportView(employeesBox);

                        ltdMarketNumberLbl.setVisible(true);
                        ltdMarketNumberDataLBL.setVisible(true);
                        ltdRegistrationNumberLbl.setVisible(true);
                        ltdRegistrationNumberDataLBL.setVisible(true);
                        ltdArePayersLbl.setVisible(true);
                        ltdArePayersDataLBL.setVisible(true);
                        ownersInDBLbl.setVisible(true);
                        scrollPaneOwners.setVisible(true);
                        employeesInDBLbl.setVisible(true);
                        scrollPaneEmployees.setVisible(true);

                        individualIDLbl.setVisible(false);
                        individualIDDataLBL.setVisible(false);
                        individualVATLbl.setVisible(false);
                        individualVATDataLBL.setVisible(false);

                        selfVATLbl.setVisible(false);
                        selfVATDataLBL.setVisible(false);
                        selfMarketNumberLbl.setVisible(false);
                        selfMarketNumberDataLBL.setVisible(false);
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Please Enter Email");
                }
            }
        });
        contentPanel.add(showBtn);

        // Section with ok and Cancel buttons:
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        {
            JButton okButton = new JButton("OK");
            okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });
            okButton.setActionCommand("OK");
            buttonPane.add(okButton);
            getRootPane().setDefaultButton(okButton);
        }
        {
            JButton cancelButton = new JButton("Cancel");
            cancelButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });
            cancelButton.setActionCommand("Cancel");
            buttonPane.add(cancelButton);
        }

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
