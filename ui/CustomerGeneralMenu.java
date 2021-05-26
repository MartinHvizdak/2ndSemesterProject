package ui;

import controller.CustomerController;
import db.DBException;
import model.Customer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CustomerGeneralMenu extends JDialog {
    private JPanel customerGeneralPanel;
    private JTable table;
    private DefaultTableModel tableModel;
    private JScrollPane scrolledTable;
    private JLabel errorInfo;
    private JButton refresh;
    private JLabel lastUpdateDataLbl;
    DateTimeFormatter dateTimeFormatter;

    private CustomerController customerController;

    public CustomerGeneralMenu(JFrame mainWindow) {
        customerController = new CustomerController();
        dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

        customerGeneralPanel = new JPanel();
        customerGeneralPanel.setLayout(null);
        customerGeneralPanel.setVisible(false);
        customerGeneralPanel.setBounds(mainWindow.getX(), mainWindow.getY(), mainWindow.getWidth(), mainWindow.getHeight() - 50);
        mainWindow.add(customerGeneralPanel);

        tableModel=  new DefaultTableModel();
        tableModel.addColumn("Email");
        tableModel.addColumn("Name");
        tableModel.addColumn("Address");
        tableModel.addColumn("Phone number");
        tableModel.addColumn("Type");

        table =  new JTable(tableModel);
        scrolledTable =  new JScrollPane(table);
        scrolledTable.setBounds(customerGeneralPanel.getX(), customerGeneralPanel.getY() , customerGeneralPanel.getWidth(), customerGeneralPanel.getHeight() - 50);
        scrolledTable.setVisible(true);
        customerGeneralPanel.add(scrolledTable);

        errorInfo = new JLabel();
        errorInfo.setBounds(mainWindow.getBounds());
        errorInfo.setVisible(false);
        errorInfo.setHorizontalAlignment(SwingConstants.CENTER);
        errorInfo.setVerticalAlignment(SwingConstants.CENTER);
        customerGeneralPanel.add(errorInfo);

        JLabel lastUpdateLbl =  new JLabel();
        lastUpdateLbl.setText("Last updated:");
        lastUpdateLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        lastUpdateLbl.setBounds(customerGeneralPanel.getWidth() - 350, customerGeneralPanel.getHeight() - 50 , 100, 30);
        customerGeneralPanel.add(lastUpdateLbl);

        lastUpdateDataLbl =  new JLabel();
        lastUpdateDataLbl.setText(dateTimeFormatter.format(LocalDateTime.now()));
        lastUpdateDataLbl.setHorizontalAlignment(SwingConstants.LEFT);
        lastUpdateDataLbl.setBounds(customerGeneralPanel.getWidth() - 240, customerGeneralPanel.getHeight() - 50 , 200, 30);
        customerGeneralPanel.add(lastUpdateDataLbl);


        refresh =  new JButton();
        refresh.setText("Refresh");
        refresh.setBounds(customerGeneralPanel.getWidth() - 100, customerGeneralPanel.getHeight() - 50 , 80, 30);
        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateData();
            }
        });
        customerGeneralPanel.add(refresh);

        updateData();
    }

    private void updateData(){
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

            lastUpdateDataLbl.setText(dateTimeFormatter.format(LocalDateTime.now()));

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
        customerGeneralPanel.setVisible(true);
    }

    public void hideMenu(){
        customerGeneralPanel.setVisible(false);
    }
}
