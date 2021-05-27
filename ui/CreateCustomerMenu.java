package ui;

import controller.CustomerController;
import controller.EmployeeController;
import controller.OwnerController;
import db.DBException;
import model.Employee;
import model.Owner;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class CreateCustomerMenu extends JDialog {
    private final JPanel contentPanel = new JPanel();
    private OwnerController ownerController =  new OwnerController();
    private EmployeeController employeeController =  new EmployeeController();
    private CustomerController customerController =  new CustomerController();
    private ArrayList<Owner> ownersInDB = null;
    private ArrayList<Employee> employeesInDB =  null;

    public CreateCustomerMenu() {
        super(null,"Create customer",ModalityType.APPLICATION_MODAL);
        setBounds(100, 100, 700, 700);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setLayout(null);
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        //Labels
        JLabel emailLbl = new JLabel("Email:");
        emailLbl.setBounds(30, 20, 200, 20);
        emailLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(emailLbl);

        JLabel zipcodeLbl = new JLabel("Zipcode:");
        zipcodeLbl.setBounds(30, 60, 200, 20);
        zipcodeLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(zipcodeLbl);

        JLabel cityLbl = new JLabel("City:");
        cityLbl.setBounds(30, 100, 200, 20);
        cityLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(cityLbl);

        JLabel streetLbl = new JLabel("Street:");
        streetLbl.setBounds(30, 140, 200, 20);
        streetLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(streetLbl);

        JLabel streetNumberLbl = new JLabel("Street number:");
        streetNumberLbl.setBounds(30, 180, 200, 20);
        streetNumberLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(streetNumberLbl);

        JLabel phoneNumberLbl = new JLabel("Phone number:");
        phoneNumberLbl.setBounds(30, 220, 200, 20);
        phoneNumberLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(phoneNumberLbl);

        JLabel customerTypeLbl = new JLabel("Customer type:");
        customerTypeLbl.setBounds(30, 260, 200, 20);
        customerTypeLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(customerTypeLbl);

        JLabel individualIDLbl = new JLabel("ID:");
        individualIDLbl.setBounds(30, 300, 200, 20);
        individualIDLbl.setVisible(true);
        individualIDLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(individualIDLbl);

        JLabel individualVATLbl = new JLabel("VAT number:");
        individualVATLbl.setBounds(30, 340, 200, 20);
        individualVATLbl.setVisible(true);
        individualVATLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(individualVATLbl);

        JLabel individualFirstNameLbl = new JLabel("First name:");
        individualFirstNameLbl.setBounds(30, 380, 200, 20);
        individualFirstNameLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(individualFirstNameLbl);

        JLabel individualSecondNameLbl = new JLabel("Second name:");
        individualSecondNameLbl.setBounds(30, 420, 200, 20);
        individualSecondNameLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(individualSecondNameLbl);

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

        JLabel selfFirstNameLbl = new JLabel("First name:");
        selfFirstNameLbl.setBounds(30, 380, 200, 20);
        selfFirstNameLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(selfFirstNameLbl);

        JLabel selfSecondNameLbl = new JLabel("Second name:");
        selfSecondNameLbl.setBounds(30, 420, 200, 20);
        selfSecondNameLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(selfSecondNameLbl);

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

        JLabel ltdCompanyNameLbl = new JLabel("Company name:");
        ltdCompanyNameLbl.setBounds(30, 380, 200, 20);
        ltdCompanyNameLbl.setVisible(false);
        ltdCompanyNameLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(ltdCompanyNameLbl);

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
        JTextField emailTxt = new JTextField();
        emailTxt.setBounds(240, 20, 200, 20);
        contentPanel.add(emailTxt);

        JTextField zipcodeTxt = new JTextField();
        zipcodeTxt.setBounds(240, 60, 200, 20);
        contentPanel.add(zipcodeTxt);

        JTextField cityTxt = new JTextField();
        cityTxt.setBounds(240, 100, 200, 20);
        contentPanel.add(cityTxt);

        JTextField streetTxt = new JTextField();
        streetTxt.setBounds(240, 140, 200, 20);
        contentPanel.add(streetTxt);

        JTextField streetNumberTxt = new JTextField();
        streetNumberTxt.setBounds(240, 180, 200, 20);
        contentPanel.add(streetNumberTxt);

        JTextField phoneNumberTxt = new JTextField();
        phoneNumberTxt.setBounds(240, 220, 200, 20);
        contentPanel.add(phoneNumberTxt);

        JTextField individualIDTxt = new JTextField();
        individualIDTxt.setBounds(240, 300, 200, 20);
        individualIDTxt.setVisible(true);
        contentPanel.add(individualIDTxt);

        JTextField individualVATTxt = new JTextField();
        individualVATTxt.setBounds(240, 340, 200, 20);
        individualVATTxt.setVisible(true);
        contentPanel.add(individualVATTxt);

        JTextField individualFirstNameTxt = new JTextField();
        individualFirstNameTxt.setBounds(240, 380, 200, 20);
        individualFirstNameTxt.setVisible(true);
        contentPanel.add(individualFirstNameTxt);

        JTextField individualSecondNameTxt = new JTextField();
        individualSecondNameTxt.setBounds(240, 420, 200, 20);
        individualSecondNameTxt.setVisible(true);
        contentPanel.add(individualSecondNameTxt);

        JTextField selfVATTxt = new JTextField();
        selfVATTxt.setBounds(240, 300, 200, 20);
        selfVATTxt.setVisible(false);
        contentPanel.add(selfVATTxt);

        JTextField selfMarketNumberTxt = new JTextField();
        selfMarketNumberTxt.setBounds(240, 340, 200, 20);
        selfMarketNumberTxt.setVisible(false);
        contentPanel.add(selfMarketNumberTxt);

        JTextField selfFirstNameTxt = new JTextField();
        selfFirstNameTxt.setBounds(240, 380, 200, 20);
        selfFirstNameTxt.setVisible(true);
        contentPanel.add(selfFirstNameTxt);

        JTextField selfSecondNameTxt = new JTextField();
        selfSecondNameTxt.setBounds(240, 420, 200, 20);
        selfSecondNameTxt.setVisible(true);
        contentPanel.add(selfSecondNameTxt);

        JTextField ltdRegistrationNumberTxt = new JTextField();
        ltdRegistrationNumberTxt.setBounds(240, 300, 200, 20);
        ltdRegistrationNumberTxt.setVisible(false);
        contentPanel.add(ltdRegistrationNumberTxt);

        JTextField ltdMarketNumberTxt = new JTextField();
        ltdMarketNumberTxt.setBounds(240, 340, 200, 20);
        ltdMarketNumberTxt.setVisible(false);
        contentPanel.add(ltdMarketNumberTxt);

        JTextField ltdCompanyNameTxt = new JTextField();
        ltdCompanyNameTxt.setBounds(240, 380, 200, 20);
        ltdCompanyNameTxt.setVisible(true);
        contentPanel.add(ltdCompanyNameTxt);

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
        for (Employee employee : employeesInDB){
            employeesInDBBox.addItem(employee.getFirstName() + " " + employee.getSurName() + " (" + employee.getId() + ")");
        }
        employeesInDBBox.setBounds(240, 540, 200, 20);
        employeesInDBBox.setVisible(false);
        contentPanel.add(employeesInDBBox);

        JComboBox<String> addedEmployeesBox = new JComboBox<>();
        addedEmployeesBox.setBounds(240, 580, 200, 20);
        addedEmployeesBox.setVisible(false);
        contentPanel.add(addedEmployeesBox);

        ArrayList<Owner> addedOwners = new ArrayList<>();

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
                    for (Owner owner : ownersInDB) {
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

        ArrayList<Employee> addedEmployees =  new ArrayList<>();

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
                    for (Employee employee : employeesInDB) {
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
                    for (Employee employee : employeesInDB) {
                        if (addedEmployeesBox.getSelectedItem().toString().endsWith("(" + employee.getId() + ")")) {
                            addedEmployees.remove(employee);
                        }
                    }

                    addedEmployeesBox.removeAllItems();
                    for (Employee employee : addedEmployees) {
                        addedEmployeesBox.addItem(employee.getFirstName() + " " + employee.getSurName() + " (" + employee.getId() + ")");
                    }
                }
            }
        });
        contentPanel.add(removeEmployeeBtn);

        JComboBox<String> customerTypeBox = new JComboBox<>();
        customerTypeBox.setBounds(240, 260, 200, 20);
        customerTypeBox.addItem("Private individual");
        customerTypeBox.addItem("Self employed");
        customerTypeBox.addItem("LTD");
        contentPanel.add(customerTypeBox);

        customerTypeBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getItem().toString().equals("Private individual")){
                    individualIDLbl.setVisible(true);
                    individualIDTxt.setVisible(true);
                    individualVATLbl.setVisible(true);
                    individualVATTxt.setVisible(true);
                    individualFirstNameLbl.setVisible(true);
                    individualFirstNameTxt.setVisible(true);
                    individualSecondNameLbl.setVisible(true);
                    individualSecondNameTxt.setVisible(true);

                    ltdMarketNumberLbl.setVisible(false);
                    ltdMarketNumberTxt.setVisible(false);
                    ltdRegistrationNumberLbl.setVisible(false);
                    ltdRegistrationNumberTxt.setVisible(false);
                    ltdCompanyNameLbl.setVisible(false);
                    ltdCompanyNameTxt.setVisible(false);
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
                    selfFirstNameLbl.setVisible(false);
                    selfFirstNameTxt.setVisible(false);
                    selfSecondNameLbl.setVisible(false);
                    selfSecondNameTxt.setVisible(false);
                }else if (e.getItem().toString().equals("Self employed")){
                    selfVATLbl.setVisible(true);
                    selfVATTxt.setVisible(true);
                    selfMarketNumberLbl.setVisible(true);
                    selfMarketNumberTxt.setVisible(true);
                    selfFirstNameLbl.setVisible(true);
                    selfFirstNameTxt.setVisible(true);
                    selfSecondNameLbl.setVisible(true);
                    selfSecondNameTxt.setVisible(true);

                    ltdMarketNumberLbl.setVisible(false);
                    ltdMarketNumberTxt.setVisible(false);
                    ltdRegistrationNumberLbl.setVisible(false);
                    ltdRegistrationNumberTxt.setVisible(false);
                    ltdCompanyNameLbl.setVisible(false);
                    ltdCompanyNameTxt.setVisible(false);
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
                    individualFirstNameLbl.setVisible(false);
                    individualFirstNameTxt.setVisible(false);
                    individualSecondNameLbl.setVisible(false);
                    individualSecondNameTxt.setVisible(false);
                }else if(e.getItem().toString().equals("LTD")){
                    ltdMarketNumberLbl.setVisible(true);
                    ltdMarketNumberTxt.setVisible(true);
                    ltdRegistrationNumberLbl.setVisible(true);
                    ltdRegistrationNumberTxt.setVisible(true);
                    ltdCompanyNameLbl.setVisible(true);
                    ltdCompanyNameTxt.setVisible(true);
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
                    individualFirstNameLbl.setVisible(false);
                    individualFirstNameTxt.setVisible(false);

                    selfVATLbl.setVisible(false);
                    selfVATTxt.setVisible(false);
                    selfMarketNumberLbl.setVisible(false);
                    selfMarketNumberTxt.setVisible(false);
                    individualSecondNameLbl.setVisible(false);
                    individualSecondNameTxt.setVisible(false);
                    selfFirstNameLbl.setVisible(false);
                    selfFirstNameTxt.setVisible(false);
                    selfSecondNameLbl.setVisible(false);
                    selfSecondNameTxt.setVisible(false);
                }
            }
        });

        // Section with Create and Cancel buttons:
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        {
            JButton createButton = new JButton("Create");
            createButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (emailTxt.getText().trim().equals("") ||
                            zipcodeTxt.getText().trim().equals("") || cityTxt.getText().trim().equals("") ||
                            streetTxt.getText().trim().equals("") || streetNumberTxt.getText().trim().equals("") ||
                            phoneNumberTxt.getText().trim().equals("")){
                        JOptionPane.showMessageDialog(null, "Please Fill In All Necessary Fields");
                    }else {
                        String email =  emailTxt.getText().trim();
                        String zipcode = zipcodeTxt.getText().trim();
                        String city = cityTxt.getText().trim();
                        String street = streetTxt.getText().trim();
                        String streetNumber = streetNumberTxt.getText().trim();
                        String phoneNumber = phoneNumberTxt.getText().trim();

                        if (customerTypeBox.getSelectedItem().toString().equals("Private individual")) {
                            if (individualIDTxt.getText().trim().equals("") || individualVATTxt.getText().trim().equals("")
                                    || individualFirstNameTxt.getText().trim().equals("") || individualSecondNameTxt.getText().trim().equals("")) {
                                JOptionPane.showMessageDialog(null, "Please Fill In All Necessary Fields");
                            } else {
                                try {
                                    customerController.savePrivateIndividualWithUserInputInDB(individualFirstNameTxt.getText().trim(),
                                            individualSecondNameTxt.getText().trim(), city, street,
                                            streetNumber, zipcode, email, phoneNumber, individualIDTxt.getText().trim(),
                                            individualVATTxt.getText().trim());
                                    dispose();
                                    JOptionPane.showMessageDialog(null, "Private individual customer created");
                                } catch (DBException ex) {
                                    JOptionPane.showMessageDialog(null, ex.getMessage());
                                }
                            }
                        } else if (customerTypeBox.getSelectedItem().toString().equals("Self employed")) {
                            if (selfVATTxt.getText().trim().equals("") || selfMarketNumberTxt.getText().trim().equals("")
                                    || selfFirstNameTxt.getText().trim().equals("") || selfSecondNameTxt.getText().trim().equals("")) {
                                JOptionPane.showMessageDialog(null, "Please Fill In All Necessary Fields");
                            } else {
                                try {
                                    customerController.saveSelfEmployedWithUserInputInDB(selfFirstNameTxt.getText().trim(),
                                            selfSecondNameTxt.getText().trim(), city, street,
                                            streetNumber, zipcode, email, phoneNumber, selfVATTxt.getText().trim(),
                                            selfMarketNumberTxt.getText().trim());
                                    dispose();
                                    JOptionPane.showMessageDialog(null, "Self employed customer created");
                                } catch (DBException ex) {
                                    JOptionPane.showMessageDialog(null, ex.getMessage());
                                }
                            }
                        } else if (customerTypeBox.getSelectedItem().toString().equals("LTD")) {
                            if (ltdRegistrationNumberTxt.getText().trim().equals("") || ltdMarketNumberTxt.getText().trim().equals("")
                                    || ltdCompanyNameTxt.getText().trim().equals("") || addedOwners.size() == 0) {
                                JOptionPane.showMessageDialog(null, "Please Fill In All Necessary Fields");
                            } else {
                                try {
                                    customerController.saveLTDUserInputInDB(ltdCompanyNameTxt.getText().trim(), city, street,
                                            streetNumber, zipcode, email, phoneNumber, addedEmployees, addedOwners, ltdRegistrationNumberTxt.getText().trim(),
                                            ltdMarketNumberTxt.getText().trim(), ltdArePayersCheckBox.isSelected());
                                    dispose();
                                    JOptionPane.showMessageDialog(null, "LTD customer created");
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
                                            for (Employee employee : employeesInDB){
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
            createButton.setActionCommand("OK");
            buttonPane.add(createButton);
            getRootPane().setDefaultButton(createButton);
        }
        {
            JButton cancelButton = new JButton("Cancel");
            cancelButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    JOptionPane.showMessageDialog(null, "Customer not created");
                }
            });
            cancelButton.setActionCommand("Cancel");
            buttonPane.add(cancelButton);
        }

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
