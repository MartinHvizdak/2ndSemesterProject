package ui;

import controller.OwnerController;
import db.DBException;
import model.Owner;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class ShowOwner extends JDialog {
    private final JPanel contentPanel = new JPanel();
    private OwnerController ownerController;


    public ShowOwner() {
        super(null,"Show owner",ModalityType.APPLICATION_MODAL);
        setBounds(100, 100, 600, 400);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setLayout(null);
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        ownerController =  new OwnerController();

        //Labels
        JLabel personalIDLbl = new JLabel("Personal ID:");
        personalIDLbl.setBounds(30, 20, 200, 20);
        personalIDLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(personalIDLbl);

        JLabel firstNameLbl = new JLabel("First name:");
        firstNameLbl.setBounds(30, 60, 200, 20);
        firstNameLbl.setVisible(false);
        firstNameLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(firstNameLbl);

        JLabel surNameLbl = new JLabel("Surname:");
        surNameLbl.setBounds(30, 100, 200, 20);
        surNameLbl.setVisible(false);
        surNameLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(surNameLbl);

        JLabel relationLbl = new JLabel("Relation:");
        relationLbl.setBounds(30, 140, 200, 20);
        relationLbl.setVisible(false);
        relationLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(relationLbl);

        JLabel ltdEmailsLbl = new JLabel("LTDs:");
        ltdEmailsLbl.setBounds(30, 180, 200, 20);
        ltdEmailsLbl.setVisible(false);
        ltdEmailsLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(ltdEmailsLbl);

        //Fields
        JTextField personalIDTxt = new JTextField("");
        personalIDTxt.setBounds(240, 20, 200, 20);
        contentPanel.add(personalIDTxt);

        JLabel firstNameDataLbl = new JLabel("");
        firstNameDataLbl.setBounds(240, 60, 200, 20);
        firstNameDataLbl.setVisible(false);
        contentPanel.add(firstNameDataLbl);

        JLabel surNameDataLbl = new JLabel("");
        surNameDataLbl.setBounds(240, 100, 200, 20);
        surNameDataLbl.setVisible(false);
        contentPanel.add(surNameDataLbl);

        JLabel relationDataLbl = new JLabel("");
        relationDataLbl.setBounds(240, 140, 200, 20);
        relationDataLbl.setVisible(false);
        contentPanel.add(relationDataLbl);

        JScrollPane scrollableLtdEmails = new JScrollPane();
        scrollableLtdEmails.setBounds(240, 180, 200, 200);
        scrollableLtdEmails.setVisible(false);
        contentPanel.add(scrollableLtdEmails);

        // Section with Show and Cancel buttons:
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        {
            JButton showButton = new JButton("show");
            showButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (personalIDTxt.getText().trim().equals("")){
                        JOptionPane.showMessageDialog(null, "Please Enter Personal ID");
                    }else {
                        try {
                            Owner owner = ownerController.getOwnerByIDFromDB(personalIDTxt.getText().trim());
                            ArrayList<String> ltdEmails = ownerController.getOwnerLTDEmailsByIDFromDB(personalIDTxt.getText().trim());
                            firstNameDataLbl.setText(owner.getFirstName());
                            surNameDataLbl.setText(owner.getSurName());
                            relationDataLbl.setText(owner.getRelation());

                            DefaultListModel<String> listModel = new DefaultListModel<>();
                            for (String email : ltdEmails){
                                listModel.addElement(email);
                            }
                            JList ltdEmailslList =  new JList(listModel);
                            scrollableLtdEmails.setViewportView(ltdEmailslList);

                            firstNameDataLbl.setVisible(true);
                            surNameDataLbl.setVisible(true);
                            relationDataLbl.setVisible(true);
                            scrollableLtdEmails.setVisible(true);
                            firstNameLbl.setVisible(true);
                            surNameLbl.setVisible(true);
                            relationLbl.setVisible(true);
                            ltdEmailsLbl.setVisible(true);
                        } catch (DBException ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage());
                        }
                    }
                }
            });
            showButton.setActionCommand("OK");
            buttonPane.add(showButton);
            getRootPane().setDefaultButton(showButton);
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

