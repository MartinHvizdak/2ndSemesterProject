package db;

import model.OrderLine;
import model.Service;

import java.sql.*;
import java.util.ArrayList;

public class DBService implements IDBService{

    public ArrayList<Service> retrieveAllServices() throws DBException {
        Connection con = DBConnection.getInstance().getDBcon();

        String select = "select * from Services";

        ArrayList<Service> services =  new ArrayList<>();
        try {

            PreparedStatement stmt = con.prepareStatement(select);
            stmt = con.prepareStatement(select);
            stmt.setQueryTimeout(5);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                String name =  rs.getString("name");
                String description =  rs.getString("description");
                double price =  rs.getDouble("price");
               Service service =  new Service(name, description, price);
               services.add(service);
            }

            stmt.close();

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
            DBException de = new DBException("Data not inserted! Technical error");
            de.setStackTrace(ex.getStackTrace());
            throw de;
        } finally {
            DBConnection.closeConnection();
        }
        return services;
    }
}
