package ui;

import controller.EmployeeController;
import controller.OrderController;
import db.DBException;
import model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class OrderGeneralMenu extends JDialog {
    private boolean isVisible;
    private JPanel orderGeneralPanel;
    private JTable table;
    private DefaultTableModel tableModel;
    private JScrollPane scrolledTable;
    private JLabel errorInfo;

    private OrderController orderController;

    public OrderGeneralMenu(JFrame mainWindow) {
        orderController = new OrderController();
        isVisible = false;

        orderGeneralPanel = new JPanel();
        orderGeneralPanel.setLayout(null);
        orderGeneralPanel.setVisible(false);
        orderGeneralPanel.setBounds(mainWindow.getBounds());
        mainWindow.add(orderGeneralPanel);

        tableModel=  new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Payday");
        tableModel.addColumn("Unique services");
        tableModel.addColumn("Worth");
        tableModel.addColumn("Customer email");

        table =  new JTable(tableModel);
        scrolledTable =  new JScrollPane(table);
        scrolledTable.setBounds(mainWindow.getBounds());
        scrolledTable.setVisible(true);
        orderGeneralPanel.add(scrolledTable);

        errorInfo = new JLabel();
        errorInfo.setBounds(mainWindow.getBounds());
        errorInfo.setVisible(false);
        errorInfo.setHorizontalAlignment(SwingConstants.CENTER);
        errorInfo.setVerticalAlignment(SwingConstants.CENTER);
        orderGeneralPanel.add(errorInfo);

        updateData();
    }

    public void updateData(){
        for (int i=tableModel.getRowCount()-1;i>=0;i--){
            tableModel.removeRow(0);
        }

        try {
            ArrayList<Order<Customer>> orders = orderController.getAllOrdersFromDB();

            for (Order<Customer> order : orders){
                double worth = 0;
                for (OrderLine ol : order.getOrderLines()){
                    worth += ol.getService().getPrice() * ol.getQuantity();
                }
                tableModel.addRow(new Object[]{order.getId(), order.getPayday(), order.getOrderLines().size(), worth, order.getCustomer().getEmail()});
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
        orderGeneralPanel.setVisible(true);
        updateData();
    }

    public void hideMenu(){
        orderGeneralPanel.setVisible(false);
    }
}

