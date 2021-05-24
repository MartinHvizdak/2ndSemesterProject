package ui;

import controller.OrderController;
import db.DBException;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DeleteOrder extends JDialog {
    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private OrderController orderController = new OrderController();


    public DeleteOrder() {
        super(null,"Delete order",ModalityType.APPLICATION_MODAL);
        setBounds(100, 100, 500, 200);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setLayout(null);
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        //Labels
        JLabel orderIDLbl = new JLabel("Order ID:");
        orderIDLbl.setBounds(30, 20, 100, 20);
        orderIDLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(orderIDLbl);

        //Fields
        JTextField orderID = new JTextField();
        orderID.setBounds(140, 20, 200, 20);
        contentPanel.add(orderID);

        // Section with OK and Cancel buttons:
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        {
            JButton deleteButton = new JButton("Delete");
            deleteButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (orderID.getText().trim().equals("")){
                        JOptionPane.showMessageDialog(null, "Please Enter Order ID");
                    }else {
                        try {
                            orderController.deleteOrderFromDBByID(Integer.parseInt(orderID.getText().trim()));
                            dispose();
                            JOptionPane.showMessageDialog(null, "Order deleted");
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "ID Has To Be Max 10 Digit Integer");
                        } catch (DBException ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage());
                        }
                    }
                }
            });
            deleteButton.setActionCommand("OK");
            buttonPane.add(deleteButton);
            getRootPane().setDefaultButton(deleteButton);
        }
        {
            JButton cancelButton = new JButton("Cancel");
            cancelButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    JOptionPane.showMessageDialog(null, "Order not deleted");
                }
            });
            cancelButton.setActionCommand("Cancel");
            buttonPane.add(cancelButton);
        }

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
