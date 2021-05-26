package ui;

import controller.OrderController;
import db.DBException;
import model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class OrderGeneralMenu extends JDialog {
    private JPanel orderGeneralPanel;
    private JTable table;
    private DefaultTableModel tableModel;
    private JScrollPane scrolledTable;
    private JLabel errorInfo;
    private JButton refresh;
    private JLabel lastUpdateDataLbl;
    DateTimeFormatter dateTimeFormatter;
    private OrderController orderController;

    public OrderGeneralMenu(JFrame mainWindow) {
        orderController = new OrderController();
        dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

        orderGeneralPanel = new JPanel();
        orderGeneralPanel.setLayout(null);
        orderGeneralPanel.setVisible(false);
        orderGeneralPanel.setBounds(mainWindow.getX(), mainWindow.getY(), mainWindow.getWidth(), mainWindow.getHeight() - 50);
        mainWindow.add(orderGeneralPanel);

        tableModel=  new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Payday");
        tableModel.addColumn("Unique services");
        tableModel.addColumn("Worth");
        tableModel.addColumn("Customer email");

        table =  new JTable(tableModel);
        scrolledTable =  new JScrollPane(table);
        scrolledTable.setBounds(orderGeneralPanel.getX(), orderGeneralPanel.getY() , orderGeneralPanel.getWidth(), orderGeneralPanel.getHeight() - 50);
        scrolledTable.setVisible(true);
        orderGeneralPanel.add(scrolledTable);

        errorInfo = new JLabel();
        errorInfo.setBounds(mainWindow.getBounds());
        errorInfo.setVisible(false);
        errorInfo.setHorizontalAlignment(SwingConstants.CENTER);
        errorInfo.setVerticalAlignment(SwingConstants.CENTER);
        orderGeneralPanel.add(errorInfo);

        JLabel lastUpdateLbl =  new JLabel();
        lastUpdateLbl.setText("Last updated:");
        lastUpdateLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        lastUpdateLbl.setBounds(orderGeneralPanel.getWidth() - 350, orderGeneralPanel.getHeight() - 50 , 100, 30);
        orderGeneralPanel.add(lastUpdateLbl);

        lastUpdateDataLbl =  new JLabel();
        lastUpdateDataLbl.setText(dateTimeFormatter.format(LocalDateTime.now()));
        lastUpdateDataLbl.setHorizontalAlignment(SwingConstants.LEFT);
        lastUpdateDataLbl.setBounds(orderGeneralPanel.getWidth() - 240, orderGeneralPanel.getHeight() - 50 , 200, 30);
        orderGeneralPanel.add(lastUpdateDataLbl);


        refresh =  new JButton();
        refresh.setText("Refresh");
        refresh.setBounds(orderGeneralPanel.getWidth() - 100, orderGeneralPanel.getHeight() - 50 , 80, 30);
        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateData();
            }
        });
        orderGeneralPanel.add(refresh);

        updateData();
    }

    private void updateData(){
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
        orderGeneralPanel.setVisible(true);
    }

    public void hideMenu(){
        orderGeneralPanel.setVisible(false);
    }
}

