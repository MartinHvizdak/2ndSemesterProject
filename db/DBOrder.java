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
		String insert2 = "insert into Order_lines (order_id, service_id, quantity) values (?, ?, ?)";
		
		try {
			DBConnection.getInstance().startTransaction();

			PreparedStatement stmt = con.prepareStatement(insert1);

			stmt.setInt(1, order.getId());
			stmt.setString(2, order.getCustomer().getEmail());
			stmt.setDate(3, Date.valueOf(order.getPayday()));
			stmt.setQueryTimeout(5);
			stmt.execute();

			for(OrderLine orderLine : order.getOrderLines()) {
				stmt = con.prepareStatement(insert2);
				stmt.setInt(1, order.getId());
				stmt.setInt(2, orderLine.getService().getID());
				stmt.setInt(3, orderLine.getQuantity());
				stmt.setQueryTimeout(5);
				try {
					stmt.execute();
				} catch (SQLException ex){
					throw new DBException("Service with id " + orderLine.getService().getID() + "does not exist in DB anymore");
				}
			}
			
			stmt.close();

			DBConnection.getInstance().commitTransaction();
		} catch (DBException ex) {
			throw ex;
		} catch (SQLException ex) {
			try {
				DBConnection.getInstance().rollbackTransaction();
			} catch (SQLException e) {
				throw new DBException("Error: transaction roll back problem while canceling transaction");
			}

			DBException de = null;
			if(ex.getMessage().startsWith("Violation of PRIMARY KEY"))
				de = new DBException("Error: Order with given id already exists");
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
			DBException de = new DBException("Data not saved! Technical error");
			de.setStackTrace(ex.getStackTrace());
			throw de;
		} finally {
			DBConnection.closeConnection();
		}

		return true;
	}

	@Override
	public ArrayList<Order<Customer>> retrieveAllOrders() throws DBException{
		Connection con = DBConnection.getInstance().getDBcon();
		ArrayList<Order<Customer>> orders = new ArrayList<>();

		try {
			DBConnection.getInstance().startTransaction();

			String select1 =
					"Select *\n" +
							"from Orders \n" +
							"inner join Customers on Orders.customer_email = Customers.email\n" +
							"inner join Cities on Customers.zip_code = Cities.zip_code";

			PreparedStatement stmt = con.prepareStatement(select1);
			stmt.setQueryTimeout(5);
			ResultSet rs = stmt.executeQuery();

			while(rs.next()) {
				int orderID = rs.getInt("id");
				LocalDate orderPayday = rs.getDate("payday").toLocalDate();

				String customerEmail = rs.getString("customer_email");
				String customerCity = rs.getString("city");
				String customerStreet = rs.getString("street");
				String customerStreetNumber = rs.getString("street_number");
				String customerPhoneNumber = rs.getString("phone_number");
				String customerZipCode = rs.getString("zip_code");

				Customer customer =  new Customer(customerEmail, customerPhoneNumber, customerCity, customerZipCode, customerStreet, customerStreetNumber);

				String select2 = "Select *\n" +
						"from Order_lines \n" +
						"inner join Services on Order_lines.service_id= Services.id\n" +
						"where Order_lines.order_id = ? \n";

				PreparedStatement stmt2 = con.prepareStatement(select2);
				stmt2.setInt(1, orderID);
				stmt2.setQueryTimeout(5);
				ResultSet rs2 = stmt2.executeQuery();

				ArrayList<OrderLine> orderLines = new ArrayList<>();
				while (rs2.next()) {
					int serviceID = rs2.getInt("id");
					String serviceName = rs2.getString("name");
					String serviceDescription = rs2.getString("description");
					double servicePrice = rs2.getDouble("price");
					int serviceQuantity = rs2.getInt("quantity");
					Service service = new Service(serviceID, serviceName, serviceDescription, servicePrice);
					OrderLine orderLine = new OrderLine(service, serviceQuantity);
					orderLines.add(orderLine);
				}
				orders.add(new Order<Customer>(customer, orderLines, orderPayday, orderID));
			}

			if(orders.isEmpty())
				throw new DBException("There are not any orders in DB");

			DBConnection.getInstance().commitTransaction();
		} catch (DBException ex) {
			throw ex;
		} catch (SQLException ex) {
			try {
				DBConnection.getInstance().rollbackTransaction();
			} catch (SQLException e) {
				throw new DBException("Error: transaction roll back problem while canceling transaction");
			}

			DBException de = new DBException("Error retrieving data");
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

		return orders;
	}
	
	@Override
	public Order retrieveOrderByID(int id) throws DBException {
		Connection con = DBConnection.getInstance().getDBcon();
		Order order = null;
		
		try {
			DBConnection.getInstance().startTransaction();

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
			String customerPhoneNumber = rs.getString("phone_number");
			String customerZipCode = rs.getString("zip_code");

			String customerType = rs.getString("customer_type");

			String select2 = "Select *\n" +
					"from Order_lines \n" +
					"inner join Services on Order_lines.service_id= Services.id\n" +
					"where Order_lines.order_id = ? \n";

			stmt = con.prepareStatement(select2);
			stmt.setInt(1, id);
			stmt.setQueryTimeout(5);
			rs = stmt.executeQuery();

			ArrayList<OrderLine> orderLines =  new ArrayList<>();
			while(rs.next()) {
				int serviceID = rs.getInt("id");
				String serviceName = rs.getString("name");
				String serviceDescription = rs.getString("description");
				double servicePrice = rs.getDouble("price");
				int serviceQuantity = rs.getInt("quantity");
				Service service = new Service(serviceID, serviceName, serviceDescription, servicePrice);
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
				String firstName = rs.getString("first_name");
				String secondName = rs.getString("second_name");

				PrivateIndividual privateIndividual =
						new PrivateIndividual(customerEmail, firstName, secondName, customerPhoneNumber, customerCity,
								customerZipCode, customerStreet, customerStreetNumber, individualID, individualVAT);
				return  new Order<PrivateIndividual>(privateIndividual, orderLines, orderPayday, orderID);

			}else if(customerType.equals("Self_employeed")){
				String select3 = "Select *\n" +
						"from Self_employeed \n" +
						"where Self_employeed.customer_email = ?";

				stmt = con.prepareStatement(select3);
				stmt.setString(1, customerEmail);
				stmt.setQueryTimeout(5);
				rs = stmt.executeQuery();
				rs.next();

				String marketNumber = rs.getString("market_number");
				String vat = rs.getString("vat_identificator");
				String firstName = rs.getString("first_name");
				String secondName = rs.getString("second_name");

				SelfEmployed selfEmployeed =
						new SelfEmployed(customerEmail, firstName, secondName, customerPhoneNumber, customerCity,
								customerZipCode, customerStreet, customerStreetNumber, marketNumber, vat);
				return  new Order<SelfEmployed>(selfEmployeed, orderLines, orderPayday, orderID);
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
				String companyName = rs.getString("company_name");

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
					String lastName = rs.getString("second_name");
					double salary = rs.getDouble("salary");
					double generatedIncome = rs.getDouble("generated_income");

					CustomerEmployee customerEmployee = new CustomerEmployee(employeeID, firstName, lastName, salary, generatedIncome);
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
					String firstName = rs.getString("first_name");
					String surName = rs.getString("second_name");
					String relation = rs.getString("relation");

					Owner owner = new Owner(ownerID, firstName, surName,relation);
					owners.add(owner);
				}

				LTD ltd =
						new LTD(customerEmail, companyName, customerPhoneNumber, customerCity,
								customerZipCode, customerStreet, customerStreetNumber, employees, owners,
								marketRegistrationNumber, marketNumber, arePayers);
				order =  new Order<LTD>(ltd, orderLines, orderPayday, orderID);
			}
			DBConnection.getInstance().commitTransaction();
		} catch (DBException ex) {
			throw ex;
		} catch (SQLException ex) {
			try {
				DBConnection.getInstance().rollbackTransaction();
			} catch (SQLException e) {
				throw new DBException("Error: transaction roll back problem while canceling transaction");
			}

			DBException de = new DBException("Error retrieving data");
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
		
		return order;
	}

	@Override
	public boolean deleteOrderByID(int id) throws DBException {
		Connection con = DBConnection.getInstance().getDBcon();
		
		String delete = "delete from Orders where id=?";
		
		try {
			DBConnection.getInstance().startTransaction();

			PreparedStatement stmt = con.prepareStatement(delete);
			stmt.setInt(1, id);
			stmt.setQueryTimeout(5);
			if(stmt.executeUpdate() == 0)
				throw new DBException("Error: Order with this id does not exist");
			stmt.close();

			DBConnection.getInstance().commitTransaction();
		} catch (DBException ex) {
			throw ex;
		} catch (SQLException ex) {
			try {
				DBConnection.getInstance().rollbackTransaction();
			} catch (SQLException e) {
				throw new DBException("Error: transaction roll back problem while canceling transaction");
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
		
		return true;
	}

	@Override
	public boolean updateOrder(Order<Customer> order) throws DBException {
		Connection con = DBConnection.getInstance().getDBcon();

		String update = "update Orders set customer_email=?, payday=? where id=?";
		String delete = "delete from Order_lines where order_id=?";
		String insert = "insert into Order_lines (order_id, service_id, quantity) values (?, ?, ?)";

		try {
			DBConnection.getInstance().startTransaction();

			PreparedStatement stmt = con.prepareStatement(update);
			stmt.setString(1, order.getCustomer().getEmail());
			stmt.setDate(2, Date.valueOf(order.getPayday()));
			stmt.setDouble(3, order.getId());
			stmt.setQueryTimeout(5);
			if(stmt.executeUpdate() == 0)
				throw new DBException("Error: Order with with this id does not exist");


			stmt = con.prepareStatement(delete);
			stmt.setInt(1, order.getId());
			stmt.setQueryTimeout(5);
			stmt.executeUpdate();



			for(OrderLine orderLine : order.getOrderLines()) {
				stmt = con.prepareStatement(insert);
				stmt.setInt(1, order.getId());
				stmt.setInt(2, orderLine.getService().getID());
				stmt.setInt(3, orderLine.getQuantity());
				stmt.setQueryTimeout(5);
				try {
					stmt.execute();
				} catch (SQLException ex){
					throw new DBException("Service with id " + orderLine.getService().getID() + "does not exist in DB anymore");
				}
			}

			stmt.close();
			DBConnection.getInstance().commitTransaction();
		} catch (DBException ex) {
			throw ex;
		} catch (SQLException ex) {
			try {
				DBConnection.getInstance().rollbackTransaction();
			} catch (SQLException e) {
				throw new DBException("Error: transaction roll back problem while canceling transaction");
			}

			DBException de = null;
			if(ex.getMessage().startsWith("Violation of PRIMARY KEY"))
				de = new DBException("Error: Order with with given new id already exists");
			else
				de = new DBException("Error updating data");
			de.setStackTrace(ex.getStackTrace());
			ex.printStackTrace();
			System.out.println(ex.getMessage());
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
		return true;
	}

	@Override
	public ArrayList<Order> retrieveOrdersByEmailAndPeriod(String email, LocalDate startDate, LocalDate endDate)
			throws DBException {
		Connection con = DBConnection.getInstance().getDBcon();
		Order order = null;
		ArrayList<Order> orders = new ArrayList<Order>();
		
		String select = "select * from Orders where customer_email = ?";
		
		try {
			
			PreparedStatement stmt = con.prepareStatement(select);
			stmt.setString(1, email);
			ResultSet rs;
			rs = stmt.executeQuery();
			int id;
			while(rs.next()) {
				id = rs.getInt("id");
				System.out.println(id);
				try {
					String select1 =
							"Select *\n" +
									"from Orders \n" +
									"inner join Customers on Orders.customer_email = Customers.email\n" +
									"inner join Cities on Customers.zip_code = Cities.zip_code\n" +
									"where Orders.id = ?";

					stmt = con.prepareStatement(select1);
					stmt.setInt(1, id);
					stmt.setQueryTimeout(5);
					rs = stmt.executeQuery();

					if(!rs.next())
						throw new DBException("Order with this id does not exist");

					
					int orderID = rs.getInt("id");
					LocalDate orderPayday = rs.getDate("payday").toLocalDate();
					String customerEmail = rs.getString("customer_email");
					String customerCity = rs.getString("city");
					String customerStreet = rs.getString("street");
					String customerStreetNumber = rs.getString("street_number");
					String customerPhoneNumber = rs.getString("phone_number");
					String customerZipCode = rs.getString("zip_code");

					String customerType = rs.getString("customer_type");

					String select2 = "Select *\n" +
							"from Order_lines \n" +
							"inner join Services on Order_lines.service_id= Services.id\n" +
							"where Order_lines.order_id = ? \n";

					stmt = con.prepareStatement(select2);
					stmt.setInt(1, id);
					stmt.setQueryTimeout(5);
					rs = stmt.executeQuery();

					ArrayList<OrderLine> orderLines =  new ArrayList<>();
					while(rs.next()) {
						int serviceID = rs.getInt("id");
						String serviceName = rs.getString("name");
						String serviceDescription = rs.getString("description");
						double servicePrice = rs.getDouble("price");
						int serviceQuantity = rs.getInt("quantity");
						Service service = new Service(serviceID, serviceName, serviceDescription, servicePrice);
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
						String firstName = rs.getString("first_name");
						String secondName = rs.getString("second_name");

						PrivateIndividual privateIndividual =
								new PrivateIndividual(customerEmail, firstName, secondName, customerPhoneNumber, customerCity,
										customerZipCode, customerStreet, customerStreetNumber, individualID, individualVAT);
						order = new Order<PrivateIndividual>(privateIndividual, orderLines, orderPayday, orderID);
						orders.add(order);

					}else if(customerType.equals("Self_employeed")){
						String select3 = "Select *\n" +
								"from Self_employeed \n" +
								"where Self_employeed.customer_email = ?";

						stmt = con.prepareStatement(select3);
						stmt.setString(1, customerEmail);
						stmt.setQueryTimeout(5);
						rs = stmt.executeQuery();
						rs.next();

						String marketNumber = rs.getString("market_number");
						String vat = rs.getString("vat_identificator");
						String firstName = rs.getString("first_name");
						String secondName = rs.getString("second_name");

						SelfEmployed selfEmployeed =
								new SelfEmployed(customerEmail, firstName, secondName, customerPhoneNumber, customerCity,
										customerZipCode, customerStreet, customerStreetNumber, marketNumber, vat);
						order = new Order<SelfEmployed>(selfEmployeed, orderLines, orderPayday, orderID);
						orders.add(order);
						
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
						String companyName = rs.getString("company_name");

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
							String lastName = rs.getString("second_name");
							double salary = rs.getDouble("salary");
							double generatedIncome = rs.getDouble("generated_income");

							CustomerEmployee customerEmployee = new CustomerEmployee(employeeID, firstName, lastName, salary, generatedIncome);
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
							String firstName = rs.getString("first_name");
							String surName = rs.getString("second_name");
							String relation = rs.getString("relation");

							Owner owner = new Owner(ownerID, firstName, surName,relation);
							owners.add(owner);
							
						}

						LTD ltd =
								new LTD(customerEmail, companyName, customerPhoneNumber, customerCity,
										customerZipCode, customerStreet, customerStreetNumber, employees, owners,
										marketRegistrationNumber, marketNumber, arePayers);
						order =  new Order<LTD>(ltd, orderLines, orderPayday, orderID);
						orders.add(order);
					}
					DBConnection.getInstance().commitTransaction();
				} catch (DBException ex) {
					throw ex;
				} catch (SQLException ex) {
					try {
						DBConnection.getInstance().rollbackTransaction();
					} catch (SQLException e) {
						throw new DBException("Error: transaction roll back problem while canceling transaction");
					}

					DBException de = new DBException("Error retrieving data");
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
				}
			}
			
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
		return orders;
	}

}
