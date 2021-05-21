package ui;

import controller.OrderController;
import db.DBException;
import model.Customer;
import model.Order;
import model.OrderLine;
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

public class UpdateOrder extends JDialog {
    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private ServiceController serviceController =  new ServiceController();
    private OrderController orderController = new OrderController();
    private Order<Customer> order = null;


    public UpdateOrder() {
        super(null,"Update order",ModalityType.APPLICATION_MODAL);
        setBounds(100, 100, 800, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setLayout(null);
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        //Labels
        JLabel orderIDLbl = new JLabel("Order ID:");
        orderIDLbl.setBounds(30, 20, 200, 20);
        orderIDLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(orderIDLbl);

        JLabel emailLbl = new JLabel("Customer email:");
        emailLbl.setBounds(30, 60, 200, 20);
        emailLbl.setVisible(false);
        emailLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(emailLbl);

        JLabel dateLbl = new JLabel("Payday (dd-mm-yyy):");
        dateLbl.setBounds(30, 100, 200, 20);
        dateLbl.setVisible(false);
        dateLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(dateLbl);

        JLabel servicesInDBLbl = new JLabel("Services in DB:");
        servicesInDBLbl.setBounds(30, 140, 200, 20);
        servicesInDBLbl.setVisible(false);
        servicesInDBLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(servicesInDBLbl);

        JLabel serviceQuantityLbl = new JLabel("Service quantity:");
        serviceQuantityLbl.setBounds(440, 140, 100, 20);
        serviceQuantityLbl.setVisible(false);
        serviceQuantityLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(serviceQuantityLbl);

        JLabel addedServicesWithQuantityLbl = new JLabel("Added services with quantity:");
        addedServicesWithQuantityLbl.setBounds(30, 180, 200, 20);
        addedServicesWithQuantityLbl.setVisible(false);
        addedServicesWithQuantityLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(addedServicesWithQuantityLbl);

        //Fields
        JTextField orderID = new JTextField();
        orderID.setBounds(240, 20, 200, 20);
        contentPanel.add(orderID);

        JTextField customerEmail = new JTextField();
        customerEmail.setVisible(false);
        customerEmail.setBounds(240, 60, 200, 20);
        contentPanel.add(customerEmail);

        JTextField paydayTxt = new JTextField();
        paydayTxt.setVisible(false);
        paydayTxt.setBounds(240, 100, 200, 20);
        contentPanel.add(paydayTxt);

        ArrayList<Service> services =  null;
        try {
            services = serviceController.getAllServicesFromDB();
        } catch (DBException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            dispose();
        }

        JComboBox servicesInDBBox = new JComboBox();
        servicesInDBBox.setVisible(false);
        for (Service service : services){
            servicesInDBBox.addItem(service.getName());
        }
        servicesInDBBox.setBounds(240, 140, 200, 20);
        contentPanel.add(servicesInDBBox);

        JComboBox quantityBox = new JComboBox();
        quantityBox.setVisible(false);
        for (int i=1; i<10000; i++){
            quantityBox.addItem(i);
        }
        quantityBox.setBounds(550, 140, 80, 20);
        contentPanel.add(quantityBox);

        HashMap<Service, Integer> servicesAndQuantity =  new HashMap<>();

        JComboBox addedServicesWithQuantityBox = new JComboBox();
        addedServicesWithQuantityBox.setVisible(false);
        addedServicesWithQuantityBox.setBounds(240, 180, 250, 20);
        contentPanel.add(addedServicesWithQuantityBox);

        ArrayList<Service> finalServices = services;

        JButton removeServiceBtn = new JButton();
        removeServiceBtn.setText("Remove");
        removeServiceBtn.setVisible(false);
        removeServiceBtn.setBounds(500, 180, 80, 20);
        removeServiceBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (addedServicesWithQuantityBox.getItemCount() > 0) {
                    for (Service service : finalServices) {
                        if (addedServicesWithQuantityBox.getSelectedItem().toString().startsWith(service.getName())) {
                            System.out.println(addedServicesWithQuantityBox.getSelectedItem().toString() + "  " + service.getName());
                            servicesAndQuantity.remove(service);
                        }
                    }

                    addedServicesWithQuantityBox.removeAllItems();
                    for (Service service : servicesAndQuantity.keySet()) {
                        addedServicesWithQuantityBox.addItem(service.getName() + " x" + servicesAndQuantity.get(service));
                    }
                }
            }
        });
        contentPanel.add(removeServiceBtn);

        JButton addServiceBtn = new JButton();
        addServiceBtn.setText("Add");
        addServiceBtn.setVisible(false);
        addServiceBtn.setBounds(640, 140, 80, 20);
        addServiceBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                servicesAndQuantity.put(finalServices.get(servicesInDBBox.getSelectedIndex()), quantityBox.getSelectedIndex() + 1);

                addedServicesWithQuantityBox.removeAllItems();
                for (Service service : servicesAndQuantity.keySet()){
                    addedServicesWithQuantityBox.addItem(service.getName() + " x" + servicesAndQuantity.get(service));
                }
            }
        });
        contentPanel.add(addServiceBtn);


        JButton button = new JButton("Show order");
        button.setBounds(440, 20, 120, 20);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (orderID.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Please Fill In All Fields");
                }else {
                    try {
                        order = orderController.getOrderFromDB(Integer.parseInt(orderID.getText()));
                        customerEmail.setText(order.getCustomer().getEmail());
                        paydayTxt.setText(order.getPayday().toString());

                        for (OrderLine ol : order.getOrderLines()){
                            addedServicesWithQuantityBox.addItem(ol.getService().getName() + "  x" + ol.getQuantity());
                        }

                        emailLbl.setVisible(true);
                        dateLbl.setVisible(true);
                        servicesInDBLbl.setVisible(true);
                        serviceQuantityLbl.setVisible(true);
                        addedServicesWithQuantityLbl.setVisible(true);
                        customerEmail.setVisible(true);
                        paydayTxt.setVisible(true);
                        addServiceBtn.setVisible(true);
                        removeServiceBtn.setVisible(true);
                        addedServicesWithQuantityBox.setVisible(true);
                        quantityBox.setVisible(true);
                        servicesInDBBox.setVisible(true);

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "ID has to be a number");
                    } catch (DBException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
                }
            }
        });
        contentPanel.add(button);

        // Section with OK and Cancel buttons:
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        {
            JButton okButton = new JButton("Update");
            okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (customerEmail.getText().equals("") || paydayTxt.getText().equals("") || addedServicesWithQuantityBox.getItemCount() == 0){
                        JOptionPane.showMessageDialog(null, "Please Fill In All Fields");
                    }else {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                        formatter = formatter.withLocale(Locale.UK);
                        try {
                            LocalDate payday = LocalDate.parse(paydayTxt.getText(), formatter);
                            orderController.updateOrderWithUserInput(order ,customerEmail.getText(), servicesAndQuantity, payday);
                            dispose();
                            JOptionPane.showMessageDialog(null, "Order updated");
                        } catch (DateTimeParseException ex) {
                            JOptionPane.showMessageDialog(null, "Please Enter Valid Date");
                        } catch (DBException ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage());
                        }
                    }
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
                    JOptionPane.showMessageDialog(null, "Order not updated");
                }
            });
            cancelButton.setActionCommand("Cancel");
            buttonPane.add(cancelButton);
        }

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
