package ui;

import controller.CustomerController;
import controller.EmployeeController;
import db.DBException;
import model.Customer;
import model.CustomerEmployee;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class CustomerGeneralMenu extends JDialog {
    private boolean isVisible;
    private JPanel customerGeneralPanel;
    private JTable table;
    private DefaultTableModel tableModel;
    private JScrollPane scrolledTable;
    private JLabel errorInfo;

    private CustomerController customerController;

    public CustomerGeneralMenu(JFrame mainWindow) {
        customerController = new CustomerController();
        isVisible = false;

        customerGeneralPanel = new JPanel();
        customerGeneralPanel.setLayout(null);
        customerGeneralPanel.setVisible(false);
        customerGeneralPanel.setBounds(mainWindow.getBounds());
        mainWindow.add(customerGeneralPanel);

        tableModel=  new DefaultTableModel();
        tableModel.addColumn("Email");
        tableModel.addColumn("Name");
        tableModel.addColumn("Address");
        tableModel.addColumn("Phone number");
        tableModel.addColumn("Type");

        table =  new JTable(tableModel);
        scrolledTable =  new JScrollPane(table);
        scrolledTable.setBounds(mainWindow.getBounds());
        scrolledTable.setVisible(true);
        customerGeneralPanel.add(scrolledTable);

        errorInfo = new JLabel();
        errorInfo.setBounds(mainWindow.getBounds());
        errorInfo.setVisible(false);
        errorInfo.setHorizontalAlignment(SwingConstants.CENTER);
        errorInfo.setVerticalAlignment(SwingConstants.CENTER);
        customerGeneralPanel.add(errorInfo);
    }

    public void updateData(){
        for (int i=tableModel.getRowCount()-1;i>=0;i--){
            tableModel.removeRow(0);
        }

        try {
            ArrayList<Customer> customers = customerController.getAllCustomersFromDB();
            for (Customer customer : customers){
                String customerType = customerController.getCustomerTypeByEmailFromDB(customer.getEmail());
                String customerAddress = customer.getZipCode() + ", " + customer.getCity() + ", " + customer.getStreet() + " " + customer.getStreetNumber();
                tableModel.addRow(new Object[]{customer.getEmail(), customer.getName(), customerAddress, customer.getPhoneNumber(), customerType});
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
        customerGeneralPanel.setVisible(true);
        updateData();
    }

    public void hideMenu(){
        customerGeneralPanel.setVisible(false);
    }
}
