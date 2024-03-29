/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ptud.Connection;

/**
 *
 * @author TomTom
 */
public class ConnectDB {

    private static ConnectDB instance = new ConnectDB();
    private static java.sql.Connection connection = null;

    //Constructor
    public ConnectDB() {
    }

    //Get
    public static ConnectDB getInstance() {
        return instance;
    }

    public static java.sql.Connection getConnection() {
        return connection;
    }

    //Methods
	public static void connectDatabase() {
		try {
			//Login by Windows Authentication
				//String url = "jdbc:sqlserver://localhost:1433;databaseName=HotelManagement;integratedSecurity=true;";
			
			//Login by SQL Login
				String url = "jdbc:sqlserver://localhost:1433;databaseName=QuanLyLSP";
				String username = "sa";
				String password = "12345678";
				
			connection = java.sql.DriverManager.getConnection(url, username, password);
                        System.err.println("Thành công");
		} 
		catch (java.sql.SQLException e) {
//                        System.out.println("Lỗi tại đây");
			e.printStackTrace();
		}
	}
	public static void disconnectDatabase() {
		if(connection != null) {
			try {
				connection.close();
			} catch (java.sql.SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
