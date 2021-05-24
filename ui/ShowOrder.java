package ui;

import controller.OrderController;
import db.DBException;
import model.Customer;
import model.Order;
import model.OrderLine;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShowOrder extends JDialog
{
    private JPanel contentPanel;

    OrderController orderController;

    ShowOrder(){
        super(null,"Show order", Dialog.ModalityType.APPLICATION_MODAL);
        contentPanel =  new JPanel();
        setBounds(100, 100, 600, 500);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setLayout(null);
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        orderController =  new OrderController();

        //Labels
        JLabel orderIDLbl = new JLabel("Order ID:");
        orderIDLbl.setBounds(10, 20, 200, 20);
        orderIDLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(orderIDLbl);

        JLabel emailLbl = new JLabel("Customer email: ");
        emailLbl.setBounds(10, 60, 200, 20);
        emailLbl.setVisible(false);
        emailLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(emailLbl);

        JLabel paydayLbl = new JLabel("Payday (dd-mm-yyy): ");
        paydayLbl.setBounds(10, 100, 200, 20);
        paydayLbl.setVisible(false);
        paydayLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(paydayLbl);

        JLabel servicesWithQuantityInOrderLbl = new JLabel("Services with quantity: ");
        servicesWithQuantityInOrderLbl.setBounds(10, 140, 200, 20);
        servicesWithQuantityInOrderLbl.setVisible(false);
        servicesWithQuantityInOrderLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(servicesWithQuantityInOrderLbl);

        //Fields
        JTextField orderID = new JTextField();
        orderID.setBounds(220, 20, 200, 20);
        contentPanel.add(orderID);

        JLabel emailDataLbl = new JLabel("");
        emailDataLbl.setBounds(220, 60, 200, 20);
        emailDataLbl.setVisible(false);
        contentPanel.add(emailDataLbl);

        JLabel paydayDataLbl = new JLabel("");
        paydayDataLbl.setBounds(220, 100, 130, 20);
        paydayDataLbl.setVisible(false);
        contentPanel.add(paydayDataLbl);

        JScrollPane scrollPaneServicesAndQuantity = new JScrollPane();
        scrollPaneServicesAndQuantity.setBounds(220, 140, 250, 200);
        scrollPaneServicesAndQuantity.setVisible(false);
        contentPanel.add(scrollPaneServicesAndQuantity);

        JButton button = new JButton("Show order");
        button.setBounds(440, 20, 120, 20);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (orderID.getText().trim().equals("")){
                    JOptionPane.showMessageDialog(null, "Please Enter Order ID");
                }else {
                    try {
                        Order<Customer> order= orderController.getOrderFromDB(Integer.parseInt(orderID.getText().trim()));
                        emailDataLbl.setText(order.getCustomer().getEmail());
                        paydayDataLbl.setText(order.getPayday().toString());

                        DefaultListModel listModel =  new DefaultListModel();
                        for (OrderLine ol : order.getOrderLines()){
                            listModel.addElement(ol.getService().getName() + "  x" + ol.getQuantity());
                        }
                        JList<String> servicesWithQuantityInOrderBox = new JList(listModel);
                        scrollPaneServicesAndQuantity.setViewportView(servicesWithQuantityInOrderBox);

                        emailLbl.setVisible(true);
                        emailDataLbl.setVisible(true);
                        paydayLbl.setVisible(true);
                        paydayDataLbl.setVisible(true);
                        servicesWithQuantityInOrderLbl.setVisible(true);
                        scrollPaneServicesAndQuantity.setVisible(true);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "ID Has To Be Max 10 Digit Integer");
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
            JButton okButton = new JButton("OK");
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
