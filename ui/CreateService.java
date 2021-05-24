package ui;

import controller.EmployeeController;
import controller.ServiceController;
import db.DBException;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CreateService extends JDialog {
    private final JPanel contentPanel = new JPanel();
    private ServiceController serviceController =  new ServiceController();


    public CreateService() {
        super(null,"Create service",ModalityType.APPLICATION_MODAL);
        setBounds(100, 100, 800, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setLayout(null);
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        //Labels
        JLabel idLbl = new JLabel("ID:");
        idLbl.setBounds(30, 20, 200, 20);
        idLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(idLbl);

        JLabel nameLbl = new JLabel("Name:");
        nameLbl.setBounds(30, 60, 200, 20);
        nameLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(nameLbl);

        JLabel descriptionLbl = new JLabel("Description:");
        descriptionLbl.setBounds(30, 100, 200, 20);
        descriptionLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(descriptionLbl);

        JLabel priceLbl = new JLabel("Price:");
        priceLbl.setBounds(30, 140, 200, 20);
        priceLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(priceLbl);

        //Fields
        JTextField idTxt = new JTextField();
        idTxt.setBounds(240, 20, 200, 20);
        contentPanel.add(idTxt);

        JTextField nameTxt = new JTextField();
        nameTxt.setBounds(240, 60, 200, 20);
        contentPanel.add(nameTxt);

        JTextField descriptionTxt = new JTextField();
        descriptionTxt.setBounds(240, 100, 200, 20);
        contentPanel.add(descriptionTxt);

        JTextField priceTxt = new JTextField();
        priceTxt.setBounds(240, 140, 200, 20);
        contentPanel.add(priceTxt);

        // Section with Create and Cancel buttons:
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        {
            JButton createButton = new JButton("Create");
            createButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (idTxt.getText().trim().equals("") || nameTxt.getText().trim().equals("") || descriptionTxt.getText().trim().equals("") ||
                            priceTxt.getText().trim().equals("")){
                        JOptionPane.showMessageDialog(null, "Please Fill In All Fields");
                    }else {
                        int id = 0;
                        double price = 0;

                        try {
                            id = Integer.parseInt(idTxt.getText().trim());
                        } catch (NumberFormatException ex){
                            JOptionPane.showMessageDialog(null, "ID has to be an integer");
                            return;
                        }

                        try {
                            price = Double.parseDouble(priceTxt.getText().trim());
                        } catch (NumberFormatException ex){
                            JOptionPane.showMessageDialog(null, "Price has to be a double");
                            return;
                        }

                        try {
                            serviceController.saveServiceWithUserInputInDB(id, nameTxt.getText().trim(), descriptionTxt.getText().trim(), price);
                            dispose();
                            JOptionPane.showMessageDialog(null, "Service created");
                        } catch (DBException ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage());
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
                    JOptionPane.showMessageDialog(null, "Service not created");
                }
            });
            cancelButton.setActionCommand("Cancel");
            buttonPane.add(cancelButton);
        }

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
