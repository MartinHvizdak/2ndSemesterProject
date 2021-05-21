package ui;

import controller.EmployeeController;
import controller.OrderController;
import db.DBException;
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

public class DeleteEmployee extends JDialog {
    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private EmployeeController employeeController =  new EmployeeController();


    public DeleteEmployee() {
        super(null,"Delete employee",ModalityType.APPLICATION_MODAL);
        setBounds(100, 100, 430, 200);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setLayout(null);
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        //Labels
        JLabel personalIDLbl = new JLabel("Personal ID:");
        personalIDLbl.setBounds(30, 20, 200, 20);
        personalIDLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(personalIDLbl);

        //Fields
        JTextField personalIDTxt = new JTextField("");
        personalIDTxt.setBounds(240, 20, 200, 20);
        contentPanel.add(personalIDTxt);

        // Section with OK and Cancel buttons:
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        {
            JButton okButton = new JButton("Delete");
            okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (personalIDTxt.getText().equals("")){
                        JOptionPane.showMessageDialog(null, "Please Fill In All Fields");
                    }else {
                        try {
                            employeeController.deleteEmployeeByIDFromDB(personalIDTxt.getText());
                            dispose();
                            JOptionPane.showMessageDialog(null, "Employee deleted");
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
                    JOptionPane.showMessageDialog(null, "Employee not deleted");
                }
            });
            cancelButton.setActionCommand("Cancel");
            buttonPane.add(cancelButton);
        }

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
