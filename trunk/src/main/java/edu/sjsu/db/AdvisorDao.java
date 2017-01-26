package edu.sjsu.db;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import edu.sjsu.models.*;
import edu.sjsu.models.Major;
import edu.sjsu.utils.DBUtils;


public class AdvisorDao {
	
	public static boolean storeAdvisor(String fname, String lname, String sid, String major, String email, String password) {
		try {
			Connection conn = DBUtils.getConn();
			if (conn == null) return false;
			String sqlStr = "insert into advisors(Email, Password, FName, LName, SID, Verified, Major)" +
							"values(?, ?, ?, ?, ?, ?, ?);";
			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			pstmt.setString(3, fname);
			pstmt.setString(4, lname);
			pstmt.setString(5, sid);
			pstmt.setString(6, "F");
			pstmt.setString(7, major);
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
	
	public static Advisor getAdvisor(String email) {
		try {
			Connection conn = DBUtils.getConn();
			String sqlStr = "SELECT FName, LName, SID, MajorID " +
					"FROM advisors WHERE Email = ?;";
			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			pstmt.setString(1,  email);
			ResultSet result = pstmt.executeQuery();
			if (!result.next()) { 
				conn.close();
				return null;    
			} else {
				Major advisorMajor = MajorDao.getMajor(result.getInt("MajorID"));
				Advisor advisor = new Advisor(email, result.getString("FName"), result.getString("LName"), result.getString("SID"), advisorMajor);
				conn.close();
				return advisor;
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
	
	public static Advisor getRandomAdvisor(){
		try {
			Connection conn = DBUtils.getConn();
			String sqlStr = "SELECT Email, FName, LName, SID, MajorID " +
							"FROM advisors;";
			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			ResultSet result = pstmt.executeQuery();
			//TODO: Make this actually random LMAO
			if (!result.next()) { 
				conn.close();
				return null;    
			} else {
				Major advisorMajor = MajorDao.getMajor(result.getInt("MajorID"));
				Advisor advisor = new Advisor(result.getString("Email"), result.getString("FName"), result.getString("LName"), result.getString("SID"), advisorMajor);
				conn.close();
				return advisor;
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
	
	public static String getAdvisorString(String email){
		try{
			Connection conn = DBUtils.getConn();
			String sqlStr = "SELECT a.`FName`, a.`LName`, a.`SID`, b.`MajorName` " +
							"FROM advisors a, major b " +
							"WHERE a.`Email` = ? AND a.MajorID = b.MajorID;";
			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			pstmt.setString(1, email);
			ResultSet result = pstmt.executeQuery();
			if(!result.next()){
				conn.close();
				return null;
			}else{
				String advisorString = "Email " + email + " FName " + result.getString("FName")+ " LNAME " + result.getString("LName")+
										" SID " + result.getString("SID") + " Major Name " + result.getString("MajorName");
				
				conn.close();
				return advisorString;
			}
			
		}catch (SQLException se){
			se.printStackTrace();
		}catch (Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void activateAdvisor(String email) {
		try {
			Connection conn = DBUtils.getConn();
			String sqlStr = "update advisors " +
							"set verified = 'T' " +
							"where Email=?;";
			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			pstmt.setString(1, email);
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
			String sqlStr = "select Email, FName, LName, SID, Major from advisors where Email=? and Password=? and Verified='T'";
			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			ResultSet result = pstmt.executeQuery();
			if (!result.next()) { 
				conn.close();
				return null;
			} else {
				Map<String, Object> user = new HashMap<String, Object>();
				user.put("Email", result.getString("Email"));
				user.put("FName", result.getString("FName"));
				user.put("LName", result.getString("LName"));
				user.put("SID", result.getString("SID"));
				user.put("Major", result.getString("Major"));
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
}