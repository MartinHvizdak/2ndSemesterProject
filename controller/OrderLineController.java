package controller;

import model.OrderLine;
import model.Service;

public class OrderLineController {

    public OrderLineController(){}

    public Service getService(OrderLine orderLine){
        return orderLine.getService();
    }

    public OrderLine createOrderLine(Service service,int quantity){
        return new OrderLine(service, quantity);
    }
}
