package db;

import model.CustomerEmployee;
import model.LTD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBCustomerEmployee implements IDBCustomerEmployee{
    @Override
    public boolean saveEmployee(CustomerEmployee employee, LTD ltd) throws DBException {
        Connection con = DBConnection.getInstance().getDBcon();

        String select = "insert into LTD_employees (id, first_name, second_name, salary, generated_income, LTD_email) values (?, ?, ?, ?, ?, ?)";

        try {
        	
            String firstName = employee.getFirstName();
            String secondName = employee.getSecondName();

            PreparedStatement stmt = con.prepareStatement(select);
            stmt.setString(1, employee.getId());
            stmt.setString(2, firstName);
            stmt.setString(3, secondName);
            stmt.setDouble(4, employee.getSalary());
            stmt.setDouble(5, employee.getIncome());
            stmt.setString(6, ltd.getEmail());
            stmt.setQueryTimeout(5);
            stmt.executeUpdate();

            stmt.close();

        } catch (SQLException ex) {
            DBException de = null;
            if(ex.getMessage().startsWith("Violation of PRIMARY KEY"))
                de = new DBException("Error: Employee with given new id already exists");
            else
                de = new DBException("Error retrieving data");
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
        return false;
    }

    @Override
    public CustomerEmployee retrieveEmployeeByID(String employeePersonalID) throws DBException {
        Connection con = DBConnection.getInstance().getDBcon();
        CustomerEmployee employee;

        String select = "select * from LTD_employees where id = ?";

        try {
            PreparedStatement stmt = con.prepareStatement(select);
            stmt.setString(1, employeePersonalID);
            stmt.setQueryTimeout(5);
            ResultSet rs = stmt.executeQuery();

            if(!rs.next())
                throw new DBException("Error: Employee with this id does not exist");

            String employeeFirstName =  rs.getString("first_name");
            String employeeSecondName = rs.getString("second_name");
            double employeeSalary =  rs.getDouble("salary");
            double employeeGeneratedIncome =  rs.getDouble("generated_income");

            employee =  new CustomerEmployee(employeePersonalID, employeeFirstName, employeeSecondName, employeeSalary, employeeGeneratedIncome);

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
            DBException de = new DBException("Data not updated! Technical error");
            de.setStackTrace(ex.getStackTrace());
            throw de;
        } finally {
            DBConnection.closeConnection();
        }
        return employee;
    }

    @Override
    public boolean updateEmployee(String oldPersonalID, CustomerEmployee employee, LTD ltd) throws DBException {
        Connection con = DBConnection.getInstance().getDBcon();

        String select = "update LTD_employees set id=?, first_name=?, second_name=?, salary=?, generated_income=?, LTD_email=? where id=?";

        try {
            PreparedStatement stmt = con.prepareStatement(select);
            String firstName = employee.getFirstName();
            String secondName = employee.getSecondName();

            stmt.setString(1, employee.getId());
            stmt.setString(2, firstName);
            stmt.setString(3, secondName);
            stmt.setDouble(4, employee.getSalary());
            stmt.setDouble(5, employee.getIncome());
            stmt.setString(6, ltd.getEmail());
            stmt.setString(7, oldPersonalID);
            stmt.setQueryTimeout(5);
            if(stmt.executeUpdate() == 0)
                throw new DBException("Error: Employee with this id does not exist");
            stmt.close();

        } catch (DBException ex) {
            throw ex;
        }catch (SQLException ex) {
            DBException de = null;
            if(ex.getMessage().startsWith("Violation of PRIMARY KEY"))
                de = new DBException("Error: Employee with given new id already exists");
            else
                de = new DBException("Error retrieving data");
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
        return false;
    }

    @Override
    public boolean deleteEmployeeByID(String employeePersonalID) throws DBException {
        Connection con = DBConnection.getInstance().getDBcon();

        String select = "delete from LTD_employees where id = ?";

        try {
            PreparedStatement stmt = con.prepareStatement(select);
            stmt.setString(1, employeePersonalID);
            stmt.setQueryTimeout(5);
            if(stmt.executeUpdate() == 0)
                throw new DBException("Error: Employee with this id does not exist");
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
        return false;
    }
}
