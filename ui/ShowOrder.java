package ui;

import controller.OrderController;
import db.DBException;
import model.Customer;
import model.Order;
import model.OrderLine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShowOrder
{
    private boolean isVisible;
    private JPanel showOrderPanel;
    JLabel orderIDLbl;
    JLabel emailLbl;
    JLabel emailDataLbl;
    JLabel paydayLbl;
    JLabel paydayDataLbl;
    JLabel servicesWithQuantityInOrderLbl;
    JTextField orderID;
    JScrollPane scrollPaneServicesAndQuantity;
    JList servicesWithQuantityInOrderBox;

    OrderController orderController;

    ShowOrder(JFrame mainWindow){
        orderController =  new OrderController();
        isVisible = false;
        showOrderPanel =  new JPanel();

        showOrderPanel.setLayout(null);
        showOrderPanel.setVisible(false);
        showOrderPanel.setBounds(mainWindow.getBounds());
        mainWindow.add(showOrderPanel);

        //Labels
        orderIDLbl = new JLabel("Order ID:");
        orderIDLbl.setBounds(10, 20, 200, 20);
        orderIDLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        showOrderPanel.add(orderIDLbl);

        emailLbl = new JLabel("Customer email: ");
        emailLbl.setBounds(10, 60, 200, 20);
        emailLbl.setVisible(false);
        emailLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        showOrderPanel.add(emailLbl);

        emailDataLbl = new JLabel("");
        emailDataLbl.setBounds(220, 60, 200, 20);
        emailDataLbl.setVisible(false);
        showOrderPanel.add(emailDataLbl);

        paydayLbl = new JLabel("Payday (dd-mm-yyy): ");
        paydayLbl.setBounds(10, 100, 200, 20);
        paydayLbl.setVisible(false);
        paydayLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        showOrderPanel.add(paydayLbl);

        paydayDataLbl = new JLabel("");
        paydayDataLbl.setBounds(220, 100, 130, 20);
        paydayDataLbl.setVisible(false);
        showOrderPanel.add(paydayDataLbl);

        servicesWithQuantityInOrderLbl = new JLabel("Services with quantity: ");
        servicesWithQuantityInOrderLbl.setBounds(10, 140, 200, 20);
        servicesWithQuantityInOrderLbl.setVisible(false);
        servicesWithQuantityInOrderLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        showOrderPanel.add(servicesWithQuantityInOrderLbl);

        //Fields
        orderID = new JTextField();
        orderID.setBounds(220, 20, 200, 20);
        showOrderPanel.add(orderID);

        servicesWithQuantityInOrderBox = new JList();
        scrollPaneServicesAndQuantity = new JScrollPane();
        scrollPaneServicesAndQuantity.setBounds(220, 140, 250, 200);
        scrollPaneServicesAndQuantity.setVisible(false);
        showOrderPanel.add(scrollPaneServicesAndQuantity);

        JButton button = new JButton("Show order");
        button.setBounds(440, 20, 120, 20);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (orderID.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Please Fill In All Fields");
                }else {
                    try {
                        Order<Customer> order= orderController.getOrderFromDB(Integer.parseInt(orderID.getText()));
                        emailDataLbl.setText(order.getCustomer().getEmail());
                        paydayDataLbl.setText(order.getPayday().toString());

                        DefaultListModel listModel =  new DefaultListModel();
                        for (OrderLine ol : order.getOrderLines()){
                            listModel.addElement(ol.getService().getName() + "  x" + ol.getQuantity());
                        }
                        servicesWithQuantityInOrderBox.setModel(listModel);
                        scrollPaneServicesAndQuantity.setViewportView(servicesWithQuantityInOrderBox);

                        emailLbl.setVisible(true);
                        emailDataLbl.setVisible(true);
                        paydayLbl.setVisible(true);
                        paydayDataLbl.setVisible(true);
                        servicesWithQuantityInOrderLbl.setVisible(true);
                        scrollPaneServicesAndQuantity.setVisible(true);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "ID has to be a number");
                    } catch (DBException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
                }
            }
        });
        showOrderPanel.add(button);
    }

    private void updatePanelVisibility(){
        showOrderPanel.setVisible(isVisible);
        if (isVisible == false)
            updateComponentsVisibility(false);
    }

    private void updateComponentsVisibility(boolean flag){

    }

    public boolean getIsVisible(){
        return isVisible;
    }

    public void setIsVisible(boolean isVisible){
        this.isVisible=isVisible;

        if (isVisible)
            showOrderPanel.setVisible(true);
        else{
            showOrderPanel.setVisible(false);
            emailLbl.setVisible(false);
            emailDataLbl.setVisible(false);
            paydayLbl.setVisible(false);
            paydayDataLbl.setVisible(false);
            servicesWithQuantityInOrderLbl.setVisible(false);
            scrollPaneServicesAndQuantity.setVisible(false);
        }
    }
}
