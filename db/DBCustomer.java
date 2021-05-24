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
			DBConnection.getInstance().startTransaction();

			PreparedStatement stmt = con.prepareStatement(select);
			stmt.setString(1, customerEmail);
			stmt.setQueryTimeout(5);
			ResultSet rs = stmt.executeQuery();

			if(!rs.next())
				throw new DBException("Customer with this email does not exist");

			customerType = rs.getString("customer_type");

			stmt.close();

			DBConnection.getInstance().commitTransaction();
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
		return customerType;
	}

	@Override
	public ArrayList<Customer> retrieveAllCustomers() throws DBException {
		Connection con = DBConnection.getInstance().getDBcon();
		ArrayList<Customer> customers = new ArrayList<>();

		String select = "select *\n" +
				"from Customers \n" +
				"inner join Cities on Customers.zip_code=Cities.zip_code";

		try {
			DBConnection.getInstance().startTransaction();

			PreparedStatement stmt = con.prepareStatement(select);
			stmt.setQueryTimeout(5);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				String customerEmail = rs.getString("email");
				String customerCity = rs.getString("city");
				String customerStreet = rs.getString("street");
				String customerStreetNumber = rs.getString("street_number");
				String customerName = rs.getString("name");
				String customerPhoneNumber = rs.getString("phone_number");
				String customerZipCode = rs.getString("zip_code");

				customers.add(new Customer(customerEmail, customerName, customerPhoneNumber, customerCity, customerZipCode, customerStreet, customerStreetNumber));
			}

			if(customers.isEmpty())
				throw new DBException("There are not any customers in DB");

			stmt.close();

			DBConnection.getInstance().commitTransaction();
		} catch (DBException ex) {
			throw ex;
		} catch (SQLException ex) {
			try {
				DBConnection.getInstance().rollbackTransaction();
			} catch (SQLException e) {
				throw new DBException("Error: Transaction roll back problem while canceling transaction");
			}

			customers = null;
			DBException de = new DBException("Error retrieving data");
			de.setStackTrace(ex.getStackTrace());
			throw de;
		} catch (NullPointerException ex) {
			customers = null;
			DBException de = new DBException("Null pointer exception - possibly Connection object");
			de.setStackTrace(ex.getStackTrace());
			throw de;
		} catch (Exception ex) {
			customers = null;
			DBException de = new DBException("Data not retrieved! Technical error");
			de.setStackTrace(ex.getStackTrace());
			throw de;
		} finally {
			DBConnection.closeConnection();
		}

		return customers;

	}

	@Override
	public ArrayList<LTD> retrieveAllLTDs() throws DBException {
		Connection con = DBConnection.getInstance().getDBcon();
		ArrayList<LTD> ltds =  new ArrayList<>();

		String select1 = "select *\n" +
				"from Customers \n" +
				"inner join Cities on Customers.zip_code=Cities.zip_code\n" +
				"where Customers.customer_type = ?";

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

		try {
			DBConnection.getInstance().startTransaction();

			PreparedStatement stmt = con.prepareStatement(select1);
			stmt.setString(1, "LTD");
			stmt.setQueryTimeout(5);
			ResultSet rs1 = stmt.executeQuery();

			if(!rs1.next())
				throw new DBException("Customer with this email does not exist");

			while(rs1.next()) {
				String customerEmail = rs1.getString("email");
				String customerCity = rs1.getString("city");
				String customerStreet = rs1.getString("street");
				String customerStreetNumber = rs1.getString("street_number");
				String customerName = rs1.getString("name");
				String customerPhoneNumber = rs1.getString("phone_number");
				String customerZipCode = rs1.getString("zip_code");


				stmt = con.prepareStatement(select2);
				stmt.setString(1, customerEmail);
				stmt.setQueryTimeout(5);
				ResultSet rs2 = stmt.executeQuery();

				String marketRegistrationNumber = rs2.getString("market_registration_number");
				String marketNumber = rs2.getString("market_number");
				boolean arePayers = rs2.getBoolean("are_payers");


				stmt = con.prepareStatement(select3);
				stmt.setString(1, customerEmail);
				stmt.setQueryTimeout(5);
				rs2 = stmt.executeQuery();

				ArrayList<CustomerEmployee> employees = new ArrayList<>();
				while (rs2.next()) {
					String employeeID = rs2.getString("id");
					String firstName = rs2.getString("first_name");
					String lastName = rs2.getString("second_name");
					double salary = rs2.getDouble("salary");
					double generatedIncome = rs2.getDouble("generated_income");

					CustomerEmployee customerEmployee = new CustomerEmployee(employeeID, firstName, lastName, salary, generatedIncome);
					employees.add(customerEmployee);
				}


				stmt = con.prepareStatement(select4);
				stmt.setString(1, customerEmail);
				stmt.setQueryTimeout(5);
				rs2 = stmt.executeQuery();

				ArrayList<Owner> owners = new ArrayList<>();
				while (rs2.next()) {
					String ownerID = rs2.getString("id");
					String firstName = rs2.getString("first_name");
					String surname = rs2.getString("second_name");
					String relation = rs2.getString("relation");

					Owner owner = new Owner(ownerID, firstName, surname, relation);
					owners.add(owner);
				}

				ltds.add(new LTD(customerEmail, customerName, customerPhoneNumber, customerCity,
								customerZipCode, customerStreet, customerStreetNumber, employees, owners,
								marketRegistrationNumber, marketNumber, arePayers));

			}
			stmt.close();

			DBConnection.getInstance().commitTransaction();
		} catch (DBException ex) {
			throw ex;
		} catch (SQLException ex) {
			try {
				DBConnection.getInstance().rollbackTransaction();
			} catch (SQLException e) {
				throw new DBException("Error: Transaction roll back problem while canceling transaction");
			}

			ltds = null;
			DBException de = new DBException("Error retrieving data");
			de.setStackTrace(ex.getStackTrace());
			ex.printStackTrace();
			throw de;
		} catch (NullPointerException ex) {
			ltds = null;
			DBException de = new DBException("Null pointer exception - possibly Connection object");
			de.setStackTrace(ex.getStackTrace());
			throw de;
		} catch (Exception ex) {
			ltds = null;
			DBException de = new DBException("Data not retrieved! Technical error");
			de.setStackTrace(ex.getStackTrace());
			throw de;
		} finally {
			DBConnection.closeConnection();
		}

		return ltds;
	}

	@Override
	public ArrayList<String> retrieveAllLTDEmails() throws DBException {
		Connection con = DBConnection.getInstance().getDBcon();
		ArrayList<String> emails =  new ArrayList<>();

		String select = "Select (customer_email) from LTDs";


		try {
			DBConnection.getInstance().startTransaction();

			PreparedStatement stmt = con.prepareStatement(select);
			stmt.setQueryTimeout(5);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				emails.add(rs.getString("customer_email"));
			}
			stmt.close();

			if(emails.isEmpty()){
				throw new DBException("There are not any LTDs");
			}

			DBConnection.getInstance().commitTransaction();
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

		return emails;
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
			DBConnection.getInstance().startTransaction();

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

			DBConnection.getInstance().commitTransaction();
		} catch (DBException ex) {
			throw ex;
		} catch (SQLException ex) {
			try {
				DBConnection.getInstance().rollbackTransaction();
			} catch (SQLException e) {
				throw new DBException("Error: Transaction roll back problem while canceling transaction");
			}

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

		try {
			DBConnection.getInstance().startTransaction();

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
				String firstName = rs.getString("first_name");
				String surname = rs.getString("second_name");
				String relation = rs.getString("relation");

				Owner owner = new Owner(ownerID, firstName, surname,relation);
				owners.add(owner);
			}

				ltd =
					new LTD(customerEmail, customerName, customerPhoneNumber, customerCity,
							customerZipCode, customerStreet, customerStreetNumber, employees, owners,
							marketRegistrationNumber, marketNumber, arePayers);


			stmt.close();

			DBConnection.getInstance().commitTransaction();
		} catch (DBException ex) {
			throw ex;
		} catch (SQLException ex) {
			try {
				DBConnection.getInstance().rollbackTransaction();
			} catch (SQLException e) {
				throw new DBException("Error: Transaction roll back problem while canceling transaction");
			}

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
			DBConnection.getInstance().startTransaction();

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

			DBConnection.getInstance().commitTransaction();
		} catch (DBException ex) {
			throw ex;
		} catch (SQLException ex) {
			try {
				DBConnection.getInstance().rollbackTransaction();
			} catch (SQLException e) {
				throw new DBException("Error: Transaction roll back problem while canceling transaction");
			}

			privateIndividual = null;
			DBException de = new DBException("Error retrieving data");
			de.setStackTrace(ex.getStackTrace());
			ex.printStackTrace();
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
			DBConnection.getInstance().startTransaction();

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

			DBConnection.getInstance().commitTransaction();
		} catch (DBException ex) {
			throw ex;
		} catch (SQLException ex) {
			try {
				DBConnection.getInstance().rollbackTransaction();
			} catch (SQLException e) {
				throw new DBException("Error: Transaction roll back problem while canceling transaction");
			}

			selfEmployed = null;
			DBException de = new DBException("Error retrieving data");
			de.setStackTrace(ex.getStackTrace());
			ex.printStackTrace();
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

		String insert1 = "insert into Cities values(?,?)";
		String insert2 = "insert into Customers (email, street, street_number, name, phone_number, zip_code, customer_type) values (?, ?, ?, ?, ?, ?, ?)";
		String insert3 = "insert into Private_individuals (customer_email, id, vat_identificator) values (?, ?, ?)";


		try {
			DBConnection.getInstance().startTransaction();

			PreparedStatement stmt = null;
			try {
				stmt = con.prepareStatement(insert1);
				stmt.setString(1, privateIndividual.getZipCode());
				stmt.setString(2, privateIndividual.getCity());
				stmt.setQueryTimeout(5);
				stmt.execute();
			}catch (SQLException ex ){ }

			stmt = con.prepareStatement(insert2);
			stmt.setString(1, privateIndividual.getEmail());
			stmt.setString(2, privateIndividual.getStreet());
			stmt.setString(3, privateIndividual.getStreetNumber());
			stmt.setString(4, privateIndividual.getName());
			stmt.setString(5, privateIndividual.getPhoneNumber());
			stmt.setString(6, privateIndividual.getZipCode());
			stmt.setString(7, "Private_individual");
			stmt.setQueryTimeout(5);
			stmt.execute();


			stmt = con.prepareStatement(insert3);
			stmt.setString(1, privateIndividual.getEmail());
			stmt.setString(2, privateIndividual.getId());
			stmt.setString(3, privateIndividual.getVat());
			stmt.setQueryTimeout(5);
			stmt.execute();

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
				de = new DBException("Error: Customer with given email already exists");
			else
				de = new DBException("Error saving data");
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
		Connection con = DBConnection.getInstance().getDBcon();

		String insert1 = "insert into Cities values(?,?)";
		String insert2 = "insert into Customers (email, street, street_number, name, phone_number, zip_code, customer_type) values (?, ?, ?, ?, ?, ?, ?)";
		String insert3 = "insert into Self_employeed (customer_email, market_number, vat_identificator) values (?, ?, ?)";

		try {
			DBConnection.getInstance().startTransaction();

			PreparedStatement stmt= null;
			try {
				stmt = con.prepareStatement(insert1);
				stmt.setString(1, selfEmployed.getZipCode());
				stmt.setString(2, selfEmployed.getCity());
				stmt.setQueryTimeout(5);
				stmt.execute();
			}catch (SQLException ex ){ }

			stmt = con.prepareStatement(insert2);
			stmt.setString(1, selfEmployed.getEmail());
			stmt.setString(2, selfEmployed.getStreet());
			stmt.setString(3, selfEmployed.getStreetNumber());
			stmt.setString(4, selfEmployed.getName());
			stmt.setString(5, selfEmployed.getPhoneNumber());
			stmt.setString(6, selfEmployed.getZipCode());
			stmt.setString(7, "Self_employed");
			stmt.setQueryTimeout(5);
			stmt.execute();

			stmt = con.prepareStatement(insert3);
			stmt.setString(1, selfEmployed.getEmail());
			stmt.setString(2, selfEmployed.getMarketNumber());
			stmt.setString(3, selfEmployed.getVat());
			stmt.setQueryTimeout(5);
			stmt.execute();
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
				de = new DBException("Error: Customer with given email already exists");
			else
				de = new DBException("Error saving data");
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
		Connection con = DBConnection.getInstance().getDBcon();

		String insert1 = "insert into Cities values(?,?)";
		String insert2 = "insert into Customers (email, street, street_number, name, phone_number, zip_code, customer_type) values (?, ?, ?, ?, ?, ?, ?)";
		String insert3 = "insert into LTDs (customer_email, market_registration_number, market_number, are_payers) values (?, ?, ?, ?)";
		String insert4 = "update LTD_employees set LTD_email=? where id=?";
		String insert5 = "insert into LTD_ownerships values(?,?)";

		try {
			DBConnection.getInstance().startTransaction();

			PreparedStatement stmt= null;
			try {
				stmt = con.prepareStatement(insert1);
				stmt.setString(1, ltd.getZipCode());
				stmt.setString(2, ltd.getCity());
				stmt.setQueryTimeout(5);
				stmt.execute();
			}catch (SQLException ex ){ }

			stmt = con.prepareStatement(insert2);
			stmt.setString(1, ltd.getEmail());
			stmt.setString(2, ltd.getStreet());
			stmt.setString(3, ltd.getStreetNumber());
			stmt.setString(4, ltd.getName());
			stmt.setString(5, ltd.getPhoneNumber());
			stmt.setString(6, ltd.getZipCode());
			stmt.setString(7, "LTD");
			stmt.setQueryTimeout(5);
			stmt.execute();

			stmt = con.prepareStatement(insert3);
			stmt.setString(1, ltd.getEmail());
			stmt.setString(2, ltd.getMarketRegistrationNumber());
			stmt.setString(3, ltd.getMarketNumber());
			stmt.setBoolean(4, ltd.arePayers());
			stmt.setQueryTimeout(5);
			stmt.execute();

			for(CustomerEmployee customerEmployee : ltd.getEmployees()) {
				stmt = con.prepareStatement(insert4);
				stmt.setString(1, ltd.getEmail());
				stmt.setString(2, customerEmployee.getId());
				stmt.setQueryTimeout(5);
				if(stmt.executeUpdate() == 0){
					throw new DBException("Employee with id " + customerEmployee.getId() + " does not exist in DB anymore");
				}
			}


			for(Owner owner : ltd.getOwners()) {
				stmt = con.prepareStatement(insert5);
				stmt.setString(1, ltd.getEmail());
				stmt.setString(2, owner.getId());
				stmt.setQueryTimeout(5);
				try {
					stmt.execute();
				} catch (SQLException ex){
					throw new DBException("Owner with id " + owner.getId() + "does not exist in DB anymore");
				}
			}

			stmt.close();

			DBConnection.getInstance().commitTransaction();
		} catch (DBException ex){
			throw ex;
		} catch (SQLException ex) {
			try {
				DBConnection.getInstance().rollbackTransaction();
			} catch (SQLException e) {
				throw new DBException("Error: Transaction roll back problem while canceling transaction");
			}

			DBException de = null;
			if(ex.getMessage().startsWith("Violation of PRIMARY KEY"))
				de = new DBException("Error: Customer with given email already exists");
			else
				de = new DBException("Error saving data");
			de.setStackTrace(ex.getStackTrace());
			System.out.println(ex.getMessage());
			ex.printStackTrace();
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
	public boolean updatePrivateIndividual(String oldEmail, PrivateIndividual privateIndividual) throws DBException {
		Connection con = DBConnection.getInstance().getDBcon();

		String insert1 = "insert into Cities values(?,?)";
		String insert2 = "update Customers set email=?, street=?, street_number=?, name=?, phone_number=?, zip_code=?, customer_type=? where email=?";
		String insert3 = "update Private_individuals set customer_email=?, id=?, vat_identificator=? where customer_email=?";


		try {
			DBConnection.getInstance().startTransaction();

			PreparedStatement stmt = null;
			try {
				stmt = con.prepareStatement(insert1);
				stmt.setString(1, privateIndividual.getZipCode());
				stmt.setString(2, privateIndividual.getCity());
				stmt.setQueryTimeout(5);
				stmt.execute();
			}catch (SQLException ex ){ }

			stmt = con.prepareStatement(insert2);
			stmt.setString(1, privateIndividual.getEmail());
			stmt.setString(2, privateIndividual.getStreet());
			stmt.setString(3, privateIndividual.getStreetNumber());
			stmt.setString(4, privateIndividual.getName());
			stmt.setString(5, privateIndividual.getPhoneNumber());
			stmt.setString(6, privateIndividual.getZipCode());
			stmt.setString(7, "Private_individual");
			stmt.setString(8, oldEmail);
			stmt.setQueryTimeout(5);
			if(stmt.executeUpdate() == 0){
				throw new DBException("Customer with given email does not exist");
			}


			stmt = con.prepareStatement(insert3);
			stmt.setString(1, privateIndividual.getEmail());
			stmt.setString(2, privateIndividual.getId());
			stmt.setString(3, privateIndividual.getVat());
			stmt.setString(4, privateIndividual.getEmail());
			stmt.setQueryTimeout(5);
			stmt.execute();

			stmt.close();

			DBConnection.getInstance().commitTransaction();
		} catch (DBException ex){
			throw ex;
		}  catch (SQLException ex) {
			try {
				DBConnection.getInstance().rollbackTransaction();
			} catch (SQLException e) {
				throw new DBException("Error: Transaction roll back problem while canceling transaction");
			}

			DBException de = null;
			de = new DBException("Error updating data");
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
	public boolean updateSelfEmployed(String oldEmail, SelfEmployed selfEmployed) throws DBException {
		Connection con = DBConnection.getInstance().getDBcon();

		String insert1 = "insert into Cities values(?,?)";
		String insert2 = "update Customers set email=?, street=?, street_number=?, name=?, phone_number=?, zip_code=?, customer_type=? where email=?";
		String insert3 = "update Self_employeed set customer_email=?, market_number=?, vat_identificator=? where customer_email=?";

		try {
			DBConnection.getInstance().startTransaction();

			PreparedStatement stmt= null;

			try {
				stmt = con.prepareStatement(insert1);
				stmt.setString(1, selfEmployed.getZipCode());
				stmt.setString(2, selfEmployed.getCity());
				stmt.setQueryTimeout(5);
				stmt.execute();
			} catch (SQLException ex){}


			stmt = con.prepareStatement(insert2);
			stmt.setString(1, selfEmployed.getEmail());
			stmt.setString(2, selfEmployed.getStreet());
			stmt.setString(3, selfEmployed.getStreetNumber());
			stmt.setString(4, selfEmployed.getName());
			stmt.setString(5, selfEmployed.getPhoneNumber());
			stmt.setString(6, selfEmployed.getZipCode());
			stmt.setString(7, "Self_employed");
			stmt.setString(8, oldEmail);
			stmt.setQueryTimeout(5);
			if(stmt.executeUpdate() == 0){
				throw new DBException("Customer with given email does not exist");
			}

			stmt = con.prepareStatement(insert3);
			stmt.setString(1, selfEmployed.getEmail());
			stmt.setString(2, selfEmployed.getMarketNumber());
			stmt.setString(3, selfEmployed.getVat());
			stmt.setString(4, selfEmployed.getEmail());
			stmt.setQueryTimeout(5);
			stmt.execute();

			stmt.close();

			DBConnection.getInstance().commitTransaction();
		} catch (DBException ex){
			throw ex;
		}  catch (SQLException ex) {
			try {
				DBConnection.getInstance().rollbackTransaction();
			} catch (SQLException e) {
				throw new DBException("Error: Transaction roll back problem while canceling transaction");
			}

			DBException de = null;
			de = new DBException("Error updating data");
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
	public boolean updateLTD(String oldEmail, LTD ltd) throws DBException {
		Connection con = DBConnection.getInstance().getDBcon();

		String insert1 = "insert into Cities values(?,?)";
		String insert2 = "update Customers set email=?, street=?, street_number=?, name=?, phone_number=?, zip_code=?, customer_type=? where email=?";
		String insert3 = "update LTDs set customer_email=?, market_registration_number=?, market_number=?, are_payers=? where customer_email=?";
		String insert4 = "update LTD_employees set LTD_email=? where LTD_email=?";
		String insert5 = "update LTD_employees set LTD_email=? where id=?";
		String insert6 = "delete from LTD_ownerships where LTD_email=?";
		String insert7 = "insert into LTD_ownerships values(?,?)";

		try {
			DBConnection.getInstance().startTransaction();

			PreparedStatement stmt= null;
			try {
				stmt = con.prepareStatement(insert1);
				stmt.setString(1, ltd.getZipCode());
				stmt.setString(2, ltd.getCity());
				stmt.setQueryTimeout(5);
				stmt.execute();
			}catch (SQLException ex ){ }

			stmt = con.prepareStatement(insert2);
			stmt.setString(1, ltd.getEmail());
			stmt.setString(2, ltd.getStreet());
			stmt.setString(3, ltd.getStreetNumber());
			stmt.setString(4, ltd.getName());
			stmt.setString(5, ltd.getPhoneNumber());
			stmt.setString(6, ltd.getZipCode());
			stmt.setString(7, "LTD");
			stmt.setString(8, oldEmail);
			stmt.setQueryTimeout(5);
			if(stmt.executeUpdate() == 0){
				throw new DBException("Customer with given email does not exist");
			}

			stmt = con.prepareStatement(insert3);
			stmt.setString(1, ltd.getEmail());
			stmt.setString(2, ltd.getMarketRegistrationNumber());
			stmt.setString(3, ltd.getMarketNumber());
			stmt.setBoolean(4, ltd.arePayers());
			stmt.setString(5, ltd.getEmail());
			stmt.setQueryTimeout(5);
			stmt.execute();

			stmt = con.prepareStatement(insert4);
			stmt.setString(1, null);
			stmt.setString(2, oldEmail);
			stmt.setQueryTimeout(5);
			stmt.execute();


			for(CustomerEmployee customerEmployee : ltd.getEmployees()) {
				stmt = con.prepareStatement(insert5);
				stmt.setString(1, ltd.getEmail());
				stmt.setString(2, customerEmployee.getId());
				stmt.setQueryTimeout(5);
				if(stmt.executeUpdate() == 0){
					throw new DBException("Employee with id " + customerEmployee.getId() + " does not exist in DB anymore");
				}
			}

			stmt = con.prepareStatement(insert6);
			stmt.setString(1, ltd.getEmail());
			stmt.setQueryTimeout(5);
			stmt.execute();


			for(Owner owner : ltd.getOwners()) {
				stmt = con.prepareStatement(insert7);
				stmt.setString(1, ltd.getEmail());
				stmt.setString(2, owner.getId());
				stmt.setQueryTimeout(5);
				try {
					stmt.execute();
				} catch (SQLException ex){
					throw new DBException("Owner with id " + owner.getId() + "does not exist in DB anymore");
				}
			}

			stmt.close();

			DBConnection.getInstance().commitTransaction();
		} catch (DBException ex){
			throw ex;
		}  catch (SQLException ex) {
			try {
				DBConnection.getInstance().rollbackTransaction();
			} catch (SQLException e) {
				throw new DBException("Error: Transaction roll back problem while canceling transaction");
			}

			DBException de = null;
			de = new DBException("Error updating data");
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
	public boolean deleteEveryCustomerTypeByEmail(String email) throws DBException {
		Connection con = DBConnection.getInstance().getDBcon();

		String delete = "delete from Customers where email=?";

		try {
			DBConnection.getInstance().startTransaction();

			PreparedStatement stmt = con.prepareStatement(delete);
			stmt.setString(1, email);
			stmt.setQueryTimeout(5);
			if(stmt.executeUpdate() == 0)
				throw new DBException("Error: Customer with this id does not exist");

			stmt.close();

			DBConnection.getInstance().commitTransaction();
		} catch (DBException ex){
			throw ex;
		} catch (SQLException ex) {
			try {
				DBConnection.getInstance().rollbackTransaction();
			} catch (SQLException e) {
				throw new DBException("Error: Transaction roll back problem while canceling transaction");
			}

			DBException de = new DBException("Error deleting data");
			de.setStackTrace(ex.getStackTrace());
			ex.printStackTrace();
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
