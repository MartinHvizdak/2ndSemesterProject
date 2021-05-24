package ui;

import controller.EmployeeController;
import db.DBException;
import model.CustomerEmployee;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class EmployeeGeneralMenu extends JDialog {
    private boolean isVisible;
    private JPanel employeeGeneralPanel;
    private JTable table;
    private DefaultTableModel tableModel;
    private JScrollPane scrolledTable;
    private JLabel errorInfo;

    private EmployeeController employeeController;

    public EmployeeGeneralMenu(JFrame mainWindow) {
        employeeController = new EmployeeController();
        isVisible = false;

        employeeGeneralPanel = new JPanel();
        employeeGeneralPanel.setLayout(null);
        employeeGeneralPanel.setVisible(false);
        employeeGeneralPanel.setBounds(mainWindow.getBounds());
        mainWindow.add(employeeGeneralPanel);

        tableModel=  new DefaultTableModel();
        tableModel.addColumn("Personal ID");
        tableModel.addColumn("First name");
        tableModel.addColumn("Surname");
        tableModel.addColumn("Salary");
        tableModel.addColumn("Generated income");
        tableModel.addColumn("LTD email");

        table =  new JTable(tableModel);
        scrolledTable =  new JScrollPane(table);
        scrolledTable.setBounds(mainWindow.getBounds());
        scrolledTable.setVisible(true);
        employeeGeneralPanel.add(scrolledTable);

        errorInfo = new JLabel();
        errorInfo.setBounds(mainWindow.getBounds());
        errorInfo.setVisible(false);
        errorInfo.setHorizontalAlignment(SwingConstants.CENTER);
        errorInfo.setVerticalAlignment(SwingConstants.CENTER);
        employeeGeneralPanel.add(errorInfo);
    }

    public void updateData(){
        for (int i=tableModel.getRowCount()-1;i>=0;i--){
            tableModel.removeRow(0);
        }

        try {
            ArrayList<CustomerEmployee> employees = employeeController.getAllEmployeesFromDB();
            for (CustomerEmployee employee : employees){
                String email = employeeController.getEmployeeLTDEmailByID(employee.getId());
                tableModel.addRow(new Object[]{employee.getId(), employee.getFirstName(), employee.getSurName(), employee.getSalary(), employee.getIncome(), email});
            }
            tableModel.fireTableDataChanged();
            scrolledTable.setVisible(true);
            errorInfo.setText("");
            errorInfo.setVisible(false);
        } catch (DBException e) {
            scrolledTable.setVisible(false);
            errorInfo.setText(e.getMessage());
            errorInfo.setVisible(true);
        }
    }

    public void showMenu(){
        this.isVisible = true;
        employeeGeneralPanel.setVisible(true);
        updateData();
    }

    public void hideMenu(){
        employeeGeneralPanel.setVisible(false);
    }
}
