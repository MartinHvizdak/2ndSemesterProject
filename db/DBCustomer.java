package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import model.*;

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

			if(!rs.next())
				throw new DBException("Customer with this id does not exist");

			String customerCity = rs.getString("city");
			String customerStreet = rs.getString("street");
			String customerStreetNumber = rs.getString("street_number");
			String customerName = rs.getString("name");
			String customerPhoneNumber = rs.getString("phone_number");
			String customerZipCode = rs.getString("zip_code");

			stmt.close();
		} catch (DBException ex) {
			throw ex;
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

	@Override
	public LTD retrieveLTDByEmail(String customerEmail) throws DBException {
		Connection con = DBConnection.getInstance().getDBcon();
		LTD ltd = null;

		String select1 = "select *\n" +
				"from Customers \n" +
				"inner join Cities on Customers.zip_code=Cities.zip_code\n" +
				"where Customers.email = ?";

		String select2 = "Select *\n" +
				"from LTDs \n" +
				"where LTDs.customer_email = ?";

		String select3 = "Select *\n" +
				"from LTD_employees\n" +
				"where LTD_employees.LTD_email= ?";

		String select4 = "Select *\n" +
				"from LTD_ownerships\n" +
				"inner join LTD_owners on LTD_ownerships.owner_id = LTD_owners.id\n" +
				"where LTD_ownerships.LTD_email= ?";

		System.out.println(select1);

		try {
			PreparedStatement stmt = con.prepareStatement(select1);
			stmt.setString(1, customerEmail);
			stmt.setQueryTimeout(5);
			ResultSet rs = stmt.executeQuery();

			if(!rs.next())
				throw new DBException("Customer with this id does not exist");

			String customerCity = rs.getString("city");
			String customerStreet = rs.getString("street");
			String customerStreetNumber = rs.getString("street_number");
			String customerName = rs.getString("name");
			String customerPhoneNumber = rs.getString("phone_number");
			String customerZipCode = rs.getString("zip_code");


			stmt = con.prepareStatement(select2);
			stmt.setString(1, customerEmail);
			stmt.setQueryTimeout(5);
			rs = stmt.executeQuery();
			rs.next();

			String marketRegistrationNumber = rs.getString("market_registration_number");
			String marketNumber = rs.getString("market_number");
			boolean arePayers = rs.getBoolean("are_payers");


			stmt = con.prepareStatement(select3);
			stmt.setString(1, customerEmail);
			stmt.setQueryTimeout(5);
			rs = stmt.executeQuery();

			ArrayList<CustomerEmployee> employees = new ArrayList<>();
			while(rs.next()) {
				String employeeID = rs.getString("id");
				String name = rs.getString("first_name") + " " + rs.getString("second_name");
				double salary = rs.getDouble("salary");
				double generatedIncome = rs.getDouble("generated_income");

				CustomerEmployee customerEmployee = new CustomerEmployee(employeeID, name, salary, generatedIncome);
				employees.add(customerEmployee);
			}


			stmt = con.prepareStatement(select4);
			stmt.setString(1, customerEmail);
			stmt.setQueryTimeout(5);
			rs = stmt.executeQuery();

			ArrayList<Owner> owners = new ArrayList<>();
			while(rs.next()) {
				String ownerID = rs.getString("id");
				String name = rs.getString("first_name") + " " + rs.getString("second_name");
				String relation = rs.getString("relation");

				Owner owner = new Owner(ownerID, name,relation);
				owners.add(owner);
			}

				ltd =
					new LTD(customerEmail, customerName, customerPhoneNumber, customerCity,
							customerZipCode, customerStreet, customerStreetNumber, employees, owners,
							marketRegistrationNumber, marketNumber, arePayers);


			stmt.close();
		} catch (DBException ex) {
			throw ex;
		} catch (SQLException ex) {
			ltd = null;
			DBException de = new DBException("Error retrieving data");
			de.setStackTrace(ex.getStackTrace());
			throw de;
		} catch (NullPointerException ex) {
			ltd = null;
			DBException de = new DBException("Null pointer exception - possibly Connection object");
			de.setStackTrace(ex.getStackTrace());
			throw de;
		} catch (Exception ex) {
			ltd = null;
			DBException de = new DBException("Data not retrieved! Technical error");
			de.setStackTrace(ex.getStackTrace());
			throw de;
		} finally {
			DBConnection.closeConnection();
		}

		return ltd;

	}

	@Override
	public PrivateIndividual retrievePrivateIndividualByEmail(String customerEmail) throws DBException {
		Connection con = DBConnection.getInstance().getDBcon();
		PrivateIndividual privateIndividual = null;

		String select1 = "select *\n" +
				"from Customers \n" +
				"inner join Cities on Customers.zip_code=Cities.zip_code\n" +
				"where Customers.email = ?";

		String select2 = "Select *\n" +
				"from Private_individuals \n" +
				"where Private_individuals.customer_email = ?";


		try {
			PreparedStatement stmt = con.prepareStatement(select1);
			stmt.setString(1, customerEmail);
			stmt.setQueryTimeout(5);
			ResultSet rs = stmt.executeQuery();

			if(!rs.next())
				throw new DBException("Customer with this id does not exist");

			String customerCity = rs.getString("city");
			String customerStreet = rs.getString("street");
			String customerStreetNumber = rs.getString("street_number");
			String customerName = rs.getString("name");
			String customerPhoneNumber = rs.getString("phone_number");
			String customerZipCode = rs.getString("zip_code");


			stmt = con.prepareStatement(select2);
			stmt.setString(1, customerEmail);
			stmt.setQueryTimeout(5);
			rs = stmt.executeQuery();
			rs.next();

			String individualID = rs.getString("id");
			String individualVAT = rs.getString("vat_identificator");

			privateIndividual =
					new PrivateIndividual(customerEmail, customerName, customerPhoneNumber, customerCity,
							customerZipCode, customerStreet, customerStreetNumber, individualID, individualVAT);

			stmt.close();
		} catch (DBException ex) {
			throw ex;
		} catch (SQLException ex) {
			privateIndividual = null;
			DBException de = new DBException("Error retrieving data");
			de.setStackTrace(ex.getStackTrace());
			throw de;
		} catch (NullPointerException ex) {
			privateIndividual = null;
			DBException de = new DBException("Null pointer exception - possibly Connection object");
			de.setStackTrace(ex.getStackTrace());
			throw de;
		} catch (Exception ex) {
			privateIndividual = null;
			DBException de = new DBException("Data not retrieved! Technical error");
			de.setStackTrace(ex.getStackTrace());
			throw de;
		} finally {
			DBConnection.closeConnection();
		}

		return privateIndividual;

	}

	@Override
	public SelfEmployeed retrieveSelfEmployedByEmail(String customerEmail) throws DBException {
		Connection con = DBConnection.getInstance().getDBcon();
		SelfEmployeed selfEmployed = null;

		String select1 = "select *\n" +
				"from Customers \n" +
				"inner join Cities on Customers.zip_code=Cities.zip_code\n" +
				"where Customers.email = ?";

		String select2 = "Select *\n" +
				"from Self_employeed \n" +
				"where Self_employeed.customer_email = ?";


		try {
			PreparedStatement stmt = con.prepareStatement(select1);
			stmt.setString(1, customerEmail);
			stmt.setQueryTimeout(5);
			ResultSet rs = stmt.executeQuery();

			if(!rs.next())
				throw new DBException("Customer with this id does not exist");

			String customerCity = rs.getString("city");
			String customerStreet = rs.getString("street");
			String customerStreetNumber = rs.getString("street_number");
			String customerName = rs.getString("name");
			String customerPhoneNumber = rs.getString("phone_number");
			String customerZipCode = rs.getString("zip_code");


			stmt = con.prepareStatement(select2);
			stmt.setString(1, customerEmail);
			stmt.setQueryTimeout(5);
			rs = stmt.executeQuery();
			rs.next();

			String marketNumber = rs.getString("market_number");
			String vat = rs.getString("vat_identificator");

			selfEmployed =
					new SelfEmployeed(customerEmail, customerName, customerPhoneNumber, customerCity,
							customerZipCode, customerStreet, customerStreetNumber, marketNumber, vat);

			stmt.close();
		} catch (DBException ex) {
			throw ex;
		} catch (SQLException ex) {
			selfEmployed = null;
			DBException de = new DBException("Error retrieving data");
			de.setStackTrace(ex.getStackTrace());
			throw de;
		} catch (NullPointerException ex) {
			selfEmployed = null;
			DBException de = new DBException("Null pointer exception - possibly Connection object");
			de.setStackTrace(ex.getStackTrace());
			throw de;
		} catch (Exception ex) {
			selfEmployed = null;
			DBException de = new DBException("Data not retrieved! Technical error");
			de.setStackTrace(ex.getStackTrace());
			throw de;
		} finally {
			DBConnection.closeConnection();
		}

		return selfEmployed;

	}
}
