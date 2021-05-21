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

public class CreateEmployee extends JDialog {
    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private EmployeeController employeeController =  new EmployeeController();


    public CreateEmployee() {
        super(null,"Create employee",ModalityType.APPLICATION_MODAL);
        setBounds(100, 100, 800, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setLayout(null);
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        //Labels
        JLabel personalIDLbl = new JLabel("Personal ID:");
        personalIDLbl.setBounds(30, 20, 200, 20);
        personalIDLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(personalIDLbl);

        JLabel firstNameLbl = new JLabel("First name:");
        firstNameLbl.setBounds(30, 60, 200, 20);
        firstNameLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(firstNameLbl);

        JLabel lastNameLbl = new JLabel("Last name:");
        lastNameLbl.setBounds(30, 100, 200, 20);
        lastNameLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(lastNameLbl);

        JLabel salaryLbl = new JLabel("Salary:");
        salaryLbl.setBounds(30, 140, 200, 20);
        salaryLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(salaryLbl);

        JLabel generatedIncomeLbl = new JLabel("Generated income");
        generatedIncomeLbl.setBounds(30, 180, 200, 20);
        generatedIncomeLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(generatedIncomeLbl);

        JLabel ltdEmailLbl = new JLabel("LTD's email");
        ltdEmailLbl.setBounds(30, 220, 200, 20);
        ltdEmailLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(ltdEmailLbl);

        //Fields
        JTextField personalIDTxt = new JTextField("");
        personalIDTxt.setBounds(240, 20, 200, 20);
        contentPanel.add(personalIDTxt);

        JTextField firstNameTxt = new JTextField("");
        firstNameTxt.setBounds(240, 60, 200, 20);
        contentPanel.add(firstNameTxt);

        JTextField lastNameTxt = new JTextField("");
        lastNameTxt.setBounds(240, 100, 200, 20);
        contentPanel.add(lastNameTxt);

        JTextField salaryTxt = new JTextField("");
        salaryTxt.setBounds(240, 140, 200, 20);
        contentPanel.add(salaryTxt);

        JTextField generatedIncomeTxt = new JTextField("");
        generatedIncomeTxt.setBounds(240, 180, 200, 20);
        contentPanel.add(generatedIncomeTxt);

        JTextField ltdEmailTxt = new JTextField("");
        ltdEmailTxt.setBounds(240, 220, 200, 20);
        contentPanel.add(ltdEmailTxt);


        // Section with OK and Cancel buttons:
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        {
            JButton okButton = new JButton("Create");
            okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (personalIDTxt.getText().equals("") || firstNameTxt.getText().equals("") || lastNameTxt.getText().equals("") ||
                            salaryTxt.getText().equals("") || generatedIncomeTxt.getText().equals("") || ltdEmailTxt.getText().equals("")){
                        JOptionPane.showMessageDialog(null, "Please Fill In All Fields");
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

                        try {
                            employeeController.saveEmployeeWithUserInputInDB(personalIDTxt.getText(), firstNameTxt.getText(),
                                    lastNameTxt.getText(), salary, generatedIncome, ltdEmailTxt.getText());
                            dispose();
                            JOptionPane.showMessageDialog(null, "Employee created");
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
                    JOptionPane.showMessageDialog(null, "Employee not created");
                }
            });
            cancelButton.setActionCommand("Cancel");
            buttonPane.add(cancelButton);
        }

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
