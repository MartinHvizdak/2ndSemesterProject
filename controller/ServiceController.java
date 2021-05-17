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

    public String getDescription(Service service){
        return service.getDescription();
    }

    public double getPrice(Service service){
        return service.getPrice();
    }

    public ArrayList<Service> getAllServicesFromDB() throws DBException {
        return dbService.retrieveAllServices();
    }

    public Service getServiceByNameFromDB(String serviceName) throws DBException {
        return dbService.retrieveServiceByName(serviceName);
    }

    public boolean updateServiceWithUserInputInDB(Service service, String newServiceName, String newServiceDescription, double newServicePrice) throws DBException {
        String oldName = service.getName();
        service.setName(newServiceName);
        service.setDescription(newServiceDescription);
        service.setPrice(newServicePrice);
        return dbService.updateService(oldName, service);
    }

    public boolean saveServiceWithUserInputInDB(String serviceName, String serviceDescription, double servicePrice) throws DBException {
        Service service =  new Service(serviceName, serviceDescription,servicePrice);
        return dbService.saveService(service);
    }

    public boolean deleteServiceByNameFromDB(String serviceName) throws DBException {
        return dbService.deleteServiceByName(serviceName);
    }
}
