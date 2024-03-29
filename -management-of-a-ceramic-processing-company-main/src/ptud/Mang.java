/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ptud;

import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import ptud.Connection.ConnectDB;
import ptud.GUI.*;
/**
 *
 * @author TomTom, TranLoc
 */
 

public class Mang
{
	  public static Connection connection = null; 
	    public static void begin() {
	        ConnectDB.connectDatabase();
	        connection = ConnectDB.getConnection(); 
	        SplashScreen sp = new SplashScreen();
	        sp.setVisible(true);
	        try {
	            Thread.sleep(1000);
	        } catch (InterruptedException ex) {
	            Logger.getLogger(SplashScreen.class.getName()).log(Level.SEVERE, null, ex);
	        }
	        sp.setVisible(false);
	        new Login().setVisible(true);
	    }

 
    public static void main(String[] args) 
    {
    	 begin();
	}
  
}