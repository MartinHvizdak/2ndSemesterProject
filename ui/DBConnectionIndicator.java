package ui;

import db.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class DBConnectionIndicator implements Runnable {
    JPanel container;

    DBConnectionIndicator(JFrame mainWindow){
        container =  new JPanel();
        container.setBounds(0,0,mainWindow.getWidth(), 1);
        container.setBackground(Color.gray);
        mainWindow.add(container);

    }

    @Override
    public void run() {

        //long start=0;
        while(true){
            DBConnection dbConnection = DBConnection.getInstanceWithoutInitializing();
            Connection connection = null;
            if (dbConnection != null) {
                System.out.println("________----___----");
                connection = dbConnection.getDBcon();
            }

            try {

                if(dbConnection == null || connection.isClosed()){
                    //System.out.println(System.nanoTime()-start);
                    container.setBackground(Color.gray);
                    //start =  System.nanoTime();
                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (InterruptedException e) { }
                }else{
                    container.setBackground(Color.green);
                    System.out.println("GREEN!!!!!!!");
                    try {
                        TimeUnit.MILLISECONDS.sleep(1000);
                    } catch (InterruptedException e) { }

                }
            } catch (SQLException throwables) {
                container.setBackground(Color.red);
                throwables.printStackTrace();
            }


        }

    }
}
