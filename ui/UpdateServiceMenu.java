package ui;

import controller.ServiceController;
import db.DBException;
import model.Service;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UpdateServiceMenu extends JDialog {
    private final JPanel contentPanel = new JPanel();
    private ServiceController serviceController =  new ServiceController();


    public UpdateServiceMenu() {
        super(null,"Update service",ModalityType.APPLICATION_MODAL);
        setBounds(100, 100, 700, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setLayout(null);
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        //Labels
        JLabel oldIDLbl = new JLabel("Current ID:");
        oldIDLbl.setBounds(30, 20, 200, 20);
        oldIDLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(oldIDLbl);

        JLabel newIDLbl = new JLabel("New ID:");
        newIDLbl.setBounds(30, 60, 200, 20);
        newIDLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(newIDLbl);

        JLabel nameLbl = new JLabel("Name:");
        nameLbl.setBounds(30, 100, 200, 20);
        nameLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(nameLbl);

        JLabel descriptionLbl = new JLabel("Description:");
        descriptionLbl.setBounds(30, 140, 200, 20);
        descriptionLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(descriptionLbl);

        JLabel priceLbl = new JLabel("Price:");
        priceLbl.setBounds(30, 180, 200, 20);
        priceLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(priceLbl);

        //Fields
        JTextField oldIDTxt = new JTextField();
        oldIDTxt.setBounds(240, 20, 200, 20);
        contentPanel.add(oldIDTxt);

        JTextField newIDTxt = new JTextField();
        newIDTxt.setBounds(240, 60, 200, 20);
        contentPanel.add(newIDTxt);

        JTextField nameTxt = new JTextField();
        nameTxt.setBounds(240, 100, 200, 20);
        contentPanel.add(nameTxt);

        JTextField descriptionTxt = new JTextField();
        descriptionTxt.setBounds(240, 140, 200, 20);
        contentPanel.add(descriptionTxt);

        JTextField priceTxt = new JTextField();
        priceTxt.setBounds(240, 180, 200, 20);
        contentPanel.add(priceTxt);

        JButton showButton = new JButton("Show");
        showButton.setBounds(450,20,80, 30 );
        showButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (oldIDTxt.getText().trim().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please Enter Current Service ID");
                } else {
                    try {
                        int id = Integer.parseInt(oldIDTxt.getText().trim());
                        Service service = serviceController.getServiceByIDFromDB(id);
                        newIDTxt.setText(String.valueOf(service.getID()));
                        nameTxt.setText(service.getName());
                        descriptionTxt.setText(service.getDescription());
                        priceTxt.setText(String.valueOf(service.getPrice()));
                    } catch (DBException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    } catch (NumberFormatException  ex){
                        JOptionPane.showMessageDialog(null, "ID has to be an Integer");
                    }
                }
            }
        });
        contentPanel.add(showButton);

        // Section with Update and Cancel buttons:
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        {
            JButton updateButton = new JButton("Update");
            updateButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (newIDTxt.getText().trim().equals("") || oldIDTxt.getText().trim().equals("") || nameTxt.getText().trim().equals("") || descriptionTxt.getText().trim().equals("") ||
                            priceTxt.getText().trim().equals("")){
                        JOptionPane.showMessageDialog(null, "Please Fill In All Fields");
                    }else {
                        int oldID = 0, newID = 0;
                        double price = 0;

                        try {
                            newID = Integer.parseInt(newIDTxt.getText().trim());
                            oldID = Integer.parseInt(oldIDTxt.getText().trim());
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
                            Service service  =  serviceController.getServiceByIDFromDB(oldID);
                            serviceController.updateServiceWithUserInputInDB(service, newID, nameTxt.getText().trim(), descriptionTxt.getText().trim(), price);
                            dispose();
                            JOptionPane.showMessageDialog(null, "Service updated");
                        } catch (DBException ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage());
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
                }
            });
            cancelButton.setActionCommand("Cancel");
            buttonPane.add(cancelButton);
        }

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
