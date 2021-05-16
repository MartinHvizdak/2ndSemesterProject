package db;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

import model.*;


public class DBOrder implements IDBOrder{

	@Override
	public boolean saveOrder(Order<Customer> order) throws DBException {
		Connection con = DBConnection.getInstance().getDBcon();

		String insert1 = "insert into Orders (id, customer_email, payday) values (?, ?, ?)";
		String insert2 = "insert into Order_lines (order_id, service_name, quantity) values (?, ?, ?)";
		
		try {
			PreparedStatement stmt = con.prepareStatement(insert1);

			stmt.setInt(1, order.getId());
			stmt.setString(2, order.getCustomer().getEmail());
			stmt.setDate(3, Date.valueOf(order.getPayday()));
			stmt.setQueryTimeout(5);
			stmt.execute();

			for(OrderLine orderLine : order.getOrderLines()) {
				stmt = con.prepareStatement(insert2);
				stmt.setInt(1, order.getId());
				stmt.setString(2, orderLine.getService().getName());
				stmt.setInt(3, orderLine.getQuantity());
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
	
	@Override
	public Order retrieveOrderByID(int id) throws DBException {
		Connection con = DBConnection.getInstance().getDBcon();
		Order order = null;
		
		try {
			String select1 =
					"Select *\n" +
							"from Orders \n" +
							"inner join Customers on Orders.customer_email = Customers.email\n" +
							"inner join Cities on Customers.zip_code = Cities.zip_code\n" +
							"where Orders.id = ?";

			PreparedStatement stmt = con.prepareStatement(select1);
			stmt.setInt(1, id);
			stmt.setQueryTimeout(5);
			ResultSet rs = stmt.executeQuery();

			if(!rs.next())
				throw new DBException("Order with this id does not exist");

			int orderID = rs.getInt("id");
			LocalDate orderPayday = rs.getDate("payday").toLocalDate();
			String customerEmail = rs.getString("customer_email");
			String customerCity = rs.getString("city");
			String customerStreet = rs.getString("street");
			String customerStreetNumber = rs.getString("street_number");
			String customerName = rs.getString("name");
			String customerPhoneNumber = rs.getString("phone_number");
			String customerZipCode = rs.getString("zip_code");

			String customerType = rs.getString("customer_type");

			String select2 = "Select *\n" +
					"from Order_lines \n" +
					"inner join Services on Order_lines.service_name= Services.name\n" +
					"where Order_lines.order_id = ? \n";

			stmt = con.prepareStatement(select2);
			stmt.setInt(1, id);
			stmt.setQueryTimeout(5);
			rs = stmt.executeQuery();

			ArrayList<OrderLine> orderLines =  new ArrayList<>();
			while(rs.next()) {
				String serviceName = rs.getString("name");
				String serviceDescription = rs.getString("description");
				double servicePrice = rs.getDouble("price");
				int serviceQuantity = rs.getInt("quantity");
				Service service = new Service(serviceName, serviceDescription, servicePrice);
				OrderLine orderLine =  new OrderLine(service, serviceQuantity);
				orderLines.add(orderLine);
			}


			if (customerType.equals("Private_individual")){
				String select3 = "Select *\n" +
						"from Private_individuals \n" +
						"where Private_individuals.customer_email = ?";

				stmt = con.prepareStatement(select3);
				stmt.setString(1, customerEmail);
				stmt.setQueryTimeout(5);
				rs = stmt.executeQuery();
				rs.next();

				String individualID = rs.getString("id");
				String individualVAT = rs.getString("vat_identificator");

				PrivateIndividual privateIndividual =
						new PrivateIndividual(customerEmail, customerName, customerPhoneNumber, customerCity,
								customerZipCode, customerStreet, customerStreetNumber, individualID, individualVAT);
				return  new Order<PrivateIndividual>(privateIndividual, orderLines, orderPayday, orderID);

			}else if(customerType.equals("Self_employed")){
				String select3 = "Select *\n" +
						"from Self_employed \n" +
						"where Self_employed.customer_email = ?";

				stmt = con.prepareStatement(select3);
				stmt.setString(1, customerEmail);
				stmt.setQueryTimeout(5);
				rs = stmt.executeQuery();
				rs.next();

				String marketNumber = rs.getString("market_number");
				String vat = rs.getString("vat_identificator");

				SelfEmployed selfEmployed =
						new SelfEmployed(customerEmail, customerName, customerPhoneNumber, customerCity,
								customerZipCode, customerStreet, customerStreetNumber, marketNumber, vat);
				return  new Order<SelfEmployed>(selfEmployed, orderLines, orderPayday, orderID);
			}else if(customerType.equals("LTD")){
				String select3 = "Select *\n" +
						"from LTDs \n" +
						"where LTDs.customer_email = ?";

				stmt = con.prepareStatement(select3);
				stmt.setString(1, customerEmail);
				stmt.setQueryTimeout(5);
				rs = stmt.executeQuery();
				rs.next();

				String marketRegistrationNumber = rs.getString("market_registration_number");
				String marketNumber = rs.getString("market_number");
				boolean arePayers = rs.getBoolean("are_payers");

				String select4 = "Select *\n" +
						"from LTD_employees\n" +
						"where LTD_employees.LTD_email= ?";

				stmt = con.prepareStatement(select4);
				stmt.setString(1, customerEmail);
				stmt.setQueryTimeout(5);
				rs = stmt.executeQuery();

				ArrayList<CustomerEmployee> employees = new ArrayList<>();
				while(rs.next()) {
					String employeeID = rs.getString("id");
					String firstName = rs.getString("first_name");
					String secondName = rs.getString("second_name");
					double salary = rs.getDouble("salary");
					double generatedIncome = rs.getDouble("generated_income");

					CustomerEmployee customerEmployee = new CustomerEmployee(employeeID, firstName, secondName, salary, generatedIncome);
					employees.add(customerEmployee);
				}


				String select5 = "Select *\n" +
						"from LTD_ownerships\n" +
						"inner join LTD_owners on LTD_ownerships.owner_id = LTD_owners.id\n" +
						"where LTD_ownerships.LTD_email= ?";

				stmt = con.prepareStatement(select5);
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

				LTD ltd =
						new LTD(customerEmail, customerName, customerPhoneNumber, customerCity,
								customerZipCode, customerStreet, customerStreetNumber, employees, owners,
								marketRegistrationNumber, marketNumber, arePayers);
				order =  new Order<LTD>(ltd, orderLines, orderPayday, orderID);
			}

		} catch (DBException ex) {
			throw ex;
		} catch (SQLException ex) {
			DBException de = new DBException("Error retrieving data");
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
	public boolean deleteOrderByID(int id) throws DBException {
		Connection con = DBConnection.getInstance().getDBcon();
		
		String delete = "delete from Orders where id=?";
		
		try {
			PreparedStatement stmt = con.prepareStatement(delete);
			stmt.setInt(1, id);
			stmt.setQueryTimeout(5);
			if(stmt.executeUpdate() == 0)
				throw new DBException("Order with with id does not exist");
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
			DBException de = new DBException("Data not inserted! Technical error");
			de.setStackTrace(ex.getStackTrace());
			throw de;
		} finally {
			DBConnection.closeConnection();
		}
		
		return true;
	}

	@Override
	public boolean updateOrder(Order<Customer> order) throws DBException {
		deleteOrderByID(order.getId());
		saveOrder(order);
		return true;
	}

}
