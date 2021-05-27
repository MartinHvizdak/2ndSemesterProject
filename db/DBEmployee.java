package db;

import model.Employee;
import model.LTD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBEmployee implements IDBCustomerEmployee{
    @Override
    public boolean saveEmployee(Employee employee, LTD ltd) throws DBException {
        Connection con = DBConnection.getInstance().getDBcon();

        String select = "insert into LTD_employees (id, first_name, second_name, salary, generated_income, LTD_email) values (?, ?, ?, ?, ?, ?)";

        try {
            DBConnection.getInstance().startTransaction();

            String email = null;
            if (ltd != null){
                email =  ltd.getEmail();
            }

            PreparedStatement stmt = con.prepareStatement(select);
            stmt.setString(1, employee.getId());
            stmt.setString(2, employee.getFirstName());
            stmt.setString(3, employee.getSurName());
            stmt.setDouble(4, employee.getSalary());
            stmt.setDouble(5, employee.getIncome());
            stmt.setString(6, email);
            stmt.setQueryTimeout(5);
            stmt.executeUpdate();

            stmt.close();

            DBConnection.getInstance().commitTransaction();
        } catch (SQLException ex) {
            try {
                DBConnection.getInstance().rollbackTransaction();
            } catch (SQLException e) {
                throw new DBException("Error: Transaction roll back problem while canceling transaction");
            }

            DBException de = null;
            if(ex.getMessage().startsWith("Violation of PRIMARY KEY"))
                de = new DBException("Error: Employee with this id already exists");
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
        return false;
    }

    @Override
    public ArrayList<Employee> retrieveAllEmployees() throws DBException{
        Connection con = DBConnection.getInstance().getDBcon();
        ArrayList<Employee> employees =  new ArrayList<>();

        String select = "select * from LTD_employees";

        try {
            DBConnection.getInstance().startTransaction();

            PreparedStatement stmt = con.prepareStatement(select);
            stmt.setQueryTimeout(5);
            ResultSet rs = stmt.executeQuery();


            while (rs.next()) {
                String employeePersonalID = rs.getString("id");
                String employeeFirstName = rs.getString("first_name");
                String employeeSecondName = rs.getString("second_name");
                double employeeSalary = rs.getDouble("salary");
                double employeeGeneratedIncome = rs.getDouble("generated_income");
                employees.add(new Employee(employeePersonalID, employeeFirstName, employeeSecondName, employeeSalary, employeeGeneratedIncome));
            }

            if(employees.isEmpty())
                throw new DBException("There are not any employees in DB");

            stmt.close();

            DBConnection.getInstance().commitTransaction();
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
        return employees;
    }

    @Override
    public Employee retrieveEmployeeByID(String employeePersonalID) throws DBException {
        Connection con = DBConnection.getInstance().getDBcon();
        Employee employee;

        String select = "select * from LTD_employees where id = ?";

        try {
            DBConnection.getInstance().startTransaction();

            PreparedStatement stmt = con.prepareStatement(select);
            stmt.setString(1, employeePersonalID);
            stmt.setQueryTimeout(5);
            ResultSet rs = stmt.executeQuery();

            if(!rs.next())
                throw new DBException("Error: Employee with this id does not exist");

            String employeeFirstName =  rs.getString("first_name");
            String employeeSecondName =  rs.getString("second_name");
            double employeeSalary =  rs.getDouble("salary");
            double employeeGeneratedIncome =  rs.getDouble("generated_income");

            employee =  new Employee(employeePersonalID, employeeFirstName, employeeSecondName, employeeSalary, employeeGeneratedIncome);

            stmt.close();

            DBConnection.getInstance().commitTransaction();
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
        return employee;
    }

    @Override
    public String retrieveEmployeeLTDEmail(String employeePersonalID) throws DBException{
        Connection con = DBConnection.getInstance().getDBcon();
        String email;

        String select = "select * from LTD_employees where id=?";

        try {
            DBConnection.getInstance().startTransaction();

            PreparedStatement stmt = con.prepareStatement(select);
            stmt.setString(1, employeePersonalID);
            stmt.setQueryTimeout(5);
            ResultSet rs = stmt.executeQuery();

            if(!rs.next())
                throw new DBException("Error: Employee with this id does not exist");

            email =  rs.getString("ltd_email");

            stmt.close();

            DBConnection.getInstance().commitTransaction();
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
        return email;
    }

    @Override
    public boolean updateEmployee(String oldPersonalID, Employee employee, LTD ltd) throws DBException {
        Connection con = DBConnection.getInstance().getDBcon();

        String select = "update LTD_employees set id=?, first_name=?, second_name=?, salary=?, generated_income=?, LTD_email=? where id=?";

        try {
            DBConnection.getInstance().startTransaction();

            String email = null;
            if (ltd != null){
                email =  ltd.getEmail();
            }

            PreparedStatement stmt = con.prepareStatement(select);
            stmt.setString(1, employee.getId());
            stmt.setString(2, employee.getFirstName());
            stmt.setString(3, employee.getSurName());
            stmt.setDouble(4, employee.getSalary());
            stmt.setDouble(5, employee.getIncome());
            stmt.setString(6, email);
            stmt.setString(7, oldPersonalID);
            stmt.setQueryTimeout(5);
            if(stmt.executeUpdate() == 0)
                throw new DBException("Error: Employee with this id does not exist");
            stmt.close();

            DBConnection.getInstance().commitTransaction();
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
            DBConnection.getInstance().startTransaction();

            PreparedStatement stmt = con.prepareStatement(select);
            stmt.setString(1, employeePersonalID);
            stmt.setQueryTimeout(5);
            if(stmt.executeUpdate() == 0)
                throw new DBException("Error: Employee with this id does not exist");
            stmt.close();

            DBConnection.getInstance().commitTransaction();
        } catch (DBException ex) {
            throw ex;
        }catch (SQLException ex) {
            try {
                DBConnection.getInstance().rollbackTransaction();
            } catch (SQLException e) {
                throw new DBException("Error: Transaction roll back problem while canceling transaction");
            }

            DBException de = new DBException("Error deleting data");
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
        return false;
    }
}
