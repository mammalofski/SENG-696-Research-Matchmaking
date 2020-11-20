package matchmaking.orm;
//package net.sqlitetutorial;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase {
	/**
	 * Connect to a sample database
	 */
//	private static DataBase single_instance = null;
	private static Connection conn = null;

	private DataBase() {
		createConnection();
	}
	
	public static Connection createConnection() {
		Connection connection = null;

		try {
			String currentDirectory = System.getProperty("user.dir");
			String url = "jdbc:sqlite:" + currentDirectory + "/mysqlite.db";
			// create a connection to the database
			connection = DriverManager.getConnection(url);

			System.out.println("Connection to SQLite has been established.");

		} catch (SQLException e) {
			System.out.println("in Dataase's catch");
			System.out.println(e.getMessage());
		} finally {
			System.out.println("in finally");
			System.out.println(connection);
//			try {
//				if (connection != null) {
//					connection.close();
//				}
//			} catch (SQLException ex) {
//				System.out.println("in Dataase's catch 2");
//				System.out.println(ex.getMessage());
//			}
		}
		System.out.println("returning conn");
		System.out.println(connection);
		return connection;
	}

	public static Connection getConnection() {
		if (conn == null) 
			conn = createConnection();
		return conn;
	}

	/**
	 * @param args the command line arguments
	 */
//	public static void main(String[] args) {
//		connect();
//	}
}
