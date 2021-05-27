package controller;

import model.OrderLine;
import model.Service;

public class OrderLineController {

    public OrderLineController(){}

    public OrderLine createOrderLine(Service service,int quantity){
        return new OrderLine(service, quantity);
    }
}
