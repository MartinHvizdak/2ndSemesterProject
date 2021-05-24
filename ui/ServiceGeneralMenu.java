package ui;

import controller.ServiceController;
import db.DBException;
import model.Service;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class ServiceGeneralMenu extends JDialog {
    private boolean isVisible;
    private JPanel serviceGeneralPanel;
    private JTable table;
    private DefaultTableModel tableModel;
    private JScrollPane scrolledTable;
    private JLabel errorInfo;

    private ServiceController serviceController;

    public ServiceGeneralMenu(JFrame mainWindow) {
        serviceController = new ServiceController();
        isVisible = false;

        serviceGeneralPanel = new JPanel();
        serviceGeneralPanel.setLayout(null);
        serviceGeneralPanel.setVisible(false);
        serviceGeneralPanel.setBounds(mainWindow.getBounds());
        mainWindow.add(serviceGeneralPanel);

        tableModel=  new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Name");
        tableModel.addColumn("Description");
        tableModel.addColumn("Price");

        table =  new JTable(tableModel);
        scrolledTable =  new JScrollPane(table);
        scrolledTable.setBounds(mainWindow.getBounds());
        scrolledTable.setVisible(true);
        serviceGeneralPanel.add(scrolledTable);

        errorInfo = new JLabel();
        errorInfo.setBounds(mainWindow.getBounds());
        errorInfo.setVisible(false);
        errorInfo.setHorizontalAlignment(SwingConstants.CENTER);
        errorInfo.setVerticalAlignment(SwingConstants.CENTER);
        serviceGeneralPanel.add(errorInfo);

        updateData();
    }

    public void updateData(){
        for (int i=tableModel.getRowCount()-1;i>=0;i--){
            tableModel.removeRow(0);
        }

        try {
            ArrayList<Service> services = serviceController.getAllServicesFromDB();
            for (Service service : services){
                tableModel.addRow(new Object[]{service.getID(), service.getName(), service.getDescription(), service.getPrice()});
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
        serviceGeneralPanel.setVisible(true);
        updateData();
    }

    public void hideMenu(){
        serviceGeneralPanel.setVisible(false);
    }
}
