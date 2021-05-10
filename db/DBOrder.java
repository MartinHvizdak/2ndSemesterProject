package db;

//Eventually will change to model.Order and model.Customer
import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;


public class DBOrder implements IDBOrder{

	@Override
	public void saveOrder(Order order) throws DBException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Order retrieveOrderByID(int id) throws DBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteOrderByID(int id) throws DBException {
		// TODO Auto-generated method stub
		
	}
}
