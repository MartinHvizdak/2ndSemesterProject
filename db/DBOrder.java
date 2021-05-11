package db;

//Eventually will change to model.Order and model.Customer
import model.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.ResultSet;
import java.util.ArrayList;


public class DBOrder implements IDBOrder{

	@Override
	public void saveOrder(Order order) throws DBException {
		Connection con = DBConnection.getInstance().getDBcon();
		
		String insert1 = "insert into Order_lines (order_id, service_name, quantity) values (?, ?, ?)";
		String insert2 = "insert into Orders (id, customer_email, payday) values (?, ?, ?)";
		
		try {
			
			PreparedStatement stmt = con.prepareStatement(insert1);
			
			// Needs to be replaced with getters
			stmt.setInt(1, 0);
			stmt.setString(2, "null");
			stmt.setDouble(3, 0.0);
			stmt.setQueryTimeout(5);
			stmt.execute();
			
			// This will include a for statement
			
			stmt = con.prepareStatement(insert2);
			stmt.setInt(1, 0);
			stmt.setString(2, "null");
			stmt.setDate(3, null);
			stmt.setQueryTimeout(5);
			stmt.execute();
			
			stmt.close();
			
		} catch (SQLException ex) {
			DBException de = new DBException("Error inserting data");
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
	}
	
	@Override
	public Order retrieveOrderByID(int id) throws DBException {
		Connection con = DBConnection.getInstance().getDBcon();
		String select1 = "select customer_email, payday from from Orders where id=?";
		String select2 = "select city, street, street_number, zip_code, name from Customers where email=?";
		String select3 = "select service_name, quantity from Order_lines where order_id=?";
		String select4 = "select name, description, price from Services where name=?";
		Order order;
		
		try {
			
			PreparedStatement stmt = con.prepareStatement(select1);
			
			stmt.setInt(1, id);
			stmt.setQueryTimeout(5);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			
			String customer_email = rs.getString("customer_email");
			Date pd = rs.getDate("payday");
			LocalDate payday = pd.toLocalDate();

			con.prepareStatement(select2);
			stmt.setString(1, customer_email);
			stmt.setQueryTimeout(5);
			rs = stmt.executeQuery();
			rs.next();
			
			rs.getString("city");
			String street = rs.getString("street");
			String street_number = rs.getString("street_number");
			String zipCode = rs.getString("zip_code");
			String name = rs.getString("name");
			int phone_number = rs.getInt("phone_number");
			
			Customer customer = new Customer(name, street, street_number, zipCode, customer_email, phone_number);
			
			
			stmt = con.prepareStatement(select3);
			stmt.setInt(1, id);
			stmt.setQueryTimeout(5);
			rs = stmt.executeQuery();
			
			ArrayList<OrderLine> orderLines = new ArrayList<OrderLine>();
			PreparedStatement stmtService = con.prepareStatement(select4);
			Service service;
			OrderLine orderLine;
			while(rs.next()) {
				
				String serviceName = rs.getString("service_name"); 
				int quantity = rs.getInt("quantity");
				
				stmtService.setString(1, serviceName);
				stmtService.setQueryTimeout(5);
				stmtService.executeQuery();
				
				String description = rs.getString("description");
				double price = rs.getDouble("price");
				
				service = new Service(serviceName, description, price); 
				orderLine = new OrderLine(service, quantity);
				
				orderLines.add(orderLine);
			}
		
			order = new Order(customer, orderLines, payday, id);
			
		} catch (SQLException ex) {
			DBException de = new DBException("Error inserting data");
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
		
		return order;
	}

	@Override
	public void deleteOrderByID(int id) throws DBException {
		// TODO Auto-generated method stub
		Connection con = DBConnection.getInstance().getDBcon();
		
		String delete = "delete from Orders where id=?";
		
		try {
			
			PreparedStatement stmt = con.prepareStatement(delete);
			stmt.setInt(1, id);
			stmt.setQueryTimeout(5);
			stmt.execute();
			
			stmt.close();
			
		} catch (SQLException ex) {
			DBException de = new DBException("Error inserting data");
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
		
		
	}
}
