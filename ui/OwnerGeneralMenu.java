package ui;

import controller.OwnerController;
import db.DBException;
import model.Owner;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class OwnerGeneralMenu extends JDialog {
    private JPanel ownerGeneralPanel;
    private JTable table;
    private DefaultTableModel tableModel;
    private JScrollPane scrolledTable;
    private JLabel errorInfo;
    private JButton refresh;
    private JLabel lastUpdateDataLbl;
    DateTimeFormatter dateTimeFormatter;

    private OwnerController ownerController;

    public OwnerGeneralMenu(JFrame mainWindow) {
        ownerController = new OwnerController();
        dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

        ownerGeneralPanel = new JPanel();
        ownerGeneralPanel.setLayout(null);
        ownerGeneralPanel.setVisible(false);
        ownerGeneralPanel.setBounds(mainWindow.getX(), mainWindow.getY(), mainWindow.getWidth(), mainWindow.getHeight() - 50);
        mainWindow.add(ownerGeneralPanel);

        tableModel=  new DefaultTableModel();
        tableModel.addColumn("Personal ID");
        tableModel.addColumn("First name");
        tableModel.addColumn("Surname");
        tableModel.addColumn("Relation");
        tableModel.addColumn("Number of owned LTDs");

        table =  new JTable(tableModel);
        scrolledTable =  new JScrollPane(table);
        scrolledTable.setBounds(ownerGeneralPanel.getX(), ownerGeneralPanel.getY() , ownerGeneralPanel.getWidth(), ownerGeneralPanel.getHeight() - 50);
        scrolledTable.setVisible(true);
        ownerGeneralPanel.add(scrolledTable);

        errorInfo = new JLabel();
        errorInfo.setBounds(mainWindow.getBounds());
        errorInfo.setVisible(false);
        errorInfo.setHorizontalAlignment(SwingConstants.CENTER);
        errorInfo.setVerticalAlignment(SwingConstants.CENTER);
        ownerGeneralPanel.add(errorInfo);

        JLabel lastUpdateLbl =  new JLabel();
        lastUpdateLbl.setText("Last updated:");
        lastUpdateLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        lastUpdateLbl.setBounds(ownerGeneralPanel.getWidth() - 350, ownerGeneralPanel.getHeight() - 50 , 100, 30);
        ownerGeneralPanel.add(lastUpdateLbl);

        lastUpdateDataLbl =  new JLabel();
        lastUpdateDataLbl.setText(dateTimeFormatter.format(LocalDateTime.now()));
        lastUpdateDataLbl.setHorizontalAlignment(SwingConstants.LEFT);
        lastUpdateDataLbl.setBounds(ownerGeneralPanel.getWidth() - 240, ownerGeneralPanel.getHeight() - 50 , 200, 30);
        ownerGeneralPanel.add(lastUpdateDataLbl);


        refresh =  new JButton();
        refresh.setText("Refresh");
        refresh.setBounds(ownerGeneralPanel.getWidth() - 100, ownerGeneralPanel.getHeight() - 50 , 80, 30);
        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateData();
            }
        });
        ownerGeneralPanel.add(refresh);

        updateData();
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
        ownerGeneralPanel.setVisible(true);
    }

    public void hideMenu(){
        ownerGeneralPanel.setVisible(false);
    }
}
