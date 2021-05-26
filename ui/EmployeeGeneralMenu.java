package ui;

import controller.EmployeeController;
import db.DBException;
import model.CustomerEmployee;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class EmployeeGeneralMenu extends JDialog {
    private JPanel employeeGeneralPanel;
    private JTable table;
    private DefaultTableModel tableModel;
    private JScrollPane scrolledTable;
    private JLabel errorInfo;
    private JButton refresh;
    private JLabel lastUpdateDataLbl;
    DateTimeFormatter dateTimeFormatter;

    private EmployeeController employeeController;

    public EmployeeGeneralMenu(JFrame mainWindow) {
        employeeController = new EmployeeController();
        dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

        employeeGeneralPanel = new JPanel();
        employeeGeneralPanel.setLayout(null);
        employeeGeneralPanel.setVisible(false);
        employeeGeneralPanel.setBounds(mainWindow.getX(), mainWindow.getY(), mainWindow.getWidth(), mainWindow.getHeight() - 50);
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
        scrolledTable.setBounds(employeeGeneralPanel.getX(), employeeGeneralPanel.getY() , employeeGeneralPanel.getWidth(), employeeGeneralPanel.getHeight() - 50);
        scrolledTable.setVisible(true);
        employeeGeneralPanel.add(scrolledTable);

        errorInfo = new JLabel();
        errorInfo.setBounds(mainWindow.getBounds());
        errorInfo.setVisible(false);
        errorInfo.setHorizontalAlignment(SwingConstants.CENTER);
        errorInfo.setVerticalAlignment(SwingConstants.CENTER);
        employeeGeneralPanel.add(errorInfo);

        JLabel lastUpdateLbl =  new JLabel();
        lastUpdateLbl.setText("Last updated:");
        lastUpdateLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        lastUpdateLbl.setBounds(employeeGeneralPanel.getWidth() - 350, employeeGeneralPanel.getHeight() - 50 , 100, 30);
        employeeGeneralPanel.add(lastUpdateLbl);

        lastUpdateDataLbl =  new JLabel();
        lastUpdateDataLbl.setText(dateTimeFormatter.format(LocalDateTime.now()));
        lastUpdateDataLbl.setHorizontalAlignment(SwingConstants.LEFT);
        lastUpdateDataLbl.setBounds(employeeGeneralPanel.getWidth() - 240, employeeGeneralPanel.getHeight() - 50 , 200, 30);
        employeeGeneralPanel.add(lastUpdateDataLbl);


        refresh =  new JButton();
        refresh.setText("Refresh");
        refresh.setBounds(employeeGeneralPanel.getWidth() - 100, employeeGeneralPanel.getHeight() - 50 , 80, 30);
        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateData();
            }
        });
        employeeGeneralPanel.add(refresh);

        updateData();
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
        employeeGeneralPanel.setVisible(true);
    }

    public void hideMenu(){
        employeeGeneralPanel.setVisible(false);
    }
}
