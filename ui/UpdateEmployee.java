package ui;

import controller.EmployeeController;
import db.DBException;
import model.CustomerEmployee;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UpdateEmployee extends JDialog {
    private JPanel contentPanel;

    private EmployeeController employeeController;

    public UpdateEmployee() {
        super(null,"Update employee",ModalityType.APPLICATION_MODAL);
        contentPanel =  new JPanel();
        setBounds(100, 100, 600, 400);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setLayout(null);
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        employeeController =  new EmployeeController();



        //Labels
        JLabel oldPersonalIDLbl = new JLabel("Personal ID:");
        oldPersonalIDLbl.setBounds(30, 20, 200, 20);
        oldPersonalIDLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(oldPersonalIDLbl);

        JLabel newPersonalIDLbl = new JLabel("New personal ID (Update):");
        newPersonalIDLbl.setBounds(30, 60, 200, 20);
        newPersonalIDLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(newPersonalIDLbl);

        JLabel firstNameLbl = new JLabel("First name:");
        firstNameLbl.setBounds(30, 100, 200, 20);
        firstNameLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(firstNameLbl);

        JLabel lastNameLbl = new JLabel("Last name:");
        lastNameLbl.setBounds(30, 140, 200, 20);
        lastNameLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(lastNameLbl);

        JLabel salaryLbl = new JLabel("Salary:");
        salaryLbl.setBounds(30, 180, 200, 20);
        salaryLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(salaryLbl);

        JLabel generatedIncomeLbl = new JLabel("Generated income:");
        generatedIncomeLbl.setBounds(30, 220, 200, 20);
        generatedIncomeLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(generatedIncomeLbl);

        JLabel ltdEmailLbl = new JLabel("LTD's email:");
        ltdEmailLbl.setBounds(30, 260, 200, 20);
        ltdEmailLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(ltdEmailLbl);

        //Fields
        JTextField oldPersonalIDTxt = new JTextField("");
        oldPersonalIDTxt.setBounds(240, 20, 200, 20);
        contentPanel.add(oldPersonalIDTxt);

        JTextField newPersonalIDTxt = new JTextField("");
        newPersonalIDTxt.setBounds(240, 60, 200, 20);
        contentPanel.add(newPersonalIDTxt);

        JTextField firstNameTxt = new JTextField("");
        firstNameTxt.setBounds(240, 100, 200, 20);
        contentPanel.add(firstNameTxt);

        JTextField lastNameTxt = new JTextField("");
        lastNameTxt.setBounds(240, 140, 200, 20);
        contentPanel.add(lastNameTxt);

        JTextField salaryTxt = new JTextField("");
        salaryTxt.setBounds(240, 180, 200, 20);
        contentPanel.add(salaryTxt);

        JTextField generatedIncomeTxt = new JTextField("");
        generatedIncomeTxt.setBounds(240, 220, 200, 20);
        contentPanel.add(generatedIncomeTxt);

        JTextField ltdEmailTxt = new JTextField("");
        ltdEmailTxt.setBounds(240, 260, 200, 20);
        contentPanel.add(ltdEmailTxt);


        JButton showButton = new JButton("Show");
        showButton.setBounds(450, 20, 80, 20);
        showButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (oldPersonalIDTxt.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Please Enter Personal ID");
                }else {
                    try {
                        CustomerEmployee employee = employeeController.getEmployeeByIDFromDB(oldPersonalIDTxt.getText());
                        String ltdEmail = employeeController.getEmployeeLTDEmailByID(oldPersonalIDTxt.getText());
                        newPersonalIDTxt.setText(employee.getId());
                        firstNameTxt.setText(employee.getFirstName());
                        lastNameTxt.setText(employee.getSurName());
                        salaryTxt.setText(String.valueOf(employee.getSalary()));
                        generatedIncomeTxt.setText(String.valueOf(employee.getIncome()));
                        ltdEmailTxt.setText(ltdEmail);
                    } catch (DBException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
                }
            }
        });
        contentPanel.add(showButton);

        // Section with Update and Cancel buttons:
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        {
            JButton deleteButton = new JButton("Update");
            deleteButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (oldPersonalIDTxt.getText().equals("")|| newPersonalIDTxt.getText().equals("") || firstNameTxt.getText().equals("") || lastNameTxt.getText().equals("") ||
                            salaryTxt.getText().equals("") || generatedIncomeTxt.getText().equals("")){
                        JOptionPane.showMessageDialog(null, "Please Fill In All Necessary Fields");
                    }else {
                        double salary = 0, generatedIncome = 0;

                        try {
                            salary = Double.parseDouble(salaryTxt.getText());
                        } catch (NumberFormatException ex){
                            JOptionPane.showMessageDialog(null, "Salary has to be a double");
                        }

                        try {
                            generatedIncome = Double.parseDouble(salaryTxt.getText());
                        } catch (NumberFormatException ex){
                            JOptionPane.showMessageDialog(null, "Generated income has to be a double");
                        }

                        String ltdEmail = null;
                        if (!ltdEmailTxt.getText().trim().equals("")){
                            ltdEmail = ltdEmailTxt.getText().trim();
                        }

                        try {
                            CustomerEmployee employee = employeeController.getEmployeeByIDFromDB(oldPersonalIDTxt.getText());
                            employeeController.updateEmployeeWithUserInputInDB(employee , newPersonalIDTxt.getText(), firstNameTxt.getText(),
                                    lastNameTxt.getText(), salary, generatedIncome, ltdEmail);
                            dispose();
                            JOptionPane.showMessageDialog(null, "Employee updated");
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
                    JOptionPane.showMessageDialog(null, "Employee not updated");
                }
            });
            cancelButton.setActionCommand("Cancel");
            buttonPane.add(cancelButton);
        }

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
