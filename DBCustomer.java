package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import model.Customer;

public class DBCustomer implements IDBCustomer {

	public Customer retrieveCustomerByEmail(String customerEmail) throws DBException {
		Customer customer = null;
		Connection con = DBConnection.getInstance().getDBcon();
		
		String select1 = "select city, street, street_number, name, phone_number from Customers WHERE email = ?";
		String select2 = "select City from Cities WHERE zip_code = ?";
		System.out.println(select1);
		System.out.println(select2);
		
		try {
			PreparedStatement stmt = con.prepareStatement(select1);
			String zipcode;
			customer = new Customer();
			
			stmt.setString(1, customerEmail);
			stmt.setQueryTimeout(5);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			
			customer.setName(rs.getString("name"));
			customer.setPhoneNumber(rs.getString("phone_number"));
			customer.setZipCode(rs.getString("zip_code"));
			customer.setStreet(rs.getString("street"));
			customer.setStreetNumber(rs.getString("street_number"));

			zipcode = rs.getString("zip_code");
			
			stmt = con.prepareStatement(select2);
			stmt.setString(1, zipcode);
			stmt.setQueryTimeout(5);
			rs = stmt.executeQuery();
			rs.next();
			customer.setCity(rs.getString("city"));
			
			stmt.close();
		} catch (SQLException ex) {
			customer = null;
			DBException de = new DBException("Error retrieving data");
			de.setStackTrace(ex.getStackTrace());
			throw de;
		} catch (NullPointerException ex) {
			customer = null;
			DBException de = new DBException("Null pointer exception - possibly Connection object");
			de.setStackTrace(ex.getStackTrace());
			throw de;
		} catch (Exception ex) {
			customer = null;
			DBException de = new DBException("Data not retrieved! Technical error");
			de.setStackTrace(ex.getStackTrace());
			throw de;
		} finally {
			DBConnection.closeConnection();
		}
				
		return customer;
		
	}
}
