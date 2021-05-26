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

    public Service getServiceByIDFromDB(int serviceID) throws DBException {
        return dbService.retrieveServiceByID(serviceID);
    }

    public boolean updateServiceWithUserInputInDB(Service service, int newID, String newServiceName, String newServiceDescription, double newServicePrice) throws DBException {
        int oldID = service.getID();
        service.setID(newID);
        service.setName(newServiceName);
        service.setDescription(newServiceDescription);
        service.setPrice(newServicePrice);
        return dbService.updateService(oldID, service);
    }

    public boolean saveServiceWithUserInputInDB(int serviceID , String serviceName, String serviceDescription, double servicePrice) throws DBException {
        Service service =  new Service(serviceID, serviceName, serviceDescription,servicePrice);
        return dbService.saveService(service);
    }

    public boolean deleteServiceByIDFromDB(int serviceID) throws DBException {
        return dbService.deleteServiceByID(serviceID);
    }
}
