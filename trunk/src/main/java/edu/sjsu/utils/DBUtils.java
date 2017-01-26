package edu.sjsu.utils;
import java.sql.*;

public class DBUtils {
	public static Connection getConn() {
		String driverStr = "com.mysql.jdbc.Driver";
		String urlStr = "jdbc:mysql://localhost:3306/cs160";
		String uid = "root";
		String pwd = "root";
		try {
			Class.forName(driverStr);
			return DriverManager.getConnection(urlStr, uid, pwd);
		} catch (SQLException | ClassNotFoundException ex) { 
			System.err.println("The connection failed because " + ex.getMessage());      
			ex.printStackTrace();
			return null;
		}
	}
}