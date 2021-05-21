package db;

import model.Service;

import java.util.ArrayList;

public interface IDBService {
    ArrayList<Service> retrieveAllServices() throws DBException;

    Service retrieveServiceByName(String serviceName) throws DBException;

    boolean updateService(String oldName, Service service) throws DBException;

    boolean saveService(Service service) throws DBException;

    boolean deleteServiceByName(String serviceName) throws DBException;

}
