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
            DBConnection.getInstance().startTransaction();

            PreparedStatement stmt = con.prepareStatement(select);
            stmt.setQueryTimeout(5);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                int id =  rs.getInt("id");
                String name =  rs.getString("name");
                String description =  rs.getString("description");
                double price =  rs.getDouble("price");
               Service service =  new Service(id, name, description, price);
               services.add(service);
            }

            if(services.isEmpty())
                throw new DBException("There are no services available");

            DBConnection.getInstance().commitTransaction();
            stmt.close();
        } catch (DBException ex) {
            throw ex;
        } catch (SQLException ex) {
            try {
                DBConnection.getInstance().rollbackTransaction();
            } catch (SQLException e) {
                throw new DBException("Error: Transaction roll back problem while canceling transaction");
            }

            DBException de = new DBException("Error retrieving data");
            de.setStackTrace(ex.getStackTrace());
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

        String select = "insert into Services (id, name, description, price) values (?, ?, ?, ?)";

        try {
            DBConnection.getInstance().startTransaction();

            PreparedStatement stmt = con.prepareStatement(select);
            stmt.setInt(1, service.getID());
            stmt.setString(2, service.getName());
            stmt.setString(3, service.getDescription());
            stmt.setDouble(4, service.getPrice());
            stmt.setQueryTimeout(5);
            stmt.executeUpdate();

            DBConnection.getInstance().commitTransaction();
            stmt.close();
        } catch (SQLException ex) {
            try {
                DBConnection.getInstance().rollbackTransaction();
            } catch (SQLException e) {
                throw new DBException("Error: Transaction roll back problem while canceling transaction");
            }

            DBException de = null;
            if(ex.getMessage().startsWith("Violation of PRIMARY KEY"))
                de = new DBException("Error: Service with this id already exists");
            else
                de = new DBException("Error saving data");
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
    public Service retrieveServiceByID(int serviceID) throws DBException {
        Connection con = DBConnection.getInstance().getDBcon();
        Service service;

        String select = "select * from Services where id = ?";

        try {
            DBConnection.getInstance().startTransaction();

            PreparedStatement stmt = con.prepareStatement(select);
            stmt.setInt(1, serviceID);
            stmt.setQueryTimeout(5);
            ResultSet rs = stmt.executeQuery();

            if(!rs.next())
                throw new DBException("Error: Service with this id does not exist");

            String serviceName =  rs.getString("name");
            String serviceDescription =  rs.getString("description");
            double servicePrice =  rs.getDouble("price");
            service =  new Service(serviceID, serviceName, serviceDescription, servicePrice);

            DBConnection.getInstance().commitTransaction();
            stmt.close();

        } catch (DBException ex) {
            throw ex;
        }catch (SQLException ex) {
            try {
                DBConnection.getInstance().rollbackTransaction();
            } catch (SQLException e) {
                throw new DBException("Error: Transaction roll back problem while canceling transaction");
            }

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
    public boolean updateService(int oldServiceID, Service service) throws DBException {
        Connection con = DBConnection.getInstance().getDBcon();

        String select = "update Services set id=?, name=?, description=?, price=? where id=?";

        try {
            DBConnection.getInstance().startTransaction();

            PreparedStatement stmt = con.prepareStatement(select);
            stmt.setInt(1, service.getID());
            stmt.setString(2, service.getName());
            stmt.setString(3, service.getDescription());
            stmt.setDouble(4, service.getPrice());
            stmt.setInt(5, oldServiceID);
            stmt.setQueryTimeout(5);
            if(stmt.executeUpdate() == 0)
                throw new DBException("Error: Service with with this id does not exist");

            DBConnection.getInstance().commitTransaction();
            stmt.close();
        } catch (DBException ex) {
            throw ex;
        }catch (SQLException ex) {
            try {
                DBConnection.getInstance().rollbackTransaction();
            } catch (SQLException e) {
                throw new DBException("Error: Transaction roll back problem while canceling transaction");
            }

            DBException de = null;
            if(ex.getMessage().startsWith("Violation of PRIMARY KEY"))
                de = new DBException("Error: Service with given new id already exists");
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
    public boolean deleteServiceByID(int serviceID) throws DBException {
        Connection con = DBConnection.getInstance().getDBcon();

        String select = "delete from Services where id = ?";

        try {
            DBConnection.getInstance().startTransaction();

            PreparedStatement stmt = con.prepareStatement(select);
            stmt.setInt(1, serviceID);
            stmt.setQueryTimeout(5);
            if(stmt.executeUpdate() == 0)
                throw new DBException("Service with this id does not exist");

            DBConnection.getInstance().commitTransaction();
            stmt.close();
        } catch (DBException ex) {
            throw ex;
        }catch (SQLException ex) {
            try {
                DBConnection.getInstance().rollbackTransaction();
            } catch (SQLException e) {
                throw new DBException("Error: Transaction roll back problem while canceling transaction");
            }

            DBException de = null;
            if(ex.getMessage().startsWith("The DELETE statement conflicted with the REFERENCE"))
                de = new DBException("Error: You can not delete service which is in an order");
            else
                de = new DBException("Error deleting data");
            de.setStackTrace(ex.getStackTrace());
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
