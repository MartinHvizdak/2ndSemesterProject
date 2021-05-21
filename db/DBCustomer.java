package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import model.*;

public class DBCustomer implements IDBCustomer {

	@Override
	public String getCustomerType(String customerEmail) throws DBException {
		Connection con = DBConnection.getInstance().getDBcon();
		String customerType = "";

		String select = "select customer_type from Customers where Customers.email = ?";
		try{
			PreparedStatement stmt = con.prepareStatement(select);
			stmt.setString(1, customerEmail);
			stmt.setQueryTimeout(5);
			ResultSet rs = stmt.executeQuery();

			if(!rs.next())
				throw new DBException("Customer with this email does not exist");

			customerType = rs.getString("customer_type");

			stmt.close();
		} catch (DBException ex) {
			throw ex;
		} catch (SQLException ex) {
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
		return customerType;
	}

	@Override
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
				throw new DBException("Customer with this email does not exist");

			String customerCity = rs.getString("city");
			String customerStreet = rs.getString("street");
			String customerStreetNumber = rs.getString("street_number");
			String customerName = rs.getString("name");
			String customerPhoneNumber = rs.getString("phone_number");
			String customerZipCode = rs.getString("zip_code");

			customer = new Customer(customerEmail, customerName, customerPhoneNumber, customerCity, customerZipCode, customerStreet, customerStreetNumber);

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
				throw new DBException("Customer with this email does not exist");

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
			if(!rs.next())
				throw new DBException("LTD with this email does not exist");

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
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("second_name");
				double salary = rs.getDouble("salary");
				double generatedIncome = rs.getDouble("generated_income");

				CustomerEmployee customerEmployee = new CustomerEmployee(employeeID, firstName, lastName, salary, generatedIncome);
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
			ex.printStackTrace();
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
				throw new DBException("Private individual with this email does not exist");

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
	public SelfEmployed retrieveSelfEmployedByEmail(String customerEmail) throws DBException {
		Connection con = DBConnection.getInstance().getDBcon();
		SelfEmployed selfEmployed = null;

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
				throw new DBException("Self employed with this email does not exist");

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
					new SelfEmployed(customerEmail, customerName, customerPhoneNumber, customerCity,
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

	@Override
	public boolean savePrivateIndividualWithUserInputInDB(PrivateIndividual privateIndividual) throws DBException {

		Connection con = DBConnection.getInstance().getDBcon();

		String insert1 = "insert into Customers (email, city, street, street_number, name, phone_number, zip_code, customer_type) values (?, ?, ?, ?, ?, ?, ?, ?)";
		String insert2 = "insert into Private_individuals (customer_email, id, vat_identificator) values (?, ?, ?)";

		try {
			PreparedStatement stmt = con.prepareStatement(insert1);

			stmt.setString(1, privateIndividual.getEmail());
			stmt.setString(2, privateIndividual.getCity());
			stmt.setString(3, privateIndividual.getStreet());
			stmt.setString(4, privateIndividual.getStreetNumber());
			stmt.setString(5, privateIndividual.getName());
			stmt.setString(6, privateIndividual.getPhoneNumber());
			stmt.setString(7, privateIndividual.getZipCode());
			stmt.setString(8, "Private_individual");

			stmt.setQueryTimeout(5);
			stmt.execute();


			stmt = con.prepareStatement(insert2);

			stmt.setString(1, privateIndividual.getEmail());
			stmt.setString(2, privateIndividual.getId());
			stmt.setString(3, privateIndividual.getVat());

			stmt.setQueryTimeout(5);
			stmt.execute();
			stmt.close();

		} catch (SQLException ex) {
			DBException de = new DBException("Error inserting data");
			de.setStackTrace(ex.getStackTrace());
			throw de;
		} catch (NullPointerException ex) {
			DBException de = new DBException("Null pointer exception - possibly Connection object");
			de.setStackTrace(ex.getStackTrace());
			ex.printStackTrace();
			throw de;
		} catch (Exception ex) {
			DBException de = new DBException("Data not inserted! Technical error");
			de.setStackTrace(ex.getStackTrace());
			throw de;
		} finally {
			DBConnection.closeConnection();
		}

		return true;

	}

	@Override
	public boolean saveSelfEmployedWithUserInputInDB(SelfEmployed selfEmployed) throws DBException {
		// TODO Auto-generated method stub

		Connection con = DBConnection.getInstance().getDBcon();

		String insert1 = "insert into Customers (email, city, street, street_number, name, phone_number, zip_code, customer_type) values (?, ?, ?, ?, ?, ?, ?, ?)";
		String insert2 = "insert into Self_employed (customer_email, market_number, vat_identificator) values (?, ?, ?)";

		try {
			PreparedStatement stmt = con.prepareStatement(insert1);

			stmt.setString(1, selfEmployed.getEmail());
			stmt.setString(2, selfEmployed.getCity());
			stmt.setString(3, selfEmployed.getStreet());
			stmt.setString(4, selfEmployed.getStreetNumber());
			stmt.setString(5, selfEmployed.getName());
			stmt.setString(6, selfEmployed.getPhoneNumber());
			stmt.setString(7, selfEmployed.getZipCode());
			stmt.setString(8, "Self_employed");

			stmt.setQueryTimeout(5);
			stmt.execute();



			stmt = con.prepareStatement(insert2);

			stmt.setString(1, selfEmployed.getEmail());
			stmt.setString(2, selfEmployed.getMarketNumber());
			stmt.setString(3, selfEmployed.getVat());

			stmt.setQueryTimeout(5);
			stmt.execute();
			stmt.close();

		} catch (SQLException ex) {
			DBException de = new DBException("Error inserting data");
			de.setStackTrace(ex.getStackTrace());
			throw de;
		} catch (NullPointerException ex) {
			DBException de = new DBException("Null pointer exception - possibly Connection object");
			de.setStackTrace(ex.getStackTrace());
			ex.printStackTrace();
			throw de;
		} catch (Exception ex) {
			DBException de = new DBException("Data not inserted! Technical error");
			de.setStackTrace(ex.getStackTrace());
			throw de;
		} finally {
			DBConnection.closeConnection();
		}

		return true;

	}

	@Override
	public boolean saveLTDUserInputInDB(LTD ltd) throws DBException {
		// TODO Auto-generated method stub


		Connection con = DBConnection.getInstance().getDBcon();

		String insert1 = "insert into Customers (email, city, street, street_number, name, phone_number, zip_code, customer_type) values (?, ?, ?, ?, ?, ?, ?, ?)";

		String insert2 = "insert into LTD_employees (id, first_name, second_name, salary, generated_income, LTD_email) values (?, ?, ?, ?, ?, ?)";
		String insert3 = "insert into LTD_owners (id, first_name, second_name, relation) values (?, ?, ?, ?)";
		String insert4 = "insert into LTDs (customer_email, market_registration_number, market_number, are_payers) values (?, ?, ?, ?)";

		try {
			PreparedStatement stmt = con.prepareStatement(insert1);

			stmt.setString(1, ltd.getEmail());
			stmt.setString(2, ltd.getCity());
			stmt.setString(3, ltd.getStreet());
			stmt.setString(4, ltd.getStreetNumber());
			stmt.setString(5, ltd.getName());
			stmt.setString(6, ltd.getPhoneNumber());
			stmt.setString(7, ltd.getZipCode());
			stmt.setString(8, "LTD");

			stmt.setQueryTimeout(5);
			stmt.execute();

			stmt = con.prepareStatement(insert4);

			stmt.setString(1, ltd.getEmail());
			stmt.setString(2, ltd.getMarketRegistrationNumber());
			stmt.setString(3, ltd.getMarketNumber());
			stmt.setBoolean(4, ltd.arePayers());
			stmt.setQueryTimeout(5);
			stmt.execute();


			for(CustomerEmployee customerEmployee : ltd.getEmployees()) {
				stmt = con.prepareStatement(insert2);

				stmt.setString(1, customerEmployee.getId());
				stmt.setString(2, customerEmployee.getFirstName());
				stmt.setString(3, customerEmployee.getSecondName());
				stmt.setDouble(4, customerEmployee.getSalary());
				stmt.setDouble(5, customerEmployee.getIncome());

				stmt.setString(6, ltd.getEmail());
				stmt.setQueryTimeout(5);
				stmt.execute();
			}

			for(Owner owner : ltd.getOwners()) {
				stmt = con.prepareStatement(insert3);

				stmt.setString(1, owner.getId());
				stmt.setString(2, owner.getName());
				stmt.setString(3, owner.getName());
				stmt.setString(4, owner.getRelation());

				stmt.setQueryTimeout(5);
				stmt.execute();
			}

			stmt.close();

		} catch (SQLException ex) {
			DBException de = new DBException("Error inserting data");
			de.setStackTrace(ex.getStackTrace());
			throw de;
		} catch (NullPointerException ex) {
			DBException de = new DBException("Null pointer exception - possibly Connection object");
			de.setStackTrace(ex.getStackTrace());
			ex.printStackTrace();
			throw de;
		} catch (Exception ex) {
			DBException de = new DBException("Data not inserted! Technical error");
			de.setStackTrace(ex.getStackTrace());
			throw de;
		} finally {
			DBConnection.closeConnection();
		}

		return true;
	}
}
