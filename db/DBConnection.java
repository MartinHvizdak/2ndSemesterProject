package db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;


public class DBConnection {
    private static ArrayList<IObserver> observers = new ArrayList<IObserver>();
	
	private static final String  driver = "jdbc:sqlserver://hildur.ucn.dk";;
    private static final String  databaseName = ";databaseName=dmaj0920_1086329";
    
    private static String  userName = "; user=dmaj0920_1086329";
    private static String password = ";password=Password1!";
   
    private DatabaseMetaData dma;
    private static Connection con;
    
    private static DBConnection  instance = null;

    private DBConnection()
    {
    	String url = driver + databaseName + userName + password;

        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            System.out.println("Driver class loaded ok");
          
        }
        
        catch(Exception e){
            System.out.println("Cannot find the driver");
            System.out.println(e.getMessage());
        }
        try{
            //con = DriverManager.getConnection(url);
            openNewConnection(DriverManager.getConnection(url));
            con.setAutoCommit(true);
            dma = con.getMetaData();
            System.out.println("Connection to " + dma.getURL());
            System.out.println("Driver " + dma.getDriverName());
            System.out.println("Database product name " + dma.getDatabaseProductName());
        }
        catch(Exception e){
            System.out.println("Problems with the connection to the database:");
            System.out.println(e.getMessage());
            System.out.println(url);
        }
    }

    public static void closeConnection()
    {
       	try{
            con.close();
            instance= null;
            System.out.println("The connection is closed");

            notifyAllObservers(false);
        }
         catch (Exception e){
            System.out.println("Error trying to close the database " +  e.getMessage());
         }
    }

    public static DBConnection getInstanceWithoutInitializing(){return instance;}
    
    public static DBConnection getInstance()
    {
        if (instance == null)
        {
          instance = new DBConnection();
        }
        return instance;
    }

    public void startTransaction() throws SQLException {
        con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        con.setAutoCommit(false);
    }

    public void commitTransaction() throws SQLException {
        con.commit();
        con.setAutoCommit(true);
    }

    public void rollbackTransaction() throws SQLException {
        con.rollback();
        con.setAutoCommit(true);
    }

    public Connection getDBcon()
    {
        return con;
    }

    //methods for observer pattern
    private void openNewConnection(Connection con) {
        this.con = con;
        notifyAllObservers(true);
    }

    public static void addObserver(IObserver observer){
        observers.add(observer);
    }

    private static void notifyAllObservers(boolean isConnectionOpen){
        for (IObserver observer : observers) {
            observer.update(isConnectionOpen);
        }
    }
}
