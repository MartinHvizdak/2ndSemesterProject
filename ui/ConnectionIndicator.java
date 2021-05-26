package ui;

import db.DBConnection;
import db.IObserver;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;

public class ConnectionIndicator implements Runnable, IObserver {
    JPanel container;
    boolean isConnectionOpen;
    boolean showGreenColor; //showGreenColor helps in showing connections so short that loop with if statements
    // would not have enough time to read positive value from isConnectionOpen
    JFrame mainWindow;

    ConnectionIndicator(JFrame mainWindow){
        this.mainWindow =  mainWindow;

        container =  new JPanel();
        container.setBounds(0,0,mainWindow.getWidth(), 2);
        container.setBackground(Color.gray);
        mainWindow.add(container);

        DBConnection.addObserver(this);
    }


    @Override
    public void run() {
        while(true){
            if(isConnectionOpen){
                showGreenColor = true;
            }

            if(showGreenColor){

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        container.setBackground(Color.green);
                        container.invalidate();
                        //mainWindow.setTitle("GREEN"); //unlike container.setBackground(Color.green), title of the mainWindow is changed precisely when connection state changes
                    }
                });

                try {
                    TimeUnit.MILLISECONDS.sleep(1000); //mostly connection is too short for screen to react and change the color
                } catch (InterruptedException e) { }
                showGreenColor =  false;

            }else{

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        container.setBackground(Color.gray);
                        container.invalidate();
                        //mainWindow.setTitle("RED"); //unlike container.setBackground(Color.green), title of the mainWindow is changed precisely when connection state changes
                    }
                });

            }

            try {
                TimeUnit.MILLISECONDS.sleep(10); //Limit while loop to 100Hz
            } catch (InterruptedException e) { }
        }
    }

    @Override
    public void update(boolean isConnectionOpen) {
        if(isConnectionOpen){
            System.out.println("-----Connection opened------");
            this.isConnectionOpen = true;
            showGreenColor = true;
        }else{
            System.out.println("-----Connection closed------");
            this.isConnectionOpen =  false;
        }

    }
}
