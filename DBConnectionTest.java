package controller;

import java.sql.Connection;

import db.DBConnection;

public class DBConnectionTest {	
	public static void main(String args[]) {
		Connection con = DBConnection.getInstance().getDBcon();
	}
}
