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

public class ShowServiceMenu extends JDialog {
    private final JPanel contentPanel = new JPanel();
    private ServiceController serviceController =  new ServiceController();


    public ShowServiceMenu() {
        super(null,"Show service",ModalityType.APPLICATION_MODAL);
        setBounds(100, 100, 700, 300);
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
        nameLbl.setVisible(false);
        nameLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(nameLbl);

        JLabel descriptionLbl = new JLabel("Description:");
        descriptionLbl.setBounds(30, 100, 200, 20);
        descriptionLbl.setVisible(false);
        descriptionLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(descriptionLbl);

        JLabel priceLbl = new JLabel("Price:");
        priceLbl.setBounds(30, 140, 200, 20);
        priceLbl.setVisible(false);
        priceLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(priceLbl);

        //Fields
        JTextField idTxt = new JTextField();
        idTxt.setBounds(240, 20, 200, 20);
        contentPanel.add(idTxt);

        JLabel nameDataLbl = new JLabel();
        nameDataLbl.setBounds(240, 60, 200, 20);
        nameDataLbl.setVisible(false);
        contentPanel.add(nameDataLbl);

        JLabel descriptionDataLbl = new JLabel();
        descriptionDataLbl.setBounds(240, 100, 200, 20);
        descriptionDataLbl.setVisible(false);
        contentPanel.add(descriptionDataLbl);

        JLabel priceDataLbl = new JLabel();
        priceDataLbl.setBounds(240, 140, 200, 20);
        priceDataLbl.setVisible(false);
        contentPanel.add(priceDataLbl);

        JButton showButton = new JButton("Show");
        showButton.setBounds(450,20,80, 20 );
        showButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (idTxt.getText().trim().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please Enter Service ID");
                } else {
                    try {
                        int id = Integer.parseInt(idTxt.getText().trim());
                        Service service = serviceController.getServiceByIDFromDB(id);
                        nameDataLbl.setText(String.valueOf(service.getID()));
                        descriptionDataLbl.setText(service.getDescription());
                        priceDataLbl.setText(String.valueOf(service.getPrice()));

                        nameLbl.setVisible(true);
                        descriptionLbl.setVisible(true);
                        priceLbl.setVisible(true);
                        nameDataLbl.setVisible(true);
                        descriptionDataLbl.setVisible(true);
                        priceDataLbl.setVisible(true);
                    } catch (DBException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    } catch (NumberFormatException  ex){
                        JOptionPane.showMessageDialog(null, "ID has to be an Integer");
                    }
                }
            }
        });
        contentPanel.add(showButton);

        // Section with OK and Cancel buttons:
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        {
            JButton okButton = new JButton("Ok");
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
