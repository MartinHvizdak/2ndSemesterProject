package ui;

import controller.CustomerController;
import db.DBException;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DeleteCustomer extends JDialog {
    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private CustomerController customerController =  new CustomerController();


    public DeleteCustomer() {
        super(null,"Delete customer",ModalityType.APPLICATION_MODAL);
        setBounds(100, 100, 500, 200);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setLayout(null);
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        //Labels
        JLabel emailLbl = new JLabel("Email:");
        emailLbl.setBounds(30, 20, 200, 20);
        emailLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(emailLbl);

        //Fields
        JTextField emailTxt = new JTextField();
        emailTxt.setBounds(240, 20, 200, 20);
        contentPanel.add(emailTxt);

        // Section with Delete and Cancel buttons:
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        {
            JButton deleteButton = new JButton("Delete");
            deleteButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (emailTxt.getText().trim().equals("")){
                        JOptionPane.showMessageDialog(null, "Please Enter Email");
                    }else {

                        try {
                            customerController.deleteEveryCustomerTypeByEmailFromDB(emailTxt.getText().trim());
                            dispose();
                            JOptionPane.showMessageDialog(null, "Customer deleted");
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
                    JOptionPane.showMessageDialog(null, "Customer not deleted");
                }
            });
            cancelButton.setActionCommand("Cancel");
            buttonPane.add(cancelButton);
        }

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
