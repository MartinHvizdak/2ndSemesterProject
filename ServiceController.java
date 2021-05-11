package controller;

import java.util.ArrayList;

import db.DBException;
import db.DBService;
import model.Service;

public class ServiceController {
    DBService dbService;

    public ServiceController() {
        dbService = new DBService();
    }

    public String getName(Service service){
        return service.getName();
    }

    public ArrayList<Service> getAllServicesFromDB() throws DBException {
        return dbService.retrieveAllServices();
    }
}
