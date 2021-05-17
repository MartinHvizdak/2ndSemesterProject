package db;

import java.sql.*;
import java.util.ArrayList;

import model.Service;

public class DBService implements IDBService{

    @Override
    public ArrayList<Service> retrieveAllServices() throws DBException {
        Connection con = DBConnection.getInstance().getDBcon();

        String select = "select * from Services";

        ArrayList<Service> services =  new ArrayList<>();
        try {

            PreparedStatement stmt = con.prepareStatement(select);
            stmt.setQueryTimeout(5);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                String name =  rs.getString("name");
                String description =  rs.getString("description");
                double price =  rs.getDouble("price");
               Service service =  new Service(name, description, price);
               services.add(service);
            }

            if(services.isEmpty())
                throw new DBException("There are no services available");


            stmt.close();

        } catch (DBException ex) {
            throw ex;
        } catch (SQLException ex) {
            DBException de = new DBException("Error retrieving data");
            de.setStackTrace(ex.getStackTrace());
            ex.printStackTrace();
            throw de;
        } catch (NullPointerException ex) {
            DBException de = new DBException("Null pointer exception - possibly Connection object");
            de.setStackTrace(ex.getStackTrace());
            throw de;
        } catch (Exception ex) {
            DBException de = new DBException("Data not retrieved! Technical error");
            de.setStackTrace(ex.getStackTrace());
            throw de;
        } finally {
            DBConnection.closeConnection();
        }
        return services;
    }

    @Override
    public boolean saveService(Service service) throws DBException {
        Connection con = DBConnection.getInstance().getDBcon();

        String select = "insert into Services (name, description, price) values (?, ?, ?)";

        try {

            PreparedStatement stmt = con.prepareStatement(select);
            stmt.setString(1, service.getName());
            stmt.setString(2, service.getDescription());
            stmt.setDouble(3, service.getPrice());
            stmt.setQueryTimeout(5);
            stmt.executeUpdate();
            stmt.close();

        } catch (SQLException ex) {
            DBException de = null;
            if(ex.getMessage().startsWith("Violation of PRIMARY KEY"))
                de = new DBException("Error: Service with with this name already exists");
            else
                de = new DBException("Error retrieving data");
            de.setStackTrace(ex.getStackTrace());
            throw de;
        } catch (NullPointerException ex) {
            DBException de = new DBException("Null pointer exception - possibly Connection object");
            de.setStackTrace(ex.getStackTrace());
            throw de;
        } catch (Exception ex) {
            DBException de = new DBException("Data not saved! Technical error");
            de.setStackTrace(ex.getStackTrace());
            throw de;
        } finally {
            DBConnection.closeConnection();
        }
        return true;
    }

    @Override
    public Service retrieveServiceByName(String serviceName) throws DBException {
        Connection con = DBConnection.getInstance().getDBcon();
        Service service;

        String select = "select * from Services where name = ?";

        try {
            PreparedStatement stmt = con.prepareStatement(select);
            stmt.setString(1, serviceName);
            stmt.setQueryTimeout(5);
            ResultSet rs = stmt.executeQuery();

            if(!rs.next())
                throw new DBException("Error: Service with this name does not exist");

            String serviceDescription =  rs.getString("description");
            double servicePrice =  rs.getDouble("price");
            service =  new Service(serviceName,serviceDescription, servicePrice);

            stmt.close();

        } catch (DBException ex) {
            throw ex;
        }catch (SQLException ex) {
            DBException de = new DBException("Error retrieving data");
            de.setStackTrace(ex.getStackTrace());
            ex.printStackTrace();
            throw de;
        } catch (NullPointerException ex) {
            DBException de = new DBException("Null pointer exception - possibly Connection object");
            de.setStackTrace(ex.getStackTrace());
            throw de;
        } catch (Exception ex) {
            DBException de = new DBException("Data not retrieved! Technical error");
            de.setStackTrace(ex.getStackTrace());
            throw de;
        } finally {
            DBConnection.closeConnection();
        }
        return service;
    }

    @Override
    public boolean updateService(String oldName, Service service) throws DBException {
        Connection con = DBConnection.getInstance().getDBcon();

        String select = "update Services set name=?, description=?, price=? where name=?";

        try {
            PreparedStatement stmt = con.prepareStatement(select);
            stmt.setString(1, service.getName());
            stmt.setString(2, service.getDescription());
            stmt.setDouble(3, service.getPrice());
            stmt.setString(4, oldName);
            stmt.setQueryTimeout(5);
            if(stmt.executeUpdate() == 0)
                throw new DBException("Error: Service with with this name does not exist");
            stmt.close();

        } catch (DBException ex) {
            throw ex;
        }catch (SQLException ex) {
            DBException de = null;
            if(ex.getMessage().startsWith("Violation of PRIMARY KEY"))
                de = new DBException("Error: Service with with given new name already exists");
            else
                de = new DBException("Error updating data");
            de.setStackTrace(ex.getStackTrace());
            throw de;
        } catch (NullPointerException ex) {
            DBException de = new DBException("Null pointer exception - possibly Connection object");
            de.setStackTrace(ex.getStackTrace());
            throw de;
        } catch (Exception ex) {
            DBException de = new DBException("Data not updated! Technical error");
            de.setStackTrace(ex.getStackTrace());
            throw de;
        } finally {
            DBConnection.closeConnection();
        }
        return true;
    }

    @Override
    public boolean deleteServiceByName(String serviceName) throws DBException {
        Connection con = DBConnection.getInstance().getDBcon();

        String select = "delete * from Services where name = ?";

        try {
            PreparedStatement stmt = con.prepareStatement(select);
            stmt.setString(1, serviceName);
            stmt.setQueryTimeout(5);
            if(stmt.executeUpdate() == 0)
                throw new DBException("Service with with name does not exist");
            stmt.close();
        } catch (DBException ex) {
            throw ex;
        }catch (SQLException ex) {
            DBException de = new DBException("Error deleting data");
            de.setStackTrace(ex.getStackTrace());
            ex.printStackTrace();
            throw de;
        } catch (NullPointerException ex) {
            DBException de = new DBException("Null pointer exception - possibly Connection object");
            de.setStackTrace(ex.getStackTrace());
            throw de;
        } catch (Exception ex) {
            DBException de = new DBException("Data not deleted! Technical error");
            de.setStackTrace(ex.getStackTrace());
            throw de;
        } finally {
            DBConnection.closeConnection();
        }
        return true;
    }
}
