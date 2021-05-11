package db;

import model.Service;

import java.util.ArrayList;

public interface IDBService {
    public ArrayList<Service> retrieveAllServices() throws DBException;
}
