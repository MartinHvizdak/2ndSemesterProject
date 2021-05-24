package ui;

import controller.OwnerController;
import db.DBException;
import model.Owner;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class OwnerGeneralMenu extends JDialog {
    private boolean isVisible;
    private JPanel ownerGeneralPanel;
    private JTable table;
    private DefaultTableModel tableModel;
    private JScrollPane scrolledTable;
    private JLabel errorInfo;

    private OwnerController ownerController;

    public OwnerGeneralMenu(JFrame mainWindow) {
        ownerController = new OwnerController();
        isVisible = false;

        ownerGeneralPanel = new JPanel();
        ownerGeneralPanel.setLayout(null);
        ownerGeneralPanel.setVisible(false);
        ownerGeneralPanel.setBounds(mainWindow.getBounds());
        mainWindow.add(ownerGeneralPanel);

        tableModel=  new DefaultTableModel();
        tableModel.addColumn("Personal ID");
        tableModel.addColumn("First name");
        tableModel.addColumn("Surname");
        tableModel.addColumn("Relation");
        tableModel.addColumn("Number of owned LTDs");

        table =  new JTable(tableModel);
        scrolledTable =  new JScrollPane(table);
        scrolledTable.setBounds(mainWindow.getBounds());
        scrolledTable.setVisible(true);
        ownerGeneralPanel.add(scrolledTable);

        errorInfo = new JLabel();
        errorInfo.setBounds(mainWindow.getBounds());
        errorInfo.setVisible(false);
        errorInfo.setHorizontalAlignment(SwingConstants.CENTER);
        errorInfo.setVerticalAlignment(SwingConstants.CENTER);
        ownerGeneralPanel.add(errorInfo);
    }

    public void updateData(){
        for (int i=tableModel.getRowCount()-1;i>=0;i--){
            tableModel.removeRow(0);
        }

        try {
            ArrayList<Owner> owners = ownerController.getAllOwnersFromDB();
            for (Owner owner : owners){
                ArrayList<String> emails = ownerController.getOwnerLTDEmailsByIDFromDB(owner.getId());
                tableModel.addRow(new Object[]{owner.getId(), owner.getFirstName(), owner.getSurName(), owner.getRelation(), String.valueOf(emails.size())});
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
        ownerGeneralPanel.setVisible(true);
        updateData();
    }

    public void hideMenu(){
        ownerGeneralPanel.setVisible(false);
    }
}
