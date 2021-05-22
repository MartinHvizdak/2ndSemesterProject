package controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import db.DBCustomer;
import db.DBException;
import db.DBOrder;
import model.Customer;
import model.Order;
import model.OrderLine;
import model.Service;



public class CreateSummaryController {
	
	DBCustomer dbCustomer;
	DBOrder dbOrder;
	Customer customer;
	List<Order> orders;
	Service service;
	List<String> services;
	ArrayList<OrderLine> orderLines;
	List<Double> earningsPerService;
	double earnings;
	int numberOfServices;
	List<Integer> servicesPerType;
	
	public CreateSummaryController() {
		
		dbCustomer = new DBCustomer();
		dbOrder = new DBOrder();
		
	}
	
	public Customer showCustomerInfo(String email) throws DBException{
		return dbCustomer.retrieveCustomerByEmail(email);
	}
	
	public void setInformation(String email, LocalDate startDate, LocalDate endDate) throws DBException{
		this.orders = dbOrder.retrieveOrdersByEmailAndPeriod(email, startDate, endDate);
	}
	
	public double getEarnings() {
		earnings = 0;
		for(Order order: orders) {
			orderLines = order.getOrderLines();
			for(OrderLine orderLine: orderLines) {
				service = orderLine.getService();
				earnings += service.getPrice() * orderLine.getQuantity();
			}
		}
		return earnings;
	}
	
	public int getNumberOfOrders() {
		return orders.size();
	}
	
	public List<String> getServices(){
		services = new ArrayList<>();
		for(Order order: orders) {
			orderLines = order.getOrderLines();
			for(OrderLine orderLine: orderLines) {
				service = orderLine.getService();
				if(!services.contains(service.getName())) {
					services.add(service.getName());
				}
			}
		}
		return services;
	}
	
	public List<Integer> getNumberOfServicesPerType(){
		servicesPerType = new ArrayList<>();
		for(String service: services) {
			numberOfServices = 0;
			for(Order order: orders) {
				orderLines = order.getOrderLines();
				for(OrderLine orderLine: orderLines) {
					//using 2 types of services - 1 is String and the other one is Service
					this.service = orderLine.getService();
					if(this.service.getName().equals(service)) {
						numberOfServices += orderLine.getQuantity();
					}
				}
			}
			servicesPerType.add(numberOfServices);
		}
		return servicesPerType;
		
	}
	
	
	public List<Double> getEarningsPerService(){
		earningsPerService = new ArrayList<>();
		for(String service: services) {
			earnings = 0;
			for(Order order: orders) {
				orderLines = order.getOrderLines();
				for(OrderLine orderLine: orderLines) {
					//using 2 types of services - 1 is String and the other one is Service
					this.service = orderLine.getService();
					if(this.service.getName().equals(service)) {
						earnings += this.service.getPrice() * orderLine.getQuantity();
					}
				}
			}
			earningsPerService.add(earnings);
		}
		return earningsPerService;
		
	}
	
	public void createSummaryFile() {
		// yet to be implemented
	}
	
	
}
