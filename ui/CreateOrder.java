package ui;

import controller.OrderController;
import db.DBException;
import model.CustomerEmployee;
import model.Owner;
import model.Service;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import controller.ServiceController;

public class CreateOrder extends JDialog {
    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private ServiceController serviceController =  new ServiceController();
    private OrderController orderController = new OrderController();
    private ArrayList<Service> servicesInDB = null;


    public CreateOrder() {
        super(null,"Create order",ModalityType.APPLICATION_MODAL);
        setBounds(100, 100, 800, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setLayout(null);
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);

            //Labels
            JLabel emailLbl = new JLabel("Customer email:");
            emailLbl.setBounds(30, 20, 200, 20);
            emailLbl.setHorizontalAlignment(SwingConstants.RIGHT);
            contentPanel.add(emailLbl);

            JLabel dateLbl = new JLabel("Payday (dd-mm-yyy):");
            dateLbl.setBounds(30, 60, 200, 20);
            dateLbl.setHorizontalAlignment(SwingConstants.RIGHT);
            contentPanel.add(dateLbl);

            JLabel servicesInDBLbl = new JLabel("Services in DB:");
            servicesInDBLbl.setBounds(30, 100, 200, 20);
            servicesInDBLbl.setHorizontalAlignment(SwingConstants.RIGHT);
            contentPanel.add(servicesInDBLbl);

            JLabel serviceQuantityInDBLbl = new JLabel("Service quantity:");
            serviceQuantityInDBLbl.setBounds(440, 100, 100, 20);
            serviceQuantityInDBLbl.setHorizontalAlignment(SwingConstants.RIGHT);
            contentPanel.add(serviceQuantityInDBLbl);

            JLabel addedServicesWithQuantityLbl = new JLabel("Added services with quantity:");
            addedServicesWithQuantityLbl.setBounds(30, 140, 200, 20);
            addedServicesWithQuantityLbl.setHorizontalAlignment(SwingConstants.RIGHT);
            contentPanel.add(addedServicesWithQuantityLbl);

            //Fields
            JTextField customerEmailTxt = new JTextField();
            customerEmailTxt.setBounds(240, 20, 200, 20);
            contentPanel.add(customerEmailTxt);

            JTextField dateTxt = new JTextField();
            dateTxt.setBounds(240, 60, 200, 20);
            contentPanel.add(dateTxt);

            try {
                servicesInDB = serviceController.getAllServicesFromDB();
            } catch (DBException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
                dispose();
            }

            JComboBox servicesInDBBox = new JComboBox();
            for (Service service : servicesInDB){
               servicesInDBBox.addItem(service.getName());
            }
            servicesInDBBox.setBounds(240, 100, 200, 20);
            contentPanel.add(servicesInDBBox);

            JComboBox quantityBox = new JComboBox();
            for (int i=1; i<10000; i++){
                quantityBox.addItem(i);
            }
            quantityBox.setBounds(550, 100, 80, 20);
            contentPanel.add(quantityBox);

            HashMap<Service, Integer> addedServicesAndQuantity =  new HashMap<>();

            JComboBox addedServicesWithQuantityBox = new JComboBox();
            addedServicesWithQuantityBox.setBounds(240, 140, 250, 20);
            contentPanel.add(addedServicesWithQuantityBox);

            JButton removeServiceBtn = new JButton();
            removeServiceBtn.setText("Remove");
            removeServiceBtn.setBounds(500, 140, 80, 20);
            removeServiceBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (addedServicesWithQuantityBox.getItemCount() > 0) {
                        for (Service service : servicesInDB) {
                            if (addedServicesWithQuantityBox.getSelectedItem().toString().startsWith(service.getName())) {
                                System.out.println(addedServicesWithQuantityBox.getSelectedItem().toString() + "  " + service.getName());
                                addedServicesAndQuantity.remove(service);
                            }
                        }

                        addedServicesWithQuantityBox.removeAllItems();
                        for (Service service : addedServicesAndQuantity.keySet()) {
                            addedServicesWithQuantityBox.addItem(service.getName() + " x" + addedServicesAndQuantity.get(service));
                        }
                    }
                }
            });
            contentPanel.add(removeServiceBtn);

            JButton addServiceBtn = new JButton();
            addServiceBtn.setText("Add");
            addServiceBtn.setBounds(640, 100, 80, 20);
            addServiceBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addedServicesAndQuantity.put(servicesInDB.get(servicesInDBBox.getSelectedIndex()), quantityBox.getSelectedIndex() + 1);

                    addedServicesWithQuantityBox.removeAllItems();
                    for (Service service : addedServicesAndQuantity.keySet()){
                        addedServicesWithQuantityBox.addItem(service.getName() + " x" + addedServicesAndQuantity.get(service));
                    }
                }
            });
            contentPanel.add(addServiceBtn);




         // Section with Create and Cancel buttons:
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                JButton createButton = new JButton("Create");
                createButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (customerEmailTxt.getText().trim().equals("") || dateTxt.getText().trim().equals("") || addedServicesWithQuantityBox.getItemCount() == 0){
                            JOptionPane.showMessageDialog(null, "Please Fill In All Necessary Fields");
                        }else {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                            formatter = formatter.withLocale(Locale.UK);
                            try {
                                LocalDate payday = LocalDate.parse(dateTxt.getText().trim(), formatter);
                                int id = orderController.saveOrderWithUserInputInDB(customerEmailTxt.getText().trim(), addedServicesAndQuantity, payday);
                                dispose();
                                JOptionPane.showMessageDialog(null, "Order got id number: " + id);
                            } catch (DateTimeParseException ex) {
                                JOptionPane.showMessageDialog(null, "Please Enter Valid Date");
                            } catch (DBException ex) {
                                JOptionPane.showMessageDialog(null, ex.getMessage());

                                if(ex.getMessage().startsWith("Service with id")){
                                    try {
                                        servicesInDB = serviceController.getAllServicesFromDB();

                                        addedServicesAndQuantity.clear();
                                        servicesInDBBox.removeAllItems();
                                        for (Service service : servicesInDB){
                                            servicesInDBBox.addItem(service.getName());
                                        }
                                        addedServicesWithQuantityBox.removeAllItems();

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
                        JOptionPane.showMessageDialog(null, "Order not created");
                    }
                });
                cancelButton.setActionCommand("Cancel");
                buttonPane.add(cancelButton);
            }

         setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
