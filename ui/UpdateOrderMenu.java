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

public class UpdateOrderMenu extends JDialog {
    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private ServiceController serviceController =  new ServiceController();
    private OrderController orderController = new OrderController();
    private ArrayList<Service> servicesInDB =  null;


    public UpdateOrderMenu() {
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
        emailLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(emailLbl);

        JLabel dateLbl = new JLabel("Payday (yyyy-mm-dd):");
        dateLbl.setBounds(30, 100, 200, 20);
        dateLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(dateLbl);

        JLabel servicesInDBLbl = new JLabel("Services in DB:");
        servicesInDBLbl.setBounds(30, 140, 200, 20);
        servicesInDBLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(servicesInDBLbl);

        JLabel serviceQuantityLbl = new JLabel("Service quantity:");
        serviceQuantityLbl.setBounds(440, 140, 100, 20);
        serviceQuantityLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(serviceQuantityLbl);

        JLabel addedServicesWithQuantityLbl = new JLabel("Added services with quantity:");
        addedServicesWithQuantityLbl.setBounds(30, 180, 200, 20);
        addedServicesWithQuantityLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(addedServicesWithQuantityLbl);

        //Fields
        JTextField orderID = new JTextField();
        orderID.setBounds(240, 20, 200, 20);
        contentPanel.add(orderID);

        JTextField customerEmail = new JTextField();
        customerEmail.setBounds(240, 60, 200, 20);
        contentPanel.add(customerEmail);

        JTextField paydayTxt = new JTextField();
        paydayTxt.setBounds(240, 100, 200, 20);
        contentPanel.add(paydayTxt);

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
        servicesInDBBox.setBounds(240, 140, 200, 20);
        contentPanel.add(servicesInDBBox);

        JComboBox quantityBox = new JComboBox();
        for (int i=1; i<10000; i++){
            quantityBox.addItem(i);
        }
        quantityBox.setBounds(550, 140, 80, 20);
        contentPanel.add(quantityBox);

        HashMap<Service, Integer> addedServicesAndQuantity =  new HashMap<>();

        JComboBox addedServicesWithQuantityBox = new JComboBox();
        addedServicesWithQuantityBox.setBounds(240, 180, 250, 20);
        contentPanel.add(addedServicesWithQuantityBox);


        JButton removeServiceBtn = new JButton();
        removeServiceBtn.setText("Remove");
        removeServiceBtn.setBounds(500, 180, 80, 20);
        removeServiceBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (addedServicesWithQuantityBox.getItemCount() > 0) {
                    ArrayList<Service> temp =  new ArrayList<>(addedServicesAndQuantity.keySet());
                    for (Service service : temp) {
                        if (addedServicesWithQuantityBox.getSelectedItem().toString().startsWith(service.getName())) {
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
        addServiceBtn.setBounds(640, 140, 80, 20);
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


        JButton button = new JButton("Show");
        button.setBounds(440, 20, 120, 20);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (orderID.getText().trim().equals("")){
                    JOptionPane.showMessageDialog(null, "Please Fill In All Fields");
                }else {
                    try {
                        Order<Customer> order = orderController.getOrderFromDB(Integer.parseInt(orderID.getText().trim()));
                        customerEmail.setText(order.getCustomer().getEmail());
                        paydayTxt.setText(order.getPayday().toString());

                        addedServicesAndQuantity.clear();
                        addedServicesWithQuantityBox.removeAllItems();
                        for (OrderLine ol : order.getOrderLines()){
                            for(Service service : servicesInDB) {
                                if(service.getID() == ol.getService().getID()) {
                                    addedServicesWithQuantityBox.addItem(service.getName() + "  x" + ol.getQuantity());
                                    addedServicesAndQuantity.put(service, ol.getQuantity());
                                }
                            }
                        }

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "ID Has To Be Max 10 Digit Integer");
                    } catch (DBException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
                }
            }
        });
        contentPanel.add(button);

        // Section with Update and Cancel buttons:
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        {
            JButton updateButton = new JButton("Update");
            updateButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (customerEmail.getText().trim().equals("") || paydayTxt.getText().trim().equals("") || addedServicesWithQuantityBox.getItemCount() == 0){
                        JOptionPane.showMessageDialog(null, "Please Fill In All Necessary Fields");
                    }else {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        formatter = formatter.withLocale(Locale.UK);
                        try {
                            LocalDate payday = LocalDate.parse(paydayTxt.getText().trim(), formatter);
                            Order<Customer> order = orderController.getOrderFromDB(Integer.parseInt(orderID.getText().trim()));
                            orderController.updateOrderWithUserInput(order ,customerEmail.getText().trim(), addedServicesAndQuantity, payday);
                            dispose();
                            JOptionPane.showMessageDialog(null, "Order updated");
                        } catch (DateTimeParseException ex) {
                            JOptionPane.showMessageDialog(null, "Please Enter Valid Date");
                        } catch (NumberFormatException ex){
                            JOptionPane.showMessageDialog(null, "ID Has To Be Max 10 Digit Integer");
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
            updateButton.setActionCommand("OK");
            buttonPane.add(updateButton);
            getRootPane().setDefaultButton(updateButton);
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
