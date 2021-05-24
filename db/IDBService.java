package db;

import model.Service;

import java.util.ArrayList;

public interface IDBService {
    ArrayList<Service> retrieveAllServices() throws DBException;

    Service retrieveServiceByID(int serviceID) throws DBException;

    boolean updateService(int oldServiceID, Service service) throws DBException;

    boolean saveService(Service service) throws DBException;

    boolean deleteServiceByID(int serviceID) throws DBException;

}
