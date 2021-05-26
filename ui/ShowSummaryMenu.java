package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Locale;
import java.util.ArrayList;

public class ShowSummaryMenu extends JDialog{
	
	private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
	double earnings;
	int numberOfOrders;
	ArrayList<String> services;
	ArrayList<Integer> numberOfServicesPerType;
	ArrayList<Double> earningsPerService;
	
	JLabel earningsLbl;
	JLabel earningsDataLbl;
	JLabel numberOfOrdersLbl;
	JLabel numberOfOrdersDataLbl;
	JLabel servicesWithEarningsLbl;
	JList servicesWithEarningsBox;
	
	public ShowSummaryMenu(double earnings, int numberOfOrders, ArrayList<String> services, 
			ArrayList<Integer> numberOfServicesPerType, ArrayList<Double> earningsPerService) {
		
		super(null,"ShowSummaryMenu",ModalityType.APPLICATION_MODAL);
        setBounds(100, 100, 800, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setLayout(null);
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        
		this.earnings = earnings;
		this.numberOfOrders = numberOfOrders;
		this.services = services;
		this.numberOfServicesPerType = numberOfServicesPerType;
		this.earningsPerService = earningsPerService;
		
		System.out.println(earnings);
		System.out.println(numberOfOrders);
		System.out.println(services);
		
		//Labels
		earningsLbl = new JLabel("Total earnings: ");
		earningsLbl.setBounds(10, 20, 250, 20);
		earningsLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		contentPanel.add(earningsLbl);
				
		numberOfOrdersLbl = new JLabel("Number of orders in total: ");
		numberOfOrdersLbl.setBounds(10, 60, 250, 20);
		numberOfOrdersLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		contentPanel.add(numberOfOrdersLbl);
		
		servicesWithEarningsLbl = new JLabel("Services with quantity and earnings: ");
		servicesWithEarningsLbl.setBounds(10, 100, 250, 20);
		servicesWithEarningsLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		contentPanel.add(servicesWithEarningsLbl);
		
		@SuppressWarnings("deprecation")
		String earningsString = new Double(earnings).toString();
		
		earningsDataLbl = new JLabel(earningsString + " EURO");
		earningsDataLbl.setBounds(270, 20, 250, 20);
		contentPanel.add(earningsDataLbl);
		
		@SuppressWarnings("deprecation")
		String numberOfOrdersString = new Integer(numberOfOrders).toString();
		
		numberOfOrdersDataLbl = new JLabel(numberOfOrdersString);
		numberOfOrdersDataLbl.setBounds(270, 60, 250, 20);
		contentPanel.add(numberOfOrdersDataLbl);
			
		servicesWithEarningsBox = new JList();
		servicesWithEarningsBox.setVisible(true);
		servicesWithEarningsBox.setBounds(270, 100, 250, 100);
        contentPanel.add(servicesWithEarningsBox);
        
        int i = 0;
        DefaultListModel listModel = new DefaultListModel();
        for(String service: services) {
        	@SuppressWarnings("deprecation")
			String numberString = new Integer(numberOfServicesPerType.get(i)).toString();
        	String earningString = new Double(earningsPerService.get(i)).toString();
        	listModel.addElement("" + numberString + " x " + service + " - " + earningString + "EURO");
        	servicesWithEarningsBox.setModel(listModel);
        	i++;
        }
        
        //servicesWithQuantityBox
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
}
