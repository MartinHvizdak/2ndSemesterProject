package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import model.Customer;

public class DBCustomer implements IDBCustomer {

	public Customer retrieveCustomerByEmail(String customerEmail) throws DBException {
		Connection con = DBConnection.getInstance().getDBcon();
		Customer customer = null;

		String select1 = "select *\n" +
				"from Customers \n" +
				"inner join Cities on Customers.zip_code=Cities.zip_code\n" +
				"where Customers.email = ?";

		System.out.println(select1);

		try {
			PreparedStatement stmt = con.prepareStatement(select1);
			stmt.setString(1, customerEmail);
			stmt.setQueryTimeout(5);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			
			String name = rs.getString("name");
			String phoneNumber = rs.getString("phone_number");
			String street = rs.getString("street");
			String streetNumber = rs.getString("street_number");
			String zipCode = rs.getString("zip_code");
			String email = rs.getString("email");
			String city = rs.getString("city");

			customer =  new Customer(email, name, phoneNumber, city, zipCode, street, streetNumber);

			stmt.close();
		} catch (SQLException ex) {
			customer = null;
			DBException de = null;
			if(ex.getMessage().equals("The result set has no current row.")){
				de = new DBException("Customer with this email does not exist");
			}else {
				de = new DBException("Error retrieving data");
			}
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
