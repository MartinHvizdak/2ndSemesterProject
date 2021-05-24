package controller;

import db.DBConnection;

import java.sql.Connection;

public class DBConnectionController {
    private DBConnection dbConnection;

    DBConnectionController(){
        dbConnection = DBConnection.getInstance();
    }

    public DBConnection getDBConnectionInstance(){
        return DBConnection.getInstance();
    }

    public Connection getDBcon(){
        return dbConnection.getDBcon();
    }
}
