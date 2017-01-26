package edu.sjsu.db;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import edu.sjsu.models.Advisor;
import edu.sjsu.models.Major;
import edu.sjsu.models.Student;
import edu.sjsu.utils.DBUtils;

public class StudentDao {
	
	public static boolean storeStudent(String fname, String lname, String sid, String major, String email, String password, String advisorEmail) {
		try {
			Connection conn = DBUtils.getConn();
			if (conn == null) return false;
			String sqlStr = "insert into students(Email, Password, FName, LName, SID, Verified, AdvisorEmail, MajorID)" +
							"values(?, ?, ?, ?, ?, ?, ?, (SELECT MajorID FROM major WHERE MajorName = ?));";
			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			pstmt.setString(3, fname);
			pstmt.setString(4, lname);
			pstmt.setString(5, sid);
			pstmt.setString(6, "F");
			pstmt.setString(7, advisorEmail);
			pstmt.setString(8, major);
			pstmt.executeUpdate();
			conn.close();
			return true;
		} catch(SQLException se) {
			//Handle errors for JDBC
			se.printStackTrace();
		} catch(Exception e) {
			//Handle errors for Class.forName
			e.printStackTrace();
		}
		return false;
    }
	
	public static Student getStudent(String email) {
		try {
			Connection conn = DBUtils.getConn();
			//Format of return string First Name, Last Name, Major Name, Advisor First Name, Advisor Name
			String sqlStr = "SELECT FName, LName, SID, MajorID, AdvisorEmail " +
					"FROM students WHERE Email = ?;";
			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			pstmt.setString(1,  email);
			ResultSet result = pstmt.executeQuery();
			if (!result.next()) { 
				conn.close();
				return null;
			} else {
				Major studentMajor = MajorDao.getMajor(result.getInt("MajorID"));
				Advisor studentAdvisor = AdvisorDao.getAdvisor(result.getString("AdvisorEmail"));
				Student student = new Student(email, result.getString("FName"), result.getString("LName"), result.getString("SID"),
						studentMajor, studentAdvisor);
				System.out.println("StudentDAO getStudent: " + student.getHashMap().toString());
				conn.close();
				return student;
			}    
		} catch(SQLException se) {
			//Handle errors for JDBC
			se.printStackTrace();
		} catch(Exception e) {
			//Handle errors for Class.forName
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getStudentString(String email){
		try{
			Connection conn = DBUtils.getConn();
			
			String sqlStr = "SELECT a.`FName`, a.`LName`, a.`SID`,c.`MajorName`, a.`AdvisorEmail` " +
				"FROM students a, (SELECT * FROM major) c " +
				"WHERE a.`Email` = ? AND a.MajorID = c.MajorID;";
			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			pstmt.setString(1, email);
			ResultSet result = pstmt.executeQuery();
			if(!result.next()) {
				conn.close();
				return null;
			} else {
				String studentInfo = email + " FNAME " + result.getString("FName") + " LNAME " + result.getString("LName") +
											" SID " + result.getString("SID") + " MajorName " + result.getString("MajorName") + 
											" AdvisorID " + result.getString("AdvisorEmail");	
				conn.close();
				return studentInfo;
			}
		} catch (SQLException se){
			se.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static void activateStudent(String email) {
		try {
			Connection conn = DBUtils.getConn();
			String sqlStr = "update students " +
							"set verified = 'T' " +
							"where Email=?;";
			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			pstmt.setString(1,  email);
			pstmt.executeUpdate();
			conn.close();
		} catch(SQLException se) {
			//Handle errors for JDBC
			se.printStackTrace();
		} catch(Exception e) {
			//Handle errors for Class.forName
			e.printStackTrace();
		}
		return;
	}
	
	public static Map<String, Object> checkAuth(String email, String password) {
		try {
			Connection conn = DBUtils.getConn();
			String sqlStr = "select Email, FName, LName, SID, AdvisorEmail, MajorID from students where Email=? and Password=? and Verified='T'";
			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			ResultSet result = pstmt.executeQuery();
			System.out.println("StudentDao Checking auth with the following query: " +  pstmt.toString());
			System.out.println(result.toString());
			// Check if there are no results, keep in mind this moves the result object pointer
			if (!result.next()) { 
				conn.close();
				return null;    			
			} else {
				Map<String, Object> user = new HashMap<String, Object>();
				user.put("Email", result.getString("Email"));
				user.put("FName", result.getString("FName"));
				user.put("LName", result.getString("LName"));
				user.put("SID", result.getString("SID"));
				user.put("AdvisorEmail", result.getString("AdvisorEmail"));
				user.put("Major", MajorDao.getMajor(result.getInt("MajorID")));
				conn.close();
				return user;
			}
		} catch(SQLException se) {
			//Handle errors for JDBC
			se.printStackTrace();
		} catch(Exception e) {
			//Handle errors for Class.forName
			e.printStackTrace();
		}
		return null;
		
	}
	
	public static boolean updateStudentAdvisor(Student student){
		try{
			Connection conn = DBUtils.getConn();
			String sqlStr = "UPDATE `student SET `AdvisorEmail` = ? WHERE `Email` = ?";
			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			pstmt.setString(1, student.getAdvisor().getEmail());
			pstmt.setString(2, student.getEmail());
			pstmt.executeUpdate();
			conn.close();
			return true;
		}catch (SQLException se){
			se.printStackTrace();
		}catch (Exception e){
			e.printStackTrace();
		}
		return false;
	}
}