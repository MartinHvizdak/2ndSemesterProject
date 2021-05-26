package ui;

import controller.ServiceController;
import db.DBException;
import model.Service;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ServiceGeneralMenu extends JDialog {
    private JPanel serviceGeneralPanel;
    private JTable table;
    private DefaultTableModel tableModel;
    private JScrollPane scrolledTable;
    private JLabel errorInfo;
    private JButton refresh;
    private JLabel lastUpdateDataLbl;
    DateTimeFormatter dateTimeFormatter;

    private ServiceController serviceController;

    public ServiceGeneralMenu(JFrame mainWindow) {
        serviceController = new ServiceController();
        dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

        serviceGeneralPanel = new JPanel();
        serviceGeneralPanel.setLayout(null);
        serviceGeneralPanel.setVisible(false);
        serviceGeneralPanel.setBounds(mainWindow.getX(), mainWindow.getY(), mainWindow.getWidth(), mainWindow.getHeight() - 50);
        mainWindow.add(serviceGeneralPanel);

        tableModel=  new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Name");
        tableModel.addColumn("Description");
        tableModel.addColumn("Price");

        table =  new JTable(tableModel);
        scrolledTable =  new JScrollPane(table);
        scrolledTable.setBounds(serviceGeneralPanel.getX(), serviceGeneralPanel.getY() , serviceGeneralPanel.getWidth(), serviceGeneralPanel.getHeight() - 50);
        scrolledTable.setVisible(true);
        serviceGeneralPanel.add(scrolledTable);

        errorInfo = new JLabel();
        errorInfo.setBounds(mainWindow.getBounds());
        errorInfo.setVisible(false);
        errorInfo.setHorizontalAlignment(SwingConstants.CENTER);
        errorInfo.setVerticalAlignment(SwingConstants.CENTER);
        serviceGeneralPanel.add(errorInfo);

        JLabel lastUpdateLbl =  new JLabel();
        lastUpdateLbl.setText("Last updated:");
        lastUpdateLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        lastUpdateLbl.setBounds(serviceGeneralPanel.getWidth() - 350, serviceGeneralPanel.getHeight() - 50 , 100, 30);
        serviceGeneralPanel.add(lastUpdateLbl);

        lastUpdateDataLbl =  new JLabel();
        lastUpdateDataLbl.setText(dateTimeFormatter.format(LocalDateTime.now()));
        lastUpdateDataLbl.setHorizontalAlignment(SwingConstants.LEFT);
        lastUpdateDataLbl.setBounds(serviceGeneralPanel.getWidth() - 240, serviceGeneralPanel.getHeight() - 50 , 200, 30);
        serviceGeneralPanel.add(lastUpdateDataLbl);


        refresh =  new JButton();
        refresh.setText("Refresh");
        refresh.setBounds(serviceGeneralPanel.getWidth() - 100, serviceGeneralPanel.getHeight() - 50 , 80, 30);
        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateData();
            }
        });
        serviceGeneralPanel.add(refresh);

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
        serviceGeneralPanel.setVisible(true);
    }

    public void hideMenu(){
        serviceGeneralPanel.setVisible(false);
    }
}
