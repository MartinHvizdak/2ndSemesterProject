package ui;

import controller.OrderController;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class GuiMainMenu
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
    JMenu summaryMenu;

    OwnerGeneralMenu ownerGeneralMenu;
    EmployeeGeneralMenu employeeGeneralMenu;
    OrderGeneralMenu orderGeneralMenu;
    ServiceGeneralMenu serviceGeneralMenu;
    CustomerGeneralMenu customerGeneralMenu;
    CreateSummaryMenu createSummaryMenu;
	CreateContractsMenu createContractsMenu;

    ConnectionIndicator dbConnectionIndicator;

    GuiMainMenu(){
        orderController = new OrderController();
        initialize();
    }

    private void initialize(){
        JFrame mainWindow= new JFrame("Accounting");
        mainWindow.setResizable(false);
        mainWindow.setBounds(0,0,1000,500);
        JMenuBar menuBar = new JMenuBar();

        dbConnectionIndicator =  new ConnectionIndicator(mainWindow);
        Thread connectionIndicatorThread =  new Thread(dbConnectionIndicator);
        connectionIndicatorThread.start();
                
        serviceGeneralMenu =  new ServiceGeneralMenu(mainWindow);
        ownerGeneralMenu =  new OwnerGeneralMenu(mainWindow);
        employeeGeneralMenu =  new EmployeeGeneralMenu(mainWindow);
        orderGeneralMenu =  new OrderGeneralMenu(mainWindow);
        customerGeneralMenu =  new CustomerGeneralMenu(mainWindow);
        createSummaryMenu = new CreateSummaryMenu(mainWindow);
		createContractsMenu = new CreateContractsMenu(mainWindow);

        orderGeneralMenu.showMenu();

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
        
        createSummaryMenu = new CreateSummaryMenu(mainWindow);
        summaryMenu = new JMenu("Create Summary");
        menuBar.add(summaryMenu);
		
		contractsMenu = new JMenu("Create Contracts");
       	menuBar.add(contractsMenu);

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
                createSummaryMenu.hideMenu();
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
                new CreateOrderMenu().setResizable(false);
            }
        });

        showOrderMI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ShowOrderMenu().setResizable(false);
            }
        });

        updateOrderMI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UpdateOrderMenu().setResizable(false);
            }
        });

        deleteOrderMI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DeleteOrderMenu().setResizable(false);
            }
        });

        //Customer menu
        customerMenu.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                orderGeneralMenu.hideMenu();
                employeeGeneralMenu.hideMenu();
                ownerGeneralMenu.hideMenu();
                serviceGeneralMenu.hideMenu();
                createSummaryMenu.hideMenu();
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
                new CreateCustomerMenu().setResizable(false);
            }
        });

        showCustomerMI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ShowCustomerMenu().setResizable(false);
            }
        });

        updateCustomerMI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UpdateCustomerMenu().setResizable(false);
            }
        });

        deleteCustomerMI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DeleteCustomerMenu().setResizable(false);
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
                createSummaryMenu.hideMenu();
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
                new CreateEmployeeMenu().setResizable(false);
            }
        });

        showEmployeeMI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ShowEmployeeMenu().setResizable(false);
            }
        });

        updateEmployeeMI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UpdateEmployeeMenu().setResizable(false);
            }
        });

        deleteEmployeeMI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DeleteEmployeeMenu().setResizable(false);
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
                createSummaryMenu.hideMenu();
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
                new CreateOwnerMenu().setResizable(false);
            }
        });

        showOwnerMI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ShowOwnerMenu().setResizable(false);
            }
        });

        updateOwnerMI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UpdateOwnerMenu().setResizable(false);
            }
        });

        deleteOwnerMI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DeleteOwnerMenu().setResizable(false);
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
                createSummaryMenu.hideMenu();
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
                new CreateServiceMenu().setResizable(false);
            }
        });

        showServiceMI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ShowServiceMenu().setResizable(false);
            }
        });

        updateServiceMI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UpdateServiceMenu().setResizable(false);
            }
        });

        deleteServiceMI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DeleteServiceMenu().setResizable(false);
            }
        });

        //Summary
        summaryMenu.addMenuListener(new MenuListener() {

			@Override
			public void menuSelected(MenuEvent e) {
				orderGeneralMenu.hideMenu();
                employeeGeneralMenu.hideMenu();
                ownerGeneralMenu.hideMenu();
                serviceGeneralMenu.hideMenu();
                customerGeneralMenu.hideMenu();
                createSummaryMenu.showMenu();
			}

			@Override
			public void menuDeselected(MenuEvent e) {
			}

			@Override
			public void menuCanceled(MenuEvent e) {				
			}
        	
        });
		
		contractsMenu.addMenuListener(new MenuListener() {

			@Override
			public void menuSelected(MenuEvent e) {
				orderGeneralMenu.hideMenu();
                employeeGeneralMenu.hideMenu();
                ownerGeneralMenu.hideMenu();
                serviceGeneralMenu.hideMenu();
                customerGeneralMenu.hideMenu();
                createSummaryMenu.hideMenu();
                createContractsMenu.showMenu();
			}

			@Override
			public void menuDeselected(MenuEvent e) {
			}

			@Override
			public void menuCanceled(MenuEvent e) {				
			}
        	
        });


        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String args[])
    {
        new GuiMainMenu();
    }
}
