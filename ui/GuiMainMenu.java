package ui;

import controller.OrderController;
import db.DBException;
import model.Order;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class MenuExample
{
    OrderController orderController;

    JMenu orderMenu;
    JMenuItem createOrderMI, updateOrderMI, deleteOrderMI;
    JMenu customerMenu;
    JMenuItem createCustomerMI, updateCustomerMI, deleteCustomerMI;
    JMenu serviceMenu;
    JMenuItem createServiceMI, updateServiceMI, deleteServiceMI;
    JMenu ownerMenu;
    JMenuItem createOwnerMI, updateOwnerMI, deleteOwnerMI;
    JMenu employeeMenu;
    JMenuItem createEmployeeMI, updateEmployeeMI, deleteEmployeeMI;

    JPanel showCustomerPanel, showServicePanel, showEmployeePanel, showOwnerPanel;

    ShowOrder showOrder;

    MenuExample(){
        orderController = new OrderController();
        initialize();
    }

    private void initialize(){
        JFrame mainWindow= new JFrame("Accounting");
        mainWindow.setResizable(false);
        mainWindow.setBounds(0,0,600,500);
        JMenuBar menuBar = new JMenuBar();

        showOrder =  new ShowOrder(mainWindow);
        orderMenu = new JMenu("Order");
        createOrderMI = new JMenuItem("Create order");
        updateOrderMI = new JMenuItem("Update order");
        deleteOrderMI = new JMenuItem("Delete order");
        orderMenu.add(createOrderMI);
        orderMenu.add(updateOrderMI);
        orderMenu.add(deleteOrderMI);
        menuBar.add(orderMenu);

        customerMenu = new JMenu("Customer");
        createCustomerMI = new JMenuItem("Create customer");
        updateCustomerMI = new JMenuItem("Update customer");
        deleteCustomerMI = new JMenuItem("Delete customer");
        customerMenu.add(createCustomerMI);
        customerMenu.add(updateCustomerMI);
        customerMenu.add(deleteCustomerMI);
        menuBar.add(customerMenu);

        serviceMenu = new JMenu("Service");
        createServiceMI = new JMenuItem("Create service");
        updateServiceMI = new JMenuItem("Update service");
        deleteServiceMI = new JMenuItem("Delete service");
        serviceMenu.add(createServiceMI);
        serviceMenu.add(updateServiceMI);
        serviceMenu.add(deleteServiceMI);
        menuBar.add(serviceMenu);

        ownerMenu = new JMenu("Owner");
        createOwnerMI = new JMenuItem("Create owner");
        updateOwnerMI = new JMenuItem("Update owner");
        deleteOwnerMI = new JMenuItem("Delete owner");
        ownerMenu.add(createOwnerMI);
        ownerMenu.add(updateOwnerMI);
        ownerMenu.add(deleteOwnerMI);
        menuBar.add(ownerMenu);

        employeeMenu = new JMenu("Employee");
        createEmployeeMI = new JMenuItem("Create employee");
        updateEmployeeMI = new JMenuItem("Update employee");
        deleteEmployeeMI = new JMenuItem("Delete employee");
        employeeMenu.add(createEmployeeMI);
        employeeMenu.add(updateEmployeeMI);
        employeeMenu.add(deleteEmployeeMI);
        menuBar.add(employeeMenu);

        mainWindow.setLayout(null);
        mainWindow.setVisible(true);
        mainWindow.setJMenuBar(menuBar);

        orderMenu.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                showOrder.setIsVisible(true);
                System.out.println("pokaz order menu");
            }

            @Override
            public void menuDeselected(MenuEvent e) {
            }

            @Override
            public void menuCanceled(MenuEvent e) {

            }
        });

        createOrderMI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CreateOrder();
            }
        });

        updateOrderMI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UpdateOrder();
            }
        });

        deleteOrderMI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DeleteOrder();
            }
        });

        createEmployeeMI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CreateEmployee();
            }
        });

        deleteEmployeeMI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DeleteEmployee();
            }
        });

        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String args[])
    {
        new MenuExample();
    }
}