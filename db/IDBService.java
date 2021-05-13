package db;

import model.Service;

import java.util.ArrayList;

public interface IDBService {
    ArrayList<Service> retrieveAllServices() throws DBException;
}
