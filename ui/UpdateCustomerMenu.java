package ui;

import controller.CustomerController;
import controller.EmployeeController;
import controller.OwnerController;
import db.DBException;
import model.*;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class UpdateCustomerMenu extends JDialog {
    private final JPanel contentPanel = new JPanel();
    private OwnerController ownerController =  new OwnerController();
    private EmployeeController employeeController =  new EmployeeController();
    private CustomerController customerController =  new CustomerController();
    private ArrayList<Owner> ownersInDB = null;
    private ArrayList<CustomerEmployee> employeesInDB =  null;
    private ArrayList<Owner> addedOwners = new ArrayList<>();
    private ArrayList<CustomerEmployee> addedEmployees =  new ArrayList<>();

    public UpdateCustomerMenu() {
        super(null,"Update customer",ModalityType.APPLICATION_MODAL);
        setBounds(100, 100, 800, 700);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setLayout(null);
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        //Labels
        JLabel oldEmailLbl = new JLabel("current email:");
        oldEmailLbl.setBounds(30, 20, 200, 20);
        oldEmailLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(oldEmailLbl);

        JLabel newEmailLbl = new JLabel("New email:");
        newEmailLbl.setBounds(30, 60, 200, 20);
        newEmailLbl.setVisible(false);
        newEmailLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(newEmailLbl);

        JLabel nameLbl = new JLabel("Name:");
        nameLbl.setBounds(30, 100, 200, 20);
        nameLbl.setVisible(false);
        nameLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(nameLbl);

        JLabel zipcodeLbl = new JLabel("Zipcode:");
        zipcodeLbl.setBounds(30, 140, 200, 20);
        zipcodeLbl.setVisible(false);
        zipcodeLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(zipcodeLbl);

        JLabel cityLbl = new JLabel("City:");
        cityLbl.setBounds(30, 180, 200, 20);
        cityLbl.setVisible(false);
        cityLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(cityLbl);

        JLabel streetLbl = new JLabel("Street:");
        streetLbl.setBounds(30, 220, 200, 20);
        streetLbl.setVisible(false);
        streetLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(streetLbl);

        JLabel streetNumberLbl = new JLabel("Street number:");
        streetNumberLbl.setBounds(30, 260, 200, 20);
        streetNumberLbl.setVisible(false);
        streetNumberLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(streetNumberLbl);

        JLabel phoneNumberLbl = new JLabel("Phone number:");
        phoneNumberLbl.setBounds(30, 300, 200, 20);
        phoneNumberLbl.setVisible(false);
        phoneNumberLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(phoneNumberLbl);

        JLabel individualIDLbl = new JLabel("ID:");
        individualIDLbl.setBounds(30, 340, 200, 20);
        individualIDLbl.setVisible(false);
        individualIDLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(individualIDLbl);

        JLabel individualVATLbl = new JLabel("VAT number:");
        individualVATLbl.setBounds(30, 380, 200, 20);
        individualVATLbl.setVisible(false);
        individualVATLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(individualVATLbl);

        JLabel selfMarketNumberLbl = new JLabel("Market number:");
        selfMarketNumberLbl.setBounds(30, 340, 200, 20);
        selfMarketNumberLbl.setVisible(false);
        selfMarketNumberLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(selfMarketNumberLbl);

        JLabel selfVATLbl = new JLabel("VAT number:");
        selfVATLbl.setBounds(30, 380, 200, 20);
        selfVATLbl.setVisible(false);
        selfVATLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(selfVATLbl);

        JLabel ltdRegistrationNumberLbl = new JLabel("Market registration number:");
        ltdRegistrationNumberLbl.setBounds(30, 340, 200, 20);
        ltdRegistrationNumberLbl.setVisible(false);
        ltdRegistrationNumberLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(ltdRegistrationNumberLbl);

        JLabel ltdMarketNumberLbl = new JLabel("Market number:");
        ltdMarketNumberLbl.setBounds(30, 380, 200, 20);
        ltdMarketNumberLbl.setVisible(false);
        ltdMarketNumberLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(ltdMarketNumberLbl);

        JLabel ltdArePayersLbl = new JLabel("Are payers ");
        ltdArePayersLbl.setBounds(30, 420, 200, 20);
        ltdArePayersLbl.setVisible(false);
        ltdArePayersLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(ltdArePayersLbl);

        JLabel ownersInDBLbl = new JLabel("Owners in DB:");
        ownersInDBLbl.setBounds(30, 460, 200, 20);
        ownersInDBLbl.setVisible(false);
        ownersInDBLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(ownersInDBLbl);

        JLabel addedOwnersLbl = new JLabel("Added owners:");
        addedOwnersLbl.setBounds(30, 500, 200, 20);
        addedOwnersLbl.setVisible(false);
        addedOwnersLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(addedOwnersLbl);

        JLabel employeesInDBLbl = new JLabel("Employees in DB:");
        employeesInDBLbl.setVisible(false);
        employeesInDBLbl.setBounds(30, 540, 200, 20);
        employeesInDBLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(employeesInDBLbl);

        JLabel addedEmployeesLbl = new JLabel("Added employees:");
        addedEmployeesLbl.setBounds(30, 580, 200, 20);
        addedEmployeesLbl.setVisible(false);
        addedEmployeesLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(addedEmployeesLbl);


        //Fields
        JTextField oldEmailTxt = new JTextField();
        oldEmailTxt.setBounds(240, 20, 200, 20);
        contentPanel.add(oldEmailTxt);

        JTextField newEmailTxt = new JTextField();
        newEmailTxt.setBounds(240, 60, 200, 20);
        newEmailTxt.setVisible(false);
        contentPanel.add(newEmailTxt);

        JTextField nameTxt = new JTextField();
        nameTxt.setBounds(240, 100, 200, 20);
        nameTxt.setVisible(false);
        contentPanel.add(nameTxt);

        JTextField zipcodeTxt = new JTextField();
        zipcodeTxt.setBounds(240, 140, 200, 20);
        zipcodeTxt.setVisible(false);
        contentPanel.add(zipcodeTxt);

        JTextField cityTxt = new JTextField();
        cityTxt.setBounds(240, 180, 200, 20);
        cityTxt.setVisible(false);
        contentPanel.add(cityTxt);

        JTextField streetTxt = new JTextField();
        streetTxt.setBounds(240, 220, 200, 20);
        streetTxt.setVisible(false);
        contentPanel.add(streetTxt);

        JTextField streetNumberTxt = new JTextField();
        streetNumberTxt.setBounds(240, 260, 200, 20);
        streetNumberTxt.setVisible(false);
        contentPanel.add(streetNumberTxt);

        JTextField phoneNumberTxt = new JTextField();
        phoneNumberTxt.setBounds(240, 300, 200, 20);
        phoneNumberTxt.setVisible(false);
        contentPanel.add(phoneNumberTxt);

        JTextField individualIDTxt = new JTextField();
        individualIDTxt.setBounds(240, 340, 200, 20);
        individualIDTxt.setVisible(false);
        contentPanel.add(individualIDTxt);

        JTextField individualVATTxt = new JTextField();
        individualVATTxt.setBounds(240, 380, 200, 20);
        individualVATTxt.setVisible(false);
        contentPanel.add(individualVATTxt);

        JTextField selfVATTxt = new JTextField();
        selfVATTxt.setBounds(240, 340, 200, 20);
        selfVATTxt.setVisible(false);
        contentPanel.add(selfVATTxt);

        JTextField selfMarketNumberTxt = new JTextField();
        selfMarketNumberTxt.setBounds(240, 380, 200, 20);
        selfMarketNumberTxt.setVisible(false);
        contentPanel.add(selfMarketNumberTxt);

        JTextField ltdRegistrationNumberTxt = new JTextField();
        ltdRegistrationNumberTxt.setBounds(240, 340, 200, 20);
        ltdRegistrationNumberTxt.setVisible(false);
        contentPanel.add(ltdRegistrationNumberTxt);

        JTextField ltdMarketNumberTxt = new JTextField();
        ltdMarketNumberTxt.setBounds(240, 380, 200, 20);
        ltdMarketNumberTxt.setVisible(false);
        contentPanel.add(ltdMarketNumberTxt);

        JCheckBox ltdArePayersCheckBox = new JCheckBox();
        ltdArePayersCheckBox.setBounds(240, 420, 200, 20);
        ltdArePayersCheckBox.setVisible(false);
        contentPanel.add(ltdArePayersCheckBox);

        try {
            ownersInDB = ownerController.getAllOwnersFromDB();
            employeesInDB =  employeeController.getAllEmployeesFromDB();
        } catch (DBException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            dispose();
            return;
        }

        JComboBox<String> ownersInDBBox = new JComboBox<>();
        for (Owner owner : ownersInDB){
            ownersInDBBox.addItem(owner.getFirstName() + " " + owner.getSurName() + " (" + owner.getId() + ")");
        }
        ownersInDBBox.setBounds(240, 460, 200, 20);
        ownersInDBBox.setVisible(false);
        contentPanel.add(ownersInDBBox);

        JComboBox<String> addedOwnersBox = new JComboBox<>();
        addedOwnersBox.setBounds(240, 500, 200, 20);
        addedOwnersBox.setVisible(false);
        contentPanel.add(addedOwnersBox);

        JComboBox<String> employeesInDBBox = new JComboBox<>();
        for (CustomerEmployee employee : employeesInDB){
            employeesInDBBox.addItem(employee.getFirstName() + " " + employee.getSurName() + " (" + employee.getId() + ")");
        }
        employeesInDBBox.setBounds(240, 540, 200, 20);
        employeesInDBBox.setVisible(false);
        contentPanel.add(employeesInDBBox);

        JComboBox<String> addedEmployeesBox = new JComboBox<>();
        addedEmployeesBox.setBounds(240, 580, 200, 20);
        addedEmployeesBox.setVisible(false);
        contentPanel.add(addedEmployeesBox);

        JButton addOwnerBtn = new JButton();
        addOwnerBtn.setText("Add owner");
        addOwnerBtn.setBounds(450, 460, 150, 20);
        addOwnerBtn.setVisible(false);
        addOwnerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!addedOwners.contains(ownersInDB.get(ownersInDBBox.getSelectedIndex()))) {
                    addedOwners.add(ownersInDB.get(ownersInDBBox.getSelectedIndex()));

                    addedOwnersBox.removeAllItems();
                    for (Owner owner : ownersInDB) {
                        addedOwnersBox.addItem(owner.getFirstName() + " " + owner.getSurName() + " (" + owner.getId() + ")");
                    }
                }
            }
        });
        contentPanel.add(addOwnerBtn);

        JButton removeOwnerBtn = new JButton();
        removeOwnerBtn.setText("Remove owner");
        removeOwnerBtn.setBounds(450, 500, 150, 20);
        removeOwnerBtn.setVisible(false);
        removeOwnerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (addedOwnersBox.getItemCount() > 0) {
                    ArrayList<Owner> temp = new ArrayList<>(addedOwners);

                    for (Owner owner : temp) {
                        if (addedOwnersBox.getSelectedItem().toString().endsWith("(" + owner.getId() + ")")) {
                            addedOwners.remove(owner);
                        }
                    }

                    addedOwnersBox.removeAllItems();
                    for (Owner owner : addedOwners) {
                        ownersInDBBox.addItem(owner.getFirstName() + " " + owner.getSurName() + " (" + owner.getId() + ")");
                    }
                }
            }
        });
        contentPanel.add(removeOwnerBtn);

        JButton addEmployeeBtn = new JButton();
        addEmployeeBtn.setText("Add employee");
        addEmployeeBtn.setBounds(450, 540, 150, 20);
        addEmployeeBtn.setVisible(false);
        addEmployeeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!addedEmployees.contains(employeesInDB.get(employeesInDBBox.getSelectedIndex()))) {
                    addedEmployees.add(employeesInDB.get(employeesInDBBox.getSelectedIndex()));

                    addedEmployeesBox.removeAllItems();
                    for (CustomerEmployee employee : employeesInDB) {
                        addedEmployeesBox.addItem(employee.getFirstName() + " " + employee.getSurName() + " (" + employee.getId() + ")");
                    }
                }
            }
        });
        contentPanel.add(addEmployeeBtn);

        JButton removeEmployeeBtn = new JButton();
        removeEmployeeBtn.setText("Remove employee");
        removeEmployeeBtn.setBounds(450, 580, 150, 20);
        removeEmployeeBtn.setVisible(false);
        removeEmployeeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (addedEmployeesBox.getItemCount() > 0) {
                    ArrayList<CustomerEmployee> temp = new ArrayList<>(addedEmployees);

                    for (CustomerEmployee employee : temp) {
                        if (addedEmployeesBox.getSelectedItem().toString().endsWith("(" + employee.getId() + ")")) {
                            addedEmployees.remove(employee);
                        }
                    }

                    addedEmployeesBox.removeAllItems();
                    for (CustomerEmployee employee : addedEmployees) {
                        employeesInDBBox.addItem(employee.getFirstName() + " " + employee.getSurName() + " (" + employee.getId() + ")");
                    }
                }
            }
        });
        contentPanel.add(removeEmployeeBtn);

        JButton showBtn = new JButton();
        showBtn.setText("Show");
        showBtn.setBounds(450, 20, 80, 20);
        showBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!oldEmailTxt.getText().trim().equals("")) {
                    String customerType = "";
                    PrivateIndividual privateIndividual = null;
                    SelfEmployed selfEmployed = null;
                    LTD ltd = null;
                    Customer customer =  null;
                    try {
                        customerType = customerController.getCustomerTypeByEmailFromDB(oldEmailTxt.getText().trim());
                        if (customerType.equals("Private_individual")){
                            privateIndividual = customerController.getPrivateIndividual(oldEmailTxt.getText().trim());
                            customer = privateIndividual;
                        }else if(customerType.equals("Self_employed")){
                            selfEmployed = customerController.getSelfEmployed(oldEmailTxt.getText().trim());
                            customer = selfEmployed;
                        }else if(customerType.equals("LTD")){
                            ltd = customerController.getLTDByEmailFromDB(oldEmailTxt.getText().trim());
                            customer = ltd;
                        }
                    } catch (DBException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                        return;
                    }

                    newEmailTxt.setText(customer.getEmail());
                    nameTxt.setText(customer.getName());
                    zipcodeTxt.setText(customer.getZipCode());
                    cityTxt.setText(customer.getCity());
                    streetTxt.setText(customer.getStreet());
                    streetNumberTxt.setText(customer.getStreetNumber());
                    phoneNumberTxt.setText(customer.getPhoneNumber());

                    newEmailLbl.setVisible(true);
                    newEmailTxt.setVisible(true);
                    nameLbl.setVisible(true);
                    nameTxt.setVisible(true);
                    zipcodeLbl.setVisible(true);
                    zipcodeTxt.setVisible(true);
                    cityLbl.setVisible(true);
                    cityTxt.setVisible(true);
                    streetLbl.setVisible(true);
                    streetTxt.setVisible(true);
                    streetNumberLbl.setVisible(true);
                    streetNumberTxt.setVisible(true);
                    phoneNumberLbl.setVisible(true);
                    phoneNumberTxt.setVisible(true);

                    if (customerType.equals("Private_individual")) {
                        individualIDTxt.setText(privateIndividual.getId());
                        individualVATTxt.setText(privateIndividual.getVat());

                        selfVATTxt.setText("");
                        selfMarketNumberTxt.setText("");
                        ltdMarketNumberTxt.setText("");
                        ltdRegistrationNumberTxt.setText("");
                        ltdArePayersCheckBox.setSelected(false);
                        addedEmployeesBox.removeAllItems();
                        addedEmployees.removeAll(addedEmployees);
                        addedOwnersBox.removeAllItems();
                        addedOwners.removeAll(addedOwners);

                        individualIDLbl.setVisible(true);
                        individualIDTxt.setVisible(true);
                        individualVATLbl.setVisible(true);
                        individualVATTxt.setVisible(true);

                        ltdMarketNumberLbl.setVisible(false);
                        ltdMarketNumberTxt.setVisible(false);
                        ltdRegistrationNumberLbl.setVisible(false);
                        ltdRegistrationNumberTxt.setVisible(false);
                        ltdArePayersLbl.setVisible(false);
                        ltdArePayersCheckBox.setVisible(false);
                        ownersInDBLbl.setVisible(false);
                        ownersInDBBox.setVisible(false);
                        addOwnerBtn.setVisible(false);
                        addedOwnersLbl.setVisible(false);
                        addedOwnersBox.setVisible(false);
                        removeOwnerBtn.setVisible(false);
                        employeesInDBLbl.setVisible(false);
                        employeesInDBBox.setVisible(false);
                        addEmployeeBtn.setVisible(false);
                        addedEmployeesLbl.setVisible(false);
                        addedEmployeesBox.setVisible(false);
                        removeEmployeeBtn.setVisible(false);

                        selfVATLbl.setVisible(false);
                        selfVATTxt.setVisible(false);
                        selfMarketNumberLbl.setVisible(false);
                        selfMarketNumberTxt.setVisible(false);
                    } else if (customerType.equals("Self_employed")) {
                        selfVATTxt.setText(selfEmployed.getVat());
                        selfMarketNumberTxt.setText(selfEmployed.getMarketNumber());

                        individualIDTxt.setText("");
                        individualVATTxt.setText("");
                        ltdMarketNumberTxt.setText("");
                        ltdRegistrationNumberTxt.setText("");
                        ltdArePayersCheckBox.setSelected(false);
                        addedEmployeesBox.removeAllItems();
                        addedEmployees.removeAll(addedEmployees);
                        addedOwnersBox.removeAllItems();
                        addedOwners.removeAll(addedOwners);

                        selfVATLbl.setVisible(true);
                        selfVATTxt.setVisible(true);
                        selfMarketNumberLbl.setVisible(true);
                        selfMarketNumberTxt.setVisible(true);

                        ltdMarketNumberLbl.setVisible(false);
                        ltdMarketNumberTxt.setVisible(false);
                        ltdRegistrationNumberLbl.setVisible(false);
                        ltdRegistrationNumberTxt.setVisible(false);
                        ltdArePayersLbl.setVisible(false);
                        ltdArePayersCheckBox.setVisible(false);
                        ownersInDBLbl.setVisible(false);
                        ownersInDBBox.setVisible(false);
                        addOwnerBtn.setVisible(false);
                        addedOwnersLbl.setVisible(false);
                        addedOwnersBox.setVisible(false);
                        removeOwnerBtn.setVisible(false);
                        employeesInDBLbl.setVisible(false);
                        employeesInDBBox.setVisible(false);
                        addEmployeeBtn.setVisible(false);
                        addedEmployeesLbl.setVisible(false);
                        addedEmployeesBox.setVisible(false);
                        removeEmployeeBtn.setVisible(false);

                        individualIDLbl.setVisible(false);
                        individualIDTxt.setVisible(false);
                        individualVATLbl.setVisible(false);
                        individualVATTxt.setVisible(false);
                    } else if (customerType.equals("LTD")) {

                        ltdRegistrationNumberTxt.setText(ltd.getMarketRegistrationNumber());
                        ltdMarketNumberTxt.setText(ltd.getMarketNumber());
                        ltdArePayersCheckBox.setSelected(ltd.arePayers());

                        addedOwners.removeAll(addedOwners);
                        addedOwnersBox.removeAllItems();
                        addedOwners = ltd.getOwners();
                        for(Owner owner : addedOwners){
                            addedOwnersBox.addItem(owner.getFirstName() + " " + owner.getSurName() + " (" + owner.getId() + ")");
                        }

                        addedEmployees.removeAll(addedEmployees);
                        addedEmployeesBox.removeAllItems();
                        addedEmployees =  ltd.getEmployees();
                        for(CustomerEmployee employee : addedEmployees){
                            addedEmployeesBox.addItem(employee.getFirstName() + " " + employee.getSurName() + " (" + employee.getId() + ")");
                        }

                        selfVATTxt.setText("");
                        selfMarketNumberTxt.setText("");
                        individualIDTxt.setText("");
                        individualVATTxt.setText("");

                        ltdMarketNumberLbl.setVisible(true);
                        ltdMarketNumberTxt.setVisible(true);
                        ltdRegistrationNumberLbl.setVisible(true);
                        ltdRegistrationNumberTxt.setVisible(true);
                        ltdArePayersLbl.setVisible(true);
                        ltdArePayersCheckBox.setVisible(true);
                        ownersInDBLbl.setVisible(true);
                        ownersInDBBox.setVisible(true);
                        addOwnerBtn.setVisible(true);
                        addedOwnersLbl.setVisible(true);
                        addedOwnersBox.setVisible(true);
                        removeOwnerBtn.setVisible(true);
                        employeesInDBLbl.setVisible(true);
                        employeesInDBBox.setVisible(true);
                        addEmployeeBtn.setVisible(true);
                        addedEmployeesLbl.setVisible(true);
                        addedEmployeesBox.setVisible(true);
                        removeEmployeeBtn.setVisible(true);

                        individualIDLbl.setVisible(false);
                        individualIDTxt.setVisible(false);
                        individualVATLbl.setVisible(false);
                        individualVATTxt.setVisible(false);

                        selfVATLbl.setVisible(false);
                        selfVATTxt.setVisible(false);
                        selfMarketNumberLbl.setVisible(false);
                        selfMarketNumberTxt.setVisible(false);
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Please Enter Email");
                }
            }
        });
        contentPanel.add(showBtn);

        // Section with Update and Cancel buttons:
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        {
            JButton updateButton = new JButton("Update");
            updateButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (oldEmailTxt.getText().trim().equals("") || nameTxt.getText().trim().equals("") ||
                            zipcodeTxt.getText().trim().equals("") || cityTxt.getText().trim().equals("") ||
                            streetTxt.getText().trim().equals("") || streetNumberTxt.getText().trim().equals("") ||
                            phoneNumberTxt.getText().trim().equals("") || newEmailTxt.getText().trim().equals("")){
                        JOptionPane.showMessageDialog(null, "Please Fill In All Necessary Fields");
                    }else {
                        String oldEmail =  oldEmailTxt.getText().trim();
                        String newEmail =  newEmailTxt.getText().trim();
                        String name = nameTxt.getText().trim();
                        String zipcode = zipcodeTxt.getText().trim();
                        String city = cityTxt.getText().trim();
                        String street = streetTxt.getText().trim();
                        String streetNumber = streetNumberTxt.getText().trim();
                        String phoneNumber = phoneNumberTxt.getText().trim();

                        String customerType = "";
                        PrivateIndividual privateIndividual = null;
                        SelfEmployed selfEmployed = null;
                        LTD ltd = null;
                        try {
                            customerType = customerController.getCustomerTypeByEmailFromDB(oldEmail);
                            if (customerType.equals("Private_individual")){
                                privateIndividual = customerController.getPrivateIndividual(oldEmailTxt.getText().trim());
                            }else if(customerType.equals("Self_employed")){
                                selfEmployed = customerController.getSelfEmployed(oldEmailTxt.getText().trim());
                            }else if(customerType.equals("LTD")){
                                ltd = customerController.getLTDByEmailFromDB(oldEmailTxt.getText().trim());
                            }
                        } catch (DBException ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage());
                            return;
                        }

                        if (customerType.equals("Private_individual")) {
                            if (individualIDTxt.getText().trim().equals("") || individualVATTxt.getText().trim().equals("")) {
                                JOptionPane.showMessageDialog(null, "Please Fill In All Necessary Fields");
                            } else {
                                try {

                                    customerController.updatePrivateIndividual(privateIndividual, name, city, street,
                                            streetNumber, zipcode, newEmail, phoneNumber, individualIDTxt.getText().trim(),
                                            individualVATTxt.getText().trim());
                                    dispose();
                                    JOptionPane.showMessageDialog(null, "Private individual customer updated");
                                } catch (DBException ex) {
                                    JOptionPane.showMessageDialog(null, ex.getMessage());
                                }
                            }
                        } else if (customerType.equals("Self_employed")) {
                            if (selfVATTxt.getText().trim().equals("") || selfMarketNumberTxt.getText().trim().equals("")) {
                                JOptionPane.showMessageDialog(null, "Please Fill In All Necessary Fields");
                            } else {
                                try {
                                    customerController.updateSelfEmployed(selfEmployed, name, city, street,
                                            streetNumber, zipcode, newEmail, phoneNumber, selfVATTxt.getText().trim(),
                                            selfMarketNumberTxt.getText().trim());
                                    dispose();
                                    JOptionPane.showMessageDialog(null, "Self employed customer updated");
                                } catch (DBException ex) {
                                    JOptionPane.showMessageDialog(null, ex.getMessage());
                                }
                            }
                        } else if (customerType.equals("LTD")) {
                            if (ltdRegistrationNumberTxt.getText().trim().equals("") || ltdMarketNumberTxt.getText().trim().equals("") ||
                                    addedOwners.size() == 0) {
                                JOptionPane.showMessageDialog(null, "Please Fill In All Necessary Fields");
                            } else {
                                try {
                                    customerController.updateLTD(ltd, name, city, street,
                                            streetNumber, zipcode, newEmail, phoneNumber, addedEmployees, addedOwners, ltdRegistrationNumberTxt.getText().trim(),
                                            ltdMarketNumberTxt.getText().trim(), ltdArePayersCheckBox.isSelected());
                                    dispose();
                                    JOptionPane.showMessageDialog(null, "LTD customer updated");
                                } catch (DBException ex) {
                                    JOptionPane.showMessageDialog(null, ex.getMessage());

                                    if(ex.getMessage().startsWith("Employee with id") || ex.getMessage().startsWith("Owner with id")){
                                        try {
                                            ownersInDB = ownerController.getAllOwnersFromDB();
                                            employeesInDB =  employeeController.getAllEmployeesFromDB();

                                            addedOwners.removeAll(addedOwners);
                                            addedEmployees.removeAll(addedEmployees);

                                            ownersInDBBox.removeAllItems();
                                            for (Owner owner : ownersInDB){
                                                ownersInDBBox.addItem(owner.getFirstName() + " " + owner.getSurName() + " (" + owner.getId() + ")");
                                            }
                                            addedOwnersBox.removeAllItems();

                                            employeesInDBBox.removeAllItems();
                                            for (CustomerEmployee employee : employeesInDB){
                                                employeesInDBBox.addItem(employee.getFirstName() + " " + employee.getSurName() + " (" + employee.getId() + ")");
                                            }
                                            addedEmployeesBox.removeAllItems();
                                        } catch (DBException exception) {
                                            JOptionPane.showMessageDialog(null, exception.getMessage());
                                            dispose();
                                            return;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            });
            updateButton.setActionCommand("OK");
            buttonPane.add(updateButton);
            getRootPane().setDefaultButton(updateButton);
        }
        {
            JButton cancelButton = new JButton("Cancel");
            cancelButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    JOptionPane.showMessageDialog(null, "Customer not updated");
                }
            });
            cancelButton.setActionCommand("Cancel");
            buttonPane.add(cancelButton);
        }

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
