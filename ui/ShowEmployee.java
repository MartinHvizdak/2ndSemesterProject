package ui;

import controller.EmployeeController;
import db.DBException;
import model.CustomerEmployee;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ShowEmployee extends JDialog {
    private JPanel contentPanel;

    private EmployeeController employeeController;

    public ShowEmployee() {
        super(null,"Show employee",ModalityType.APPLICATION_MODAL);
        contentPanel = new JPanel();
        setBounds(100, 100, 700, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setLayout(null);
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        employeeController = new EmployeeController();


        //Labels
        JLabel personalIDLbl = new JLabel("Personal ID:");
        personalIDLbl.setBounds(30, 20, 200, 20);
        personalIDLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(personalIDLbl);

        JLabel firstNameLbl = new JLabel("First name:");
        firstNameLbl.setBounds(30, 60, 200, 20);
        firstNameLbl.setVisible(false);
        firstNameLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(firstNameLbl);

        JLabel lastNameLbl = new JLabel("Last name:");
        lastNameLbl.setBounds(30, 100, 200, 20);
        lastNameLbl.setVisible(false);
        lastNameLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(lastNameLbl);

        JLabel salaryLbl = new JLabel("Salary:");
        salaryLbl.setBounds(30, 140, 200, 20);
        salaryLbl.setVisible(false);
        salaryLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(salaryLbl);

        JLabel generatedIncomeLbl = new JLabel("Generated income:");
        generatedIncomeLbl.setBounds(30, 180, 200, 20);
        generatedIncomeLbl.setVisible(false);
        generatedIncomeLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(generatedIncomeLbl);

        JLabel ltdEmailLbl = new JLabel("LTD's email:");
        ltdEmailLbl.setBounds(30, 220, 200, 20);
        ltdEmailLbl.setVisible(false);
        ltdEmailLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(ltdEmailLbl);

        //Fields
        JTextField personalIDTxt = new JTextField();
        personalIDTxt.setBounds(240, 20, 200, 20);
        contentPanel.add(personalIDTxt);

        JLabel firstNameDataLbl = new JLabel();
        firstNameDataLbl.setBounds(240, 60, 200, 20);
        firstNameDataLbl.setVisible(false);
        contentPanel.add(firstNameDataLbl);

        JLabel lastNameDataLbl = new JLabel();
        lastNameDataLbl.setBounds(240, 100, 200, 20);
        lastNameDataLbl.setVisible(false);
        contentPanel.add(lastNameDataLbl);

        JLabel salaryDataLbl = new JLabel();
        salaryDataLbl.setBounds(240, 140, 200, 20);
        salaryDataLbl.setVisible(false);
        contentPanel.add(salaryDataLbl);

        JLabel generatedIncomeDataLbl = new JLabel();
        generatedIncomeDataLbl.setBounds(240, 180, 200, 20);
        generatedIncomeDataLbl.setVisible(false);
        contentPanel.add(generatedIncomeDataLbl);

        JLabel ltdEmailDataLbl = new JLabel();
        ltdEmailDataLbl.setBounds(240, 220, 200, 20);
        ltdEmailDataLbl.setVisible(false);
        contentPanel.add(ltdEmailDataLbl);

        JButton showButton = new JButton("Show");
        showButton.setBounds(450,20,80, 20 );
        showButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (personalIDTxt.getText().trim().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please Enter Personal ID");
                } else {
                    try {
                        CustomerEmployee employee = employeeController.getEmployeeByIDFromDB(personalIDTxt.getText().trim());
                        String ltdEmail = employeeController.getEmployeeLTDEmailByID(personalIDTxt.getText().trim());
                        personalIDTxt.setText(employee.getId());
                        firstNameDataLbl.setText(employee.getFirstName());
                        lastNameDataLbl.setText(employee.getSurName());
                        salaryDataLbl.setText(String.valueOf(employee.getSalary()));
                        generatedIncomeDataLbl.setText(String.valueOf(employee.getIncome()));
                        ltdEmailDataLbl.setText(ltdEmail);

                        firstNameDataLbl.setVisible(true);
                        lastNameDataLbl.setVisible(true);
                        salaryDataLbl.setVisible(true);
                        generatedIncomeDataLbl.setVisible(true);
                        ltdEmailDataLbl.setVisible(true);
                        firstNameLbl.setVisible(true);
                        lastNameLbl.setVisible(true);
                        salaryLbl.setVisible(true);
                        generatedIncomeLbl.setVisible(true);
                        ltdEmailLbl.setVisible(true);
                    } catch (DBException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
                }
            }
        });
        contentPanel.add(showButton);

        // Section with Create and Cancel buttons:
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
