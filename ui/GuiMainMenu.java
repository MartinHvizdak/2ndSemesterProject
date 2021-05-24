package ui;

import controller.OrderController;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class MenuExample
{
    OrderController orderController;

    JMenu orderMenu;
    JMenuItem createOrderMI, showOrderMI, updateOrderMI, deleteOrderMI;
    JMenu customerMenu;
    JMenuItem createCustomerMI ,showCustomerMI, updateCustomerMI, deleteCustomerMI;
    JMenu serviceMenu;
    JMenuItem createServiceMI, showServiceMI, updateServiceMI, deleteServiceMI;
    JMenu ownerMenu;
    JMenuItem createOwnerMI, showOwnerMI, updateOwnerMI, deleteOwnerMI;
    JMenu employeeMenu;
    JMenuItem createEmployeeMI, showEmployeeMI, updateEmployeeMI, deleteEmployeeMI;

    JPanel showCustomerPanel, showServicePanel, showEmployeePanel, showOwnerPanel;

    OwnerGeneralMenu ownerGeneralMenu;
    EmployeeGeneralMenu employeeGeneralMenu;
    OrderGeneralMenu orderGeneralMenu;
    ServiceGeneralMenu serviceGeneralMenu;
    CustomerGeneralMenu customerGeneralMenu;

    DBConnectionIndicator dbConnectionIndicator;

    MenuExample(){
        orderController = new OrderController();
        initialize();
    }

    private void initialize(){
        JFrame mainWindow= new JFrame("Accounting");
        mainWindow.setResizable(true);
        mainWindow.setBounds(0,0,1000,500);
        JMenuBar menuBar = new JMenuBar();

        dbConnectionIndicator =  new DBConnectionIndicator(mainWindow);
        Thread connectionIndicatorThread =  new Thread(dbConnectionIndicator);
        connectionIndicatorThread.start();

        serviceGeneralMenu =  new ServiceGeneralMenu(mainWindow);
        ownerGeneralMenu =  new OwnerGeneralMenu(mainWindow);
        employeeGeneralMenu =  new EmployeeGeneralMenu(mainWindow);
        orderGeneralMenu =  new OrderGeneralMenu(mainWindow);
        customerGeneralMenu =  new CustomerGeneralMenu(mainWindow);
        orderMenu = new JMenu("Order");
        createOrderMI = new JMenuItem("Create order");
        showOrderMI = new JMenuItem("Show order");
        updateOrderMI = new JMenuItem("Update order");
        deleteOrderMI = new JMenuItem("Delete order");
        orderMenu.add(createOrderMI);
        orderMenu.add(showOrderMI);
        orderMenu.add(updateOrderMI);
        orderMenu.add(deleteOrderMI);
        menuBar.add(orderMenu);

        customerMenu = new JMenu("Customer");
        createCustomerMI = new JMenuItem("Create customer");
        showCustomerMI = new JMenuItem("Show customer");
        updateCustomerMI = new JMenuItem("Update customer");
        deleteCustomerMI = new JMenuItem("Delete customer");
        customerMenu.add(createCustomerMI);
        customerMenu.add(showCustomerMI);
        customerMenu.add(updateCustomerMI);
        customerMenu.add(deleteCustomerMI);
        menuBar.add(customerMenu);

        serviceMenu = new JMenu("Service");
        createServiceMI = new JMenuItem("Create service");
        showServiceMI = new JMenuItem("Show service");
        updateServiceMI = new JMenuItem("Update service");
        deleteServiceMI = new JMenuItem("Delete service");
        serviceMenu.add(createServiceMI);
        serviceMenu.add(showServiceMI);
        serviceMenu.add(updateServiceMI);
        serviceMenu.add(deleteServiceMI);
        menuBar.add(serviceMenu);

        ownerMenu = new JMenu("Owner");
        createOwnerMI = new JMenuItem("Create owner");
        showOwnerMI = new JMenuItem("Show owner");
        updateOwnerMI = new JMenuItem("Update owner");
        deleteOwnerMI = new JMenuItem("Delete owner");
        ownerMenu.add(createOwnerMI);
        ownerMenu.add(showOwnerMI);
        ownerMenu.add(updateOwnerMI);
        ownerMenu.add(deleteOwnerMI);
        menuBar.add(ownerMenu);

        employeeMenu = new JMenu("Employee");
        createEmployeeMI = new JMenuItem("Create employee");
        showEmployeeMI = new JMenuItem("Show employee");
        updateEmployeeMI = new JMenuItem("Update employee");
        deleteEmployeeMI = new JMenuItem("Delete employee");
        employeeMenu.add(createEmployeeMI);
        employeeMenu.add(showEmployeeMI);
        employeeMenu.add(updateEmployeeMI);
        employeeMenu.add(deleteEmployeeMI);
        menuBar.add(employeeMenu);

        mainWindow.setLayout(null);
        mainWindow.setVisible(true);
        mainWindow.setJMenuBar(menuBar);

        //Order menu
        orderMenu.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                customerGeneralMenu.hideMenu();
                serviceGeneralMenu.hideMenu();
                ownerGeneralMenu.hideMenu();
                employeeGeneralMenu.hideMenu();
                orderGeneralMenu.showMenu();
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

        showOrderMI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ShowOrder();
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

        //Employee menu
        employeeMenu.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                customerGeneralMenu.hideMenu();
                serviceGeneralMenu.hideMenu();
                ownerGeneralMenu.hideMenu();
                orderGeneralMenu.hideMenu();
                employeeGeneralMenu.showMenu();
            }

            @Override
            public void menuDeselected(MenuEvent e) {

            }

            @Override
            public void menuCanceled(MenuEvent e) {

            }
        });

        createEmployeeMI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CreateEmployee();
            }
        });

        showEmployeeMI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ShowEmployee();
            }
        });

        updateEmployeeMI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UpdateEmployee();
            }
        });

        deleteEmployeeMI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DeleteEmployee();
            }
        });

        //Owner menu
        ownerMenu.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                customerGeneralMenu.hideMenu();
                serviceGeneralMenu.hideMenu();
                orderGeneralMenu.hideMenu();
                employeeGeneralMenu.hideMenu();
                ownerGeneralMenu.showMenu();
            }

            @Override
            public void menuDeselected(MenuEvent e) {

            }

            @Override
            public void menuCanceled(MenuEvent e) {

            }
        });

        createOwnerMI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CreateOwner();
            }
        });

        showOwnerMI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ShowOwner();
            }
        });

        updateOwnerMI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UpdateOwner();
            }
        });

        deleteOwnerMI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DeleteOwner();
            }
        });

        //Service menu
        serviceMenu.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                customerGeneralMenu.hideMenu();
                orderGeneralMenu.hideMenu();
                employeeGeneralMenu.hideMenu();
                ownerGeneralMenu.hideMenu();
                serviceGeneralMenu.showMenu();
            }

            @Override
            public void menuDeselected(MenuEvent e) {

            }

            @Override
            public void menuCanceled(MenuEvent e) {

            }
        });

        createServiceMI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CreateService();
            }
        });

        showServiceMI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ShowService();
            }
        });

        updateServiceMI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UpdateService();
            }
        });

        deleteServiceMI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DeleteService();
            }
        });

        //Service menu
        customerMenu.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                orderGeneralMenu.hideMenu();
                employeeGeneralMenu.hideMenu();
                ownerGeneralMenu.hideMenu();
                serviceGeneralMenu.hideMenu();
                customerGeneralMenu.showMenu();
            }

            @Override
            public void menuDeselected(MenuEvent e) {

            }

            @Override
            public void menuCanceled(MenuEvent e) {

            }
        });

        createCustomerMI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CreateCustomer();
            }
        });

        showCustomerMI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ShowCustomer();
            }
        });

        updateCustomerMI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UpdateCustomer();
            }
        });

        deleteCustomerMI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DeleteCustomer();
            }
        });

        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String args[])
    {
        new MenuExample();
    }
}