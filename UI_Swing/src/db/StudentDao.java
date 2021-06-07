package db;

import java.sql.Connection;
import java.sql.DriverManager;

public class StudentDao {
	private static StudentDao instance;
	private StudentDao() {}
	public static StudentDao getInstance() {
		if (instance == null) {
			instance = new StudentDao();
		}
		return instance;
	}
	
	private final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
	private final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private final String USER = "exam01";
	private final String PASSWORD = "1234";
	
	private Connection getConnection() {
		try {
			Class.forName(DRIVER_NAME);
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println(conn);
			return conn;
		} catch(Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	
}
