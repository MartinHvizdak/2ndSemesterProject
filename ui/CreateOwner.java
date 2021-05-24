package ui;

import controller.CustomerController;
import controller.OwnerController;
import db.DBException;
import model.CustomerEmployee;
import model.LTD;
import model.Owner;
import model.Service;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class CreateOwner extends JDialog {
    private final JPanel contentPanel = new JPanel();
    private OwnerController ownerController;
    private CustomerController customerController;
    ArrayList<String> addedLtdEmails;
    ArrayList<String> ltdEmailsInDB;


    public CreateOwner() {
        super(null,"Create employee",ModalityType.APPLICATION_MODAL);
        setBounds(100, 100, 700, 400);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setLayout(null);
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        ownerController =  new OwnerController();
        customerController =  new CustomerController();

        addedLtdEmails =  new ArrayList<>();
        ltdEmailsInDB = new ArrayList<>();

        //Labels
        JLabel personalIDLbl = new JLabel("Personal ID:");
        personalIDLbl.setBounds(30, 20, 200, 20);
        personalIDLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(personalIDLbl);

        JLabel firstNameLbl = new JLabel("First name:");
        firstNameLbl.setBounds(30, 60, 200, 20);
        firstNameLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(firstNameLbl);

        JLabel surNameLbl = new JLabel("Surname:");
        surNameLbl.setBounds(30, 100, 200, 20);
        surNameLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(surNameLbl);

        JLabel relationLbl = new JLabel("Relation:");
        relationLbl.setBounds(30, 140, 200, 20);
        relationLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(relationLbl);

        JLabel ltdEmailsLbl = new JLabel("LTD emails in DB:");
        ltdEmailsLbl.setBounds(30, 180, 200, 20);
        ltdEmailsLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(ltdEmailsLbl);

        JLabel ltdEmailLbl = new JLabel("Added LTDs:");
        ltdEmailLbl.setBounds(30, 220, 200, 20);
        ltdEmailLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(ltdEmailLbl);

        //Fields
        JTextField personalIDTxt = new JTextField("");
        personalIDTxt.setBounds(240, 20, 200, 20);
        contentPanel.add(personalIDTxt);

        JTextField firstNameTxt = new JTextField("");
        firstNameTxt.setBounds(240, 60, 200, 20);
        contentPanel.add(firstNameTxt);

        JTextField surNameTxt = new JTextField("");
        surNameTxt.setBounds(240, 100, 200, 20);
        contentPanel.add(surNameTxt);

        JTextField relationTxt = new JTextField("");
        relationTxt.setBounds(240, 140, 200, 20);
        contentPanel.add(relationTxt);

        try {
            ltdEmailsInDB = customerController.getAllLTDEmailsFromDB();
        } catch (DBException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            dispose();
            return;
        }

        JComboBox ltdEmailsInDBBox = new JComboBox();
        for (String email : ltdEmailsInDB){
            ltdEmailsInDBBox.addItem(email);
        }
        ltdEmailsInDBBox.setBounds(240, 180, 200, 20);
        contentPanel.add(ltdEmailsInDBBox);

        JComboBox<String> addedLtdEmailsBox = new JComboBox();
        addedLtdEmailsBox.setBounds(240, 220, 200, 20);
        contentPanel.add(addedLtdEmailsBox);

        JButton addLTDBtn = new JButton();
        addLTDBtn.setText("Add");
        addLTDBtn.setBounds(450, 180, 80, 20);
        addLTDBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!addedLtdEmails.contains(ltdEmailsInDB.get(ltdEmailsInDBBox.getSelectedIndex()))) {
                    addedLtdEmails.add(ltdEmailsInDB.get(ltdEmailsInDBBox.getSelectedIndex()));

                    addedLtdEmailsBox.removeAllItems();
                    for (String email : addedLtdEmails) {
                        addedLtdEmailsBox.addItem(email);
                    }
                }
            }
        });
        contentPanel.add(addLTDBtn);

        JButton removeLTDBtn = new JButton();
        removeLTDBtn.setText("Remove");
        removeLTDBtn.setBounds(450, 220, 80, 20);
        removeLTDBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (addedLtdEmailsBox.getItemCount() > 0) {
                    for (String email : ltdEmailsInDB) {
                        if (addedLtdEmailsBox.getSelectedItem().toString().equals(email)) {
                            addedLtdEmails.remove(email);
                        }
                    }

                    addedLtdEmailsBox.removeAllItems();
                    for (String email : addedLtdEmails) {
                        addedLtdEmailsBox.addItem(email);
                    }
                }
            }
        });
        contentPanel.add(removeLTDBtn);

        // Section with Create and Cancel buttons:
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        {
            JButton createButton = new JButton("Create");
            createButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (personalIDTxt.getText().trim().equals("") || firstNameTxt.getText().trim().equals("")
                            || surNameTxt.getText().trim().equals("") || relationLbl.getText().trim().equals("")){
                        JOptionPane.showMessageDialog(null, "Please Fill In All Necessary Data");
                    }else {
                        try {
                            ownerController.saveOwnerWithUserInputInDB(personalIDTxt.getText().trim(),
                                    firstNameTxt.getText().trim(), surNameTxt.getText().trim(), relationTxt.getText().trim(), addedLtdEmails);
                            dispose();
                            JOptionPane.showMessageDialog(null, "Owner created");
                        } catch (DBException ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage());

                            if(ex.getMessage().startsWith("LTD with email")){
                                try {
                                    ltdEmailsInDB =  customerController.getAllLTDEmailsFromDB();

                                    addedLtdEmails.removeAll(addedLtdEmails);

                                    ltdEmailsInDBBox.removeAllItems();
                                    for (String email : ltdEmailsInDB){
                                        ltdEmailsInDBBox.addItem(email);
                                    }
                                    addedLtdEmailsBox.removeAllItems();
                                } catch (DBException exception) {
                                    JOptionPane.showMessageDialog(null, exception.getMessage());
                                    dispose();
                                    return;
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
                    JOptionPane.showMessageDialog(null, "Owner not created");
                }
            });
            cancelButton.setActionCommand("Cancel");
            buttonPane.add(cancelButton);
        }

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
