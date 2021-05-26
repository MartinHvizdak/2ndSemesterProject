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

public class DeleteServiceMenu extends JDialog {
    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private ServiceController serviceController =  new ServiceController();


    public DeleteServiceMenu() {
        super(null,"Delete service",ModalityType.APPLICATION_MODAL);
        setBounds(100, 100, 600, 200);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setLayout(null);
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        //Labels
        JLabel idLbl = new JLabel("ID:");
        idLbl.setBounds(30, 20, 200, 20);
        idLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(idLbl);

        //Fields
        JTextField idTxt = new JTextField();
        idTxt.setBounds(240, 20, 200, 20);
        contentPanel.add(idTxt);

        // Section with Delete and Cancel buttons:
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        {
            JButton deleteButton = new JButton("Delete");
            deleteButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (idTxt.getText().trim().equals("")){
                        JOptionPane.showMessageDialog(null, "Please Enter ID");
                    }else {
                        int id = 0;
                        try {
                            id = Integer.parseInt(idTxt.getText().trim());
                        } catch (NumberFormatException ex){
                            JOptionPane.showMessageDialog(null, "ID has to be an integer");
                        }

                        try {
                            serviceController.deleteServiceByIDFromDB(id);
                            dispose();
                            JOptionPane.showMessageDialog(null, "Service deleted");
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
                    JOptionPane.showMessageDialog(null, "Service not deleted");
                }
            });
            cancelButton.setActionCommand("Cancel");
            buttonPane.add(cancelButton);
        }

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
